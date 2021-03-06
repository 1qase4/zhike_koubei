package com.czc.bi.mapper;

import com.czc.bi.pojo.ShopToken;
import org.apache.ibatis.annotations.Param;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : 数据库表[shop_token]的mapper接口
 * @date : 2016/8/22.
 * @version: V1.0
 */

public interface ShopTokenMapper extends BaseMapper<ShopToken> {

    // 将token置为无效
    int updataTokenStat(@Param("account") String account, @Param("token") String token, @Param("stat") String stat);

    // update token info
    int updateToken(@Param("record") ShopToken token);

    int checkAccountUserid(@Param("account") String account, @Param("userid") String userid);
}
