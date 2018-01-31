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
import com.supconit.repair.daos.RepairEvtCategoryPersonDao;
import com.supconit.repair.entities.RepairEvtCategoryPerson;
import com.supconit.repair.services.RepairEvtCategoryPersonService;

import hc.base.domains.Pageable;

@Service
public class RepairEvtCategoryPersonServiceImpl extends
		AbstractBaseBusinessService<RepairEvtCategoryPerson, Long> implements
		RepairEvtCategoryPersonService {

	@Autowired
	private RepairEvtCategoryPersonDao repairEvtCategoryPersonDao;
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
	public RepairEvtCategoryPerson getById(Long id) {
		if (null == id || id <= 0)
			return null;
		RepairEvtCategoryPerson repairEvtCategoryPerson = repairEvtCategoryPersonDao
				.getById(id);

		return repairEvtCategoryPerson;
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
		repairEvtCategoryPersonDao.deleteById(id);
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
			repairEvtCategoryPersonDao.deleteById(ids[i]);

		}

	}

	@Override
	@Transactional
	public void deleteBypersonId(RepairEvtCategoryPerson repairEvtCategoryPerson) {
		repairEvtCategoryPersonDao.deleteBypersonId(repairEvtCategoryPerson);
	}

	@Override
	@Transactional(readOnly = true)
	public Pageable<RepairEvtCategoryPerson> findRunReportByCondition(
			Pageable<RepairEvtCategoryPerson> pager,
			RepairEvtCategoryPerson condition) {
		setParameter(condition);
		// added by zhaosc
		return repairEvtCategoryPersonDao.findByCondition(pager, condition);
	}

	public void setParameter(RepairEvtCategoryPerson condition) {
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
	public void insert(RepairEvtCategoryPerson repairEvtCategoryPerson) { // 没用到
		repairEvtCategoryPersonDao.insert(repairEvtCategoryPerson);
	}

	/**
	 * insert RepairEvtCategoryPerson
	 * 
	 * @param repairEvtCategoryPerson
	 *            instance
	 * @return
	 */
	@Override
	@Transactional
	public void insert(RepairEvtCategoryPerson repairEvtCategoryPerson,
			String areaIds, String categoryIds) {
		isAllowSave(repairEvtCategoryPerson, areaIds, categoryIds);
		String[] categoryIdArr = categoryIds.split(",");
		String[] areaIdArr = areaIds.split(",");
		String categoryId = "";

		List<Long> list = new ArrayList<Long>();
		if (categoryIdArr.length > 0) {
			categoryId = categoryIdArr[0];
			if (!categoryId.equalsIgnoreCase(""))
				repairEvtCategoryPerson.setCategoryId(Long
						.parseLong(categoryId));
		}
		if (areaIdArr.length > 0) {
			for (String areaId : areaIdArr) {
				if (!areaId.equalsIgnoreCase(""))
					list.add(Long.parseLong(areaId));
			}
		}
		if (!UtilTool.isEmptyList(list)) {
			repairEvtCategoryPersonDao.insert(list, repairEvtCategoryPerson);
		}
		deleteForDistinct();
	}

	/**
	 * update RepairEvtCategoryPerson
	 * 
	 * @param repairEvtCategoryPerson
	 *            instance
	 * @return
	 */
	@Override
	@Transactional
	public void update(RepairEvtCategoryPerson repairEvtCategoryPerson) { // 没用到

		repairEvtCategoryPersonDao.update(repairEvtCategoryPerson);
	}

	/**
	 * update RepairEvtCategoryPerson
	 * 
	 * @param repairEvtCategoryPerson
	 *            instance
	 * @return
	 * @throws Exception
	 *             modified by zhaosc
	 */
	@Override
	@Transactional
	public void update(RepairEvtCategoryPerson repairEvtCategoryPerson,
			String areaIds, String categoryIds) {
		isAllowSave(repairEvtCategoryPerson, areaIds, categoryIds);
		// 删掉该人员下的所有分配记录
		// insert新的记录
		repairEvtCategoryPersonDao.deleteBypersonId(repairEvtCategoryPerson);
		String[] categoryIdArr = categoryIds.split(",");
		String[] areaIdArr = areaIds.split(",");
		String categoryId = "";
		List<Long> list = new ArrayList<Long>();
		if (categoryIdArr.length > 0)
			categoryId = categoryIdArr[0];
		if (!categoryId.equalsIgnoreCase(""))
			repairEvtCategoryPerson.setCategoryId(Long.parseLong(categoryId));
		if (areaIdArr.length > 0) {
			for (String areaId : areaIdArr) {
				if (!areaId.equalsIgnoreCase(""))
					list.add(Long.parseLong(areaId));
			}
			if (!UtilTool.isEmptyList(list)) {
				repairEvtCategoryPersonDao
						.insert(list, repairEvtCategoryPerson);
			}
		}
	}

	/**
	 * 去重复 added by zhaosc
	 * **/
	@Override
	@Transactional
	public void deleteForDistinct() {
		repairEvtCategoryPersonDao.deleteForDistinct();
	}

	// Check that allows you to save

	private void isAllowSave(RepairEvtCategoryPerson repairEvtCategoryPerson,
			String area, String catagoryIds) {
		if (area.equalsIgnoreCase("") || area == null)
			throw new BusinessDoneException("请选择地区");
		if (repairEvtCategoryPerson.getPersonId() == null)
			throw new BusinessDoneException("请选择人员");
		if (catagoryIds.equalsIgnoreCase("") || catagoryIds == null)
			throw new BusinessDoneException("请选择维修类别");

	}

	@Override
	public RepairEvtCategoryPerson findById(Long id) {
		// TODO Auto-generated method stub
		return repairEvtCategoryPersonDao.findById(id);
	}

	@Override
	public List<RepairEvtCategoryPerson> findByParam(
			RepairEvtCategoryPerson repairEvtCategoryPerson) {
		return repairEvtCategoryPersonDao.findByParam(repairEvtCategoryPerson);
	}

	@Override
	public List<RepairEvtCategoryPerson> findByIds(List<Long> ids) {
		return repairEvtCategoryPersonDao.findByIds(ids);
	}

	@Override
	public Long getByCategoryTotal(String categoryCode) {
		return repairEvtCategoryPersonDao.getByCategoryTotal(categoryCode);
	}

	@Override
	public Pageable<RepairEvtCategoryPerson> findByCategoryArea(
			Pageable<RepairEvtCategoryPerson> pager,
			RepairEvtCategoryPerson condition) {
		return repairEvtCategoryPersonDao.findByCategoryArea(pager, condition);
	}

	@Override
	public Pageable<RepairEvtCategoryPerson> findByCondition(
			Pageable<RepairEvtCategoryPerson> pager,
			RepairEvtCategoryPerson condition) {
		setParameter(condition);
		return repairEvtCategoryPersonDao.findByCondition(pager, condition);
	}

	private void setParameter2(RepairEvtCategoryPerson condition) {
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
	public List<RepairEvtCategoryPerson> findByPersonIdandCategoryId(
			RepairEvtCategoryPerson repairEvtCategoryPerson) {
		return repairEvtCategoryPersonDao
				.findByPersonIdandCategoryId(repairEvtCategoryPerson);
	}
	
    @Override
    public List<RepairEvtCategoryPerson> findByCategoryIdAndAreaId(Long categoryType,Long categoryId, Long areaId) {
        return repairEvtCategoryPersonDao.findByCategoryIdAndAreaId(categoryType,categoryId, areaId);
    }
    
    @Override
    public RepairEvtCategoryPerson getByCategoryIdAndAreaId(Long categoryType,Long categoryId, Long areaId) {
    	List<RepairEvtCategoryPerson> evtPersonList = repairEvtCategoryPersonDao.findByCategoryIdAndAreaId(categoryType,categoryId, areaId);
    	return (null!=evtPersonList && evtPersonList.size()>0) ? evtPersonList.get(0) : new RepairEvtCategoryPerson();
    }

}
