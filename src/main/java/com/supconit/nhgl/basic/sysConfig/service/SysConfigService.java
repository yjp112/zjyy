package com.supconit.nhgl.basic.sysConfig.service;

import java.util.List;

import com.supconit.base.entities.Device;

import hc.base.domains.Pageable;
import hc.orm.BasicOrmService;



public interface SysConfigService extends BasicOrmService<Device,Long>{
	Pageable<Device> findByConditon(Pageable<Device> pager,Device condition);
	Pageable<Device> findByConditonPower(Pageable<Device> pager,Device condition);
	List<Device> findById(Long id);
	public void update(List<Device> lst);
	List<Device> findListByCondition(Device condition);
	Pageable<Device> findByAllConditon(Pageable<Device> pager, Device condition);
	Pageable<Device> findDeptByEnergy(Pageable<Device> pager, Device condition);
	Pageable<Device> findDeptByGas(Pageable<Device> pager, Device condition);
	Pageable<Device> findDeptByWater(Pageable<Device> pager, Device condition);
	Pageable<Device> findDeptByElectric(Pageable<Device> pager, Device condition);
}
