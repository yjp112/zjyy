package com.supconit.hikvision.services;

import com.supconit.hikvision.entities.AccessCount;

import java.util.List;

public interface AccessCountService {

	/** 手机端使用 **/
	List<AccessCount> searchMonthAttendances(AccessCount condition);

	List<AccessCount> searchUnusualMonthAttendances(AccessCount condition);

	AccessCount countMonthAttendances(AccessCount condition);

	AccessCount searchAttendance(AccessCount condition);
}
