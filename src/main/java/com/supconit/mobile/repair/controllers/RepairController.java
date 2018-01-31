package com.supconit.mobile.repair.controllers;

import com.supconit.base.services.*;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.repair.entities.Repair;
import com.supconit.repair.services.RepairService;
import com.supconit.repair.services.RepairSpareService;
import com.supconit.repair.services.RepairWorkerService;
import hc.base.domains.AjaxMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangwei on 16/4/13.
 */
@Controller
@RequestMapping("mobile/repair")
public class RepairController extends BaseControllerSupport {

	private static final transient Logger logger = LoggerFactory.getLogger(RepairController.class);

	@Autowired
	private RepairService repairService;
	@Autowired
	private PersonService personService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private RepairWorkerService repairWorkerService;
	@Autowired
	private RepairSpareService repairSpareService;

	/**
	 * 跳转到已办流程查询页
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String view(ModelMap model, @RequestParam(required = false) Long id) {
		Repair history = null;
		if (null != id) {
			history = repairService.getById(id);
			if (null != history.getDeviceId()) {
				history.setDeviceName(deviceService.getById(history.getDeviceId()).getDeviceName());
			}
			if (null == history.getRepairCode() || "".equals(history.getRepairCode())) {
				history.setRepairCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.REPAIR));// 初始化维修单号
			}
			model.put(PROCESS_INSTANCE_ID, history.getProcessInstanceId());
			if (history.getRepairCode() != null) {
				model.put("workerList", repairWorkerService.findByOrderCode(history.getRepairCode()));//人工信息
				model.put("spareList", repairSpareService.findByOrderCode(history.getRepairCode()));//耗材信息
			}
			model.put("taskSourceList", DictUtils.getDictList(DictTypeEnum.TASK_SOURCE)); // 诉求来源
			model.put("urgencyList", DictUtils.getDictList(DictTypeEnum.URGENCY)); // 紧急程度
			model.put("repairModeList", DictUtils.getDictList(DictTypeEnum.REPAIR_MODE)); // 维修模式
			model.put("repairWorkList", DictUtils.getDictList(DictTypeEnum.REPAIR_WORK)); // 工种
			model.put("history", history);
		}
		return "mobile/repair/view";
	}

	/**
	 * 跳转到待办流程查询页
	 */
	@RequestMapping(value = "repair", method = RequestMethod.GET)
	public String repair(ModelMap model, @RequestParam(required = false) Long id) {
		// 修改
		Repair task = null;
		if (null != id) {
			task = repairService.getById(id);
			if (null != task.getDeviceId()) {
				task.setDeviceName(deviceService.getById(task.getDeviceId()).getDeviceName());
			}
			if (null == task.getRepairCode() || "".equals(task.getRepairCode())) {
				task.setRepairCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.REPAIR));// 初始化维修单号
			}
			model.put(PROCESS_INSTANCE_ID, task.getProcessInstanceId());
			if (task.getRepairCode() != null) {
				model.put("workerList", repairWorkerService.findByOrderCode(task.getRepairCode()));//人工信息
				model.put("spareList", repairSpareService.findByOrderCode(task.getRepairCode()));//耗材信息
			}
			model.put("taskSourceList", DictUtils.getDictList(DictTypeEnum.TASK_SOURCE)); // 诉求来源
			model.put("urgencyList", DictUtils.getDictList(DictTypeEnum.URGENCY)); // 紧急程度
			model.put("repairModeList", DictUtils.getDictList(DictTypeEnum.REPAIR_MODE)); // 维修模式
			model.put("repairWorkList", DictUtils.getDictList(DictTypeEnum.REPAIR_WORK)); // 工种
			model.put("task", task);
		}
		return "mobile/repair/repair";
	}

	/**
	 * 跳转到待办流程查询页
	 */
	@ResponseBody
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public AjaxMessage submit(Repair task) {
		String bpmId = task.getBpm_ts_id();//按钮Id
		String res = "";
		if (StringUtils.isNotEmpty(res)) {
			return AjaxMessage.error(res);
		}
		if (task.getId() == null) {
			return AjaxMessage.error(AjaxMessage.STATUS_ERROR);
		} else {
			if (task.getStatus() == 1 && "SQD_STEP2".equals(bpmId)) {//组长派单给维修人
				Long repairPersonId = task.getRepairPersonId();
				if (null == repairPersonId) return AjaxMessage.error("未设置现场负责人！");
				String handlePersonCode = this.personService.getById(repairPersonId).getCode();
				if ("".equals(handlePersonCode))
					return AjaxMessage.error("维修人‘" + task.getRepairPersonName() + "’不存在！");
				task.setHandlePersonCode(handlePersonCode);
				task.setStatus(2);
			} else if (task.getStatus() == 2 && "SQD_STEP3".equals(bpmId)) {//维修完成给报修人评价
				Long contactId = task.getContactId();
				if (null == contactId) return AjaxMessage.error("报修人‘" + task.getContact() + "’不存在！");
				String handlePersonCode = this.personService.getById(contactId).getCode();
				if ("".equals(handlePersonCode)) return AjaxMessage.error("报修人‘" + task.getContact() + "’不存在！");
				task.setHandlePersonCode(handlePersonCode);
				if(task.getResult()==null||"".equals(task.getResult()))
				{
					task.setResult("无");
				}
				task.setStatus(3);
			}else if (task.getStatus() == 3 && "SQD_STEP4".equals(bpmId)) {//报修人评价
				task.setStatus(4);
			}
			if (task.getCategoryName()!=null&&!"".equals(task.getCategoryName()))
			{
				task.setProcessInstanceName(task.getCategoryName().substring(0,2)+"诉求单:" + task.getTaskCode().toString());
			}
			else
			{
				task.setProcessInstanceName("诉求单:" + task.getTaskCode().toString());
			}
			this.repairService.submitProcess(task);
			return AjaxMessage.success("提交成功");
		}
	}
}