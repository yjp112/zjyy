
package com.supconit.base.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DutyGroupDao;
import com.supconit.base.daos.DutyGroupPersonDao;
import com.supconit.base.entities.DutyGroup;
import com.supconit.base.entities.DutyGroupPerson;
import com.supconit.base.services.DutyGroupService;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.ywgl.exceptions.BusinessDoneException;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Service
public class DutyGroupServiceImpl extends AbstractBaseBusinessService<DutyGroup, Long> implements DutyGroupService{

	@Autowired
	private DutyGroupDao		dutyGroupDao;	
	@Autowired
	private DutyGroupPersonDao		dutyGroupPersonDao;	

    /**
	 * Get dutyGroup by  ID
	 * @param id  dutyGroup id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public DutyGroup getById(Long id) {
		if (null == id || id <= 0)
			return null;
		DutyGroup dutyGroup = dutyGroupDao.getById(id);
		
		return dutyGroup;
	}	

    /**
	 * delete DutyGroup by ID 
	 * @param id  dutyGroup  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
		if(!isAllowDelete(id))
            throw new BusinessDoneException("该班组有组员，不能删除");
		
		dutyGroupDao.deleteById(id);
	}


    /**
	 * delete DutyGroup by ID array
	 * @param ids  dutyGroup ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new BusinessDoneException("该班组有组员，不能删除");
        }  
        
        dutyGroupDao.deleteByIds(ids);
	}

    /**
	 * Find DutyGroup list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<DutyGroup> findByCondition(Pagination<DutyGroup> pager, DutyGroup condition) {
		return dutyGroupDao.findByCondition(pager, condition);
	}

	    
    
    /**
	 * update DutyGroup
	 * @param dutyGroup  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(DutyGroup dutyGroup) {
    	checkToSave(dutyGroup);
		 if(dutyGroup.getParentId()==null) 
	        	dutyGroup.setParentId(0L);    
		dutyGroupDao.update(dutyGroup);
	}
    
    /**
	 * insert DutyGroup
	 * @param dutyGroup  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(DutyGroup dutyGroup) {
        
    	checkToSave(dutyGroup);
        if(dutyGroup.getParentId()==null) 
        	dutyGroup.setParentId(0L);
		dutyGroupDao.insert(dutyGroup);
	}
    
     //Check that allows you to delete 
    private boolean isAllowDelete(Long id)
    {	
    	//是否有组员
    	List<DutyGroupPerson> listPersons = dutyGroupPersonDao.findGroupPersons(id);
    	if(null!=listPersons && listPersons.size()>0){
    		return false;
    	}
    	//是否有子班组
    	List<DutyGroup> listGroups = dutyGroupDao.findSubByRoot(id);
    	if(null!=listGroups && listGroups.size()>0){
    		return false;
    	}
        return true;
    }
    //Check that allows you to save
    private boolean isAllowSave(DutyGroup dutyGroup)
    {
        return true;
    }
    
    private void checkToSave(DutyGroup entity){
        List<DutyGroup> list = new ArrayList<DutyGroup>();
		list = dutyGroupDao.findByCode(entity.getGroupCode());
		if (null != list && list.size() >= 1) {
			if (list.size() > 1) {
				throw new BusinessDoneException("编码[" + entity.getGroupCode()+ "]已经被占用。");
			} else {
				// list.size()==1
				if (entity.getId() != null) {
					// update
					DutyGroup old = list.get(0);
					if (entity.getId().longValue() == old.getId().longValue()) {
						// ok
					} else {
						throw new BusinessDoneException("编码["+ entity.getGroupCode() + "]已经被占用。");
					}
				} else {
					// insert
					throw new BusinessDoneException("编码["+ entity.getGroupCode() + "]已经被占用。");
				}

			}
		}
        //最多只能有三层（包括根节点）
        if(entity.getParentId()!=0){
        	DutyGroup father = dutyGroupDao.getById(entity.getParentId());
        	if(father.getParentId()!=0){
        		throw new BusinessDoneException("最多只能有三层，不能再为["+father.getGroupName()+"]增加子班组。");  
        	}
        }
    }

	@Override
	public List<DutyGroup> findTree() {
		return dutyGroupDao.findTree();
	}

	@Override
	public List<DutyGroup> findGroupCategory(Long parentId) {
		return dutyGroupDao.findSubNoRecursion(parentId);//仅仅查找第一级子节点
	}

	@Override
	public List<DutyGroup> findSubById(Long id) {
		return dutyGroupDao.findSubById(id);
	}

    @Override
    public List<DutyGroup> findByGroup() {
        return dutyGroupDao.findByGroup();
    }

}
