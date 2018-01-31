package com.supconit.common.web.controllers;

import hc.bpm.entities.ApprovalRecord;
import hc.bpm.entities.UserTask;
import hc.bpm.exceptions.BpmException;
import hc.bpm.services.ApprovalRecordService;
import hc.bpm.services.UserTaskService;
import hc.business.organization.entities.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.NoneEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.NoneStartEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskDelegateExpressionActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.honeycomb.business.authorization.services.UserService;
import com.supconit.honeycomb.business.organization.services.PersonService;

@Controller
@RequestMapping({ "/bpm/monitor" })
public class BpmMonitorController {

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PersonService personService;

	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private UserTaskService userTaskService;

	@Autowired
	private ApprovalRecordService approvalRecordService;

	@RequestMapping({ "pic" })
	public String pic(String processInstanceId, ModelMap model) {
		model.put("processInstanceId", processInstanceId);
		return "platform/bpm/monitor/pic";
	}

	@RequestMapping({ "render" })
	public String render(String processInstanceId, ModelMap model, HttpServletResponse response) {
		ProcessInstance processInstance = (ProcessInstance) this.runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc().list();//获取流程走过的节点，并按照节点生成先后顺序排序
		if(null !=activityInstances && activityInstances.size()>0){
			Map hisExecutionMap = new HashMap();
			for (HistoricActivityInstance activityInstance : activityInstances) {
				hisExecutionMap.put(activityInstance.getActivityId(), activityInstance);
			}
			model.put("hisExecutionMap", hisExecutionMap);
		}
		ApprovalRecord condition = new ApprovalRecord();
		condition.setProcessInstanceId(processInstanceId);
		List<ApprovalRecord> approvalRecords = this.approvalRecordService.find(condition);
		String processDefinitionId;
		if (null == processInstance) {
			if (CollectionUtils.isEmpty(approvalRecords))
				throw new BpmException("error process intance id.");
			processDefinitionId = ((ApprovalRecord) approvalRecords.get(0)).getProcessDefinitionId();
		} else {
			processDefinitionId = processInstance.getProcessDefinitionId();
		}

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) this.repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		List<String> highFlows = new ArrayList<String>();
		highFlows =getHighLightedFlows(processDefinition,activityInstances);
		model.put("highFlows", highFlows);
		List<ActivityImpl> activities = processDefinition.getActivities();
		List<ActivityImpl> roundActivities = new ArrayList<ActivityImpl>();
		List<ActivityImpl> rectangleActivities = new ArrayList<ActivityImpl>();
		List<ActivityImpl> serviceActivities = new ArrayList<ActivityImpl>();
		List<ActivityImpl> exclusiveGatewayActivities = new ArrayList<ActivityImpl>();
		for (ActivityImpl activity : activities) {
			if (((activity.getActivityBehavior() instanceof NoneStartEventActivityBehavior))
					|| ((activity.getActivityBehavior() instanceof NoneEndEventActivityBehavior))) {
				roundActivities.add(activity);
			} else if ((activity.getActivityBehavior() instanceof UserTaskActivityBehavior)) {
				rectangleActivities.add(activity);
			} else if ((activity.getActivityBehavior() instanceof ServiceTaskDelegateExpressionActivityBehavior)) {
				serviceActivities.add(activity);
			} else if ((activity.getActivityBehavior() instanceof ExclusiveGatewayActivityBehavior)) {
				exclusiveGatewayActivities.add(activity);
			}
		}
         
		model.put("activities", activities);
		model.put("roundActivities", roundActivities);
		model.put("rectangleActivities", rectangleActivities);
		model.put("serviceActivities", serviceActivities);
		model.put("exclusiveGatewayActivities", exclusiveGatewayActivities);
		if (null != processInstance) {
			List<Execution> executions = this.runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
            List<UserTask> tasks= this.userTaskService.findByExecutionId(processInstanceId);
			Map executionMap = new HashMap();
			for (Execution execution : executions) {
				executionMap.put(execution.getActivityId(), execution);
			}
			for (UserTask task : tasks) {
				User user= userService.getByUsername(task.getUsername());
				if(null!=user){
					Long personId= user.getPersonId();
					ExPerson person=(ExPerson)personService.getById(personId.longValue());
					task.setDescription(person.getName());
				}
			}
			model.put("executionMap", executionMap);
			model.put("tasks", tasks);
		}

