package com.supconit.nhgl.basic.discipine.gas.dao;

import java.util.List;

import com.supconit.nhgl.basic.discipine.gas.entities.GasSubSystemInfo;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;


public interface GasSubSystemInfoDao extends BasicDao<GasSubSystemInfo,Long>{
	Pageable<GasSubSystemInfo> findByCondition(Pageable<GasSubSystemInfo> pager,GasSubSystemInfo condition);
	public GasSubSystemInfo findById(Long id);
	public int deleteById(Long id);
	public int deleteByIds(Long[] ids);
	public List<GasSubSystemInfo> findAll();
	public List<GasSubSystemInfo> selecCategories();
	public GasSubSystemInfo findByStandardCode(String StandardCode);
	public List<GasSubSystemInfo> findChildren(String standardCode);
	List<GasSubSystemInfo> findByCon(GasSubSystemInfo gssi);
	List<GasSubSystemInfo> findByConGas(GasSubSystemInfo gssi);
	
	public List<String> selectChilrenStandardCodes(String standardCode);
}
