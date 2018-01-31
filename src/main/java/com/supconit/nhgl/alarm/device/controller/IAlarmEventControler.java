package com.supconit.nhgl.alarm.device.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.alarm.device.entities.IRealAlarm;
import com.supconit.nhgl.alarm.device.service.MAlarmEventService;

import hc.base.domains.Pagination;
import hc.mvc.annotations.FormBean;



@Controller
@RequestMapping("nhgl/alarm/device")
public class IAlarmEventControler extends BaseControllerSupport{
	@Autowired
	private MAlarmEventService eventService;
	
	/**
	 * 准备实体对象。
	 * 
	 * @return
	 */	
	@ModelAttribute("preparesiRealAlarm")
	public IRealAlarm preparesiRealAlarm() {
		return new IRealAlarm();
	}
	@RequestMapping("showRealAlarm")
	public String showRealAlarm(ModelMap model) {
		return "nhgl/alarm/device/showRealAlarms";
	}
	
	@ResponseBody
	@RequestMapping("getRealAlarmPage")
	public Pagination<IRealAlarm> getRealAlarmPage(ModelMap model,Pagination<IRealAlarm> pager,
			@FormBean(value = "condition", modelCode = "preparesiRealAlarm") IRealAlarm condition ){
		 this.eventService.findRealAlarmsByConditions(pager, condition);
	     return pager;
	}
	@RequestMapping("alarm")
	public String alarm(ModelMap model,@RequestParam(required = false) Long id){
		IRealAlarm realAlarm=eventService.findRealAlarmById(id);
		model.put("realAlarm", realAlarm);
		return "nhgl/alarm/device/alarmInfo";
	}
}
