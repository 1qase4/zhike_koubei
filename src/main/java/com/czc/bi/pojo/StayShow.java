package com.czc.bi.pojo;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc :
 * @date :   2017/9/27.
 * @version: V1.0
 */
public class StayShow {
    private String mac;
    private String first;
    private String last;
    private int days;
    private int stay;
    private String stayStr;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getStay() {
        return stay;
    }

    public void setStay(int stay) {
        this.stay = stay;
        if (stay == 180) {
            this.stayStr = "3分钟内";
        } else if (stay < 600) {
            this.stayStr = "3~10分钟";
        } else if (stay < 30*60) {
            this.stayStr = "10~30分钟";
        } else if (stay < 60*60) {
            this.stayStr = "30分钟~1小时";
        } else if (stay < 2*60*60){
            this.stayStr = "1~2小时";
        } else if(stay < 4*60*60){
            this.stayStr = "2~4小时";
        } else{
            this.stayStr = "4小时以上";
        }
    }

    public String getStayStr() {
        return stayStr;
    }

    public void setStayStr(String stayStr) {
        this.stayStr = stayStr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StayShow{");
        sb.append("mac='").append(mac).append('\'');
        sb.append(", first='").append(first).append('\'');
        sb.append(", last='").append(last).append('\'');
        sb.append(", days=").append(days);
        sb.append(", stay=").append(stay);
        sb.append(", stayStr='").append(stayStr).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
