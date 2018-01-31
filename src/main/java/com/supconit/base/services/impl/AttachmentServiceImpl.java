
package com.supconit.base.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.base.entities.Attachment;
import com.supconit.base.services.AttachmentService;
import com.supconit.common.services.AbstractBaseBusinessService;

import hc.base.domains.Pageable;

@Service
public class AttachmentServiceImpl extends AbstractBaseBusinessService<Attachment, Long> implements AttachmentService{

	@Autowired
	private AttachmentDao		attachmentDao;	

    /**
	 * Get attachment by  ID
	 * @param id  attachment id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public Attachment getById(Long id) {
		if (null == id || id <= 0)
			return null;
		Attachment attachment = attachmentDao.getById(id);
		
		return attachment;
	}	

    /**
	 * delete Attachment by ID 
	 * @param id  attachment  ID 	 
	 * @return 
	 */
    @Override
	@Transactional
	public void deleteById(Long id) {
        
		if(!isAllowDelete(id))
            throw new NullPointerException("attachment  ?????");
		
		attachmentDao.deleteByIds(new Long[]{id});
	}


    /**
	 * delete Attachment by ID array
	 * @param ids  attachment ID array	 
	 * @return 
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i]))
                throw new NullPointerException("attachment  ?????");
        }  
        
        attachmentDao.deleteByIds(ids);
	}

    /**
	 * Find Attachment list by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public Pageable<Attachment> findByCondition(Pageable<Attachment> pager, Attachment condition) {
		return attachmentDao.findByCondition(pager, condition);
	}

	    
    
    /**
	 * update Attachment
	 * @param attachment  instance 
	 * @return
	 */
    @Override
	@Transactional
	public void update(Attachment attachment) {
		 if(!isAllowSave(attachment))
            throw new NullPointerException("attachment  ?????");
            
		attachmentDao.update(attachment);
	}
    
    /**
	 * insert Attachment
	 * @param attachment  instance	 
	 * @return
	 */
    @Override
	@Transactional
	public void insert(Attachment attachment) {
        
         if(!isAllowSave(attachment))
            throw new NullPointerException("attachment  ?????");
            
		attachmentDao.insert(attachment);
	}
    
     //Check that allows you to delete 
    private boolean isAllowDelete(Long id)
    {
        return true;
    }
    //Check that allows you to save
    private boolean isAllowSave(Attachment attachment)
    {
        return true;
    }

	@Override
	public List<Attachment> getAttachmentByFid(Long fId, String modelType) {
		// TODO Auto-generated method stub
		return attachmentDao.getAttachmentByFid(fId,modelType);
	}

}
