package com.supconit.hikvision.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.EnumDetail;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.StringUtil;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.hikvision.entities.AccessCount;
import com.supconit.hikvision.entities.AcsHisEvent;
import com.supconit.hikvision.services.AcsHisEventService;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import jodd.datetime.JDateTime;
import net.sf.jxls.transformer.XLSTransformer;


/**
 *   门禁考勤控制类
 * @author wangbo
 * @日期 2016/03
 */
@Controller
@RequestMapping("hikvision/acsHisEvent")
public class AcsHisEventController extends BaseControllerSupport {
	@Autowired
	private DepartmentService deptService;
	@Autowired
	private AcsHisEventService  acsHisEventService;
	
	
	/**
	 * 导出考勤结果到Excel文件中
	 */
	@ResponseBody
	@RequestMapping(value = "exportAccessCount")
	public void exportAccessCount(AccessCount condition  , HttpServletResponse response) {
		try {
			
			URL url = this.getClass().getResource("/templates/accessCountTemplate.xls");
			boolean b = hasAdminRole();
			if (!b) {
				condition.setPersonId(getCurrentPersonId());
			}
			if(null!=condition.getDeptId() && condition.getDeptId()==DEFAULT_DEPARTMENTID) condition.setDeptId(null);
			List<AccessCount> accessCountList = acsHisEventService.findAccessCount(condition);
			System.out.println(accessCountList.size());
			
			// ===========================
			String filename = condition.getStartDate()+"至"+condition.getEndDate()+"考勤数据.xls";
			response.reset();
			response.addHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
			response.setContentType("application/octet-stream");
			// excel.writeToStream(response.getOutputStream());
			Map<String, Object> beanParams = new HashMap<String, Object>();
			beanParams.put("accessCountList", accessCountList);
			XLSTransformer transformer = new XLSTransformer();
			org.apache.poi.ss.usermodel.Workbook workbook = transformer.transformXLS(url.openStream(), beanParams);
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(ModelMap model,String doorSyscodes) {
		model.put("doorSyscodes", doorSyscodes);
		model.put("eventTypes", htmlSelectOptions(DictTypeEnum.DOOR_EVENT_TYPE,"198914"));
		return "hikvision/acshisevent/list";
    }
	
	@RequestMapping(value="listTongji",method=RequestMethod.GET)
	public String listTongji(ModelMap model,AccessCount condition,String doorSyscodes) {
		model.put("doorSyscodes", doorSyscodes);
		List<EnumDetail> comeStatusList = acsHisEventService.getComeStatusList();
		List<EnumDetail> offStatusList = acsHisEventService.getOffStatusList();
		JDateTime now = new JDateTime();
		JDateTime firstDate =new JDateTime(DateUtils.getMonthBegain());
		String FirstMonth = firstDate.toString("YYYY-MM-DD");
        String LastMonth = now.toString("YYYY-MM-DD");
        if(null==condition||StringUtil.isNullOrEmpty(condition.getStartDate())||StringUtil.isNullOrEmpty(condition.getEndDate())){
        	condition = new AccessCount();
            condition.setStartDate(FirstMonth );
            condition.setEndDate(LastMonth );
        }
        List<Department> deptList = deptService.findAllWithoutVitualRoot();
		model.addAttribute("treeList", deptList);
        model.put("searchStart", condition.getStartDate());
        model.put("searchEnd", condition.getEndDate());
		model.put("comeStatusList", comeStatusList);
		model.put("offStatusList", offStatusList);
		return "hikvision/acshisevent/listTongji";
    }
	/**
	 * 列表统计
	 */
	@RequestMapping(value="listTongji",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<AccessCount>  dolistTongji(ModelMap model,AccessCount condition,Pagination<AccessCount> pager) {
		List<EnumDetail> comeStatusList = acsHisEventService.getComeStatusList();
		List<EnumDetail> offStatusList = acsHisEventService.getOffStatusList();
		JDateTime now = new JDateTime();
        String LastMonth = now.toString("YYYY-MM-DD");
        if(null==condition||StringUtil.isNullOrEmpty(condition.getStartDate())||StringUtil.isNullOrEmpty(condition.getEndDate())){
        	condition = new AccessCount();
            condition.setStartDate(LastMonth );
            condition.setEndDate(LastMonth);
        }
		model.put("comeStatusList", comeStatusList);
		model.put("offStatusList", offStatusList);
		boolean b = hasAdminRole();
		if (!b) {
			condition.setPersonId(getCurrentPersonId());
		}
		if(null!=condition.getDeptId() && condition.getDeptId()==DEFAULT_DEPARTMENTID) condition.setDeptId(null);
		return acsHisEventService.findAccessCountByPage(pager, condition);
	}
	
	/**
	 * 列表查询海康接口查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<AcsHisEvent>  dolist(AcsHisEvent event,Pagination<AcsHisEvent> pager) {
		boolean b = hasAdminRole();
		if (!b) {
			event.setCardNums(getCurrentPerson().getDescription());
		}
		return acsHisEventService.findByPage(pager, event);
	}
	
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="tongjiLink",method=RequestMethod.GET)
	public String tongjiLink(ModelMap model,Integer personId,String eventDate) {
		model.put("personId", personId);
		model.put("eventDate", eventDate);
		return "hikvision/acshisevent/tongjiLink";
    }
	
}
