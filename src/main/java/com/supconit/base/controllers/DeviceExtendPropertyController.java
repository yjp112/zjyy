
package com.supconit.base.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.DeviceExtendProperty;
import com.supconit.base.services.DeviceExtendPropertyService;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.honeycomb.mvc.DwzMsg;

import hc.base.domains.Pageable;

@Controller
@RequestMapping("device/deviceExtendProperty")
public class DeviceExtendPropertyController extends BaseControllerSupport {

    
	@Autowired
	private DeviceExtendPropertyService deviceExtendPropertyService;
		
    /*
    get "deviceExtendProperty" list
    */
    @RequestMapping("list")
	public String list(Pageable<DeviceExtendProperty> pager, DeviceExtendProperty deviceExtendProperty,
			ModelMap model) {
		
		model.put("deviceExtendProperty", deviceExtendProperty);
		model.put("pager", deviceExtendPropertyService.findByCondition(pager, deviceExtendProperty));
		
		return "base/deviceExtendProperty/deviceExtendProperty_list";
	}


    /*
    save  deviceExtendProperty
    DeviceExtendProperty object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public DwzMsg save(DeviceExtendProperty deviceExtendProperty) {
				
         if(deviceExtendProperty.getId()==null){
          
            deviceExtendPropertyService.insert(deviceExtendProperty);	
        }
        else{
             
            deviceExtendPropertyService.update(deviceExtendProperty);
        }
            
		DwzMsg msg = DwzMsg.success(DwzMsg.DEFAULT_SUCCESS_MSG);
		
		return msg;
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public DwzMsg delete( @RequestParam("ids[]") Long[] ids) {
		
		deviceExtendPropertyService.deleteByIds(ids);
		DwzMsg msg = DwzMsg.success(DwzMsg.DEFAULT_SUCCESS_MSG);
		
		return msg;
	}   
    
    /**
	 * Edit DeviceExtendProperty
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  ModelMap model) {
		if (null != id) {
			DeviceExtendProperty deviceExtendProperty = deviceExtendPropertyService.getById(id);
			if (null == deviceExtendProperty) {
				throw new IllegalArgumentException("Object does not exist");
			}
			
			model.put("deviceExtendProperty", deviceExtendProperty);			
		}		
		
		return "base/deviceExtendProperty/deviceExtendProperty_edit";
	}
    
}
