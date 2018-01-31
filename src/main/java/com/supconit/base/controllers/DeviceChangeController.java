
package com.supconit.base.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceChange;
import com.supconit.base.services.DeviceChangeService;
import com.supconit.base.services.DeviceService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.mvc.DwzMsg;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("device/deviceChange")
public class DeviceChangeController extends BaseControllerSupport {

    
	@Autowired
	private DeviceChangeService deviceChangeService;
	
	@Autowired
	private DeviceService deviceService;
    /*
    get "deviceChange" list
    */
    @RequestMapping("list")
	public String list(ModelMap model) {
    	model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.DEVICE_CHANGE_STATUS));
		return "base/deviceChange/deviceChange_list";
	}
    
    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping("page")
	public Pageable<DeviceChange> page( Pagination<DeviceChange> pager, @ModelAttribute DeviceChange deviceChange,
			ModelMap model) {
//    	if(deviceChange.getChangeDateTo()!=null){
//    		deviceChange.setChangeDateTo(DateUtils.addDay(deviceChange.getChangeDateTo(),1));
//    	}
		return deviceChangeService.findByCondition(pager, deviceChange);
	}
   
    /*
    save  deviceChange
    DeviceChange object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(Device device) {
		DeviceChange  deviceChange = new DeviceChange();		
         //if(deviceChange.getId()==null){
            copyCreateInfo(deviceChange);
            deviceChange.setDeviceId(device.getId());
            deviceChange.setChangeDate(new Date());
            deviceChangeService.insert(deviceChange,device);	
        /*}
        else{
            copyUpdateInfo(deviceChange);    
            deviceChangeService.update(deviceChange);
        }*/
		return ScoMessage.success("device/deviceChange/list",ScoMessage.SAVE_SUCCESS_MSG);
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public DwzMsg delete( @RequestParam("ids[]") Long[] ids) {
		
		deviceChangeService.deleteByIds(ids);
		DwzMsg msg = DwzMsg.success(DwzMsg.DEFAULT_SUCCESS_MSG);
		
		return msg;
	}   
    
    /**
	 * Edit DeviceChange
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id, String showFlag, ModelMap model) {
		if (null != id) {
			Device device = deviceService.getById(id);
			if (null == device) {
				throw new IllegalArgumentException("Object does not exist");
			}
			//DeviceChange deviceChange = deviceChangeService.getById(id);
			model.put("device", device);	
		}	
		model.put("showFlag", showFlag);
		model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.DEVICE_STATUS));		
		return "base/deviceChange/deviceChange_edit";
	}
    
}
