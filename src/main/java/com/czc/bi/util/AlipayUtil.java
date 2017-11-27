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

    /**
     * build special client for yfy shop
     * @return yfy alipay client
     */
    public static AlipayClient getYFYClient(){
        String gatewat = "https://openapi.alipay.com/gateway.do";
        String appid = "2016101202120719";
        String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCX3bzjcN1jYzBjM0LYztcmJ2OD0v+StCzqpdI3TusAFe/EO7d65j4/ba22TCZOi+DOBbs3tCXlRxt1Un6UzKICQBYCZUSfMoaa9IUdXm6E8q/bgyvbWKcb+MvsmbqhbSFyPliUiwsAE8xB/a2h9OJtOCOESTJhHG8Qvv3YqjxiajgV2HWQcGiVMJJgVqcH9m37iNovj97L6Ti5B/CG12xetYWK5I3uKgkdNk83mwdAUUFX2e8q2hyD8bJ7w92dfa2trAdSY8YY1yVmOH5vRqy6/69igYsWZitOwwQn8H1Sm77PszYbISBwX4CVUu/3aydkCUo08Oz2NPYTAiCgj8WxAgMBAAECggEARsRJ/8YZM/YFl0RM0xDXiuzx33zUIT2abKFmquU4dqrCNrQJFNjzisUGHJuxggqGcBqVmih1PDj9X5dYauhMWjYYy3b7GiAGP3DQEtZtM8CPGAAw0J6oCE3QYOll4VEkM8M/rcB5GMkg9mNKpNyjInf1fPBK7Ju+WZRWX788MjNJ4Lk3s/RV/Kls555I359oe01YWOu/VhH0plQMTgf24Y7vmpTRxMKMPMTHmrOkiSj2BqFaGTQYufC1hVyuDGdy/u2CelXCSfsbX5znd8RrKKV79OXSQbBWrVuCsgoeh5jlrYYmcEjjmG7BzNwPprAXftGv5n8XfzMiVRL7QMg2jQKBgQDekJPNOJ56AmKRJJbIB6KMpYLF4awcHR/i/KyvI6Crq6DAGHkrPNWNdLjh37GE6GBdofe5AccWJYoUxuDVaZmTq8fpx/7ptuCsTIDC7NoDOsnlVOZlkA6sb8AGjFI7BA3pMV94eCl5toZ2B/pgxCe5IIUCzuvvQaVgEiFy7KaCtwKBgQCurjmyorYCcuv34uQbAW6Ld0Ea4ek5TucFPhLBng7vfvNUy6qyghUTfeWuD/gzu2OyncWR8DABKax8EO1ueseQPhdwfSwlgeGBQDV22W4BBBLHKGyauk0hqGWLv8ZBsFwuZ51Q9ZWzPRn/OvQL1jEyXrNKpB5oUp7HhLfvjRHy1wKBgQCnZX6FQCyR//E60ZZG5NcxidsOEmpsyssM4pbPM7DI7dF+KeoYxyyntD7KRIE0QcMZKZU0pU2kLp/oeJzqZv6HvxAhmbb0+gOKFBBUi+h9vV3VC0Iu6Wbf8z0ur6O5l5cO6X+xEE/Bp7MCW4XJ+j8WUos4TYHO9kT4OSrHUY77iQKBgGlqXwt8whZMQDcCR+I8RPgJadLNTq/0+9ngEfWiayUBGdusZUEA27M9oOQgYwrlJU9fgyGFL5MkESF2HbM0iUZ1EjQidgfUj8AufZVRtd8LFEH4FUJy96U+OLnTa9OsmJSwlvGKo2Ldlti+PRT/mATrvYtn66nNYmDJqS75lUFVAoGACxZV3BBfPAIZkxv+q64omqo4lp72ytp4dfuCoSBIE51MCnFUxzGYQZfwD9C8fw0Ak6LJn2yDtfxefCdJ1JEGB2W0FrOZz4uHzwb59kZLvrB6g6AdXEc9iasJ8MPcoQYn3iVhYGZBJfpoCZ2z8wK9oO+cZfOKKbQ9YykTBDafCmk=";
        String format = "json";
        String charset = "GBK";
        String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAotc1C6bPPN0ScPAZmGN84N3rgZRS2X5ThhX/aKyWUB47tgnTk4dK978XdMV7Ye1LOhO8fTie7TwY4+7eM1Y0U6RWqe3j0wlKb/Ka/PBR1XX9nbSLzgEa/HWC9tjoOWHwInMpDzNrMs/QAYyrFlI/WkB3s+esOiDX8mCCGES27CQEm98V3KHN6P/2izsJ6wqyahGShSdhlAEvmg7ZSDUs+mrWvEJZOrQcpWMwWYnczwsqaLich76NZ9CJV2m9a/GF0J06xWPpA0dDvc6k/M/sgYZDEz8Lq64IjAcqk0Xwv3tqZ1aUnR2lLsyVAAZv5FWuGYH9Zg6NoJp2JHCLhbTCNQIDAQAB";
        String sign_type = "RSA2";

        AlipayClient alipayClient = new DefaultAlipayClient(
                gatewat,
                appid,
                private_key,
                format,
                charset,
                alipay_public_key,
                sign_type);

        return alipayClient;
    }

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
