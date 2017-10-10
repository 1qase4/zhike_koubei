package com.czc.bi.pojo;

import java.io.Serializable;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc :   简单的pojo
 * @date : 2017/9/27.
 * @version: V1.0
 */
public class SimpleKV<K, V> implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	K key;
    V value;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SimpleKV{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
