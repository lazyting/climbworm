package com.haveatry.climbworm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class JSoupUtil {

	/**
	 * Created by lzt on 2018/9/10.
	 */
	public static List<Map<String, Object>> getElements(String html, String tagName, String attrName, String attrValue,
			String propertyName, String attr, boolean flag) {
		Document doc = null;
		List<Map<String, Object>> maps = new ArrayList<>();
		List<Element> elements = null;
		if (StringUtils.isNotBlank(html)) {
			doc = Jsoup.parse(html);
		}
		if (doc != null) {
			if (StringUtils.isNotBlank(tagName)) {
				elements = doc.getElementsByTag(tagName);
			} else if (StringUtils.isNotBlank(attrName) && StringUtils.isNotBlank(attrValue)) {
				elements = doc.getElementsByAttributeValue(attrName, attrValue);
			}
			if (flag) {
				if (elements != null && elements.size() > 0) {
					Map<String, Object> map = null;
					for (Element element : elements) {
						map = new HashMap<>();
						map.put("name", element.html());
						if ("attr".equals(propertyName)) {
							map.put("value", element.attr(attr));
						} else {
							map.put("value", element.text());
						}
						maps.add(map);
					}
				}
			} else {
				if (elements != null && elements.size() > 0) {
					Map<String, Object> map = new HashMap<>();
					for (Element element : elements) {
						map.put("", element.html());
					}
					maps.add(map);
				}
			}
		}
		return maps;
	}
}
