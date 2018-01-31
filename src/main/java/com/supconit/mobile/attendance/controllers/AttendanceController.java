package com.supconit.mobile.attendance.controllers;

import com.supconit.base.entities.EnumDetail;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.hikvision.entities.AccessCount;
import com.supconit.hikvision.services.AccessCountService;
import com.supconit.mobile.pick.entities.MobilePicker;
import hc.mvc.annotations.FormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangwei on 16/4/8.
 */
@Controller
@RequestMapping("mobile/attendance")
public class AttendanceController extends BaseControllerSupport {

	@Autowired
	private AccessCountService accessCountService;

	@ModelAttribute("prepareAccessCount")
	public AccessCount prepareAccessCount(){return new AccessCount();}
	/**
	 * 我的待办
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
    public String view(ModelMap map) {
          return "mobile/attendance/view";
    }

	/**
	 * AJAX获取列表数据。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "searchMonthAttendances")
	public List<AccessCount> searchMonthAttendances(@FormBean(value = "condition",modelCode = "prepareAccessCount")AccessCount accessCount)
	{
		long personId=getCurrentPersonId();
		List<AccessCount> accessCounts=null;
		if(personId>0)
		{
			accessCount.setPersonId(getCurrentPersonId());
			accessCounts=this.accessCountService.searchMonthAttendances(accessCount);
		}
		return accessCounts;
	}

	/**
	 * AJAX获取列表数据。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "searchUnusualMonthAttendances")
	public List<AccessCount> searchUnusualMonthAttendances(@FormBean(value = "condition",modelCode = "prepareAccessCount")AccessCount accessCount)
	{
		long personId=getCurrentPersonId();
		List<AccessCount> accessCounts=null;
		if(personId>0)
		{
			accessCount.setPersonId(getCurrentPersonId());
			accessCounts=this.accessCountService.searchUnusualMonthAttendances(accessCount);
		}
		return accessCounts;
	}

	/**
	 * AJAX获取列表数据。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "countMonthAttendances")
	public AccessCount countMonthAttendances(@FormBean(value = "condition",modelCode = "prepareAccessCount")AccessCount accessCount)
	{
		long personId=getCurrentPersonId();
		AccessCount access=null;
		if(personId>0)
		{
			accessCount.setPersonId(getCurrentPersonId());
			access=this.accessCountService.countMonthAttendances(accessCount);
		}
		return access;
	}

	/**
	 * 根据日期搜索当前用户的考勤信息
	 * @param accessCount
	 * @return
     */
	@ResponseBody
	@RequestMapping(value = "searchAttendance")
	public AccessCount searchAttendance(@FormBean(value = "condition",modelCode = "prepareAccessCount")AccessCount accessCount)
	{
		long personId=getCurrentPersonId();
		AccessCount access=null;
		if(personId>0)
		{
			accessCount.setPersonId(getCurrentPersonId());
			access=this.accessCountService.searchAttendance(accessCount);
		}
		return access;
	}
}