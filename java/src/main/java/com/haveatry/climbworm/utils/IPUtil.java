package com.haveatry.climbworm.utils;

import com.haveatry.climbworm.service.JsoupHandle;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class IPUtil {

	private static final String IP_ADDRESS = "https://www.kuaidaili.com/free/inha/";// 要加上页数
	public static final String PICT_ADDRESS="http://www.aiimg.com/photoshop/brush_";//要加上页数 和.html
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static List<Map<String, Object>> getIPsMehod() throws ParseException, IOException {
		List<Map<String, Object>> mapIPs = new ArrayList<>();
		for (int i = 1;; i++) {
			String ip = IP_ADDRESS + "" + i;
			String html = GetPageUtils.getPageNoUseIp(ip);
			String timeStr = JsoupHandle.getElementByAttribute(html, "data-title", "最后验证时间");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -2);
			if (timeStr != null) {
				Date date = sdf.parse(timeStr);
				if (date.before(cal.getTime())) {
					break;
				} else {
					List<Map<String, Object>> maps = JsoupHandle.getElement(html);
					mapIPs.addAll(maps);
				}
			}
		}
		return mapIPs;
	}
}
