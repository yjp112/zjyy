package com.supconit.nhgl.basic.areaConfig.controller;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import net.sf.jxls.transformer.XLSTransformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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
import com.supconit.common.utils.UtilTool;
import com.supconit.common.utils.excel.ExcelImportHelper;
import com.supconit.common.utils.excel.ExcelReader;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.mvc.utils.UploadUtils;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadRenamePolicy;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadedFile;
import com.supconit.nhgl.basic.areaConfig.entities.AreaConfig;
import com.supconit.nhgl.basic.areaConfig.entities.ExportAreaConfig;
import com.supconit.nhgl.basic.areaConfig.service.AreaConfigService;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;
import com.supconit.nhgl.basic.ngArea.service.NgAreaService;
@Controller
@RequestMapping("nhgl/basic/areaConfig")
public class AreaConfigController extends BaseControllerSupport{
	private transient static final Logger log = LoggerFactory.getLogger(AreaConfigController.class);
	@Autowired
	private AreaConfigService areaConfigService;
	@Autowired
	private NgAreaService ngAreaService;
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
		List<NgArea> treeList=ngAreaService.findTree();
		model.put("treeList", treeList);
		model.put("nhType", nhType);
		return "nhgl/basic/areaConfig/list";
	}
	
	@ResponseBody
	@RequestMapping("pager")
	public Pageable<AreaConfig> pager(Pagination<AreaConfig> pager, AreaConfig condition){
		if(condition.getAreaId()==null){
	 		condition.setAreaId(0l);
		}
		areaConfigService.findByCondition(pager, condition);
		return pager;
	}
	
	@RequestMapping("edit")
	public String edit(@RequestParam(required = false) Boolean viewOnly,ModelMap model,AreaConfig condition){
		String devName="";
		if(condition.getId()!=null){   //修改
			AreaConfig areaConfig=areaConfigService.getByCondition(condition);
			//区域叠加区域
			areaConfig.setSubLeftConfigList(areaConfigService.findByAreaIdAndRule(condition,Constant.RULE_FLAG_PLUS));
			//区域被减区
			areaConfig.setSubRightConfigList(areaConfigService.findByAreaIdAndRule(condition,Constant.RULE_FLAG_DECREASE));
			model.put("areaConfig", areaConfig);
		}else{
			model.put("areaConfig", condition);
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
		return "nhgl/basic/areaConfig/edit";
	}
	@RequestMapping("check")
	public String check(@RequestParam(required = false) Boolean viewOnly,ModelMap model,AreaConfig condition){
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
		List<AreaConfig> areaConfig=areaConfigService.findErrorIsSum(String.valueOf(nhtype));
		Map<Long,String> allArea = areaConfigService.findAllArea();
		areaConfig=errorIsSum(areaConfig,allArea);
		model.put("areaConfiglst", areaConfig);
		model.put("deviceName", devName);
		model.put("category", category);
		model.put("nhtype", nhtype);
		return "nhgl/basic/areaConfig/check";
	}
	@ResponseBody
	@RequestMapping("unpager")
	public Pageable<AreaConfig> unpager(Pagination<AreaConfig> pager, AreaConfig condition){
		//未使用的device
		areaConfigService.findUnusedDevice(pager, condition);
		return pager;
	}
	@ResponseBody
	@RequestMapping("repeatpager")
	public Pageable<AreaConfig> repeatpager(Pagination<AreaConfig> pager, AreaConfig condition){
		//加减重复的device
		areaConfigService.findRepeatDevice(pager, condition);
		return pager;
	}
	@ResponseBody
	@RequestMapping("ftxspager")
	public Pageable<AreaConfig> ftxspager(Pagination<AreaConfig> pager, AreaConfig condition){
		//分摊系数!=1的device
		areaConfigService.findErrorFtxs(pager, condition);
		return pager;
	}	
	@ResponseBody
	@RequestMapping("save")
	public ScoMessage save(AreaConfig areaConfig){
		if(areaConfig.getId()==null){//添加
			areaConfigService.insert(areaConfig);
		}else{//修改
			areaConfigService.update(areaConfig); 
		}
		return ScoMessage.success("nhgl/basic/areaConfig/list?nhType="+areaConfig.getNhType(), "操作成功。");
	}
	
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ScoMessage delete(AreaConfig areaConfig){
		areaConfigService.deleteByAreaId(areaConfig.getAreaId(),areaConfig.getNhType().toString());
		return ScoMessage.success("nhgl/basic/areaConfig/list?nhType="+areaConfig.getNhType(), "删除成功。");
	}
	@ResponseBody
	@RequestMapping(value = "check", method = RequestMethod.POST)
	public boolean check(AreaConfig areaConfig){
		boolean state=false;
		if(null!=areaConfig.getAreaId()){
			int count=areaConfigService.countFindAll(areaConfig);
			if(count>0)
				state=true;
		}
		return state;
	}
	@RequestMapping(value = "preImp")
	public String preImp(ModelMap model) {
		return "nhgl/basic/areaConfig/areaConfig_imp";
	}
	
	@RequestMapping(value = "impAreaConfig",method = RequestMethod.POST)
	public void impAreaConfig(HttpServletRequest request,HttpServletResponse response){
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
				
//				Map<String, List> map = new ExcelReader().readExcelContentAreaConfig(in);
//				lstErrMsg = map.get("lstErrMsg");
//				List<AreaConfig> result = map.get("lstAreaConfig");
				Map<String,Integer> mapDevices = areaConfigService.findDevicesMapWithOutRoot();
				ExcelImportHelper<AreaConfig> areaPojo = new ExcelImportHelper(AreaConfig.class);
				Workbook book = Workbook.getWorkbook(in);
				List<AreaConfig> result = new ArrayList<AreaConfig>();
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
					List<AreaConfig> d_list = areaPojo.importExcel(book,new int[]{d});
					for (AreaConfig areaConfig : d_list) {
						areaConfig.setNhType(1);
						if(StringUtils.isNotEmpty(areaConfig.getAreaCode())) result.add(areaConfig);
					}
				}
				if(w!=-1){
					List<AreaConfig> w_list = areaPojo.importExcel(book,new int[]{w});
					for (AreaConfig areaConfig : w_list) {
						areaConfig.setNhType(2);
						if(StringUtils.isNotEmpty(areaConfig.getAreaCode())) result.add(areaConfig);
					}
				}
				if(s!=-1){
					List<AreaConfig> s_list = areaPojo.importExcel(book,new int[]{s});
					for (AreaConfig areaConfig : s_list) {
						areaConfig.setNhType(3);
						if(StringUtils.isNotEmpty(areaConfig.getAreaCode())) result.add(areaConfig);
					}
				}
				if(e!=-1){
					List<AreaConfig> e_list = areaPojo.importExcel(book,new int[]{e});
					for (AreaConfig areaConfig : e_list) {
						areaConfig.setNhType(4);
						if(StringUtils.isNotEmpty(areaConfig.getAreaCode())) result.add(areaConfig);
					}
				}
				
				for (AreaConfig areaConfig : result) {
					if(StringUtils.isNotEmpty(areaConfig.getBitNo())
							&& areaConfig.getIsSum().equals(1)){
						lstErrMsg.add("当表位号非空时,区域编码为‘"+areaConfig.getAreaCode()+"’的下级区域生成应该为'否'");
					}
					if(StringUtils.isEmpty(areaConfig.getBitNo())
							&& areaConfig.getIsSum().equals(0)){
						lstErrMsg.add("当表位号为空时,区域编码为‘"+areaConfig.getAreaCode()+"’的下级区域生成应该为'是'");
					}
					Integer nhType = mapDevices.get(areaConfig.getBitNo());
					if(null!=nhType) areaConfig.setNhType(nhType);
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
			log.error("导入区域能耗出错");
		} finally {
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().write(msg);
				response.getWriter().flush();
			} catch (IOException e) {
				msg = "error,导入失败,原因:<br>" + e.getMessage();
				log.error("导入区域能耗出错");
			}
		}
	}
	
	/**
	 *	导出到Excel文件中
	 * @param condition
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "exportAreaConfig", method = RequestMethod.POST)
	public void exportAreaConfig(AreaConfig  condition, HttpServletResponse response) {
		try {
			URL url = this.getClass().getResource("/templates/areaConfigTemplate.xls");
			condition.setNhType(1);
			List<ExportAreaConfig> areaElecConfigList = areaConfigService.findAreaConfigByNhType(condition);
			condition.setNhType(2);
			List<ExportAreaConfig> areaWaterConfigList = areaConfigService.findAreaConfigByNhType(condition);
			condition.setNhType(3);
			List<ExportAreaConfig> areaGasConfigList = areaConfigService.findAreaConfigByNhType(condition);
			condition.setNhType(4);
			List<ExportAreaConfig> areaEnergyConfigList = areaConfigService.findAreaConfigByNhType(condition);
			
			// ===========================
			String filename = "区域能耗设置.xls";
			response.reset();
			response.addHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
			response.setContentType("application/octet-stream");
			// excel.writeToStream(response.getOutputStream());
			Map<String, Object> beanParams = new HashMap<String, Object>();
			beanParams.put("areaElecConfigList", areaElecConfigList);
			beanParams.put("areaWaterConfigList",areaWaterConfigList);
			beanParams.put("areaGasConfigList", areaGasConfigList);
			beanParams.put("areaEnergyConfigList", areaEnergyConfigList);
			
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
	 * @param lstAreaConfig
	 * @return
	 */
	private Map<String,String> importExcelInOrder(List<String> lstErrMsg,List<AreaConfig> lstAreaConfig){
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		areaConfigService.setAreaId(lstErrMsg,lstAreaConfig);
		if(UtilTool.isEmptyList(lstErrMsg)){
			try {
				areaConfigService.insertList(lstAreaConfig);
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
		map.put("size", String.valueOf(lstAreaConfig.size()));
		return map;
	}
	private List<AreaConfig> errorIsSum(List<AreaConfig> lst,Map<Long,String> allArea){
		List<AreaConfig> nlst=new ArrayList<AreaConfig>();
		for(AreaConfig d:lst){
			if((d.getIsSum()==1 && d.getSumDevice()>0)||(d.getIsSum()==0 && d.getSumDevice()==0)){
				d.setAreaName(allArea.get(d.getAreaId()));
				nlst.add(d);
			}
		}
		return nlst;
	}
}
