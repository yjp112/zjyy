package com.supconit.visitor.daos.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.visitor.daos.VisitorReservationDao;
import com.supconit.visitor.entities.VisitorReservation;

import hc.base.domains.Pageable;

@Repository
public class VisitorReservationDaoImpl extends AbstractBaseDao<VisitorReservation, Long> implements VisitorReservationDao {

	private static final String	NAMESPACE = VisitorReservation.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public List<VisitorReservation> findByConditions(VisitorReservation reservationVisitor) {
		return selectList("findByConditions", reservationVisitor);
	}
    @Override
    public Pageable<VisitorReservation> findByPages(Pageable<VisitorReservation> pager,VisitorReservation condition) {
        return findByPager(pager, "findByConditions", "countByConditions", condition);
    }
    
    @Override
    public VisitorReservation findById(Long id) {
        return selectOne("findById", id);
    }
    @Override
    public List<Long> findDeptChildIds(Long id) {
        List<Long> childIds = null;
        if(id!=null){//递归查询子节点
            childIds=getSqlSession().selectList(NAMESPACE+".deptChildIds",id);
          } 
        return  childIds;
    }
	@Override
	public List<Long> findToLeaveVisitorsIds() {
		return selectList("findToLeaveVisitorsIds");
	}
	@Override
	public VisitorReservation findById2(Long reservationId) {
		return selectOne("findById2", reservationId);
	}

	/** 手机端加载更多使用 **/
	@Override
	public List<VisitorReservation> selectVisitorReservations(VisitorReservation condition) {
		return selectList("selectVisitorReservations",condition);
	}

	@Override
	public VisitorReservation countVisitorReservations(VisitorReservation condition) {
		return selectOne("countVisitorReservations",condition);
	}
}
