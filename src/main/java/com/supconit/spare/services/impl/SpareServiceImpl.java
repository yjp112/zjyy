
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
import com.supconit.spare.daos.SpareDao;
import com.supconit.spare.entities.Spare;
import com.supconit.spare.services.SpareService;

import hc.base.domains.Pageable;


@Service
public class SpareServiceImpl extends AbstractBaseBusinessService<Spare, Long> implements SpareService{

	@Autowired
	private SpareDao		spareDao;	

	@Resource
	private SpareCategoryDao spareCategoryDao;
    /**
	 * Get spare by  ID
	 * @param id  spare id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public Spare getById(Long id) {
		if (null == id || id <= 0)
			return null;
		Spare spare = spareDao.getById(id);
		
		return spare;
	}	

    /**
	 * delete Spare by ID 
	 * @param id  spare  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
        checkDelete(id);
		
		spareDao.deleteById(id);
	}


    /**
	 * delete Spare by ID array
	 * @param ids  spare ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            checkDelete(ids[0]);
        }  
        
        spareDao.deleteByIds(ids);
	}

    /**
	 * Find Spare list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<Spare> findByCondition(Pageable<Spare> pager,
			Spare condition) {
		 if(condition.getSpareCategoryId()!=null){
	          List<Long> ids= spareCategoryDao.selecChilrenCategorieIds(condition.getSpareCategoryId());
	          condition.setSpareCategoryId(null);
	          condition.setSpareCategoryIds(ids);
	        }
		return spareDao.findByPage(pager, condition);
	}
	    
    
    /**
	 * update Spare
	 * @param spare  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(Spare spare) {
        checkToSave(spare);
		spareDao.update(spare);
	}
    
    /**
	 * insert Spare
	 * @param spare  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(Spare spare) {
        
        checkToSave(spare);
            
		spareDao.insert(spare);
	}
    
     //Check that allows you to delete 
    private void checkDelete(Long id)
    {
        if(spareDao.countSpareIdUsed(id)>0){
            throw new DeleteDenyException("备件["+spareDao.getById(id).getSpareName()+"]已经被使用,不允许删除。");
        }
    }
    //Check that allows you to save
    private void checkToSave(Spare spare)
    {
    	Integer upper=spare.getUpperQty();
    	Integer lower=spare.getLowerQty();
    	Integer safe=spare.getSafeQty();
        if(upper!=null&&safe!=null&&upper-safe<0){
            throw new BusinessDoneException("安全库存不能大于最高库存。");
        }
        if(upper!=null&&lower!=null&&upper.compareTo(lower)<0){
            throw new BusinessDoneException("最低库存不能大于最高库存。");
        }
        if(safe!=null&&lower!=null&&safe.compareTo(lower)<0){
            throw new BusinessDoneException("最低库存不能大于安全库存。");
        }
        
        long count=spareDao.countRecordBySpareCode(spare.getSpareCode());
        if(spare.getId()!=null){
            //update
            if(count>1){
                throw new BusinessDoneException("备件编码["+spare.getSpareCode()+"]已经被占用。");
            }
        }else{
            //insert
            if(count>=1){
                throw new BusinessDoneException("备件编码["+spare.getSpareCode()+"]已经被占用。");  
            }
        }
    }

	@Override
	public Pageable<Spare> findStockByCondition(Pageable<Spare> pager,
			Spare condition) {
		return spareDao.findStockByCondition(pager, condition);
	}

	/** 手机端使用 **/
	@Override
	public List<Spare> selectSpares(Spare spare) {
		return this.spareDao.selectSpares(spare);
	}

	@Override
	public long countSpares(Spare spare) {
		return this.spareDao.countSpares(spare);
	}


}
