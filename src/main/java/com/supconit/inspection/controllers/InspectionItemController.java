package com.supconit.inspection.controllers;

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
import com.supconit.inspection.entities.InspectionItem;
import com.supconit.inspection.services.InspectionItemService;
import com.supconit.inspection.services.InspectionPlanService;

/**
 * 巡检项目控制类
 * @author yuhuan
 * @日期 2015/09
 */
@Controller
@RequestMapping("inspection/item")
public class InspectionItemController extends BaseControllerSupport {
	@Autowired
	private InspectionItemService inspectionItemService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private InspectionPlanService inspectionPlanService;
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(ModelMap model) {
		List<DeviceCategory> deviceCategoryList = deviceCategoryService.findAll();
        model.addAttribute("deviceCategoryList", deviceCategoryList);//设备类别树
        List<GeoArea> geoAreaList = geoAreaService.findTree();
		model.put("geoAreaList", geoAreaList);//地理区域树
        model.put("circleUnitList", DictUtils.getDictList(DictTypeEnum.INSPECTION_CYCLE_UNIT)); 
		return "inspection/item/list";
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<InspectionItem> dolist(InspectionItem condition,
			Pagination<InspectionItem> pager,
            ModelMap model) {
		return inspectionItemService.findByCondition(pager, condition);
	}
	
	/**
	 * 跳转到编辑页
	 */
	@RequestMapping(value="edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false)Boolean view,
			ModelMap model, @RequestParam(required = false) Long deviceId) {
		InspectionItem inspectionItem = null;
		//修改
		if (null != deviceId) {
			inspectionItem = inspectionItemService.getItem(deviceId);
			view = true;
		}else{
			inspectionItem = new InspectionItem();
			view = false;
		}
		model.put("circleUnitList", DictUtils.getDictList(DictTypeEnum.INSPECTION_CYCLE_UNIT)); 
		model.put("inspectionItem", inspectionItem);
		model.put("viewOnly", viewOnly);
		model.put("view", view);
		return "inspection/item/edit";
	}
	
	/**
	 * 新增或修改
	 */
	@ResponseBody
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public ScoMessage doEdit(InspectionItem inspectionItem) {
		List<InspectionItem> deviceList = inspectionItem.getDeviceList();
		List<InspectionItem> itemList = inspectionItem.getInspectionItemList();
		//清除无效实体
		deviceList.remove(0);
		if(itemList.size() > 0){
			itemList.remove(0);
		}
		//表单验证
		for (int i = 0; i < deviceList.size(); i++) {
			InspectionItem device = deviceList.get(i);
			if(null != device.getDeviceId() && null == device.getStartTime()){
				return ScoMessage.error("巡检设备列表中第"+(i+1)+"行计划巡检时间不能为空。");
			}
		}
		for (int i = 0; i < itemList.size(); i++) {
			InspectionItem item = itemList.get(i);
			if(StringUtils.isNotEmpty(item.getItemContent())){
				if(null == item.getCycle()){
					return ScoMessage.error("巡检项目列表中第"+(i+1)+"行巡检周期不能为空。");
				}
				if(null == item.getCycleUnit()){
					return ScoMessage.error("巡检项目列表中第"+(i+1)+"行巡检单位不能为空。");
				}
				if(null == item.getInspectionGroupId() || StringUtils.isEmpty(item.getInspectionGroupName())){
					return ScoMessage.error("巡检项目列表中第"+(i+1)+"行巡检班组不能为空。");
				}
			}
		}
		
		int countDevice = 0;
		int countItem = 0;
		//清除空的设备
		Iterator<InspectionItem> device = deviceList.iterator();
		while(device.hasNext()){
			InspectionItem t = device.next();
			if(null == t.getDeviceId()){
				device.remove();
			}else{
				countDevice++;
			}
		}
		
		//清除空的巡检项
		Iterator<InspectionItem> item = itemList.iterator();
		while(item.hasNext()){
			InspectionItem t = item.next();
			if(StringUtils.isEmpty(t.getItemContent())){
				item.remove();
			}else{
				countItem++;
			}
		}
		
		if(countDevice == 0){
			return ScoMessage.error("请至少添加一个设备。");
		}
		if (null == inspectionItem.getDeviceId()) {
			if(countItem == 0){
				return ScoMessage.error("请至少添加一个巡检项。");
			}
			copyCreateInfo(inspectionItem);
			inspectionItemService.insert(inspectionItem);
		} else {
			copyCreateInfo(inspectionItem);
			inspectionItemService.update(inspectionItem);
		}
		return ScoMessage.success("inspection/item/list",ScoMessage.SAVE_SUCCESS_MSG);
	}
	
	/**
	 * 删除操作
	 * @param ids为deviceId
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage doDelete( @RequestParam("ids") Long[] ids) {
		inspectionItemService.deleteByDeviceId(ids[0]);
		return ScoMessage.success("inspection/item/list", ScoMessage.DELETE_SUCCESS_MSG);
	} 
	
	/**
	 * 生成计划
	 */
	@ResponseBody
	@RequestMapping(value = "generatePlan", method = RequestMethod.POST)     
	public ScoMessage generatePlan() {
		inspectionPlanService.generatePlan();
		return ScoMessage.success("inspection/item/list", "生成计划成功");
	} 
	
}
