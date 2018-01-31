
package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.DutyGroup;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

public interface DutyGroupDao extends BaseDao<DutyGroup, Long>{
	
	Pageable<DutyGroup> findByCondition(Pagination<DutyGroup> pager, DutyGroup condition);

	int deleteByIds(Long[] ids);
    
	public List<DutyGroup> findTree();

	List<DutyGroup> findSubByRoot(Long groupId);//从根节点开始递归，不包括根节点本身

	List<DutyGroup> findSubById(Long groupId); //从非根节点开始递归，包括自身

	List<DutyGroup> findSubNoRecursion(Long parentId);//仅仅查找第一级子节点，不递归
	
	public Long countRecordByCode(String code);

	List<DutyGroup> findByCode(String groupCode);
	List<DutyGroup> findAll();

    List<DutyGroup> findByGroup();
	
}
