
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DutyGroupDao;
import com.supconit.base.entities.DutyGroup;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;


@Repository
public class DutyGroupDaoImpl extends AbstractBaseDao<DutyGroup, Long> implements DutyGroupDao {

    private static final String	NAMESPACE	= DutyGroup.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<DutyGroup> findByCondition(Pagination<DutyGroup> pager, DutyGroup condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return delete("deleteByIds", map);
	}
    
	@Override
	public List<DutyGroup> findTree() {
		// TODO Auto-generated method stub
		return selectList("findAll");
	}

	@Override
	public List<DutyGroup> findSubByRoot(Long groupId) {
		return selectList("findSubByRoot",groupId);
	}

	@Override
	public List<DutyGroup> findSubById(Long groupId) {
		return selectList("findSubById",groupId);
	}

	@Override
	public List<DutyGroup> findSubNoRecursion(Long parentId) {
		return selectList("findSubNoRecursion",parentId);
	}
	
	@Override
	public Long countRecordByCode(String code) {
    	DutyGroup condition=new DutyGroup();
        condition.setGroupCode(code);
        return getSqlSession().selectOne(DutyGroup.class.getName()+".countByCondition", condition);
	}

	@Override
	public List<DutyGroup> findByCode(String groupCode) {
		return selectList("findByCode",groupCode);
	}
	
	@Override
	public List<DutyGroup> findAll() {
		return selectList("findAll");
	}

    @Override
    public List<DutyGroup> findByGroup() {
        return selectList("findByGroudIds");
    }
}