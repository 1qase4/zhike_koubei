package com.czc.bi.pojo.query;


/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[job_list]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class JobListQuery extends BaseQuery {

    // java bean
    private String bean;

    // java bean 操符号
    private String beanOperator;

    // job名称
    private String name;

    // job名称 操符号
    private String nameOperator;

    // shopid
    private String shopid;

    // shopid 操符号
    private String shopidOperator;

    // token
    private String token;

    // token 操符号
    private String tokenOperator;

    // pdate
    private String pdate;

    // pdate 操符号
    private String pdateOperator;

    // 受影响的记录数
    private Integer rows;

    // 受影响的记录数 操符号
    private String rowsOperator;

    // 状态
    private String stauts;

    // 状态 操符号
    private String stautsOperator;


    // get set 方法
    public String getBean() {
        return this.bean;
    }

    public JobListQuery setBean(String bean) {
        this.bean = bean;
        this.beanOperator = "=";
        return this;
    }

    public JobListQuery setBean(String bean, String operator) {
        this.bean = bean;
        this.beanOperator = operator;
        return this;
    }

    public String getBeanOperator() {
        return this.beanOperator;
    }

    public String getName() {
        return this.name;
    }

    public JobListQuery setName(String name) {
        this.name = name;
        this.nameOperator = "=";
        return this;
    }

    public JobListQuery setName(String name, String operator) {
        this.name = name;
        this.nameOperator = operator;
        return this;
    }

    public String getNameOperator() {
        return this.nameOperator;
    }

    public String getShopid() {
        return this.shopid;
    }

    public JobListQuery setShopid(String shopid) {
        this.shopid = shopid;
        this.shopidOperator = "=";
        return this;
    }

    public JobListQuery setShopid(String shopid, String operator) {
        this.shopid = shopid;
        this.shopidOperator = operator;
        return this;
    }

    public String getShopidOperator() {
        return this.shopidOperator;
    }

    public String getToken() {
        return this.token;
    }

    public JobListQuery setToken(String token) {
        this.token = token;
        this.tokenOperator = "=";
        return this;
    }

    public JobListQuery setToken(String token, String operator) {
        this.token = token;
        this.tokenOperator = operator;
        return this;
    }

    public String getTokenOperator() {
        return this.tokenOperator;
    }

    public String getPdate() {
        return this.pdate;
    }

    public JobListQuery setPdate(String pdate) {
        this.pdate = pdate;
        this.pdateOperator = "=";
        return this;
    }

    public JobListQuery setPdate(String pdate, String operator) {
        this.pdate = pdate;
        this.pdateOperator = operator;
        return this;
    }

    public String getPdateOperator() {
        return this.pdateOperator;
    }

    public Integer getRows() {
        return this.rows;
    }

    public JobListQuery setRows(Integer rows) {
        this.rows = rows;
        this.rowsOperator = "=";
        return this;
    }

    public JobListQuery setRows(Integer rows, String operator) {
        this.rows = rows;
        this.rowsOperator = operator;
        return this;
    }

    public String getRowsOperator() {
        return this.rowsOperator;
    }

    public String getStauts() {
        return this.stauts;
    }

    public JobListQuery setStauts(String stauts) {
        this.stauts = stauts;
        this.stautsOperator = "=";
        return this;
    }

    public JobListQuery setStauts(String stauts, String operator) {
        this.stauts = stauts;
        this.stautsOperator = operator;
        return this;
    }

    public String getStautsOperator() {
        return this.stautsOperator;
    }

}
