package com.supconit.nhgl.basic.discipine.water.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.basic.discipine.water.dao.WaterSubSystemInfoDao;
import com.supconit.nhgl.basic.discipine.water.entities.WaterSubSystemInfo;
import com.supconit.nhgl.basic.discipine.water.service.WaterSubSystemInfoService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;

@Service
public class WaterSubSystemInfoServiceImpl extends AbstractBasicOrmService<WaterSubSystemInfo,Long>implements WaterSubSystemInfoService {

	@Autowired
	private WaterSubSystemInfoDao	waterSubSystemInfoDao;
	
	@Override
	public WaterSubSystemInfo getById(Long arg0) {
		return waterSubSystemInfoDao.getById(arg0);
	}


	@Override
	public void insert(WaterSubSystemInfo entity) {
		waterSubSystemInfoDao.insert(entity);
	}


	@Override
	public void update(WaterSubSystemInfo entity) {
		waterSubSystemInfoDao.update(entity);

	}


	@Override
	public void delete(WaterSubSystemInfo entity) {
		waterSubSystemInfoDao.delete(entity);
	}


	@Override
	public Pageable<WaterSubSystemInfo> findByCondition(
			Pageable<WaterSubSystemInfo> pager, WaterSubSystemInfo condition) {
		if(condition.getParentId()!=null){
			List<String> standardCodes=waterSubSystemInfoDao.selectChilrenStandardCodes(condition.getParentId());
			condition.setParentId(new String());
			condition.setStandardCodes(standardCodes);
		}
		return waterSubSystemInfoDao.findByCondition(pager, condition);
	}


	@Override
	public WaterSubSystemInfo findById(Long id) {
		return waterSubSystemInfoDao.findById(id);
	}


	@Override
	public int deleteById(Long id) {
		return waterSubSystemInfoDao.deleteByIds(new Long[]{id});
	}


	@Override
	public int deleteByIds(Long[] ids) {
		return waterSubSystemInfoDao.deleteByIds(ids);
	}



	@Override
	public List<WaterSubSystemInfo> selectCategories() {
		return waterSubSystemInfoDao.selecCategories();
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
		return waterSubSystemInfoDao.findByCon(ssi);
	}


	@Override
	public WaterSubSystemInfo findByStandardCode(String standardCode) {
		return waterSubSystemInfoDao.findByStandardCode(standardCode);
	}


	@Override
	public List<WaterSubSystemInfo> findAll() {
		return waterSubSystemInfoDao.findAll();
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
	public List<WaterSubSystemInfo> findChildren(String standardCode) {
		WaterSubSystemInfo sub=waterSubSystemInfoDao.findByStandardCode(standardCode);
		String parentId=sub.getParentId();
		List<WaterSubSystemInfo> subList=waterSubSystemInfoDao.findChildren(standardCode);
		for(WaterSubSystemInfo s:subList){
			if(s.getParentId().equals(parentId)){
				subList.removeAll(subList);
				break;
			}
		}
		return subList;
	}


	@Override
	public List<WaterSubSystemInfo> findByConWater(WaterSubSystemInfo ssi) {
		return waterSubSystemInfoDao.findByConWater(ssi);
	}

}
