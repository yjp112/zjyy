package com.supconit.repair.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.EnumDetail;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.repair.entities.Repair;
import com.supconit.repair.services.RepairService;

@Controller
@RequestMapping("repair/repair")
public class RepairController extends BaseControllerSupport {

    
	@Autowired
	private RepairService repairService;
	
	
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy");
    /*
    get "repairEvtCategory" list
    */
	@RequestMapping(value="analyseCategory")
	public String listCategory(ModelMap model ,Repair repair) {
    	model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.EMERGENCY));
		return "repair/repair/category";
	}
	@RequestMapping(value="analyseMode")
	public String listMode(ModelMap model ,Repair repair) {
		return "repair/repair/mode";
	}
	@RequestMapping("analyseGrade/{type}")
	public String listGrad(@PathVariable(value = "type") int type,ModelMap model ,Repair repair) {
		String path = "";
		switch (type) {
		case 1:
			path = "repair/repair/grade";
			break;
		case 2:
			path = "repair/repair/grade_month";
			break;
		default:
			break;
		}
		return path;
	}
	@RequestMapping(value="analyseDelay")
	public String listDelay(ModelMap model ,Repair repair) {
		return "repair/repair/delay";
	}
	@RequestMapping(value="analyseDelayByself")
	public String listDelayByself(ModelMap model ,Repair repair) {
		return "repair/repair/delayByself";
	}
	@RequestMapping(value="costSaving")
	public String costSaving(ModelMap model ,Repair repair) {
		model.put("startTime", sf.format(new Date()));
		return "repair/repair/repairCostSaving";
	}
	@RequestMapping(value="trend/{type}")
	public String trend(@PathVariable(value = "type") int type,ModelMap model ,Repair repair) {
		String path = "";
		switch (type) {
		case 0:
			path = "repair/repair/repairTrend0";
			break;
		case 1:
			path = "repair/repair/repairTrend1";
			break;
		default:
			break;
		}
		model.put("startTime", sf.format(new Date()));
		return path;
	}

    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping(value="pie",method=RequestMethod.POST)
	public Pageable<Repair> pie(ModelMap model ,Repair repair) {
    	List<Repair> causelst = repairService.findByCauseType(repair);
    	List<Repair> repairlst=repairService.findByRepairType(repair);
    	List<EnumDetail> enumcause=DictUtils.getDictList(DictTypeEnum.CAUSE_TYPE);
    	List<EnumDetail> enumrepair=DictUtils.getDictList(DictTypeEnum.REPAIR_TYPE);
    	List<Repair> rlst=new ArrayList<Repair>();
    	int i=0;
    	for(EnumDetail e : enumcause){
    		i=0;
    		for(Repair r:causelst){
    			if(e.getEnumText().equals(r.getCauseValue())){
    				Repair re=new Repair();
    				re.setCauseValue(r.getCauseValue());
    				re.setSumCause(r.getSumCause());
    				rlst.add(re);
    				continue;
    			}else{
    				i++;
    				if(i==causelst.size()){
    		    		Repair re=new Repair();
    		    		re.setCauseValue(e.getEnumText());
    		    		re.setSumCause("0");
    		    		rlst.add(re);
    				}
    			}
    		}
    	}
    	for(EnumDetail e : enumrepair){
    		i=0;
    		for(Repair r:repairlst){
    			if(e.getEnumText().equals(r.getRepairTypeValue())){
    				Repair re=new Repair();
    				re.setRepairTypeValue(r.getRepairTypeValue());
    				re.setSumRepair(r.getSumRepair());
    				rlst.add(re);
    				continue;
    			}else{
    				i++;
    				if(i==repairlst.size()){
    		    		Repair re=new Repair();
    		    		re.setRepairTypeValue(e.getEnumText());
    		    		re.setSumRepair("0");
    		    		rlst.add(re);
    				}
    			}
    		}
    	}
    	Pageable<Repair> pager=new Pagination<Repair>(rlst);
    	pager.setPageNo(1);
        pager.setPageSize(Integer.MAX_VALUE);
        pager.setTotal(rlst.size());
    	return pager;
	}
    @ResponseBody
    @RequestMapping("mode")
    public Pageable<Repair> mode(@ModelAttribute Repair repair) {
    	List<Repair> lst = repairService.findByRepairMode(repair);
    	Pageable<Repair> pager=new Pagination<Repair>(lst);
    	pager.setPageNo(1);
    	pager.setPageSize(Integer.MAX_VALUE);
    	pager.setTotal(lst.size());
    	return pager;
    }
    @ResponseBody
    @RequestMapping("grade")
    public Pageable<Repair> grade(@ModelAttribute Repair repair) {
    	List<Repair> lst = repairService.findByGrade(repair);
    	List<Repair> groupslst=repairService.findGradeByGroups(repair);
    	for(Repair r:lst){
    		switch(r.getGrade3()){
    		case 1:
    			groupslst.get(0).setGrade1(Integer.valueOf(r.getSumGrade3()));
    			break;
    		case 2:
    			groupslst.get(0).setGrade2(Integer.valueOf(r.getSumGrade3()));
    			break;
    		case 3:
    			groupslst.get(0).setGrade4(Integer.valueOf(r.getSumGrade3()));
    			break;
    		case 4:
    			groupslst.get(0).setGrade4(Integer.valueOf(r.getSumGrade3()));
    			break;
    		case 5:
    			groupslst.get(0).setGrade5(Integer.valueOf(r.getSumGrade3()));
    			break;
    		}
    	}
    	Pageable<Repair> pager=new Pagination<Repair>(groupslst);
    	pager.setPageNo(1);
    	pager.setPageSize(Integer.MAX_VALUE);
    	pager.setTotal(groupslst.size());
    	return pager;
    }
    @ResponseBody
    @RequestMapping("gradeByMonth")
    public Pageable<Repair> gradeByMonth(@ModelAttribute Repair repair) {
    	repair.setRepairMonth(sf.format(new Date()));
    	List<Repair> lst = repairService.queryByGrade(repair);
    	Pageable<Repair> pager=new Pagination<Repair>(lst);
    	pager.setPageNo(1);
    	pager.setPageSize(Integer.MAX_VALUE);
    	pager.setTotal(lst.size());
    	return pager;
    }
    @ResponseBody
    @RequestMapping("repairTrend")
    public Pageable<Repair> repairTrend(@ModelAttribute Repair repair) {
    	List<Repair> lst = repairService.findByRepairTrend(repair);
    	Pageable<Repair> pager=new Pagination<Repair>(lst);
    	pager.setPageNo(1);
    	pager.setPageSize(Integer.MAX_VALUE);
    	pager.setTotal(lst.size());
    	return pager;
    }
    @ResponseBody
    @RequestMapping("repairArea")
    public Pageable<Repair> repairArea(@ModelAttribute Repair repair) {
    	List<Repair> lst = repairService.findByRepairArea(repair);
    	Pageable<Repair> pager=new Pagination<Repair>(lst);
    	pager.setPageNo(1);
    	pager.setPageSize(Integer.MAX_VALUE);
    	pager.setTotal(lst.size());
    	return pager;
    }
    @ResponseBody
    @RequestMapping("repairCostSave")
    public Pageable<Repair> repairCostSave(@ModelAttribute Repair repair) {
    	List<Repair> lst = repairService.findByCostSaving(repair);
    	for(Repair r:lst){
    		BigDecimal pecent=new BigDecimal("0");
    		if(!r.getLastCostSaving().equals("0") || r.getLastCostSaving()!="0"){
    			pecent=((new BigDecimal(r.getCostSaving()).subtract(new BigDecimal(r.getLastCostSaving())))
    					.divide(new BigDecimal(r.getLastCostSaving()), 4)).multiply(new BigDecimal(100));
    		}else{
    			pecent=new BigDecimal("100");
    		}
    		r.setPecent(pecent);
    	}
    	Pageable<Repair> pager=new Pagination<Repair>(lst);
    	pager.setPageNo(1);
    	pager.setPageSize(Integer.MAX_VALUE);
    	pager.setTotal(lst.size());
    	return pager;
    }
    @ResponseBody
    @RequestMapping("delay")
    public Pageable<Repair> delay(@ModelAttribute Repair repair) {
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy");
    	if(repair.getStartTime()==null){
    		repair.setStartTime(sf.format(new Date()));
    	}
    	repair.setRepairMode(0);
    	List<Repair> lst = repairService.findByDelay(repair);
    	Pageable<Repair> pager=new Pagination<Repair>(lst);
    	pager.setPageNo(1);
    	pager.setPageSize(Integer.MAX_VALUE);
    	pager.setTotal(lst.size());
    	return pager;
    }
    @ResponseBody
    @RequestMapping("delayByself")
    public Pageable<Repair> delayByself(@ModelAttribute Repair repair) {
    	if(repair.getStartTime()==null){
    		repair.setStartTime(sf.format(new Date()));
    	}
//    	repair.setRepairMode(1);
    	List<Repair> lst = repairService.findByDelay(repair);
    	Pageable<Repair> pager=new Pagination<Repair>(lst);
    	pager.setPageNo(1);
    	pager.setPageSize(Integer.MAX_VALUE);
    	pager.setTotal(lst.size());
    	return pager;
    }
