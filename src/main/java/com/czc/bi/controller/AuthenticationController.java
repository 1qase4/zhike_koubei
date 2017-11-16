package com.czc.bi.controller;

import com.czc.bi.pojo.dto.Result;
import com.czc.bi.service.ShopPassengerflowAnalyzeService;
import com.czc.bi.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc : 的请求数据
 * @date : 2017年9月28日 上午10:51:33
 * @version: V1.0
 */
@RestController
@RequestMapping("/sqs_ret")
public class AuthenticationController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private ShopPassengerflowAnalyzeService shopPassengerflowAnalyzeService;


    // 今日客流走势
    @RequestMapping(value = "mainDayFlow")
    public Result mainDayFlow(@RequestParam("account") String account, HttpSession session) throws Exception {
        if (account == null) {
            return null;
        }
        String date = (String) session.getAttribute("today");
        Result res = shopPassengerflowAnalyzeService.getMainDayFlow(account,date);
        return res;
    }


    @Autowired
    private UserService userService;

    // 支付宝回调url
    @RequestMapping(value = "/sqs_ret", produces = "text/plain;charset=UTF-8")
    public String ret(
            @RequestParam("app_id") String app_id,
            @RequestParam(value = "account", required = false) String account,
            @RequestParam("app_auth_code") String app_auth_code) {

        if (account == null) {
            account = "";
        }
        return userService.authAlipay(app_id, account, app_auth_code);
    }

}
