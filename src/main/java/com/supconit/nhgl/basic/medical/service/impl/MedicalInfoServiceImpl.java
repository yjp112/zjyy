package com.supconit.nhgl.basic.medical.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.basic.medical.dao.MedicalInfoDao;
import com.supconit.nhgl.basic.medical.entities.MedicalInfo;
import com.supconit.nhgl.basic.medical.service.MedicalInfoService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;


@Service("medicalInfoService")
public class MedicalInfoServiceImpl extends AbstractBasicOrmService<MedicalInfo,Long>implements MedicalInfoService {

	@Autowired
	private MedicalInfoDao	medicalInfoDao;
	


	@Override
	public void delete(MedicalInfo entity) {
		medicalInfoDao.delete(entity);
	}


	@Override
	public Pageable<MedicalInfo> findByCondition(
			Pageable<MedicalInfo> pager, MedicalInfo condition) {
		return medicalInfoDao.findByCondition(pager, condition);
	}


	@Override
	public MedicalInfo getById(Long id) {
		return medicalInfoDao.getById(id);
	}


	@Override
	public void insert(MedicalInfo entity) {
		medicalInfoDao.insert(entity);
	}


	@Override
	public void update(MedicalInfo entity) {
		medicalInfoDao.update(entity);
	}


	@Override
	public List<MedicalInfo> findByMonthKey(MedicalInfo minfo) {
		return medicalInfoDao.findByMonthKey(minfo);
	}


	@Override
	public void deleteByIds(Long[] ids) {
		medicalInfoDao.deleteByIds(ids);		
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


	
}

