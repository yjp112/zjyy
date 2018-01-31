package com.supconit.nhgl.basic.electricDept.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.basic.electricDept.dao.ElectricDeptDao;
import com.supconit.nhgl.basic.electricDept.entities.ElectricDept;
import com.supconit.nhgl.basic.electricDept.service.ElectricDeptService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;



@Service("ElectricDeptService")
public class ElectricDeptServiceImpl extends AbstractBasicOrmService<ElectricDept,Long>implements ElectricDeptService {

	@Autowired
	private ElectricDeptDao	deviceMeterDao;
	


	@Override
	public void delete(ElectricDept entity) {
		deviceMeterDao.delete(entity);
	}


	@Override
	public Pageable<ElectricDept> findByCondition(
			Pageable<ElectricDept> pager, ElectricDept condition) {
		return deviceMeterDao.findByCondition(pager, condition);
	}


	@Override
	public ElectricDept getById(Long id) {
		return deviceMeterDao.getById(id);
	}



	@Override
	public void update(ElectricDept entity) {
		deviceMeterDao.update(entity);
	}


	@Override
	public List<ElectricDept> findDmByCondition(ElectricDept dm) {
		return deviceMeterDao.findDmByCondition(dm);
	}



	@Override
	public void insert(ElectricDept dm) {
		deviceMeterDao.insert(dm);
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

