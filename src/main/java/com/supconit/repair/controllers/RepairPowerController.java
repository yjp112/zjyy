package com.supconit.repair.controllers;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.AttachmentService;
import com.supconit.base.services.DeviceService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.repair.entities.Repair;
import com.supconit.repair.services.RepairService;
import com.supconit.repair.services.RepairSpareService;
import com.supconit.repair.services.RepairWorkerService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@Scope("prototype")
@RequestMapping("repair/power")
public class RepairPowerController extends BaseControllerSupport {

	@Autowired
	private RepairService repairService;
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private RepairWorkerService repairWorkerService;
	@Autowired
	private RepairSpareService repairSpareService;
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 跳转到列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<GeoArea> geoAreaList = geoAreaService.findTree();
		model.put("geoAreaList", geoAreaList);// 地理区域树
		model.put("taskSourceList", DictUtils.getDictList(DictTypeEnum.TASK_SOURCE));
		model.put("urgencyList", DictUtils.getDictList(DictTypeEnum.URGENCY));
		return "repair/power/list";
	}
	
	/**
	 * 从生命周期跳转到列表
	 */
	@RequestMapping(value = "lifeCycleList", method = RequestMethod.GET)
	public String lifeCycleList(String deviceCode,ModelMap model) {
		List<GeoArea> geoAreaList = geoAreaService.findTree();
		model.put("geoAreaList", geoAreaList);// 地理区域树
		model.put("taskSourceList", DictUtils.getDictList(DictTypeEnum.TASK_SOURCE));
		model.put("urgencyList", DictUtils.getDictList(DictTypeEnum.URGENCY));
		model.put("deviceCode", deviceCode);
		return "repair/power/lifeCycleList";
	}

	/**
	 * 列表查询
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Pageable<Repair> dolist(Repair repair, Pagination<Repair> pager, ModelMap model) {
		boolean b = hasAdminRole();
		if (!b) {
			repair.setCreateId(getCurrentPersonId());
		}
		repair.setDeviceType(0);
		return repairService.findByPage(pager, repair);
	}
	
	/**
	 * 从生命周期列表查询
	 */
	@RequestMapping(value = "lifeCycleList", method = RequestMethod.POST)
	@ResponseBody
	public Pageable<Repair> lifeCycleList(Repair repair, Pagination<Repair> pager, ModelMap model) {
		boolean b = hasAdminRole();
		if (!b) {
			repair.setCreateId(getCurrentPersonId());
		}
		return repairService.findByPage(pager, repair);
	}

	/**
	 * 跳转到编辑页
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Boolean viewOnly, ModelMap model,
			@RequestParam(required = false) Long id) {
		// 修改
		Repair task = null;
		if (null != id) {
			task = repairService.getById(id);
			if(null != task.getDeviceId()){
				task.setDeviceName(deviceService.getById(task.getDeviceId()).getDeviceName());
			}
			model.put("listAttachments", attachmentService.getAttachmentByFid(id,Constant.ATTACHEMENT_REPAIR));
		} else {
			task = new Repair();
			task.setStatus(0);
			task.setTaskSource(1);// 初始化诉求来源
			task.setRepairMode(0);//设置默认维修方式 ：委外
			task.setRepairWorry(0);//设置默认是否抢修 ：否
			task.setTaskCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.TASK));// 初始化诉求单号
			task.setRepairCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.REPAIR));// 初始化维修单号
			task.setContact(getCurrentPersonName());
			task.setRepairDate(new Date());
			copyCreateInfo(task);
		}
		model.put("workerList", repairWorkerService.findByOrderCode(task.getRepairCode()));//人工信息
		model.put("spareList", repairSpareService.findByOrderCode(task.getRepairCode()));//耗材信息
		model.put("taskSourceList", DictUtils.getDictList(DictTypeEnum.TASK_SOURCE)); // 诉求来源
		model.put("urgencyList", DictUtils.getDictList(DictTypeEnum.URGENCY)); // 紧急程度
		model.put("causeTypeList", DictUtils.getDictList(DictTypeEnum.CAUSE_TYPE)); // 原因归类
		model.put("repairModeList", DictUtils.getDictList(DictTypeEnum.REPAIR_MODE)); // 维修模式
		model.put("repairWorryList", DictUtils.getDictList(DictTypeEnum.REPAIR_WORRY)); // 是否抢修
		model.put("repairWorkList", DictUtils.getDictList(DictTypeEnum.REPAIR_WORK)); // 工种
		model.put(PROCESS_INSTANCE_ID, task.getProcessInstanceId());
		model.put("task", task);
		if (viewOnly == null)
			viewOnly = false;
		model.put("viewOnly", viewOnly);
		if (!viewOnly) {
			return "repair/power/edit";
		} else {
			return "repair/power/view";
		}
	}

}
