
package com.supconit.spare.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.BillStatusEnum;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.spare.daos.PriceChangeDao;
import com.supconit.spare.daos.SpareCategoryDao;
import com.supconit.spare.daos.SpareDao;
import com.supconit.spare.entities.PriceChange;
import com.supconit.spare.entities.Spare;
import com.supconit.spare.services.PriceChangeService;
import com.supconit.spare.services.SpareService;

import hc.base.domains.Pageable;


@Service
public class PriceChangeServiceImpl extends AbstractBaseBusinessService<PriceChange, Long> implements PriceChangeService{

	@Autowired
	private PriceChangeDao		priceChangeDao;	
	@Resource
	private SpareService spareService;
	@Resource
	private SpareDao spareDao;
	@Resource
    private SpareCategoryDao        spareCategoryDao;   
    /**
	 * Get priceChange by  ID
	 * @param id  priceChange id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public PriceChange getById(Long id) {
		if (null == id || id <= 0)
			return null;
		PriceChange priceChange = priceChangeDao.getById(id);
		
		return priceChange;
	}	

    /**
	 * delete PriceChange by ID 
	 * @param id  priceChange  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
        checkDelete(id);
		
		priceChangeDao.deleteById(id);
	}


    /**
	 * delete PriceChange by ID array
	 * @param ids  priceChange ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
		    checkDelete(ids[i]);
        }  
        
        priceChangeDao.deleteByIds(ids);
	}

    /**
	 * Find PriceChange list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<PriceChange> findByCondition(Pageable<PriceChange> pager,
			PriceChange condition) {
		 if(condition.getSpareCategoryId()!=null){
	            List<Long> ids= spareCategoryDao.selecChilrenCategorieIds(condition.getSpareCategoryId());
	            condition.setSpareCategoryId(null);
	            condition.setSpareCategoryIds(ids);
	          }
		return priceChangeDao.findByPage(pager, condition);
	}
	    
    
    /**
	 * update PriceChange
	 * @param priceChange  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(PriceChange priceChange) {
		 if(!isAllowSave(priceChange))
            throw new NullPointerException("priceChange  ?????");
//        if(priceChange.getChangeType()){
//            
//        }
		priceChangeDao.update(priceChange);

        Spare spare=new Spare();
        spare.setId(priceChange.getSpareId());
        spare.setSpareCode(priceChange.getSpareCode());
        spare.setPrice(priceChange.getNewPrice());
        spareDao.updateSelective(spare);
	}
    
    /**
	 * insert PriceChange
	 * @param priceChange  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(PriceChange priceChange) {
        
         if(!isAllowSave(priceChange))
            throw new NullPointerException("priceChange  ?????");
        priceChange.setChangeType(CHANGE_TYPE_IMMEDIATELY);
        priceChange.setStatus(BillStatusEnum.SAVED.getValue());
        priceChange.setBillNo(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.PRICE_CHANGE));    
		priceChangeDao.insert(priceChange);

        Spare spare=new Spare();
        spare.setId(priceChange.getSpareId());
        spare.setSpareCode(priceChange.getSpareCode());
        spare.setPrice(priceChange.getNewPrice());
        spareDao.updateSelective(spare);
	}

    @Override
    public void takeEffect(Long id) {
        // 1.检查单据是否已经生效，如果已经生效，则程序终止
        PriceChange priceChange = priceChangeDao.findById(id);
        Spare spare=new Spare();
        spare.setId(priceChange.getSpareId());
        spare.setSpareCode(priceChange.getSpareCode());
        spare.setPrice(priceChange.getNewPrice());
        spareDao.updateSelective(spare);
    }   
     //Check that allows you to delete 
    private void checkDelete(Long id) {
//        PriceChange priceChange=priceChangeDao.getById(id);
//        if(priceChange.getStatus()!=BillStatusEnum.SAVED.getValue()){
//            throw new DeleteDenyException("调价单["+transfer.getTransferCode()+"]状态为["+BillStatusEnum.SAVED.getDesc()+"],不允许删除。");
//        }
    }
    //Check that allows you to save
    private boolean isAllowSave(PriceChange priceChange)
    {
        return true;
    }

	


}
