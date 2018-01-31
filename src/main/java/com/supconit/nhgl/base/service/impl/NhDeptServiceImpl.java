package com.supconit.nhgl.base.service.impl;

import hc.base.domains.Pagination;
import hc.orm.AbstractBasicOrmService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.base.dao.NhDeptDao;
import com.supconit.nhgl.base.entities.NhDept;
import com.supconit.nhgl.base.service.NhDeptService;
@Service
public class NhDeptServiceImpl extends AbstractBasicOrmService<NhDept,Long> implements NhDeptService{

	@Autowired
	private NhDeptDao nhDeptDao;
	@Override
	public NhDept getById(Long arg0) {
		return nhDeptDao.getById(arg0);
	}

	@Override
	public void delete(NhDept arg0) {
		nhDeptDao.delete(arg0);
	}

	@Override
	public void insert(NhDept arg0) {
		nhDeptDao.insert(arg0);
	}

	@Override
	public void update(NhDept arg0) {
		nhDeptDao.update(arg0);
	}

	@Override
	public List<NhDept> findById(Long id) {
		return nhDeptDao.findById(id);
	}

	@Override
	public List<NhDept> findAll() {
		return nhDeptDao.findAll();
	}

	@Override
	public List<NhDept> findAllChildren(Long id) {
		return nhDeptDao.findAllChildren(id);
	}

	@Override
	public List<Long> findAllChildrenIds(Long id) {
		return nhDeptDao.findAllChildrenIds(id);
	}

	@Override
	public List<NhDept> findByNoIds(List<Long> ids) {
		return nhDeptDao.findByNoIds(ids);
	}
	@Override
	public List<NhDept> findByCon(NhDept dept) {
		return nhDeptDao.findByCon(dept);
	}

	@Override
	public NhDept findDeptTypeByPid(NhDept dept) {
		return nhDeptDao.findDeptTypeByPid(dept);
	}

	@Override
	public List<NhDept> getTreeById(Integer deptId) {
		return nhDeptDao.getTreeById(deptId);
	}

	@Override
	public List<NhDept> getDeptArea(NhDept deptEMonth) {
		return nhDeptDao.getDeptArea(deptEMonth);
	}

	@Override
	public void findByCondition(Pagination<NhDept> pager, NhDept condition) {
		nhDeptDao.findByCondition(pager,condition);
	}

	@Override
	public Long findRootId() {
		return nhDeptDao.findRootId();
	}

	@Override
	public List<NhDept> findByConfig(Long nhType) {
		// TODO Auto-generated method stub
		return nhDeptDao.findByConfig(nhType);
	}

}
