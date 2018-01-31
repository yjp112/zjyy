package com.supconit.repair.controllers;

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

import com.supconit.base.entities.EnumDetail;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.repair.entities.RepairEvtCategory;
import com.supconit.repair.entities.RepairEvtCategoryPerson;
import com.supconit.repair.services.RepairEvtCategoryPersonService;
import com.supconit.repair.services.RepairEvtCategoryService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("repair/repairEvtCategoryPerson")
public class RepairEvtCategoryPersonController  extends BaseControllerSupport{

    
	@Autowired
	private RepairEvtCategoryPersonService repairEvtCategoryPersonService;
	@Autowired
	private RepairEvtCategoryService repairEvtCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;
	
    /*
    get "device" list
    */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(ModelMap model) {
		List<GeoArea> treeListLou = geoAreaService.findTree();
		model.put("treeListLou", treeListLou);		
		List<RepairEvtCategory> treeListType = repairEvtCategoryService.findAll();
		model.put("treeListType", treeListType);
		
		return "repair/attendant/list";
	}
    
    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping(value="list",method=RequestMethod.POST)
	public Pageable<RepairEvtCategoryPerson> page(Pagination<RepairEvtCategoryPerson> pager, 
			@ModelAttribute RepairEvtCategoryPerson repairEvtCategoryPerson,
			ModelMap model) {
    	repairEvtCategoryPerson.setCategoryType(1);
		return repairEvtCategoryPersonService.findByCondition(pager, repairEvtCategoryPerson);
	}
	
    /**
	 * Edit RepairEvtCategoryPerson
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  String areaId,ModelMap model,String categoryId,String from) {
    	List<GeoArea> treeListLou = geoAreaService.findTree();
		model.put("treeListLou", treeListLou);		
		List<RepairEvtCategory> treeListType = repairEvtCategoryService.findAll();
		model.put("treeListType", treeListType);
		List<EnumDetail> listRepairMode = DictUtils.getDictList(DictTypeEnum.REPAIR_MODE);
		model.put("listRepairMode", listRepairMode);
		if(id==null){
			//新增
			RepairEvtCategoryPerson evtCP =new RepairEvtCategoryPerson();
			evtCP.setRepairMode(0);
			evtCP.setCategoryType(1);
			model.put("repairEvtCategoryPerson", evtCP);	
			return "repair/attendant/edit";
		}

		//修改
		RepairEvtCategoryPerson repairEvtCategoryPerson = repairEvtCategoryPersonService.findById(id);
		if (null == repairEvtCategoryPerson) {
			throw new IllegalArgumentException("Object does not exist");
		}

		List<RepairEvtCategoryPerson> list=repairEvtCategoryPersonService.findByPersonIdandCategoryId(repairEvtCategoryPerson);
		for (GeoArea area:treeListLou){
			for(RepairEvtCategoryPerson person:list){
				if(area.getId().equals(person.getAreaId())){
					area.setChecked(true);
				}
			}
		}
		if(UtilTool.isEmpty(repairEvtCategoryPerson.getCategoryName())){
			repairEvtCategoryPerson.setCategoryName("维修类别簇");
		}
	
		model.put("repairEvtCategoryPerson", repairEvtCategoryPerson);
	
		return "repair/attendant/edit";
	}
    /*save  RepairEvtCategoryPerson
    RepairEvtCategoryPerson object instance 
    */
    @ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(String flag,RepairEvtCategoryPerson repairEvtCategoryPerson, String areaIds,String categoryIds) {
    	if(repairEvtCategoryPerson.getId()==null){
			copyCreateInfo(repairEvtCategoryPerson);
			repairEvtCategoryPersonService.insert(repairEvtCategoryPerson,areaIds,categoryIds);
    	}else{
			copyCreateInfo(repairEvtCategoryPerson);
    		repairEvtCategoryPersonService.update(repairEvtCategoryPerson,areaIds,categoryIds);
    	}
    	return ScoMessage.success(ScoMessage.SAVE_SUCCESS_MSG);
	}
    /*MasterTable.Name,0)%>  
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {	
		repairEvtCategoryPersonService.deleteByIds(ids);	
		return ScoMessage.success(ScoMessage.DELETE_SUCCESS_MSG);
	}   
	
	/**
	 *
	 * @param type
	 *            1:单选；2：多选
	 * @return
	 */
	@RequestMapping("chooseperson/{type}/{txtId}/{txtName}")
	public String choosepersonPage(@PathVariable int type,@PathVariable String txtId,@PathVariable String txtName, String deptId, String deptName,String dialogId,ModelMap map) {
       map.put("type",type);
       map.put("txtId",txtId);
       map.put("txtName",txtName);
       map.put("deptId",deptId);
       map.put("deptName",deptName);
       map.put("dialogId",dialogId);
           return "repair/attendant/choose_repairperson"; 
	}
   

}
