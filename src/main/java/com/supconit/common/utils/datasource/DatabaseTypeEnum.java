package com.supconit.common.utils.datasource;

import java.util.Arrays;

public enum DatabaseTypeEnum {
	DB_DLHMC("ywgl", "ywgl数据库,默认的数据库")
	,DB_NHGL("nhgl", "能耗管理数据库")
	,DB_GIS("gis", "GIS数据库");
	private String value;
	private String desc;

	private DatabaseTypeEnum(String value, String description) {
		this.value = value;
		this.desc = description;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {

		return "{" + value + ":" + desc + "}";
	}

	public static DatabaseTypeEnum from(String value) {
		for (DatabaseTypeEnum item : values()) {
			if (item.getValue() == value) {
				return item;
			}
		}
		throw new IllegalArgumentException(String.format(
				"非法的输入参数 '%s' ! 必须是%s中的其中一个。", value, Arrays.asList(values())
						.toString()));
	}

}
