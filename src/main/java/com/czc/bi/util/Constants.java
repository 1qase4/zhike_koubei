package com.czc.bi.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    private Constants(){};

    // 按天统计客流
    public static final String CUSTFLOW_TYPE_DAY = "按天统计客流";

    // 按小时统计客流
    public static final String CUSTFLOW_TYPE_HOUR = "按小时统计客流";

    // 按月统计月均客流
    public static final String CUSTFLOW_AVG_TYPE_MONTH = "按月统计月均客流";

    // 按月统计客流
    public static final String CUSTFLOW_TYPE_MONTH = "按月统计客流";

    // 按天统计新用户
    public static final String NEWCUST_TYPE_DAY = "按天统计新用户";

    // 按天统计老用户
    public static final String OLDCUST_TYPE_DAY = "按天统计老用户";

    // 流失客户
    public static final String LOST_TYPE = "按天统计流失客户";

    // 回流客户
    public static final String BACK_FLOW_TYPE = "按天统计回流客户";

    // 回头客
    public static final String RETURN_TYPE = "按天统计回头客";

    // 常客
    public static final String FREQUENT_TYPE = "按天统计常客";

    // 客户特征
    // 按月统计性别
    public static final String GENDER_TYPE_MONTH = "按月统计性别";

    // 按月统计年龄段
    public static final String AGE_TYPE_MONTH = "按月统计年龄段";

    // 按月统计职业
    public static final String OCCUPATION_TYPE_MONTH = "按月统计职业";

    // 按月统计小孩拥有
    public static final String HAVECHILD_TYPE_MONTH = "按月统计小孩拥有";

    // 按月统计星座
    public static final String CONSTELLATIONS_TYPE_MONTH = "按月统计星座";

    // 按月统计家庭状态
    public static final String FAMILY_TYPE_MONTH = "按月统计家庭状态";

    // 地区分布 - 按省统计客流
    public static final String PROVINCE_TYPE_MONTH = "按月统计各省客流";

    // 热力坐标
    public static final String ELEVATION_TYPE = "热力坐标";

    // 按月统计消费群
    public static final String CONSUME_LEVLE_MONTH = "按月统计消费群";

    public static Map<String,String> ProvinceMap = new HashMap<String ,String>(34);

    static {
        ProvinceMap.put("上海市","上海");
        ProvinceMap.put("云南省","云南");
        ProvinceMap.put("内蒙古自治区","内蒙古");
        ProvinceMap.put("北京市","北京");
        ProvinceMap.put("台湾省","台湾");
        ProvinceMap.put("吉林省","吉林");
        ProvinceMap.put("四川省","四川");
        ProvinceMap.put("天津市","天津");
        ProvinceMap.put("宁夏回族自治区","宁夏");
        ProvinceMap.put("安徽省","安徽");
        ProvinceMap.put("山东省","山东");
        ProvinceMap.put("山西省","山西");
        ProvinceMap.put("广东省","广东");
        ProvinceMap.put("广西壮族自治区","广西");
        ProvinceMap.put("新疆维吾尔自治区","新疆");
        ProvinceMap.put("江苏省","江苏");
        ProvinceMap.put("江西省","江西");
        ProvinceMap.put("河北省","河北");
        ProvinceMap.put("河南省","河南");
        ProvinceMap.put("浙江省","浙江");
        ProvinceMap.put("海南省","海南");
        ProvinceMap.put("湖北省","湖北");
        ProvinceMap.put("湖南省","湖南");
        ProvinceMap.put("澳门","澳门");
        ProvinceMap.put("甘肃省","甘肃");
        ProvinceMap.put("福建省","福建");
        ProvinceMap.put("西藏自治区","西藏");
        ProvinceMap.put("贵州省","贵州");
        ProvinceMap.put("辽宁省","辽宁");
        ProvinceMap.put("重庆市","重庆");
        ProvinceMap.put("陕西省","陕西");
        ProvinceMap.put("青海省","青海");
        ProvinceMap.put("香港","香港");
        ProvinceMap.put("黑龙江省","黑龙江");
    }

}
