package com.supconit.mobile.search.controllers;

import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.DeviceService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.mobile.search.entities.MobileSearch;
import com.supconit.spare.entities.Spare;
import com.supconit.spare.entities.SpareCategory;
import com.supconit.spare.services.SpareCategoryService;
import com.supconit.spare.services.SpareService;
import hc.mvc.annotations.FormBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwei on 16/4/22.
 */
@Controller
@RequestMapping("mobile/search")
public class MobileSearchController {

	private static final transient Logger logger = LoggerFactory.getLogger(MobileSearchController.class);
	
	@Autowired
	private DeviceService deviceService;

	@Autowired
	private GeoAreaService geoAreaService;

	@Autowired
	private DeviceCategoryService deviceCategoryService;

	@Autowired
	private SpareCategoryService spareCategoryService;

	@Autowired
	private SpareService spareService;

	@ModelAttribute("prepareMobileSearch")
	public MobileSearch prepareMobileSearch(){return new MobileSearch();}

	/**
	 * 设备搜索
	 */
	@RequestMapping(value = "searchDevice", method = RequestMethod.GET)
	public String searchDevice(ModelMap map) {
		return "mobile/repair/device";
	}


	/**
	 * AJAX获取列表数据。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "searchDeviceTree")
	public List<MobileSearch> searchDeviceTree(@FormBean(value = "condition",modelCode = "prepareMobileSearch")MobileSearch mobileSearch)
	{
		List<MobileSearch> deviceSearchTrees=new ArrayList<MobileSearch>();
		String deviceSearchType=mobileSearch.getDeviceSearchType();
		long parentId=mobileSearch.getParentId();
		if(deviceSearchType!=null&&!"".equals(deviceSearchType))
		{
			if("0".equals(deviceSearchType)){
				List<DeviceCategory> deviceCategories=this.deviceCategoryService.findByParentId(parentId);
				if(deviceCategories!=null&&deviceCategories.size()>0)
				{
					for(DeviceCategory c:deviceCategories)
					{
						MobileSearch d=new MobileSearch();
						d.setSearchId(c.getId());
						String categoryName=c.getCategoryName();
						if(categoryName!=null&&categoryName.length()>4)
						{
							categoryName=categoryName.substring(0,4)+"...";
						}
						d.setSearchName(categoryName);
						deviceSearchTrees.add(d);
					}
				}
			}
			else
			{
				List<GeoArea> geoAreas=this.geoAreaService.findByParentId(parentId);
				if(geoAreas!=null&&geoAreas.size()>0)
				{
					for(GeoArea g:geoAreas)
					{
						MobileSearch d=new MobileSearch();
						d.setSearchId(g.getId());
						String areaName=g.getAreaName();
						if(areaName!=null&&areaName.length()>4)
						{
							areaName=areaName.substring(0,4)+"...";
						}
						d.setSearchName(areaName);
						deviceSearchTrees.add(d);
					}
				}
			}
		}
		return deviceSearchTrees;
	}


	/**
	 * AJAX获取列表数据。
	 * @param mobileSearch   查询条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list_device")
	public List<Device> list_device(@FormBean(value = "condition",modelCode = "prepareMobileSearch")MobileSearch mobileSearch)
	{
		List<Device> devices=null;
		String deviceSearchType=mobileSearch.getDeviceSearchType();
		long searchId=mobileSearch.getSearchId();
		long deviceId=mobileSearch.getDeviceCodeId();
		long parentId=mobileSearch.getParentId();
		String deviceSearch=mobileSearch.getDeviceSearch();
		Device device=new Device();
		if(searchId!=0)
		{
			if(deviceSearchType!=null&&!"".equals(deviceSearchType))
			{
				if("0".equals(deviceSearchType)){
					device.setCategoryId(searchId);
				}
				else
				{
					device.setLocationId(searchId);
				}
			}
		}
		if(parentId!=0)
		{
			if(deviceSearchType!=null&&!"".equals(deviceSearchType))
			{
				if("0".equals(deviceSearchType)){
					device.setCategoryParentId(parentId);
				}
				else
				{
					device.setLocationParentId(parentId);
				}
			}
		}
		if(deviceId!=0)
		{
			device.setDeviceId(deviceId);
		}
		if(deviceSearch!=null)
		{
			device.setDeviceSearch(deviceSearch);
		}
		devices=this.deviceService.selectDevices(device);
		long total=this.deviceService.countDevices(device);
		for (Device d:devices)
		{
			d.setDeviceTotal(total);
		}
		return devices;
	}

	/**
	 * AJAX获取列表数据。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "searchAllDevice")
	public List<Device> searchAllDevice()
	{
		List<Device> devices=this.deviceService.searchAllDevice();
		return devices;
	}

	/**
	 * 配件搜索
	 */
	@RequestMapping(value = "searchSpare", method = RequestMethod.GET)
	public String searchSpare(ModelMap map,String spareIndex) {
		map.put("spareIndex",spareIndex);
		List<SpareCategory> spareCategories=this.spareCategoryService.findByParentId(0);
		map.put("spareCategories",spareCategories);
		return "mobile/repair/spare";
	}

	/**
	 * AJAX获取列表数据。
	 * @param mobileSearch   查询条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list_spare")
	public List<Spare> list_spare(@FormBean(value = "condition",modelCode = "prepareMobileSearch")MobileSearch mobileSearch)
	{
		List<Spare> spares=null;
		long searchId=mobileSearch.getSearchId();
		long spareCodeId=mobileSearch.getSpareCodeId();
		String spareSearch=mobileSearch.getSpareSearch();
		Spare spare=new Spare();
		if(searchId!=0)
		{
			spare.setSpareCategoryId(searchId);
		}
		if(spareCodeId!=0)
		{
			spare.setSpareCodeId(spareCodeId);
		}
		if(spareSearch!=null)
		{
			spare.setSpareSearch(spareSearch);
		}
		spares=this.spareService.selectSpares(spare);
		long total=this.spareService.countSpares(spare);
		for (Spare s:spares)
		{
			s.setSpareTotal(total);
		}
		return spares;
	}
}
