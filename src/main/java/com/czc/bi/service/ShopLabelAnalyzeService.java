package com.czc.bi.service;

import com.czc.bi.mapper.ShopLabelAnalyzeMapper;
import com.czc.bi.mapper.ShopMapper;
import com.czc.bi.pojo.Shop;
import com.czc.bi.pojo.ShopLabelAnalyze;
import com.czc.bi.pojo.SimpleKV;
import com.czc.bi.pojo.dto.NameValue;
import com.czc.bi.pojo.excel.DataRow;
import com.czc.bi.pojo.query.ShopLabelAnalyzeQuery;
import com.czc.bi.util.Constants;
import com.czc.bi.util.ExportData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : fw
 * @Desc : 标签分析客户数据服务
 * @date : 2017/9/27. 上午11:33:19
 * @version: V1.0
 */

@Service
public class ShopLabelAnalyzeService {

    @Resource
    private ShopLabelAnalyzeMapper shopLabelAnalyzeMapper;

    // 客户分析 - 客户特征
    public Map<String, Map> getCustFeature(String account, String time) {
        Map<String, Map> maps = new HashMap<>(5);
        Map<String, Float> map = new HashMap<>(2);
        Map<String, Object> submap = new HashMap<>(2);
        ShopLabelAnalyzeQuery query = new ShopLabelAnalyzeQuery();
        // 获取性别比例
        query.setAccount(account)
                .setType(Constants.GENDER_TYPE_MONTH)
                .setPdate(time);
        List<SimpleKV<String, String>> simpleKVS = shopLabelAnalyzeMapper.selectKYByQuery(query);
        int total = simpleKVS.stream().mapToInt(a -> Integer.valueOf(a.getValue())).sum();

        for (SimpleKV<String, String> simple : simpleKVS) {
            map.put(simple.getKey(),
                    new BigDecimal(Float.valueOf(simple.getValue()) * 100 / total)
                            .setScale(2, BigDecimal.ROUND_HALF_UP)
                            .floatValue());
        }
        submap.put("name", "性别比例");
        submap.put("subdata", map);
        maps.put("gender", submap);

        // 获取年龄分布
        map = new HashMap<>(4);
        query.setType(Constants.AGE_TYPE_MONTH);
        simpleKVS = shopLabelAnalyzeMapper.selectKYByQuery(query);
        total = simpleKVS.stream().mapToInt(a -> Integer.valueOf(a.getValue())).sum();

        for (SimpleKV<String, String> simple : simpleKVS) {
            map.put(simple.getKey(),
                    new BigDecimal(Float.valueOf(simple.getValue()) * 100 / total)
                            .setScale(2, BigDecimal.ROUND_HALF_UP)
                            .floatValue());
        }
        submap = new HashMap<>(2);
        submap.put("name", "年龄分布");
        submap.put("subdata", map);
        maps.put("age", submap);

        // 获取职业情况
        map = new HashMap<>(2);
        query.setType(Constants.OCCUPATION_TYPE_MONTH);
        simpleKVS = shopLabelAnalyzeMapper.selectKYByQuery(query);
        total = simpleKVS.stream().mapToInt(a -> Integer.valueOf(a.getValue())).sum();

        for (SimpleKV<String, String> simple : simpleKVS) {
            map.put(simple.getKey(),
                    new BigDecimal(Float.valueOf(simple.getValue()) * 100 / total)
                            .setScale(2, BigDecimal.ROUND_HALF_UP)
                            .floatValue());
        }
        submap = new HashMap<>(2);
        submap.put("name", "职业情况");
        submap.put("subdata", map);
        maps.put("job", submap);

        // 获取汽车拥有情况
        map = new HashMap<>(2);
        query.setType(Constants.HAVECHILD_TYPE_MONTH);
        simpleKVS = shopLabelAnalyzeMapper.selectKYByQuery(query);
        total = simpleKVS.stream().mapToInt(a -> Integer.valueOf(a.getValue())).sum();

        for (SimpleKV<String, String> simple : simpleKVS) {
            map.put(simple.getKey(),
                    new BigDecimal(Float.valueOf(simple.getValue()) * 100 / total)
                            .setScale(2, BigDecimal.ROUND_HALF_UP)
                            .floatValue());
        }
        submap = new HashMap<>(2);
        submap.put("name", "有孩占比");
        submap.put("subdata", map);
        maps.put("children", submap);

        // 获取星座情况
        query.setType(Constants.CONSTELLATIONS_TYPE_MONTH);
        simpleKVS = shopLabelAnalyzeMapper.selectKYByQuery(query);
        submap = new HashMap<>(2);
        submap.put("name", "星座");
        submap.put("subdata", formartConstellations(simpleKVS));
        maps.put("constellation", submap);

        return maps;
    }

