package com.supconit.dev.gis.controllers;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.Constant;
import com.supconit.hl.gis.entities.MReadBlock;
import com.supconit.hl.gis.services.IBlockService;
import com.supconit.base.entities.Device;
import com.supconit.base.services.DeviceService;

@Controller
@RequestMapping("/threeD")
public class ThreeDControler {

	// 日志打印工具
	Logger logger = LoggerFactory.getLogger(ThreeDControler.class);

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private IBlockService blockService;

	/**
	 * AJAX读取温湿度传感器数据。
	 *
	 * @return
	 */
	@RequestMapping(value = "readETHStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  readETHStatus(ModelMap model) {
		List<Device> devices = deviceService.findByCategory(Constant.DC_REM_ETH);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			for (Device device : devices) {
                String value="";
				List<String> blocks = new ArrayList<String>();
				blocks.add(device.getHpid()+"_H");
				blocks.add(device.getHpid()+"_T");
				List<MReadBlock> mReadBlocks = blockService.readBlocks(blocks);
				for(MReadBlock tmp:mReadBlocks){  
		            if(tmp.getBlock().equals(device.getHpid()+"_H"))value=value+" "+tmp.getValue()+"%";
		            if(tmp.getBlock().equals(device.getHpid()+"_T"))value=value+" "+tmp.getValue()+"℃";
		        }  
				result.put(device.getHpid(), value);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		    e.printStackTrace();
		}
		return result;
	}

}
