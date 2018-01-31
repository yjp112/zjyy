package com.supconit.nhgl.basic.deptConfig.controller;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import net.sf.jxls.transformer.XLSTransformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.utils.Constant;
import com.supconit.common.utils.StringUtil;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.utils.excel.ExcelImportHelper;
import com.supconit.common.utils.excel.ExcelReader;
import com.supconit.common.utils.excel.pojo.DeviceImportPojo;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.mvc.utils.UploadUtils;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadRenamePolicy;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadedFile;
import com.supconit.nhgl.base.entities.NhDept;
import com.supconit.nhgl.base.service.NhDeptService;
import com.supconit.nhgl.basic.deptConfig.entities.DeptConfig;
import com.supconit.nhgl.basic.deptConfig.service.DeptConfigService;
import com.supconit.nhgl.cost.entities.YearEletricityCost;
import com.supconit.nhgl.utils.OrganizationUtils;
@Controller
@RequestMapping("nhgl/basic/deptConfig")
public class DeptConfigController extends BaseControllerSupport{
	private transient static final Logger log = LoggerFactory.getLogger(DeptConfigController.class);
	@Autowired
	private DeptConfigService deptConfigService;
	@Autowired
	private NhDeptService nhDeptService;
	@Value("${file.tmpsave}")
	private String tmpPath;
	@Value("${electric_category}")
	private String dcategoryCode;
	@Value("${water_category}")
	private String scategoryCode;
	@Value("${gas_category}")
	private String qcategoryCode;
	@Value("${energy_category}")
	private String encategoryCode;
	@RequestMapping("list")
	public String list(ModelMap model,Integer nhType){
		List<NhDept> treeList=nhDeptService.findAll();
		model.put("treeList", treeList);
		model.put("nhType", nhType);
		return "nhgl/basic/deptConfig/list";
	}
	
	
	@ResponseBody
	@RequestMapping("pager")
	public Pageable<DeptConfig> pager(Pagination<DeptConfig> pager, DeptConfig condition){
		if(condition.getDeptId()==null){
	 		condition.setDeptId(0l);
		}
		deptConfigService.findByCondition(pager, condition);
		
		Long rootId = nhDeptService.findRootId();
		for(int i=0;i<pager.size();i++){//设置部门全路径
			if(null!= rootId && pager.get(i).getDeptId().longValue()!=rootId.longValue())
				pager.get(i).setDeptName(OrganizationUtils.getFullDeptNameByDeptId(pager.get(i).getDeptId()));
		}
		return pager;
	}
	
