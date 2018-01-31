package com.supconit.mobile.tool.controllers;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.employee.todo.entities.BpmApprovalRecord;
import com.supconit.employee.todo.entities.ExUserTask;
import com.supconit.employee.todo.services.BpmApprovalRecordService;
import com.supconit.employee.todo.services.ExUserTaskService;
import hc.mvc.annotations.FormBean;
import hc.safety.manager.SafetyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangwei on 16/4/8.
 */
@Controller
@RequestMapping("mobile/tool")
public class ToolController extends BaseControllerSupport {

	/**
	 * 我的待办
	 */
	@RequestMapping(value = "tool-4", method = RequestMethod.GET)
	public String tool4(ModelMap map) {
		return "mobile/tool/tool-4";
	}

}