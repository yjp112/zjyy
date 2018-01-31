package com.supconit.nhgl.basic.discipine.discipine.dao;

import java.util.List;

import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;

import hc.base.domains.Pageable;
import hc.orm.BasicDao;


public interface NhItemDao extends BasicDao<NhItem,Long>{
	Pageable<NhItem> findByCondition(Pageable<NhItem> pager,NhItem condition);
	public NhItem findById(Long id);
	public NhItem findByStandardCode(String StandardCode,Integer nhType);
	public Long countRecordByStandardCode(String standardCode);
	public int deleteById(Long id);
	public int deleteByIds(Long[] ids);
	public List<NhItem> selecCategories(Integer nhType);
	public List<String> selectChilrenStandardCodes(String standardCode);
	List<NhItem> findByCon(NhItem condition);
	public List<NhItem> findAll();
	public List<NhItem> findChildren(String standardCode);
	public List<NhItem> findParents();
	List<NhItem> findByConEle(NhItem ssi);
	List<NhItem> getTreeByItemCode(String itemCode);
	List<NhItem> findAllById(String code);
	List<Long> findIdsByCode(String code);
	List<NhItem> findChildrenByCode(String standardCode, Integer nhType);   
}
