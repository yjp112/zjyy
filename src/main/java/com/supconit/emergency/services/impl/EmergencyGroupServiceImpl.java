package com.supconit.emergency.services.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.emergency.daos.EmergencyGroupDao;
import com.supconit.emergency.daos.EmergencyPersonDao;
import com.supconit.emergency.entities.EmergencyGroup;
import com.supconit.emergency.entities.EmergencyPerson;
import com.supconit.emergency.services.EmergencyGroupService;
import com.supconit.ywgl.exceptions.BusinessDoneException;


@Service
public class EmergencyGroupServiceImpl extends AbstractBaseBusinessService<EmergencyGroup, Long> implements EmergencyGroupService{

	@Autowired
	private EmergencyGroupDao		emergencyGroupDao;	
	@Autowired
	private EmergencyPersonDao		emergencyPersonDao;	
	@Override
	@Transactional(readOnly = true)
	public EmergencyGroup getById(Long id) {
		if (null == id || id <= 0)
			return null;
		return emergencyGroupDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(EmergencyGroup entity) {
		checkToSave(entity);
        if(entity.getParentId()==null) 
        	entity.setParentId(0L);
        emergencyGroupDao.insert(entity);
		
	}

	@Override
	@Transactional
	public void update(EmergencyGroup entity) {
		checkToSave(entity);
		 if(entity.getParentId()==null) 
			 entity.setParentId(0L);    
		 emergencyGroupDao.update(entity);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		if(!isAllowDelete(id))
            throw new BusinessDoneException("该组有组员，不能删除");
		
		emergencyGroupDao.deleteById(id);
	}

	@Override
	public List<EmergencyGroup> findTree(Long flag) {
		return emergencyGroupDao.findTree(flag);
	}

	@Override
	@Transactional(readOnly = true)
	public Pageable<EmergencyGroup> findByCondition(
			Pagination<EmergencyGroup> page, EmergencyGroup dutyGroup) {
		return emergencyGroupDao.findByCondition(page,dutyGroup);
	}

	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new BusinessDoneException("该组有组员，不能删除");
        }  
        
		emergencyGroupDao.deleteByIds(ids);
	}

	@Override
	public List<EmergencyGroup> findSubById(Long parentId) {
		return emergencyGroupDao.findSubById(parentId);
	}

	  private void checkToSave(EmergencyGroup entity){
	        List<EmergencyGroup> list = new ArrayList<EmergencyGroup>();
			list = emergencyGroupDao.findByCode(entity.getGroupCode());
			if (null != list && list.size() >= 1) {
				if (list.size() > 1) {
					throw new BusinessDoneException("编码[" + entity.getGroupCode()+ "]已经被占用。");
				} else {
					// list.size()==1
					if (entity.getId() != null) {
						// update
						EmergencyGroup old = list.get(0);
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
	        	EmergencyGroup father = emergencyGroupDao.getById(entity.getParentId());
	        	if(father.getParentId()!=0){
	        		throw new BusinessDoneException("最多只能有三层，不能再为["+father.getGroupName()+"]增加子班组。");  
	        	}
	        }
	    }
	  
	  //Check that allows you to delete 
	    private boolean isAllowDelete(Long id){	
	    	//是否有组员
	    	List<EmergencyPerson> listPersons = emergencyPersonDao.findGroupPersons(id);
	    	if(null!=listPersons && listPersons.size()>0){
	    		return false;
	    	}
	    	//是否有子班组
	    	List<EmergencyGroup> listGroups = emergencyGroupDao.findSubByRoot(id);
	    	if(null!=listGroups && listGroups.size()>0){
	    		return false;
	    	}
	        return true;
	    }
}
