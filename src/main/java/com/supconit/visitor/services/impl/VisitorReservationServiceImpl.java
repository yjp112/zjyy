package com.supconit.visitor.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.StringUtil;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;
import com.supconit.common.utils.UtilTool;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.visitor.daos.VisitorDao;
import com.supconit.visitor.daos.VisitorReservationDao;
import com.supconit.visitor.entities.Visitor;
import com.supconit.visitor.entities.VisitorReservation;
import com.supconit.visitor.services.VisitorReservationService;

import hc.base.domains.Pageable;

@Service
public class VisitorReservationServiceImpl extends AbstractBaseBusinessService<VisitorReservation, Long>
		implements VisitorReservationService {

	@Autowired
	private VisitorDao visitorDao;

	@Autowired
	private AttachmentDao attachmentDao;

	@Autowired
	private VisitorReservationDao reservationVisitorDao;

	@Autowired
	private DepartmentService deptService;

	@Override
	@Transactional
	public void deleteById(Long ids) {
		isAllowDelete(ids);
		reservationVisitorDao.deleteById(ids);
		visitorDao.deleteByVisitorId(ids);
	}

	@Override
	public VisitorReservation getById(Long id) {
		return reservationVisitorDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(VisitorReservation reservationVisitor) {
		reservationVisitor.setActualVisitors(0);
		reservationVisitorDao.insert(reservationVisitor);
		for (Visitor visitor : reservationVisitor.getVisitorList()) {
			visitor.setReservationId(reservationVisitor.getId());
			visitorDao.insert(visitor);
		}
	}

	@Override
	@Transactional
	public void update(VisitorReservation reservationVisitor) {
		reservationVisitorDao.update(reservationVisitor);
		visitorDao.deleteByVisitorId(reservationVisitor.getId());
		for (Visitor visitor : reservationVisitor.getVisitorList()) {
			visitor.setReservationId(reservationVisitor.getId());
			visitorDao.insert(visitor);
		}
	}

	@Override
	public VisitorReservation findById(Long id) {
		VisitorReservation vr = reservationVisitorDao.findById(id);
		if (null != vr) {
			vr.setVisitor(visitorDao.findByReservationId(id));
		}
		return vr;
	}

	@Override
	public Pageable<VisitorReservation> findByPages(Pageable<VisitorReservation> pager, VisitorReservation condition) {
		setParameter(condition);
		pager = reservationVisitorDao.findByPages(pager, condition);
		Iterator<VisitorReservation> it = pager.iterator();
		while (it.hasNext()) {

			VisitorReservation r = it.next();
			r.setVisitReason(DictUtils.getDictLabel(DictTypeEnum.VISIT_REASON, r.getVisitReason().toString()));
			r.setVisitStatus(DictUtils.getDictLabel(DictTypeEnum.VISIT_STATUS, r.getVisitStatus().toString()));

		}
		return pager;
	}

	private void isAllowDelete(Long id) {
		// 访客状态是1：在访，2：已访 访客记录不能删除
		VisitorReservation reservationVisitor = reservationVisitorDao.findById(id);
		if ("1".equals(reservationVisitor.getVisitStatus())) {
			throw new BusinessDoneException("该访客状态是在访，不能删除。");
		}
		if ("2".equals(reservationVisitor.getVisitStatus())) {
			throw new BusinessDoneException("该访客状态是已访，不能删除。");
		}

	}

	public void setParameter(VisitorReservation condition) {
		if (condition.getDeptNameId() != null) {
			List<Department> deptList = deptService.findByPid(condition.getDeptNameId());
			List<Long> deptId = new ArrayList<Long>();
			if (!UtilTool.isEmptyList(deptList))
				;
			{
				for (Department dept : deptList) {
					deptId.add(dept.getId());
				}
			}
			deptId.add(condition.getDeptNameId());
			condition.setDeptChildIds(deptId);
		}
	}

	@Override
	public List<Visitor> autoCompletePersonList(String visitorName) {
		return visitorDao.autoCompletePersonList(visitorName);
	}

	@Override
	public void update(VisitorReservation reservationVisitor, String[] fileorignal, String[] filename,
			String[] delfiles, String fileLength) {
		try {
			reservationVisitorDao.update(reservationVisitor);
			visitorDao.deleteByVisitorId(reservationVisitor.getId());
			for (Visitor visitor : reservationVisitor.getVisitorList()) {
				visitor.setReservationId(reservationVisitor.getId());
				visitorDao.insert(visitor);
			}
			attachmentDao.saveAttachements(reservationVisitor.getId(), Constant.ATTACHEMENT_VISITOR, fileorignal,
					filename, delfiles, fileLength);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessDoneException("保存失败" + e.getMessage());
		}
	}

	@Override
	public List<VisitorReservation> findAllVisitorReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> findToLeaveVisitorsIds() {
		return reservationVisitorDao.findToLeaveVisitorsIds();
	}

	@Override
	public VisitorReservation findById2(Long reservationId) {
		VisitorReservation vr = reservationVisitorDao.findById2(reservationId);
		return vr;
	}

	@Override
	public Visitor findVisitorByCardNo(String cardNo) {
		
		List<Visitor> visitors ;
		if (StringUtil.isNullOrEmpty(cardNo)) {
			return null;
		} else {
			 visitors =  visitorDao.findByCardNo(cardNo);
		}
		if(null==visitors||visitors.size()==0){
			return null;
		}else{
			return visitors.get(0);
		}
	}

	@Override
	public void update2(VisitorReservation reservationVisitor) {

		reservationVisitorDao.update(reservationVisitor);
		//visitorDao.deleteByVisitorId(reservationVisitor.getId());
		for (Visitor visitor : reservationVisitor.getVisitorList()) {
			visitor.setReservationId(reservationVisitor.getId());
			//visitorDao.insert(visitor);
			visitorDao.update(visitor);
		}
	
	}

	/** 手机端加载更多使用 **/
	@Override
	public List<VisitorReservation> selectVisitorReservations(VisitorReservation condition) {
		return this.reservationVisitorDao.selectVisitorReservations(condition);
	}

	@Override
	public VisitorReservation countVisitorReservations(VisitorReservation condition) {
		return this.reservationVisitorDao.countVisitorReservations(condition);
	}


}
