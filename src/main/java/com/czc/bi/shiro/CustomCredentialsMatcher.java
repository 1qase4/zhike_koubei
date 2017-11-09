package com.czc.bi.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 自定义的密码验证方案
 * @date   : 2017/11/8.
 * @version: V1.0
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    /**
     *
     * @param token
     * @param info
     * @return 直接返回True 不做验证
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        return true;
    }
}

