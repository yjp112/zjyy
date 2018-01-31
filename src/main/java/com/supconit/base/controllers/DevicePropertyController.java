
package com.supconit.base.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.DeviceProperty;
import com.supconit.base.services.DevicePropertyService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("device/deviceProperty")
public class DevicePropertyController extends BaseControllerSupport {

    
	@Autowired
	private DevicePropertyService devicePropertyService;
		
    /*
    get "deviceProperty" list
    */
    @RequestMapping("list")
	public String list(ModelMap model) {
    	model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.DEVICE_PROPERTY_STATUS));
		return "base/deviceProperty/deviceProperty_list";
	}
    
    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping("page")
	public Pageable<DeviceProperty> page(Pagination<DeviceProperty> pager, @ModelAttribute DeviceProperty deviceProperty,
			ModelMap model) {
		return devicePropertyService.findByCondition(pager, deviceProperty);
	}

    /*
    save  deviceProperty
    DeviceProperty object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(DeviceProperty deviceProperty) {
		ScoMessage msg = ScoMessage.success("device/deviceProperty/list",ScoMessage.SAVE_SUCCESS_MSG);

		if(deviceProperty.getId()==null){
			copyCreateInfo(deviceProperty);
			devicePropertyService.insert(deviceProperty);	
		}
		else{
			copyUpdateInfo(deviceProperty);    
			devicePropertyService.update(deviceProperty);
		}
		return msg;
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		devicePropertyService.deleteByIds(ids);	
		return ScoMessage.success(".",ScoMessage.DELETE_SUCCESS_MSG);
	}   
    
    /**
	 * Edit DeviceProperty
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  ModelMap model,String showFlag) {
    	model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.DEVICE_PROPERTY_STATUS));
    	model.put("lstInputType", DictUtils.getDictList(DictTypeEnum.DEVICE_PROPERTY_INPUT_TYPE));
		if (null != id) {
			DeviceProperty deviceProperty = devicePropertyService.getById(id);
			if (null == deviceProperty) {
				throw new IllegalArgumentException("Object does not exist");
			}
			
			model.put("deviceProperty", deviceProperty);			
		}		
		model.put("showFlag", showFlag);
		return "base/deviceProperty/deviceProperty_edit";
	}
    
}
