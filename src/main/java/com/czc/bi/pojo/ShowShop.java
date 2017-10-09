package com.czc.bi.pojo;

import com.czc.bi.util.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc : 店铺前台列表展示
 * @date : 2017/9/27.
 * @version: V1.0
 */
public class ShowShop {
    private int id;
    private String account;
    private String name;
    private String agent;
    private String merchant;
    private String area;
    private String type;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public void setCreatedt(Date createdt) {
        this.createdt = createdt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShowShop{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", agent='").append(agent).append('\'');
        sb.append(", merchant='").append(merchant).append('\'');
        sb.append(", area='").append(area).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", createdt=").append(createdt);
        sb.append('}');
        return sb.toString();
    }
}
