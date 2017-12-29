package com.czc.bi.service;

import com.czc.bi.mapper.PotentialCustMapper;
import com.czc.bi.pojo.PotentialCust;
import com.czc.bi.pojo.query.PotentialCustQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */
@Service
public class PotentialCustService {

    @Autowired
    private PotentialCustMapper potentialCustMapper;


    public List<PotentialCust> findPage(int curPage, int pageSize) {
        List<PotentialCust> potentialCusts = potentialCustMapper.selectByQuery(new PotentialCustQuery().setPage(curPage,pageSize));
        return potentialCusts;
    }

    public int findCount() {
        int count = potentialCustMapper.selectRowsByQuery(new PotentialCustQuery());
        return count;
    }
}
