package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2016武汉辰智. All rights reserved.
 *
 * @Prject : dcsj
 * @Package : com.czc.bi.pojo
 * @Description: Echarts Option的封装类 默认属性为null不参与序列化
 * @author : zchong
 * @date : 2016年3月8日 下午5:57:38
 * @version : V1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Option {
	private Title title;
	private Tooltip tooltip;
	private Legend legend;
	private List<DataZoom> dataZoom;
	private Grid grid;
	private Radar radar;
	private Toolbox toolbox;
	private Boolean calculable;
	private List<Axis> xAxis;
	private List<Axis> yAxis;
	private List<Series> series;

	public Option() {
		// yAxis 为必输项，随便给一个默认值吧。。。
		this.yAxis = new ArrayList<>();
		Axis y = new Axis();
		y.setType("value");
		this.yAxis.add(y);
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Title title() {
		if (this.title == null) {
			this.title = new Title();
		}
		return title;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}

	public Option setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	public Tooltip tooltip() {
		if (this.tooltip == null) {
			this.tooltip = new Tooltip();
		}
		return tooltip;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Grid grid() {
		if (this.grid == null) {
			this.grid = new Grid();
		}
		return this.grid;
	}

	public Legend getLegend() {
		return legend;
	}

	public void setLegend(Legend legend) {
		this.legend = legend;
	}

	public Legend legend() {
		if (this.legend == null) {
			this.legend = new Legend();
		}
		return legend;
	}

	public List<DataZoom> getDataZoom() {
		return dataZoom;
	}

	public void setDataZoom(List<DataZoom> dataZoom) {
		this.dataZoom = dataZoom;
	}

	public Option addDataZoom(DataZoom dataZoom) {
		if (this.dataZoom == null) {
			this.dataZoom = new ArrayList<>();
		}
		this.dataZoom.add(dataZoom);
		return this;
	}

	public Radar getRadar() {
		return radar;
	}

	public Option setRadar(Radar radar) {
		this.radar = radar;
		return this;
	}

	public Radar radar() {
		if (this.radar == null) {
			this.radar = new Radar();
		}
		return this.radar;
	}

	public Toolbox getToolbox() {
		return toolbox;
	}

	public void setToolbox(Toolbox toolbox) {
		this.toolbox = toolbox;
	}

	public Toolbox toolbox() {
		if (this.toolbox == null) {
			this.toolbox = new Toolbox();
		}
		return toolbox;
	}

	public Boolean getCalculable() {
		return calculable;
	}

	public void setCalculable(Boolean calculable) {
		this.calculable = calculable;
	}

	public List<Axis> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<Axis> xAxis) {
		this.xAxis = xAxis;
	}

	public Option addxAxis(Axis xAxis) {
		if (this.xAxis == null) {
			this.xAxis = new ArrayList<Axis>();
		}
		this.xAxis.add(xAxis);
		return this;
	}

	public List<Axis> getyAxis() {
		return yAxis;
	}

	public void setyAxis(List<Axis> yAxis) {
		this.yAxis = yAxis;
	}

	public Option addyAxis(Axis yAxis) {
		if (this.yAxis == null) {
			this.yAxis = new ArrayList<>();
		}
		this.yAxis.add(yAxis);
		return this;
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

	public Option addSeries(Series series) {
		if (this.series == null) {
			this.series = new ArrayList<>();
		}
		this.series.add(series);
		return this;
	}
}
