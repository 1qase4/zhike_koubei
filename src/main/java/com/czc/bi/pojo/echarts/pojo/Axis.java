package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Axis {

	private String type;

	private String name;

	private List<String> data;

	private Boolean scale;

	private AxisLabel axisLabel;

	public AxisLabel axisLabel(){
		if(this.axisLabel == null){
			this.axisLabel = new AxisLabel();
		}
		return this.axisLabel;
	}

	public String getName() {
		return name;
	}

	public Axis setName(String name) {
		this.name = name;
		return this;
	}

	public List<String> getData() {
		return data;
	}

	public Axis setData(List<String> data) {
		this.data = data;
		return this;
	}

	public Axis addData(String... args) {
		if (this.data == null) {
			this.data = new ArrayList<String>();
		}
		for (String s : args) {
			this.data.add(s);
		}
		return this;
	}

	public String getType() {
		return type;
	}

	public Axis setType(String type) {
		this.type = type;
		return this;
	}

	public Boolean getScale() {
		return scale;
	}

	public Axis setScale(Boolean scale) {
		this.scale = scale;
		return this;
	}
	
	public AxisLabel getAxisLabel() {
		return axisLabel;
	}

	public void setAxisLabel(AxisLabel axisLabel) {
		this.axisLabel = axisLabel;
	}

	// AxisLabel对象
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public class AxisLabel {
		// x坐标轴的旋转角度从 -90 度到 90 度。
		private int rotate;

		public int getRotate() {
			return rotate;
		}

		public void setRotate(int rotate) {
			this.rotate = rotate;
		}
		
	}

}
