/**
 * 
 */
package com.supconit.nhgl.schedule.controller.taskPlan;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.nhgl.analyse.electric.dept.entities.Department;
import com.supconit.nhgl.analyse.electric.dept.service.DepartmentService;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;
import com.supconit.nhgl.basic.ngArea.service.NgAreaService;
import com.supconit.nhgl.basic.ngDept.entities.NhDepartment;
import com.supconit.nhgl.basic.ngDept.service.NhDepartmentService;
import com.supconit.nhgl.schedule.entites.MonitorObject;
import com.supconit.nhgl.schedule.entites.MonitorTask;
import com.supconit.nhgl.schedule.service.CriteriaDetailService;
import com.supconit.nhgl.schedule.service.MonitorObjectService;
import com.supconit.nhgl.schedule.service.MonitorTaskService;
import com.supconit.nhgl.schedule.service.TaskCatagoryService;
import com.supconit.nhgl.schedule.service.TaskExecutionPlanService;

import hc.base.domains.AjaxMessage;
import hc.mvc.annotations.FormBean;

/** ========================== 自定义区域结束 ========================== **/
/**
 * 监测任务控制层。
 * 
 * @author
 * @create 2014-06-16 18:01:51
 * @since
 * 
 */
@Controller("nhgl_ycynjc_jcrw_water_controller")
@RequestMapping("nhgl/basic/taskSet/water")
public class WaterMonitorTaskController extends BaseMonitorTaskController {

	/**
	 * 日志服务。
	 */
	private transient static final Logger logger = LoggerFactory.getLogger(WaterMonitorTaskController.class);

	/**
	 * 注入服务。
	 */
	@Autowired
	private MonitorTaskService monitorTaskService;
	@Resource
	private MonitorObjectService monitorObjectService;
	@Resource
	private CriteriaDetailService detailService;
	@Resource
	private TaskExecutionPlanService executionPlanService;
	@Resource
	private GeoAreaService geoAreaService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private TaskCatagoryService tcService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private NhDepartmentService nhDepartmentService;
	@Autowired
	private NgAreaService ngAreaService;

	@Value("${water_category}")
	private String waterCatagoryCode;

