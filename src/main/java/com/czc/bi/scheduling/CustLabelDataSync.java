package com.czc.bi.scheduling;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.czc.bi.mapper.EtlDateMapper;
import com.czc.bi.mapper.ShopMapper;
import com.czc.bi.mapper.ShopTokenMapper;
import com.czc.bi.pojo.Shop;
import com.czc.bi.pojo.ShopLabelAnalyze;
import com.czc.bi.pojo.ShopPassengerflowAnalyze;
import com.czc.bi.pojo.ShopToken;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.pojo.query.EtlDateQuery;
import com.czc.bi.service.ShopLabelAnalyzeService;
import com.czc.bi.service.ShopPassengerflowAnalyzeService;
import com.czc.bi.service.ShopService;
import com.czc.bi.util.AlipayUtil;
import com.czc.bi.util.BaseUtil;
import com.czc.bi.util.Constants;
import com.czc.bi.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.czc.bi.util.AlipayConstant.*;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : 支付宝-口碑数据同步
 * @date : 2017/10/24.
 * @version: V1.0
 */
@Service
public class CustLabelDataSync {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopLabelAnalyzeService shopLabelAnalyzeService;

    @Autowired
    private CustFlowDataSync custFlowDataSync;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopTokenMapper shopTokenMapper;

    @Autowired
    private EtlDateMapper etlDateMapper;

    private List<String> shops;

