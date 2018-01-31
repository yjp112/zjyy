
package com.supconit.base.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.supconit.base.entities.EnumDetail;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.utils.excel.ExcelReader;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.authorization.services.UserService;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.honeycomb.mvc.utils.UploadUtils;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadRenamePolicy;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadedFile;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import jodd.util.StringUtil;

@Controller
@RequestMapping("base/geoArea")
public class GeoAreaController extends BaseControllerSupport {
	private transient static final Logger log = LoggerFactory.getLogger(GeoAreaController.class);
    
	@Autowired
	private GeoAreaService geoAreaService;
//	@Autowired
//	private ISysEnumService enumService;
	@Autowired
	private UserService userService;
	@Autowired
	private PersonService personService;
	@Value("${file.tmpsave}")
	private String tmpPath;
	
	/*@RequestMapping("page")
	public String page(@ModelAttribute GeoArea geoArea,
			@RequestParam(required = false) String onlyLastNode,
			@RequestParam(required = false) String flag,
			@RequestParam(defaultValue = "1") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, ModelMap model) {
		List<GeoArea> treeList = null;
		treeList = geoAreaService.findTree();
		model.put("treeList", treeList);
		if (null != flag && "tree".equals(flag)) {
			return "base/geoArea/geoArea_tree";
		}
		if (null != flag &&"openPOP".equals(flag)) {
			if ("true".equals(onlyLastNode)) {
				model.put("onlyLastNode", onlyLastNode);// 只需要在末节点添加单选框
			}
			return "base/geoArea/geoArea_search";
		}else{
			return null;
		}
	}*/
	
	@RequestMapping("go")
	public String go(ModelMap model) {
		List<GeoArea> treeList = null;
		treeList = geoAreaService.findTree();
		model.put("treeList", treeList);
		return "base/geoArea/geoArea_list";
    }

	@RequestMapping("list")
    @ResponseBody
	public Pageable<GeoArea>  list(@ModelAttribute GeoArea geoArea,
			@RequestParam(required = false) String treeId,
			Pagination<GeoArea> pager,
            ModelMap model) {
		if(StringUtil.isNotBlank(treeId)){//为了区别树上面的参数parentId和查询表单里面的parentId
			geoArea.setId(Long.parseLong(treeId));
		}
		return geoAreaService.findByPage(pager, geoArea);
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,ModelMap model, @RequestParam(required = false) Long id) {
    	//修改
		if (null != id) {
			GeoArea geoArea = geoAreaService.getById(id);	
			if (StringUtil.isBlank(geoArea.getParentName())){
				geoArea.setParentName("地理区域簇");
			}
			model.put("geoArea", geoArea);
		}
		List<EnumDetail> listAreaType = DictUtils.getDictList(DictTypeEnum.AREA_TYPE);
		model.put("listAreaType", listAreaType);
		model.put("viewOnly", viewOnly);
		return "base/geoArea/geoArea_edit";
	}
	
    /**
	 * 判断编码是否重名  
	 * @throws HoncombException
	 */
	@ResponseBody
	@RequestMapping("checkAreaCode")
	public String checkAreaCode(String code) throws Exception{

		List<GeoArea> list=geoAreaService.findByCode(code);	
		
		String responseText;
		if(list.size()==0){
			responseText="usable";
		}
		else{
			responseText="unusable";
		}
		
		return responseText;
	}
	
