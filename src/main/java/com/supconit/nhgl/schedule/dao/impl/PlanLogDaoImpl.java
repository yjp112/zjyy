/**
 * 
 */
package com.supconit.nhgl.schedule.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.supconit.common.utils.ListUtils;
import com.supconit.nhgl.schedule.dao.PlanLogDao;
import com.supconit.nhgl.schedule.entites.PlanLog;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:26:12
 * @since 
 * 
 */
 
@Repository("jobschedule_planLog_dao")
public class PlanLogDaoImpl extends hc.orm.AbstractBasicDaoImpl<PlanLog, Long> implements PlanLogDao {

	private static final String	NAMESPACE	= PlanLog.class.getName();
	private transient static final Logger	log	= LoggerFactory.getLogger(PlanLogDaoImpl.class);

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
	
	
	public Pageable<PlanLog> findLogByPager(Pageable<PlanLog> pager, PlanLog condition) {
		return findByPager(pager, "selectLogPager", "countLogPager", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		int maxSize=1000;
		int count=0;
		for (List<Long> eachIdList : ListUtils.partition(Arrays.asList(ids), maxSize)) {
			count+=update("deleteByIds", eachIdList);
		}
		if(log.isInfoEnabled()){
			log.info("要求删除["+ids.length+"]条记录，实际删除["+count+"]条记录。");
		}
		return count;
	}

}
