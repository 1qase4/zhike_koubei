package com.czc.bi.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[shop_token]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class ShopToken implements Serializable {

    // 主键id
    private Integer id;

    // 账号
    private String account;

    // 店铺名称
    private String name;

    //商户验证码
    private String app_auth_code;

    //商户授权令牌
    private String app_auth_token;

    // 授权商户的ID
    private String user_id;

    // 授权商户的AppId
    private String auth_app_id;

    // 令牌有效期
    private Integer expires_in;

    // 刷新令牌有效期
    private Integer re_expires_in;

    // 刷新令牌时使用
    private String app_refresh_token;

    // 状态
    private String stat;

    // 记录创建时间
    private Date createdt;

    // 记录最后更新时间
    private Date updatedt;

    // get set 方法
    public Integer getId(){
        return this.id;
    }

    public ShopToken setId(Integer id){
        this.id = id;
        return this;
    }

    public String getAccount(){
        return this.account;
    }

    public ShopToken setAccount(String account){
        this.account = account;
        return this;
    }

    public String getName(){
        return this.name;
    }

    public ShopToken setName(String name){
        this.name = name;
        return this;
    }

    public String getApp_auth_code(){
        return this.app_auth_code;
    }

    public ShopToken setApp_auth_code(String app_auth_code){
        this.app_auth_code = app_auth_code;
        return this;
    }

    public String getApp_auth_token(){
        return this.app_auth_token;
    }

    public ShopToken setApp_auth_token(String app_auth_token){
        this.app_auth_token = app_auth_token;
        return this;
    }

    public String getUser_id(){
        return this.user_id;
    }

    public ShopToken setUser_id(String user_id){
        this.user_id = user_id;
        return this;
    }

    public String getAuth_app_id(){
        return this.auth_app_id;
    }

    public ShopToken setAuth_app_id(String auth_app_id){
        this.auth_app_id = auth_app_id;
        return this;
    }

    public Integer getExpires_in(){
        return this.expires_in;
    }

    public ShopToken setExpires_in(Integer expires_in){
        this.expires_in = expires_in;
        return this;
    }

    public Integer getRe_expires_in(){
        return this.re_expires_in;
    }

    public ShopToken setRe_expires_in(Integer re_expires_in){
        this.re_expires_in = re_expires_in;
        return this;
    }

    public String getApp_refresh_token(){
        return this.app_refresh_token;
    }

    public ShopToken setApp_refresh_token(String app_refresh_token){
        this.app_refresh_token = app_refresh_token;
        return this;
    }

    public String getStat(){
        return this.stat;
    }

    public ShopToken setStat(String stat){
        this.stat = stat;
        return this;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public ShopToken setCreatedt(Date createdt) {
        this.createdt = createdt;
        return this;
    }

    public Date getUpdatedt() {
        return updatedt;
    }

    public ShopToken setUpdatedt(Date updatedt) {
        this.updatedt = updatedt;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ShopToken{");
        sb.append("id=").append(id);
        sb.append(", account='").append(account).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", app_auth_code='").append(app_auth_code).append('\'');
        sb.append(", app_auth_token='").append(app_auth_token).append('\'');
        sb.append(", user_id='").append(user_id).append('\'');
        sb.append(", auth_app_id='").append(auth_app_id).append('\'');
        sb.append(", expires_in=").append(expires_in);
        sb.append(", re_expires_in=").append(re_expires_in);
        sb.append(", app_refresh_token='").append(app_refresh_token).append('\'');
        sb.append(", stat='").append(stat).append('\'');
        sb.append(", createdt=").append(createdt);
        sb.append(", updatedt=").append(updatedt);
        sb.append('}');
        return sb.toString();
    }
}
