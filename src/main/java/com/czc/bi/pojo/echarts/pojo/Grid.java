package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Grid {
	// 上边距
	private Object top;
	// 左边距
	private Object left;
	
	// 右边距
	private Object right;
	
	// 下边距
	private Object bottom;

	public Object getLeft() {
		return left;
	}

	public Grid setLeft(Object left) {
		this.left = left;
		return this;
	}

	public Object getTop() {
		return top;
	}

	public Grid setTop(Object top) {
		this.top = top;
		return this;
	}

	public Object getRight() {
		return right;
	}

	public Grid setRight(Object right) {
		this.right = right;
		return this;
	}

	public Object getBottom() {
		return bottom;
	}

	public Grid setBottom(Object bottom) {
		this.bottom = bottom;
		return this;
	}

}
