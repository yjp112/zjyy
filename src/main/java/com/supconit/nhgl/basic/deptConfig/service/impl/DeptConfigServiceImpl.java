package com.supconit.nhgl.basic.deptConfig.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.UtilTool;
import com.supconit.nhgl.basic.deptConfig.dao.DeptConfigDao;
import com.supconit.nhgl.basic.deptConfig.entities.DeptConfig;
import com.supconit.nhgl.basic.deptConfig.service.DeptConfigService;

import hc.base.domains.Pageable;
import jodd.util.StringUtil;
@Service
public class DeptConfigServiceImpl extends AbstractBaseBusinessService<DeptConfig,Long> implements DeptConfigService{

	@Autowired
	private DeptConfigDao deptConfigDao;
	
	
	@Override
	public DeptConfig getById(Long arg0) {
		return deptConfigDao.getById(arg0);
	}

	@Override
	public void delete(DeptConfig arg0) {
		deptConfigDao.delete(arg0);
	}

	@Override
	public void insert(DeptConfig deptConfig) {
		if(deptConfig.getIsSum().equals(1)){
			deptConfigDao.insert(deptConfig);
		}else{
			if(deptConfig.getSubLeftConfigList()==null&&deptConfig.getSubRightConfigList()==null){
				throw new BusinessDoneException("请为该部门配置设备。");
			}
			if(deptConfig.getSubLeftConfigList()!=null){
				for(DeptConfig deptSubConfig:deptConfig.getSubLeftConfigList()){
					if(!StringUtil.isEmpty(deptSubConfig.getExtended1())){//去空
						init(deptSubConfig,deptConfig);
						deptConfigDao.insert(deptSubConfig);
					}
				}
			}
			if(deptConfig.getSubRightConfigList()!=null){
				for(DeptConfig areaSubConfig:deptConfig.getSubRightConfigList()){
					if(!StringUtil.isEmpty(areaSubConfig.getExtended1())){//去空
						init(areaSubConfig,deptConfig);
						deptConfigDao.insert(areaSubConfig);
					}
				}
			}
		}
		
	}
	//设置附带数据
		private void init(DeptConfig deptSubConfig, DeptConfig deptConfig) { 
			deptSubConfig.setDeptId(deptConfig.getDeptId()); 
			deptSubConfig.setIsSum(deptConfig.getIsSum());
			deptSubConfig.setNhType(deptConfig.getNhType());
			//areaSubConfig.setDescription(areaConfig.getDescription());
			deptSubConfig.setMemo(deptConfig.getMemo());
			deptSubConfig.setDeviceId(deptSubConfig.getId()); 
			deptSubConfig.setBitNo(deptSubConfig.getExtended1()); 
			deptSubConfig.setId(deptConfig.getId());
		}
		@Override
		public void update(DeptConfig deptConfig) {
			deptConfigDao.deleteByDeptId(deptConfig.getDeptId(),deptConfig.getNhType().toString());
			insert(deptConfig);
		}

	@Override
	public Pageable<DeptConfig> findByCondition(Pageable<DeptConfig> pager,
			DeptConfig condition) {
		return deptConfigDao.findByCondition(pager, condition);
	}

	@Override
	public List<DeptConfig> findByDeptIdAndRule(Long deptId,Integer ruleFlag,Integer nhtype) {
		return deptConfigDao.findByDeptIdAndRule(deptId, ruleFlag,nhtype);
	}

