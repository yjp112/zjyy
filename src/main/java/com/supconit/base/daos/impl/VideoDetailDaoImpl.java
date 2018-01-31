package com.supconit.base.daos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.base.daos.VideoDetailDao;
import com.supconit.base.entities.VideoDetail;
import com.supconit.common.daos.AbstractBaseDao;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Repository
public class VideoDetailDaoImpl extends AbstractBaseDao<VideoDetail, Long> implements VideoDetailDao {
    private static final String	NAMESPACE	= VideoDetail.class.getName();
	
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public VideoDetail findDetailByVideoId(Long videoId){
		//List<VideoDetail> dss =  selectList("findByDeviceid",videoId);
		return selectOne("findByDeviceid",videoId);
	}
	@Override
	public Pageable<VideoDetail> findByCondition(Pagination<VideoDetail> pager, VideoDetail condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}
	

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}

	@Override
	public VideoDetail findById(Long id) {
		// TODO Auto-generated method stub
		return selectOne("getById",id);
	}

	@Override
	public List<VideoDetail> findList(String sql, Map map) {
		// TODO Auto-generated method stub
		return selectList(sql,map);
	}

	@Override
	public Long findUseCount(long id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getNamespace()+"", id);
	}
}
