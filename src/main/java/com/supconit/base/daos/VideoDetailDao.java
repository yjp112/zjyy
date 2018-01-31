package com.supconit.base.daos;

import java.util.List;
import java.util.Map;

import com.supconit.base.entities.VideoDetail;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

public interface VideoDetailDao extends BaseDao<VideoDetail, Long> {
	public VideoDetail findDetailByVideoId(Long videoId);
	public Pageable<VideoDetail> findByCondition(Pagination<VideoDetail> pager, VideoDetail condition);

	int deleteByIds(Long[] ids);
	
	public  VideoDetail findById(Long id); 
	
	public List<VideoDetail> findList(String sql,Map map);
	
	public Long findUseCount(long id) ;
}
