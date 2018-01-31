package com.supconit.meeting.controllers;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.supconit.base.domains.Organization;
import com.supconit.base.entities.EnumDetail;
import com.supconit.base.honeycomb.extend.ExDepartment;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.utils.excel.ExcelExport;
import com.supconit.common.utils.mail.Email;
import com.supconit.common.utils.mail.EmailSender;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.meeting.entities.AccessRecord;
import com.supconit.meeting.entities.MeetingPerson;
import com.supconit.meeting.entities.RoomInfo;
import com.supconit.meeting.entities.RoomReservation;
import com.supconit.meeting.services.AccessRecordService;
import com.supconit.meeting.services.MeetingPersonService;
import com.supconit.meeting.services.RoomInfoService;
import com.supconit.meeting.services.RoomReservationService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * 预定会议类
 * @author yuhuan
 * @日期 2015/07
 */
@Controller
@RequestMapping("meeting/roomReservation")
public class RoomReservationController extends BaseControllerSupport{
	@Autowired
	private RoomInfoService roomInfoService;
	@Autowired
	private MeetingPersonService meetingPersonService;
	@Autowired
	private RoomReservationService roomReservationService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private AccessRecordService accessRecordService;
	@Resource
	private PersonService personService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static ResourceBundle rb = ResourceBundle.getBundle("email");
	
