package com.supconit.synchronizeData.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.supconit.common.utils.DBConnectionUtils;
import com.supconit.common.utils.FastJsonUtils;
import com.supconit.common.utils.PropertiesLoader;
import com.supconit.hikvision.util.HttpClientUtil;
import com.supconit.synchronizeData.entities.HKCard;
import com.supconit.synchronizeData.entities.HKPerson;
import com.supconit.synchronizeData.entities.Person;

public class UpdatePersonCardService {

	private static DBConnectionUtils dbUtils;
	private static String apiurl; // 此处替换成平台SDK所在服务器IP与端口
	private static String appkey;// 此处替换成申请的appkey
	private static String secret;// 此处替换成申请的secret
	private static int PAGE_SIZE;// 此处替换成申请的secret
	private static Long SLEEP_TIME;// 此处替换成申请的secret
	private static String personSql = "select description,id,code,name from HO_PERSON where description is null";
	private static Map<String, Person> personMap = new HashMap<String, Person>();
	private static PropertiesLoader loader = new PropertiesLoader("classpath:/config.properties");

	private static Logger log = LoggerFactory.getLogger(UpdatePersonCardService.class);

	static {
		String driverClass = loader.getProperty("jdbc.driver");
		String url = loader.getProperty("jdbc.jdbcUrl");
		String userName = loader.getProperty("jdbc.username");
		String password = loader.getProperty("jdbc.password");
		PAGE_SIZE = loader.getInteger("page.size");
		SLEEP_TIME=loader.getLong("updatePerson.sleep.time");
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
				Long id = rs.getLong("id");
				String code = rs.getString("code");
				String name = rs.getString("name");
				Person p = new Person();
				p.setPersonCode(code);
				p.setPersonId(id);
				p.setPersonName(name);
				personMap.put(code, p);
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

	public static int  getPersonInfoList(List<HKPerson> infoList,int pageNo,int pageSize){
		Integer total =0;
		Map<String, Object> map = new HashMap<String, Object>();;
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		String method1 = "base/getPlatPersonInfoList"; 
		String json =  HttpClientUtil.doGet(apiurl, method1, map, appkey, secret);
		Map<String, Object> jsonMap =FastJsonUtils.json2map(json);
		int errorCode=(Integer) (null==jsonMap? 1:jsonMap.get("errorCode"));
		if(errorCode==0){
			JSONObject data = (JSONObject) jsonMap.get("data");
			total=(Integer)data.get("total");
			JSONArray listjson= (JSONArray)data.get("rows");
			List<HKPerson> list=FastJsonUtils.json2list(listjson.toJSONString(), HKPerson.class);
			infoList.addAll(list);
		}
		return total;
	}
	
	public static int  getCardInfoList(List<HKCard> infoList,int pageNo,int pageSize){
		Integer total =0;
		Map<String, Object> map = new HashMap<String, Object>();;
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		String method1 = "base/getPlatCardInfoList"; 
		String json =  HttpClientUtil.doGet(apiurl, method1, map, appkey, secret);
		Map<String, Object> jsonMap =FastJsonUtils.json2map(json);
		int errorCode=(Integer) (null==jsonMap? 1:jsonMap.get("errorCode"));
		if(errorCode==0){
			JSONObject data = (JSONObject) jsonMap.get("data");
			total=(Integer)data.get("total");
			JSONArray listjson= (JSONArray)data.get("rows");
			List<HKCard> list=FastJsonUtils.json2list(listjson.toJSONString(), HKCard.class);
			infoList.addAll(list);
		}
		return total;
	}
	public static  List<HKPerson>   getAllPersonInfoList(List<HKPerson> infoList){
		 int totle =getPersonInfoList(infoList,1,PAGE_SIZE);
		 int  i = totle/PAGE_SIZE;
		 int  j = totle%PAGE_SIZE;
		 if(i>0){
			 if(j>0)i=i++;
			 for(int k=2;k<=i+1;k++){
				 getPersonInfoList(infoList,k,PAGE_SIZE);
			 }
		 }
		 return infoList;
	}
	public static  List<HKCard>   getAllCardInfoList(List<HKCard> infoList){
		 int totle =getCardInfoList(infoList,1,PAGE_SIZE);
		 int  i = totle/PAGE_SIZE;
		 int  j = totle%PAGE_SIZE;
		 if(i>0){
			 if(j>0)i=i++;
			 for(int k=2;k<=i+1;k++){
				 getCardInfoList(infoList,k,PAGE_SIZE);
			 }
		 }
		 return infoList;
	}
	public static void updatePersonForMap(Map<String, Person> tmpMap){
		List<String> sqlList = new ArrayList<String>();
		Iterator<Map.Entry<String, Person>> entries = tmpMap.entrySet().iterator();  
		while (entries.hasNext()) {  
		    Map.Entry<String, Person> entry = entries.next();  
		    StringBuffer sql=new StringBuffer("UPDATE HO_PERSON SET description='");
		    sql.append(entry.getKey()).append("'").append(" ");
		    sql.append("WHERE ID=").append(entry.getValue().getPersonId().toString());
		    log.info("更新卡号sql:"+sql.toString());  
		    sqlList.add(sql.toString());
		}
        dbUtils.executeBatch(sqlList);
	}
	
	@Transactional
	public void doJob() {
		log.info("---------同步员工卡号任务开始---------");
		try {
			 List<HKPerson> personList = new ArrayList<HKPerson>();
			 personList= getAllPersonInfoList(personList);
			 log.info("读取到海康平台人员数据:"+personList.size()+"条!");
			 List<HKCard> cardList = new ArrayList<HKCard>();
			 cardList=getAllCardInfoList(cardList);
			 log.info("读取到海康平台卡片数据:"+cardList.size()+"条!");
			 personInit();
			 Map<String, Person> tmpMap1 = new HashMap<String, Person>();
			 for(int i = 0; i < personList.size(); i++) {
				  HKPerson hkp=personList.get(i);
				  Person p =personMap.get(hkp.getPersonCode());
				  if(p!=null){
					  tmpMap1.put(hkp.getPersonId().toString(), p);
				  }
		     }
			 Map<String, Person> tmpMap2 = new HashMap<String, Person>();
			 for(int i = 0; i < cardList.size(); i++) {
				  HKCard card=cardList.get(i);
				  if(card.getPersonId()!=null){
					  Person p =tmpMap1.get(card.getPersonId().toString());
					  if(p!=null){
						  tmpMap2.put(card.getCardNumber(), p);
					  }
				  }
		     }
			 updatePersonForMap(tmpMap2);
		}catch (Exception e) {
			e.printStackTrace();
			log.info("同步员工卡号任务异常!");
		}
		log.info("---------同步员工卡号任务结束---------");
	}

}
