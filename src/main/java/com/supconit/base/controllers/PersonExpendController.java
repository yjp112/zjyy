package com.supconit.base.controllers;

import com.alibaba.fastjson.JSON;
import com.supconit.base.entities.ExPosition;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.honeycomb.business.organization.controllers.AbstractDepartmentTreeController;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.entities.DepartmentPersonInfo;
import com.supconit.honeycomb.business.organization.entities.Person;
import com.supconit.honeycomb.business.organization.entities.Position;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.honeycomb.business.organization.services.PositionService;
import com.supconit.honeycomb.mvc.exceptions.EntityNotFoundException;

import hc.base.constants.OrderSort;
import hc.base.domains.AjaxMessage;
import hc.base.domains.AuditedEntity;
import hc.base.domains.OrderPart;
import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.modelextend.ExtendedModelProvider;
import hc.mvc.annotations.FormBean;
import hc.safety.manager.SafetyManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import jodd.util.StringUtil;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "platform/organization/personExpand" })
public class PersonExpendController extends AbstractDepartmentTreeController {
	private static final transient Logger logger = LoggerFactory
			.getLogger(PersonExpendController.class);

	@Autowired
	private PersonService personService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private ExtendedModelProvider extendedModelProvider;

	@Autowired
	private PositionService positionService;
	
	@Autowired
	private OrganizationService organizationService;
	@Resource
	private SafetyManager safetyManager;

	@ModelAttribute("newPerson")
	public ExPerson  generatePerson() {
		return this.extendedModelProvider.createBean(
				"PLAT_ORG_EX_PERSON", Person.class);
	}

	@ModelAttribute("newPagination")
	public <X extends Person> Pagination<X> generatePager() {
		Pagination pager = new Pagination();
		return pager;
	}

