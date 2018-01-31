/**
 * 
 */
package com.supconit.nhgl.schedule.controller.taskPlan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.LabelValueBean;
import com.supconit.jobschedule.utils.CronExpressionEx;
import com.supconit.nhgl.schedule.entites.MonitorObject;
import com.supconit.nhgl.schedule.entites.MonitorTask;
import com.supconit.nhgl.schedule.entites.TaskExecutionPlan;
import com.supconit.nhgl.schedule.service.MonitorObjectService;
import com.supconit.nhgl.schedule.service.MonitorTaskService;

import hc.base.domains.AjaxMessage;
import hc.mvc.annotations.FormBean;

/** ========================== 自定义区域结束 ========================== **/
/**
 * 监测任务基础类
 * 
 * @author
 * @create 2014-06-16 18:01:51
 * @since
 * 
 */
@Controller("nhgl_ycynjc_base_controller")
@RequestMapping("nhgl/basic/taskSet/base")
public class BaseMonitorTaskController extends BaseControllerSupport {

	/**
	 * 日志服务。
	 */
	private transient static final Logger log = LoggerFactory
			.getLogger(BaseMonitorTaskController.class);

	/**
	 * 注入服务。
	 */
	@Autowired
	private MonitorTaskService monitorTaskService;
	@Resource
	private MonitorObjectService monitorObjectService;

	/** ========================== 自定义区域开始 ========================== **/
	/************************* 自定义区域内容不会被覆盖 *************************/
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxMessage doDelete(MonitorTask task) {
		try {
			if (null == task || null == task.getId()) return AjaxMessage.error("错误的参数。");
			this.monitorTaskService.delete(task);
			return AjaxMessage.success(task.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return AjaxMessage.error(e.getMessage());
		}
	}
	@ResponseBody
	@RequestMapping(value = "loadMonitorObject")
	public List<MonitorObject> loadMonitorObject(
			@FormBean(value = "monitorObject") MonitorObject condition,String catagoryCode) {
		condition.setCatagoryCode(catagoryCode);
		return monitorObjectService.findMonitorObject(condition);
	}

	@ResponseBody
	@RequestMapping(value = "loadMonitorObjectByTaskCode")
	public List<MonitorObject> loadMonitorObject(
			@RequestParam(required = true) String taskCode) {
		return monitorObjectService.findMonitorObjectByTaskCode(taskCode);
	}

	@ResponseBody
	@RequestMapping(value = "computeExecuteTime")
	public List<String> loadMonitorObject(
			@FormBean(value = "executionPlan") TaskExecutionPlan executionPlan) {
		int maxTimes = 10;
		CronExpressionEx expressionEx;
		try {
			expressionEx = new CronExpressionEx(
					executionPlan.getCronExpression());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		java.util.Date startDate = executionPlan.getStartDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		List<String> result = new ArrayList<String>();
		if(executionPlan.getExecuteType().intValue()==1){
			String item ="1." + "\t"
					+ simpleDateFormat.format(executionPlan.getStartDate());
			result.add(item);
			return result;
		}
	     List<Date> tmp=expressionEx.computeFireTimes(startDate, executionPlan.getEndDate(), maxTimes);
	     for (int i = 0; tmp!=null&&i < tmp.size(); i++) {
				String item=(i+1) + "."+"\t"+simpleDateFormat.format(tmp.get(i));
				result.add(item);
		}		
		return result;
	}

	public  List<Date> loadMonitorObject(
			@FormBean(value = "executionPlan") TaskExecutionPlan executionPlan,
			@RequestParam(required = true, value = "startDate") Date startDate,
			@RequestParam(required = true, value = "endDate") Date endDate) {
		if (log.isInfoEnabled()) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String startDate1Str = "";
			if (executionPlan.getStartDate() != null) {
				startDate1Str = simpleDateFormat.format(executionPlan
						.getStartDate());
			}
			String endDate1Str = "";
			if (executionPlan.getEndDate() != null) {
				endDate1Str = simpleDateFormat.format(executionPlan
						.getEndDate());
			}
			if(endDate == null)
				endDate = startDate;
			log.info("params:[executionPlan.startDate=" + startDate1Str
					+ ",executionPlan.endDate=" + endDate1Str + ",startDate="
					+ simpleDateFormat.format(startDate) + ",endDate="
					+ simpleDateFormat.format(endDate) + "]");
		}
		CronExpressionEx expressionEx;
		try {
			expressionEx = new CronExpressionEx(
					executionPlan.getCronExpression());
		} catch (ParseException e) {
			log.error(executionPlan.toString());
			log.error(e.getMessage()+":cron表达式【"+executionPlan.getCronExpression()+"】",e);
			return null;
		}
		java.util.Date startDateFinal = executionPlan.getStartDate();
		java.util.Date endDateFinal = executionPlan.getStartDate();
		if (startDateFinal == null || startDateFinal.before(startDate)) {
			startDateFinal = new Date();
		}
		if (endDateFinal == null || endDateFinal.before(endDate)) {
			endDateFinal = endDate;
		}
		List<Date> result = new ArrayList<Date>();
		if(executionPlan.getExecuteType().intValue()==1){
			result.add(executionPlan.getStartDate());
			return result;
		}
		try {
			List<Date> tmp = expressionEx.computeFireTimesIncludePassed(startDateFinal, endDateFinal, -1);
			for (int i = 0; tmp!=null&&i < tmp.size(); i++) {
				result.add(tmp.get(i));
			}	
			if (log.isInfoEnabled()) {
				log.info("============taskCode["+executionPlan.getTaskCode()+"]======start=========");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				for (int i = 0; i < result.size(); i++) {
					log.info((i + 1) + "." + "\t"
							+ simpleDateFormat.format(result.get(i)));
				}
				log.info("============taskCode["+executionPlan.getTaskCode()+"]======end=========");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 生成监控时段 下拉框
	 * 
	 * @param checkedValue
	 * @return
	 */
	protected String monitorHourOptions(String checkedValue) {
		List<LabelValueBean> labelValueBeans = new ArrayList<LabelValueBean>();
		for (int i = 0; i < 24; i++) {
			String tmp = StringUtils.leftPad(i + "", 2, "0");
			;
			labelValueBeans.add(new LabelValueBean(tmp, tmp));
		}

		return htmlSelectOptions(labelValueBeans, checkedValue);
	}

	/** ========================== 自定义区域结束 ========================== **/
    @RequestMapping("demo")
	public String go(ModelMap model) {
		return "nhgl/demo/demo-yjynfx";
	}	
    @RequestMapping("demo-nhgs")
	public String goNhgs(ModelMap model) {
		return "nhgl/demo/demo-nhgs";
	}	
    @RequestMapping("demo-fxpj")
    public String goFxpj(ModelMap model) {
    	return "nhgl/demo/demo-fxpj";
    }	
    @RequestMapping("demo-ztgk")
    public String goZtgk(ModelMap model) {
    	return "nhgl/demo/demo-ztgk";
    }	
}