		Set transitionIds = new HashSet();
		if (!CollectionUtils.isEmpty(approvalRecords)) {
			Map approvalMap = new HashMap();
			for (ApprovalRecord ar : approvalRecords) {
				if(null !=ar.getTaskId()){
					User user= userService.getByUsername(ar.getUsername());
					if(null!=user){
						Long personId= user.getPersonId();
						ExPerson person=(ExPerson)personService.getById(personId.longValue());
						ar.setDescription(person.getName());
					}
					List list = (List) approvalMap.get(ar.getActivityId());
					if (null == list) {
						list = new ArrayList();
						approvalMap.put(ar.getActivityId(), list);
					}
					list.add(ar);
					transitionIds.addAll(ar.getOutingTransitions());
				}
			}
			model.put("approvalMap", approvalMap);
			model.put("transitionIds", transitionIds);
		}

		response.setContentType("text/javascript");
		return "platform/bpm/monitor/render";
	}
	
	
	public List<String> getHighLightedFlows(
			ProcessDefinitionEntity processDefinitionEntity,
			List<HistoricActivityInstance> historicActivityInstances) {
		List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId

		for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
			ActivityImpl activityImpl = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i)
							.getActivityId());// 得到节点定义的详细信息
			List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
			ActivityImpl sameActivityImpl1 = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i + 1)
							.getActivityId());// 将后面第一个节点放在时间相同节点的集合里
			sameStartTimeNodes.add(sameActivityImpl1);
			for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
				HistoricActivityInstance activityImpl1 = historicActivityInstances
						.get(j);// 后续第一个节点
				HistoricActivityInstance activityImpl2 = historicActivityInstances
						.get(j + 1);// 后续第二个节点
				if (activityImpl1.getStartTime().equals(
						activityImpl2.getStartTime())) {// 如果第一个节点和第二个节点开始时间相同保存
					ActivityImpl sameActivityImpl2 = processDefinitionEntity
							.findActivity(activityImpl2.getActivityId());
					sameStartTimeNodes.add(sameActivityImpl2);
				} else {// 有不相同跳出循环
					break;
				}
			}
			List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
			for (PvmTransition pvmTransition : pvmTransitions) {// 对所有的线进行遍历
                
				ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
						.getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
				if (sameStartTimeNodes.contains(pvmActivityImpl)) {
					highFlows.add(pvmTransition.getId());
				}
			}

		}
		return highFlows;

	}

	public List<Map<String, Boolean>> getHighLightedActivities(
			ProcessDefinitionEntity processDefinition,
			List<HistoricActivityInstance> historicActivityInstances) {

		List<Map<String, Boolean>> highLightedActivities = new ArrayList<Map<String, Boolean>>();
		int activityCount = historicActivityInstances.size();
		Map<String, Boolean> activityMap = new HashMap<String, Boolean>();
		activityMap.put(
				historicActivityInstances.get(
						historicActivityInstances.size() - 1).getActivityId(),
				true);
		highLightedActivities.add(activityMap);
		boolean findCurrActivity = true;// 是否查询并发任务节点
		for (int j = activityCount - 2; j >= 0; j--) {// 判断前面节点是否与
			Map<String, Boolean> activityMap2 = new HashMap<String, Boolean>();
			HistoricActivityInstance activityImpl1 = historicActivityInstances
					.get(j);// 前面第一个节点
			if (findCurrActivity) {
				HistoricActivityInstance activityImpl2 = historicActivityInstances
						.get(j + 1);// 当前节点
				if (activityImpl1.getStartTime().equals(
						activityImpl2.getStartTime())) {
					String type = activityImpl1.getActivityType();
					// 排除系统自动执行节点
					if ("userTask".equals(type) || "endEvent".equals(type)) {
						activityMap2.put(activityImpl1.getActivityId(), false);
					} else {

						activityMap2.put(activityImpl1.getActivityId(), false);
					}

				} else {
					activityMap2.put(activityImpl1.getActivityId(), false);
					findCurrActivity = false;
				}

				highLightedActivities.add(activityMap2);
			} else {
				activityMap2.put(activityImpl1.getActivityId(), false);
				highLightedActivities.add(activityMap2);
			}

		}

		return highLightedActivities;
	}
}