package com.czc.bi;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlisisReport;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportBatchqueryRequest;
import com.alipay.api.request.KoubeiMarketingDataIndicatorQueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportBatchqueryResponse;
import com.alipay.api.response.KoubeiMarketingDataIndicatorQueryResponse;
import com.czc.bi.util.AlipayUtil;


import java.util.HashMap;
import java.util.Map;

import static com.czc.bi.util.Constant.*;

/**
 * Created by Administrator on 2017/9/25.
 */
public class T3 {

    public static void main(String[] args) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAT,
                PPID,
                PRIVATE_KEY,
                FORMAT,
                CHARSET,
                ALIPAY_PUBLIC_KEY,
                SIGN_TYPE);


        KoubeiMarketingDataAlisisReportBatchqueryRequest request = new KoubeiMarketingDataAlisisReportBatchqueryRequest();

        request.setBizContent("{" +
                "\"page\":\"1\"," +
                "\"size\":\"10\"" +
                " }");

        KoubeiMarketingDataAlisisReportBatchqueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            System.out.println(response.getReportList());
        } else {
            System.out.println("调用失败");
        }
    }
}
