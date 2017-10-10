package com.czc.bi.pojo.query;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[shop_custflow_analyze]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class ShopPassengerflowAnalyzeQuery extends BaseQuery {

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

    // 序号
    private Integer rank;

    // 序号 操符号
    private String rankOperator;

    // 栏目
    private String label;

    // 栏目 操符号
    private String labelOperator;

    // 值
    private Integer value;

    // 值 操符号
    private String valueOperator;

    // 计算时间
    private String pdate;

    // 计算时间 操符号
    private String pdateOperator;


    // get set 方法
    public String getType() {
        return this.type;
    }

    public ShopPassengerflowAnalyzeQuery setType(String type) {
        this.type = type;
        this.typeOperator = "=";
        return this;
    }

    public ShopPassengerflowAnalyzeQuery setType(String type, String operator) {
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

    public ShopPassengerflowAnalyzeQuery setAccount(String account) {
        this.account = account;
        this.accountOperator = "=";
        return this;
    }

    public ShopPassengerflowAnalyzeQuery setAccount(String account, String operator) {
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

    public ShopPassengerflowAnalyzeQuery setShop(String shop) {
        this.shop = shop;
        this.shopOperator = "=";
        return this;
    }

    public ShopPassengerflowAnalyzeQuery setShop(String shop, String operator) {
        this.shop = shop;
        this.shopOperator = operator;
        return this;
    }

    public String getShopOperator() {
        return this.shopOperator;
    }

    public Integer getRank() {
        return this.rank;
    }

    public ShopPassengerflowAnalyzeQuery setRank(Integer rank) {
        this.rank = rank;
        this.rankOperator = "=";
        return this;
    }

    public ShopPassengerflowAnalyzeQuery setRank(Integer rank, String operator) {
        this.rank = rank;
        this.rankOperator = operator;
        return this;
    }

    public String getRankOperator() {
        return this.rankOperator;
    }

    public String getLabel() {
        return this.label;
    }

    public ShopPassengerflowAnalyzeQuery setLabel(String label) {
        this.label = label;
        this.labelOperator = "=";
        return this;
    }

    public ShopPassengerflowAnalyzeQuery setLabel(String label, String operator) {
        this.label = label;
        this.labelOperator = operator;
        return this;
    }

    public String getLabelOperator() {
        return this.labelOperator;
    }

    public Integer getValue() {
        return this.value;
    }

    public ShopPassengerflowAnalyzeQuery setValue(Integer value) {
        this.value = value;
        this.valueOperator = "=";
        return this;
    }

    public ShopPassengerflowAnalyzeQuery setValue(Integer value, String operator) {
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

    public ShopPassengerflowAnalyzeQuery setPdate(String pdate) {
        this.pdate = pdate;
        this.pdateOperator = "=";
        return this;
    }

    public ShopPassengerflowAnalyzeQuery setPdate(String pdate, String operator) {
        this.pdate = pdate;
        this.pdateOperator = operator;
        return this;
    }

    public String getPdateOperator() {
        return this.pdateOperator;
    }

}
