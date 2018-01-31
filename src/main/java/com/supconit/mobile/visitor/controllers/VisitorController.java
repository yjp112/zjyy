package com.supconit.mobile.visitor.controllers;

import com.supconit.base.domains.Organization;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.employee.todo.entities.BpmApprovalRecord;
import com.supconit.employee.todo.entities.ExUserTask;
import com.supconit.employee.todo.services.BpmApprovalRecordService;
import com.supconit.employee.todo.services.ExUserTaskService;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.mobile.util.DateOperate;
import com.supconit.visitor.entities.Visitor;
import com.supconit.visitor.entities.VisitorReservation;
import com.supconit.visitor.services.VisitorReportService;
import com.supconit.visitor.services.VisitorReservationService;
import hc.base.domains.AjaxMessage;
import hc.mvc.annotations.FormBean;
import hc.safety.manager.SafetyManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wangwei on 16/5/3.
 */
@Controller
@RequestMapping("mobile/visitor")
public class VisitorController extends BaseControllerSupport {

	@Autowired
	private VisitorReservationService visitorReservationService;
	@Autowired
	private PersonService personService;
	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private SafetyManager safetyManager;

	@ModelAttribute("prepareVisitorReservation")
	public VisitorReservation prepareVisitorReservation(){return new VisitorReservation();}