    //客户特征
    public void syncShopProperty(String shopId, String pdate, String token) throws Exception {
        // if token equals 201710BB587b6a2bf52a4795bba5e7eca40c1C55 then set alipayclient is special clinet
        AlipayClient alipayClient = this.alipayClient;

        if ("201710BB587b6a2bf52a4795bba5e7eca40c1C55".equals(token)) {
            alipayClient = AlipayUtil.getYFYClient();
        }
        // end

        String month = checkMonthFirst(pdate);
        if ("0".equals(month)) {
            logger.info("日期[" + pdate + "]不是月初,不执行按月取数程序");
            return;
        }

        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_PROPERTY);  //QK1711019f6d4557
        if (shopId != null) {
            rc.addCondition("shop_id", "=", shopId);
        }
        if (month != null) {
            rc.addCondition("month", "=", month);
        }
        Map<String, Object> map = AlipayUtil.getKoubeiReportData(rc, token, alipayClient);
        Integer status = (Integer) map.get("status");
        System.out.println(map.get("msg"));
        if (status == 0) {
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopLabelAnalyze> list = new ArrayList<>();
            if (reportData == null) {
                logger.debug("报表无数据");
            } else {
                for (AlisisReportRow reportDatum : reportData) {
                    ShopLabelAnalyze s1 = new ShopLabelAnalyze();
                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
                    Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                            "shop_id",
                            "month",
                            "indicator",
                            "category",
                            "usr_cnt",
                            "shop_name");
                    s1.setAccount(columnValue.get("shop_id"));
                    s1.setPdate(columnValue.get("month"));
                    String type = columnValue.get("indicator");
                    if ("age".equals(type)) {
                        s1.setType(Constants.AGE_TYPE_MONTH);
                    } else if ("gender".equals(type)) {
                        s1.setType(Constants.GENDER_TYPE_MONTH);
                    } else if ("constellation".equals(type)) {
                        s1.setType(Constants.CONSTELLATIONS_TYPE_MONTH);
                    } else if ("occupation".equals(type)) {
                        s1.setType(Constants.OCCUPATION_TYPE_MONTH);
                    } else if ("consume_level".equals(type)) {
                        s1.setType(Constants.CONSUME_LEVLE_MONTH);
                    } else if ("have_baby".equals(type)) {
                        s1.setType(Constants.HAVECHILD_TYPE_MONTH);
                    }
                    s1.setKey(columnValue.get("category"));
                    s1.setValue(columnValue.get("usr_cnt"));
                    s1.setShop(columnValue.get("shop_name"));
                    list.add(s1);
                }
                shopLabelAnalyzeService.saves(list);
            }
        }
    }

    //客户区域特征
    public void syncShopPropertyArea(String shopId, String pdate, String token) throws Exception {
        // if token equals 201710BB587b6a2bf52a4795bba5e7eca40c1C55 then set alipayclient is special clinet
        AlipayClient alipayClient = this.alipayClient;

        if ("201710BB587b6a2bf52a4795bba5e7eca40c1C55".equals(token)) {
            alipayClient = AlipayUtil.getYFYClient();
        }
        // end

        String month = checkMonthFirst(pdate);
        if ("0".equals(month)) {
            logger.info("日期[" + pdate + "]不是月初,不执行按月取数程序");
            return;
        }

        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_PROPERTY_AREA_DIS);  //QK17110221vjfg3r
        if (shopId != null) {
            rc.addCondition("shop_id", "=", shopId);
        }
        if (month != null) {
            rc.addCondition("month", "=", month);
        }
        Map<String, Object> map = AlipayUtil.getKoubeiReportData(rc, token, alipayClient);
        Integer status = (Integer) map.get("status");
        System.out.println(map.get("msg"));
        if (status == 0) {
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopLabelAnalyze> list = new ArrayList<>();
            Map<String, ShopLabelAnalyze> provinceMap = new HashMap<>(34);
            if (reportData == null) {
                logger.debug("报表无数据");
            } else {
                for (AlisisReportRow reportDatum : reportData) {
                    ShopLabelAnalyze s1 = new ShopLabelAnalyze();

                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
                    Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                            "shop_id",
                            "month",
                            "indicator",
                            "province",
                            "usr_cnt",
                            "shop_name");
                    s1.setAccount(columnValue.get("shop_id"));
                    s1.setPdate(columnValue.get("month"));
                    s1.setType(Constants.PROVINCE_TYPE_MONTH);
                    if (Constants.ProvinceMap.containsKey(columnValue.get("province"))) {
                        s1.setKey(Constants.ProvinceMap.get(columnValue.get("province")));
                    } else {
                        continue;
                    }
                    s1.setShop(columnValue.get("shop_name"));
                    String province = columnValue.get("province");
                    Integer total = 0;
                    if (provinceMap.containsKey(province)) {
                        ShopLabelAnalyze labelAnalyze = provinceMap.get(province);
                        total = Integer.parseInt(labelAnalyze.getValue());
                    }
                    total += Integer.parseInt(columnValue.get("usr_cnt"));
                    s1.setValue(String.valueOf(total));
                    provinceMap.put(province, s1);
                }
                Collection<ShopLabelAnalyze> values = provinceMap.values();
                Iterator<ShopLabelAnalyze> it = values.iterator();
                while (it.hasNext()) {
                    list.add(it.next());
                }
                shopLabelAnalyzeService.saves(list);
            }
        }
    }

    /**
     * 检查日期是否是月初,如果是月初,返回上月的月份String,如果不是,返回0
     *
     * @param pdate 日期
     * @return
     */
    private String checkMonthFirst(String pdate) throws ParseException {
        // 如果不是1号 返回0
        if (!pdate.endsWith("-01")) {
            return "0";
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sf.parse(pdate);
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return BaseUtil.getDateString(calendar.getTime(), "yyyy-MM");
    }

    //周边分布热力图
    public void syncShopHotDiagram(String account, String pdate, String token) throws Exception {
        // if token equals 201710BB587b6a2bf52a4795bba5e7eca40c1C55 then set alipayclient is special clinet
        AlipayClient alipayClient = this.alipayClient;

        if ("201710BB587b6a2bf52a4795bba5e7eca40c1C55".equals(token)) {
            alipayClient = AlipayUtil.getYFYClient();
        }
        // end

        String month = checkMonthFirst(pdate);
        if ("0".equals(month)) {
            logger.info("日期[" + pdate + "]不是月初,不执行按月取数程序");
            return;
        }

        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_HOT_DIAGRAM);  //QK171101ozq154g7
        // 尔华那边month处理不规范,先使用in转换处理
        rc.addCondition("month", "in", String.format("%s,%s", month, month.replace("-", "")));
        rc.addCondition("shop_id", "=", account);
        Map<String, Object> map = AlipayUtil.getKoubeiReportData(rc, token, alipayClient);
        Integer status = (Integer) map.get("status");
        System.out.println(map.get("msg"));
        if (status == 0) {
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopLabelAnalyze> list = new ArrayList<>();
            Map<String, ShopLabelAnalyze> gisMap = new HashMap<>();
            if (reportData == null) {
                logger.debug("报表无数据");
            } else {
                for (AlisisReportRow reportDatum : reportData) {
                    ShopLabelAnalyze s1 = new ShopLabelAnalyze();

                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
                    Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                            "shop_id",
                            "month",
                            "longitude",
                            "latitude",
                            "lng",
                            "lat",
                            "user_cnt",
                            "shop_name");
                    s1.setAccount(columnValue.get("shop_id"));
                    String _month = columnValue.get("month");
                    // if month format "201701" convert to "2017-01"
                    _month = _month.length() == 6?_month.substring(0,4) + "-" + _month.substring(4):_month;
                    s1.setPdate(_month);
                    s1.setType(Constants.ELEVATION_TYPE);
                    String key = columnValue.get("lng") + "," + columnValue.get("lat");
                    s1.setKey(key);
                    s1.setShop(columnValue.get("shop_name"));
                    Integer total = 0;
                    if (gisMap.containsKey(key)) {
                        ShopLabelAnalyze labelAnalyze = gisMap.get(key);
                        total = Integer.parseInt(labelAnalyze.getValue());
                    }
                    Integer pct = Integer.parseInt(columnValue.get("user_cnt"));
                    total += pct;
                    s1.setValue(String.valueOf(total));
                    gisMap.put(key, s1);
                }
                Collection<ShopLabelAnalyze> values = gisMap.values();
                Iterator<ShopLabelAnalyze> it = values.iterator();
                while (it.hasNext()) {
                    list.add(it.next());
                }
                shopLabelAnalyzeService.saves(list);
            }
        }
    }
}
