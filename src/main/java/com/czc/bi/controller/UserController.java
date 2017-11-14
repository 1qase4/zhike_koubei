package com.czc.bi.controller;

import com.czc.bi.pojo.Return;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public Return login(HttpServletRequest request, HttpSession session, String username, String password) throws Exception {
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
            return new Return();
        } catch (Exception e) {
            return new Return(e.getMessage());
        }
    }
}
