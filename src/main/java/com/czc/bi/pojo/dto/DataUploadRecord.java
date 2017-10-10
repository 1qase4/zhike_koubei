package com.czc.bi.pojo.dto;

import java.util.Date;

import com.czc.bi.util.CustomDateSerializer3;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * Copyright © 上海辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc :
 * @date : 2017年9月27日上午10:22:21
 * @version: V1.0
 */
public class DataUploadRecord {
	private String orderId;
	private String account;
	@JsonSerialize(using = CustomDateSerializer3.class)
	private Date dataUploadTime;
	private String shop;
	private String dataBeginEnddt;
	private String labelBeginEnddt;

	public String getOrderId() {
		return orderId;
	}

	public DataUploadRecord setOrderId(String orderId) {
		this.orderId = orderId;
		return this;
	}

	public String getAccount() {
		return account;
	}

	public DataUploadRecord setAccount(String account) {
		this.account = account;
		return this;
	}

	public Date getDataUploadTime() {
		return dataUploadTime;
	}

	public DataUploadRecord setDataUploadTime(Date dataUploadTime) {
		this.dataUploadTime = dataUploadTime;
		return this;
	}

	public String getShop() {
		return shop;
	}

	public DataUploadRecord setShop(String shop) {
		this.shop = shop;
		return this;
	}

	public String getDataBeginEnddt() {
		return dataBeginEnddt;
	}

	public DataUploadRecord setDataBeginEnddt(String dataBeginEnddt) {
		this.dataBeginEnddt = dataBeginEnddt;
		return this;
	}

	public String getLabelBeginEnddt() {
		return labelBeginEnddt;
	}

	public DataUploadRecord setLabelBeginEnddt(String labelBeginEnddt) {
		this.labelBeginEnddt = labelBeginEnddt;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("DataUploadRecord{");
		sb.append("account='").append(account).append('\'');
		sb.append(", shop='").append(shop).append('\'');
		sb.append(", dataBeginEnddt='").append(dataBeginEnddt).append('\'');
		sb.append(", labelBeginEnddt='").append(labelBeginEnddt).append('\'');
		sb.append(", dataUploadTime='").append(dataUploadTime).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
