package com.supconit.synchronizeData.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.supconit.common.utils.DBConnectionUtils;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.FastJsonUtils;
import com.supconit.common.utils.PropertiesLoader;
import com.supconit.hikvision.util.HttpClientUtil;
import com.supconit.synchronizeData.entities.AcsHisEvent;
import com.supconit.synchronizeData.entities.Device;
import com.supconit.synchronizeData.entities.Person;

public class ReadDoorEventService {

	private static DBConnectionUtils dbUtils;
	private static String apiurl; // 此处替换成平台SDK所在服务器IP与端口
	private static String appkey;// 此处替换成申请的appkey
	private static String secret;// 此处替换成申请的secret
	private static int PAGE_SIZE;// 此处替换成申请的secret
	private static Long SLEEP_TIME;// 此处替换成申请的secret
	private static String personSql = "select description,id,code,name from HO_PERSON where description is not null";
	private static String deviceSql = "select extended1,id,device_name from DEVICE  where category_id=21249";
	private static String maxTimeSql = "select max(event_time) maxTime from ACCESS_RECORD";
	private static Map<String, Person> personMap = new HashMap<String, Person>();
	private static Map<String, Device> deviceMap = new HashMap<String, Device>();
	private static PropertiesLoader loader = new PropertiesLoader("classpath:/config.properties");

	private static Logger log = LoggerFactory.getLogger(ReadDoorEventService.class);

	static {
		String driverClass = loader.getProperty("jdbc.driver");
		String url = loader.getProperty("jdbc.jdbcUrl");
		String userName = loader.getProperty("jdbc.username");
		String password = loader.getProperty("jdbc.password");
		PAGE_SIZE = loader.getInteger("page.size");
		SLEEP_TIME=loader.getLong("readDoor.sleep.time");
		apiurl = loader.getProperty("hk8700.host");
		appkey = loader.getProperty("hk8700.appkey");
		secret = loader.getProperty("hk8700.secret");
		dbUtils = new DBConnectionUtils(driverClass, url, userName, password);
	}

