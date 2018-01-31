package com.supconit.nhgl.basic.deviceMeter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.basic.deviceMeter.dao.DeviceMeterDao;
import com.supconit.nhgl.basic.deviceMeter.entities.DeviceMeter;
import com.supconit.nhgl.basic.deviceMeter.service.DeviceMeterService;

import hc.base.domains.Pageable;
import hc.orm.AbstractBasicOrmService;



@Service("DeviceMeterService")
public class DeviceMeterServiceImpl extends AbstractBasicOrmService<DeviceMeter,Long>implements DeviceMeterService {

	@Autowired
	private DeviceMeterDao	deviceMeterDao;
	


	@Override
	public void delete(DeviceMeter entity) {
		deviceMeterDao.delete(entity);
	}


	@Override
	public Pageable<DeviceMeter> findByCondition(
			Pageable<DeviceMeter> pager, DeviceMeter condition) {
		return deviceMeterDao.findByCondition(pager, condition);
	}


	@Override
	public DeviceMeter getById(Long id) {
		return deviceMeterDao.getById(id);
	}


	@Override
	public void insert(DeviceMeter entity) {
		deviceMeterDao.insert(entity);
	}


	@Override
	public void update(DeviceMeter entity) {
		deviceMeterDao.update(entity);
	}


	@Override
	public List<DeviceMeter> findDmByCondition(DeviceMeter dm) {
		return deviceMeterDao.findDmByCondition(dm);
	}


	@Override
	public void deleteByDeviceId(DeviceMeter dm) {
		deviceMeterDao.deleteByDeviceId(dm);
	}


	@Override
	public void insertList(List<DeviceMeter> dmList) {
		deviceMeterDao.insertList(dmList);
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

