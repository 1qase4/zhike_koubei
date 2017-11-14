package com.czc.bi;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.ReportDataItem;
import com.alipay.api.request.KoubeiMarketingDataCustomreportQueryRequest;
import com.alipay.api.response.KoubeiMarketingDataCustomreportQueryResponse;

import java.util.List;

import static com.czc.bi.util.AlipayConstant.*;

/**
 * Created by Administrator on 2017/9/25.
 */
public class T4 {

    public static void main(String[] args) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAT,
                PPID,
                PRIVATE_KEY,
                FORMAT,
                CHARSET,
                ALIPAY_PUBLIC_KEY,
                SIGN_TYPE);

        KoubeiMarketingDataCustomreportQueryRequest request = new KoubeiMarketingDataCustomreportQueryRequest();
        request.setBizContent("{" +
                "\"condition_key\":\"200000081\"," +
                "      \"filter_tags\":[{" +
                "        \"code\":\"orpt_ubase_age\"," +
                "\"filter_items\":\"[{\\\"operate\\\": \\\"EQ\\\",\\\"value\\\": \\\"1\\\" }]\"" +
                "        }]," +
                "\"max_count\":\"50\"" +
                "  }");
        KoubeiMarketingDataCustomreportQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("success");
            List<ReportDataItem> reportData = response.getReportData();
            for (ReportDataItem reportDatum : reportData) {
                System.out.println(reportDatum);
            }
        }else {
            System.out.println("error");
            System.out.println(response.getSubCode());
            System.out.println(response.getSubMsg());
        }

    }
}
