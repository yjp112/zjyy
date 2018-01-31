package com.supconit.base.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.domains.Organization;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.DutyGroup;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.DeviceService;
import com.supconit.base.services.DutyGroupPersonService;
import com.supconit.base.services.DutyGroupService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.nhgl.alarm.device.entities.IAlarmType;
import com.supconit.nhgl.alarm.device.service.MAlarmTypeService;
import com.supconit.nhgl.basic.deviceMeter.entities.DeviceMeter;
import com.supconit.nhgl.basic.deviceMeter.service.DeviceMeterService;
import com.supconit.nhgl.basic.discipine.discipine.controller.NhItemController;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.basic.discipine.energy.controller.EnergySubSystemInfoController;
import com.supconit.nhgl.basic.discipine.energy.entities.EnergySubSystemInfo;
import com.supconit.nhgl.basic.discipine.energy.service.EnergySubSystemInfoService;
import com.supconit.nhgl.basic.discipine.gas.controller.GasSubSystemInfoController;
import com.supconit.nhgl.basic.discipine.gas.entities.GasSubSystemInfo;
import com.supconit.nhgl.basic.discipine.gas.service.GasSubSystemInfoService;
import com.supconit.nhgl.basic.discipine.water.controller.WaterSubSystemInfoController;
import com.supconit.nhgl.basic.discipine.water.entities.WaterSubSystemInfo;
import com.supconit.nhgl.basic.discipine.water.service.WaterSubSystemInfoService;
import com.supconit.nhgl.schedule.entites.TaskCatagory;
import com.supconit.nhgl.schedule.service.TaskCatagoryService;
import com.supconit.nhgl.schedule.service.TaskExecutionPlanService;
import com.supconit.repair.entities.RepairEvtCategory;
import com.supconit.repair.entities.RepairEvtCategoryPerson;
import com.supconit.repair.services.RepairEvtCategoryPersonService;
import com.supconit.repair.services.RepairEvtCategoryService;

/**
 * 选择控制类
 * @since 2015/07
 */
@Controller
@RequestMapping("/choose")
public class ChooseController extends BaseControllerSupport {
	@Autowired
	private MAlarmTypeService alarmTypeService;
	@Autowired
	private DepartmentService deptService;
	@Autowired
	private PersonService personService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private RepairEvtCategoryService repairEvtCategoryService;
	@Autowired
	private DutyGroupService dutyGroupService;
	@Autowired
	private RepairEvtCategoryPersonService repairEvtCategoryPersonService;
	
	@Autowired
    private NhItemService subSystemInfoService;
    @Autowired
	private TaskCatagoryService taskCategoryService;
    @Autowired
	private TaskExecutionPlanService taskPlanService;
    @Autowired
    private WaterSubSystemInfoService waterSubSystemInfoService;
    @Autowired
    private GasSubSystemInfoService gasSubSystemInfoService;
    
