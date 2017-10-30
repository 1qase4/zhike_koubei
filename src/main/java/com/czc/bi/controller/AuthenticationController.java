package com.czc.bi.controller;

import com.czc.bi.pojo.dto.Result;
import com.czc.bi.service.ShopPassengerflowAnalyzeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
    public Result mainDayFlow(@RequestParam("account") String account) {
        if (account == null) {
            return null;
        }
        Result res = shopPassengerflowAnalyzeService.getMainDayFlow(account);
        return res;
    }


}