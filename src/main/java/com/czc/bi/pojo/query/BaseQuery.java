package com.czc.bi.pojo.query;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据查询通用参数（分页、排序等）
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class BaseQuery {

    // 操作符号
    // 等于
    public static final String EQUAL = "=";

    // 不等于
    public static final String NOT_EQUAL = "<>";

    // 大于
    public static final String GREATER_THAN = ">";

    // 大于或等于
    public static final String GREATER_OR_EQUAL = ">=";

    // 小于
    public static final String LESS_THAN = "<";

    // 小于或等于
    public static final String LESS_OR_EQUAL = ">=";

    // Like
    public static final String LIKE = "like";


    // 页数
    private Integer pageNo;

    // 页面大小
    private Integer pageSize;

    // 起始记录数
    private Integer startRecord;

    // 排序字段
    private String orderBy;

    // 是否为升序排列
    private boolean asc;

    // get set 方法
    public Integer getPageNo() {
        return pageNo;
    }

    public BaseQuery setPage(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.startRecord = (pageNo - 1) * pageSize;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getStartRecord() {
        return startRecord;
    }

    public String getOrderBy() {
        return orderBy;
    }

    // 默认升序
    public BaseQuery setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        this.asc = true;
        return this;
    }

    public boolean isAsc() {
        return asc;
    }

    public BaseQuery setOrderBy(String orderBy, boolean asc) {
        this.orderBy = orderBy;
        this.asc = asc;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseQuery{");
        sb.append("pageNo=").append(pageNo);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", startRecord=").append(startRecord);
        sb.append(", orderBy='").append(orderBy).append('\'');
        sb.append(", asc=").append(asc);
        sb.append('}');
        return sb.toString();
    }
}
