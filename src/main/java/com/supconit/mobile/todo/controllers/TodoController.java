package com.supconit.mobile.todo.controllers;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
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
@RequestMapping("mobile/todo")
public class TodoController extends BaseControllerSupport {

	@Autowired
	private ExUserTaskService exUserTaskService;

	@Autowired
	private BpmApprovalRecordService bpmApprovalRecordService;

	@Autowired
	private SafetyManager safetyManager;



	@ModelAttribute("prepareExUserTask")
	public ExUserTask prepareExUserTask(){return new ExUserTask();}

	@ModelAttribute("prepareBpmApprovalRecord")
	public BpmApprovalRecord prepareBpmApprovalRecord(){return new BpmApprovalRecord();}
	/**
	 * 我的待办
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(ModelMap map) {
          return "mobile/todo/list";
    }

	/**
	 * AJAX获取列表数据。
	 * @param exUserTask   查询条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list_task")
	public List<ExUserTask> list_task(@FormBean(value = "condition",modelCode = "prepareExUserTask")ExUserTask exUserTask)
	{
		List<ExUserTask> exUserTasks=null;
		exUserTask.setUsername(this.safetyManager.getCurrentUser().getPrincipal());
		exUserTask.setProcessDefinitionKey("DEVICE_NEW_REPAIR_PROCESS");
		exUserTasks=this.exUserTaskService.selectUserTasks(exUserTask);
		long total=this.exUserTaskService.countUserTasks(exUserTask);
		for (ExUserTask e:exUserTasks)
		{
			e.setTaskTotal(total);
			String description=e.getDescription();
			if(description!=null&&!"".equals(description))
			{
				String[] ds=description.split(":");
				e.setTypeName(ds[0].substring(0,2));
			}
			else
			{
				e.setTypeName("其他");
			}
			String typeColor=DictUtils.getDictValue(DictTypeEnum.BPM_TYPE,e.getTypeName());
			if(typeColor==null||"".equals(typeColor))
			{
				typeColor="icon-qt";
			}
			e.setTypeColor(typeColor);
		}
		return exUserTasks;
	}

	/**
	 * AJAX获取列表数据。
	 * @param bpmApprovalRecord   查询条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list_history")
	public List<BpmApprovalRecord> list_history(@FormBean(value = "condition",modelCode = "prepareBpmApprovalRecord")BpmApprovalRecord bpmApprovalRecord)
	{
		List<BpmApprovalRecord> bpmApprovalRecords=null;
		bpmApprovalRecord.setUsername(this.safetyManager.getCurrentUser().getPrincipal());
		bpmApprovalRecord.setProcessDefinitionKey("DEVICE_NEW_REPAIR_PROCESS");
		bpmApprovalRecords=this.bpmApprovalRecordService.selectHistoryBpms(bpmApprovalRecord);
		long total=this.bpmApprovalRecordService.countHistoryBpms(bpmApprovalRecord);
		for (BpmApprovalRecord b:bpmApprovalRecords)
		{
			String formUrl=b.getFormUrl();
			if(formUrl!=null||!"".equals(formUrl)){
				if(formUrl.indexOf("repair")>-1)
				{
					formUrl="/jcds/mobile/repair/view?id="+b.getBusinessKey();
				}
				else
				{
					formUrl="error";
				}
			}
			b.setFormUrl(formUrl);
			b.setHistoryTotal(total);
			String description=b.getDescription();
			if(description!=null&&!"".equals(description))
			{
				String[] ds=description.split(":");
				b.setTypeName(ds[0].substring(0,2));
			}
			else
			{
				b.setTypeName("其他");
			}
			String typeColor=DictUtils.getDictValue(DictTypeEnum.BPM_TYPE,b.getTypeName());
			if(typeColor==null||"".equals(typeColor))
			{
				typeColor="icon-qt";
			}
			b.setTypeColor(typeColor);
		}
		return bpmApprovalRecords;
	}
}