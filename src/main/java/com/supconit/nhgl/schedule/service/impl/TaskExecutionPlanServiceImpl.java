/**
 * 
 */
package com.supconit.nhgl.schedule.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.supconit.common.domains.ZTreeNode;
import com.supconit.nhgl.schedule.dao.TaskExecutionPlanDao;
import com.supconit.nhgl.schedule.entites.TaskCatagory;
import com.supconit.nhgl.schedule.entites.TaskExecutionPlan;
import com.supconit.nhgl.schedule.service.MonitorTaskService;
import com.supconit.nhgl.utils.GraphUtils;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-16 18:01:44 
 * @since 
 * 
 */
@Service("dlhmc_ycynjc_rwzxjh_service")
public class TaskExecutionPlanServiceImpl extends hc.orm.AbstractBasicOrmService<TaskExecutionPlan, Long> implements com.supconit.nhgl.schedule.service.TaskExecutionPlanService {
	
	private transient static final Logger	logger	= LoggerFactory.getLogger(TaskExecutionPlanServiceImpl.class);
	
	@Autowired
	private TaskExecutionPlanDao		rwzxjhDao;
	@Autowired
	private MonitorTaskService mtService;
	
	@Override
	@Transactional(readOnly = true)
	public TaskExecutionPlan getById(Long id) {
		return this.rwzxjhDao.getById(id);
	}

	@Override
	@Transactional
	public void insert(TaskExecutionPlan entity) {
		this.rwzxjhDao.insert(entity);
	}

	@Override
	@Transactional
	public void update(TaskExecutionPlan entity) {
		this.rwzxjhDao.update(entity);
	}

	@Override
	@Transactional
	public void delete(TaskExecutionPlan entity) {
		this.rwzxjhDao.delete(entity);
	}
	
	/*
	 * @see com.supconit.nhgl.ycynjc.services.TaskExecutionPlanService#find(hc.base.domains.Pageable, com.supconit.nhgl.ycynjc.entities.TaskExecutionPlan)
	 */

	@Override
	public TaskExecutionPlan selectByTaskCode(String taskCode) {
		return rwzxjhDao.selectByTaskCode(taskCode);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TaskExecutionPlan> findBetweenTimes(TaskExecutionPlan taskExcutionPlan) {
		String[] str = taskExcutionPlan.getStrTaskVesting().split(",");
		List<Integer> list=new ArrayList<Integer>();
		for(Integer i = 0; i < str.length; i++){
			//水电气设备分别代表3，2，4，1
			if((GraphUtils.WATER).equals(str[i])){
				list.add(3);
				
			}
			else if((GraphUtils.ELECTRIC).equals(str[i])){
				list.add(2);
			}
			else if((GraphUtils.GAS).equals(str[i])){
				list.add(4);
			}
			else if((GraphUtils.DEVICE).equals(str[i])){
				list.add(1);
			}
			else
				break;
		}
		list.add(0);
		taskExcutionPlan.setTaskTypeList(list);
		List<TaskExecutionPlan> ls = rwzxjhDao.findBetweenTimes(taskExcutionPlan);
		return ls;
	}
	
//	@Transactional(rollbackFor = Exception.class)
//	public void del(Integer id) throws Exception{
//		MonitorTask monitorTask = mtService.getById(id.longValue());
//		mtService.delete(monitorTask);
//		deleteByTaskCode(monitorTask.getTaskCode());
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	public void saveInsert(MonitorTask monitorTask) throws Exception{
//		TaskExecutionPlan taskPlan = null;
//		mtService.insert(monitorTask);
//        taskPlan.setStartDate(monitorTask.getStartDate());
//        taskPlan.setEndDate(monitorTask.getEndDate());
//        taskPlan.setTaskCode(monitorTask.getTaskCode());
//        insert(taskPlan);
//	}
//	
//	@Transactional(rollbackFor = Exception.class)
//	public void saveUpdate(MonitorTask monitorTask) throws Exception{
//		mtService.update(monitorTask);
//		TaskExecutionPlan taskPlan = null;
//    	taskPlan = selectByTaskCode(monitorTask.getTaskCode());
//    	taskPlan.setStartDate(monitorTask.getStartDate());
//        taskPlan.setEndDate(monitorTask.getEndDate());
//        taskPlan.setTaskCode(monitorTask.getTaskCode());
//        update(taskPlan);
//	}
	
	@Override
	public Pageable<TaskExecutionPlan> find(Pageable<TaskExecutionPlan> pager,
			TaskExecutionPlan condition) {
		return rwzxjhDao.findByPager(pager, condition);
	}
	

	@Override
	public int deleteByTaskCode(String taskCode) {
		return rwzxjhDao.deleteByTaskCode(taskCode);
	}

	@Override
	public String buildTree(List<TaskCatagory> tclist) {
		String json = null;
		List<ZTreeNode> tcTree = new ArrayList<ZTreeNode>();
		ZTreeNode zt = null;
		for(TaskCatagory tc : tclist){
			zt = new ZTreeNode();
			zt.setTreeId(tc.getId().intValue());
			zt.setId(tc.getCatagoryCode());
			zt.setPId(tc.getParentCode());
			zt.setName(tc.getCatagoryText());
			zt.setOpen(true);
			zt.setNocheck(true);
			tcTree.add(zt);
		}
		json = JSON.toJSONString(tcTree);
		
		return json;
	}

}
