package com.czc.bi.pojo;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc :   简单的pojo
 * @date : 2017/9/27.
 * @version: V1.0
 */
public class Simple {
    Object  key;
    Object  value;

    public Object getKey() {
        return key;
    }

    public Simple setKey(Object key) {
        this.key = key;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Simple setValue(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Simple{");
        sb.append("key=").append(key);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
