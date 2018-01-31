package com.supconit.meeting.controllers;

import java.util.List;

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
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.meeting.entities.RoomInfo;
import com.supconit.meeting.services.RoomInfoService;
import com.supconit.meeting.services.RoomReservationService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * 会议室信息控制类
 * @author yuhuan
 * @日期 2015/07
 */
@Controller
@RequestMapping("meeting/roomInfo")
public class RoomInfoController extends BaseControllerSupport {
	
	@Autowired
	private RoomInfoService roomInfoService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private RoomReservationService roomReservationService;
	
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(ModelMap model) {
		List<Department> deptList = departmentService.findAllWithoutVitualRoot();
		model.put("deptList", deptList);
		model.put("roomTypeList", htmlSelectOptions(DictTypeEnum.MEETING_TYPE,""));
		model.put("network", htmlSelectOptions(DictTypeEnum.MEETING_NETWORK,""));
		model.put("multiFunction", htmlSelectOptions(DictTypeEnum.MEETING_MULTI_FUNCTIONAL,""));
		return "meeting/roominfo/list";
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<RoomInfo>  dolist(RoomInfo meeting,
			@RequestParam(required = false) String treeId,
			Pagination<RoomInfo> pager,
            ModelMap model) {
		if("0".equals(treeId)){
			treeId = null;
		}
		if(null != treeId){
			meeting.setUseDeptId(Long.parseLong(treeId));
		}
		return roomInfoService.findByPage(pager, meeting);
	}
	
	/**
	 * 跳转到编辑页
	 */
	@RequestMapping(value="edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,ModelMap model, @RequestParam(required = false) Long id) {
		//修改
		if (null != id) {
			RoomInfo meeting = roomInfoService.getById(id);
			model.put("meeting", meeting);
		}	
		List<EnumDetail> listMeetingType = DictUtils.getDictList(DictTypeEnum.MEETING_TYPE);
    	List<EnumDetail> listMeetingNetWork = DictUtils.getDictList(DictTypeEnum.MEETING_NETWORK);
    	List<Department> dList = departmentService.findAllWithoutVitualRoot();
    	List<Department> firstLevelDept = departmentService.findByPid(DEFAULT_DEPARTMENTID);
    	model.put("listMeetingType", listMeetingType);	
    	model.put("listMeetingNetWork", listMeetingNetWork);
    	model.put("dList", dList);
    	model.put("firstLevelDept", firstLevelDept);
		model.put("viewOnly", viewOnly);
		return "meeting/roominfo/edit";
	}
	
	/**
	 * 新增或修改操作
	 */
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public ScoMessage doEdit(RoomInfo meeting) {
		if (null == meeting.getId()) {
			copyCreateInfo(meeting);
		} else {
			copyUpdateInfo(meeting);
		}
		roomInfoService.save(meeting);
		return ScoMessage.success("meeting/roomInfo/list",ScoMessage.SAVE_SUCCESS_MSG);
	}
	
	/**
	 * 删除操作
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage doDelete( @RequestParam("ids") Long[] ids) {
		long roomId = ids[0];
		boolean b = roomReservationService.countReservationByRoomId(roomId);
		if(b){
			return ScoMessage.error("该会议室已被使用,不能删除。");
		}
		roomInfoService.deleteById(ids[0]);
		return ScoMessage.success("meeting/roomInfo/list", ScoMessage.DELETE_SUCCESS_MSG);
	} 
	
	
}
