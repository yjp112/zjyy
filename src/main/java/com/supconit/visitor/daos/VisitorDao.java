package com.supconit.visitor.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.visitor.entities.Visitor;

import hc.base.domains.Pageable;


public interface VisitorDao extends BaseDao<Visitor, Long>{
	/**
	 * 
	 *@方法描述：通过实体和分页条件查询
	 *@方法名：findByPage
	 *@参数：@param visitor
	 *@参数：@param pageNo
	 *@参数：@param pageSize
	 *@参数：@return
	 *@返回：Pager<Visitor>
	 *@exception 
	 *@since
	 */
	Pageable<Visitor> findByPages(Pageable<Visitor> pager,Visitor condition);
	/**
	 * 
	 *@方法描述：通过访客ID删除访客记录
	 *@方法名：deleteByVisitorId
	 *@参数：@param visitorId
	 *@返回：void
	 *@exception 
	 *@since
	 */
	void deleteByVisitorId(Long visitorId);
	
    Visitor findByReservationId(Long reservationId);
    List<Visitor> autoCompletePersonList(String visitorName);
    /**
     * 根据卡号查询未离开的访客的人员信息
     * @param cardNo
     */
    List<Visitor> findByCardNo(String cardNo);
}