	@RequestMapping("edit")
	public String edit(@RequestParam(required = false) Boolean viewOnly,ModelMap model,DeptConfig condition){
		String devName="";
		if(condition.getId()!=null){   //修改
			DeptConfig deptConfig=deptConfigService.findById(condition.getId(),condition.getNhType());
			//部门叠加部门
			deptConfig.setSubLeftConfigList(deptConfigService.findByDeptIdAndRule(deptConfig.getDeptId(),Constant.RULE_FLAG_PLUS,deptConfig.getNhType()));
			//部门被减区
			deptConfig.setSubRightConfigList(deptConfigService.findByDeptIdAndRule(deptConfig.getDeptId(),Constant.RULE_FLAG_DECREASE,deptConfig.getNhType()));
			model.put("deptConfig", deptConfig);
		}else{
			model.put("deptConfig", condition);
		}
		model.put("viewOnly", viewOnly);
		if(condition.getNhType().equals(1)){
			devName="电表";
		}else if(condition.getNhType().equals(2)){
			devName="水表";
		}else if(condition.getNhType().equals(3)){
			devName="蒸汽表";
		}else{
			devName="能量表";
		}
		model.put("devName", devName);
		return "nhgl/basic/deptConfig/edit";
	}
	@RequestMapping("check")
	public String check(@RequestParam(required = false) Boolean viewOnly,ModelMap model,DeptConfig condition){
		String devName="";
		String category="";
		Integer nhtype=1;
		if(condition.getNhType().equals(1)){
			category=dcategoryCode;
			nhtype=Constant.NH_TYPE_D;
			devName="电表";
		}else if(condition.getNhType().equals(2)){
			category=scategoryCode;
			nhtype=Constant.NH_TYPE_S;
			devName="水表";
		}else if(condition.getNhType().equals(3)){
			category=qcategoryCode;
			nhtype=Constant.NH_TYPE_Q;
			devName="蒸汽表";
		}else{
			category=encategoryCode;
			nhtype=Constant.NH_TYPE_EN;
			devName="能量表";
		}
		List<DeptConfig> deptConfig=deptConfigService.findErrorIsSum(String.valueOf(nhtype));
		Map<Long,String> allDept = deptConfigService.findAllDept();
		deptConfig=errorIsSum(deptConfig,allDept);
		model.put("deptConfiglst", deptConfig);
		model.put("deviceName", devName);
		model.put("category", category);
		model.put("nhtype", nhtype);
		return "nhgl/basic/deptConfig/check";
	}
	@ResponseBody
	@RequestMapping("unpager")
	public Pageable<DeptConfig> unpager(Pagination<DeptConfig> pager, DeptConfig condition){
		//未使用的device
		deptConfigService.findUnusedDevice(pager, condition);
		return pager;
	}
	@ResponseBody
	@RequestMapping("repeatpager")
	public Pageable<DeptConfig> repeatpager(Pagination<DeptConfig> pager, DeptConfig condition){
		//加减重复的device
		deptConfigService.findRepeatDevice(pager, condition);
		return pager;
	}
	@ResponseBody
	@RequestMapping("ftxspager")
	public Pageable<DeptConfig> ftxspager(Pagination<DeptConfig> pager, DeptConfig condition){
		//分摊系数>1的device
		deptConfigService.findErrorFtxs(pager, condition);
		return pager;
	}
	
	@ResponseBody
	@RequestMapping("save")
	public ScoMessage save(DeptConfig deptConfig){
		if(deptConfig.getId()==null){//添加
			deptConfigService.insert(deptConfig);
		}else{//修改
			deptConfigService.update(deptConfig); 
		}
		return ScoMessage.success("nhgl/basic/deptConfig/list?nhType="+deptConfig.getNhType(), "操作成功。");
	}
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ScoMessage delete(DeptConfig deptConfig){
		deptConfigService.deleteByDeptId(deptConfig.getDeptId(),deptConfig.getNhType().toString());
		return ScoMessage.success("nhgl/basic/deptConfig/list?nhType="+deptConfig.getNhType(), "删除成功。");
	}
	@ResponseBody
	@RequestMapping(value = "check", method = RequestMethod.POST)
	public boolean check(DeptConfig deptConfig){
		boolean state=false;
		if(null!=deptConfig.getDeptId()){
			int count=deptConfigService.countFindAll(deptConfig);
			if(count>0)
				state=true;
		}
		return state;
	}
	
	@RequestMapping(value = "preImp")
	public String preImp(ModelMap model) {
		return "nhgl/basic/deptConfig/deptConfig_imp";
	}
	
