import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.util.BaseUtil;

import java.math.BigDecimal;
import java.util.Random;


public class Test2 {

    public static void main(String[] args) {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk("123454");
        rc.addCondition("id",">","15");
        String string = BaseUtil.jsonToString(rc);
        System.out.println(string);
    }
}
