package com.czc.bi.scheduling;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.alipay.api.request.AlipayOpenAuthTokenAppQueryRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppQueryResponse;
import com.czc.bi.mapper.EtlDateMapper;
import com.czc.bi.mapper.ShopMapper;
import com.czc.bi.mapper.ShopTokenMapper;
import com.czc.bi.pojo.*;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.pojo.query.EtlDateQuery;
import com.czc.bi.pojo.query.ShopTokenQuery;
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
    public void scheduler() {
        logger.info(">>>>>>>>>>>>> scheduled ... ");
    }


    // 同步商户信息
    public void syncShopList(String account, String token) throws AlipayApiException {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_SHOP_INFO_LIST);

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

    @Scheduled(cron = "0 0 3 * * ?") // 每天凌晨3点
    public void syncAlipayData() throws Exception {

        List<ShopToken> shopTokens = shopTokenMapper.selectByCondition("`stat` != '0'");

        List<String> msgs = new ArrayList<>();
        logger.info("开始同步支付宝口碑数据");
        // 循环token 同步更数据
        for (ShopToken shopToken : shopTokens) {
            String account = shopToken.getAccount();
            String token = shopToken.getApp_auth_token();

            // 验证token有效性
            if (isTokenValid(account, token)) {
                logger.warn(String.format("账号[%s]令牌[%s]有效性查询失败,跳过", account, token));
            }

            // 根据账号 获取账号的当前数据时间
            EtlDateQuery etlDateQuery = new EtlDateQuery();
            etlDateQuery.setAccount(account);
            String pdate = etlDateMapper.selectByQuery(etlDateQuery).get(0).getPdate();
            pdate = BaseUtil.getNextDateString(pdate);
            logger.info(String.format("开始同步账号[%s]在日期[%s]的数据", account, pdate));
            // 同步商户信息
            syncShopList(account, token);
            // 同步当日客流
            logger.info("开始同步当日客流");
            boolean b = custFlowDataSync.syncDayFlow(pdate, token);
            if (!b) {
                msgs.add("同步当日客流同步失败");
                String format = String.format("账号[%s]在日期[%s]的无法取得客流数据,退出同步,请检查!", account, pdate);
                logger.info(format);
                // 发送短信提醒同步失败
                MessageUtil.sendMessageAdmin(format);
                continue;
            }
            Thread.sleep(1000);

            logger.info("开始同步热力图数据");
            custLabelDataSync.syncShopHotDiagram(pdate, token);
            Thread.sleep(1000);

            logger.info("开始同步回流情况数据");
            custFlowDataSync.syncUsrLostBackForweek(pdate, token);
            Thread.sleep(1000);

            // 按店铺同步部分
            // 同步区间客流
            for (String shop : this.shops) {
                b = custFlowDataSync.syncIntervalFlow(shop, pdate, token);
                if (b) {
                    msgs.add(String.format("店铺[%s]同步区间客流失败", shop));
                }
                Thread.sleep(1000);

                logger.debug(String.format("开始处理shop[%s]", shop));
                logger.info("开始同步客户特征数据");
                custLabelDataSync.syncShopProperty(shop, pdate, token);
                Thread.sleep(1000);
                logger.info("开始同步客户区域特征数据");
                custLabelDataSync.syncShopPropertyArea(shop, pdate, token);
                Thread.sleep(1000);
                logger.info("开始同步每周新老客户数据");
                custFlowDataSync.syncUsranalysisForweek(shop, pdate, token);
                Thread.sleep(1000);
                logger.info("开始同步回头客数据");
                custFlowDataSync.syncUsrBackForweek(shop, pdate, token);
                Thread.sleep(1000);
            }
            logger.info("同步支付宝口碑数据结束");
            // 更新etl时间
            etlDateMapper.updataEtlDate(account, pdate);
        }

        logger.debug("更新数据日志------------>" + msgs);

    }
}
