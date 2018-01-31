
package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DutyGroupPersonDao;
import com.supconit.base.entities.DutyGroupPerson;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;


@Repository
public class DutyGroupPersonDaoImpl extends AbstractBaseDao<DutyGroupPerson, Long> implements DutyGroupPersonDao {

    private static final String	NAMESPACE	= DutyGroupPerson.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<DutyGroupPerson> findByCondition(Pagination<DutyGroupPerson> pager, DutyGroupPerson condition) {
		return findByPager(pager, "findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return delete("deleteByIds", map);
	}
    
    @Override
	public DutyGroupPerson findById(Long id) {
		return selectOne("findById",id);
	}

	@Override
	public List<DutyGroupPerson> findTree(List<Long> groupIds) {
		return selectList("findByGroupIds",groupIds);
	}

	@Override
	public List<DutyGroupPerson> findGroupPersons(Long groupId) {
		return selectList("findGroupPersons",groupId);
	}

    @Override
    public DutyGroupPerson findGroupByPerson(Long personId) {
        return selectOne("findGroupByPerson",personId);
    }

    @Override
    public List<DutyGroupPerson> findAllPerson() {
        return selectList("findAllPersons");
    }

	@Override
	public List<String> findPersonCodeByGroupIdAndPostId(
			Map<String, Object> param) {
		return selectList("findPersonCodeByGroupIdAndPostId", param);
	}

	@Override
	public Long countExistByCondition(DutyGroupPerson condition) {
		return selectOne("countExistByCondition",condition);
	}

	/** 手机端使用 **/
	@Override
	public List<DutyGroupPerson> searchDutyGroupPersons(DutyGroupPerson dutyGroupPerson) {
		return selectList("searchDutyGroupPersons",dutyGroupPerson);
	}
}