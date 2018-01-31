package com.supconit.visitor.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.visitor.entities.VisitorReport;
import com.supconit.visitor.services.VisitorReportService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;


/**
 * 访客统计控制类
 * @author yuhuan
 * @日期 2015/07
 */
@Controller
@RequestMapping("visitor/report")
public class VisitorReportController extends BaseControllerSupport {
	@Autowired
	private VisitorReportService visitorReportService;
	private static final SimpleDateFormat sdf_month = new SimpleDateFormat("yyyy-MM");
	
	/**
	 * 跳转到列表
	 * type:1.部门统计;2.年度统计;3.事由统计
	 */
	@RequestMapping("list/{type}")
    public String page(@PathVariable(value = "type") int type, ModelMap model) {
        String path = "";
        String month = "";
        switch (type) {
        case 1:
            month = sdf_month.format(new Date());
            model.put("startMonth", month);
            model.put("endMonth", month);
            path = "visitor/report/dept";
            break;
        case 2:
        	Calendar cal = Calendar.getInstance();
        	int year=cal.get(Calendar.YEAR);
        	model.put("visitYear", year);
            path = "visitor/report/year";
            break;
        case 3:
            month = sdf_month.format(new Date());
            model.put("startMonth", month);
            model.put("endMonth", month);
            path = "visitor/report/purpose";
            break;
        default:
        	month = sdf_month.format(new Date());
            model.put("startMonth", month);
            model.put("endMonth", month);
            path = "visitor/report/dept";
            break;
        }
        model.put("type", type);
        return path;
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<VisitorReport>  list(@ModelAttribute VisitorReport report, int type,
            ModelMap model) {
		List<VisitorReport> list = visitorReportService.findList(report, type);
		Pageable<VisitorReport> pager = new Pagination<VisitorReport>(list);
        pager.setPageNo(1);
        pager.setPageSize(Integer.MAX_VALUE);
        pager.setTotal(list.size());
		return pager;
	}

	
}