	public static void personInit() {
		Connection conn = dbUtils.getConnection();
	//	log.info("初始化数据库连接...");
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(personSql);
			while (rs.next()) {
				String desc = rs.getString("description");
				Long id = rs.getLong("id");
				String code = rs.getString("code");
				String name = rs.getString("name");
				Person p = new Person();
				p.setPersonCode(code);
				p.setPersonId(id);
				p.setPersonName(name);
				personMap.put(desc, p);
			}
			log.info("加载人员信息成功!");
		} catch (SQLException e) {
			log.info("加载人员信息失败!");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deviceInit() {
		Connection conn = dbUtils.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(deviceSql);
			while (rs.next()) {
				String ext = rs.getString("extended1");
				Long id = rs.getLong("id");
				String name = rs.getString("device_name");
				Device dev = new Device();
			    dev.setDeviceId(id);
			    dev.setDeviceName(name);
				deviceMap.put(ext, dev);
			}
			log.info("加载门禁设备信息成功!");
		} catch (SQLException e) {
			log.info("加载门禁设备信息失败!");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static int  getPlatAcsHistoryEventList(List<AcsHisEvent> eventList,Date startDate,int pageNo,int pageSize){
		Integer total =0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime",startDate.getTime()+10);
		Date endDate=DateUtils.addHour(startDate, 4);
		map.put("endTime",endDate.getTime());
		map.put("eventType","198914");
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		String method1 = "acs/getPlatAcsHistoryEventList"; 
		String json =  HttpClientUtil.doGet(apiurl, method1, map, appkey, secret);
		Map<String, Object> jsonMap =FastJsonUtils.json2map(json);
		int errorCode=(Integer) (null==jsonMap? 1:jsonMap.get("errorCode"));
		if(errorCode==0){
			JSONObject data = (JSONObject) jsonMap.get("data");
//			pager.setPageNo((Integer)data.get("pageNo"));
//			pager.setPageSize((Integer)data.get("pageSize"));
			total=(Integer)data.get("total");
			JSONArray listjson= (JSONArray)data.get("rows");
			List<AcsHisEvent> acsHisEventList=FastJsonUtils.json2list(listjson.toJSONString(), AcsHisEvent.class);
			eventList.addAll(acsHisEventList);
		}
		return total;
	}
	public static int[] InsertEventList(List<AcsHisEvent> eventList){
		List<String> sqlList = new ArrayList<String>();
		for(int i = 0; i < eventList.size(); i++) {  
			AcsHisEvent event = eventList.get(i);
			StringBuffer sql=new StringBuffer("INSERT INTO ACCESS_RECORD(ID,EVENT_TIME,EVENT_DATE,EVENT_TYPE,PERSON_ID,PERSON_NO,PERSON_NAME,CARD_NO,DEVICE_ID,DEVICE_NAME) VALUES(");
			sql.append("SEQ_ACCESS_RECORD.NEXTVAL").append(",");
			sql.append(event.getEventTime().toString()).append(",");
			String yMd=DateUtils.formatYyyyMMdd(new Date(event.getEventTime()));
			sql.append("'").append(yMd).append("',");
			sql.append("'").append(event.getEventType().toString()).append("',");
			Person p = personMap.get(event.getCardNo());
			if(p!=null){
				sql.append(p.getPersonId().toString()).append(",");
				sql.append("'").append(p.getPersonCode().toString()).append("',");
				sql.append("'").append(p.getPersonName()).append("',");
			}else{
				sql.append(event.getPersonId()).append(",");
				sql.append("'").append("',");
				sql.append("'").append(event.getPersonName()).append("',");
			}
			sql.append("'").append(event.getCardNo()).append("',");
			Device d = deviceMap.get(event.getDoorSyscode());
			if(d!=null){
				sql.append(d.getDeviceId().toString()).append(",");
				sql.append("'").append(d.getDeviceName()).append("')");
			}else{
				sql.append("null,");
				sql.append("'").append(event.getDeviceName()).append("')");
			}
			sqlList.add(sql.toString());
        } 
		return dbUtils.executeBatch(sqlList);
	}
	public static Long getMaxEventTime() {
		Connection conn = dbUtils.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		Long maxTime =0L;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(maxTimeSql);
			if (rs.next()) {
				maxTime=rs.getLong("maxTime");
			}
			log.info("查询最新事件时间成功!"+DateUtils.format(new Date(maxTime)));
		} catch (SQLException e) {
			log.info("查询最新事件时间失败!");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return maxTime;
	}

	public static void ReadDoorEvent() {
		 Date startDate=null;
		 Long maxTime=getMaxEventTime();
		 if(maxTime==0L){
			 try {
				startDate=DateUtils.parseDate(DateUtils.getMonthBegin(2016, 2), "yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 }else{
			 startDate= new Date(maxTime);
		 }
		 List<AcsHisEvent> eventList = new ArrayList<AcsHisEvent>();
		 int totle =getPlatAcsHistoryEventList(eventList, startDate,1,PAGE_SIZE);
		 int  i = totle/PAGE_SIZE;
		 int  j = totle%PAGE_SIZE;
		 if(i>0){
			 if(j>0)i=i++;
			 for(int k=2;k<=i+1;k++){
				 getPlatAcsHistoryEventList(eventList, startDate,k,PAGE_SIZE);
			 }
		 }
		 log.info("查询到最新:"+eventList.size()+"条考勤数据");
		 int[] r=InsertEventList(eventList);
		 log.info("成功插入:"+r.length+"条考勤数据");
	}
	
	@Transactional
	public void doJob() {
		//log.info("---------同步考勤数据任务开始---------");
		try {
			personInit();
			deviceInit();
			ReadDoorEvent();
		}catch (Exception e) {
			e.printStackTrace();
			log.info("同步考勤数据异常!");
		}
		//log.info("---------同步考勤数据任务结束---------");
	}

}
