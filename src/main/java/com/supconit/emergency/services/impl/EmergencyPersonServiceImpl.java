package com.supconit.emergency.services.impl;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.ArrayList;
import java.util.List;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.emergency.daos.EmergencyGroupDao;
import com.supconit.emergency.daos.EmergencyPersonDao;
import com.supconit.emergency.entities.EmergencyGroup;
import com.supconit.emergency.entities.EmergencyPerson;
import com.supconit.emergency.services.EmergencyPersonService;
import com.supconit.ywgl.exceptions.BusinessDoneException;

@Service
public class EmergencyPersonServiceImpl extends AbstractBaseBusinessService<EmergencyPerson, Long> implements EmergencyPersonService{

	@Autowired
	private EmergencyPersonDao		emergencyPersonDao;	
	@Autowired
	private EmergencyGroupDao		emergencyGroupDao;	
	
	@Override
	@Transactional(readOnly = true)
	public EmergencyPerson getById(Long id) {
		if (null == id || id <= 0)
			return null;		
		return emergencyPersonDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(EmergencyPerson entity) {
		if(!isAllowSave(entity))
            throw new BusinessDoneException("请不要选择组类别作为班组。");
            
		emergencyPersonDao.insert(entity);		
	}

	@Override
	@Transactional
	public void update(EmergencyPerson entity) {

		if(!isAllowSave(entity))
            throw new BusinessDoneException("请不要选择组类别作为班组。");
            
		emergencyPersonDao.update(entity);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		emergencyPersonDao.deleteById(id);
	}

	@Override
	public List<EmergencyPerson> findTree(
			List<EmergencyGroup> emergencyGroup_tree) {
		List<Long> groupIds = new ArrayList<Long>();
		for(EmergencyGroup dg:emergencyGroup_tree){
			groupIds.add(dg.getId());
		}
		return emergencyPersonDao.findTree(groupIds);
	}

	@Override
	@Transactional(readOnly = true)
	public Pageable<EmergencyPerson> findByCondition(
			Pagination<EmergencyPerson> page, EmergencyPerson condition,
			String treeId) {
		if (treeId.length() > 4 && treeId.substring(0, 4).equals("leef")) {// 如果点的是叶子节点
			String strId = treeId.substring(4, treeId.length());
			Long id = Long.parseLong(strId);
			condition.setId(id);
			return emergencyPersonDao.findByCondition(page, condition);
		} else {
			Long groupId = 0L;
			if (StringUtil.isNotBlank(treeId)) {
				groupId = Long.parseLong(treeId);
			}
			// 查出每个节点对应的子节点（包括自己）
			List<EmergencyGroup> listEmergencyGroups = new ArrayList<EmergencyGroup>();
			if (groupId == 0L || groupId.equals(0L)) {// 如果是根节点
				listEmergencyGroups = emergencyGroupDao.findSubByRoot(groupId);
			} else {
				listEmergencyGroups = emergencyGroupDao.findSubById(groupId);
			}
			List<Long> subEmergencyGroups = new ArrayList<Long>();
			for (int i = 0; i < listEmergencyGroups.size(); i++) {
				subEmergencyGroups.add(listEmergencyGroups.get(i).getId());
			}
			if (subEmergencyGroups != null && subEmergencyGroups.size() > 0) {
				condition.setSearchEmergencyGroupList(subEmergencyGroups);
			}
			return emergencyPersonDao.findByCondition(page, condition);
		}
	}

	@Override
	public boolean countByGroupIdAndPersonId(EmergencyPerson emergencyPerson) {
		return emergencyPersonDao.countByGroupIdAndPersonId(emergencyPerson)==0? true:false;
	}

	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
//		for(int i =0;i<ids.length;i++)
//        {
//            if(!isAllowDelete(ids[i]))
//                throw new BusinessDoneException("该组员有值班任务，不能删除");
//        }  
        
		emergencyPersonDao.deleteByIds(ids);
	}
	
	
	 //Check that allows you to save
    private boolean isAllowSave(EmergencyPerson emergencyPerson){
    	//GroupId不能为空；根节点不允许成为groupId
    	if(null==emergencyPerson.getGroupId()||emergencyPerson.getGroupId().equals(0L)){
    		return false;
    	}
    	//二级节点不允许成为组
    	EmergencyGroup dg = emergencyGroupDao.getById(emergencyPerson.getGroupId());
    	if(null==dg.getParentId()||dg.getParentId().equals(0L)){
    		return false;
    	}
        return true;
    }

}
