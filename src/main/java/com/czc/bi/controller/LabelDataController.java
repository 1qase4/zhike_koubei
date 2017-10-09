package com.czc.bi.controller;

import com.czc.bi.pojo.dto.NameValue;
import com.czc.bi.pojo.dto.Result;
import com.czc.bi.service.ShopLabelAnalyzeService;
import org.apache.log4j.Logger;
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
import java.util.ArrayList;
import java.util.Map;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc : 标签计算的请求数据
 * @date : 2016年9月29日 上午10:51:33
 * @version: V1.0
 */
@RestController
@RequestMapping("/label")
public class LabelDataController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private ShopLabelAnalyzeService shopLabelAnalyzeService;

    // 客户分析 - 客户特征
    @ResponseBody
    @RequestMapping("custFeature")
    public Result custFeature(@RequestParam("account") String account, @RequestParam("time") String time) {
        if(account == null || time == null) {
            return null;
        }
        Map<String, Map> custFeature = shopLabelAnalyzeService.getCustFeature(account, time);
        return new Result(custFeature);
    }

    // 来源分析 - 地区分布
    @ResponseBody
    @RequestMapping("custArea")
    public Result custArea(@RequestParam("account") String account, @RequestParam("time") String time) {
        if(account == null || time == null) {
            return null;
        }
        ArrayList<NameValue> custArea = shopLabelAnalyzeService.getCustArea(account, time);
        return new Result(custArea);
    }

    // 获取热力图示
    @ResponseBody
    @RequestMapping("elevation")
    public Result elevation(@RequestParam("account") String account, @RequestParam("time") String time) {
        if(account == null || time == null) {
            return null;
        }
        Map<String, Object> elevation = shopLabelAnalyzeService.getElevation(account, time);
        return new Result(elevation);
    }

    // 客户特征数据下载
    @RequestMapping("custFeatureDownload")
    public ResponseEntity<byte[]> custFeatureDownload(
            @RequestParam("account") String account,
            @RequestParam("time") String time) throws Exception {
        if(account == null || time == null) {
            return null;
        }
        byte[] bytes = shopLabelAnalyzeService.custFeatureDownload(account, time);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "export.xls");
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }

    // 来源分析-地区分布数据下载
    @ResponseBody
    @RequestMapping("custAreaDownload")
    public ResponseEntity<byte[]> custAreaDownload(
            @RequestParam("account") String account,
            @RequestParam("time") String time) throws Exception {
        if(account == null || time == null) {
            return null;
        }
        byte[] bytes = shopLabelAnalyzeService.custAreaDownload(account, time);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String file = "客流分析-客户特征(" + time + ").xls";
        headers.setContentDispositionFormData("attachment", new String(file.getBytes("UTF-8"), "iso-8859-1"));
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }


}
