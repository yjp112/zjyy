package com.supconit.employee.lunchapply.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.domains.Organization;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.employee.lunchapply.entities.LunchApply;
import com.supconit.employee.lunchapply.services.LunchApplyService;
import com.supconit.honeycomb.business.organization.services.PersonService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * 工作餐申请控制类
 * @author yuhuan
 * @日期 2015/08
 */
@Controller
@RequestMapping("employee/lunchapply")
public class LunchApplyControllers extends BaseControllerSupport{
	
	@Autowired
	private LunchApplyService lunchApplyService;
	@Autowired
	private PersonService personService;
	@Autowired
	private OrganizationService organizationService;
	private static SimpleDateFormat sdf_hour = new SimpleDateFormat("HH");
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(ModelMap model) {
		model.put("reslutList", DictUtils.getDictList(DictTypeEnum.LUNCH_RESULT));
		return "employee/lunchapply/list";
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<LunchApply>  list(LunchApply condition,
			Pagination<LunchApply> pager) {
		boolean b = hasAdminRole();
		if(!b){
			condition.setCreateId(getCurrentPersonId());
		}
		return lunchApplyService.findByPage(pager, condition);
	}
	
	/**
	 * 跳转到编辑页
	 */
	@RequestMapping(value="edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false)Boolean hisView,
			@RequestParam(required = false)Boolean view,ModelMap model, @RequestParam(required = false) Long id) {
		LunchApply lunchApply=null;
		long personId = 0;
		if(null != id){
			lunchApply = lunchApplyService.getById(id);
			model.put(PROCESS_INSTANCE_ID,lunchApply.getProcessInstanceId());
			personId  = lunchApply.getCreateId();
			if(!"1".equals(lunchApply.getResult())){
				viewOnly = true;
			}
		}else{
			personId = getCurrentPersonId();
			lunchApply = new LunchApply();
			ExPerson person = personService.getById(personId);
			lunchApply.setCreateId(person.getId());
			lunchApply.setCreator(person.getName());
			lunchApply.setCreateDate(new Date());
			lunchApply.setLunchDate(new Date());
		}
		List<Organization> orList = organizationService.getFullDeptNameByPersonId(personId);
		String deptFullName = OrganizationUtils.joinFullDeptName(orList);
		lunchApply.setDeptName(deptFullName);
		model.put("lunchApply", lunchApply);
		model.put("hisView", hisView==null? false:hisView);
		model.put("view", view==null? false:view);
		model.put("lunchTypeList", DictUtils.getDictList(DictTypeEnum.LUNCH_TYPE));
		model.put("viewOnly", viewOnly);
		return "employee/lunchapply/edit";
	}
	
	/**
	 * 新增或修改操作
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(LunchApply lunchApply) {
		if (null == lunchApply.getId()) {
			Date now = new Date();
			String res = allowApply(now);
			if(StringUtils.isNotEmpty(res)){
				return ScoMessage.error(res);
			}
			copyCreateInfo(lunchApply);
			lunchApply.setResult("0");//待审批
			lunchApply.setLunchcode(SerialNumberGenerator.getSerialNumbersByDB(SerialNumberGenerator.TableAndColumn.LUNCH_APPLY));
			lunchApply.setProcessInstanceName("工作餐:"+lunchApply.getLunchcode());
			Pagination<ExPerson> pager = new Pagination<ExPerson>();
			pager.setPageSize(Integer.MAX_VALUE);
			//查询部门所有人员的岗位
			Organization org = new Organization();
			org.setPersonId(lunchApply.getCreateId());
	    	org=organizationService.getPersonPositionByOrganization(org);
	    	if(null != org){
	    		if(org.getPostId().longValue()==1){//中心领导
	    			return ScoMessage.error("中心领导的工作餐申请不在本系统受理。");
	    		}
	    		if(DEFAULT_DEPARTMENTID != org.getpId().longValue()){//科室级别
		    		long deptId = 0;
		    		if(null != org.getLunch() && 1==org.getLunch().intValue()){//有审批权限
		    			deptId = org.getpId().longValue();//部门审批
		    			lunchApply.setDeptLevel(2);
		    			org = new Organization();
			    		org.setDeptId(deptId);
			    		List<Organization> orgList = organizationService.getPersonListByDeptAndLunch(org);
			    		if(null != orgList && orgList.size()>0){
			    			StringBuffer sbs = new StringBuffer();
			    			for (Organization organization : orgList) {
			    				sbs.append(organization.getPersonCode()+",");
							}
			    			lunchApply.setHandlePersonCode(sbs.substring(0, sbs.length()-1).toString());
			    		}else{
			    			return ScoMessage.error("您的部门未分配工作餐申请审核人员,无法进行工作餐申请,请与管理员联系。");
			    		}
		    		}else{//无审批权限
		    			deptId = org.getDeptId().longValue();//科室审批
		    			lunchApply.setDeptLevel(1);
		    			org = new Organization();
			    		org.setDeptId(deptId);
			    		List<Organization> orgList = organizationService.getPersonListByDeptAndLunch(org);
			    		if(null != orgList && orgList.size()>0){
			    			StringBuffer sbs = new StringBuffer();
			    			for (Organization organization : orgList) {
			    				sbs.append(organization.getPersonCode()+",");
							}
			    			lunchApply.setHandlePersonCode(sbs.substring(0, sbs.length()-1).toString());
			    		}else{
			    			return ScoMessage.error("您的科室未分配工作餐申请审核人员,无法进行工作餐申请,请与管理员联系。");
			    		}
		    		}
		    	}else{//部门级别
		    		long deptId = org.getDeptId().longValue();//自己部门
		    		lunchApply.setDeptLevel(2);
		    		if(null != org.getLunch() && 1==org.getLunch().intValue()){//有审批权限
		    			org = new Organization();
			    		org.setDeptId(deptId);
			    		List<Organization> orgList = organizationService.getPersonListByDeptAndLunch(org);
			    		if(null != orgList && orgList.size()>0){
			    			StringBuffer sbs = new StringBuffer();
			    			for (Organization organization : orgList) {
			    				sbs.append(organization.getPersonCode()+",");
							}
			    			lunchApply.setHandlePersonCode(sbs.substring(0, sbs.length()-1).toString());
			    		}else{
			    			return ScoMessage.error("您的部门未分配工作餐申请审核人员,无法进行工作餐申请,请与管理员联系。");
			    		}
		    		}else{//无审批权限
			    		org = new Organization();
			    		org.setDeptId(deptId);
			    		List<Organization> orgList = organizationService.getPersonListByDeptAndLunch(org);
			    		if(null != orgList && orgList.size()>0){
			    			StringBuffer sbs = new StringBuffer();
			    			for (Organization organization : orgList) {
			    				sbs.append(organization.getPersonCode()+",");
							}
			    			lunchApply.setHandlePersonCode(sbs.substring(0, sbs.length()-1).toString());
			    		}else{
			    			return ScoMessage.error("您的部门未分配工作餐申请审核人员,无法进行工作餐申请,请与管理员联系。");
			    		}
		    		}
		    	}
	    	}else{
	    		return ScoMessage.error("您的账号异常,无法进行工作餐申请,请与管理员联系。");
	    	}
			lunchApplyService.insertLunchApply(lunchApply);
			return ScoMessage.success("employee/lunchapply/list",ScoMessage.SAVE_SUCCESS_MSG);
		} else {
			//查询部门所有人员的岗位
			Organization org = new Organization();
			org.setPersonId(getCurrentPersonId());
	    	org=organizationService.getPersonPositionByOrganization(org);
	    	if(0 == lunchApply.getStatus()){//不同意
				lunchApply.setResult("1");//不通过
			}
			if("LUNCH_APPLY_KS".equals(lunchApply.getBpm_ts_id())){//科室提交
				long deptId = org.getpId().longValue();//部门审批
    			org = new Organization();
	    		org.setDeptId(deptId);
	    		List<Organization> orgList = organizationService.getPersonListByDeptAndLunch(org);
	    		if(null != orgList && orgList.size()>0){
	    			StringBuffer sbs = new StringBuffer();
	    			for (Organization organization : orgList) {
	    				sbs.append(organization.getPersonCode()+",");
					}
	    			lunchApply.setHandlePersonCode(sbs.substring(0, sbs.length()-1).toString());
	    		}else{
	    			return ScoMessage.error("您的部门未分配工作餐申请审核人员,无法进行工作餐申请,请与管理员联系。");
	    		}
			}else{//部门提交
				if(1 == lunchApply.getStatus()){//同意
					lunchApply.setResult("2");//通过
				}
			}
			copyUpdateInfo(lunchApply);
			lunchApply.setProcessInstanceName("工作餐:"+lunchApply.getLunchcode());
			lunchApplyService.updateLunchApply(lunchApply);
			return ScoMessage.success("workspace/todo/list",ScoMessage.DEFAULT_SUCCESS_MSG);
		}
	}
	
	
	
	/**
	 * 当天是否能申请
	 */
	private String allowApply(Date now){
		String res = "";
		ResourceBundle rb = ResourceBundle.getBundle("parameter");
		//rb.clearCache();
		int finalTime = Integer.parseInt(rb.getString("lunch.supply.finaltime"));//每天截止订餐时间
		int nowTime = Integer.parseInt(sdf_hour.format(now));
		if( nowTime >= finalTime){
			res = "工作餐申请需在每天"+finalTime+"点前进行,今天已不能预定。";
		}
		return res;
	}
	

}
