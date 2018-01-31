package com.supconit.common.utils;

import java.util.Arrays;

/**
 * @文 件 名：alarmState.java
 * @创建日期：2013年9月3日
 * @版    权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：李桂平
 * @版    本: 
 * @描    述：报警状态
 */
public enum AlarmStateEnum {
    STATE1(1, "正常"), 
    STATE2(2, "确认"), 
    STATE3(3, "忽略"), 
    STATE4(4, "正在处理");
    private long value;
    private String desc;

    private AlarmStateEnum(long value, String description) {
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

    public static AlarmStateEnum from(long value) {
        for (AlarmStateEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        throw new IllegalArgumentException(String.format(
                        "非法的输入参数 '%s' ! 必须是%s中的其中一个。", value,
                        Arrays.asList(values()).toString()));
    }

}
