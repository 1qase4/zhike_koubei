package com.czc.bi.pojo.echarts.pojo;

public class PieData {
	private String name;
	private Object value;

	public PieData(){
		
	}
	
	public PieData(String name, Object value) {
		this.name = name;
		this.setValue(value);
	}

	public String getName() {
		return name;
	}

	public PieData setName(String name) {
		this.name = name;
		return this;
	}

	public Object getValue() {
		return value;
	}

	public PieData setValue(Object value) {
		this.value = value;
		return this;
	}
 
}
