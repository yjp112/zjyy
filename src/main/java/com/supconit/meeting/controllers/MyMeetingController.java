package com.supconit.meeting.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.meeting.entities.AccessRecord;
import com.supconit.meeting.entities.RoomReservation;
import com.supconit.meeting.services.AccessRecordService;
import com.supconit.meeting.services.RoomReservationService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * 我的会议控制类
 * @author yuhuan
 * @日期 2015/07
 */
@Controller
@RequestMapping("meeting/myMeeting")
public class MyMeetingController extends BaseControllerSupport {
	@Autowired
	private RoomReservationService roomReservationService;
	@Autowired
	private AccessRecordService accessRecordService;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String go(ModelMap model) {
		model.put("roomTypeList", htmlSelectOptions(DictTypeEnum.MEETING_TYPE,""));
		model.put("type", "1");//1:查询预定会议 ;2:查询参加会议
		return "meeting/myMeeting/list";
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<RoomReservation> list(@ModelAttribute RoomReservation roomReservation,
			@RequestParam(required = false) String roomType,
			@RequestParam(required = false) String type,
			Pagination<RoomReservation> pager,
            ModelMap model) {
		Map map = new HashMap();
		map.put("roomType", roomType);
		map.put("personId", getCurrentPersonId());
		String sub = roomReservation.getMeetingSubject();
		//非法字符
		sub = UtilTool.transIllegalString(sub);
		roomReservation.setMeetingSubject(sub);
		map.put("meetingSubject", roomReservation.getMeetingSubject());
		map.put("meetingStart", roomReservation.getMeetingStart());
		map.put("meetingEnd", roomReservation.getMeetingEnd());
		Pageable<RoomReservation> pagers = null;
		if("2".equals(type)){//查询参加会议
			pagers = roomReservationService.queryMyAttendMeeting(pager,map);
		}else{//查询预定会议
			pagers = roomReservationService.queryMyReserveMeeting(pager,map);
		}
		return pagers;
	}
	
	/**
	 * 取消预定会议
	 */
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)     
	public ScoMessage cancel( @RequestParam("ids") Long[] ids) {
		long reserveId = ids[0];
		RoomReservation roomReservation= roomReservationService.getById(reserveId);
		Date now = new Date();
		if(now.before(roomReservation.getMeetingStart())){//会议还没开始
			roomReservationService.deleteById(reserveId);
			return ScoMessage.success("meeting/myMeeting/list", "取消成功。");
		}else{
			if(now.after(roomReservation.getMeetingEnd())){//会议已结束
				return ScoMessage.error("会议已结束,无法取消。");
			}else{//会议正在进行
				return ScoMessage.error("会议正在进行,无法取消。");
			}
		}
	} 
	
	/**
	 * 结束会议
	 */
	@ResponseBody
	@RequestMapping(value = "over", method = RequestMethod.POST)     
	public ScoMessage over( @RequestParam("ids") Long[] ids) {
		long reserveId = ids[0];
		RoomReservation roomReservation= roomReservationService.getById(reserveId);
		Date now = new Date();
		if(now.before(roomReservation.getMeetingStart())){//会议还没开始
			return ScoMessage.error("会议未开始,无法结束。");
		}else{//会议已开始
			if(now.after(roomReservation.getMeetingEnd())){//会议已结束
				return ScoMessage.error("会议已结束,无法结束。");
			}else{//会议正在进行
				Date endTime;
				try {
					endTime = setMeetingOverTime();
					roomReservation.setMeetingEnd(endTime);
					roomReservation.setActualMeetingEnd(endTime);
					roomReservationService.updateMeetingEndTime(roomReservation);
					return ScoMessage.success("meeting/myMeeting/list", "会议将于"+sdf.format(endTime)+"结束。");
				} catch (ParseException e) {
					e.printStackTrace();
					return ScoMessage.error("时间解析异常。");
				}
			}
		}
	}
	
	/**
	 * 新增或修改操作
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(RoomReservation reserveMeeting) {
		roomReservationService.updateActualMeetingTime(reserveMeeting);
		return ScoMessage.success("meeting/myMeeting/list",ScoMessage.SAVE_SUCCESS_MSG);
	}
	
	/**
	 * 到会状态
	 
	@ResponseBody
	@RequestMapping(value = "queryStatus", method = RequestMethod.POST)
	public List<String> queryStatus(RoomReservation reserveMeeting) {
		AccessRecord record = new AccessRecord();
		record.setDeviceId(reserveMeeting.getDeviceId());
		Date startTime = reserveMeeting.getActualMeetingStart();
		Date endTime = reserveMeeting.getActualMeetingEnd();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.add(Calendar.MINUTE, -10);
		Date startCopy = cal.getTime();//取实际开始前10分钟
		cal.setTime(endTime);
		cal.add(Calendar.MINUTE, 10);
		Date endCopy = cal.getTime();//取实际结束后10分钟
		record.setStartTime(startCopy);
		record.setEndTime(endCopy);
		List<String> list  = accessRecordService.findRecordByTime(record, reserveMeeting);
		return list;
	}*/
	
	/**
	 * 取当前时间最近的半点时间
	 */
	private Date setMeetingOverTime() throws ParseException{
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		String str = sdf.format(now);
		StringBuilder sb = new StringBuilder();
		String zero = ":00";
		String thirty = ":30";
		String space = " ";
		String[] arr = str.split(space);
		String[] hour = arr[1].split(":");
		if(hour[0].equals("23")){
			if((Integer.parseInt(hour[1]) == 0 && Integer.parseInt(hour[2]) > 0) 
					|| (Integer.parseInt(hour[1]) > 0 && Integer.parseInt(hour[1]) < 30 )){
				sb.append(arr[0]).append(space).append(hour[0]).append(thirty).append(zero);
			}else if((Integer.parseInt(hour[1]) == 30 && Integer.parseInt(hour[2]) > 0) 
					|| Integer.parseInt(hour[1]) > 30){
				cal.setTime(sdf.parse(str));
				cal.add(Calendar.DAY_OF_YEAR, 1);
				String strAdd = sdf.format(cal.getTime());
				sb.append(strAdd.split(space)[0]).append(space).append("00").append(zero).append(zero);
			}else{
				sb.append(str);
			}
		}else{
			if((Integer.parseInt(hour[1]) == 0 && Integer.parseInt(hour[2]) > 0) 
					|| (Integer.parseInt(hour[1]) > 0 && Integer.parseInt(hour[1]) < 30 )){
				sb.append(arr[0]).append(space).append(hour[0]).append(thirty).append(zero);
			}else if((Integer.parseInt(hour[1]) == 30 && Integer.parseInt(hour[2]) > 0) 
					|| Integer.parseInt(hour[1]) > 30){
				String hourTmp = Integer.parseInt(hour[0])+1 < 10? ("0"+(Integer.parseInt(hour[0])+1)):(""+(Integer.parseInt(hour[0])+1));
				sb.append(arr[0]).append(space).append(hourTmp).append(zero).append(zero);
			}else{
				sb.append(str);
			}
		}
		return sdf.parse(sb.toString());
	}
}
