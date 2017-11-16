package com.czc.bi.util;

import java.io.UnsupportedEncodingException;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :
 * @date : 2017/11/16.
 * @version: V1.0
 */
public class MessageUtil {
    // 管理员手机号
    public static String PHONES = "15527771627,15623851882";
    private final static String STAR = "800491";
    private final static String PW = "dkenvig";


    public static void sendMessageAdmin(String message) throws Exception {
        sendMessage(PHONES,message);
    }

    public static void sendMessage(String phones, String message) throws Exception {
        message = "【知客|口碑】"+message;
        // 检查失败调用短信程序发送短信
        String urlName = "http://222.73.44.38:8080/mt?un=" + STAR
                + "&pw=" + PW
                + "&da=" + phones
                + "&sm=" + StringToHex(message)
                + "&dc=15&rd=1";
        HttpUtil.sendGet(urlName);
    }

    public static void main(String[] args) throws Exception {
        sendMessageAdmin("测试一下");
    }

    private static String StringToHex(String s) throws UnsupportedEncodingException {
        byte[] b = s.getBytes("gbk");
        String a = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            a = a + hex;
        }
        return a;
    }
}
