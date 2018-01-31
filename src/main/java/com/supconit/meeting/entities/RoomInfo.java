package com.supconit.meeting.entities;

import java.util.List;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.web.entities.AuditExtend;
import com.supconit.meeting.domains.RoomUsedCondition;

/**
 * 会议信息类
 * @author yuhuan
 */
public class RoomInfo extends AuditExtend{
	private static final long serialVersionUID = 1L;
	
	private String roomName;//会议室名称
	private String roomType;//会议室类别 1:部门会议室;2:中心会议室
	private Integer capacityPerson;//可容纳人数
	private Long useDeptId;//所属部门ID
	private String location;//地理位置
	private Integer network;//网络情况 0:无网络;1:外网;2:内网;3:内外网
	private Integer projector;//是否有投影 0:否;1:是
	private Integer multiFunctional;//是否有多功能会议室 0:否;1:是
	private String remark;//备注
	
	
	private Integer capacity;//会议室大小   1:5人以下;2:5-10人;3:10-20人;4:20人以上
	private String meetingDate;//会议时间
	
	//查询用
	private String meetingDateStart;//会议时间起
	private String meetingDateEnd;//会议时间止
	
	private Integer usedTimes;//会议使用次数
	private double usedPercent;//会议使用百分比
	private String roomTypeName;//类别转化
	private String networkName;//网络情况转化
	private String multiFunctionalName;//多功能会议室转化
	private boolean flag;//查询的是否是自己部门
	private Integer isSign;//是否有签到功能 0：否 1：是
	private String hpid;//读卡器HPID
	private Long deviceId;//设备ID
	
	private List<RoomUsedCondition> meetingUsedConditionList;//预定会议列表中，每个会议每天有17块时间段
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public Integer getCapacityPerson() {
		return capacityPerson;
	}
	public void setCapacityPerson(Integer capacityPerson) {
		this.capacityPerson = capacityPerson;
	}
	public Long getUseDeptId() {
		return useDeptId;
	}
	public void setUseDeptId(Long useDeptId) {
		this.useDeptId = useDeptId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getNetwork() {
		return network;
	}
	public void setNetwork(Integer network) {
		this.network = network;
	}
	public Integer getProjector() {
		return projector;
	}
	public void setProjector(Integer projector) {
		this.projector = projector;
	}
	public Integer getMultiFunctional() {
		return multiFunctional;
	}
	public void setMultiFunctional(Integer multiFunctional) {
		this.multiFunctional = multiFunctional;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}	
	public String getMeetingDate() {
		return meetingDate;
	}
	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
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
	public Integer getUsedTimes() {
		return usedTimes;
	}
	public void setUsedTimes(Integer usedTimes) {
		this.usedTimes = usedTimes;
	}
	public double getUsedPercent() {
		return usedPercent;
	}
	public void setUsedPercent(double usedPercent) {
		this.usedPercent = usedPercent;
	}
	public List<RoomUsedCondition> getMeetingUsedConditionList() {
		return meetingUsedConditionList;
	}
	public void setMeetingUsedConditionList(
			List<RoomUsedCondition> meetingUsedConditionList) {
		this.meetingUsedConditionList = meetingUsedConditionList;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Integer getIsSign() {
		return isSign;
	}
	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}
	public String getHpid() {
		return hpid;
	}
	public void setHpid(String hpid) {
		this.hpid = hpid;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	//*********************************
	public String getRoomTypeName(){
		return DictUtils.getDictLabel(DictTypeEnum.MEETING_TYPE, this.roomType);
	}
	public String getNetworkName() {
		return DictUtils.getDictLabel(DictTypeEnum.MEETING_NETWORK, this.network==null?"":this.network.toString());
	}
	public String getMultiFunctionalName() {
		return DictUtils.getDictLabel(DictTypeEnum.MEETING_MULTI_FUNCTIONAL, this.multiFunctional==null?"":this.multiFunctional.toString());
	}
	public String getDeptName(){
		if(this.useDeptId==null){
			return "";
		}
		return OrganizationUtils.getFullDeptNameByDeptId(this.useDeptId);
	}
	
}
