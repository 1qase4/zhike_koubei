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
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String ,String> login(String username,String password){
        Map<String ,String> result = new HashMap<String ,String>();
        List<CfgUser> cfgUsers = cfgUserMapper.selectByQuery(new CfgUserQuery().setUsername(username));
        if (cfgUsers == null || cfgUsers.size() == 0){
            result.put("message","用户不存在！");
            result.put("status","0");
            return result;
        }
        CfgUser cfgUser = cfgUsers.get(0);
        if (cfgUser.getPassword()!= null && !cfgUser.getPassword().equals(password)){
            result.put("status","0");
            result.put("message","密码错误！");
            return result;
        }
        result.put("status","1");
        result.put("userid",cfgUser.getId().toString());
        return result;
    }

    @RequestMapping("show")
    public Map<String ,Object> show(Integer userid){
        Map<String ,Object> result = new HashMap<String ,Object>();

        if (userid == null){
            result.put("status",0);
            result.put("message","请登录！");
            return result;
        }

        CfgUser cfgUser = cfgUserMapper.selectByPrimaryKey(userid);
        if (cfgUser == null){
            result.put("status",0);
            result.put("message","用户无权限！");
            return result;
        }

        List<PotentialCust> list = potentialCustMapper.selectByQuery(new PotentialCustQuery());
        result.put("status",1);
        result.put("result",list);
        return result;
    }

    @RequestMapping("test")
    public Result test(){
        return new Result("5555");
    }
}
