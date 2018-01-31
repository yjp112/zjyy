/**
 * 
 */
package com.supconit.nhgl.schedule.controller.taskPlan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.LabelValueBean;
import com.supconit.nhgl.schedule.entites.CriteriaDetail;
import com.supconit.nhgl.schedule.entites.MonitorObject;
import com.supconit.nhgl.schedule.entites.MonitorTask;
import com.supconit.nhgl.schedule.entites.TaskExecutionPlan;
import com.supconit.nhgl.schedule.service.MonitorObjectService;
import com.supconit.nhgl.schedule.service.MonitorTaskService;
import com.supconit.nhgl.utils.GraphUtils;

import hc.base.domains.AjaxMessage;
import hc.base.domains.Pagination;
import hc.mvc.annotations.FormBean;



/** ========================== 自定义区域结束 ========================== **/
/**
 * 监测任务控制层-------测试。
 * 
 * @author 
 * @create 2014-06-16 18:01:51 
 * @since 
 * 
 */
@Controller("nhgl_ycynjc_jcrw_controller")
@RequestMapping("nhgl/basic/taskSet")
public class MonitorTaskController extends BaseControllerSupport{

	/**
	 * 日志服务。
	 */
	private transient static final Logger	logger	= LoggerFactory.getLogger(MonitorTaskController.class);

	/**
	 * 注入服务。
	 */
	@Autowired
	private MonitorTaskService						monitorTaskService;
	@Resource
	private MonitorObjectService monitorObjectService;

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
	 * 列表展现。
	 * 
	 * @param model
	 * @return
	 */	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		return "ycynjc/jcrw/list";
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
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public Pagination<MonitorTask> list(Pagination<MonitorTask> pager, @FormBean(value = "condition", modelCode = "prepareJcrw") MonitorTask condition) {
		/* Validate Pager Parameters */
		if (pager.getPageNo() < 1 || pager.getPageSize() < 1 || pager.getPageSize() > Pagination.MAX_PAGE_SIZE) return pager;
				
		this.monitorTaskService.find(pager, condition); //获取分页查询结果
		
		return pager;
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
	@RequestMapping(value = "tasklist", method = RequestMethod.POST)
	public Pagination<MonitorTask> tasklist(Pagination<MonitorTask> pager, @FormBean(value = "condition", modelCode = "prepareJcrw") MonitorTask condition) {
		/* Validate Pager Parameters */
		if (pager.getPageNo() < 1 || pager.getPageSize() < 1 || pager.getPageSize() > Pagination.MAX_PAGE_SIZE) return pager;
				
		if(("").equals(condition.getTaskName()))
			condition.setTaskName(null);
		if(condition.getTaskVesting() == null)
			condition.setTaskVesting(null);
		if(condition.getTaskType() == null)
			condition.setTaskType((long) 10000);
		this.monitorTaskService.findTaskList(pager, condition); //获取分页查询结果
		for(MonitorTask tep : pager){
			switch(tep.getTaskVesting()){
			case 3 : 
				tep.setTypeName(GraphUtils.WATER); break;
			case 2 : 
				tep.setTypeName(GraphUtils.ELECTRIC); break;
			case 4 : 
				tep.setTypeName(GraphUtils.GAS); break;
			case 1 :
				tep.setTypeName(GraphUtils.DEVICE); break;
			default : break;
			}
		}
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
		return "nhgl/basic/ycyn/add_index";
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
		MonitorTask task=this.monitorTaskService.getById(id);
		if(task==null){
			task=new MonitorTask();
			//task.setTaskCode("taskCode");		
			task.setTaskName("taskName");		
			task.setTaskType(1L);		
			task.setTaskDesc("taskDesc");		
			task.setNoticeTmpl("noticeTmpl");		
			task.setCrCode("crCode");	
			
			TaskExecutionPlan executionPlan=new TaskExecutionPlan();
			executionPlan.setTaskCode("taskCode");		
			executionPlan.setExecuteType(1);		
			executionPlan.setMonitorHourStart("03");		
			executionPlan.setMonitorHourEnd("10");		
			executionPlan.setExecuteParam("0;1;2;6");		
			executionPlan.setStartDate(new Date());		
			//executionPlan.setNextDate(new Date());		
			executionPlan.setEndDate(new Date());	
			List<MonitorObject> monitorObjects=new ArrayList<MonitorObject>();
			List<CriteriaDetail> criteriaDetails=new ArrayList<CriteriaDetail>();
			CriteriaDetail c=new CriteriaDetail();
			criteriaDetails.add(c);
			c.setCrCondition("#tagValue==\"OFF\"");
			c.setCrScore(new BigDecimal(15));
			task.setExecutionPlan(executionPlan);
			task.setMonitorObjects(monitorObjects);
			task.setCriteriaDetails(criteriaDetails);
		}
		model.put("task", task);
		model.put("monitorHourStart", monitorHourOptions("00"));
		model.put("monitorHourEnd", monitorHourOptions("23"));
		return "nhgl/basic/ycyn/add_index";
	}

	/**
	 * 保存编辑内容。
	 * 
	 * @param equipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"add", "edit"}, method = RequestMethod.POST)
	public AjaxMessage doEdit(@FormBean(value = "task", modelCode = "prepareJcrw") MonitorTask task) {
		task.setTaskType(1L);
		// TODO Validate
		try {
			this.monitorTaskService.save(task);
			return AjaxMessage.success(task.getId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxMessage.error(e.getMessage());
		}
	}
	
	/**
	 * 删除一条记录。
	 * 
	 * @param jcrw
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public AjaxMessage doDelete(MonitorTask jcrw) {
		try {
			if (null == jcrw || null == jcrw.getId()) return AjaxMessage.error("错误的参数。");
			this.monitorTaskService.delete(jcrw);
			return AjaxMessage.success(jcrw.getId());
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
		model.put("jcrw", this.monitorTaskService.getById(id));
		return "ycynjc/jcrw/view";
	}
	
		
	/** ========================== 自定义区域开始 ========================== **/
	/************************* 自定义区域内容不会被覆盖 *************************/
	@ResponseBody
	@RequestMapping(value = "loadMonitorObject")
	public List<MonitorObject> loadMonitorObject(@FormBean(value = "monitorObject") MonitorObject condition){
		return monitorObjectService.findMonitorObject(condition);
	}
	/**生成监控时段 下拉框
	 * @param checkedValue
	 * @return
	 */
	private String monitorHourOptions(String checkedValue){
		List<LabelValueBean> labelValueBeans=new ArrayList<LabelValueBean>();
		for (int i = 0; i < 24; i++) {
			String tmp=StringUtils.leftPad(i+"", 2,"0");;
			labelValueBeans.add(new LabelValueBean(tmp, tmp));
		}
		
		return htmlSelectOptions(labelValueBeans, checkedValue);
	}
	
	
	/** ========================== 自定义区域结束 ========================== **/
}