	/**
	 * 区域保存/修改
	 * @return
	 * @throws HoncombException
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(GeoArea geoArea) {
		if (geoArea.getId() == null) {
			copyCreateInfo(geoArea);
			geoAreaService.insert(geoArea);
		} else {
			copyUpdateInfo(geoArea);
			geoAreaService.update(geoArea);
		}
		return ScoMessage.success("base/geoArea/go","操作成功。");
	}
	
	/**
	 * 
	 * @return
	 * @throws HoncombException
	 *//*
	@RequestMapping("getGeoAreaById")
	public String getGeoAreaById(ModelMap model,@RequestParam(required = true)Long id){
		//从检索画面迁过来
		GeoArea geoArea = geoAreaService.getById(id);
		 
		if(geoArea != null){		 
			//取得区域性质名词
			SysEnum sysEnum = enumService.findEnumTypeId(3l,String.valueOf(geoArea.getAreaAttr()));
			if (sysEnum != null){
				String enumText_show = sysEnum.getEnumText();
				model.put("enumText_show",enumText_show);
			}	
			//取得创建人名词和上级区域名
			getPersonNameAreaName(model,geoArea);
		}
		model.put("geoArea", geoArea);
		return "base/geoArea/geoArea_show";
	}*/
	
	/*@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)   
	public String delete(Long[] checkValue) {
		if (null != checkValue) {
			if(!"".equals(geoAreaService.checkDelete(checkValue))){
				//有下级区域 或者 已经被使用时，不能删除。
				//responseText = "编码：" + geoAreaService.checkDelete(this.getCheckValue());
				return "3";
			}else{
				try {
					geoAreaService.removeGeoArea(checkValue);
				} catch (Exception e) {
					return "4";
				}
				return"1";
				
			}			
		} else {
			return "2";
		}
	}*/
	
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		
		geoAreaService.removeGeoArea(ids);
		
