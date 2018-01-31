
package com.supconit.base.daos;

import java.util.List;
import java.util.Map;

import com.supconit.base.entities.DutyGroupPerson;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

public interface DutyGroupPersonDao extends BaseDao<DutyGroupPerson, Long>{
	
	Pageable<DutyGroupPerson> findByCondition(Pagination<DutyGroupPerson> pager, DutyGroupPerson condition);

	int deleteByIds(Long[] ids);
    
    public  DutyGroupPerson findById(Long id);

	public List<DutyGroupPerson> findTree(List<Long> groupIds);

	public List<DutyGroupPerson> findGroupPersons(Long groupId);
    public DutyGroupPerson findGroupByPerson(Long personId);

    public List<DutyGroupPerson> findAllPerson();
    /**
     * 通过班组id和岗位查找对应人员code
     */
    List<String> findPersonCodeByGroupIdAndPostId(Map<String,Object> param);
    /**
     * 查询该人员是否已存在该班组
     */
    Long countExistByCondition(DutyGroupPerson condition);

    /** 手机端使用 **/
    List<DutyGroupPerson> searchDutyGroupPersons(DutyGroupPerson dutyGroupPerson);
}
