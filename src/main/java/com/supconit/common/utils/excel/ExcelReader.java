
	/**
*@FileName: ExcelReader.java
*@Description:
*@Author: PeiJie
*@Date: 2014-3-7
*@Copyright: 2011-2014 All rights reserved.
*@Version:
*/
package com.supconit.common.utils.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supconit.base.entities.Device;
import com.supconit.base.entities.GeoArea;
import com.supconit.common.utils.UtilTool;
import com.supconit.hl.systemrule.entities.SystemRule;
import com.supconit.nhgl.basic.areaConfig.entities.AreaConfig;
import com.supconit.nhgl.basic.deptConfig.entities.DeptConfig;

 
/**
 * Excel读取
 */
public class ExcelReader {
	private transient static final Logger log = LoggerFactory
			.getLogger(ExcelReader.class);	
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;
	private InputStream input;
	
	// 读取联系人Excel数据内容
	public List<ExcelParam> readExcelContent(InputStream inputStream) {
		List<ExcelParam> excelParams = new ArrayList<ExcelParam>();
		try {
			input = inputStream;
			fs = new POIFSFileSystem(input);
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum()+1; // 得到总行数
			row = sheet.getRow(0);// 得到标题的内容对象。
			for (int i = 1; i < rowNum; i++) { // 正文内容应该从第二行开始,第一行为表头的标题
				if(log.isInfoEnabled()){
					log.info("读取第["+(i+1)+"]行数据.....");
				}
				ExcelParam excelParam = new ExcelParam();
				row = sheet.getRow(i);
				if(row.getCell((short)0)!=null){
					excelParam.setSkey(getStringCellValue(row.getCell((short) 0)).trim());
					excelParam.setStypeName(getStringCellValue(row.getCell((short) 1)).trim());
					excelParam.setSruleName(getStringCellValue(row.getCell((short) 2)).trim());
					excelParam.setMin(getStringCellValue(row.getCell((short) 3)).trim());
					excelParam.setMax(getStringCellValue(row.getCell((short) 4)).trim());
					excelParam.setValue(getStringCellValue(row.getCell((short) 5)).trim());
					excelParams.add(excelParam);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return excelParams;
	}

	// 读取Device Excel数据内容
	public Map<String,List> readExcelContentDevice(InputStream inputStream) {
		Map<String,List> map = new HashMap<String,List>();
		List<String> lstErrMsg = new ArrayList<String>();
		List<String> lstHpid = new ArrayList<String>();
		List<Device> lstDevice = new ArrayList<Device>();
		
		try {
			input = inputStream;
			fs = new POIFSFileSystem(input);
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum()+1; // 得到总行数
			row = sheet.getRow(0);// 得到标题的内容对象。
			if(rowNum<1){
				lstErrMsg.add("Excel无有效数据。");
			}
			for (int i = 1; i < rowNum; i++) { // 正文内容应该从第二行开始,第一行为表头的标题
				if(log.isInfoEnabled()){
					log.info("读取第["+(i+1)+"]行数据.....");
				}
				
				row = sheet.getRow(i);
				if(row==null 
				||UtilTool.isEmpty(getStringCellValue(row.getCell((short) 0)).trim())
				|| UtilTool.isEmpty(getStringCellValue(row.getCell((short) 1)).trim())
				|| UtilTool.isEmpty(getStringCellValue(row.getCell((short) 2)).trim())){
					lstErrMsg.add("第"+i+"行有必填项没有填写。");
				}
				
				Device device = new Device();
				device.setHpid(getStringCellValue(row.getCell((short) 0)).trim());
				device.setDeviceName(getStringCellValue(row.getCell((short) 1)).trim());
				device.setCategoryName(getStringCellValue(row.getCell((short) 2)).trim());	
				device.setDeviceSpec(getStringCellValue(row.getCell((short) 3)).trim());
				device.setDeviceCode(getStringCellValue(row.getCell((short) 4)).trim());
				device.setBarcode(getStringCellValue(row.getCell((short) 5)).trim());
				device.setAssetsCode(getStringCellValue(row.getCell((short) 6)).trim());
				device.setManagePersonIds(getStringCellValue(row.getCell((short) 7)).trim());
				device.setManagePersonName(getStringCellValue(row.getCell((short) 8)).trim());
				device.setUpdator(getStringCellValue(row.getCell((short) 9)).trim());
				device.setSmallType(getStringCellValue(row.getCell((short) 10)).trim());
				
				if(!UtilTool.isEmpty(device.getHpid())){
					if(lstHpid.contains(device.getHpid())){
						lstErrMsg.add("Excel里此hpid("+device.getHpid()+")重复出现。");
					}else{
						lstHpid.add(device.getHpid());
					}
					lstDevice.add(device);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		map.put("lstErrMsg", lstErrMsg);
		map.put("lstHpid", lstHpid);
		map.put("lstDevice", lstDevice);
		
		return map;
	}
	
	// 读取SystemRule Excel数据内容
	public Map<String,List> readExcelContentSystemRule(InputStream inputStream) {
		Map<String,List> map = new HashMap<String,List>();
		List<String> lstErrMsg = new ArrayList<String>();
		List<String> lstCode = new ArrayList<String>();
		List<String> lstName = new ArrayList<String>();
		List<SystemRule> lstSystemRule = new ArrayList<SystemRule>();
		
		try {
			input = inputStream;
			fs = new POIFSFileSystem(input);
			wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum()+1; // 得到总行数
			row = sheet.getRow(0);// 得到标题的内容对象。
			if(rowNum<=1){
				lstErrMsg.add("Excel无有效数据。");
			}
			for (int i = 1; i < rowNum; i++) { // 正文内容应该从第二行开始,第一行为表头的标题
				if(log.isInfoEnabled()){
					log.info("读取第["+(i+1)+"]行数据.....");
				}
				
				row = sheet.getRow(i);
				if(row==null 
				||UtilTool.isEmpty(getStringCellValue(row.getCell((short) 0)).trim())
				|| UtilTool.isEmpty(getStringCellValue(row.getCell((short) 1)).trim())
				|| UtilTool.isEmpty(getStringCellValue(row.getCell((short) 4)).trim())
				|| UtilTool.isEmpty(getStringCellValue(row.getCell((short) 5)).trim())
				|| UtilTool.isEmpty(getStringCellValue(row.getCell((short) 6)).trim())){
					lstErrMsg.add("第"+i+"行有必填项没有填写。");
				}
				
				SystemRule systemRule = new SystemRule();
				systemRule.setCode(getStringCellValue(row.getCell((short) 0)).trim());
				systemRule.setName(getStringCellValue(row.getCell((short) 1)).trim());
				systemRule.setParentName(getStringCellValue(row.getCell((short) 2)).trim());
				if(UtilTool.isEmpty(getStringCellValue(row.getCell((short) 2)).trim())){
					systemRule.setParentId(0L);
					systemRule.setFullCode(systemRule.getCode());
				}
				systemRule.setSoftCode(getStringCellValue(row.getCell((short) 3)).trim());
				
				if("是".equals(getStringCellValue(row.getCell((short) 4)).trim())){
					systemRule.setHaveOpenStatus(1);
				}else if("否".equals(getStringCellValue(row.getCell((short) 4)).trim())){
					systemRule.setHaveOpenStatus(0);
				}else{
					lstErrMsg.add("第"+i+"行开关状态填写错误。");
				}
				
				if("是".equals(getStringCellValue(row.getCell((short) 5)).trim())){
					systemRule.setHaveAlarmStatus(1);
				}else if("否".equals(getStringCellValue(row.getCell((short) 5)).trim())){
					systemRule.setHaveAlarmStatus(0);
				}else{
					lstErrMsg.add("第"+i+"行报警状态填写错误。");
				}
				
				if("是".equals(getStringCellValue(row.getCell((short) 6)).trim())){
					systemRule.setHaveFaultStatus(1);
				}else if("否".equals(getStringCellValue(row.getCell((short) 6)).trim())){
					systemRule.setHaveFaultStatus(0);
				}else{
					lstErrMsg.add("第"+i+"行故障状态填写错误。");
				}
					
				if(!UtilTool.isEmpty(systemRule.getCode())&&!UtilTool.isEmpty(systemRule.getName())){
//					if(lstCode.contains(systemRule.getCode())){
//						lstErrMsg.add("Excel里此code("+systemRule.getCode()+")重复出现。");
//					}else{
//						lstCode.add(systemRule.getCode());
//					}
					lstCode.add(systemRule.getCode());
//					if(lstName.contains(systemRule.getName())){
//						lstErrMsg.add("Excel里此name("+systemRule.getName()+")重复出现。");
//					}else{
//						lstName.add(systemRule.getName());
//					}
					lstName.add(systemRule.getName());
					lstSystemRule.add(systemRule);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		map.put("lstErrMsg", lstErrMsg);
		map.put("lstCode", lstCode);
		map.put("lstName", lstName);
		map.put("lstSystemRule", lstSystemRule);
		
		return map;
	}
		
		// 读取GeoArea Excel数据内容
		public Map<String,List> readExcelContentGeoArea(InputStream inputStream) {
			Map<String,List> map = new HashMap<String,List>();
			List<String> lstErrMsg = new ArrayList<String>();
			List<GeoArea> lstGeoArea = new ArrayList<GeoArea>();
			
			try {
				input = inputStream;
				fs = new POIFSFileSystem(input);
				wb = new HSSFWorkbook(fs);
				sheet = wb.getSheetAt(0);
				int rowNum = sheet.getLastRowNum()+1; // 得到总行数
				row = sheet.getRow(0);// 得到标题的内容对象。
				if(rowNum<=1){
					lstErrMsg.add("Excel无有效数据。");
				}
				for (int i = 1; i < rowNum; i++) { // 正文内容应该从第二行开始,第一行为表头的标题
					if(log.isInfoEnabled()){
						log.info("读取第["+(i+1)+"]行数据.....");
					}
					
					row = sheet.getRow(i);
					if(row==null 
					||UtilTool.isEmpty(getStringCellValue(row.getCell((short) 0)).trim())
					|| UtilTool.isEmpty(getStringCellValue(row.getCell((short) 1)).trim())
					|| UtilTool.isEmpty(getStringCellValue(row.getCell((short) 2)).trim())
					|| UtilTool.isEmpty(getStringCellValue(row.getCell((short) 4)).trim())){
						lstErrMsg.add("第"+(i+1)+"行有必填项没有填写。");
					}
					
					GeoArea geoArea = new GeoArea();
					String areaCode = getStringCellValue(row.getCell((short) 0)).trim();
					if(areaCode.length()<=10){
						geoArea.setAreaCode(areaCode);
					}else{
						lstErrMsg.add("第"+(i+1)+"行区域编码超过长度。");
					}
					
					String areaName = getStringCellValue(row.getCell((short) 1)).trim();
					if(areaCode.length()<=15){
						geoArea.setAreaName(areaName);
					}else{
						lstErrMsg.add("第"+(i+1)+"行区域名称超过长度。");
					}
					
					String areaTypeName = getStringCellValue(row.getCell((short) 2)).trim();
					if(areaTypeName.equals("园区")){
						geoArea.setAreaType(1l);
					}else if(areaTypeName.equals("办公区")){
						geoArea.setAreaType(2l);
					}else if(areaTypeName.equals("重点区")){
						geoArea.setAreaType(3l);
					}else if(areaTypeName.equals("试验区")){
						geoArea.setAreaType(4l);
					}else{
						if(!areaTypeName.equals("")){
							lstErrMsg.add("第"+(i+1)+"行区域类别非法。");
						}
					}
					
					geoArea.setParentName(getStringCellValue(row.getCell((short) 3)).trim());
					if(UtilTool.isEmpty(getStringCellValue(row.getCell((short) 3)).trim())){
						geoArea.setParentId(0L);
						geoArea.setFullLevelName(geoArea.getAreaName());
					}
					
					String s_sort = getStringCellValue(row.getCell((short) 4)).trim();
					if(!UtilTool.isEmpty(s_sort) && s_sort.length()<=5){
						Long sort = 0L;
						try {
							sort = Long.parseLong(s_sort);
							geoArea.setSort(sort);
						} catch (NumberFormatException e) {
							lstErrMsg.add("第"+(i+1)+"行序号不能为非数字。");
						}
					}else if(!UtilTool.isEmpty(s_sort) && s_sort.length()>5){
						lstErrMsg.add("第"+(i+1)+"行序号超过长度。");
					}
					
					String remark = getStringCellValue(row.getCell((short) 5)).trim();
					if(remark.length()<=100){
						geoArea.setRemark(remark);
					}else{
						lstErrMsg.add("第"+(i+1)+"行备注超过长度。");
					}
					
					if (UtilTool.isEmptyList(lstErrMsg)) {
						lstGeoArea.add(geoArea);
					}
				}
			} catch (FileNotFoundException e) {
				lstErrMsg.add("文件不存在");
				//e.printStackTrace();
			} catch (IOException e) {
				lstErrMsg.add("文件错误,请参照模板文件导入");
				//e.printStackTrace();
			} finally {
				try {
					if (input != null) {
						input.close();
					}
				} catch (IOException e) {
					lstErrMsg.add("文件流异常");
					//e.printStackTrace();
				}
			}
			map.put("lstErrMsg", lstErrMsg);
			map.put("lstGeoArea", lstGeoArea);
			return map;
		}
		
		//读取deptConfig Excel数据内容
		public Map<String,List> readExcelContentDeptConfig(InputStream inputStream) {
			Map<String,List> map = new HashMap<String,List>();
			List<String> lstErrMsg = new ArrayList<String>();
			List<DeptConfig> lstDeptConfig = new ArrayList<DeptConfig>();
			
			try {
				input = inputStream;
				fs = new POIFSFileSystem(input);
				wb = new HSSFWorkbook(fs);
				sheet = wb.getSheetAt(0);
				int rowNum = sheet.getLastRowNum()+1; // 得到总行数
				row = sheet.getRow(0);// 得到标题的内容对象。
				if(rowNum<=1){
					lstErrMsg.add("Excel无有效数据。");
				}
				for (int i = 1; i < rowNum; i++) { // 正文内容应该从第二行开始,第一行为表头的标题
					if(log.isInfoEnabled()){
						log.info("读取第["+(i+1)+"]行数据.....");
					}
					
					row = sheet.getRow(i);
					if(row==null 
					||UtilTool.isEmpty(getStringCellValue(row.getCell((short) 0)).trim())
					|| UtilTool.isEmpty(getStringCellValue(row.getCell((short) 1)).trim())){
						lstErrMsg.add("第"+(i+1)+"行有必填项没有填写。");
					}
					
					DeptConfig deptConfig = new DeptConfig();
					String deptCode = getStringCellValue(row.getCell((short) 0)).trim();
					if(deptCode.length()<=10){
						deptConfig.setDeptCode(deptCode);
						if(null != deptCode && deptCode.equals("ROOT")){
							deptConfig.setIsSum(1);
							String nhType = getStringCellValue(row.getCell((short) 5)).trim();
							if(nhType.length()<=2 && nhType.length()>=0){
								if(nhType.equals("电")){//加
									deptConfig.setNhType(1);
								}else if(nhType.equals("水")){
									deptConfig.setNhType(2);
								}else if(nhType.equals("蒸气")){
									deptConfig.setNhType(3);
								}else if(nhType.equals("能量")){
									deptConfig.setNhType(4);
								}else{
									lstErrMsg.add("第"+(i+1)+"行‘能耗种类’为非法字符。");
								}
							}else{
								lstErrMsg.add("第"+(i+1)+"行‘能耗种类’为非法字符。");
							}
						}
					}else{
						lstErrMsg.add("第"+(i+1)+"行‘部门编码’超过长度。");
					}
					
					String isSum = getStringCellValue(row.getCell((short) 1)).trim();
					if(isSum.length()==1){
						if(isSum.equals("是")){//是
							deptConfig.setIsSum(1);
						}else if(isSum.equals("否")){//否
							deptConfig.setIsSum(0);
							
							String bitNo = getStringCellValue(row.getCell((short) 2)).trim();
							if(bitNo.length()<=50){
								deptConfig.setBitNo(bitNo);
							}else{
								lstErrMsg.add("第"+(i+1)+"行‘表位号’超过长度。");
							}
							
							String ftxs = getStringCellValue(row.getCell((short) 3)).trim();
							if(!UtilTool.isEmpty(ftxs)){
								Long sort = 0L;
								try {
									deptConfig.setFtxs(Float.parseFloat(ftxs));
								} catch (NumberFormatException e) {
									lstErrMsg.add("第"+(i+1)+"行‘分摊系数’须为整数或小数。");
								}
							}
							
							String ruleFlag = getStringCellValue(row.getCell((short) 4)).trim();
							if(ruleFlag.length()==1){
								if(ruleFlag.equals("加")){//加
									deptConfig.setRuleFlag(1);
								}else if(ruleFlag.equals("减")){//减
									deptConfig.setRuleFlag(0);
								}else{
									lstErrMsg.add("第"+(i+1)+"行‘算法规则标志’为非法字符。");
								}
							}else{
								lstErrMsg.add("第"+(i+1)+"行‘算法规则标志’为非法字符。");
							}
							
							String nhType = getStringCellValue(row.getCell((short) 5)).trim();
							if(nhType.length()<=2 && nhType.length()>=0){
								if(nhType.equals("电")){//加
									deptConfig.setNhType(1);
								}else if(nhType.equals("水")){
									deptConfig.setNhType(2);
								}else if(nhType.equals("蒸气")){
									deptConfig.setNhType(3);
								}else if(nhType.equals("能量")){
									deptConfig.setNhType(4);
								}else{
									lstErrMsg.add("第"+(i+1)+"行‘能耗种类’为非法字符。");
								}
							}else{
								lstErrMsg.add("第"+(i+1)+"行‘能耗种类’为非法字符。");
							}
						}else{
							lstErrMsg.add("第"+(i+1)+"行‘该部门值是否通过下级部门汇总生成’为非法字符。");
						}
					}else if(isSum.length() > 1){
						lstErrMsg.add("第"+(i+1)+"行‘该部门值是否通过下级部门汇总生成’为非法字符。");
					}
					
					String memo = getStringCellValue(row.getCell((short) 6)).trim();
					if(memo.length()<=100){
						deptConfig.setMemo(memo);
					}else{
						lstErrMsg.add("第"+(i+1)+"行‘备注说明’超过长度。");
					}
					
					if (UtilTool.isEmptyList(lstErrMsg)) {
						lstDeptConfig.add(deptConfig);
					}
				}
			} catch (FileNotFoundException e) {
				lstErrMsg.add("文件不存在");
			} catch (IOException e) {
				lstErrMsg.add("文件错误,请参照模板文件导入");
			} finally {
				try {
					if (input != null) {
						input.close();
					}
				} catch (IOException e) {
					lstErrMsg.add("文件流异常");
				}
			}
			map.put("lstErrMsg", lstErrMsg);
			map.put("lstDeptConfig", lstDeptConfig);
			return map;
		}
		
		//读取areaConfig Excel数据内容
				public Map<String,List> readExcelContentAreaConfig(InputStream inputStream) {
					Map<String,List> map = new HashMap<String,List>();
					List<String> lstErrMsg = new ArrayList<String>();
					List<AreaConfig> lstAreaConfig = new ArrayList<AreaConfig>();
					
					try {
						input = inputStream;
						fs = new POIFSFileSystem(input);
						wb = new HSSFWorkbook(fs);
						sheet = wb.getSheetAt(0);
						int rowNum = sheet.getLastRowNum()+1; // 得到总行数
						row = sheet.getRow(0);// 得到标题的内容对象。
						if(rowNum<=1){
							lstErrMsg.add("Excel无有效数据。");
						}
						for (int i = 1; i < rowNum; i++) { // 正文内容应该从第二行开始,第一行为表头的标题
							if(log.isInfoEnabled()){
								log.info("读取第["+(i+1)+"]行数据.....");
							}
							
							row = sheet.getRow(i);
							if(row==null 
									||UtilTool.isEmpty(getStringCellValue(row.getCell((short) 0)).trim())
									|| UtilTool.isEmpty(getStringCellValue(row.getCell((short) 1)).trim())){
								lstErrMsg.add("第"+(i+1)+"行有必填项没有填写。");
							}
							
							AreaConfig areaConfig = new AreaConfig();
							String areaCode = getStringCellValue(row.getCell((short) 0)).trim();
							if(areaCode.length()<=10){
								areaConfig.setAreaCode(areaCode);
								if(null != areaCode && areaCode.equals("ROOT")){
									areaConfig.setIsSum(1);
									String nhType = getStringCellValue(row.getCell((short) 5)).trim();
									if(nhType.length()<=2 && nhType.length()>=0){
										if(nhType.equals("电")){//加
											areaConfig.setNhType(1);
										}else if(nhType.equals("水")){
											areaConfig.setNhType(2);
										}else if(nhType.equals("蒸气")){
											areaConfig.setNhType(3);
										}else if(nhType.equals("能量")){
											areaConfig.setNhType(4);
										}else{
											lstErrMsg.add("第"+(i+1)+"行‘能耗种类’为非法字符。");
										}
									}else{
										lstErrMsg.add("第"+(i+1)+"行‘能耗种类’为非法字符。");
									}
								}
							}else{
								lstErrMsg.add("第"+(i+1)+"行‘部门编码’超过长度。");
							}
							
							String isSum = getStringCellValue(row.getCell((short) 1)).trim();
							if(isSum.length()==1){
								if(isSum.equals("是")){//是
									areaConfig.setIsSum(1);
								}else if(isSum.equals("否")){//否
									areaConfig.setIsSum(0);
									
									String bitNo = getStringCellValue(row.getCell((short) 2)).trim();
									if(bitNo.length()<=50){
										areaConfig.setBitNo(bitNo);
									}else{
										lstErrMsg.add("第"+(i+1)+"行‘表位号’超过长度。");
									}
									
									String ftxs = getStringCellValue(row.getCell((short) 3)).trim();
									if(!UtilTool.isEmpty(ftxs)){
										Long sort = 0L;
										try {
											areaConfig.setFtxs(Float.parseFloat(ftxs));
										} catch (NumberFormatException e) {
											lstErrMsg.add("第"+(i+1)+"行‘分摊系数’须为整数或小数。");
										}
									}
									
									String ruleFlag = getStringCellValue(row.getCell((short) 4)).trim();
									if(ruleFlag.length()==1){
										if(ruleFlag.equals("加")){//加
											areaConfig.setRuleFlag(1);
										}else if(ruleFlag.equals("减")){//减
											areaConfig.setRuleFlag(0);
										}else{
											lstErrMsg.add("第"+(i+1)+"行‘算法规则标志’为非法字符。");
										}
									}else{
										lstErrMsg.add("第"+(i+1)+"行‘算法规则标志’为非法字符。");
									}
									
									String nhType = getStringCellValue(row.getCell((short) 5)).trim();
									if(nhType.length()<=2 && nhType.length()>=0){
										if(nhType.equals("电")){//加
											areaConfig.setNhType(1);
										}else if(nhType.equals("水")){
											areaConfig.setNhType(2);
										}else if(nhType.equals("蒸气")){
											areaConfig.setNhType(3);
										}else if(nhType.equals("能量")){
											areaConfig.setNhType(4);
										}else{
											lstErrMsg.add("第"+(i+1)+"行‘能耗种类’为非法字符。");
										}
									}else{
										lstErrMsg.add("第"+(i+1)+"行‘能耗种类’为非法字符。");
									}
								}else{
									lstErrMsg.add("第"+(i+1)+"行‘该部门值是否通过下级部门汇总生成’为非法字符。");
								}
							}else if(isSum.length() > 1){
								lstErrMsg.add("第"+(i+1)+"行‘该部门值是否通过下级部门汇总生成’为非法字符。");
							}
							
							String memo = getStringCellValue(row.getCell((short) 6)).trim();
							if(memo.length()<=100){
								areaConfig.setMemo(memo);
							}else{
								lstErrMsg.add("第"+(i+1)+"行‘备注说明’超过长度。");
							}
							
							if (UtilTool.isEmptyList(lstErrMsg)) {
								lstAreaConfig.add(areaConfig);
							}
						}
					} catch (FileNotFoundException e) {
						lstErrMsg.add("文件不存在");
					} catch (IOException e) {
						lstErrMsg.add("文件错误,请参照模板文件导入");
					} finally {
						try {
							if (input != null) {
								input.close();
							}
						} catch (IOException e) {
							lstErrMsg.add("文件流异常");
						}
					}
					map.put("lstErrMsg", lstErrMsg);
					map.put("lstAreaConfig", lstAreaConfig);
					return map;
				}

	private String getStringCellValue(HSSFCell cell) {// 获取单元格数据内容为字符串类型的数据
		String strCell = "";
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			//strCell = String.valueOf(cell.getNumericCellValue());
			strCell = new BigDecimal(cell.getNumericCellValue()).toPlainString();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		//strCell = cell.getStringCellValue();
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		return strCell;
	}
	
	
	private String getDateCellValue(HSSFCell cell) {// 获取单元格数据内容为日期类型的数据
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
						+ "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}
	public class ExcelParam {

		private String skey;// 点位
		private String stype;//  类别
		private String stypeName;//类别名称
		private String srule;//   规则
		private String sruleName;//规则名称
		private String min;//   区域最小值
		private String max;//   区域最大值
		private String value;//  初始值
		
		
		
		public String getStypeName() {
			return stypeName;
		}
		public void setStypeName(String stypeName) {
			this.stypeName = stypeName;
		}
		public String getSruleName() {
			return sruleName;
		}
		public void setSruleName(String sruleName) {
			this.sruleName = sruleName;
		}
		public String getSkey() {
			return skey;
		}
		public void setSkey(String skey) {
			this.skey = skey;
		}
		public String getStype() {
			return stype;
		}
		public void setStype(String stype) {
			this.stype = stype;
		}
		public String getSrule() {
			return srule;
		}
		public void setSrule(String srule) {
			this.srule = srule;
		}
		public String getMin() {
			return min;
		}
		public void setMin(String min) {
			this.min = min;
		}
		public String getMax() {
			return max;
		}
		public void setMax(String max) {
			this.max = max;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		
		
	}
}
