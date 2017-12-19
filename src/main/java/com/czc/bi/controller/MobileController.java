package com.czc.bi.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/12/19.
 */

@Controller
@RequestMapping("/m")
public class MobileController {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @RequestMapping("/login")
    public String login() {
        return "mobile/login";
    }


}
