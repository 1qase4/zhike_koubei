import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlisisReport;
import com.alipay.api.domain.ReportCondition;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportBatchqueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportBatchqueryResponse;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.util.BaseUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static com.czc.bi.util.AlipayConstant.*;


public class Test2 {


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
                "\"size\":\"30\"" +
                " }");

        KoubeiMarketingDataAlisisReportBatchqueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
            List<AlisisReport> reports = response.getReportList();
            for (AlisisReport report : reports) {
                String reportUk = report.getReportUk();
                String reportName = report.getReportName();
                String reportDesc = report.getReportDesc();
                System.out.println(String.format("reportUk[%s],reportName[%s],reportDesc[%s]", reportUk, reportName, reportDesc));
                List<ReportCondition> conditions = report.getConditions();
                if(conditions==null){
                    continue;
                }
                for (ReportCondition condition : conditions) {
                    System.out.println(String.format("  key[%s]condition[%s]",
                            condition.getKey(),
                            condition.getOperate()));
                }
                System.out.println("*********************");
            }

        } else {
            System.out.println("调用失败");
        }
    }
}
