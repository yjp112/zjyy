package com.supconit.maintain.controllers;

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
import com.supconit.maintain.entities.MaintainTaskContent;
import com.supconit.maintain.services.MaintainTaskContentService;

/**
 * 保养任务内容控制类
 * @author yuhuan
 * @日期 2015/09
 */
@Controller
@RequestMapping("maintain/content")
public class MaintainTaskContentController extends BaseControllerSupport {
	@Autowired
	private MaintainTaskContentService maintainTaskContentService;
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<MaintainTaskContent> dolist(String maintainCode,
			Pagination<MaintainTaskContent> pager,
            ModelMap model) {
		pager.setPageSize(Integer.MAX_VALUE);
		MaintainTaskContent condition = new MaintainTaskContent();
		condition.setMaintainCode(maintainCode);
		return maintainTaskContentService.findByCondition(pager, condition);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public ScoMessage doEdit(long id,Integer result,String descripton) {
		MaintainTaskContent content = new MaintainTaskContent();
		content.setId(id);
		content.setResult(result==2? null:result);
		content.setDescripton(descripton.equals("null")? null:descripton);
		maintainTaskContentService.update(content);
		return ScoMessage.success(ScoMessage.SAVE_SUCCESS_MSG);
	}

}
