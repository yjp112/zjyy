package com.supconit.nhgl.basic.GasDept.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.services.GeoAreaService;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.basic.GasDept.entities.GasDept;
import com.supconit.nhgl.basic.GasDept.service.GasDeptService;
import com.supconit.nhgl.basic.sysConfig.service.SysConfigService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("nhgl/basic/gasDept")
public class GasDeptController extends BaseControllerSupport{

	@Autowired
	private GasDeptService gasDeptService;
	@Autowired
	private GeoAreaService areaService;
	
	@Autowired
	private SysConfigService deviceService;
	
	@RequestMapping("list")
	public String list(ModelMap model){
		return "nhgl/basic/gasDept/gasDept_list";
	}
	@ResponseBody
	@RequestMapping("pager")
	public Pageable<GasDept> pager(Pagination<GasDept> pager, GasDept condition){
		gasDeptService.findByCondition(pager, condition);
		return pager;
	}
	@ResponseBody
	@RequestMapping("findDmByCondition")
	public List<GasDept> findDmByCondition(String deviceName, String deptName){
		GasDept dm = new GasDept();
		dm.setDeviceName(deviceName);
		dm.setDeptName(deptName);
		return gasDeptService.findDmByCondition(dm);
	}
	
	@RequestMapping("add")
	public String add(ModelMap model){
		
		return "nhgl/basic/gasDept/add";
	}
	
	@RequestMapping("edit")
	public String edit(ModelMap model, Long id){
		
		GasDept wd = new GasDept();
		wd = gasDeptService.getById(id);
		model.put("wd", wd);
		return "nhgl/basic/gasDept/add";
	}
	
	@ResponseBody
	@RequestMapping("del")
	public ScoMessage del(ModelMap model, Long id){
		try{
			GasDept mi = gasDeptService.getById(id);
			gasDeptService.delete(mi);
			return ScoMessage.success("删除成功！");
				
		}catch(Exception e){
			e.printStackTrace();
			return ScoMessage.error("服务器发生异常！");
		}
		
	}
	
	@ResponseBody
	@RequestMapping("save")
	public ScoMessage save(GasDept ed){
		if(ed.getDeviceId() == null)
			return ScoMessage.error("未选择蒸汽表，请选择后保存！");
		if(ed.getDeptId() == null)
			return ScoMessage.error("未选择部门，请选择后保存！");
		try{
			if(ed.getId() != null){
				gasDeptService.update(ed);
				return ScoMessage.success("操作成功！");
			}else{
				gasDeptService.insert(ed);
				return ScoMessage.success("操作成功！");
			}
		}catch(Exception e){
			e.printStackTrace();
			return ScoMessage.error("服务器发生异常！");
		}
	}
}
