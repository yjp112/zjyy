package com.supconit.emergency.controllers;


import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.emergency.entities.EmergencyFacility;
import com.supconit.emergency.services.EmergencyFacilityService;
@Controller
@RequestMapping("emergency/emergencyFacility")
public class EmergencyFacilityController extends BaseControllerSupport{

	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private EmergencyFacilityService emergencyFacilityService;
	private String flag;
	@RequestMapping("list/{flag}")
	public String list(@PathVariable String flag,ModelMap model) {
		List<GeoArea> treeListLou = geoAreaService.findTree();
		model.put("treeListLou", treeListLou);
		this.flag=flag;
		return "emergency/emergencyFacility/emergencyFacility_list";
	}
	
    @ResponseBody
    @RequestMapping("page")
	public Pageable<EmergencyFacility> page( Pagination<EmergencyFacility> pager, @ModelAttribute EmergencyFacility emergencyFacility,
			ModelMap model) {
    	if("xf".equals(flag)){
    		emergencyFacility.setFacilityType(1);
    	}else if("af".equals(flag)){
    		emergencyFacility.setFacilityType(2);
    	}
		 emergencyFacilityService.findByCondition(pager, emergencyFacility);
		 return pager;
	}
	    
	    
    @ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(EmergencyFacility emergencyFacility) {
		ScoMessage msg = ScoMessage.success("emergency/emergencyFacility/list/"+flag,ScoMessage.SAVE_SUCCESS_MSG);

		if(emergencyFacility.getId()==null){
			copyCreateInfo(emergencyFacility);
			emergencyFacilityService.insert(emergencyFacility);	
		}
		else{
			copyUpdateInfo(emergencyFacility);  
			emergencyFacilityService.update(emergencyFacility);
		}
		return msg;
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		emergencyFacilityService.deleteByIds(ids);	
		return ScoMessage.success("emergency/emergencyFacility/list/"+flag,ScoMessage.DELETE_SUCCESS_MSG);
	}   
    
    /**
	 * Edit EmergencyFacility
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  ModelMap model,@RequestParam(required = false)Boolean viewOnly) {
    	if (null != id) {
			EmergencyFacility emergencyFacility = emergencyFacilityService.getById(id);
			if (null == emergencyFacility) {
				throw new IllegalArgumentException("Object does not exist");
			}
			
			model.put("emergencyFacility", emergencyFacility);			
		}else{
			EmergencyFacility emergencyFacility = new EmergencyFacility();
			if("xf".equals(flag)){
	    		emergencyFacility.setFacilityType(1);
	    	}else if("af".equals(flag)){
	    		emergencyFacility.setFacilityType(2);
	    	}
			emergencyFacility.setCreator(getCurrentPersonName());
			model.put("emergencyFacility", emergencyFacility);
		}	
    	model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.FACILITY_TYPE));
		model.put("viewOnly", viewOnly);
		return "emergency/emergencyFacility/emergencyFacility_edit";
	}
   
}
