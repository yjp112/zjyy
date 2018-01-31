package com.supconit.nhgl.basic.extractData.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.EnumDetail;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.LabelValueBean;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.hl.common.utils.DateUtils;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.jobschedule.entities.ScheduleJob;
import com.supconit.jobschedule.services.ScheduleJobService;
import com.supconit.jobschedule.services.SchedulerManagerService;
import com.supconit.nhgl.basic.extractData.entities.ExtractData;
import com.supconit.nhgl.basic.extractData.service.ExtractDataService;

import hc.base.domains.Pagination;
import hc.mvc.annotations.FormBean;

@Controller
@RequestMapping("/nhgl/basic/extractData")
public class ExtractDataController extends BaseControllerSupport {

	@Autowired
	private ExtractDataService extractDataService;
	@Autowired
	private ScheduleJobService scheduleJobService;
	@Autowired
	private SchedulerManagerService schedulerManagerService;

	/**
	 * 列表展现
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<LabelValueBean> labelValueBeans = new ArrayList<LabelValueBean>();
		labelValueBeans.add(new LabelValueBean("全部", ""));
		labelValueBeans.add(new LabelValueBean("成功", "Y"));
		labelValueBeans.add(new LabelValueBean("失败", "N"));
		labelValueBeans.add(new LabelValueBean("正在执行", "I"));
		model.put("success", htmlSelectOptions(labelValueBeans, ""));
		List<EnumDetail> typeList = DictUtils.getDictList(DictTypeEnum.EXTRACT_NH_TYPE);
		List<EnumDetail> weiduList = DictUtils.getDictList(DictTypeEnum.EXTRACT_NH_WEIDU);
		model.put("typeList", typeList);
		model.put("weiduList", weiduList);
		return "nhgl/basic/extractData/list";
	}

	/**
	 * AJAX获取列表数据。
	 * 
	 * @param pager
	 *            分页信息
	 * @param condition
	 *            查询条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public Pagination<ExtractData> list(Pagination<ExtractData> pager,
			@FormBean(value = "condition", modelCode = "prepareextractData") ExtractData condition) {
		/* Validate Pager Parameters */
		// if (pager.getPageNo() < 1 || pager.getPageSize() < 1 ||
		// pager.getPageSize() > Pagination.MAX_PAGE_SIZE) return pager;
		String text = "";
		Integer type = condition.getNhType();
		Integer weidu = condition.getWeidu();
		int count = 0;
		if (null != type) {
			text = type + "-";
			count++;
		}
		if (null != weidu) {
			if (count == 0) {
				text = text + "-" + weidu;
			} else {
				text = text + weidu;
			}
		}
		condition.setEnumText(text);
		this.extractDataService.find(pager, condition); // 获取分页查询结果
		for (ExtractData extractData : pager) {
			String enumText = extractData.getEnumText();
			if (StringUtils.isNotEmpty(enumText)) {
				String[] arr = enumText.split("-");
				extractData.setNhTypes(arr[0]);
				extractData.setWeidus(arr[1]);
			}
			String targetArguments=extractData.getTargetArguments();
			if(StringUtils.isNotEmpty(targetArguments)){
				String[] arr=targetArguments.split(";");
				String[] arr2=new String[arr.length-1];
				System.arraycopy(arr,1,arr2,0,arr2.length);
				extractData.setTargetArguments(StringUtils.join(arr2, "~"));
			}
		}
		return pager;
	}

	/**
	 * 执行
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(ModelMap model) {
		List<EnumDetail> typeList = DictUtils.getDictList(DictTypeEnum.EXTRACT_NH_TYPE);
		List<EnumDetail> weiduList = DictUtils.getDictList(DictTypeEnum.EXTRACT_NH_WEIDU);
		model.put("typeList", typeList);
		model.put("weiduList", weiduList);
		return "nhgl/basic/extractData/edit";
	}

	/**
	 * 保存编辑内容。
	 * 
	 * @param equipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "add", "edit" }, method = RequestMethod.POST)
	public ScoMessage doEdit(ExtractData entity) {
		try {
			EnumDetail item = DictUtils.getDictItem(DictTypeEnum.EXTRACT_NH_RELATION,
					entity.getNhType() + "-" + entity.getWeidu());
			long jobId = item.getInt1();
			final ScheduleJob scheduleJob = scheduleJobService.getById(jobId);
			scheduleJob.setTargetArguments(scheduleJob.getTargetArguments().split(";")[0]+";"+DateUtils.formatYyyyMMdd(entity.getStartTime()) + ";"
					+ DateUtils.formatYyyyMMdd(entity.getEndTime()));
			Thread t=new Thread(new Runnable() {

				@Override
				public void run() {
					SpringContextHolder.getBean(SchedulerManagerService.class).runImmediately(scheduleJob);
				}
			});
			t.setDaemon(true);
			t.start();
			return ScoMessage.success("抽取数据可能耗费很长时间，请耐心等候...");
		} catch (Exception e) {
			return ScoMessage.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public String view(@RequestParam(required = true) Long id,  ModelMap model) {
		ExtractData extractData = extractDataService.getById(id);
		model.put("scheduleLog", extractData);
		return "nhgl/basic/extractData/view";
	}
}
