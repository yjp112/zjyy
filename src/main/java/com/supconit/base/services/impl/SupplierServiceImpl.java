
package com.supconit.base.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DeviceDao;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.Supplier;
import com.supconit.base.services.SupplierService;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.spare.daos.SpareDao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Service
public class SupplierServiceImpl extends AbstractBaseBusinessService<Supplier, Long> implements SupplierService{

	@Autowired
	private com.supconit.base.daos.SupplierDao		supplierDao;	
	@Autowired
	private DeviceDao deviceDao;
	@Autowired
	private SpareDao spareDao;

    /**
	 * Get supplier by  ID
	 * @param id  supplier id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public Supplier getById(Long id) {
		if (null == id || id <= 0)
			return null;
		Supplier supplier = supplierDao.getById(id);
		
		return supplier;
	}	

    /**
	 * delete Supplier by ID 
	 * @param id  supplier  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
		if(!isAllowDelete(id))
            throw new BusinessDoneException("该厂商信息已被设备引用，不能删除。");
		
		supplierDao.deleteById(id);
	}


    /**
	 * delete Supplier by ID array
	 * @param ids  supplier ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new BusinessDoneException("该厂商信息已被设备引用，不能删除。");
        }  
        
        supplierDao.deleteByIds(ids);
	}

    /**
	 * Find Supplier list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<Supplier> findByCondition(Pagination<Supplier> pager, Supplier condition) {
		return supplierDao.findByCondition(pager, condition);
	}

	    
    
    /**
	 * update Supplier
	 * @param supplier  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(Supplier supplier) {
    	checkToSave(supplier);
            
		supplierDao.update(supplier);
	}
    
    /**
	 * insert Supplier
	 * @param supplier  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(Supplier supplier) {
        
    	checkToSave(supplier);
            
		supplierDao.insert(supplier);
	}
    
     //Check that allows you to delete 
    private boolean isAllowDelete(Long id){
    	Device device = new Device();
		device.setSupplierId(id);
		Long num = deviceDao.countByDevice(device);
		Long spare = spareDao.countByConditions(device);
		if((null!=num && num.longValue()>0l )||(null!=spare && spare.longValue()>0l)){
			 return false;
		}
        return true;
    }
    //Check that allows you to save
    private boolean isAllowSave(Supplier supplier)
    {
        return true;
    }
    
    private void checkToSave(Supplier entity) {
		List<Supplier> list = new ArrayList<Supplier>();
		list = supplierDao.findByCode(entity.getSupplierCode());
		if (null != list && list.size() >= 1) {
			if (list.size() > 1) {
				throw new BusinessDoneException("编码[" + entity.getSupplierCode()+ "]已经被占用。");
			} else {
				// list.size()==1
				if (entity.getId() != null) {
					// update
					Supplier old = list.get(0);
					if (entity.getId().longValue() == old.getId().longValue()) {
						// ok
					} else {
						throw new BusinessDoneException("编码["+ entity.getSupplierCode() + "]已经被占用。");
					}
				} else {
					// insert
					throw new BusinessDoneException("编码["+ entity.getSupplierCode() + "]已经被占用。");
				}

			}
		}
	}
    

}
