package com.supconit.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.supconit.base.daos.EnumDetailDao;
import com.supconit.base.entities.EnumDetail;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.honeycomb.cache.Cache;
import com.supconit.honeycomb.util.StringUtils;

/**
 * @文 件 名：DictUtils.java
 * @创建日期：2013年7月5日
 * @版 权：Copyrigth(c)2013
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版 本:
 * @描 述：字典工具类
 */
public class DictUtils {
	private static Cache cache = SpringContextHolder.getBean(Cache.class);
	private static EnumDetailDao enumDetailDao = SpringContextHolder
			.getBean(EnumDetailDao.class);
	private static String group = EnumDetail.class.getName();
	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	/**
	 * @方法名称:getDictList
	 * @作 者:丁阳光
	 * @创建日期:2013年7月5日
	 * @方法描述:根据typeId获取字典列表
	 * @param typeEnum
	 * @return List<EnumDetail>
	 */
	public static List<EnumDetail> getDictList(DictTypeEnum typeEnum) {
		return getDictList(typeEnum.value);
	}
	public static List<EnumDetail> getDictList(long typeId) {
		rwl.readLock().lock();
		try {
			List<EnumDetail> result = cache.get(group, String.valueOf(typeId));
			if (result == null) {
				rwl.readLock().unlock();
				rwl.writeLock().lock();
				try {
					if (result == null) {
						result = enumDetailDao.selectByTypeId(typeId);
						cache.put(group, String.valueOf(typeId), result);
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}finally {
					rwl.writeLock().unlock(); // Unlock write, still hold read
				}
				rwl.readLock().lock();
			}
			return result;
		} finally {
			rwl.readLock().unlock();
		}
		
	}

	/**
	 * @方法名称:getDictLabel
	 * @作 者:丁阳光
	 * @创建日期:2013年7月5日
	 * @方法描述: 根据typeId和value 获取label
	 * @param typeEnum
	 * @param value
	 * @return String
	 */
	public static String getDictLabel(DictTypeEnum typeEnum, String value) {
		if (typeEnum != null && StringUtils.isNotBlank(value)) {
			for (EnumDetail detail : getDictList(typeEnum)) {
				if (value.equals(detail.getEnumValue())) {
					return detail.getEnumText();
				}
			}
		}
		return "";
	}
	/**根据typeId和value 获取一行数据
	 * @param typeEnum
	 * @param value
	 * @return
	 */
	public static EnumDetail getDictItem(DictTypeEnum typeEnum, String value) {
		if (typeEnum != null && StringUtils.isNotBlank(value)) {
			for (EnumDetail detail : getDictList(typeEnum)) {
				if (value.equals(detail.getEnumValue())) {
					return detail;
				}
			}
		}
		return null;
	}

	
	/**
	 * @方法名称:getDictText
	 * @方法描述: 根据EnumDetails和label 获取 value
	 * @param EnumDetails
	 * @param label
	 * @return String
	 */
	public static String getDictValue(List<EnumDetail> EnumDetails, String label) {
		for(EnumDetail num:EnumDetails){
			if(num.getEnumText().equals(label)){
				return num.getEnumValue();
			}
		}
		return "";
	}
	
	/**
	 * @方法名称:getDictValue
	 * @作 者:丁阳光
	 * @创建日期:2013年7月5日
	 * @方法描述: 根据typeId和label 获取value
	 * @param typeEnum
	 * @param label
	 * @return String
	 */
	public static String getDictValue(DictTypeEnum typeEnum, String label) {
		if (typeEnum != null && StringUtils.isNotBlank(label)) {
			for (EnumDetail detail : getDictList(typeEnum)) {
				if (label.equals(detail.getEnumText())) {
					return detail.getEnumValue();
				}
			}
		}
		return "";
	}

	/**
	 * @方法名称:reloadDict
	 * @作 者:丁阳光
	 * @创建日期:2013年7月5日
	 * @方法描述: 使得typeId对应的字典失效。
	 * @param typeEnum
	 *            void
	 */
	public static void evictDict(DictTypeEnum typeEnum) {
		evictDict(String.valueOf(typeEnum.value));
	}
	public static void evictDict(String typeEnumValue) {
		rwl.writeLock().lock();
		try {
			cache.evict(group, typeEnumValue);
		} finally {
			rwl.writeLock().unlock();
		}
	}

	/**
	 * @文 件 名：DictUtils.java
	 * @创建日期：2013年7月11日
	 * @版 权：Copyrigth(c)2013
	 * @公司名称：浙江浙大中控信息技术有限公司
	 * @开发人员：丁阳光
	 * @版 本:
	 * @描 述：字典类型的枚举类
	 */
	public enum DictTypeEnum {
        BILLSTATUS(1, "单据状态"),DEVICE_PROPERTY_STATUS(2, "设备属性_状态"),DEVICE_PROPERTY_INPUT_TYPE(3, "设备属性_数据输入方式")
        ,DEVICE_STATUS(4, "设备_状态") ,DEVICE_DEPRECIATION(5, "设备_折旧方法") ,CONTRACT_STATUS(6, "合同_状态"),DEVICE_CHANGE_STATUS(7, "设备变更_单据状态")
        ,CHANGE_PRICE(8, "调价类型"),STOCK_IN_SOURCE(9, "入库来源"),REPAIR_TOOL_OWNER_TYPE(10,"维修工具_保管属性")
        ,REPAIR_TOOL_USEABLE(11,"维修工具_是否可用"),STOCK_OUT_USETYPE(12, "出库用途"),ALARM_HANDLE_MODE(13, "设备警报处理方式"),TASK_PLAN_STATUS(14,"计划任务_状态")
        ,STANDARD_COAL(15,"标准煤"),POSITIONS(16,"岗位"),PATROL_ADDRESS(17,"巡更地点"),PATROL_DEVICE_STATUS(18,"巡更设备状态"),MENU_LAYER_MODEL(500,"菜单图层模式")
        ,INVISION_TYPE(19,"invision类别"),INVISION_RULE_SWITCH(20,"开关量规则"),INVISION_RULE_SIMULATE(21,"模拟量规则")
        ,FLASHCONFIGMODEL(22,"模式"),MENU_TYPE(23,"菜单type参数"),SYSTEM_CODE(24,"系统图层编码"),SOFT_CODE(25,"程序用编码"),CLICK_FUNCTION(26,"gis单击方法"),CIRCLE_FUNCTION(27,"框选方法")
        ,FLASHCONFIGPOSITION(28,"位置"),MEETING_MULTI_FUNCTIONAL(29,"是否有多功能会议室"),MEETING_NETWORK(30,"网络情况")
        ,MEETING_TYPE(31,"会议室类别"),MEETING_CAPACITY(32,"会议室大小"),VISIT_REASON(33,"来访事由"),ZJLX(34,"证件类型"),VISIT_STATUS(35,"来访状态"),EMERGENCY(36,"紧急程度")
         ,VISIT_RETURN_CARD(37,"是否归还证件"),DEPT_POSITION(38,"部门岗位"),LUNCH_TYPE(39,"餐别"),LUNCH_RESULT(40,"审批结果"),MAINTAIN_CYCLE_UNIT(41,"保养周期单位"),MAINTAIN_TASK_STATUS(42,"保养任务状态"),
         INSPECTION_CYCLE_UNIT(43,"巡检周期单位"),INSPECTION_TASK_STATUS(44,"巡检任务状态"),CELEBRATE(45,"厂庆天数"),TASK_VESTING(55,"任务归属")
        ,DOCUMENT_TYPE(56,"文档分类"),ARALM_ENUM(108,"报警类型"),ROAD_GATE_NAME(109,"闸口名称"),ROAD_GATE_ID(110,"闸口ID")
        ,LEAVE_TYPE(100,"请假类别"),LEAVE_RESULT(101,"请假结果"),LEAVE_APPLY_STATUS(102,"请假流程环节"), POST_NAME(58,"排班岗位"),GROUP_ENUM(105,"班组"),ORDER_ENUM(107,"班次")
        ,TASK_SOURCE(59,"诉求来源"),URGENCY(60,"紧急程度"),CAUSE_TYPE(61,"原因归类"),REPAIR_TYPE(62,"维修类别"),REPAIR_WORK(65,"工种"),NH_TYPE(66,"能耗种类"),STOCK_BACK_SOURCE(67,"归还类型"),AREA_TYPE(103,"区域类别"),EMERGENCY_TYPE(120,"应急类别"),EMERGENCY_HEADER(121,"组长")
        ,FACILITY_TYPE(122,"设施类别"),EDUCACTION(123,"学历"),PERSON_STATUS(124,"在职情况"),PERSONSEX(125,"性别"),POSTIDS(126,"级别"),REPAIR_MODE(63,"维修模式"),REPAIR_WORRY(64,"抢修方式")
        ,FILELENGTH(181,"文件大小"),EXTRACT_NH_TYPE(1000,"能耗类型"),EXTRACT_NH_WEIDU(1001,"统计纬度"),EXTRACT_NH_RELATION(1002,"能耗类型纬度对应关系"),DOOR_EVENT_TYPE(200,"门禁事件"),BPM_TYPE(800,"流程类型");

		private int value;
		private String desc; 

		private DictTypeEnum(int value, String description) {
			this.value = value;
			this.desc = description;
		}

		public int getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

		@Override
		public String toString() {

			return "{" + value + ":" + desc + "}";
		}

		public static DictTypeEnum from(int value) {
			for (DictTypeEnum item : values()) {
				if (item.getValue() == value) {
					return item;
				}
			}
			throw new IllegalArgumentException(String.format(
					"非法的输入参数 '%s' ! 必须是%s中的其中一个。", value,
					Arrays.asList(values()).toString()));
		}

	}
}
