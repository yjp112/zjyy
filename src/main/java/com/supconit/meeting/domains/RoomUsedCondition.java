package com.supconit.meeting.domains;

/**
 * 会议使用情况类
 * @author yuhuan
 */
public class RoomUsedCondition {
	private Long id;//预定会议ID
	private String color;//颜色 1：会议已结束 2：会议正在进行 3：会议还未召开  4：会议未预定
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
