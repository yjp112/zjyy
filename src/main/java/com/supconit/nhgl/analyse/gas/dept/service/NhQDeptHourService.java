package com.supconit.nhgl.analyse.gas.dept.service;

import java.util.List;

import com.supconit.nhgl.analyse.gas.dept.entities.NhQDeptHour;



public interface NhQDeptHourService {
	List<NhQDeptHour> getDayofDeptGas(NhQDeptHour em);
	List<NhQDeptHour> getAllSubGas(NhQDeptHour em);
	List<NhQDeptHour> getDayofSubGas(NhQDeptHour em);
	NhQDeptHour getTwoDayofSubGas(NhQDeptHour em);
}
