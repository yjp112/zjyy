package com.supconit.nhgl.basic.discipine.gas.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.discipine.gas.dao.GasSubSystemInfoDao;
import com.supconit.nhgl.basic.discipine.gas.entities.GasSubSystemInfo;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;

@Repository
public class GasSubSystemInfoDaoImpl extends AbstractBasicDaoImpl<GasSubSystemInfo,Long> implements GasSubSystemInfoDao{
	private static final String NAMESPACE=GasSubSystemInfo.class.getName();
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public Pageable<GasSubSystemInfo> findByCondition(
			Pageable<GasSubSystemInfo> pager, GasSubSystemInfo condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}

	@Override
	public GasSubSystemInfo findById(Long id) {
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
	public List<GasSubSystemInfo> findAll() {
		return selectList("findAll");
	}

	@Override
	public List<GasSubSystemInfo> selecCategories() {
		return selectList("findAll");
	}

	@Override
	public GasSubSystemInfo findByStandardCode(String StandardCode) {
		return selectOne("findByStandardCode",StandardCode);
	}

	@Override
	public List<GasSubSystemInfo> findChildren(String standardCode) {
		return selectList("findChild",standardCode);
	}

	@Override
	public List<GasSubSystemInfo> findByCon(GasSubSystemInfo gssi) {
		return selectList("findByCon",gssi);
	}

	@Override
	public List<GasSubSystemInfo> findByConGas(GasSubSystemInfo gssi) {
		return selectList("findByConGas",gssi);
	}

	@Override
	public List<String> selectChilrenStandardCodes(String standardCode) {
		GasSubSystemInfo condition =new GasSubSystemInfo();
		condition.setStandardCode(standardCode);
		return selectList("selectStandardCodes",condition);
	}


}
