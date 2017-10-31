import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlisisReport;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportBatchqueryRequest;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportQueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportBatchqueryResponse;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportQueryResponse;
import com.czc.bi.pojo.ShopPassengerflowAnalyze;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.util.BaseUtil;

import java.util.List;

import static com.czc.bi.util.Constant.*;

/**
 * Created by Administrator on 2017/9/25.
 */
public class T3_1 {

//    public void syncData(String shopId, String uk, String pdate) throws AlipayApiException {
//        AlipayClient alipayClient = new DefaultAlipayClient(
//                GATEWAT,
//                PPID,
//                PRIVATE_KEY,
//                FORMAT,
//                CHARSET,
//                ALIPAY_PUBLIC_KEY,
//                SIGN_TYPE);
//
//        ShopPassengerflowAnalyze analyze;
//
//        // 获取天客流量
//        KoubeiMarketingDataAlisisReportQueryRequest kbrequest = new KoubeiMarketingDataAlisisReportQueryRequest();
//        ReportDataContext rc = new ReportDataContext();
//        rc.setReport_uk(uk);
//        rc.addCondition("shop_id", "=", shopId);
//        rc.addCondition("day", "=", pdate);
//        //rc.addCondition("week", ">", "12");
//        kbrequest.setBizContent(BaseUtil.jsonToString(rc));
//        kbrequest.putOtherTextParam("app_auth_token", "201710BB587b6a2bf52a4795bba5e7eca40c1C55");
//        KoubeiMarketingDataAlisisReportQueryResponse kbresponse = alipayClient.execute(kbrequest);
//        if (kbresponse.isSuccess()) {
//            System.out.println("数据查询调用成功");
//
//            List<AlisisReportRow> reportData = kbresponse.getReportData();
//            if (reportData == null) {
//                System.out.println("报表无数据");
//            } else {
//                analyze = new ShopPassengerflowAnalyze();
//                analyze.setType("按天统计客流")
//                        .setRank(1)
//                        .setLabel(pdate)
//                        .setPdate(pdate);
//                for (AlisisReportRow reportDatum : reportData) {
//                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
//                    for (AlisisReportColumn rowDatum : rowData) {
//                        String alias = rowDatum.getAlias();
//                        switch (alias) {
//                            case "shop_id":
//                                analyze.setAccount(rowDatum.getData());
//                                break;
//                            case "traffic":
//                                analyze.setValue(Integer.valueOf(rowDatum.getData()));
//                                break;
//                            case "shop_name":
//                                analyze.setShop(rowDatum.getData());
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                    System.out.println(analyze);
//                    System.out.println("--------------------");
//                }
//            }
//        } else {
//            System.out.println("数据查询调用失败");
//            System.out.println(kbresponse.getSubCode());
//            System.out.println(kbresponse.getSubMsg());
//        }
//    }


    public void syncData2(String shopId, String uk, String pdate) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAT,
                PPID,
                PRIVATE_KEY,
                FORMAT,
                CHARSET,
                ALIPAY_PUBLIC_KEY,
                SIGN_TYPE);

        ShopPassengerflowAnalyze analyze;

        // 获取天客流量
        KoubeiMarketingDataAlisisReportQueryRequest kbrequest = new KoubeiMarketingDataAlisisReportQueryRequest();
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk("QK171024w055awh3");
        rc.addCondition("shop_id", "=", shopId);
        rc.addCondition("day", "=", pdate);
        //rc.addCondition("week", ">", "12");
        kbrequest.setBizContent(BaseUtil.jsonToString(rc));
        kbrequest.putOtherTextParam("app_auth_token", "201710BB587b6a2bf52a4795bba5e7eca40c1C55");
        KoubeiMarketingDataAlisisReportQueryResponse kbresponse = alipayClient.execute(kbrequest);
        if (kbresponse.isSuccess()) {
            System.out.println("数据查询调用成功");

            List<AlisisReportRow> reportData = kbresponse.getReportData();
            if (reportData == null) {
                System.out.println("报表无数据");
            } else {
                for (AlisisReportRow reportDatum : reportData) {
                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
                    for (AlisisReportColumn rowDatum : rowData) {
                        System.out.print(rowDatum.getAlias() + ":" + rowDatum.getData() + " ");
                    }
                    System.out.println("");
                }
            }
        } else {
            System.out.println("数据查询调用失败");
            System.out.println(kbresponse.getSubCode());
            System.out.println(kbresponse.getSubMsg());
        }
    }





    public static void main(String[] args) throws Exception {
        T3_1 t = new T3_1();
        t.syncData2("2015060200077000000000121608", "QK171024vnw9va74", "2017-10-21");
    }
}