	@Override
	public List<Long> findDeptIds(String nhtype) {
		return deptConfigDao.findDeptIds(nhtype);
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public void deleteByIds(Long[] ids) {
		deptConfigDao.deleteByIds(ids);
	}

	@Override
	public void deleteByDeptId(Long deptId,String nhtype) {
		deptConfigDao.deleteByDeptId(deptId,nhtype);
	}

	@Override
	@Transactional
	public void insertList(List<DeptConfig> deptConfiglst) {
		Set<Long> d_ids = new HashSet<Long>();
		Set<Long> w_ids = new HashSet<Long>();
		Set<Long> s_ids = new HashSet<Long>();
		Set<Long> e_ids = new HashSet<Long>();
		for (int i = 0; i < deptConfiglst.size(); i++) {
			DeptConfig d = deptConfiglst.get(i);
			if(d.getNhType().equals(1)){//电
				d_ids.add(d.getDeptId());
			}else if(d.getNhType().equals(2)){//水
				w_ids.add(d.getDeptId());
			}else if(d.getNhType().equals(3)){//蒸气
				s_ids.add(d.getDeptId());
			}else{//能量
				e_ids.add(d.getDeptId());
			}
		}
		if(d_ids.size() > 0) deptConfigDao.deleteByDeptIdAndNhtype(d_ids, "1");
		if(w_ids.size() > 0) deptConfigDao.deleteByDeptIdAndNhtype(w_ids, "2");
		if(s_ids.size() > 0) deptConfigDao.deleteByDeptIdAndNhtype(s_ids, "3");
		if(e_ids.size() > 0) deptConfigDao.deleteByDeptIdAndNhtype(e_ids, "4");
		for(DeptConfig d:deptConfiglst){
			deptConfigDao.insert(d);
		}
		
	}

	@Override
	public void updateList(List<DeptConfig> deptConfiglst, String nhType) {
		deptConfigDao.deleteByDeptId(deptConfiglst.get(0).getDeptId(), nhType);
		for(DeptConfig d:deptConfiglst){
			deptConfigDao.insert(d);
		}
	}

	@Override
	public int countFindAll(DeptConfig deptConfig) {
		return deptConfigDao.countFindAll(deptConfig);
	}

	@Override
	public DeptConfig findById(Long id, Integer nhtype) {
		return deptConfigDao.findById(id, nhtype);
	}

	@Override
	public Long getByDeptId(Long id) {
		// TODO Auto-generated method stub
		return deptConfigDao.getByDeptId(id);
	}

	@Override
	public List<DeptConfig> setDeviceId(List<String> lstErrMsg,List<DeptConfig> lstDeptConfig) {
		Map<String, Long> mapDept = new HashMap<String, Long>();
		Map<String, Long> mapDevice = new HashMap<String, Long>();
		List<DeptConfig> lstDept = deptConfigDao.findAllDept();
		List<DeptConfig> lstDevice = deptConfigDao.findAllDevicesWithOutRoot();
		
		for (DeptConfig dept : lstDept) {//部门
			mapDept.put(dept.getDeptCode(),dept.getDeptId());
		}
		for (DeptConfig device : lstDevice) {//设备
			mapDevice.put(device.getHpId(), device.getDeviceId());
		}
		//bitNo等于HPID
		Iterator<DeptConfig> it = lstDeptConfig.iterator();
		while(it.hasNext()){
			DeptConfig deptConfig = it.next();
			if(null == deptConfig || (null == deptConfig.getDeptCode() 
					&& null == deptConfig.getBitNo())){
				it.remove();
				continue;
			}
			Long pid = mapDept.get(deptConfig.getDeptCode());//部门ID
			if(null == pid){
				lstErrMsg.add(deptConfig.getNhTypeValue()+"_"+"部门编码:‘"+deptConfig.getDeptCode()+"’不存在");
			}else{
				deptConfig.setDeptId(pid);
			}
			Integer isSum = deptConfig.getIsSum();
			if(null!=isSum && isSum.intValue()==0){//否
				String bitNo = deptConfig.getBitNo();
				Long did = mapDevice.get(bitNo);//设备ID
				if(null == did){
					if(StringUtils.isNotEmpty(bitNo)) lstErrMsg.add(deptConfig.getNhTypeValue()+"_"+"表位号:‘"+bitNo+"’不存在");
				}else{
					deptConfig.setDeviceId(did);
				}
			}
		}
		List<DeptConfig> deptConfigList = new ArrayList<DeptConfig>();
		if(UtilTool.isEmptyList(lstErrMsg)){
			for (DeptConfig deptConfig : lstDeptConfig) {
				if(deptConfig.getIsSum().intValue()==1){
					deptConfigList.add(deptConfig);
				}else{
					if(!deptConfigList.contains(deptConfig)){
						deptConfigList.add(deptConfig);
					}else{
						lstErrMsg.add(deptConfig.getNhTypeValue()+"_"+"部门编码:‘"+deptConfig.getDeptCode()+"’和表位号:‘"+deptConfig.getBitNo()+"’重复配置");
					}
				}
			}
		}
		return deptConfigList;
	}
	public Pageable<DeptConfig> findUnusedDevice(Pageable<DeptConfig> pager,
			DeptConfig condition) {
		return deptConfigDao.findUnusedDevice(pager, condition);
	}


	@Override
	public Pageable<DeptConfig> findRepeatDevice(Pageable<DeptConfig> pager,
			DeptConfig condition) {
		return deptConfigDao.findRepeatDevice(pager, condition);
	}

	@Override
	public Pageable<DeptConfig> findErrorFtxs(Pageable<DeptConfig> pager,
			DeptConfig condition) {
		return deptConfigDao.findErrorFtxs(pager, condition);
	}

	@Override
	public List<DeptConfig> findErrorIsSum(String nhType) {
		return deptConfigDao.findErrorIsSum(nhType);
	}

	@Override
	public List<DeptConfig> findAllDeptConfig(DeptConfig condition) {
		return deptConfigDao.findAllDeptConfig(condition);
	}

	@Override
	public Map<String, Integer> findDevicesMapWithOutRoot() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		List<DeptConfig> list = deptConfigDao.findDevicesMapWithOutRoot();
		for (DeptConfig deptConfig : list) {
			map.put(deptConfig.getHpId(), deptConfig.getNhType());
		}
		return map;
	}

	@Override
	public Map<Long,String> findAllDept() {
		Map<Long,String> map = new HashMap<Long,String>();
		List<DeptConfig> list = deptConfigDao.findAllDept();
		for (DeptConfig deptConfig : list) {
			map.put(deptConfig.getDeptId(), deptConfig.getDeptName());
		}
		return map;
	}



}
