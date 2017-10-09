package com.czc.bi.pojo.echarts.pojo;

public class EchartParameter {
	// 图表的标题
	private String title;
	// 图表的类型  若不设置则图表不能正常显示
	//'line'（折线图） | 'bar'（柱状图） | 'scatter'（散点图） | 'k'（K线图）
	//'pie'（饼图） | 'radar'（雷达图） | 'chord'（和弦图） | 'force'（力导向布局图） | 'map'（地图） 
	private String chartType;
	// 鼠标悬浮交互时的信息 不设置图表不正常显示
	private String tooltipTrigger;
	// 背景颜色
	private int[] backgroundColor;
	// 是否显示工具箱
	private Boolean showTools;
	
	// Y轴类型
	private String yaxisType;
	
	// scale策略
	private Boolean scale;
	
	// 构造函数  设置一些默认值
	public EchartParameter(){
		// 设置默认图表类型为折线图
		this.setChartType("line");
		// 设置鼠标悬浮交互时的信息显示
		this.setTooltipTrigger("item");
		// 设置Y轴类型
		this.setYaxisType("value");
		// 默认不显示工具箱
		//this.setShowTools(false);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getTooltipTrigger() {
		return tooltipTrigger;
	}

	public void setTooltipTrigger(String tooltipTrigger) {
		this.tooltipTrigger = tooltipTrigger;
	}

	public int[] getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int[] backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Boolean getShowTools() {
		return showTools;
	}

	public void setShowTools(Boolean showTools) {
		this.showTools = showTools;
	}

	public String getYaxisType() {
		return yaxisType;
	}

	public void setYaxisType(String yaxisType) {
		this.yaxisType = yaxisType;
	}

	public Boolean getScale() {
		return scale;
	}

	public void setScale(Boolean scale) {
		this.scale = scale;
	}

}

