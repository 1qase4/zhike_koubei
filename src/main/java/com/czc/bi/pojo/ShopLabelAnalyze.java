package com.czc.bi.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[shop_label_analyze]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class ShopLabelAnalyze implements Serializable {

    // 主键id
    private Integer id;

    // 类型
    private String type;

    // 账号
    private String account;

    // 店铺名称
    private String shop;

    // Key
    private String key;

    // 值
    private String value;

    // 数据时间
    private String pdate;

    // 记录创建时间
    private Date createdt;

    // 记录最后更新时间
    private Date updatedt;

    // get set 方法
    public Integer getId(){
        return this.id;
    }

    public ShopLabelAnalyze setId(Integer id){
        this.id = id;
        return this;
    }

    public String getType(){
        return this.type;
    }

    public ShopLabelAnalyze setType(String type){
        this.type = type;
        return this;
    }

    public String getAccount(){
        return this.account;
    }

    public ShopLabelAnalyze setAccount(String account){
        this.account = account;
        return this;
    }

    public String getShop(){
        return this.shop;
    }

    public ShopLabelAnalyze setShop(String shop){
        this.shop = shop;
        return this;
    }

    public String getKey(){
        return this.key;
    }

    public ShopLabelAnalyze setKey(String key){
        this.key = key;
        return this;
    }

    public String getValue(){
        return this.value;
    }

    public ShopLabelAnalyze setValue(String value){
        this.value = value;
        return this;
    }

    public String getPdate(){
        return this.pdate;
    }

    public ShopLabelAnalyze setPdate(String pdate){
        this.pdate = pdate;
        return this;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public ShopLabelAnalyze setCreatedt(Date createdt) {
        this.createdt = createdt;
        return this;
    }

    public Date getUpdatedt() {
        return updatedt;
    }

    public ShopLabelAnalyze setUpdatedt(Date updatedt) {
        this.updatedt = updatedt;
        return this;
    }

}
