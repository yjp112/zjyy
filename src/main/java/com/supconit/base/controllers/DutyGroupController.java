
package com.supconit.base.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.daos.DutyGroupDao;
import com.supconit.base.daos.DutyGroupPersonDao;
import com.supconit.base.domains.Organization;
import com.supconit.base.entities.DutyGroup;
import com.supconit.base.entities.DutyGroupPerson;
import com.supconit.base.services.DutyGroupPersonService;
import com.supconit.base.services.DutyGroupService;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.SerialNumberGenerator;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("base/dutyGroup")
public class DutyGroupController extends BaseControllerSupport {

    
	@Autowired
	private DutyGroupService dutyGroupService;
	@Autowired
	private DutyGroupPersonService dsutyGroupPersonService;
	@Autowired
	private DutyGroupDao		dutyGroupDao;	
	@Autowired
	private DutyGroupPersonDao		dutyGroupPersonDao;	
	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping("go")
	public String go(ModelMap model) {
		List<DutyGroup> dutyGroup_tree = dutyGroupService.findTree();
		model.put("dutyGroup_tree", dutyGroup_tree);
		if(null!=dutyGroup_tree && dutyGroup_tree.size()>0){
			List<DutyGroupPerson> dutyGroupPerson_tree = dsutyGroupPersonService.findTree(dutyGroup_tree);
			model.put("dutyGroupPerson_tree", dutyGroupPerson_tree);
		}
		return "base/dutyGroup/dutyGroup_list";
	}
		
    /*
    get "dutyGroup" list
    */
	@ResponseBody
    @RequestMapping("list")
	public Pageable<DutyGroup> list(
			Pagination<DutyGroup> pager, @ModelAttribute DutyGroup dutyGroup,
			ModelMap model) {

//		model.put("dutyGroup", dutyGroup);
//		model.put("pager", pager);
		
		return dutyGroupService.findByCondition(pager, dutyGroup);
	}


    /*
    save  dutyGroup
    DutyGroup object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(DutyGroup dutyGroup) {	    
		boolean judgeSave = false;
		judgeSave = checkToSave(dutyGroup);
		if(!judgeSave){
	        if(dutyGroup.getId()==null){
	            copyCreateInfo(dutyGroup);
	            dutyGroupService.insert(dutyGroup);	
	        }
	        else{
	            copyUpdateInfo(dutyGroup);    
	            dutyGroupService.update(dutyGroup);
	        }           
	        return ScoMessage.success("base/dutyGroup/go","操作成功。");
		}
		else{
			return ScoMessage.error("编码[" + dutyGroup.getGroupCode()+ "]或名称[" + dutyGroup.getGroupName()+ "]已经被占用。");
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
			dutyGroupService.deleteByIds(ids);
			return ScoMessage.success("base/dutyGroup/go", "删除成功。");
		}
		else{
			return ScoMessage.error("该班组有组员，不能删除");
		}
	}   
    
    /**
	 * Edit DutyGroup
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id,  ModelMap model) {
		if (null != id) {
			DutyGroup dutyGroup = dutyGroupService.getById(id);
			if (null == dutyGroup) {
				throw new IllegalArgumentException("Object does not exist");
			}
			String deptName = organizationService.getFullDeptNameByDeptId(dutyGroup.getDepId());
			dutyGroup.setDepName(deptName);
			if(dutyGroup.getParentId()==0){
                dutyGroup.setParentName("值班班组");
            }
			model.put("dutyGroup", dutyGroup);			
		}else{ 
            DutyGroup dutyGroup = new DutyGroup();
            dutyGroup.setGroupCode( SerialNumberGenerator.getSerialNumbers("BZ"));
            model.put("dutyGroup", dutyGroup);
        }
		model.put("viewOnly", viewOnly);
		return "base/dutyGroup/dutyGroup_edit";
	}
    
    @RequestMapping("lookup")
    public String lookup(String txtId,String txtName,String userId,String user,String telphone,String handlePersonCode, String type,
    		@RequestParam(required = false) String from,
    		@RequestParam(required = false) Long treeSonId,
    		@RequestParam(required = false) Long treeFatherId,
            @RequestParam(required = false) String id,
    		ModelMap model,String dialogId) {
    	List<DutyGroup> dutyGroup_tree = new ArrayList<DutyGroup>();
    	if(treeSonId!=null){
    		DutyGroup dg = dutyGroupService.getById(treeSonId);
    		dutyGroup_tree = dutyGroupService.findSubById(dg.getParentId());
    	}else if(treeFatherId!=null){
    		dutyGroup_tree = dutyGroupService.findSubById(treeFatherId);
    	}else{
    		dutyGroup_tree = dutyGroupService.findTree();
    	}
		model.put("dutyGroup_tree", dutyGroup_tree);
        model.put("txtId", txtId);  
        model.put("txtName", txtName);
        model.put("dialogId",dialogId);
        model.put("userId",userId);
        model.put("user",user);
        model.put("telphone",telphone);
        model.put("handlePersonCode",handlePersonCode);
        model.put("type",type);
        model.put("id",id);
        if(null!=from && from.equals("groupChoose"))
        	return "base/dutyGroup/dutyGroup_groupChoose_lookup";
        else
        	return "base/dutyGroup/dutyGroup_lookup";
    }
    
    private boolean checkToSave(DutyGroup dutyGroup){
    	boolean judgeName = false;
		boolean judgeSave = false;
		List<DutyGroup> Grouplist = dutyGroupDao.findAll();
		if(Grouplist.size() > 0){
			if(dutyGroup.getId() == null){ // 新增
				for(DutyGroup dgroup:Grouplist){
					if(dgroup.getGroupName().equals(dutyGroup.getGroupName())){
						judgeName = true;
					}
				}
			}
			else{ // 修改
				for(DutyGroup dgroup:Grouplist){
					long currentId = dutyGroup.getId();
					long dgroupId = dgroup.getId();					
					if(currentId != dgroupId){
						if(dgroup.getGroupName().equals(dutyGroup.getGroupName())){
							judgeName = true;
						}
					}
				}				
			}
		}
        List<DutyGroup> list = new ArrayList<DutyGroup>();
		list = dutyGroupDao.findByCode(dutyGroup.getGroupCode());
		if (null != list && list.size() >= 1) {
			if (list.size() > 1) {
				judgeSave = true;
			} else {
				// list.size()==1
				if (dutyGroup.getId() != null) {
					// update
					DutyGroup old = list.get(0);
					if (dutyGroup.getId().longValue() == old.getId().longValue()) {
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
   	List<DutyGroupPerson> listPersons = dutyGroupPersonDao.findGroupPersons(id);
   	if(null!=listPersons && listPersons.size()>0){
   		return false;
   	}
   	//是否有子班组
   	List<DutyGroup> listGroups = dutyGroupDao.findSubByRoot(id);
   	if(null!=listGroups && listGroups.size()>0){
   		return false;
   	}
       return true;
   }    
}
