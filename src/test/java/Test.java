import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlisisReport;
import com.alipay.api.request.KoubeiMarketingDataAlisisReportBatchqueryRequest;
import com.alipay.api.response.KoubeiMarketingDataAlisisReportBatchqueryResponse;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.util.BaseUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/9/25.
 */
public class Test {
    /**
     * @param MinLon：最新经度 MaxLon： 最大经度   MinLat：最新纬度   MaxLat：最大纬度    type：设置返回经度还是纬度
     * @return
     * @throws
     * @Title: randomLonLat
     * @Description: 在矩形内随机生成经纬度
     */
    public String randomLonLat(double MinLon, double MaxLon, double MinLat, double MaxLat, String type) {
        Random random = new Random();
        BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
        String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
        db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
        String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
//        if (type.equals("Lon")) {
//            return lon;
//        } else {
//            return lat;
//        }
        String format = String.format("insert into `shop_label_analyze` (`type`, `account`, `shop`, `key`, `value`, `pdate`, `createdt`, `updatedt`) values('热力坐标','zhike',NULL,'%s','%s','2017-09','2017-08-14 16:24:02','2017-08-14 16:24:02');",
                lon, lat);
        System.out.println(format);
        return null;
    }


    private static int getPossionVariable(double lamda) {
        int x = 0;
        double y = Math.random(), cdf = getPossionProbability(x, lamda);
        while (cdf < y) {
            x++;
            cdf += getPossionProbability(x, lamda);
        }
        return x;
    }

    private static double getPossionProbability(int k, double lamda) {
        double c = Math.exp(-lamda), sum = 1;
        for (int i = 1; i <= k; i++) {
            sum *= lamda / i;
        }
        return sum * c;
    }


    public static void main(String[] args) {
        String _month = "201765";
        _month = _month.length() == 6?_month.substring(0,4) + "-" + _month.substring(4):_month;
        System.out.println(_month);

    }
}
