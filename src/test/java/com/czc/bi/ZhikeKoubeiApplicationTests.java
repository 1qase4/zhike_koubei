package com.czc.bi;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportQueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportQueryResponse;
import com.czc.bi.mapper.ShopMapper;
import com.czc.bi.pojo.Shop;
import com.czc.bi.pojo.ShopLabelAnalyze;
import com.czc.bi.pojo.ShopPassengerflowAnalyze;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.service.ShopLabelAnalyzeService;
import com.czc.bi.service.ShopPassengerflowAnalyzeService;
import com.czc.bi.service.ShopService;
import com.czc.bi.util.AlipayUtil;
import com.czc.bi.util.BaseUtil;
import com.czc.bi.util.Constant;
import com.czc.bi.util.Constants;
import org.apache.log4j.Logger;
import org.apache.poi.util.SystemOutLogger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.czc.bi.util.Constant.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZhikeKoubeiApplicationTests {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    public ShopService shopService;

    @Autowired
    public ShopLabelAnalyzeService shopLabelAnalyzeService;

    @Autowired
    private ShopPassengerflowAnalyzeService shopPassengerflowAnalyzeService;

    @Autowired
    private AlipayClient alipayClient;

    @Test
    public void getClient(){
        System.out.println("获取的client---------------------->" + alipayClient);
    }

    @Test
	public void contextLoads() {
        String pdate1 = "2017-09-18";
        String pdate2 = "2017-06-30";
        shopPassengerflowAnalyzeService.updataByPadte(pdate1,pdate2);
        String label1 = "2017-09-18";
        String label2 = "2017-06-30";
        shopPassengerflowAnalyzeService.updataByLabel(label1,label2);
	}

    @Test
    public void a() throws ParseException {
        String pdate1 = "2017-09-17";
        String pdate2 = "2017-06-29";
        for (int i = 0 ;i<73;i++){
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sf.parse(pdate1);
            Date date2 = sf.parse(pdate2);
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar1.setTime(date1);
            calendar2.setTime(date2);
            calendar1.add(Calendar.DATE,-i);
            calendar2.add(Calendar.DATE,-i);
            String a = sf.format(calendar1.getTime());
            String b = sf.format(calendar2.getTime());
            shopPassengerflowAnalyzeService.updataByPadte(a,b);
            shopPassengerflowAnalyzeService.updataByLabel(a,b);
            logger.debug("九月递减时间是："+a);
            logger.debug("六月递减时间是："+b);
        }
    }


    //新老用户
    @Test
    public void insert2() throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk("QK1710256q45gfz4");
        rc.addCondition("shop_id", "=", "2015051400077000000000046605");
        rc.addCondition("day","=","2017-08-10" );
        Map<String,Object> map = AlipayUtil.getKoubeiReport(rc);
        Integer status = (Integer) map.get("status");
        logger.debug((String) map.get("msg"));
        if (status == 0){
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopPassengerflowAnalyze> list = new ArrayList<>();
            if (reportData == null){
                logger.debug("报表无数据");
            } else {
                for (AlisisReportRow reportDatum : reportData) {
                    ShopPassengerflowAnalyze s1 = new ShopPassengerflowAnalyze();
                    ShopPassengerflowAnalyze s2 = new ShopPassengerflowAnalyze();
                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
                    logger.debug("rowData:"+rowData.size());
                    for (AlisisReportColumn rowDatum : rowData) {
                        String alias = rowDatum.getAlias();
                        String data = rowDatum.getData();
                        if ("shop_name".equals(alias)){
                            s1.setShop(data);
                            s2.setShop(data);
                        }
                        if ("shop_id".equals(alias)){
                            s1.setAccount(data);
                            s2.setAccount(data);
                        }
                        if ("day".equals(alias)){
                            s1.setPdate(data);
                            s1.setLabel(data);
                            s2.setPdate(data);
                            s2.setLabel(data);
                        }
                        if ("user_cnt_new".equals(alias)){
                            s1.setRank(1);
                            s1.setType(Constants.NEWCUST_TYPE_DAY);
                            s1.setValue(Integer.parseInt(data));
                        }
                        if ("user_cnt_old".equals(alias)){
                            s2.setRank(1);
                            s2.setType(Constants.OLDCUST_TYPE_DAY);
                            s2.setValue(Integer.parseInt(data));
                        }
                        logger.debug(String.format("alias[%s],data[%s]", alias, data));
                    }
                    list.add(s1);
                    list.add(s2);
                    logger.debug("--------------------");
                }
            }
            shopPassengerflowAnalyzeService.saves(list);
        }

    }

    //保存每周回头客户
    @Test
    public void insert3() throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk("QK171025k863e26v");
        rc.addCondition("shop_id", "=", "2015051400077000000000046605");
        rc.addCondition("day","=","2017-09-25" );
        Map<String,Object> map = AlipayUtil.getKoubeiReport(rc);
        Integer status = (Integer) map.get("status");
        logger.debug((String) map.get("msg"));
        if (status == 0){
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopPassengerflowAnalyze> list = new ArrayList<>();
            if (reportData == null){
                logger.debug("报表无数据");
            } else {
                for (AlisisReportRow reportDatum : reportData) {
                    ShopPassengerflowAnalyze s1 = new ShopPassengerflowAnalyze();
                    ShopPassengerflowAnalyze s2 = new ShopPassengerflowAnalyze();
                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
                    logger.debug("rowData:"+rowData.size());
                    for (AlisisReportColumn rowDatum : rowData) {
                        String alias = rowDatum.getAlias();
                        String data = rowDatum.getData();
                        if ("shop_name".equals(alias)){
                            s1.setShop(data);
                            s2.setShop(data);
                        }
                        if ("shop_id".equals(alias)){
                            s1.setAccount(data);
                            s2.setAccount(data);
                        }
                        if ("day".equals(alias)){
                            s1.setPdate(data);
                            s1.setLabel(data);
                            s2.setPdate(data);
                            s2.setLabel(data);
                        }
                        if ("categoryidx".equals(alias)){
                            if ("2".equals(data)){
                                s1.setRank(1);
                                s1.setType(Constants.LOST_TYPE);
                            }
                            if ("3".equals(data)){
                                s2.setRank(1);
                                s2.setType(Constants.BACK_FLOW_TYPE);
                            }
                        }
                        if ("user_cnt".equals(alias)){
                            if (Constants.LOST_TYPE.equals(s1.getType())){
                                s1.setValue(Integer.parseInt(data));
                            }
                            if (Constants.BACK_FLOW_TYPE.equals(s2.getType())){
                                s2.setValue(Integer.parseInt(data));
                            }
                        }
                        logger.debug(String.format("alias[%s],data[%s]", alias, data));
                    }
                    if (s1.getType()!=null){
                        list.add(s1);
                    }
                    if (s2.getType()!=null){
                        list.add(s2);
                    }
                    logger.debug("--------------------");
                }
            }
            shopPassengerflowAnalyzeService.saves(list);
        }

    }

    //保存商户信息
    @Test
    public void insert4() throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk("QK171030940e6aqv");
        Map<String,Object> map = AlipayUtil.getKoubeiReport(rc);
        Integer status = (Integer) map.get("status");
        logger.debug((String) map.get("msg"));
        if (status == 0){
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<Shop> list = new ArrayList<>();
            if (reportData == null){
                logger.debug("报表无数据");
            } else {
                for (AlisisReportRow reportDatum : reportData) {
                    Shop s1 = new Shop();
                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
                    logger.debug("rowData:"+rowData.size());
                    for (AlisisReportColumn rowDatum : rowData) {
                        String alias = rowDatum.getAlias();
                        String data = rowDatum.getData();
                        if ("shop_name".equals(alias)){
                            s1.setName(data);
                            s1.setInshort(data);
                            s1.setMerchant("鱼非鱼");
                        }
                        if ("shop_id".equals(alias)){
                            s1.setAccount(data);
                        }
                        logger.debug(String.format("alias[%s],data[%s]", alias, data));
                    }
                    list.add(s1);
                    logger.debug("--------------------");
                }
            }
            shopService.saves(list);
        }

    }

    //保存商户信息
    @Test
    public void demo() throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_SHOP_INFO_LIST);
        Map map = AlipayUtil.getKoubeiReportData(rc, "201710BB587b6a2bf52a4795bba5e7eca40c1C55",alipayClient);
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

                // 执行数据插入
                list.add(shop);
