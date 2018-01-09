package com.czc.bi.scheduling.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.czc.bi.mapper.ShopPassengerflowAnalyzeMapper;
import com.czc.bi.pojo.ShopPassengerflowAnalyze;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.scheduling.JobResult;
import com.czc.bi.scheduling.SyncJob;
import com.czc.bi.service.AlipayService;
import com.czc.bi.util.AlipayUtil;
import com.czc.bi.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.czc.bi.util.AlipayConstant.UK_REPORT_YFY_SHOP_USRANALYSIS_USRBACK_FORWEEK;

/**
 * Created by Administrator on 2017/12/5.
 * 回头客数据同步
 */
@Component
public class SyncUsrBackForweekData implements SyncJob {
    private Logger logger = Logger.getLogger(SyncCustDayFlowData.class.getName());

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private ShopPassengerflowAnalyzeMapper shopPassengerflowAnalyzeMapper;

    @Override
    public JobResult execute(String shopid, String token, String pdate) {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_USRANALYSIS_USRBACK_FORWEEK);
        rc.addCondition("shop_id", "=",  shopid);
        rc.addCondition("day", "=", pdate);
        JobResult result = new JobResult();
        try {
            List<AlisisReportRow> reportData = alipayService.getReportData(rc, token);
            if (reportData == null) {
                logger.debug(String.format("店铺[%s]报表[%s]在日期[%s]无数据",
                        shopid,
                        "REPORT_YFY_SHOP_USRANALYSIS_FORWEEK_FORTIMEPERIOD",
                        pdate)
                );

                result.setStatus("success").setRows(0);
                return result;
            }

            // analysis data
            List<ShopPassengerflowAnalyze> list = new ArrayList<>();
            for (AlisisReportRow reportDatum : reportData) {
                ShopPassengerflowAnalyze shopPassengerflowAnalyze = new ShopPassengerflowAnalyze();
                List<AlisisReportColumn> rowData = reportDatum.getRowData();
                Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                        "shop_id",
                        "day",
                        "categoryidx",
                        "user_cnt",
                        "shop_name");
                shopPassengerflowAnalyze.setRank(1);
                shopPassengerflowAnalyze.setAccount(columnValue.get("shop_id"));
                shopPassengerflowAnalyze.setPdate(columnValue.get("day"));
                shopPassengerflowAnalyze.setLabel(columnValue.get("day"));
                shopPassengerflowAnalyze.setShop(columnValue.get("shop_name"));
                if ("2".equals(columnValue.get("categoryidx"))) {
                    shopPassengerflowAnalyze.setType(Constants.RETURN_TYPE);
                } else if ("3".equals(columnValue.get("categoryidx"))) {
                    shopPassengerflowAnalyze.setType(Constants.FREQUENT_TYPE);
                }
                if (Constants.RETURN_TYPE.equals(shopPassengerflowAnalyze.getType())) {
                    shopPassengerflowAnalyze.setValue(Integer.parseInt(columnValue.get("user_cnt")));
                } else if (Constants.FREQUENT_TYPE.equals(shopPassengerflowAnalyze.getType())) {
                    shopPassengerflowAnalyze.setValue(Integer.parseInt(columnValue.get("user_cnt")));
                }
                if (shopPassengerflowAnalyze.getType() != null) {
                    logger.debug(String.format("获取店铺当日回头客流数据[%s]", shopPassengerflowAnalyze));
                    list.add(shopPassengerflowAnalyze);
                }
            }

            // 执行数据插入
            if(list.size() != 0){
                int rows = shopPassengerflowAnalyzeMapper.replaces(list);
                logger.debug(String.format("客户在日期[%s]时的当日回头客流数据获取完成", pdate));
                result.setStatus("success").setRows(rows);
            }else{
                logger.debug(String.format("客户在日期[%s]时的当日无回头客流数据", pdate));
                result.setStatus("success").setRows(0);
            }
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return result.setStatus("fail").setError(e.getErrCode(),e.getErrMsg());
        }
    }
}
