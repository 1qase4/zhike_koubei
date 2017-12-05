package com.czc.bi.scheduling.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlisisReportColumn;
import com.czc.bi.mapper.ShopPassengerflowAnalyzeMapper;
import com.czc.bi.pojo.ShopPassengerflowAnalyze;
import com.czc.bi.service.AlipayService;
import com.czc.bi.util.AlipayUtil;
import org.apache.log4j.Logger;
import com.alipay.api.domain.AlisisReportRow;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.scheduling.JobResult;
import com.czc.bi.scheduling.SyncJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.czc.bi.util.AlipayConstant.UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORTIMEPERIOD;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/12/5.
 * @version: V1.0
 */
@Component
public class SyncIntervalFlowData implements SyncJob {
    private Logger logger = Logger.getLogger(SyncCustDayFlowData.class.getName());

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private ShopPassengerflowAnalyzeMapper shopPassengerflowAnalyzeMapper;

    @Override
    public JobResult execute(String shopid, String token, String pdate) {

        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORTIMEPERIOD);
        rc.addCondition("shop_id", "=", shopid);
        rc.addCondition("day", "=", pdate);
        JobResult result = new JobResult();
        try {
            List<AlisisReportRow> reportData = alipayService.getReportData(rc, token);
            if (reportData == null) {
                logger.debug(String.format("店铺[%s]报表[%s]在日期[%s]无数据",
                        shopid,
                        "REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORTIMEPERIOD",
                        pdate)
                );

                result.setStatus("success").setRows(0);
                return result;
            }

            List<ShopPassengerflowAnalyze> list = new ArrayList<>(24);
            // 构建一个全0的map
            Map<String, Integer> map = new HashMap<>(24);
            map.put("0-1点", 0);
            map.put("1-2点", 0);
            map.put("2-3点", 0);
            map.put("3-4点", 0);
            map.put("4-5点", 0);
            map.put("5-6点", 0);
            map.put("6-7点", 0);
            map.put("7-8点", 0);
            map.put("8-9点", 0);
            map.put("9-10点", 0);
            map.put("10-11点", 0);
            map.put("11-12点", 0);
            map.put("12-13点", 0);
            map.put("13-14点", 0);
            map.put("14-15点", 0);
            map.put("15-16点", 0);
            map.put("16-17点", 0);
            map.put("17-18点", 0);
            map.put("18-19点", 0);
            map.put("19-20点", 0);
            map.put("20-21点", 0);
            map.put("21-22点", 0);
            map.put("22-23点", 0);

            for (AlisisReportRow reportDatum : reportData) {
                List<AlisisReportColumn> rowData = reportDatum.getRowData();
                // 提取行数据
                Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData, "timeperiod", "traffic");
                String timeperiod = columnValue.get("timeperiod");
                String traffic = columnValue.get("traffic");

                // 添加数据
                if (map.containsKey(timeperiod)) {
                    map.put(timeperiod, Integer.valueOf(traffic));
                }
            }

            // 将记录添加到list
            map.forEach((k, v) -> {
                ShopPassengerflowAnalyze analyze = new ShopPassengerflowAnalyze();
                analyze.setType("按小时统计客流")
                        .setAccount(shopid)
                        .setShop("")
                        .setRank(Integer.valueOf(k.split("-")[0]))
                        .setLabel(k)
                        .setValue(v)
                        .setPdate(pdate);
                list.add(analyze);
            });

            int rows;
            // 执行记录插入
            if (list.size() > 0) {
                rows = shopPassengerflowAnalyzeMapper.replaces(list);
                logger.debug(String.format("客户[%s]在日期[%s]时的分区段客流数据获取完成", shopid, pdate));
                return result.setStatus("success").setRows(rows);
            } else {
                return result.setStatus("success").setRows(0);
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return null;
    }
}
