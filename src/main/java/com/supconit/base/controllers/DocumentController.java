package com.supconit.base.controllers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.Document;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.DocumentService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.ywgl.exceptions.BusinessDoneException;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import jodd.io.FileUtil;

@Controller
@RequestMapping("document")
public class DocumentController extends BaseControllerSupport{
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private DocumentService documentService;
	@Value("${file.tmpsave}")
	private String tmpPath;

	@Value("${file.persistentsave}")
	private String savePath;
	@RequestMapping("list")
	public String list(String deviceCode,ModelMap model) {
		model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.DOCUMENT_TYPE));
		List<GeoArea> treeListLou = geoAreaService.findTree();
		model.put("treeListLou", treeListLou);		
		List<DeviceCategory> treeListType = deviceCategoryService.findAll();
		model.put("treeListType", treeListType);		
		model.put("deviceCode", deviceCode);
		return "base/document/document_list";
	}
	
    @ResponseBody
    @RequestMapping("page")
	public Pageable<Document> page( Pagination<Document> pager, @ModelAttribute Document document,
			ModelMap model) {
		 documentService.findByCondition(pager, document);
		 return pager;
	}
	    
	    
    @ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(Document document,String[] fileorignal,String[] filename,String[] delfiles) {
		ScoMessage msg = ScoMessage.success(ScoMessage.SAVE_SUCCESS_MSG);

		if(document.getId()==null){
			//copyCreateInfo(document);
			document.setCreateId(getCurrentPersonId());
			document.setCreator(getCurrentPersonName());
			saveFile(document,fileorignal,filename,delfiles);
			documentService.insert(document);	
		}
		else{
			//copyUpdateInfo(document);  
			document.setUpdateId(getCurrentPersonId());
			document.setUpdator(getCurrentPersonName());
			document.setUpdateDate(document.getCreateDate());
			saveFile(document,fileorignal,filename,delfiles);
			documentService.update(document);
		}
		return msg;
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		documentService.deleteByIds(ids);	
		return ScoMessage.success(".",ScoMessage.DELETE_SUCCESS_MSG);
	}   
    
    /**
	 * Edit Document
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  ModelMap model,String showFlag) {
    	if (null != id) {
			Document document = documentService.getById(id);
			if (null == document) {
				throw new IllegalArgumentException("Object does not exist");
			}
			if("1".equals(showFlag)){
				document.timesJY(document.getTimes());   
				documentService.update(document);
			}
			model.put("document", document);			
		}else{
			Document document = new Document();
			document.setCreator(getCurrentPersonName());
			model.put("document", document);
		}	
    	model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.DOCUMENT_TYPE));
		model.put("showFlag", showFlag);
		return "base/document/document_edit";
	}
   
    public void saveFile(Document document,String[] fileorignal,String[] filename,String[] delfiles){
				if (delfiles != null) {
					String filePath = savePath + "/" + document.getStorePath();
					try {
						FileUtil.delete(filePath);
					} catch (Exception e) {
						System.out.println("删除磁盘文件失败");
					}
			}

			// 新文件移动并保存
			if (fileorignal != null) {
				int fileNum = fileorignal.length;
				for (int i = 0; i < fileNum; i++) {
		
					
					File saveFile = new File(tmpPath + "/" + filename[i]);
					System.out.println(saveFile.length());
					if (checkFileSize(saveFile, 20 * 1024 * 1024L)) {
						//throw new Exception("新增附件失败,附件过大");
						throw new BusinessDoneException("新增附件失败,附件过大");  
					}
					document.setFileSize((long)Math.ceil(saveFile.length()/1024));//单位KB
					// 移动文件
					try {
						FileUtil.move(tmpPath + "/" + filename[i], savePath + "/"+ filename[i]);
					} catch (Exception e) {
						throw new BusinessDoneException("新增附件失败,移动文件失败");  
					}				
					
					document.setFileName(fileorignal[i]);
					document.setStorePath(filename[i]);
					document.setFileType(fileorignal[i].substring(fileorignal[i].lastIndexOf(".")+1, fileorignal[i].length()));
					System.out.println(saveFile.length());
				}
			}
    }
 // 判断文件是否过大
 	public boolean checkFileSize(File file, Long maxSize) {
 		boolean tooBig = false;
 		if (file.length() > maxSize) {
 			tooBig = true;
 		}
 		return tooBig;
 	}
}
