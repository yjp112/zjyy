
package com.supconit.nhgl.basic.meterConfig.electic.controller;

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
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ElectricConfig;
import com.supconit.nhgl.basic.meterConfig.electic.service.ElectricConfigService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("nhgl/basic/meterConfig/electricConfig")
public class ElectricConfigController  extends BaseControllerSupport{

    
	@Autowired
	private ElectricConfigService deviceService;
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private NhItemService subSystemInfoService;

  
    private String subSystemInfoOptions(Object checkedValue){
    	StringBuffer optionsBuffer=new StringBuffer();
    	List<NhItem> lstSub=subSystemInfoService.findAll();
    	for(NhItem sub:lstSub){
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
		model.put("subSystemInfoOptions",subSystemInfoOptions(""));
		model.put("treeList", treeList);	
		return "nhgl/basic/meterConfig/electric/powerSet_list";
	}
    @ResponseBody
    @RequestMapping("pagePower")
	public Pageable<ElectricConfig> pagePower( Pagination<ElectricConfig> pager, @ModelAttribute ElectricConfig device,
			ModelMap model) {
    	deviceService.findByConditonPower(pager, device);
		return pager;
	}
	@ResponseBody
	@RequestMapping(value = "savePower", method = RequestMethod.POST)
	public ScoMessage savePower(ElectricConfig d) {
		List<ElectricConfig> lst = new ArrayList<ElectricConfig>();
		ElectricConfig device = new ElectricConfig();
		if(!UtilTool.isEmptyList(d.getSubDeviceList())){
			for(SubDevice s:d.getSubDeviceList()){
				if(s.getId()!=null){
					device = new ElectricConfig();
					device.setId(s.getId());
					device.setDiscipinesCode(s.getSubDeviceCode());
					device.setExtended1(s.getSubDeviceName());
					device.setUseDepartmentId(s.getNum());			
					lst.add(device);
				}
			}
			deviceService.update(lst);
		}
		return ScoMessage.success("basic/meterConfig/electric/listPower", "操作成功。");
	}

}
