package com.czc.bi.pojo.query;

import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[shop_label_analyze]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class ShopLabelAnalyzeQuery extends BaseQuery {

    // 类型
    private String type;

    // 类型 操符号
    private String typeOperator;

    // 账号
    private String account;

    // 账号 操符号
    private String accountOperator;

    // 店铺名称
    private String shop;

    // 店铺名称 操符号
    private String shopOperator;

    // Key
    private String key;

    // Key 操符号
    private String keyOperator;

    // 值
    private String value;

    // 值 操符号
    private String valueOperator;

    // 数据时间
    private String pdate;

    // 数据时间 操符号
    private String pdateOperator;


    // get set 方法
    public String getType() {
        return this.type;
    }

    public ShopLabelAnalyzeQuery setType(String type) {
        this.type = type;
        this.typeOperator = "=";
        return this;
    }

    public ShopLabelAnalyzeQuery setType(String type, String operator) {
        this.type = type;
        this.typeOperator = operator;
        return this;
    }

    public String getTypeOperator() {
        return this.typeOperator;
    }

    public String getAccount() {
        return this.account;
    }

    public ShopLabelAnalyzeQuery setAccount(String account) {
        this.account = account;
        this.accountOperator = "=";
        return this;
    }

    public ShopLabelAnalyzeQuery setAccount(String account, String operator) {
        this.account = account;
        this.accountOperator = operator;
        return this;
    }

    public String getAccountOperator() {
        return this.accountOperator;
    }

    public String getShop() {
        return this.shop;
    }

    public ShopLabelAnalyzeQuery setShop(String shop) {
        this.shop = shop;
        this.shopOperator = "=";
        return this;
    }

    public ShopLabelAnalyzeQuery setShop(String shop, String operator) {
        this.shop = shop;
        this.shopOperator = operator;
        return this;
    }

    public String getShopOperator() {
        return this.shopOperator;
    }

    public String getKey() {
        return this.key;
    }

    public ShopLabelAnalyzeQuery setKey(String key) {
        this.key = key;
        this.keyOperator = "=";
        return this;
    }

    public ShopLabelAnalyzeQuery setKey(String key, String operator) {
        this.key = key;
        this.keyOperator = operator;
        return this;
    }

    public String getKeyOperator() {
        return this.keyOperator;
    }

    public String getValue() {
        return this.value;
    }

    public ShopLabelAnalyzeQuery setValue(String value) {
        this.value = value;
        this.valueOperator = "=";
        return this;
    }

    public ShopLabelAnalyzeQuery setValue(String value, String operator) {
        this.value = value;
        this.valueOperator = operator;
        return this;
    }

    public String getValueOperator() {
        return this.valueOperator;
    }

    public String getPdate() {
        return this.pdate;
    }

    public ShopLabelAnalyzeQuery setPdate(String pdate) {
        this.pdate = pdate;
        this.pdateOperator = "=";
        return this;
    }

    public ShopLabelAnalyzeQuery setPdate(String pdate, String operator) {
        this.pdate = pdate;
        this.pdateOperator = operator;
        return this;
    }

    public String getPdateOperator() {
        return this.pdateOperator;
    }

}
