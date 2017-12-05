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

import java.util.List;
import java.util.Map;

import static com.czc.bi.util.AlipayConstant.UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORYEAR;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : 客户月客流
 * @date : 2017/12/4.
 * @version: V1.0
 */

@Component
public class SyncCustMonthFlowData implements SyncJob {
    private Logger logger = Logger.getLogger(SyncCustMonthFlowData.class.getName());

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private ShopPassengerflowAnalyzeMapper shopPassengerflowAnalyzeMapper;

    @Override
    public JobResult execute(String shopid, String token, String month) {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORYEAR);
        rc.addCondition("shop_id", "=", shopid);
        rc.addCondition("month", "=", month);
        JobResult result = new JobResult();

        try {
            List<AlisisReportRow> reportData = alipayService.getReportData(rc, token);
            if (reportData == null) {
                logger.debug(String.format("店铺[%s]报表[%s]在日期[%s]无数据",
                        shopid,
                        "REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORYEAR",
                        month)
                );
                result.setStatus("success").setRows(0);
                return result;
            }

            // analysis data
            AlisisReportRow row = reportData.get(0);
            List<AlisisReportColumn> rowData = row.getRowData();
            Map<String, String> columnValue = AlipayUtil.convertReprot2Map(rowData);
            ShopPassengerflowAnalyze analyze = new ShopPassengerflowAnalyze();

            analyze.setType(Constants.CUSTFLOW_TYPE_MONTH)
                    .setRank(1)
                    .setLabel(month)
                    .setPdate(month);
            analyze.setAccount(columnValue.get("shop_id"));
            analyze.setValue(Integer.valueOf(columnValue.get("month_traffic")));
            analyze.setShop(columnValue.get("shop_name"));
            logger.debug(String.format("获取店铺当日客流数据[%s]", analyze));

            // 执行数据插入
            int rows = shopPassengerflowAnalyzeMapper.replace(analyze);
            logger.debug(String.format("客户在日期[%s]时的当日流数据获取完成", month));
            result.setStatus("success").setRows(rows);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
