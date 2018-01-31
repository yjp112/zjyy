package com.supconit.common.utils;

import java.util.Arrays;

public enum TaskPlanStatusEnum {
	SAVED(0, "草稿状态"), 
	EXECUTE(1,"任务执行中"),
    CHECK(2, "任务验收中"), 
    COMPLETE(3, "任务完成"), 
    REFUSE(-1, "拒绝执行");
    private long value;
    private String desc;
    
	private TaskPlanStatusEnum(long value, String desc) {
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
    
	public static TaskPlanStatusEnum from(long value) {
        for (TaskPlanStatusEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        throw new IllegalArgumentException(String.format(
                        "非法的输入参数 '%s' ! 必须是%s中的其中一个。", value,
                        Arrays.asList(values()).toString()));
    }
}
