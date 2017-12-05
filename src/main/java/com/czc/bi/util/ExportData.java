package com.czc.bi.util;

import com.czc.bi.pojo.excel.DataRow;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.lang.reflect.Method;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc :  数据导出功能
 * @date : 2016/12/22.
 * @version: V1.0
 */
public class ExportData {

    private List<Method> method;

    // 设置排除字段
    private List<String> exclude;

    public List<String> getExclude() {
        return exclude;
    }


    public static byte[] writeExcel(List<DataRow> rows) {
        // 创建Excel的工作书册 Workbook,对应到一个excel文档
        HSSFWorkbook wb = new HSSFWorkbook();

        // 创建Excel的工作sheet,对应到一个excel文档的tab
        HSSFSheet sheet = wb.createSheet("sheet1");

        // 设置正文格式
        HSSFFont font = wb.createFont();
        font.setFontName("Verdana");
        font.setFontHeight((short) 200);
        HSSFCellStyle bodyStyle = wb.createCellStyle();
        bodyStyle.setFont(font);

        bodyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        bodyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        bodyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        bodyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        // 设置标题格式
        font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setFontHeight((short) 220);

        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFont(font);
        titleStyle.setFillBackgroundColor(HSSFColor.RED.index);

        titleStyle.setFillForegroundColor(HSSFColor.TURQUOISE.index);// 设置填充颜色
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置填充方式

        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框


        HSSFRow row = null;
        HSSFCell cell = null;
        HSSFCellStyle style = null;
        // 循环提取每一行
        for (int i = 0; i < rows.size(); i++) {
            row = sheet.createRow(i);
            DataRow dr = rows.get(i);
            if (dr == null) {
                continue;
            }
            List<String> record = dr.getRecord();
            if (record == null) {
                continue;
            }
            // 设置样式
            style = dr.getTitle() ? titleStyle : bodyStyle;

            // 循环提取每一列
            for (int n = 0; n < record.size(); n++) {
                cell = row.createCell(n);
                cell.setCellStyle(style);
                cell.setCellValue(record.get(n));

            }
        }
        // 自适应列宽  如果存在合并单元格 sheet.autoSizeColumn(1, true);
        sheet.autoSizeColumn((short)0); //调整第一列宽度
        sheet.autoSizeColumn((short)1); //调整第二列宽度
        sheet.autoSizeColumn((short)2); //调整第三列宽度
        sheet.autoSizeColumn((short)3); //调整第四列宽度

        // 转换为byte[] 输出
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = null;
        /*try {
            wb.write(bos);
            bytes = bos.toByteArray();
            bos.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return bytes;

    }

    // 添加排除字段
    public ExportData addExclude(String exclude) {
        if (this.exclude == null) {
            this.exclude = new ArrayList<>();
        }
        this.exclude.add(exclude);
        return this;
    }


    public String listPojo2String(List pojos, Class cls) throws Exception {
        //pojo.stream().mapping()

        //t.getClass()
        //PropertyDescriptor pd = new PropertyDescriptor();
        getPojoField(cls);

        int totalLen = pojos.size();
        String[] rows = new String[pojos.size()];
        for (int m = 0; m < totalLen; m++) {
            int len = this.method.size();
            String[] row = new String[len];
            for (int i = 0; i < len; i++) {
                Object invoke = this.method.get(i).invoke(pojos.get(m));
                row[i] = invoke == null ? "" : invoke.toString();
            }
            rows[m] = String.join(",", row);
        }
        return String.join("\r\n", rows);
    }

    // 获取pojo字段名称和对应的get方法
    private void getPojoField(Class clazz) throws Exception {
        List<String> files = new ArrayList<>();
        //获得属性
        Field[] fields = clazz.getDeclaredFields();
        this.method = Arrays.stream(fields)
                .filter(a -> {
                    return this.exclude == null ? true : !this.exclude.contains(a.getName());
                })
                .map(a -> {
                    try {
                        PropertyDescriptor pd = new PropertyDescriptor(a.getName(), clazz);
                        return pd.getReadMethod();//获得get方法
                    } catch (IntrospectionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

//    public static void main(String[] args) {
//        List<Simple> a = new ArrayList<>();
//        Simple s = new Simple();
//        s.setKey("11111");
//        s.setValue("aaaaa");
//        a.add(s);
//
//        s = new Simple();
//        s.setKey("22222");
//        s.setValue("bbbbb");
//        a.add(s);
//
//        ExportData<Simple> ed = new ExportData<>();
//        try {
//            String s1 = ed.listPojo2FileStream(a,Simple.class);
//            System.out.println(s1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
}
