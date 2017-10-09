package com.czc.bi.pojo;

import com.czc.bi.util.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : 店铺
 * @date : 2016/10/14.
 * @version: V1.0
 */
public class Shop {
    private int id;
    private String name;
    private String account;
    private String password;
    private String inshort;
    private String merchant;
    private String agent;
    private String province;
    private String city;
    private String area;
    private String address;
    private String type;
    private String subtype;
    private String longitude;
    private String latitude;
    private String link;
    private String phone;
    private String shoptime;
    private String introduce;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdt;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updatedt;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInshort() {
        return inshort;
    }

    public void setInshort(String inshort) {
        this.inshort = inshort;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    

    public String getShoptime() {
		return shoptime;
	}

	public void setShoptime(String shoptime) {
		this.shoptime = shoptime;
	}

	public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public void setCreatedt(Date createdt) {
        this.createdt = createdt;
    }

    public Date getUpdatedt() {
        return updatedt;
    }

    public void setUpdatedt(Date updatedt) {
        this.updatedt = updatedt;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Shop{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", inshort='").append(inshort).append('\'');
        sb.append(", merchant='").append(merchant).append('\'');
        sb.append(", agent='").append(agent).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", area='").append(area).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", subtype='").append(subtype).append('\'');
        sb.append(", longitude='").append(longitude).append('\'');
        sb.append(", latitude='").append(latitude).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", shoptime='").append(shoptime).append('\'');
        sb.append(", introduce='").append(introduce).append('\'');
        sb.append(", createdt=").append(createdt);
        sb.append(", updatedt=").append(updatedt);
        sb.append('}');
        return sb.toString();
    }
}
