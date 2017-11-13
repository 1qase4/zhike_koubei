package com.czc.bi.scheduling;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportQueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportQueryResponse;
import com.czc.bi.mapper.ShopPassengerflowAnalyzeMapper;
import com.czc.bi.pojo.ShopPassengerflowAnalyze;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.util.AlipayUtil;
import com.czc.bi.util.BaseUtil;
import com.czc.bi.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.czc.bi.util.Constant.UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS;
import static com.czc.bi.util.Constant.UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORTIMEPERIOD;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/10/31.
 * @version: V1.0
 */
@Service
public class CustFlowDataSync {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private AlipayClient client;

    @Autowired
    private ShopPassengerflowAnalyzeMapper shopPassengerflowAnalyzeMapper;

    public void syncDayFlow(String date, String token) throws AlipayApiException {
        // 判断是否为当月1号 月初需要同步上月日均客流数据
        boolean isMonthBegin = date.endsWith("-01") ? true : false;

        // 获取天客流量
        KoubeiMarketingDataAlisisReportQueryRequest kbrequest = new KoubeiMarketingDataAlisisReportQueryRequest();
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS);
        //rc.addCondition("shop_id", "=", shopId);
        rc.addCondition("day", "=", date);

        kbrequest.setBizContent(BaseUtil.jsonToString(rc));
//        kbrequest.putOtherTextParam("app_auth_token", "201710BB587b6a2bf52a4795bba5e7eca40c1C55");
        kbrequest.putOtherTextParam("app_auth_token", token);
        KoubeiMarketingDataAlisisReportQueryResponse kbresponse = client.execute(kbrequest);
        if (!kbresponse.isSuccess()) {
            logger.warn("数据查询调用失败");
            logger.warn(kbresponse.getSubCode());
            logger.warn(kbresponse.getSubMsg());
            return;
        }

        System.out.println("数据查询调用成功");

        List<AlisisReportRow> reportData = kbresponse.getReportData();
        if (reportData == null) {
            logger.debug(String.format("报表[%s]在日期[%s]无数据",
                    UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORTIMEPERIOD,
                    date)
            );
            return;
        }

        List<ShopPassengerflowAnalyze> list = new ArrayList<>(18);

        // 获取数据
        for (AlisisReportRow reportDatum : reportData) {
            // 拆分数据
            List<AlisisReportColumn> rowData = reportDatum.getRowData();
            Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                    "shop_id",
                    "traffic",
                    "shop_name",
                    "month_traffic");
            ShopPassengerflowAnalyze analyze = new ShopPassengerflowAnalyze();
            analyze.setType(Constants.CUSTFLOW_TYPE_DAY)
                    .setRank(1)
                    .setLabel(date)
                    .setPdate(date);
            analyze.setAccount(columnValue.get("shop_id"));
            analyze.setValue(Integer.valueOf(columnValue.get("traffic")));
            analyze.setShop(columnValue.get("shop_name"));
            logger.debug(String.format("获取店铺数据[%s]", analyze));
            list.add(analyze);

            // 月均数据
            if(isMonthBegin){
                String month = date.substring(0,7);
                analyze = new ShopPassengerflowAnalyze();
                analyze.setType(Constants.CUSTFLOW_TYPE_MONTH)
                        .setRank(1)
                        .setLabel(month)
                        .setPdate(date);
                analyze.setAccount(columnValue.get("shop_id"));
                analyze.setValue(Integer.valueOf(columnValue.get("month_traffic")));
                analyze.setShop(columnValue.get("shop_name"));
                logger.debug(String.format("获取店铺月客流数据[%s]", analyze));
                list.add(analyze);

            }
        }
        // 执行数据插入
        shopPassengerflowAnalyzeMapper.replaces(list);
        logger.debug(String.format("客户在日期[%s]时的当日流数据获取完成", date));

    }


    public void syncIntervalFlow(String shopId, String date, String token) throws AlipayApiException {
        KoubeiMarketingDataAlisisReportQueryRequest kbrequest = new KoubeiMarketingDataAlisisReportQueryRequest();
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORTIMEPERIOD);
        rc.addCondition("shop_id", "=", shopId);
        rc.addCondition("day", "=", date);
        kbrequest.setBizContent(BaseUtil.jsonToString(rc));
        kbrequest.putOtherTextParam("app_auth_token", token);
        KoubeiMarketingDataAlisisReportQueryResponse kbresponse = client.execute(kbrequest);

        if (!kbresponse.isSuccess()) {
            logger.debug("数据查询调用失败");
            logger.debug(kbresponse.getSubCode());
            logger.debug(kbresponse.getSubMsg());
            return;
        }

        logger.debug("数据查询调用成功");
        List<AlisisReportRow> reportData = kbresponse.getReportData();
        if (reportData == null) {
            logger.debug(String.format("报表[%s]在日期[%s]无数据",
                    UK_REPORT_YFY_SHOP_DAY_TRAFFIC_ANALYSIS_FORTIMEPERIOD,
                    date)
            );
            return;
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
                    .setAccount(shopId)
                    .setShop("")
                    .setRank(Integer.valueOf(k.split("-")[0]))
                    .setLabel(k)
                    .setValue(v)
                    .setPdate(date);
            list.add(analyze);
        });

        // 执行记录插入
        if (list.size() > 0) {
            shopPassengerflowAnalyzeMapper.replaces(list);
            logger.debug(String.format("客户[%s]在日期[%s]时的分区段客流数据获取完成", shopId, date));
        }
    }
}




