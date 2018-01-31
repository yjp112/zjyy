package com.supconit.dev.gis.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hc.base.domains.Pagination;
import jodd.datetime.JDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.supconit.common.utils.StringUtil;
import com.supconit.hikvision.services.AcsHisEventService;
import com.supconit.hl.base.entities.Device;
import com.supconit.hl.base.services.DeviceService;
import com.supconit.hl.gis.entities.MReadBlock;
import com.supconit.hl.gis.services.IBlockService;
import com.supconit.hl.montrol.entity.DeviceTag;
import com.supconit.hl.montrol.service.DeviceTagService;
import com.supconit.nhgl.query.complex.entities.Complex;
import com.supconit.nhgl.query.complex.service.ComplexService;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.FastJsonUtils;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.employee.timeCard.entities.TimeCard;
import com.supconit.employee.timeCard.services.TimeCardService;

@Controller
@RequestMapping("extendGis")
public class ExtendGisController extends BaseControllerSupport {

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private com.supconit.base.services.DeviceService deviceService1;
	@Autowired
	private ComplexService complexService;
	@Autowired
	private IBlockService blockService;
	@Autowired
    private DeviceTagService deviceTagService;
	@Autowired
	private TimeCardService timeCardService;
	
	@Value("${blockServer}")
	private String blockServer;

	@RequestMapping(value = "toFlashDTXT", method = RequestMethod.GET)
	public String toFlashDTXT(String hpid, String flag,
			@RequestParam("flashName") String flashFileName,
			@RequestParam("flashH") Long flashHeight,
			@RequestParam("flashW") Long flashWidth, ModelMap model,
			HttpServletRequest request) {
		Device device = deviceService.getByHpid(hpid);
		model.put("blockServer", blockServer);
		model.put("device", device);
		model.put("serverName", request.getServerName());
		model.put("serverPort", Integer.valueOf(request.getServerPort()));
		String result = "ywgl/gis/flash/flashDianTi";

		model.put("flashFileName", flashFileName);
		model.put("flashHeight", flashHeight);
		model.put("flashWidth", flashWidth);
		return result;
	}
	
	@RequestMapping(value = "toYCCB", method = RequestMethod.GET)
	public String toYCCB(String hpid, ModelMap model,
			HttpServletRequest request) {
//		Device device = deviceService.getByHpid(hpid);
		Complex condition = new Complex();
     	condition.setLocationId(0L); 
		condition.setEnergyCode(hpid);
//		condition.setSubCode(null);
		Pagination<Complex> pager = new Pagination<Complex>();
//		pager.setPageNo(1);
//		pager.setPageSize(1);
		int a = hpid.indexOf("AM");//电表
		int b = hpid.indexOf("WM");//水表
		if(a>0){
			complexService.findByConditon(pager, condition);
			model.put("deviceType", "电");
		}
		if(b>0){
			complexService.findByWaterConditon(pager, condition);
			model.put("deviceType", "水");
		}
		for (Complex complex : pager) {
			  model.put("device", complex);
			  break;
		}
		return "ywgl/gis/gis_yccb";
	}
	
	@RequestMapping(value = "toVRV", method = RequestMethod.GET)
	public String toVRV(String hpid, ModelMap model,
			HttpServletRequest request) {
		com.supconit.base.entities.Device device = deviceService1.getByHpid(hpid);
		model.put("id", device.getId());
		String amhpid= device.getDiscipinesCode();
		if(amhpid!=null && !"".equals(amhpid)){
			Device am = deviceService.getByHpid(amhpid);
			model.put("deviceId", am.getId());
		}
		JDateTime now = new JDateTime();
		JDateTime firstDate =new JDateTime(DateUtils.getMonthBegain());
		String FirstMonth = firstDate.toString("YYYY-MM-DD");
        String LastMonth = now.toString("YYYY-MM-DD");
		model.put("startTime", FirstMonth);
		model.put("endTime", LastMonth);
		model.put("deviceName", device.getDeviceName());
		return "ywgl/gis/gis_vrv";
	}
	
