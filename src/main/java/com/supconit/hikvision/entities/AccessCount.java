package com.supconit.hikvision.entities;

import java.util.List;

import com.supconit.common.web.entities.AuditExtend;

import jodd.datetime.JDateTime;

public class AccessCount extends AuditExtend {


	private static final long serialVersionUID = 6336893733255084827L;
	private Long personId;
	private String personNo;
	private String personName;
	private String cardNo;
	private Long comeDate;
	private String comeDateString;
	private String comeDateText;
	private Long offDate;
	private String offDateString;
	private Long comeStatus;
	private String comeStatusString;
	private Long offStatus;
	private String offStatusString;

	private Long deptId;
	private List<Long> deptChildIds;
	

	private String offDateText;
	private String startDate;
	private String endDate;
	private String eventDate;
	private String eventDayOfWeek;

	/** 手机端使用 **/
	private int totalAttendance;//总共考勤天数
	private int normalAttendance;//正常考勤天数
	private int unusualAttendance;//异常考勤天数
	private String dataDate;//日历上展示时间用
	private String dataDateCss;//日历上展示CSS
	private String showComeTime;//展示HH24：MI
	private String showOffTime;//展示HH24：MI
	private String showWeekDay;//展示星期几
	private String showEventDate;//展示MM-DD

	public Long getComeStatus() {
		return comeStatus;
	}
	public void setComeStatus(Long comeStatus) {
		this.comeStatus = comeStatus;
	}
	public String getComeStatusString() {
		if(comeStatus==null){
			comeStatusString = "";
			return comeStatusString;
		}
		switch (comeStatus.intValue()) {
		case 0:
			comeStatusString = "正常";
			break;
		case 1:
			comeStatusString = "异常";//迟到
			break;
		default:
			comeStatusString = "";
			break;
		}
		return comeStatusString;
	}
	public void setComeStatusString(String comeStatusString) {
		this.comeStatusString = comeStatusString;
	}
	public Long getOffStatus() {
		return offStatus;
	}
	public void setOffStatus(Long offStatus) {
		this.offStatus = offStatus;
	}
	public String getOffStatusString() {
		if(null==offStatus){
			offStatusString = "";
			return offStatusString;
		}
		switch (offStatus.intValue()) {
		case 0:
			offStatusString = "正常";
			break;
		case 1:
			offStatusString = "异常";//早退
			break;
		default:
			offStatusString = "";
			break;
		}
		return offStatusString;
	}
	public void setOffStatusString(String offStatusString) {
		this.offStatusString = offStatusString;
	}
	public String getComeDateString() {
		if(comeDate==null||comeDate==0L){
			return "";
		}
		this.comeDateString =new JDateTime(comeDate).toString("YYYY-MM-DD hh:mm:ss");
		return this.comeDateString;
	}
	public void setComeDateString(String comeDateString) {
		this.comeDateString = comeDateString;
	}
	public String getOffDateString() {
		if(offDate==null||offDate==0L){
			return "";
		}
		this.offDateString =new JDateTime(offDate).toString("YYYY-MM-DD hh:mm:ss");
		return this.offDateString;
	}
	public void setOffDateString(String offDateString) {
		this.offDateString = offDateString;
	}

	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getPersonNo() {
		return personNo;
	}
	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Long getComeDate() {
		return comeDate;
	}
	public void setComeDate(Long comeDate) {
		this.comeDate = comeDate;
	}
	public String getComeDateText() {
		return comeDateText;
	}
	public void setComeDateText(String comeDateText) {
		this.comeDateText = comeDateText;
	}
	public Long getOffDate() {
		return offDate;
	}
	public void setOffDate(Long offDate) {
		this.offDate = offDate;
	}
	public String getOffDateText() {
		return offDateText;
	}
	public void setOffDateText(String offDateText) {
		this.offDateText = offDateText;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public List<Long> getDeptChildIds() {
		return deptChildIds;
	}
	public void setDeptChildIds(List<Long> deptChildIds) {
		this.deptChildIds = deptChildIds;
	}

	public int getTotalAttendance() {
		return totalAttendance;
	}

	public void setTotalAttendance(int totalAttendance) {
		this.totalAttendance = totalAttendance;
	}

	public int getNormalAttendance() {
		return normalAttendance;
	}

	public void setNormalAttendance(int normalAttendance) {
		this.normalAttendance = normalAttendance;
	}

	public int getUnusualAttendance() {
		return unusualAttendance;
	}

	public void setUnusualAttendance(int unusualAttendance) {
		this.unusualAttendance = unusualAttendance;
	}

	public String getDataDate() {
		if(eventDate==null||"".equals(eventDate))
		{
			return "";
		}
		else
		{
			String[] date=eventDate.split("-");
			String year=date[0];
			String month=String.valueOf(Integer.valueOf(date[1])-1);
			String day=String.valueOf(Integer.valueOf(date[2]));
			return year+"-"+month+"-"+day;
		}
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public String getDataDateCss() {
		if(comeStatus==null&&offStatus==null){
			dataDateCss = "";
			return dataDateCss;
		}
		if(comeStatus==1||offStatus==1)
		{
			dataDateCss = "0";
		}
		else if(comeStatus==0&&offStatus==0)
		{
			dataDateCss = "1";
		}
		else
		{
			dataDateCss = "";
		}
		return dataDateCss;
	}

	public void setDataDateCss(String dataDateCss) {
		this.dataDateCss = dataDateCss;
	}
	public String getEventDayOfWeek() {
		return eventDayOfWeek;
	}
	public void setEventDayOfWeek(String eventDayOfWeek) {
		this.eventDayOfWeek = eventDayOfWeek;
	}

	public String getShowComeTime() {
		return showComeTime;
	}

	public void setShowComeTime(String showComeTime) {
		this.showComeTime = showComeTime;
	}

	public String getShowOffTime() {
		return showOffTime;
	}

	public void setShowOffTime(String showOffTime) {
		this.showOffTime = showOffTime;
	}

	public String getShowWeekDay() {
		return showWeekDay;
	}

	public void setShowWeekDay(String showWeekDay) {
		this.showWeekDay = showWeekDay;
	}

	public String getShowEventDate() {
		return showEventDate;
	}

	public void setShowEventDate(String showEventDate) {
		this.showEventDate = showEventDate;
	}
}
