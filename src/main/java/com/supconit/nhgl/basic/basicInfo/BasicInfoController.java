package com.supconit.nhgl.basic.basicInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.basic.medical.entities.MedicalInfo;
import com.supconit.nhgl.basic.medical.service.MedicalInfoService;
import com.supconit.nhgl.query.collect.entities.ElectricMeterMonth;
import com.supconit.nhgl.query.collect.service.ElectricMeterMonthService;
import com.supconit.nhgl.query.collect.water.entities.WaterMeterMonth;
import com.supconit.nhgl.query.collect.water.service.WaterMeterMonthService;
import com.supconit.nhgl.utils.GraphUtils;

import jodd.datetime.JDateTime;
@Controller
@RequestMapping("nhgl/basic/basicInfo")
public class BasicInfoController extends BaseControllerSupport{

	@Autowired
	ElectricMeterMonthService emService;
	@Autowired
	MedicalInfoService miService;
	@Autowired
	WaterMeterMonthService wmService;
	
	@RequestMapping("go")
	public String go(){
		return "nhgl/basic/basicInfo/go";
	}
	
	@RequestMapping("view")
	public String view(ModelMap model, String start){
		JDateTime date = new JDateTime(new Date());
		List<ElectricMeterMonth> list = null;
		List<WaterMeterMonth> waterList = null;
		List<MedicalInfo> mlist = null;
		ElectricMeterMonth emm = new ElectricMeterMonth();
		WaterMeterMonth wmm = new WaterMeterMonth();
		MedicalInfo minfo = new MedicalInfo();
		if(start != null){
			emm.setStart(start + "-01");
			emm.setEnd(start + "-12");
			wmm.setStart(start + "-01");
			wmm.setEnd(start + "-12");
			minfo.setStart(start + "-01");
			minfo.setEnd(start + "-12");
		}else{
			emm.setStart(date.getYear() + "-01");
			emm.setEnd(date.getYear() + "-12");
			wmm.setStart(date.getYear() + "-01");
			wmm.setEnd(date.getYear() + "-12");
			minfo.setStart(date.getYear() + "-01");
			minfo.setEnd(date.getYear() + "-12");
		}
		list = emService.getMonthTotal(emm);
		waterList = wmService.getMonthTotal(wmm);
		mlist = miService.findByMonthKey(minfo);
		Map<String, Object> mapElectric = GraphUtils.getMonthInfoElectric(list);
		Map<String, Object> mapWater = GraphUtils.getMonthInfoWater(waterList);
		Map<String, Object> mapMedical = GraphUtils.getMedicalInfo(mlist);
		if(start != null)
			model.put("year", start);
		else
			model.put("year", date.getYear());
		model.put("mapElectric", mapElectric);
		model.put("mapWater", mapWater);
		model.put("mapMedical", mapMedical);
		return "nhgl/basic/basicInfo/view";
	}
}
