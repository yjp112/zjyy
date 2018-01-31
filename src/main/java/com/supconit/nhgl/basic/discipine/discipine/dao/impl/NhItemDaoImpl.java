package com.supconit.nhgl.basic.discipine.discipine.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.basic.discipine.discipine.dao.NhItemDao;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicDaoImpl;



@Repository
public class NhItemDaoImpl extends AbstractBasicDaoImpl<NhItem,Long> implements NhItemDao {
	private static final String NAMESPACE=NhItem.class.getName();
	
	@Override
	public Pageable<NhItem> findByCondition(
			Pageable<NhItem> pager, NhItem condition) {
		return findByPager(pager,"findByCondition","countByCondition",condition);
	}
	@Override
	public NhItem findById(Long id) {
		return selectOne("findById",id);
	}
	@Override
	public Long countRecordByStandardCode(String standardCode) {
		NhItem condition=new NhItem();
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
	public List<NhItem> selecCategories(Integer nhType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nhType", nhType);
		return selectList("findAll",map);
	}
	/**
	 * 
	 * @方法名: findByCon
	 * @创建日期: 2014-5-7
	 * @开发人员:高文龙
	 * @描述:获取对应的分项
	 */
	@Override
	public List<NhItem> findByCon(NhItem condition) {
		return selectList("findByCon", condition);
	}
	@Override
	public List<String> selectChilrenStandardCodes(String standardCode) {
		NhItem condition=new NhItem();
		condition.setStandardCode(standardCode);
		return selectList("selectStandardCodesNh",condition);
	}
	@Override
	public NhItem findByStandardCode(String standardCode,Integer nhType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("standardCode", standardCode);
		map.put("nhType", nhType);
		return selectOne("findByStandardCode",map);
	}
	@Override
	public List<NhItem> findAll() {
		return selectList("findAll");
	}
	@Override
	public List<NhItem> findChildren(String standardCode) {
		return selectList("findChild",standardCode);
	}
	@Override
	public List<NhItem> findParents() {
		return selectList("findParents");
	}
	@Override
	public List<NhItem> findByConEle(NhItem ssi) {
		return selectList("findByConEle", ssi);
	}
	@Override
	public List<NhItem> getTreeByItemCode(String itemCode) {
		return selectList("getTreeByItemCode", itemCode);
	}
	@Override
	public List<NhItem> findAllById(String code) {
		// TODO Auto-generated method stub
		return selectList("findAllById",code);
	}
	@Override
	public List<Long> findIdsByCode(String code) {
		// TODO Auto-generated method stub
		return selectList("findIdsByCode",code);
	}
	@Override
	public List<NhItem> findChildrenByCode(String standardCode, Integer nhType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("standardCode", standardCode);
		map.put("nhType", nhType);
		return selectList("findChildrenByCode",map);
	}
}