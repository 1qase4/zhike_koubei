package com.czc.bi.pojo.dto;

import java.util.Date;

import com.czc.bi.util.CustomDateSerializer3;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * Copyright © 上海辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : yangmeng
 * @Desc :
 * @date : 2017年3月22日上午10:22:21
 * @version: V1.0
 */
public class HistRecordDownload {
	private String account;
	@JsonSerialize(using = CustomDateSerializer3.class)
	private Date exportTime;
	private String dataType;
	private String dataShop;
	private String group;
	private String device;
	private String startStopTime;

	public String getAccount() {
		return account;
	}

	public HistRecordDownload setAccount(String account) {
		this.account = account;
		return this;
	}

	public Date getExportTime() {
		return exportTime;
	}

	public HistRecordDownload setExportTime(Date exportTime) {
		this.exportTime = exportTime;
		return this;
	}

	public String getDataType() {
		return dataType;
	}

	public HistRecordDownload setDataType(String dataType) {
		this.dataType = dataType;
		return this;
	}

	public String getDataShop() {
		return dataShop;
	}

	public HistRecordDownload setDataShop(String dataShop) {
		this.dataShop = dataShop;
		return this;
	}

	public String getGroup() {
		return group;
	}

	public HistRecordDownload setGroup(String group) {
		this.group = group;
		return this;
	}

	public String getDevice() {
		return device;
	}

	public HistRecordDownload setDevice(String device) {
		this.device = device;
		return this;
	}

	public String getStartStopTime() {
		return startStopTime;
	}

	public HistRecordDownload setStartStopTime(String startStopTime) {
		this.startStopTime = startStopTime;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("HistRecordDownload{");
		sb.append("account='").append(account).append('\'');
		sb.append(", exportTime='").append(exportTime).append('\'');
		sb.append(", dataType='").append(dataType).append('\'');
		sb.append(", dataShop='").append(dataShop).append('\'');
		sb.append(", group='").append(group).append('\'');
		sb.append(", device='").append(device).append('\'');
		sb.append(", startStopTime='").append(startStopTime).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
