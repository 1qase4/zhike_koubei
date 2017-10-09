package com.czc.bi.pojo.echarts.pojo;

import java.util.ArrayList;
import java.util.List;

public class EchartData {
	private List<String> legend;
	private List<String> range;
	private List<ArrayList<Number>> data;

	public EchartData() {
		this.legend = new ArrayList<String>();
		this.setRange(new ArrayList<String>());
		this.setData(new ArrayList<ArrayList<Number>>());
	}

	public EchartData addData(String legend, List<Number> data) {
		this.legend.add(legend);
		this.getData().add((ArrayList<Number>) data);
		return this;
	}

	public List<String> getLegend() {
		return legend;
	}

	public List<String> getRange() {
		return range;
	}

	public void setRange(List<String> range) {
		this.range = range;
	}

	public EchartData addRange(String range) {
		this.range.add(range);
		return this;
	}

	public List<ArrayList<Number>> getData() {
		return data;
	}

	public List<ArrayList<PieData>> getPieData() {
		List<ArrayList<PieData>> pieData = new ArrayList<ArrayList<PieData>>();
		for (ArrayList<Number> nums : this.data) {
			ArrayList<PieData> pies = new ArrayList<PieData>();
			for (int i = 0; i < nums.size(); ++i) {
				pies.add(new PieData(this.legend.get(i), nums.get(i)));
			}
			pieData.add(pies);
		}
		return pieData;
	}

	public void setData(List<ArrayList<Number>> data) {
		this.data = data;
	}

	public int size() {
		return this.getData().size();
	}

}
