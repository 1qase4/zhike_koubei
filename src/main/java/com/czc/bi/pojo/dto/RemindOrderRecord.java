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
public class RemindOrderRecord {
	private String id;
	private String account;
	private String name;
	private String begindt;
	private String enddt;
	@JsonSerialize(using = CustomDateSerializer3.class)
	private Date updatedt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBegindt() {
		return begindt;
	}

	public void setBegindt(String begindt) {
		this.begindt = begindt;
	}

	public String getEnddt() {
		return enddt;
	}

	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}

	public Date getUpdatedt() {
		return updatedt;
	}

	public void setUpdatedt(Date updatedt) {
		this.updatedt = updatedt;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("RemindOrderRecord{");
		sb.append("id='").append(id).append('\'');
		sb.append(", account='").append(account).append('\'');
		sb.append(", name='").append(name).append('\'');
		sb.append(", begindt='").append(begindt).append('\'');
		sb.append(", enddt='").append(enddt).append('\'');
		sb.append(", updatedt='").append(updatedt).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
