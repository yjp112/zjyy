package com.supconit.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.supconit.common.web.entities.LabelValueBean;

public abstract class LabelValueBeanConverter<T> {
    public abstract LabelValueBean toLabelValueBean(T t);

    public List<LabelValueBean> toLabelValueBean(List<T> objectLists) {
        List<LabelValueBean> resultBeans = new ArrayList<LabelValueBean>();
        for (T t : objectLists) {
            resultBeans.add(toLabelValueBean(t));
        }
        return resultBeans;
    }
}
