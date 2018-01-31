package com.supconit.repair.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.domains.Organization;
import com.supconit.base.entities.DutyGroupPerson;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.AttachmentService;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.DeviceService;
import com.supconit.base.services.DutyGroupPersonService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceCategory;
import com.supconit.hl.montrol.entity.MHistoryAlarm;
import com.supconit.hl.montrol.entity.MRealAlarm;
import com.supconit.hl.montrol.service.IHistoryAlarmService;
import com.supconit.hl.montrol.service.IRealAlarmService;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.repair.entities.Repair;
import com.supconit.repair.entities.RepairEvtCategoryPerson;
import com.supconit.repair.services.RepairEvtCategoryPersonService;
import com.supconit.repair.services.RepairService;
import com.supconit.repair.services.RepairSpareService;
import com.supconit.repair.services.RepairWorkerService;

@Controller
@Scope("prototype")
@RequestMapping("repair/newTask")
public class RepairNewTaskController extends BaseControllerSupport {

	@Value("${file.tmpsave}")
	private String tmpPath;

	@Value("${file.persistentsave}")
	private String savePath;
	//配置文件长度
	private String fileLength="";
	private static final transient Logger logger = LoggerFactory.getLogger(RepairNewTaskController.class);
	
	@Autowired
	private RepairService repairService;
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private PersonService personService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private RepairEvtCategoryPersonService repairPersonService;
	@Autowired
	private DutyGroupPersonService dutyGroupPersonService;
	@Autowired
	private RepairWorkerService repairWorkerService;
	@Autowired
	private RepairSpareService repairSpareService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private IRealAlarmService alarmService;
    @Autowired
    private IHistoryAlarmService historyAlarmService;

