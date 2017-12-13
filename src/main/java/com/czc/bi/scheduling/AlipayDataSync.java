package com.czc.bi.scheduling;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.alipay.api.request.AlipayOpenAuthTokenAppQueryRequest;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportQueryRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppQueryResponse;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportQueryResponse;
import com.czc.bi.mapper.EtlDateMapper;
import com.czc.bi.mapper.JobListMapper;
import com.czc.bi.mapper.ShopMapper;
import com.czc.bi.mapper.ShopTokenMapper;
import com.czc.bi.pojo.*;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.pojo.query.EtlDateQuery;
import com.czc.bi.pojo.query.JobListQuery;
import com.czc.bi.pojo.query.ShopTokenQuery;
import com.czc.bi.service.ShopLabelAnalyzeService;
import com.czc.bi.service.ShopPassengerflowAnalyzeService;
import com.czc.bi.service.ShopService;
import com.czc.bi.util.*;
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
    private CustFlowDataSync custFlowDataSync;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopTokenMapper shopTokenMapper;

    @Autowired
    private EtlDateMapper etlDateMapper;

    private List<String> shops;


//    @Scheduled(cron = "0/10 * * * * ?") // 每10秒执行一次
//    public void scheduler(String pdate) {
//        System.out.println(pdate);
//        logger.info(">>>>>>>>>>>>> scheduled ... ");
//    }


    // 同步商户信息
    public void syncShopList(String account, String token) throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_SHOP_INFO_LIST);

        // if account equals 15527771627 then set alipayclient is special clinet
        AlipayClient alipayClient = this.alipayClient;

        if ("15527771627".equals(account)) {
            alipayClient = AlipayUtil.getYFYClient();
        }
        // end

        Map map = AlipayUtil.getKoubeiReportData(rc, token, alipayClient);
        Integer status = (Integer) map.get("status");

        if (status != 0) {
            logger.warn(String.format("店铺数据同步失败msg[%s]", map.get("msg")));
            return;
        }

        List<AlisisReportRow> reportData = (List<AlisisReportRow>) map.get("data");

        this.shops = new ArrayList<>(20);

        for (AlisisReportRow reportDatum : reportData) {
            Shop shop = new Shop();
            List<AlisisReportColumn> rowData = reportDatum.getRowData();

            Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                    "shop_id",
                    "shop_name");
            shop.setAccount(columnValue.get("shop_id"));
            shop.setName(columnValue.get("shop_name"));
            shop.setInshort(columnValue.get("shop_name"));
            shop.setMerchant(account);
            shopMapper.updateAliPay(shop);
            shops.add(columnValue.get("shop_id"));
        }
    }

    @Autowired
    private CustLabelDataSync custLabelDataSync;

    // 验证令牌是否有效
    public boolean isTokenValid(String account, String token) throws AlipayApiException {

        // if account equals 15527771627 then set alipayclient is special clinet
        AlipayClient alipayClient = this.alipayClient;

        if ("15527771627".equals(account)) {
            alipayClient = AlipayUtil.getYFYClient();
        }
        // end

        AlipayOpenAuthTokenAppQueryRequest request = new AlipayOpenAuthTokenAppQueryRequest();
        ReportDataContext rc = new ReportDataContext();
        request.setBizContent("{" +
                "    \"app_auth_token\":\"" + token + "\"" +
                "  }");
        AlipayOpenAuthTokenAppQueryResponse response = alipayClient.execute(request);
        if (!response.isSuccess()) {
            logger.warn(String.format("令牌[%s]有效性查询失败,SubCode[%s],SubMsg[%s]",
                    token,
                    response.getSubCode(),
                    response.getSubMsg()));
            //shopTokenMapper.updataTokenInvalid(account, token);
            return false;
        }

        if ("valid".equals(response.getStatus())) {
            Date authEnd = response.getAuthEnd();
            long diff = authEnd.getTime() - System.currentTimeMillis();
            // 时间小于10天的有效期 将状态置为将过期
            if (diff < 60 * 60 * 24 * 10 * 1000) {
                shopTokenMapper.updataTokenStat(account, token, "9");
            }
            return true;
        } else {
            shopTokenMapper.updataTokenStat(account, token, "0");
            // 其他情况认为令牌已经失效,修改令牌状态
            return false;
        }
    }

