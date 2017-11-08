package com.czc.bi.mapper;

import com.czc.bi.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/11/7.
 */
public interface UserMapper extends BaseMapper<User> {
    User findUserByAccount(@Param("account") String username);
}
