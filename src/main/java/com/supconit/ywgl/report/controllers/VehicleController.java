package com.supconit.ywgl.report.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.ywgl.report.entities.Vehicle;
import com.supconit.ywgl.report.services.VehicleService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;


@Controller
@RequestMapping("ywgl/report/vehicle")
public class VehicleController extends BaseControllerSupport{
	@Autowired
	private VehicleService vehicleService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	
	
	@RequestMapping("go")
	public String go(ModelMap model) {
		model.put("listRoadGateName", DictUtils.getDictList(DictTypeEnum.ROAD_GATE_NAME));//获取闸口字典数据		
		return "ywgl2/report/vehicle/vehicle_list";
	}
	
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<Vehicle>  list(@ModelAttribute Vehicle vehicle,
			Pagination<Vehicle> page, ModelMap model) {
		//默认为当前年
		if(vehicle.getVehicleYear()==null){
			String year=sdf.format(new Date());
			vehicle.setVehicleYear(year);
			vehicle.setVehicleLastYear((Integer.parseInt(year)-1)+"");
		}else{
			vehicle.setVehicleLastYear((Integer.parseInt(vehicle.getVehicleYear())-1)+"");
		}
		Pageable<Vehicle> pager = vehicleService.findByCondition(page, vehicle);
		
		model.put("vehicle", vehicle);
		model.put("pager", pager);	
			
		return pager;
	}
}
