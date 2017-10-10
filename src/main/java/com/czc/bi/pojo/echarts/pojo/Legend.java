package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Legend {
	private List<String> data;
	private String x;
	// 图例列表的布局朝向 horizontal(水平)  vertical(垂直)
	private String orient;
	// 上
	private String top;
	// 下
	private String bottom;
	// 左
	private String left;
	// 右
	private String right;

	public List<String> getData() {
		return data;
	}

	public Legend setData(List<String> data) {
		this.data = data;
		return this;
	}
	
	public Legend addData(String data) {
		if(this.data == null){
			this.data = new ArrayList<String>();
		}
		this.data.add(data);
		return this;
	}

	public String getRight() {
		return right;
	}

	public Legend setRight(String right) {
		this.right = right;
		return this;
	}

	public String getX() {
		return x;
	}

	public Legend setX(String x) {
		this.x = x;
		return this;
	}

	public String getOrient() {
		return orient;
	}

	public String getBottom() {
		return bottom;
	}

	public void setBottom(String bottom) {
		this.bottom = bottom;
	}

	public Legend setOrient(String orient) {
		this.orient = orient;
		return this;
	}

	public String getTop() {
		return top;
	}

	public Legend setTop(String top) {
		this.top = top;
		return this;
	}

	public String getLeft() {
		return left;
	}

	public Legend setLeft(String left) {
		this.left = left;
		return this;
	}
}
