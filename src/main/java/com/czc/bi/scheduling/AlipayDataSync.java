package com.czc.bi.scheduling;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.czc.bi.mapper.ShopTokenMapper;
import com.czc.bi.pojo.Shop;
import com.czc.bi.pojo.ShopLabelAnalyze;
import com.czc.bi.pojo.ShopPassengerflowAnalyze;
import com.czc.bi.pojo.ShopToken;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.pojo.query.ShopTokenQuery;
import com.czc.bi.service.ShopLabelAnalyzeService;
import com.czc.bi.service.ShopPassengerflowAnalyzeService;
import com.czc.bi.service.ShopService;
import com.czc.bi.util.AlipayUtil;
import com.czc.bi.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
public class AlipayDataSync {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private ShopPassengerflowAnalyzeService shopPassengerflowAnalyzeService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopLabelAnalyzeService shopLabelAnalyzeService;

    @Autowired
    private ShopTokenMapper shopTokenMapper;

//    @Scheduled(cron = "0/10 * * * * ?") // 每10秒执行一次
    public void scheduler() {
        logger.info(">>>>>>>>>>>>> scheduled ... ");
    }

    // 获取现存的所有token数据
    public void getAllToken(){
        List<ShopToken> shopTokens = shopTokenMapper.selectByCondition("1=1");

        // 循环token 同步更数据
        for (ShopToken shopToken : shopTokens) {
            String account = shopToken.getAccount();
            String appAuthToken = shopToken.getApp_auth_token();
            // 根据账号 获取账号的当前数据时间

        }

        System.out.println(shopTokens);
    }



