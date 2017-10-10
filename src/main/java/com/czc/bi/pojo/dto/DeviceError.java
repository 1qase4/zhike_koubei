package com.czc.bi.pojo.dto;

/**
 * 
 * Copyright © 上海辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc :
 * @date : 2017年9月27日下午5:28:02
 * @version: V1.0
 */
public class DeviceError {
	private String lanmac;
	private String code;
	private String shop;
	private String stat;
	private String time;
	private String position;

	public DeviceError() {
	}

	public String getLanmac() {
		return lanmac;
	}

	public void setLanmac(String lanmac) {
		this.lanmac = lanmac;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("DeviceError{");
		sb.append("lanmac='").append(lanmac).append('\'');
		sb.append(", code='").append(code).append('\'');
		sb.append(", shop='").append(shop).append('\'');
		sb.append(", stat='").append(stat).append('\'');
		sb.append(", time='").append(time).append('\'');
		sb.append(", position='").append(position).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
