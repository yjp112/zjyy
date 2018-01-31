package com.supconit.meeting.entities;

import java.util.Date;
import java.util.List;

import com.supconit.common.web.entities.AuditExtend;

/**
 * 预定会议类
 * @author yuhuan
 */
public class RoomReservation extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	private Long reservePersonid;//预订人
	private Long roomId;//会议室ID
	private Date meetingStart;//预订时间起
	private Date meetingEnd;//预订时间止
	private Date actualMeetingStart;//实际时间起
	private Date actualMeetingEnd;//实际时间止
	private Long meetingCompere;//会议主持人ID
	private String meetingCompereName;//会议主持人姓名
	private String meetingSubject;//会议主题
	private String meetingProgram;//会议议程
	
	//查询用
	private String meetingDateStart;//会议时间起
	private String meetingDateEnd;//会议时间止
	
	private List<Long> roomIdList;//多个会议室ID
	
	private List<MeetingPerson> personDetailList;//与会人员
	
	private RoomInfo roomInfo=new RoomInfo();//会议室信息
	private Integer attendeePersonNum;//出席会议人数
	private String way;
	private Long deviceId;//设备ID
	
	public Long getReservePersonid() {
		return reservePersonid;
	}
	public void setReservePersonid(Long reservePersonid) {
		this.reservePersonid = reservePersonid;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public Date getMeetingStart() {
		return meetingStart;
	}
	public void setMeetingStart(Date meetingStart) {
		this.meetingStart = meetingStart;
	}
	public Date getMeetingEnd() {
		return meetingEnd;
	}
	public void setMeetingEnd(Date meetingEnd) {
		this.meetingEnd = meetingEnd;
	}
	public Long getMeetingCompere() {
		return meetingCompere;
	}
	public void setMeetingCompere(Long meetingCompere) {
		this.meetingCompere = meetingCompere;
	}
	public String getMeetingCompereName() {
		return meetingCompereName;
	}
	public void setMeetingCompereName(String meetingCompereName) {
		this.meetingCompereName = meetingCompereName;
	}
	public String getMeetingSubject() {
		return meetingSubject;
	}
	public void setMeetingSubject(String meetingSubject) {
		this.meetingSubject = meetingSubject;
	}
	public String getMeetingProgram() {
		return meetingProgram;
	}
	public void setMeetingProgram(String meetingProgram) {
		this.meetingProgram = meetingProgram;
	}
	public String getMeetingDateStart() {
		return meetingDateStart;
	}
	public void setMeetingDateStart(String meetingDateStart) {
		this.meetingDateStart = meetingDateStart;
	}
	public String getMeetingDateEnd() {
		return meetingDateEnd;
	}
	public void setMeetingDateEnd(String meetingDateEnd) {
		this.meetingDateEnd = meetingDateEnd;
	}
	public List<Long> getRoomIdList() {
		return roomIdList;
	}
	public void setRoomIdList(List<Long> roomIdList) {
		this.roomIdList = roomIdList;
	}
	public List<MeetingPerson> getPersonDetailList() {
		return personDetailList;
	}
	public void setPersonDetailList(List<MeetingPerson> personDetailList) {
		this.personDetailList = personDetailList;
	}
	public RoomInfo getRoomInfo() {
		return roomInfo;
	}
	public void setRoomInfo(RoomInfo roomInfo) {
		this.roomInfo = roomInfo;
	}
	public Integer getAttendeePersonNum() {
		return attendeePersonNum;
	}
	public void setAttendeePersonNum(Integer attendeePersonNum) {
		this.attendeePersonNum = attendeePersonNum;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public Date getActualMeetingStart() {
		return actualMeetingStart;
	}
	public void setActualMeetingStart(Date actualMeetingStart) {
		this.actualMeetingStart = actualMeetingStart;
	}
	public Date getActualMeetingEnd() {
		return actualMeetingEnd;
	}
	public void setActualMeetingEnd(Date actualMeetingEnd) {
		this.actualMeetingEnd = actualMeetingEnd;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	
}
