package com.supconit.base.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.VideoDetailDao;
import com.supconit.base.entities.VideoDetail;
import com.supconit.base.services.VideoDetailService;
import com.supconit.common.services.AbstractBaseBusinessService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
@Service
public class VideoDetailServiceImpl extends AbstractBaseBusinessService<VideoDetail, Long> implements VideoDetailService{

	@Autowired
	public VideoDetailDao videoDetailDao;
	@Override
	@Transactional(readOnly = true)
	public VideoDetail getById(Long id) {
		if (null == id || id <= 0)
			return null;
		VideoDetail videoDetail = videoDetailDao.getById(id);
		
		return videoDetail;
	}
	

	

	@Override
	@Transactional
	public void insert(VideoDetail videoDetail) {
		// TODO Auto-generated method stub
		videoDetailDao.insert(videoDetail);
	}

	@Override
	@Transactional
	public void update(VideoDetail videoDetail) {
		// TODO Auto-generated method stub
		videoDetailDao.update(videoDetail);
	}

	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		// TODO Auto-generated method stub
		List<VideoDetail> videoDetailList=new ArrayList<VideoDetail>();
		for(int i =0;i<ids.length;i++)
        {
            
            videoDetailList.add(getById(ids[i]));
        }  
        
		videoDetailDao.deleteByIds(ids);
	}

	
	@Override
	@Transactional(readOnly = true)
	public Pageable<VideoDetail> findByCondition(Pagination<VideoDetail> pager,VideoDetail condition) {
		
		return videoDetailDao.findByCondition(pager, condition);
	}
	@Override
	public List<VideoDetail> findList(String sql, Map map) {
		// TODO Auto-generated method stub
		return videoDetailDao.findList(sql, map);
	}


	@Override
	public void deleteById(Long arg0) {
		// TODO Auto-generated method stub
		
	}

}