	/**
	 * 准备实体对象。
	 * 
	 * @return
	 */
	@ModelAttribute("prepareJcrw")
	public MonitorTask prepareJcrw() {
		return new MonitorTask();
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String edit(ModelMap model) {
		// 改为从VIEW_NH_S_AREA中取数据
		List<NgArea> treearea = ngAreaService.findAllSArea();
		for (int i = 0; i < treearea.size(); i++) {
			if ("ROOT".equalsIgnoreCase(treearea.get(i).getAreaCode())) {
				treearea.get(i).setAreaName("服务区域簇");
				break;
			}
		}
		Department dept = new Department();
		dept.setpId(10001l);
		// 改为从VIEW_NH_S_DEPT中取
		// List<Department> treeDept = departmentService.findByCon(dept);
		List<NhDepartment> treeDept = nhDepartmentService.findAllSDeptWithoutVitualRoot();
		// List<GeoArea> treearea=geoAreaService.findAll();
		// List<DeviceCategory> treecategory=deviceCategoryService.findAll();
		// Department dept = new Department();
		// dept.setpId(10001l);
		// List<Department> treeDept = departmentService.findByCon(dept);
		model.put("treeListLou", treearea);
		model.put("treeListType", treeDept);
		model.put("monitorHourStart", monitorHourOptions(""));
		model.put("monitorHourEnd", monitorHourOptions(""));
		model.put("waterCatagoryCode", waterCatagoryCode);
		return "nhgl/schedule/taskPlan/ycyn/water/water";
	}

	/**
	 * 编辑展示。
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = true) Long id, ModelMap model) {
		MonitorTask task = this.monitorTaskService.getById(id);
		task.setCategoryText(tcService.getById(task.getTaskType()).getCatagoryText());
		String hourStart = "";
		String hourEnd = "";
		if (task != null) {
			task.setCriteriaDetails(detailService.selectByTaskCode(task.getTaskCode()));
			task.setExecutionPlan(executionPlanService.selectByTaskCode(task.getTaskCode()));
			task.setMonitorObjects(monitorObjectService.selectByTaskCode(task.getTaskCode()));
			hourStart = task.getExecutionPlan().getMonitorHourStart();
			hourEnd = task.getExecutionPlan().getMonitorHourEnd();
		} /*
			 * else { task = new MonitorTask(); // task.setTaskCode("taskCode");
			 * task.setTaskName("taskName"); task.setTaskType(1L);
			 * task.setTaskDesc("taskDesc"); task.setNoticeTmpl("noticeTmpl");
			 * task.setCrCode("crCode");
			 * 
			 * TaskExecutionPlan executionPlan = new TaskExecutionPlan();
			 * executionPlan.setTaskCode("taskCode");
			 * executionPlan.setExecuteType(1);
			 * executionPlan.setMonitorHourStart("03");
			 * executionPlan.setMonitorHourEnd("10");
			 * executionPlan.setExecuteParam("0;1;2;6");
			 * executionPlan.setStartDate(new Date());
			 * //executionPlan.setNextDate(new Date());
			 * executionPlan.setEndDate(new Date()); List<MonitorObject>
			 * monitorObjects = new ArrayList<MonitorObject>();
			 * List<CriteriaDetail> criteriaDetails = new
			 * ArrayList<CriteriaDetail>(); CriteriaDetail c = new
			 * CriteriaDetail(); criteriaDetails.add(c);
			 * c.setCrCondition("#tagValue==\"OFF\""); c.setCrScore(new
			 * BigDecimal(15)); task.setExecutionPlan(executionPlan);
			 * task.setMonitorObjects(monitorObjects);
			 * task.setCriteriaDetails(criteriaDetails); }
			 */
		// List<GeoArea> treearea=geoAreaService.findAll();
		// Department dept = new Department();
		// dept.setpId(10001l);
		// List<Department> treecategory=departmentService.findByCon(dept);
		List<NgArea> treearea = ngAreaService.findAll();
		for (int i = 0; i < treearea.size(); i++) {
			if ("ROOT".equalsIgnoreCase(treearea.get(i).getAreaCode())) {
				treearea.get(i).setAreaName("服务区域簇");
				break;
			}
		}
		Department dept = new Department();
		dept.setpId(10001l);
		List<NhDepartment> treeDept = nhDepartmentService.findAllWithoutVitualRoot();
		model.put("treeListLou", treearea);
		model.put("treeListType", treeDept);
		model.put("task", task);
		model.put("monitorHourStart", monitorHourOptions(hourStart));
		model.put("monitorHourEnd", monitorHourOptions(hourEnd));
		model.put("waterCatagoryCode", waterCatagoryCode);
		return "nhgl/schedule/taskPlan/ycyn/water/water";
	}

	/**
	 * 保存编辑内容。
	 * 
	 * @param equipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "add", "edit" }, method = RequestMethod.POST)
	public AjaxMessage doEdit(@FormBean(value = "task", modelCode = "prepareJcrw") MonitorTask task) {
		// task.setTaskType(2L);//电

		// TODO Validate
		try {
			String[] strings = task.getDeviceIdListStr().split(";");
			List<MonitorObject> monitorObjects = new ArrayList<MonitorObject>();
			for (String deviceId : strings) {
				MonitorObject object = new MonitorObject();
				object.setDeviceId(Long.parseLong(deviceId));
				monitorObjects.add(object);
			}
			task.setMonitorObjects(monitorObjects);
			this.monitorTaskService.save(task);
			return AjaxMessage.success(task.getId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxMessage.error(e.getMessage());
		}
	}

	/** ========================== 自定义区域开始 ========================== **/
	/************************* 自定义区域内容不会被覆盖 *************************/
	/** ========================== 自定义区域结束 ========================== **/
}