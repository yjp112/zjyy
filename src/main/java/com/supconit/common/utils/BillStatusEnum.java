package com.supconit.common.utils;

import java.util.Arrays;

/**
 * @文 件 名：BillStatusEnum.java
 * @创建日期：2013年7月12日
 * @版    权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版    本: 
 * @描    述：单据状态
 */
public enum BillStatusEnum {

    SAVED(0, "草稿状态"), 
    DOING(1, "审核中"), 
    PASS(2, "审核通过"), 
    NOPASS(-1, "审核不通过");
    private long value;
    private String desc;

    private BillStatusEnum(long value, String description) {
        this.value = value;
        this.desc = description;
    }

    public long getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {

        return "{" + value + ":" + desc + "}";
    }

    public static BillStatusEnum from(long value) {
        for (BillStatusEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        throw new IllegalArgumentException(String.format(
                        "非法的输入参数 '%s' ! 必须是%s中的其中一个。", value,
                        Arrays.asList(values()).toString()));
    }

}
