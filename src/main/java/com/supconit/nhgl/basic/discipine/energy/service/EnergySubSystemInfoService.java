package com.supconit.nhgl.basic.discipine.energy.service;

import java.util.List;

import com.supconit.nhgl.basic.discipine.energy.entities.EnergySubSystemInfo;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;

public interface EnergySubSystemInfoService extends BasicOrmService<EnergySubSystemInfo,Long>{
	Pageable<EnergySubSystemInfo> findByCondition(Pageable<EnergySubSystemInfo> pager,EnergySubSystemInfo condition);
	public EnergySubSystemInfo findById(Long id);
	public int deleteById(Long id);
	public int deleteByIds(Long[] ids);
	public List<EnergySubSystemInfo> findAll();
	public List<EnergySubSystemInfo> selecCategories();
	public EnergySubSystemInfo findByStandardCode(String StandardCode);
	public List<EnergySubSystemInfo> findChildren(String standardCode);
	List<EnergySubSystemInfo> findByCon(EnergySubSystemInfo ssi);
	List<EnergySubSystemInfo> findByConEnergy(EnergySubSystemInfo ssi);
}
