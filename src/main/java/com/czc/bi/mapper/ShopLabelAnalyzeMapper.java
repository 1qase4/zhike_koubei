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
 * @author : zchong
 * @Desc : 数据库表[shop_label_analyze]的mapper接口
 * @date : 2016/8/22.
 * @version: V1.0
 */

@Mapper
public interface ShopLabelAnalyzeMapper extends BaseMapper<ShopLabelAnalyze> {

    List<SimpleKV<String, String>> selectKYByQuery(@Param("query") BaseQuery query);

    int deleteByAccountPdate(@Param("account") String account, @Param("pdate") String pdate);

}
