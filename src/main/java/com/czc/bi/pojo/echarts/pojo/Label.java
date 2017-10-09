package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Label {
	private Normal normal;

	public Normal getNormal() {
		return normal;
	}

	public Label setNormal(Normal normal) {
		this.normal = normal;
		return this;
	}

}