    @Autowired
    private DeviceMeterService deviceMeterService;
    @Autowired
    private EnergySubSystemInfoService energySubSystemInfoService;
    @Value("${water_category}")
	private String waterCatagoryCode;
    @Value("${gas_category}")
	private String gasCategoryCode;
	/**
	 * 选择部门
	 * @param model
	 * @param txtId
	 * @param txtName
	 * @return
	 */
	@RequestMapping(value = "dept", method = RequestMethod.GET)
	public String department(ModelMap model, String txtId, String txtName,String dialogId,String afterAction) {
		List<Department> deptList = deptService.findAllWithoutVitualRoot();
		model.addAttribute("treeList", deptList);
		model.put("txtId",txtId);
		model.put("txtName",txtName);
		model.put("dialogId",dialogId);
		model.put("afterAction",afterAction);
		return "choose/choose_dept";
	}
	
	
	@RequestMapping(value="alarmType",method=RequestMethod.GET)
	public String alarmType(ModelMap model,String txtId,String txtName){
		List<IAlarmType> lstalarmType=alarmTypeService.findAllTypes();
		model.addAttribute("treeList", lstalarmType);
		model.put("txtId",txtId);
		model.put("txtName",txtName);
		return "choose/choose_alarmType";
	}
	/**
	 * 获取部门全路径
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "getFullDeptName", method = RequestMethod.POST)
	@ResponseBody
	public String getFullDeptName(Long deptId) {
		String deptName ="";
		if(null != deptId){
			deptName = OrganizationUtils.getFullDeptNameByDeptId(deptId);
		}
		return deptName;
	}
	
	/**
	 *	跳转至选择用户列表
	 * @param type
	 *            1:单个框赋单个值
	 *			  2:列表中多个框赋值
	 *			  3:单个框赋多个值
	 *        id:当type为2时，某一个框的ID
	 * @author yuhuan
	 */
	@RequestMapping("personPage")
	public String personPage(Integer type,String txtId,String txtName,String deptId,String deptName,String tel,  
			String dialogId,String id,String personId,ModelMap model) {
		if(null == type) type = 1;
		model.put("type",type);
		model.put("dialogId",dialogId);
		List<Department> deptList = deptService.findAllWithoutVitualRoot();
		List<Department> personDept = null;
		if(StringUtils.isNotEmpty(personId)){
			personDept = deptService.findByPersonId(Long.parseLong(personId));
		}
		long personDeptId = DEFAULT_DEPARTMENTID;
		//对于左边树的默认选中状态，如果用户有多个部门，只取第一个选中
		if(null != personDept && personDept.size()>0){
			personDeptId = personDept.get(0).getId();
		}
		model.put("treeId",personDeptId);
		model.put("deptList",deptList);
		if(1 == type){
			model.put("txtId",txtId);
			model.put("txtName",txtName);
			model.put("deptId",deptId);
			model.put("deptName",deptName);
			model.put("tel",tel);
			model.put("multi",false);//单选
		}else if(2 == type){
			model.put("id",id);
			model.put("multi",true);//多选
			model.put("stype",true);//列表
		}else{
			model.put("id",0);//防止页面报错
			model.put("txtId",txtId);
			model.put("txtName",txtName);
			model.put("multi",true);//多选
			model.put("stype",false);//单个框
		}
		return "choose/choose_person";
	}

    @RequestMapping("tmpPersonPage")
    public String tmpPersonPage(String txtId,String txtName,String personId,
                             String dialogId,ModelMap model) {
        model.put("dialogId",dialogId);
        List<Department> deptList = deptService.findAllWithoutVitualRoot();
        List<Department> personDept = null;
        if(StringUtils.isNotEmpty(personId)){
            personDept = deptService.findByPersonId(Long.parseLong(personId));
        }
        long personDeptId = DEFAULT_DEPARTMENTID;
        //对于左边树的默认选中状态，如果用户有多个部门，只取第一个选中
        if(null != personDept && personDept.size()>0){
            personDeptId = personDept.get(0).getId();
        }
        model.put("treeId",personDeptId);
        model.put("deptList",deptList);
        model.put("txtId",txtId);
        model.put("txtName",txtName);
        model.put("multi",false);//单选
        return "choose/choose_tmpPerson";
    }

	@RequestMapping("groupPersonPage")
	public String groupPersonPage(String txtId,String txtName,String groupId,String groupName,String dialogId,String gId,ModelMap model) {
		model.put("dialogId",dialogId);
		List<DutyGroup> dutyGroupList = dutyGroupService.findTree();
		model.put("treeId",gId);
		model.put("dutyGroupList",dutyGroupList);
		model.put("txtId",txtId);
		model.put("txtName",txtName);
		model.put("groupId",groupId);
		model.put("groupName",groupName);		
		return "choose/choose_dutyGroupPerson";
	}
	
	/**
	 * 用户列表查询
	 */
	@RequestMapping("person")
    @ResponseBody
	public Pageable<ExPerson>  person(@ModelAttribute ExPerson condition,
			Pagination<ExPerson> pager,
            ModelMap model) {
		if(null == condition.getDeptId()){
			condition.setDeptId(DEFAULT_DEPARTMENTID);
		}
		personService.findByDepartmentId(pager, condition.getDeptId(),condition);
		Iterator<ExPerson> it = pager.iterator();
		while(it.hasNext()){
			ExPerson p = it.next();
			List<Organization> orList = organizationService.getFullDeptNameByPersonId(p.getId());
			String deptFullName = OrganizationUtils.joinFullDeptName(orList);
			p.setDeptName(deptFullName);
			p.setDeptId(orList.get(0).getDeptId());
		}
		return pager;
	}
	
