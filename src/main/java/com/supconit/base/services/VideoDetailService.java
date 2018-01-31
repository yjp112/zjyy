package com.supconit.base.services;

import java.util.List;
import java.util.Map;

import com.supconit.base.entities.VideoDetail;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

public interface VideoDetailService extends BaseBusinessService<VideoDetail, Long>{

	void save(VideoDetail videoDetail);
	
	
	void deleteByIds(Long[] ids);
	
	Pageable<VideoDetail> findByCondition(Pagination<VideoDetail> pager, VideoDetail condition);

	public List<VideoDetail> findList(String sql,Map map);
}
