package com.czc.bi.pojo.query;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[shop]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class ShopQuery extends BaseQuery {

    // 名称
    private String name;

    // 名称 操符号
    private String nameOperator;

    // 账号
    private String account;

    // 账号 操符号
    private String accountOperator;

    // 简称
    private String inshort;

    // 简称 操符号
    private String inshortOperator;

    // 归属商户
    private String merchant;

    // 归属商户 操符号
    private String merchantOperator;

    // 归属代理商
    private String agent;

    // 归属代理商 操符号
    private String agentOperator;

    // 省
    private String province;

    // 省 操符号
    private String provinceOperator;

    // 城市
    private String city;

    // 城市 操符号
    private String cityOperator;

    // 区县
    private String area;

    // 区县 操符号
    private String areaOperator;

    // 地址
    private String address;

    // 地址 操符号
    private String addressOperator;

    // 类型
    private String type;

    // 类型 操符号
    private String typeOperator;

    // 小类
    private String subtype;

    // 小类 操符号
    private String subtypeOperator;

    // 经度
    private String longitude;

    // 经度 操符号
    private String longitudeOperator;

    // 纬度
    private String latitude;

    // 纬度 操符号
    private String latitudeOperator;

    // 联系人
    private String link;

    // 联系人 操符号
    private String linkOperator;

    // 电话
    private String phone;

    // 电话 操符号
    private String phoneOperator;

    // 营业时间
    private String shoptime;

    // 营业时间 操符号
    private String shoptimeOperator;

    // 简介
    private String introduce;

    // 简介 操符号
    private String introduceOperator;


    // get set 方法
    public String getName() {
        return this.name;
    }

    public ShopQuery setName(String name) {
        this.name = name;
        this.nameOperator = "=";
        return this;
    }

    public ShopQuery setName(String name, String operator) {
        this.name = name;
        this.nameOperator = operator;
        return this;
    }

    public String getNameOperator() {
        return this.nameOperator;
    }

    public String getAccount() {
        return this.account;
    }

    public ShopQuery setAccount(String account) {
        this.account = account;
        this.accountOperator = "=";
        return this;
    }

    public ShopQuery setAccount(String account, String operator) {
        this.account = account;
        this.accountOperator = operator;
        return this;
    }

    public String getAccountOperator() {
        return this.accountOperator;
    }

    public String getInshort() {
        return this.inshort;
    }

    public ShopQuery setInshort(String inshort) {
        this.inshort = inshort;
        this.inshortOperator = "=";
        return this;
    }

    public ShopQuery setInshort(String inshort, String operator) {
        this.inshort = inshort;
        this.inshortOperator = operator;
        return this;
    }

    public String getInshortOperator() {
        return this.inshortOperator;
    }

    public String getMerchant() {
        return this.merchant;
    }

    public ShopQuery setMerchant(String merchant) {
        this.merchant = merchant;
        this.merchantOperator = "=";
        return this;
    }

    public ShopQuery setMerchant(String merchant, String operator) {
        this.merchant = merchant;
        this.merchantOperator = operator;
        return this;
    }

    public String getMerchantOperator() {
        return this.merchantOperator;
    }

    public String getAgent() {
        return this.agent;
    }

    public ShopQuery setAgent(String agent) {
        this.agent = agent;
        this.agentOperator = "=";
        return this;
    }

    public ShopQuery setAgent(String agent, String operator) {
        this.agent = agent;
        this.agentOperator = operator;
        return this;
    }

    public String getAgentOperator() {
        return this.agentOperator;
    }

    public String getProvince() {
        return this.province;
    }

    public ShopQuery setProvince(String province) {
        this.province = province;
        this.provinceOperator = "=";
        return this;
    }

    public ShopQuery setProvince(String province, String operator) {
        this.province = province;
        this.provinceOperator = operator;
        return this;
    }

    public String getProvinceOperator() {
        return this.provinceOperator;
    }

    public String getCity() {
        return this.city;
    }

    public ShopQuery setCity(String city) {
        this.city = city;
        this.cityOperator = "=";
        return this;
    }

    public ShopQuery setCity(String city, String operator) {
        this.city = city;
        this.cityOperator = operator;
        return this;
    }

    public String getCityOperator() {
        return this.cityOperator;
    }

    public String getArea() {
        return this.area;
    }

    public ShopQuery setArea(String area) {
        this.area = area;
        this.areaOperator = "=";
        return this;
    }

    public ShopQuery setArea(String area, String operator) {
        this.area = area;
        this.areaOperator = operator;
        return this;
    }

    public String getAreaOperator() {
        return this.areaOperator;
    }

    public String getAddress() {
        return this.address;
    }

    public ShopQuery setAddress(String address) {
        this.address = address;
        this.addressOperator = "=";
        return this;
    }

    public ShopQuery setAddress(String address, String operator) {
        this.address = address;
        this.addressOperator = operator;
        return this;
    }

    public String getAddressOperator() {
        return this.addressOperator;
    }

    public String getType() {
        return this.type;
    }

    public ShopQuery setType(String type) {
        this.type = type;
        this.typeOperator = "=";
        return this;
    }

    public ShopQuery setType(String type, String operator) {
        this.type = type;
        this.typeOperator = operator;
        return this;
    }

    public String getTypeOperator() {
        return this.typeOperator;
    }

    public String getSubtype() {
        return this.subtype;
    }

    public ShopQuery setSubtype(String subtype) {
        this.subtype = subtype;
        this.subtypeOperator = "=";
        return this;
    }

    public ShopQuery setSubtype(String subtype, String operator) {
        this.subtype = subtype;
        this.subtypeOperator = operator;
        return this;
    }

    public String getSubtypeOperator() {
        return this.subtypeOperator;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public ShopQuery setLongitude(String longitude) {
        this.longitude = longitude;
        this.longitudeOperator = "=";
        return this;
    }

    public ShopQuery setLongitude(String longitude, String operator) {
        this.longitude = longitude;
        this.longitudeOperator = operator;
        return this;
    }

    public String getLongitudeOperator() {
        return this.longitudeOperator;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public ShopQuery setLatitude(String latitude) {
        this.latitude = latitude;
        this.latitudeOperator = "=";
        return this;
    }

    public ShopQuery setLatitude(String latitude, String operator) {
        this.latitude = latitude;
        this.latitudeOperator = operator;
        return this;
    }

    public String getLatitudeOperator() {
        return this.latitudeOperator;
    }

    public String getLink() {
        return this.link;
    }

    public ShopQuery setLink(String link) {
        this.link = link;
        this.linkOperator = "=";
        return this;
    }

    public ShopQuery setLink(String link, String operator) {
        this.link = link;
        this.linkOperator = operator;
        return this;
    }

    public String getLinkOperator() {
        return this.linkOperator;
    }

    public String getPhone() {
        return this.phone;
    }

    public ShopQuery setPhone(String phone) {
        this.phone = phone;
        this.phoneOperator = "=";
        return this;
    }

    public ShopQuery setPhone(String phone, String operator) {
        this.phone = phone;
        this.phoneOperator = operator;
        return this;
    }

    public String getPhoneOperator() {
        return this.phoneOperator;
    }

    public String getShoptime() {
        return this.shoptime;
    }

    public ShopQuery setShoptime(String shoptime) {
        this.shoptime = shoptime;
        this.shoptimeOperator = "=";
        return this;
    }

    public ShopQuery setShoptime(String shoptime, String operator) {
        this.shoptime = shoptime;
        this.shoptimeOperator = operator;
        return this;
    }

    public String getShoptimeOperator() {
        return this.shoptimeOperator;
    }

    public String getIntroduce() {
        return this.introduce;
    }

    public ShopQuery setIntroduce(String introduce) {
        this.introduce = introduce;
        this.introduceOperator = "=";
        return this;
    }

    public ShopQuery setIntroduce(String introduce, String operator) {
        this.introduce = introduce;
        this.introduceOperator = operator;
        return this;
    }

    public String getIntroduceOperator() {
        return this.introduceOperator;
    }

}
