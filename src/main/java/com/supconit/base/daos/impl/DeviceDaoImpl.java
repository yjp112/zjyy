package com.supconit.base.daos.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supconit.base.daos.DeviceCategoryDao;
import com.supconit.base.daos.DeviceDao;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceTree;
import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.UtilTool;

import hc.base.domains.Pageable;

@Repository
public class DeviceDaoImpl extends AbstractBaseDao<Device, Long> implements DeviceDao {

	private static final String	NAMESPACE	= Device.class.getName();
	@Autowired
	private DeviceCategoryDao	deviceCategoryDao;

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


	// 设备资产运行统计查询
	@Override
	public Pageable<Device> findRunReportByCondition(Pageable<Device> pager, Device condition) {
		return findByPager(pager,"findRunReportByCondition", "countRunReportByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}

	@Override
	public Device findById(Long id) {
		return selectOne("findById", id);
	}

	// 按类别查询设备
	@Override
	public List<Device> findByCategory(String categoryCode) {
		return selectList("findByCategory", categoryCode);
	}

	// 查找所有摄像头
	@Override
	public List<Device> findVideos() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code1", Constant.DEVICE_SXT);
		map.put("code2", Constant.DEVICE_SXT_BQ);
		map.put("code3", Constant.DEVICE_SXT_KQ);
		map.put("code4", Constant.DEVICE_SXT_QJ);
		return selectList("findVideos", map);
	}

	// 按设备编码
	@Override
	
	public Device getByDeviceCode(String deviceCode) {
		return selectOne("getByDeviceCode", deviceCode);
	}
	@Override
	public List<Device> findByCode(String deviceCode) {
		return selectList("getByDeviceCode", deviceCode);
	}
	// 按设备模型编码
	@Override
	public Device getByHpid(String hpid) {
		return selectOne("getByHpid", hpid);
	}
	
	public List<Device> findByHpid(String hpid) {
		return selectList("getByHpid", hpid);
	}
	// 按设备模型编码或设备名称
	@Override
	public Pageable<Device> findByCodeName_g(Pageable<Device> pager, Device condition) {
		return findByPager(pager,"findByCodeName_g", "countByFindByCodeName_g", condition);
	}

	@Override
	public List<Device> findByCodeName_g(Device condition) {
		return selectList("findByCodeName_g", condition);
	}

	// 按设备模型编码
	@Override
	public Pageable<Device> findByKuangxuan_g(Pageable<Device> pager, Device device, String[] codes) {
		device.setHpidlst(Arrays.asList(codes));
		return findByPager(pager,"findByKuangxuan_g", "countByFindByKuangxuan_g", device);
	}

	// 按设备类别ID查询数量-删除设备类别验证用
	public Long findCountByCategoryId(long id) {
		return getSqlSession().selectOne(getNamespace() + ".findCountByCategoryId", id);
	}

	@Override
	public List findMjZnzm(String geoareaCode ,List<Long> lstHaveOpenStatus ,List<Long> lstHaveAlarmStatus, boolean bol){
		Map<String, Object> map = new HashMap<String, Object>();
		List lst = new ArrayList();
		map.put("geoareaCode", geoareaCode);
		map.put("lstHaveAlarmStatus", lstHaveAlarmStatus);		
		lst.add(selectList("findAlarmDevice_g", map));
		if (bol) {
			map.put("lstHaveOpenStatus", lstHaveOpenStatus);
			lst.add(selectList("findMjZnzm_g", map));
		}
		return lst;
	}

	// 查设备图片
	@Override
	public Device getDeviceImg(Device device) {
		return selectOne("getDeviceImg", device);
	}

	@Override
	public List<Device> findStatusByCodes(Device device) {
		return selectList("findStatusByCodes", device);
	}

	@Override
	public Long countByDevice(Device condition) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("condition", condition);
		return getSqlSession().selectOne(getNamespace() + ".countByCondition", param);
	}

	@Override
	public Long countsByDevice(Long serviceAreaId) {
		return getSqlSession().selectOne(getNamespace() + ".countByConditions", serviceAreaId);
	}
	
	@Override
	public List<Device> getDeviceUseCount(Device device) {
		return selectList("getDeviceUseCount", device);
	}

	@Override
	public int updateTotalRunningTime(Device d) {
		return update("updateTotalRunningTime", d);
	}


	@Override
	public List<Device> findGroupByCategoryId(List<Long> ids) {
		Map map = new HashMap();
		map.put("ids", ids);
		return selectList("findGroupByCategory", map);
	}

	@Override
	public List<Device> findGroupByLocationId(List<Long> ids) {
		Map map = new HashMap();
		map.put("ids", ids);
		return selectList("findGroupByLocation", map);
	}
    @Override
    public List<Device> findByIds(List<Long> ids){
        Map map = new HashMap();
        map.put("ids",ids);
        return selectList("findByIds",map);
    }

    @Override
    public List<Device> findByParam(Device device){
        return selectList("findByParam",device);
    }
    @Override
    public Long getByCategoryTotal(String categoryCode){
        return getSqlSession().selectOne("getByCategoryTotal",categoryCode);
    }
    
	// 按设备类型和地理区域--gis
	@Override
	public Pageable<Device> findByCategoryArea(Pageable<Device> pager, Device condition) {
		return findByPager(pager,"findByCategoryArea", "countByFindByCategoryArea", condition);
	}

