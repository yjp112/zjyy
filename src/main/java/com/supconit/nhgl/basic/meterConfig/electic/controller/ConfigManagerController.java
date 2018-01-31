package com.supconit.nhgl.basic.meterConfig.electic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;
import com.supconit.nhgl.basic.meterConfig.electic.service.ConfigManagerService;
import com.supconit.nhgl.utils.HolidayConstans;

import hc.base.domains.AjaxMessage;

@Controller
@RequestMapping("nhgl/basic/meterConfig/electric")
public class ConfigManagerController extends BaseControllerSupport{
	
	@Autowired
	private ConfigManagerService cmService;
	
	@RequestMapping("edit")
	public String getConfigInfo(ModelMap model){
		String[] configValue = null;
		List<ConfigManager> cmlist = cmService.getConfigInfo();
		for(ConfigManager cm : cmlist){
			configValue = cm.getConfigValue().split(" ");
			if(configValue.length>1){
				cm.setStartTime(configValue[0]);
				cm.setEndTime(configValue[1]);
			}else{
				cm.setStartTime("");
				cm.setEndTime("");
			}
			
		}
		model.addAttribute("cmlist", cmlist);
		model.addAttribute("JJR", HolidayConstans.ELECTRIC_JJR_TYPECODE);
		model.addAttribute("BTWS", HolidayConstans.ELECTRIC_BTWS_TYPECODE);
		model.addAttribute("BFBG", HolidayConstans.ELECTRIC_BFBG_TYPECODE);
		return "/nhgl/basic/meterConfig/electric/configEdit";
	}
	
	@ResponseBody
	@RequestMapping("update")
	public AjaxMessage update(Long[] id, Integer[] flag, String[] startTime, String[] endTime ){
		//节假日 order<10 波峰波谷order>=10&&order<20 白天晚上 order>=20 && order<30
		ConfigManager cm = null;
		Integer num = startTime.length;
		for(Integer i = 0; i<id.length; i++){
			if(id[i] != null){
				cm = new ConfigManager();
				cm.setId(id[i]);
				cm.setConfigValue(startTime[i] + " " + endTime[i]);
				cm.setFlag(flag[i]);
				cmService.update(cm);
			}
		}
		return AjaxMessage.success("操作成功");
		
	}
}
