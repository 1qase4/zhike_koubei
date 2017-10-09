package com.czc.bi.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : 前台表格展示数据
 * @date : 2016/8/23.
 * @version: V1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableData {
    private boolean success;
    private String message;
    private int totalRows;
    private int curPage;
    private List data;

    public boolean isSuccess() {
        return success;
    }

    public TableData setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public TableData setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public TableData setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        return this;
    }

    public int getCurPage() {
        return curPage;
    }

    public TableData setCurPage(int curPage) {
        this.curPage = curPage;
        return this;
    }

    public List getData() {
        return data;
    }

    public TableData setData(List data) {
        this.data = data;
        return this;
    }

    public TableData setSimpleDate(List data){
        this.curPage = 1;
        this.totalRows = data.size();
        this.success = true;
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TableData{");
        sb.append("success=").append(success);
        sb.append(", totalRows=").append(totalRows);
        sb.append(", curPage=").append(curPage);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
