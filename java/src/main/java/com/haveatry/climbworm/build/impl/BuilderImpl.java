package com.haveatry.climbworm.build.impl;

import com.haveatry.climbworm.build.JsoupBuilder;
import com.haveatry.climbworm.model.JsoupModel;

public class BuilderImpl implements JsoupBuilder<BuilderImpl> {

    private JsoupModel jsoupModel;

    public BuilderImpl(String html) {
        jsoupModel = new JsoupModel(html);
    }

    @Override
    public BuilderImpl useTgName(String tagName) {
        jsoupModel.setTagName(tagName);
        return this;
    }

    public BuilderImpl useInnerHtml(String innerHTML) {
        jsoupModel.setInnerHTML(innerHTML);
        return this;
    }

    public BuilderImpl useAttribute(String attribute, String attributeValue) {
        jsoupModel.setAttribute(attribute);
        jsoupModel.setAttributeValue(attributeValue);
        return this;
    }

    public BuilderImpl addSelector(String selector) {
        jsoupModel.setSelector(selector);
        return this;
    }

    @Override
    public BuilderImpl getLastOrFirstEle(boolean first, boolean last, int index) {
        if (first) {
            jsoupModel.setFirst(first);
        } else if (last) {
            jsoupModel.setLast(last);
        } else
            jsoupModel.setIndex(index);
        return this;
    }
}
