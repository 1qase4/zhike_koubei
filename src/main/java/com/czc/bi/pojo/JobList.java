package com.czc.bi.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/12/5.
 * @version: V1.0
 */
public class JobList implements Serializable {

    // 主键id
    private Integer id;

    // java bean
    private String bean;

    // job名称
    private String name;

    // shopid
    private String shopid;

    // token
    private String token;

    // pdate
    private String pdate;

    // 受影响的记录数
    private Integer rows;

    // 状态
    private String stauts;

    // 记录创建时间
    private Date createdt;

    // 记录最后更新时间
    private Date updatedt;

    // get set 方法
    public Integer getId() {
        return this.id;
    }

    public com.czc.bi.pojo.JobList setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getBean() {
        return this.bean;
    }

    public com.czc.bi.pojo.JobList setBean(String bean) {
        this.bean = bean;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public com.czc.bi.pojo.JobList setName(String name) {
        this.name = name;
        return this;
    }

    public String getShopid() {
        return this.shopid;
    }

    public com.czc.bi.pojo.JobList setShopid(String shopid) {
        this.shopid = shopid;
        return this;
    }

    public String getToken() {
        return this.token;
    }

    public com.czc.bi.pojo.JobList setToken(String token) {
        this.token = token;
        return this;
    }

    public String getPdate() {
        return this.pdate;
    }

    public com.czc.bi.pojo.JobList setPdate(String pdate) {
        this.pdate = pdate;
        return this;
    }

    public Integer getRows() {
        return this.rows;
    }

    public com.czc.bi.pojo.JobList setRows(Integer rows) {
        this.rows = rows;
        return this;
    }

    public String getStauts() {
        return this.stauts;
    }

    public com.czc.bi.pojo.JobList setStauts(String stauts) {
        this.stauts = stauts;
        return this;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public com.czc.bi.pojo.JobList setCreatedt(Date createdt) {
        this.createdt = createdt;
        return this;
    }

    public Date getUpdatedt() {
        return updatedt;
    }

    public com.czc.bi.pojo.JobList setUpdatedt(Date updatedt) {
        this.updatedt = updatedt;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobList{");
        sb.append("id=").append(id);
        sb.append(", bean='").append(bean).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", shopid='").append(shopid).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", pdate='").append(pdate).append('\'');
        sb.append(", rows=").append(rows);
        sb.append(", stauts='").append(stauts).append('\'');
        sb.append(", createdt=").append(createdt);
        sb.append(", updatedt=").append(updatedt);
        sb.append('}');
        return sb.toString();
    }
}
