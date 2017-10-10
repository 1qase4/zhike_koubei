package com.czc.bi.pojo.query;

import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[label_order]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class LabelOrderQuery extends BaseQuery {

    // 提交人账号
    private String account;

    // 提交人账号 操符号
    private String accountOperator;

    // 订单类型
    private String type;

    // 订单类型 操符号
    private String typeOperator;

    // 批次号,对应label表ID
    private String bid;

    // 批次号,对应label表ID 操符号
    private String bidOperator;

    // 批次时间
    private String btime;

    // 批次时间 操符号
    private String btimeOperator;

    // 状态
    private String stat;

    // 状态 操符号
    private String statOperator;

    // 备注
    private String remark;

    // 备注 操符号
    private String remarkOperator;

    // 工作人员
    private String staff;

    // 工作人员 操符号
    private String staffOperator;


    // get set 方法
    public String getAccount() {
        return this.account;
    }

    public LabelOrderQuery setAccount(String account) {
        this.account = account;
        this.accountOperator = "=";
        return this;
    }

    public LabelOrderQuery setAccount(String account, String operator) {
        this.account = account;
        this.accountOperator = operator;
        return this;
    }

    public String getAccountOperator() {
        return this.accountOperator;
    }

    public String getType() {
        return this.type;
    }

    public LabelOrderQuery setType(String type) {
        this.type = type;
        this.typeOperator = "=";
        return this;
    }

    public LabelOrderQuery setType(String type, String operator) {
        this.type = type;
        this.typeOperator = operator;
        return this;
    }

    public String getTypeOperator() {
        return this.typeOperator;
    }

    public String getBid() {
        return this.bid;
    }

    public LabelOrderQuery setBid(String bid) {
        this.bid = bid;
        this.bidOperator = "=";
        return this;
    }

    public LabelOrderQuery setBid(String bid, String operator) {
        this.bid = bid;
        this.bidOperator = operator;
        return this;
    }

    public String getBidOperator() {
        return this.bidOperator;
    }

    public String getBtime() {
        return this.btime;
    }

    public LabelOrderQuery setBtime(String btime) {
        this.btime = btime;
        this.btimeOperator = "=";
        return this;
    }

    public LabelOrderQuery setBtime(String btime, String operator) {
        this.btime = btime;
        this.btimeOperator = operator;
        return this;
    }

    public String getBtimeOperator() {
        return this.btimeOperator;
    }

    public String getStat() {
        return this.stat;
    }

    public LabelOrderQuery setStat(String stat) {
        this.stat = stat;
        this.statOperator = "=";
        return this;
    }

    public LabelOrderQuery setStat(String stat, String operator) {
        this.stat = stat;
        this.statOperator = operator;
        return this;
    }

    public String getStatOperator() {
        return this.statOperator;
    }

    public String getRemark() {
        return this.remark;
    }

    public LabelOrderQuery setRemark(String remark) {
        this.remark = remark;
        this.remarkOperator = "=";
        return this;
    }

    public LabelOrderQuery setRemark(String remark, String operator) {
        this.remark = remark;
        this.remarkOperator = operator;
        return this;
    }

    public String getRemarkOperator() {
        return this.remarkOperator;
    }

    public String getStaff() {
        return this.staff;
    }

    public LabelOrderQuery setStaff(String staff) {
        this.staff = staff;
        this.staffOperator = "=";
        return this;
    }

    public LabelOrderQuery setStaff(String staff, String operator) {
        this.staff = staff;
        this.staffOperator = operator;
        return this;
    }

    public String getStaffOperator() {
        return this.staffOperator;
    }

}
