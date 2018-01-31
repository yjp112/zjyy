package com.supconit.nhgl.cost.controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.nhgl.cost.entities.RealTimeCost;
import com.supconit.nhgl.cost.service.CostService;
import com.supconit.nhgl.utils.GraphUtils;

/**
 * 中控大厦用电量报表统计控制类
 * @author yuhuan
 * @日期 2016/06
 */
@Controller
@RequestMapping("nhgl/cost/zkCost")
public class ZkCostController {
	
	@Autowired
    private CostService costService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping("zk_list")
	public String zkList(ModelMap model,Integer nhType) {
    	RealTimeCost condition=new RealTimeCost();
		JDateTime jdt = new JDateTime();
		condition.setStartTime(GraphUtils.getDateString(jdt.getYear(), jdt.getMonth(), 1));
		condition.setEndTime(jdt.toString("YYYY-MM-DD"));
		model.put("condition", condition);
		model.put("nhType", nhType==null? 1:nhType);
		return "nhgl/cost/zkCost/collect_list";
	}
    
    @ResponseBody
    @RequestMapping(value = "zkExp",method = RequestMethod.POST)
    public void zkExp(RealTimeCost cost,HttpServletResponse response) {
    	String start = cost.getStartTime();
    	String end = cost.getEndTime();
    	Map<String,RealTimeCost> mapCost = new HashMap<String,RealTimeCost>();
    	RealTimeCost condition = new RealTimeCost();
    	condition.setEndTime(end + " 23:30:01");
    	Calendar cal = Calendar.getInstance(); 
    	Date ends = null;
		try {
			ends = sdf.parse(end);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
    	cal.setTime(ends);
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	condition.setEndTime1(sdf.format(cal.getTime()) + " 00:00:01");
    	
    	condition.setStartTime(start + " 00:00:01");
    	condition.setStartTime1(start + " 00:30:01");
    	
    	List<RealTimeCost> zkList = costService.findZkCost(condition);
    	List<RealTimeCost> zkCostList = new ArrayList<RealTimeCost>();//组装有效的列表
    	
    	try {
            URL url = this.getClass().getResource("/templates/zkCostCountTemplate.xls");
            POIFSFileSystem fs = new POIFSFileSystem(url.openStream());
            HSSFWorkbook workbook=new HSSFWorkbook(fs);
            HSSFSheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            Set<String> bitNos = new HashSet<String>();
            for (int i = 1; i < rowNum; i++) {
            	HSSFRow row = sheet.getRow(i);
            	HSSFCell cell = row.getCell(2);
				if(null == row || null == cell){
					continue;
				}else{
					bitNos.add(cell.getStringCellValue());
				}
			}
            for (RealTimeCost realTimeCount : zkList) {
            	String bitNo = realTimeCount.getBitNo();
            	if(bitNos.contains(bitNo)){
            		mapCost.put(bitNo, realTimeCount);
            	}
    		}
            
            for (int i = 1; i < rowNum; i++) {
            	HSSFRow row = sheet.getRow(i);
            	HSSFCell cell = row.getCell(2);
            	if(null == row || null == cell){
					continue;
				}else{
					String value = cell.getStringCellValue();
					if(mapCost.containsKey(value)){
						RealTimeCost realTimeCount = mapCost.get(value);
						String deptName = row.getCell(1).getStringCellValue();
						String box = row.getCell(3).getStringCellValue();
						String purpose = row.getCell(4).getStringCellValue();
						realTimeCount.setDeptName(deptName);
						realTimeCount.setBox(box);
						realTimeCount.setPurpose(purpose);
						zkCostList.add(realTimeCount);
					}
				}
			}
            
            String filename = start + "～" + end + "成本核算_中控.xls";
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
            response.setContentType("application/octet-stream");
            XLSTransformer transformer = new XLSTransformer();
            Map<String,Object> beanParams=new HashMap<String, Object>();
            beanParams.put("zkCostList", zkCostList);
            beanParams.put("title",  filename);
            url = this.getClass().getResource("/templates/zkCostOutPutTemplate.xls");
            Workbook workbooks = transformer.transformXLS(url.openStream(), beanParams);
            workbooks.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
