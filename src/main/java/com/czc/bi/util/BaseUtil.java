package com.czc.bi.util;

import com.czc.bi.pojo.TableData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright © 武汉辰智商务信息咨询有限公司. All rights reserved.
 *
 * @author : zchong
 * @Desc : 基本工具
 * @date : 2016年4月14日 上午11:33:19
 * @version: V1.0
 */
public class BaseUtil {

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String getDateString(Date date,String formart){
		SimpleDateFormat sdf=new SimpleDateFormat(formart);//转换格式
		return sdf.format(date);//打印
	}

	public static String getDateString(Date date){
		return getDateString(date,"yyyy-MM-dd");
	}

	public static String getCurrentDate(){
		return getDateString(new Date(),"yyyy-MM-dd");
	}

	public static String getCurrentDate(String formart){
		return getDateString(new Date(),formart);
	}

	public static TableData paging(List data, int cur, int size) {
		List<Object> res = new ArrayList<>();
		int len = data.size();
		for (int i = (cur - 1) * size; i < len; i++) {
			res.add(data.get(i));
		}

		return new TableData().setSuccess(true).setCurPage(cur).setTotalRows(len).setData(res);
	}

	public static String jsonToString(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String StringToHex(String s) throws UnsupportedEncodingException {
		byte[] b = s.getBytes("gbk");
		String a = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}

			a = a + hex;
		}

		return a;
	}

	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-","").toUpperCase();
	}

	// 获取7天后的日期
	public static String getAfter7Day(String start) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sf.parse(start);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,7);
		return sf.format(calendar.getTime());
	}

	public static void main(String[] args) throws ParseException {
		String after7Day = BaseUtil.getAfter7Day("2017-09-04");
		System.out.println(after7Day);
	// 获取7天前的日期
	public static String getBegin7Day(String start) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sf.parse(start);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,-7);
		return sf.format(calendar.getTime());
	}

	public static void main(String[] args) throws ParseException {
		String after7Day = BaseUtil.getAfter7Day("2017-09-04");
		System.out.println(after7Day);
		String begin7Day = BaseUtil.getBegin7Day("2017-09-04");
		System.out.println(begin7Day);
	}
}
