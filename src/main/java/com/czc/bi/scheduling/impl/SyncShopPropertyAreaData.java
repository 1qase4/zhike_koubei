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

import java.util.*;

import static com.czc.bi.util.AlipayConstant.UK_REPORT_YFY_SHOP_PROPERTY;
import static com.czc.bi.util.AlipayConstant.UK_REPORT_YFY_SHOP_PROPERTY_AREA_DIS;

/**
 * Created by Administrator on 2017/12/5.
 * 客户区域特征数据同步
 */
@Component
public class SyncShopPropertyAreaData implements SyncJob {
    private Logger logger = Logger.getLogger(SyncCustDayFlowData.class.getName());

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private ShopLabelAnalyzeMapper shopLabelAnalyzeMapper;

    @Override
    public JobResult execute(String shopid, String token, String pdate) {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_PROPERTY_AREA_DIS);
        rc.addCondition("shop_id", "=",  shopid);
        rc.addCondition("month", "=", pdate);
        JobResult result = new JobResult();
        try {
            List<AlisisReportRow> reportData = alipayService.getReportData(rc, token);
            if (reportData == null) {
                logger.debug(String.format("店铺[%s]报表[%s]在日期[%s]无数据",
                        shopid,
                        "REPORT_YFY_SHOP_PROPERTY_AREA_DIS_FORTIMEPERIOD",
                        pdate)
                );

                result.setStatus("success").setRows(0);
                return result;
            }

            // analysis data
            List<ShopLabelAnalyze> list = new ArrayList<>();
            Map<String, ShopLabelAnalyze> provinceMap = new HashMap<>(34);
            for (AlisisReportRow reportDatum : reportData) {
                ShopLabelAnalyze shopLabelAnalyze = new ShopLabelAnalyze();

                List<AlisisReportColumn> rowData = reportDatum.getRowData();
                Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                        "shop_id",
                        "month",
                        "indicator",
                        "province",
                        "usr_cnt",
                        "shop_name");
                shopLabelAnalyze.setAccount(columnValue.get("shop_id"));
                shopLabelAnalyze.setPdate(columnValue.get("month"));
                shopLabelAnalyze.setType(Constants.PROVINCE_TYPE_MONTH);
                if (Constants.ProvinceMap.containsKey(columnValue.get("province"))) {
                    shopLabelAnalyze.setKey(Constants.ProvinceMap.get(columnValue.get("province")));
                } else {
                    continue;
                }
                shopLabelAnalyze.setShop(columnValue.get("shop_name"));
                String province = columnValue.get("province");
                Integer total = 0;
                if (provinceMap.containsKey(province)) {
                    ShopLabelAnalyze labelAnalyze = provinceMap.get(province);
                    total = Integer.parseInt(labelAnalyze.getValue());
                }
                total += Integer.parseInt(columnValue.get("usr_cnt"));
                shopLabelAnalyze.setValue(String.valueOf(total));
                provinceMap.put(province, shopLabelAnalyze);
            }
            Collection<ShopLabelAnalyze> values = provinceMap.values();
            Iterator<ShopLabelAnalyze> it = values.iterator();
            while (it.hasNext()) {
                ShopLabelAnalyze next = it.next();
                logger.debug(String.format("获取店铺客户区域特征数据[%s]", next));
                list.add(next);
            }

            // 执行数据插入
            int rows = shopLabelAnalyzeMapper.replaces(list);
            logger.debug(String.format("客户区域特征数据在日期[%s]获取完成", pdate));
            result.setStatus("success").setRows(rows);
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            result.setStatus("fail").setError(e.getErrCode(),e.getErrMsg());
        }
        return null;
    }
}
