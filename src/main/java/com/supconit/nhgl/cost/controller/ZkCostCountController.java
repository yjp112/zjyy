package com.supconit.nhgl.cost.controller;

import com.supconit.common.utils.UtilTool;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.nhgl.base.entities.NhDept;
import com.supconit.nhgl.base.service.NhDeptService;
import com.supconit.nhgl.cost.entities.Cost;
import com.supconit.nhgl.cost.service.CostService;

import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jodd.datetime.JDateTime;
import jodd.util.StringUtil;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 中控大厦用电量报表统计控制类
 * @author yuhuan
 * @日期 2016/07
 */
@Controller
@RequestMapping({"nhgl/cost/zkCostCount"})
public class ZkCostCountController extends BaseControllerSupport
{

  @Autowired
  private NhDeptService deptService;

  @Autowired
  private CostService costService;

  @RequestMapping({"monthElectric"})
  public String monthElectric(ModelMap model, Cost condition) {
    if ((condition == null) || (StringUtil.isEmpty(condition.getStart())) || (StringUtil.isEmpty(condition.getEnd()))) {
      JDateTime now = new JDateTime();
      String LastMonth = now.toString("YYYY-MM");
      if (condition == null) condition = new Cost();
      condition.setStart(LastMonth + "-01");
      condition.setEnd(LastMonth + "-" + now.getMonthLength());
    }
    model.put("condition", condition);

    return "nhgl/cost/count/monthElectric";
  }
  @RequestMapping({"monthWater"})
  public String monthWater(ModelMap model, Cost condition) {
    if ((condition == null) || (StringUtil.isEmpty(condition.getStart())) || (StringUtil.isEmpty(condition.getEnd()))) {
      JDateTime now = new JDateTime();
      String LastMonth = now.toString("YYYY-MM");
      if (condition == null) condition = new Cost();
      condition.setStart(LastMonth + "-01");
      condition.setEnd(LastMonth + "-" + now.getMonthLength());
    }
    
    model.put("condition", condition);

    return "nhgl/cost/count/monthWater";
  }
  @ResponseBody
  @RequestMapping(value={"costlistElectric"}, method={RequestMethod.POST})
  public List<Cost> costListElectric(Cost condition) {
    List<Cost> costList = this.costService.findByConditionEletricity(condition);
    //获取根目录的部门id，通过id查询根目录信息
    NhDept deptList = this.deptService.getById(deptService.findRootId());
    if(costList.size()>0 && costList != null){
    	Cost cost = new Cost();
    	cost.setId(deptList.getId());
    	cost.setPpId(deptList.getpId());
    	cost.setLft(deptList.getLft());
    	cost.setDeptName(deptList.getName());
    	cost.setElectric(costList.get(0).getSumElectric());
    	cost.setElectricCost(costList.get(0).getSumElectricCost());
    	cost.setBitNo("");
    	cost.setDeviceSpec("");
    	cost.setIncremental(new BigDecimal(0));
    	costList.add(cost);
    }
    return costList;
  }

  @ResponseBody
  @RequestMapping(value={"costlistWater"}, method={RequestMethod.POST})
  public List<Cost> costListWater(Cost condition)
  {
	//获取成本核算数据
    List<Cost> costList = this.costService.findByConditionWater(condition);
    //获取根目录的部门id，通过id查询根目录信息
    NhDept deptList = this.deptService.getById(deptService.findRootId());
    if(costList.size()>0 && costList != null){
    	Cost cost = new Cost();
    	cost.setId(deptList.getId());
    	cost.setPpId(deptList.getpId());
    	cost.setLft(deptList.getLft());
    	cost.setDeptName(deptList.getName());
    	cost.setWater(costList.get(0).getSumWater());
    	cost.setWaterCost(costList.get(0).getSumWaterCost());
    	cost.setBitNo("");
    	cost.setDeviceSpec("");
    	cost.setIncremental(new BigDecimal(0));
    	costList.add(cost);
    }
    return costList;
  }

