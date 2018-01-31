package com.supconit.employee.todo.entities;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.supconit.common.web.entities.AuditExtend;
import hc.base.domains.OrderPart;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 *	历史流程
 */
public class BpmApprovalRecord extends AuditExtend{
	private static final long serialVersionUID = 1L;

	protected Long userId;
	protected String username;
	protected Long userTaskId;
	protected String taskId;
	protected String executionId;
	protected String processInstanceId;
	protected String processDefinitionId;
	protected String processDefinitionKey;
	protected String processDefinitionName;
	protected String activityId;
	protected String activityName;
	protected String formUrl;
	protected String transitionIds;
	protected String businessKey;
	protected String description;
	protected String operateName;
	protected Date handleTime;
	protected String handleComment;
	protected Date generateTime;
	protected boolean saves;
	protected OrderPart[] orderParts;
	protected String personName;

	/** 手机端使用 **/
	private long historyTotal;
	private long bpmHistoryId;
	private String typeName;
	private String showTime;
	private String historySearch;
	private String typeColor;

	public String getResidence() {
		if(this.handleTime != null && this.generateTime != null) {
			DateTime handle = new DateTime(this.handleTime.getTime());
			DateTime generate = new DateTime(this.generateTime.getTime());
			Period period = new Period(generate, handle);
			StringBuilder builder = new StringBuilder();
			if(period.getYears() > 0) {
				builder.append(period.getYears()).append("年");
			}

			if(period.getMonths() > 0) {
				builder.append(period.getMonths()).append("月");
			}

			if(period.getDays() > 0) {
				builder.append(period.getDays()).append("天");
			}

			if(period.getHours() > 0) {
				builder.append(period.getHours()).append("小时");
			}

			if(period.getMinutes() > 0) {
				builder.append(period.getMinutes()).append("分钟");
			}

			if(builder.length() == 0) {
				builder.append("小于1分钟");
			}

			return builder.toString();
		} else {
			return "0分钟";
		}
	}

	public String getProcessDefinitionKey() {
		return this.processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getFormUrl() {
		return this.formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserTaskId() {
		return this.userTaskId;
	}

	public void setUserTaskId(Long userTaskId) {
		this.userTaskId = userTaskId;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionName() {
		return this.processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public String getActivityId() {
		return this.activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperateName() {
		return this.operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public Date getHandleTime() {
		return this.handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getHandleComment() {
		return this.handleComment;
	}

	public void setHandleComment(String handleComment) {
		this.handleComment = handleComment;
	}

	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public boolean isSaves() {
		return this.saves;
	}

	public void setSaves(boolean saves) {
		this.saves = saves;
	}

	public OrderPart[] getOrderParts() {
		return this.orderParts;
	}

	public void setOrderParts(OrderPart[] orderParts) {
		this.orderParts = orderParts;
	}

	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Date getGenerateTime() {
		return this.generateTime;
	}

	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}

	public String getTransitionIds() {
		return this.transitionIds;
	}

	public void setTransitionIds(String transitionIds) {
		this.transitionIds = transitionIds;
	}

	public long getHistoryTotal() {
		return historyTotal;
	}

	public void setHistoryTotal(long historyTotal) {
		this.historyTotal = historyTotal;
	}

	public long getBpmHistoryId() {
		return bpmHistoryId;
	}

	public void setBpmHistoryId(long bpmHistoryId) {
		this.bpmHistoryId = bpmHistoryId;
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

	public String getHistorySearch() {
		return historySearch;
	}

	public void setHistorySearch(String historySearch) {
		this.historySearch = historySearch;
	}

	public String getTypeColor() {
		return typeColor;
	}

	public void setTypeColor(String typeColor) {
		this.typeColor = typeColor;
	}
}
