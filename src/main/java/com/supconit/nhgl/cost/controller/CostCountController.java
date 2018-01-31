
package com.supconit.nhgl.cost.controller;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.UtilTool;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.nhgl.cost.entities.Cost;
import com.supconit.nhgl.cost.service.CostService;

import jodd.datetime.JDateTime;
import jodd.util.StringUtil;
import net.sf.jxls.transformer.XLSTransformer;

@Controller
@RequestMapping("nhgl/cost/costCount")
public class CostCountController extends BaseControllerSupport {


/*    @Autowired
    private WaterService waterService;

    @Autowired
    private EnergyAnalyseByDeptService energyAnalyseByDeptService;*/

    @Autowired
    private DepartmentService deptService;
    @Autowired
    private CostService costService;
//    @Value("${deployYear}")
//    private Integer deployYear;

    @RequestMapping("go")
    public String go(ModelMap model) {
        return "nhgl/cost/count/month";
    }

    @RequestMapping("month")
    public String month(ModelMap model, Cost condition) {
        Date today = new Date();
        if (null == condition || StringUtil.isEmpty(condition.getStart()) || StringUtil.isEmpty(condition.getEnd())) {
            JDateTime now = new JDateTime();
            String LastMonth = now.toString("YYYY-MM");
            if (null == condition) condition = new Cost();
            condition.setStart(LastMonth + "-01");
            condition.setEnd(LastMonth + "-" + now.getMonthLength());
        }
        List<Department> deptList = deptService.findByPid(10001L);
        List<Cost> costList = costService.findByCondition(condition);
        model.put("listBuildings", deptList);

        model.put("condition", condition);
        model.put("costList", costList);
        model.put("sumCost", costList.get(0));

        return "nhgl/cost/count/month";
    }
    @ResponseBody
    @RequestMapping(value = "costlist", method = RequestMethod.POST)
    public List<Cost> costList(Cost condition){
    	List<Cost> costList = costService.findByCondition(condition);
		return  costList;
    	
    }
    
    
/*    private List<GeoArea> sort(List<GeoArea> listBuildings ){
    	List<GeoArea> lstResult = new ArrayList<GeoArea>();
    	if(UtilTool.isEmptyList(listBuildings)){
    		lstResult =  listBuildings;
    	}else{
	    	String[] names = new String[listBuildings.size()];
	    	for(int i =0;i<listBuildings.size();i++){
	    		names[i]=listBuildings.get(i).getAreaName();
	    	}
	    	Comparator cmp = Collator.getInstance(Locale.CHINA);
	    	Arrays.sort(names,cmp);
	    	for(String s:names){
	    		for(GeoArea g :listBuildings){
	    			if(s.equals(g.getAreaName())){
	    				lstResult.add(g);
	    				break;
	    			}
	    		}
	    	}
    	}
    	return lstResult;
    }*/

    /**
     * @param
     * @方法描述:导出excel
     */
    @ResponseBody
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public void export(Cost condition, HttpServletResponse response) {
        try {
            URL url = this.getClass().getResource("/templates/costCountTemplate.xls");
            List<Cost> costList = costService.findByCondition(condition);

            if (!UtilTool.isEmptyList(costList)) {}
            //===========================
            String filename = condition.getStart() + "～" + condition.getEnd() + "成本核算.xls";
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
            response.setContentType("application/octet-stream");
            //excel.writeToStream(response.getOutputStream());
            Map<String,Object> beanParams=new HashMap<String, Object>();
            beanParams.put("costList", costList);
            beanParams.put("title", condition.getStart() + "～" + condition.getEnd() + " 成本核算");
            beanParams.put("aa", costList.size()+1);
            XLSTransformer transformer = new XLSTransformer();
            Workbook workbook=transformer.transformXLS(url.openStream(), beanParams);
            //resetCellFormula((HSSFWorkbook)workbook);
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    /** 
   *  
   * 重新设置单元格计算公式 
   *  
   * */  
   public static void resetCellFormula(HSSFWorkbook wb) {  
   HSSFFormulaEvaluator e = new HSSFFormulaEvaluator(wb);  
   int sheetNum = wb.getNumberOfSheets();  
   for (int i = 0; i < sheetNum; i++) {  
       HSSFSheet sheet = wb.getSheetAt(i);  
       int rows = sheet.getLastRowNum() + 1;  
       for (int j = 0; j < rows; j++) {  
           HSSFRow row = sheet.getRow(j);  
           if (row == null)  
               continue;  
           int cols = row.getLastCellNum();  
           for (int k = 0; k < cols; k++) {  
               HSSFCell cell = row.getCell(k);  
               if(cell!=null){
            	   //System.out.println("cell["+j+","+k+"]=:"+cell.getCellType());  
               }else{
            	   continue;  
               }  
               if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {  
                   cell.setCellFormula(cell.getCellFormula());  
                  // System.out.println("----公式："+cell.getCellFormula());  
                   cell=e.evaluateInCell(cell);  
                   //System.out.println("-----------"+cell.getNumericCellValue());  
               }  
           }  
       }  
   }  
}
   }