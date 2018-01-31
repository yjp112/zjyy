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
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.ExpressionParserUtils;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.nhgl.schedule.entites.CriteriaDetail;
import com.supconit.nhgl.schedule.entites.MonitorObjScore;
import com.supconit.nhgl.schedule.entites.MonitorTask;
import com.supconit.nhgl.schedule.entites.TaskExecutionPlan;
import com.supconit.nhgl.utils.NumberFormatUtils;

/**设备用能消耗监控JOB
 * 通知信息模板里可用到的velocity变量列表:
 * $taskName 任务名称
 * $taskCatagory 任务分类
 * $alarmTime 报警时间
 * $monitorObjectList 监控对象列表
 * $totalEnergy 耗能总量
 * $scoreDeduction 扣分数
 * 
 * $monitorTime 监控时段，如2014-09-18日22点至 次日 7 点
 * $baselineNum 能耗指标值
 * $overstepNum 超过指标值
 */
public class EnergyMonitorJob {
	private transient static final Logger log = LoggerFactory
			.getLogger(EnergyMonitorJob.class);

	private static JdbcTemplate jdbcTemplate = new JdbcTemplate(
			(DataSource) SpringContextHolder.getBean("dataSource"));

	/**
	 * 设备用能消耗监控。
	 */
	public void doJob(String taskCode) {

		if (StringUtils.isBlank(taskCode)) {
			log.error("任务编码不能为空,JOB异常退出。");
			return;
		}
		// 查询报警模板；
		String querySql3 = "select t.*,t2.CATAGORY_TEXT CATEGORY_TEXT from NH_MONITOR_TASK t INNER JOIN NH_TASK_CATAGORY t2 on t.TASK_TYPE=t2.ID where t.task_code=?";
		List<MonitorTask> tasks = jdbcTemplate.query(querySql3,
				new String[] { taskCode }, new RowMapper<MonitorTask>() {
					@Override
					public MonitorTask mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						MonitorTask task = new MonitorTask();
						task.setTaskCode(rs.getString("task_code"));
						task.setTaskName(rs.getString("task_name"));
						task.setNoticeTmpl(rs.getString("NOTICE_TMPL"));
						task.setTaskType(rs.getLong("TASK_TYPE"));// 任务类型
						task.setCategoryText(rs.getString("CATEGORY_TEXT"));//任务类型名称
						task.setTaskVesting(rs.getInt("TASK_VESTING"));	// 设备运行状态：1；电：2；水：3；气：4；
						return task;
					}

				});
		if (tasks == null) {
			log.error("task_code=" + taskCode + ",MonitorTask 不存在。");
			return;
		}
		if (tasks.size() > 1) {
			log.error("task_code=" + taskCode + ",MonitorTask 不唯一。");
			return;
		}
		final MonitorTask task = tasks.get(0);
		String querySql = "SELECT ID,TASK_CODE,EXECUTE_TYPE,MONITOR_HOUR_START,MONITOR_HOUR_END FROM NH_TASK_EXECUTION_PLAN where TASK_CODE=?";
		// String querySql3="select * from NH_MONITOR_TASK where task_code=?";
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
			return;
		}
		if (plans.size() > 1) {
			log.error("task_code=" + taskCode + ",TaskExecutionPlan 不唯一。");
			return;
		}
		final TaskExecutionPlan plan = plans.get(0);
		String tableName = "NH_ELECTRIC_METER";
		if (task.getTaskVesting().intValue() == 2) {
			tableName = "NH_ELECTRIC_METER";
		} else if (task.getTaskVesting().intValue() == 3) {
			tableName = "NH_WATER_METER";
		} else if (task.getTaskVesting().intValue() == 4) {
			tableName = "NH_WATER_METER";// TODO 暂未定义
		}
		String sumSql = "SELECT sum(t1.INCREMENTAL) INCREMENTAL " + "FROM "
				+ "	" + tableName + " t1 "
				+ "INNER JOIN DEVICE t2 ON t1.BIT_NO = t2.EXTENDED1 "
				+ "INNER JOIN NH_MONITOR_OBJECT t3 ON t3.DEVICE_ID = t2.ID and t3.TASK_CODE=? "
				+ "where t1.COLLECT_TIME >= ? and t1.COLLECT_TIME<?";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date start = DateUtils.addHour(cal.getTime(),
				Integer.parseInt(plan.getMonitorHourStart()));
		Date end = DateUtils.addHour(cal.getTime(),
				Integer.parseInt(plan.getMonitorHourEnd()) + 1);
		if ("Y".equals(plan.getIsTomorrow())) {
			end = DateUtils.addDay(end, 1);
		}
		BigDecimal sumIncrement = jdbcTemplate.queryForObject(sumSql,
				new Object[] { taskCode,start, end }, BigDecimal.class);
		if(sumIncrement==null){
			sumIncrement=new BigDecimal(0);
		}
		if(log.isDebugEnabled()){
			log.debug("*************************************************************************");
			log.debug("query sql["+sumSql+"]");
			log.debug("parameters["+taskCode+","+DateUtils.format(start)+","+DateUtils.format(end)+"]");
			log.debug("query result["+sumIncrement.toPlainString()+"]");
			log.debug("*************************************************************************");
		}
		
		// 查询评分细则
		String querySql2 = "select id,cr_condition,cr_score from NH_CRITERIA_DETAIL where TASK_CODE=?";
		final List<CriteriaDetail> criteriaDetails = jdbcTemplate.query(
				querySql2, new String[] { taskCode },
				new RowMapper<CriteriaDetail>() {
					@Override
					public CriteriaDetail mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						CriteriaDetail criteriaDetail = new CriteriaDetail();
						criteriaDetail.setId(rs.getLong("id"));
						criteriaDetail.setCrCondition(rs.getString("cr_condition"));
						criteriaDetail.setCrScore(rs.getBigDecimal("cr_score"));
						return criteriaDetail;
					}

				});
		if (criteriaDetails==null||criteriaDetails.size() <= 0) {
			log.error("系统查找不到任务编号【" + taskCode + "】的评分细则，JOB异常退出。");
			return;
		}

		final List<MonitorObjScore> executeResultList = new ArrayList<MonitorObjScore>();
		for (CriteriaDetail criteriaDetail : criteriaDetails) {
			Number score = (Number) getScore(criteriaDetail.getCrCondition(),
					criteriaDetail.getCrScore().toPlainString(), "0",
					"sum", sumIncrement);
			if (new BigDecimal(score.toString()).compareTo(new BigDecimal(0))<0) {
				MonitorObjScore obj = new MonitorObjScore();
				obj.setTaskCode(taskCode);
				obj.setValue(sumIncrement.toPlainString());
				obj.setCrCondition(criteriaDetail.getCrCondition());
				obj.setScore(score.toString());
				executeResultList.add(obj);
			}
		}

		String queryDeviceIdSql = "SELECT t.task_code,t.device_Id,t2.device_name " + "FROM "
				+ "NH_MONITOR_OBJECT t INNER JOIN DEVICE t2 on t.DEVICE_ID=t2.id " + "where t.task_code=?";
		final StringBuilder deviceIdBuilder = new StringBuilder();
		final StringBuilder deviceNameBuilder = new StringBuilder();
		jdbcTemplate.query(queryDeviceIdSql, new String[] { taskCode },
				new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						deviceIdBuilder.append(rs.getString("device_Id"))
								.append(";");
						deviceNameBuilder.append(rs.getString("device_name"))
						.append(";");
					}
				});
		String deviceIds = "";
		String deviceNames = "";
		if (deviceIdBuilder.length() > 0) {
			deviceIds = deviceIdBuilder.substring(0,
					deviceIdBuilder.length() - 1);
			deviceNames = deviceNameBuilder.substring(0,
					deviceNameBuilder.length() - 1);
		}
		final String deviceIdsFinal = deviceIds;
		final String deviceNamesFinal = deviceNames;
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
						ps.setString(2, deviceIdsFinal);
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

		// 产生报警信息
		String insertSql2 = "insert into NH_ENERGY_ALARM (TASK_CODE,TASK_NAME,DEVICE_ID, ALARM_TIME, CONTENT) values (?,?,?,?,?)";
		int[] count2 = jdbcTemplate.batchUpdate(insertSql2,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int rowIndex)
							throws SQLException {
						MonitorObjScore obj = executeResultList.get(rowIndex);
						ps.setString(1, task.getTaskCode());
						ps.setString(2, task.getTaskName());
						ps.setString(3, deviceIdsFinal);
						ps.setTimestamp(4, executeTime);
						Map<String, Object> params = new HashMap<String, Object>();
						// TODO 模板中可以使用的变量待定
						// params.put("", alue)

						 params.put("taskName", task.getTaskName());//任务名称
						 params.put("taskCatagory", task.getCategoryText());//任务分类
						 params.put("alarmTime", DateUtils.format(new Date(executeTime.getTime())));//报警时间
						 params.put("monitorObjectList",deviceNamesFinal);// 监控对象列表
						 params.put("totalEnergy",obj.getValue());// 耗能总量
						 params.put("scoreDeduction",obj.getScore());// 扣分数
						 
						 params.put("monitorTime",getMonitorTime(plan));//监控时段，如2014-09-18日22点至 次日 7 点
						 params.put("baselineNum",NumberFormatUtils.formatThousand2(getBaselineNum(obj.getCrCondition())[0]));//能耗指标值
						 params.put("overstepNum",NumberFormatUtils.formatThousand2(getOverstepNum(getBaselineNum(obj.getCrCondition())[0],obj.getValue())));//超过指标值
						ps.setString(5, ExpressionParserUtils
								.mergeVelocityTemplateString(
										task.getNoticeTmpl(), params));

					}

					@Override
					public int getBatchSize() {
						return executeResultList.size();
					}
				});
		if(executeResultList!=null&&executeResultList.size()>0){
			String logInsertSql="insert into NH_TASK_LOG(TASK_CODE,TASK_NAME,TASK_TYPE,TASK_VESTING,INVOKE_TIME) values(?,?,?,?,?)";
			jdbcTemplate.update(logInsertSql, task.getTaskCode(),task.getTaskName(),task.getTaskType(),task.getTaskVesting(),executeTime);
		}
	}

	public static Object getScore(String conditionExpression, String trueValue,
			String falseValue, String variableName, Number variableValue) {
		Map<String, Number> variableMap = new HashMap<String, Number>();
		variableMap.put(variableName, variableValue);
		return (Object) ExpressionParserUtils.getSprintElExpressionValue(
				conditionExpression + "?" + trueValue + ":" + falseValue,
				variableMap);
	}

	public static Object getScore2(String conditionExpression,
			String trueValue, String falseValue, String variableName,
			String variableValue) {
		Map<String, String> variableMap = new HashMap<String, String>();
		variableMap.put(variableName, variableValue);
		return ExpressionParserUtils.getSprintElExpressionValue(
				conditionExpression + "?" + trueValue + ":" + falseValue,
				variableMap);
	}

	/**解析基准值
	 * @param crCondition
	 * @return
	 */
	private String[] getBaselineNum(String crCondition){
		String start="";
		String end="";
		if(StringUtils.isBlank(crCondition)){
		      return new String[]{start,end};
		}
	    if(crCondition.indexOf("and") > 0){
			      //#sum>=5 and #sum<=20
			start=StringUtils.trim((crCondition.substring(crCondition.indexOf(">=")+2,crCondition.indexOf("and"))));
			end=StringUtils.trim(crCondition.substring(crCondition.indexOf("<=")+2));
		}else{
			     //#sum>20
			start=StringUtils.trim(crCondition.substring(crCondition.indexOf(">")+1));
		}
       return new String[]{start,end};
	}
	
	/**计算超过基准值
	 * @param baselineNum
	 * @param totalEnergy
	 * @return
	 */
	private String getOverstepNum(String baselineNum,String totalEnergy){
		if(StringUtils.isBlank(baselineNum)||StringUtils.isBlank(totalEnergy)){
			return "";
		}
		try {
			return new BigDecimal(totalEnergy).subtract(new BigDecimal(baselineNum)).toPlainString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "";
		}
	}
	
	/**获取监控时间段的描述信息，如2014-09-18日22点至 次日 7 点
	 * @param plan
	 * @return
	 */
	private String getMonitorTime(TaskExecutionPlan plan){
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
	public static void main(String[] args) {
		System.out.println(EnergyMonitorJob.getScore(
				"#tagValue>5 and #variable<20", "100", "-100", "tagValue",
				-10.2));
		// System.out.println(DeviceRuntimeStatusMonitorJob.getScore2("#tagValue","100",
		// "-100","tagValue", true));
		// System.out.println(DeviceRuntimeStatusMonitorJob.getScore2("#tagValue","100",
		// "-100","tagValue", false));
	}
}
