package com.supconit.nhgl.basic.otherCategory.servive.impl;

import hc.base.domains.Pageable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DeviceDao;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.nhgl.basic.otherCategory.dao.OtherCategoryDao;
import com.supconit.nhgl.basic.otherCategory.entity.OtherCategory;
import com.supconit.nhgl.basic.otherCategory.servive.OtherCategoryService;

@Service
public class OtherCategoryServiceImpl extends AbstractBaseBusinessService<OtherCategory, Long> implements OtherCategoryService{
	@Autowired
	private OtherCategoryDao otherCategoryDao;
	
	@Autowired
	private DeviceDao deviceDao;

	@Override
	@Transactional
	public OtherCategory getById(Long id) {
		return otherCategoryDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(OtherCategory otherCategory) {
		checkToSave(otherCategory);
		//如果上级区域为空的话，parenentId=0l
		if(otherCategory.getParentId() == null){
			otherCategory.setParentId(0l);
		}
		Long pId = otherCategory.getParentId();
		String FName = otherCategory.getName();
		while (pId != 0L){
			OtherCategory tempOtherCategory = otherCategoryDao.getById(pId);
			if (tempOtherCategory != null ){
				FName = tempOtherCategory.getName()+ "→" + FName ;
				pId = tempOtherCategory.getParentId();
			}					
		}
		otherCategory.setFullLevelName(FName);
		otherCategoryDao.insert(otherCategory);
		deviceDao.updateLocationName();
	}

	@Override
	@Transactional
	public void update(OtherCategory otherCategory) {
		checkToSave(otherCategory);
		if(otherCategory.getParentId() == null){
			otherCategory.setParentId(0l);
		}
		if(otherCategory.getParentId() == otherCategory.getId()){
			 throw new BusinessDoneException("请不要选择自身作为父节点。");
		}
		Long pId = otherCategory.getParentId();
		String FName = otherCategory.getName();
		while (pId != 0L){
			OtherCategory tempOtherCategory = otherCategoryDao.getById(pId);
			if (tempOtherCategory != null ){
				FName = tempOtherCategory.getName()+ "→" + FName ;
				pId = tempOtherCategory.getParentId();
			}					
		}
		otherCategory.setFullLevelName(FName);
		otherCategoryDao.update(otherCategory);
		// 修改子区域
		List<OtherCategory> SonOtherCategoryList = otherCategoryDao.findById(otherCategory.getId());
		for (int i = 0; i < SonOtherCategoryList.size(); i++) {
			if(otherCategory.getId().equals(SonOtherCategoryList.get(i).getId())){
				SonOtherCategoryList.remove(i);
				break;
			}
		}
		setFullLevelName(otherCategory, SonOtherCategoryList);
		for(OtherCategory SonOtherCategory:SonOtherCategoryList){
			OtherCategory OtherCategoryson = new OtherCategory();
			OtherCategoryson.setId(SonOtherCategory.getId());
			OtherCategoryson.setFullLevelName(SonOtherCategory.getFullLevelName());
			otherCategoryDao.update(OtherCategoryson);
		}
		deviceDao.updateLocationName();
	}
	/**
	 * 设置全称
	 * @param father
	 * @param children
	 */
	private void setFullLevelName(OtherCategory father,List<OtherCategory> children){
		for (int i = 0; i < children.size(); i++) {
			OtherCategory iter = children.get(i);
			if(iter.getParentId().equals(father.getId())){
				iter.setFullLevelName(father.getFullLevelName()+ "→" + iter.getName());
			}
			if(hasChild(iter, children)){
				List<OtherCategory> t_children = new ArrayList<OtherCategory>();
				for (OtherCategory OtherCategory : children) {
					if(iter.getId().equals(OtherCategory.getParentId())){
						t_children.add(OtherCategory);
					}
				}
				setFullLevelName(iter, t_children);
			}
		}
	}
	private boolean hasChild(OtherCategory father,List<OtherCategory> children){
		boolean flag = false;
		for (int i = 0; i < children.size(); i++) {
			if(father.getId().equals(children.get(i).getParentId())){
				flag = true;
			}
		}
		return flag;
	}
	@Override
	@Transactional
	public Pageable<OtherCategory> findByPage(Pageable pager,OtherCategory ngArea){
		if(null== ngArea.getId()){
			ngArea.setId(0L);//必须要有层级结构，防止不在树结构的垃圾数据被查出,正常情况下，点击菜单“地理区域簇”时，跳到此处
		}
		//查出每个节点对应的子节点（包括自己）
		List<OtherCategory> listOtherCategorys = new ArrayList<OtherCategory>();
		if(ngArea.getId()==0l||ngArea.getId().equals(0l)){//如果是根节点
			listOtherCategorys = otherCategoryDao.findByRoot(ngArea.getId());
		}else{
			listOtherCategorys = otherCategoryDao.findById(ngArea.getId());
		}
		List<Long> subOtherCategorys = new ArrayList<Long>();
		for (int i=0;i<listOtherCategorys.size();i++){
			subOtherCategorys.add(listOtherCategorys.get(i).getId());
		}
		if(subOtherCategorys!=null&&subOtherCategorys.size()>0){
			ngArea.setSubOtherCategoryList(subOtherCategorys);
		}

		return otherCategoryDao.findByPage(pager,ngArea);
	}
	
	private void checkToSave(OtherCategory entity) {
		List<OtherCategory> list = new ArrayList<OtherCategory>();
		//验证编码
		list = otherCategoryDao.findByCode(entity.getCode());
		if (null != list && list.size() >= 1) {
			if (list.size() > 1) {
				throw new BusinessDoneException("编码[" + entity.getCode()+ "]已经被占用。");
			} else {
				// list.size()==1
				if (entity.getId() != null) {
					// update
					OtherCategory old = list.get(0);
					if (entity.getId().longValue() == old.getId().longValue()) {
						// ok
					} else {
						throw new BusinessDoneException("编码["+ entity.getCode() + "]已经被占用。");
					}
				} else {
					// insert
					throw new BusinessDoneException("编码["+ entity.getCode() + "]已经被占用。");
				}

			}
		}
		
		//验证名称
		list = otherCategoryDao.findByName(entity);
		if (null != list && list.size() >= 1) {
			if (list.size() > 1) {
				throw new BusinessDoneException("该区域名称[" + entity.getName()+ "]在同级中已经被占用。");
			} else {
				// list.size()==1
				if (entity.getId() != null) {
					// update
					OtherCategory old = list.get(0);
					if (entity.getId().longValue() == old.getId().longValue()) {
						// ok
					} else {
						throw new BusinessDoneException("该区域名称[" + entity.getName()+ "]在同级中已经被占用。");
					}
				} else {
					// insert
					throw new BusinessDoneException("该区域名称[" + entity.getName()+ "]在同级中已经被占用。");
				}

			}
		}
		
	}
	
	@Override
	public List<OtherCategory> findBuildsGis3D() {		
		return otherCategoryDao.findBuildsGis3D();
	}
	@Override
	public List<OtherCategory> findFloorById(Long buildingId) {		
		return otherCategoryDao.findFloorById(buildingId);
	}

	@Override
	public List<OtherCategory> findTree() {
		return otherCategoryDao.findAll();
	}

	@Override
	public List<OtherCategory> findByCode(String code) {
		return otherCategoryDao.findByCode(code);
	}

	@Override
	@Transactional
	public void removeOtherCategory(Long[] ids) {
		for(Long id : ids){
			List<OtherCategory> lsOtherCategorys = otherCategoryDao.findByRoot(id); 
			if(null!=lsOtherCategorys && lsOtherCategorys.size()>0){
				 throw new BusinessDoneException("该地理区域有子区域，不能删除。");
			}
			//Device device = new Device();
			/*List<Long> temp = new ArrayList<Long>();
			temp.add(id);
			device.setLstLocationId(temp);*/ 
			//device.setServiceAreaId(id); 
			//Long serviceAreaId=id;
			Long num = deviceDao.countsByDevice(id);
			if(null!=num && num.longValue()>0l){
				 throw new BusinessDoneException("该地理区域已被使用，不能删除。");
			}
			otherCategoryDao.delete(new OtherCategory(id));
		}
	}

	@Override
	public OtherCategory getByCode(String code) {
		return otherCategoryDao.getByCode(code);
	}
	
	@Override
	public List<OtherCategory> findBuildings() {
		return otherCategoryDao.findByParentId(0L);
	}
	@Override
	public List<OtherCategory> findAllFullName() {
		return otherCategoryDao.findAllFullName();
	}
	@Override
	public List<OtherCategory> findLouByCategory_g(String categoryCode) {
        return  otherCategoryDao.findLouByCategory_g(categoryCode);
	}
	@Override
	public List<OtherCategory> findCengByCategory_g(String categoryCode) {
        return  otherCategoryDao.findCengByCategory_g(categoryCode);
	}
	@Override
	public List<OtherCategory> findAlarmFloor(Long parentId,String tuCeng) {
		return  otherCategoryDao.findAlarmFloor(parentId,tuCeng);
	}

	@Override
	public List<OtherCategory> findByRoot(long parentId) {
		return otherCategoryDao.findByRoot(parentId);
	}
	@Override
	public List<OtherCategory> findAll() {
		return otherCategoryDao.findAll();
	}

	@Override
	public void insertForImp(List<OtherCategory> list) {
		for (OtherCategory OtherCategory : list) {
			otherCategoryDao.insert(OtherCategory);
		}
	}

	@Override
	public void deleteById(Long id) {
		otherCategoryDao.deleteById(id);
	}

	@Override
	public List<OtherCategory> findByCon(OtherCategory area) {
		return otherCategoryDao.findByCon(area);
	}

	@Override
	public List<OtherCategory> findById(Long id) {
		return otherCategoryDao.findById(id);
	}

	@Override
	public List<OtherCategory> findByCodes_g(List<String> lstCodes) {
		return otherCategoryDao.findByCodes_g(lstCodes);
	}

	@Override
	public List<OtherCategory> findByParentId(Long parentId) {
		return otherCategoryDao.findByParentId(parentId);
	}
	
	

}
