package com.supconit.meeting.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.meeting.entities.RoomInfo;
import com.supconit.meeting.services.RoomInfoService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * 会议统计控制类
 * @author yuhuan
 * @日期 2015/07
 */
@Controller
@RequestMapping("meeting/meetingReport")
public class MeetingReportController  extends BaseControllerSupport {
	@Autowired
	private RoomInfoService meetingService;
	@Autowired
	private DepartmentService departmentService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String go(ModelMap model) {
		List<Department> dList = departmentService.findAllWithoutVitualRoot();
		model.put("dList", dList);
		RoomInfo roomInfo = new RoomInfo();
		String now=sdf.format(new Date());
		roomInfo.setMeetingDateStart(now.substring(0, 7)+"-01");
		roomInfo.setMeetingDateEnd(now);
		model.put("roomInfo", roomInfo);
		return "meeting/report/list";
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<RoomInfo>  list(@ModelAttribute RoomInfo roomInfo,
			Pagination<RoomInfo> pager,
            ModelMap model) {
		return meetingService.meetingReport(pager,roomInfo);
	}
}
