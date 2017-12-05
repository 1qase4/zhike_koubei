package com.czc.bi;

import com.alipay.api.AlipayApiException;
import com.czc.bi.mapper.ShopMapper;
import com.czc.bi.scheduling.AlipayDataSync;
import com.czc.bi.scheduling.CustFlowDataSync;
import com.czc.bi.scheduling.JobResult;
import com.czc.bi.scheduling.impl.*;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZhikeKoubeiApplicationTests2 {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private AlipayDataSync alipayDataSync;

    @Autowired
    private CustFlowDataSync custFlowDataSync;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private SyncShopHotDiagramData syncShopHotDiagramData;

    @Autowired
    private SyncShopPropertyAreaData syncShopPropertyAreaData;

    @Autowired
    private SyncShopPropertyData syncShopPropertyData;

    @Autowired
    private SyncUsranalysisForweekData syncUsranalysisForweekData;

    @Autowired
    private SyncUsrBackForweekData syncUsrBackForweekData;

    @Autowired
    private SyncUsrLostBackForweekData syncUsrLostBackForweekData;

    @Test
    public void test1() throws Exception{
        for (int i = 0; i < 1; i++) {
            alipayDataSync.syncAlipayData();
        }
    }

    @Test
    public void demo22() throws Exception {
        // 获取所有的shopid
        List<String> ids = shopMapper.selectAllShopId();
        logger.debug("shopid ======>" + ids);

        String[] datas = {"2017-11-01",
                "2017-11-02",
                "2017-11-03",
                "2017-11-04",
                "2017-11-05",
                "2017-11-06",
                "2017-11-07"
        };

        for (String data : datas) {
            for (String id : ids) {
                logger.debug(String.format("开始处理date[%s] shop[%s]",data,id));
                custFlowDataSync.syncDayFlow(
                        data,
                        "201710BB587b6a2bf52a4795bba5e7eca40c1C55");
                Thread.sleep(1000);
            }
        }

    }


    @Test
    public void demo23() throws Exception {
        // 获取所有的shopid
        List<String> ids = shopMapper.selectAllShopId();
        logger.debug("shopid ======>" + ids);

        String[] datas = {"2017-11-01",
                "2017-11-02",
                "2017-11-03",
                "2017-11-04",
                "2017-11-05",
                "2017-11-06",
                "2017-11-07"
        };

        for (String data : datas) {
            for (String id : ids) {
                logger.debug(String.format("开始处理date[%s] shop[%s]",data,id));
                custFlowDataSync.syncIntervalFlow(
                        id,
                        data,
                        "201710BB587b6a2bf52a4795bba5e7eca40c1C55");
                Thread.sleep(1300);
            }
        }

    }

    @Test
    public void demo() throws AlipayApiException, InterruptedException {
        // 获取所有的shopid
        List<String> ids = shopMapper.selectAllShopId();
        logger.debug("shopid ======>" + ids);

        String[] datas = {"2017-11-31",
                "2017-12-01",
                "2017-12-02",
                "2017-12-03",
                "2017-12-04"
        };

        String month = "2017-11";
        for (String id : ids) {
            JobResult result1 = syncShopPropertyData.execute(
                    id,
                    "201710BB587b6a2bf52a4795bba5e7eca40c1C55",
                    month);
            Thread.sleep(1300);
            JobResult result2 = syncShopPropertyAreaData.execute(
                    id,
                    "201710BB587b6a2bf52a4795bba5e7eca40c1C55",
                    month);
            Thread.sleep(1300);
            JobResult result3 = syncShopHotDiagramData.execute(
                    id,
                    "201710BB587b6a2bf52a4795bba5e7eca40c1C55",
                    "201711");
            Thread.sleep(1300);
        }
        for (String data : datas) {
            for (String id : ids) {
                logger.debug(String.format("开始处理date[%s] shop[%s]",data,id));
                JobResult result1 = syncUsrLostBackForweekData.execute(
                        id,
                        "201710BB587b6a2bf52a4795bba5e7eca40c1C55",
                        data);
                Thread.sleep(1300);
                JobResult result2 = syncUsrBackForweekData.execute(
                        id,
                        "201710BB587b6a2bf52a4795bba5e7eca40c1C55",
                        data);
                Thread.sleep(1300);
                JobResult result3 = syncUsranalysisForweekData.execute(
                        id,
                        "201710BB587b6a2bf52a4795bba5e7eca40c1C55",
                        data);
            }
        }
    }
}
