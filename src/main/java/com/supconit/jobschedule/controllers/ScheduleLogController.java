/**
 * 
 */
package com.supconit.jobschedule.controllers;

import java.util.ArrayList;
import java.util.List;

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
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.jobschedule.entities.ScheduleLog;
import com.supconit.jobschedule.services.ScheduleLogService;
/** ========================== 自定义区域开始 ========================== **/
/************************* 自定义区域内容不会被覆盖 *************************/

import hc.base.domains.Pagination;
import hc.mvc.annotations.FormBean;



/** ========================== 自定义区域结束 ========================== **/
/**
 * JOB定义控制层。
 * 
 * @author 
 * @create 2014-06-05 11:26:12 
 * @since 
 * 
 */
@Controller("jobschedule_scheduleLog_controller")
@RequestMapping("/jobschedule/schedule-log")
public class ScheduleLogController extends BaseControllerSupport{

	/**
	 * 日志服务。
	 */
	private transient static final Logger	logger	= LoggerFactory.getLogger(ScheduleLogController.class);

	/**
	 * 注入服务。
	 */
	@Autowired
	private ScheduleLogService						scheduleLogService;

	/**
	 * 准备实体对象。
	 * 
	 * @return
	 */	
	@ModelAttribute("preparescheduleLog")
	public ScheduleLog preparescheduleLog() {
		return new ScheduleLog();
	}
	
	/**
	 * 列表展现。
	 * 
	 * @param model
	 * @return
	 */	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<LabelValueBean> labelValueBeans=new ArrayList<LabelValueBean>();
		labelValueBeans.add(new LabelValueBean("全部", ""));
		labelValueBeans.add(new LabelValueBean("成功", "Y"));
		labelValueBeans.add(new LabelValueBean("失败", "N"));
		labelValueBeans.add(new LabelValueBean("正在执行", "I"));
		model.put("success", htmlSelectOptions(labelValueBeans, ""));
		return "jobschedule/schedule_log/list";
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
	public Pagination<ScheduleLog> list(Pagination<ScheduleLog> pager, @FormBean(value = "condition", modelCode = "preparescheduleLog") ScheduleLog condition) {
		/* Validate Pager Parameters */
		//if (pager.getPageNo() < 1 || pager.getPageSize() < 1 || pager.getPageSize() > Pagination.MAX_PAGE_SIZE) return pager;
				
		this.scheduleLogService.find(pager, condition); //获取分页查询结果
		
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
		return "jobschedule/schedule_log/add";
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
		model.put("scheduleLog", this.scheduleLogService.getById(id));
		return "jobschedule/schedule_log/edit";
	}

	/**
	 * 保存编辑内容。
	 * 
	 * @param equipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"add", "edit"}, method = RequestMethod.POST)
	public ScoMessage doEdit(@FormBean(value = "scheduleLog", modelCode = "preparescheduleLog") ScheduleLog scheduleLog) {
		try {
			this.scheduleLogService.save(scheduleLog);
			return ScoMessage.success("操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ScoMessage.error(e.getMessage());
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
	public ScoMessage doDelete(Long[] ids) {
		try {
			if (null == ids ||ids.length<=0) return ScoMessage.error("错误的参数。");
			this.scheduleLogService.deleteByIds(ids);
			return ScoMessage.success("操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ScoMessage.error(e.getMessage());
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
		model.put("scheduleLog", this.scheduleLogService.getById(id));
		return "jobschedule/schedule_log/view";
	}
	
		
	/** ========================== 自定义区域开始 ========================== **/
	/************************* 自定义区域内容不会被覆盖 *************************/
	
	
	
	/** ========================== 自定义区域结束 ========================== **/
}