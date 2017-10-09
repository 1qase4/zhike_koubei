package com.czc.bi.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 返回信息封装
 * @date   : 2017/7/22.
 * @version: V1.0
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    // 返回状态  true  正常  false 异常
    private boolean result;
    private String message;
    private T data;

    public Result(){}

    // 构建正常的返回对象
    public Result(T data){
        this.data = data;
        this.result = true;
    }

    public boolean isResult() {
        return result;
    }

    public Result<T> setResult(boolean result) {
        this.result = result;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
