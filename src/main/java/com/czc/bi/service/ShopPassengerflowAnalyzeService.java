package com.czc.bi.service;

import com.czc.bi.mapper.ShopPassengerflowAnalyzeMapper;
import com.czc.bi.pojo.ShopPassengerflowAnalyze;
import com.czc.bi.pojo.dto.Result;
import com.czc.bi.pojo.echarts3.Option;
import com.czc.bi.pojo.echarts3.axis.CategoryAxis;
import com.czc.bi.pojo.echarts3.axis.ValueAxis;
import com.czc.bi.pojo.echarts3.code.Trigger;
import com.czc.bi.pojo.echarts3.json.GsonOption;
import com.czc.bi.pojo.echarts3.series.Line;
import com.czc.bi.pojo.excel.DataRow;
import com.czc.bi.pojo.query.BaseQuery;
import com.czc.bi.pojo.query.ShopPassengerflowAnalyzeQuery;
import com.czc.bi.util.BaseUtil;
import com.czc.bi.util.Constants;
import com.czc.bi.util.ExportData;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc : 客户客流数据服务
 * @date : 2017/9/28 上午11:33:19
 * @version: V1.0
 */

@Service
public class ShopPassengerflowAnalyzeService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private ShopPassengerflowAnalyzeMapper shopPassengerflowAnalyzeMapper;

    // 今日概况
    // 获取首页的当前客流信息
    public Map getCurrentFlow(String account) {
        // 取今天的日期
        String date = BaseUtil.getCurrentDate();

        Map<String, Object> map = new HashMap<>(4);
        // 获取今日客流
        ShopPassengerflowAnalyzeQuery query = new ShopPassengerflowAnalyzeQuery();
        query.setAccount(account)
                .setType(Constants.CUSTFLOW_TYPE_DAY)
                .setPdate(date)
                .setOrderBy("rank");
        List<ShopPassengerflowAnalyze> ShopPassengerflowAnalyze = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        Integer currentFlow = ShopPassengerflowAnalyze.size() == 0 ? 0 : ShopPassengerflowAnalyze.get(0).getValue();

        map.put("currentFlow", currentFlow);

        // 获取峰值客流
        query.setType(Constants.CUSTFLOW_TYPE_HOUR).setOrderBy("value", false);
        ShopPassengerflowAnalyze = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        Map<String, Object> maxFlow = new HashMap<>(2);
        if (ShopPassengerflowAnalyze.size() == 0) {
            maxFlow.put("label", "无数据");
            maxFlow.put("value", 0);
        } else {
            maxFlow.put("label", ShopPassengerflowAnalyze.get(0).getLabel());
            maxFlow.put("value", ShopPassengerflowAnalyze.get(0).getValue());
        }
        map.put("maxFlow", maxFlow);

        // 获取昨天客流
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        date = BaseUtil.getDateString(cal.getTime());
        query.setType(Constants.CUSTFLOW_TYPE_DAY).setPdate(date);
        ShopPassengerflowAnalyze = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        Integer yesterdayFlow = ShopPassengerflowAnalyze.size() == 0 ? 0 : ShopPassengerflowAnalyze.get(0).getValue();

        map.put("yesterdayFlow", yesterdayFlow);

        // 获取上周同期客流
        cal.add(Calendar.DATE, -6);
        date = BaseUtil.getDateString(cal.getTime());
        query.setPdate(date);
        ShopPassengerflowAnalyze = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        Integer lastWeekFlow = ShopPassengerflowAnalyze.size() == 0 ? 0 : ShopPassengerflowAnalyze.get(0).getValue();

        map.put("lastWeekFlow", lastWeekFlow);

        return map;
    }

    // 获取首页上面的今日客流走势
    public Result getMainDayFlow(String account) {
        // 取今天的日期
        String date = BaseUtil.getCurrentDate();
        logger.debug("获取今日[" + date + "]的客流信息");
        // 获取今日客流
        ShopPassengerflowAnalyzeQuery query = new ShopPassengerflowAnalyzeQuery();
        query.setAccount(account)
                .setType(Constants.CUSTFLOW_TYPE_HOUR)
                .setPdate(date)
                .setRank(7, BaseQuery.GREATER_THAN)
                .setOrderBy("rank")
                .setPage(1, 15)
        ;
        List<ShopPassengerflowAnalyze> shopPassengerflowAnalyzes = shopPassengerflowAnalyzeMapper.selectByQuery(query);

        List<Integer> values = shopPassengerflowAnalyzes.stream().map(a -> a.getValue()).collect(Collectors.toList());
        GsonOption option = new GsonOption();
        option.tooltip().trigger(Trigger.axis);
        option.legend("今日客流", "昨日客流", "上周同期");
        option.toolbox().show(true);
        option.xAxis(new CategoryAxis().boundaryGap(false)
                .data("7-8点", "8-9点", "9-10点", "10-11点", "11-12点", "12-13点", "13-14点", "14-15点",
                        "15-16点", "16-17点", "17-18点", "18-19点", "19-20点", "20-21点", "21-22点"));
        option.yAxis(new ValueAxis());
        option.series(new Line().name("今日客流")
                .data(values));

        // 获取昨日客流
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        logger.debug("获取昨日[" + date + "]的客流信息");
        date = BaseUtil.getDateString(cal.getTime());
        query.setPdate(date);
        shopPassengerflowAnalyzes = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        values = shopPassengerflowAnalyzes.stream().map(a -> a.getValue()).collect(Collectors.toList());
        option.series(new Line().name("昨日客流")
                .data(values));

        // 获取上周同期
        cal.add(Calendar.DATE, -6);
        date = BaseUtil.getDateString(cal.getTime());
        logger.debug("获取上周同期[" + date + "]的客流信息");
        query.setPdate(date);
        shopPassengerflowAnalyzes = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        values = shopPassengerflowAnalyzes.stream().map(a -> a.getValue()).collect(Collectors.toList());
        option.series(new Line().name("上周同期")
                .data(values));
        return new Result<Option>(option);
    }

    // 获取首页的周客流echarts图
    public Result getMainWeekFlow(String account) throws ParseException {
        Calendar cal = Calendar.getInstance();
        // 获取周一的日期
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        String monday = BaseUtil.getDateString(cal.getTime());
        ShopPassengerflowAnalyzeQuery query = new ShopPassengerflowAnalyzeQuery();
        query.setAccount(account)
                .setType(Constants.CUSTFLOW_TYPE_DAY)
                .setPdate(monday, BaseQuery.GREATER_OR_EQUAL);
        List<ShopPassengerflowAnalyze> shopPassengerflowAnalyzes = shopPassengerflowAnalyzeMapper.selectByQuery(query);

        List<Integer> integers = formartOneWeekFlow(BaseUtil.getDateString(cal.getTime()), shopPassengerflowAnalyzes);

        Option option = new Option();
        option.tooltip().trigger(Trigger.axis);
        option.legend("本周客流", "上周客流");
        option.toolbox().show(true);
        option.xAxis(new CategoryAxis().boundaryGap(false)
                .data("周一", "周二", "周三", "周四", "周五", "周六", "周天"));
        option.yAxis(new ValueAxis());
        option.series(new Line().name("本周客流").data(integers));

        // 上周客流信息
        cal.add(Calendar.DAY_OF_WEEK, -7);
        query.setAccount(account)
                .setType(Constants.CUSTFLOW_TYPE_DAY)
                .setPdate(BaseUtil.getBegin7Day(monday), BaseQuery.GREATER_OR_EQUAL)
                .setLabel(monday,BaseQuery.LESS_THAN);
        logger.debug("查询条件有："+query.getPdate()+","+query.getLabel());
        shopPassengerflowAnalyzes = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        integers = formartOneWeekFlow(BaseUtil.getDateString(cal.getTime()), shopPassengerflowAnalyzes);
        option.series(new Line().name("上周客流")
                .data(integers));

        return new Result<Option>(option);
    }

    // 数据趋势
    // 数据趋势 - 年客流量
    public List<Map> getYearFlow(String account, String years) {
        List<Map> data = new ArrayList<>(2);
        ShopPassengerflowAnalyzeQuery query = new ShopPassengerflowAnalyzeQuery();
        query.setAccount(account).setType(Constants.CUSTFLOW_TYPE_MONTH);
        for (String year : years.split(",")) {
            Map<String, Object> map = new HashMap<>(2);
            query.setAccount(account)
                    .setPdate(String.format("%s-%%", year), BaseQuery.LIKE);
            List<ShopPassengerflowAnalyze> shopCustflowAnalyzes = shopPassengerflowAnalyzeMapper.selectByQuery(query);
            List<Integer> flow = formartOneYearFlow(year, shopCustflowAnalyzes);
            map.put("name", String.format("%s年", year));
            map.put("data", flow);
            data.add(map);
        }
        return data;
    }

    // 数据趋势 - 月客流量
    public List<Map> getMonthFlow(String account, String months) {
        List<Map> data = new ArrayList<>(2);
        ShopPassengerflowAnalyzeQuery query = new ShopPassengerflowAnalyzeQuery();
        query.setAccount(account)
                .setType(Constants.CUSTFLOW_TYPE_DAY);
        for (String month : months.split(",")) {
            Map<String, Object> map = new HashMap<>(2);
            query.setPdate(String.format("%s%%", month), BaseQuery.LIKE);
            List<ShopPassengerflowAnalyze> flows = shopPassengerflowAnalyzeMapper.selectByQuery(query);
            List<Integer> flow = formartOneMonthFlow(month, flows);
            map.put("name", String.format("%s年%d月", month.substring(0, 4), Integer.valueOf(month.substring(5, 7))));
            map.put("data", flow);
            data.add(map);
        }
        return data;
    }

    // 数据趋势 - 周客流量
    public List<Map> getWeekFlow(String account, String weekstrs) throws ParseException {
        List<Map> data = new ArrayList<>(2);
        Map<String, List> maps = new HashMap<>(2);
        for (String weekstr : weekstrs.split(",")) {
            Map<String, Object> map = new HashMap<>(2);
            // 提取日期和周信息
            String date = weekstr.substring(0, 10);
            String week = weekstr.substring(10);
            ShopPassengerflowAnalyzeQuery query = new ShopPassengerflowAnalyzeQuery();
            query.setAccount(account)
                    .setPdate(date, BaseQuery.GREATER_OR_EQUAL)
                    .setType(Constants.CUSTFLOW_TYPE_DAY)
                    .setLabel(BaseUtil.getAfter7Day(date), BaseQuery.LESS_THAN);

            List<ShopPassengerflowAnalyze> shopCustflowAnalyzes = shopPassengerflowAnalyzeMapper.selectByQuery(query);

            List<Integer> flow = formartOneWeekFlow(date, shopCustflowAnalyzes);
            map.put("name", String.format("%s年%d周", weekstr.substring(0, 4), Integer.valueOf(weekstr.substring(10, 12))));
            map.put("data", flow);
            data.add(map);
        }
        return data;
    }

    // 数据趋势 - 日客流量
    public List<Map> getDayFlow(String account, String days) {
        List<Map> data = new ArrayList<>(2);
        ShopPassengerflowAnalyzeQuery query = new ShopPassengerflowAnalyzeQuery();
        query.setAccount(account)
                .setType(Constants.CUSTFLOW_TYPE_HOUR);
        for (String day : days.split(",")) {
            Map<String, Object> map = new HashMap<>(2);
            query.setPdate(day).setOrderBy("rank");
            List<ShopPassengerflowAnalyze> shopCustflowAnalyzes = shopPassengerflowAnalyzeMapper.selectByQuery(query);
            List<Integer> flow = shopCustflowAnalyzes.stream()
                    .filter(a -> (a.getRank() > 7 && a.getRank() < 23))
                    .map(a -> a.getValue())
                    .collect(Collectors.toList());
            map.put("name", String.format("%s年%d月%d日"
                    , day.substring(0, 4)
                    , Integer.valueOf(day.substring(5, 7))
                    , Integer.valueOf(day.substring(8, 10))));
            map.put("data", flow);
            data.add(map);
        }
        return data;
    }

    // 客户分析 - 新老客户/周
    public Map<String, Map> getWeekNewOldCust(
            String account,
            String time)
            throws ParseException {
        String pdate = time.substring(0, 10);
        // 获取新客户数据
        ShopPassengerflowAnalyzeQuery query = new ShopPassengerflowAnalyzeQuery();
        query.setType(Constants.NEWCUST_TYPE_DAY)
                .setAccount(account)
                .setPdate(pdate, BaseQuery.GREATER_OR_EQUAL)
                .setLabel(BaseUtil.getAfter7Day(pdate), BaseQuery.LESS_THAN);

        List<ShopPassengerflowAnalyze> shopCustflowAnalyzes = shopPassengerflowAnalyzeMapper.selectByQuery(query);

        List<Integer> flow = formartOneWeekFlow(pdate, shopCustflowAnalyzes);
        // 获取老客户数据
        query.setType(Constants.OLDCUST_TYPE_DAY);
        List<ShopPassengerflowAnalyze> shopCustflowAnalyzes2 = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        // 格式化数据
        List<Integer> flow2 = formartOneWeekFlow(pdate, shopCustflowAnalyzes2);

        // 计算总客户量: 老客户+新客户
        List<Integer> flow3 = new ArrayList<>(7);
        for (int i = 0; i < flow2.size(); i++) {
            int e = flow2.get(i) + flow.get(i);
            flow3.add(e);
        }

        // 构建返回数据
        Map<String, Map> maps = new HashMap<>(3);
        Map<String, Object> map = new HashMap<>(2);

        // 总客流
        map.put("name", "总客流");
        map.put("subdata", flow3);
        maps.put("totalUser", map);

        // 新客户  newUser
        map = new HashMap<>(2);
        map.put("name", "新用户");
        map.put("subdata", flow);
        maps.put("newUser", map);

        //老用户   oldUser
        map = new HashMap<>(2);
        map.put("name", "老用户");
        map.put("subdata", flow2);
        maps.put("oldUser", map);
        return maps;
    }

    // 客户分析 - 新老客户/月
    public Map<String, Map> getMonthNewOldCust(String account, String time) {
        // 获取新客户数据
        ShopPassengerflowAnalyzeQuery query = new ShopPassengerflowAnalyzeQuery();
        query.setType(Constants.NEWCUST_TYPE_DAY)
                .setAccount(account)
                .setPdate(String.format("%s%%", time), BaseQuery.LIKE);

        List<ShopPassengerflowAnalyze> shopPassengerflowAnalyzes = shopPassengerflowAnalyzeMapper.selectByQuery(query);

        List<Integer> flow = formartOneMonthFlow(time, shopPassengerflowAnalyzes);
        // 获取老客户数据
        query.setType(Constants.OLDCUST_TYPE_DAY);
        List<ShopPassengerflowAnalyze> shopPassengerflowAnalyzes2 = shopPassengerflowAnalyzeMapper.selectByQuery(query);

        List<Integer> flow2 = formartOneMonthFlow(time, shopPassengerflowAnalyzes2);

        List<Integer> flow3 = new ArrayList<>(31);
        for (int i = 0; i < flow2.size(); i++) {
            int e = flow2.get(i) + flow.get(i);
            flow3.add(e);
        }

        // 构建返回数据
        Map<String, Map> maps = new HashMap<>(3);
        Map<String, Object> map = new HashMap<>(2);

        // 总客流
        map.put("name", "总客流");
        map.put("subdata", flow3);
        maps.put("totalUser", map);

        // 新客户  newUser
        map = new HashMap<>(2);
        map.put("name", "新用户");
        map.put("subdata", flow);
        maps.put("newUser", map);

        //老用户   oldUser
        map = new HashMap<>(2);
        map.put("name", "老用户");
        map.put("subdata", flow2);
        maps.put("oldUser", map);
        return maps;
    }

    // 客户分析 - 回流流失/周
    public Map<String, Map> getWeekBackFlow(String account, String date) throws ParseException {
        date = date.substring(0, 10);
        Map<String, Map> maps = new HashMap<>(4);
        // 获取流失客户
        ShopPassengerflowAnalyzeQuery query = new ShopPassengerflowAnalyzeQuery();
        query.setType(Constants.LOST_TYPE)
                .setAccount(account)
                .setPdate(date, BaseQuery.GREATER_OR_EQUAL)
                .setLabel(BaseUtil.getAfter7Day(date), BaseQuery.LESS_THAN);
        List<ShopPassengerflowAnalyze> mapper = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        List<Integer> flow_lost = formartOneWeekFlow(date, mapper);
        Map<String, Object> map = new HashMap<>(2);
        map.put("name", "流失客户");
        map.put("subdata", flow_lost);
        maps.put("lost", map);

        // 获取回流客户
        query.setType(Constants.BACK_FLOW_TYPE);
        mapper = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        List<Integer> flow_back = formartOneWeekFlow(date, mapper);
        map = new HashMap<>(2);
        map.put("name", "回流客户");
        map.put("subdata", flow_back);
        maps.put("back_flow", map);

        // 获取回头客
        query.setType(Constants.RETURN_TYPE);
        mapper = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        List<Integer> flow_return = formartOneWeekFlow(date, mapper);
        map = new HashMap<>(2);
        map.put("name", "回头客");
        map.put("subdata", flow_return);
        maps.put("return", map);

        // 获取常客
        query.setType(Constants.FREQUENT_TYPE);
        mapper = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        List<Integer> flow_frequent = formartOneWeekFlow(date, mapper);
        map = new HashMap<>(2);
        map.put("name", "常客");
        map.put("subdata", flow_frequent);
        maps.put("frequent", map);

        return maps;
    }

    // 客户分析 - 回流流失/月
    public Map<String, Map> getMonthBackFlow(String account, String month) {
        Map<String, Map> maps = new HashMap<>(4);
        // 获取流失客户
        ShopPassengerflowAnalyzeQuery query = new ShopPassengerflowAnalyzeQuery();
        query.setType(Constants.LOST_TYPE)
                .setAccount(account)
                .setPdate(String.format("%s%%", month), BaseQuery.LIKE);
        List<ShopPassengerflowAnalyze> mapper = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        List<Integer> flow_lost = formartOneMonthFlow(month, mapper);
        Map<String, Object> map = new HashMap<>(2);
        map.put("name", "流失客户");
        map.put("subdata", flow_lost);
        maps.put("lost", map);

        // 获取回流客户
        query.setType(Constants.BACK_FLOW_TYPE);
        mapper = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        List<Integer> flow_back = formartOneMonthFlow(month, mapper);
        map = new HashMap<>(2);
        map.put("name", "回流客户");
        map.put("subdata", flow_back);
        maps.put("back_flow", map);

        // 获取回头客
        query.setType(Constants.RETURN_TYPE);
        mapper = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        List<Integer> flow_return = formartOneMonthFlow(month, mapper);
        map = new HashMap<>(2);
        map.put("name", "回头客");
        map.put("subdata", flow_return);
        maps.put("return", map);

        // 获取常客
        query.setType(Constants.FREQUENT_TYPE);
        mapper = shopPassengerflowAnalyzeMapper.selectByQuery(query);
        List<Integer> flow_frequent = formartOneMonthFlow(month, mapper);
        map = new HashMap<>(2);
        map.put("name", "常客");
        map.put("subdata", flow_frequent);
        maps.put("frequent", map);

        return maps;
    }

    // 格式化输出1月的客流信息
    private List<Integer> formartOneMonthFlow(String month, List<ShopPassengerflowAnalyze> flows) {
        String firstDay = month + "-01";
        Calendar cal = Calendar.getInstance();
        // 构建一个月的map
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>(31);
        try {
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(firstDay));
            logger.debug("现在的时间是:"+new Date());
            logger.debug("本月第一天是:"+cal.getTime());
            while (true) {

                // 如果到了当天 退出循环
                if (new Date().before(cal.getTime())) {
                    break;
                }

                map.put(String.format("%d日", cal.get(Calendar.DATE)), 0);

                cal.add(Calendar.DATE, 1);
                // 如果到了下月1号  退出循环
                if (cal.get(Calendar.DATE) == 1) {
                    break;
                }
            }
            // 添加数据
            for (ShopPassengerflowAnalyze flow : flows) {
                map.put(String.format("%d日", Integer.valueOf(flow.getLabel().split("-")[2])), flow.getValue());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Collection<Integer> collection = map.values();
        return new ArrayList<Integer>(collection);
    }


    //格式化输出1年的月客流信息
    private List<Integer> formartOneYearFlow(String yearStr, List<ShopPassengerflowAnalyze> flows) {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        // 构建12月Map
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>(12);
        for (int i = 1; i < 13; i++) {
            // 如果大于当前月份 跳出循环
            if (i > month && Integer.valueOf(yearStr) == year) {
                break;
            }
            map.put(String.format("%d月", i), 0);
        }
        // 插入数据
        for (ShopPassengerflowAnalyze flow : flows) {
            map.put(String.format("%d月", Integer.valueOf(flow.getLabel().split("-")[1])), flow.getValue());
        }
        Collection<Integer> collection = map.values();
        return new ArrayList<Integer>(collection);
    }

    // 格式化数据一周的客流信息
    public List<Integer> formartOneWeekFlow(String start, List<ShopPassengerflowAnalyze> flows) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 构建7天的map
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>(7);
        for (int i = 0; i < 7; i++) {
            if (new Date().after(cal.getTime())) {
                break;
            }

            map.put(BaseUtil.getDateString(cal.getTime()), 0);
            cal.add(Calendar.DATE, 1);
        }
        // 插入数据
        for (ShopPassengerflowAnalyze flow : flows) {
            map.put(flow.getPdate(), flow.getValue());
        }
        return new ArrayList<Integer>(map.values());
    }

    // 当前页面的数据下载
    public byte[] currentDownload(String account) throws IOException {

        String callValue = null;
        // 初始化Excel环境
        // 创建Excel的工作书册 Workbook,对应到一个excel文档
        HSSFWorkbook wb = new HSSFWorkbook();

        // 创建Excel的工作sheet,对应到一个excel文档的tab
        HSSFSheet sheet = wb.createSheet("sheet1");

        // 设置excel每列宽度
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 3500);

        // 创建字体样式
        HSSFFont font = wb.createFont();
        font.setFontName("Verdana");
        font.setBoldweight((short) 100);
        font.setFontHeight((short) 300);

        // 创建单元格样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(HSSFColor.PINK.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 设置字体
        style.setFont(font);

        // 创建Excel的sheet的一行
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 500);// 设定行的高度
        // 创建一个Excel的单元格
        HSSFCell cell = row.createCell(0);

        // 写入关键数据
        cell.setCellValue("关键数据");
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("今日客流");

        cell = row.createCell(1);
        cell.setCellValue("峰值客流");

        cell = row.createCell(2);
        cell.setCellValue("昨日客流");

        cell = row.createCell(3);
        cell.setCellValue("上周同期");


        row = sheet.createRow(2);
        // 今日客流 峰值客流 昨日客流 上周同期
        Map map = getCurrentFlow(account);
        callValue = map.get("currentFlow").toString();
        cell = row.createCell(0);
        cell.setCellValue(callValue);

        callValue = map.get("maxFlow").toString();
        cell = row.createCell(1);
        cell.setCellValue(callValue);

        callValue = map.get("yesterdayFlow").toString();
        cell = row.createCell(2);
        cell.setCellValue(callValue);

        callValue = map.get("lastWeekFlow").toString();
        cell = row.createCell(1);
        cell.setCellValue(callValue);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        byte[] bytes = bos.toByteArray();
        bos.close();
        return bytes;

    }

    public ResponseEntity<byte[]> dateTrendDownload(String account, String time, String type) throws Exception {

        List<Map> list = null;
        String file = null;

        // 定义待写入的数据
        List<DataRow> datas = new ArrayList<>(15);

        DataRow dr = new DataRow();
        datas.add(dr.addRecord("客流分析-数据趋势"));
        datas.add(null);

        int loop;
        String[] format = null;
        switch (type) {
            case "year":
                list = getYearFlow(account, time);
                loop = 12;
                format = new String[]{
                        "1月", "2月", "3月", "4月", "5月", "6月",
                        "7月", "8月", "9月", "10月", "11月", "12月"};
                file = String.format("客流分析-数据趋势(%s年).xls", time);
                break;
            case "month":
                list = getMonthFlow(account, time);
                loop = 31;
                format = new String[]{
                        "1日", "2日", "3日", "4日", "5日",
                        "6日", "7日", "8日", "9日", "10日",
                        "11日", "12日", "13日", "14日", "15日",
                        "16日", "17日", "18日", "19日", "20日",
                        "21日", "22日", "23日", "24日", "25日",
                        "25日", "27日", "28日", "29日", "30日",
                        "31日"};
                file = String.format("客流分析-数据趋势(%s).xls", time);
                break;
            case "week":
                list = getWeekFlow(account, time);
                loop = 7;
                format = new String[]{
                        "周一", "周二", "周三", "周四", "周五",
                        "周六", "周日"};
                file = String.format(
                        "客流分析-数据趋势(第%s周%s).xls",
                        time.substring(1, 10),
                        time.substring(10));
                break;

            case "date":
                list = getDayFlow(account, time);
                loop = 15;
                format = new String[]{
                        "7-8点", "8-9点", "9-10点", "10-11点", "11-12点",
                        "12-13点", "13-14点", "14-15点", "15-16点", "16-17点",
                        "17-18点", "18-19点", "19-20点", "20-21点", "21-22点"};
                file = String.format("客流分析-数据趋势(%s).xls", time);
                break;
            default:
                return null;
        }

        // 获取item
        dr = new DataRow();
        dr.addRecord("时段");
        for (Map map : list) {
            String name = (String) map.get("name");
            dr.addRecord(name + "客流量");
        }
        datas.add(dr.setTitle(true));

        int size = list.size();

        // 获取数据
        for (int i = 0; i < loop; i++) {
            dr = new DataRow();
            dr.addRecord(format[i]);
            for (int n = 0; n < size; n++) {
                List<Integer> flows = (List<Integer>) list.get(n).get("data");
                if (flows.size() <= i) {
                    dr.addRecord("");
                } else {
                    dr.addRecord(String.valueOf(flows.get(i)));
                }

            }
            datas.add(dr);
        }
        byte[] bytes = ExportData.writeExcel(datas);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(file.getBytes("UTF-8"), "iso-8859-1"));
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }

    public ResponseEntity<byte[]> newOldCustDownload(String account, String time, String type) throws Exception {

        int loop;
        String[] format = null;
        String file = null;
        Map<String, Map> maps = null;

        switch (type) {
            case "week":
                loop = 7;
                format = getWeekString(time.substring(0, 10));
                maps = getWeekNewOldCust(account, time);
                file = String.format(
                        "客流分析-新老用户(第%s周%s).xls",
                        time.substring(1, 10),
                        time.substring(10));
                break;
            case "month":
                loop = 31;
                format = new String[]{
                        "1日", "2日", "3日", "4日", "5日",
                        "6日", "7日", "8日", "9日", "10日",
                        "11日", "12日", "13日", "14日", "15日",
                        "16日", "17日", "18日", "19日", "20日",
                        "21日", "22日", "23日", "24日", "25日",
                        "25日", "27日", "28日", "29日", "30日",
                        "31日"};
                maps = getMonthNewOldCust(account, time);
                file = String.format("客流分析-新老用户(%s).xls", time);
                break;
            default:
                return null;
        }

        // 定义待写入的数据
        List<DataRow> datas = new ArrayList<>(19);

        DataRow dr = new DataRow();
        datas.add(dr.addRecord("客流分析-新老用户"));
        datas.add(null);


        List<Integer> newCust = (List<Integer>) maps.get("newUser").get("subdata");
        int newSize = newCust.size();
        int newSum = newCust.stream().mapToInt(a -> a).sum();

        List<Integer> oldCust = (List<Integer>) maps.get("oldUser").get("subdata");
        int oldSize = oldCust.size();
        int oldSum = oldCust.stream().mapToInt(a -> a).sum();

        // 汇总信息
        datas.add(new DataRow()
                .addRecord("新用户")
                .addRecord("新用户占比")
                .addRecord("老用户")
                .addRecord("老用户占比")
                .setTitle(true));

        datas.add(new DataRow()
                .addRecord(String.valueOf(newSum))
                .addRecord(String.valueOf((newSum + oldSum) == 0 ? 0 : newSum * 100 / (newSum + oldSum)) + "%")
                .addRecord(String.valueOf(newSum))
                .addRecord(String.valueOf((newSum + oldSum) == 0 ? 0 : oldSum * 100 / (newSum + oldSum)) + "%"));

        datas.add(null);
        //  详细信息
        datas.add(new DataRow()
                .addRecord("时间")
                .addRecord("新用户")
                .addRecord("占比")
                .addRecord("老用户")
                .addRecord("占比")
                .setTitle(true));


        for (int i = 0; i < loop; i++) {
            dr = new DataRow();
            int n, o;

            if (newSize <= i) {
                n = 0;
            } else {
                n = newCust.get(i);
            }

            if (oldSize <= i) {
                o = 0;
            } else {
                o = oldCust.get(i);
            }

            dr.addRecord(format[i])
                    .addRecord(String.valueOf(n))
                    .addRecord(String.valueOf(n + o == 0 ? 0 : n * 100 / (n + o)) + "%")
                    .addRecord(String.valueOf(o))
                    .addRecord(String.valueOf(n + o == 0 ? 0 : o * 100 / (n + o)) + "%");

            datas.add(dr);

        }
        byte[] bytes = ExportData.writeExcel(datas);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(file.getBytes("UTF-8"), "iso-8859-1"));
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);

    }

    private String[] getWeekString(String start) throws Exception {
        // 设置传入的时间格式
        String[] week = new String[]{
                "周一", "周二", "周三", "周四", "周五",
                "周六", "周日"};
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(start));
        String[] res = new String[7];
        for (int i = 0; i < 7; i++) {
            res[i] = String.format(
                    "%s(%s)",
                    week[i],
                    dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        return res;
    }

    // 回流 流失客户数据下载
    public ResponseEntity<byte[]> backFlowDownload(String account, String time, String type) throws Exception {
        List<DataRow> dataRows = new ArrayList<>(19);
        int loop;
        String[] format = null;
        Map<String, Map> maps = null;
        String file = null;

        switch (type) {
            case "week":
                loop = 7;
                format = getWeekString(time.substring(0, 10));
                maps = getWeekBackFlow(account, time);
                file = String.format(
                        "客流分析-回流流失(第%s周%s).xls",
                        time.substring(1, 10),
                        time.substring(10));
                break;
            case "month":
                loop = 31;
                format = new String[]{
                        "1日", "2日", "3日", "4日", "5日",
                        "6日", "7日", "8日", "9日", "10日",
                        "11日", "12日", "13日", "14日", "15日",
                        "16日", "17日", "18日", "19日", "20日",
                        "21日", "22日", "23日", "24日", "25日",
                        "25日", "27日", "28日", "29日", "30日",
                        "31日"};
                maps = getMonthBackFlow(account, time);
                file = String.format("客流分析-回流流失(%s).xls", time);
                break;
            default:
                return null;
        }

        DataRow dr = new DataRow();
        dataRows.add(dr.addRecord("客户回流流失分析"));
        dataRows.add(null);
        dataRows.add(new DataRow()
                .addRecord("时间")
                .addRecord("流失客户")
                .addRecord("回流客户")
                .addRecord("回头客")
                .addRecord("常客"));

        // 解析数据
        List<Integer> lost = (List<Integer>) maps.get("lost").get("subdata");
        List<Integer> backFlow = (List<Integer>) maps.get("back_flow").get("subdata");
        List<Integer> return_ = (List<Integer>) maps.get("return").get("subdata");
        List<Integer> frequent = (List<Integer>) maps.get("frequent").get("subdata");

        for (int i = 0; i < loop; i++) {
            dr = new DataRow();

            dr.addRecord(format[i]);

            if (lost.size() <= i) {
                dr.addRecord("-");
            } else {
                dr.addRecord(String.valueOf(lost.get(i)));
            }

            if (backFlow.size() <= i) {
                dr.addRecord("-");
            } else {
                dr.addRecord(String.valueOf(backFlow.get(i)));
            }

            if (return_.size() <= i) {
                dr.addRecord("-");
            } else {
                dr.addRecord(String.valueOf(return_.get(i)));
            }

            if (frequent.size() <= i) {
                dr.addRecord("-");
            } else {
                dr.addRecord(String.valueOf(frequent.get(i)));
            }

            dataRows.add(dr);
        }

        byte[] bytes = ExportData.writeExcel(dataRows);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(file.getBytes("UTF-8"), "iso-8859-1"));
        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }
}
