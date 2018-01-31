package com.supconit.nhgl.alarm.energy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.alarm.energy.entities.EnergyAlarm;
import com.supconit.nhgl.alarm.energy.service.EnergyAlarmService;

import hc.base.domains.Pagination;
import hc.mvc.annotations.FormBean;
@Controller()
@RequestMapping("nhgl/alarm/energy")
public class EnergyAlarmController extends BaseControllerSupport{
	@Autowired
	public EnergyAlarmService energyAlarmService;
	
	
	@ModelAttribute("preparesenergyAlarm")
	public EnergyAlarm preparesenergyAlarm() {
		return new EnergyAlarm();
	}
	@RequestMapping("list")
	public String list(ModelMap model){
		return "nhgl/alarm/energy/energyAlarm_list";
	}
	
	@ResponseBody
	@RequestMapping("page")
	public Pagination<EnergyAlarm> page(ModelMap model,Pagination<EnergyAlarm> pager,
			@FormBean(value = "condition", modelCode = "preparesenergyAlarm") EnergyAlarm condition ){
		 this.energyAlarmService.findByCondition(pager, condition);
	     return pager;
	}
	
	@RequestMapping("show")
	public String show(ModelMap model,Long id){
		if (null != id) {
			EnergyAlarm energyAlarm=energyAlarmService.findById(id);
			if (null == energyAlarm) {
				throw new IllegalArgumentException("object dose not exist");
			}
			model.put("energyAlarm", energyAlarm);
		}
		return "nhgl/alarm/energy/energyAlarm_edit";
	}
}
