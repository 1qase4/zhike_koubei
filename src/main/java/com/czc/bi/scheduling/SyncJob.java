package com.czc.bi.scheduling;

import com.alipay.api.domain.AlisisReportRow;

import java.util.List;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/12/5.
 * @version: V1.0
 */
public interface SyncJob {
    JobResult execute(String shopid,String token,String pdate);
}
