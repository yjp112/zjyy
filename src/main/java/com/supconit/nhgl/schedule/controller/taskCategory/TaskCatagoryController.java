package com.supconit.nhgl.schedule.controller.taskCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.schedule.entites.MonitorTask;
import com.supconit.nhgl.schedule.entites.TaskCatagory;
import com.supconit.nhgl.schedule.service.MonitorTaskService;
import com.supconit.nhgl.schedule.service.TaskCatagoryService;
import com.supconit.nhgl.schedule.service.TaskExecutionPlanService;

import ch.qos.logback.classic.Logger;
import hc.base.domains.AjaxMessage;
import hc.base.domains.Pagination;
import hc.mvc.annotations.FormBean;

/**
 * 任务分类列表
 * @author WangHaiBO
 *
 */
@Controller
@RequestMapping("nhgl/schedule/controller/taskCatagory")
public class TaskCatagoryController extends BaseControllerSupport {
	
	@Autowired
	private TaskCatagoryService tcService;
	@Autowired
	private TaskExecutionPlanService taskPlanService;
	@Autowired
	private MonitorTaskService mtService;
	/**
	 * 日志服务。
	 */
	private transient static final Logger	logger	= (Logger) LoggerFactory.getLogger(TaskCatagoryController.class);
	
	/**
	 * 准备实体对象。
	 * 
	 * @return
	 */	
	@ModelAttribute("prepareTaskCatagory")
	public TaskCatagory prepareTaskCatagory() {
		return new TaskCatagory();
	}
	
	@RequestMapping("list")
	public String list(ModelMap model){
		model.put("tree", taskPlanService.buildTree(tcService.getTaskCategory()));
		return "/nhgl/schedule/taskCatagory/taskCatagory_list";
	}
	
	@RequestMapping("tree")
	public String tree(){
		return taskPlanService.buildTree(tcService.getTaskCategory());
	}
	
	/**
	 * AJAX获取列表数据。
	 * 
	 * @param pager
	 *            分页信息
	 * @param condition
	 *            查询条件
	 * @return
	 */	
	@ResponseBody
	@RequestMapping(value = "pager", method = RequestMethod.POST)
	public Pagination<TaskCatagory> pager(Pagination<TaskCatagory> pager, @FormBean(value = "condition", modelCode = "prepareTaskCatagory") TaskCatagory condition) {
		/* Validate Pager Parameters */
		if (pager.getPageNo() < 1 || pager.getPageSize() < 1 || pager.getPageSize() > Pagination.MAX_PAGE_SIZE) return pager;
		if(condition.getId() == null)
			condition.setId((long) 10000);
		this.tcService.findTaskByPager(pager, condition); //获取分页查询结果
		
		return pager;
	}
	
	/**
	 * 新增展示。
	 * 
	 * @param id
	 * @param model
	 * @return
	 */	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String edit(ModelMap model) {
		return "/nhgl/schedule/taskCatagory/add";
	}
	
	
	
	/**
	 * 编辑展示。
	 * 
	 * @param id
	 * @param model
	 * @return
	 */	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = true) Long id,  ModelMap model) {
		model.put("taskCatagory", this.tcService.getById(id));
		model.addAttribute("tclist", tcService.getTaskCategory());
		return "/nhgl/schedule/taskCatagory/add";
	}

	/**
	 * 保存编辑内容。
	 * 
	 * @param equipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"add", "edit"}, method = RequestMethod.POST)
	public AjaxMessage doEdit(@FormBean(value = "taskCatagory", modelCode = "prepareTaskCatagory") TaskCatagory taskCatagory) {
		
		List<TaskCatagory> tclist = tcService.getTaskCategory();
		for(TaskCatagory tc : tclist){
			if((taskCatagory.getCatagoryCode()).equals(tc.getCatagoryCode())){
				if(taskCatagory.getId().longValue() != tc.getId().longValue() && taskCatagory.getId() != null)
					return AjaxMessage.error("任务分类编码已存在");
			}
		}
		try {
			if (taskCatagory.getId() != null) {
				if(taskCatagory.getParentCode().equals(taskCatagory.getCatagoryCode())){
					return AjaxMessage.error("任务分类父类编码不能与任务分类编码相同");
				}else{
					this.tcService.update(taskCatagory);
				}
			} else {
				this.tcService.save(taskCatagory);
			}
			return AjaxMessage.success(taskCatagory.getId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxMessage.error(e.getMessage());
		}
	}
	
	/**
	 * 删除一批记录。
	 * 
	 * @param scheduleLog
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public AjaxMessage doDelete(@RequestParam(value = "id[]")Long[] ids) {
		List<MonitorTask> monitorTask = null;
		TaskCatagory tc = null;
		String msg = "";
		List<TaskCatagory> tclist = tcService.getTaskCategory();
		try {
			if (null == ids ||ids.length<=0) return AjaxMessage.error("错误的参数。");
			for(Integer i = 0; i < ids.length; i++){
				tc = new TaskCatagory();
				monitorTask = new ArrayList<MonitorTask>();
				monitorTask = mtService.getByTaskType(ids[i]);
				tc = tcService.getById(ids[i]);
				if(monitorTask != null && monitorTask.size() > 0){
					msg = "在执行计划中被使用，请先删除该执行计划";
					return AjaxMessage.error(tc.getCatagoryText() + msg);
				}
				for(Integer j = 0; j < tclist.size(); j++){
					if(tc.getId() == tclist.get(j).getId())
						continue;
					if(tc.getCatagoryCode().equals(tclist.get(j).getParentCode())){
						msg = "该任务分类为父分类，请先删除该任务子类";
						return AjaxMessage.error("'"+tc.getCatagoryText() + "'" + msg);
					}
				}
			}
			this.tcService.deleteByIds(ids);
			return AjaxMessage.success(Arrays.asList(ids));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxMessage.error(e.getMessage());
		}
	}
	
	/**
	 * 查看展示。
	 * 
	 * @param id
	 * @param model
	 * @return
	 */	
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String view(@RequestParam(required = true) Long id,  ModelMap model) {
		model.put("taskCatagory", this.tcService.getById(id));
		model.addAttribute("tclist", tcService.getTaskCategory());
		return "/nhgl/schedule/taskCatagory/view";
	}
}
