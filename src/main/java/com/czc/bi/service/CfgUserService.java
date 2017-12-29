package com.czc.bi.service;

import com.czc.bi.mapper.CfgUserMapper;
import com.czc.bi.pojo.CfgUser;
import com.czc.bi.pojo.query.CfgUserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */
@Service
public class CfgUserService {

    @Autowired
    private CfgUserMapper cfgUserMapper;

    public CfgUser findByUsername(String usrname) {
        List<CfgUser> cfgUsers = cfgUserMapper.selectByQuery(new CfgUserQuery().setUsername(usrname));
        if (cfgUsers.size() < 0){
            return null;
        }
        return cfgUsers.get(0);
    }

    public CfgUser findById(Integer userid) {
        return cfgUserMapper.selectByPrimaryKey(userid);
    }
}
