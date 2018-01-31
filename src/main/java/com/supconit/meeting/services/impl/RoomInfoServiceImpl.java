package com.supconit.meeting.services.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.meeting.daos.RoomInfoDao;
import com.supconit.meeting.daos.RoomReservationDao;
import com.supconit.meeting.domains.RoomUsedCondition;
import com.supconit.meeting.entities.RoomInfo;
import com.supconit.meeting.entities.RoomReservation;
import com.supconit.meeting.services.RoomInfoService;

import hc.base.domains.Pageable;


@Service
public class RoomInfoServiceImpl extends AbstractBaseBusinessService<RoomInfo, Long> implements RoomInfoService{
	
	@Autowired
	private RoomInfoDao roomInfoDao;
	@Autowired
	private RoomReservationDao reserveMeetingDao;
	
	private SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdf_min = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat sdf_sec = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void deleteById(Long id) {
		roomInfoDao.deleteById(id);
	}

	@Override
	public RoomInfo getById(Long id) {
		return roomInfoDao.getById(id);
	}

	@Override
	public void insert(RoomInfo meeting) {
		roomInfoDao.insert(meeting);
	}

	@Override
	public void update(RoomInfo meeting) {
		roomInfoDao.update(meeting);
	}

	@Override
	public Pageable<RoomInfo> findByPage(Pageable<RoomInfo> pager,RoomInfo condition) {
		return roomInfoDao.findByPage(pager,condition);
	}
	