	/**
	 * 跳转到列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<GeoArea> geoAreaList = geoAreaService.findTree();
		model.put("geoAreaList", geoAreaList);// 地理区域树
		model.put("taskSourceList", DictUtils.getDictList(DictTypeEnum.TASK_SOURCE));
		model.put("urgencyList", DictUtils.getDictList(DictTypeEnum.URGENCY));
		return "repair/newTask/list";
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
		Pageable<Repair> r=(Pageable<Repair>) repairService.findByPage(pager, repair);
		return r;
	}
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value = "find", method = RequestMethod.POST)
	@ResponseBody
	public Pageable<Repair> find(Repair repair, Pagination<Repair> pager, ModelMap model) {
		Pageable<Repair> r=(Pageable<Repair>) repairService.findByDevicePage(pager, repair);
		return r;
	}
	@RequestMapping("contact")
	@ResponseBody
	public List<ExPerson> contact() {
		List<ExPerson> plst=personService.find(null);
		Iterator<ExPerson> it = plst.iterator();
		while(it.hasNext()){
			ExPerson p = it.next();
			List<Organization> orList = organizationService.getFullDeptNameByPersonId(p.getId());
			String deptFullName = OrganizationUtils.joinFullDeptName(orList);
			p.setDeptName(deptFullName);
			p.setDeptId(orList.get(0).getDeptId());
		}
		return plst;
	}

	/**
	 * 跳转到编辑页
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Boolean viewOnly, ModelMap model,
			@RequestParam(required = false) Long id,@RequestParam(required = false) Long alarmId,@RequestParam(required = false) String todoPage) {
		// 修改
		Repair task = null;
		if (null != id) {
			task = repairService.getById(id);
			if(null != task.getDeviceId()){
				task.setDeviceName(deviceService.getById(task.getDeviceId()).getDeviceName());
			}
			model.put(PROCESS_INSTANCE_ID, task.getProcessInstanceId());
			model.put("listAttachments", attachmentService.getAttachmentByFid(id,Constant.ATTACHEMENT_REPAIR));
		} else {
			task = new Repair();
			task.setTaskSource(1);// 初始化诉求来源
			task.setRepairMode(1);//设置默认维修方式 ：自修
			task.setRepairWorry(0);//设置默认是否抢修 ：否
			task.setTaskCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.TASK));// 初始化诉求单号
			task.setContact(getCurrentPersonName());
			task.setContactId(getCurrentPersonId());
			task.setRepairDate(new Date());
			Organization o = new Organization();
	    	o.setPersonId(getCurrentPersonId());
	    	Organization org=organizationService.getPersonPositionByOrganization(o);
	    	ExPerson person=(ExPerson)personService.getById(getCurrentPersonId());
	    	if(person.getPersonOfficePhone()!=null && !"".equals(person.getPersonOfficePhone())){
	    		task.setContactTel(person.getPersonOfficePhone());
	    	}else{
	    		task.setContactTel(person.getPersonTelephone());
	    	}
	    	task.setRepairDeptId(org.getDeptId());
	    	task.setRepairDeptName(OrganizationUtils.getFullDeptNameByDeptId(org.getDeptId()));
			copyCreateInfo(task);
			if(null!=alarmId){
				MRealAlarm mAlarm = alarmService.getById(alarmId);
				Device device  = deviceService.getById(mAlarm.getDeviceId());
				task.setCategoryId(device.getCategoryId());
				DeviceCategory category  = deviceCategoryService.getById(device.getCategoryId());
				task.setCategoryName(category.getFullLevelName());
				task.setTaskSource(2);// 初始化诉求来源
				task.setAlarmId(alarmId);
				task.setHisAlarmId(mAlarm.getHisAlarmId());
				task.setDeviceId(mAlarm.getDeviceId());
				task.setDeviceCode(mAlarm.getDeviceCode());
				task.setDeviceName(mAlarm.getDeviceName());
				task.setAreaId(mAlarm.getAreaId());
				task.setAreaName(geoAreaService.getById(mAlarm.getAreaId()).getFullLevelName());
				RepairEvtCategoryPerson repairPerson=repairPersonService.getByCategoryIdAndAreaId(2L, category.getId(), mAlarm.getAreaId());
				if(repairPerson!=null) {
					task.setEngineerId(repairPerson.getPersonId());
					task.setEngineerName(repairPerson.getPersonName());
					task.setRepairGroupId(repairPerson.getRepairGroupId());
					task.setRepairGroupName(repairPerson.getRepairGroupName());
				}
				task.setDescripton(mAlarm.getAlarmTime()+"  "+mAlarm.getDeviceName()+"   "+mAlarm.getAlarmRemark());
			}
		}
		if(task.getRepairCode()!=null){
			model.put("workerList", repairWorkerService.findByOrderCode(task.getRepairCode()));//人工信息
			model.put("spareList", repairSpareService.findByOrderCode(task.getRepairCode()));//耗材信息
		}
		model.put("taskSourceList", DictUtils.getDictList(DictTypeEnum.TASK_SOURCE)); // 诉求来源
		model.put("urgencyList", DictUtils.getDictList(DictTypeEnum.URGENCY)); // 紧急程度
		model.put("causeTypeList", DictUtils.getDictList(DictTypeEnum.CAUSE_TYPE)); // 原因归类
		model.put("repairModeList", DictUtils.getDictList(DictTypeEnum.REPAIR_MODE)); // 维修模式
		model.put("repairWorryList", DictUtils.getDictList(DictTypeEnum.REPAIR_WORRY)); // 是否抢修
		model.put("repairWorkList", DictUtils.getDictList(DictTypeEnum.REPAIR_WORK)); // 工种
		model.put("task", task);
		if (viewOnly == null)
			viewOnly = false;
		model.put("viewOnly", viewOnly);
		if (!viewOnly) {
			return "repair/newTask/edit";
		} else {
			return "repair/newTask/view";
		}
	}

	/**
	 * 跳转到填写维修单页
	 */
	@RequestMapping(value = "repair", method = RequestMethod.GET)
	public String repair(@RequestParam(required = false) Boolean viewOnly, ModelMap model,
			@RequestParam(required = false) Long id) {
		// 修改
		Repair task = null;
		if (null != id) {
			task = repairService.getById(id);
			if(null != task.getDeviceId()){
				task.setDeviceName(deviceService.getById(task.getDeviceId()).getDeviceName());
			}
			if(null == task.getRepairCode() || "".equals(task.getRepairCode())){
				task.setRepairCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.REPAIR));// 初始化维修单号
			}
			model.put(PROCESS_INSTANCE_ID, task.getProcessInstanceId());
			if(task.getRepairCode()!=null){
				model.put("workerList", repairWorkerService.findByOrderCode(task.getRepairCode()));//人工信息
				model.put("spareList", repairSpareService.findByOrderCode(task.getRepairCode()));//耗材信息
			}
			if("".equals(fileLength)){//配置上传文件大小
				fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "维修");
			}
			model.put("fileLength", fileLength);
			model.put("taskSourceList", DictUtils.getDictList(DictTypeEnum.TASK_SOURCE)); // 诉求来源
			model.put("urgencyList", DictUtils.getDictList(DictTypeEnum.URGENCY)); // 紧急程度
			model.put("causeTypeList", DictUtils.getDictList(DictTypeEnum.CAUSE_TYPE)); // 原因归类
			model.put("repairModeList", DictUtils.getDictList(DictTypeEnum.REPAIR_MODE)); // 维修模式
			model.put("repairWorryList", DictUtils.getDictList(DictTypeEnum.REPAIR_WORRY)); // 是否抢修
			model.put("repairWorkList", DictUtils.getDictList(DictTypeEnum.REPAIR_WORK)); // 工种
			model.put("listAttachments", attachmentService.getAttachmentByFid(id,Constant.ATTACHEMENT_REPAIR));
			model.put("task", task);
		}
		if (viewOnly == null)
			viewOnly = false;
		model.put("viewOnly", viewOnly);
		if (!viewOnly) {
			return "repair/newTask/repair";
		} else {
			return "repair/newTask/view";
		}
	}

	/**
	 * 新增或修改操作
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(Repair task, String[] fileorignal, String[] filename, String[] delfiles,HttpServletRequest request ) {
		// 验证表单
		String res = validateForm(task);
		if (StringUtils.isNotEmpty(res)) {
			return ScoMessage.error(res);
		}
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "维修");
		}
		if (task.getCategoryName()!=null&&!"".equals(task.getCategoryName()))
		{
			task.setProcessInstanceName(task.getCategoryName().substring(0,2)+"诉求单:" + task.getTaskCode().toString());
		}
		else
		{
			task.setProcessInstanceName("诉求单:" + task.getTaskCode().toString());
		}
		String handlePersonCode = findGroupPersons(task.getRepairGroupId());
		if("".equals(handlePersonCode))return ScoMessage.error(task.getRepairGroupName()+"未设置班组长！");
		List<DutyGroupPerson> gPersonList = dutyGroupPersonService.findGroupPersons(task.getRepairGroupId());
		if(null == gPersonList || gPersonList.size()==0){
			return ScoMessage.error(task.getRepairGroupName()+"未设置班组成员！");
		}else{
			for(DutyGroupPerson p : gPersonList){
				if(p.getPostId()==1){
					task.setGroupEmpId(p.getPersonId());
					task.setGroupEmpName(p.getPersonName());
					break;
				}
			}
		}
		if (null == task.getId()) {
			task.setStatus(0);// 待申请	
			copyCreateInfo(task);
			repairService.startProcess(task, fileorignal, filename, delfiles,fileLength);
			return ScoMessage.success("repair/newTask/list", ScoMessage.SAVE_SUCCESS_MSG);
		} else {
			copyUpdateInfo(task);
			if(task.getStatus()==3)
			{
				repairService.update(task, fileorignal, filename, delfiles,fileLength,false);
			}
			else
			{
				repairService.update(task, fileorignal, filename, delfiles,fileLength,true);
			}
			return ScoMessage.success("workspace/todo/list", ScoMessage.DEFAULT_SUCCESS_MSG);
		}

	}

	/**
	 * 提交流程
	 */
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	@ResponseBody
	public ScoMessage submit(Repair task, String[] fileorignal, String[] filename, String[] delfiles) {
		String bpmId = task.getBpm_ts_id();//按钮Id
		boolean flag = true;
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "维修");
		}
		String res = "";
		if (StringUtils.isNotEmpty(res)) {
			return ScoMessage.error(res);
		}
		if (task.getId() == null) {

			String handlePersonCode = findGroupPersons(task.getRepairGroupId());
			if("".equals(handlePersonCode))return ScoMessage.error(task.getRepairGroupName()+"未设置班组长！");
			List<DutyGroupPerson> gPersonList = dutyGroupPersonService.findGroupPersons(task.getRepairGroupId());
			if(null == gPersonList || gPersonList.size()==0){
				return ScoMessage.error(task.getRepairGroupName()+"未设置班组成员！");
			}else{
				for(DutyGroupPerson p : gPersonList){
					if(p.getPostId()==1){
						task.setGroupEmpId(p.getPersonId());
						task.setGroupEmpName(p.getPersonName());
						break;
					}
				}
			}
			task.setHandlePersonCode(handlePersonCode);
			task.setStatus(1);// 提交维修中
			if(task.getAlarmId()!=null){
				MRealAlarm realAlarm = alarmService.getById(task.getAlarmId());
				realAlarm.setChangeTime(new Date());
				realAlarm.setProcessPerson(task.getContact());
				realAlarm.setProcessType("维修处理");
				realAlarm.setProcessState(2);
		        alarmService.update(realAlarm);
		        alarmService.displayAlarm();
		        MHistoryAlarm historyAlarm = historyAlarmService.getById(task.getHisAlarmId());
	            historyAlarm.setProcessTime(realAlarm.getChangeTime());
	            historyAlarm.setProcessPerson(realAlarm.getProcessPerson());
	            historyAlarm.setProcessType("维修处理");
	            historyAlarm.setProcessState(2);
	            historyAlarmService.update(historyAlarm);
	            historyAlarmService.displayAlarm();
	            task.setAlarmId(task.getHisAlarmId());
			}
			if (task.getCategoryName()!=null&&!"".equals(task.getCategoryName()))
			{
				task.setProcessInstanceName(task.getCategoryName().substring(0,2)+"诉求单:" + task.getTaskCode().toString());
			}
			else
			{
				task.setProcessInstanceName("诉求单:" + task.getTaskCode().toString());
			}
			
			repairService.submitProcess(task, fileorignal, filename, delfiles,fileLength,flag);
			return ScoMessage.success("repair/newTask/list", ScoMessage.SAVE_SUCCESS_MSG);
		} else {
			if(task.getStatus()==0){//提交给组长
				String handlePersonCode = findGroupPersons(task.getRepairGroupId());
				if("".equals(handlePersonCode))return ScoMessage.error(task.getRepairGroupName()+"未设置班组长！");
				task.setHandlePersonCode(handlePersonCode);
				List<DutyGroupPerson> gPersonList = dutyGroupPersonService.findGroupPersons(task.getRepairGroupId());
				if(null == gPersonList || gPersonList.size()==0){
					return ScoMessage.error(task.getRepairGroupName()+"未设置班组成员！");
				}else{
					for(DutyGroupPerson p : gPersonList){
						if(p.getPostId()==1){
							task.setGroupEmpId(p.getPersonId());
							task.setGroupEmpName(p.getPersonName());
							break;
						}
					}
				}
			}else if(task.getStatus()==1 && "SQD_STEP2".equals(bpmId)){//组长派单给维修人
				Long rpId = task.getRepairPersonId();
				if(null == rpId) return ScoMessage.error("未设置现场负责人！");
				String handlePersonCode = personService.getById(rpId).getCode();
				if("".equals(handlePersonCode))return ScoMessage.error("维修人‘"+task.getRepairPersonName()+"’不存在！");
				task.setHandlePersonCode(handlePersonCode);
			}else if(task.getStatus()==2 && "SQD_STEP3".equals(bpmId)){//维修完成给报修人评价
				Long cId = task.getContactId();
				if(null == cId) return ScoMessage.error("报修人‘"+task.getContact()+"’不存在！");
				String handlePersonCode = personService.getById(cId).getCode();
				if("".equals(handlePersonCode))return ScoMessage.error("报修人‘"+task.getContact()+"’不存在！");
				task.setHandlePersonCode(handlePersonCode);
			}
			//if("SQD_REJECT1".equals(bpmId)) task.setStatus(0);
			if("SQD_STEP1".equals(bpmId)) {
				task.setStatus(1);
				if(task.getAlarmId()!=null){
					MRealAlarm realAlarm = alarmService.getById(task.getAlarmId());
					realAlarm.setChangeTime(new Date());
					realAlarm.setProcessPerson(task.getContact());
					realAlarm.setProcessType("维修处理");
					realAlarm.setProcessState(2);
			        alarmService.update(realAlarm);
			        alarmService.displayAlarm();
			        MHistoryAlarm historyAlarm = historyAlarmService.getById(task.getHisAlarmId());
		            historyAlarm.setProcessTime(realAlarm.getChangeTime());
		            historyAlarm.setProcessPerson(realAlarm.getProcessPerson());
		            historyAlarm.setProcessType("维修处理");
		            historyAlarm.setProcessState(2);
		            historyAlarmService.update(historyAlarm);
		            historyAlarmService.displayAlarm();
		            task.setAlarmId(task.getHisAlarmId());
				}
			}
			//if("SQD_REJECT2".equals(bpmId)) task.setStatus(1);
			if("SQD_STEP2".equals(bpmId)) task.setStatus(2);
			//if("SQD_REJECT3".equals(bpmId)) task.setStatus(2);
			if("SQD_STEP3".equals(bpmId)) {
				task.setStatus(3);
				if(task.getAlarmId()!=null){
			        MHistoryAlarm historyAlarm = historyAlarmService.getById(task.getAlarmId());
		            historyAlarm.setProcessState(3);
		            historyAlarmService.update(historyAlarm);
		            historyAlarmService.displayAlarm();
				}
			}
			if("SQD_STEP4".equals(bpmId)){
				task.setStatus(4);
				flag = false;
			}
			if (task.getCategoryName()!=null&&!"".equals(task.getCategoryName()))
			{
				task.setProcessInstanceName(task.getCategoryName().substring(0,2)+"诉求单:" + task.getTaskCode().toString());
			}
			else
			{
				task.setProcessInstanceName("诉求单:" + task.getTaskCode().toString());
			}
			repairService.submitProcess(task, fileorignal, filename, delfiles,fileLength,flag);
			return ScoMessage.success("workspace/todo/list", ScoMessage.DEFAULT_SUCCESS_MSG);
		}
	}

	@ResponseBody
	@RequestMapping(value = "deleteProcessInstance", method = RequestMethod.POST)
	public ScoMessage deleteProcessInstance(@RequestParam("id") Long id) {
		repairService.deleteProcessInstance(id);
		return ScoMessage.success("workspace/todo/list", "删除成功！");
	}
	

	/**
	 * 表单验证
	 */
	private String validateForm(Repair repair) {
		String res = "";
		Integer status = repair.getStatus();
		if(null!=status && status == 2){
			String subType = repair.getSubType();
			Date actualFinishTime = repair.getActualFinishTime();
			if(null != subType && subType.equals("2") && null == actualFinishTime){
				res = "实际完成时间不能为空";
			}
		}
		return res;
	}

	/**
	 * 查询维修班组长code
	 */
	private String findGroupPersons(Long groupId) {
		String handlePersonCode = "";
		if (null != groupId) {
			List<DutyGroupPerson> gpersonList = dutyGroupPersonService.findGroupPersons(groupId);
			for (int i = 0; i < gpersonList.size(); i++) {
				DutyGroupPerson gperson = gpersonList.get(i);
				if (gperson.getPostId() == 1) {
					if ("".equals(handlePersonCode)) {
						handlePersonCode = personService.getById(gperson.getPersonId()).getCode();
					} else {
						handlePersonCode = handlePersonCode + ","
								+ personService.getById(gperson.getPersonId()).getCode();
					}
				}
			}
		}
		return handlePersonCode;
	}

}
