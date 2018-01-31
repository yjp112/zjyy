
package com.supconit.base.services;

import java.util.List;

import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceTree;
import com.supconit.common.services.BaseBusinessService;

import hc.base.domains.Pageable;


public interface DeviceService extends BaseBusinessService<Device, Long> {	

	/**
	 * Query by condition
	 * @param pageNo  page number
	 * @param pageSize  page size
	 * @param condition Query condition
	 * @return
	 */
	Pageable<Device> findByCondition(Pageable<Device> pager, Device condition);
	Pageable<Device> findRunReportByCondition(Pageable<Device> pager, Device condition);
    /**
	 * findById
	 * @param object instance id
	 * @return object instance
	 */
    Device findById(Long id);

   /**
	 * delete objects 
	 * @param ids  object ID array	 
	 * @return 
	 */
	void deleteByIds(Long[] ids);

    /**
	 * save
	 * @param device  object instance 
	 * @return
	 */
	void save(Device device);
	void insert(Device device,String[] fileorignal,String[] filename,String[] delfiles,String[] fileorignal1,String[] filename1,String[] delfiles1);
	void update(Device device,String[] fileorignal,String[] filename,String[] delfiles,String[] fileorignal1,String[] filename1,String[] delfiles1);
 
	public List<Device> findByCodeName_g(Device condition);
	public Device getByDeviceCode(String deviceCode);
	public Device getByHpid(String hpid);
	public Pageable<Device> findByCodeName_g(Pageable<Device> pager, Device condition);
	public Pageable<Device> findByKuangxuan_g(Pageable<Device> pager,Device device,String[] codes);
	public List findMjZnzm(String geoareaCode ,List<Long> lstHaveOpenStatus ,List<Long> lstHaveAlarmStatus,boolean bol);
	public Device getDeviceImg(Device device);
	public List<Device> findStatusByCodes(Device device);

    boolean check(List<Long> ids);

    List<Device> findByIds(List<Long> ids);

    List<Device> findByParam(Device device);
    public List<Device> findByCategory(String categoryCode);
    public Long getByCategoryTotal(String categoryCode);
    public Pageable<Device> findByCategoryArea(Pageable<Device> pager, Device condition) ;
    public List<DeviceTree> findByParent(Device condition);
	public List<Device> findByExpDevice( Device device) ;
	public void insertForImp(List<Device> lstDevice) ;
	public List<Device> findCate(List<Long> lstSystemRuleId);
	public Pageable<Device> findDeptByElectric(Pageable<Device> pager,Device condition);
	Pageable<Device> findDeptByEnergy(Pageable<Device> pager, Device condition);
	Pageable<Device> findDeptByGas(Pageable<Device> pager, Device condition);
	Pageable<Device> findDeptByWater(Pageable<Device> pager, Device condition);
	void rebuildDeviceTree();
	List<Device> findAll();
	/**
	 * @param systemRuleIds
	 * @return CategoryId :类别ID
	 *          CategoryName:类别名称:
	 *          deviceCount:设备数量
	 *          alarmDeviceCount:报警设备数量
	 */
	public List<Device> countDeviceBySystemRuleId(List<Long> systemRuleIds,Long floorId);
	/** 手机端使用 **/
	List<Device> searchAllDevice();
	List<Device> selectDevices(Device device);
	long countDevices(Device device);
}
