package com.supconit.nhgl.schedule.controller.taskPlan;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.EnumDetail;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.nhgl.schedule.entites.TaskCatagory;
import com.supconit.nhgl.schedule.entites.TaskExecutionPlan;
import com.supconit.nhgl.schedule.service.MonitorTaskService;
import com.supconit.nhgl.schedule.service.TaskCatagoryService;
import com.supconit.nhgl.schedule.service.TaskExecutionPlanService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import jodd.datetime.JDateTime;

/**
 * 执行计划
 * @author WangHaiBO
 *
 */
@Controller
@RequestMapping("nhgl/schedule/controller/taskPlan")
public class TaskPlanController  extends BaseMonitorTaskController{

    
	@Autowired
	private TaskExecutionPlanService taskPlanService;
	@Autowired
	private MonitorTaskService mtService;
	@Autowired
	private PersonService personService;
	@Autowired
	private TaskCatagoryService taskCategoryService;
		
	/**
	 * 准备实体对象。
	 * 
	 * @return
	 */	
//	@ModelAttribute("prepareMonitorTask")
//	public MonitorTask prepareMonitorTask() {
//		return new MonitorTask();
//	}
	
    @RequestMapping("go")
	public String go(ModelMap model) {
    	List<TaskCatagory> tclist = taskCategoryService.getTaskCategory();
    	List<EnumDetail> ed = DictUtils.getDictList(DictTypeEnum.TASK_VESTING);
    	model.put("taskV", ed);
    	String taskVesting = "";
    	for(Integer i = 0; i < ed.size(); i++){
    		taskVesting = taskVesting + ed.get(i).getEnumText() + ",";
    	}
    	model.put("tree", taskPlanService.buildTree(tclist));
    	model.put("taskVesting", taskVesting);
    	model.put("tclist", tclist);
		return "nhgl/schedule/taskPlan/taskPlan_list";
	}	
    
    /*
    get "taskPlan" list
    */
    @ResponseBody
    @RequestMapping("list")
    public Pageable<TaskExecutionPlan> list(
			Pagination<TaskExecutionPlan> pager,
            @ModelAttribute TaskExecutionPlan taskPlan,
			ModelMap model)  {
		 taskPlanService.find(pager, taskPlan);
		model.put("taskPlan", taskPlan);
		model.put("pager", pager);
		
		return pager;
	}


    /*
    save  taskPlan
    TaskPlan object instance 
    */
//    @ResponseBody
//	@RequestMapping(value ="save", method = RequestMethod.POST)    
//	public ScoMessage save(@FormBean(value = "monitorTask", modelCode = "prepareMonitorTask")MonitorTask monitorTask) {
//		 TaskExecutionPlan taskPlan = null;
//         if(monitorTask.getId()==null){
//        	 try {
//				taskPlanService.saveInsert(monitorTask);
//				return ScoMessage.success("plan/taskPlan/go","操作成功。");
//			} catch (Exception e) {
//				return ScoMessage.error("操作失败");
//			}
//            
//        }
//        else{
//        	try {
//				taskPlanService.saveUpdate(monitorTask);
//				return ScoMessage.success("plan/taskPlan/go","操作成功。");
//			} catch (Exception e) {
//				return ScoMessage.error("操作失败");
//			}
//        }
//            
//        
//	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Date selectdate,
			ModelMap model) {
		model.put("selectdate", selectdate);
		// 任务归属
		List<EnumDetail> edlist = DictUtils
				.getDictList(DictTypeEnum.TASK_VESTING);
		model.put("taskTypeList", taskCategoryService.getTaskCategory());
		model.put("edlist", edlist);
		return "nhgl/schedule/taskPlan/taskPlan_edit";
	}
	    
	/**
	 * 
	 * @param id
	 * @param model
	 * @描述 增加任务
	 * @return
	 */
//	@RequestMapping(value = "addTask", method = RequestMethod.GET)
//	public String addTask(ModelMap model, Integer id) {
//		// 任务归属
//		List<EnumDetail> edlist = DictUtils
//				.getDictList(DictTypeEnum.TASK_VESTING);
//		model.put("edlist", edlist);
//		if (id != null) {
//			MonitorTask monitorTask = mtService.findById(id);
//			switch (monitorTask.getTaskVesting()) {
//			case 1:
//				monitorTask.setTypeName(Utils.WATER);
//				break;
//			case 2:
//				monitorTask.setTypeName(Utils.ELECTRIC);
//				break;
//			case 3:
//				monitorTask.setTypeName(Utils.GAS);
//				break;
//			default:
//				break;
//			}
//			model.put("monitorTask", monitorTask);
//		}
//		model.put("taskTypeList", taskCategoryService.getTaskCategory());
//		return "nhgl/base/taskPlan/task_edit";
//	}
	
	/**
	 * 
	 * @param id
	 * @param model
	 * @描述 增加任务
	 * @return
	 */
//	@RequestMapping(value = "del", method = RequestMethod.GET)
//	public AjaxMessage del(ModelMap model, Integer id) {
//		try{
//			taskPlanService.del(id);
//			return AjaxMessage.error("删除成功");
//		}catch(Exception e){
//			return AjaxMessage.error("删除失败");
//		}
//		
//	}
	
    /**
     * 计划任务管理 日历
     * @param model
     * @return
     */
    @RequestMapping(value = "goCanlendarModel")
	public String goCanlendarModel(ModelMap model, String taskVesting,Date time){
    	JDateTime date = new JDateTime(new Date());
    	model.put("year", date.getYear());
    	model.put("month", date.getMonth());
    	model.put("day", date.getDay());
    	if(time!=null){
    		date = new JDateTime(time);
    	}
    	model.put("defaultDate", date.toString("YYYY-MM-DD"));
    	
    	model.put("taskVesting", taskVesting);
    	model.put("time", time);
		return "nhgl/schedule/taskPlan/taskPlan_canlendar";
	}
    
    @ResponseBody
    @RequestMapping(value = "canlendarModel", method = RequestMethod.POST)
	public List<TaskExecutionPlan> canlendarModel(ModelMap model,Date timeStart,Date timeEnd, String taskVesting) throws ParseException{
		Date[] dd = {timeStart,timeEnd};
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(timeStart);
//		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE)); 
		List<Date> listDate = null;
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TaskExecutionPlan taskExecutionPlan=new TaskExecutionPlan();
		taskExecutionPlan.setEndDate(timeEnd);
		taskExecutionPlan.setStartDate(timeStart);
		taskExecutionPlan.setStrTaskVesting(taskVesting);
		List<TaskExecutionPlan> listTaskPlans = taskPlanService.findBetweenTimes(taskExecutionPlan);
		for(TaskExecutionPlan tep : listTaskPlans){
//			timeEnd = calendar.getTime();
			//获取这个月执行该计划的所有日期
			listDate = this.loadMonitorObject(tep, tep.getStartDate(), tep.getEndDate());
			if(listDate!=null&&listDate.size()>0){
//				if(listDate.get(0).after(tep.getStartDate()))
//					listDate.add(tep.getStartDate());
				tep.setDateList(listDate);
				
			}
			else{
				listDate = new ArrayList<Date>();
				listDate.add(tep.getStartDate());
				tep.setDateList(listDate);
			}
		}
		return listTaskPlans;
	}
    
}
