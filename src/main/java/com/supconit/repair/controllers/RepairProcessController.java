package com.supconit.repair.controllers;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.repair.entities.Repair;
import com.supconit.repair.services.RepairService;
import com.supconit.repair.services.RepairSpareService;
import com.supconit.repair.services.RepairWorkerService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@Scope("prototype")
@RequestMapping("repair/process")
public class RepairProcessController extends BaseControllerSupport {

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
	//配置文件长度
	private String fileLength="";
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<GeoArea> geoAreaList = geoAreaService.findTree();
		model.put("geoAreaList", geoAreaList);// 地理区域树
		model.put("taskSourceList", DictUtils.getDictList(DictTypeEnum.TASK_SOURCE));
		model.put("urgencyList", DictUtils.getDictList(DictTypeEnum.URGENCY));
		return "repair/process/list";
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
		repair.setDeviceType(1);
		repair.setProcessInstanceId("0");
		return repairService.findByPage(pager, repair);
	}

	/**
	 * 跳转到编辑页
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Boolean viewOnly, ModelMap model,
			@RequestParam(required = false) Long id,String openStyle) {
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
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "维修");
		}
		model.put("fileLength", fileLength);
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
		model.put("openStyle", openStyle==null? "":openStyle);
		if (viewOnly == null)
			viewOnly = false;
		model.put("viewOnly", viewOnly);
		if (!viewOnly) {
			return "repair/process/edit";
		} else {
			return "repair/process/view";
		}
	}

	/**
	 * 新增或修改操作
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(Repair task, String[] fileorignal, String[] filename, String[] delfiles) {
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "维修");
		}
		// 验证表单
		String res = validateForm(task);
		if (StringUtils.isNotEmpty(res)) {
			return ScoMessage.error(res);
		}
		if(task.getCostSaving()==null ||"".equals(task.getCostSaving()))task.setCostSaving("0");
		if (null == task.getId()) {
			copyCreateInfo(task);
			repairService.insert(task, fileorignal, filename, delfiles,fileLength);
			return ScoMessage.success("repair/process/list", ScoMessage.SAVE_SUCCESS_MSG);
		} else {
			copyUpdateInfo(task);
			repairService.update(task, fileorignal, filename, delfiles,fileLength,true);
			return ScoMessage.success("repair/process/list", ScoMessage.DEFAULT_SUCCESS_MSG);
		}

	}


	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {	
		Repair r = repairService.getById(ids[0]);
		Long pId = getCurrentPersonId();
		if(pId.intValue()!=r.getCreateId().intValue() || StringUtils.isNotEmpty(r.getProcessInstanceId())){
			return ScoMessage.error("当前用户无该记录删除权限");
		}
		if(r.getStatus()==2){
		    return ScoMessage.error("已提交状态的维修单不允许删除");
		}
		repairService.deleteByIds(ids);	
		return ScoMessage.success(ScoMessage.DELETE_SUCCESS_MSG);
	}
	

	/**
	 * 表单验证
	 */
	private String validateForm(Repair repair) {
		String res = "";
		return res;
	}

}
