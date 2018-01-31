
package com.supconit.base.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.base.daos.ContractDao;
import com.supconit.base.entities.Contract;
import com.supconit.base.services.ContractService;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.Constant;

import hc.base.domains.Pageable;

@Service
public class ContractServiceImpl extends AbstractBaseBusinessService<Contract, Long> implements ContractService{

	@Autowired
	private ContractDao		contractDao;	
	@Autowired
	private AttachmentDao attachmentDao;
    /**
	 * Get contract by  ID
	 * @param id  contract id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public Contract getById(Long id) {
		if (null == id || id <= 0)
			return null;
		Contract contract = contractDao.getById(id);
		
		return contract;
	}	

    /**
	 * delete Contract by ID 
	 * @param id  contract  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
		if(!isAllowDelete(id))
            throw new NullPointerException("contract  ?????");
		
		contractDao.deleteById(id);
	}


    /**
	 * delete Contract by ID array
	 * @param ids  contract ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new NullPointerException("contract  ?????");
        }  
        
        contractDao.deleteByIds(ids);
	}

    /**
	 * Find Contract list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<Contract> findByCondition(Pageable<Contract> pager, Contract condition) {
		return contractDao.findByCondition(pager, condition);
	}

	    
    
    /**
	 * update Contract
	 * @param contract  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(Contract contract) {           
		contractDao.update(contract);
	}
    /**
  	 * update Contract
  	 * @param contract  instance 
  	 * @return
  	 */
      @Override
  	@Transactional
  	public void update(Contract contract,String[] fileorignal,String[] filename,String[] delfiles,String fileLength){     
  		try {
		
    	    contractDao.update(contract);
	  	    //update 随机档案
	    	attachmentDao.saveAttachements(contract.getId(),Constant.ATTACHEMENT_CONTRACT,fileorignal, filename, delfiles,fileLength);
    	
		} catch (Exception e) {
			throw new BusinessDoneException("保存失败");  
		}
  	}
      
    /**
	 * insert Contract
	 * @param contract  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(Contract contract) {
    	isAllowSave(contract);
		contractDao.insert(contract);
	}
    /**
	 * insert Contract
	 * @param contract  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(Contract contract,String[] fileorignal,String[] filename,String[] delfiles,String fileLength) {
        
        isAllowSave(contract); 
        try {
			contractDao.insert(contract);
			//update 随机档案
	    	attachmentDao.saveAttachements(contract.getId(),Constant.ATTACHEMENT_CONTRACT,fileorignal, filename, delfiles,fileLength);
		} catch (Exception e) {
			throw new BusinessDoneException("保存失败");  
		}
    }
     //Check that allows you to delete 
    private boolean isAllowDelete(Long id)
    {
        return true;
    }
    //Check that allows you to save
    private void isAllowSave(Contract contract)
    {
//    	Contract dp;
//    	long count;
//    	if(contract.getId()==null){
//    		//insert
//    		dp = new Contract();
//        	dp.setContractCode(contract.getContractCode());
//        	Pageable<Constant> pager=new Pagination<Constant>();
//        	pager.setPageNo(1);
//        	pager.setPageSize(10);
//        	count=contractDao.findByCondition(pager, dp).getTotal();
//            if(count>0){
//                throw new BusinessDoneException("合同编码["+contract.getContractCode()+"]已经被占用。");  
//            }
//    	}
    }

}
