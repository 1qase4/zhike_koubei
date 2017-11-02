package com.czc.bi.util;

public class Constants {

    private Constants(){};

    // 按天统计客流
    public static final String CUSTFLOW_TYPE_DAY = "按天统计客流";

    // 按小时统计客流
    public static final String CUSTFLOW_TYPE_HOUR = "按小时统计客流";

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

}
