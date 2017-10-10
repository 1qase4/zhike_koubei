package com.czc.bi.pojo.dto;

public class UserDto {

    private String account;
    private String name;

    public UserDto() {
    }

    public UserDto(String account, String name) {
        this.account = account;
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
