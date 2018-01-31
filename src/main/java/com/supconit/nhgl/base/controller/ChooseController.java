package com.supconit.nhgl.base.controller;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.common.utils.Constant;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;
import com.supconit.nhgl.base.entities.NhDept;
import com.supconit.nhgl.base.entities.NhDevice;
import com.supconit.nhgl.base.service.NhDeptService;
import com.supconit.nhgl.base.service.NhDeviceService;
import com.supconit.nhgl.basic.deptConfig.service.DeptConfigService;
import com.supconit.nhgl.basic.discipine.discipine.controller.NhItemController;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;
import com.supconit.nhgl.basic.ngArea.service.NgAreaService;

@Controller("nhgl")
@RequestMapping("nhgl/choose")
public class ChooseController extends BaseControllerSupport{
	@Autowired
    private NhItemService subSystemInfoService;
	@Autowired
	private NhDeptService nhDeptService;
	@Autowired
	private NgAreaService ngAreaService;
	@Autowired
	private NhAreaService nhAreaService;
	@Autowired
	private DeptConfigService deptConfigService;
	@Autowired
	private NhDeviceService nhDeviceService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Value("${electric_category}")
	private String dcategoryCode;
	@Value("${water_category}")
	private String scategoryCode;
	@Value("${gas_category}")
	private String qcategoryCode;
	@Value("${energy_category}")
	private String encategoryCode;
	@Value("${nhgl_category}")
	private String nhglcategoryCode;
	
	@RequestMapping(value = "devices", method = RequestMethod.GET)
	public String device(ModelMap model, String txtId,String rule,String flag) {
		String category="";
		String deviceName="";
		Integer nhtype=1;
		switch(Integer.valueOf(flag)){
			case 1:category=dcategoryCode;
			nhtype=Constant.NH_TYPE_D;
			deviceName="电表";
			break;
			case 2:category=scategoryCode;
			nhtype=Constant.NH_TYPE_S;
			deviceName="水表";
			break;
			case 3:category=qcategoryCode;
			nhtype=Constant.NH_TYPE_Q;
			deviceName="蒸汽表";
			break;
			case 4:category=encategoryCode;
			nhtype=Constant.NH_TYPE_EN;
			deviceName="能量表";
			break;
			default :category="";
				break;
		
		}
//		List<DeviceCategory>  treeListDevice=deviceCategoryService.findByCategoryCode(nhglcategoryCode);
		List<NgArea>  treeListArea=ngAreaService.findTree();
//		model.put("treeListDevice", treeListDevice);
		model.put("treeListArea",treeListArea);
		model.put("deviceName", deviceName);
		model.put("category", category);
		model.put("txtId", txtId);
		model.put("rule", rule);
		model.put("nhtype", nhtype);
		return "nhgl/choose/choose_devices";
	}
	
	@RequestMapping(value = "otherCategoryDevices", method = RequestMethod.GET)
	public String otherCategoryDevices(ModelMap model, String txtId,String rule,String flag) {
		String category="";
		String deviceName="";
		switch(Integer.valueOf(flag)){
		case 1:category=dcategoryCode;
		deviceName="电表";
		break;
		case 2:category=scategoryCode;
		deviceName="水表";
		break;
		case 3:category=qcategoryCode;
		deviceName="蒸汽表";
		break;
		case 4:category=encategoryCode;
		deviceName="能量表";
		break;
		default :category="";
		break;
		
		}
		model.put("deviceName", deviceName);
		model.put("category", category);
		model.put("txtId", txtId);
		model.put("rule", rule);
		return "nhgl/choose/choose_categoryDevices";
	}
	//选择服务区域
	@RequestMapping("chooseArea")
    public String lookup(String txtId,String txtName,String dialogId,Long id,Long nhType,ModelMap model) {
		List<NgArea> treeList = null;
		List<Long> ids =new ArrayList<Long>();
		treeList = ngAreaService.findTree();
		model.put("treeList", treeList);
        model.put("txtId", txtId); 
        //根据不同的dialog名称，产生不同的屏蔽条件
        if(id!=null||nhType!=null){
        	List<NgArea> list=null;
        	if(dialogId.equals("nhArea")){
        		list= ngAreaService.findById(id);
        	}else if(dialogId.equals("areaConfig")){
        		 list = ngAreaService.findByConfig(nhType); 
        	}
        	if(list!=null){
 				for(NgArea item:list ){
 					ids.add(item.getId());
 				}
        	 }
        }
       
        model.put("ids", ids);  
        model.put("txtName", txtName);  
        model.put("dialogId", dialogId);
        return "nhgl/choose/choose_area";
    }
	
	@RequestMapping(value = "dept", method = RequestMethod.GET)
	public String department(String txtId,String txtName,String dialogId,Long id,Long nhType,ModelMap model) {
		List<NhDept> treeList = null;
		List<Long> ids =new ArrayList<Long>();
		treeList = nhDeptService.findAll();
		
        //根据不同的dialog名称，产生不同的屏蔽条件
        if(id!=null||nhType!=null){
        	List<NhDept> list=null;
        	if(dialogId.equals("dept")){
        		list= nhDeptService.findAllChildren(id);
        	}else if(dialogId.equals("deptConfig")){
        		 list = nhDeptService.findByConfig(nhType); 
        	}
        	if(list!=null){
 				for(NhDept item:list ){
 					ids.add(item.getId());
 				}
        	 }
        }
        model.put("treeList", treeList);
        model.put("txtId", txtId); 
        model.put("ids", ids);  
        model.put("txtName", txtName);  
        model.put("dialogId", dialogId);
		return "nhgl/choose/choose_dept";
	}
	/**
	*选择分项
	 */
	@RequestMapping("subSystem")
	public String lookup(String txtId,String txtName,String type,Integer nhType,Long id,ModelMap model){
		List<NhItem> list=subSystemInfoService.selectCategories(nhType);
		
		List<Long> ids=new ArrayList<Long>();
		if(id!=null){
			NhItem nhItem =subSystemInfoService.findById(id);
			List<NhItem> list1=subSystemInfoService.findChildrenByCode(nhItem.getStandardCode(), nhType);
			if(list1.size()>0){
				for(NhItem item:list1 ){
					ids.add(item.getId());
				}
			}
		}
		list.add(new NhItemController().getRootSubSystemInfo());
		model.put("ids",ids);
		model.put("treeDatas", list);
		model.put("txtId", txtId);  
        model.put("txtName", txtName); 
        model.put("type", type);
        return "nhgl/choose/choose_subSystemInfo";
	}
	@RequestMapping("devicePage")
    @ResponseBody
	public Pageable<NhDevice> devicePage(@ModelAttribute NhDevice condition,
			Pagination<NhDevice> pager,
            ModelMap model) {
		return nhDeviceService.findByCondition(pager, condition);
	}
}
