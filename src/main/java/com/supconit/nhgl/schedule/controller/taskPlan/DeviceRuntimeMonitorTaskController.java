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
 * 监测任务控制层-----重要设备。
 * 
 * @author
 * @create 2014-06-16 18:01:51
 * @since
 * 
 */
@Controller("nhgl_ycynjc_jcrw_deviceruntime_controller")
@RequestMapping("nhgl/basic/taskSet/device_runtime")
public class DeviceRuntimeMonitorTaskController extends
		BaseMonitorTaskController {

	/**
	 * 日志服务。
	 */
	private transient static final Logger logger = LoggerFactory
			.getLogger(DeviceRuntimeMonitorTaskController.class);

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
	/**
	 * 准备实体对象。
	 * 
	 * @return
	 */
	@ModelAttribute("prepareJcrw")
	public MonitorTask prepareJcrw() {
		return new MonitorTask();
	}

	/**
	 * 
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String edit(ModelMap model) {
		List<GeoArea> treearea=geoAreaService.findAll();
		Department dept = new Department();
		dept.setpId(10001l);
		List<Department> treeDept = departmentService.findByCon(dept);
		model.put("treeListLou", treearea);
		model.put("treeListType", treeDept);
		model.put("monitorHourStart", monitorHourOptions(""));
		model.put("monitorHourEnd", monitorHourOptions(""));
		return "nhgl/schedule/taskPlan/ycyn/device/device_runtime";
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
		String hourStart="";
		String hourEnd="";
		MonitorTask task = this.monitorTaskService.getById(id);
		task.setCategoryText(tcService.getById(task.getTaskType()).getCatagoryText());
		if (task != null) {
			task.setCriteriaDetails(detailService.selectByTaskCode(task
					.getTaskCode()));
			task.setExecutionPlan(executionPlanService.selectByTaskCode(task.getTaskCode()));
			task.setMonitorObjects(monitorObjectService.selectByTaskCode(task.getTaskCode()));
			hourStart=task.getExecutionPlan().getMonitorHourStart();
			hourEnd=task.getExecutionPlan().getMonitorHourEnd();
		} /*else {
			task = new MonitorTask();
			// task.setTaskCode("taskCode");
			task.setTaskName("taskName");
			task.setTaskType(1L);
			task.setTaskDesc("taskDesc");
			task.setNoticeTmpl("noticeTmpl");
			task.setCrCode("crCode");
			TaskExecutionPlan executionPlan = new TaskExecutionPlan();
			executionPlan.setTaskCode("taskCode");
			executionPlan.setExecuteType(1);
			executionPlan.setMonitorHourStart("03");
			executionPlan.setMonitorHourEnd("10");
			executionPlan.setExecuteParam("0;1;2;6");
			executionPlan.setStartDate(new Date());
			//executionPlan.setNextDate(new Date());
			executionPlan.setEndDate(new Date());
			List<MonitorObject> monitorObjects = new ArrayList<MonitorObject>();
			List<CriteriaDetail> criteriaDetails = new ArrayList<CriteriaDetail>();
			CriteriaDetail c = new CriteriaDetail();
			criteriaDetails.add(c);
			c.setCrCondition("#tagValue==\"OFF\"");
			c.setCrScore(new BigDecimal(15));
			task.setExecutionPlan(executionPlan);
			task.setMonitorObjects(monitorObjects);
			task.setCriteriaDetails(criteriaDetails);
		}*/
		List<GeoArea> treearea=geoAreaService.findAll();
		Department dept = new Department();
		dept.setpId(10001l);
		List<Department> treeDept = departmentService.findByCon(dept);
		model.put("treeListLou", treearea);
		model.put("treeListType", treeDept);
		model.put("task", task);
		model.put("monitorHourStart", monitorHourOptions(hourStart));
		model.put("monitorHourEnd", monitorHourOptions(hourEnd));
		
		return "nhgl/schedule/taskPlan/ycyn/device/device_runtime";
	}

	/**
	 * 保存编辑内容。
	 * 
	 * @param equipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "add", "edit" }, method = RequestMethod.POST)
	public AjaxMessage doEdit(
			@FormBean(value = "task", modelCode = "prepareJcrw") MonitorTask task) {
		//task.setTaskType(1L);
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