package com.czc.bi.controller;

import com.czc.bi.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
public class IndexController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private ShopPassengerflowAnalyzeService shopPassengerflowAnalyzeService;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @RequestMapping("/")
    public String shouye(HttpServletRequest request) {
        request.getSession().setAttribute("today",shopPassengerflowAnalyzeService.selectPdate());
        return "shouye";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception{
        System.out.println("IndexController.login()");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+exception;
                System.out.println("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "/login";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "403";
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
