package com.czc.bi.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : 支付宝-口碑数据同步
 * @date : 2017/10/24.
 * @version: V1.0
 */
@Service
public class AlipayDataSync {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(cron = "0/10 * * * * ?") // 每20秒执行一次
    public void scheduler() {
        logger.info(">>>>>>>>>>>>> scheduled ... ");
    }
}
