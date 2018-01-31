package com.supconit.nhgl.basic.waterDept.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.services.GeoAreaService;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.basic.discipine.water.service.WaterSubSystemInfoService;
import com.supconit.nhgl.basic.sysConfig.service.SysConfigService;
import com.supconit.nhgl.basic.waterDept.entities.WaterDept;
import com.supconit.nhgl.basic.waterDept.service.WaterDeptService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("nhgl/basic/waterDept")
public class WaterDeptController extends BaseControllerSupport{

	@Autowired
	private WaterDeptService waterDeptService;
	@Autowired
	private WaterSubSystemInfoService waterSubSystemInfoService;
	@Autowired
	private GeoAreaService areaService;
	
	@Autowired
	private SysConfigService deviceService;
	
	
	@RequestMapping("list")
	public String list(ModelMap model){
		return "nhgl/basic/waterDept/waterDept_list";
	}
	@ResponseBody
	@RequestMapping("pager")
	public Pageable<WaterDept> pager(Pagination<WaterDept> pager, WaterDept condition){
		waterDeptService.findByCondition(pager, condition);
		return pager;
	}
	@ResponseBody
	@RequestMapping("findDmByCondition")
	public List<WaterDept> findDmByCondition(String deviceName, String deptName){
		WaterDept dm = new WaterDept();
		dm.setDeviceName(deviceName);
		dm.setDeptName(deptName);
		return waterDeptService.findDmByCondition(dm);
	}
	
	@RequestMapping("add")
	public String add(ModelMap model){
		
		return "nhgl/basic/waterDept/add";
	}
	
	@RequestMapping("edit")
	public String edit(ModelMap model, Long id){
		
		WaterDept wd = new WaterDept();
		wd = waterDeptService.getById(id);
		model.put("wd", wd);
		return "nhgl/basic/waterDept/add";
	}
	
	@ResponseBody
	@RequestMapping("del")
	public ScoMessage del(ModelMap model, Long id){
		try{
			WaterDept mi = waterDeptService.getById(id);
			waterDeptService.delete(mi);
			return ScoMessage.success("删除成功！");
				
		}catch(Exception e){
			e.printStackTrace();
			return ScoMessage.error("服务器发生异常！");
		}
		
	}
	
	@ResponseBody
	@RequestMapping("save")
	public ScoMessage save(WaterDept ed){
		if(ed.getDeviceId() == null)
			return ScoMessage.error("未选择水表，请选择后保存！");
		if(ed.getDeptId() == null)
			return ScoMessage.error("未选择部门，请选择后保存！");
		try{
			if(ed.getId() != null){
				waterDeptService.update(ed);
				return ScoMessage.success("操作成功！");
			}else{
				waterDeptService.insert(ed);
				return ScoMessage.success("操作成功！");
			}
		}catch(Exception e){
			e.printStackTrace();
			return ScoMessage.error("服务器发生异常！");
		}
	}
}
