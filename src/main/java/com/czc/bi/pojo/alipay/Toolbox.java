package com.czc.bi.pojo.alipay;

import com.czc.bi.util.BaseUtil;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class Toolbox {
	private Boolean show;
	private List<Feature> feature;

	public Boolean getShow() {
		return this.show;
	}

	public Toolbox setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public void addFeature() {
		// 判断空值
		if (feature == null) {
			this.feature = new ArrayList<>();
		}
		 this.feature.add(new Feature().setMark("12344"));
	}

	
	// Toolbox的内部类Feature
	public class Feature {
		private String mark;

		public String getMark() {
			return mark;
		}

		public Feature setMark(String mark) {
			this.mark = mark;
			return this;
		}
	}

	public static void main(String[] args) {
		Toolbox toolbox = new Toolbox();
		toolbox.setShow(true);
		toolbox.addFeature();
		String string = BaseUtil.jsonToString(toolbox);
		System.out.println(string);

	}
}
