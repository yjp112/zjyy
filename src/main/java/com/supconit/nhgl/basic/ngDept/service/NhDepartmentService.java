package com.supconit.nhgl.basic.ngDept.service;

import com.supconit.nhgl.basic.ngDept.entities.NhDepartment;

import hc.base.services.EntityBaiscOperator;
import hc.base.domains.Pageable;
import hc.base.domains.Suggestion;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface NhDepartmentService extends
		EntityBaiscOperator<NhDepartment> {
	public static final String EXTEND_MODEL_DEPARTMENT_CODE = "PLAT_ORG_EX_NHDEPARTMENT";

	public abstract <X extends NhDepartment> X getById(long paramLong);

	public abstract <X extends NhDepartment> X getByCode(String paramString);

	public abstract <X extends NhDepartment> Pageable<X> find(
			Pageable<X> paramPageable, X paramX);

	public abstract <X extends NhDepartment> Pageable<X> find(
			Pageable<X> paramPageable, X paramX, boolean paramBoolean);

	public abstract <X extends NhDepartment> List<X> findByUserId(long paramLong);

	public abstract <X extends NhDepartment> Map<Long, List<X>> findMapByPersonIds(
			Set<Long> paramSet);

	public abstract <X extends NhDepartment> List<X> findByPersonId(
			long paramLong);

	public abstract <X extends NhDepartment> List<X> findAllWithoutVitualRoot();

	public abstract <X extends NhDepartment> List<X> findByPid(long paramLong);

	public abstract long countByPid(long paramLong);

	public abstract <X extends NhDepartment> X getTree(Long paramLong);

	public abstract <X extends NhDepartment> X getTree();

	public abstract <X extends NhDepartment> X getTreeIncludePosition();

	public abstract <X extends NhDepartment> void save(X paramX);

	@Deprecated
	public abstract void deleteById(Long paramLong);

	public abstract <X extends NhDepartment> List<X> findByIds(
			Set<Long> paramSet);

	public abstract Suggestion suggest(String paramString);

	public abstract List<NhDepartment> findAllDDeptWithoutVitualRoot();

	public abstract List<NhDepartment> findAllSDeptWithoutVitualRoot();

	public abstract <X extends NhDepartment> List<X> findAll();
}
