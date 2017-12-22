package com.czc.bi.scheduling.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlisisReportColumn;
import com.alipay.api.domain.AlisisReportRow;
import com.czc.bi.mapper.ShopLabelAnalyzeMapper;
import com.czc.bi.mapper.ShopMapper;
import com.czc.bi.pojo.Shop;
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

import static com.czc.bi.util.AlipayConstant.UK_REPORT_YFY_SHOP_HOT_DIAGRAM;

/**
 * Created by Administrator on 2017/12/5.
 * 周边热力图数据同步
 */
@Component
public class SyncShopHotDiagramData implements SyncJob {
    private Logger logger = Logger.getLogger(SyncCustDayFlowData.class.getName());

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private ShopLabelAnalyzeMapper shopLabelAnalyzeMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public JobResult execute(String shopid, String token, String pdate) {
        ReportDataContext rc = new ReportDataContext();
        rc.setReport_uk(UK_REPORT_YFY_SHOP_HOT_DIAGRAM);  //QK171101ozq154g7
        // 尔华那边month处理不规范,先使用in转换处理
        rc.addCondition("month", "in", String.format("%s,%s", pdate, pdate.replace("-", "")));
        rc.addCondition("shop_id", "=", shopid);
        JobResult result = new JobResult();
        try {
            List<AlisisReportRow> reportData = alipayService.getReportData(rc, token);
            if (reportData == null) {
                logger.debug(String.format("店铺[%s]报表[%s]在日期[%s]无数据",
                        shopid,
                        "REPORT_YFY_SHOP_HOT_DIAGRAM_FORTIMEPERIOD",
                        pdate)
                );

                result.setStatus("success").setRows(0);
                return result;
            }

            // analysis data
            List<ShopLabelAnalyze> list = new ArrayList<>();
            Map<String, ShopLabelAnalyze> gisMap = new HashMap<>();
            Boolean flag = true;
            for (AlisisReportRow reportDatum : reportData) {
                ShopLabelAnalyze shopLabelAnalyze = new ShopLabelAnalyze();
                List<AlisisReportColumn> rowData = reportDatum.getRowData();
                Map<String, String> columnValue = AlipayUtil.getColumnValue(rowData,
                        "shop_id",
                        "month",
                        "longitude",
                        "latitude",
                        "lng",
                        "lat",
                        "user_cnt",
                        "shop_name");
                shopLabelAnalyze.setAccount(columnValue.get("shop_id"));
                String _month = columnValue.get("month");
                // if month format "201701" convert to "2017-01"
                _month = _month.length() == 6?_month.substring(0,4) + "-" + _month.substring(4):_month;
                shopLabelAnalyze.setPdate(_month);
                shopLabelAnalyze.setType(Constants.ELEVATION_TYPE);
                String key = columnValue.get("lng") + "," + columnValue.get("lat");
                shopLabelAnalyze.setKey(key);
                shopLabelAnalyze.setShop(columnValue.get("shop_name"));
                Integer total = 0;
                if (gisMap.containsKey(key)) {
                    ShopLabelAnalyze labelAnalyze = gisMap.get(key);
                    total = Integer.parseInt(labelAnalyze.getValue());
                }
                Integer pct = Integer.parseInt(columnValue.get("user_cnt"));
                total += pct;
                shopLabelAnalyze.setValue(String.valueOf(total));
                if (flag){
                    shopMapper.updateLatAndLng(columnValue.get("longitude"),columnValue.get("latitude"),shopid);
                    flag = false;
                }
                gisMap.put(key, shopLabelAnalyze);
            }
            Collection<ShopLabelAnalyze> values = gisMap.values();
            Iterator<ShopLabelAnalyze> it = values.iterator();
            while (it.hasNext()) {
                ShopLabelAnalyze next = it.next();
                logger.debug(String.format("获取周边热力图数据[%s]", next));
                list.add(next);
            }

            // 执行数据插入
            int rows = shopLabelAnalyzeMapper.replaces(list);
            logger.debug(String.format("客户周边热力图数据在日期[%s]获取完成", pdate));
            result.setStatus("success").setRows(rows);
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return result.setStatus("fail").setError(e.getErrCode(),e.getErrMsg());

        }
    }
}
