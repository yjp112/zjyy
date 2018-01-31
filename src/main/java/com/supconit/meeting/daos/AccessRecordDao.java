package com.supconit.meeting.daos;

import java.util.List;

import com.supconit.common.daos.BaseDao;
import com.supconit.meeting.entities.AccessRecord;

public interface AccessRecordDao  extends BaseDao<AccessRecord, Long>{
	/**
	 * 查询一段时间内的刷卡记录
	 */
	List<AccessRecord> findRecordByTime(AccessRecord condition);
}
