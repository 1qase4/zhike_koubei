package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tooltip {
	private String trigger;
	private String formatter;
	private AxisPointer axisPointer;

	public String getTrigger() {
		return trigger;
	}

	public Tooltip setTrigger(String trigger) {
		this.trigger = trigger;
		return this;
	}

	public String getFormatter() {
		return formatter;
	}

	public Tooltip setFormatter(String formatter) {
		this.formatter = formatter;
		return this;
	}
	
	
	public AxisPointer getAxisPointer() {
		return axisPointer;
	}
	
	public AxisPointer axisPointer() {
		if(this.axisPointer == null){
			// 坐标轴指示器配置项生效吗,必须先设置trigger = "axis"
			this.trigger = "axis";
			this.axisPointer = new AxisPointer();
		}
		return this.axisPointer;
	}

	public void setAxisPointer(AxisPointer axisPointer) {
		this.axisPointer = axisPointer;
	}


	@JsonInclude(JsonInclude.Include.NON_NULL)
	public class AxisPointer{
		private String type;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
	}
	
}
