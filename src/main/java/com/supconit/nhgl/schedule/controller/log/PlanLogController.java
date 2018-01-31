package com.supconit.nhgl.schedule.controller.log;


import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.EnumDetail;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.nhgl.schedule.controller.taskPlan.BaseMonitorTaskController;
import com.supconit.nhgl.schedule.entites.PlanLog;
import com.supconit.nhgl.schedule.service.PlanLogService;

import hc.base.domains.AjaxMessage;
import hc.base.domains.Pagination;
import hc.mvc.annotations.FormBean;

/**
 * 执行日志
 * @author WangHaiBO
 *
 */
@Controller
@RequestMapping("nhgl/schedule/controller/log")
public class PlanLogController  extends BaseMonitorTaskController{

	/**
	 * 日志服务。
	 */
	private transient static final Logger	logger	= LoggerFactory.getLogger(PlanLogController.class);
	
	/**
	 * 注入服务。
	 */
	@Autowired
	private PlanLogService						planLogService;
		
	/**
	 * 准备实体对象。
	 * 
	 * @return
	 */	
//	@ModelAttribute("prepareMonitorTask")
//	public MonitorTask prepareMonitorTask() {
//		return new MonitorTask();
//	}
	
    
    /**
	 * 执行日志 列表展现。
	 * 
	 * @param model
	 * @return
	 */	
	@RequestMapping(value = "logList")
	public String logList(ModelMap model) {
		List<EnumDetail> ed = DictUtils.getDictList(DictTypeEnum.TASK_VESTING);
    	model.put("taskV", ed);
		return "nhgl/schedule/log/taskPlanlog_list";
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
	@RequestMapping(value = "executeLoglist", method = RequestMethod.POST)
	public Pagination<PlanLog> executeLoglist(Pagination<PlanLog> pager, @FormBean(value = "condition", modelCode = "preparescheduleLog") PlanLog condition) {
		/* Validate Pager Parameters */
		if (pager.getPageNo() < 1 || pager.getPageSize() < 1
				|| pager.getPageSize() > Pagination.MAX_PAGE_SIZE)
			return pager;

		this.planLogService.findLog(pager, condition); // 获取分页查询结果

		return pager;
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
		try {
			if (null == ids ||ids.length<=0) return AjaxMessage.error("错误的参数。");
			this.planLogService.deleteByIds(ids);
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
		model.put("planLog", this.planLogService.getById(id));
		return "nhgl/schedule/log/view";
	}
	
}
