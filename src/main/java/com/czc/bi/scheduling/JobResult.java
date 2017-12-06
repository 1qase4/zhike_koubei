package com.czc.bi.scheduling;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/12/5.
 * @version: V1.0
 */
public class JobResult {

    // success   fail   ignore
    public String status;

    // table affected rows
    public int rows;

    // error info
    public String errorCode;

    public String errorMsg;

    public String getStatus() {
        return status;
    }

    public JobResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public int getRows() {
        return rows;
    }

    public JobResult setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public JobResult setError(String errorCode, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobResult{");
        sb.append("status='").append(status).append('\'');
        sb.append(", rows=").append(rows);
        sb.append(", errorCode='").append(errorCode).append('\'');
        sb.append(", errorMsg='").append(errorMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
