package com.supconit.base.daos;

import com.supconit.base.entities.DeviceTree;

import hc.base.domains.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceTree;
import com.supconit.common.daos.BaseDao;

import hc.base.domains.Pageable;

public interface DeviceDao extends BaseDao<Device, Long> {

	public Pageable<Device> findByCategoryArea(Pageable<Device> pager, Device condition);

	public Pageable<Device> findRunReportByCondition(Pageable<Device> page, Device condition);

	public int deleteByIds(Long[] ids);

	public Device findById(Long id);

	public List<Device> findByCodeName_g(Device condition);

	public List<Device> findByCategory(String categoryCode);

	public List<Device> findVideos();

	public Device getByDeviceCode(String deviceCode); 
	
	Device getByHpid(String hpid) ;
	
	public List<Device> findByHpid(String hpid) ;
	
	public Pageable<Device> findByCodeName_g(Pageable<Device> pager, Device condition);

	public Pageable<Device> findByKuangxuan_g(Pageable<Device> pager, Device device, String[] codes);

	public Long findCountByCategoryId(long id);

	public List findMjZnzm(String geoareaCode ,List<Long> lstHaveOpenStatus ,List<Long> lstHaveAlarmStatus, boolean bol);

	public Device getDeviceImg(Device device);

	public List<Device> findStatusByCodes(Device device);

	public Long countByDevice(Device device);

	public List<Device> getDeviceUseCount(Device device);

	public int updateTotalRunningTime(Device d);

	public List<Device> findGroupByCategoryId(List<Long> ids);

	public List<Device> findGroupByLocationId(List<Long> ids);

    List<Device> findByIds(List<Long> ids);

    List<Device> findByParam(Device device);
    public Long getByCategoryTotal(String categoryCode);
    //======add by dingyg=========
	public Pageable<Device> findByCondition(Pageable<Device> pager,
			Device condition);
	public List<Device> findByExpDevice(Device device);
	public void updateLocationName();
	public List<Device> findCate(List<Long> lstSystemRuleId) ;
	
	
	/**
	 * 
	 */
	public Pageable<Device> findByConditionSys(Pageable<Device> pager, Device condition);
	public Pageable<Device> findByConditionPowerSys(Pageable<Device> pager, Device condition);
	public List<Device> findByIdSys(Long id);
	
	public Device findByBitNoSys(String bitNo);
	public List<Device> findListByConditionSys(Device condition);
	public Pageable<Device> findByAllConditonSys(Pageable<Device> pager,
			Device condition);
	Pageable<Device> findDeptByElectricSys(Pageable<Device> pager, Device condition);
	Pageable<Device> findDeptByEnergySys(Pageable<Device> pager, Device condition);
	Pageable<Device> findDeptByGasSys(Pageable<Device> pager, Device condition);
	Pageable<Device> findDeptByWaterSys(Pageable<Device> pager, Device condition);
	public Device getByIdSys(Long arg0);
	public void insertSys(Device entity);
	public void updateSys(Device entity);
	public void deleteSys(Device entity);

    public List<DeviceTree> findByPid(Device device);
    public List<DeviceTree> findByPid(List<DeviceTree> device);
	Long countsByDevice(Long id);  
	
	public void updateAssetsCode(Map map);

	List<Device> findAll();
	public List<Device> findByCode(String deviceCode); 
	/**
	 * 查询第一层设备
	 */
	List<Device> findFirstLevel();
	/**
	 * 查询设备子类别
	 */
	List<Long> findChildIds(Long id);
	
	/**按类别统计设备个数
	 * @param systemRuleIds
	 * @return
	 */
	List<Device> countDeviceBySystemRuleId(List<Long> systemRuleIds,Long floorId);
	List<Device> countAlarmDeviceBySystemRuleId(List<Long> systemRuleIds,Long floorId);
	
	/** 手机端使用 **/
	List<Device> searchAllDevice();
	List<Device> selectDevices(Device device);
	long countDevices(Device device);
}
