package com.czc.bi.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/12/5.
 * @version: V1.0
 */
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        SpringContextUtils.context = context;
    }

    public static Object getBean(String name) throws BeansException {
        return context.getBean(name);
    }
}
