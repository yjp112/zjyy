package com.supconit.nhgl.basic.deviceMeter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.Device;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.basic.deviceMeter.entities.DeviceMeter;
import com.supconit.nhgl.basic.deviceMeter.service.DeviceMeterService;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.discipine.discipine.service.NhItemService;
import com.supconit.nhgl.basic.sysConfig.service.SysConfigService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import jodd.datetime.JDateTime;
/**
 * 医疗数据录入
 * @author WangHaiBO
 *
 */
@Controller
@RequestMapping("nhgl/basic/deviceMeter")
public class DeviceMeterController extends BaseControllerSupport {
	
	@Autowired
	private DeviceMeterService miService;
	
	@Autowired
	private NhItemService subService;
	
	@Autowired
	private GeoAreaService areaService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Value("${electric_category}")
	private String electricCategoryCode;
	
	@Value("${water_category}")
	private String waterCategoryCode;
	
	@Value("${energy_category}")
	private String energyCategoryCode;
	
	@RequestMapping("list")
	public String list(ModelMap model){
		List<GeoArea> areaList = areaService.findAll();
		model.put("areaList", areaList);
		return "nhgl/basic/deviceMeter/deviceMeter_list";
	}
	
	@ResponseBody
	@RequestMapping("pager")
	public Pageable<DeviceMeter> pager(Pagination<DeviceMeter> pager, DeviceMeter condition){
		miService.findByCondition(pager, condition);
		return pager;
	}
	
	@ResponseBody
	@RequestMapping("findDmByCondition")
	public List<DeviceMeter> findDmByCondition(Long deviceMeterId){
		DeviceMeter dm = new DeviceMeter();
		dm.setDeviceMeterId(deviceMeterId);
		return miService.findDmByCondition(dm);
	}
	@RequestMapping("getSubCodeTree")
	public String getSubCodeTree(ModelMap model){
		List<NhItem> subList = subService.findByCon(null);
		model.put("subList", subList);
		return "nhgl/basic/deviceMeter/getSubCodeTree";
	}
	
	@RequestMapping("add")
	public String add(ModelMap model){
		//flag标识是否是新增页面，0新增，1修改
		model.put("flag", 0);
		return "nhgl/basic/deviceMeter/add";
	}
	
	@RequestMapping("edit")
	public String add(ModelMap model, Long id){
		DeviceMeter condition = miService.getById(id);
		model.put("condition", condition);
		//flag标识是否是新增页面，0新增，1修改
		model.put("flag", 1);
		return "nhgl/basic/deviceMeter/add";
	}
	
	@ResponseBody
	@RequestMapping("del")
	public ScoMessage del(ModelMap model, Long id){
		try{
			DeviceMeter mi = miService.getById(id);
			if(mi.getIsValid() == 0){//该数据已经失效
				miService.delete(mi);
				return ScoMessage.success("删除成功！");
			}else{
				return ScoMessage.error("有效数据不能删除！");
			}
				
		}catch(Exception e){
			e.printStackTrace();
			return ScoMessage.error("服务器发生异常！");
		}
		
	}
	@ResponseBody
	@RequestMapping("isValidEdit")
	public ScoMessage isValidEdit(Long id){
		DeviceMeter mi = miService.getById(id);
		if(mi.getIsValid() == 1)
			mi.setIsValid(0l);
		else
			mi.setIsValid(1l);
		try{
			miService.update(mi);
			return ScoMessage.success("操作成功！");
		}catch(Exception e){
			return ScoMessage.error("操作失败！");
		}
	}
	
	@ResponseBody
	@RequestMapping("save")
	public ScoMessage save(DeviceMeter md,String deviceIds){
		if(!("").equals(deviceIds) && deviceIds != null){
			md.setIsValid(1l);
			miService.deleteByDeviceId(md);
			String [] deviceIdStr = deviceIds.split(";");
			String dateStr = new JDateTime(new Date()).toString("YYYY-MM-DD hh:mm:ss");
			List<DeviceMeter> dmList = new ArrayList<DeviceMeter>(); 
			for(int i = 0; i< deviceIdStr.length; i++){
				DeviceMeter deviceMeter = new DeviceMeter();
				//deviceIds传过来的格式"设备编码_是否有效"
				if(("1").equals(deviceIdStr[i].split("_")[1]))
					deviceMeter.setDeviceId(Long.valueOf(deviceIdStr[i].split("_")[0]));
				else
					continue;
				deviceMeter.setDeviceMeterId(md.getDeviceMeterId());
				deviceMeter.setIsValid(1l);
				deviceMeter.setShareProportion("1");
				deviceMeter.setLinkTime(dateStr);
				dmList.add(deviceMeter);
			}
			try{
				miService.insertList(dmList);
				return ScoMessage.success("新增成功！");
			}catch(Exception e){
				e.printStackTrace();
				return ScoMessage.error("服务器发生异常");
			}
		}else{
			return ScoMessage.error("未选择电表，请添加测量设备！");
		}
	}
	
	@ResponseBody
	@RequestMapping("findListByCondition")
	public List<Device> findListByCondition(Device condition, String deviceIds){
		if(!("").equals(deviceIds) && deviceIds != null){
			String[] dvsIds = deviceIds.split(";");
			List<Long> ids = new ArrayList<Long>();
			for(int i = 0; i< dvsIds.length; i++){
				ids.add(Long.valueOf(dvsIds[i]));
			}
			condition.setDeviceIds(ids);
		}
		condition.setElectricCategoryCode(electricCategoryCode);
		condition.setWaterCategoryCode(waterCategoryCode);
		condition.setGasCategoryCode(energyCategoryCode);
		return sysConfigService.findListByCondition(condition);
	}
}
