
package com.supconit.base.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.Attachment;
import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.DeviceProperty;
import com.supconit.base.services.AttachmentService;
import com.supconit.base.services.DeviceCategoryPropertyService;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.DevicePropertyService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("device/deviceCategory")
public class DeviceCategoryController extends BaseControllerSupport {

    
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private DevicePropertyService devicePropertyService;
	@Autowired
	private DeviceCategoryPropertyService deviceCategoryPropertyService;
	@Autowired
	private AttachmentService attachmentService;
	@Value("${image.server.url}")
	private String imageServerUrl;
	@Value("${image.temp.server.url}")
	private String imageTempServerUrl;
	
	//配置文件长度
	private String fileLength="";
    /*
    get "deviceCategory" list
    */
    @RequestMapping("list")
	public String list(ModelMap model) {
		List<DeviceCategory> treeList = deviceCategoryService.findAll();
		model.put("treeList", treeList);		
		return "base/deviceCategory/deviceCategory_list";
	}

    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping("page")
	public Pageable<DeviceCategory> page( Pagination<DeviceCategory> pager, @ModelAttribute DeviceCategory deviceCategory,
			ModelMap model) {
		return deviceCategoryService.findByCondition(pager, deviceCategory);
	}

    /*
    save  deviceCategory
    DeviceCategory object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(DeviceCategory deviceCategory,Long[] propertyIds,Long[] propertySorts,String[] fileorignal,String[] filename,String[] delfiles) {
		//filename:新增的文件原名 系统名,fileorignal:新增的文件新名 显示名, delfiles:删除的文件		
		 if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "图片");
		 }
		 if(deviceCategory.getLastNode()==null){
			 deviceCategory.setLastNode(1L);
		 }
		 if(deviceCategory.getParentId()==null){
			 deviceCategory.setParentId(0L);
		 }
         if(deviceCategory.getId()==null){
            copyCreateInfo(deviceCategory);
            deviceCategoryService.insert(deviceCategory,propertyIds,propertySorts,fileorignal,filename,null,fileLength);	
        }
        else{
            copyUpdateInfo(deviceCategory);    
            deviceCategoryService.update(deviceCategory,propertyIds,propertySorts,fileorignal,filename,delfiles,fileLength);	
        }
		return ScoMessage.success("device/deviceCategory/list",ScoMessage.SAVE_SUCCESS_MSG);
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {		
		deviceCategoryService.deleteByIds(ids);	
		return ScoMessage.success("device/deviceCategory/list",ScoMessage.DELETE_SUCCESS_MSG);
	}   
    
    /**
	 * Edit DeviceCategory
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  ModelMap model,String showFlag,String viewOnly) {
    	
    	List<DeviceProperty> lstProperty = new ArrayList<DeviceProperty>();
    	List<DeviceProperty> lstPropertyAll = devicePropertyService.findList("findAll",null);
		if (null != id) {
			Map<String, Long> slqMap = new HashMap<String, Long>();
	    	slqMap.put("id",id);
	    	lstProperty = devicePropertyService.findList("findByCategoryId",slqMap);
			DeviceCategory deviceCategory = deviceCategoryService.getById(id);
			if (null == deviceCategory) {
				throw new IllegalArgumentException("Object does not exist");
			}
			Long parentId=deviceCategory.getParentId();
			if(parentId==0L){
				deviceCategory.setParentName("设备类别簇");
			}else{
				deviceCategory.setParentName(deviceCategoryService.getById(parentId).getCategoryName());
			}
			model.put("deviceCategory", deviceCategory);
			
			//图片
			List<Attachment>  listAttachments = attachmentService.getAttachmentByFid(id,Constant.ATTACHEMENT_DEVICE_CATEGORY);
			if(!UtilTool.isEmptyList(listAttachments)){
				model.put("imageSysName", listAttachments.get(0).getStorePath());
				model.put("imageShowName", listAttachments.get(0).getFileName());
				model.put("attachId", listAttachments.get(0).getId());
			}
		}	
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "图片");
		}
		model.put("imagePath", imageServerUrl);
		model.put("imageTempPath", imageTempServerUrl);
		model.put("fileLength", fileLength); 
		model.put("lstProperty", lstProperty);
		model.put("lstPropertyAll", lstPropertyAll);
		model.put("showFlag", showFlag);
		model.put("viewOnly", viewOnly);
		return "base/deviceCategory/deviceCategory_edit";
	}

}
