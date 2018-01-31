package com.supconit.spare.controllers;

import hc.base.domains.Pagination;
import hc.bpm.entities.UserTask;
import hc.bpm.services.UserTaskService;
import hc.safety.manager.SafetyManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
/**
 * @文件名: DispatchOrderController
 * @创建日期: 13-8-2
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
@Controller
@RequestMapping("spare/todo")
public class SpareTodoController extends BaseControllerSupport {

	@Autowired
	private UserTaskService userTaskService;
	@Autowired
	private SafetyManager safetyManager;
	
	@RequestMapping(value = "go", method = RequestMethod.GET)
    public String go(ModelMap map) {
		
          return "spare/todo/todo_list";
      
    }
    
    @ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public Pagination<UserTask> findtasklist(Pagination<UserTask> pager,UserTask condition){
		if (pager.getPageNo() < 1 || pager.getPageSize() < 1 || pager.getPageSize() > Pagination.MAX_PAGE_SIZE) return pager;
		this.userTaskService.findPendings(pager, getCurrentUserName());
		return pager;
	}
    
}
