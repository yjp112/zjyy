package com.supconit.base.services.impl.pojos;

import com.supconit.common.utils.excel.ExcelAnnotation;

public class ImpAlarmLevel{
    private Long id;
	@ExcelAnnotation(exportName = "级别名称")
	private String alarmLevel;
	@ExcelAnnotation(exportName = "级别描述")
	private String levelRemark;
	@ExcelAnnotation(exportName = "优先级")
	private String alarmRank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public String getLevelRemark() {
		return levelRemark;
	}
	public void setLevelRemark(String levelRemark) {
		this.levelRemark = levelRemark;
	}
	public String getAlarmRank() {
		return alarmRank;
	}
	public void setAlarmRank(String alarmRank) {
		this.alarmRank = alarmRank;
	}

}
