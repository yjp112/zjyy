
package com.supconit.base.services;

import java.util.List;
import java.util.Map;

import com.supconit.base.entities.DutyGroup;
import com.supconit.base.entities.DutyGroupPerson;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;


public interface DutyGroupPersonService extends BaseBusinessService<DutyGroupPerson, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @param treeId 
	 * @return
	 */
	Pageable<DutyGroupPerson> findByCondition(Pagination<DutyGroupPerson> pager, DutyGroupPerson condition, String treeId);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param dutyGroupPerson  object instance 
	 * @return
	 */
	void save(DutyGroupPerson dutyGroupPerson);



	List<DutyGroupPerson> findTree(List<DutyGroup> dutyGroup_tree);



	List<DutyGroupPerson> findGroupPersons(Long groupId);

    DutyGroupPerson findGroupByPerson(Long personId);

    public List<DutyGroupPerson> findAllPerson();
    /**
     * 通过班组id和岗位查找对应人员code
     */
    List<String> findPersonCodeByGroupIdAndPostId(Map<String,Object> param);
    /**
     * 查询该人员是否已存在该班组
     */
    boolean countExistByCondition(DutyGroupPerson condition);
}

