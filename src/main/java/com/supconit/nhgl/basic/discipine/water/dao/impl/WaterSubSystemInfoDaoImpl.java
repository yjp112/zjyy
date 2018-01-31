package com.supconit.nhgl.basic.discipine.water.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.discipine.water.dao.WaterSubSystemInfoDao;
import com.supconit.nhgl.basic.discipine.water.entities.WaterSubSystemInfo;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;
@Repository
public class WaterSubSystemInfoDaoImpl extends AbstractBasicDaoImpl<WaterSubSystemInfo,Long> implements WaterSubSystemInfoDao {
	private static final String NAMESPACE=WaterSubSystemInfo.class.getName();
	
	@Override
	public Pageable<WaterSubSystemInfo> findByCondition(
			Pageable<WaterSubSystemInfo> pager, WaterSubSystemInfo condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}
	@Override
	public WaterSubSystemInfo findById(Long id) {
		return selectOne("findById",id);
	}
	@Override
	public Long countRecordByStandardCode(String standardCode) {
		WaterSubSystemInfo condition=new WaterSubSystemInfo();
		condition.setStandardCode(standardCode);
		return getSqlSession().selectOne(NAMESPACE+".countRecordByStandardCode",condition);
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
	protected String getNamespace() {
		return NAMESPACE;
	}
	@Override
	public List<WaterSubSystemInfo> selecCategories() {
		return selectList("findAll");
	}
	/**
	 * 
	 * @方法名: findByCon
	 * @创建日期: 2014-5-7
	 * @开发人员:高文龙
	 * @描述:获取对应的分项
	 */
	@Override
	public List<WaterSubSystemInfo> findByCon(WaterSubSystemInfo ssi) {
		return selectList("findByCon", ssi);
	}
	@Override
	public List<String> selectChilrenStandardCodes(String standardCode) {
		WaterSubSystemInfo condition=new WaterSubSystemInfo();
		condition.setStandardCode(standardCode);
		return selectList("selectStandardCodes",condition);
	}
	@Override
	public WaterSubSystemInfo findByStandardCode(String standardCode) {
		return selectOne("findByStandardCode",standardCode);
	}
	@Override
	public List<WaterSubSystemInfo> findAll() {
		return selectList("findAll");
	}
	@Override
	public List<WaterSubSystemInfo> findChildren(String standardCode) {
		return selectList("findChild",standardCode);
	}
	@Override
	public List<WaterSubSystemInfo> findByConWater(WaterSubSystemInfo ssi) {
		return selectList("findByConWater",ssi);
	}

}
