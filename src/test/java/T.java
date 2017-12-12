
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlisisReport;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportBatchqueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportBatchqueryResponse;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.util.BaseUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
public class T {
    private static final String APPID = "2016101202120719";
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAotc1C6bPPN0ScPAZmGN84N3rgZRS2X5ThhX/aKyWUB47tgnTk4dK978XdMV7Ye1LOhO8fTie7TwY4+7eM1Y0U6RWqe3j0wlKb/Ka/PBR1XX9nbSLzgEa/HWC9tjoOWHwInMpDzNrMs/QAYyrFlI/WkB3s+esOiDX8mCCGES27CQEm98V3KHN6P/2izsJ6wqyahGShSdhlAEvmg7ZSDUs+mrWvEJZOrQcpWMwWYnczwsqaLich76NZ9CJV2m9a/GF0J06xWPpA0dDvc6k/M/sgYZDEz8Lq64IjAcqk0Xwv3tqZ1aUnR2lLsyVAAZv5FWuGYH9Zg6NoJp2JHCLhbTCNQIDAQAB";
    private static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCX3bzjcN1jYzBjM0LYztcmJ2OD0v+StCzqpdI3TusAFe/EO7d65j4/ba22TCZOi+DOBbs3tCXlRxt1Un6UzKICQBYCZUSfMoaa9IUdXm6E8q/bgyvbWKcb+MvsmbqhbSFyPliUiwsAE8xB/a2h9OJtOCOESTJhHG8Qvv3YqjxiajgV2HWQcGiVMJJgVqcH9m37iNovj97L6Ti5B/CG12xetYWK5I3uKgkdNk83mwdAUUFX2e8q2hyD8bJ7w92dfa2trAdSY8YY1yVmOH5vRqy6/69igYsWZitOwwQn8H1Sm77PszYbISBwX4CVUu/3aydkCUo08Oz2NPYTAiCgj8WxAgMBAAECggEARsRJ/8YZM/YFl0RM0xDXiuzx33zUIT2abKFmquU4dqrCNrQJFNjzisUGHJuxggqGcBqVmih1PDj9X5dYauhMWjYYy3b7GiAGP3DQEtZtM8CPGAAw0J6oCE3QYOll4VEkM8M/rcB5GMkg9mNKpNyjInf1fPBK7Ju+WZRWX788MjNJ4Lk3s/RV/Kls555I359oe01YWOu/VhH0plQMTgf24Y7vmpTRxMKMPMTHmrOkiSj2BqFaGTQYufC1hVyuDGdy/u2CelXCSfsbX5znd8RrKKV79OXSQbBWrVuCsgoeh5jlrYYmcEjjmG7BzNwPprAXftGv5n8XfzMiVRL7QMg2jQKBgQDekJPNOJ56AmKRJJbIB6KMpYLF4awcHR/i/KyvI6Crq6DAGHkrPNWNdLjh37GE6GBdofe5AccWJYoUxuDVaZmTq8fpx/7ptuCsTIDC7NoDOsnlVOZlkA6sb8AGjFI7BA3pMV94eCl5toZ2B/pgxCe5IIUCzuvvQaVgEiFy7KaCtwKBgQCurjmyorYCcuv34uQbAW6Ld0Ea4ek5TucFPhLBng7vfvNUy6qyghUTfeWuD/gzu2OyncWR8DABKax8EO1ueseQPhdwfSwlgeGBQDV22W4BBBLHKGyauk0hqGWLv8ZBsFwuZ51Q9ZWzPRn/OvQL1jEyXrNKpB5oUp7HhLfvjRHy1wKBgQCnZX6FQCyR//E60ZZG5NcxidsOEmpsyssM4pbPM7DI7dF+KeoYxyyntD7KRIE0QcMZKZU0pU2kLp/oeJzqZv6HvxAhmbb0+gOKFBBUi+h9vV3VC0Iu6Wbf8z0ur6O5l5cO6X+xEE/Bp7MCW4XJ+j8WUos4TYHO9kT4OSrHUY77iQKBgGlqXwt8whZMQDcCR+I8RPgJadLNTq/0+9ngEfWiayUBGdusZUEA27M9oOQgYwrlJU9fgyGFL5MkESF2HbM0iUZ1EjQidgfUj8AufZVRtd8LFEH4FUJy96U+OLnTa9OsmJSwlvGKo2Ldlti+PRT/mATrvYtn66nNYmDJqS75lUFVAoGACxZV3BBfPAIZkxv+q64omqo4lp72ytp4dfuCoSBIE51MCnFUxzGYQZfwD9C8fw0Ak6LJn2yDtfxefCdJ1JEGB2W0FrOZz4uHzwb59kZLvrB6g6AdXEc9iasJ8MPcoQYn3iVhYGZBJfpoCZ2z8wK9oO+cZfOKKbQ9YykTBDafCmk=";


    public static void main(String[] args) {
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",
                APPID,
                PRIVATE_KEY,
                "json",
                "GBK",
                ALIPAY_PUBLIC_KEY,
                "RSA2");
        KoubeiMarketingDataAlisisReportBatchqueryRequest request = new
                KoubeiMarketingDataAlisisReportBatchqueryRequest();
        request.setBizContent("{" +
                "\"page\":\"1\"," +
                "\"size\":\"10\"" +
                " }");
        KoubeiMarketingDataAlisisReportBatchqueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
            List<AlisisReport> reportList = response.getReportList();
            System.out.println(response.getBody());

            for (AlisisReport alisisReport : reportList) {
                System.out.println(alisisReport);
            }
        } else {
            System.out.println("调用失败");
        }



    }
}