	@Override
	public Pageable<RoomInfo> findReservation(Pageable<RoomInfo> pager,RoomInfo condition,boolean b,long deptId) {
		String suffixStart = " 08:00:00";
		String suffixEnd = " 17:00:00";
		pager = roomInfoDao.findByPage(pager,condition);
		List<Long> roomIdList = new ArrayList<Long>();
		Iterator<RoomInfo> it = pager.iterator();//会议室集合
		while(it.hasNext()){
			RoomInfo m = it.next();
			if(null != m.getUseDeptId() && m.getUseDeptId().longValue() == deptId){
				m.setFlag(true);
			}else{
				m.setFlag(b);
			}
			roomIdList.add(m.getId());
		}
		if(roomIdList.size() > 0){
			RoomReservation reserveMeeting = new RoomReservation();
			String date = condition.getMeetingDate();
			try {
				reserveMeeting.setMeetingStart(sdf_sec.parse(date + suffixStart));
				reserveMeeting.setMeetingEnd(sdf_sec.parse(date + suffixEnd));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			reserveMeeting.setRoomIdList(roomIdList);
			List<RoomReservation> reserveMeetingList = reserveMeetingDao.findByConditions(reserveMeeting);//所有会议室的单天预约情况
			it = pager.iterator();
			while(it.hasNext()){
				RoomInfo m = it.next();
				List<RoomReservation> singleMeetingList = new ArrayList<RoomReservation>();//单个会议室的单天预约情况
				for (RoomReservation rm : reserveMeetingList) {
					if(m.getId().longValue() == rm.getRoomId().longValue()){
						singleMeetingList.add(rm);
					}
				}
				initialMeetingUsedConditionList(m,singleMeetingList,reserveMeeting);
			}
		}
		return pager;
	}
	
	/**
	 * 初始化会议使用情况
	 */
	private void initialMeetingUsedConditionList(RoomInfo meeting,List<RoomReservation> singleMeetingList,RoomReservation rMeeting){
		Map<Integer,String> mapColor = new HashMap<Integer,String>();//存放颜色
		Map<Integer,Long> mapId = new HashMap<Integer,Long>();//存放预定ID
		Date now = new Date();//当前时间
		for (RoomReservation reserveMeeting : singleMeetingList) {
			Long reserveId = reserveMeeting.getId();
			Date start = reserveMeeting.getMeetingStart();
			Date end = reserveMeeting.getMeetingEnd();
			Date t_start = rMeeting.getMeetingStart();
			Date t_end = rMeeting.getMeetingEnd();
			
			//如果时间跨天，则只计算当天情况
			if(start.before(t_start)){
				start = t_start;
			}
			if(end.after(t_end)){
				end = t_end;
			}
			
			Integer startTmp = getIndexFromDay(start);
			Integer endTmp = getIndexFromDay(end);
			if(now.before(start)){//会议还未召开
				if(now.after(reserveMeeting.getMeetingStart())){
					mapColor.put(startTmp, "2");//跨天的情况
				}else{
					mapColor.put(startTmp, "3");
				}
				mapId.put(startTmp, reserveId);
				if((endTmp - startTmp)>1){
					for (int i = startTmp + 1; i < endTmp; i++) {
						if(now.after(reserveMeeting.getMeetingStart())){
							mapColor.put(i, "2");//跨天的情况
						}else{
							mapColor.put(i, "3");
						}
						mapId.put(i, reserveId);
					}
				}
			}else if(now.after(start) && now.before(end)){//会议正在进行
				mapColor.put(startTmp, "2");
				mapId.put(startTmp, reserveId);
				if((endTmp - startTmp)>1){
					for (int i = startTmp + 1; i < endTmp; i++) {
						mapColor.put(i, "2");
						mapId.put(i, reserveId);
					}
				}
			}else if(now.after(end)){//会议已结束
				if(now.before(reserveMeeting.getMeetingEnd())){
					mapColor.put(startTmp, "2");//跨天的情况
				}else{
					mapColor.put(startTmp, "1");
				}
				mapId.put(startTmp, reserveId);
				if((endTmp - startTmp)>1){
					for (int i = startTmp + 1; i < endTmp; i++) {
						if(now.before(reserveMeeting.getMeetingEnd())){
							mapColor.put(i, "2");//跨天的情况
						}else{
							mapColor.put(i, "1");
						}
						mapId.put(i, reserveId);
					}
				}
			}
		}
		List<RoomUsedCondition> meetingUsedConditionList = new ArrayList<RoomUsedCondition>();
		for (int i = 0; i < 18; i++) {
			RoomUsedCondition muc = new RoomUsedCondition();
			String color = mapColor.get(i);
			muc.setColor(null == color? "4":color);
			muc.setId(mapId.get(i));
			meetingUsedConditionList.add(muc);
		}
		meeting.setMeetingUsedConditionList(meetingUsedConditionList);
	}
	
	/**
	 * 定位参数在一天中的时间段
	 */
	private Integer getIndexFromDay(Date date){
		Integer res = 0;
		String dateTmp = sdf_min.format(date);
		dateTmp = dateTmp.substring(11, dateTmp.length());
		if(dateTmp.equals("08:00")){
			res = 0;
		}else if(dateTmp.equals("08:30")){
			res = 1;
		}else if(dateTmp.equals("09:00")){
			res = 2;
		}else if(dateTmp.equals("09:30")){
			res = 3;
		}else if(dateTmp.equals("10:00")){
			res = 4;
		}else if(dateTmp.equals("10:30")){
			res = 5;
		}else if(dateTmp.equals("11:00")){
			res = 6;
		}else if(dateTmp.equals("11:30")){
			res = 7;
		}else if(dateTmp.equals("12:00")){
			res = 8;
		}else if(dateTmp.equals("12:30")){
			res = 9;
		}else if(dateTmp.equals("13:00")){
			res = 10;
		}else if(dateTmp.equals("13:30")){
			res = 11;
		}else if(dateTmp.equals("14:00")){
			res = 12;
		}else if(dateTmp.equals("14:30")){
			res = 13;
		}else if(dateTmp.equals("15:00")){
			res = 14;
		}else if(dateTmp.equals("15:30")){
			res = 15;
		}else if(dateTmp.equals("16:00")){
			res = 16;
		}else if(dateTmp.equals("16:30")){
			res = 17;
		}else if(dateTmp.equals("17:00")){
			res = 18;
		}
		return res;
	}

	@Override
	public Pageable<RoomInfo> meetingReport(Pageable<RoomInfo> pager,RoomInfo condition) {
		String meetingstart = condition.getMeetingDateStart();
		String meetingEnd = condition.getMeetingDateEnd();
		Date start = null;
		Date end = null;
		double between = 9.0;//默认统计1天9小时
		try {
			start = sdf_day.parse(meetingstart);
			end = sdf_day.parse(meetingEnd);
			Calendar cal = Calendar.getInstance();
			cal.setTime(start);
			long time1 = cal.getTimeInMillis();
			cal.setTime(end);
			long time2 = cal.getTimeInMillis();
			between = ((time2 - time1)/(1000 * 60 * 60 * 24) + 1) * 9.0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String suffixStart = " 08:00:00";
		String suffixEnd = " 17:00:00";
		pager = roomInfoDao.findByPage(pager,condition);
		List<Long> roomIdList = new ArrayList<Long>();
		Iterator<RoomInfo> it = pager.iterator();//会议室集合
		while(it.hasNext()){
			RoomInfo m = it.next();
			roomIdList.add(m.getId());
		}
		if(roomIdList.size() > 0){
			RoomReservation reserveMeeting = new RoomReservation();
			try {
				reserveMeeting.setMeetingStart(sdf_sec.parse(meetingstart + suffixStart));
				reserveMeeting.setMeetingEnd(sdf_sec.parse(meetingEnd + suffixEnd));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			reserveMeeting.setRoomIdList(roomIdList);
			List<RoomReservation> reserveMeetingList = reserveMeetingDao.findByConditions(reserveMeeting);//所有会议室一段时间内的预约情况
			calculateMeetingUse(pager, reserveMeetingList, between);
		}
		return pager;
	}
	
	/**
	 * 计算会议使用次数和占用率
	 * @param roomIdList 符合条件的会议室
	 * @param reserveMeetingList 符合条件的会议室的预定列表
	 * @param between 总天数对应有效小时数
	 */
	private void calculateMeetingUse(Pageable<RoomInfo> pager,List<RoomReservation> reserveMeetingList,double between){
		DecimalFormat df = new DecimalFormat("#.00");
		Iterator<RoomInfo> it = pager.iterator();
		while(it.hasNext()){
			RoomInfo roomInfo = it.next();
			List<RoomReservation> reserveList = new ArrayList<RoomReservation>();//单个会议预约情况
			for (RoomReservation roomReservation : reserveMeetingList) {
				if(roomInfo.getId().longValue() == roomReservation.getRoomId().longValue()){
					reserveList.add(roomReservation);
				}
			}
			int size = reserveList.size();
			roomInfo.setUsedTimes(size);
			double usedTimes = 0.0;
			for (RoomReservation roomReservation : reserveList) {
				usedTimes += getAvailHours(roomReservation.getMeetingStart(), roomReservation.getMeetingEnd());
			}
			double percent = (double)(usedTimes/between) * 100;
			percent = Double.parseDouble(df.format(percent));
			roomInfo.setUsedPercent(percent);
		}
	}
	
	/**
	 * 计算一个会议的有效时间(8:00~17:00)
	 * @param mStart 开始时间
	 * @param mEnd 结束时间
	 * @return
	 */
	private double getAvailHours(Date meetingStart,Date meetingEnd){
		double avail = 0;//有效时间
		
		Date d1_tmp = null;
		Date d2_tmp = null;
		String mStart = sdf_sec.format(meetingStart);
		String mEnd = sdf_sec.format(meetingEnd);
		try {
			d1_tmp = sdf_day.parse(mStart);
			d2_tmp = sdf_day.parse(mEnd);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d1_tmp);
			long d1_tmp_long = cal.getTimeInMillis();
			cal.setTime(d2_tmp);
			long d2_tmp_long = cal.getTimeInMillis();
			double between = (double)(d2_tmp_long - d1_tmp_long)/(1000 * 60 * 60 * 24);
			double between_start = 0;
			double between_End = 0;
			cal.setTime(d1_tmp);
			cal.add(Calendar.HOUR_OF_DAY, 8);
			long mStart_start = cal.getTimeInMillis();//开始当天有效开始时间
			cal.add(Calendar.HOUR_OF_DAY, 9);
			long mStart_end = cal.getTimeInMillis();//开始当天有效结束时间
			cal.setTime(d2_tmp);
			cal.add(Calendar.HOUR_OF_DAY, 8);
			long mEnd_start = cal.getTimeInMillis();//结束当天有效开始时间
			cal.add(Calendar.HOUR_OF_DAY, 9);
			long mEnd_end = cal.getTimeInMillis();//结束当天有效结束时间
			cal.setTime(meetingStart);
			long mStart_real = cal.getTimeInMillis();//开始当天真实开始时间
			cal.setTime(meetingEnd);
			long mEnd_real = cal.getTimeInMillis();//结束当天真实结束时间
			if(between > 0 ){//跨天
				if((mStart_end - mStart_real) > 0){
					if(mStart_real < mStart_start){
						mStart_real = mStart_start;
					}
					between_start = (double)(mStart_end - mStart_real)/(1000 * 60 * 60);//开始当天有效时间(小时)
				}
				if((mEnd_real - mEnd_start) > 0){
					if(mEnd_real > mEnd_end){
						mEnd_real = mEnd_end;
					}
					between_End = (double)(mEnd_real - mEnd_start)/(1000 * 60 * 60);//结束当天有效时间(小时)
				}
				avail = between_start + (between-1)*9 + between_End;
			}else{//当天
				if(mStart_start > mStart_real){
					mStart_real = mStart_start;
				}
				if(mEnd_real > mEnd_end){
					mEnd_real = mEnd_end;
				}
				avail = (double)(mEnd_real - mStart_real)/(1000 * 60 * 60);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return 0.0;
		}
		return avail;
	}

	@Override
	public List<Long> selectDeptChildren(long useDeptId) {
		return roomInfoDao.selectDeptChildren(useDeptId);
	}
	
	
}
