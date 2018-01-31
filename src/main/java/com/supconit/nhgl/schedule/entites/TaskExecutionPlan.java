/**
 * 
 */
package com.supconit.nhgl.schedule.entites;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.supconit.common.utils.DateUtils;
import com.supconit.common.web.entities.AuditExtend;

/**
 * @author 
 * @create 2014-06-16 18:01:44 
 * @since 
 * 
 */
public class TaskExecutionPlan extends AuditExtend{

	private static final long	serialVersionUID	= 1L;
		
			
	private String taskCode;		
	private Integer executeType;	//1:执行一次,2 每天;3 每周
	private String monitorHourStart;		
	private String monitorHourEnd;		
	private String isTomorrow = "N";
	private String executeParam ;		
	private Date startDate;		
	private Date endDate;
	//虚拟字段
	private String taskName;//任务名
	private Integer taskVesting;
	private Integer monitorTaskId;//监测任务Id
	private List<Date> dateList;
	private List<Integer> taskTypeList;
	private String strTaskVesting;
	
	
	public String getStrTaskVesting() {
		return strTaskVesting;
	}

	public void setStrTaskVesting(String strTaskVesting) {
		this.strTaskVesting = strTaskVesting;
	}

	public List<Integer> getTaskTypeList() {
		return taskTypeList;
	}

	public void setTaskTypeList(List<Integer> taskTypeList) {
		this.taskTypeList = taskTypeList;
	}

	public Integer getTaskVesting() {
		return taskVesting;
	}

	public void setTaskVesting(Integer taskVesting) {
		this.taskVesting = taskVesting;
	}

	public List<Date> getDateList() {
		return dateList;
	}

	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}

	public Integer getMonitorTaskId() {
		return monitorTaskId;
	}

	public void setMonitorTaskId(Integer monitorTaskId) {
		this.monitorTaskId = monitorTaskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskCode() {
		return taskCode;
	}
	
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
				
	public Integer getExecuteType() {
		return executeType;
	}
	
	public void setExecuteType(Integer executeType) {
		this.executeType = executeType;
	}
				
	public String getMonitorHourStart() {
		return monitorHourStart;
	}
	
	public void setMonitorHourStart(String monitorHourStart) {
		this.monitorHourStart = monitorHourStart;
	}
				
	public String getMonitorHourEnd() {
		return monitorHourEnd;
	}
	
	public void setMonitorHourEnd(String monitorHourEnd) {
		this.monitorHourEnd = monitorHourEnd;
	}
				
	public String getIsTomorrow() {
		return isTomorrow;
	}

	public void setIsTomorrow(String isTomorrow) {
		this.isTomorrow = isTomorrow;
	}

	public String getExecuteParam() {
		if(StringUtils.isNotBlank(executeParam)){
			return executeParam;
		}
		if(executeType==2){//每天
			return executeParam2;
		}else if(executeType==3){//每周
			StringBuilder builder=new StringBuilder();
			if(executeParam3!=null&&executeParam3.length>0){
				builder.append(executeParam3[0]);
				for (int i = 1; i < executeParam3.length; i++) {
					builder.append(",").append(executeParam3[i]);
				}
			}
			return builder.toString();
		}
		return null;
	}
	
	public void setExecuteParam(String executeParam) {
		this.executeParam = executeParam;
	}
				
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
				
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
			
	private String executeParam2;//每天
	private String[] executeParam3;//每周
	

	public String getExecuteParam2() {
		return executeParam2;
	}

	public void setExecuteParam2(String executeParam2) {
		this.executeParam2 = executeParam2;
	}

	public String[] getExecuteParam3() {
		return executeParam3;
	}

	public void setExecuteParam3(String[] executeParam3) {
		this.executeParam3 = executeParam3;
	}

    /**生成cron表达式
     * @return
     */
    public String getCronExpression(){
    	if(executeType==null){
    		return null;
    	}
    	String second="";
    	String mi="";
    	String hour="";
    	String day="";
    	String month="";
    	String week="";
		if(executeType==1){//执行一次
			Calendar c=Calendar.getInstance();
			c.setTime(startDate);
			second=String.valueOf(c.get(Calendar.SECOND));
			mi=String.valueOf(c.get(Calendar.MINUTE));
			hour=String.valueOf(c.get(Calendar.HOUR_OF_DAY));
			day=String.valueOf(c.get(Calendar.DAY_OF_MONTH));
			month=String.valueOf(c.get(Calendar.MONTH)+1);
			week="?";
			
		}else if(executeType==2){//每天
			Calendar c=Calendar.getInstance();
			c.setTime(startDate);
			second=String.valueOf(c.get(Calendar.SECOND));
			mi=String.valueOf(c.get(Calendar.MINUTE));
			hour=String.valueOf(c.get(Calendar.HOUR_OF_DAY));
			day="*/"+getExecuteParam();
			month="*";
			week="?";
		}else if(executeType==3){//每周
			Calendar c=Calendar.getInstance();
			c.setTime(startDate);
			second=String.valueOf(c.get(Calendar.SECOND));
			mi=String.valueOf(c.get(Calendar.MINUTE));
			hour=String.valueOf(c.get(Calendar.HOUR_OF_DAY));
			day="?";
			month="*";
			/*StringBuilder builder=new StringBuilder();
			if(executeParam3!=null&&executeParam3.length>0){
				builder.append(executeParam3[0]);
				for (int i = 1; i < executeParam3.length; i++) {
					builder.append(",").append(executeParam3[i]);
				}
			}*/
			week=getExecuteParam();
		}
		return second+" "+mi+" "+hour+" "+day+" "+month+" "+week;	
    }
	

	@Override
	public String toString() {
		return "TaskExecutionPlan [taskCode=" + taskCode + ", executeType="
				+ executeType + ", monitorHourStart=" + monitorHourStart
				+ ", monitorHourEnd=" + monitorHourEnd + ", isTomorrow="
				+ isTomorrow + ", executeParam=" + executeParam
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", taskName=" + taskName + ", taskVesting=" + taskVesting
				+ ", monitorTaskId=" + monitorTaskId + ", dateList=" + dateList
				+ ", executeParam2=" + executeParam2 + ", executeParam3="
				+ Arrays.toString(executeParam3) + ",getCronExpression="+getCronExpression()+"]";
	}

	public static void main(String[] args) {
		//执行一次
		/*TaskExecutionPlan plan=new TaskExecutionPlan();
		plan.setStartDate(new Date());
		System.out.println(DateUtils.format(plan.getStartDate()));
		plan.setExecuteType(1);
		System.out.println(plan.getCronExpression());*/
		//每天
		/*TaskExecutionPlan plan=new TaskExecutionPlan();
		plan.setStartDate(new Date());
		System.out.println(DateUtils.format(plan.getStartDate()));
		plan.setExecuteType(2);
		plan.setExecuteParam("2");
		System.out.println(plan.getCronExpression());*/
		//每周
		TaskExecutionPlan plan=new TaskExecutionPlan();
		plan.setStartDate(new Date());
		System.out.println(DateUtils.format(plan.getStartDate()));
		plan.setExecuteType(3);
		plan.setExecuteParam3(new String[]{"2,3,4,5"});
		System.out.println(plan.getCronExpression());
	}
	
}