	//========add by dingyg=============
	@Override
	public Pageable<Device> findByCondition(Pageable<Device> pager,
			Device condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public List<Device> findByExpDevice(Device device) {
		return selectList("findByExpDevice", device);
	}
	
//	protected  Pageable<Device> findByPager(Pageable<Device> pager,
//			String selectStatement, String countStatement, Device condition) {
//		return findByPager(pager, selectStatement, countStatement, condition,
//				null);
//	}
//
//	protected  Pageable<Device> findByPager(Pageable<Device> pager,
//			String selectStatement, String countStatement, Device condition,
//			Map<String, Object> otherParams) {
//		Pageable<Device> pager2=findByPager(pager,selectStatement, countStatement, condition);
//		pager.clear();
//		pager.setTotal(pager2.getTotal());
//		pager.addAll(pager2);
//		return pager;
//	}

	@Override
	public void updateLocationName() {
		update("updateLocationName");
	}
	@Override
	public List<Device> findCate(List<Long> lstSystemRuleId) {
		List<Device> lst=new ArrayList<Device>();
		if(!UtilTool.isEmptyList(lstSystemRuleId)){
			Map map = new HashMap();
			map.put("lstSystemRuleId", lstSystemRuleId);
			lst=selectList("findCate", map);
		}
		return lst;
	}

  /*  @Override
    public List<DeviceTree> findByPid(Device device) {
    	List<DeviceTree> list = selectList("findByPid",device);
    	for (DeviceTree deviceTree : list) {
    		deviceTree.setManageDepartmentId(null);
		}
        return list;
    }*/
    
    @Override
    public List<DeviceTree> findByPid(Device device) {
        return selectList("findByPid",device);
    }

    @Override
    public List<DeviceTree> findByPid(List<DeviceTree> device) {
        return selectList("findByleft",device);
    }


	@Override
	public Pageable<Device> findByConditionSys(Pageable<Device> pager,
			Device condition) {
		return findByPager(pager,"findByCondition_sys","countByCondition_sys",condition);
		
	}


	@Override
	public Pageable<Device> findByConditionPowerSys(Pageable<Device> pager,
			Device condition) {
		return findByPager(pager,"findByConditionPower_sys","countByConditionPower_sys",condition);
		
	}


	@Override
	public List<Device> findByIdSys(Long id) {
		return selectList("findById_sys", id);
	}


	@Override
	public Device findByBitNoSys(String bitNo) {
		return selectOne("findByBitNo_sys", bitNo);
	}


	@Override
	public List<Device> findListByConditionSys(Device condition) {
		return selectList("findListByCondition_sys", condition);
		
	}


	@Override
	public Pageable<Device> findByAllConditonSys(Pageable<Device> pager,
			Device condition) {
		return findByPager(pager,"findListElectricByCondition_sys","countListByCondition_sys",condition);
		
	}


	@Override
	public Pageable<Device> findDeptByElectricSys(Pageable<Device> pager,
			Device condition) {
		return findByPager(pager,"findDeptElectricByCondition_sys","countDeptElectricByCondition_sys",condition);
		
	}


	@Override
	public Pageable<Device> findDeptByEnergySys(Pageable<Device> pager,
			Device condition) {
		return findByPager(pager,"findDeptEnergyByCondition_sys","countDeptWaterByCondition_sys",condition);
		
	}


	@Override
	public Pageable<Device> findDeptByGasSys(Pageable<Device> pager,
			Device condition) {
		return findByPager(pager,"findDeptGasByCondition_sys","countDeptGasByCondition_sys",condition);
		
	}


	@Override
	public Pageable<Device> findDeptByWaterSys(Pageable<Device> pager,
			Device condition) {
		return findByPager(pager,"findDeptWaterByCondition_sys","countDeptEnergyByCondition_sys",condition);
		
	}


	@Override
	public Device getByIdSys(Long id) {
	   return selectOne("", id);
	}


	@Override
	public void insertSys(Device entity) {
		insert("", entity);
	}


	@Override
	public void updateSys(Device entity) {
		update("update_sys", entity);
		
	}


	@Override
	public void deleteSys(Device entity) {
		delete("", entity);
	}

	@Override
	public void updateAssetsCode(Map map) {
		update("updateAssetsCode",map);
	}


	@Override
	public List<Device> findFirstLevel() {
		return selectList("findFirstLevel");
	}


	@Override
	public List<Long> findChildIds(Long id) {
		return selectList("findChildIds",id);
	}
	
	@Override
	public List<Device> findAll() {
		return selectList("findAll");
	}

	@Override
	public List<Device> countDeviceBySystemRuleId(List<Long> systemRuleIds,Long floorId) {
		Map<String,Object> parameter=new HashMap<String,Object>();
		parameter.put("systemRuleIds", systemRuleIds);
		parameter.put("floorId", floorId);
		return selectList("countDeviceBySystemRuleId", parameter);
	}
	@Override
	public List<Device> countAlarmDeviceBySystemRuleId(List<Long> systemRuleIds,Long floorId) {
		Map<String,Object> parameter=new HashMap<String,Object>();
		parameter.put("systemRuleIds", systemRuleIds);
		parameter.put("floorId", floorId);
		return selectList("countAlarmDeviceBySystemRuleId", parameter);
	}

	@Override
	public List<Device> searchAllDevice() {
		return this.selectList("searchAllDevice");
	}

	@Override
	public List<Device> selectDevices(Device device) {
		return this.selectList("selectDevices",device);
	}

	@Override
	public long countDevices(Device device) {
		return this.selectOne("countDevices",device);
	}
	
}
