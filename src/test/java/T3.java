package com.czc.bi;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlisisReport;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportBatchqueryRequest;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportQueryRequest;
import com.alipay.api.request.KoubeiMarketingDataIndicatorQueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportBatchqueryResponse;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportQueryResponse;
import com.alipay.api.response.KoubeiMarketingDataIndicatorQueryResponse;
import com.czc.bi.util.AlipayUtil;


import java.util.HashMap;
import java.util.List;
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
                "\"size\":\"20\"" +
                " }");

        KoubeiMarketingDataAlisisReportBatchqueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            List<AlisisReport> reports = response.getReportList();
            for (AlisisReport report : reports) {
                String reportUk = report.getReportUk();
                String reportName = report.getReportName();
                String reportDesc = report.getReportDesc();
                System.out.println(String.format("reportUk[%s],reportName[%s],reportDesc[%s]",reportUk,reportName,reportDesc));

//                KoubeiMarketingDataAlisisReportQueryRequest kbrequest = new KoubeiMarketingDataAlisisReportQueryRequest();
//                kbrequest.setBizContent("{" +
//                        "\"report_uk\":\""+ reportUk +"\"" +
//                        " }");
//                kbrequest.putOtherTextParam("app_auth_token","201710BB587b6a2bf52a4795bba5e7eca40c1C55");
//                KoubeiMarketingDataAlisisReportQueryResponse kbresponse = alipayClient.execute(kbrequest);
//                if(kbresponse.isSuccess()){
//                    System.out.println("数据查询调用成功");
//                    List<AlisisReportRow> reportData = kbresponse.getReportData();
//                    for (AlisisReportRow reportDatum : reportData) {
//                        List<AlisisReportColumn> rowData = reportDatum.getRowData();
//                        for (AlisisReportColumn rowDatum : rowData) {
//                            String alias = rowDatum.getAlias();
//                            String data = rowDatum.getData();
//                            System.out.print(String.format("alias[%s],data[%s]",alias,data));
//                        }
//                        System.out.println("--------------------");
//                    }
//
//                }else {
//                    System.out.println("数据查询调用失败");
//                }
            }

        } else {
            System.out.println("调用失败");
        }
    }
}
