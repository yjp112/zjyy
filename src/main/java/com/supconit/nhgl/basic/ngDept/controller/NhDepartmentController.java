package com.supconit.nhgl.basic.ngDept.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.honeycomb.business.organization.controllers.AbstractDepartmentTreeController;
import com.supconit.honeycomb.business.organization.services.CompanyService;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.honeycomb.mvc.exceptions.EntityNotFoundException;
import com.supconit.honeycomb.util.LetterUtils;
import com.supconit.nhgl.basic.deptConfig.service.DeptConfigService;
import com.supconit.nhgl.basic.ngDept.entities.NhDepartment;
import com.supconit.nhgl.basic.ngDept.service.NhDepartmentService;

import hc.base.constants.OrderSort;
import hc.base.domains.AjaxMessage;
import hc.base.domains.OrderPart;
import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.base.domains.Suggestion;
import hc.modelextend.ExtendedModelProvider;
import hc.mvc.annotations.FormBean;
import jodd.util.StringUtil;

@Controller
@RequestMapping("nhgl/basic/ngDept")
public class NhDepartmentController  extends AbstractDepartmentTreeController{
	 private static final transient Logger logger = LoggerFactory.getLogger(NhDepartmentController.class);
	  @Autowired
	  private NhDepartmentService nhDepartmentService;
	  @Autowired
	  private CompanyService companyService;
	  @Autowired
	  private PersonService personService;
	  @Autowired
	  private ExtendedModelProvider extendedModelProvider;
	  @Autowired
	  private DeptConfigService deptConfigService;
	  
	  @ModelAttribute("newNhDepartment")
	  public <X extends NhDepartment> X generateNhDepartment()
	  {
	    return this.extendedModelProvider.createBean(NhDepartmentService.EXTEND_MODEL_DEPARTMENT_CODE, NhDepartment.class);
	  }
	  
	  @ModelAttribute("newPagination")
	  public <X extends NhDepartment> Pagination<X> generatePager()
	  {
	    Pagination<X> pager = new Pagination();
	    return pager;
	  }
	  
