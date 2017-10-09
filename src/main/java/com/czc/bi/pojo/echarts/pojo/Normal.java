package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Normal {
	private boolean show;
	private String position;

	public boolean isShow() {
		return show;
	}

	public Normal setShow(boolean show) {
		this.show = show;
		return this;
	}

	public String getPosition() {
		return position;
	}

	public Normal setPosition(String position) {
		this.position = position;
		return this;
	}

}
