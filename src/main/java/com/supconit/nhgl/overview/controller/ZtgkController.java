package com.supconit.nhgl.overview.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jodd.datetime.JDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supconit.common.utils.BigDecimalUtil;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DateUtils;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaHour;
import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaMonth;
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;
import com.supconit.nhgl.analyse.electric.area.service.NhDAreaHourService;
import com.supconit.nhgl.analyse.electric.area.service.NhDAreaMonthService;
import com.supconit.nhgl.analyse.electric.dept.entities.NhDDeptHour;
import com.supconit.nhgl.evaluate.service.EvaluateInfoService;
import com.supconit.nhgl.utils.GraphUtils;

/**
 * 总体概况
 * @author WangHaiBO
 *
 */
@Controller
@RequestMapping("nhgl/overview")
public class ZtgkController {

	@Autowired
	private NhDAreaHourService hourEleService;
	@Autowired
	private NhAreaService nhAreaService;
	
	@Autowired
	private NhDAreaMonthService monthEleService;
	
	@Autowired
	private EvaluateInfoService evaluateInfoService;
	
	
	@RequestMapping("list")
	public String list(ModelMap model){
		
		Date date = new Date();
		JDateTime nowTime = new JDateTime(date);
		NhDAreaMonth etm = new NhDAreaMonth();
		etm.setMonthKey(nowTime.toString("YYYY-MM"));
		etm.setDeptPid(GraphUtils.TECHNIC_PID);
		//获取今年本月及去年总耗电量 --本月总能耗
		NhDAreaMonth etme = monthEleService.getMonthElectricityTotal(etm);
		BigDecimal total = new BigDecimal(0.00);//今天的耗电总量
		BigDecimal lasttotal = new BigDecimal(0.00);//昨天的耗电总量
		//获取当前时间区域耗电总量 
		NhDAreaHour elec = new NhDAreaHour();
		elec.setCollectDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		elec.setCollectTime(new Date());
		elec.setParentId(Integer.valueOf(nhAreaService.findRootId().toString()));
		//获取今天及昨天截至当前时间区域各分项的耗电量 --各分项能耗占比
		List<NhDAreaHour> elecList=hourEleService.getSubInfoDetailByArea(elec);
		BigDecimal tbzl=new BigDecimal(0.00);
		BigDecimal percent = new BigDecimal(0.00);
		List<String> subName=new ArrayList<String>();
		for(NhDAreaHour n :elecList){
			tbzl=n.getTotal().subtract(n.getLastTotal());
			if(n.getLastTotal().compareTo(new BigDecimal(0))!=0){
				percent=BigDecimalUtil.divide4(tbzl,n.getLastTotal()).multiply(new BigDecimal(100));
			}
			n.setPercent(percent.floatValue());
			subName.add("'"+n.getName()+"'");
		}
		
		//获取今天及昨天截至当前时间各区域的耗电量 --各区域能耗占比
		List<NhDAreaHour> eleAreaList=hourEleService.getAreaDetail(elec);
		List<String> areaName=new ArrayList<String>();
			
		NhDAreaHour ectm = new NhDAreaHour();
		ectm.setCollectDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
		ectm.setCollectTime(new Date());
		ectm.setNhType(Constant.NH_TYPE_D);
		//获取当前各小时耗电总量 --能耗数据分析
		List<NhDAreaHour> eclist = hourEleService.getDayofAreaElectricty(ectm);
		//各区域能耗占比区域名称
		for(NhDAreaHour e:eleAreaList){
			areaName.add("'"+e.getName()+"'");
		}
		ectm.setNow(date);
		String ecDay = GraphUtils.getNowDayEle(eclist,nowTime.getHour());
		
		//能耗健康指标
		Map evalMap=evaluateInfoService.showEvaluateResult();
		model.put("map", evalMap);
		//截止当前时间各分项耗电总量饼图数据
		String electric = getPieString(elecList);
		String elecArea=getPieString(eleAreaList);
		model.addAttribute("electric", electric);
		model.put("elecArea", elecArea);
		model.put("subName", subName);
		model.put("areaName", areaName);
		model.addAttribute("ecDay", ecDay);
		model.addAttribute("eclist", eclist);
		model.addAttribute("total", total);
		model.addAttribute("lasttotal", lasttotal);
		model.addAttribute("totalNumber", new DecimalFormat("0.00").format(total));
		model.addAttribute("nowDate", nowTime.toString("MM月DD日")) ;
		model.put("subTree", elecList);
		BigDecimal thisMonthP = new BigDecimal(0.00);
		BigDecimal lastMonthP = new BigDecimal(0.00);
		if(etme != null){
			if(etme.getTotalYoy().compareTo(new BigDecimal(0.00)) != 0)
				thisMonthP = BigDecimalUtil.divide4(etme.getTotalMonthValue(),
						etme.getTotalYoy().add(etme.getTotalMonthValue())).multiply(new BigDecimal(100));
			else
				thisMonthP = new BigDecimal(100.00);
			lastMonthP = new BigDecimal(100).subtract(thisMonthP);
			model.put("thisMonth", etme.getTotalMonthValue());
			model.put("lastMonth", etme.getTotalYoy());
		}else{
			model.put("thisMonth", 0.00);
			model.put("lastMonth", 0.00);
		}
		model.put("thisMonthP", thisMonthP);
		model.put("lastMonthP", lastMonthP);
		return "nhgl/overview/ztgk";
	}
	
	/**
	 * 封装饼状图所需字符串
	 * @return 
	 * @方法名: getPieString
	 * @创建日期: 2014-5-22
	 * @开发人员:王海波
	 * @描述:
	 */
	private String getPieString(List<NhDAreaHour> emlist)
	{
		StringBuffer sb = new StringBuffer("[");
		int i=0;
		for(NhDAreaHour em : emlist)
		{
			if(em.getTotal().compareTo(new BigDecimal(0))==0){
				i++;
			}
			if(em != null){
				sb.append("{").append("value:")
				.append(em.getTotal())
				.append(",name:");
				sb.append("'"+em.getName() ).append("'},");
			}
			if(i==emlist.size()){
				sb=new StringBuffer();
			}
		}
			
		String pieString  = sb.length()>1?sb.toString().substring(0, sb.toString().length()-1):"[";
		return pieString + "]";
	}
}
