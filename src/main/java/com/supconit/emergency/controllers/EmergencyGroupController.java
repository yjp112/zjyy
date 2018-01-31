package com.supconit.emergency.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.ArrayList;
import java.util.List;

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
import com.supconit.emergency.daos.EmergencyPersonDao;
import com.supconit.emergency.entities.EmergencyGroup;
import com.supconit.emergency.entities.EmergencyPerson;
import com.supconit.emergency.services.EmergencyGroupService;
import com.supconit.emergency.services.EmergencyPersonService;


@Controller
@RequestMapping("emergency/emergencyGroup")
public class EmergencyGroupController extends BaseControllerSupport{

	@Autowired
	private EmergencyGroupService emergencyGroupService;
	@Autowired
	private EmergencyPersonService emergencyPersonService;
	@Autowired
	private EmergencyGroupDao		emergencyGroupDao;	
	@Autowired
	private EmergencyPersonDao		emergencyPersonDao;	
	private String flag;
	private Long flag1;
	@RequestMapping("go/{flag}")
	public String go(@PathVariable String flag,ModelMap model) {
		this.flag=flag;
		if("xf".equals(flag)){
			flag1 = 1l;
    	}else if("af".equals(flag)){
    		flag1 = 2l;
    	}
		model.put("flag", flag);
		List<EmergencyGroup> emergencyGroup_tree = emergencyGroupService.findTree(flag1);
		model.put("emergencyGroup_tree", emergencyGroup_tree);
		if(null!=emergencyGroup_tree&&emergencyGroup_tree.size()>0){
			List<EmergencyPerson> emergencyPerson_tree = emergencyPersonService.findTree(emergencyGroup_tree);
			model.put("emergencyPerson_tree", emergencyPerson_tree);
		}
		model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.EMERGENCY_TYPE));
		return "emergency/emergencyGroup/emergencyGroup_list";
	}
		
    /*
    get "emergencyGroup" list
    */
	@ResponseBody
    @RequestMapping("list")
	public Pageable<EmergencyGroup> list(
			Pagination<EmergencyGroup> page, @ModelAttribute EmergencyGroup emergencyGroup,
			ModelMap model){
		if("xf".equals(flag)){
			emergencyGroup.setEmergencyType(1);
    	}else if("af".equals(flag)){
    		emergencyGroup.setEmergencyType(2);
    	}
		Pageable<EmergencyGroup> pager = emergencyGroupService.findByCondition(page, emergencyGroup);	
		
		model.put("pager", pager);
		
		return pager;
	}

    /*
    save  emergencyGroup
    EmergencyGroup object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(EmergencyGroup emergencyGroup) {	 
		if(emergencyGroup.getParentId() != 0){
			EmergencyGroup parent = emergencyGroupDao.findByPid(emergencyGroup.getParentId()).get(0);
			if(!emergencyGroup.getEmergencyType().equals(parent.getEmergencyType())){
				//return ScoMessage.error("所属分组[" + parent.getGroupName()+ "]与添加分组[" + emergencyGroup.getGroupName()+ "]的应急类别不一致！请修改。");
				return ScoMessage.error("应急类别有误！请重新选择。");
			}
		}
		boolean judgeSave = false;
		judgeSave = checkToSave(emergencyGroup);
		if(!judgeSave){
	        if(emergencyGroup.getId()==null){
	            copyCreateInfo(emergencyGroup);
	            emergencyGroupService.insert(emergencyGroup);	
	        }
	        else{
	            copyUpdateInfo(emergencyGroup);    
	            emergencyGroupService.update(emergencyGroup);
	        }           
	        return ScoMessage.success("emergency/emergencyGroup/go/"+flag,"操作成功。");
		}
		else{
			return ScoMessage.error("编码[" + emergencyGroup.getGroupCode()+ "]或名称[" + emergencyGroup.getGroupName()+ "]已经被占用。");
		}
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		boolean judge = true;
		for(int i =0;i<ids.length;i++)
        {
            if(!isAllowDelete(ids[i])){
            	judge = judge && false;
            }
            else{
            	judge = judge && true;
            }
        }  
			
		if(judge){
			emergencyGroupService.deleteByIds(ids);
			return ScoMessage.success("emergency/emergencyGroup/go/"+flag, "删除成功。");
		}
		else{
			return ScoMessage.error("该组有组员，不能删除");
		}
	}   
    
    /**
	 * Edit EmergencyGroup
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id,  ModelMap model) {
		if (null != id) {
			EmergencyGroup emergencyGroup = emergencyGroupService.getById(id);
//			if (null == emergencyGroup) {
//				throw new IllegalArgumentException("Object does not exist");
//			}
			
			model.put("emergencyGroup", emergencyGroup);			
		}	
		model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.EMERGENCY_TYPE));
		model.put("viewOnly", viewOnly);
		return "emergency/emergencyGroup/emergencyGroup_edit";
	}
    
    @RequestMapping("lookup")
    public String lookup(String txtId,String txtName,
    		@RequestParam(required = false) String from,
    		@RequestParam(required = false) Long treeSonId,
    		@RequestParam(required = false) Long treeFatherId,
    		ModelMap model,String dialogId) {
    	List<EmergencyGroup> emergencyGroup_tree = new ArrayList<EmergencyGroup>();
    	if(treeSonId!=null){
    		EmergencyGroup eg = emergencyGroupService.getById(treeSonId);
    		emergencyGroup_tree = emergencyGroupService.findSubById(eg.getParentId());
    	}else if(treeFatherId!=null){
    		emergencyGroup_tree = emergencyGroupService.findSubById(treeFatherId);
    	}else{
    		emergencyGroup_tree = emergencyGroupService.findTree(flag1);
    	}
		model.put("emergencyGroup_tree", emergencyGroup_tree);
        model.put("txtId", txtId);  
        model.put("txtName", txtName);
        model.put("dialogId",dialogId);
        if(null!=from && from.equals("groupChoose"))
        	return "emergency/emergencyGroup/emergencyGroup_groupChoose_lookup";
        else
        	return "emergency/emergencyGroup/emergencyGroup_lookup";
    }
    
    private boolean checkToSave(EmergencyGroup emergencyGroup){
    	boolean judgeName = false;
		boolean judgeSave = false;
		
		List<EmergencyGroup> Grouplist = emergencyGroupDao.findAll();
		if(Grouplist.size() > 0){
			if(emergencyGroup.getId() == null){ // 新增
				for(EmergencyGroup dgroup:Grouplist){
					if(dgroup.getGroupName().equals(emergencyGroup.getGroupName())){
						judgeName = true;
					}
				}
			}
			else{ // 修改
				for(EmergencyGroup dgroup:Grouplist){
					long currentId = emergencyGroup.getId();
					long dgroupId = dgroup.getId();					
					if(currentId != dgroupId){
						if(dgroup.getGroupName().equals(emergencyGroup.getGroupName())){
							judgeName = true;
						}
					}
				}				
			}
		}
        List<EmergencyGroup> list = new ArrayList<EmergencyGroup>();
		list = emergencyGroupDao.findByCode(emergencyGroup.getGroupCode());
		if (null != list && list.size() >= 1) {
			if (list.size() > 1) {
				judgeSave = true;
			} else {
				// list.size()==1
				if (emergencyGroup.getId() != null) {
					// update
					EmergencyGroup old = list.get(0);
					if (emergencyGroup.getId().longValue() == old.getId().longValue()) {
						judgeSave = false;
					} else {
						judgeSave = true;
					}
				} else {
					// insert
					judgeSave = true;
				}

			}
		}
		return judgeSave || judgeName;
    }  
    //Check that allows you to delete 
   private boolean isAllowDelete(Long id)
   {	
   	//是否有组员
   	List<EmergencyPerson> listPersons = emergencyPersonDao.findGroupPersons(id);
   	if(null!=listPersons && listPersons.size()>0){
   		return false;
   	}
   	//是否有子组
   	List<EmergencyGroup> listGroups = emergencyGroupDao.findSubByRoot(id);
   	if(null!=listGroups && listGroups.size()>0){
   		return false;
   	}
       return true;
   }  
}
