package com.supconit.employee.todo.entities;

import hc.base.domains.OrderPart;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.supconit.common.web.entities.AuditExtend;

/**
 *	代办流程
 */
public class ExUserTask extends AuditExtend{
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "BPM_USER_TASK";
	public static final String INSERT_SQL = "";
	protected String taskId;
	protected String name;
	protected String activityId;
	protected String category;
	protected String description;
	protected String executionId;
	protected String processInstanceId;
	protected String processDefinitionId;
	protected String processDefinitionKey;
	protected Date createTime;
	protected String username;
	protected Long userId;
	protected String formUrl;
	protected String businessKey;
	protected String processDefinitionName;
	private OrderPart[] orderParts;

	/** 手机端使用 **/
	private long taskTotal;
	private long bpmTaskId;
	private String typeName;
	private String showTime;
	private String taskSearch;
	private String typeColor;

	public String getResidence()
	{
	if (null == this.createTime) {
	return "0分钟";
	}
	DateTime handle = DateTime.now();
	DateTime generate = new DateTime(this.createTime.getTime());
	Period period = new Period(generate, handle);
	StringBuilder builder = new StringBuilder();
	if (period.getYears() > 0) {
	builder.append(period.getYears()).append("年");
	}
	if (period.getMonths() > 0) {
	builder.append(period.getMonths()).append("月");
	}
	if (period.getDays() > 0) {
	builder.append(period.getDays()).append("天");
	}
	if (period.getHours() > 0) {
	builder.append(period.getHours()).append("小时");
	}
	if (period.getMinutes() > 0) {
	builder.append(period.getMinutes()).append("分钟");
	}
	if (builder.length() == 0) {
	builder.append("小于1分钟");
	}
	return builder.toString();
	}

	public String getProcessDefinitionKey()
	{
	return this.processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey)
	{
	this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionName()
	{
	return this.processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName)
	{
	this.processDefinitionName = processDefinitionName;
	}

	public Date getCreateTime()
	{
	return this.createTime;
	}

	public void setCreateTime(Date createTime)
	{
	this.createTime = createTime;
	}

	public String getFormUrl()
	{
	return this.formUrl;
	}

	public void setFormUrl(String formUrl)
	{
	this.formUrl = formUrl;
	}

	public String getActivityId()
	{
	return this.activityId;
	}

	public void setActivityId(String activityId)
	{
	this.activityId = activityId;
	}

	public String getTaskId()
	{
	return this.taskId;
	}

	public void setTaskId(String taskId)
	{
	this.taskId = taskId;
	}

	public String getName()
	{
	return this.name;
	}

	public void setName(String name)
	{
	this.name = name;
	}

	public String getDescription()
	{
	return this.description;
	}

	public void setDescription(String description)
	{
	this.description = description;
	}

	public String getExecutionId()
	{
	return this.executionId;
	}

	public void setExecutionId(String executionId)
	{
		this.executionId = executionId;
	}

	public String getProcessInstanceId()
	{
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId)
	{
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionId()
	{
		return this.processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId)
	{
		this.processDefinitionId = processDefinitionId;
	}

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public Long getUserId()
	{
		return this.userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public String getCategory()
	{
		return this.category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public OrderPart[] getOrderParts()
	{
		return this.orderParts;
	}

	public void setOrderParts(OrderPart[] orderParts)
	{
		this.orderParts = orderParts;
	}

	public String getBusinessKey()
	{
		return this.businessKey;
	}

	public void setBusinessKey(String businessKey)
	{
	this.businessKey = businessKey;
	}

	public long getTaskTotal() {
		return taskTotal;
	}

	public void setTaskTotal(long taskTotal) {
		this.taskTotal = taskTotal;
	}

	public long getBpmTaskId() {
		return bpmTaskId;
	}

	public void setBpmTaskId(long bpmTaskId) {
		this.bpmTaskId = bpmTaskId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getTaskSearch() {
		return taskSearch;
	}

	public void setTaskSearch(String taskSearch) {
		this.taskSearch = taskSearch;
	}

	public String getTypeColor() {
		return typeColor;
	}

	public void setTypeColor(String typeColor) {
		this.typeColor = typeColor;
	}
}