		return ScoMessage.success("base/geoArea/go", "删除成功。");
	} 
	
	@ResponseBody
	@RequestMapping("findFloorById")
	public Map<String,List<GeoArea>> findFloorById(
			@RequestParam(required = false) Long buildingId) {
		if(null!=buildingId){
			List<GeoArea> listGeoAreas = geoAreaService.findFloorById(buildingId);
			Map<String,List<GeoArea>> map = new HashMap<String,List<GeoArea>>();
			map.put("listGeoAreas", listGeoAreas);
			return map;
		}else
			return null;
	}
	
	@ResponseBody
	@RequestMapping("findGeoAreaByCode")
	public Map<String,GeoArea> findGeoAreaByCode(
			@RequestParam(required = false) String code) {
		if(null!=code&&!("").equals(code)){
			List<GeoArea> list=geoAreaService.findByCode(code);
			Map<String,GeoArea> map = new HashMap<String,GeoArea>();
			map.put("geoArea", list.get(0));
			return map;
		}else
			return null;
	}
	
	@RequestMapping("lookup")
    public String lookup(String txtId,String txtName,String dialogId,ModelMap model) {
		List<GeoArea> treeList = null;
		treeList = geoAreaService.findTree();
		model.put("treeList", treeList);
        model.put("txtId", txtId);  
        model.put("txtName", txtName);  
        model.put("dialogId", dialogId);
        return "base/geoArea/geoArea_lookup";
    }
	
	@RequestMapping(value = "preImp")
	public String preImp(ModelMap model) {
		return "base/geoArea/geoArea_imp";
	}
	
	@RequestMapping(value = "impGeoArea",method = RequestMethod.POST)
	public void impGeoArea(HttpServletRequest request,HttpServletResponse response){
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
				Map<String, List> map = new ExcelReader().readExcelContentGeoArea(in);
				lstErrMsg = map.get("lstErrMsg");
				List<GeoArea> lstSystemRule = map.get("lstGeoArea");
				long size = lstSystemRule.size();
				
				List<GeoArea> firstLevel = new ArrayList<GeoArea>();
				List<GeoArea> restLevel = new ArrayList<GeoArea>();
				for (GeoArea tmp:lstSystemRule) {
					if(StringUtil.isEmpty(tmp.getParentName())){
						firstLevel.add(tmp);
					}else{
						restLevel.add(tmp);
					}
				}
				
				msg = importExcelInOrder(lstErrMsg, firstLevel, restLevel,msg, size);
				if(StringUtils.isEmpty(msg)){
					Long after = System.currentTimeMillis();
					System.out.println("导入结束，此次导入" + size
							+ "条数据,共耗时：" + (after - befor) + "毫秒");
				}				
			}

		} catch (Exception e) {
			msg = "error,导入失败,原因:<br>" + e.getMessage();
			log.error("导入地理区域出错");
		} finally {
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().write(msg);
				response.getWriter().flush();
			} catch (IOException e) {
				msg = "error,导入失败,原因:<br>" + e.getMessage();
				log.error("导入地理区域出错");
			}
		}
	}
	
	/**
	 * EXCEL按父子顺序导入
	 */
	private String importExcelInOrder(List<String> lstErrMsg,List<GeoArea> firstLevel,List<GeoArea> restLevel,String msg,long size){
		if (UtilTool.isEmptyList(lstErrMsg)) {
			List<GeoArea> lstGeoAreaAll = geoAreaService.findAll();
			for (GeoArea s : firstLevel) {
				String FName = s.getAreaName();
				if(!UtilTool.isEmpty(s.getParentName())){
					boolean bolExists = false;
					for (GeoArea s2 : lstGeoAreaAll) {
						if (s2.getAreaName().equals(s.getParentName())) {
							s.setParentId(s2.getId());
							bolExists = true;
							if(s.getParentId()!=0L){
								FName = s2.getFullLevelName()+ "→" + FName ;
							}
							s.setFullLevelName(FName);
							break;
						}
					}
					if (!bolExists) {
						lstErrMsg.add("此父系统(" + s.getParentName()
								+ ")在地理区域表里不存在。");
						break;
					}
				}else{
					s.setFullLevelName(FName);
				}
			}

			if (UtilTool.isEmptyList(lstErrMsg)) {
				for (GeoArea s : firstLevel){
					boolean bolCode = true;
					for (GeoArea s2 : lstGeoAreaAll) {
						if (s2.getAreaCode().equals(s.getAreaCode())) {
							bolCode = false;
							break;
						}
					}
					if (!bolCode) {
						lstErrMsg.add("此区域编码(" + s.getAreaCode()
								+ ")在地理区域表里已经存在，不能重复插入。");
						break;
					}
				}
			}
			
			if (UtilTool.isEmptyList(lstErrMsg)) {// 验证通过
				for (GeoArea geoArea : firstLevel) {
					copyCreateInfo(geoArea);
				}
				geoAreaService.insertForImp(firstLevel);
				if(!restLevel.isEmpty()){
					List<GeoArea> t_firstLevel = new ArrayList<GeoArea>();
					List<GeoArea> t_restLevel = new ArrayList<GeoArea>();
					List<String> arr = new ArrayList<String>();
					for (GeoArea fir:firstLevel) {
						arr.add(fir.getAreaName());
					}
					for (GeoArea geo : lstGeoAreaAll) {
						arr.add(geo.getAreaName());
					}
					int count = 0;
					for (GeoArea res:restLevel) {
						if(arr.contains(res.getParentName())){
							t_firstLevel.add(res);
							count ++;
						}else{
							t_restLevel.add(res);
						}
					}
					String str = "";
					if(count == 0){
						StringBuilder sb = new StringBuilder();
						for (GeoArea res:restLevel) {
							sb.append(res.getParentName()+",");
						}		
						if(sb.length()>1){
							String tmp = sb.substring(0, sb.length()-1);
							lstErrMsg.add("此父系统(" + tmp
									+ ")在地理区域表里不存在。");
						} 
					}else{
						str = importExcelInOrder(lstErrMsg, t_firstLevel, t_restLevel, msg, size);
					}
					if(StringUtils.isNotEmpty(str)){
						lstErrMsg.add(str);
					}
					if (!UtilTool.isEmptyList(lstErrMsg)) {
						msg = "error,导入失败,原因:<br>";
						for (String s : lstErrMsg) {
							msg += s + "<br>";
						}
					}
				}
			}
		}else {
			msg = "error,导入失败,原因:<br>";
			for (String s : lstErrMsg) {
				msg += s + "<br>";
			}
		}
		return msg;
	}
}
