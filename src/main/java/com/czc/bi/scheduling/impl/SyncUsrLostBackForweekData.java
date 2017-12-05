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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.czc.bi.util.AlipayConstant.UK_REPORT_YFY_SHOP_USRANALYSIS_USRLOSTBACK_FORWEEK;

/**
 * Created by Administrator on 2017/12/5.
 * 回流客户数据同步
 */
public class SyncUsrLostBackForweekData implements SyncJob{
    private Logger logger = Logger.getLogger(SyncCustDayFlowData.class.getName());

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private ShopPassengerflowAnalyzeMapper shopPassengerflowAnalyzeMapper;

    @Override
    public JobResult execute(String shopid, String token, String pdate) {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_USRANALYSIS_USRLOSTBACK_FORWEEK);
        rc.addCondition("shop_id", "=",  shopid);
        rc.addCondition("day", "=", pdate);
        JobResult result = new JobResult();
        try {
            List<AlisisReportRow> reportData = alipayService.getReportData(rc, token);
            if (reportData == null) {
                logger.debug(String.format("店铺[%s]报表[%s]在日期[%s]无数据",
                        shopid,
                        "REPORT_YFY_SHOP_USRANALYSIS_USRLOSTBACK_FORWEEK_FORTIMEPERIOD",
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
                if ("1".equals(columnValue.get("categoryidx"))) {
                    shopPassengerflowAnalyze.setType(Constants.LOST_TYPE);
                } else if ("2".equals(columnValue.get("categoryidx"))) {
                    shopPassengerflowAnalyze.setType(Constants.BACK_FLOW_TYPE);
                }
                if (Constants.LOST_TYPE.equals(shopPassengerflowAnalyze.getType())) {
                    shopPassengerflowAnalyze.setValue(Integer.parseInt(columnValue.get("user_cnt")));
                } else if (Constants.BACK_FLOW_TYPE.equals(shopPassengerflowAnalyze.getType())) {
                    shopPassengerflowAnalyze.setValue(Integer.parseInt(columnValue.get("user_cnt")));
                }
                if (shopPassengerflowAnalyze.getType() != null) {
                    logger.debug(String.format("获取店铺当日客户回流数据[%s]", shopPassengerflowAnalyze));
                    list.add(shopPassengerflowAnalyze);
                }
            }

            // 执行数据插入
            int rows = shopPassengerflowAnalyzeMapper.replaces(list);
            logger.debug(String.format("客户在日期[%s]时的当日回流数据获取完成", pdate));
            result.setStatus("success").setRows(rows);
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            result.setStatus("fail").setError(e.getErrCode(),e.getErrMsg());
        }
        return null;
    }
}
