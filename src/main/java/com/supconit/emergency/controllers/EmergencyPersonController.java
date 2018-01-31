package com.supconit.emergency.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.emergency.daos.EmergencyGroupDao;
import com.supconit.emergency.entities.EmergencyGroup;
import com.supconit.emergency.entities.EmergencyPerson;
import com.supconit.emergency.services.EmergencyPersonService;


@Controller
@RequestMapping("emergency/emergencyPerson")
public class EmergencyPersonController extends BaseControllerSupport{

	@Autowired
	private EmergencyPersonService emergencyPersonService;
	@Autowired
	private EmergencyGroupDao		emergencyGroupDao;	
    /*
    get "emergencyPerson" list
    */
	private String flag;
	@ResponseBody
    @RequestMapping("list/{flag}")
	public Pageable<EmergencyPerson> list(@RequestParam(required = false) String treeId,
			Pagination<EmergencyPerson> page, @ModelAttribute EmergencyPerson emergencyPerson,
			ModelMap model,@PathVariable String flag){
		this.flag=flag;
		if(StringUtil.isBlank(treeId)||treeId.equals("0")){
			treeId = "0";
		}
		//消防“1”，安防“2”
		if("xf".equals(flag)){
			emergencyPerson.setType(Integer.valueOf(1));
		}else if("af".equals(flag)){
			emergencyPerson.setType(Integer.valueOf(2));
		}
		Pageable<EmergencyPerson> pager = emergencyPersonService.findByCondition(page, emergencyPerson,treeId);	
		model.put("emergencyPerson", emergencyPerson);
		model.put("pager", pager);
		model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.EMERGENCY_HEADER));
		
		return pager;
	}

    /*
    save  emergencyPerson
    EmergencyPerson object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(@ModelAttribute EmergencyPerson emergencyPerson) {
		EmergencyGroup emergencyGroup = emergencyGroupDao.getById(emergencyPerson.getGroupId());	
         if(emergencyGroup.getParentId() == 0){
        	 return ScoMessage.error("所属分组选择有误！");
         }
		if(emergencyPerson.getId()==null){
        	boolean b = emergencyPersonService.countByGroupIdAndPersonId(emergencyPerson);
        	if(b){
        		copyCreateInfo(emergencyPerson);
                emergencyPersonService.insert(emergencyPerson);	
        	}else{
        		return ScoMessage.error("该成员已加入该组,不能重复添加。");
        	}
        }
        else{
            copyUpdateInfo(emergencyPerson);    
            emergencyPersonService.update(emergencyPerson);
        }
            
         return ScoMessage.success("emergency/emergencyGroup/go/"+flag,"操作成功。");
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		emergencyPersonService.deleteByIds(ids);
		return ScoMessage.success("emergency/emergencyGroup/go/"+flag, "删除成功。");
	}   
    
    /**
	 * Edit EmergencyPerson
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id,  ModelMap model) {
		if (null != id) {
			EmergencyPerson emergencyPerson = emergencyPersonService.getById(id);
			if (null == emergencyPerson) {
				throw new IllegalArgumentException("Object does not exist");
			}
			
			model.put("emergencyPerson", emergencyPerson);			
		}
		model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.EMERGENCY_HEADER));
		model.put("viewOnly", viewOnly);
		return "emergency/emergencyPerson/emergencyPerson_edit";
	}
	
}
