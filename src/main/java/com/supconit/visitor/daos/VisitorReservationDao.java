package com.supconit.visitor.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.visitor.entities.VisitorReservation;

import hc.base.domains.Pageable;

public interface VisitorReservationDao extends BaseDao<VisitorReservation, Long>{
    /**
     * 
     *@方法描述： 通过实体和分页查询
     *@方法名：findByPage
     *@参数：@param reservationVisitor
     *@参数：@param pageNo
     *@参数：@param pageSize
     *@参数：@return
     *@返回：Pager<ReservationVisitor>
     *@exception 
     *@since
     */
	Pageable<VisitorReservation> findByPages(Pageable<VisitorReservation> pager,VisitorReservation condition);
    /**
     * 
     *@方法描述：通过实体条件查询
     *@方法名：findByConditions
     *@参数：@param reservationVisitor
     *@参数：@return
     *@返回：List<ReservationVisitor>
     *@exception 
     *@since
     */
	List<VisitorReservation> findByConditions(VisitorReservation reservationVisitor);
	/**
	 * 
	 *@方法描述：通过ID查询
	 *@方法名：findById
	 *@参数：@param id
	 *@参数：@return
	 *@返回：ReservationVisitor
	 *@exception 
	 *@since
	 */
	VisitorReservation findById(Long id);
	
	List<Long> findDeptChildIds(Long id);
	List<Long> findToLeaveVisitorsIds();
	VisitorReservation findById2(Long reservationId);

	/** 手机端加载更多使用 **/
	List<VisitorReservation> selectVisitorReservations(VisitorReservation condition);

	VisitorReservation countVisitorReservations(VisitorReservation condition);
}
