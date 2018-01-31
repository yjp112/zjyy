/**
 * 
 */
package com.supconit.nhgl.schedule.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.nhgl.schedule.dao.PlanLogDao;
import com.supconit.nhgl.schedule.entites.PlanLog;
import com.supconit.nhgl.schedule.service.PlanLogService;
import com.supconit.nhgl.utils.GraphUtils;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-05 11:21:48 
 * @since 
 * 
 */
@Service("Logschedule_planLog_service")
public class PlanLogServiceImpl extends hc.orm.AbstractBasicOrmService<PlanLog, Long> implements PlanLogService {
	
	private transient static final Logger	log	= LoggerFactory.getLogger(PlanLogServiceImpl.class);
	
	@Autowired
	private PlanLogDao		planLogDao;

	@Override
	public PlanLog getById(Long arg0) {
		return this.planLogDao.getById(arg0);
	}

	@Override
	public void insert(PlanLog entity) {
		planLogDao.insert(entity);
	}

	@Override
	public void update(PlanLog entity) {
		planLogDao.update(entity);
	}

	@Override
	public void delete(PlanLog entity) {
		planLogDao.delete(entity);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		return planLogDao.deleteByIds(ids);
	}

	@Override
	public Pageable<PlanLog> findLog(Pageable<PlanLog> pager,
			PlanLog condition) {
		if (("").equals(condition.getTaskName()))
			condition.setTaskName(null);
		if (("").equals(condition.getTaskVesting()))
			condition.setTaskVesting(null);
		this.planLogDao.findLogByPager(pager, condition);
		for (PlanLog planLog : pager) {
			if(planLog.getTaskVesting() != null){
				switch (planLog.getTaskVesting()) {
					case 3:
						planLog.setTypeName(GraphUtils.WATER);
						break;
					case 2:
						planLog.setTypeName(GraphUtils.ELECTRIC);
						break;
					case 4:
						planLog.setTypeName(GraphUtils.GAS);
						break;
					case 1:
						planLog.setTypeName(GraphUtils.DEVICE);
						break;
					default:
						break;
				}
			}else{
				planLog.setTypeName(null);
			}
			//执行类别 1，2，3分别对应执行一次，每日执行，按设置
			if(planLog.getExecuteType() != null){
				switch (planLog.getExecuteType()) {
					case 1:
						planLog.setExecuteTypeName(GraphUtils.EXECUTEONE);
						break;
					case 2:
						planLog.setExecuteTypeName(GraphUtils.EXECUTE_EVERY_DAY);
						break;
					case 3:
						planLog.setExecuteTypeName(GraphUtils.SETUP);
						break;
					default:
						break;
				}
			}else{
				planLog.setExecuteTypeName(null);
			}
			if(!("").equals(planLog.getSuccess())){
				if((GraphUtils.UNSUCCESS).equals(planLog.getSuccess()))
					planLog.setStrStatus("失败");
				if((GraphUtils.SUCCESS).equals(planLog.getSuccess()))
					planLog.setStrStatus("成功");
			}else{
				planLog.setStrStatus(null);
			}
			
		}
		return pager;
	}


}