	/**
	 * 访客管理
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(ModelMap map) {
          return "mobile/visitor/list";
    }

	/**
	 * AJAX获取列表数据。
	 * @param visitorReservation 查询条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list_visitor")
	public List<VisitorReservation> list_visitor(@FormBean(value = "condition",modelCode = "prepareVisitorReservation")VisitorReservation visitorReservation)
	{
		List<VisitorReservation> visitorReservations=null;
		visitorReservation.setVisitPersonId(this.getCurrentPersonId());
		String visitStatus=visitorReservation.getVisitStatus();
		String startDate="";
		String endDate="";
		String visitorDate=visitorReservation.getVisitorDate();
		if(visitStatus!=null&&!"".equals(visitStatus))
		{
			if(visitStatus.equals("0"))
			{
				Date today=new Date();
				startDate = DateOperate.DateChange(today,"",0,"yyyy-MM-dd");
				endDate=this.selectVisitorDate(visitorDate,visitStatus);
			}
			else if(visitStatus.equals("2"))
			{
				Date today=new Date();
				endDate = DateOperate.DateChange(today,"",0,"yyyy-MM-dd");
				startDate=this.selectVisitorDate(visitorDate,visitStatus);
			}
			visitorReservation.setStartDate(startDate);
			visitorReservation.setEndDate(endDate);
			long startNum=visitorReservation.getStartNum()+1;
			long endNum=startNum+20;
			visitorReservation.setStartNum(startNum);
			visitorReservation.setEndNum(endNum);
			visitorReservations=this.visitorReservationService.selectVisitorReservations(visitorReservation);
			long total= visitorReservation.getVisitorTotal();
			long visitorId=visitorReservation.getVisitorId();
			if(total==0)
			{
				VisitorReservation visitor=this.visitorReservationService.countVisitorReservations(visitorReservation);
				total=visitor.getVisitorTotal();
				visitorId=visitor.getVisitorId();
			}
			for(VisitorReservation v:visitorReservations)
			{
				String visitors=v.getVisitors();
				long exceptVisitors=v.getExpectVisitors();
				if(exceptVisitors>1)
				{
					if(visitors!=null&&!"".equals(visitors))
					{
						String[] visitorList=visitors.split(" ");
						visitors=visitorList[0]+"等";
					}
					else
					{
						visitors="多人等";
					}
				}
				v.setVisitors(visitors);
				v.setVisitorTotal(total);
				v.setVisitorId(visitorId);
				v.setVisitReasonName(DictUtils.getDictLabel(DictTypeEnum.VISIT_REASON, v.getVisitReason().toString()));
			}
		}
		return visitorReservations;
	}

	/**
	 * 访客管理预约接待
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		return "mobile/visitor/add";
	}

	/**
	 * 访客管理预约接待
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String view(ModelMap map,@RequestParam(required = false) Long id) {
		VisitorReservation visitorReservation = null;
		ExPerson receptor = null;
		visitorReservation = this.visitorReservationService.findById(id);
		if(visitorReservation!=null)
		{
			long personId=visitorReservation.getReceptorID();
			receptor = personService.getById(personId);
			if(receptor!=null)
			{
				List<Organization> orList = organizationService.getFullDeptNameByPersonId(personId);
				String fullDeptName = OrganizationUtils.joinFullDeptName(orList);
				receptor.setDeptId(orList.get(0).getDeptId());
				receptor.setDeptName(fullDeptName);
			}
			visitorReservation.setVisitReasonName(DictUtils.getDictLabel(DictTypeEnum.VISIT_REASON, visitorReservation.getVisitReason().toString()));
			map.put("visitorReservation", visitorReservation);
			map.put("receptor", receptor);
		}
		return "mobile/visitor/view";
	}

	/**
	 * 访客管理预约接待
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(ModelMap map,@RequestParam(required = false) Long id) {
		VisitorReservation visitorReservation = null;
		ExPerson receptor = null;
		visitorReservation = this.visitorReservationService.findById(id);
		if(visitorReservation!=null)
		{
			long personId=visitorReservation.getReceptorID();
			receptor = personService.getById(personId);
			if(receptor!=null)
			{
				List<Organization> orList = organizationService.getFullDeptNameByPersonId(personId);
				String fullDeptName = OrganizationUtils.joinFullDeptName(orList);
				receptor.setDeptId(orList.get(0).getDeptId());
				receptor.setDeptName(fullDeptName);
			}
			visitorReservation.setVisitReasonName(DictUtils.getDictLabel(DictTypeEnum.VISIT_REASON, visitorReservation.getVisitReason().toString()));
			map.put("visitorReservation", visitorReservation);
			map.put("receptor", receptor);
		}
		return "mobile/visitor/edit";
	}

	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public AjaxMessage save(@FormBean(value = "condition",modelCode = "prepareVisitorReservation")VisitorReservation visitorReservation) {

		// itemList.remove(0);//删除无效实体
		visitorReservation.setVisitStatus("0");
		if(visitorReservation.getVisitDays()!=null&&visitorReservation.getVisitDays()!=0)
		{
			Date visitTime=visitorReservation.getVisitTime();
			if (visitTime!=null)
			{
				String expectLeave=DateOperate.DateChange(visitTime,"day",(visitorReservation.getVisitDays()-1),"yyyy-MM-dd");
				try {
					Date expectLeaveTime=DateUtils.parseDate(expectLeave,"yyyy-MM-dd");
					visitorReservation.setExpectLeaveTime(expectLeaveTime);
				} catch (ParseException e) {
					e.printStackTrace();
					return AjaxMessage.error("访问时间有误！");
				}
			}
			else
			{
				return AjaxMessage.error("访问时间有误！");
			}
		}
		else
		{
			return AjaxMessage.error("访问时间有误！");
		}
		if (null == visitorReservation.getId()) {
			// 查询接待所在的部门,插入RESERVATION表VISIT_DEPTID字段
			long personId=getCurrentPersonId();
			copyCreateInfo(visitorReservation);
			visitorReservation.setReceptorID(personId);
			List<Organization> orList = organizationService.getFullDeptNameByPersonId(personId);
			visitorReservation.setDeptNameId(orList.get(0).getDeptId());
			visitorReservation.setReversationName(getCurrentPersonName());
			this.visitorReservationService.insert(visitorReservation);
			return AjaxMessage.success("预约成功");
		} else {
			copyUpdateInfo(visitorReservation);
			this.visitorReservationService.update(visitorReservation);
			return AjaxMessage.success("修改成功");

		}
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public AjaxMessage delete(@RequestParam(required = false) Long id) {
		if (!this.visitorReservationService.findById(id).getCreateId().equals(getCurrentPersonId())) {
			return AjaxMessage.error("您不是登记人，不可以删除。");
		}
		visitorReservationService.deleteById(id);
		return  AjaxMessage.success("删除成功");
	}

	private String selectVisitorDate(String visitorDate,String visitStatus)
	{
		String selectDate="";
		Date today=new Date();
		if(visitStatus!=null&&"0".equals(visitStatus))
		{
			if(visitorDate!=null&&!"".equals(visitorDate))
			{
				if("week".equals(visitorDate))
				{
					selectDate = DateOperate.DateChange(today,"day",7,"yyyy-MM-dd");
				}
				else if("month".equals(visitorDate))
				{
					selectDate = DateOperate.DateChange(today,"month",1,"yyyy-MM-dd");
				}
				else if("month3".equals(visitorDate))
				{
					selectDate = DateOperate.DateChange(today,"month",3,"yyyy-MM-dd");
				}
				else if("year".equals(visitorDate))
				{
					selectDate = DateOperate.DateChange(today,"year",1,"yyyy-MM-dd");
				}
			}
			else
			{
				selectDate = DateOperate.DateChange(today,"month",1,"yyyy-MM-dd");
			}
		}
		else
		{
			if(visitorDate!=null&&!"".equals(visitorDate))
			{
				if("week".equals(visitorDate))
				{
					selectDate = DateOperate.DateChange(today,"day",-7,"yyyy-MM-dd");
				}
				else if("month".equals(visitorDate))
				{
					selectDate = DateOperate.DateChange(today,"month",-1,"yyyy-MM-dd");
				}
				else if("month3".equals(visitorDate))
				{
					selectDate = DateOperate.DateChange(today,"month",-3,"yyyy-MM-dd");
				}
				else if("year".equals(visitorDate))
				{
					selectDate = DateOperate.DateChange(today,"year",-1,"yyyy-MM-dd");
				}
			}
			else
			{
				selectDate = DateOperate.DateChange(today,"month",-1,"yyyy-MM-dd");
			}
		}
		return selectDate;
	}
}