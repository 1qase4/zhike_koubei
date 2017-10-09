package com.czc.bi.pojo.echarts.pojo;

public class CopyOfEchartData {

	
	private String[] xaxis;
	private String[] yaxis;
	private Number[][] data;

	private int xaxisLength;

	public CopyOfEchartData(int x, int y) {
		this.xaxisLength = 0;
		this.data = new Number[x][y];
	}

	public CopyOfEchartData addArrayData(Number[] arrayData) {
		this.data[xaxisLength] = arrayData;
		xaxisLength++;
		return this;
	}

	public Number[] getArrayData(int index) {
		return data[index];
	}

	public String[] getXaxis() {
		return xaxis;
	}

	public void setXaxis(String[] xaxis) {
		this.xaxis = xaxis;
	}

	public String[] getYaxis() {
		return yaxis;
	}

	public void setYaxis(String[] yaxis) {
		this.yaxis = yaxis;
	}

	public int getLength() {
		return data.length;
	}

}
