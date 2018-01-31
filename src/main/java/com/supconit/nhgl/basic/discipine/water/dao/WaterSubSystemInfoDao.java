package com.supconit.nhgl.basic.discipine.water.dao;

import java.util.List;

import com.supconit.nhgl.basic.discipine.water.entities.WaterSubSystemInfo;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;


public interface WaterSubSystemInfoDao extends BasicDao<WaterSubSystemInfo,Long>{
	Pageable<WaterSubSystemInfo> findByCondition(Pageable<WaterSubSystemInfo> pager,WaterSubSystemInfo condition);
	public WaterSubSystemInfo findById(Long id);
	public WaterSubSystemInfo findByStandardCode(String StandardCode);
	public Long countRecordByStandardCode(String standardCode);
	public int deleteById(Long id);
	public int deleteByIds(Long[] ids);
	public List<WaterSubSystemInfo> selecCategories();
	public List<String> selectChilrenStandardCodes(String standardCode);
	List<WaterSubSystemInfo> findByCon(WaterSubSystemInfo ssi);
	List<WaterSubSystemInfo> findByConWater(WaterSubSystemInfo ssi);
	public List<WaterSubSystemInfo> findAll();
	public List<WaterSubSystemInfo> findChildren(String standardCode);

}
