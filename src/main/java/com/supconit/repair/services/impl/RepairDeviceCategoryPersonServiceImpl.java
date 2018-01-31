package com.supconit.repair.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.daos.SubDeviceDao;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.UtilTool;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.repair.daos.RepairEvtCategoryDao;
import com.supconit.repair.daos.RepairDeviceCategoryPersonDao;
import com.supconit.repair.entities.RepairDeviceCategoryPerson;
import com.supconit.repair.services.RepairDeviceCategoryPersonService;

import hc.base.domains.Pageable;

@Service
public class RepairDeviceCategoryPersonServiceImpl extends
		AbstractBaseBusinessService<RepairDeviceCategoryPerson, Long> implements
		RepairDeviceCategoryPersonService {

	@Autowired
	private RepairDeviceCategoryPersonDao repairDeviceCategoryPersonDao;
	@Autowired
	private RepairEvtCategoryDao repairEvtCategoryDao;
	
	@Autowired
	private DeviceCategoryDao deviceCategoryDao;
	
	@Autowired
	private SubDeviceDao subDeviceDao;
	@Autowired
	private AttachmentDao attachmentDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	private static final int batchMaxSize = 100;
	@Autowired
	private DepartmentService deptService;

	/**
	 * Get device by ID
	 * 
	 * @param id
	 *            device id
	 * @return object instance
	 */
	@Override
	@Transactional(readOnly = true)
	public RepairDeviceCategoryPerson getById(Long id) {
		if (null == id || id <= 0)
			return null;
		RepairDeviceCategoryPerson repairDeviceCategoryPerson = repairDeviceCategoryPersonDao
				.getById(id);

		return repairDeviceCategoryPerson;
	}

	/**
	 * delete Device by ID
	 * 
	 * @param id
	 *            device ID
	 * @return
	 */
	@Override
	@Transactional
	public void deleteById(Long id) {
		repairDeviceCategoryPersonDao.deleteById(id);
	}

	/**
	 * delete Device by ID array
	 * 
	 * @param ids
	 *            device ID array
	 * @return
	 */
	@Override
	@Transactional
	public void deleteByIds(Long[] ids) {
		/*
		 * for(int i =0;i<ids.length;i++) { isAllowDelete(ids[i]); }
		 */
		for (int i = 0; i < ids.length; i++) {
			repairDeviceCategoryPersonDao.deleteById(ids[i]);

		}

	}

	@Override
	@Transactional
	public void deleteBypersonId(RepairDeviceCategoryPerson repairDeviceCategoryPerson) {
		repairDeviceCategoryPersonDao.deleteBypersonId(repairDeviceCategoryPerson);
	}

	@Override
	@Transactional(readOnly = true)
	public Pageable<RepairDeviceCategoryPerson> findRunReportByCondition(
			Pageable<RepairDeviceCategoryPerson> pager,
			RepairDeviceCategoryPerson condition) {
		setParameter(condition);
		// added by zhaosc
		return repairDeviceCategoryPersonDao.findByCondition(pager, condition);
	}

	public void setParameter(RepairDeviceCategoryPerson condition) {
		if (condition.getAreaId() != null) {// 递归查询子节点
			List<GeoArea> listGeoAreas = geoAreaDao.findById(condition
					.getAreaId());
			List<Long> lstLocationId = new ArrayList<Long>();
			if (!UtilTool.isEmptyList(listGeoAreas)) {
				for (GeoArea g : listGeoAreas) {
					lstLocationId.add(g.getId());
				}
			}
			condition.setLstLocationId(lstLocationId);
		}
		if (condition.getCategoryId() != null) {// 递归查询子节点
			if(condition.getCategoryType() ==1){
				List<Long> lstCategoryId = repairEvtCategoryDao
						.findChildIds(condition.getCategoryId());
				condition.setLstCategoryId(lstCategoryId);
			}
			if(condition.getCategoryType() ==2){
				List<Long> lstCategoryId = deviceCategoryDao
						.findChildIds(condition.getCategoryId());
				condition.setLstCategoryId(lstCategoryId);
			}
		}
		if (condition.getDepartmentId() != null) {
			List<Department> deptList = deptService.findByPid(condition
					.getDepartmentId());
			List<Long> deptId = new ArrayList<Long>();
			if (!UtilTool.isEmptyList(deptList))
				;
			{
				for (Department dept : deptList) {
					deptId.add(dept.getId());
				}
			}
			deptId.add(condition.getDepartmentId());
			condition.setDeptId(deptId);
		}
	}
	

	/**
	 * insert Device
	 * 
	 * @param device
	 *            instance
	 * @return
	 */
	@Override
	@Transactional
	public void insert(RepairDeviceCategoryPerson repairDeviceCategoryPerson) { // 没用到
		repairDeviceCategoryPersonDao.insert(repairDeviceCategoryPerson);
	}

	/**
	 * insert RepairDeviceCategoryPerson
	 * 
	 * @param RepairDeviceCategoryPerson
	 *            instance
	 * @return
	 */
	@Override
	@Transactional
	public void insert(RepairDeviceCategoryPerson repairDeviceCategoryPerson,
			String areaIds, String categoryIds) {
		isAllowSave(repairDeviceCategoryPerson, areaIds, categoryIds);
		String[] categoryIdArr = categoryIds.split(",");
		String[] areaIdArr = areaIds.split(",");
		String categoryId = "";

		List<Long> list = new ArrayList<Long>();
		if (categoryIdArr.length > 0) {
			categoryId = categoryIdArr[0];
			if (!categoryId.equalsIgnoreCase(""))
				repairDeviceCategoryPerson.setCategoryId(Long
						.parseLong(categoryId));
		}
		if (areaIdArr.length > 0) {
			for (String areaId : areaIdArr) {
				if (!areaId.equalsIgnoreCase(""))
					list.add(Long.parseLong(areaId));
			}
		}
		if (!UtilTool.isEmptyList(list)) {
			repairDeviceCategoryPersonDao.insert(list, repairDeviceCategoryPerson);
		}
		deleteForDistinct();
	}

	/**
	 * update RepairDeviceCategoryPerson
	 * 
	 * @param RepairDeviceCategoryPerson
	 *            instance
	 * @return
	 */
	@Override
	@Transactional
	public void update(RepairDeviceCategoryPerson repairDeviceCategoryPerson) { // 没用到

		repairDeviceCategoryPersonDao.update(repairDeviceCategoryPerson);
	}

	/**
	 * update RepairDeviceCategoryPerson
	 * 
	 * @param RepairDeviceCategoryPerson
	 *            instance
	 * @return
	 * @throws Exception
	 *             modified by zhaosc
	 */
	@Override
	@Transactional
	public void update(RepairDeviceCategoryPerson repairDeviceCategoryPerson,
			String areaIds, String categoryIds) {
		isAllowSave(repairDeviceCategoryPerson, areaIds, categoryIds);
		// 删掉该人员下的所有分配记录
		// insert新的记录
		repairDeviceCategoryPersonDao.deleteBypersonId(repairDeviceCategoryPerson);
		String[] categoryIdArr = categoryIds.split(",");
		String[] areaIdArr = areaIds.split(",");
		String categoryId = "";
		List<Long> list = new ArrayList<Long>();
		if (categoryIdArr.length > 0)
			categoryId = categoryIdArr[0];
		if (!categoryId.equalsIgnoreCase(""))
			repairDeviceCategoryPerson.setCategoryId(Long.parseLong(categoryId));
		if (areaIdArr.length > 0) {
			for (String areaId : areaIdArr) {
				if (!areaId.equalsIgnoreCase(""))
					list.add(Long.parseLong(areaId));
			}
			if (!UtilTool.isEmptyList(list)) {
				repairDeviceCategoryPersonDao
						.insert(list, repairDeviceCategoryPerson);
			}
		}
	}

	/**
	 * 去重复 added by zhaosc
	 * **/
	@Override
	@Transactional
	public void deleteForDistinct() {
		repairDeviceCategoryPersonDao.deleteForDistinct();
	}

	// Check that allows you to save

	private void isAllowSave(RepairDeviceCategoryPerson repairDeviceCategoryPerson,
			String area, String catagoryIds) {
		if (area.equalsIgnoreCase("") || area == null)
			throw new BusinessDoneException("请选择地区");
		if (repairDeviceCategoryPerson.getPersonId() == null)
			throw new BusinessDoneException("请选择人员");
		if (catagoryIds.equalsIgnoreCase("") || catagoryIds == null)
			throw new BusinessDoneException("请选择维修类别");

	}

	@Override
	public RepairDeviceCategoryPerson findById(Long id) {
		// TODO Auto-generated method stub
		return repairDeviceCategoryPersonDao.findById(id);
	}

	@Override
	public List<RepairDeviceCategoryPerson> findByParam(
			RepairDeviceCategoryPerson RepairDeviceCategoryPerson) {
		return repairDeviceCategoryPersonDao.findByParam(RepairDeviceCategoryPerson);
	}

	@Override
	public List<RepairDeviceCategoryPerson> findByIds(List<Long> ids) {
		return repairDeviceCategoryPersonDao.findByIds(ids);
	}

	@Override
	public Long getByCategoryTotal(String categoryCode) {
		return repairDeviceCategoryPersonDao.getByCategoryTotal(categoryCode);
	}

	@Override
	public Pageable<RepairDeviceCategoryPerson> findByCategoryArea(
			Pageable<RepairDeviceCategoryPerson> pager,
			RepairDeviceCategoryPerson condition) {
		return repairDeviceCategoryPersonDao.findByCategoryArea(pager, condition);
	}

	@Override
	public Pageable<RepairDeviceCategoryPerson> findByCondition(
			Pageable<RepairDeviceCategoryPerson> pager,
			RepairDeviceCategoryPerson condition) {
		setParameter(condition);
		return repairDeviceCategoryPersonDao.findByCondition(pager, condition);
	}

	private void setParameter2(RepairDeviceCategoryPerson condition) {
		if (condition.getAreaId() != null) {// 递归查询子节点
			List<GeoArea> listGeoAreas = geoAreaDao.findById(condition
					.getAreaId());
			List<Long> lstLocationId = new ArrayList<Long>();
			if (!UtilTool.isEmptyList(listGeoAreas)) {
				for (GeoArea g : listGeoAreas) {
					lstLocationId.add(g.getId());
				}
			}
			condition.setLstLocationId(lstLocationId);
		}
		if (condition.getCategoryId() != null) {// 递归查询子节点
			List<Long> lstCatagoryId = repairEvtCategoryDao
					.findChildIds(condition.getCategoryId());
			if (!UtilTool.isEmptyList(lstCatagoryId)) {
				condition.setLstCategoryId(lstCatagoryId);
			}

		}

	}

	@Override
	public List<RepairDeviceCategoryPerson> findByPersonIdandCategoryId(
			RepairDeviceCategoryPerson repairDeviceCategoryPerson) {
		return repairDeviceCategoryPersonDao
				.findByPersonIdandCategoryId(repairDeviceCategoryPerson);
	}
	
    @Override
    public List<RepairDeviceCategoryPerson> findByCategoryIdAndAreaId(Long categoryType,Long categoryId, Long areaId) {
        return repairDeviceCategoryPersonDao.findByCategoryIdAndAreaId(categoryType,categoryId, areaId);
    }
    
    @Override
    public RepairDeviceCategoryPerson getByCategoryIdAndAreaId(Long categoryType,Long categoryId, Long areaId) {
    	List<RepairDeviceCategoryPerson> evtPersonList = repairDeviceCategoryPersonDao.findByCategoryIdAndAreaId(categoryType,categoryId, areaId);
    	return (null!=evtPersonList && evtPersonList.size()>0) ? evtPersonList.get(0) : new RepairDeviceCategoryPerson();
    }

}
