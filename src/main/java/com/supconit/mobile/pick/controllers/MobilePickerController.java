package com.supconit.mobile.pick.controllers;

import com.supconit.base.entities.DutyGroupPerson;
import com.supconit.base.entities.EnumDetail;
import com.supconit.common.utils.DictUtils;
import com.supconit.mobile.pick.entities.MobilePicker;
import com.supconit.mobile.pick.services.MobilePickerService;
import hc.mvc.annotations.FormBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wangwei on 16/4/15.
 */
@Controller
@RequestMapping("mobile/pick")
public class MobilePickerController{

	private static final transient Logger logger = LoggerFactory.getLogger(MobilePickerController.class);
	
	@Autowired
	private MobilePickerService mobilePickerService;

	@ModelAttribute("prepareDutyGroupPerson")
	public DutyGroupPerson prepareDutyGroupPerson(){return new DutyGroupPerson();}

	/**
	 * AJAX获取列表数据。
	 * @param dutyGroupPerson   查询条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "searchDutyGroupPersons")
	public MobilePicker searchDutyGroupPersons(@FormBean(value = "condition",modelCode = "prepareDutyGroupPerson")DutyGroupPerson dutyGroupPerson)
	{
		MobilePicker mobilePicker=this.mobilePickerService.searchDutyGroupPersons(dutyGroupPerson);
		return mobilePicker;
	}

	/**
	 * AJAX获取列表数据。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "searchRepairMode")
	public MobilePicker searchRepairMode()
	{
		List<EnumDetail> repairModes=DictUtils.getDictList(DictUtils.DictTypeEnum.REPAIR_MODE);
		MobilePicker mobilePicker=this.mobilePickerService.searchMobilePicker(repairModes);
		return mobilePicker;
	}

	/**
	 * AJAX获取列表数据。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "searchWorkerType")
	public MobilePicker searchWorkerType()
	{
		List<EnumDetail> workerTypes=DictUtils.getDictList(DictUtils.DictTypeEnum.REPAIR_WORK);
		MobilePicker mobilePicker=this.mobilePickerService.searchMobilePicker(workerTypes);
		return mobilePicker;
	}

	/**
	 * AJAX获取列表数据。
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "searchVisitReason")
	public MobilePicker searchVisitReason()
	{
		List<EnumDetail> visitReasons=DictUtils.getDictList(DictUtils.DictTypeEnum.VISIT_REASON);
		MobilePicker mobilePicker=this.mobilePickerService.searchMobilePicker(visitReasons);
		return mobilePicker;
	}
}
