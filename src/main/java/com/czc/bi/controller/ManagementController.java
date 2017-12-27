package com.czc.bi.controller;

import com.czc.bi.mapper.CfgUserMapper;
import com.czc.bi.mapper.PotentialCustMapper;
import com.czc.bi.pojo.CfgUser;
import com.czc.bi.pojo.PotentialCust;
import com.czc.bi.pojo.dto.Result;
import com.czc.bi.pojo.query.CfgUserQuery;
import com.czc.bi.pojo.query.PotentialCustQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 * 口碑后台管理
 */
@RestController
@RequestMapping("/management")
public class ManagementController {
    @Autowired
    private PotentialCustMapper potentialCustMapper;

    @Autowired
    private CfgUserMapper cfgUserMapper;

    @RequestMapping("cfgLogin")
    public Result login(String username,String password){
        List<CfgUser> cfgUsers = cfgUserMapper.selectByQuery(new CfgUserQuery().setUsername(username).setPassword(password));
        if (cfgUsers == null || cfgUsers.size() == 0){
            return new Result().setResult(false);
        }
        return new Result(cfgUsers.get(0));
    }

    @RequestMapping("show")
    public Result show(){
        List<PotentialCust> list = potentialCustMapper.selectByQuery(new PotentialCustQuery().setStatus("0"));
        return new Result(list);
    }
}
