package com.czc.bi.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlisisReportRow;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportQueryRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportQueryResponse;
import com.czc.bi.mapper.ShopTokenMapper;
import com.czc.bi.pojo.ShopToken;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.pojo.query.ShopTokenQuery;
import com.czc.bi.util.AlipayUtil;
import com.czc.bi.util.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/12/5.
 * @version: V1.0
 */
@Service
public class AlipayService {
    @Autowired
    private AlipayClient alipayClient;

    public List<AlisisReportRow> getReportData(ReportDataContext rc, String token) throws AlipayApiException {
        // if token equals 201710BB587b6a2bf52a4795bba5e7eca40c1C55 then set alipayclient is special clinet
        AlipayClient alipayClient = this.alipayClient;

        if ("201710BB587b6a2bf52a4795bba5e7eca40c1C55".equals(token)) {
            alipayClient = AlipayUtil.getYFYClient();
        }
        // end
        KoubeiMarketingDataAlisisReportQueryRequest request = new KoubeiMarketingDataAlisisReportQueryRequest();
        request.setBizContent(BaseUtil.jsonToString(rc));
        request.putOtherTextParam("app_auth_token", token);
        KoubeiMarketingDataAlisisReportQueryResponse response = alipayClient.execute(request);

        if (!response.isSuccess()) {
            String subCode = response.getSubCode();
            String message = response.getSubMsg();
            AlipayApiException e = new AlipayApiException(subCode, message);
            throw e;
        }
        List<AlisisReportRow> reportData = response.getReportData();
        return reportData;
    }

    @Autowired
    private ShopTokenMapper shopTokenMapper;
    public String getUseridByAuthCode(String authCode){
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authCode);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
            if(!response.isSuccess()){
                return null;
            }
            return response.getUserId();
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
            return null;
        }
    }

}
