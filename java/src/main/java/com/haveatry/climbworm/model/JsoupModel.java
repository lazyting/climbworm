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
    private int index;
    private List<Result> results;//处理后的结果

    public JsoupModel(String html) {
        this.html = html;
        if (!StringUtils.endsWithIgnoreCase("-1", html))
            this.document = Jsoup.parse(html);
    }

    public JsoupModel clearModel() {
        this.setSelector("");
        this.setTagName("");
        this.setInnerHTML("");
        this.setAttribute("");
        this.setAttributeValue("");
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
