package com.haveatry.climbworm.model;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class JsoupModel {
    private Document document;//html转化的document
    private String tagName;//标签名
    private String html;//html文档
    private String key;
    private String value;
    private String attributeName;//属性名
    private String attributeValue;//属性值
    private String properName;
    private String properValue;
    private List<Result> results;//处理后的结果
    private String innerHTML;
    private boolean flag;
    private Object ext1;
    private Object ext2;
    private Object ext3;

    public JsoupModel(String html) {
        this.html = html;
        this.document = Jsoup.parse(html);
    }

    public JsoupModel useTgName(String tagName) {
        this.setTagName(tagName);
        return this;
    }

    public JsoupModel useInnerHtml(String innerHTML) {
        this.setInnerHTML(innerHTML);
        return this;
    }

    public JsoupModel useAttribute(String attributeName, String attributeValue) {
        this.setAttributeName(attributeName);
        this.setAttributeValue(attributeValue);
        return this;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getInnerHTML() {
        return innerHTML;
    }

    public void setInnerHTML(String innerHTML) {
        this.innerHTML = innerHTML;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getProperName() {
        return properName;
    }

    public void setProperName(String properName) {
        this.properName = properName;
    }

    public String getProperValue() {
        return properValue;
    }

    public void setProperValue(String properValue) {
        this.properValue = properValue;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getExt1() {
        return ext1;
    }

    public void setExt1(Object ext1) {
        this.ext1 = ext1;
    }

    public Object getExt2() {
        return ext2;
    }

    public void setExt2(Object ext2) {
        this.ext2 = ext2;
    }

    public Object getExt3() {
        return ext3;
    }

    public void setExt3(Object ext3) {
        this.ext3 = ext3;
    }
}
