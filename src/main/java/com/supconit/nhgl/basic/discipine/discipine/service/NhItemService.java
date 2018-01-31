package com.supconit.nhgl.basic.discipine.discipine.service;

import java.util.List;

import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;


public interface NhItemService extends BasicOrmService<NhItem,Long>{
	Pageable<NhItem> findByCondition(Pageable<NhItem> pager,NhItem condition);
	public NhItem findById(Long id);
	public NhItem findByStandardCode(String standardCode,Integer nhType);
	public int deleteById(Long id);
	public int deleteByIds(Long[] ids);
	public List<NhItem> selectCategories(Integer nhType);
	List<NhItem> findByCon(NhItem condition);
	List<NhItem> findByConEle(NhItem ssi);
	public List<NhItem> findAll();
	public List<NhItem> findChildren(String standardCode,Integer nhType);
	public List<NhItem> findParents();
//	String buildTree(List<SubSystemInfo> slist);
	/**
	 * 通过分项编码查询该分项及下级分项
	 * @param itemCode
	 * @return
	 */
	List<NhItem> getTreeByItemCode(String itemCode);
	//获取所有子分项的id包括自己
	List<NhItem> findChildrenByCode(String standardCode, Integer nhType); 
}
