package com.czc.bi.controller;

import com.czc.bi.pojo.dto.Result;
import com.czc.bi.service.ShopPassengerflowAnalyzeService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/flow")
public class FlowDataController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private ShopPassengerflowAnalyzeService shopPassengerflowAnalyzeService;

    // 当前客流情况
    @RequestMapping(value = "current")
    public Result current(@RequestParam("account") String account, HttpSession session) throws Exception {
        if (account == null) {
            return null;
        }
        String date = (String)session.getAttribute("today");
        Map map = shopPassengerflowAnalyzeService.getCurrentFlow(account,date);
        return new Result(map);
    }


    // 今日客流走势
    @RequestMapping(value = "mainDayFlow")
    public Result mainDayFlow(@RequestParam("account") String account,HttpSession session) throws Exception {
        if (account == null) {
            return null;
        }
        String date = (String)session.getAttribute("today");
        Result res = shopPassengerflowAnalyzeService.getMainDayFlow(account,date);
        return res;
    }

    // 获取首页的周客流echarts图
    @RequestMapping(value = "mainWeekFlow")
    public Result mainWeekFlow(@RequestParam("account") String account) throws ParseException {
        if (account == null) {
            return null;
        }
        Result res = shopPassengerflowAnalyzeService.getMainWeekFlow(account);
        return res;
    }

    // 年客流
    @RequestMapping(value = "yearFlow")
    public Result yearFlow(@RequestParam("account") String account, @RequestParam("time") String time) {
        if (account == null || time == null) {
            return null;
        }
        List<Map> res = shopPassengerflowAnalyzeService.getYearFlow(account, time);
        return new Result(res);
    }

    // 月客流
    @RequestMapping(value = "monthFlow")
    public Result monthFlow(@RequestParam("account") String account, @RequestParam("time") String time) {
        if (account == null || time == null) {
            return null;
        }
        List<Map> res = shopPassengerflowAnalyzeService.getMonthFlow(account, time);
        return new Result(res);
    }

    // 周客流
    @RequestMapping(value = "weekFlow")
    public Result weekFlow(@RequestParam("account") String account, @RequestParam("time") String time) throws ParseException {
        if (account == null || time == null) {
            return null;
        }
        logger.debug("时间信息为："+time);
        List<Map> res = shopPassengerflowAnalyzeService.getWeekFlow(account, time);
        return new Result(res);
    }

    // 天客流
    @RequestMapping(value = "dayFlow")
    public Result dayFlow(@RequestParam("account") String account, @RequestParam("time") String time) {
        if (account == null || time == null) {
            return null;
        }
        List<Map> res = shopPassengerflowAnalyzeService.getDayFlow(account, time);
        return new Result(res);
    }

    // 客户分析 - 新老客户/周
    @RequestMapping(value = "weekNewOldCust")
    public Result weekNewOldCust(@RequestParam("account") String account, @RequestParam("time") String time) throws ParseException {
        if (account == null || time == null) {
            return null;
        }
        Map<String, Map> res = shopPassengerflowAnalyzeService.getWeekNewOldCust(account, time);
        return new Result(res);
    }

    // 客户分析 - 新老客户/月
    @RequestMapping(value = "monthNewOldCust")
    public Result monthNewOldCust(@RequestParam("account") String account, @RequestParam("time") String time) {
        if (account == null || time == null) {
            return null;
        }
        Map<String, Map> res = shopPassengerflowAnalyzeService.getMonthNewOldCust(account, time);
        return new Result(res);
    }

    // 客户分析 - 回流流失/周
    @RequestMapping(value = "weekBackFlow")
    public Result weekBackFlow(@RequestParam("account") String account, @RequestParam("time") String time) throws ParseException {
        if (account == null || time == null) {
            return null;
        }
        Map<String, Map> res = shopPassengerflowAnalyzeService.getWeekBackFlow(account, time);
        return new Result(res);
    }

    // 客户分析 - 回流流失/月
    @RequestMapping(value = "monthBackFlow")
    public Result monthBackFlow(@RequestParam("account") String account, @RequestParam("time") String time) {
        if (account == null || time == null) {
            return null;
        }
        Map<String, Map> res = shopPassengerflowAnalyzeService.getMonthBackFlow(account, time);
        return new Result(res);
    }

    // 今日概况下载页面
    @RequestMapping("currentDownload")
    public ResponseEntity<byte[]> currentDownload(
            @RequestParam("account") String account,
            HttpSession session
            ) throws Exception {
        logger.debug("今日概况下载！！！");
        if (account == null) {
            return null;
        }

        String date = (String)session.getAttribute("today");

        // 今日客流 峰值客流 昨日客流 上周同期
        byte[] bytes = shopPassengerflowAnalyzeService.currentDownload(account,date);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String("客流分析-今日概况.xls".getBytes("UTF-8"), "iso-8859-1"));
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }

    // 客流分析-数据趋势 报告下载
   /* @RequestMapping("dateTrendDownload")
    public ResponseEntity<byte[]> dateTrendDownload(
            @RequestParam("account") String account,
            @RequestParam("time") String time,
            @RequestParam("type") String type) throws Exception {

        if (account == null || time == null) {
            return null;
        }

        return shopPassengerflowAnalyzeService.dateTrendDownload(account, time, type);
    }*/

    // 客户分析 - 新老客户 数据下载
    /*@RequestMapping("newOldCustDownload")
    public ResponseEntity<byte[]> newOldCustDownload(
            @RequestParam("account") String account,
            @RequestParam("time") String time,
            @RequestParam("type") String type) throws Exception {

        if (account == null || time == null) {
            return null;
        }
        return shopPassengerflowAnalyzeService.newOldCustDownload(account, time, type);
    }*/

    // 回流流失数据下载
   /* @RequestMapping("backFlowDownload")
    public ResponseEntity<byte[]> backFlowDownload(
            @RequestParam("account") String account,
            @RequestParam("time") String time,
            @RequestParam("type") String type) throws Exception {

        if (account == null || time == null) {
            return null;
        }
        return shopPassengerflowAnalyzeService.backFlowDownload(account, time, type);
    }*/

}
