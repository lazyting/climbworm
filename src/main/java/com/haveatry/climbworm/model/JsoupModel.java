package com.haveatry.climbworm.model;


import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class JsoupModel {
    private Document document;//html转化的document
    private String html;//html文档
    private String tagName;//标签名
    private String attribute;//属性名
    private String attributeValue;//属性值
    private String innerHTML;//元素展示的文字
    private String selector;//选择器
    private boolean last;
    private boolean first;
    private boolean flag;
    private Object ext1;
    private Object ext2;
    private Object ext3;
    private List<Result> results;//处理后的结果

    public JsoupModel(String html) {
        this.html = html;
        if (!StringUtils.endsWithIgnoreCase("-1", html))
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

    public JsoupModel useAttribute(String attribute, String attributeValue) {
        this.setAttribute(attribute);
        this.setAttributeValue(attributeValue);
        return this;
    }

    public JsoupModel addSelector(String selector) {
        this.setSelector(selector);
        return this;
    }

    public JsoupModel clearModel() {
        this.setSelector("");
        this.setTagName("");
        this.setInnerHTML("");
        this.setAttribute("");
        this.setAttributeValue("");
        return this;
    }

    public JsoupModel getLastOrFirstEle(boolean first, boolean last) {
        this.setLast(last);
        this.setFirst(first);
        return this;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
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

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
        if (!StringUtils.endsWithIgnoreCase("-1", html))
            this.document = Jsoup.parse(html);
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
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