    @RequestMapping("deviceCategory")
	public String deviceCategory(String txtId,String txtName, String dialogId, String type,boolean chooseRoot,ModelMap model) {
		List<DeviceCategory> treeList = deviceCategoryService.findAll();
		model.put("treeList", treeList);	
		model.put("txtId", txtId);	
		model.put("txtName", txtName);
		model.put("dialogId", dialogId);
		model.put("type",type);
		model.put("chooseRoot",chooseRoot);
		return "choose/choose_deviceCategory";
	}
    /*
     *选图层
     */
    @RequestMapping("layer")
	public String layer(String txtId,String txtName, String dialogId, ModelMap model) {	
		model.put("txtId", txtId);	
		model.put("txtName", txtName);
		model.put("dialogId", dialogId);
		return "choose/choose_layer";
	}
	/*
     *选地理区域
     */
	@RequestMapping("geoarea")
    public String geoarea(String txtId,String txtName, String dialogId,ModelMap model) {
		List<GeoArea> treeList = null;
		treeList = geoAreaService.findTree();
		model.put("treeList", treeList);
        model.put("txtId", txtId);  
        model.put("txtName", txtName); 
        model.put("dialogId", dialogId);
        return "choose/choose_geoArea";
    }
	

	/**	跳转至选择设备列表
	 * @param type
	 *            1:单个框赋值 //TODO
	 *            2：列表中多个框赋值
	 *        id:当type为2时，某一个框的ID
	 * @author yuhuan
	 */
	@RequestMapping("devicePage")
	public String devicePage(int type,String txtId,String txtName,String dialogId,String id,
			String deviceName, String deviceSpec, String locationName,String useDepartmentName,ModelMap model) {
		model.put("type",type);
		model.put("dialogId",dialogId);
	    List<DeviceCategory> deviceCategoryList = deviceCategoryService.findAll();
	    model.addAttribute("deviceCategoryList", deviceCategoryList);//设备类别树
	    List<GeoArea> geoAreaList = geoAreaService.findTree();
	    model.put("geoAreaList", geoAreaList);//地理区域树
	    if(1 == type){
	    	model.put("txtId",txtId);
	    	model.put("txtName",txtName);
	    	model.put("deviceName",deviceName);
	    	model.put("deviceSpec",deviceSpec);
	    	model.put("locationName",locationName);
	    	model.put("useDepartmentName",useDepartmentName);
	    	model.put("multi",false);//单选
	    }else{
	    	model.put("id",id);
	    	model.put("multi",true);//多选
	    }
	    return "choose/choose_device";
	}
	
	/**
	 * 设备列表查询
	 */
	@RequestMapping("device")
    @ResponseBody
	public Pageable<Device> device(@ModelAttribute Device condition,
			Pagination<Device> pager,
            ModelMap model) {
		deviceService.findByCondition(pager, condition);
		return pager;
	}
	/*
     *选班组
     */
	@RequestMapping("dutyGroup")
    public String dutyGroup(String txtId,String txtName, String dialogId,ModelMap model) {
		List<DutyGroup> treeList = null;
		treeList = dutyGroupService.findTree();
		model.put("treeList", treeList); 
        model.put("txtId", txtId);  
        model.put("txtName", txtName); 
        model.put("dialogId", dialogId);
        return "choose/choose_dutyGroup";
    }
	
	/*
     *选维修事件
     */
	@RequestMapping("repairEvtCategory")
    public String repairEvtCategory(String txtId,String txtName, String dialogId,String txtTime, String txtEmergency,ModelMap model) {
		List<RepairEvtCategory> treeList = null;
		treeList = repairEvtCategoryService.findAll();
		model.put("treeList", treeList);
        model.put("txtId", txtId);  
        model.put("txtName", txtName);
        model.put("txtTime", txtTime);  
        model.put("txtEmergency", txtEmergency); 
        model.put("dialogId", dialogId);
        return "choose/choose_repairEvtCategory";
    }
	
