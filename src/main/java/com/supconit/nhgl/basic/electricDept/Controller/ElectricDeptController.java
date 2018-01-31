package com.supconit.nhgl.basic.electricDept.Controller;

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
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.basic.electricDept.entities.ElectricDept;
import com.supconit.nhgl.basic.electricDept.service.ElectricDeptService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
/**
 * 医疗数据录入
 * @author WangHaiBO
 *
 */
@Controller
@RequestMapping("nhgl/basic/electricDept")
public class ElectricDeptController extends BaseControllerSupport {
	
	@Autowired
	private ElectricDeptService miService;
	
	@Autowired
	private NhItemService subService;
	
	@Autowired
	private GeoAreaService areaService;
	
	
	@RequestMapping("list")
	public String list(ModelMap model){
		return "nhgl/basic/electricDept/electricDept_list";
	}
	
	@ResponseBody
	@RequestMapping("pager")
	public Pageable<ElectricDept> pager(Pagination<ElectricDept> pager, ElectricDept condition){
		miService.findByCondition(pager, condition);
		return pager;
	}
	
	@ResponseBody
	@RequestMapping("findDmByCondition")
	public List<ElectricDept> findDmByCondition(String deviceName, String deptName){
		ElectricDept dm = new ElectricDept();
		dm.setDeviceName(deviceName);
		dm.setDeptName(deptName);
		return miService.findDmByCondition(dm);
	}
	
	@RequestMapping("add")
	public String add(ModelMap model){
		
		return "nhgl/basic/electricDept/add";
	}
	
	@RequestMapping("edit")
	public String edit(ModelMap model, Long id){
		
		ElectricDept ed = new ElectricDept();
		ed = miService.getById(id);
		model.put("ed", ed);
		return "nhgl/basic/electricDept/add";
	}
	
	@ResponseBody
	@RequestMapping("del")
	public ScoMessage del(ModelMap model, Long id){
		try{
			ElectricDept mi = miService.getById(id);
			miService.delete(mi);
			return ScoMessage.success("删除成功！");
				
		}catch(Exception e){
			e.printStackTrace();
			return ScoMessage.error("服务器发生异常！");
		}
		
	}
	
	@ResponseBody
	@RequestMapping("save")
	public ScoMessage save(ElectricDept ed){
		if(ed.getDeviceId() == null)
			return ScoMessage.error("未选择电表，请选择后保存！");
		if(ed.getDeptId() == null)
			return ScoMessage.error("未选择部门，请选择后保存！");
		try{
			if(ed.getId() != null){
				miService.update(ed);
				return ScoMessage.success("操作成功！");
			}else{
				miService.insert(ed);
				return ScoMessage.success("操作成功！");
			}
		}catch(Exception e){
			e.printStackTrace();
			return ScoMessage.error("服务器发生异常！");
		}
	}
	
}
