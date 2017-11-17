package com.czc.bi.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[etl_date]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class EtlDate implements Serializable {

    // 主键id
    private Integer id;

    // 账号
    private String account;

    // 日期
    private String pdate;

    // 记录创建时间
    private Date createdt;

    // 记录最后更新时间
    private Date updatedt;

    // get set 方法
    public Integer getId(){
        return this.id;
    }

    public EtlDate setId(Integer id){
        this.id = id;
        return this;
    }

    public String getAccount(){
        return this.account;
    }

    public EtlDate setAccount(String account){
        this.account = account;
        return this;
    }

    public String getPdate(){
        return this.pdate;
    }

    public EtlDate setPdate(String pdate){
        this.pdate = pdate;
        return this;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public EtlDate setCreatedt(Date createdt) {
        this.createdt = createdt;
        return this;
    }

    public Date getUpdatedt() {
        return updatedt;
    }

    public EtlDate setUpdatedt(Date updatedt) {
        this.updatedt = updatedt;
        return this;
    }

}
