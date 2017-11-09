package com.czc.bi.controller;

import com.czc.bi.pojo.Simple;
import com.czc.bi.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/11/9.
 * @version: V1.0
 */
@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShopService shopService;

    @RequestMapping("getShops")
    public List<Simple<String, String>> getShops(String account){
        List<Simple<String,String>> shops = shopService.selectShopsByMerchant(account);
        return shops;
    }



}
