package com.supconit.nhgl.basic.EnergyDept.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.services.GeoAreaService;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.basic.EnergyDept.entities.EnergyDept;
import com.supconit.nhgl.basic.EnergyDept.service.EnergyDeptService;
import com.supconit.nhgl.basic.sysConfig.service.SysConfigService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("nhgl/basic/energyDept")
public class EnergyDeptController extends BaseControllerSupport{

	@Autowired
	private EnergyDeptService energyDeptService;
	@Autowired
	private GeoAreaService areaService;
	
	@Autowired
	private SysConfigService deviceService;
	
	@RequestMapping("list")
	public String list(ModelMap model){
		return "nhgl/basic/energyDept/energyDept_list";
	}
	@ResponseBody
	@RequestMapping("pager")
	public Pageable<EnergyDept> pager(Pagination<EnergyDept> pager, EnergyDept condition){
		energyDeptService.findByCondition(pager, condition);
		return pager;
	}
	@ResponseBody
	@RequestMapping("findDmByCondition")
	public List<EnergyDept> findDmByCondition(String deviceName, String deptName){
		EnergyDept dm = new EnergyDept();
		dm.setDeviceName(deviceName);
		dm.setDeptName(deptName);
		return energyDeptService.findDmByCondition(dm);
	}
	
	@RequestMapping("add")
	public String add(ModelMap model){
		
		return "nhgl/basic/energyDept/add";
	}
	
	@RequestMapping("edit")
	public String edit(ModelMap model, Long id){
		
		EnergyDept wd = new EnergyDept();
		wd = energyDeptService.getById(id);
		model.put("wd", wd);
		return "nhgl/basic/energyDept/add";
	}
	
	@ResponseBody
	@RequestMapping("del")
	public ScoMessage del(ModelMap model, Long id){
		try{
			EnergyDept mi = energyDeptService.getById(id);
			energyDeptService.delete(mi);
			return ScoMessage.success("删除成功！");
				
		}catch(Exception e){
			e.printStackTrace();
			return ScoMessage.error("服务器发生异常！");
		}
		
	}
	
	@ResponseBody
	@RequestMapping("save")
	public ScoMessage save(EnergyDept ed){
		if(ed.getDeviceId() == null)
			return ScoMessage.error("未选择蒸汽表，请选择后保存！");
		if(ed.getDeptId() == null)
			return ScoMessage.error("未选择部门，请选择后保存！");
		try{
			if(ed.getId() != null){
				energyDeptService.update(ed);
				return ScoMessage.success("操作成功！");
			}else{
				energyDeptService.insert(ed);
				return ScoMessage.success("操作成功！");
			}
		}catch(Exception e){
			e.printStackTrace();
			return ScoMessage.error("服务器发生异常！");
		}
	}
}
