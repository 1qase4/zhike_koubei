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
import com.czc.bi.pojo.ShopPassengerflowAnalyze;
import com.czc.bi.pojo.alipay.ReportDataContext;
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
    private ShopPassengerflowAnalyzeService shopPassengerflowAnalyzeService;

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

    //保存每周回流流失客户
    @Test
    public void insert4() throws AlipayApiException {
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
}
