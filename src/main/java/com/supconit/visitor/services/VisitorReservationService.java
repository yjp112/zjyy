package com.supconit.visitor.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.visitor.entities.Visitor;
import com.supconit.visitor.entities.VisitorReservation;

import hc.base.domains.Pageable;


public interface VisitorReservationService extends BaseBusinessService<VisitorReservation, Long> {
    
    /**
     * findById
     * 
     * @param id
     * @return object instance
     */
    VisitorReservation findById(Long id);
    /**
     * 分页查询
     */
    Pageable<VisitorReservation> findByPages(Pageable<VisitorReservation> pager,VisitorReservation condition);
    List<Visitor> autoCompletePersonList(String visitorName);
    
	void update(VisitorReservation reservationVisitor,String[] fileorignal,String[] filename,String[] delfiles,String fileLength);
    
	List<VisitorReservation> findAllVisitorReservations();
	List<Long> findToLeaveVisitorsIds();
	VisitorReservation findById2(Long reservationId);
	Visitor findVisitorByCardNo(String cardNo);
	void update2(VisitorReservation visitorReservationInDb);

	/** 手机端加载更多使用 **/
	List<VisitorReservation> selectVisitorReservations(VisitorReservation condition);

	VisitorReservation countVisitorReservations(VisitorReservation condition);
}
