package com.supconit.employee.timeCard.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.employee.timeCard.entities.TimeCard;
import com.supconit.employee.timeCard.services.TimeCardService;

/**
 * 考勤控制类
 * @author yuhuan
 * @日期 2016/04
 */
@Controller
@RequestMapping("employee/timeCard")
public class TimeCardController extends BaseControllerSupport{
	@Autowired
	private TimeCardService timeCardService;
	
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(ModelMap model,Integer deviceId) {
		model.put("deviceId", deviceId);
		model.put("eventTypes", htmlSelectOptions(DictTypeEnum.DOOR_EVENT_TYPE,"198914"));
		return "employee/timeCard/list";
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<TimeCard>  list(TimeCard condition,
			Pagination<TimeCard> pager) {
		boolean b = hasAdminRole();
		if(!b){
			condition.setPersonId(getCurrentPersonId());
		}
		if(null!=condition.getPageSize()) pager.setPageSize(condition.getPageSize());
		if(null!=condition.getStartDate()) condition.setStartTime(condition.getStartDate().getTime());
		if(null!=condition.getEndDate()) condition.setEndTime(condition.getEndDate().getTime());
		if(null!=condition.getDeviceId()) condition.setOrderField("a.EVENT_TIME DESC");
		return timeCardService.findByPage(pager, condition);
	}

}
