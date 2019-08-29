package com.haveatry.climbworm.service;

import java.util.*;
import java.util.regex.Pattern;

import com.haveatry.climbworm.model.JsoupModel;
import com.haveatry.climbworm.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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

    public JsoupModel getElements(JsoupModel jsoupModel) {
        List<Result> results = new ArrayList<>();
        Document doc = jsoupModel.getDocument();
        if (doc != null) {
            List<Element> elements = doc.getElementsByTag(jsoupModel.getTagName());
            if (elements != null && elements.size() > 0) {
                Result result = null;
                for (Element element : elements) {
                    result = new Result();
                    if (!"".equals(jsoupModel.getKey())) {
                        result.setValue(element.attr(jsoupModel.getValue()));
                    } else {
                        result.setValue(element.text());
                    }
                    results.add(result);
                }
                jsoupModel.setResults(results);
            }
        }
        return jsoupModel;
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
                            String srcValue = element.attr(attr);
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
