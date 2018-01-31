
package com.supconit.base.daos;

import java.util.List;

import com.supconit.base.entities.Attachment;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;


public interface AttachmentDao extends BasicDao<Attachment, Long>{
	
	Pageable<Attachment> findByCondition(Pageable<Attachment> pager, Attachment condition);

	int deleteByIds(Long[] ids);
    public  Attachment findById(Long id); 
    public List<Attachment> getAttachmentByFid(Long fId,String modelType) ;
    public void saveAttachements(Long objId,String objType, String[] fileorignal,String[] filename, String[] delfiles,String fileLength);
    public int deleteByObj(Long fId,String modelType);
}