	@RequestMapping("deviceCate")
	public String deviceCate(String txtId,String txtName,String dialogId, String categoryId, String categoryName,ModelMap model) {
		List<DeviceCategory> deviceCategoryList = deviceCategoryService.findAll();
	    model.addAttribute("deviceCategoryList", deviceCategoryList);//设备类别树
	    List<GeoArea> geoAreaList = geoAreaService.findTree();
	    model.put("geoAreaList", geoAreaList);//地理区域树
		model.put("txtId",txtId);
		model.put("txtName",txtName);
		model.put("categoryId",categoryId);
		model.put("categoryName",categoryName);
		model.put("dialogId",dialogId);
        return "choose/choose_device_cate"; 
	}
	@RequestMapping(value = "deviceWater", method = RequestMethod.GET)
	public String getDeviceWater(ModelMap model,String txtId,String txtName) {
		model.put("txtId", txtId);
		model.put("txtName", txtName);
		return "choose/choose_deviceWater";
	}
	
	@RequestMapping(value = "deviceGas", method = RequestMethod.GET)
	public String getDeviceGas(ModelMap model,String txtId,String txtName) {
		model.put("txtId", txtId);
		model.put("txtName", txtName);
		return "choose/choose_deviceGas";
	}
	
	@RequestMapping(value = "deviceElectric", method = RequestMethod.GET)
	public String getDeviceElectric(ModelMap model,String txtId,String txtName) {
		model.put("txtId", txtId);
		model.put("txtName", txtName);
		return "choose/choose_deviceElectric";
	}
	
	@RequestMapping(value = "deviceEnergy", method = RequestMethod.GET)
	public String getDeviceEnergy(ModelMap model,String txtId,String txtName) {
		model.put("txtId", txtId);
		model.put("txtName", txtName);
		return "choose/choose_deviceEnergy";
	}
	
	@ResponseBody
	@RequestMapping(value = "devicePagerWater", method = RequestMethod.GET)
	public Pageable<Device> devicePagerWater(Pagination<Device> pager, Device condition, Integer flag) {
		return deviceService.findDeptByWater(pager, condition);
	}
	
	@ResponseBody
	@RequestMapping(value = "devicePagerGas", method = RequestMethod.GET)
	public Pageable<Device> devicePagerGas(Pagination<Device> pager, Device condition, Integer flag) {
		return deviceService.findDeptByGas(pager, condition);
	}
	@ResponseBody
	@RequestMapping(value = "devicePagerElectric", method = RequestMethod.GET)
	public Pageable<Device> devicePagerElectric(Pagination<Device> pager, Device condition, Integer flag) {
		return deviceService.findDeptByElectric(pager, condition);
	}
	
