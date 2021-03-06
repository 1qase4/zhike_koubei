package com.czc.bi.pojo;

import com.czc.bi.util.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/7.
 */
public class User {
    private Integer id;
    private String account;
    private String sqsAccount;
    private String password;
    private String name;
    private String inshort;
    private String address;
    private String phone;
    private String email;
    private String link;
    private String stat;
    private String role;
    private String agent;
    private List<String> shop;

    public String getSqsAccount() {
        return sqsAccount;
    }

    public void setSqsAccount(String sqsAccount) {
        this.sqsAccount = sqsAccount;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createDt;
    private Date updateDt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public List<String> getShop() {
        return shop;
    }

    public void setShop(List<String> shop) {
        this.shop = shop;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", account='").append(account).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", inshort='").append(inshort).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append(", stat='").append(stat).append('\'');
        sb.append(", role='").append(role).append('\'');
        sb.append(", agent='").append(agent).append('\'');
        sb.append(", shop=").append(shop);
        sb.append(", createDt=").append(createDt);
        sb.append(", updateDt=").append(updateDt);
        sb.append('}');
        return sb.toString();
    }
}
