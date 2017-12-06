package com.czc.bi.mapper;

import com.czc.bi.pojo.JobList;
import org.apache.ibatis.annotations.Param;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[job_list]的mapper接口
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public interface JobListMapper extends BaseMapper<JobList>{

    int updateSuccess(@Param("id") int id,@Param("rows") int rows);

    int updateFail(@Param("id")int id,@Param("remark") String remark);
}
