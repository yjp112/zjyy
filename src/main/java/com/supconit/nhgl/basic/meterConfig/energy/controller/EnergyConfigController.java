package com.supconit.nhgl.basic.meterConfig.energy.controller;

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
import com.supconit.nhgl.basic.discipine.energy.entities.EnergySubSystemInfo;
import com.supconit.nhgl.basic.discipine.energy.service.EnergySubSystemInfoService;
import com.supconit.nhgl.basic.meterConfig.energy.entities.EnergyConfig;
import com.supconit.nhgl.basic.meterConfig.energy.service.EnergyConfigService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("nhgl/basic/meterConfig/energyConfig")
public class EnergyConfigController extends BaseControllerSupport{
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private EnergyConfigService energyConfigService;
	@Autowired
	private EnergySubSystemInfoService energySubSystemInfoService;
	
    private String EnergySubSystemInfoOptions(Object checkedValue){
    	StringBuffer optionsBuffer=new StringBuffer();
    	List<EnergySubSystemInfo> lstSub=energySubSystemInfoService.findAll();
    	for(EnergySubSystemInfo sub:lstSub){
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
		model.put("EnergySubSystemInfoOptions",EnergySubSystemInfoOptions(""));
		model.put("treeList", treeList);	
		return "nhgl/basic/meterConfig/energy/powerSet_list";
	}
    @ResponseBody
    @RequestMapping("pagePower")
	public Pageable<EnergyConfig> pagePower( Pagination<EnergyConfig> pager, @ModelAttribute EnergyConfig device,
			ModelMap model) {
    	energyConfigService.findByConditonPower(pager, device);
		return pager;
	}
	@ResponseBody
	@RequestMapping(value = "savePower", method = RequestMethod.POST)
	public ScoMessage savePower(EnergyConfig d) {
		List<EnergyConfig> lst = new ArrayList<EnergyConfig>();
		EnergyConfig device = new EnergyConfig();
		if(!UtilTool.isEmptyList(d.getSubDeviceList())){
			for(SubDevice s:d.getSubDeviceList()){
				if(s.getId()!=null){
					device = new EnergyConfig();
					device.setId(s.getId());
					device.setDiscipinesCode(s.getSubDeviceCode());
					device.setExtended1(s.getSubDeviceName());
					device.setUseDepartmentId(s.getNum());			
					lst.add(device);
				}
			}
			energyConfigService.update(lst);
		}
		return ScoMessage.success("basic/meterConfig/enery/listPower", "操作成功。");
	}
}