	@RequestMapping(value = "toPTD", method = RequestMethod.GET)
	public String toPTD(String hpid, ModelMap model,
			HttpServletRequest request) {
		model.put("hpid", hpid);
		Device device=deviceService.getByHpid(hpid);
		model.put("device", device);
		Map<String,String> tagDescMap = new HashMap<String,String>();
		Map<String,String> tagUnitMap = new HashMap<String,String>();
		Map<String,String> textRulesMap = new HashMap<String,String>();
		List<String> blocks = new ArrayList<String>();
		DeviceTag deviceTag = new DeviceTag();
		deviceTag.setMonitorID(hpid);
		Pagination<DeviceTag> pager = new Pagination<DeviceTag>();
		pager.setPageSize(100);
		deviceTagService.find(pager, deviceTag);
		for (DeviceTag tag : pager) {
			blocks.add(tag.getTagName());
			tagDescMap.put(tag.getTagName(), tag.getTagDesc());
			tagUnitMap.put(tag.getTagName(), tag.getTagUnit());
			textRulesMap.put(tag.getTagName(), tag.getTextRules());
		}
		List<MReadBlock> mReadBlocks = blockService.readBlocks(blocks);
		List<DeviceTag> deviceBlocks = new ArrayList<DeviceTag>();
		for (MReadBlock mReadBlock : mReadBlocks) {
			DeviceTag ex = new DeviceTag();
			ex.setTagName(mReadBlock.getBlock());
			ex.setTagDesc(tagDescMap.get(mReadBlock.getBlock()));
			ex.setTagUnit(tagUnitMap.get(mReadBlock.getBlock()));
			String tagUnit = tagUnitMap.get(mReadBlock.getBlock());
			String textRules = textRulesMap.get(mReadBlock.getBlock());
			ex.setExtension1(mReadBlock.getValue());
			if(!StringUtil.isNullOrEmpty(tagUnit)){
				ex.setExtension1(mReadBlock.getValue()+" "+tagUnit);
			}
			if(!StringUtil.isNullOrEmpty(textRules)){
				textRules=textRules.replaceAll(":", "\":\"");
				textRules=textRules.replaceAll(",", "\",\"");
				textRules=textRules.replaceAll("^", "{\"");
				textRules=textRules.replaceAll("$", "\"}");
				try{
					Map<String,String> m=FastJsonUtils.json2map(textRules);
					ex.setExtension1(m.get(mReadBlock.getValue()));
				}catch(Exception e){
					
				}
			}
			deviceBlocks.add(ex);
		}
		model.put("deviceBlocks", deviceBlocks);
		return "ywgl/gis/gis_ptd";
	}
	
	@RequestMapping(value = "toSW", method = RequestMethod.GET)
	public String toSW(String hpid, ModelMap model,HttpServletRequest request) {

		return "ywgl/gis/gis_sw";
	}
	
	@RequestMapping(value = "toMJ", method = RequestMethod.GET)
	public String toMJ(String hpid, ModelMap model,HttpServletRequest request) {
		Device device= deviceService.getByHpid(hpid);
		boolean b = hasAdminRole();
		//--------------海康查询start-------------------
//		AcsHisEvent event = new AcsHisEvent();
//		if (!b) {
//			event.setCardNums(getCurrentPerson().getDescription());
//		}
//		Pagination<AcsHisEvent> pager = new Pagination<AcsHisEvent>();
//		pager.setPageSize(10);
//		acsHisEventService.findByPage(pager, event);
		//--------------海康查询end-------------------
		
		//--------------本地查询start-------------------
		TimeCard condition = new TimeCard();
		if(!b){
			condition.setPersonId(getCurrentPersonId());
		}
		Pagination<TimeCard> pager = new Pagination<TimeCard>();
		pager.setPageSize(10);
		timeCardService.findByPage(pager, condition);
		//--------------本地查询end-------------------
		
		model.put("device", device);
		model.put("pager", pager);
//		model.put("alarmState1",1);
//      model.put("alarmState4",4);
		return "ywgl/gis/gis_mj";
	}
	
}
