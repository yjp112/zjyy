
package com.supconit.base.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DutyGroupDao;
import com.supconit.base.daos.DutyGroupPersonDao;
import com.supconit.base.entities.DutyGroup;
import com.supconit.base.entities.DutyGroupPerson;
import com.supconit.base.services.DutyGroupPersonService;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import jodd.util.StringUtil;

@Service
public class DutyGroupPersonServiceImpl extends AbstractBaseBusinessService<DutyGroupPerson, Long> implements DutyGroupPersonService{

	@Autowired
	private DutyGroupPersonDao		dutyGroupPersonDao;	
	@Autowired
	private DutyGroupDao		dutyGroupDao;	
//	@Autowired
//	private DutySchedulingDetailDao dutySchedulingDetailDao;

    /**
	 * Get dutyGroupPerson by  ID
	 * @param id  dutyGroupPerson id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public DutyGroupPerson getById(Long id) {
		if (null == id || id <= 0)
			return null;
		DutyGroupPerson dutyGroupPerson = dutyGroupPersonDao.getById(id);
		
		return dutyGroupPerson;
	}	

    /**
	 * delete DutyGroupPerson by ID 
	 * @param id  dutyGroupPerson  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
//		if(!isAllowDelete(id))
//            throw new BusinessDoneException("该组员有值班任务，不能删除");
		
		dutyGroupPersonDao.deleteById(id);
	}


    /**
	 * delete DutyGroupPerson by ID array
	 * @param ids  dutyGroupPerson ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
/*		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new BusinessDoneException("该组员有值班任务，不能删除");
        }  */
        
        dutyGroupPersonDao.deleteByIds(ids);
	}

    /**
	 * Find DutyGroupPerson list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<DutyGroupPerson> findByCondition(Pagination<DutyGroupPerson> pager, DutyGroupPerson condition, String treeId) {
		/*
		 * if(StringUtil.isBlank(treeId)||treeId.equals("0")){//如果没有点树上节点,即相当于点了根节点 return dutyGroupPersonDao.findByCondition(pageNo, pageSize, condition); }else{
		 * if(treeId.length()>4 && treeId.substring(0,4).equals("leef")){//如果点的是叶子节点 String strId = treeId.substring(4,treeId.length()); Long id = Long.parseLong(strId);
		 * condition.setId(id); return dutyGroupPersonDao.findByCondition(pageNo, pageSize, condition); }else{//如果点的是树枝节点 Long groupId = Long.parseLong(treeId);
		 * condition.setGroupId(groupId); return dutyGroupPersonDao.findByCondition(pageNo, pageSize, condition); }
		 */
		if (treeId.length() > 4 && treeId.substring(0, 4).equals("leef")) {// 如果点的是叶子节点
			String strId = treeId.substring(4, treeId.length());
			Long id = Long.parseLong(strId);
			condition.setId(id);
			return dutyGroupPersonDao.findByCondition(pager, condition);
		} else {
			Long groupId = 0L;
			if (StringUtil.isNotBlank(treeId)) {
				groupId = Long.parseLong(treeId);
			}
			// 查出每个节点对应的子节点（包括自己）
			List<DutyGroup> listDutyGroups = new ArrayList<DutyGroup>();
			if (groupId == 0L || groupId.equals(0L)) {// 如果是根节点
				listDutyGroups = dutyGroupDao.findSubByRoot(groupId);
			} else {
				listDutyGroups = dutyGroupDao.findSubById(groupId);
			}
			List<Long> subDutyGroups = new ArrayList<Long>();
			for (int i = 0; i < listDutyGroups.size(); i++) {
				subDutyGroups.add(listDutyGroups.get(i).getId());
			}
			if (subDutyGroups != null && subDutyGroups.size() > 0) {
				condition.setSearchDutyGroupList(subDutyGroups);
			}
			return dutyGroupPersonDao.findByCondition(pager, condition);
		}
	}

	    
    
    /**
	 * update DutyGroupPerson
	 * @param dutyGroupPerson  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(DutyGroupPerson dutyGroupPerson) {
		 if(!isAllowSave(dutyGroupPerson))
            throw new BusinessDoneException("请不要选择班组类别作为班组。");
            
		dutyGroupPersonDao.update(dutyGroupPerson);
	}
    
    /**
	 * insert DutyGroupPerson
	 * @param dutyGroupPerson  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(DutyGroupPerson dutyGroupPerson) {
        
         if(!isAllowSave(dutyGroupPerson))
            throw new BusinessDoneException("请不要选择班组类别作为班组。");
            
		dutyGroupPersonDao.insert(dutyGroupPerson);
	}
    
     //Check that allows you to delete 
/*    @SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean isAllowDelete(Long id)
    {
    	DutyGroupPerson p = dutyGroupPersonDao.getById(id);
    	Map map = new HashMap();
		map.put("beginTime", new Date());
		map.put("groupPersonId", p.getPersonId().toString());
    	List<DutySchedulingDetail> list = dutySchedulingDetailDao.checkGroupPersonDelete(map);
    	if(null!=list && list.size()>0){
    		return false;
    	}
        return true;
    }*/
    //Check that allows you to save
    private boolean isAllowSave(DutyGroupPerson dutyGroupPerson){
    	//GroupId不能为空；根节点不允许成为groupId
    	if(null==dutyGroupPerson.getGroupId()||dutyGroupPerson.getGroupId().equals(0L)){
    		return false;
    	}
    	//二级节点不允许成为班组（锅炉组、新风机组...）
    	DutyGroup dg = dutyGroupDao.getById(dutyGroupPerson.getGroupId());
    	if(null==dg.getParentId()||dg.getParentId().equals(0L)){
    		return false;
    	}
        return true;
    }

	@Override
	public List<DutyGroupPerson> findTree(List<DutyGroup> dutyGroup_tree) {
		List<Long> groupIds = new ArrayList<Long>();
		for(DutyGroup dg:dutyGroup_tree){
			groupIds.add(dg.getId());
		}
		return dutyGroupPersonDao.findTree(groupIds);
	}

	@Override
	public List<DutyGroupPerson> findGroupPersons(Long groupId) {
		// TODO Auto-generated method stub
		return dutyGroupPersonDao.findGroupPersons(groupId);
	}

    @Override
    public DutyGroupPerson findGroupByPerson(Long personId) {
        return dutyGroupPersonDao.findGroupByPerson(personId);
    }

    @Override
    public List<DutyGroupPerson> findAllPerson() {
        return dutyGroupPersonDao.findAllPerson();
    }

	@Override
	public List<String> findPersonCodeByGroupIdAndPostId(
			Map<String, Object> param) {
		return dutyGroupPersonDao.findPersonCodeByGroupIdAndPostId(param);
	}

	@Override
	public boolean countExistByCondition(DutyGroupPerson condition) {
		return dutyGroupPersonDao.countExistByCondition(condition)>0? true:false;
	}

}
