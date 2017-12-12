package com.czc.bi.scheduling.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.czc.bi.mapper.ShopLabelAnalyzeMapper;
import com.czc.bi.pojo.ShopLabelAnalyze;
import com.czc.bi.pojo.alipay.ReportDataContext;
import com.czc.bi.scheduling.JobResult;
import com.czc.bi.scheduling.SyncJob;
import com.czc.bi.service.AlipayService;
import com.czc.bi.util.AlipayUtil;
import com.czc.bi.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.czc.bi.util.AlipayConstant.UK_REPORT_YFY_SHOP_PROPERTY;

/**
 * Created by Administrator on 2017/12/5.
 * 客户特征数据同步
 */
@Component
public class SyncShopPropertyData implements SyncJob{
    private Logger logger = Logger.getLogger(SyncCustDayFlowData.class.getName());

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private ShopLabelAnalyzeMapper shopLabelAnalyzeMapper;

    @Override
    public JobResult execute(String shopid, String token, String pdate) {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_PROPERTY);
        rc.addCondition("shop_id", "=",  shopid);
        rc.addCondition("month", "=", pdate);
        JobResult result = new JobResult();
        try {
            List<AlisisReportRow> reportData = alipayService.getReportData(rc, token);
            if (reportData == null) {
                logger.debug(String.format("店铺[%s]报表[%s]在日期[%s]无数据",
                        shopid,
                        "REPORT_YFY_SHOP_PROPERTY_FORTIMEPERIOD",
                        pdate)
                );

                result.setStatus("success").setRows(0);
                return result;
            }

            // analysis data
            List<ShopLabelAnalyze> list = new ArrayList<>();
            for (AlisisReportRow reportDatum : reportData) {
                ShopLabelAnalyze shopLabelAnalyze = new ShopLabelAnalyze();
                List<AlisisReportColumn> rowData = reportDatum.getRowData();
                Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                        "shop_id",
                        "month",
                        "indicator",
                        "category",
                        "usr_cnt",
                        "shop_name");
                shopLabelAnalyze.setAccount(columnValue.get("shop_id"));
                shopLabelAnalyze.setPdate(columnValue.get("month"));
                String type = columnValue.get("indicator");
                if ("age".equals(type)) {
                    shopLabelAnalyze.setType(Constants.AGE_TYPE_MONTH);
                } else if ("gender".equals(type)) {
                    shopLabelAnalyze.setType(Constants.GENDER_TYPE_MONTH);
                } else if ("constellation".equals(type)) {
                    shopLabelAnalyze.setType(Constants.CONSTELLATIONS_TYPE_MONTH);
                } else if ("occupation".equals(type)) {
                    shopLabelAnalyze.setType(Constants.OCCUPATION_TYPE_MONTH);
                } else if ("consume_level".equals(type)) {
                    shopLabelAnalyze.setType(Constants.CONSUME_LEVLE_MONTH);
                } else if ("have_baby".equals(type)) {
                    shopLabelAnalyze.setType(Constants.HAVECHILD_TYPE_MONTH);
                }
                shopLabelAnalyze.setKey(columnValue.get("category"));
                shopLabelAnalyze.setValue(columnValue.get("usr_cnt"));
                shopLabelAnalyze.setShop(columnValue.get("shop_name"));
                logger.debug(String.format("获取店铺客户特征数据[%s]", shopLabelAnalyze));
                list.add(shopLabelAnalyze);
            }

            // 执行数据插入
            int rows = shopLabelAnalyzeMapper.replaces(list);
            logger.debug(String.format("客户特征数据在日期[%s]获取完成", pdate));
            result.setStatus("success").setRows(rows);
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return result.setStatus("fail").setError(e.getErrCode(),e.getErrMsg());
        }
    }
}
