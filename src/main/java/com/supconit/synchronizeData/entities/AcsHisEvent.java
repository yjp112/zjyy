package com.supconit.synchronizeData.entities;

import java.util.Date;


/**
 * 门禁历史事件
 * @author wangbo
 */

public class AcsHisEvent {    
	
    	private static final long	serialVersionUID	= 5225454907469638711L;
        
        private String personIds; //人员 ID(Integer 类型)，多个人员使用”,”隔开,个数要求少于1000	
        private String personId;//人员 ID
        private String personName;//人员名称，模糊搜索
        private String doorSyscodes;//门禁点 syscode 数组，多个门禁点使用“,”隔开,个数要求少于50	
        private String cardNums;//卡号，多个卡号使用”,”隔开
        private Integer eventType;//事件码，不支持数组
        private Long startTime;//开始时间
        private Long endTime;//结束时间
		private Date startDate;//开始时间
        private Date endDate;//结束时间
        private String acEventId;//事件ID
		private String eventName;//事件类型描述
        private String cardNo;//卡号
        private Long eventTime;//事件发生事件戳，Long 型数字（毫秒值）
		private Date eventDate;//事件发生时间
        private String doorName;//门禁点名称
        private String doorSyscode;//门禁点 syscode
        private Integer deviceId;//事件发生的设备 Id
        private String deviceName;//事件发生的设备名称
        private Integer cardStatus;//卡片类型
        
        public Date getEventDate() {
        	if(this.eventTime!=null){
//            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                return new Date(this.eventTime);
        	}
			return eventDate;
		}
		public void setEventDate(Date eventDate) {
			this.eventDate = eventDate;
		}
        public String getPersonIds() {
			return personIds;
		}
		public void setPersonIds(String personIds) {
			this.personIds = personIds;
		}
		public String getPersonId() {
			return personId;
		}
		public void setPersonId(String personId) {
			this.personId = personId;
		}
		public String getPersonName() {
			return personName;
		}
		public void setPersonName(String personName) {
			this.personName = personName;
		}
		public String getDoorSyscodes() {
			return doorSyscodes;
		}
		public void setDoorSyscodes(String doorSyscodes) {
			this.doorSyscodes = doorSyscodes;
		}
		public String getCardNums() {
			return cardNums;
		}
		public void setCardNums(String cardNums) {
			this.cardNums = cardNums;
		}
		public Integer getEventType() {
			return eventType;
		}
		public void setEventType(Integer eventType) {
			this.eventType = eventType;
		}
		public Long getStartTime() {
			return startTime;
		}
		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}
		public Long getEndTime() {
			return endTime;
		}
		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public String getAcEventId() {
			return acEventId;
		}
		public void setAcEventId(String acEventId) {
			this.acEventId = acEventId;
		}
		public String getEventName() {
			return eventName;
		}
		public void setEventName(String eventName) {
			this.eventName = eventName;
		}
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		public Long getEventTime() {
			return eventTime;
		}
		public void setEventTime(Long eventTime) {
			this.eventTime = eventTime;
		}
		public String getDoorName() {
			return doorName;
		}
		public void setDoorName(String doorName) {
			this.doorName = doorName;
		}
		public String getDoorSyscode() {
			return doorSyscode;
		}
		public void setDoorSyscode(String doorSyscode) {
			this.doorSyscode = doorSyscode;
		}
		public Integer getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(Integer deviceId) {
			this.deviceId = deviceId;
		}
		public String getDeviceName() {
			return deviceName;
		}
		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}
		public Integer getCardStatus() {
			return cardStatus;
		}
		public void setCardStatus(Integer cardStatus) {
			this.cardStatus = cardStatus;
		}
		

}

