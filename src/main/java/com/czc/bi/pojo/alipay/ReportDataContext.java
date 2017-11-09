package com.czc.bi.pojo.alipay;

import com.czc.bi.util.BaseUtil;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : 支付宝报表数据请求参数
 * @date : 2017/10/24.
 * @version: V1.0
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDataContext implements Serializable {

    private String report_uk;
    private List<Condition> conditions;

    public ReportDataContext() {
        this.conditions = new ArrayList<>();
    }

    public ReportDataContext addCondition(String key, String operate, String value) {
        if(this.conditions == null){
            this.conditions = new ArrayList<>(4);
        }
        this.conditions.add(new Condition(key,getOperate(operate),value));
        return this;
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private class Condition {
        private String key;
        private String operate;
        private String value;

        public Condition(String key, String operate, String value) {
            this.key = key;
            this.operate = operate;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getOperate() {
            return operate;
        }

        public void setOperate(String operate) {
            this.operate = operate;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }


    public String getReport_uk() {
        return report_uk;
    }

    public void setReport_uk(String report_uk) {
        this.report_uk = report_uk;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }


    private String getOperate(String operate) {
        String res;
        switch (operate) {
            case "=":
                res = "EQ";
                break;
            case ">":
                res = "GT";
                break;
            case "<":
                res = "LT";
                break;
            case "<=":
                res = "LTE";
                break;
            case ">=":
                res = "GTE";
                break;
            case "!=":
            case "<>":
                res = "NOT_EQ";
                break;
            case "like":
                res = "LIKE";
                break;
            case "not like":
                res = "NOT_LIKE";
                break;
            case "in":
                res = "IN";
                break;
            case "not in":
                res = "NOT_IN";
                break;
            default:
                res = "EQ";
                break;

        }

        return res;
    }
}
