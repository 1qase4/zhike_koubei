package com.czc.bi.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.czc.bi.mapper.EtlDateMapper;
import com.czc.bi.mapper.ShopTokenMapper;
import com.czc.bi.mapper.UserMapper;
import com.czc.bi.pojo.EtlDate;
import com.czc.bi.pojo.ShopToken;
import com.czc.bi.pojo.User;
import com.czc.bi.pojo.query.EtlDateQuery;
import com.czc.bi.pojo.query.ShopTokenQuery;
import com.czc.bi.util.BaseUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static com.czc.bi.util.AlipayConstant.*;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : 用户登陆相关
 * @date : 2017/10/10.
 * @version: V1.0
 */
@Service
public class UserService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private ShopTokenMapper shopTokenMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EtlDateMapper etlDateMapper;

    @Autowired
    private AlipayClient alipayClient;

    public boolean authAlipay(String app_id, String account, String app_auth_code) {
        logger.info(String.format("authAlipay-->用户[%s]授权返回信息app_auth_code为[%s],appid[%s]", account, app_auth_code, app_id));
        ShopToken shopToken = new ShopToken();
        shopToken.setApp_auth_code(app_auth_code)
                .setAccount(account)
                .setStat("1")
                .setName(account);

        // 根据app_auth_code换取app_auth_token
        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        request.setBizContent("{" +
                "    \"grant_type\":\"authorization_code\"," +
                "    \"code\":\"" + app_auth_code + "\"" +
                "  }");
        try {
            AlipayOpenAuthTokenAppResponse response = this.alipayClient.execute(request);
            if (response.isSuccess()) {
                String token = response.getAppAuthToken();
                String userId = response.getUserId();
                String authAppId = response.getAuthAppId();
                String expiresIn = response.getExpiresIn();
                String reExpiresIn = response.getReExpiresIn();
                String appRefreshToken = response.getAppRefreshToken();

                shopToken.setApp_auth_token(token)
                        .setUser_id(userId)
                        .setAuth_app_id(authAppId)
                        .setExpires_in(Integer.valueOf(expiresIn))
                        .setRe_expires_in(Integer.valueOf(reExpiresIn))
                        .setApp_refresh_token(appRefreshToken);

                logger.info(String.format("用户[%s]Token[%s]", account, token));
            } else {
                logger.info(String.format("支付宝接口调用失败,ErrorCode[%s],ErrorMsg[%s]",
                        response.getSubCode(),
                        response.getSubMsg()));
                shopToken.setApp_auth_token("NULL_TOKEN");
                return false;
            }
        } catch (AlipayApiException e) {
            shopToken.setApp_auth_token("NULL_TOKEN");
            e.printStackTrace();
            return false;
        }

        shopTokenMapper.insert(shopToken);
        // 处理用户etl数据时间
        EtlDateQuery query = new EtlDateQuery();
        query.setAccount(account);
        int i = etlDateMapper.selectRowsByQuery(query);
        if (i == 0) {
            EtlDate etlDate = new EtlDate();
            etlDate.setAccount(account);
            etlDate.setPdate(BaseUtil.getCurrentDate());
            etlDateMapper.insert(etlDate);
        }
        return true;
    }

    public User findUserByAccount(String username) {
        return userMapper.findUserByAccount(username);
    }


    public boolean authUserToken(String account) {
        ShopTokenQuery query = new ShopTokenQuery();
        query.setAccount(account)
                .setStat("1");
        List<ShopToken> shopTokens = shopTokenMapper.selectByQuery(query);
        if (shopTokens.size() == 0) {
            return false;
        }
        return true;
    }
}


