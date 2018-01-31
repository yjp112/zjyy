
package com.supconit.spare.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.spare.daos.StockDao;
import com.supconit.spare.daos.WarehouseDao;
import com.supconit.spare.entities.Stock;
import com.supconit.spare.entities.Warehouse;
import com.supconit.spare.services.WarehouseService;

import hc.base.domains.Pageable;


@Service
public class WarehouseServiceImpl extends AbstractBaseBusinessService<Warehouse, Long> implements WarehouseService{

	@Autowired
	private WarehouseDao		warehouseDao;	
	@Autowired
	private StockDao stockDao;	

    /**
	 * Get warehouse by  ID
	 * @param id  warehouse id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public Warehouse getById(Long id) {
		if (null == id || id <= 0)
			return null;
		Warehouse warehouse = warehouseDao.getById(id);
		
		return warehouse;
	}	
	
	@Override
	public List<Warehouse> findByCode(String warehouseCode, String warehouseName){
		return warehouseDao.findByCode(warehouseCode, warehouseName);
	}
	
    /**
	 * delete Warehouse by ID 
	 * @param id  warehouse  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
		if(!isAllowDelete(id))
            throw new BusinessDoneException("仓库已经被使用，不能删除");
		
		warehouseDao.deleteById(id);
	}


    /**
	 * delete Warehouse by ID array
	 * @param ids  warehouse ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new BusinessDoneException("仓库已经被使用，不能删除");
        }  
        
        warehouseDao.deleteByIds(ids);
	}

    /**
	 * Find Warehouse list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<Warehouse> findByCondition(Pageable<Warehouse> pager,
			Warehouse condition) {
		return warehouseDao.findByPage(pager, condition);
	}	    
    
    /**
	 * update Warehouse
	 * @param warehouse  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(Warehouse warehouse) {
		warehouseDao.update(warehouse);
	}
    
    /**
	 * insert Warehouse
	 * @param warehouse  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(Warehouse warehouse) {
        
		warehouseDao.insert(warehouse);
	}
    
     //Check that allows you to delete 
    private boolean isAllowDelete(Long id)
    {
    	List<Stock> list = stockDao.selectByWarehouseId(id);
    	if(null!=list && list.size()>0){
    		return false;
    	}
        return true;
    }
    
    

    @Override
    public List<Warehouse> finaAll() {
        return warehouseDao.findAll();
    }

	

}
