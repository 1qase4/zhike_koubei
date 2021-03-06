package com.czc.bi.mapper;

import com.czc.bi.pojo.ShopPassengerflowAnalyze;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc   : 数据库表[shop_passengerflow_analyze]的mapper接口
 * @date   : 2017/9/28.
 * @version: V1.0
 */


public interface ShopPassengerflowAnalyzeMapper extends BaseMapper<ShopPassengerflowAnalyze> {

    int replace(@Param("record") ShopPassengerflowAnalyze record);

    int replaces(@Param("records") List<ShopPassengerflowAnalyze> records);

    String selectPdateByAccount(@Param("account") String account);
}
