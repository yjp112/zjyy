package com.supconit.repair.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.entities.GeoArea;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.repair.daos.RepairEvtCategoryDao;
import com.supconit.repair.daos.RepairEvtCategoryPersonDao;
import com.supconit.repair.entities.RepairEvtCategory;
import com.supconit.repair.services.RepairEvtCategoryService;

import hc.base.domains.Pageable;


@Service
public class RepairEvtCategoryServiceImpl extends AbstractBaseBusinessService<RepairEvtCategory, Long> implements RepairEvtCategoryService{

	@Autowired
	private RepairEvtCategoryDao repairEvtCategoryDao;	
	@Autowired
	private RepairEvtCategoryPersonDao repairEvtCategoryPersonDao;

    /**
	 * Get repairEvtCategory by  ID
	 * @param id  repairEvtCategory id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public RepairEvtCategory getById(Long id) {
		if (null == id || id <= 0)
			return null;
		RepairEvtCategory repairEvtCategory = repairEvtCategoryDao.getById(id);
		
		return repairEvtCategory;
	}	

    /**
	 * delete DeviceCategory by ID 
	 * @param id  deviceCategory  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
    	isAllowDelete(id);
		
    	repairEvtCategoryDao.deleteById(id);
	}


    /**
	 * delete RepairEvtCategory by ID array
	 * @param ids  repairEvtCategory ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            isAllowDelete(ids[i]);
        }          
		repairEvtCategoryDao.deleteByIds(ids);
        
	}

    /**
	 * Find RepairEvtCategory list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<RepairEvtCategory> findByCondition(Pageable<RepairEvtCategory> pager, RepairEvtCategory condition) {
        if(condition.getParentId()!=null){//递归查询子节点
            List<Long> childIds=repairEvtCategoryDao.findChildIds(condition.getParentId());
        
             condition.setChildIds(childIds);
          }	
		return repairEvtCategoryDao.findByCondition(pager, condition);
	}

    @Override
	@Transactional
	public void update(RepairEvtCategory repairEvtCategory){
    	if(repairEvtCategory.getParentId() == null){
    		repairEvtCategory.setParentId(0l);
		}
    	isAllowSave(repairEvtCategory);
    	long pId = repairEvtCategory.getParentId();
    	if(pId !=0 ){
    		List<Long> list = repairEvtCategoryDao.findChildIds(repairEvtCategory.getId());
        	if(list.contains(pId)){
        		throw new BusinessDoneException("父类别不能设置为自身或自身的子类别"); 
        	}
    	}
		String FName = repairEvtCategory.getCategoryName();
		while (pId != 0L){
			RepairEvtCategory tempEvt = repairEvtCategoryDao.getById(pId);
			if (tempEvt != null ){
				FName = tempEvt.getCategoryName()+ "→" + FName ;
				pId = tempEvt.getParentId();
			}					
		}
		repairEvtCategory.setFullLevelName(FName);
    	repairEvtCategoryDao.update(repairEvtCategory);
    	List<RepairEvtCategory> list = repairEvtCategoryDao.findChildIdsEntity(repairEvtCategory.getId());
    	for (RepairEvtCategory repair : list) {
    		if(repair.getId().longValue()==repairEvtCategory.getId()){
    			continue;
    		}
    		long pid = repair.getParentId();
    		String FNames = repair.getCategoryName();
    		while (pid != 0L){
    			RepairEvtCategory tempEvt = repairEvtCategoryDao.getById(pid);
    			if (tempEvt != null ){
    				FNames = tempEvt.getCategoryName()+ "→" + FNames ;
    				pid = tempEvt.getParentId();
    			}					
    		}
    		repair.setFullLevelName(FNames);
        	repairEvtCategoryDao.update(repair);
		}
	}

    /**
	 * insert repairEvtCategory
	 * @param repairEvtCategory  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(RepairEvtCategory repairEvtCategory) {
    	if(repairEvtCategory.getParentId() == null){
        	repairEvtCategory.setParentId(0l);
		}
        isAllowSave(repairEvtCategory);
		Long pId = repairEvtCategory.getParentId();
		String FName = repairEvtCategory.getCategoryName();
		while (pId != 0L){
			RepairEvtCategory tempEvt = repairEvtCategoryDao.getById(pId);
			if (tempEvt != null ){
				FName = tempEvt.getCategoryName()+ "→" + FName ;
				pId = tempEvt.getParentId();
			}					
		}
		repairEvtCategory.setFullLevelName(FName);    
        repairEvtCategoryDao.insert(repairEvtCategory);

	}

    private void isAllowDelete(Long id)
    {
        long count=repairEvtCategoryDao.findChildrenCount(id);
        if(count>0){
            throw new BusinessDoneException("该类别含有子类别，不能删除。");
        }
        long num = repairEvtCategoryPersonDao.countByCategoryId(id);
        if(num>0){
            throw new BusinessDoneException("该类别已经被引用，不能删除。");
        }
        
    }
    //Check that allows you to save
    private void isAllowSave(RepairEvtCategory repairEvtCategory)
    {
    		RepairEvtCategory dp=repairEvtCategoryDao.getByPidAndCategoryName(repairEvtCategory);
            if(dp!=null){
                throw new BusinessDoneException("类别名词["+repairEvtCategory.getCategoryName()+"]在当前父类别下已被占用。");  
    	}
    }
	@Override
	public List<RepairEvtCategory> findAll() {
		// TODO Auto-generated method stub
		return repairEvtCategoryDao.findAll();
	}
	
}