    // 来源分析 - 地区分布
    public ArrayList<NameValue> getCustArea(String account, String time) {
        ShopLabelAnalyzeQuery query = new ShopLabelAnalyzeQuery();
        query.setAccount(account)
                .setType(Constants.PROVINCE_TYPE_MONTH)
                .setPdate(time);
        List<SimpleKV<String, String>> kvs = shopLabelAnalyzeMapper.selectKYByQuery(query);
        ArrayList<NameValue> collect = kvs.stream().map(a -> {
            return new NameValue(a.getKey(), Integer.valueOf(a.getValue()));
        })
                .sorted((a1, a2) -> (a2.getValue().compareTo(a1.getValue())))
                .collect(Collectors.toCollection(ArrayList<NameValue>::new));
        return collect;
    }

    @Resource
    private ShopMapper shopMapper;

    // 获取热力图示
    public Map<String, Object> getElevation(String account, String time) {
        Map<String, Object> map = new HashMap<>(2);
        // 获取用户的坐标
        Shop shop = shopMapper.selectByAccount(account);
        // 如果坐标不存在 写入上海辰智的坐标121.524752,31.299115
        double longitude = shop.getLongitude() != null && !"".equals(shop.getLongitude()) ? Double.valueOf(shop.getLongitude()) : 121.524752;
        double latitude = shop.getLatitude() != null && !"".equals(shop.getLatitude()) ? Double.valueOf(shop.getLatitude()) : 31.299115;
        double[] local = new double[]{longitude, latitude};
        map.put("local", local);
        // 获取热力坐标
        ShopLabelAnalyzeQuery query = new ShopLabelAnalyzeQuery();
        query.setAccount(account)
                .setType(Constants.ELEVATION_TYPE)
                .setPdate(time);
        List<SimpleKV<String, String>> simpleKVS = shopLabelAnalyzeMapper.selectKYByQuery(query);

        ArrayList<Elevation> collect = simpleKVS.stream().map(a -> {
            return new Elevation().setCoord(Double.valueOf(a.getKey()), Double.valueOf(a.getValue())).setElevation(1);
        }).collect(Collectors.toCollection(ArrayList<Elevation>::new));
        map.put("coordinate", collect);
        return map;
    }

    public byte[] custFeatureDownload(String account, String time) {
        Map<String, Map> maps = getCustFeature(account, time);
        List<DataRow> dataRows = new ArrayList<>(30);

        for (Map<String, Object> map : maps.values()) {
            DataRow dr = new DataRow();
            dr.addRecord((String) map.get("name")).setTitle(true);
            dataRows.add(dr);

            Map<String, Float> subdata = (Map<String, Float>) map.get("subdata");
            for (Map.Entry<String, Float> en : subdata.entrySet()) {
                dr = new DataRow();
                dr.addRecord(en.getKey()).addRecord(String.valueOf(en.getValue()));
                dataRows.add(dr);
            }
            dr.addRecord(null);

        }

        return ExportData.writeExcel(dataRows);
    }

    public byte[] custAreaDownload(String account, String time) {
        ArrayList<NameValue> list = getCustArea(account, time);
        List<DataRow> dataRows = new ArrayList<>(50);

        dataRows.add(new DataRow().addRecord("客流分析-地区分布"));
        dataRows.add(null);
        dataRows.add(new DataRow().addRecord("城市名称").addRecord("客流量(人)").setTitle(true));

        for (NameValue nameValue : list) {
            dataRows.add(new DataRow().addRecord("城市名称").addRecord(nameValue.getName()).addRecord(String.valueOf(nameValue.getValue())));
        }
        return ExportData.writeExcel(dataRows);
    }

    public int saves(List<ShopLabelAnalyze> list) {
        return shopLabelAnalyzeMapper.inserts(list);
    }

    class Elevation {
        private Double[] coord;
        private Number elevation;

        public Elevation() {
            this.coord = new Double[2];
        }

        public Double[] getCoord() {
            return coord;
        }

        public Elevation setCoord(Double arg0, Double arg1) {
            this.coord[0] = arg0;
            this.coord[1] = arg1;
            return this;
        }

        public Number getElevation() {
            return elevation;
        }

        public Elevation setElevation(Number elevation) {
            this.elevation = elevation;
            return this;
        }
    }

    // 格式化星座数据
    private Map<String, Integer> formartConstellations(List<SimpleKV<String, String>> kvs) {
        // 初始化12星座数据
        Map<String, Integer> map = new LinkedHashMap<>(12);
        map.put("白羊座", 0);
        map.put("金牛座", 0);
        map.put("双子座", 0);
        map.put("巨蟹座", 0);
        map.put("狮子座", 0);
        map.put("处女座", 0);
        map.put("天秤座", 0);
        map.put("天蝎座", 0);
        map.put("射手座", 0);
        map.put("摩羯座", 0);
        map.put("水瓶座", 0);
        map.put("双鱼座", 0);

        int total = kvs.stream().mapToInt(a -> Integer.valueOf(a.getValue())).sum();

        for (SimpleKV<String, String> kv : kvs) {
            map.put(kv.getKey(), Integer.valueOf(kv.getValue()));
        }
        return map;
    }
}
