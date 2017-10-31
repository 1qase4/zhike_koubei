package com.czc.bi.scheduling;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.alipay.api.domain.ContactFollower;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportQueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportQueryResponse;
import com.czc.bi.pojo.Shop;
import com.czc.bi.pojo.ShopPassengerflowAnalyze;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.service.ShopPassengerflowAnalyzeService;
import com.czc.bi.service.ShopService;
import com.czc.bi.util.AlipayUtil;
import com.czc.bi.util.BaseUtil;
import com.czc.bi.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.czc.bi.util.Constant.*;

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
    private ShopPassengerflowAnalyzeService shopPassengerflowAnalyzeService;

    @Autowired
    private ShopService shopService;

    @Scheduled(cron = "0/10 * * * * ?") // 每10秒执行一次
    public void scheduler() {
        logger.info(">>>>>>>>>>>>> scheduled ... ");
    }

    //保存商户信息
    @Scheduled(cron = "0 0 3 * * ?") // 每天凌晨3点执行一次
    public void saveShopList() throws AlipayApiException {
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

    //保存每周新老客户
    @Scheduled(cron = "0 0 3 * * ?") // 每天凌晨3点执行一次
    public void saveUsranalysisForweek() throws AlipayApiException {
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

    //保存：按天统计回头客
    @Scheduled(cron = "0 0 3 * * ?") // 每天凌晨3点执行一次
    public void saveUsrLostandbackForweekk() throws AlipayApiException {
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
                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
                    logger.debug("rowData:"+rowData.size());
                    for (AlisisReportColumn rowDatum : rowData) {
                        String alias = rowDatum.getAlias();
                        String data = rowDatum.getData();
                        if ("shop_name".equals(alias)){
                            s1.setShop(data);
                        }
                        if ("shop_id".equals(alias)){
                            s1.setAccount(data);
                        }
                        if ("day".equals(alias)){
                            s1.setPdate(data);
                            s1.setLabel(data);
                        }
                        if ("categoryidx".equals(alias)){
                            if ("2".equals(data)){
                                s1.setRank(1);
                                s1.setType(Constants.LOST_TYPE);
                            }
                            if ("3".equals(data)){
                                s1.setRank(1);
                                s1.setType(Constants.BACK_FLOW_TYPE);
                            }
                        }
                        if ("user_cnt".equals(alias)){
                            if (Constants.LOST_TYPE.equals(s1.getType())){
                                s1.setValue(Integer.parseInt(data));
                            }
                            if (Constants.BACK_FLOW_TYPE.equals(s1.getType())){
                                s1.setValue(Integer.parseInt(data));
                            }
                        }
                        logger.debug(String.format("alias[%s],data[%s]", alias, data));
                    }
                    if (s1.getType()!=null){
                        list.add(s1);
                    }
                    logger.debug("--------------------");
                }
            }
            shopPassengerflowAnalyzeService.saves(list);
        }
    }
}