//                logger.debug(String.format("客户[%s]在日期[%s]时的当日流数据获取完成", shopId, date));
            }
            shopService.saves(list);
        }
    }

    //新老用户
    @Test
    public void demo2() throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk("QK1710256q45gfz4");
        rc.addCondition("shop_id", "=", "2015051400077000000000046605");
        rc.addCondition("day","=","2017-08-10" );
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,"201710BB587b6a2bf52a4795bba5e7eca40c1C55",alipayClient);
        Integer status = (Integer) map.get("status");
        logger.debug((String) map.get("msg"));
        if (status == 0){
            List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");
            List<ShopPassengerflowAnalyze> list = new ArrayList<>();
            if (reportData == null){
                logger.debug("报表无数据");
            } else {
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
            }
            shopPassengerflowAnalyzeService.saves(list);
        }
    }

    //保存每周回头客户
    @Test
    public void demo3() throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_USRANALYSIS_USRBACK_FORWEEK);  //QK171025k863e26v
        rc.addCondition("shop_id", "=", "2015051400077000000000046605");
        rc.addCondition("day","=","2017-09-25" );
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,"201710BB587b6a2bf52a4795bba5e7eca40c1C55",alipayClient);
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
                        s1.setType(Constants.LOST_TYPE);
                    }else if ("3".equals(columnValue.get("categoryidx"))){
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
                    logger.debug("--------------------");
                }
                shopPassengerflowAnalyzeService.saves(list);
            }
        }
    }

    //客户特征
    @Test
    public void demo5() throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_PROPERTY);  //QK1711019f6d4557
        rc.addCondition("shop_id", "=", "2016042300077000000015402772");
        rc.addCondition("month","=","2017-05" );
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,"201710BB587b6a2bf52a4795bba5e7eca40c1C55",alipayClient);
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

    //各省客流量
    @Test
    public void demo6() throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_PROPERTY_AREA_DIS);  //QK17110221vjfg3r
        rc.addCondition("shop_id", "=", "2015051400077000000000046605");
        rc.addCondition("month","=","2017-04" );
