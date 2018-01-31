package com.supconit.parking.controllers;

import java.util.*;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DBConnectionUtils;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.montrol.util.RedisUtil;
import com.supconit.parking.entity.ParkingLot;
import com.alibaba.fastjson.JSONObject;
import com.supconit.base.entities.Device;
import com.supconit.base.services.DeviceService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("/parking")
public class RealLotControler {

	// 日志打印工具
	Logger logger = LoggerFactory.getLogger(RealLotControler.class);

	@Autowired
	private DeviceService deviceService;

	@Value("${alarm.message.redisIp}")
	private String redisIp;

	@Value("${alarm.message.redisPort}")
	private String redisPort;

	/**
	 * AJAX读取停车位实时信息并更新gis数据库。
	 *
	 * @return
	 */
	@RequestMapping(value = "readLot", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  readLot(ModelMap model) {
		List<Device> devices = deviceService.findByCategory(Constant.DC_PMS_PS);
		List<ParkingLot> allLotList = new ArrayList<ParkingLot>();
		List<String> sqlList = new ArrayList<String>();
		int allLots = devices.size();
		int yesLots = 0;
		int faultLots = 0;
		int alarmLots = 0;
		try {
			JedisPool pool = RedisUtil.getPool(redisIp,Integer.parseInt(redisPort) );
			Jedis jedis = pool.getResource();
			for (Device device : devices) {
				String value = jedis.get(device.getHpid());
//				System.out.println("value:"+value);
				if (value != null) {
                    ParkingLot lot= new ParkingLot();
					JSONObject json = JSONObject.parseObject(value);
					lot.setHpid(device.getHpid());
					lot.setLotStatus(Integer.parseInt((String)json.get("LOTSTATUS")));
					lot.setDevStatus(Integer.parseInt((String)json.get("DEVSTATUS")));
					lot.setBatStatus(Integer.parseInt((String)json.get("BATSTATUS")));
					if(lot.getLotStatus()==1) yesLots++;
					if(lot.getDevStatus()==1) faultLots++;
					if(lot.getBatStatus()==1) alarmLots++;
					allLotList.add(lot);
					sqlList.add("UPDATE  \"D_01F_cheku\" SET STATUS="+lot.getLotStatus()+",ALARM="+lot.getBatStatus()+",FAULT="+lot.getDevStatus()+" WHERE HPID='"+lot.getHpid()+"'");
				}
			}
			DBConnectionUtils.executeBatch((DataSource) SpringContextHolder.getBean("gisDS"), sqlList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		     e.printStackTrace();
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("allLots", allLots);
//		result.put("yesLots", yesLots);
		result.put("noLots", allLots-yesLots);
		result.put("faultLots", faultLots);
		result.put("alarmLots", alarmLots);
		return result;
	}

}
