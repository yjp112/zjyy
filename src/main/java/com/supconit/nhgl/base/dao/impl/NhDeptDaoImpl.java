package com.supconit.nhgl.base.dao.impl;

import hc.base.domains.Pagination;
import hc.orm.AbstractBasicDaoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.base.dao.NhDeptDao;
import com.supconit.nhgl.base.entities.NhDept;
@Repository
public class NhDeptDaoImpl extends AbstractBasicDaoImpl<NhDept,Long> implements NhDeptDao{

	private static final String NAME_SPACE=NhDept.class.getName();
	@Override
	public List<NhDept> findById(Long id) {
		return selectList("findById",id);
	}

	@Override
	public List<NhDept> findAll() {
		return selectList("findAll");
	}

	@Override
	public List<NhDept> findAllChildren(Long id) {
		return selectList("findAllChildren",id);
	}

	@Override
	public List<Long> findAllChildrenIds(Long id) {
		return selectList("findAllChildrenIds",id);
	}

	@Override
	protected String getNamespace() {
		return NAME_SPACE;
	}

	@Override
	public List<NhDept> findByNoIds(List<Long> ids) {
		Map map=new HashMap();
		map.put("ids", ids);
		return selectList("findByNoIds",map);
	}
	
	@Override
	public List<NhDept> findByCon(NhDept dept) {
		return selectList("findByCon", dept);
	}
	
	@Override
	public NhDept findDeptTypeByPid(NhDept dept) {
		return selectOne("findDeptTypeByPid", dept);
	}

	@Override
	public List<NhDept> getTreeById(Integer deptId) {
		return selectList("getTreeById", deptId);
	}

	@Override
	public List<NhDept> getDeptArea(NhDept deptEMonth) {
		return selectList("getDeptArea", deptEMonth);
	}

	@Override
	public void findByCondition(Pagination<NhDept> pager, NhDept condition) {
		findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public Long findRootId() {
		return selectOne("findRootId");
	}

	@Override
	public List<NhDept> findByConfig(Long nhType) {
		// TODO Auto-generated method stub
		return selectList("findByConfig",nhType);
	}

}
