package com.supconit.alarm.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.alarm.daos.AlarmAnalyseDao;
import com.supconit.alarm.entities.AlarmAnalyse;
import com.supconit.alarm.services.AlarmAnalyseService;
import com.supconit.base.daos.DepartmentDao;
import com.supconit.base.daos.DeviceDao;
import com.supconit.base.daos.GeoAreaDao;
import com.supconit.base.entities.Department;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.services.AbstractBaseBusinessService;

@Service
public class AlarmAnalyseServiceImpl extends AbstractBaseBusinessService<AlarmAnalyse, Long> implements AlarmAnalyseService{
	@Autowired
	private AlarmAnalyseDao alarmTrendAnalyseDao;
	@Autowired
	private DeviceDao deviceDao;
	@Autowired
	private GeoAreaDao geoAreaDao;
	@Autowired
	private DepartmentDao departmentDao;
	
	@Override
	public AlarmAnalyse getById(Long arg0) {
		return null;
	}

	@Override
	public void insert(AlarmAnalyse entity) {
		
	}

	@Override
	public void update(AlarmAnalyse entity) {
		
	}

	@Override
	public void deleteById(Long id) {
		
	}

	@Override
	public List<AlarmAnalyse> findList(AlarmAnalyse alarm, int type,Long default_departmentId) {
		List<AlarmAnalyse> list = new ArrayList<AlarmAnalyse>();
		switch (type) {
        case 1:
        	list = alarmTrendAnalyseDao.yearReport(alarm);
            break;
        case 2:
        	List<GeoArea> geoList = geoAreaDao.findFirstLevel();
        	for (GeoArea geoArea : geoList) {
        		AlarmAnalyse ala = new AlarmAnalyse();
        		alarm.setAreaId(geoArea.getId());
        		List<Long> areaIds = geoAreaDao.findChildIds(geoArea.getId());
        		alarm.setAreaIds(areaIds);
        		Long num = alarmTrendAnalyseDao.areaReport(alarm);
        		ala.setAlarmArea(geoArea.getAreaName());
        		ala.setAlarmNum(num);
        		list.add(ala);
			}
            break;
        case 3:
        	List<Department> deptList = departmentDao.findFirstLevel(default_departmentId);
        	for (Department department : deptList) {
        		AlarmAnalyse ala = new AlarmAnalyse();
        		alarm.setDeptId(department.getId());
        		List<Long> deptIds = departmentDao.findDeptChildIdsById(department.getId());
        		alarm.setDeptIds(deptIds);
        		Long num = alarmTrendAnalyseDao.deptReport(alarm);
        		ala.setAlarmDept(department.getName());
        		ala.setAlarmNum(num);
        		list.add(ala);
			}
            break;
        default:
        	list = alarmTrendAnalyseDao.yearReport(alarm);
            break;
        }
		return list;
	}

	@Override
	public List<AlarmAnalyse> findPie(AlarmAnalyse alarm, int type) {
		List<AlarmAnalyse> alarmList = new ArrayList<AlarmAnalyse>();
		List<Device> deviceList=deviceDao.findFirstLevel();
		if(type==2 && StringUtils.isNotEmpty(alarm.getAlarmArea())){
			List<Long> areaIds = geoAreaDao.findAreaChildIdsByName(alarm.getAlarmArea());
			alarm.setAreaIds(areaIds);
		}else if(type==3 && StringUtils.isNotEmpty(alarm.getAlarmDept())){
			List<Long> deptIds = departmentDao.findDeptChildIdsByName(alarm.getAlarmDept());
			alarm.setDeptIds(deptIds);
		}
		for (Device device : deviceList) {
			AlarmAnalyse ala = new AlarmAnalyse();
			List<Long> deviceIds = deviceDao.findChildIds(device.getId());
			alarm.setDeviceIds(deviceIds);
			Long num = alarmTrendAnalyseDao.findPie(alarm, type);
			ala.setName(device.getDeviceName());
			ala.setNum(num);
			alarmList.add(ala);
		}
		return alarmList;
	}

	
}
