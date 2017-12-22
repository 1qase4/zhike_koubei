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
import com.czc.bi.pojo.dto.StatusDto;
import com.czc.bi.pojo.query.BaseQuery;
import com.czc.bi.pojo.query.EtlDateQuery;
import com.czc.bi.pojo.query.ShopTokenQuery;
import com.czc.bi.util.BaseUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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

    public String getAccountByUserid(String userId){
        ShopTokenQuery query = new ShopTokenQuery();
        query.setUser_id(userId);
        List<ShopToken> list = shopTokenMapper.selectByQuery(query);
        if(list.size() == 0){
            return null;
        }
        return list.get(0).getAccount();
    }


    // merger local auth info
    public boolean mergeLocalAuth(ShopToken token, HttpSession session){
        ShopTokenQuery query = new ShopTokenQuery();
        query.setUser_id(token.getUser_id());
        List<ShopToken> list = shopTokenMapper.selectByQuery(query);
        if(list.size() == 0){
            String account = token.getAccount();
            if(account != null){
                shopTokenMapper.insert(token);

                // 处理用户etl数据时间
                EtlDate etlDate = new EtlDate();
                etlDate.setAccount(account);
                etlDate.setPdate(BaseUtil.getCurrentDate());
                etlDateMapper.insert(etlDate);
                return true;
            }

            session.setAttribute("token",token);
            return false;
        }else {
            // if user exists update token
            String account = list.get(0).getAccount();
            token.setAccount(account);
            shopTokenMapper.updateToken(token);
            return true;
        }
    }

    public ShopToken alipayOpenAuth(String app_auth_code) {
        // 根据app_auth_code换取app_auth_token
        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        request.setBizContent("{" +
                "    \"grant_type\":\"authorization_code\"," +
                "    \"code\":\"" + app_auth_code + "\"" +
                "  }");
        try {
            AlipayOpenAuthTokenAppResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                ShopToken shopToken = new ShopToken();
                String token = response.getAppAuthToken();
                String userId = response.getUserId();
                String authAppId = response.getAuthAppId();
                String expiresIn = response.getExpiresIn();
                String reExpiresIn = response.getReExpiresIn();
                String appRefreshToken = response.getAppRefreshToken();

                // fill shop token
                shopToken.setApp_auth_token(token)
                        .setUser_id(userId)
                        .setAuth_app_id(authAppId)
                        .setExpires_in(Integer.valueOf(expiresIn))
                        .setRe_expires_in(Integer.valueOf(reExpiresIn))
                        .setApp_refresh_token(appRefreshToken);
                return shopToken;
            }else {
                return null;
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String authAlipay(String app_id, String account, String app_auth_code) {

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
            AlipayOpenAuthTokenAppResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                String token = response.getAppAuthToken();
                String userId = response.getUserId();
                String authAppId = response.getAuthAppId();
                String expiresIn = response.getExpiresIn();
                String reExpiresIn = response.getReExpiresIn();
                String appRefreshToken = response.getAppRefreshToken();

                // fill shop token
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
                return "error";
            }
        } catch (AlipayApiException e) {
            shopToken.setApp_auth_token("NULL_TOKEN");
            e.printStackTrace();
            return "error";
        }

//        shopTokenMapper.mergeToken(shopToken);
//        if (account == null) {
//            // not binding sqs account
//            return "binding";
//        }

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
        return "shouye";
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

    public String saveUser(ShopToken token) {
        int i = shopTokenMapper.checkAccountUserid(token.getAccount(),token.getUser_id());
        if(i > 0){
            return "您已经有绑定记录了,请联系管理员解绑!";
        }
        shopTokenMapper.insert(token);
        return null;
    }
}


