package com.supconit.inspection.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.inspection.services.InspectionTaskContentService;
import com.supconit.inspection.entities.InspectionTaskContent;

/**
 * 保养任务内容控制类
 * @author yuhuan
 * @日期 2015/09
 */
@Controller
@RequestMapping("inspection/content")
public class InspectionTaskContentController extends BaseControllerSupport {
	@Autowired
	private InspectionTaskContentService inspectionTaskContentService;
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<InspectionTaskContent> dolist(String inspectionCode,
			Pagination<InspectionTaskContent> pager,
            ModelMap model) {
		pager.setPageSize(Integer.MAX_VALUE);
		InspectionTaskContent condition = new InspectionTaskContent();
		condition.setInspectionCode(inspectionCode);
		return inspectionTaskContentService.findByCondition(pager, condition);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public ScoMessage doEdit(long id,Integer result,String descripton) {
		InspectionTaskContent content = new InspectionTaskContent();
		content.setId(id);
		content.setResult(result==2? null:result);
		content.setDescripton(descripton.equals("null")? null:descripton);
		inspectionTaskContentService.update(content);
		return ScoMessage.success(ScoMessage.SAVE_SUCCESS_MSG);
	}

}