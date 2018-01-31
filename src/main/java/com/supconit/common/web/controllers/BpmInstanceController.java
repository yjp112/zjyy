package com.supconit.common.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.entities.ExProcessInstance;
import com.supconit.common.web.entities.ScoMessage;

import hc.base.domains.Pagination;
import hc.bpm.domains.Activity;
import hc.bpm.services.ProcessService;
import jodd.util.StringUtil;

@Controller
@RequestMapping("bpm/instance")
public class BpmInstanceController {
	private static final transient Logger logger = LoggerFactory.getLogger(BpmInstanceController.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ProcessService processService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "platform/bpm/instance/list";
	}

	/**
	 * 后台流程实例列表
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public Pagination<ExProcessInstance> list(Pagination<ExProcessInstance> pager,
			@RequestParam(required = false) String key) {
		ProcessInstanceQuery query = this.runtimeService.createProcessInstanceQuery();
		if (StringUtil.isNotBlank(key)) {
			query.processDefinitionKey(key);
		}
		long total = query.count();
		pager.setTotal(total);
		if ((total != 0L) && (pager.getOffset() <= total)) {
			List<ProcessInstance> list = query.orderByProcessInstanceId().desc().listPage(pager.getOffset(), pager.getPageSize());
			List<ExProcessInstance> exList = new ArrayList<ExProcessInstance>();
			List<String> current = new ArrayList<String>();// 当前节点
			for (ProcessInstance processInstance : list) {
				ExProcessInstance ex = new ExProcessInstance();
				ex.setProcessDefinitionId(processInstance.getProcessDefinitionId());
				ex.setBusinessKey(processInstance.getBusinessKey());
				ex.setId(processInstance.getId());
				current.add(processInstance.getId());
				exList.add(ex);
			}
			if (current.size() > 0) {
				Map<String, String> result = new HashMap<String, String>();
				for (String id : current) {
					Activity[] activities = this.processService.currentActivities(id);
					if (null != activities) {
						StringBuilder builder = new StringBuilder();
						for (Activity activity : activities) {
							builder.append(activity.getName()).append(",");
						}
						builder.deleteCharAt(builder.length() - 1);
						result.put(id, builder.toString());
					}
				}
				for (ExProcessInstance ex : exList) {
					ex.setCurrentNode(result.get(ex.getId()));
				}
			}
			pager.addAll(exList);
		}
		return pager;
	}

	/**
	 * 后台流程实例删除
	 */
	@ResponseBody
	@RequestMapping(value = { "delete" }, method = RequestMethod.POST)
	public ScoMessage deleteInstance(@RequestParam(value = "id", required = false) String id) {
		if (StringUtil.isBlank(id)) {
			return ScoMessage.error("错误的参数");
		}
		try {
			this.processService.deleteProcessInstance(id);
			return ScoMessage.success(ScoMessage.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			return ScoMessage.error(ScoMessage.DEFAULT_FAIL_MSG);
		}
	}

}
