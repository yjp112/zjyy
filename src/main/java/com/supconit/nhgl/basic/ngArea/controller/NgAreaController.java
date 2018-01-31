package com.supconit.nhgl.basic.ngArea.controller;

import java.util.List;

import jodd.util.StringUtil;
import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.nhgl.analyse.electric.area.service.NhAreaService;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;
import com.supconit.nhgl.basic.ngArea.service.NgAreaService;

@Controller
@RequestMapping("nhgl/basic/ngArea")
public class NgAreaController extends BaseControllerSupport {
	@Autowired
	private NgAreaService ngAreaService;
	@Autowired
	private NhAreaService nhAreaService;
	
	
	@RequestMapping("go")
	public String go(ModelMap model) {
		List<NgArea> treeList = null;
		treeList = ngAreaService.findTree();
		model.put("treeList", treeList);
		return "nhgl/basic/ngArea/list";
    }

	@RequestMapping("list")
    @ResponseBody
	public Pageable<NgArea>  list(@ModelAttribute NgArea ngArea,
			@RequestParam(required = false) String treeId,
			Pagination<NgArea> pager,
            ModelMap model) {
		if(StringUtil.isNotBlank(treeId)){ 
			ngArea.setId(Long.parseLong(treeId));
		}else{
			if(null==nhAreaService.findRootId()){
				ngArea.setId(0l);
			}else{
				ngArea.setId(nhAreaService.findRootId());
			}
		}
		return  ngAreaService.findByPage(pager, ngArea);
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,ModelMap model, @RequestParam(required = false) Long id) {
    	//修改
		if (null != id) {
			NgArea ngArea = ngAreaService.getById(id);
			//获取父级节点的姓名
			if(null!=nhAreaService.findRootId()){
				if(nhAreaService.findRootId().longValue()!=ngArea.getId().longValue()){
					ngArea.setParentName(ngAreaService.getById(ngArea.getParentId()).getAreaName()); 
				}
			}
			model.put("ngArea", ngArea);
		}
		model.put("viewOnly", viewOnly);
		return "nhgl/basic/ngArea/edit";
	}
	
	
	/**
	 * 区域保存/修改
	 * @return
	 * @throws HoncombException
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(NgArea ngArea) {
		if (ngArea.getId() == null) {
			copyCreateInfo(ngArea);
			ngAreaService.insert(ngArea);
		} else {
			copyUpdateInfo(ngArea);
			ngAreaService.update(ngArea);
		}
		return ScoMessage.success("nhgl/basic/ngArea/go","操作成功。");
	}
	
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		ngAreaService.removeNgArea(ids);
		return ScoMessage.success("nhgl/basic/ngArea/go", "删除成功。");
	} 
	
}
