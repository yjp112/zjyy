package com.supconit.visitor.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.domains.Organization;
import com.supconit.base.entities.EnumDetail;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.visitor.entities.Visitor;
import com.supconit.visitor.entities.VisitorReservation;
import com.supconit.visitor.services.VisitorReservationService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * 预约登记控制类
 * 
 * @author Huanghaitao
 * @日期 2015/06
 */
@Controller
@RequestMapping("visitor/reservation")
public class VisitorReservationController extends BaseControllerSupport {

	@Autowired
	private PersonService personService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private VisitorReservationService reservationVisitorService;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String go(ModelMap model) {
		return "visitor/reservation/list";
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Pageable<VisitorReservation> list(Pagination<VisitorReservation> pager,
			@ModelAttribute VisitorReservation reservationVisitor) {
		boolean b = hasAdminRole();
		if (!b) {
			reservationVisitor.setReceptorID(getCurrentPersonId());
		}
		return reservationVisitorService.findByPages(pager, reservationVisitor);
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Boolean viewOnly, ModelMap model,
			@RequestParam(required = false) Long id, String type) {

		VisitorReservation reservationVisitor = null;
		ExPerson receptor = null;
		long currentPersonId = getCurrentPersonId();
		Date now = new Date();
		// 修改
		viewOnly = false;
		if (null != id) {
			reservationVisitor = reservationVisitorService.findById(id);
			receptor = setReservePerson(receptor, reservationVisitor.getReceptorID());
			if ("0".equals(reservationVisitor.getVisitStatus())
					&& reservationVisitor.getCreateId().longValue() == currentPersonId
					&& (now.before(reservationVisitor.getVisitTime()) || DateUtils.compare(now, reservationVisitor.getVisitTime())==0)) {
				viewOnly = false;
			}else{
				viewOnly = true;
			}
		} else {
			reservationVisitor = new VisitorReservation();
			copyCreateInfo(reservationVisitor);
//			reservationVisitor.setExpectVisitors(1);
			reservationVisitor.setVisitDays(1);
			receptor = setReservePerson(receptor, currentPersonId);
		}
		List<EnumDetail> visitorTypeList = DictUtils.getDictList(DictTypeEnum.VISIT_REASON);
		List<EnumDetail> visitorZJLXList = DictUtils.getDictList(DictTypeEnum.ZJLX);
		model.put("visitorTypeList", visitorTypeList);
		model.put("visitorZJLXList", visitorZJLXList);
		model.put("reservationVisitor", reservationVisitor);
		model.put("receptor", receptor);
		model.put("viewOnly", viewOnly);
		model.put("type", type);
		return "visitor/reservation/edit";
	}

	/**
	 * 设置接待人信息及部门全名
	 */
	private ExPerson setReservePerson(ExPerson receptor, long personId) {
		receptor = personService.getById(personId);
		List<Organization> orList = organizationService.getFullDeptNameByPersonId(personId);
		String fullDeptName = OrganizationUtils.joinFullDeptName(orList);
		receptor.setDeptId(orList.get(0).getDeptId());
		receptor.setDeptName(fullDeptName);
		return receptor;
	}

	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(VisitorReservation reservationVisitor) {
		try {
			Date today = sdf.parse(DateUtils.format(new Date(), "yyyy-MM-dd"));
			if (reservationVisitor.getVisitTime().before(today)) {
				return ScoMessage.error("来访时间不能早于今天。");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return ScoMessage.error("格式化日期发生错误。");
		}

		List<Visitor> itemList = reservationVisitor.getVisitorList();
		// itemList.remove(0);//删除无效实体
		int count = 0;
		for (int i = 0; i < itemList.size(); i++) {
			Visitor visitor = itemList.get(i);
			if (StringUtils.isNotEmpty(visitor.getVisitorName())) {
				if (visitor.getVisitorName().length() > 10) {
					throw new BusinessDoneException("访客姓名长度不大于10个字符。");
				}
				if (null != visitor.getVisitorUnit() && visitor.getVisitorUnit().length() > 20) {
					throw new BusinessDoneException("访客单位长度不大于20个字符。");
				}
				count++;
			} else {
				continue;
			}
		}
		if (count == 0) {
			return ScoMessage.error("请至少登记一个访客信息。");
		}
		reservationVisitor.setVisitStatus("0");
		if (null == reservationVisitor.getId()) {
			// 查询接待所在的部门,插入RESERVATION表VISIT_DEPTID字段
			copyCreateInfo(reservationVisitor);
			reservationVisitorService.insert(reservationVisitor);
		} else {
			copyUpdateInfo(reservationVisitor);
			reservationVisitorService.update(reservationVisitor);
		}
		return ScoMessage.success("visitor/reservation/list", ScoMessage.SAVE_SUCCESS_MSG);
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ScoMessage delete(@RequestParam("ids") Long ids) {
		if (!reservationVisitorService.findById(ids).getCreateId().equals(getCurrentPersonId())) {
			throw new BusinessDoneException("您不是登记人，不可以删除。");
		}
		reservationVisitorService.deleteById(ids);
		return ScoMessage.success("visitor/reservation/list", ScoMessage.DELETE_SUCCESS_MSG);
	}

	@RequestMapping("autoCompletePersonList")
	@ResponseBody
	public List<Visitor> autoCompletePersonList(String nameText) {
		return reservationVisitorService.autoCompletePersonList(nameText);
	}

}
