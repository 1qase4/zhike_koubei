package com.czc.bi.controller;

import com.czc.bi.mapper.PotentialCustMapper;
import com.czc.bi.pojo.PotentialCust;
import com.czc.bi.pojo.dto.Result;
import com.czc.bi.pojo.query.PotentialCustQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 * 口碑后台管理
 */
@RestController("management")
public class ManagementController {
    @Autowired
    private PotentialCustMapper potentialCustMapper;

    @RequestMapping("show")
    public Result show(){
        List<PotentialCust> list = potentialCustMapper.selectByQuery(new PotentialCustQuery().setStatus("0"));
        return new Result(list);
    }
}
