package com.haveatry.climbworm.service;

import java.util.*;
import java.util.regex.Pattern;

import com.haveatry.climbworm.model.JsoupModel;
import com.haveatry.climbworm.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupHandle {

    /**
     * 获取ip网所有有用ip
     *
     * @param html
     * @return
     */
    public static List<Map<String, Object>> getElement(String html) {
        List<Map<String, Object>> maps = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        List<Element> elementsTBody = doc.getElementsByTag("tbody");
        if (elementsTBody != null && elementsTBody.size() > 0) {
            List<Element> elementTrs = elementsTBody.get(0).getElementsByTag("tr");
            if (elementTrs != null && elementTrs.size() > 0) {
                for (Element elementTr : elementTrs) {
                    List<Element> elementIPs = elementTr.getElementsByAttributeValue("data-title", "IP");
                    List<Element> elementPORTs = elementTr.getElementsByAttributeValue("data-title", "PORT");
                    if (elementIPs != null && elementIPs.size() > 0 && elementPORTs != null
                            && elementPORTs.size() > 0) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("ip", elementIPs.get(0).text());
                        map.put("port", elementPORTs.get(0).text());
                        maps.add(map);
                    }
                }
            }
        }
        return maps;
    }

    /**
     * 解析html，获取指定属性以及指定值的元素的显示值
     *
     * @param html
     * @param key
     * @param value
     * @return
     */

    public static String getElementByAttribute(String html, String key, String value) {
        String getValue = null;
        Document doc = Jsoup.parse(html);
        if (doc != null) {
            List<Element> elements = doc.getElementsByAttributeValue(key, value);
            if (elements != null && elements.size() > 0) {
                getValue = elements.get(0).getElementsByAttributeValue(key, value).text();
            }
        }
        return getValue;
    }

    /**
     * 解析html，获取指定属性以及指定值的元素的显示值
     *
     * @param html
     * @param key
     * @param value
     * @return
     */

    public static List<String> getElements(String html, String key, String value, String property, String attr,
                                           String tagname) {
        List<String> strs = new ArrayList<>();
        Document doc = null;
        if (html != null) {
            doc = Jsoup.parse(html);
        }
        String regex = "[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(regex);

        if (doc != null) {
            List<Element> elements = doc.getElementsByAttributeValue(key, value);
            if (elements != null && elements.size() > 0) {
                for (Element element : elements) {
                    if ("attr".equals(property) && !"".equals(tagname)) {
                        List<Element> tagNameElements = element.getElementsByTag(tagname);
                        for (Element e : tagNameElements) {
                            if (!p.matcher(e.attr(attr)).find()) {
                                strs.add(e.attr(attr));
                            }

                        }
                    } else if ("attr".equals(property) && "".equals(tagname)) {
                        String getAttr = element.attr(attr);
                        if (!p.matcher(getAttr).find()) {
                            if (!getAttr.contains("https")) {
                                strs.add(element.attr(attr));
                            }
                        }
                    } else {
                        if (!p.matcher(element.text()).find()) {
                            strs.add(element.text());
                        }

                    }

                }
            }
        }
        return strs;
    }

    public static JsoupModel getElements(JsoupModel jsoupModel) {
        Elements elements = null;
        Element element = null;
        Document document = jsoupModel.getDocument();
        if (StringUtils.isNotEmpty(jsoupModel.getInnerHTML())) {
            if (jsoupModel.isFirst()) {
                element = document.select(jsoupModel.getSelector()).first();
            } else if (jsoupModel.isLast()) {
                element = document.select(jsoupModel.getSelector()).last();
            }
        }
        if (element != null) {
            jsoupModel.setHtml(element.toString());
            return jsoupModel.clearModel();
        }
        if (StringUtils.isNotEmpty(jsoupModel.getTagName())) {
            if (jsoupModel.isFirst()) {
                element = document.select(jsoupModel.getTagName()).first();
            } else if (jsoupModel.isLast()) {
                element = document.select(jsoupModel.getTagName()).last();
            } else {
                elements = document.getElementsByTag(jsoupModel.getTagName());
            }
        }
        if (jsoupModel.isFirst() || jsoupModel.isLast()) {
            if (element != null) {
                jsoupModel.setHtml(element.toString());
                return jsoupModel.clearModel();
            }
        }
        if (elements != null && elements.size() > 0) {
            jsoupModel.setHtml(elements.toString());
            return jsoupModel.clearModel();
        }
        if (StringUtils.isNotEmpty(jsoupModel.getAttribute()) && StringUtils.isEmpty(jsoupModel.getAttributeValue())) {
            //获取包含此attribute（属性）的所有元素及其子元素
            elements = document.getElementsByAttribute(jsoupModel.getAttribute());
        } else if (StringUtils.isNotEmpty(jsoupModel.getAttribute()) && StringUtils.isNotEmpty(jsoupModel.getAttributeValue())) {
            //获得属性名为getAttribute的值，属性值为getAttributeValue的值的元素及其子元素
            elements = document.getElementsByAttributeValue(jsoupModel.getAttribute(), jsoupModel.getAttributeValue());
        }
        if (elements != null && elements.size() > 0) {
            jsoupModel.setHtml(elements.toString());
            return jsoupModel.clearModel();
        }
        if (StringUtils.isNotEmpty(jsoupModel.getSelector())) {
            //直接使用css选择器
            elements = document.select(jsoupModel.getSelector());
        }
        if (elements != null && elements.size() > 0) {
            jsoupModel.setHtml(elements.toString());
            return jsoupModel.clearModel();
        }
        jsoupModel.setHtml("-1");
        return jsoupModel.clearModel();
    }

    public static JsoupModel getAttributeValues(JsoupModel jsoupModel) {
        Document document = jsoupModel.getDocument();
        Elements elements;
        if (StringUtils.isNotEmpty(jsoupModel.getTagName())) {
            elements = document.getElementsByTag(jsoupModel.getTagName());
        } else {
            elements = document.getAllElements();
        }
        List<Result> results = new ArrayList<>();
        String attr = jsoupModel.getAttribute();
        Result result;
        if (StringUtils.isNotEmpty(attr)) {
            for (Element element : elements) {
                result = new Result();
                result.setKey(jsoupModel.getAttribute());
                result.setValue(element.attr(attr));
                results.add(result);
            }
            jsoupModel.setResults(results);
        }
        return jsoupModel.clearModel();
    }

    public static List<?> getElements(String html, String tagName, String attrName, String attrValue,
                                      String propertyName, String attr, boolean flag) {
        Document doc = null;
        List<Object> maps = new ArrayList<>();
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
                        map.put("name", element.text());
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

    public static List<?> getElements(JsoupModel jsoupModel, String aa) {
        Document document = jsoupModel.getDocument();
        if (StringUtils.isNotEmpty(jsoupModel.getInnerHTML())) {
            Element element = document.select("a:contains(" + jsoupModel.getInnerHTML() + ")").last();
            System.out.println(element);
        }
        return null;
    }

}
