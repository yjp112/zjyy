package com.supconit.maintain.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.maintain.entities.MaintainItem;
import com.supconit.maintain.services.MaintainItemService;
import com.supconit.maintain.services.MaintainPlanService;

/**
 * 保养项目控制类
 * @author yuhuan
 * @日期 2015/08
 */
@Controller
@RequestMapping("maintain/item")
public class MaintainItemController extends BaseControllerSupport {
	@Autowired
	private MaintainItemService maintainItemService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private MaintainPlanService maintainPlanService;
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(ModelMap model) {
		List<DeviceCategory> deviceCategoryList = deviceCategoryService.findAll();
        model.addAttribute("deviceCategoryList", deviceCategoryList);//设备类别树
        List<GeoArea> geoAreaList = geoAreaService.findTree();
		model.put("geoAreaList", geoAreaList);//地理区域树
        model.put("circleUnitList", DictUtils.getDictList(DictTypeEnum.MAINTAIN_CYCLE_UNIT)); 
		return "maintain/item/list";
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<MaintainItem> dolist(MaintainItem condition,
			Pagination<MaintainItem> pager,
            ModelMap model) {
		return maintainItemService.findByCondition(pager, condition);
	}
	
	/**
	 * 跳转到编辑页
	 */
	@RequestMapping(value="edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false)Boolean view,
			ModelMap model, @RequestParam(required = false) Long deviceId) {
		MaintainItem maintainItem = null;
		//修改
		if (null != deviceId) {
			maintainItem = maintainItemService.getItem(deviceId);
			view = true;
		}else{
			maintainItem = new MaintainItem();
			view = false;
		}
		model.put("circleUnitList", DictUtils.getDictList(DictTypeEnum.MAINTAIN_CYCLE_UNIT)); 
		model.put("maintainItem", maintainItem);
		model.put("viewOnly", viewOnly);
		model.put("view", view);
		return "maintain/item/edit";
	}
	
	/**
	 * 新增或修改
	 */
	@ResponseBody
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public ScoMessage doEdit(MaintainItem maintainItem) {
		List<MaintainItem> deviceList = maintainItem.getDeviceList();
		List<MaintainItem> itemList = maintainItem.getMaintainItemList();
		//清除无效实体
		deviceList.remove(0);
		if(itemList.size() > 0){
			itemList.remove(0);
		}
		//表单验证
		for (int i = 0; i < deviceList.size(); i++) {
			MaintainItem device = deviceList.get(i);
			if(null != device.getDeviceId() && null == device.getStartTime()){
				return ScoMessage.error("保养设备列表中第"+(i+1)+"行计划保养时间不能为空。");
			}
		}
		for (int i = 0; i < itemList.size(); i++) {
			MaintainItem item = itemList.get(i);
			if(StringUtils.isNotEmpty(item.getItemContent())){
				if(null == item.getCycle()){
					return ScoMessage.error("保养项目列表中第"+(i+1)+"行保养周期不能为空。");
				}
				if(null == item.getCycleUnit()){
					return ScoMessage.error("保养项目列表中第"+(i+1)+"行周期单位不能为空。");
				}
				if(null == item.getMaintainGroupId() || StringUtils.isEmpty(item.getMaintainGroupName())){
					return ScoMessage.error("保养项目列表中第"+(i+1)+"行保养班组不能为空。");
				}
			}
		}
		
		int countDevice = 0;
		int countItem = 0;
		//清除空的设备
		Iterator<MaintainItem> device = deviceList.iterator();
		while(device.hasNext()){
			MaintainItem t = device.next();
			if(null == t.getDeviceId()){
				device.remove();
			}else{
				countDevice++;
			}
		}
		
		//清除空的保养项
		Iterator<MaintainItem> item = itemList.iterator();
		while(item.hasNext()){
			MaintainItem t = item.next();
			if(StringUtils.isEmpty(t.getItemContent())){
				item.remove();
			}else{
				countItem++;
			}
		}
		
		if(countDevice == 0){
			return ScoMessage.error("请至少添加一个设备。");
		}
		if (null == maintainItem.getDeviceId()) {
			if(countItem == 0){
				return ScoMessage.error("请至少添加一个保养项。");
			}
			copyCreateInfo(maintainItem);
			maintainItemService.insert(maintainItem);
		} else {
			copyCreateInfo(maintainItem);
			maintainItemService.update(maintainItem);
		}
		return ScoMessage.success("maintain/item/list",ScoMessage.SAVE_SUCCESS_MSG);
	}
	
	/**
	 * 删除操作
	 * @param ids为deviceId
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage doDelete( @RequestParam("ids") Long[] ids) {
		maintainItemService.deleteByDeviceId(ids[0]);
		return ScoMessage.success("maintain/item/list", ScoMessage.DELETE_SUCCESS_MSG);
	} 
	
	/**
	 * 生成计划
	 */
	@ResponseBody
	@RequestMapping(value = "generatePlan", method = RequestMethod.POST)     
	public ScoMessage generatePlan() {
		maintainPlanService.generatePlan();
		return ScoMessage.success("maintain/item/list", "生成计划成功");
	} 
	
}