	@ResponseBody
	@RequestMapping(value = "devicePagerEnergy", method = RequestMethod.GET)
	public Pageable<Device> devicePagerEnergy(Pagination<Device> pager, Device condition, Integer flag) {
		return deviceService.findDeptByEnergy(pager, condition);
	}
	/*
	@ResponseBody
	@RequestMapping(value = "devicePager", method = RequestMethod.GET)
	public JsonPager<Device> devicePager(Pagination<Device> pager, Device condition, Integer flag) {
		//flag=0获取所有的电表，flag=1获取设备电表以外的电表
		if(flag != 1){
			List<DeviceMeter> dmList = deviceMeterService.findDmByCondition(null);
			List<Long> deviceMeterIds = new ArrayList<Long>();
			for(DeviceMeter dm : dmList){
				deviceMeterIds.add(dm.getDeviceMeterId());
			}
			condition.setDeviceIds(deviceMeterIds);
		}
		condition.setCategoryId(eleCatagoryId);//获取电表的设备
		deviceService.findByAllConditon(pager, condition);
		return toJsonPager(pager);
	}*/
	/**
	*选择分项
	 */
	@RequestMapping("subSystem")
	public String lookup(String txtId,String txtName,String type,Integer nhType,Long id,ModelMap model){
		List<NhItem> list=subSystemInfoService.selectCategories(nhType);
		
		List<Long> ids=new ArrayList<Long>();
		if(id!=1){
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
        return "choose/choose_subSystemInfo";
	}
	@RequestMapping("waterSubSystem")
	public String lookupWater(String txtId,String txtName,String type,ModelMap model){
		List<WaterSubSystemInfo> list=waterSubSystemInfoService.selectCategories();
		list.add(new WaterSubSystemInfoController().getRootSubSystemInfo());
		model.put("treeDatas", list);
		model.put("txtId", txtId);  
        model.put("txtName", txtName); 
        model.put("type", type);
        return "choose/choose_subSystemInfo";
	}
	@RequestMapping("energySubSystem")
	public String lookupEnergy(String txtId,String txtName,String type,ModelMap model){
		List<EnergySubSystemInfo> list=energySubSystemInfoService.selecCategories();
		list.add(new EnergySubSystemInfoController().getRootSubSystemInfo());
		model.put("treeDatas", list);
		model.put("txtId", txtId);  
		model.put("txtName", txtName); 
		model.put("type", type);
		return "choose/choose_subSystemInfo";
	}
	
	@RequestMapping("gasSubSystem")
	public String lookupGas(String txtId,String txtName,String type,ModelMap model){
		List<GasSubSystemInfo> list=gasSubSystemInfoService.selecCategories();
		list.add(new GasSubSystemInfoController().getRootSubSystemInfo());
		model.put("treeDatas", list);
		model.put("txtId", txtId);  
		model.put("txtName", txtName); 
		model.put("type", type);
		return "choose/choose_subSystemInfo";
	}
	@RequestMapping("task")
	public String taskLoopup(String txtId,String txtName,String type,ModelMap model){
		List<TaskCatagory> tclist = taskCategoryService.getTaskCategory();
		model.put("treeDatas", tclist);
		model.put("txtId", txtId);  
        model.put("txtName", txtName); 
        model.put("type", type);
		return "choose/choose_task";
	}
	@RequestMapping("taskCategory")
	public String taskLoop(String txtId,String txtName,String type,ModelMap model){
		List<TaskCatagory> tclist = taskCategoryService.getTaskCategory();
		model.put("treeDatas", tclist);
		model.put("txtId", txtId);  
		model.put("txtName", txtName); 
		model.put("type", type);
		return "choose/choose_taskCategoryCode";
	}
	/*
     *选维修人员
     */
	@RequestMapping("repairPerson")
    public String repairEvtCategory(Long categoryType,Long categoryId,Long areaId,String txtId,String txtName, String dialogId,String txtGroupId, String txtGroupName,ModelMap model) {
		List<RepairEvtCategoryPerson> treeList = null;
		treeList = repairEvtCategoryPersonService.findByCategoryIdAndAreaId(categoryType,categoryId, areaId);
		model.put("treeList", treeList);
        model.put("txtId", txtId);  
        model.put("txtName", txtName);
        model.put("txtGroupId", txtGroupId);  
        model.put("txtGroupName", txtGroupName); 
        model.put("dialogId", dialogId);
        return "choose/choose_repairPerson";
    }
	
	@RequestMapping(value="serialNumber",method=RequestMethod.GET)
	@ResponseBody
	public String serialNumber(String tableName){
		if(null==tableName) return "";
		if("TASK".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.TASK);
		}else if("REPAIR".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.REPAIR);
		}else if("LEAVE_APPLY".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.LEAVE_APPLY);
		}else if("STOCK_IN".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.STOCK_IN);
		}else if("STOCK_OUT".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.STOCK_OUT);
		}else if("TRANSFER".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.TRANSFER);
		}else if("INVENTORY".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.INVENTORY);
		}else if("PRICE_CHANGE".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.PRICE_CHANGE);
		}else if("DUTY_CHANGE".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.DUTY_CHANGE);
		}else if("MAINTAIN_PLAN".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.MAINTAIN_PLAN);
		}else if("MAINTAIN_ORDER".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.MAINTAIN_ORDER);
		}else if("INSPECTION_ORDER".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.INSPECTION_ORDER);
		}else if("SPEC_MAINTAIN_PLAN".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.SPEC_MAINTAIN_PLAN);
		}else if("SPEC_MAINTAIN_ORDER".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.SPEC_MAINTAIN_ORDER);
		}else if("TASK_PLAN".equals(tableName)){
			return SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.TASK_PLAN);
		}else{
			return "";
		}
	}
}
