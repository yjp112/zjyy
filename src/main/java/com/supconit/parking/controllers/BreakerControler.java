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
import com.supconit.hl.gis.entities.MReadBlock;
import com.supconit.hl.gis.services.IBlockService;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.base.entities.Device;
import com.supconit.base.services.DeviceService;

@Controller
@RequestMapping("/breaker")
public class BreakerControler {

	// 日志打印工具
	Logger logger = LoggerFactory.getLogger(BreakerControler.class);

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private IBlockService blockService;

	@Value("${alarm.message.redisIp}")
	private String redisIp;


	/**
	 * AJAX读取停车位实时信息并更新gis数据库。
	 *
	 * @return
	 */
	@RequestMapping(value = "readStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  readStatus(ModelMap model) {
		List<Device> devices = deviceService.findByCategory(Constant.DC_PTD_LVP);
		List<String> sqlList = new ArrayList<String>();
		try {
			for (Device device : devices) {
				int status = 0;
				List<String> blocks = new ArrayList<String>();
				blocks.add(device.getHpid()+"_IA");
				blocks.add(device.getHpid()+"_IB");
				blocks.add(device.getHpid()+"_IC");
				List<MReadBlock> mReadBlocks = blockService.readBlocks(blocks);
				String ia = "";String ib = "";String ic = "";
				for(MReadBlock tmp:mReadBlocks){  
		            if(tmp.getBlock().equals(device.getHpid()+"_IA"))ia=tmp.getValue();
		            if(tmp.getBlock().equals(device.getHpid()+"_IB"))ib=tmp.getValue();
		            if(tmp.getBlock().equals(device.getHpid()+"_IC"))ic=tmp.getValue();
		        }  
                try{
                   if(Double.parseDouble(ia)>0 && Double.parseDouble(ib)>0 &&Double.parseDouble(ic)>0){
                	   status=1;
                   }
                }catch(Exception e){
                	continue;
                }
				sqlList.add("UPDATE  \"A_B1F_duanluqi\" SET STATUS="+status+" WHERE HPID='"+device.getHpid()+"'");
			}
			DBConnectionUtils.executeBatch((DataSource) SpringContextHolder.getBean("gisDS"), sqlList);
		} catch (Exception e) {
		//	System.out.println(e.getMessage());
			logger.error(e.getMessage());
		    e.printStackTrace();
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("yesLots", yesLots);
//		result.put("noLots", allLots-yesLots);
//		result.put("faultLots", faultLots);
//		result.put("alarmLots", alarmLots);
		return result;
	}
	
	/**
	 * AJAX读取停车位实时信息并更新gis数据库。
	 *
	 * @return
	 */
	@RequestMapping(value = "readVRVStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  readVRVStatus(ModelMap model) {
		List<Device> devices1 = deviceService.findByCategory(Constant.DC_BA_VRV);
		List<Device> devices2 = deviceService.findByCategory(Constant.DC_BA_UNIT);
		List<String> sqlList = new ArrayList<String>();
		try {
			for (Device device : devices1) {
				List<String> blocks = new ArrayList<String>();
				blocks.add(device.getHpid()+"_ST");
				List<MReadBlock> mReadBlocks = blockService.readBlocks(blocks);
				int status1=0;
				String st1 = "";
				for(MReadBlock tmp:mReadBlocks){  
		            if(tmp.getBlock().equals(device.getHpid()+"_ST"))st1=tmp.getValue();
		        }
				if("0".equals(st1)){status1=0;}else{status1=1;}
				sqlList.add("UPDATE  \"A_00F_neiji\" SET STATUS="+status1+" WHERE HPID='"+device.getHpid()+"'");
			}
			for (Device device : devices2) {
				List<String> blocks = new ArrayList<String>();
				blocks.add(device.getHpid()+"_ST");
				List<MReadBlock> mReadBlocks = blockService.readBlocks(blocks);
				int status2=0;
				String st2 = "";
				for(MReadBlock tmp:mReadBlocks){  
		            if(tmp.getBlock().equals(device.getHpid()+"_ST"))st2=tmp.getValue();
		        }  
				if("0".equals(st2)){status2=0;}else{status2=1;}
				sqlList.add("UPDATE  \"A_00F_waiji\" SET STATUS="+status2+" WHERE HPID='"+device.getHpid()+"'");
			}
			DBConnectionUtils.executeBatch((DataSource) SpringContextHolder.getBean("gisDS"), sqlList);
		} catch (Exception e) {
		//	System.out.println(e.getMessage());
			logger.error(e.getMessage());
		    e.printStackTrace();
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("yesLots", yesLots);
//		result.put("noLots", allLots-yesLots);
//		result.put("faultLots", faultLots);
//		result.put("alarmLots", alarmLots);
		return result;
	}

}
