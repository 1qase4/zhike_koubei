package com.czc.bi.pojo.echarts.pojo;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : Name Value 键值对
 * @date   : 2016年6月22日 下午4:47:27
 * @version: V1.0
 */
public class NameValue {
	private String name;
	private Number value;
	
	public String getName() {
		return name;
	}
	public NameValue setName(String name) {
		this.name = name;
		return this;
	}
	public Number getValue() {
		return value;
	}
	public NameValue setValue(Number value) {
		this.value = value;
		return this;
	}
	
	
	@Override
	public String toString() {
		return "NameValue [name=" + name + ", value=" + value + "]";
	}
}
