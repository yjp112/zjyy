package com.supconit.nhgl.basic.areaConfig.service.impl;

import hc.base.domains.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import jodd.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.UtilTool;
import com.supconit.nhgl.basic.areaConfig.dao.AreaConfigDao;
import com.supconit.nhgl.basic.areaConfig.entities.AreaConfig;
import com.supconit.nhgl.basic.areaConfig.entities.ExportAreaConfig;
import com.supconit.nhgl.basic.areaConfig.service.AreaConfigService;

@Service
public class AreaConfigServiceImpl extends AbstractBaseBusinessService<AreaConfig,Long> implements AreaConfigService{

	@Autowired
	private AreaConfigDao areaConfigDao;
	
	
	@Override
	public AreaConfig getById(Long arg0) {
		return areaConfigDao.getById(arg0);
	}


	@Override
	public void insert(AreaConfig areaConfig) {
		
		if(areaConfig.getIsSum().equals(1)){
			areaConfigDao.insert(areaConfig);
		}else{
			if(areaConfig.getSubLeftConfigList()==null&&areaConfig.getSubRightConfigList()==null){
				throw new BusinessDoneException("请为该区域配置设备。");
			}
			if(areaConfig.getSubLeftConfigList()!=null){
				for(AreaConfig areaSubConfig:areaConfig.getSubLeftConfigList()){
					if(!StringUtil.isEmpty(areaSubConfig.getExtended1())){//去空
						init(areaSubConfig,areaConfig);
						areaConfigDao.insert(areaSubConfig);
					}
				}
			}
			if(areaConfig.getSubRightConfigList()!=null){
				for(AreaConfig areaSubConfig:areaConfig.getSubRightConfigList()){
					if(!StringUtil.isEmpty(areaSubConfig.getExtended1())){//去空
						init(areaSubConfig,areaConfig);
						areaConfigDao.insert(areaSubConfig);
					}
				}
			}
		}
		
	}
	//设置附带数据
	private void init(AreaConfig areaSubConfig, AreaConfig areaConfig) { 
		areaSubConfig.setAreaId(areaConfig.getAreaId()); 
		areaSubConfig.setIsSum(areaConfig.getIsSum());
		areaSubConfig.setNhType(areaConfig.getNhType());
		//areaSubConfig.setDescription(areaConfig.getDescription());
		areaSubConfig.setMemo(areaConfig.getMemo());
		areaSubConfig.setDeviceId(areaSubConfig.getId()); 
		areaSubConfig.setBitNo(areaSubConfig.getExtended1()); 
		areaSubConfig.setId(areaConfig.getId());
	}

	@Override
	public void update(AreaConfig areaConfig) {
		areaConfigDao.deleteByAreaId(areaConfig.getAreaId(),areaConfig.getNhType().toString());
		insert(areaConfig);
	}

	@Override
	public Pageable<AreaConfig> findByCondition(Pageable<AreaConfig> pager,
			AreaConfig condition) {
		return areaConfigDao.findByCondition(pager, condition);
	}

	@Override
	public List<AreaConfig> findByAreaIdAndRule(AreaConfig areaConfig,Integer ruleFlag) {
		areaConfig.setRuleFlag(ruleFlag);
		return areaConfigDao.findByAreaIdAndRule(areaConfig);
	}


