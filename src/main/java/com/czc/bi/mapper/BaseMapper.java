package com.czc.bi.mapper;

import com.czc.bi.pojo.query.BaseQuery;

import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 基础mapper对象,抽取通用方法
 * @date   : 2017/9/29.
 * @version: V1.0
 */

public interface BaseMapper<T> {

    // 根据主键查询记录
    T selectByPrimaryKey(@Param("key") Integer key);

    // 插入1条记录
    int insert(@Param("record") T record);

    // 批量插入数据
    int inserts(@Param("records") List<T> records);

    // 根据Query查询记录
    List<T> selectByQuery(@Param("query") BaseQuery query);

    // 根据Query查询记录条数
    List<T> selectRowsByQuery(@Param("query") BaseQuery query);

    // 根据主键删除记录
    int deleteByPrimaryKey(@Param("key") Integer key);

    // 自定义where查询
    List<T> selectByCondition(@Param("condition") String condition);

}
