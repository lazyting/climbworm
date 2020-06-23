package com.haveatry.climbworm.utils;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 纯记录Jsoup方法
 */
public class JSoupUtil {


    /**
     * 根据id查询
     *
     * @param html
     * @param id
     * @return
     */
    public static Element getElementById(String html, String id) {
        Document document = Jsoup.parse(html);
        return document.getElementById(id);
    }

    /**
     * 根据组件名查找
     *
     * @param html
     * @param tagName div
     * @return
     */
    public static Elements getElementsByTag(String html, String tagName) {
        Document document = Jsoup.parse(html);
        return document.getElementsByTag(tagName);
    }

    /**
     * 根据样式名查找
     *
     * @param html
     * @param className
     * @return
     */
    public static Elements getElementsByClass(String html, String className) {
        Document document = Jsoup.parse(html);
        return document.getElementsByClass(className);
    }

    /**
     * 查找具有命名属性集的元素。不区分大小写。
     * 参数：key - 属性的名称，例如 href
     *
     * @param html
     * @param key
     * @return
     */
    public static Elements getElementsByAttribute(String html, String key) {
        Document document = Jsoup.parse(html);
        return document.getElementsByAttribute(key);
    }

    /**
     * 查找具有以提供的前缀开头的属性名称的元素。使用 data-发现有HTML5数据集的元素。keyPrefix - 属性的名称前缀，例如 data-
     *
     * @param html
     * @param keyPrefix
     * @return 属性名称以前缀开头的元素，如果没有，则为空。
     */
    public static Elements getElementsByAttributeStarting(String html, String keyPrefix) {
        Document document = Jsoup.parse(html);
        return document.getElementsByAttributeStarting(keyPrefix);
    }

    /**
     * 查找具有特定值属性的元素。不区分大小写。
     *
     * @param html
     * @param key
     * @param value
     * @return
     */
    public static Elements getElementsByAttributeValue(String html, String key, String value) {
        Document document = Jsoup.parse(html);
        return document.getElementsByAttributeValue(key, value);
    }

    /**
     * 查找具有以值前缀开头的属性的元素。不区分大小写。valuePrefix - 属性值的开始
     *
     * @param html
     * @param key
     * @param valuePrefix
     * @return
     */
    public static Elements getElementsByAttributeValueStarting(String html, String key, String valuePrefix) {
        Document document = Jsoup.parse(html);
        return document.getElementsByAttributeValueStarting(key, valuePrefix);
    }

    /**
     * 查找具有以值前缀结尾的属性的元素。不区分大小写。valueSuffix - 属性值的结束
     *
     * @param html
     * @param key
     * @param valueSuffix
     * @return
     */
    public static Elements getElementsByAttributeValueEnding(String html, String key, String valueSuffix) {
        Document document = Jsoup.parse(html);
        return document.getElementsByAttributeValueEnding(key, valueSuffix);
    }

    /**
     * 查找具有值包含匹配字符串的属性的元素。不区分大小写。
     *
     * @param html
     * @param key
     * @param match
     * @return
     */
    public static Elements getElementsByAttributeValueContaining(String html, String key, String match) {
        Document document = Jsoup.parse(html);
        return document.getElementsByAttributeValueContaining(key, match);
    }

    /**
     * 获取此元素及其所有子元素的组合文本。空格被标准化和修剪。
     * 例如，给定HTML <p>Hello <b>there</b> now! </p>，p.text()返回"Hello there now!"
     *
     * @param element
     * @return
     */
    public static String text(Element element) {
        return element.text();
    }

    /**
     * 获取此元素所拥有的文本; 不会得到所有子元素的组合文本。
     * 例如，给定HTML <p>Hello <b>there</b> now!</p>，p.ownText()返回"Hello now!"，而p.text()返回"Hello there now!"。请注意，b元素中的文本不会返回，因为它不是p元素的直接文本。
     *
     * @param element
     * @return
     */
    public static String ownText(Element element) {
        return element.ownText();
    }

    /**
     * 获取此元素的“class”属性的文字值，该属性可能包含多个类名，空格分隔。（例如，在 <div class="header gray">退货时，“ header gray”）
     *
     * @param element
     * @return
     */
    public static String className(Element element) {
        return element.className();
    }

    /**
     * 获取所有元素的类名。例如，在元素上 <div class="header gray">，返回一组两个元素 "header", "gray"
     *
     * @param element
     * @return
     */
    public static Set<String> classNames(Element element) {
        return element.classNames();
    }

    /**
     * 检索元素的内部HTML。例如，在 <div>一个空的时候 <p>，会返回 <p></p>。
     *
     * @param element
     * @return
     */
    public static String html(Element element) {
        return element.html();
    }
}
