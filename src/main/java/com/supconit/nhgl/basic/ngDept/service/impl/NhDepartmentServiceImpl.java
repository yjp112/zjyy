package com.supconit.nhgl.basic.ngDept.service.impl;

import com.alibaba.druid.sql.PagerUtils;

import com.supconit.nhgl.basic.areaConfig.entities.AreaConfig;
import com.supconit.nhgl.basic.deptConfig.dao.DeptConfigDao;
import com.supconit.nhgl.basic.deptConfig.entities.DeptConfig;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;
import com.supconit.nhgl.basic.ngDept.entities.NhDepartment;
import com.supconit.nhgl.basic.ngDept.service.NhDepartmentService;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.entities.Position;
import com.supconit.honeycomb.business.organization.services.PositionService;
import com.supconit.honeycomb.cache.Cache;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.base.domains.Suggestion;
import hc.base.exceptions.TreeException;
import hc.jdbc.JdbcProcessor;
import hc.modelextend.ExtendedModelProvider;
import hc.orm.search.Term;
import hc.orm.triggers.AbstractBusinessTriggerSupport;
import hc.orm.triggers.BusinessTriggerExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import jodd.util.StringUtil;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class NhDepartmentServiceImpl extends AbstractBusinessTriggerSupport<NhDepartment>
		implements NhDepartmentService {
	private static final String SELECT_PRE_SQL = "SELECT * FROM " + NhDepartment.TABLE_NAME + " D ";
	private static final String CACHE_NHDEPARTMENT_BY_ID = "NHDepartment_BY_ID";
	private static final String CACHE_NHDEPARTMENT_ID_BY_CODE = "NHDepartment_ID_BY_CODE";
	private static final int MAX_SUGGESTION = 20;
	@Autowired
	private ExtendedModelProvider extendedModelProvider;
	@Autowired(required = false)
	private BusinessTriggerExecutor businessTriggerExecutor;
	@Autowired
	private JdbcProcessor jdbcProcessor;
	private String defaultOrderBy = "ORDER BY D.ID DESC";
	@Autowired
	private Cache cache;
	@Autowired
	private PositionService positionService;
	@Autowired
	private DeptConfigDao deptConfigDao;

	public void setDefaultOrderBy(String defaultOrderBy) {
		this.defaultOrderBy = defaultOrderBy;
	}

	@Transactional(readOnly = true)
	public <X extends NhDepartment> X getById(long id) {
		String key = String.valueOf(id);
		X nhDepartment = this.cache.get(CACHE_NHDEPARTMENT_BY_ID, key);
		if (null == nhDepartment) {
			nhDepartment = this.extendedModelProvider.get(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class, id);
			if (null != nhDepartment)
				this.cache.put(CACHE_NHDEPARTMENT_BY_ID, key, nhDepartment);
		}
		return nhDepartment;
	}

	@Transactional(readOnly = true)
	public <X extends NhDepartment> X getByCode(String code) {
		Long id = (Long) this.cache.get(CACHE_NHDEPARTMENT_ID_BY_CODE, code);
		if (null == id) {
			X NhDepartment = this.extendedModelProvider.get(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class,
					SELECT_PRE_SQL + " WHERE D.CODE = ?", new Object[] { code });
			if (null != NhDepartment) {
				this.cache.put(CACHE_NHDEPARTMENT_ID_BY_CODE, code, NhDepartment.getId());
			}
			return NhDepartment;
		}
		return getById(id.longValue());
	}

	private <X extends NhDepartment> void evictCache(X NhDepartment) {
		if (null != NhDepartment.getId()) {
			this.cache.evict(CACHE_NHDEPARTMENT_BY_ID, String.valueOf(NhDepartment.getId()));
		}
		if (StringUtil.isNotBlank(NhDepartment.getCode())) {
			this.cache.evict(CACHE_NHDEPARTMENT_ID_BY_CODE, NhDepartment.getCode());
		}
	}

	@Transactional(readOnly = true)
	public <X extends NhDepartment> List<X> findByUserId(long userId) {
		return this.extendedModelProvider.find(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class,
				SELECT_PRE_SQL
						+ ",HO_Department_PERSON DP,HO_USER U WHERE D.ID = DP.Department_ID AND DP.PERSON_ID = U.PERSON_ID AND U.ID = ? "
						+ this.defaultOrderBy,
				new Object[] { Long.valueOf(userId) });
	}

	@Transactional(readOnly = true)
	public <X extends NhDepartment> List<X> findByPersonId(long personId) {
		return this.extendedModelProvider.find(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class,
				SELECT_PRE_SQL + "  WHERE D.ID IN (SELECT Department_ID FROM NH_DEPT WHERE PERSON_ID = ?)",
				new Object[] { Long.valueOf(personId) });
	}

	public <X extends NhDepartment> Map<Long, List<X>> findMapByPersonIds(Set<Long> personIds) {
		if (CollectionUtils.isEmpty(personIds)) {
			return Collections.emptyMap();
		}
		Map<Long, List<X>> map = new HashMap();
		for (Long personId : personIds) {
			if (null != personId) {
				List<X> NhDepartments = findByPersonId(personId.longValue());
				if (!CollectionUtils.isEmpty(NhDepartments)) {
					map.put(personId, NhDepartments);
				}
			}
		}
		return map;
	}

	@Transactional(readOnly = true)
	public <X extends NhDepartment> List<X> findAllWithoutVitualRoot() {
		return this.extendedModelProvider.find(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class,
				SELECT_PRE_SQL + "  WHERE D.PID > 0 " + this.defaultOrderBy, new Object[0]);
	}

	@Transactional(readOnly = true)
	public <X extends NhDepartment> X getTree(Long id) {
		if ((null == id) || (id.longValue() < 1L)) {
			return null;
		}
		X root = getById(id.longValue());
		if (null == root) {
			return null;
		}
		if ((null == root.getLft()) || (null == root.getRgt())) {
			throw new TreeException("root has no lft or rgt.");
		}
		return buildTree(root);
	}

	@Transactional(readOnly = true)
	public <X extends NhDepartment> X getTree() {
		List<X> roots = findByPid(0L);
		if ((CollectionUtils.isEmpty(roots)) || (roots.size() > 1)) {
			throw new TreeException("has no root or more the one root.");
		}
		X root = roots.get(0);
		if ((null == root.getLft()) || (null == root.getRgt())) {
			throw new TreeException("root has no lft or rgt.");
		}
		return buildTree(root);
	}

	private <X extends NhDepartment> X buildTree(X root) {
		List<X> list = this.extendedModelProvider.find(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class,
				SELECT_PRE_SQL + "  WHERE D.LFT BETWEEN ? AND ? ORDER BY D.LFT",
				new Object[] { root.getLft(), root.getRgt() });

		Stack<X> rightStack = new Stack();
		for (X node : list) {
			if (!rightStack.isEmpty()) {
				while (((NhDepartment) rightStack.lastElement()).getRgt().longValue() < node.getRgt().longValue()) {
					rightStack.pop();
				}
			}
			if (!rightStack.isEmpty()) {
				((NhDepartment) rightStack.lastElement()).addChild(node);
			}
			rightStack.push(node);
		}
		return rightStack.firstElement();
	}

	private void rebuildLftAndRgt() {
		List<Long> ids = findIdsByPid(0L);
		if (!CollectionUtils.isEmpty(ids)) {
			for (Long id : ids) {
				rebuildTree(id, Long.valueOf(1L));
			}
		}
	}

	private List<Long> findIdsByPid(long pid) {
		return this.jdbcProcessor.find(Long.class,
				"SELECT D.ID AS ID FROM " + NhDepartment.TABLE_NAME + " D WHERE D.PID = ?",
				new Object[] { Long.valueOf(pid) });
	}

	private Long rebuildTree(Long pid, Long lft) {
		Long rgt = Long.valueOf(lft.longValue() + 1L);
		List<Long> ids = findIdsByPid(pid.longValue());
		if (!CollectionUtils.isEmpty(ids)) {
			for (Long id : ids) {
				rgt = rebuildTree(id, rgt);
			}
		}
		this.jdbcProcessor.update("UPDATE NH_DEPT SET LFT = ?, RGT = ? WHERE ID = ?", new Object[] { lft, rgt, pid });
		evictCache(new NhDepartment(pid));
		return Long.valueOf(rgt.longValue() + 1L);
	}

	@Transactional(readOnly = true)
	public <X extends NhDepartment> List<X> findByPid(long pid) {
		return this.extendedModelProvider.find(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class,
				SELECT_PRE_SQL + "  WHERE D.PID = ?", new Object[] { Long.valueOf(pid) });
	}

	@Transactional(readOnly = true)
	public long countByPid(long pid) {
		return ((Long) this.jdbcProcessor.get(Long.class,
				"SELECT COUNT(*) FROM " + NhDepartment.TABLE_NAME + " D WHERE D.PID = ?",
				new Object[] { Long.valueOf(pid) })).longValue();
	}

	@Transactional
	public <X extends NhDepartment> void save(X NhDepartment) {
		Assert.notNull(NhDepartment);
		if (null == NhDepartment.getId()) {
			insert(NhDepartment);
			// 同时向区域能耗设置中插入4条信息
			insertConfig(NhDepartment);
		} else {
			update(NhDepartment);
		}
		rebuildLftAndRgt();

	}

	@Transactional
	private void insertConfig(NhDepartment dept) {
		// 向能耗区域配置中插入4条数据，分别代表电，水，蒸汽，能量
		for (int i = 1; i <= 4; i++) {
			DeptConfig deptConfig = new DeptConfig();
			deptConfig.setDeptId(dept.getId());
			deptConfig.setIsSum(0);
			deptConfig.setNhType(i);
			// 判断在能耗区域中是否已经存在该配置
			if (isNotExistsConfig(deptConfig)) {
				deptConfigDao.insert(deptConfig);
			}
		}
	}

	private boolean isNotExistsConfig(DeptConfig deptConfig) {
		int config = deptConfigDao.countFindAll(deptConfig);
		if (config == 0) {
			return true;
		}
		return false;
	}

	@Deprecated
	@Transactional
	public void deleteById(Long id) {
		NhDepartment NhDepartment = new NhDepartment();
		NhDepartment.setId(id);
		delete(NhDepartment);
	}

	protected Class<?> getDefaultEntityClass() {
		return NhDepartment.class;
	}

	protected void doInsert(NhDepartment entity) {
		this.extendedModelProvider.insert(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class, entity);

		evictCache(entity);
	}

	protected void doUpdate(NhDepartment entity) {
		this.extendedModelProvider.update(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class, entity);

		evictCache(entity);
	}

	protected void doDelete(NhDepartment entity) {
		this.extendedModelProvider.deleteById(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class, entity.getId());

		evictCache(entity);
	}

	@Transactional(readOnly = true)
	public <X extends NhDepartment> Pageable<X> find(Pageable<X> pager, X condition) {
		Assert.notNull(pager);
		return this.extendedModelProvider.find(pager, EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class, condition);
	}

	@Transactional(readOnly = true)
	public Suggestion suggest(String query) {
		if (StringUtil.isBlank(query)) {
			return new Suggestion(query);
		}
		return new Suggestion(query,
				this.extendedModelProvider.find(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class,
						PagerUtils.limit("SELECT ID,NAME FROM NH_DEPT WHERE SUGGEST_CODE LIKE ?  ",
								this.extendedModelProvider.getDbType(), 0, MAX_SUGGESTION),
				new Object[] { query + "%" }));
	}

	@Transactional(readOnly = true)
	public <X extends NhDepartment> Pageable<X> find(Pageable<X> pager, X condition, boolean includeSub) {
		if ((!includeSub) || (null == condition) || (null == condition.getPid())) {
			return find(pager, condition);
		}
		NhDepartment parent = getById(condition.getPid().longValue());
		if (null == parent) {
			return Pagination.empty();
		}
		condition.setPid(null);
		List<Object> params = new ArrayList<Object>();
		StringBuilder builder = new StringBuilder(SELECT_PRE_SQL + " WHERE D.LFT >= ? and D.RGT <= ? ");
		params.add(parent.getLft());
		params.add(parent.getRgt());
		Term term = condition.getTerm("D");
		if ((null != term) && (StringUtil.isNotBlank(term.getSql()))) {
			builder.append(" AND ").append(term.getSql());
			if ((null != term.getParams()) && (term.getParams().length != 0)) {
				for (Object p : term.getParams()) {
					params.add(p);
				}
			}
		}
		String orderBySQL = this.extendedModelProvider.buildOrderBySQL(condition);
		if (StringUtil.isBlank(orderBySQL)) {
			orderBySQL = " ORDER BY ID DESC";
		}
		builder.append(orderBySQL);
		return this.extendedModelProvider.find(pager, EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class,
				builder.toString(), params.toArray());
	}

	public <X extends NhDepartment> List<X> findByIds(Set<Long> set) {
		if (CollectionUtils.isEmpty(set)) {
			return Collections.emptyList();
		}
		StringBuilder builder = new StringBuilder(SELECT_PRE_SQL).append(" WHERE ID IN (");
		for (Long id : set) {
			builder.append(id).append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(")");
		return this.extendedModelProvider.find(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class, builder.toString(),
				new Object[0]);
	}

	public <X extends NhDepartment> X getTreeIncludePosition() {
		X NhDepartmentTree = getTree();
		if (null != NhDepartmentTree) {
			Pagination<Position> positions = new Pagination(1, 2147483647);
			this.positionService.find(positions, null);
			if (!CollectionUtils.isEmpty(positions)) {
				Map<Long, List<Position>> positionMap = new HashMap();
				for (Position p : positions) {
					if (null != p.getDepartmentId()) {
						List<Position> list = (List) positionMap.get(p.getDepartmentId());
						if (null == list) {
							list = new ArrayList();
							positionMap.put(p.getDepartmentId(), list);
						}
						list.add(p);
					}
				}
				resNhDepartmentTree(positionMap, NhDepartmentTree);
			}
		}
		return NhDepartmentTree;
	}

	private void resNhDepartmentTree(Map<Long, List<Position>> positionMap, NhDepartment NhDepartment) {
		if (null == NhDepartment) {
			return;
		}
		boolean hasSubDept = !CollectionUtils.isEmpty(NhDepartment.getChildren());
		List<Position> positions = (List) positionMap.get(NhDepartment.getId());
		if (!CollectionUtils.isEmpty(positions)) {
			List<NhDepartment> children;
			if (!hasSubDept) {
				children = new ArrayList();
				NhDepartment.setChildren(children);
			} else {
				children = NhDepartment.getChildren();
			}
			for (int i = 0; i < positions.size(); i++) {
				NhDepartment positionChild = new NhDepartment(((Position) positions.get(i)).getId());
				positionChild.setName(((Position) positions.get(i)).getName());
				positionChild.setCode("_POST_" + positions.get(i));
				children.add(i, positionChild);
			}
		}
		if (hasSubDept) {
			for (NhDepartment d : NhDepartment.getChildren()) {
				if (!d.getCode().startsWith("_POST_")) {
					resNhDepartmentTree(positionMap, d);
				}
			}
		}
	}

	@Override
	public List<NhDepartment> findAllDDeptWithoutVitualRoot() {
		return this.extendedModelProvider.find(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class,
				"SELECT * FROM VIEW_NH_D_DEPT D   WHERE D.PID > 0 " +" ORDER BY D.LFT  ASC", new Object[0]);
	}

	@Override
	public List<NhDepartment> findAllSDeptWithoutVitualRoot() {
		return this.extendedModelProvider.find(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class,
				"SELECT * FROM VIEW_NH_S_DEPT D   WHERE D.PID > 0 " +" ORDER BY D.LFT ASC ", new Object[0]);
	}

	@Override
	public <X extends NhDepartment> List<X> findAll() {
		return this.extendedModelProvider.find(EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class,
				SELECT_PRE_SQL + " ORDER BY D.ID ", new Object[0]);
	}
}