	@Override
	public void deleteByAreaId(Long areaId,String nhtype) {
		areaConfigDao.deleteByAreaId(areaId,nhtype);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int countFindAll(AreaConfig areaConfig) {
		// TODO Auto-generated method stub
		return areaConfigDao.countFindAll(areaConfig);
	}

	@Override
	public AreaConfig getByCondition(AreaConfig areaConfig) {
		// TODO Auto-generated method stub
		return areaConfigDao.getByCondition(areaConfig);
	}


	@Override
	@Transactional
	public List<AreaConfig> setAreaId(List<String> lstErrMsg, List<AreaConfig> lstAreaConfig) {
		Map<String, Long> mapArea = new HashMap<String, Long>();
		Map<String, Long> mapDevice = new HashMap<String, Long>();
		List<AreaConfig> lstArea = areaConfigDao.findAllArea();
		List<AreaConfig> lstDevice = areaConfigDao.findAllDevicesWithOutRoot();
		
		for (AreaConfig area : lstArea) {//区域
			mapArea.put(area.getAreaCode(),area.getAreaId());
		}
		for (AreaConfig device : lstDevice) {//设备
			mapDevice.put(device.getHpId(), device.getDeviceId());
		}
		//bitNo等于HPID
		Iterator<AreaConfig> it = lstAreaConfig.iterator();
		while(it.hasNext()){
			AreaConfig areaConfig = it.next();
			if(null == areaConfig || (null == areaConfig.getAreaCode() 
					&& null == areaConfig.getBitNo())){
				it.remove();
				continue;
			}
			Long pid = mapArea.get(areaConfig.getAreaCode());//区域ID
			if(null == pid){
				lstErrMsg.add(areaConfig.getNhTypeValue()+"_"+"区域编码:‘"+areaConfig.getAreaCode()+"’不存在");
			}else{
				areaConfig.setAreaId(pid);
			}
			Integer isSum = areaConfig.getIsSum();
			if(null!=isSum && isSum.intValue()==0){//否
				String bitNo = areaConfig.getBitNo();
				Long did = mapDevice.get(bitNo);//设备ID
				if(null == did){
					if(StringUtils.isNotEmpty(bitNo)) lstErrMsg.add(areaConfig.getNhTypeValue()+"_"+"表位号:‘"+bitNo+"’不存在");
				}else{
					areaConfig.setDeviceId(did);
				}
			}
		}
		List<AreaConfig> areaConfigList = new ArrayList<AreaConfig>();
		if(UtilTool.isEmptyList(lstErrMsg)){
			for (AreaConfig areaConfig : areaConfigList) {
				if(areaConfig.getIsSum().intValue()==1){
					areaConfigList.add(areaConfig);
				}else{
					if(!areaConfigList.contains(areaConfig)){
						areaConfigList.add(areaConfig);
					}else{
						lstErrMsg.add(areaConfig.getNhTypeValue()+"_"+"区域编码:‘"+areaConfig.getAreaCode()+"’和表位号:‘"+areaConfig.getBitNo()+"’重复配置");
					}
				}
			}
		}
		return areaConfigList;
	}


	@Override
	public void insertList(List<AreaConfig> areaConfiglst) {
		Set<Long> d_ids = new HashSet<Long>();
		Set<Long> w_ids = new HashSet<Long>();
		Set<Long> s_ids = new HashSet<Long>();
		Set<Long> e_ids = new HashSet<Long>();
		for(AreaConfig a:areaConfiglst){
			if(a.getNhType().equals(1)){//电
				d_ids.add(a.getAreaId());
			}else if(a.getNhType().equals(2)){//水
				w_ids.add(a.getAreaId());
			}else if(a.getNhType().equals(3)){//蒸气
				s_ids.add(a.getAreaId());
			}else{//能量
				e_ids.add(a.getAreaId());
			}
		}
		if(d_ids.size() > 0) areaConfigDao.deleteByAreaIdAndNhtype(d_ids, "1");
		if(w_ids.size() > 0) areaConfigDao.deleteByAreaIdAndNhtype(w_ids, "2");
		if(s_ids.size() > 0) areaConfigDao.deleteByAreaIdAndNhtype(s_ids, "3");
		if(e_ids.size() > 0) areaConfigDao.deleteByAreaIdAndNhtype(e_ids, "4");
		
		for (AreaConfig areaConfig : areaConfiglst) {
			areaConfigDao.insert(areaConfig);
		}
	}


	@Override
	public Pageable<AreaConfig> findUnusedDevice(Pageable<AreaConfig> pager,
			AreaConfig condition) {
		return areaConfigDao.findUnusedDevice(pager, condition);
	}


	@Override
	public Pageable<AreaConfig> findRepeatDevice(Pageable<AreaConfig> pager,
			AreaConfig condition) {
		return areaConfigDao.findRepeatDevice(pager, condition);
	}


	@Override
	public Pageable<AreaConfig> findErrorFtxs(Pageable<AreaConfig> pager,
			AreaConfig condition) {
		return areaConfigDao.findErrorFtxs(pager, condition);
	}


	@Override
	public List<AreaConfig> findErrorIsSum(String nhType) {
		return areaConfigDao.findErrorIsSum(nhType);
	}


	@Override
	public List<ExportAreaConfig> findAreaConfigByNhType(AreaConfig condition) {
		List<AreaConfig> areaConfigList = areaConfigDao.findAreaConfigByNhType(condition);
		List<ExportAreaConfig> exportAreaConfigList = new ArrayList<ExportAreaConfig>();
		for(AreaConfig areaConfig :areaConfigList){
			ExportAreaConfig temp = new ExportAreaConfig();
			temp.setAreaCode(areaConfig.getAreaCode());
			temp.setAreaName(areaConfig.getAreaName());
			temp.setBitNo(areaConfig.getBitNo());
			temp.setDeviceName(areaConfig.getDeviceName());
			temp.setFtxs(areaConfig.getFtxs());
			temp.setIsSum(areaConfig.getIsSum()==1?"是":"否");
			temp.setMemo(areaConfig.getMemo());
			switch (areaConfig.getNhType()) {
			case 1:
				temp.setNhType("电");
				break;
			case 2:
				temp.setNhType("水");
				break;
			case 3:
				temp.setNhType("蒸汽");
				break;
			case 4:
				temp.setNhType("能量");
			default:
				break;
			}
			if(areaConfig.getRuleFlag()!=null&&areaConfig.getRuleFlag()==1){
				temp.setRuleFlag("加");
			}else if(areaConfig.getRuleFlag()!=null&&areaConfig.getRuleFlag()==0){
				temp.setRuleFlag("减");
			}
			exportAreaConfigList.add(temp);
		}
		return exportAreaConfigList;
	}


	@Override
	public Map<String, Integer> findDevicesMapWithOutRoot() {
		Map<String, Integer> map = new HashMap<String,Integer>();
		List<AreaConfig> list = areaConfigDao.findDevicesMapWithOutRoot();
		for (AreaConfig areaConfig : list) {
			map.put(areaConfig.getHpId(), areaConfig.getNhType());
		}
		return map;
	}


	@Override
	public Map<Long, String> findAllArea() {
		Map<Long,String> map = new HashMap<Long,String>();
		List<AreaConfig> list = areaConfigDao.findAllArea();
		for (AreaConfig areaConfig : list) {
			map.put(areaConfig.getAreaId(), areaConfig.getAreaName());
		}
		return map;
	}



}
