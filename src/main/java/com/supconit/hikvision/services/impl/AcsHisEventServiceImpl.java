package com.supconit.hikvision.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.supconit.base.entities.EnumDetail;
import com.supconit.common.utils.FastJsonUtils;
import com.supconit.common.utils.UtilTool;
import com.supconit.hikvision.daos.AccessCountDao;
import com.supconit.hikvision.entities.AccessCount;
import com.supconit.hikvision.entities.AcsHisEvent;
import com.supconit.hikvision.services.AcsHisEventService;
import com.supconit.hikvision.util.HttpClientUtil;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import jodd.util.StringUtil;


@Service
public class AcsHisEventServiceImpl implements AcsHisEventService{
	
	// 根据需要修改
	private static final String HOST = "http://172.20.112.151/webapi/service/"; //此处替换成平台SDK所在服务器IP与端口
	public static final String APPKEY = "b408fd60";//此处替换成申请的appkey
	public static final String SECRET = "543f361f0bcc46dc83eb71fb1d3ab879";//此处替换成申请的secret
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private AccessCountDao accessCountDao; 
	@Autowired
    private DepartmentService deptService;
	
	@Override
	public Pageable<AcsHisEvent> findByPage(Pageable<AcsHisEvent> pager,AcsHisEvent condition) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtil.isNotEmpty(condition.getCardNums())){
			map.put("cardNums",condition.getCardNums());
		}
		if(StringUtil.isNotEmpty(condition.getPersonIds())){
			map.put("personIds",condition.getPersonIds());
		}
		if(StringUtil.isNotEmpty(condition.getPersonName())){
			map.put("personName",condition.getPersonName());
		}
		if(StringUtil.isNotEmpty(condition.getDoorSyscodes())){
			map.put("doorSyscodes",condition.getDoorSyscodes());
		}
		if(condition.getEventType()!=null){
			map.put("eventType",condition.getEventType());
		}
		if(condition.getStartDate()!=null){
			map.put("startTime",condition.getStartDate().getTime());
		}
		if(condition.getEndDate()!=null){
			map.put("endTime",condition.getEndDate().getTime());
		}
		map.put("pageNo", pager.getPageNo());
		map.put("pageSize", pager.getPageSize());
		
		String method1 = "acs/getPlatAcsHistoryEventList"; 
		String json =  HttpClientUtil.doGet(HOST, method1, map, APPKEY, SECRET);
		Map<String, Object> jsonMap = FastJsonUtils.json2map(json);
		int errorCode=(Integer) (null==jsonMap? 1:jsonMap.get("errorCode"));
		if(errorCode==0){
			JSONObject data = (JSONObject) jsonMap.get("data");
//			pager.setPageNo((Integer)data.get("pageNo"));
//			pager.setPageSize((Integer)data.get("pageSize"));
			pager.setTotal((Integer)data.get("total"));
			JSONArray listjson= (JSONArray)data.get("rows");
			List<AcsHisEvent> acsHisEventList=FastJsonUtils.json2list(listjson.toJSONString(), AcsHisEvent.class);
			pager.addAll(acsHisEventList);
		}
		return pager;
	}
	
	@Override
	public Pageable<AccessCount> findAccessCountByPage(Pagination<AccessCount> pager, AccessCount condition) {
		setParameter(condition);
		accessCountDao.findByPage(pager, condition);
		Calendar cal = Calendar.getInstance();
		for (AccessCount accessCount : pager) {
			getDayOfWeek(cal, accessCount);
		}
		return pager;
	}

	private void getDayOfWeek(Calendar cal,AccessCount accessCount){
		Date date;
		try {
			date = sdf.parse(accessCount.getEventDate());
		} catch (ParseException e) {
			date = new Date();
			e.printStackTrace();
		}
		cal.setTime(date);
		int index = cal.get(Calendar.DAY_OF_WEEK);
		switch (index) {
		case 1:
			accessCount.setEventDayOfWeek("星期天");
			break;
		case 2:
			accessCount.setEventDayOfWeek("星期一");
			break;
		case 3:
			accessCount.setEventDayOfWeek("星期二");
			break;
		case 4:
			accessCount.setEventDayOfWeek("星期三");
			break;
		case 5:
			accessCount.setEventDayOfWeek("星期四");
			break;
		case 6:
			accessCount.setEventDayOfWeek("星期五");
			break;
		case 7:
			accessCount.setEventDayOfWeek("星期六");
			break;
		default:
			break;
		}
	}
	private void setParameter(AccessCount condition) {
		if(condition.getDeptId()!=null){
            List<Department> deptList = deptService.findByPid(condition.getDeptId());
            List<Long> deptId=new ArrayList<Long>();
            if(!UtilTool.isEmptyList(deptList));{
                for(Department dept:deptList){
                    deptId.add(dept.getId());
                }
            }
            deptId.add(condition.getDeptId());
            condition.setDeptChildIds(deptId);
        }
	}

	@Override
	public List<EnumDetail> getComeStatusList() {
		List<EnumDetail> enumDetails = new ArrayList<>();
		EnumDetail normal = new EnumDetail();
		normal.setEnumValue("0");
		normal.setEnumText("正常");
		normal.setTypeName("上班状态");
		enumDetails.add(normal);
		EnumDetail late = new EnumDetail();
		late.setEnumValue("1");
		late.setEnumText("异常");//迟到
		late.setTypeName("上班状态");
		enumDetails.add(late);
		return enumDetails;
	}

	@Override
	public List<EnumDetail> getOffStatusList() {
		List<EnumDetail> enumDetails = new ArrayList<>();
		EnumDetail normal = new EnumDetail();
		normal.setEnumValue("0");
		normal.setEnumText("正常");
		normal.setTypeName("下班状态");
		enumDetails.add(normal);
		EnumDetail early = new EnumDetail();
		early.setEnumValue("1");
		early.setEnumText("异常");//早退
		early.setTypeName("下班状态");
		enumDetails.add(early);
		return enumDetails;
	}

	@Override
	public List<AccessCount> findAccessCount(AccessCount condition) {
		setParameter(condition);
		return accessCountDao.findAllByConditions(condition);
	}

}
