package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Series {
	private String name;
	private String type;
	private List<String> center;
	private List<String> radius;
	private List<Number> data;
	private Mark markPoint;
	private Mark markLine;
	private boolean smooth;
	private Label label;

	public String getName() {
		return name;
	}

	public Series setName(String name) {
		this.name = name;
		return this;
	}

	public String getType() {
		return type;
	}

	public List<String> getRadius() {
		return radius;
	}

	public void setRadius(List<String> radius) {
		this.radius = radius;
	}

	public Series setType(String type) {
		this.type = type;
		return this;
	}

	public List<Number> getData() {
		return data;
	}

	public void setData(List<Number> data) {
		this.data = data;
	}

	public Series addData(Number args) {
		if (this.data == null) {
			this.data = new ArrayList<Number>();
		}
		this.data.add(args);
		return this;
	}

	public Series addData(List<Number> args) {
		if (this.data == null) {
			this.data = new ArrayList<Number>();
		}
		this.data = args;
		return this;
	}

	public Mark getMarkPoint() {
		return markPoint;
	}

	public void setMarkPoint(Mark markPoint) {
		this.markPoint = markPoint;
	}

	public Mark getMarkLine() {
		return markLine;
	}

	public void setMarkLine(Mark markLine) {
		this.markLine = markLine;
	}

	public List<String> getCenter() {
		return center;
	}

	public Series setCenter(List<String> center) {
		this.center = center;
		return this;
	}

	public Series setCenter(String x, String y) {
		this.center = new ArrayList<String>();
		this.center.add(x);
		this.center.add(y);
		return this;
	}

	public Label getLabel() {
		return label;
	}

	public Series setLabel(Label label) {
		this.label = label;
		return this;
	}

	public boolean isSmooth() {
		return smooth;
	}

	public Series setSmooth(boolean smooth) {
		this.smooth = smooth;
		return this;
	}
	
	

}
