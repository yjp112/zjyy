package com.supconit.base.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.daos.DocumentDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.Document;
import com.supconit.base.services.DocumentService;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Service
public class DocumentServiceImpl extends AbstractBaseBusinessService<Document, Long> implements  DocumentService{

	@Autowired
	private DocumentDao		documentDao;
	@Autowired
	private DeviceCategoryDao		deviceCategoryDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	@Override
	@Transactional
	public Document getById(Long id) {
		Document document = documentDao.getById(id);
		return document;
	}

	@Override
	@Transactional
	public void insert(Document document) {
		checkToSave(document);
		documentDao.insert(document);		
	}

	@Override
	@Transactional
	public void update(Document document) {
		checkToSave(document);
		documentDao.update(document);
	}

	@Override
	@Transactional(readOnly = true)
	public Pageable<Document> findByCondition(Pagination<Document> pager,
			Document document) {
		if(document.getCategoryId()!=null){
	          List<Long> ids= deviceCategoryDao.findChildIds(document.getCategoryId());
	          document.setCategoryId(null);
	          document.setCategoryIds(ids);
	        }
		if(document.getAreaId()!=null){
	          List<Long> ids= geoAreaDao.findChildIds(document.getAreaId());
	          document.setAreaId(null);
	          document.setAreaIds(ids);
	        }
		return documentDao.findByPage(pager,document);
	}
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		documentDao.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		documentDao.deleteByIds(ids);
	}
 //Check that allows you to save
	  private void checkToSave(Document document){
			List<Document> list = new ArrayList<Document>();
			list = documentDao.findByDocNo(document.getDocNo());
			if(document.getFileName() == null || document.getFileName() == "")
				throw new BusinessDoneException("上传文档不能为空，请选择！");
			if (null != list && list.size() >= 1) {
				if (list.size() > 1) {
					throw new BusinessDoneException("文档编号[" + document.getDocNo()+ "]已经被占用。");
				} else {
					// list.size()==1
					if (document.getId() != null) {
						// update
						Document old = list.get(0);
						if (document.getId().longValue() == old.getId().longValue()) {
							// ok
						} else {
							throw new BusinessDoneException("文档编号["+ document.getDocNo() + "]已经被占用。");
						}
					} else {
						// insert
						throw new BusinessDoneException("文档编号["+ document.getDocNo() + "]已经被占用。");
					}

				}
			}
		  
		  
	  }

	@Override
	public List<Document> getByDeviceId(Long deviceId) {
		return documentDao.getByDeviceId(deviceId);
	}
}
