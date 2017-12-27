package com.czc.bi.pojo.query;

import java.util.Date;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc   : 数据库表[cfg_user]的PO对象
 * @date   : 2016/8/22.
 * @version: V1.0
 */

public class CfgUserQuery extends BaseQuery {

    // 
    private String username;

    //  操符号
    private String usernameOperator;

    // 
    private String password;

    //  操符号
    private String passwordOperator;

    // 
    private String nickname;

    //  操符号
    private String nicknameOperator;

    // 
    private String status;

    //  操符号
    private String statusOperator;


    // get set 方法
    public String getUsername() {
        return this.username;
    }

    public CfgUserQuery setUsername(String username) {
        this.username = username;
        this.usernameOperator = "=";
        return this;
    }

    public CfgUserQuery setUsername(String username, String operator) {
        this.username = username;
        this.usernameOperator = operator;
        return this;
    }

    public String getUsernameOperator() {
        return this.usernameOperator;
    }

    public String getPassword() {
        return this.password;
    }

    public CfgUserQuery setPassword(String password) {
        this.password = password;
        this.passwordOperator = "=";
        return this;
    }

    public CfgUserQuery setPassword(String password, String operator) {
        this.password = password;
        this.passwordOperator = operator;
        return this;
    }

    public String getPasswordOperator() {
        return this.passwordOperator;
    }

    public String getNickname() {
        return this.nickname;
    }

    public CfgUserQuery setNickname(String nickname) {
        this.nickname = nickname;
        this.nicknameOperator = "=";
        return this;
    }

    public CfgUserQuery setNickname(String nickname, String operator) {
        this.nickname = nickname;
        this.nicknameOperator = operator;
        return this;
    }

    public String getNicknameOperator() {
        return this.nicknameOperator;
    }

    public String getStatus() {
        return this.status;
    }

    public CfgUserQuery setStatus(String status) {
        this.status = status;
        this.statusOperator = "=";
        return this;
    }

    public CfgUserQuery setStatus(String status, String operator) {
        this.status = status;
        this.statusOperator = operator;
        return this;
    }

    public String getStatusOperator() {
        return this.statusOperator;
    }

}
