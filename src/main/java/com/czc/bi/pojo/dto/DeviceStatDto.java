package com.czc.bi.pojo.dto;

public class DeviceStatDto {

    private String code;

    private String type;

    private String version;

    private String stat;

    private String group;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "DeviceStatDto{" +
                "code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", stat='" + stat + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
