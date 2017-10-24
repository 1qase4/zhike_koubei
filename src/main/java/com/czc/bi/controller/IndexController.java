package com.czc.bi.controller;

import com.czc.bi.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
public class IndexController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @RequestMapping("/")
    public String shouye() {
        return "shouye";
    }

    @RequestMapping("/todayProfile")
    public String todayProfile() {
        return "todayProfile";
    }

    @RequestMapping("/dataTrends")
    public String dataTrends() {
        return "dataTrends";
    }

    @RequestMapping("/oldNewUsers")
    public String oldNewUsers() {
        return "oldNewUsers";
    }

    @RequestMapping("/returnLoss")
    public String returnLoss() {
        return "returnLoss";
    }

    @RequestMapping("/clientFeature")
    public String clientFeature() {
        return "clientFeature";
    }

    @RequestMapping("/areaDistributed")
    public String areaDistributed() {
        return "areaDistributed";
    }

    @RequestMapping("/peripheryDistributed")
    public String peripheryDistributed() {
        return "peripheryDistributed";
    }

    @RequestMapping("/modify")
    public String modify() {
        return "modify";
    }


    @Autowired
    private UserService userService;

    // 支付宝回调url
    @RequestMapping(value = "/sqs_ret", produces = "text/plain;charset=UTF-8")
    @ResponseBody
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
