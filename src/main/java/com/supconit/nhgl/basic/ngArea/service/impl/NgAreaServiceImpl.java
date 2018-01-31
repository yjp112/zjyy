package com.supconit.nhgl.basic.ngArea.service.impl;

import hc.base.domains.Pageable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.supconit.base.daos.DeviceDao;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.nhgl.analyse.electric.area.dao.NhAreaDao;
import com.supconit.nhgl.analyse.electric.area.entities.NhArea;
import com.supconit.nhgl.basic.areaConfig.dao.AreaConfigDao;
import com.supconit.nhgl.basic.areaConfig.entities.AreaConfig;
import com.supconit.nhgl.basic.ngArea.dao.NgAreaDao;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;
import com.supconit.nhgl.basic.ngArea.service.NgAreaService;

@Service
public class NgAreaServiceImpl extends AbstractBaseBusinessService<NgArea, Long> implements NgAreaService{

	@Autowired
	private NgAreaDao ngAreaDao;
	@Autowired
	private NhAreaDao nhAreaDao;
	@Autowired
	private DeviceDao deviceDao;
	@Autowired
	private AreaConfigDao areaConfigDao;
	@Override
	@Transactional
	public NgArea getById(Long id) {
		return ngAreaDao.getById(id);
	}
	@Override
	@Transactional
	public void insert(NgArea ngArea) {
		//检测该新增的服务区域是否有效
		checkToSave(ngArea);
		//级联上级名称拼接
		String fullAreaname=getFullName(ngArea.getParentId(),ngArea.getAreaName());
		//设置服务区域的全称  
		ngArea.setFullLevelName(fullAreaname); 
		//插入服务区域
		ngAreaDao.insert(ngArea);
		//同时向区域能耗设置中插入4条信息
		insertConfig(ngArea);
		
	}
	@Transactional
	private void insertConfig(NgArea ngArea) {
		//向能耗区域配置中插入4条数据，
		for(int i=1;i<=4;i++){
			AreaConfig areaConfig =new AreaConfig();
			areaConfig.setAreaId(ngArea.getId()); 
			areaConfig.setIsSum(0);
			areaConfig.setNhType(i);
			//判断在能耗区域中是否已经存在该配置
			if(isNotExistsConfig(areaConfig)){  
				areaConfigDao.insert(areaConfig);
			}
		}
	}
	private boolean isNotExistsConfig(AreaConfig areaConfig) {
		int config=areaConfigDao.countFindAll(areaConfig);
		if(config==0){
			return true;
		}
		return false;
	}
	//获取元素的父级节点名称
	public String getFullName(Long id,String name) {
		String fullAreaname="";
		List<String> list=new ArrayList();
		NgArea area=ngAreaDao.getById(id);
		while(nhAreaDao.findRootId().longValue()!=area.getId().longValue()){
			list.add(area.getAreaName());
			area=ngAreaDao.getById(area.getParentId());
		}
		for(int i=list.size()-1;i>=0;i--){
			fullAreaname=fullAreaname+list.get(i);
			fullAreaname=fullAreaname+"→";
		}
		fullAreaname=fullAreaname+name;
		return fullAreaname;
	}

	@Override
	@Transactional
	public void update(NgArea ngArea) {
		checkToSave(ngArea);
		/*if(ngArea.getArea()==null){
			ngArea.setArea(0f); 
		}
		if(ngArea.getPersons()==null){
			ngArea.setPersons(0l);
		}*/
		//级联上级名称拼接
		String fullAreaname=getFullName(ngArea.getParentId(),ngArea.getAreaName());
		//设置服务区域的全称  
		ngArea.setFullLevelName(fullAreaname); 
		ngAreaDao.update(ngArea);
		// 修改子孙区域
		List<NgArea> sonNgAreaList = ngAreaDao.findById(ngArea.getId());
		for (int i = 0; i < sonNgAreaList.size(); i++) {
			if(ngArea.getId().equals(sonNgAreaList.get(i).getId())){
				sonNgAreaList.remove(i);
				break;
			}
		}
		setFullLevelName(ngArea, sonNgAreaList);
		for(NgArea sonNgArea:sonNgAreaList){
			NgArea ngAreason = new NgArea();
			ngAreason.setId(sonNgArea.getId());
			ngAreason.setFullLevelName(sonNgArea.getFullLevelName());
			ngAreason.setPersons(sonNgArea.getPersons());
			ngAreason.setArea(sonNgArea.getArea());
			ngAreaDao.update(ngAreason);
		}
	}
	
