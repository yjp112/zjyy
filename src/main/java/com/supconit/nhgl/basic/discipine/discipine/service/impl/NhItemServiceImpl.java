package com.supconit.nhgl.basic.discipine.discipine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.nhgl.basic.discipine.discipine.dao.NhItemDao;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;



@Service
public class NhItemServiceImpl extends AbstractBasicOrmService<NhItem,Long>implements NhItemService {

	@Autowired
	private NhItemDao	subSystemInfoDao;
	
	@Override
	public NhItem getById(Long arg0) {
		return subSystemInfoDao.getById(arg0);
	}


	@Override
	public void insert(NhItem entity) {
		subSystemInfoDao.insert(entity);
	}


	@Override
	public void update(NhItem entity) {
		subSystemInfoDao.update(entity);

	}


	@Override
	public void delete(NhItem entity) {
		subSystemInfoDao.delete(entity);
	}


	@Override
	public Pageable<NhItem> findByCondition(
			Pageable<NhItem> pager, NhItem condition) {
		if(condition.getParentCode()!=null){
			List<String> standardCodes=subSystemInfoDao.selectChilrenStandardCodes(condition.getParentCode());
			condition.setParentCode(new String());
			condition.setStandardCodes(standardCodes);
		}
		return subSystemInfoDao.findByCondition(pager, condition);
	}


	@Override
	public NhItem findById(Long id) {
		return subSystemInfoDao.findById(id);
	}

	@Override
	public int deleteById(Long id) {
		NhItem nhitem=subSystemInfoDao.findById(id);
		//校验该分项下是否有自分项
		List<NhItem> nhItemList=subSystemInfoDao.findChildrenByCode(nhitem.getStandardCode(), nhitem.getNhType());
		if(nhItemList.size()>1){
			throw new BusinessDoneException("该分项下存在子分项,不可以删除");
		}
		return subSystemInfoDao.deleteByIds(new Long[]{id});
	}


	@Override
	public int deleteByIds(Long[] ids){
		return subSystemInfoDao.deleteByIds(ids);
	}



	@Override
	public List<NhItem> selectCategories(Integer nhType) {
		return subSystemInfoDao.selecCategories(nhType);
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
		return subSystemInfoDao.findByCon(condition);
	}


	@Override
	public NhItem findByStandardCode(String standardCode,Integer nhType) {
		return subSystemInfoDao.findByStandardCode(standardCode,nhType);
	}


	@Override
	public List<NhItem> findAll() {
		return subSystemInfoDao.findAll();
	}
	//格式化树型结构
//	@Override
//	public String buildTree(List<SubSystemInfo> slist) {
//		
//		String json = null;
////		json = cach.get("treeJson");
////		if(null != json) return json;
//		List<ZTreeNode> subTree = new ArrayList<ZTreeNode>();
//		ZTreeNode zt = null;
//		//目录
//		for(SubSystemInfo subinfo:slist)
//		{
//			zt = new ZTreeNode();
//			zt.setPId(subinfo.getParentId().toString());
//			zt.setId(subinfo.getStandardCode().toString());
//			zt.setName(subinfo.getName() + "  " + subinfo.getDayofElectricTotal());
//			zt.setOpen(false);
//			zt.setNocheck(true);
//			subTree.add(zt);
//		}
//		json = JSON.toJSONString(subTree);
////		cach.put("treeJson", json);
//		return json;
//	}


	@Override
	public List<NhItem> findChildren(String standardCode,Integer nhType) {
		NhItem sub=subSystemInfoDao.findByStandardCode(standardCode,nhType);
		String parentId=sub.getParentCode();
		List<NhItem> subList=subSystemInfoDao.findChildren(standardCode);
		for(NhItem s:subList){
			if(s.getParentCode().equals(parentId)){
				subList.removeAll(subList);
				break;
			}
		}
		return subList;
	}


	@Override
	public List<NhItem> findParents() {
		return subSystemInfoDao.findParents();
	}


	@Override
	public List<NhItem> findByConEle(NhItem ssi) {
		return subSystemInfoDao.findByConEle(ssi);
	}


	@Override
	public List<NhItem> getTreeByItemCode(String itemCode) {
		return subSystemInfoDao.getTreeByItemCode(itemCode);
	}





	@Override
	public List<NhItem> findChildrenByCode(String standardCode, Integer nhType) {
		// TODO Auto-generated method stub
		return subSystemInfoDao.findChildrenByCode(standardCode,nhType); 
	}
	
}

