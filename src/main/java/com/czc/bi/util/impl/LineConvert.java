package com.czc.bi.util.impl;

import com.czc.bi.pojo.echarts.pojo.*;
import com.czc.bi.util.OptConvert;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : 折线图
 * @date : 2016年6月24日 下午2:49:01
 * @version: V1.0
 */

public class LineConvert implements OptConvert {
	// x轴
	private Axis x;
	// y轴
	private Axis y;
	// title
	private Title title;
	// legend
	private Legend legend;
	// 数据集合
	private List<Number[]> data;
	// 数据缩放域
	private DataZoom zoom;
	// option
	private Option option;

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	// label
	private Label label;

	// get set
	public Legend getLegend() {
		return legend;
	}

	public void setLegend(Legend legend) {
		this.legend = legend;
	}

	public LineConvert() {
		this.x = new Axis();
		this.y = new Axis();
		this.legend = new Legend();
		this.option = new Option();
		this.data = new ArrayList<>();
		this.label = new Label().setNormal(new Normal().setShow(true).setPosition("top"));
	}

	public Axis getX() {
		return x;
	}

	public void setX(Axis x) {
		this.x = x;
	}

	public void setTitleString(String text) {
		this.title = new Title();
		this.title.setText(text);
		this.option.setTitle(title);
	}

	public void setZoom(int start, int end) {
		this.zoom = new DataZoom();
		this.zoom.setStart(start).setEnd(end);
		this.option.addDataZoom(this.zoom);
	}

	// 默认情况下缩放域为25~75
	public void setZoom() {
		this.setZoom(25, 75);
	}

	// 设置工具箱显示
	public void setToolbox(String fileName) {
		Toolbox toolbox = new Toolbox();
		toolbox.setShow(true);
		toolbox.feature().saveAsImage();
		toolbox.feature().dataZoom().setShow(true);
		toolbox.feature().magicType().addType("line").addType("bar");
		toolbox.feature().restore();
		toolbox.feature().dataView().setReadOnly(true);
		toolbox.feature().saveAsImage().setName(fileName);
		this.option.setToolbox(toolbox);
	}

	// 添加提示框
	public void setTooltip() {
		Tooltip tooltip = new Tooltip();
		tooltip.setTrigger("axis");
		this.option.setTooltip(tooltip);
	}

	// 设置图表的边距 上下左右
	public void setMargin(String top, String bottom, String left, String right) {
		if (top != null) {
			this.option.grid().setTop(top);
		}
		if (bottom != null) {
			this.option.grid().setBottom(bottom);
		}
		if (left != null) {
			this.option.grid().setLeft(left);
		}
		if (right != null) {
			this.option.grid().setRight(right);
		}
	}

	// 设置Legend的位置
	public void setLegendPosition(String top, String bottom, String left, String right) {
		if (top != null) {
			this.legend.setTop(top);
		}
		if (bottom != null) {
			this.legend.setBottom(bottom);
		}
		if (left != null) {
			this.legend.setLeft(left);
		}
		if (right != null) {
			this.legend.setRight(right);
		}
	}

	// 设置坐标轴的名字
	public void setAxisName(String x, String y) {
		if (x != null) {
			this.x.setName(x);
		}
		if (y != null) {
			this.y.setName(y);
		}
	}

	@Override
	public void setXString(List<String> x) throws Exception {
		if (x.size() == 0 || x == null) {
			throw new Exception("xAxis not null");
		}
		this.x.setData(x);
	}

	@Override
	public void addRecord(String legend, List<Number> record) throws Exception {
		this.addRecord(legend, record.toArray(new Number[record.size()]));
	}

	@Override
	public void addRecord(String legend, Number[] record) throws Exception {
		int length = this.x.getData().size();
		if (length == 0) {
			throw new Exception("xAxis must be set");
		}
		if (length != record.length) {
			throw new Exception("data length wrong");
		}
		this.legend.addData(legend);
		this.data.add(record);
	}

	@Override
	public Option getOption() {
		for (int i = 0; i < this.data.size(); i++) {
			Series series = new Series();
			series.setName(this.legend.getData().get(i));
			series.setType("line");
			series.addData(Arrays.asList(this.data.get(i)));
			series.setLabel(this.label);
			series.setSmooth(true);
			this.option.addSeries(series);
		}

		// 添加xy轴
		this.option.addxAxis(x);
		this.option.getyAxis().set(0, y);
		// 添加legend
		this.option.setLegend(this.legend);
		return option;
	}

}
