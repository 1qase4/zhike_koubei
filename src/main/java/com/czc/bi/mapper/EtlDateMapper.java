package com.czc.bi.mapper;

import com.czc.bi.mapper.BaseMapper;
import com.czc.bi.pojo.EtlDate;
import org.apache.ibatis.annotations.Param;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[etl_date]的mapper接口
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public interface EtlDateMapper extends BaseMapper<EtlDate>{

    int updataEtlDate(@Param("account") String account, @Param("pdate") String pdate);
}
