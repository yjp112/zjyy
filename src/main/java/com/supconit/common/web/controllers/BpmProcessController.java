package com.supconit.common.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hc.base.domains.AjaxMessage;
import hc.base.domains.Pagination;
import jodd.util.StringUtil;

@Controller
@RequestMapping("bpm/process")
public class BpmProcessController {
	private static final transient Logger logger = LoggerFactory.getLogger(BpmProcessController.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;

	@RequestMapping({ "list" })
	public String list() {
		return "platform/bpm/process/list";
	}

	@ResponseBody
	@RequestMapping(value = { "list" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public Pagination<Map<String, Object>> list(Pagination<Map<String, Object>> pager,
			@RequestParam(required = false) String name, @RequestParam(required = false) String key) {
		if ((pager.getPageNo() < 1) || (pager.getPageSize() < 1) || (pager.getPageSize() > 1000))
			return pager;
		ProcessDefinitionQuery query = this.repositoryService.createProcessDefinitionQuery();
		if (StringUtil.isNotBlank(name))
			query.processDefinitionNameLike("%" + name + "%");
		if (StringUtil.isNotBlank(key))
			query.processDefinitionKeyLike("%" + key + "%");
		long total = query.count();
		if ((total != 0L) && (pager.getOffset() <= total)) {
			List<ProcessDefinition> list = ((ProcessDefinitionQuery) query.orderByDeploymentId().desc())
					.listPage(pager.getOffset(), pager.getPageSize());
			pager.setTotal(total);
			if (!CollectionUtils.isEmpty(list)) {
				for (ProcessDefinition pd : list) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", pd.getId());
					map.put("name", pd.getName());
					map.put("version", Integer.valueOf(pd.getVersion()));
					map.put("key", pd.getKey());
					map.put("description", pd.getDescription());
					pager.add(map);
				}
			}
		}
		return pager;
	}

	@ResponseBody
	@RequestMapping(value = { "delete" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public AjaxMessage delete(@RequestParam(value = "id", required = false) String id) {
		if (StringUtil.isBlank(id)) {
			return AjaxMessage.error("错误的参数");
		}
		try {
			ProcessDefinition processDefinition = this.repositoryService.getProcessDefinition(id);
			if (null == processDefinition)
				return AjaxMessage.error("错误的ID");
			long count = this.runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinition.getId())
					.count();
			if (count > 0L) {
				return AjaxMessage.error("该流程下尚有在途流程实例，无法删除！");
			}
			this.repositoryService.deleteDeployment(processDefinition.getDeploymentId());
			return AjaxMessage.success("删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxMessage.error(e.getMessage());
		}
	}

}
