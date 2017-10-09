package com.czc.bi.pojo.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc   : Excel的一行记录
 * @date   : 2017/9/27.
 * @version: V1.0
 */

public class DataRow {
    // 一行的记录数
    private List<String> record;
    // 合并多少个单元格
    private Integer meger;
    // 记录是否为标题
    private Boolean title = false;

    public DataRow addRecord(String argv){
        if(this.record ==null){
            this.record = new ArrayList<>(5);
        }
        this.record.add(argv);
        return this;
    }

    public List<String> getRecord() {
        return record;
    }

    public DataRow setRecord(List<String> record) {
        this.record = record;
        return this;
    }

    public Integer getMeger() {
        return meger;
    }

    public DataRow setMeger(Integer meger) {
        this.meger = meger;
        return this;
    }

    public Boolean getTitle() {
        return title;
    }

    public DataRow setTitle(Boolean title) {
        this.title = title;
        return this;
    }
}
