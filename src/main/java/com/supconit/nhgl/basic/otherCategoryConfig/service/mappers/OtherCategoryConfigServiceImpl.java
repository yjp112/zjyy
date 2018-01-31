package com.supconit.nhgl.basic.otherCategoryConfig.service.mappers;

import hc.base.domains.Pageable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.nhgl.basic.otherCategoryConfig.dao.OtherCategoryConfigDao;
import com.supconit.nhgl.basic.otherCategoryConfig.entities.OtherCategoryConfig;
import com.supconit.nhgl.basic.otherCategoryConfig.service.OtherCategoryConfigService;
@Service
public class OtherCategoryConfigServiceImpl extends AbstractBaseBusinessService<OtherCategoryConfig,Long> implements OtherCategoryConfigService{

	@Autowired
	private OtherCategoryConfigDao otherCategoryConfigDao;
	
	
	@Override
	public OtherCategoryConfig getById(Long arg0) {
		return otherCategoryConfigDao.getById(arg0);
	}

	@Override
	public void delete(OtherCategoryConfig otherCategoryConfig) {
		if(otherCategoryConfig.getSubLeftOtherCategoryConfigList()!=null){
			for(OtherCategoryConfig areaSubConfig:otherCategoryConfig.getSubLeftOtherCategoryConfigList()){
				init(areaSubConfig,otherCategoryConfig);
				otherCategoryConfigDao.delete(areaSubConfig);
			}
		}
		if(otherCategoryConfig.getSubRightOtherCategoryConfigList()!=null){
			for(OtherCategoryConfig areaSubConfig:otherCategoryConfig.getSubRightOtherCategoryConfigList()){
				init(areaSubConfig,otherCategoryConfig);
				otherCategoryConfigDao.delete(areaSubConfig);
			}
		}
	}

	@Override
	public void insert(OtherCategoryConfig otherCategoryConfig) {
		
		if(otherCategoryConfig.getIsSum().equals(1)){
			otherCategoryConfigDao.insert(otherCategoryConfig);
		}else{
			if(otherCategoryConfig.getSubLeftOtherCategoryConfigList()==null&&otherCategoryConfig.getSubRightOtherCategoryConfigList()==null){
				throw new BusinessDoneException("请为该区域配置设备。");
			}
			if(otherCategoryConfig.getSubLeftOtherCategoryConfigList()!=null){
				for(OtherCategoryConfig areaSubConfig:otherCategoryConfig.getSubLeftOtherCategoryConfigList()){
					init(areaSubConfig,otherCategoryConfig);
					otherCategoryConfigDao.insert(areaSubConfig);
				}
			}
			if(otherCategoryConfig.getSubRightOtherCategoryConfigList()!=null){
				for(OtherCategoryConfig areaSubConfig:otherCategoryConfig.getSubRightOtherCategoryConfigList()){
					init(areaSubConfig,otherCategoryConfig);
					otherCategoryConfigDao.insert(areaSubConfig);
				}
			}
		}
		
	}
	//设置附带数据
	private void init(OtherCategoryConfig areaSubConfig, OtherCategoryConfig otherCategoryConfig) { 
		areaSubConfig.setCategoryId(otherCategoryConfig.getCategoryId());
		areaSubConfig.setIsSum(otherCategoryConfig.getIsSum());
		areaSubConfig.setNhType(otherCategoryConfig.getNhType());
		//areaSubConfig.setDescription(otherCategoryConfig.getDescription());
		areaSubConfig.setMemo(otherCategoryConfig.getMemo());
		areaSubConfig.setDeviceId(areaSubConfig.getId()); 
		areaSubConfig.setBitNo(areaSubConfig.getExtended1()); 
		areaSubConfig.setId(otherCategoryConfig.getId());
	}

	@Override
	public void update(OtherCategoryConfig otherCategoryConfig) {
		otherCategoryConfigDao.deleteByCategoryId(otherCategoryConfig.getCategoryId(),otherCategoryConfig.getNhType().toString());
		insert(otherCategoryConfig);
	}

	@Override
	public Pageable<OtherCategoryConfig> findByCondition(Pageable<OtherCategoryConfig> pager,
			OtherCategoryConfig condition) {
		return otherCategoryConfigDao.findByCondition(pager, condition);
	}

	@Override
	public List<OtherCategoryConfig> findByCategoryIdAndRule(OtherCategoryConfig otherCategoryConfig,Integer ruleFlag) {
		otherCategoryConfig.setRuleFlag(ruleFlag);
		return otherCategoryConfigDao.findByCategoryIdAndRule(otherCategoryConfig);
	}


	@Override
	public void deleteByCategoryId(Long categoryId,String nhtype) {
		otherCategoryConfigDao.deleteByCategoryId(categoryId,nhtype);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int countFindAll(OtherCategoryConfig otherCategoryConfig) {
		// TODO Auto-generated method stub
		return otherCategoryConfigDao.countFindAll(otherCategoryConfig);
	}

	@Override
	public OtherCategoryConfig getByCondition(OtherCategoryConfig otherCategoryConfig) {
		// TODO Auto-generated method stub
		return otherCategoryConfigDao.getByCondition(otherCategoryConfig);
	}



}