	@RequestMapping(value = { "list" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public <X extends Person> String list(ModelMap model) {
		Department departmentTree = this.departmentService
				.getTreeIncludePosition();
		
		model.put("departmentJson", JSON.toJSONString(departmentTree));
		return "platform/organization/person/list";
	}

	@ResponseBody
	@RequestMapping(value = { "list", "select" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public <X extends Person> Pageable<X> dolist(
			@ModelAttribute("newPagination") Pagination<X> pager,
			@FormBean(value = "condition", modelCode = "newPerson") X condition){
		if ((null == condition.getOrderParts())
				|| (condition.getOrderParts().length == 0)) {
			condition.setOrderParts(new OrderPart[] { new OrderPart("ID",
					OrderSort.DESC) });
		}
		if ((null != condition)
				&& (!CollectionUtils.isEmpty(condition.getPositions()))
				&& (null != ((Position) condition.getPositions().get(0))
						.getId()))
			this.personService.findByPositionId(pager, ((Position) condition
					.getPositions().get(0)).getId(), condition);
		else if ((null != condition)
				&& (!CollectionUtils.isEmpty(condition.getDepartments()))
				&& (null != ((Department) condition.getDepartments().get(0))
						.getId()))
			this.personService.findByDepartmentId(pager,
					((Department) condition.getDepartments().get(0)).getId(),
					condition);
		else {
			this.personService.find(pager, condition);
		}
		return pager;
	}

	@RequestMapping(value = { "edit", "add" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public <X extends Person> String edit(
			@FormBean(value = "person", modelCode = "newPerson") X person,Long departId,
			ModelMap model){
		if (null != person.getId()) {
			person = this.personService.getById(person.getId().longValue());
			if (null == person) {
				throw new EntityNotFoundException();
			}
			if (null == person.getDepartments()) {
				person.setDepartments(this.departmentService
						.findByPersonId(person.getId().longValue()));
				person.setDepartmentPersonInfos(this.personService.findDepartmentPersonInfos(person.getId().longValue()));     
			}
			if (null == person.getPositions())
				person.setPositions(this.positionService.findByPersonId(person                                    
						.getId()));
			//为department排序
			person.setDepartments(sortDepartments(person.getDepartmentPersonInfos(),person.getDepartments()));
		} else {
			model.put("_type", "add");
			
			if ((!CollectionUtils.isEmpty(person.getDepartments()))
					&& (null != ((Department) person.getDepartments().get(0))
							.getId())) {
				person.getDepartments().set(
						0,
						this.departmentService.getById(((Department) person
								.getDepartments().get(0)).getId().longValue()));
			}
			if ((!CollectionUtils.isEmpty(person.getPositions()))
					&& (null != ((Position) person.getPositions().get(0))
							.getId())) {
				Position position = this.positionService
						.getById(((Position) person.getPositions().get(0))
								.getId());
				if ((null != position) && (null != position.getDepartmentId())) {
					Department dept = this.departmentService.getById(position
							.getDepartmentId().longValue());
					if (null != dept) {
						if (null == person.getDepartments()) {
							person.setDepartments(new ArrayList());
						}
						person.getDepartments().add(dept);
					}
				}
				person.getPositions().set(0, position);
			}
		}
		
		
		if (!CollectionUtils.isEmpty(person.getDepartments())) {
			StringBuilder departmentIds = new StringBuilder();
			for (Department d : person.getDepartments()) {
				if (null == d)
					continue;
				if (null == d.getId())
					continue;
				departmentIds.append(d.getId()).append(",");
			}
			for(int i=0;i<person.getDepartments().size();i++){
				if(OrganizationUtils.getFullDeptNameByDeptId(person.getDepartments().get(i).getId())!=null){
					person.getDepartments().get(i).setName(OrganizationUtils.getFullDeptNameByDeptId(person.getDepartments().get(i).getId()));
				}
			}
			if (departmentIds.length() > 0) {
				departmentIds.deleteCharAt(departmentIds.length() - 1);
			}
			model.addAttribute("departmentIds", departmentIds);
		}
		if (!CollectionUtils.isEmpty(person.getPositions())) {
			StringBuilder positionIds = new StringBuilder();
			for (Position p : person.getPositions()) {
				if (null == p)
					continue;
				if (null == p.getId())
					continue;
				positionIds.append(p.getId()).append(",");
			}
			if (positionIds.length() > 0) {
				positionIds.deleteCharAt(positionIds.length() - 1);
			}
			model.addAttribute("positionIds", positionIds);
		}
		boolean selectDepart=false;
		if(departId!=null){
			selectDepart=true;
			model.put("departId", departId);
			model.put("departName", OrganizationUtils.getFullDeptNameByDeptId(departId));
			model.put("selectDepart", selectDepart);
		}
		model.put("educations", DictUtils.getDictList(DictTypeEnum.EDUCACTION));// 学历数据字典
		model.put("personStatus",
				DictUtils.getDictList(DictTypeEnum.PERSON_STATUS));// 在职情况数据字典
		model.put("personSexs", DictUtils.getDictList(DictTypeEnum.PERSONSEX));// 性别数据字典
		model.put("postIds", DictUtils.getDictList(DictTypeEnum.POSTIDS));// 级别数据字典 
		model.put("person", person);
		return "platform/organization/person/edit";
	}

	private List<Department> sortDepartments(
			List<DepartmentPersonInfo> departmentPersonInfos,
			List<Department> departments) {
		List<Department> tempDepartments=new ArrayList<Department>();
		for(int i=0;i<departmentPersonInfos.size();i++){
			for(int j=0;j<departments.size();j++){
				Long d=departmentPersonInfos.get(i).getDepartmentId();
				Long s=departments.get(j).getId();
				if(departmentPersonInfos.get(i).getDepartmentId().equals(departments.get(j).getId())){
					tempDepartments.add(i, departments.get(j));
				}
			}
		}
		return tempDepartments;
	}

	@RequestMapping(value = { "add", "edit" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public <X extends Person> AjaxMessage save(
			@FormBean(value = "person", modelCode = "newPerson") X person) {
		if (null != person.getId()) {
			Person oldPerson = this.personService.getById(person.getId()
					.longValue());
			if (null == oldPerson)
				return AjaxMessage.error("错误的参数.");
			person.setCode(oldPerson.getCode());
		}
		if (StringUtil.isBlank(person.getCode())) {
			return AjaxMessage.error("编码必须填写");
		}
		//检验部门是否为空
		List<DepartmentPersonInfo> list =new ArrayList<DepartmentPersonInfo>();
		for(int i=0;i<person.getDepartmentPersonInfos().size();i++){
			if(!StringUtil.isEmpty(person.getDepartments().get(i).getName())){
				copyCreateInfo(person.getDepartments().get(i));	 	
				list.add(person.getDepartmentPersonInfos().get(i));
			}
		}
		if (list.size()==0) {
			return AjaxMessage.error("部门必须填写");
		}else{
			person.setDepartmentPersonInfos(list);
		}
		
		person.setCode(person.getCode().trim().toUpperCase());
		Person sample = this.personService.getByCode(person.getCode());
		if ((null != sample)
				&& (((null == person.getId()) || (!person.getId().equals(
						sample.getId())))))
			return AjaxMessage.error(new StringBuilder()
					.append(person.getCode()).append("编码已经存在！").toString());

		if ((person.getDepartmentPersonInfos() == null)
				|| (person.getDepartmentPersonInfos().size() == 0)) {
			DepartmentPersonInfo departmentPersonInfo = new DepartmentPersonInfo();
			departmentPersonInfo.setPersonId(person.getId());
			Department rootDepartment = this.departmentService
					.getByCode("ROOT");
			departmentPersonInfo.setDepartmentId(rootDepartment.getId());
			List departmentPersonInfos = new ArrayList(1);
			departmentPersonInfos.add(departmentPersonInfo);
			person.setDepartmentPersonInfos(departmentPersonInfos);
		}
		try {
			copyCreateInfo(person);			
			this.personService.save(person);
			return AjaxMessage.success("保存成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxMessage.error(e.getMessage());
		}
	}

	@RequestMapping(value = { "delete" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public AjaxMessage delete(Long id) {
		try {
			this.personService.delete(new Person(id.longValue()));
			return AjaxMessage.success().setMessage("删除人员成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxMessage.error(e.getMessage());
		}
	}
	
	protected void copyCreateInfo(AuditedEntity audit) {
		audit.setCreateTime(new Date()); 
		audit.setCreator(getCurrentPersonId());
	}
	protected Long getCurrentPersonId() {
		return getCurrentUser().getPersonId();
	}
	protected User getCurrentUser() { 
		// SpringContextHolder
		// return userService.getCurrentUser();
		return (User) this.safetyManager.getCurrentUser();
	}
	
	
	//查找部门所有上级节点，并且拼凑成字符串
	@RequestMapping("getboot")
	@ResponseBody
	public String getfulldepartment(long departId){
		if(OrganizationUtils.getFullDeptNameByDeptId(departId)!=null){
			return OrganizationUtils.getFullDeptNameByDeptId(departId);
		}
		return "";
	}
	//根据部门返回岗位树
	@RequestMapping(value = "selectp", method = RequestMethod.GET)
	public String select(@RequestParam(defaultValue = "false") boolean muilt,
			@RequestParam(required = false) String selected,
			@RequestParam(required = false) Long departmentIds, ModelMap model) {
		List<ExPosition> postionTree=organizationService.findSubPositionById(departmentIds);
		model.put("postionTree", postionTree); 
		return "platform/organization/position/lookup";
	}
	
}