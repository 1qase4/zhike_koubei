package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataZoom {
	private String type;
	private int start;
	private int end;
	
	public String getType() {
		return type;
	}
	public DataZoom setType(String type) {
		this.type = type;
		return this;
	}
	public int getStart() {
		return start;
	}
	public DataZoom setStart(int start) {
		this.start = start;
		return this;
	}
	public int getEnd() {
		return end;
	}
	public DataZoom setEnd(int end) {
		this.end = end;
		return this;
	}
	
}