//        rc.addCondition("province","=","黑龙江省" );
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,"201710BB587b6a2bf52a4795bba5e7eca40c1C55",alipayClient);
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
                    s1.setKey(columnValue.get("province"));
                    s1.setShop(columnValue.get("shop_name"));
                    String province = columnValue.get("province");
                    Integer total = 0;
                    if(provinceMap.containsKey(province)){
                        ShopLabelAnalyze labelAnalyze = provinceMap.get(province);
                        total = Integer.parseInt(labelAnalyze.getValue());
                    }
                    Integer pct = Integer.parseInt(columnValue.get("usr_cnt"));
                    total += pct;
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

    //各省客流量
    @Test
    public void demo7() throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk("QK171101ozq154g7");  //QK17110221vjfg3r
//        rc.addCondition("shop_id", "=", "2015052800077000000000121715");
//        rc.addCondition("month","=","201710" );
//        rc.addCondition("province","=","黑龙江省" );
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,"201710BB587b6a2bf52a4795bba5e7eca40c1C55",alipayClient);
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

    //保存每周回流客户
    @Test
    public void demo8() throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_USRANALYSIS_USRLOSTBACK_FORWEEK);  //QK171106873ffwly
//        rc.addCondition("shop_id", "=", "2015060200077000000000124999");
//        rc.addCondition("day","=","2017-08-23" );
        Map<String,Object> map = AlipayUtil.getKoubeiReportData(rc,"201710BB587b6a2bf52a4795bba5e7eca40c1C55",alipayClient);
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
                    logger.debug("--------------------");
                }
                shopPassengerflowAnalyzeService.saves(list);
            }
        }
    }
}
