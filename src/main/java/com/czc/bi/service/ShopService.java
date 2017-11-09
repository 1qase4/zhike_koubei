package com.czc.bi.service;

import com.czc.bi.mapper.ShopMapper;
import com.czc.bi.pojo.Shop;
import com.czc.bi.pojo.Simple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */
@Service
public class ShopService {

    @Autowired
    private ShopMapper shopMapper;

    public int saves(List<Shop> list) {
        return shopMapper.inserts(list);
    }

    public List<Simple<String,String>> selectShopsByMerchant(String account) {
        List<Simple<String, String>> res = shopMapper.selectShopsByMerchant(account);
        return res;
    }
}
