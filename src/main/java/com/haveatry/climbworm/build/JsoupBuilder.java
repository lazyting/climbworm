package com.haveatry.climbworm.build;

public interface JsoupBuilder<T> {

    public T useTgName(String tagName);

    public T useInnerHtml(String innerHTML);

    public T useAttribute(String attribute, String attributeValue);

    public T addSelector(String selector);

    public T getLastOrFirstEle(boolean first, boolean last, int index);

    //    private Document document;//html转化的document
//    private String html;//html文档
//    private String tagName;//标签名
//    private String attribute;//属性名
//    private String attributeValue;//属性值
//    private String innerHTML;//元素展示的文字
//    private String selector;//选择器
//    private boolean last;
//    private boolean first;
//    private boolean flag;
//    private Object ext1;
//    private Object ext2;
//    private Object ext3;
}