	private static RoomReservation cache;
	
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String go(ModelMap model) {
		List<EnumDetail> roomTypeList = DictUtils.getDictList(DictTypeEnum.MEETING_TYPE);
		List<EnumDetail> roomCapacityList = DictUtils.getDictList(DictTypeEnum.MEETING_CAPACITY);
		List<Organization> orList = organizationService.getFullDeptNameByPersonId(getCurrentPersonId());
		long pid = orList.get(0).getpId();
		if(pid != DEFAULT_DEPARTMENTID){
			model.put("deptName", orList.get(0).getDeptParentName());
			model.put("deptId", pid);
		}else{
			model.put("deptName", orList.get(0).getFullDeptName());
			model.put("deptId", orList.get(0).getDeptId());
		}
		model.put("roomTypeList", roomTypeList);
		model.put("roomCapacityList", roomCapacityList);
		RoomInfo meeting = new RoomInfo();
		meeting.setRoomType("1");//部门会议室
		meeting.setMeetingDate(sdf.format(new Date()));
		model.put("meeting", meeting);
		return "meeting/roomReservation/list";
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<RoomInfo>  list(@ModelAttribute RoomInfo roomInfo,
			Pagination<RoomInfo> pager,
            ModelMap model) {
		List<Organization> orList = organizationService.getFullDeptNameByPersonId(getCurrentPersonId());
		long deptId = orList.get(0).getDeptId();
		long pid = orList.get(0).getpId();
		if(pid != DEFAULT_DEPARTMENTID){
			deptId = pid;
		}
		if(roomInfo.getRoomType().equals("1") && null == roomInfo.getUseDeptId()){
			roomInfo.setUseDeptId(deptId);
		}
		boolean b = false;//是否可预定
		boolean belong = false;//是否归属部门
		if(!roomInfo.getRoomType().equals("2")){
			long queryDept = roomInfo.getUseDeptId().longValue();
			if(deptId == queryDept){
				belong = true;
			}else{
				List<Long> list = roomInfoService.selectDeptChildren(deptId);
				if(list.contains(queryDept)){
					belong = true;
				}
			}
		}
		if((roomInfo.getRoomType().equals("1") && belong) || roomInfo.getRoomType().equals("2")){
			b = true;
		}
		return roomInfoService.findReservation(pager,roomInfo,b,deptId);
	}
	
	/**
	 * 部门树形菜单选择
	 */
	@ResponseBody
    @RequestMapping("dept_firstLevel")
    public String categoryMenu(String name) {
        List<ExDepartment> treeList = departmentService.findByPid(DEFAULT_DEPARTMENTID);
        for (ExDepartment exDepartment : treeList) {
        	exDepartment.setCid(exDepartment.getId());
		}
        ExDepartment dept = new ExDepartment();
        dept.setCid(DEFAULT_DEPARTMENTID);
        dept.setPid(0l);
        dept.setName("部门会议室");
        ExDepartment center = new ExDepartment();
        center.setCid(1l);
        center.setPid(0l);
        center.setName("中心会议室");
        treeList.add(dept);
        treeList.add(center);
        return JSON.toJSONString(treeList);
    }
	/**
	 * 跳转到新增或修改、查看页面
	 * id:预定会议主键
	 * mId:会议室ID
	 * way:1.跳转至预定会议列表;2.跳转至我的会议列表
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,ModelMap model, Long id,Long mId,String way) {
		MeetingPerson reservePerson = null;
		RoomReservation reserveMeeting = null;
		//修改
		if (null != id) {
			reserveMeeting = roomReservationService.getById(id);
			List<MeetingPerson> attendees = meetingPersonService.selectAttendees(id);
			for (MeetingPerson person : attendees) {
				List<Organization> orList = organizationService.getFullDeptNameByPersonId(person.getId());
				String deptFullName = OrganizationUtils.joinFullDeptName(orList);
				person.setDeptName(deptFullName);
			}
			reserveMeeting.setPersonDetailList(attendees);
			model.put("reserveMeeting", reserveMeeting);//预定会议人员
			reservePerson = setReservePerson(reservePerson, reserveMeeting.getReservePersonid());
			if(new Date().after(reserveMeeting.getMeetingStart()) 
					||  getCurrentPersonId().longValue() != reserveMeeting.getReservePersonid().longValue()){
				model.put("viewOnly", true);
				if(getCurrentPersonId().longValue() != reserveMeeting.getReservePersonid().longValue()){
					model.put("readOnly", true);
				}
			}else{
				model.put("readOnly", false);
			}
		}else{
			reservePerson = setReservePerson(reservePerson, getCurrentPersonId());
		}
		RoomInfo meeting = roomInfoService.getById(mId);
		model.put("meeting", meeting);//会议室信息
		model.put("reservePerson", reservePerson);//预定人
		model.put("way", way);
		if("1".equals(way)){
			return "meeting/roomReservation/edit";
		}else{
			cache = reserveMeeting;
			return "meeting/myMeeting/edit";
		}
	}
	
	/**
	 * 到会情况
	 */
	@ResponseBody
	@RequestMapping(value = "queryCondition", method = RequestMethod.POST)
	public Map<String,String> queryCondition() {
		AccessRecord record = new AccessRecord();
		record.setDeviceId(cache.getDeviceId());
		Calendar cal = Calendar.getInstance();
		cal.setTime(cache.getActualMeetingStart());
		cal.add(Calendar.MINUTE, -10);
		Date startCopy = cal.getTime();//取实际开始前10分钟
		cal.setTime(cache.getActualMeetingEnd());
		cal.add(Calendar.MINUTE, 10);
		Date endCopy = cal.getTime();//取实际结束后10分钟
		record.setStartTimes(startCopy.getTime());
		record.setEndTimes(endCopy.getTime());
		return accessRecordService.findArriveCondition(record, cache);
	}
	
	/**
	 * 设置预定人信息及部门全名
	 */
	private MeetingPerson setReservePerson(MeetingPerson reservePerson,long personId){
		reservePerson = meetingPersonService.selectPersonAndDeptInfo(personId);
		List<Organization> orList = organizationService.getFullDeptNameByPersonId(personId);
		String fullDeptName = OrganizationUtils.joinFullDeptName(orList);
		reservePerson.setDeptName(fullDeptName);
		return reservePerson;
	}
	
	
	/**
	 * 新增或修改操作
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(RoomReservation reserveMeeting) {
		boolean b = roomReservationService.queryRoomIsReverse(reserveMeeting);
		if(!b){
			return ScoMessage.error("该时间段已有预约,请调整时间范围");
		}
		Date meetingStart = reserveMeeting.getMeetingStart();
		Date meetingEnd = reserveMeeting.getMeetingEnd();
		if(meetingStart.equals(meetingEnd)){
			return ScoMessage.error("结束时间不能等于开始时间");
		}
		if(new Date().after(meetingStart)){
			return ScoMessage.error("开始时间不能早于当前时间");
		}
		if(null == reserveMeeting.getId()){//新增
			roomReservationService.insert(reserveMeeting);
			//发邮件
			String from = rb.getString("mail.from");
			String username = rb.getString("mail.username");
			String password = rb.getString("mail.password");
			if(StringUtils.isNotEmpty(from) ||StringUtils.isNotEmpty(username) ||StringUtils.isNotEmpty(password)){
				List<ExPerson> pList = personService.find(new ExPerson());
				Map<Long,String> map = new HashMap<Long,String>();
				for (ExPerson person : pList) {
					map.put(person.getId(), person.getPersonEmail());
				}
				Email email = new Email();
				email.setSubject(reserveMeeting.getMeetingSubject());
				email.setContent(reserveMeeting.getMeetingProgram());
				email.setFrom(from);
				List<MeetingPerson> mpList = reserveMeeting.getPersonDetailList();
				String[] emailTo_ = new String[mpList.size()];
				for (int i = 0; i < mpList.size(); i++) {
					emailTo_[i] = map.get(mpList.get(i).getId());
				}
				email.setTo(emailTo_);
				try {
					EmailSender.send(email);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			else{
//				return ScoMessage.error("发件人未配置,邮件无法发送");
//			}
		}else{//修改
			reserveMeeting.setActualMeetingStart(reserveMeeting.getMeetingStart());
			reserveMeeting.setActualMeetingEnd(reserveMeeting.getMeetingEnd());
			roomReservationService.update(reserveMeeting);
		}
		if("1".equals(reserveMeeting.getWay())){
			return ScoMessage.success("meeting/roomReservation/list",ScoMessage.SAVE_SUCCESS_MSG);
		}else{
			return ScoMessage.success("meeting/myMeeting/list",ScoMessage.SAVE_SUCCESS_MSG);
		}
	}
	
	//数据以xls导出
    @RequestMapping("excelExport")
    public void excelExport(HttpServletRequest request,HttpServletResponse response) {
        String title = "到会信息"+DateUtils.format(new Date(),"yyyyMMddHHmmss");
        OutputStream out = null;
        try {
        	response.setHeader("Content-Disposition", "attachment; filename="+ new String((title+".xls").getBytes("GB2312"), "iso8859-1"));
            response.setContentType("application/msexcel;charset=UTF-8");
            out = response.getOutputStream();            
            ExcelExport<MeetingPerson> ex = new ExcelExport<MeetingPerson>();
            
            AccessRecord record = new AccessRecord();
    		record.setDeviceId(cache.getDeviceId());
    		Date startTime = cache.getActualMeetingStart();
    		Date endTime = cache.getActualMeetingEnd();
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(startTime);
    		cal.add(Calendar.MINUTE, -10);
    		Date startCopy = cal.getTime();//取实际开始前10分钟
    		cal.setTime(endTime);
    		cal.add(Calendar.MINUTE, 10);
    		Date endCopy = cal.getTime();//取实际结束后10分钟
    		record.setStartTimes(startCopy.getTime());
    		record.setEndTimes(endCopy.getTime());
    		List<String> list  = accessRecordService.findRecordByTime(record, cache);
    		
            List<MeetingPerson> personDetailList = cache.getPersonDetailList();
            for (int i = 0; i < personDetailList.size(); i++) {
            	MeetingPerson meetingPerson = personDetailList.get(i);
            	meetingPerson.setIsSign(list.get(i));
			}
            ex.exportMeetingExcel(title,personDetailList,cache.getMeetingSubject(),cache.getMeetingCompereName(),out);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
            out.close();
            }catch(Exception e){
            	
            }
        }
    }
    
}
