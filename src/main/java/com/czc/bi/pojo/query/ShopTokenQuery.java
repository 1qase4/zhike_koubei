package com.czc.bi.pojo.query;

import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[shop_token]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class ShopTokenQuery extends BaseQuery {

    // 账号
    private String account;

    // 账号 操符号
    private String accountOperator;

    // 店铺名称
    private String name;

    // 店铺名称 操符号
    private String nameOperator;

    // 店铺Token
    private String app_auth_token;

    // 店铺Token 操符号
    private String app_auth_tokenOperator;

    // 状态
    private String stat;

    // 状态 操符号
    private String statOperator;


    // get set 方法
    public String getAccount() {
        return this.account;
    }

    public ShopTokenQuery setAccount(String account) {
        this.account = account;
        this.accountOperator = "=";
        return this;
    }

    public ShopTokenQuery setAccount(String account, String operator) {
        this.account = account;
        this.accountOperator = operator;
        return this;
    }

    public String getAccountOperator() {
        return this.accountOperator;
    }

    public String getName() {
        return this.name;
    }

    public ShopTokenQuery setName(String name) {
        this.name = name;
        this.nameOperator = "=";
        return this;
    }

    public ShopTokenQuery setName(String name, String operator) {
        this.name = name;
        this.nameOperator = operator;
        return this;
    }

    public String getNameOperator() {
        return this.nameOperator;
    }

    public String getApp_auth_token() {
        return this.app_auth_token;
    }

    public ShopTokenQuery setApp_auth_token(String app_auth_token) {
        this.app_auth_token = app_auth_token;
        this.app_auth_tokenOperator = "=";
        return this;
    }

    public ShopTokenQuery setApp_auth_token(String app_auth_token, String operator) {
        this.app_auth_token = app_auth_token;
        this.app_auth_tokenOperator = operator;
        return this;
    }

    public String getApp_auth_tokenOperator() {
        return this.app_auth_tokenOperator;
    }

    public String getStat() {
        return this.stat;
    }

    public ShopTokenQuery setStat(String stat) {
        this.stat = stat;
        this.statOperator = "=";
        return this;
    }

    public ShopTokenQuery setStat(String stat, String operator) {
        this.stat = stat;
        this.statOperator = operator;
        return this;
    }

    public String getStatOperator() {
        return this.statOperator;
    }

}
