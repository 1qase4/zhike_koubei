package com.czc.bi.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : 供支付宝/口碑接口使用
 * @date : 2017/9/24.
 * @version: V1.0
 */
public class AlipayUtil {

    // 构建请求的参数
    public static String buildBiz(Map<String, String> param) {
        StringJoiner sj = new StringJoiner(",");
        for (Map.Entry<String, String> entry : param.entrySet()) {
            sj.add("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"");
        }
        return "{" + sj.toString() + "}";
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("page", "10");
        map.put("end", "2017-06-10");
        System.out.println(buildBiz(map));
    }
}
