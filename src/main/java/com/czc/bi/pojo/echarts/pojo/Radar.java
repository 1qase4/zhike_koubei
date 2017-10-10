package com.czc.bi.pojo.echarts.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Radar {
	private List<Indicator> indicator;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	class Indicator {
		private String name;
		private Number max;

		public String getName() {
			return name;
		}

		public Indicator setName(String name) {
			this.name = name;
			return this;
		}

		public Number getMax() {
			return max;
		}

		public Indicator setMax(Number max) {
			this.max = max;
			return this;
		}
	}

	public List<Indicator> getIndicator() {
		return indicator;
	}

	public void addIndicator(String name, Number max) {
		if (this.indicator == null) {
			this.indicator = new ArrayList<Indicator>();
		}
		this.indicator.add(new Indicator().setName(name).setMax(max));
	}

	public void setSimpleArrayIndicator(List<String> names) {
		if (this.indicator == null) {
			this.indicator = new ArrayList<Indicator>();
		}
		for (String s : names) {
			this.indicator.add(new Indicator().setName(s).setMax(null));
		}
	}

}
