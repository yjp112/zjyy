package com.supconit.nhgl.basic.meterConfig.water.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.GeoArea;
import com.supconit.base.entities.SubDevice;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.basic.discipine.water.entities.WaterSubSystemInfo;
import com.supconit.nhgl.basic.discipine.water.service.WaterSubSystemInfoService;
import com.supconit.nhgl.basic.meterConfig.water.entities.WaterConfig;
import com.supconit.nhgl.basic.meterConfig.water.service.WaterConfigService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
@Controller
@RequestMapping("nhgl/basic/meterConfig/waterConfig")
public class WaterConfigController extends BaseControllerSupport{

    
	@Autowired
	private WaterConfigService waterConfigService;
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private WaterSubSystemInfoService waterSubSystemInfoService;

  
    private String waterSubSystemInfoOptions(Object checkedValue){
    	StringBuffer optionsBuffer=new StringBuffer();
    	List<WaterSubSystemInfo> lstSub=waterSubSystemInfoService.findAll();
    	for(WaterSubSystemInfo sub:lstSub){
    		optionsBuffer.append("<option value='")
    			.append(sub.getCode()).append("'");
    		if(checkedValue!=null
    				&& String.valueOf(checkedValue).equals(sub.getName())){
    			optionsBuffer.append(" selected ");
    		}
    		optionsBuffer.append(">");
    		optionsBuffer.append(sub.getName());
    		optionsBuffer.append("</option>");
    	}
    	return optionsBuffer.toString();
    }
    @RequestMapping("listPower")
	public String listPower(ModelMap model) {
		List<GeoArea> treeList = geoAreaService.findAll();
		model.put("waterSubSystemInfoOptions",waterSubSystemInfoOptions(""));
		model.put("treeList", treeList);	
		return "nhgl/basic/meterConfig/water/powerSet_list";
	}
    @ResponseBody
    @RequestMapping("pagePower")
	public Pageable<WaterConfig> pagePower( Pagination<WaterConfig> pager, @ModelAttribute WaterConfig device,
			ModelMap model) {
    	waterConfigService.findByConditonPower(pager, device);
		return pager;
	}
	@ResponseBody
	@RequestMapping(value = "savePower", method = RequestMethod.POST)
	public ScoMessage savePower(WaterConfig d) {
		List<WaterConfig> lst = new ArrayList<WaterConfig>();
		WaterConfig device = new WaterConfig();
		if(!UtilTool.isEmptyList(d.getSubDeviceList())){
			for(SubDevice s:d.getSubDeviceList()){
				if(s.getId()!=null){
					device = new WaterConfig();
					device.setId(s.getId());
					device.setDiscipinesCode(s.getSubDeviceCode());
					device.setExtended1(s.getSubDeviceName());
					device.setUseDepartmentId(s.getNum());			
					lst.add(device);
				}
			}
			waterConfigService.update(lst);
		}
		return ScoMessage.success("basic/meterConfig/water/listPower", "操作成功。");
	}

}
