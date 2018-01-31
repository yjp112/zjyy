
package com.supconit.base.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.DutyGroupPerson;
import com.supconit.base.entities.EnumDetail;
import com.supconit.base.services.DutyGroupPersonService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.cache.Cache;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import jodd.util.StringUtil;

@Controller
@RequestMapping("base/dutyGroupPerson")
public class DutyGroupPersonController extends BaseControllerSupport {

    
	@Autowired
	private DutyGroupPersonService dutyGroupPersonService;
//    @Autowired
//    private DepartmentPersonReaderServiceImpl dept;
    @Autowired
    private Cache cache;
		
    /*
    get "dutyGroupPerson" list
    */
	
	@ResponseBody
    @RequestMapping("list")
	public Pageable<DutyGroupPerson> list(@RequestParam(required = false) String treeId,
			Pagination<DutyGroupPerson> pager, DutyGroupPerson dutyGroupPerson,
			ModelMap model) {		
		if(StringUtil.isBlank(treeId)||treeId.equals("0")){
			treeId = "0";
		}
/*        List<ExDepartmentPersonInfo> list =  dept.findByPersonId(89l);
		model.put("dutyGroupPerson", dutyGroupPerson);
		model.put("pager", pager);*/
		
		return dutyGroupPersonService.findByCondition(pager, dutyGroupPerson,treeId);
	}


    /*
    save  dutyGroupPerson
    DutyGroupPerson object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(DutyGroupPerson dutyGroupPerson) {
		boolean b = dutyGroupPersonService.countExistByCondition(dutyGroupPerson);
		if(b){
			return ScoMessage.error("该人员已存在该班组,请勿重复添加");
		}
		if(dutyGroupPerson.getId()==null){
			copyCreateInfo(dutyGroupPerson);
            dutyGroupPersonService.insert(dutyGroupPerson);	
        }
        else{
            copyUpdateInfo(dutyGroupPerson);    
            dutyGroupPersonService.update(dutyGroupPerson);
        }
		return ScoMessage.success("base/dutyGroup/go","操作成功。");
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		
		dutyGroupPersonService.deleteByIds(ids);
		return ScoMessage.success("base/dutyGroup/go", "删除成功。");
	}   
    
    /**
	 * Edit DutyGroupPerson
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id,  ModelMap model) {
		if (null != id) {
			DutyGroupPerson dutyGroupPerson = dutyGroupPersonService.getById(id);
			if (null == dutyGroupPerson) {
				throw new IllegalArgumentException("Object does not exist");
			}
			model.put("dutyGroupPerson", dutyGroupPerson);
		}
       List<EnumDetail> positionList = DictUtils.getDictList(DictUtils.DictTypeEnum.POST_NAME);

       model.put("list", positionList);
		model.put("viewOnly", viewOnly);
		return "base/dutyGroupPerson/dutyGroupPerson_edit";
	}

    /*delete
  */
    @ResponseBody
    @RequestMapping(value = "getGroupByPerson", method = RequestMethod.POST)
    public ScoMessage getGroupByPerson( @RequestParam("id") Long id) {

        DutyGroupPerson dutyGroupPerson =dutyGroupPersonService.findGroupByPerson(id);
        ScoMessage scoMessage = new ScoMessage();
        if(dutyGroupPerson==null){
            scoMessage.setMessage("未在排班班组中");
            scoMessage.setStatus("0");
        }else{
//            scoMessage.setObject(dutyGroupPerson.getGroupId());
            scoMessage.setStatus("1");
        }
        return scoMessage;
    }

    @ResponseBody
    @RequestMapping(value = "findGroupByPersonId")
    public String findGroupByPersonId(Long id){
        DutyGroupPerson dp = dutyGroupPersonService.findGroupByPerson(id);
        if(null != dp)
            return "1";
        else
            return "0";

    }


}
