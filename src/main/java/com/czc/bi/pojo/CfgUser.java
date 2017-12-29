package com.czc.bi.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[cfg_user]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class CfgUser implements Serializable {

    // 主键id
    private Integer id;

    // 
    private String username;

    // 
    private String password;

    // 
    private String nickname;

    // 
    private String status;

    // 记录创建时间
    private Date createdt;

    // 记录最后更新时间
    private Date updatedt;

    // get set 方法
    public Integer getId(){
        return this.id;
    }

    public CfgUser setId(Integer id){
        this.id = id;
        return this;
    }

    public String getUsername(){
        return this.username;
    }

    public CfgUser setUsername(String username){
        this.username = username;
        return this;
    }

    public String getPassword(){
        return this.password;
    }

    public CfgUser setPassword(String password){
        this.password = password;
        return this;
    }

    public String getNickname(){
        return this.nickname;
    }

    public CfgUser setNickname(String nickname){
        this.nickname = nickname;
        return this;
    }

    public String getStatus(){
        return this.status;
    }

    public CfgUser setStatus(String status){
        this.status = status;
        return this;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public CfgUser setCreatedt(Date createdt) {
        this.createdt = createdt;
        return this;
    }

    public Date getUpdatedt() {
        return updatedt;
    }

    public CfgUser setUpdatedt(Date updatedt) {
        this.updatedt = updatedt;
        return this;
    }

}
