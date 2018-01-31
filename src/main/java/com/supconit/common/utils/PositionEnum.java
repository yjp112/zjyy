package com.supconit.common.utils;

import java.util.Arrays;

public enum PositionEnum {
	DIRECTOR(1,"主管"),
	ASSISTANT(2, "副主管"), 
	WORKER(3, "职员");
    private long value;
    private String desc;
    
	private PositionEnum(long value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
    public String toString() {

        return "{" + value + ":" + desc + "}";
    }
    
	public static PositionEnum from(long value) {
        for (PositionEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        throw new IllegalArgumentException(String.format(
                        "非法的输入参数 '%s' ! 必须是%s中的其中一个。", value,
                        Arrays.asList(values()).toString()));
    }
}
