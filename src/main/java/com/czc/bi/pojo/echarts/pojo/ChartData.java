package com.czc.bi.pojo.echarts.pojo;

public class ChartData {

	private String[] legend;
	private String[] axis;
	private Number[][] data;
	private int nlegend = 0;
	private int naxis = 0;
	private final int maxLegend;
	private final int maxAxis;

	public ChartData(int x, int y) {
		this.legend = new String[x];
		this.axis = new String[y];
		this.setData(new Number[x][y]);

		this.maxLegend = x;
		this.maxAxis = y;

	}

	public ChartData addData(Number... args) throws Exception {
		if (args.length != this.getMaxAxis()) {
			throw (new Exception("args error:args nums must is " +  this.getMaxAxis()));
		}
		for (int i = 0; i < args.length; i++) {
			this.data[this.nlegend][i] = args[i];
		}
		nlegend++;
		return this;
	}

	public String[] getLegend() {
		return legend;
	}

	public void setLegend(String... args) throws Exception {
		if (args.length != this.getMaxLegend()) {
			throw (new Exception("args error:args nums must is " +  this.getMaxLegend()));
		}
		for (int i = 0; i < args.length; i++) {
			this.legend[i] = args[i];
		}
	}

	public String[] getAxis() {
		return axis;
	}

	public void setAxis(String... args) throws Exception {
		if (args.length != this.getMaxAxis()) {
			throw (new Exception("args error:args nums must is " +  this.getMaxAxis()));
		}
		for (int i = 0; i < args.length; i++) {
			this.axis[i] = args[i];
		}
	}

	public int getNaxis() {
		return naxis;
	}

	public void setNaxis(int naxis) {
		this.naxis = naxis;
	}

	public int getMaxLegend() {
		return maxLegend;
	}

	public int getMaxAxis() {
		return maxAxis;
	}

	public Number[][] getData() {
		return data;
	}

	public void setData(Number[][] data) {
		this.data = data;
	}
 
}
