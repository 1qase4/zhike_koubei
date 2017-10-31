package com.czc.bi.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.czc.bi.util.Constant.*;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/10/31.
 * @version: V1.0
 */
@Component
public class BuildAlipayClient implements FactoryBean<AlipayClient> {

    @Override
    public AlipayClient getObject() throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAT,
                PPID,
                PRIVATE_KEY,
                FORMAT,
                CHARSET,
                ALIPAY_PUBLIC_KEY,
                SIGN_TYPE);

        return alipayClient;
    }

    @Override
    public Class<?> getObjectType() {
        return AlipayClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
