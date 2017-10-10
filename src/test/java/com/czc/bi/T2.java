package com.czc.bi;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportBatchqueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportBatchqueryResponse;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/25.
 */
public class T2 {

    public static void main(String[] args) throws InterruptedException {
        String today = "2017-01-02";
        String loopDay = "2017-01-02";

        if(today.compareTo(loopDay) <= 0){
            System.out.println("*************");
        }
    }
}