//
//    /*
//    save  repairEvtCategory
//    repairEvtCategory object instance 
//    */
//	@ResponseBody
//	@RequestMapping(value ="save", method = RequestMethod.POST)    
//	public ScoMessage save(RepairEvtCategory repairEvtCategory) {
//
//		if(repairEvtCategory.getParentId()==null){
//			 repairEvtCategory.setParentId(0L);
//		 }
//         if(repairEvtCategory.getId()==null){
//            copyCreateInfo(repairEvtCategory);
//            repairEvtCategoryService.insert(repairEvtCategory);	
//        }
//        else{
//            copyUpdateInfo(repairEvtCategory);    
//            repairEvtCategoryService.update(repairEvtCategory);	
//        }
//		return ScoMessage.success("repair/repairEvtCategory/list",ScoMessage.SAVE_SUCCESS_MSG);
//	}
//
//    /*delete   
//    */
//	@ResponseBody
//	@RequestMapping(value = "delete", method = RequestMethod.POST)     
//	public ScoMessage delete( @RequestParam("ids") Long[] ids) {		
//		repairEvtCategoryService.deleteByIds(ids);	
//		return ScoMessage.success("repair/repairEvtCategory/list",ScoMessage.DELETE_SUCCESS_MSG);
//	}   
//    
//    /**
//	 * Edit RepairEvtCategory
//	 * @param id  ID 
//	 * @return
//	 */
//    @RequestMapping(value = "edit", method = RequestMethod.GET)
//	public String edit(@RequestParam(required = false) Long id,  ModelMap model,String showFlag,String viewOnly) {
//    	
//    	List<RepairEvtCategory> lstProperty = new ArrayList<RepairEvtCategory>();
//    	//List<RepairEvtCategory> lstPropertyAll = repairEvtCategoryService.findList("findAll",null);
//		if (null != id) {
//			Map<String, Long> slqMap = new HashMap<String, Long>();
//	    	slqMap.put("id",id);
//	    	//lstProperty = repairEvtCategoryService.findList("findByCategoryId",slqMap);
//	    	RepairEvtCategory repairEvtCategory = repairEvtCategoryService.getById(id);
//			if (null == repairEvtCategory) {
//				throw new IllegalArgumentException("Object does not exist");
//			}
//			if(UtilTool.isEmpty(repairEvtCategory.getParentName())){
//				repairEvtCategory.setParentName("维修类别簇");
//			}
//			model.put("repairEvtCategory", repairEvtCategory);
//		}	
//		model.put("lstProperty", lstProperty);
//    	model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.EMERGENCY));
//		//model.put("lstPropertyAll", lstPropertyAll);
//		model.put("showFlag", showFlag);
//		model.put("viewOnly", viewOnly);
//		return "repair/appeal/edit";
//	}
//    
//    
//	@RequestMapping(value = "repair", method = RequestMethod.GET)
//	public String repairevt(ModelMap model, String txtId, String txtName) {
//    	List<RepairEvtCategory> treeList = repairEvtCategoryService.findAll();
//		model.addAttribute("treeList", treeList);
//		model.put("txtId",txtId);
//		model.put("txtName","categoryName");
//		return "repair/appeal/choose_repair";
//	}

	
}
