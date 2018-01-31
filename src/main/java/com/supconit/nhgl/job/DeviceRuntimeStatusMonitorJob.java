package com.supconit.nhgl.job;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.ExpressionParserUtils;
import com.supconit.honeycomb.base.context.SpringContextHolder;
//import com.supconit.montrol.entity.MReadBlock;
//import com.supconit.montrol.service.IBlockService;
import com.supconit.nhgl.schedule.entites.CriteriaDetail;
import com.supconit.nhgl.schedule.entites.MonitorObjScore;
import com.supconit.nhgl.schedule.entites.MonitorTask;
import com.supconit.nhgl.schedule.entites.TaskExecutionPlan;

/**
 * 设备运行状态监控JOB
 * 通知信息模板里可用到的velocity变量列表:
 * $taskName 任务名称
 * $taskCatagory 任务分类
 * $alarmTime 报警时间
 * $monitorObject 监控对象
 * $deviceRuntimeStatus 设备运行状况
 * $scoreDeduction 扣分数
 * 
 * $monitorTime 监控时段，如2014-09-18日22点至 次日 7 点
 * $deviceLocation 设备安装位置
 */
public class DeviceRuntimeStatusMonitorJob {
	private transient static final Logger log = LoggerFactory
			.getLogger(DeviceRuntimeStatusMonitorJob.class);

//	private static IBlockService blockService=SpringContextHolder.getBean(IBlockService.class);
	private static JdbcTemplate jdbcTemplate=new JdbcTemplate((DataSource) SpringContextHolder.getBean("dataSource"));

