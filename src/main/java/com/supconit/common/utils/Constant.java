package com.supconit.common.utils;
public interface Constant {
	//附件表（ATTACHEMENT）模块类型（MODEL_TYPE）字段
	public final static String ATTACHEMENT_DEVICE_DOS = "device_dos";//设备随机档案
	public final static String ATTACHEMENT_DEVICE_IMG = "device_img";//设备图片
	public final static String ATTACHEMENT_REPAIR = "repair";//设备维修单
	public final static String ATTACHEMENT_MAINTAIN = "maintain";//设备保养单
	public final static String ATTACHEMENT_INSPECTION = "inspection";//设备巡检单
	public final static String ATTACHEMENT_CONTRACT = "contract";//合同
	public final static String ATTACHEMENT_SPEC_MAINTAIN = "spec_maintain";//特种设备保养单
	public final static String ATTACHEMENT_DEVICE_CATEGORY = "device_category";//设备分类
	public final static String SPARE_DEVICE = "spare_device";//随机备件
    final static String ATTACHMENT_REPAIR = "repair";//维修单
    public final static String ATTACHMENT_CAR_IMG="car_img";//车辆出入记录图片	
	public final static String ALARM_DISAPPEAR= "报警已消失";//Alarmcontrol
	public final static String ATTACHEMENT_LEAVE = "leave";//请假单
	public final static String ATTACHEMENT_VISITOR = "visitor";//访客
	public final static String ATTACHEMENT_DEVCE_SPARE_IMG = "device_spare_img";//随机备件图片
	public final static String ATTACHEMENT_DEVCE_SPARE_DOC = "device_spare_doc";//随机备件文档
	//---------------------- end-----------------------	
	//对应设备类别表中的类别编码start---------------------
	public final static String DEVICE_SXT = "VS";//视频监控
	public final static String DEVICE_SXT_BQ = "HB";//半球
	public final static String DEVICE_SXT_KQ = "FB";//快球
	public final static String DEVICE_SXT_QJ = "RB";//枪机
	public final static String DC_AHU = "AHU";//新风空调
	public final static String DC_KTS = "KTS";//空调水
	public final static String DC_KTS_CHILD = "AHU,FAN,GL,CH";//空调水包含的设备种类
	public final static String DC_PS = "PS";//巡更
	public final static String DC_ES = "ES";//电梯
	public final static String DC_EL = "LA";//漏电
	public final static String DC_PMS = "PMS";//停车场
	public final static String DC_PMS_PS = "PMS_PS";//停车场车位
	public final static String DC_PTD_LVP = "PTD_LVP";//低压配电柜断路器
	public final static String DC_REM_ETH = "REM_ETH";//机房温湿度传感器
	public final static String DC_BA_VRV = "BA_VRV";//空调VRV
	public final static String DC_BA_UNIT = "BA_UNIT";//空调外机
	//---------------------- end-----------------------
	//-----------------------点位后缀start------------------
	public final static String BIT_STATUS = "_ST";//通常的状态点位
	public final static String BIT_OPEN = "_SET";//通常的开关控制点位
	public final static int BIT_OPEN_VALUE = 1;//开状态点位值
	public final static String BIT_ES_DIRECTION = "_DIRECTION";//上行、下行（运行方向）
	//-------------------------end------------------------
	
	
	public final static Integer NH_TYPE_D=1;//电
	public final static Integer NH_TYPE_S=2;//水
	public final static Integer NH_TYPE_Q=3;//汽
	public final static Integer NH_TYPE_EN=4;//能量
	public final static Integer RULE_FLAG_PLUS=1;//加
	public final static Integer RULE_FLAG_DECREASE=0;//减
}