  @ResponseBody
  @RequestMapping(value={"exportElectric"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void exportElectric(Cost condition, HttpServletResponse response)
  {
    try
    {
      URL url = super.getClass().getResource("/templates/costCountElectricTemplate.xls");
      List<Cost> costList = this.costService.findByConditionEletricity(condition);
    //获取根目录的部门id，通过id查询根目录信息
      NhDept deptList = this.deptService.getById(deptService.findRootId());
      if(costList.size()>0 && costList != null){
      	Cost cost = new Cost();
      	cost.setId(deptList.getId());
      	cost.setPpId(deptList.getpId());
      	cost.setLft(deptList.getLft());
      	cost.setDeptName(deptList.getName());
      	cost.setElectric(costList.get(0).getSumElectric());
      	cost.setElectricCost(costList.get(0).getSumElectricCost());
      	costList.add(cost);
      }
      String deptName = "";
      Integer num = Integer.valueOf(0);
      List<Integer> list = new ArrayList<Integer>();
      for (Cost cost : costList) {
        if (!cost.getDeptName().equals(deptName)) {
          if (!"".equals(deptName))
            list.add(num);
          deptName = cost.getDeptName();
        }
        num = Integer.valueOf(num.intValue() + 1);
        if (costList.size() == num.intValue())
          list.add(num);
      }
      UtilTool.isEmptyList(costList);

      String filename = condition.getStart() + "～" + condition.getEnd() + "成本核算-电.xls";
      response.reset();
      response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
      response.setContentType("application/octet-stream");

      Map<String,Object> beanParams=new HashMap<String, Object>();
      beanParams.put("costList", costList);
      beanParams.put("title", condition.getStart() + "～" + condition.getEnd() + " 成本核算-电");
      beanParams.put("aa", Integer.valueOf(costList.size() + 1));
      XLSTransformer transformer = new XLSTransformer();
      Workbook workbook = transformer.transformXLS(url.openStream(), beanParams);
      resetCellFormula((HSSFWorkbook)workbook, list);
      workbook.write(response.getOutputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @ResponseBody
  @RequestMapping(value={"exportWater"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void export(Cost condition, HttpServletResponse response)
  {
    try
    {
      URL url = super.getClass().getResource("/templates/costCountWaterTemplate.xls");
      List<Cost> costList = this.costService.findByConditionWater(condition);
      //获取根目录的部门id，通过id查询根目录信息
      NhDept deptList = this.deptService.getById(deptService.findRootId());
      if(costList.size()>0 && costList != null){
      	Cost cost = new Cost();
      	cost.setId(deptList.getId());
      	cost.setPpId(deptList.getpId());
      	cost.setLft(deptList.getLft());
      	cost.setDeptName(deptList.getName());
      	cost.setWater(costList.get(0).getSumWater());
      	cost.setWaterCost(costList.get(0).getSumWaterCost());
      	costList.add(cost);
      }
      String deptName = "";
      Integer num = Integer.valueOf(0);
      //定义集合，存放需要合并单元格的行数
      List<Integer> list = new ArrayList<Integer>();
      for (Cost cost : costList) {
    	//不等于部门名称
        if (!cost.getDeptName().equals(deptName)) {
          //部门名称不是初始值
          if (!"".equals(deptName))
            list.add(num);
          deptName = cost.getDeptName();
        }
        num = num + 1;
        //判断当前是否是最后一行
        if (costList.size() == num.intValue()) {
          list.add(num);
        }
      }
      UtilTool.isEmptyList(costList);

      String filename = condition.getStart() + "～" + condition.getEnd() + "成本核算-水.xls";
      response.reset();
      response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
      response.setContentType("application/octet-stream");

      Map<String,Object> beanParams=new HashMap<String, Object>();
      beanParams.put("costList", costList);
      beanParams.put("title", condition.getStart() + "～" + condition.getEnd() + " 成本核算-水");
      beanParams.put("aa", Integer.valueOf(costList.size() + 1));
      XLSTransformer transformer = new XLSTransformer();
      Workbook workbook = transformer.transformXLS(url.openStream(), beanParams);
      resetCellFormula((HSSFWorkbook)workbook, list);
      workbook.write(response.getOutputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void resetCellFormula(HSSFWorkbook wb, List<Integer> list)
  {
    HSSFFormulaEvaluator e = new HSSFFormulaEvaluator(wb);
    int sheetNum = wb.getNumberOfSheets();
    for (int i = 0; i < sheetNum; ++i) {
      //获取sheet
      HSSFSheet sheet = wb.getSheetAt(i);

      int startRow = 2;
      for (Integer num : list) {
        CellRangeAddress range = new CellRangeAddress(startRow, num.intValue() + 1, 1, 1);
        sheet.addMergedRegion(range);
        CellRangeAddress range1 = new CellRangeAddress(startRow, num.intValue() + 1, 9, 9);
        sheet.addMergedRegion(range1);
        CellRangeAddress range2 = new CellRangeAddress(startRow, num.intValue() + 1, 10, 10);
        sheet.addMergedRegion(range2);
        startRow = num.intValue() + 2;
      }
    }
  }
}