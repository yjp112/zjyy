
package com.supconit.spare.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.exceptions.DeleteDenyException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.spare.daos.SpareCategoryDao;
import com.supconit.spare.entities.SpareCategory;
import com.supconit.spare.services.SpareCategoryService;
import com.supconit.spare.services.SpareService;

import hc.base.domains.Pageable;


@Service
public class SpareCategoryServiceImpl extends AbstractBaseBusinessService<SpareCategory, Long> implements SpareCategoryService{

	@Autowired
	private SpareCategoryDao		spareCategoryDao;	
	@Resource
	private SpareService spareService;

    /**
	 * Get spareCategory by  ID
	 * @param id  spareCategory id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public SpareCategory getById(Long id) {
		if (null == id || id <= 0)
			return null;
		SpareCategory spareCategory = spareCategoryDao.getById(id);
		
		return spareCategory;
	}	

    /**
	 * delete SpareCategory by ID 
	 * @param id  spareCategory  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
        checkDelete(id);
		
		spareCategoryDao.deleteById(id);
	}


    /**
	 * delete SpareCategory by ID array
	 * @param ids  spareCategory ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
		    checkDelete(ids[i]);
        }  
        
        //spareCategoryDao.deleteByIds(ids);
	}

	/**
	 * Find SpareCategory list by condition
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<SpareCategory> findByCondition(
			Pageable<SpareCategory> pager, SpareCategory condition) {
		 if(condition.getParentId()!=null){
	            List<Long> ids= spareCategoryDao.selecChilrenCategorieIds(condition.getParentId());
	            condition.setParentId(null);
	            condition.setCategoryIds(ids);
	          }
		return spareCategoryDao.findByPage(pager, condition);
	}
	    
    
    /**
	 * update SpareCategory
	 * @param spareCategory  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(SpareCategory spareCategory) {
        checkToSave(spareCategory);
            
		spareCategoryDao.update(spareCategory);
	}
    
    /**
	 * insert SpareCategory
	 * @param spareCategory  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(SpareCategory spareCategory) {
        
        checkToSave(spareCategory);
            
		spareCategoryDao.insert(spareCategory);
	}
    
     //Check that allows you to delete 
    private void checkDelete(Long id) {
        if(spareCategoryDao.countSpareCategoryIdUsed(id)>0){
            throw new DeleteDenyException("备件类别["+spareCategoryDao.getById(id).getCategoryName()+"]已经被使用,不允许删除。");
        }
    }
    //Check that allows you to save
    private void checkToSave(SpareCategory spareCategory)
    {
    	List<SpareCategory> list =spareCategoryDao.findByCode(spareCategory.getCategoryCode());
      
    	if (null != list && list.size() >=1) {
    		if(list.size() >1){
                throw new BusinessDoneException("类别编码["+spareCategory.getCategoryCode()+"]已经被占用。");
    		}else{
    			 if(spareCategory.getId()!=null){  //update
    				SpareCategory spareCategoryTemp =list.get(0);
 	            	if(spareCategoryTemp.getId().equals(spareCategory.getId())){
 	            		
 	            	}else{
 	            		throw new BusinessDoneException("类别编码["+spareCategory.getCategoryCode()+"]已经被占用。");
 	            	}
    			 }else{  //insert
    				 throw new BusinessDoneException("类别编码["+spareCategory.getCategoryCode()+"]已经被占用。");  
    			 }
    		}
    	}
    }

    @Override
    public List<SpareCategory> selectCategories() {
        return spareCategoryDao.selecCategories();
    }

    @Override
    public List<SpareCategory> selectCategoriesByFilter(Long id)
    {
        // TODO Auto-generated method stub
        return spareCategoryDao.selectCategoriesByFilter(id);
    }


	/** 手机端使用 **/
	@Override
	public List<SpareCategory> findByParentId(long parentId) {
		return this.spareCategoryDao.findByParentId(parentId);
	}
}
