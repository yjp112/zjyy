package com.supconit.ywgl.patrol.entities;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PatrolStatistic {
	//得到所有巡更人员
	private List<PatrolPerson> patrolPerson;
	//通过开始与结束时间得到巡更人员的所有巡更次数
	private List<Long> planCount;
	//巡更人员实际的巡更次数
	private List<Long> realityCount;
	//巡更人员的漏检次数
	private List<Long> leakCount;
	//统计开始时间
	private Date StatisticStarTime;
	//统计结束时间
	private Date StatisticEndTime;
	public List<PatrolPerson> getPatrolPerson() {
		return patrolPerson;
	}
	public void setPatrolPerson(List<PatrolPerson> patrolPerson) {
		this.patrolPerson = patrolPerson;
	}
	
	
	public List<Long> getPlanCount() {
		return planCount;
	}
	public void setPlanCount(List<Long> planCount) {
		this.planCount = planCount;
	}
	public List<Long> getRealityCount() {
		return realityCount;
	}
	public void setRealityCount(List<Long> realityCount) {
		this.realityCount = realityCount;
	}
	public List<Long> getLeakCount() {
		return leakCount;
	}
	public void setLeakCount(List<Long> leakCount) {
		this.leakCount = leakCount;
	}
	public Date getStatisticStarTime() {
		return StatisticStarTime;
	}
	public void setStatisticStarTime(Date statisticStarTime) {
		StatisticStarTime = statisticStarTime;
	}
	public Date getStatisticEndTime() {
		return StatisticEndTime;
	}
	public void setStatisticEndTime(Date statisticEndTime) {
		StatisticEndTime = statisticEndTime;
	}
	
	
}
