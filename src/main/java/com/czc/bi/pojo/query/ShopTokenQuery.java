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

    // 商户验证码
    private String app_auth_code;

    // 商户验证码 操符号
    private String app_auth_codeOperator;

    // 店铺Token
    private String app_auth_token;

    // 店铺Token 操符号
    private String app_auth_tokenOperator;

    // 授权商户的ID
    private String user_id;

    // 授权商户的ID 操符号
    private String user_idOperator;

    // 授权商户的AppId
    private String auth_app_id;

    // 授权商户的AppId 操符号
    private String auth_app_idOperator;

    // 令牌有效期
    private Integer expires_in;

    // 令牌有效期 操符号
    private String expires_inOperator;

    // 刷新令牌有效期
    private Integer re_expires_in;

    // 刷新令牌有效期 操符号
    private String re_expires_inOperator;

    // 刷新令牌时使用
    private String app_refresh_token;

    // 刷新令牌时使用 操符号
    private String app_refresh_tokenOperator;

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

    public String getApp_auth_code() {
        return this.app_auth_code;
    }

    public ShopTokenQuery setApp_auth_code(String app_auth_code) {
        this.app_auth_code = app_auth_code;
        this.app_auth_codeOperator = "=";
        return this;
    }

    public ShopTokenQuery setApp_auth_code(String app_auth_code, String operator) {
        this.app_auth_code = app_auth_code;
        this.app_auth_codeOperator = operator;
        return this;
    }

    public String getApp_auth_codeOperator() {
        return this.app_auth_codeOperator;
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

    public String getUser_id() {
        return this.user_id;
    }

    public ShopTokenQuery setUser_id(String user_id) {
        this.user_id = user_id;
        this.user_idOperator = "=";
        return this;
    }

    public ShopTokenQuery setUser_id(String user_id, String operator) {
        this.user_id = user_id;
        this.user_idOperator = operator;
        return this;
    }

    public String getUser_idOperator() {
        return this.user_idOperator;
    }

    public String getAuth_app_id() {
        return this.auth_app_id;
    }

    public ShopTokenQuery setAuth_app_id(String auth_app_id) {
        this.auth_app_id = auth_app_id;
        this.auth_app_idOperator = "=";
        return this;
    }

    public ShopTokenQuery setAuth_app_id(String auth_app_id, String operator) {
        this.auth_app_id = auth_app_id;
        this.auth_app_idOperator = operator;
        return this;
    }

    public String getAuth_app_idOperator() {
        return this.auth_app_idOperator;
    }

    public Integer getExpires_in() {
        return this.expires_in;
    }

    public ShopTokenQuery setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
        this.expires_inOperator = "=";
        return this;
    }

    public ShopTokenQuery setExpires_in(Integer expires_in, String operator) {
        this.expires_in = expires_in;
        this.expires_inOperator = operator;
        return this;
    }

    public String getExpires_inOperator() {
        return this.expires_inOperator;
    }

    public Integer getRe_expires_in() {
        return this.re_expires_in;
    }

    public ShopTokenQuery setRe_expires_in(Integer re_expires_in) {
        this.re_expires_in = re_expires_in;
        this.re_expires_inOperator = "=";
        return this;
    }

    public ShopTokenQuery setRe_expires_in(Integer re_expires_in, String operator) {
        this.re_expires_in = re_expires_in;
        this.re_expires_inOperator = operator;
        return this;
    }

    public String getRe_expires_inOperator() {
        return this.re_expires_inOperator;
    }

    public String getApp_refresh_token() {
        return this.app_refresh_token;
    }

    public ShopTokenQuery setApp_refresh_token(String app_refresh_token) {
        this.app_refresh_token = app_refresh_token;
        this.app_refresh_tokenOperator = "=";
        return this;
    }

    public ShopTokenQuery setApp_refresh_token(String app_refresh_token, String operator) {
        this.app_refresh_token = app_refresh_token;
        this.app_refresh_tokenOperator = operator;
        return this;
    }

    public String getApp_refresh_tokenOperator() {
        return this.app_refresh_tokenOperator;
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