package com.supconit.duty.controllers;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.datasource.DynamicDataSource;
import com.supconit.common.utils.ireport.JasperReportUtil;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.duty.entities.DutyLog;
import com.supconit.duty.entities.DutyLogDetail;
import com.supconit.duty.services.DutyLogDetailService;
import com.supconit.duty.services.DutyLogService;
import com.supconit.honeycomb.base.context.SpringContextHolder;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("duty/dutylog")
public class DutyLogController extends BaseControllerSupport{
	@Autowired
	private DutyLogService dutyLogService;
	@Autowired
	private DutyLogDetailService dutyLogDetailService;

	@RequestMapping("go")
	public String go(ModelMap model,DutyLog dutyLog){
		model.put("dutyLog",dutyLog);
		return "duty/dutyLog/dutyLog_list";
	}
	
	
	@ResponseBody
    @RequestMapping("list")
	public Pageable<DutyLog> list(
			Pagination<DutyLog> page, @ModelAttribute DutyLog dutyLog,
			ModelMap model){
		Pageable<DutyLog> pager = dutyLogService.findByCondition(page, dutyLog);	
		model.put("dutyLog", dutyLog);
		model.put("pager", pager);
		
		return pager;
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false) Long id,DutyLog dutyLog, ModelMap model) {
		DutyLog dutyLogx=null;
		if (null != id) {        //修改状态
			dutyLogx = dutyLogService.getById(id);
			if (null == dutyLogx){
				throw new IllegalArgumentException("Object does not exist");
			}
			if(dutyLog.getDutyType()!=3||dutyLog.getDutyType()!=4){     //3 为总值班   4为呼叫中心
				//获取值班日志详情数据连表				
				dutyLogx.setDutyLogDetailList(dutyLogDetailService.findAll(id));
			}
			copyCreateInfo(dutyLogx);
			model.put("dutyLog", dutyLogx);
		}else{                //添加状态
			copyCreateInfo(dutyLog);
			model.put("dutyLog", dutyLog);
		}
		model.put("viewOnly", viewOnly);
		//获取班组
		model.put("listGroupType", DictUtils.getDictList(DictTypeEnum.GROUP_ENUM));
		//获取班次
		model.put("listOrderType", DictUtils.getDictList(DictTypeEnum.ORDER_ENUM));
		if(dutyLog.getDutyType()==3||dutyLog.getDutyType()==4){    //3 为总值班   4为呼叫中心
			return "duty/dutyLog/dutyLogAll_edit";   
		}
		return "duty/dutyLog/dutyLog_edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(DutyLog dutyLog){
		String info=checkDate(dutyLog.getDutyLogDetailList());
		if(info!=null){
			return ScoMessage.error(info);
		}
		if (dutyLog.getId() == null){
			
			copyCreateInfo(dutyLog);   //设置创建人，创建时间，创建id
			dutyLogService.insert(dutyLog);
		} else {
			copyUpdateInfo(dutyLog);
			dutyLogService.update(dutyLog);
		}
		return ScoMessage.success("duty/dutylog/go?dutyType="+dutyLog.getDutyType(), "操作成功。");
	}
	private String checkDate(List<DutyLogDetail> dutyLogDetailList) { 
		for(DutyLogDetail dutylist:dutyLogDetailList){
			if(dutylist.getEventTime()==null){
				return "发生时间不能为空";
			}
			if(dutylist.getPlace()==""){
				return "具体位置不能为空";
			}else{
				if(dutylist.getPlace().getBytes().length>100){
					return "具体位置大小不大于100字节";
				}
			}
			if(dutylist.getDeviceCode()==""){
				return "报警设备编码不能为空";
			}
			if(dutylist.getAralmType()==0){
				return "报警类型不能为空";
			}
			if(dutylist.getResult()==""){
				return "处理结果不能为空";
			}
		}
		return null;
	}


	/*delete   
	    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids,DutyLog dutyLog) {
		if(dutyLog!=null){
			dutyLogService.deleteByIds(ids,dutyLog.getDutyType()); 
		}
		return ScoMessage.success("duty/dutylog/go?dutyType="+dutyLog.getDutyType(), "删除成功。");
	}   

}
