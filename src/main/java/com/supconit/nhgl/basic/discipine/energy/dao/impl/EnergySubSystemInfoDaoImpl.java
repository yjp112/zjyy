package com.supconit.nhgl.basic.discipine.energy.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.discipine.energy.dao.EnergySubSystemInfoDao;
import com.supconit.nhgl.basic.discipine.energy.entities.EnergySubSystemInfo;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class EnergySubSystemInfoDaoImpl extends AbstractBasicDaoImpl<EnergySubSystemInfo,Long> implements EnergySubSystemInfoDao{
	private static final String NAMESPACE=EnergySubSystemInfo.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public Pageable<EnergySubSystemInfo> findByCondition(
			Pageable<EnergySubSystemInfo> pager, EnergySubSystemInfo condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}

	@Override
	public EnergySubSystemInfo findById(Long id) {
		return selectOne("findById",id);
	}



	@Override
	public int deleteById(Long id) {
		return delete("deleteById",id);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids",ids);
		return update("deleteByIds", map);
	}




	@Override
	public List<EnergySubSystemInfo> findAll() {
		return selectList("findAll");
	}

	@Override
	public List<EnergySubSystemInfo> selecCategories() {
		return selectList("findAll");
	}

	@Override
	public EnergySubSystemInfo findByStandardCode(String StandardCode) {
		return selectOne("findByStandardCode",StandardCode);
	}

	@Override
	public List<EnergySubSystemInfo> findChildren(String standardCode) {
		return selectList("findChild",standardCode);
	}

	@Override
	public List<EnergySubSystemInfo> findByCon(EnergySubSystemInfo ssi) {
		return selectList("findByCon", ssi);
	}

	@Override
	public List<EnergySubSystemInfo> findByConEnergy(EnergySubSystemInfo ssi) {
		return selectList("findByConEnergy", ssi);
	}

	@Override
	public List<String> selectChilrenStandardCodes(String standardCode) {
		EnergySubSystemInfo condition=new EnergySubSystemInfo();
		condition.setStandardCode(standardCode);
		return selectList("selectStandardCodes",condition);
	}


}
