package com.czc.bi.pojo.query;

import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[potential_cust]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class PotentialCustQuery extends BaseQuery {

    // alipay_userid
    private String userid;

    // alipay_userid 操符号
    private String useridOperator;

    // 联系人
    private String link;

    // 联系人 操符号
    private String linkOperator;

    // 电话
    private String tel;

    // 电话 操符号
    private String telOperator;

    // 电子邮件
    private String email;

    // 电子邮件 操符号
    private String emailOperator;

    // 状态
    private String status;

    // 状态 操符号
    private String statusOperator;


    // get set 方法
    public String getUserid() {
        return this.userid;
    }

    public PotentialCustQuery setUserid(String userid) {
        this.userid = userid;
        this.useridOperator = "=";
        return this;
    }

    public PotentialCustQuery setUserid(String userid, String operator) {
        this.userid = userid;
        this.useridOperator = operator;
        return this;
    }

    public String getUseridOperator() {
        return this.useridOperator;
    }

    public String getLink() {
        return this.link;
    }

    public PotentialCustQuery setLink(String link) {
        this.link = link;
        this.linkOperator = "=";
        return this;
    }

    public PotentialCustQuery setLink(String link, String operator) {
        this.link = link;
        this.linkOperator = operator;
        return this;
    }

    public String getLinkOperator() {
        return this.linkOperator;
    }

    public String getTel() {
        return this.tel;
    }

    public PotentialCustQuery setTel(String tel) {
        this.tel = tel;
        this.telOperator = "=";
        return this;
    }

    public PotentialCustQuery setTel(String tel, String operator) {
        this.tel = tel;
        this.telOperator = operator;
        return this;
    }

    public String getTelOperator() {
        return this.telOperator;
    }

    public String getEmail() {
        return this.email;
    }

    public PotentialCustQuery setEmail(String email) {
        this.email = email;
        this.emailOperator = "=";
        return this;
    }

    public PotentialCustQuery setEmail(String email, String operator) {
        this.email = email;
        this.emailOperator = operator;
        return this;
    }

    public String getEmailOperator() {
        return this.emailOperator;
    }

    public String getStatus() {
        return this.status;
    }

    public PotentialCustQuery setStatus(String status) {
        this.status = status;
        this.statusOperator = "=";
        return this;
    }

    public PotentialCustQuery setStatus(String status, String operator) {
        this.status = status;
        this.statusOperator = operator;
        return this;
    }

    public String getStatusOperator() {
        return this.statusOperator;
    }

}
