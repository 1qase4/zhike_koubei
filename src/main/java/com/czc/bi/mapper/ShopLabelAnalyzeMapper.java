package com.czc.bi.mapper;

import com.czc.bi.pojo.ShopLabelAnalyze;
import com.czc.bi.pojo.SimpleKV;
import com.czc.bi.pojo.query.BaseQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc : 数据库表[shop_label_analyze]的mapper接口
 * @date : 2017/9/28.
 * @version: V1.0
 */


public interface ShopLabelAnalyzeMapper extends BaseMapper<ShopLabelAnalyze> {

    List<SimpleKV<String, String>> selectKYByQuery(@Param("query") BaseQuery query);

}
