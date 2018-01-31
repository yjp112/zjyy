
package com.supconit.base.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.DeviceDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;

import hc.base.domains.Pageable;

@Service
public class GeoAreaServiceImpl extends AbstractBaseBusinessService<GeoArea, Long> implements GeoAreaService{

	@Autowired
	private GeoAreaDao geoAreaDao;
	@Autowired
	private DeviceDao deviceDao;

	@Override
	@Transactional
	public GeoArea getById(Long id) {
		return geoAreaDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(GeoArea geoArea) {
		checkToSave(geoArea);
		//如果上级区域为空的话，parenentId=0l
		if(geoArea.getParentId() == null){
			geoArea.setParentId(0l);
		}
		Long pId = geoArea.getParentId();
		String FName = geoArea.getAreaName();
		while (pId != 0L){
			GeoArea tempGeoArea = geoAreaDao.getById(pId);
			if (tempGeoArea != null ){
				FName = tempGeoArea.getAreaName()+ "→" + FName ;
				pId = tempGeoArea.getParentId();
			}					
		}
		geoArea.setFullLevelName(FName);
		geoAreaDao.insert(geoArea);
		deviceDao.updateLocationName();
	}

	@Override
	@Transactional
	public void update(GeoArea geoArea) {
		checkToSave(geoArea);
		if(geoArea.getParentId() == null){
			geoArea.setParentId(0l);
		}
		List<Long> list = geoAreaDao.findChildIds(geoArea.getId());
		long pid = geoArea.getParentId().longValue();
		if(list.contains(pid)){
			throw new BusinessDoneException("请不要选择自身或子节点作为父节点。");
		}
		Long pId = geoArea.getParentId();
		String FName = geoArea.getAreaName();
		while (pId != 0L){
			GeoArea tempGeoArea = geoAreaDao.getById(pId);
			if (tempGeoArea != null ){
				FName = tempGeoArea.getAreaName()+ "→" + FName ;
				pId = tempGeoArea.getParentId();
			}					
		}
		geoArea.setFullLevelName(FName);
		geoAreaDao.update(geoArea);
		// 修改子区域
		List<GeoArea> SonGeoAreaList = geoAreaDao.findById(geoArea.getId());
		for (int i = 0; i < SonGeoAreaList.size(); i++) {
			if(geoArea.getId().equals(SonGeoAreaList.get(i).getId())){
				SonGeoAreaList.remove(i);
				break;
			}
		}
		setFullLevelName(geoArea, SonGeoAreaList);
		for(GeoArea SonGeoArea:SonGeoAreaList){
			GeoArea geoAreason = new GeoArea();
			geoAreason.setId(SonGeoArea.getId());
			geoAreason.setFullLevelName(SonGeoArea.getFullLevelName());
			geoAreaDao.update(geoAreason);
		}
		deviceDao.updateLocationName();
	}
	/**
	 * 设置全称
	 * @param father
	 * @param children
	 */
	private void setFullLevelName(GeoArea father,List<GeoArea> children){
		for (int i = 0; i < children.size(); i++) {
			GeoArea iter = children.get(i);
			if(iter.getParentId().equals(father.getId())){
				iter.setFullLevelName(father.getFullLevelName()+ "→" + iter.getAreaName());
			}
			if(hasChild(iter, children)){
				List<GeoArea> t_children = new ArrayList<GeoArea>();
				for (GeoArea geoArea : children) {
					if(iter.getId().equals(geoArea.getParentId())){
						t_children.add(geoArea);
					}
				}
				setFullLevelName(iter, t_children);
			}
		}
	}
	private boolean hasChild(GeoArea father,List<GeoArea> children){
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
	public Pageable<GeoArea> findByPage(Pageable pager,GeoArea geoArea){
		if(null== geoArea.getId()){
			geoArea.setId(0L);//必须要有层级结构，防止不在树结构的垃圾数据被查出,正常情况下，点击菜单“地理区域簇”时，跳到此处
		}
		//查出每个节点对应的子节点（包括自己）
		List<GeoArea> listGeoAreas = new ArrayList<GeoArea>();
		if(geoArea.getId()==0l||geoArea.getId().equals(0l)){//如果是根节点
			listGeoAreas = geoAreaDao.findByRoot(geoArea.getId());
		}else{
			listGeoAreas = geoAreaDao.findById(geoArea.getId());
		}
		List<Long> subGeoAreas = new ArrayList<Long>();
		for (int i=0;i<listGeoAreas.size();i++){
			subGeoAreas.add(listGeoAreas.get(i).getId());
		}
		if(subGeoAreas!=null&&subGeoAreas.size()>0){
			geoArea.setSubGeoAreaList(subGeoAreas);
		}

		return geoAreaDao.findByPage(pager,geoArea);
	}
	
	private void checkToSave(GeoArea entity) {
		List<GeoArea> list = new ArrayList<GeoArea>();
		list = geoAreaDao.findByCode(entity.getAreaCode());
		if (null != list && list.size() >= 1) {
			if (list.size() > 1) {
				throw new BusinessDoneException("编码[" + entity.getAreaCode()+ "]已经被占用。");
			} else {
				// list.size()==1
				if (entity.getId() != null) {
					// update
					GeoArea old = list.get(0);
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
	}
	
	@Override
	public List<GeoArea> findBuildsGis3D() {		
		return geoAreaDao.findBuildsGis3D();
	}
	@Override
	public List<GeoArea> findFloorById(Long buildingId) {		
		return geoAreaDao.findFloorById(buildingId);
	}

	@Override
	public List<GeoArea> findTree() {
		return geoAreaDao.findAll();
	}

	@Override
	public List<GeoArea> findByCode(String code) {
		return geoAreaDao.findByCode(code);
	}

	@Override
	@Transactional
	public void removeGeoArea(Long[] ids) {
		for(Long id : ids){
			List<GeoArea> lsGeoAreas = geoAreaDao.findByRoot(id);
			if(null!=lsGeoAreas && lsGeoAreas.size()>0){
				 throw new BusinessDoneException("该地理区域有子区域，不能删除。");
			}
			Device device = new Device();
			List<Long> temp = new ArrayList<Long>();
			temp.add(id);
			device.setLstLocationId(temp);
			device.setLocationId(id);
			Long num = deviceDao.countByDevice(device);
			if(null!=num && num.longValue()>0l){
				 throw new BusinessDoneException("该地理区域已被使用，不能删除。");
			}
			geoAreaDao.delete(new GeoArea(id));
		}
	}

	@Override
	public GeoArea getByCode(String code) {
		return geoAreaDao.getByCode(code);
	}
	
	@Override
	public List<GeoArea> findBuildings() {
		return geoAreaDao.findByParentId(0L);
	}
	@Override
	public List<GeoArea> findAllFullName() {
		return geoAreaDao.findAllFullName();
	}
	@Override
	public List<GeoArea> findLouByCategory_g(String categoryCode) {
        return  geoAreaDao.findLouByCategory_g(categoryCode);
	}
	@Override
	public List<GeoArea> findCengByCategory_g(String categoryCode) {
        return  geoAreaDao.findCengByCategory_g(categoryCode);
	}
	@Override
	public List<GeoArea> findAlarmFloor(Long parentId,String tuCeng) {
		return  geoAreaDao.findAlarmFloor(parentId,tuCeng);
	}

	@Override
	public List<GeoArea> findByRoot(long parentId) {
		return geoAreaDao.findByRoot(parentId);
	}
	@Override
	public List<GeoArea> findAll() {
		return geoAreaDao.findAll();
	}

	@Override
	public void insertForImp(List<GeoArea> list) {
		for (GeoArea geoArea : list) {
			geoAreaDao.insert(geoArea);
		}
	}

	@Override
	public void deleteById(Long id) {
		geoAreaDao.deleteById(id);
	}

	@Override
	public List<GeoArea> findByCon(GeoArea area) {
		return geoAreaDao.findByCon(area);
	}

	@Override
	public List<GeoArea> findById(Long id) {
		return geoAreaDao.findById(id);
	}

	@Override
	public List<GeoArea> findByCodes_g(List<String> lstCodes) {
		return geoAreaDao.findByCodes_g(lstCodes);
	}

	@Override
	public List<GeoArea> findByParentId(Long parentId) {
		return geoAreaDao.findByParentId(parentId);
	}
}

