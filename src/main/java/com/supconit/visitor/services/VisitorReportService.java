package com.supconit.visitor.services;

import java.util.List;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.visitor.entities.VisitorReport;

public interface VisitorReportService extends BaseBusinessService<VisitorReport, Long> {
	/**
	 * 查询
	 * type:1.部门统计;2.年度统计;3.事由统计
	 */
	List<VisitorReport> findList(VisitorReport dept, int type);

}
