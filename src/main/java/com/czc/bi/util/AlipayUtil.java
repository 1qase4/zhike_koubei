package com.czc.bi.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportQueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportQueryResponse;
import com.czc.bi.pojo.alipay.ReportDataContext;

import java.util.*;
import java.util.stream.Collectors;

import static com.czc.bi.util.AlipayConstant.*;

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

    // 获取一行数据的字段数据
    public static Map<String, String> getColumnValue(List<AlisisReportColumn> rowData, String... names) {
        Map<String, String> map = Arrays.stream(names).collect(Collectors.toMap(a -> a, a -> ""));

        for (AlisisReportColumn rowDatum : rowData) {
            String alias = rowDatum.getAlias();
            if(map.containsKey(alias)){
                map.put(alias,rowDatum.getData());
            }
        }
        return map;
    }

    public static Map getKoubeiReport(ReportDataContext rc) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAT,
                APPID,
                PRIVATE_KEY,
                FORMAT,
                CHARSET,
                ALIPAY_PUBLIC_KEY,
                SIGN_TYPE);

        KoubeiMarketingDataAlisisReportQueryRequest kbrequest = new KoubeiMarketingDataAlisisReportQueryRequest();
        kbrequest.setBizContent(BaseUtil.jsonToString(rc));
        kbrequest.putOtherTextParam("app_auth_token", "201710BB587b6a2bf52a4795bba5e7eca40c1C55");
        KoubeiMarketingDataAlisisReportQueryResponse kbresponse = alipayClient.execute(kbrequest);
        Map<String, Object> map = new HashMap<String, Object>();
        if (kbresponse.isSuccess()) {
            List<AlisisReportRow> reportData = kbresponse.getReportData();
            map.put("status", 0);
            map.put("msg", "数据查询调用成功");
            map.put("data", reportData);
            return map;
        } else {
            map.put("status", 1);
            map.put("msg", "数据查询调用失败:" + kbresponse.getSubCode() + "," + kbresponse.getSubMsg());
            return map;
        }
    }


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("page", "10");
        map.put("end", "2017-06-10");
        System.out.println(buildBiz(map));
    }

    public static Map getKoubeiReportData(ReportDataContext rc,String token,AlipayClient alipayClient) throws AlipayApiException {
        KoubeiMarketingDataAlisisReportQueryRequest kbrequest = new KoubeiMarketingDataAlisisReportQueryRequest();
        kbrequest.setBizContent(BaseUtil.jsonToString(rc));
        kbrequest.putOtherTextParam("app_auth_token", token);
        KoubeiMarketingDataAlisisReportQueryResponse kbresponse = alipayClient.execute(kbrequest);
        Map<String, Object> map = new HashMap<String, Object>();
        if (kbresponse.isSuccess()) {
            List<AlisisReportRow> reportData = kbresponse.getReportData();
            map.put("status", 0);
            map.put("msg", "数据查询调用成功");
            map.put("data", reportData);
            return map;
        } else {
            map.put("status", 1);
            map.put("msg", "数据查询调用失败:" + kbresponse.getSubCode() + "," + kbresponse.getSubMsg());
            return map;
        }
    }
}