	@ResponseBody
	@RequestMapping(value = "importByFileName", method = RequestMethod.POST)     
	public ScoMessage importByFileName( @RequestParam("filename") String filename) {	

		List<String> lstErrMsg = new ArrayList<String>();
	
		String msg = "";
		try {
			Long befor = System.currentTimeMillis();
			if (!StringUtil.isNullOrEmpty(filename)) {
				File excel =  new File("D:\\ccyq_temp_file\\"+filename);
				InputStream in = new FileInputStream(excel); 
				
//				Map<String, List> map = new ExcelReader().readExcelContentDeptConfig(in);
//				lstErrMsg = map.get("lstErrMsg");
//				List<DeptConfig> result = map.get("lstDeptConfig");
				Map<String,Integer> mapDevices = deptConfigService.findDevicesMapWithOutRoot();
				ExcelImportHelper<DeptConfig> deptPojo = new ExcelImportHelper(DeptConfig.class);
				Workbook book = Workbook.getWorkbook(in);
				List<DeptConfig> result = new ArrayList<DeptConfig>();
				int d = -1;
				int w = -1;
				int s = -1;
				int e = -1;
				String[] sheetNames = book.getSheetNames();
				for (int i = 0; i < sheetNames.length; i++) {
					if(sheetNames[i].equals("电")){
						d=i;
					}else if(sheetNames[i].equals("水")){
						w=i;
					}else if(sheetNames[i].equals("蒸汽")){
						s=i;
					}else if(sheetNames[i].equals("能量")){
						e=i;
					}
				}
				if(d!=-1){
					List<DeptConfig> d_list = deptPojo.importExcel(book,new int[]{d});
					for (DeptConfig deptConfig : d_list) {
						deptConfig.setNhType(1);
						result.add(deptConfig);
					}
				}
				if(w!=-1){
					List<DeptConfig> w_list = deptPojo.importExcel(book,new int[]{w});
					for (DeptConfig deptConfig : w_list) {
						deptConfig.setNhType(2);
						result.add(deptConfig);
					}
				}
				if(s!=-1){
					List<DeptConfig> s_list = deptPojo.importExcel(book,new int[]{s});
					for (DeptConfig deptConfig : s_list) {
						deptConfig.setNhType(3);
						result.add(deptConfig);
					}
				}
				if(e!=-1){
				List<DeptConfig> e_list = deptPojo.importExcel(book,new int[]{e});
					for (DeptConfig deptConfig : e_list) {
						deptConfig.setNhType(4);
						result.add(deptConfig);
					}
				}
				for (DeptConfig deptConfig : result) {
					if(StringUtils.isNotEmpty(deptConfig.getBitNo())
							&& deptConfig.getIsSum().equals(1)){
						lstErrMsg.add("当表位号非空时,部门编码为‘"+deptConfig.getDeptCode()+"’的下级部门生成应该为'否'");
					}
					Integer nhType = mapDevices.get(deptConfig.getBitNo());
					if(null!=nhType) deptConfig.setNhType(nhType);
				}
				
				Map<String,String> maps = new HashMap<String,String>();
				maps = importExcelInOrder(lstErrMsg, result);
				msg = maps.get("msg");
				if(StringUtils.isEmpty(msg)){
					Long after = System.currentTimeMillis();
					msg = "导入结束，此次导入" + maps.get("size") + "条数据,共耗时：" + (after - befor) + "毫秒";
					System.out.println(msg);
				}else{
					msg = "error,导入失败,原因:<br>" + msg;
				}				
			}else{
				
			}

		} catch (Exception e) {
			msg = "error,导入失败,原因:<br>" + e.getMessage();
			log.error("导入部门能耗出错");
			return ScoMessage.fail(msg);
		} 
		return ScoMessage.success(msg);
	}   
	
