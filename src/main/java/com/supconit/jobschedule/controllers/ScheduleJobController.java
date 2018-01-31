/**
 * 
 */
package com.supconit.jobschedule.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.jobschedule.entities.ScheduleJob;
import com.supconit.jobschedule.services.ScheduleJobService;
import com.supconit.jobschedule.services.SchedulerManagerService;
import com.supconit.jobschedule.utils.CronExpressionEx;
/** ========================== 自定义区域开始 ========================== **/
/************************* 自定义区域内容不会被覆盖 *************************/

import hc.base.domains.Pagination;
import hc.mvc.annotations.FormBean;



/** ========================== 自定义区域结束 ========================== **/
/**
 * JOB执行历史控制层。
 * 
 * @author 
 * @create 2014-06-05 11:21:48 
 * @since 
 * 
 */
@Controller("jobschedule_scheduleJob_controller")
@RequestMapping("/jobschedule/schedule-job")
public class ScheduleJobController extends BaseControllerSupport{

	/**
	 * 日志服务。
	 */
	private transient static final Logger	logger	= LoggerFactory.getLogger(ScheduleJobController.class);

	/**
	 * 注入服务。
	 */
	@Autowired
	private ScheduleJobService						scheduleJobService;
	@Autowired
	private SchedulerManagerService					schedulerManagerService;

