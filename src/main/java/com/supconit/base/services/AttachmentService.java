
package com.supconit.base.services;

import java.util.List;

import com.supconit.base.entities.Attachment;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;


public interface AttachmentService extends BaseBusinessService<Attachment, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<Attachment> findByCondition(Pageable<Attachment> pager, Attachment condition);



   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param attachment  object instance 
	 * @return
	 */
	void save(Attachment attachment);
	public List<Attachment> getAttachmentByFid(Long fId,String modelType);
}

