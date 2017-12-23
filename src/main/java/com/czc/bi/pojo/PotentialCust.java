package com.czc.bi.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[potential_cust]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class PotentialCust implements Serializable {

    // 主键id
    private Integer id;

    // alipay_userid
    private String userid;

    // 联系人
    private String link;

    // 电话
    private String tel;

    // 电子邮件
    private String email;

    // 状态
    private String status;

    // 记录创建时间
    private Date createdt;

    // 记录最后更新时间
    private Date updatedt;

    // get set 方法
    public Integer getId(){
        return this.id;
    }

    public PotentialCust setId(Integer id){
        this.id = id;
        return this;
    }

    public String getUserid(){
        return this.userid;
    }

    public PotentialCust setUserid(String userid){
        this.userid = userid;
        return this;
    }

    public String getLink(){
        return this.link;
    }

    public PotentialCust setLink(String link){
        this.link = link;
        return this;
    }

    public String getTel(){
        return this.tel;
    }

    public PotentialCust setTel(String tel){
        this.tel = tel;
        return this;
    }

    public String getEmail(){
        return this.email;
    }

    public PotentialCust setEmail(String email){
        this.email = email;
        return this;
    }

    public String getStatus(){
        return this.status;
    }

    public PotentialCust setStatus(String status){
        this.status = status;
        return this;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public PotentialCust setCreatedt(Date createdt) {
        this.createdt = createdt;
        return this;
    }

    public Date getUpdatedt() {
        return updatedt;
    }

    public PotentialCust setUpdatedt(Date updatedt) {
        this.updatedt = updatedt;
        return this;
    }

}