	private void checkToSave(NgArea entity) {
		List<NgArea> list = new ArrayList<NgArea>();
		//验证编码
		list = ngAreaDao.findByCode(entity.getAreaCode());
		if (null != list && list.size() >= 1) {
			if (list.size() > 1) {
				throw new BusinessDoneException("编码[" + entity.getAreaCode()+ "]已经被占用。");
			} else {
				// list.size()==1
				if (entity.getId() != null) {
					// update
					NgArea old = list.get(0);
					if (entity.getId().longValue() == old.getId().longValue()) {
						// ok
					} else {
						throw new BusinessDoneException("编码["+ entity.getAreaCode() + "]已经被占用。");
					}
				} else {
					// insert
					throw new BusinessDoneException("编码["+ entity.getAreaCode() + "]已经被占用。");
				}

			}
		}
		
		//验证名称
		list = ngAreaDao.findByName(entity);
		if (null != list && list.size() >= 1) {
			if (list.size() > 1) {
				throw new BusinessDoneException("该区域名称[" + entity.getAreaName()+ "]在同级中已经被占用。");
			} else {
				// list.size()==1
				if (entity.getId() != null) {
					// update
					NgArea old = list.get(0);
					if (entity.getId().longValue() == old.getId().longValue()) {
						// ok
					} else {
						throw new BusinessDoneException("该区域名称[" + entity.getAreaName()+ "]在同级中已经被占用。");
					}
				} else {
					// insert
					throw new BusinessDoneException("该区域名称[" + entity.getAreaName()+ "]在同级中已经被占用。");
				}

			}
		}
		
	}

	@Override
	public Pageable<NgArea> findByPage(Pageable pager, NgArea condition) {
		// TODO Auto-generated method stub
		return ngAreaDao.findByPage(pager, condition);
	} 

	@Override
	public List<NgArea> findTree() {
		return ngAreaDao.findAll();
	}
	@Override
	@Transactional
	public void removeNgArea(Long[] ids) {
		for(Long id : ids){
			List<NgArea> lsNgAreas = ngAreaDao.findByRoot(id); 
			//验证
			if(null!=lsNgAreas && lsNgAreas.size()>0){
				 throw new BusinessDoneException("该服务区域有子区域，不能删除。");
			}
			//判断该服务区域下是否有挂载区域能耗配置
			Long count =areaConfigDao.getByAreaId(id);
			if(null!=count && count.longValue()>0l){
				 throw new BusinessDoneException("该服务区域已被使用，不能删除。");
			}
			Long num = deviceDao.countsByDevice(id);
			if(null!=num && num.longValue()>0l){
				 throw new BusinessDoneException("该服务区域已被使用，不能删除。");
			}
			ngAreaDao.deleteById(id); 
		}
	}
	@Override
	public void deleteById(Long id) {
		ngAreaDao.deleteById(id);
		
	}

	@Override
	public List<NgArea> findById(Long id) {
		// TODO Auto-generated method stub
		return ngAreaDao.findById(id);
	}
	//查找所用已经配置过的服务区域
	@Override
	public List<NgArea> findByConfig(Long nhType) {
		// TODO Auto-generated method stub
		return ngAreaDao.findByConfig(nhType);
	}
	
	/**
	 * 设置全称
	 * @param father
	 * @param children
	 */
	private void setFullLevelName(NgArea father,List<NgArea> children){
		for (int i = 0; i < children.size(); i++) {
			NgArea iter = children.get(i);
			if(iter.getParentId().equals(father.getId())){
				iter.setFullLevelName(father.getFullLevelName()+ "→" + iter.getAreaName());
			}
			if(hasChild(iter, children)){
				List<NgArea> t_children = new ArrayList<NgArea>();
				for (NgArea ngArea : children) {
					if(iter.getId().equals(ngArea.getParentId())){
						t_children.add(ngArea);
					}
				}
				setFullLevelName(iter, t_children);
			}
		}
	}
	private boolean hasChild(NgArea father,List<NgArea> children){
		boolean flag = false;
		for (int i = 0; i < children.size(); i++) {
			if(father.getId().equals(children.get(i).getParentId())){
				flag = true;
			}
		}
		return flag;
	}
	@Override
	public List<NgArea> findAll() {
		return ngAreaDao.findAll();
		
	}
	@Override
	public List<NgArea> findAllDArea() {
		return ngAreaDao.findAllDArea();
	}
	@Override
	public List<NgArea> findAllSArea() {
		return ngAreaDao.findAllSArea();
	}
}
