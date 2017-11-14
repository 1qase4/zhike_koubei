package com.czc.bi.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/11/14.
 * @version: V1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Return {
    private boolean result;
    private String message;
    private Object data;

    public Return() {
        this.result = true;
    }

    public Return(String message) {
        this.result = false;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public Return setResult(boolean result) {
        this.result = result;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Return setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Return setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Return{");
        sb.append(" result='").append(result).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
