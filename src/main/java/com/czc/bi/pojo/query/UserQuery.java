package com.czc.bi.pojo.query;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/11/9.
 * @version: V1.0
 */
public class UserQuery extends BaseQuery  {

    // 账号
    private String account;

    // 账号 操符号
    private String accountOperator;

    // 商圈秀账号
    private String sqsAccount;

    // 账号 操符号
    private String sqsAccountOperator;

    // 密码
    private String password;

    // 密码 操符号
    private String passwordOperator;

    // 显示名称
    private String name;

    // 显示名称 操符号
    private String nameOperator;

    // 简称
    private String inshort;

    // 简称 操符号
    private String inshortOperator;

    // 地址
    private String address;

    // 地址 操符号
    private String addressOperator;

    // 电话
    private String phone;

    // 电话 操符号
    private String phoneOperator;

    // E_mail
    private String email;

    // E_mail 操符号
    private String emailOperator;

    // 联系人
    private String link;

    // 联系人 操符号
    private String linkOperator;

    // 角色
    private String role;

    // 角色 操符号
    private String roleOperator;

    // 代理商
    private String agent;

    // 代理商 操符号
    private String agentOperator;

    // 状态
    private String stat;

    // 状态 操符号
    private String statOperator;


    // get set 方法
    public String getAccount() {
        return this.account;
    }

    public UserQuery setAccount(String account) {
        this.account = account;
        this.accountOperator = "=";
        return this;
    }

    public UserQuery setAccount(String account, String operator) {
        this.account = account;
        this.accountOperator = operator;
        return this;
    }

    public String getSqsAccount() {
        return sqsAccount;
    }

    public String getSqsAccountOperator() {
        return sqsAccountOperator;
    }

    public UserQuery setSqsAccount(String sqsAccount) {
        this.sqsAccount = sqsAccount;
        this.sqsAccountOperator = "=";
        return this;
    }

    public UserQuery setSqsAccount(String sqsAccount, String operator) {
        this.sqsAccount = sqsAccount;
        this.sqsAccountOperator = operator;
        return this;
    }


    public String getAccountOperator() {
        return this.accountOperator;
    }

    public String getPassword() {
        return this.password;
    }

    public UserQuery setPassword(String password) {
        this.password = password;
        this.passwordOperator = "=";
        return this;
    }

    public UserQuery setPassword(String password, String operator) {
        this.password = password;
        this.passwordOperator = operator;
        return this;
    }

    public String getPasswordOperator() {
        return this.passwordOperator;
    }

    public String getName() {
        return this.name;
    }

    public UserQuery setName(String name) {
        this.name = name;
        this.nameOperator = "=";
        return this;
    }

    public UserQuery setName(String name, String operator) {
        this.name = name;
        this.nameOperator = operator;
        return this;
    }

    public String getNameOperator() {
        return this.nameOperator;
    }

    public String getInshort() {
        return this.inshort;
    }

    public UserQuery setInshort(String inshort) {
        this.inshort = inshort;
        this.inshortOperator = "=";
        return this;
    }

    public UserQuery setInshort(String inshort, String operator) {
        this.inshort = inshort;
        this.inshortOperator = operator;
        return this;
    }

    public String getInshortOperator() {
        return this.inshortOperator;
    }

    public String getAddress() {
        return this.address;
    }

    public UserQuery setAddress(String address) {
        this.address = address;
        this.addressOperator = "=";
        return this;
    }

    public UserQuery setAddress(String address, String operator) {
        this.address = address;
        this.addressOperator = operator;
        return this;
    }

    public String getAddressOperator() {
        return this.addressOperator;
    }

    public String getPhone() {
        return this.phone;
    }

    public UserQuery setPhone(String phone) {
        this.phone = phone;
        this.phoneOperator = "=";
        return this;
    }

    public UserQuery setPhone(String phone, String operator) {
        this.phone = phone;
        this.phoneOperator = operator;
        return this;
    }

    public String getPhoneOperator() {
        return this.phoneOperator;
    }

    public String getEmail() {
        return this.email;
    }

    public UserQuery setEmail(String email) {
        this.email = email;
        this.emailOperator = "=";
        return this;
    }

    public UserQuery setEmail(String email, String operator) {
        this.email = email;
        this.emailOperator = operator;
        return this;
    }

    public String getEmailOperator() {
        return this.emailOperator;
    }

    public String getLink() {
        return this.link;
    }

    public UserQuery setLink(String link) {
        this.link = link;
        this.linkOperator = "=";
        return this;
    }

    public UserQuery setLink(String link, String operator) {
        this.link = link;
        this.linkOperator = operator;
        return this;
    }

    public String getLinkOperator() {
        return this.linkOperator;
    }

    public String getRole() {
        return this.role;
    }

    public UserQuery setRole(String role) {
        this.role = role;
        this.roleOperator = "=";
        return this;
    }

    public UserQuery setRole(String role, String operator) {
        this.role = role;
        this.roleOperator = operator;
        return this;
    }

    public String getRoleOperator() {
        return this.roleOperator;
    }

    public String getAgent() {
        return this.agent;
    }

    public UserQuery setAgent(String agent) {
        this.agent = agent;
        this.agentOperator = "=";
        return this;
    }

    public UserQuery setAgent(String agent, String operator) {
        this.agent = agent;
        this.agentOperator = operator;
        return this;
    }

    public String getAgentOperator() {
        return this.agentOperator;
    }

    public String getStat() {
        return this.stat;
    }

    public UserQuery setStat(String stat) {
        this.stat = stat;
        this.statOperator = "=";
        return this;
    }

    public UserQuery setStat(String stat, String operator) {
        this.stat = stat;
        this.statOperator = operator;
        return this;
    }

    public String getStatOperator() {
        return this.statOperator;
    }

}