	/**
	 * 设备运行状态的监控。（即设备的开关监控）
	 */
	public void doJob(final String taskCode) {
		
		if(StringUtils.isBlank(taskCode)){
			log.error("任务编码不能为空,JOB异常退出。");
			return;
		}
		// 1.查找监控设备//select HP_ID from NH_MONITOR_OBJECT where TASK_CODE=''
		// 2.查找监控设备点位
		/*
		 * 位号类型   值 描述 
		 * TAGTYPE    value desc 
		 *            13 开关量 
		 *            14 模拟量 
		 * 位号性质   值 描述 
		 * ALARMPOINT 1 报警点
		 *            0 运行点 
		 *            2 起停点
		 */

		String querySql="SELECT"
				+"	t1.device_id,"
				+"	t2.device_name,"
				+"	t2.location_name,"
				+"	t3.tagname,"
				+"	t3.tagdesc,"
				+"	t3.monitorid "
				+"FROM"
				+"	NH_MONITOR_OBJECT t1 "
				+"INNER JOIN device t2 ON t2.id = t1.device_id "
				+"INNER JOIN M_MONITOROBJECTTAG t3 ON t3.MONITORID = t2.HPID "
				+"WHERE"
				+"	t3.TAGTYPE = 13 "
				+"AND t3.ALARMPOINT = 0 "
				+"AND t1.TASK_CODE =? ";
		final Map<String, List<String>> tagNamesMap = new HashMap<String, List<String>>();
		jdbcTemplate.query(querySql, new String[]{taskCode},new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String tagName = rs.getString("tagname");
				String deviceId = rs.getString("device_id");
				String deviceName = rs.getString("device_name");
				String locationName = rs.getString("location_name");
				String hpid = rs.getString("monitorid");
				String key=deviceId+"@"+hpid+"@"+deviceName+"@"+locationName;
				List<String> tagNameList = tagNamesMap.get(key);
				if (tagNameList == null) {
					tagNameList = new ArrayList<String>();
					tagNamesMap.put(key, tagNameList);
				}
				tagNameList.add(tagName);
			}
		});
		if (tagNamesMap.isEmpty()) {
			log.error("系统查找不到任务编号【" + taskCode + "】的点位信息，JOB异常退出。");
			return;
		}
		// 查询评分细则
		String querySql2 = "select id,cr_condition,cr_score from NH_CRITERIA_DETAIL where TASK_CODE=?";
		final List<CriteriaDetail> criteriaDetails = new ArrayList<CriteriaDetail>();
		jdbcTemplate.query(querySql2,new String[]{taskCode}, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				CriteriaDetail criteriaDetail = new CriteriaDetail();
				criteriaDetail.setId(rs.getLong("id"));
				criteriaDetail.setCrCondition(rs.getString("cr_condition"));
				criteriaDetail.setCrScore(rs.getBigDecimal("cr_score"));
				criteriaDetails.add(criteriaDetail);
			}
		});
		if (criteriaDetails.size() <= 0) {
			log.error("系统查找不到任务编号【" + taskCode + "】的评分细则，JOB异常退出。");
			return;
		}

		final List<MonitorObjScore> executeResultList = new ArrayList<MonitorObjScore>();
		for (Map.Entry<String, List<String>> entry : tagNamesMap.entrySet()) {
			String[] deviceIdHpid=entry.getKey().split("@");
			String deviceId =deviceIdHpid[0] ;
			String hpId =deviceIdHpid[1] ;
			String deviceName =deviceIdHpid[2] ;
			String locationName =deviceIdHpid[3] ;
			List<String> tagNames = entry.getValue();
			if (log.isDebugEnabled()) {
				log.debug("====开始读取设备【deviceName="+deviceName+",deviceId="+deviceId+",hpid=" + hpId + "】的开关运行状态,位号为" + tagNames
						+ "====");
			}
//			List<MReadBlock> lstReadBlocks = blockService.readBlocks(tagNames);
//			// 计算评分 select * from NH_CRITERIA_DETAIL where TASK_CODE=?
//			for (MReadBlock r : lstReadBlocks) {
//				if (log.isDebugEnabled()) {
//					log.debug("====已经读取到设备【deviceName="+deviceName+",deviceId="+deviceId+",hpid=" + hpId + "】的开关运行状态,结果为【tagName=" + r.getBlock()
//							+ ",value=" + r.getValue() + "】" + "====");
//				}
//				int hitCount=0;
//				if(hitCount>0){
//					break;//每个设备暂时只扣一次分
//				}
//				String tagValue="OFF";
//				
//				 if ("1".equals(r.getValue()) ||
//				 "1.0".equals(r.getValue())) {// open // "开"; status="ON";
//					 tagValue="ON";
//				 }
//				 
//				for (CriteriaDetail criteriaDetail : criteriaDetails) {
//					Number score = (Number)getScore(criteriaDetail.getCrCondition(),
//							String.valueOf(criteriaDetail.getCrScore()
//									.doubleValue()), "0", "tagValue", tagValue);
//					if(new BigDecimal(score.toString()).compareTo(new BigDecimal(0))<0){
//						MonitorObjScore obj = new MonitorObjScore();
//						obj.setTaskCode(taskCode);
//						obj.setDeviceId(deviceId);
//						obj.setDeviceName(deviceName);
//						obj.setLocationName(locationName);
//						obj.setHpId(hpId);
//						obj.setValue(tagValue);
//						obj.setCrCondition(criteriaDetail.getCrCondition());
//						obj.setScore(score.toString());
//						executeResultList.add(obj);
//						hitCount++;
//						//每个设备暂时只扣一次分
//						break;
//					}
//					/*
//					 * String status=""; if ("1".equals(r.getValue()) ||
//					 * "1.0".equals(r.getValue())) {// open // "开"; status="ON";
//					 * } else { // "关"; status="OFF"; }
//					 */
//				}
//			}
//			// 输出评分值 NH_MONITOR_OBJ_SCORE

		}
		if(executeResultList.size()<=0){
			return;
		}
		final Timestamp executeTime=new Timestamp(new Date().getTime());
		String insertSql = "insert into NH_MONITOR_OBJ_SCORE (TASK_CODE, DEVICE_ID, VALUE, SCORE, CR_CONDITION,EXECUTE_TIME)"
				+ "values(?,?,?,?,?,?)";
		int[] count = jdbcTemplate.batchUpdate(insertSql,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int rowIndex)
							throws SQLException {
						MonitorObjScore obj = executeResultList.get(rowIndex);
						ps.setString(1, obj.getTaskCode());
						ps.setString(2, obj.getDeviceId());
						ps.setString(3, obj.getValue());
						ps.setString(4, obj.getScore());
						ps.setString(5, obj.getCrCondition());
						ps.setTimestamp(6, executeTime);

					}

					@Override
					public int getBatchSize() {
						return executeResultList.size();
					}
				});
		//查询报警模板；
		String querySql3="select t.*,t2.CATAGORY_TEXT CATEGORY_TEXT from NH_MONITOR_TASK t INNER JOIN NH_TASK_CATAGORY t2 on t.TASK_TYPE=t2.ID where t.task_code=?";
		final MonitorTask task=jdbcTemplate.query(querySql3, new String[]{taskCode}, new ResultSetExtractor<MonitorTask>(){

			@Override
			public MonitorTask extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				MonitorTask task=new MonitorTask();
				task.setTaskCode(rs.getString("task_code"));
				task.setTaskName(rs.getString("task_name"));
				task.setNoticeTmpl(rs.getString("NOTICE_TMPL"));
				task.setTaskType(rs.getLong("TASK_TYPE"));// 任务类型
				task.setCategoryText(rs.getString("CATEGORY_TEXT"));//任务类型名称
				task.setTaskVesting(rs.getInt("TASK_VESTING"));		
				return task;
			}});
		//产生报警信息
		String insertSql2="insert into NH_ENERGY_ALARM (TASK_CODE,TASK_NAME,DEVICE_ID, ALARM_TIME, CONTENT) values (?,?,?,?,?)";
		int[] count2=jdbcTemplate.batchUpdate(insertSql2, new BatchPreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement ps, int rowIndex)
					throws SQLException {
				MonitorObjScore obj = executeResultList.get(rowIndex);
				ps.setString(1, task.getTaskCode());
				ps.setString(2, task.getTaskName());
				ps.setString(3, obj.getDeviceId());
				ps.setTimestamp(4, executeTime);
				Map<String, Object> params=new HashMap<String, Object>();
				//TODO 模板中可以使用的变量待定
				 params.put("taskName", task.getTaskName());//任务名称
				 params.put("taskCatagory", task.getCategoryText());//任务分类
				 params.put("alarmTime", DateUtils.format(new Date(executeTime.getTime())));//报警时间
				 params.put("monitorObject",obj.getDeviceName());// 监控对象
				 params.put("deviceRuntimeStatus",obj.getValue());// 设备运行状况
				 params.put("scoreDeduction",obj.getScore());// 扣分数
				 
				 params.put("monitorTime",getMonitorTime(taskCode));//监控时段，如2014-09-18日22点至 次日 7 点
				 params.put("deviceLocation",obj.getLocationName());//设备安装位置
				ps.setString(5, ExpressionParserUtils.mergeVelocityTemplateString(task.getNoticeTmpl(), params));
				
			}

			@Override
			public int getBatchSize() {
				return executeResultList.size();
			}});
		if(executeResultList!=null&&executeResultList.size()>0){
			String logInsertSql="insert into NH_TASK_LOG(TASK_CODE,TASK_NAME,TASK_TYPE,TASK_VESTING,INVOKE_TIME) values(?,?,?,?,?)";
			jdbcTemplate.update(logInsertSql, task.getTaskCode(),task.getTaskName(),task.getTaskType(),task.getTaskVesting(),executeTime);
		}
	}


	public static Object getScore(String conditionExpression,
			String trueValue, String falseValue, String variableName,
			String variableValue) {
		Map<String, String> variableMap = new HashMap<String, String>();
		variableMap.put(variableName, variableValue);
		return ExpressionParserUtils.getSprintElExpressionValue(conditionExpression
				+ "?" + trueValue + ":" + falseValue, variableMap);
	}
	/**获取监控时间段的描述信息，如2014-09-18日22点至 次日 7 点
	 * @param plan
	 * @return
	 */
	private String getMonitorTime(String taskCode){
		String querySql = "SELECT ID,TASK_CODE,EXECUTE_TYPE,MONITOR_HOUR_START,MONITOR_HOUR_END FROM NH_TASK_EXECUTION_PLAN where TASK_CODE=?";
		List<TaskExecutionPlan> plans = jdbcTemplate.query(querySql,
				new String[] { taskCode }, new RowMapper<TaskExecutionPlan>() {

					@Override
					public TaskExecutionPlan mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						TaskExecutionPlan plan = new TaskExecutionPlan();
						plan.setTaskCode(rs.getString("TASK_CODE"));
						plan.setMonitorHourStart(rs
								.getString("MONITOR_HOUR_START"));
						plan.setMonitorHourEnd(rs.getString("MONITOR_HOUR_END"));
						return plan;
					}
				});
		if (plans == null) {
			log.error("task_code=" + taskCode + ",TaskExecutionPlan 不存在。");
			return "";
		}
		if (plans.size() > 1) {
			log.error("task_code=" + taskCode + ",TaskExecutionPlan 不唯一。");
			return "";
		}
		TaskExecutionPlan plan = plans.get(0);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date start = DateUtils.addHour(cal.getTime(),
				Integer.parseInt(plan.getMonitorHourStart()));
		Date end = DateUtils.addHour(cal.getTime(),
				Integer.parseInt(plan.getMonitorHourEnd()));
		if ("Y".equals(plan.getIsTomorrow())) {
			end = DateUtils.addDay(end, 1);
			return DateUtils.format(start, "yyyy-MM-dd日HH点")+DateUtils.format(end, "至 次日HH点");
		}else{
		   return DateUtils.format(start, "yyyy-MM-dd日HH点")+DateUtils.format(start, "至HH点");
		}		
	}	
}
