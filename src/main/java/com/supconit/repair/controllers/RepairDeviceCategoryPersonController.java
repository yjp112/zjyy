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

import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.EnumDetail;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.repair.entities.RepairDeviceCategoryPerson;
import com.supconit.repair.services.RepairDeviceCategoryPersonService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("repair/repairDeviceCategoryPerson")
public class RepairDeviceCategoryPersonController  extends BaseControllerSupport{

    
	@Autowired
	private RepairDeviceCategoryPersonService repairDeviceCategoryPersonService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;
	
	
    /*
    get "device" list
    */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(ModelMap model) {
		List<GeoArea> treeListLou = geoAreaService.findTree();
		model.put("treeListLou", treeListLou);		
		List<DeviceCategory> treeListType = deviceCategoryService.findAll();
		model.put("treeListType", treeListType);
		
		return "repair/attendant/person_list";
	}
    
    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping(value="list",method=RequestMethod.POST)
	public Pageable<RepairDeviceCategoryPerson> page(Pagination<RepairDeviceCategoryPerson> pager, 
			@ModelAttribute RepairDeviceCategoryPerson repairDeviceCategoryPerson,
			ModelMap model) {
    	repairDeviceCategoryPerson.setCategoryType(2);
		return repairDeviceCategoryPersonService.findByCondition(pager, repairDeviceCategoryPerson);
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
		List<DeviceCategory> treeListType = deviceCategoryService.findAll();
		model.put("treeListType", treeListType);
		List<EnumDetail> listRepairMode = DictUtils.getDictList(DictTypeEnum.REPAIR_MODE);
		model.put("listRepairMode", listRepairMode);
		if(id==null){
			//新增
			RepairDeviceCategoryPerson evtCP =new RepairDeviceCategoryPerson();
			evtCP.setRepairMode(0);
			evtCP.setCategoryType(2);
			model.put("repairEvtCategoryPerson", evtCP);	
			return "repair/attendant/person_edit";
		}

		//修改
		RepairDeviceCategoryPerson repairDeviceCategoryPerson = repairDeviceCategoryPersonService.findById(id);
		if (null == repairDeviceCategoryPerson) {
			throw new IllegalArgumentException("Object does not exist");
		}

		List<RepairDeviceCategoryPerson> list=repairDeviceCategoryPersonService.findByPersonIdandCategoryId(repairDeviceCategoryPerson);
		for (GeoArea area:treeListLou){
			for(RepairDeviceCategoryPerson person:list){
				if(area.getId().equals(person.getAreaId())){
					area.setChecked(true);
				}
			}
		}
		if(UtilTool.isEmpty(repairDeviceCategoryPerson.getCategoryName())){
			repairDeviceCategoryPerson.setCategoryName("设备类别簇");
		}
	
		model.put("repairEvtCategoryPerson", repairDeviceCategoryPerson);
	
		return "repair/attendant/person_edit";
	}
    /*save  RepairEvtCategoryPerson
    RepairEvtCategoryPerson object instance 
    */
    @ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(String flag,RepairDeviceCategoryPerson repairDeviceCategoryPerson, String areaIds,String categoryIds) {
    	if(repairDeviceCategoryPerson.getId()==null){
			copyCreateInfo(repairDeviceCategoryPerson);
			repairDeviceCategoryPersonService.insert(repairDeviceCategoryPerson,areaIds,categoryIds);
    	}else{
			copyCreateInfo(repairDeviceCategoryPerson);
			repairDeviceCategoryPersonService.update(repairDeviceCategoryPerson,areaIds,categoryIds);
    	}
    	return ScoMessage.success(ScoMessage.SAVE_SUCCESS_MSG);
	}
    /*MasterTable.Name,0)%>  
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {	
		repairDeviceCategoryPersonService.deleteByIds(ids);	
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
