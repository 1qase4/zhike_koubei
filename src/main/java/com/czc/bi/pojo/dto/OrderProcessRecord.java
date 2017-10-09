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
 * @date : 2017年3月23日上午10:33:11
 * @version: V1.0
 */
public class OrderProcessRecord {
	private int logId;// 日志编号
	private String id;// 订单编号
	private String account;// 订单处理账号
	private String process;// 处理步骤
	@JsonSerialize(using = CustomDateSerializer3.class)
	private Date processTime;// 处理时间

	public int getLogId() {
		return logId;
	}

	public OrderProcessRecord setLogId(int logId) {
		this.logId = logId;
		return this;
	}

	public String getId() {
		return id;
	}

	public OrderProcessRecord setId(String id) {
		this.id = id;
		return this;
	}

	public String getAccount() {
		return account;
	}

	public OrderProcessRecord setAccount(String account) {
		this.account = account;
		return this;
	}

	public String getProcess() {
		return process;
	}

	public OrderProcessRecord setProcess(String process) {
		this.process = process;
		return this;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public OrderProcessRecord setProcessTime(Date processTime) {
		this.processTime = processTime;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("OrderProcessRecord{");
		sb.append("id=").append(id);
		sb.append(", account='").append(account).append('\'');
		sb.append(", process='").append(process).append('\'');
		sb.append(", processTime=").append(processTime);
		sb.append('}');
		return sb.toString();
	}

}
