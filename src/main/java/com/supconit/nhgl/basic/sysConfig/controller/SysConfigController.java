
package com.supconit.nhgl.basic.sysConfig.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.Device;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.entities.SubDevice;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.basic.sysConfig.service.SysConfigService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("nhgl/nhgl/basic/sysConfig")
public class SysConfigController  extends BaseControllerSupport{

    
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private NhItemService subSystemInfoService;
	
	
    @RequestMapping("list")
	public String list(ModelMap model) {
		List<GeoArea> treeList = geoAreaService.findAll();
		model.put("subSystemInfoOptions",subSystemInfoOptions(""));
		model.put("treeList", treeList);
		return "nhgl/basic/sysConfig/collect_list";
	}
  
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
    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping("page")
	public Pageable<Device> page( Pagination<Device> pager, @ModelAttribute Device device,
			ModelMap model) {
    	sysConfigService.findByConditon(pager, device);
		return pager;
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
	public Pageable<Device> pagePower( Pagination<Device> pager, @ModelAttribute Device device,
			ModelMap model) {
    	sysConfigService.findByConditonPower(pager, device);
		return pager;
	}
	@ResponseBody
	@RequestMapping(value = "savePower", method = RequestMethod.POST)
	public ScoMessage savePower(Device d) {
		List<Device> lst = new ArrayList<Device>();
		Device device = new Device();
		if(!UtilTool.isEmptyList(d.getSubDeviceList())){
			for(SubDevice s:d.getSubDeviceList()){
				if(s.getId()!=null){
					device = new Device();
					device.setId(s.getId());
					device.setDiscipinesCode(s.getSubDeviceCode());
					device.setExtended1(s.getSubDeviceName());
					device.setUseDepartmentId(s.getNum());			
					lst.add(device);
				}
			}
			sysConfigService.update(lst);
		}
		return ScoMessage.success("collect/listPower", "操作成功。");
	}

}