//    public void syncAlipayDataByPdate(String _pdate) throws Exception {
//
//        List<ShopToken> shopTokens = shopTokenMapper.selectByCondition("`stat` != '0'");
//
//        List<String> msgs = new ArrayList<>();
//        logger.info("开始同步支付宝口碑数据");
//        // loop get token 同步更数据
//        for (ShopToken shopToken : shopTokens) {
//            String account = shopToken.getAccount();
//            String token = shopToken.getApp_auth_token();
//
//            // 验证token有效性
//            if (!isTokenValid(account, token)) {
//                logger.warn(String.format("账号[%s]令牌[%s]有效性查询失败,跳过数据拉取", account, token));
//                continue;
//            }
//            delay();
//            // get account local etl date
//            EtlDateQuery etlDateQuery = new EtlDateQuery();
//            etlDateQuery.setAccount(account);
//            String localdate = etlDateMapper.selectByQuery(etlDateQuery).get(0).getPdate();
//
//            // get account alipay etl date
//            String alipayDate = getAlipayEtlDate(token);
//            if (alipayDate == null) {
//                logger.warn(String.format("account[%s]get token error continue loop", account));
//                continue;
//            }
//            delay();
//
//            String pdate;
//            // auth date
//            {
//                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//                // if _pdate null, set pdate = nextDay. if not set pdate=_pdate
//                if (_pdate == null) {
//                    pdate = BaseUtil.getNextDateString(localdate);
//                } else {
//                    pdate = _pdate;
//                }
//                Date pdateDate = sf.parse(pdate);
//                Date alipayDateDate = sf.parse(alipayDate);
//                if (pdateDate.after(alipayDateDate)) {
//                    logger.info(String.format("pdate[%s] is after alipayDate[%s] return", pdate, alipayDate));
//                    return;
//                }
//
//            }
//
//            logger.info(String.format("开始同步账号[%s]在日期[%s]的数据", account, pdate));
//            // 同步商户信息
//            syncShopList(account, token);
//            // 同步当日客流
//            logger.info("开始同步当日客流");
//            boolean b = custFlowDataSync.syncDayFlow(pdate, token);
//            if (!b) {
//                msgs.add("同步当日客流同步失败");
//                String format = String.format("账号[%s]在日期[%s]的无法取得客流数据,退出同步,请检查!", account, pdate);
//                logger.info(format);
//                // 发送短信提醒同步失败
//                MessageUtil.sendMessageAdmin(format);
//                continue;
//            }
//            delay();
//
//            b = custFlowDataSync.syncMonthFlow(pdate, token);
//
//            logger.info("开始同步回流情况数据");
//            custFlowDataSync.syncUsrLostBackForweek(pdate, token);
//            delay();
//
//            // 按店铺同步部分
//            // 同步区间客流
//            for (String shop : this.shops) {
//                b = custFlowDataSync.syncIntervalFlow(shop, pdate, token);
//                if (b) {
//                    msgs.add(String.format("店铺[%s]同步区间客流失败", shop));
//                }
//                delay();
//                logger.info("开始同步热力图数据");
//                custLabelDataSync.syncShopHotDiagram(shop, pdate, token);
//                delay();
//                logger.debug(String.format("开始处理shop[%s]", shop));
//                logger.info("开始同步客户特征数据");
//                custLabelDataSync.syncShopProperty(shop, pdate, token);
//                delay();
//                logger.info("开始同步客户区域特征数据");
//                custLabelDataSync.syncShopPropertyArea(shop, pdate, token);
//                delay();
//                logger.info("开始同步每周新老客户数据");
//                custFlowDataSync.syncUsranalysisForweek(shop, pdate, token);
//                delay();
//                logger.info("开始同步回头客数据");
//                custFlowDataSync.syncUsrBackForweek(shop, pdate, token);
//                delay();
//            }
//            logger.info("同步支付宝口碑数据结束");
//            // 更新etl时间
//            if (_pdate != null) {
//                etlDateMapper.updataEtlDate(account, pdate);
//            }
//        }
//        logger.debug("更新数据日志------------>" + msgs);
//
//    }

    @Autowired
    private JobListMapper jobListMapper;

    public void buildJobList() throws Exception {
        // get token
        List<ShopToken> shopTokens = shopTokenMapper.selectByCondition("`stat` != '0'");
        for (ShopToken shopToken : shopTokens) {
            String account = shopToken.getAccount();
            String token = shopToken.getApp_auth_token();

            // 验证token有效性
            if (!isTokenValid(account, token)) {
                logger.warn(String.format("账号[%s]令牌[%s]有效性查询失败,跳过数据拉取", account, token));
                continue;
            }
            delay();
            // get account local etl date
            EtlDateQuery etlDateQuery = new EtlDateQuery();
            etlDateQuery.setAccount(account);
            String localdate = etlDateMapper.selectByQuery(etlDateQuery).get(0).getPdate();

            // get account alipay etl date
            String alipayDate = getAlipayEtlDate(token);
            if (alipayDate == null) {
                logger.warn(String.format("account[%s]get token error continue loop", account));
                continue;
            }
            delay();

            String pdate;
            // auth date
            {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                pdate = BaseUtil.getNextDateString(localdate);
                Date pdateDate = sf.parse(pdate);
                Date alipayDateDate = sf.parse(alipayDate);
                if (pdateDate.after(alipayDateDate)) {
                    logger.info(String.format("pdate[%s] is after alipayDate[%s] return", pdate, alipayDate));
                    return;
                }
            }

            // 同步商户信息
            syncShopList(account, token);

            // modify pdate
            etlDateMapper.updataEtlDate(account, pdate);

            List<JobList> list = new ArrayList<>(80);
            // build job list
            for (String shop : this.shops) {
                JobList jl = new JobList();
                jl.setBean("syncCustDayFlowData").setName("").setShopid(shop).setToken(token).setPdate(pdate).setStauts("fail");
                list.add(jl);

                jl = new JobList();
                jl.setBean("syncIntervalFlowData").setName("").setShopid(shop).setToken(token).setPdate(pdate).setStauts("fail");
                list.add(jl);

                jl = new JobList();
                jl.setBean("syncUsranalysisForweekData").setName("").setShopid(shop).setToken(token).setPdate(pdate).setStauts("fail");
                list.add(jl);

                jl = new JobList();
                jl.setBean("syncUsrBackForweekData").setName("").setShopid(shop).setToken(token).setPdate(pdate).setStauts("fail");
                list.add(jl);

                jl = new JobList();
                jl.setBean("syncUsrLostBackForweekData").setName("").setShopid(shop).setToken(token).setPdate(pdate).setStauts("fail");
                list.add(jl);

                // month data
                if(pdate.endsWith("-01")){
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    String month;
                    Date parse = sf.parse(pdate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(parse);
                    calendar.add(Calendar.MONTH, -1);
                    Date time = calendar.getTime();
                    month = BaseUtil.getDateString(time, "yyyy-MM");

                    jl = new JobList();
                    jl.setBean("syncCustMonthFlowData").setName("").setShopid(shop).setToken(token).setPdate(month).setStauts("fail");
                    list.add(jl);

                    jl = new JobList();
                    jl.setBean("syncShopHotDiagramData").setName("").setShopid(shop).setToken(token).setPdate(month).setStauts("fail");
                    list.add(jl);

                    jl = new JobList();
                    jl.setBean("syncShopPropertyAreaData").setName("").setShopid(shop).setToken(token).setPdate(month).setStauts("fail");
                    list.add(jl);

                    jl = new JobList();
                    jl.setBean("syncShopPropertyData").setName("").setShopid(shop).setToken(token).setPdate(month).setStauts("fail");
                    list.add(jl);
                }
            }
            jobListMapper.replaces(list);
        }
        // syncAlipayDataByPdate(null);
    }

    @Scheduled(cron = "0 0 5 * * ?") // 每天凌晨5点
    public void syncAlipayData() throws Exception {
        logger.debug("begin build joblist");
        buildJobList();
        logger.debug("begin execute job");
        executeJob();
    }

    public void executeJob(){
        JobListQuery query = new JobListQuery();
        query.setStauts("fail");
        List<JobList> list = jobListMapper.selectByQuery(query);
        for (JobList job : list) {
            int id = job.getId();
            String bean = job.getBean();
            SyncJob syncJob = (SyncJob) SpringContextUtils.getBean(bean);
            String shopid = job.getShopid();
            String token = job.getToken();
            String pdate = job.getPdate();
            JobResult result = syncJob.execute(shopid, token, pdate);
            if("success".equals(result.getStatus())){
                int rows = result.getRows();
                jobListMapper.updateSuccess(id,rows);
            }else {
                String remark = String.format("errorCode[%s]errorMsg",result.getErrorCode(),result.getErrorMsg());
                jobListMapper.updateFail(id,remark);
            }
            delay();
        }

    }

    /**
     * get alipay etl date
     *
     * @param token
     * @return date
     */
    private String getAlipayEtlDate(String token) throws AlipayApiException {
        // if token equals 201710BB587b6a2bf52a4795bba5e7eca40c1C55 then set alipayclient is special clinet
        AlipayClient alipayClient = this.alipayClient;

        if ("201710BB587b6a2bf52a4795bba5e7eca40c1C55".equals(token)) {
            alipayClient = AlipayUtil.getYFYClient();
        }
        // end

        KoubeiMarketingDataAlisisReportQueryRequest request = new KoubeiMarketingDataAlisisReportQueryRequest();
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_SHOP_PDATELIST);
        request.setBizContent(BaseUtil.jsonToString(rc));
        request.putOtherTextParam("app_auth_token", token);
        KoubeiMarketingDataAlisisReportQueryResponse response = alipayClient.execute(request);
        if (!response.isSuccess()) {
            logger.warn(String.format("token[%s]get etl date fail, errorCode[%s], errorMessage[%s]",
                    token,
                    response.getSubCode(),
                    response.getSubMsg()));
            return null;
        }
        List<AlisisReportRow> reportData = response.getReportData();
        if (reportData == null) {
            logger.warn(String.format("token[%s] has no etl date",
                    token));
            return null;
        }
        // parse response's etl date,   only one recode
        List<AlisisReportColumn> alisisReportRow = reportData.get(0).getRowData();
        Map<String, String> columnValue = AlipayUtil.getColumnValue(alisisReportRow, "pdate");
        String pdate = columnValue.get("pdate");
        return pdate;
    }

//    /**
//     * get dates between local_pdate and alipay_pdate
//     *
//     * @param local
//     * @param alipay
//     * @return list of dateString
//     * @throws ParseException
//     */
//    List<String> getDatesBetween(String local, String alipay) throws ParseException {
//        // convert string to date
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//        Date localDate = sf.parse(local);
//        Date alipayDate = sf.parse(alipay);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(localDate);
//
//        List<String> res = new ArrayList<>(2);
//        // to prevent dead loop, set max loop time=4
//        for (int i = 0; i < 3; i++) {
//            calendar.add(Calendar.DATE, 1);
//            Date time = calendar.getTime();
//            // if time after alipay time break loop
//            if (time.after(alipayDate)) {
//                break;
//            }
//            res.add(BaseUtil.getDateString(time));
//        }
//        return res;
//    }

    /**
     * delay 1s
     * because alipay limit request time
     */
    private void delay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
