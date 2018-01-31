
package com.supconit.base.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.EnumDetailDao;
import com.supconit.base.entities.EnumDetail;
import com.supconit.base.services.EnumDetailService;
import com.supconit.common.services.AbstractBaseBusinessService;

import hc.base.domains.Pageable;

@Service
public class EnumDetailServiceImpl extends AbstractBaseBusinessService<EnumDetail, Long> implements EnumDetailService{

	@Autowired
	private EnumDetailDao		enumDetailDao;	

    /**
	 * Get enumDetail by  ID
	 * @param id  enumDetail id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public EnumDetail getById(Long id) {
		if (null == id || id <= 0)
			return null;
		EnumDetail enumDetail = enumDetailDao.getById(id);
		
		return enumDetail;
	}	

	@Override
	public Pageable<EnumDetail> findByCondition(Pageable pager,
			EnumDetail condition) {
		return enumDetailDao.findByCondition(pager, condition);
	}
    /**
	 * delete EnumDetail by ID array
	 * @param ids  enumDetail ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new NullPointerException("enumDetail  ?????");
        }  
        
        enumDetailDao.deleteByIds(ids);
	}

	    
    
    /**
	 * update EnumDetail
	 * @param enumDetail  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(EnumDetail enumDetail) {
		 if(!isAllowSave(enumDetail))
            throw new NullPointerException("enumDetail  ?????");
            
		enumDetailDao.update(enumDetail);
	}
    
    /**
	 * insert EnumDetail
	 * @param enumDetail  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(EnumDetail enumDetail) {
        
         if(!isAllowSave(enumDetail))
            throw new NullPointerException("enumDetail  ?????");
            
		enumDetailDao.insert(enumDetail);
	}
    
     //Check that allows you to delete 
    private boolean isAllowDelete(Long id)
    {
        return true;
    }
    //Check that allows you to save
    private boolean isAllowSave(EnumDetail enumDetail)
    {
        return true;
    }

	@Override
	public void deleteById(Long id) {
		enumDetailDao.deleteById(id);
	}


}