	@RequestMapping(value = "impDeptConfig",method = RequestMethod.POST)
	public void impDeptConfig(HttpServletRequest request,HttpServletResponse response){
		List<String> lstErrMsg = new ArrayList<String>();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		UploadedFile saveFile = null;
		try {
			MultiValueMap<String, UploadedFile> fileMap = UploadUtils.uploadFiles(request, tmpPath, Integer.MAX_VALUE,
					"UTF-8", new UploadRenamePolicy() {
						@Override
						public String rename(String s, String s1) {
							String postfix = s1.substring(s1.indexOf(".") + 1, s1.length());
							return UUID.randomUUID().toString() + "." + postfix;
						}
					}, null);

			List<UploadedFile> file = fileMap.get("file");
			if (file != null) {
				saveFile = file.get(0);
			}
		} catch (Exception e) {
			log.error("上传出错");
			lstErrMsg.add("上传出错<br>");
		}
		String msg = "";
		try {
			Long befor = System.currentTimeMillis();
			if (saveFile != null && saveFile.getFile() != null) {
				File excel = saveFile.getFile();
				InputStream in = new FileInputStream(excel); 
				
//				Map<String, List> map = new ExcelReader().readExcelContentDeptConfig(in);
//				lstErrMsg = map.get("lstErrMsg");
//				List<DeptConfig> result = map.get("lstDeptConfig");
				Map<String,Integer> mapDevices = deptConfigService.findDevicesMapWithOutRoot();
				ExcelImportHelper<DeptConfig> deptPojo = new ExcelImportHelper(DeptConfig.class);
				Workbook book = Workbook.getWorkbook(in);
				List<DeptConfig> result = new ArrayList<DeptConfig>();
				int d = -1;
				int w = -1;
				int s = -1;
				int e = -1;
				String[] sheetNames = book.getSheetNames();
				for (int i = 0; i < sheetNames.length; i++) {
					if(sheetNames[i].equals("电")){
						d=i;
					}else if(sheetNames[i].equals("水")){
						w=i;
					}else if(sheetNames[i].equals("蒸汽")){
						s=i;
					}else if(sheetNames[i].equals("能量")){
						e=i;
					}
				}
				if(d!=-1){
					List<DeptConfig> d_list = deptPojo.importExcel(book,new int[]{d});
					for (DeptConfig deptConfig : d_list) {
						deptConfig.setNhType(1);
						if(StringUtils.isNotEmpty(deptConfig.getDeptCode())) result.add(deptConfig);
					}
				}
				if(w!=-1){
					List<DeptConfig> w_list = deptPojo.importExcel(book,new int[]{w});
					for (DeptConfig deptConfig : w_list) {
						deptConfig.setNhType(2);
						if(StringUtils.isNotEmpty(deptConfig.getDeptCode())) result.add(deptConfig);
					}
				}
				if(s!=-1){
					List<DeptConfig> s_list = deptPojo.importExcel(book,new int[]{s});
					for (DeptConfig deptConfig : s_list) {
						deptConfig.setNhType(3);
						if(StringUtils.isNotEmpty(deptConfig.getDeptCode())) result.add(deptConfig);
					}
				}
				if(e!=-1){
				List<DeptConfig> e_list = deptPojo.importExcel(book,new int[]{e});
					for (DeptConfig deptConfig : e_list) {
						deptConfig.setNhType(4);
						if(StringUtils.isNotEmpty(deptConfig.getDeptCode())) result.add(deptConfig);
					}
				}
				for (DeptConfig deptConfig : result) {
					if(StringUtils.isNotEmpty(deptConfig.getBitNo())
							&& deptConfig.getIsSum().equals(1)){
						lstErrMsg.add("当表位号非空时,部门编码为‘"+deptConfig.getDeptCode()+"’的下级部门生成应该为'否'");
					}
					if(StringUtils.isEmpty(deptConfig.getBitNo())
							&& deptConfig.getIsSum().equals(0)){
						lstErrMsg.add("当表位号为空时,部门编码为‘"+deptConfig.getDeptCode()+"’的下级部门生成应该为'是'");
					}
					Integer nhType = mapDevices.get(deptConfig.getBitNo());
					if(null!=nhType) deptConfig.setNhType(nhType);
				}
				
				Map<String,String> maps = new HashMap<String,String>();
				maps = importExcelInOrder(lstErrMsg, result);
				msg = maps.get("msg");
				if(StringUtils.isEmpty(msg)){
					Long after = System.currentTimeMillis();
					msg = "导入结束，此次导入" + maps.get("size") + "条数据,共耗时：" + (after - befor) + "毫秒";
					System.out.println(msg);
				}else{
					String prefix = "error,导入失败,原因:<br>";
					if(msg.indexOf(prefix)==-1)
						msg = prefix + msg;
				}				
			}

		} catch (Exception e) {
			msg = "error,导入失败,原因:<br>" + e.getMessage();
			log.error("导入部门能耗出错");
		} finally {
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().write(msg);
				response.getWriter().flush();
			} catch (IOException e) {
				msg = "error,导入失败,原因:<br>" + e.getMessage();
				log.error("导入部门能耗出错");
			}
		}
	}
	
	/**
	 *	导出到Excel文件中
	 * @param condition
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "exportDeptConfig", method = RequestMethod.POST)
	public void exportDeptConfig(DeptConfig  condition, HttpServletResponse response) {
		try {
			URL url = this.getClass().getResource("/templates/deptConfigTemplate.xls");
			if(condition.getDeptId()==null){
		 		condition.setDeptId(0l);
			}
			condition.setNhType(1);
			List<DeptConfig> deptElecConfigList = deptConfigService.findAllDeptConfig(condition);
			for(int i=0;i<deptElecConfigList.size();i++){//设置部门全路径
				if(deptElecConfigList.get(i).getId().longValue()!=nhDeptService.findRootId().longValue())
					deptElecConfigList.get(i).setDeptName(OrganizationUtils.getFullDeptNameByDeptId(deptElecConfigList.get(i).getId()));
			}
			condition.setNhType(2);
			List<DeptConfig> deptWaterConfigList = deptConfigService.findAllDeptConfig(condition);
			for(int i=0;i<deptWaterConfigList.size();i++){//设置部门全路径
				if(deptWaterConfigList.get(i).getId().longValue()!=nhDeptService.findRootId().longValue())
					deptWaterConfigList.get(i).setDeptName(OrganizationUtils.getFullDeptNameByDeptId(deptWaterConfigList.get(i).getId()));
			}
			condition.setNhType(3);
			List<DeptConfig> deptGasConfigList = deptConfigService.findAllDeptConfig(condition);
			for(int i=0;i<deptGasConfigList.size();i++){//设置部门全路径
				if(deptGasConfigList.get(i).getId().longValue()!=nhDeptService.findRootId().longValue())
					deptGasConfigList.get(i).setDeptName(OrganizationUtils.getFullDeptNameByDeptId(deptGasConfigList.get(i).getId()));
			}
			condition.setNhType(4);
			List<DeptConfig> deptEnergyConfigList = deptConfigService.findAllDeptConfig(condition);
			for(int i=0;i<deptEnergyConfigList.size();i++){//设置部门全路径
				if(deptEnergyConfigList.get(i).getId().longValue()!=nhDeptService.findRootId().longValue())
					deptEnergyConfigList.get(i).setDeptName(OrganizationUtils.getFullDeptNameByDeptId(deptEnergyConfigList.get(i).getId()));
			}
			// ===========================
			String filename = "部门能耗设置.xls";
			response.reset();
			response.addHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
			response.setContentType("application/octet-stream");
			// excel.writeToStream(response.getOutputStream());
			Map<String, Object> beanParams = new HashMap<String, Object>();
			beanParams.put("deptElecConfigList", deptElecConfigList);
			beanParams.put("deptWaterConfigList",deptWaterConfigList);
			beanParams.put("deptGasConfigList", deptGasConfigList);
			beanParams.put("deptEnergyConfigList", deptEnergyConfigList);
			
			beanParams.put("title", filename);
			XLSTransformer transformer = new XLSTransformer();
			org.apache.poi.ss.usermodel.Workbook workbook = transformer.transformXLS(url.openStream(), beanParams);
			// resetCellFormula((HSSFWorkbook)workbook);
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	/**
	 * 
	 * @param lstErrMsg
	 * @param lstDeptConfig
	 * @return
	 */
	private Map<String,String> importExcelInOrder(List<String> lstErrMsg,List<DeptConfig> lstDeptConfig){
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		lstDeptConfig = deptConfigService.setDeviceId(lstErrMsg,lstDeptConfig);
		if(UtilTool.isEmptyList(lstErrMsg)){
			try {
				deptConfigService.insertList(lstDeptConfig);
			} catch (Exception e) {
				msg += e.toString() + "<br>";
			}
		}else{
			msg = "error,导入失败,原因:<br>";
			for (String s : lstErrMsg) {
				msg += s + "<br>";
			}
		}
		map.put("msg", msg);
		map.put("size", String.valueOf(lstDeptConfig.size()));
		return map;
	}
	private List<DeptConfig> errorIsSum(List<DeptConfig> lst,Map<Long,String> allDept){
		List<DeptConfig> nlst=new ArrayList<DeptConfig>();
		for(DeptConfig d:lst){
			if((d.getIsSum()==1 && d.getSumDevice() > 0)||(d.getIsSum()==0 && d.getSumDevice()==0)){
				d.setDeptName(allDept.get(d.getDeptId()));
				nlst.add(d);
			}
		}
		return nlst;
	}
}