	  @RequestMapping(value={"list"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public <X extends NhDepartment> String list(ModelMap model)
	  {
	    NhDepartment departmentTree = this.nhDepartmentService.getTree();
	    model.put("departmentTree", JSON.toJSONString(departmentTree));
	    
	    return "nhgl/basic/ngDept/list";
	  }
	  
	  @ResponseBody
	  @RequestMapping(value={"list"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  public <X extends NhDepartment> Pageable<X> dolist(@ModelAttribute("newPagination") Pagination<X> pager, @FormBean(value="condition", modelCode="newNhDepartment") X condition, ModelMap model)
	  {
	    if (null == condition.getOrderParts()) {
	      condition.setOrderParts(new OrderPart[] { new OrderPart("LFT", OrderSort.ASC), new OrderPart("CODE", OrderSort.ASC) });
	    }
	    this.nhDepartmentService.find(pager, condition, true);
	    Map<Long, X> parentMap;
	    if (!pager.isEmpty())
	    {
	      Set<Long> set = new HashSet();
	      for (X NhDepartment : pager) {
	        set.add(NhDepartment.getPid());
	      }
	      List<X> parents = this.nhDepartmentService.findByIds(set);
	      if (!CollectionUtils.isEmpty(parents))
	      {
	        parentMap = new HashMap();
	        for (X d : parents) {
	          parentMap.put(d.getId(), d);
	        }
	        for (X d : pager) {
	          if (null != d.getPid()) {
	            d.setParent((NhDepartment)parentMap.get(d.getPid()));
	          }
	        }
	      }
	    }
	    return pager;
	  }
	  
	  @RequestMapping(value={"add", "edit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public <X extends NhDepartment> String edit(@ModelAttribute("newNhDepartment") X department, ModelMap model)
	  {
	    if (null != department.getId())
	    {
	    	department = this.nhDepartmentService.getById(department.getId().longValue());
	      if (null == department) {
	        throw new EntityNotFoundException();
	      }
	    }
	    else
	    {
	      model.put("_type", "add");
	    }
	    if ((null != department) && (null != department.getPid())) {
	    	department.setParent(this.nhDepartmentService.getById(department.getPid().longValue()));
	    }
	    model.put("department", department);
	    NhDepartment NhDepartmentTree = this.nhDepartmentService.getTree();
	    model.put("NhDepartmentJson", JSON.toJSONString(NhDepartmentTree));
	    return "nhgl/basic/ngDept/edit";
	  }
	  
	  @RequestMapping(value={"view"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public <X extends NhDepartment> String view(@ModelAttribute("newNhDepartment") X department, ModelMap model)
	  {
	    if (null != department.getId())
	    {
	    	department = this.nhDepartmentService.getById(department.getId().longValue());
	      if (null == department) {
	        throw new EntityNotFoundException();
	      }
	    }
	    if ((null != department) && (null != department.getPid())) {
	    	department.setParent(this.nhDepartmentService.getById(department.getPid().longValue()));
	    }
	    model.put("department", department);
	    return "nhgl/basic/ngDept/view";
	  }
	  
	  @ResponseBody
	  @RequestMapping(value={"add", "edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  public <X extends NhDepartment> AjaxMessage save(@FormBean(value="department", modelCode="newNhDepartment") X department)
	  {
	    if (null == department) {
	      return AjaxMessage.error("参数错误！");
	    }
	    if (StringUtil.isBlank(department.getCode())) {
	      return AjaxMessage.error("编码必须填写");
	    }
	    if (null != department.getId())
	    {
	      NhDepartment old = this.nhDepartmentService.getById(department.getId().longValue());
	      if (null == old) {
	        return AjaxMessage.error("错误的参数");
	      }
	      if ("ROOT".equalsIgnoreCase(old.getCode())) {
	        return AjaxMessage.error("部门簇根节点禁止修改！");
	      }
	    }
	    department.setCode(department.getCode().trim().toUpperCase());
	    NhDepartment sample = this.nhDepartmentService.getByCode(department.getCode());
	    if ((null != sample) && ((null == department.getId()) || (!department.getId().equals(sample.getId())))) {
	      return AjaxMessage.error(department.getCode() + "编码已经存在！");
	    }
	    if ((null != department.getParent()) && (null != department.getParent().getId()))
	    {
	    	department.setPid(department.getParent().getId());
	      if ((null != department.getId()) && (department.getId().equals(department.getPid()))) {
	        return AjaxMessage.error("上级部门不能为自己！");
	      }
	    }
	    else
	    {
	      return AjaxMessage.error("上级部门未指定！");
	    }
	    try
	    {
	    	department.setSuggestCode(LetterUtils.convertToFirstLetters(department.getName()));
	      this.nhDepartmentService.save(department);
	      return AjaxMessage.success();
	    }
	    catch (Exception e)
	    {
	      logger.error(e.getMessage(), e);
	      return AjaxMessage.error(e.getMessage());
	    }
	  }
	  
	  @ResponseBody
	  @RequestMapping(value={"delete"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  public AjaxMessage delete(@RequestParam(required=true) Long id)
	  {
	    NhDepartment old = this.nhDepartmentService.getById(id.longValue());
	    if (null == old) {
	      return AjaxMessage.error("错误的参数");
	    }
	    if ("ROOT".equalsIgnoreCase(old.getCode())) {
	      return AjaxMessage.error("部门簇根节点禁止删除！");
	    }
	    if (this.nhDepartmentService.countByPid(id.longValue()) > 0L) {
	      return AjaxMessage.error("有下级部门，不允许删除！");
	    }
	    if (this.personService.findByDepartmentId(new Pagination(), id, null).size() > 0) {
	      return AjaxMessage.error("部门下有人员，不允许删除！");
	    }
	    //判断该部门下是否有挂载部门能耗配置
	    Long count =deptConfigService.getByDeptId(id);
		if(null!=count && count.longValue()>0l){
			 return AjaxMessage.error("该部门已被使用，不能删除。");
		}
	    try
	    {
	      this.nhDepartmentService.delete(new NhDepartment(id));
	      return AjaxMessage.success();
	    }
	    catch (Exception e)
	    {
	      logger.error(e.getMessage(), e);
	      return AjaxMessage.error(e.getMessage());
	    }
	  }
	  
	  @RequestMapping(value={"select"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String select(@RequestParam(defaultValue="false") boolean muilt, @RequestParam(required=false) String selected, ModelMap model)
	  {
	    NhDepartment NhDepartmentTree = this.nhDepartmentService.getTree();
	    model.put("departmentJson", JSON.toJSONString(NhDepartmentTree));
	    model.put("muilt", Boolean.valueOf(muilt));
	    if (StringUtil.isNotBlank(selected))
	    {
	      String[] split = selected.split(",");
	      Long[] sids = new Long[split.length];
	      for (int i = 0; i < split.length; i++) {
	        sids[i] = Long.valueOf(split[i]);
	      }
	      model.put("selected", JSON.toJSONString(sids));
	    }
	    return "nhgl/basic/ngDept/select";
	  }
	  
	  @ResponseBody
	  @RequestMapping(value={"find-by-persons"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  public Map<String, String[]> findNhDepartmentNamesByPersonIds(String personIds)
	  {
	    if (StringUtil.isBlank(personIds)) {
	      return Collections.emptyMap();
	    }
	    String[] ids = StringUtil.split(personIds, ",");
	    Set<Long> set = new HashSet();
	    for (String id : ids) {
	      if (!StringUtil.isBlank(id)) {
	        set.add(Long.valueOf(id.trim()));
	      }
	    }
	    Map<Long, List<NhDepartment>> map = this.nhDepartmentService.findMapByPersonIds(set);
	    Map<String, String[]> result = new HashMap(map.size());
	    for (Map.Entry<Long, List<NhDepartment>> entry : map.entrySet())
	    {
	      StringBuilder builder = new StringBuilder();
	      StringBuilder idss = new StringBuilder();
	      for (NhDepartment d : entry.getValue())
	      {
	        builder.append(d.getName()).append(",");
	        idss.append(d.getId()).append(",");
	      }
	      if (builder.length() > 0) {
	        builder.deleteCharAt(builder.length() - 1);
	      }
	      if (idss.length() > 0) {
	        idss.deleteCharAt(idss.length() - 1);
	      }
	      result.put(String.valueOf(entry.getKey()), new String[] { builder.toString(), idss.toString() });
	    }
	    return result;
	  }
	  
	  @ResponseBody
	  @RequestMapping(value={"auto"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  public Suggestion autocomplete(String query)
	  {
	    return this.nhDepartmentService.suggest(query);
	  }
}
