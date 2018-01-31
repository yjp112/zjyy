package com.supconit.employee.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.employee.todo.entities.BpmApprovalRecord;
import com.supconit.employee.todo.entities.ExUserTask;
import com.supconit.employee.todo.services.BpmApprovalRecordService;
import com.supconit.employee.todo.services.ExUserTaskService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.bpm.services.UserTaskService;
import hc.safety.manager.SafetyManager;

/**
 * 待办、已办事项控制类
 * @author yuhuan
 * @日期 2015/08
 */
@Controller
@RequestMapping("workspace/todo")
public class TodoController extends BaseControllerSupport {

	@Autowired
	private ExUserTaskService exUserTaskService;
	@Autowired
	private SafetyManager safetyManager;
	@Autowired
	private BpmApprovalRecordService bpmApprovalRecordService;
	@Autowired
	private UserTaskService userTaskService;
	
	/**
	 * 我的待办
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
    public String go(ModelMap map) {
          return "employee/todo/todo_list";
    }
    
    @ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public Pageable<ExUserTask> findtasklist(Pagination<ExUserTask> pager,ExUserTask condition){
		if (pager.getPageNo() < 1 || pager.getPageSize() < 1 || pager.getPageSize() > Pagination.MAX_PAGE_SIZE) return pager;
		condition.setUsername(this.safetyManager.getCurrentUser().getPrincipal());
		return exUserTaskService.findByPage(pager, condition);  
	}
    
	/**
	 * 撤销
	 */
	@RequestMapping(value = "withdraw", method = RequestMethod.GET)
    public String withdraw(Long id) {
    	userTaskService.withdraw(id);
    	return "employee/todo/todo_list";
    }
    
    /**
     * 历史流程
     */
    @RequestMapping(value = "hisList", method = RequestMethod.GET)
    public String hisList(ModelMap map) {
          return "employee/todo/his_list";
    }
    
    @ResponseBody
	@RequestMapping(value = "hisList", method = RequestMethod.POST)
	public Pageable<BpmApprovalRecord> findhisList(Pagination<BpmApprovalRecord> pager,BpmApprovalRecord condition){
    	condition.setUsername(getCurrentUserName());
		return bpmApprovalRecordService.findByPage(pager, condition);
	}
}