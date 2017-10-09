package com.czc.bi.pojo;

import com.czc.bi.util.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class Device {
	private int id;
	private String lanmac;
	private String code;
	private String position;
	private String shop;
	private String merchant;
	private String longitude;
	private String latitude;
	private String agents;
	private String path;
	private String type;
	private String version;
	private String flag;
	private String issend;// 是否发送
	private String sendtime;// 发送时间
	private String sendphone;
	private String mac;// mac检测
	private Date last_arrival;// 数据的时间戳
	private Integer last_arrivaltime;// 数据的差值
	private String stat;// 状态
	private String stattime;// 状态的时间
	private int signal;// 设备信号强度
	private String regdt;// 注册时间
	private String group;// 分组
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date createdt;
	private Date updatedt;

	public String getPosition() {
		return position;
	}

	public Device setPosition(String position) {
		this.position = position;
		return this;
	}

	public int getId() {
		return id;
	}

	public Device setId(int id) {
		this.id = id;
		return this;
	}

	public String getLanmac() {
		return lanmac;
	}

	public Device setLanmac(String lanmac) {
		this.lanmac = lanmac;
		return this;
	}

	public String getCode() {
		return code;
	}

	public Device setCode(String code) {
		this.code = code;
		return this;
	}

	public String getShop() {
		return shop;
	}

	public Device setShop(String shop) {
		this.shop = shop;
		return this;
	}

	public String getMerchant() {
		return merchant;
	}

	public Device setMerchant(String merchant) {
		this.merchant = merchant;
		return this;
	}

	public String getLongitude() {
		return longitude;
	}

	public Device setLongitude(String longitude) {
		this.longitude = longitude;
		return this;
	}

	public String getLatitude() {
		return latitude;
	}

	public Device setLatitude(String latitude) {
		this.latitude = latitude;
		return this;
	}

	public String getAgents() {
		return agents;
	}

	public Device setAgents(String agents) {
		this.agents = agents;
		return this;
	}

	public String getPath() {
		return path;
	}

	public Device setPath(String path) {
		this.path = path;
		return this;
	}

	public String getFlag() {
		return flag;
	}

	public Device setFlag(String flag) {
		this.flag = flag;
		return this;
	}

	public Date getCreatedt() {
		return createdt;
	}

	public void setCreatedt(Date createdt) {
		this.createdt = createdt;

	}

	public Date getUpdatedt() {
		return updatedt;
	}

	public void setUpdatedt(Date updatedt) {
		this.updatedt = updatedt;
	}

	public String getType() {
		return type;
	}

	public Device setType(String type) {
		this.type = type;
		return this;
	}

	public String getVersion() {
		return version;
	}

	public String getSendtime() {
		return sendtime;
	}

	public Device setSendtime(String sendtime) {
		this.sendtime = sendtime;
		return this;
	}

	public String getSendphone() {
		return sendphone;
	}

	public Device setSendphone(String sendphone) {
		this.sendphone = sendphone;
		return this;
	}

	public Device setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getIssend() {
		return issend;
	}

	public void setIssend(String issend) {
		this.issend = issend;
	}

	public String getStat() {
		return stat;
	}

	public Device setStat(String stat) {
		this.stat = stat;
		return this;
	}

	public String getMac() {
		return mac;
	}

	public Device setMac(String mac) {
		this.mac = mac;
		return this;
	}

	public Date getLast_arrival() {
		return last_arrival;
	}

	public Device setLast_arrival(Date last_arrival) {
		this.last_arrival = last_arrival;
		return this;
	}

	public Integer getLast_arrivaltime() {
		return last_arrivaltime;
	}

	public Device setLast_arrivaltime(Integer last_arrivaltime) {
		this.last_arrivaltime = last_arrivaltime;
		return this;
	}

	public String getStattime() {
		return stattime;
	}

	public Device setStattime(String stattime) {
		this.stattime = stattime;
		return this;
	}

	public int getSignal() {
		return signal;
	}

	public Device setSignal(int signal) {
		this.signal = signal;
		return this;
	}

	public String getRegdt() {
		return regdt;
	}

	public Device setRegdt(String regdt) {
		this.regdt = regdt;
		return this;
	}

	public String getGroup() {
		return group;
	}

	public Device setGroup(String group) {
		this.group = group;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Device{");
		sb.append("id=").append(id);
		sb.append(", lanmac='").append(lanmac).append('\'');
		sb.append(", code='").append(code).append('\'');
		sb.append(", shop='").append(shop).append('\'');
		sb.append(", merchant='").append(merchant).append('\'');
		sb.append(", longitude='").append(longitude).append('\'');
		sb.append(", latitude='").append(latitude).append('\'');
		sb.append(", agents='").append(agents).append('\'');
		sb.append(", path='").append(path).append('\'');
		sb.append(", type='").append(type).append('\'');
		sb.append(", version='").append(version).append('\'');
		sb.append(", flag='").append(flag).append('\'');
		sb.append(", issend='").append(issend).append('\'');
		sb.append(", sendtime='").append(sendtime).append('\'');
		sb.append(", sendphone='").append(sendphone).append('\'');
		sb.append(", mac='").append(mac).append('\'');
		sb.append(", last_arrival='").append(last_arrival).append('\'');
		sb.append(", last_arrivaltime='").append(last_arrivaltime).append('\'');
		sb.append(", stattime='").append(stattime).append('\'');
		sb.append(", regdt='").append(regdt).append('\'');
		sb.append(", createdt=").append(createdt);
		sb.append(", updatedt=").append(updatedt);
		sb.append('}');
		return sb.toString();
	}
}