package com.supconit.nhgl.basic.meterConfig.gas.controller;

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
import com.supconit.nhgl.basic.discipine.gas.entities.GasSubSystemInfo;
import com.supconit.nhgl.basic.discipine.gas.service.GasSubSystemInfoService;
import com.supconit.nhgl.basic.meterConfig.gas.entities.GasConfig;
import com.supconit.nhgl.basic.meterConfig.gas.service.GasConfigService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("nhgl/basic/meterConfig/gasConfig")
public class GasConfigController extends BaseControllerSupport{
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private GasConfigService gasConfigService;
	@Autowired
	private GasSubSystemInfoService gasSubSystemInfoService;
	
    private String GasSubSystemInfoOptions(Object checkedValue){
    	StringBuffer optionsBuffer=new StringBuffer();
    	List<GasSubSystemInfo> lstSub=gasSubSystemInfoService.findAll();
    	for(GasSubSystemInfo sub:lstSub){
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
		model.put("GasSubSystemInfoOptions",GasSubSystemInfoOptions(""));
		model.put("treeList", treeList);	
		return "nhgl/basic/meterConfig/gas/powerSet_list";
	}
    @ResponseBody
    @RequestMapping("pagePower")
	public Pageable<GasConfig> pagePower( Pagination<GasConfig> pager, @ModelAttribute GasConfig device,
			ModelMap model) {
    	gasConfigService.findByConditonPower(pager, device);
		return pager;
	}
	@ResponseBody
	@RequestMapping(value = "savePower", method = RequestMethod.POST)
	public ScoMessage savePower(GasConfig d) {
		List<GasConfig> lst = new ArrayList<GasConfig>();
		GasConfig device = new GasConfig();
		if(!UtilTool.isEmptyList(d.getSubDeviceList())){
			for(SubDevice s:d.getSubDeviceList()){
				if(s.getId()!=null){
					device = new GasConfig();
					device.setId(s.getId());
					device.setDiscipinesCode(s.getSubDeviceCode());
					device.setExtended1(s.getSubDeviceName());
					device.setUseDepartmentId(s.getNum());			
					lst.add(device);
				}
			}
			gasConfigService.update(lst);
		}
		return ScoMessage.success("basic/meterConfig/gas/listPower", "操作成功。");
	}
}
