package com.czc.bi;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.KoubeiMarketingDataCustomreportBatchqueryRequest;
import com.alipay.api.response.KoubeiMarketingDataCustomreportBatchqueryResponse;

import static com.czc.bi.util.Constant.*;

/**
 * Created by Administrator on 2017/9/25.
 */
public class T2 {

    public static void main(String[] args) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAT,
                PPID,
                PRIVATE_KEY,
                FORMAT,
                CHARSET,
                ALIPAY_PUBLIC_KEY,
                SIGN_TYPE);

        KoubeiMarketingDataCustomreportBatchqueryRequest request = new KoubeiMarketingDataCustomreportBatchqueryRequest();

        request.setBizContent("{" +
                "\"page_no\":\"1\"," +
                "\"page_size\":\"20\"" +
                "  }");

        KoubeiMarketingDataCustomreportBatchqueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            System.out.println(response.getReportConditionList());
        } else {
            System.out.println("调用失败");
            System.out.println(response.getSubCode());
            System.out.println(response.getSubMsg());
        }
    }
}