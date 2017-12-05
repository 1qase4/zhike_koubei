package com.czc.bi.controller;

import com.czc.bi.pojo.dto.Result;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/11/14.
 * @version: V1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    // 用户登陆
    @ResponseBody
    @RequestMapping("/login")
    public Result login(HttpServletRequest request, HttpSession session, String username, String password ,String code) throws Exception {
//        if (!verifyCode(request,session,code)){
//            return new Result<>().setResult(false).setMessage("验证码错误！");
//        }
        // 将用户名和密码打包成token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        // 获取shiro验证器
        Subject subject = SecurityUtils.getSubject();
        try {
            // 将token交给shiro验证
            subject.login(usernamePasswordToken);//完成登录
            String account = (String) subject.getPrincipal();
            logger.debug(String.format("用户[%s]通过验证,准备跳转主页", account));
            session.setAttribute("account", account);
            return new Result();
        } catch (Exception e) {
            return new Result<>().setResult(false).setMessage(e.getMessage());
        }
    }

    private Boolean verifyCode(HttpServletRequest request, HttpSession session, String code) {
        String value = code;
        String sessionid = session.getId();
        List<String> sValue = (List<String>) request.getSession().getAttribute(sessionid + "randomCode3");
        System.out.println("*****" + value);
        System.out.println("*&&&**" + sValue.toString());


        boolean flag = false;
        //为null 或者"" 或者 " "
//        if (StringUtils.isBlank(value) || sValue == null || sValue.size() < 1) {
//            mapping.put("result", flag);
//            return mapping;
//        }
        if (value == null || value == "" || value == " " || sValue == null || sValue.size() < 1) {
            return flag;
        }
        String[] valueStr = value.split(",");
        if (valueStr.length != sValue.size() || valueStr.length != 4) {
            return flag;
        }

        /*判断坐标参数是否正确*/
        String str = "";
        for (int i = 0; i < valueStr.length; i++) {
            str = valueStr[i].toString();
//            if(StringUtils.isBlank(str) || StringUtils.isBlank(sValue.get(i).toString())){
//                mapping.put("result", flag);
//                return mapping;
//            }
            if (str == null || str == "" || str == " " || sValue.get(i).toString() == null && sValue.get(i).toString() == "") {
                return flag;
            }
            String[] vL = valueStr[i].toString().split("_");
            String[] svL = sValue.get(i).toString().split("_");
            if (vL.length != svL.length || svL.length != 2) {
                return flag;
            }
            //x轴  y轴判断    坐标点在左上角 ，图片宽度32px  点击范围扩大17px，  范围在      x-17 < x <x+30+17  ;
            if (!(Integer.parseInt(svL[0]) - 17 < Integer.parseInt(vL[0])
                    && Integer.parseInt(vL[0]) < Integer.parseInt(svL[0]) + 47)
                    || !(Integer.parseInt(svL[1]) - 17 < Integer.parseInt(vL[1])
                    && Integer.parseInt(vL[1]) < Integer.parseInt(svL[1]) + 47)) {
                return flag;
            }
            ;

        }
        flag = true;
        return flag;
    }

}
