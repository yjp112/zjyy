package com.supconit.visitor.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.eclipse.jdt.internal.compiler.env.IGenericField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.mail.handlers.text_plain;
import com.supconit.base.domains.Organization;
import com.supconit.base.entities.Attachment;
import com.supconit.base.entities.EnumDetail;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.AttachmentService;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.visitor.entities.Visitor;
import com.supconit.visitor.entities.VisitorReservation;
import com.supconit.visitor.services.VisitorReservationService;

import hc.base.domains.Pagination;

/**
 * 访客接待控制类
 * 
 * @author
 * @日期 2015/06
 */
@Controller
@RequestMapping("visitor/reception2")
public class VisitorReceptionController2 extends BaseControllerSupport {
	@Autowired
	private VisitorReservationService reservationVisitorService;
	@Autowired
	private PersonService personService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private DepartmentService deptService;
	@Autowired
	private AttachmentService attachmentService;
	private static Pagination<VisitorReservation> cache;

	@Value("${file.tmpsave}")
	private String tmpPath;
	@Value("${image.server.url}")
	private String imageServerUrl;
	@Value("${image.temp.server.url}")
	private String imageTempServerUrl;

	@ResponseBody
	@RequestMapping(value = "leaveByCardNo", method = RequestMethod.POST)
	public Visitor leaveByCardNo(String cardNo) {
		//根据卡号得到访客信息
		Visitor visitor = reservationVisitorService.findVisitorByCardNo(cardNo);
		Visitor returnedVisitor = null;
		if (null != visitor && null != visitor.getReservationId()) {
			//根据访客的预约ID号查询预约信息
			VisitorReservation visitorReservationInDb = reservationVisitorService.findById2(visitor.getReservationId());
			int unleftVisitorCount = 0;
			//查询没有还卡的人数
			for (Visitor temp : visitorReservationInDb.getVisitorList()) {
				if (false == "1".equals(temp.getVisitorReturnCard())||temp.getVisitorReturnCard()==null) {
					unleftVisitorCount++;
				}
			}
			//如果还剩下一个人没还卡，则更换预约表中的状态
			if (unleftVisitorCount == 1) {
				visitorReservationInDb.setVisitStatus("2");
			} else {
				visitorReservationInDb.setVisitStatus("1");
			}
			//
			for (int j = 0; j < visitorReservationInDb.getVisitorList().size(); j++) {
				returnedVisitor = visitorReservationInDb.getVisitorList().get(j);
				if (returnedVisitor.getId().intValue() == visitor.getId().intValue()) {
					returnedVisitor.setVisitorCardNo(visitor.getVisitorCardNo());
					returnedVisitor.setReturnTime(new Date());
					returnedVisitor.setVisitorReturnCard("1");
					break;
				}
			}
			visitorReservationInDb.setFlag(false);
			copyUpdateInfo(visitorReservationInDb);
			visitorReservationInDb.setActualLeaveTime(new Date());
			visitorReservationInDb.setRevokePersonID(getCurrentPersonId());

			visitorReservationInDb.setRevokeTime(new Date());
			reservationVisitorService.update2(visitorReservationInDb);
			 //reservationVisitorService.findById(visitor.getReservationId());
		}
		return returnedVisitor;
	}

