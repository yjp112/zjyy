package com.supconit.nhgl.basic.medical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.basic.medical.entities.MedicalInfo;
import com.supconit.nhgl.basic.medical.service.MedicalInfoService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
/**
 * 医疗数据录入
 * @author WangHaiBO
 *
 */
@Controller
@RequestMapping("nhgl/basic/medicalInfo")
public class MedicalInfoController extends BaseControllerSupport {
	
	@Autowired
	private MedicalInfoService miService;
	
	@RequestMapping("list")
	public String list(){
		return "nhgl/basic/medicalInfo/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "page",method = RequestMethod.POST)
	public Pageable<MedicalInfo> page(Pagination<MedicalInfo> pager,@ModelAttribute MedicalInfo condition){
		
		miService.findByCondition(pager, condition);
		return pager;
	}
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(){
		return "nhgl/basic/medicalInfo/edit";
	}
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  ModelMap model) {
		if (null != id) {
			MedicalInfo mi = miService.getById(id);
			model.put("medical", mi);		
		}		
		
		return "nhgl/basic/medicalInfo/edit";
	}
    
	@ResponseBody
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public ScoMessage del(@RequestParam("ids") Long[] ids){
			miService.deleteByIds(ids);
			return ScoMessage.success("basic/medicalInfo/list", "删除成功。");
	}
	
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(MedicalInfo md){
		try{
			Pagination<MedicalInfo> pager = new Pagination<MedicalInfo>();
			MedicalInfo condition = new MedicalInfo();
			condition.setMonthKey(md.getMonthKey());
			miService.findByCondition(pager, condition);
			if(md.getId() != null){
				if(pager.size() <= 1){
					miService.update(md);
					return ScoMessage.success("修改成功！");
				}else{
					return ScoMessage.error("该月份数据已存在！");
				}
				
			}else{
				if(pager.size() > 0){
					return ScoMessage.error("该月份数据已存在！");
				}else{
					md.setTypeId(0L);
					miService.insert(md);
					return ScoMessage.success("新增成功！");
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			return ScoMessage.error("服务器发生异常");
		}
	}
}
