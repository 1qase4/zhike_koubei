package com.czc.bi.controller;

import com.czc.bi.pojo.dto.Result;
import com.czc.bi.scheduling.AlipayDataSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/11/27.
 * @version: V1.0
 */
@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private AlipayDataSync alipayDataSync;

    @RequestMapping("syncAlipayDate")
    public Result syncAlipayDate(HttpSession session) throws Exception {
        String account = (String)session.getAttribute("account");
        if("15527771627".equals(account)){
            alipayDataSync.syncAlipayData();
            return new Result().setResult(true).setMessage("sync date complete");
        }else {
            return new Result().setResult(false);
        }
    }
}