	@ResponseBody
	@RequestMapping(value = "leave", method = RequestMethod.POST)
	public ScoMessage leaveSave(VisitorReservation reservationVisitor, String[] fileorignal, String[] filename,
			String type) {

		int index = 0;
		Visitor tmp;
		List<Visitor> itemList = reservationVisitor.getVisitorList();
		List<Visitor> leftVisitors = new ArrayList<>();
		Iterator<Visitor> iter = itemList.iterator();
		Map<Long, Integer> leaveCount = new HashMap<>();
		// 刪除没有归还卡号的人员,记录每一个预约号的人员数量
		// for(int i=0;i<itemList.size();i++){
		while (iter.hasNext()) {
			tmp = iter.next();
			// tmp = itemList.get(i);
			if (StringUtils.isEmpty(tmp.getVisitorCardNo()) && StringUtils.isEmpty(tmp.getVisitorName())) {
				// iter.remove();
				// itemList.remove(i);

			}
			if (StringUtils.isNotEmpty(tmp.getVisitorReturnCard()) && "1".equals(tmp.getVisitorReturnCard())) {
				index++;
				leftVisitors.add(tmp);
				if (leaveCount.get(tmp.getReservationId()) != null) {
					leaveCount.put(tmp.getReservationId(), (leaveCount.get(tmp.getReservationId() + 1)));
				} else {
					List<Visitor> tempVisitors = new ArrayList<>();
					tempVisitors.add(tmp);
					leaveCount.put(tmp.getReservationId(), 1);
				}
			}
		}
		if (index == 0) {
			return ScoMessage.error("请确认至少一个访客归还访客证。");
		}
		// 归还卡号的人员
		for (int i = 0; i < leftVisitors.size(); i++) {
			Visitor next = leftVisitors.get(i);
			VisitorReservation visitorReservationInDb = reservationVisitorService.findById2(next.getReservationId());
			if (visitorReservationInDb.getVisitorList().size() == leaveCount.get(next.getReservationId()).intValue()) {
				visitorReservationInDb.setVisitStatus("2");
			} else {
				visitorReservationInDb.setVisitStatus("1");
			}
			for (int j = 0; j < visitorReservationInDb.getVisitorList().size(); j++) {
				Visitor visitorInDb = visitorReservationInDb.getVisitorList().get(j);

				if (visitorInDb.getId().intValue() == next.getId().intValue()) {
					visitorInDb.setVisitorCardNo(next.getVisitorCardNo());
					visitorInDb.setReturnTime(new Date());
					visitorInDb.setVisitorReturnCard(next.getVisitorReturnCard());
				}
			}
			visitorReservationInDb.setFlag(false);
			copyUpdateInfo(visitorReservationInDb);
			reservationVisitorService.update(visitorReservationInDb, fileorignal, filename, null, "");
		}

		return ScoMessage.success("visitor/reception/list", ScoMessage.SAVE_SUCCESS_MSG);
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Boolean viewOnly, ModelMap model,
			@RequestParam(required = false) Long id, @RequestParam(required = false) String type,
			@RequestParam(required = false) Boolean view) {
		Date now = new Date();
		VisitorReservation reservationVisitor = null;
		List<Attachment> listImage = new ArrayList<Attachment>();
		ExPerson receptor = null;
		ExPerson grantPerson = null;
		ExPerson revokePerson = null;
		model.put("imagePath", imageServerUrl);
		model.put("imageTempPath", imageTempServerUrl);
		// 修改
		viewOnly = false;
		List<Long> ids = reservationVisitorService.findToLeaveVisitorsIds();
		VisitorReservation toLeaveVisitors = new VisitorReservation();
		if (ids != null && ids.size() != 0) {
			for (Long reservationId : ids) {

				reservationVisitor = reservationVisitorService.findById2(reservationId);
				if (null == reservationVisitor || null == reservationVisitor.getVisitorList()) {
					continue;
				}
				toLeaveVisitors.getVisitorList().addAll(reservationVisitor.getVisitorList());
				//
				// for (int i = 0; i < toLeaveVisitors.getVisitorList().size();
				// i++) {
				// toLeaveVisitors.getVisitorList().get(i).setReturnTime(new
				// Date());
				// //
				// toLeaveVisitors.getVisitorList().get(i).setVisitorCardNo("12345678");
				// toLeaveVisitors.getVisitorList().get(i).setVisitorReturnCard("1");
				// break;
				// }
				listImage = attachmentService.getAttachmentByFid(reservationId, "visitor");
				// 查询接待人员
				receptor = personService.getById(reservationVisitor.getReceptorID());
				List<Organization> orList = organizationService
						.getFullDeptNameByPersonId(reservationVisitor.getReceptorID());
				String deptFullName = OrganizationUtils.joinFullDeptName(orList);
				receptor.setDeptName(deptFullName);
				// 查询发证人
				if (null == reservationVisitor.getGrantPersonID()) {
					reservationVisitor.setGrantPersonID(getCurrentPersonId());
				}
				grantPerson = personService.getById(reservationVisitor.getGrantPersonID());
				orList = organizationService.getFullDeptNameByPersonId(reservationVisitor.getGrantPersonID());
				deptFullName = OrganizationUtils.joinFullDeptName(orList);
				grantPerson.setDeptName(deptFullName);
				// 查询收证人
				if (null == reservationVisitor.getRevokePersonID()) {
					reservationVisitor.setRevokePersonID(getCurrentPersonId());
				}
				revokePerson = personService.getById(reservationVisitor.getRevokePersonID());
				orList = organizationService.getFullDeptNameByPersonId(reservationVisitor.getRevokePersonID());
				deptFullName = OrganizationUtils.joinFullDeptName(orList);
				revokePerson.setDeptName(deptFullName);

				if (reservationVisitor.getActualVisitTime() == null && reservationVisitor.getGrantTime() == null
						&& "1".equals(type)) {
					reservationVisitor.setGrantPersonID(getCurrentPersonId());
					reservationVisitor.setActualVisitTime(now);
					reservationVisitor.setGrantTime(now);
				}
				if (reservationVisitor.getActualLeaveTime() == null && reservationVisitor.getRevokeTime() == null
						&& "2".equals(type) && view == false) {
					reservationVisitor.setRevokePersonID(getCurrentPersonId());
					reservationVisitor.setActualLeaveTime(now);
					reservationVisitor.setRevokeTime(now);
				}
				viewOnly = true;
			}
		}
		List<EnumDetail> visitorTypeList = DictUtils.getDictList(DictTypeEnum.VISIT_REASON);
		List<EnumDetail> visitorZJLXList = DictUtils.getDictList(DictTypeEnum.ZJLX);
		List<EnumDetail> visitorRETURNENList = DictUtils.getDictList(DictTypeEnum.VISIT_RETURN_CARD);
		model.put("visitorTypeList", visitorTypeList);
		model.put("visitorZJLXList", visitorZJLXList);
		model.put("visitorRETURNENList", visitorRETURNENList);
		model.put("reservationVisitor", toLeaveVisitors);
		model.put("listImage", listImage);
		model.put("receptor", receptor);
		model.put("grantPerson", grantPerson);
		model.put("revokePerson", revokePerson);
		model.put("type", type);
		model.put("viewOnly", viewOnly);
		model.put("view", view);
		model.put("toLeaveVisitors", toLeaveVisitors);
		if ("1".equals(type)) {
			return "visitor/reception/reception_edit";
		} else if ("2".equals(type)) {
			return "visitor/reception/leave_all_edit";
		} else {
			if (reservationVisitor.getActualVisitTime() == null) {
				model.put("grantPerson", null);
			}
			if (reservationVisitor.getActualLeaveTime() == null) {
				model.put("revokePerson", null);
			}
			return "visitor/reception/visitor_view";
		}
	}

}
