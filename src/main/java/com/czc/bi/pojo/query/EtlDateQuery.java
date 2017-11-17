package com.czc.bi.pojo.query;

import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[etl_date]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class EtlDateQuery extends BaseQuery {

    // 账号
    private String account;

    // 账号 操符号
    private String accountOperator;

    // 日期
    private String pdate;

    // 日期 操符号
    private String pdateOperator;


    // get set 方法
    public String getAccount() {
        return this.account;
    }

    public EtlDateQuery setAccount(String account) {
        this.account = account;
        this.accountOperator = "=";
        return this;
    }

    public EtlDateQuery setAccount(String account, String operator) {
        this.account = account;
        this.accountOperator = operator;
        return this;
    }

    public String getAccountOperator() {
        return this.accountOperator;
    }

    public String getPdate() {
        return this.pdate;
    }

    public EtlDateQuery setPdate(String pdate) {
        this.pdate = pdate;
        this.pdateOperator = "=";
        return this;
    }

    public EtlDateQuery setPdate(String pdate, String operator) {
        this.pdate = pdate;
        this.pdateOperator = operator;
        return this;
    }

    public String getPdateOperator() {
        return this.pdateOperator;
    }

}
