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

import static com.czc.bi.util.AlipayConstant.UK_REPORT_YFY_SHOP_USRANALYSIS_FORWEEK;

/**
 * Created by Administrator on 2017/12/5.
 * 新老用户数据同步
 */
public class SyncUsranalysisForweekData implements SyncJob{
    private Logger logger = Logger.getLogger(SyncCustDayFlowData.class.getName());

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private ShopPassengerflowAnalyzeMapper shopPassengerflowAnalyzeMapper;

    @Override
    public JobResult execute(String shopid, String token, String pdate) {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_USRANALYSIS_FORWEEK);
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
                ShopPassengerflowAnalyze newCust = new ShopPassengerflowAnalyze();
                ShopPassengerflowAnalyze oldCust = new ShopPassengerflowAnalyze();
                List<AlisisReportColumn> rowData = reportDatum.getRowData();
                Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                        "shop_id",
                        "day",
                        "user_cnt_old",
                        "user_cnt_new",
                        "shop_name");
                newCust.setRank(1);
                oldCust.setRank(1);
                newCust.setAccount(columnValue.get("shop_id"));
                oldCust.setAccount(columnValue.get("shop_id"));
                newCust.setShop(columnValue.get("shop_name"));
                oldCust.setShop(columnValue.get("shop_name"));
                newCust.setPdate(columnValue.get("day"));
                oldCust.setPdate(columnValue.get("day"));
                newCust.setLabel(columnValue.get("day"));
                oldCust.setLabel(columnValue.get("day"));
                newCust.setValue(Integer.parseInt(columnValue.get("user_cnt_new")));
                newCust.setType(Constants.NEWCUST_TYPE_DAY);
                oldCust.setValue(Integer.parseInt(columnValue.get("user_cnt_old")));
                oldCust.setType(Constants.OLDCUST_TYPE_DAY);
                logger.debug(String.format("获取店铺当日新用户客流数据[%s]", newCust));
                list.add(newCust);
                logger.debug(String.format("获取店铺当日老用户客流数据[%s]", oldCust));
                list.add(oldCust);
            }

            // 执行数据插入
            int rows = shopPassengerflowAnalyzeMapper.replaces(list);
            logger.debug(String.format("客户在日期[%s]时的当日新老用户流数据获取完成", pdate));
            result.setStatus("success").setRows(rows);
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            result.setStatus("fail").setError(e.getErrCode(),e.getErrMsg());
        }
        return null;
    }
}
