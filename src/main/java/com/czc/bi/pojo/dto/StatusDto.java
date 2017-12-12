package com.czc.bi.pojo.dto;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/12/11.
 * @version: V1.0
 */
public class StatusDto<T> {
    String status;
    String message;
    T t;

    public String getStatus() {
        return status;
    }

    public StatusDto<T> setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public StatusDto<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getT() {
        return t;
    }

    public StatusDto<T> setT(T t) {
        this.t = t;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("StatusDto{");
        sb.append("status='").append(status).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", t=").append(t);
        sb.append('}');
        return sb.toString();
    }
}