    //保存商户信息
    public void syncShopList(String date, String token) throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_SHOP_INFO_LIST);
        if (date != null){
            rc.addCondition("day","=",date );
        }
        Map map = AlipayUtil.getKoubeiReportData(rc, token, alipayClient);
        Integer status = (Integer) map.get("status");
        System.out.println(map.get("msg"));
        if (status == 0){
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<Shop> list = new ArrayList<>();
            for (AlisisReportRow reportDatum : reportData) {
                Shop shop = new Shop();
                List<AlisisReportColumn> rowData = reportDatum.getRowData();

                Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                        "shop_id",
                        "shop_name");
                shop.setAccount(columnValue.get("shop_id"));
                shop.setName(columnValue.get("shop_name"));
                shop.setInshort(columnValue.get("shop_name"));

                list.add(shop);
            }
            shopService.saves(list);
        }
    }

    //保存每周新老客户
    public void syncUsranalysisForweek(String shopId, String date, String token) throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_USRANALYSIS_FORWEEK);
        if (shopId != null){
            rc.addCondition("shop_id", "=", shopId);
        }
        if (date != null){
            rc.addCondition("day","=",date );
        }
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,token,alipayClient);
        Integer status = (Integer) map.get("status");
        System.out.println(map.get("msg"));
        if (status == 0){
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopPassengerflowAnalyze> list = new ArrayList<>();
            if (reportData == null){
                System.out.println("报表无数据");
                return;
            }

            for (AlisisReportRow reportDatum : reportData) {
                ShopPassengerflowAnalyze s1 = new ShopPassengerflowAnalyze();
                ShopPassengerflowAnalyze s2 = new ShopPassengerflowAnalyze();
                List<AlisisReportColumn> rowData = reportDatum.getRowData();
                Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                        "shop_id",
                        "day",
                        "user_cnt_old",
                        "user_cnt_new",
                        "shop_name");
                s1.setRank(1);
                s2.setRank(1);
                s1.setAccount(columnValue.get("shop_id"));
                s2.setAccount(columnValue.get("shop_id"));
                s1.setShop(columnValue.get("shop_name"));
                s2.setShop(columnValue.get("shop_name"));
                s1.setPdate(columnValue.get("day"));
                s2.setPdate(columnValue.get("day"));
                s1.setLabel(columnValue.get("day"));
                s2.setLabel(columnValue.get("day"));
                s1.setValue(Integer.parseInt(columnValue.get("user_cnt_new")));
                s1.setType(Constants.NEWCUST_TYPE_DAY);
                s2.setValue(Integer.parseInt(columnValue.get("user_cnt_old")));
                s2.setType(Constants.OLDCUST_TYPE_DAY);
                list.add(s1);
                list.add(s2);

            }
            shopPassengerflowAnalyzeService.saves(list);
        }
    }

    //保存：按天统计回头客
    public void syncUsrBackForweek(String shopId, String date, String token) throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_USRANALYSIS_USRBACK_FORWEEK);  //QK171025k863e26v
        if (shopId != null){
            rc.addCondition("shop_id", "=", shopId);
        }
        if (date != null){
            rc.addCondition("day","=",date );
        }
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,token,alipayClient);
        Integer status = (Integer) map.get("status");
        System.out.println(map.get("msg"));
        if (status == 0){
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopPassengerflowAnalyze> list = new ArrayList<>();
            if (reportData == null){
                logger.debug("报表无数据");
            } else {
                for (AlisisReportRow reportDatum : reportData) {
                    ShopPassengerflowAnalyze s1 = new ShopPassengerflowAnalyze();
                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
                    Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                            "shop_id",
                            "day",
                            "categoryidx",
                            "user_cnt",
                            "shop_name");
                    s1.setRank(1);
                    s1.setAccount(columnValue.get("shop_id"));
                    s1.setPdate(columnValue.get("day"));
                    s1.setLabel(columnValue.get("day"));
                    s1.setShop(columnValue.get("shop_name"));
                    if ("2".equals(columnValue.get("categoryidx"))){
                        s1.setType(Constants.RETURN_TYPE);
                    }else if ("3".equals(columnValue.get("categoryidx"))){
                        s1.setType(Constants.FREQUENT_TYPE);
                    }
                    if (Constants.RETURN_TYPE.equals(s1.getType())){
                        s1.setValue(Integer.parseInt(columnValue.get("user_cnt")));
                    }else if (Constants.FREQUENT_TYPE.equals(s1.getType())){
                        s1.setValue(Integer.parseInt(columnValue.get("user_cnt")));
                    }
                    if (s1.getType()!=null){
                        list.add(s1);
                    }
                }
                shopPassengerflowAnalyzeService.saves(list);
            }
        }
    }

    //保存：按天统计回流情况
    public void syncUsrLostBackForweek(String date, String token) throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_USRANALYSIS_USRLOSTBACK_FORWEEK);  //QK171106873ffwly
        if (date != null){
            rc.addCondition("day","=",date );
        }
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,token,alipayClient);
        Integer status = (Integer) map.get("status");
        System.out.println(map.get("msg"));
        if (status == 0){
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopPassengerflowAnalyze> list = new ArrayList<>();
            if (reportData == null){
                logger.debug("报表无数据");
            } else {
                for (AlisisReportRow reportDatum : reportData) {
                    ShopPassengerflowAnalyze s1 = new ShopPassengerflowAnalyze();
                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
                    Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                            "shop_id",
                            "day",
                            "categoryidx",
                            "user_cnt",
                            "shop_name");
                    s1.setRank(1);
                    s1.setAccount(columnValue.get("shop_id"));
                    s1.setPdate(columnValue.get("day"));
                    s1.setLabel(columnValue.get("day"));
                    s1.setShop(columnValue.get("shop_name"));
                    if ("1".equals(columnValue.get("categoryidx"))){
                        s1.setType(Constants.LOST_TYPE);
                    }else if ("2".equals(columnValue.get("categoryidx"))){
                        s1.setType(Constants.BACK_FLOW_TYPE);
                    }
                    if (Constants.LOST_TYPE.equals(s1.getType())){
                        s1.setValue(Integer.parseInt(columnValue.get("user_cnt")));
                    }else if (Constants.BACK_FLOW_TYPE.equals(s1.getType())){
                        s1.setValue(Integer.parseInt(columnValue.get("user_cnt")));
                    }
                    if (s1.getType()!=null){
                        list.add(s1);
                    }
                }
                shopPassengerflowAnalyzeService.saves(list);
            }
        }
    }

    //客户特征
    public void syncShopProperty(String shopId, String date, String token) throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_PROPERTY);  //QK1711019f6d4557
        if (shopId != null){
            rc.addCondition("shop_id", "=", shopId);
        }
        if (date != null){
            rc.addCondition("day","=",date );
        }
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,token,alipayClient);
        Integer status = (Integer) map.get("status");
        System.out.println(map.get("msg"));
        if (status == 0){
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopLabelAnalyze> list = new ArrayList<>();
            if (reportData == null){
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
                    if ("age".equals(type)){
                        s1.setType(Constants.AGE_TYPE_MONTH);
                    }else if ("gender".equals(type)){
                        s1.setType(Constants.GENDER_TYPE_MONTH);
                    }else if ("constellation".equals(type)){
                        s1.setType(Constants.CONSTELLATIONS_TYPE_MONTH);
                    }else if ("occupation".equals(type)){
                        s1.setType(Constants.OCCUPATION_TYPE_MONTH);
                    }else if ("consume_level".equals(type)){
                        s1.setType(Constants.CONSUME_LEVLE_MONTH);
                    }else if ("have_baby".equals(type)){
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
    public void syncShopPropertyArea(String shopId, String date, String token) throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_PROPERTY_AREA_DIS);  //QK17110221vjfg3r
        if (shopId != null){
            rc.addCondition("shop_id", "=", shopId);
        }
        if (date != null){
            rc.addCondition("day","=",date );
        }
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,token,alipayClient);
        Integer status = (Integer) map.get("status");
        System.out.println(map.get("msg"));
        if (status == 0){
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopLabelAnalyze> list = new ArrayList<>();
            Map<String,ShopLabelAnalyze> provinceMap = new HashMap<>(34);
            if (reportData == null){
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
                    if (Constants.ProvinceMap.containsKey(columnValue.get("province"))){
                        s1.setKey(Constants.ProvinceMap.get(columnValue.get("province")));
                    }
                    s1.setShop(columnValue.get("shop_name"));
                    String province = columnValue.get("province");
                    Integer total = 0;
                    if(provinceMap.containsKey(province)){
                        ShopLabelAnalyze labelAnalyze = provinceMap.get(province);
                        total = Integer.parseInt(labelAnalyze.getValue());
                    }
                    total += Integer.parseInt(columnValue.get("usr_cnt"));
                    s1.setValue(String.valueOf(total));
                    provinceMap.put(province,s1);
                }
                Collection<ShopLabelAnalyze> values =  provinceMap.values();
                Iterator<ShopLabelAnalyze> it = values.iterator();
                while(it.hasNext()) {
                    list.add(it.next());
                }
                shopLabelAnalyzeService.saves(list);
            }
        }
    }

    //周边分布热力图
    public void syncShopHotDiagram(String date, String token) throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_HOT_DIAGRAM);  //QK171101ozq154g7
        if (date != null){
            rc.addCondition("day","=",date );
        }
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,token,alipayClient);
        Integer status = (Integer) map.get("status");
        System.out.println(map.get("msg"));
        if (status == 0){
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopLabelAnalyze> list = new ArrayList<>();
            Map<String,ShopLabelAnalyze> gisMap = new HashMap<>();
            if (reportData == null){
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
                    s1.setPdate(columnValue.get("month"));
                    s1.setType(Constants.ELEVATION_TYPE);
                    String key = columnValue.get("lng")+","+columnValue.get("lat");
                    s1.setKey(key);
                    s1.setShop(columnValue.get("shop_name"));
                    Integer total = 0;
                    if(gisMap.containsKey(key)){
                        ShopLabelAnalyze labelAnalyze = gisMap.get(key);
                        total = Integer.parseInt(labelAnalyze.getValue());
                    }
                    Integer pct = Integer.parseInt(columnValue.get("user_cnt"));
                    total += pct;
                    s1.setValue(String.valueOf(total));
                    gisMap.put(key,s1);
                }
                Collection<ShopLabelAnalyze> values =  gisMap.values();
                Iterator<ShopLabelAnalyze> it = values.iterator();
                while(it.hasNext()) {
                    list.add(it.next());
                }
                shopLabelAnalyzeService.saves(list);
            }
        }
    }

    @Scheduled(cron = "0 0 3 * * ?") // 每天凌晨3点
    public void syncAlipayData() throws ParseException {
        String pdate = shopPassengerflowAnalyzeService.selectPdate();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sf.parse(pdate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,1);
        String format = sf.format(calendar.getTime());
        String token = "201710BB587b6a2bf52a4795bba5e7eca40c1C55";
        try {
            logger.info("开始同步支付宝口碑数据");
            logger.info("开始同步商铺信息数据");
            syncShopList(null,token);
            Thread.sleep(1300);

            logger.info("开始同步热力图数据");
            syncShopHotDiagram(null,token);
            Thread.sleep(1300);

            logger.info("开始同步回流情况数据");
            syncUsrLostBackForweek(null,token);
            List<String> shopIds = shopService.selectAllShopId();
            for (String shopId : shopIds) {
                Thread.sleep(1300);
                logger.debug(String.format("开始处理shop[%s]",shopId));
                logger.info("开始同步客户特征数据");
                syncShopProperty(shopId,null,token);
                Thread.sleep(1300);
                logger.info("开始同步客户区域特征数据");
                syncShopPropertyArea(shopId,null,token);
                Thread.sleep(1300);
                logger.info("开始同步每周新老客户数据");
                syncUsranalysisForweek(shopId,null,token);
                Thread.sleep(1300);
                logger.info("开始同步回头客数据");
                syncUsrBackForweek(shopId,null,token);
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("同步支付宝口碑数据结束");
        shopPassengerflowAnalyzeService.updataEtlDate(format);
    }
}
