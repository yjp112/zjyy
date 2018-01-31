package com.supconit.employee.leave.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.domains.Organization;
import com.supconit.base.entities.Attachment;
import com.supconit.base.entities.EnumDetail;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.AttachmentService;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.utils.SerialNumberGenerator.TableAndColumn;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.employee.leave.entities.LeaveApply;
import com.supconit.employee.leave.services.LeaveApplyService;
import com.supconit.honeycomb.business.organization.services.PersonService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;


/**
 * 请假申请控制类
 * @author wangbo
 * @author yuhuan
 * @日期 2015/07
 */
@Controller
@RequestMapping("employee/leave")
public class LeaveApplyController extends BaseControllerSupport {
	
	@Autowired
	private LeaveApplyService leaveApplyService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private PersonService personService;
	//配置文件长度
	private String fileLength="";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String HUMAN_RESOURCE = "RLZYSP";//人力资源角色code
	private static final String CAPACITY_ENSURE = "NLBZSP";//能力保障角色code
	private static final String SAFE_PRODUCTION = "AQSCSP";//安全生产角色code
	private static final String FILELENGTHDEFAULT ="10";          //默认文件上传大小
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(ModelMap model) {
		model.put("leaveTypes", htmlSelectOptions(DictTypeEnum.LEAVE_TYPE,""));
		model.put("leaveResults", htmlSelectOptions(DictTypeEnum.LEAVE_RESULT,""));
		return "employee/leave/list";
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<LeaveApply>  dolist(LeaveApply leave,Pagination<LeaveApply> pager) {
		boolean b = hasAdminRole();
		if(!b){
			leave.setCreateId(getCurrentPersonId());
		}
		return leaveApplyService.findByPage(pager, leave);
	}
	
	/**
	 * 跳转到编辑页
	 */
	@RequestMapping(value="edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,ModelMap model, @RequestParam(required = false) Long id) {
		//修改
		LeaveApply leave = null;
		if (null != id) {
			leave = leaveApplyService.getById(id);
			setFullDeptName(leave, leave.getCreateId());
			model.put(PROCESS_INSTANCE_ID,leave.getProcessInstanceId());
			model.put("listAttachments", attachmentService.getAttachmentByFid(id,Constant.ATTACHEMENT_LEAVE));
		}else{
			long personId = getCurrentPersonId();
			leave = new LeaveApply();
			leave.setLeaveType(1);//初始选中事假类别
			leave.setCreateId(personId);
			leave.setCreator(getCurrentPersonName());
			leave.setCreateDate(new Date());
			setFullDeptName(leave, personId);
		}
		List<EnumDetail> listLeaveType = DictUtils.getDictList(DictTypeEnum.LEAVE_TYPE);
		//配置上传文件大小
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "员工请假");
			if("".equals(fileLength))
				fileLength=FILELENGTHDEFAULT;
		}
		model.put("fileLength", fileLength); 
		model.put("leave", leave);
    	model.put("listLeaveType", listLeaveType);
		model.put("viewOnly", viewOnly);
		return "employee/leave/edit";
	}
	
	
	/**
	 * 跳转到审核页
	 */
	@RequestMapping(value="audit", method = RequestMethod.GET)
	public String audit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false)Boolean hisView,ModelMap model, @RequestParam(required = false) Long id) {
		//修改
		LeaveApply leave = null;
		if (null != id) {
			leave = leaveApplyService.getById(id);
			setFullDeptName(leave, leave.getCreateId());
			model.put(PROCESS_INSTANCE_ID,leave.getProcessInstanceId());
			model.put("listAttachments", attachmentService.getAttachmentByFid(id,Constant.ATTACHEMENT_LEAVE));
			List<EnumDetail> listLeaveType = DictUtils.getDictList(DictTypeEnum.LEAVE_TYPE);
			model.put("leave", leave);
	    	model.put("listLeaveType", listLeaveType);
			model.put("viewOnly", viewOnly);
			model.put("hisView", hisView);
		}
		return "employee/leave/audit";
	}
	

	
	/**
	 * 设置部门全名
	 */
	private LeaveApply setFullDeptName(LeaveApply leave,long personId){
		List<Organization> orList = organizationService.getFullDeptNameByPersonId(personId);
		String fullDeptName = OrganizationUtils.joinFullDeptName(orList);
		leave.setDeptName(fullDeptName);
		long deptId=orList.get(0).getDeptId();
		leave.setDeptId(deptId);
		return leave;
	}
	
	/**
	 * 新增或修改操作
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(LeaveApply leave,String[] fileorignal,String[] filename,String[] delfiles) {
		Organization o = new Organization();
    	o.setPersonId(getCurrentPersonId());
    	Organization org=organizationService.getPersonPositionByOrganization(o);
    	if(null == org.getPostId()){
    		return ScoMessage.error("请联系管理员在系统后台人员管理模块配置人员职务。");
    	}
    	if(org.getPostId() <= 2){//部长及以上职位
    		return ScoMessage.error("部长及以上职位请假申请不在本系统受理。");
    	}
    	Integer type = leave.getLeaveType();
    	Long leaveId = leave.getId();
    	if(type == LeaveTypes.NXJ.value){
			double leaveDay = calculateDay(leave);
    		Double spare = calculateSpareNXJ();
    		double currentTmp = 0;
    		if (null != leaveId) {
    			LeaveApply la = leaveApplyService.getById(leaveId);
    			currentTmp = calculateDay(la);
    		}
    		if(null == spare || ((spare + currentTmp) - leaveDay) < 0){
    			return ScoMessage.error("您目前可用的年休假为：" + (spare == null? 0:((spare + currentTmp)<=0? 0:(spare + currentTmp))) + "天");
    		}
    	}
    	//验证表单
		String res = validateForm(leave,fileorignal,delfiles);
		if(StringUtils.isNotEmpty(res)){
			return ScoMessage.error(res);
		}
		
		leave.setResult(0);//待申请
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "员工请假");
		}
		if (null == leaveId) {
			leave.setLeaveCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.LEAVE_APPLY));
			leave.setProcessInstanceName(getLeaveTypeName(leave)+"申请:"+leave.getLeaveCode().toString());
			copyCreateInfo(leave);
			leaveApplyService.startProcess(leave,fileorignal,filename,delfiles,fileLength);
			return ScoMessage.success("employee/leave/list",ScoMessage.SAVE_SUCCESS_MSG);
		}else {
			copyUpdateInfo(leave);
			leaveApplyService.update(leave,fileorignal,filename,delfiles,fileLength);
			return ScoMessage.success("workspace/todo/list",ScoMessage.DEFAULT_SUCCESS_MSG);
		}
        
	}
	
	/**
	 * 提交流程
	 */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public ScoMessage submit(LeaveApply leave,String[] fileorignal,String[] filename,String[] delfiles) {
    	Long leaveId = leave.getId();
    	Integer type = leave.getLeaveType();
    	String res = validateForm(leave,fileorignal,delfiles);
		if(StringUtils.isNotEmpty(res)){
			return ScoMessage.error(res);
		}
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "员工请假");
		}
        if(null == leaveId) {
        	Organization o = new Organization();
        	o.setPersonId(getCurrentPersonId());
        	Organization org=organizationService.getPersonPositionByOrganization(o);
        	if(null == org.getPostId()){
        		return ScoMessage.error("请联系管理员在系统后台人员管理模块配置人员职务。");
        	}
        	long postId = org.getPostId();
        	if(postId <= 2){//部长及以上职位
        		return ScoMessage.error("部长及以上职位请假申请不在本系统受理。");
        	}
        	leave.setHandlePersonCode(org.getPersonCode());
        	if(null == type || type > 13 || type < 1 ){
        		type = 1;
        	}
        	if(type == LeaveTypes.SJ.value || type == LeaveTypes.NXJ.value 
        			|| type == LeaveTypes.TQJ.value || type == LeaveTypes.LYJ.value){
        		leave.setPostId(postId);
        	}
        	if(type == LeaveTypes.NXJ.value){
    			double leaveDay = calculateDay(leave);
        		Double spare = calculateSpareNXJ();
        		if(null == spare || (spare - leaveDay) < 0){
        			return ScoMessage.error("您目前可用的年休假为：" + (spare == null? 0:(spare<=0? 0:spare)) + "天");
        		}
        	}
        	
        	leave.setLeaveCode(SerialNumberGenerator.getSerialNumbersByDB(TableAndColumn.LEAVE_APPLY));
        	leave.setProcessInstanceName(getLeaveTypeName(leave)+"申请:"+leave.getLeaveCode().toString());
        	leave.setResult(1);//审批中
        	leaveApplyService.submitProcess(leave, fileorignal, filename, delfiles,fileLength);
        	return ScoMessage.success("employee/leave/list",ScoMessage.SAVE_SUCCESS_MSG);
        }else{
        	String bpmId = leave.getBpm_ts_id();//按钮Id
        	double leaveDay = calculateDay(leave);
        	if(type == LeaveTypes.NXJ.value && "LEAVE_APPLY".equals(bpmId)){
        		Double spare = calculateSpareNXJ();
        		double currentTmp = 0;
        		if (null != leaveId) {
        			LeaveApply la = leaveApplyService.getById(leaveId);
        			currentTmp = calculateDay(la);
        		}
        		if(null == spare || ((spare + currentTmp) - leaveDay) < 0){
        			return ScoMessage.error("您目前可用的年休假为：" + (spare == null? 0:((spare + currentTmp)<=0? 0:(spare + currentTmp))) + "天");
        		}
        	}
        	Integer status = leave.getStatus();
        	if(null == status){
        		leave.setResult(1);//审批中
        	}
        	if(null == type || type > 13 || type < 1 ){
        		type = 1;
        	}
            if(type == LeaveTypes.SJ.value){//事假
            	Organization o = new Organization();
            	o.setPersonId(getCurrentPersonId());
            	Organization org=organizationService.getPersonPositionByOrganization(o);
            	leave.setHandlePersonCode(org.getPersonCode());
            	if(null == status){
            		leave.setPostId(org.getPostId());
            	}else{
            		if(status == 1){//同意
                  	   if((leaveDay <= 3 && "LEAVE_APPLY_KZ".equals(bpmId)) 
                  			   || (leaveDay <= 5 && "LEAVE_APPLY_BZ".equals(bpmId))){
                  		  leave.setResult(2);//通过
                  	   }
                  	   if("LEAVE_APPLY_ZXLD".equals(bpmId)){
                  		  leave.setResult(2);//通过
                  	   }
                 	} 
                 	if(status==0){//不同意
                 	   leave.setResult(9);//不通过
                 	}	
            	}
            }else if(type == LeaveTypes.BJ.value){//病假
            	if(!"LEAVE_APPLY_FINISH".equals(bpmId)){//非最后一步
            		if("LEAVE_APPLY_LEADER".equals(bpmId)){//部门指定人员
            			String handle = setHandlePersonCodes(HUMAN_RESOURCE);
            			if(StringUtils.isEmpty(handle)){
            				return ScoMessage.error("请指定人力资源审批人员");
            			}else{
            				leave.setHandlePersonCode(handle);
            			}
                	}else{//上级领导
                		Organization o = new Organization();
                    	o.setPersonId(getCurrentPersonId());
                    	Organization org=organizationService.getPersonPositionByOrganization(o);
                    	leave.setHandlePersonCode(org.getPersonCode());
                	}
            	}
            	if(null == status){
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			if("LEAVE_APPLY_FINISH".equals(bpmId)){//人力资源审批
            				leave.setResult(2);//通过
                		}
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }else if(type == LeaveTypes.SQJ.value){//丧假
            	if(null == status){
            		Organization o = new Organization();
                	o.setPersonId(getCurrentPersonId());
                	Organization org=organizationService.getPersonPositionByOrganization(o);
                	leave.setHandlePersonCode(org.getPersonCode());
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			leave.setResult(2);//通过
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }else if(type == LeaveTypes.NXJ.value){//年休假
            	if(!"LEAVE_APPLY_FINISH".equals(bpmId)){//非最后一步
	            	if("LEAVE_APPLY_LEADER".equals(bpmId)){//部门指定人员
	            		String handle = setHandlePersonCodes(HUMAN_RESOURCE);
            			if(StringUtils.isEmpty(handle)){
            				return ScoMessage.error("请指定人力资源审批人员");
            			}else{
            				leave.setHandlePersonCode(handle);
            			}
	            	}else{//上级领导
	            		Organization o = new Organization();
	                	o.setPersonId(getCurrentPersonId());
	                	Organization org=organizationService.getPersonPositionByOrganization(o);
	                	leave.setHandlePersonCode(org.getPersonCode());
	                	if("LEAVE_APPLY".equals(bpmId)){
	                		long postId = org.getPostId();
	                		if(postId <= 2){//部长及以上职位
		                		return ScoMessage.error("部长及以上职位请假申请不在本系统受理。");
		                	}
	                		leave.setPostId(postId);
	                	}
	            	}
            	}
            	if(null == status){
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			if("LEAVE_APPLY_FINISH".equals(bpmId)){//人力资源审批
            				leave.setResult(2);//通过
                		}
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }else if(type == LeaveTypes.HJ.value){//婚假
            	if(!"LEAVE_APPLY_FINISH".equals(bpmId)){//非最后一步
            		if("LEAVE_APPLY_LEADER".equals(bpmId)){//部门指定人员
            			String handle = setHandlePersonCodes(CAPACITY_ENSURE);
            			if(StringUtils.isEmpty(handle)){
            				return ScoMessage.error("请指定能力保障审批人员");
            			}else{
            				leave.setHandlePersonCode(handle);
            			}
                	}else{//上级领导
                		Organization o = new Organization();
                    	o.setPersonId(getCurrentPersonId());
                    	Organization org=organizationService.getPersonPositionByOrganization(o);
                    	leave.setHandlePersonCode(org.getPersonCode());
                	}
            	}
            	if(null == status){
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			if("LEAVE_APPLY_FINISH".equals(bpmId)){//能力保障审批
            				leave.setResult(2);//通过
                		}
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }else if(type == LeaveTypes.TQJ.value){//探亲假
            	if(!"LEAVE_APPLY_FINISH".equals(bpmId)){//非最后一步
	            	if("LEAVE_APPLY_LEADER".equals(bpmId)){//部门指定人员
	            		String handle = setHandlePersonCodes(HUMAN_RESOURCE);
            			if(StringUtils.isEmpty(handle)){
            				return ScoMessage.error("请指定人力资源审批人员");
            			}else{
            				leave.setHandlePersonCode(handle);
            			}
	            	}else{//上级领导
	            		Organization o = new Organization();
	                	o.setPersonId(getCurrentPersonId());
	                	Organization org=organizationService.getPersonPositionByOrganization(o);
	                	leave.setHandlePersonCode(org.getPersonCode());
	                	if("LEAVE_APPLY".equals(bpmId)){
	                		long postId = org.getPostId();
	                		if(postId <= 2){//部长及以上职位
		                		return ScoMessage.error("部长及以上职位请假申请不在本系统受理。");
		                	}
	                		leave.setPostId(postId);
	                	}
	            	}
            	}
            	if(null == status){
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			if("LEAVE_APPLY_FINISH".equals(bpmId)){//人力资源审批
            				leave.setResult(2);//通过
                		}
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }else if(type == LeaveTypes.CQJ.value){//产前假
            	if(!"LEAVE_APPLY_FINISH".equals(bpmId)){//非最后一步
            		if("LEAVE_APPLY_LEADER".equals(bpmId)){//部门指定人员
            			String handle = setHandlePersonCodes(HUMAN_RESOURCE);
            			if(StringUtils.isEmpty(handle)){
            				return ScoMessage.error("请指定人力资源审批人员");
            			}else{
            				leave.setHandlePersonCode(handle);
            			}
                	}else{//上级领导
                		Organization o = new Organization();
                    	o.setPersonId(getCurrentPersonId());
                    	Organization org=organizationService.getPersonPositionByOrganization(o);
                    	leave.setHandlePersonCode(org.getPersonCode());
                	}
            	}
            	if(null == status){
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			if("LEAVE_APPLY_FINISH".equals(bpmId)){//人力资源审批
            				leave.setResult(2);//通过
                		}
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }else if(type == LeaveTypes.CJ.value){//产假
            	if(!"LEAVE_APPLY_FINISH".equals(bpmId)){//非最后一步
            		if("LEAVE_APPLY_LEADER".equals(bpmId)){//部门指定人员
            			String handle = setHandlePersonCodes(CAPACITY_ENSURE);
            			if(StringUtils.isEmpty(handle)){
            				return ScoMessage.error("请指定能力保障审批人员");
            			}else{
            				leave.setHandlePersonCode(handle);
            			}
                	}else{//上级领导
                		Organization o = new Organization();
                    	o.setPersonId(getCurrentPersonId());
                    	Organization org=organizationService.getPersonPositionByOrganization(o);
                    	leave.setHandlePersonCode(org.getPersonCode());
                	}
            	}
            	if(null == status){
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			if("LEAVE_APPLY_FINISH".equals(bpmId)){//能力保障审批
            				leave.setResult(2);//通过
                		}
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }else if(type == LeaveTypes.BRJ.value){//哺乳假
            	if(null == status){
            		Organization o = new Organization();
                	o.setPersonId(getCurrentPersonId());
                	Organization org=organizationService.getPersonPositionByOrganization(o);
                	leave.setHandlePersonCode(org.getPersonCode());
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			leave.setResult(2);//通过
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }else if(type == LeaveTypes.PCJ.value){//陪产假
            	if(!"LEAVE_APPLY_FINISH".equals(bpmId)){//非最后一步
            		if("LEAVE_APPLY_LEADER".equals(bpmId)){//部门指定人员
            			String handle = setHandlePersonCodes(CAPACITY_ENSURE);
            			if(StringUtils.isEmpty(handle)){
            				return ScoMessage.error("请指定能力保障审批人员");
            			}else{
            				leave.setHandlePersonCode(handle);
            			}
                	}else{//上级领导
                		Organization o = new Organization();
                    	o.setPersonId(getCurrentPersonId());
                    	Organization org=organizationService.getPersonPositionByOrganization(o);
                    	leave.setHandlePersonCode(org.getPersonCode());
                	}
            	}
            	if(null == status){
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			if("LEAVE_APPLY_FINISH".equals(bpmId)){//能力保障审批
            				leave.setResult(2);//通过
                		}
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }else if(type == LeaveTypes.JHSYJ.value){//计划生育假
            	if(!"LEAVE_APPLY_FINISH".equals(bpmId)){//非最后一步
            		if("LEAVE_APPLY_LEADER".equals(bpmId)){//部门指定人员
            			String handle = setHandlePersonCodes(CAPACITY_ENSURE);
            			if(StringUtils.isEmpty(handle)){
            				return ScoMessage.error("请指定能力保障审批人员");
            			}else{
            				leave.setHandlePersonCode(handle);
            			}
                	}else{//上级领导
                		Organization o = new Organization();
                    	o.setPersonId(getCurrentPersonId());
                    	Organization org=organizationService.getPersonPositionByOrganization(o);
                    	leave.setHandlePersonCode(org.getPersonCode());
                	}
            	}
            	if(null == status){
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			if("LEAVE_APPLY_FINISH".equals(bpmId)){//能力保障审批
            				leave.setResult(2);//通过
                		}
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }else if(type == LeaveTypes.GSJ.value){//工伤假
            	if(!"LEAVE_APPLY_FINISH".equals(bpmId)){//非最后一步
            		if("LEAVE_APPLY_LEADER".equals(bpmId)){//部门指定人员
            			String handle = setHandlePersonCodes(SAFE_PRODUCTION);
            			if(StringUtils.isEmpty(handle)){
            				return ScoMessage.error("请指定安全生产审批人员");
            			}else{
            				leave.setHandlePersonCode(handle);
            			}
                	}else{//上级领导
                		Organization o = new Organization();
                    	o.setPersonId(getCurrentPersonId());
                    	Organization org=organizationService.getPersonPositionByOrganization(o);
                    	leave.setHandlePersonCode(org.getPersonCode());
                	}
            	}
            	if(null == status){
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			if("LEAVE_APPLY_FINISH".equals(bpmId)){//安全生产审批
            				leave.setResult(2);//通过
                		}
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }else if(type == LeaveTypes.LYJ.value){//疗养假
            	if(!"LEAVE_APPLY_FINISH".equals(bpmId)){//非最后一步
	            	if("LEAVE_APPLY_LEADER".equals(bpmId)){//部门指定人员
	            		String handle = setHandlePersonCodes(HUMAN_RESOURCE);
            			if(StringUtils.isEmpty(handle)){
            				return ScoMessage.error("请指定人力资源审批人员");
            			}else{
            				leave.setHandlePersonCode(handle);
            			}
	            	}else{//上级领导
	            		Organization o = new Organization();
	                	o.setPersonId(getCurrentPersonId());
	                	Organization org=organizationService.getPersonPositionByOrganization(o);
	                	leave.setHandlePersonCode(org.getPersonCode());
	                	if("LEAVE_APPLY".equals(bpmId)){
	                		long postId = org.getPostId();
	                		if(postId <= 2){//部长及以上职位
		                		return ScoMessage.error("部长及以上职位请假申请不在本系统受理。");
		                	}
	                		leave.setPostId(postId);
	                	}
	            	}
            	}
            	if(null == status){
            		leave.setResult(1);//审批中
            	}else{
            		if(status==1){//同意
            			if("LEAVE_APPLY_FINISH".equals(bpmId)){//人力资源审批
            				leave.setResult(2);//通过
                		}
                 	} 
                 	if(status==0){//不同意
                 		leave.setResult(9);//不通过
                 	}
            	}
            }
            
        	leave.setProcessInstanceName(getLeaveTypeName(leave)+"申请:"+leave.getLeaveCode().toString());
        	leaveApplyService.submitProcess(leave, fileorignal, filename, delfiles,fileLength);
        	return ScoMessage.success("workspace/todo/list",ScoMessage.DEFAULT_SUCCESS_MSG);
        }
    }
	
	/**
	 * 删除操作
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		LeaveApply leave=leaveApplyService.getById(ids[0]);
		if(leave.getStatus()>0)
			return ScoMessage.error("请假申请提交后不允许删除操作！");
		leaveApplyService.deleteById(ids[0]);
		return ScoMessage.success("workspace/todo/list", ScoMessage.DELETE_SUCCESS_MSG);
	} 
	
    @ResponseBody
    @RequestMapping(value = "deleteProcessInstance", method = RequestMethod.POST)
    public ScoMessage deleteProcessInstance(@RequestParam("id") Long id) {
    	leaveApplyService.deleteProcessInstance(id);
        return ScoMessage.success("workspace/todo/list", "删除成功。");
    }
    
    
    /**
	 * 设置时间
	 */
	private void setApplyDays(LeaveApply leave){
		
		if(leave.getLeaveDays() ==null){
			leave.setLeaveDays(0);
		}
		if(leave.getLeaveHours()==null){
			leave.setLeaveHours(0);
		}
		Double days = leave.getLeaveDays().doubleValue()+leave.getLeaveHours().doubleValue()/8D;//8小时算一天
		leave.setDays(days);
	}
	
	/**
	 * 获取请假类型名称
	 */
	private String getLeaveTypeName(LeaveApply leave){
		String typeName="";
		Integer type = leave.getLeaveType();
		if(null == type || type > 13 || type < 1 ){
    		type = 1;
    	}
		if(type == LeaveTypes.SJ.value){//事假
			typeName = LeaveTypes.SJ.desc;
        }else if(type == LeaveTypes.BJ.value){//病假
        	typeName = LeaveTypes.BJ.desc;
        }else if(type == LeaveTypes.SQJ.value){//丧假
        	typeName = LeaveTypes.SQJ.desc;
        }else if(type == LeaveTypes.NXJ.value){//年休假
        	typeName = LeaveTypes.NXJ.desc;
        }else if(type == LeaveTypes.HJ.value){//婚假
        	typeName = LeaveTypes.HJ.desc;
        }else if(type == LeaveTypes.TQJ.value){//探亲假
        	typeName = LeaveTypes.TQJ.desc;
        }else if(type == LeaveTypes.CQJ.value){//产前假
        	typeName = LeaveTypes.CQJ.desc;
        }else if(type == LeaveTypes.CJ.value){//产假
        	typeName = LeaveTypes.CJ.desc;
        }else if(type == LeaveTypes.BRJ.value){//哺乳假
        	typeName = LeaveTypes.BRJ.desc;
        }else if(type == LeaveTypes.PCJ.value){//陪产假
        	typeName = LeaveTypes.PCJ.desc;
        }else if(type == LeaveTypes.JHSYJ.value){//计划生育假
        	typeName = LeaveTypes.JHSYJ.desc;
        }else if(type == LeaveTypes.GSJ.value){//工伤假
        	typeName = LeaveTypes.GSJ.desc;
        }else if(type == LeaveTypes.LYJ.value){//疗养假
        	typeName = LeaveTypes.LYJ.desc;
        }
		return typeName;
	}
	
	/**
	 * 设置部门指定审批人员
	 */
	private String setHandlePersonCodes(String roleCode){
		List<Organization> list = organizationService.getPersonCodesByRoleCode(roleCode);
    	StringBuffer sb = new StringBuffer();
		for (Organization organization : list) {
			sb.append(organization.getPersonCode() + ",");
		}
		return sb.length()==0? "":sb.substring(0, sb.length()-1);
	}
	
	/**
     * 表单验证
     */
    private String validateForm(LeaveApply leave,String[] fileorignal,String[] delfiles){
    	String res = "";
    	if(leave.getLeaveType() == 9){//哺乳假
			if(StringUtils.isEmpty(leave.getStartTime())){
				return "开始日期不能为空！";
			}
			if(StringUtils.isEmpty(leave.getStartTime1())){
				return "开始时间不能为空！";
			}
			if(StringUtils.isEmpty(leave.getEndTime())){
				return "结束日期不能为空！";
			}
			if(StringUtils.isEmpty(leave.getEndTime1())){
				return "结束时间不能为空！";
			}
			try {
				leave.setStartDate(DateUtils.parseDate(leave.getStartTime()+" "+leave.getStartTime1(), "yyyy-MM-dd HH:mm:ss"));
				leave.setEndDate(DateUtils.parseDate(leave.getEndTime()+" "+leave.getEndTime1(), "yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				return "请假时间格式化错误！";
			}
		}else{//哺乳假以外的假
			if(null == leave.getStartDate()){
				return "开始时间不能为空！";
			}
			if(null == leave.getEndDate()){
				return "结束时间不能为空！";
			}
			if(leave.getStartDate().equals(leave.getEndDate())){
				return "结束时间不能等于开始时间！";
			}
		}
    	setApplyDays(leave);
		if(leave.getDays()==0){
			return "请假天数不能为空！";
		}
		if(leave.getLeaveHours()>8){
			return "请假小时需为[0-8]之间的整数";
		}
		//验证附件
		Integer type = leave.getLeaveType();
	  	if(null == type || type > 13 || type < 1 ){
	  		type = 1;
	  	}
	  	if(type == LeaveTypes.BJ.value || type == LeaveTypes.CJ.value || type == LeaveTypes.JHSYJ.value
	  			|| type == LeaveTypes.GSJ.value || type == LeaveTypes.LYJ.value ){
	  		if(null == fileorignal){
	  			if(null != leave.getId()){
	  				List<Attachment> list = attachmentService.getAttachmentByFid(leave.getId(),Constant.ATTACHEMENT_LEAVE);
	  				int del = delfiles==null? 0:delfiles.length;
	  				if(null == list ||(list.size()-del) < 1){
	  					return "附件不能为空。";
	  				}
	  			}else{
	  				return "附件不能为空。";
	  			}
	  		}
	  	}
		return res;
    }
    
    /**
     * 请假后剩余年休假
     */
    private Double calculateSpareNXJ(){
    	Double spare = null;
    	long personId = getCurrentPersonId();
    	ExPerson person = this.personService.getById(personId);
		String come = person.getComeTime();//入职时间
		String join = person.getJoinWorkTime();//参加工作时间
		if(StringUtils.isEmpty(come) || StringUtils.isEmpty(join) ) return spare;
		String prefix = "-01-01";
		String suffix = "-12-31";
		String celeFix = "-07-15";
		try {
			Date co = sdf.parse(come);
			Calendar cal = Calendar.getInstance();
			int nowYear = cal.get(cal.YEAR);//今年年份
			Date lastCeleDate = sdf.parse(String.valueOf((nowYear-1))+celeFix);//去年厂庆时间
			Date currentCeleDate = sdf.parse(String.valueOf(nowYear)+celeFix);//去年厂庆时间
			int comeTimeYear = Integer.parseInt(come.split("-")[0]);//入职年份
			Date nowYearStart = sdf.parse(nowYear + prefix);//今年开始日期
			Date nowYearEnd = sdf.parse(nowYear + suffix);//今年结束日期
			Date joinWorkTime = sdf.parse(join);
			long nowdiff = nowYearEnd.getTime() - joinWorkTime.getTime();
			double nowNxjNum = DateUtils.calculateNXJ(nowdiff);//今年可用年休假
			LeaveApply leave = new LeaveApply();
			leave.setLeaveType(LeaveTypes.NXJ.value);//年休假
			leave.setApplyStart(nowYearStart);
			leave.setApplyEnd(nowYearEnd);
			leave.setCreateId(personId);
			LeaveApply currentYear = leaveApplyService.sumByConditions(leave);//今年已使用
			double nowYearUsed = 0;
			if(null != currentYear){
				double currentHours = currentYear.getLeaveHours()/8 + (double)currentYear.getLeaveHours()%8/8;
				double cuurentTmp = Double.parseDouble(String.valueOf(currentHours).split("\\.")[0]+".5");
				double tmps = Double.parseDouble("0."+String.valueOf(currentHours).split("\\.")[1]);
				nowYearUsed = currentYear.getLeaveDays() + Math.rint(currentHours);
				if(tmps !=0 && currentHours <= cuurentTmp){
					if(currentHours == cuurentTmp){
						nowYearUsed = currentYear.getLeaveDays() + currentHours;
					}else{
						nowYearUsed = nowYearUsed + 0.5;
					}
				}
			}
			String last = DictUtils.getDictValue(DictTypeEnum.CELEBRATE, "去年");//厂庆天数
			if(StringUtils.isEmpty(last)){
				throw new BusinessDoneException("请配置去年厂庆天数");  
			}
			double lastCele = Integer.parseInt(last);
			if(co.after(lastCeleDate)){
				lastCele = 0;
			}
			String current = DictUtils.getDictValue(DictTypeEnum.CELEBRATE, "今年");//厂庆天数
			if(StringUtils.isEmpty(current)){
				throw new BusinessDoneException("请配置今年厂庆天数");  
			}
			double currentCele = Integer.parseInt(current);
			if(co.after(currentCeleDate)){
				currentCele = 0;
			}
			if(comeTimeYear < nowYear){//今年之前入职
				Date lastYearStart = sdf.parse((nowYear-1) + prefix);//去年开始日期
				Date lastYearEnd = sdf.parse((nowYear-1) + suffix);//去年结束日期
				long lastDiff = lastYearEnd.getTime() - joinWorkTime.getTime();
				double lastNxjNum = DateUtils.calculateNXJ(lastDiff);//去年可用年休假
				if(co.after(lastYearStart)){//去年开始之后入职
					long diffs = lastYearEnd.getTime() - co.getTime();
					double days = millPercent(diffs);//参加工作时间不满一年，只按当年时间百分比计算年休假
					double d = days*lastNxjNum;
					lastNxjNum = Math.rint(d);
					double tt = Double.parseDouble("0."+String.valueOf(d).split("\\.")[1]);
					double tmp = Double.parseDouble(String.valueOf(days).split("\\.")[0]+".5");
					double tmps = Double.parseDouble("0."+String.valueOf(days).split("\\.")[1]);
					if(tmps != 0 && days <= tmp){
						if(days == tmp){
							lastNxjNum = days;
						}else{
							if(tt < 0.5){
								lastNxjNum = lastNxjNum + 0.5;
							}
						}
					}
				}
				leave.setApplyStart(lastYearStart);
				leave.setApplyEnd(lastYearEnd);
				LeaveApply lastYear = leaveApplyService.sumByConditions(leave);//去年已使用
				double lastYearUsed = 0;
				if(null != lastYear){
					double lastHours = lastYear.getLeaveHours()/8 + (double)lastYear.getLeaveHours()%8/8;
					double lastTmp = Double.parseDouble(String.valueOf(lastHours).split("\\.")[0]+".5");
					double tmps = Double.parseDouble("0."+String.valueOf(lastHours).split("\\.")[1]);
					lastYearUsed = lastYear.getLeaveDays() + Math.rint(lastHours);
					if(tmps !=0 && lastHours <= lastTmp){
						if(lastHours == lastTmp){
							lastYearUsed = lastYear.getLeaveDays() + lastHours;
						}else{
							lastYearUsed = lastYearUsed + 0.5;
						}
					}
				}
				spare = nowNxjNum + lastNxjNum - nowYearUsed - lastYearUsed - (lastCele + currentCele);//剩余年休假
			}else{//今年入职
				if(co.after(nowYearStart)){
					long diffs = nowYearEnd.getTime() - co.getTime();
					double days = millPercent(diffs);//参加工作时间不满一年，只按当年时间百分比计算年休假
					double d = days*(nowNxjNum<5? 5:nowNxjNum);
					nowNxjNum = Math.rint(d);
					double tt = Double.parseDouble("0." + String.valueOf(d).split("\\.")[1]);
					double tmp = Double.parseDouble(String.valueOf(days).split("\\.")[0]+".5");
					double tmps = Double.parseDouble("0." + String.valueOf(days).split("\\.")[1]);
					if(tmps != 0 && days <= tmp){
						if(days == tmp){
							nowNxjNum = days;
						}else{
							if(tt < 0.5){
								nowNxjNum = nowNxjNum + 0.5;
							}
						}
					}
				}
				spare = nowNxjNum - nowYearUsed - currentCele;//剩余年休假
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    	return spare;
    }
    
    /**
	 * 毫秒换算成年占一年的百分比
	 */
	private double millPercent(Long ms){
		if(null == ms){
			return 0;
		}
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;
		Long day = ms / dd;
		return (double)day/365;
	}
	
	/**
	 * 由天和小时计算出天
	 */
	private double calculateDay(LeaveApply leave){
		Integer day = leave.getLeaveDays();
		Integer hour = leave.getLeaveHours();
		if(null == day) day = 0;
		if(null == hour) hour = 0;
		double leaveDays = day + (double)hour/8;
		double leaveDay = Math.rint(leaveDays);
		double tmp = Double.parseDouble(String.valueOf(leaveDays).split("\\.")[0]+".5");
		double tmps = Double.parseDouble("0."+String.valueOf(leaveDays).split("\\.")[1]);
		if(tmps != 0 && leaveDays <= tmp){
			if(leaveDays == tmp){
				leaveDay = tmp;
			}else{
				leaveDay = leaveDay + 0.5;
			}
		}
		return leaveDay;
	}
   
	/**
	 * 请假类别
	 */
	public enum LeaveTypes{
		SJ(1, "事假"),BJ(2, "病假"),SQJ(3, "丧假"),NXJ(4, "年休假"),HJ(5, "婚假"),TQJ(6, "探亲假"),CQJ(7, "产前假")
		,CJ(8, "产假"),BRJ(9, "哺乳假"),PCJ(10, "陪产假"),JHSYJ(11, "计划生育假"),GSJ(12, "工伤假"),LYJ(13, "疗养假");
		
		private int value;
		private String desc; 

		private LeaveTypes(int value, String description) {
			this.value = value;
			this.desc = description;
		}

		public int getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}

		@Override
		public String toString() {
			return "{" + value + ":" + desc + "}";
		}
	}
	
}
