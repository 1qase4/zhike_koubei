package com.czc.bi.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[shop_passengerflow_analyze]的PO对象
 * @date   : 2017/9/27.
 * @version: V1.0
 */

public class ShopPassengerflowAnalyze implements Serializable {

    // 主键id
    private Integer id;

    // 类型
    private String type;

    // 账号
    private String account;

    // 店铺名称
    private String shop;

    // 序号
    private Integer rank;

    // 栏目
    private String label;

    // 值
    private Integer value;

    // 计算时间
    private String pdate;

    // 记录创建时间
    private Date createdt;

    // 记录最后更新时间
    private Date updatedt;

    // get set 方法
    public Integer getId(){
        return this.id;
    }

    public ShopPassengerflowAnalyze setId(Integer id){
        this.id = id;
        return this;
    }

    public String getType(){
        return this.type;
    }

    public ShopPassengerflowAnalyze setType(String type){
        this.type = type;
        return this;
    }

    public String getAccount(){
        return this.account;
    }

    public ShopPassengerflowAnalyze setAccount(String account){
        this.account = account;
        return this;
    }

    public String getShop(){
        return this.shop;
    }

    public ShopPassengerflowAnalyze setShop(String shop){
        this.shop = shop;
        return this;
    }

    public Integer getRank(){
        return this.rank;
    }

    public ShopPassengerflowAnalyze setRank(Integer rank){
        this.rank = rank;
        return this;
    }

    public String getLabel(){
        return this.label;
    }

    public ShopPassengerflowAnalyze setLabel(String label){
        this.label = label;
        return this;
    }

    public Integer getValue(){
        return this.value;
    }

    public ShopPassengerflowAnalyze setValue(Integer value){
        this.value = value;
        return this;
    }

    public String getPdate(){
        return this.pdate;
    }

    public ShopPassengerflowAnalyze setPdate(String pdate){
        this.pdate = pdate;
        return this;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public ShopPassengerflowAnalyze setCreatedt(Date createdt) {
        this.createdt = createdt;
        return this;
    }

    public Date getUpdatedt() {
        return updatedt;
    }

    public ShopPassengerflowAnalyze setUpdatedt(Date updatedt) {
        this.updatedt = updatedt;
        return this;
    }

    @Override
    public String toString() {
        return "ShopCustflowAnalyze{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", account='" + account + '\'' +
                ", shop='" + shop + '\'' +
                ", rank=" + rank +
                ", label='" + label + '\'' +
                ", value=" + value +
                ", pdate='" + pdate + '\'' +
                ", createdt=" + createdt +
                ", updatedt=" + updatedt +
                '}';
    }
}