	/**
	 * 准备实体对象。
	 * 
	 * @return
	 */	
	@ModelAttribute("preparescheduleJob")
	public ScheduleJob preparescheduleJob() {
		return new ScheduleJob();
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
		labelValueBeans.add(new LabelValueBean("运行", String.valueOf(SchedulerManagerService.STATUS_READY)));
		labelValueBeans.add(new LabelValueBean("停止", String.valueOf(SchedulerManagerService.STATUS_STOP)));
		model.put("status", htmlSelectOptions(labelValueBeans, ""));
		return "jobschedule/schedule_job/list";
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
	public Pagination<ScheduleJob> list(Pagination<ScheduleJob> pager, @FormBean(value = "condition", modelCode = "preparescheduleJob") ScheduleJob condition) {
		/* Validate Pager Parameters */
		if (pager.getPageNo() < 1 || pager.getPageSize() < 1 || pager.getPageSize() > Pagination.MAX_PAGE_SIZE) return pager;
				
		this.scheduleJobService.find(pager, condition); //获取分页查询结果
		
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
		List<LabelValueBean> labelValueBeans=new ArrayList<LabelValueBean>();
		labelValueBeans.add(new LabelValueBean("是", "Y"));
		labelValueBeans.add(new LabelValueBean("否", "N"));
		
		List<LabelValueBean> labelValueBeans2=new ArrayList<LabelValueBean>();
		labelValueBeans2.add(new LabelValueBean("Spring Bean", "bean"));
		labelValueBeans2.add(new LabelValueBean("类名", "class"));
		
		model.put("ignoreError", htmlSelectOptions(labelValueBeans, "Y"));
		model.put("concurrent", htmlSelectOptions(labelValueBeans, "Y"));
		model.put("beanOrClass", htmlSelectOptions(labelValueBeans2, "class"));
		return "jobschedule/schedule_job/add";
	}
	
	/**
	 * 编辑展示。
	 * 
	 * @param id
	 * @param model
	 * @return
	 */	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = true) Long id,  ModelMap model,Long flag) {
		ScheduleJob job=this.scheduleJobService.getById(id);
		List<LabelValueBean> labelValueBeans=new ArrayList<LabelValueBean>();
		labelValueBeans.add(new LabelValueBean("是", "Y"));
		labelValueBeans.add(new LabelValueBean("否", "N"));
		
		List<LabelValueBean> labelValueBeans2=new ArrayList<LabelValueBean>();
		labelValueBeans2.add(new LabelValueBean("Spring Bean", "bean"));
		labelValueBeans2.add(new LabelValueBean("类名", "class"));
		
		model.put("ignoreError", htmlSelectOptions(labelValueBeans, job.getIgnoreError()));
		model.put("concurrent", htmlSelectOptions(labelValueBeans, job.getConcurrent()));
		model.put("beanOrClass", htmlSelectOptions(labelValueBeans2, job.getBeanOrClass()));
		model.put("scheduleJob", job);
		model.put("flag", flag);
		return "jobschedule/schedule_job/add";
	}

	/**
	 * 保存编辑内容。
	 * 
	 * @param equipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"add", "edit"}, method = RequestMethod.POST)
	public ScoMessage doEdit(@FormBean(value = "scheduleJob", modelCode = "preparescheduleJob") ScheduleJob scheduleJob) {
		try {
			if(scheduleJob.getId()==null&&scheduleJob.getStatus()==null){
				scheduleJob.setStatus(SchedulerManagerService.STATUS_STOP);
			}
			this.scheduleJobService.save(scheduleJob);
			if(scheduleJob.getStatus()==SchedulerManagerService.STATUS_READY){
				schedulerManagerService.scheduleJob(scheduleJob);
			}else if(scheduleJob.getStatus()==SchedulerManagerService.STATUS_STOP){
				schedulerManagerService.stopJob(scheduleJob);
			}
			return ScoMessage.success("操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ScoMessage.error(e.getMessage());
		}
	}
	
	/**
	 * 删除一条记录。
	 * 
	 * @param scheduleJob
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public ScoMessage doDelete(Long ids) {
		try {
			if (null == ids ) return ScoMessage.error("错误的参数。");
			ScheduleJob scheduleJob=scheduleJobService.getById(ids);
			schedulerManagerService.stopJob(scheduleJob);
			this.scheduleJobService.delete(scheduleJob);
			return ScoMessage.success("操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ScoMessage.error(e.getMessage());
		}
	}
	
	/** ========================== 自定义区域开始 ========================== **/
	/************************* 自定义区域内容不会被覆盖 *************************/
	
	/**
	 * 运行JOB
	 * 
	 * @param scheduleJob
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "runJob", method = RequestMethod.POST)
	public ScoMessage doRun(ScheduleJob scheduleJob) {
		try {
			if (null == scheduleJob || null == scheduleJob.getId()) return ScoMessage.error("错误的参数。");
			scheduleJob=scheduleJobService.getById(scheduleJob.getId());
			this.schedulerManagerService.scheduleJob(scheduleJob);
			return ScoMessage.success("操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ScoMessage.error(e.getMessage());
		}
	}	
	/**
	 * 停止JOB
	 * 
	 * @param scheduleJob
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "stopJob", method = RequestMethod.POST)
	public ScoMessage doStop(ScheduleJob scheduleJob) {
		try {
			if (null == scheduleJob || null == scheduleJob.getId()) return ScoMessage.error("错误的参数。");
			scheduleJob=scheduleJobService.getById(scheduleJob.getId());
			this.schedulerManagerService.stopJob(scheduleJob);
			return ScoMessage.success("操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ScoMessage.error(e.getMessage());
		}
	}	
	
	/**
	 * 立即执行JOB
	 * 
	 * @param scheduleJob
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "runImmediately", method = RequestMethod.POST)
	public ScoMessage runImmediately(ScheduleJob scheduleJob) {
		try {
			if (null == scheduleJob || null == scheduleJob.getId()) return ScoMessage.error("错误的参数。");
			final ScheduleJob job=scheduleJobService.getById(scheduleJob.getId());
			Thread t=new Thread(new Runnable() {
				@Override
				public void run() {
					SpringContextHolder.getBean(SchedulerManagerService.class).runImmediately(job);
				}
			});
			t.setDaemon(true);
			t.start();
			return ScoMessage.success("操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ScoMessage.error(e.getMessage());
		}
	}	
	/** ========================== 自定义区域结束 ========================== **/
	@ResponseBody
	@RequestMapping(value = "computeExecuteTime")
	public List<String> loadMonitorObject(@FormBean(value = "scheduleJob", modelCode = "preparescheduleJob") ScheduleJob scheduleJob){
		int maxTimes=10;
		CronExpressionEx expressionEx;
		try {
			expressionEx = new CronExpressionEx(scheduleJob.getCronExpression());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	      java.util.Date startDate = scheduleJob.getStartDate();
	      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     List<String> result=new ArrayList<String>();
	     List<Date> tmp=expressionEx.computeFireTimes(startDate, scheduleJob.getEndDate(), maxTimes);
	     for (int i = 0; tmp!=null&&i < tmp.size(); i++) {
				String item=(i+1) + "."+"\t"+simpleDateFormat.format(tmp.get(i));
				result.add(item);
		}
		return result;
	}
}