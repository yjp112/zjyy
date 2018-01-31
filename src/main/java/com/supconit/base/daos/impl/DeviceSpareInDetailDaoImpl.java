package com.supconit.base.daos.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DeviceSpareInDetailDao;
import com.supconit.base.entities.DeviceSpareInDetail;
import com.supconit.common.daos.AbstractBaseDao;
@Repository
public class DeviceSpareInDetailDaoImpl extends AbstractBaseDao<DeviceSpareInDetail, Long> implements
	DeviceSpareInDetailDao{
	private static final String NAMESPACE=DeviceSpareInDetail.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public Pageable<DeviceSpareInDetail> findByCondition(Pagination<DeviceSpareInDetail> pager,
			DeviceSpareInDetail condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}
	@Override
	public DeviceSpareInDetail findById(Long id) {
		return selectOne("findById",id);
	}
	@Override
	public DeviceSpareInDetail findTopOne() {
		return selectOne("findTopOne");
	}
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
	@Override
	public List<DeviceSpareInDetail> findListBySpareId(Long spareId) {
		return selectList("findListBySpareId",spareId);
	}

}
