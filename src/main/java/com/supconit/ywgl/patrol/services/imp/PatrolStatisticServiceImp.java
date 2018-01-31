package com.supconit.ywgl.patrol.services.imp;

import hc.base.domains.Pageable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.ywgl.patrol.daos.PatrolDetailsDao;
import com.supconit.ywgl.patrol.daos.PatrolPersonDao;
import com.supconit.ywgl.patrol.daos.PatrolPlanDao;
import com.supconit.ywgl.patrol.entities.PatrolDetails;
import com.supconit.ywgl.patrol.entities.PatrolPerson;
import com.supconit.ywgl.patrol.entities.PatrolPlan;
import com.supconit.ywgl.patrol.entities.PatrolStatistic;
import com.supconit.ywgl.patrol.services.PatrolStatisticService;

@Service
public class PatrolStatisticServiceImp implements PatrolStatisticService{
	@Autowired
	private PatrolPersonDao patrolPersonDao ;
	@Autowired
	private PatrolPlanDao patrolPlanDao;
	@Autowired
	private PatrolDetailsDao patrolDetailsDao;
	
	@Override
	public PatrolStatistic initStatistic(PatrolStatistic patrolStatistic) {
		 //获取巡更人员
		List<PatrolPerson> PatrolPersonList=patrolPersonDao.findAll();
		//通过巡更人员的ID和用户输入的时间段取得巡更人的巡更计划次数
		 List<Long> planCount=getPatrolPlanCount(patrolStatistic,PatrolPersonList);		
		//通过巡更人员的ID和用户输入的时间段取得巡更人的实际巡更次数 
		 List<Long> realityCount=getRealityCount(patrolStatistic,PatrolPersonList);
		//计算每个巡更人员的漏检次数
		 List<Long> leakCount=getLeakCount(PatrolPersonList,planCount,realityCount);
		 //为统计
		 patrolStatistic.setPatrolPerson(PatrolPersonList); 
		 patrolStatistic.setPlanCount(planCount); 
		 patrolStatistic.setRealityCount(realityCount);
		 patrolStatistic.setLeakCount(leakCount);
		return patrolStatistic;
	}
	
	//计算漏检次数
	@SuppressWarnings("unused")
	private List<Long> getLeakCount(
			List<PatrolPerson> patrolPersonList,
			List<Long> planCount, List<Long> realityCount){
		List<Long> leakCountList=new ArrayList<Long>();
		for(int i=0;i<patrolPersonList.size();i++){
			long leakCount=0;
			leakCount=planCount.get(i)-realityCount.get(i); 
			leakCountList.add(leakCount);
		}
		return leakCountList;
	}
	//获取实际巡更次数
	private List<Long> getRealityCount(PatrolStatistic patrolStatistic, List<PatrolPerson> patrolPersonList) {
		 List<Long> realityCount=new ArrayList<Long>();
		 for(PatrolPerson patrolPerson:patrolPersonList){
				PatrolDetails patrolDetails=new PatrolDetails();
				patrolDetails.setPersonId(patrolPerson.getId()); 
				patrolDetails.setStartTime(patrolStatistic.getStatisticStarTime());
				patrolDetails.setEndTime(patrolStatistic.getStatisticEndTime());
				
				long RealityCount=patrolDetailsDao.findDetailsCuont(patrolDetails);
				realityCount.add(RealityCount); 
			}
		return realityCount;
	}
	//获取巡更计划次数
	private List<Long> getPatrolPlanCount(PatrolStatistic patrolStatistic, List<PatrolPerson> patrolPersonList) {  
		 List<Long> planCountList=new ArrayList<Long>(); 
		for(PatrolPerson patrolPerson:patrolPersonList){
			PatrolPlan patrolPlan=new PatrolPlan();
			patrolPlan.setPersonId(patrolPerson.getId()); 
			patrolPlan.setStartDate(patrolStatistic.getStatisticStarTime());
			patrolPlan.setEndDate(patrolStatistic.getStatisticEndTime()); 
			long planCount=patrolPlanDao.findPlanCuont(patrolPlan);
			planCountList.add(planCount); 
		}
		return planCountList;
	}

	

	
}
