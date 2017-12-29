package com.czc.bi.controller;


import com.czc.bi.pojo.CfgUser;
import com.czc.bi.pojo.PotentialCust;
import com.czc.bi.service.CfgUserService;
import com.czc.bi.service.PotentialCustService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
    private PotentialCustService potentialCustService;

    @Autowired
    private CfgUserService cfgUserService;

    @RequestMapping("manageMain")
    public ModelAndView manageMain(ModelAndView mv,Integer userid,HttpSession session){
        session.setAttribute("cfguserid",userid);
        int count = potentialCustService.findCount();
        session.setAttribute("total",count);
        mv.setViewName("manage/main");
        return mv;
    }

    @RequestMapping("cfgLogin")
    public Map<String ,String> login(String username,String password){
        Map<String ,String> result = new HashMap<String ,String>();
        CfgUser cfgUser = cfgUserService.findByUsername(username);
        if (cfgUser == null){
            result.put("message","用户不存在！");
            result.put("status","0");
            return result;
        }
        if (cfgUser.getPassword() == null || !cfgUser.getPassword().equals(password)){
            result.put("status","0");
            result.put("message","密码错误！");
            return result;
        }
        result.put("status","1");
        result.put("userid",cfgUser.getId().toString());
        return result;
    }

    @RequestMapping("show")
    public Map<String ,Object> show(Integer userid, @RequestParam(required=true,defaultValue="1") int curPage, @RequestParam(required=false,defaultValue="10") int pageSize){
        Map<String ,Object> result = new HashMap<String ,Object>();

        if (userid == null){
            result.put("status",0);
            result.put("message","请登录！");
            return result;
        }

        CfgUser cfgUser = cfgUserService.findById(userid);
        if (cfgUser == null){
            result.put("status",0);
            result.put("message","用户无权限！");
            return result;
        }

        List<PotentialCust> custList = potentialCustService.findPage(curPage, pageSize);
        result.put("status",1);
        result.put("result",custList);
        return result;
    }
}
