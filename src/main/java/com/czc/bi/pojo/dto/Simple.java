package com.czc.bi.pojo.dto;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc :   简单的pojo
 * @date : 2017/9/27.
 * @version: V1.0
 */
public class Simple<T1,T2> {
    T1  key;
    T2  value;

    public T1 getKey() {
        return key;
    }

    public Simple<T1, T2> setKey(T1 key) {
        this.key = key;
        return this;
    }

    public T2 getValue() {
        return value;
    }

    public Simple<T1, T2> setValue(T2 value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Simple{");
        sb.append("key=").append(key);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
