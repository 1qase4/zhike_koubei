import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportQueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportQueryResponse;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.util.AlipayUtil;
import com.czc.bi.util.BaseUtil;

import java.util.List;

import static com.czc.bi.util.AlipayConstant.*;

/**
 * Created by Administrator on 2017/9/25.
 */
public class T3_1 {

    public static void main(String[] args) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAT,
                APPID,
                PRIVATE_KEY,
                FORMAT,
                CHARSET,
                ALIPAY_PUBLIC_KEY,
                SIGN_TYPE);

        alipayClient = AlipayUtil.getYFYClient();
        KoubeiMarketingDataAlisisReportQueryRequest kbrequest = new KoubeiMarketingDataAlisisReportQueryRequest();
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk("QK171101231co6td"); //"QK1711019f6d4557"

        rc.addCondition("shop_id", "=", "2015051400077000000000046605");
//        rc.addCondition("day","=","2017-11-04" );

     //   rc.addCondition("day", "=", "2017-11-07");
     //   rc.addCondition("month","=","2017-10" );

        // rc.addCondition("week", ">", "12");
        kbrequest.setBizContent(BaseUtil.jsonToString(rc));
        kbrequest.putOtherTextParam("app_auth_token", "201710BB587b6a2bf52a4795bba5e7eca40c1C55");
        KoubeiMarketingDataAlisisReportQueryResponse kbresponse = alipayClient.execute(kbrequest);
        if (kbresponse.isSuccess()) {
            System.out.println("数据查询调用成功");

            List<AlisisReportRow> reportData = kbresponse.getReportData();
            if (reportData == null){
                System.out.println("报表无数据");
            } else {
                for (AlisisReportRow reportDatum : reportData) {
                    List<AlisisReportColumn> rowData = reportDatum.getRowData();
                    for (AlisisReportColumn rowDatum : rowData) {
                        String alias = rowDatum.getAlias();
                        String data = rowDatum.getData();
                        System.out.print(String.format("%s[%s]", alias, data));
                    }
                    System.out.println("--------------------");
                }
            }

            System.out.println(reportData.size());
        } else {
            System.out.println("数据查询调用失败");
            System.out.println(kbresponse.getSubCode());
            System.out.println(kbresponse.getSubMsg());
        }

    }
}
