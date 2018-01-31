package com.supconit.base.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.ArrayList;
import java.util.List;

import jodd.util.StringUtil;

import org.hyperic.sigar.pager.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.Attachment;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.DeviceSpareIn;
import com.supconit.base.entities.DeviceSpareInDetail;
import com.supconit.base.entities.DeviceSpareOut;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.entities.SubDeviceSpareOut;
import com.supconit.base.services.AttachmentService;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.DeviceService;
import com.supconit.base.services.DeviceSpareInDetailService;
import com.supconit.base.services.DeviceSpareInService;
import com.supconit.base.services.DeviceSpareOutService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.RegExpValidatorUtils;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.authorization.constants.SettingConstants;

@Controller
@RequestMapping("base/deviceSpareIn")
public class DeviceSpareInController extends BaseControllerSupport{
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private DeviceSpareInService deviceSpareInService;
	@Autowired
	private DeviceSpareInDetailService deviceSpareInDetailService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DeviceSpareOutService deviceSpareOutService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private OrganizationService organizationService;
	@Value("${image.temp.server.url}")
	private String imageTempServerUrl;
	@Value("${image.server.url}")
	private String imageServerUrl;
	
	//配置文件长度
	private String fileLength="";
	
    @RequestMapping("list")
	public String list(ModelMap model) {
		List<GeoArea> treeListLou = geoAreaService.findTree();
		model.put("treeListLou", treeListLou);		
		List<DeviceCategory> treeListType = deviceCategoryService.findAll();
		model.put("treeListType", treeListType);		
		return "base/deviceSpare/deviceSpare_list";
	}
    @RequestMapping("add_paper")
    public String add_paper(ModelMap model,Long id) {
    	if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "备件");
		}
    	model.put("fileLength", fileLength); 
    	model.put("deviceSpareId", id);
    	return "base/deviceSpare/deviceSparePaper_add";
    }
    
    @ResponseBody
    @RequestMapping("documentSave")
    public ScoMessage documentsave(Long deviceSpareId,String[] fileorignal,String[] filename,String[] delfiles) {
    	
    	deviceSpareInService.documentSave(deviceSpareId,fileorignal,filename,delfiles,fileLength);
    	return ScoMessage.success("操作成功");
    }

    
    
    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping("page")
	public Pageable<DeviceSpareIn> page( Pagination<DeviceSpareIn> pager, @ModelAttribute DeviceSpareIn deviceSpareIn,
			ModelMap model) {
    	return deviceSpareInService.findByCondition(pager,deviceSpareIn);
	}
    @RequestMapping("spareDetailEdit")
    public String spareDetailEdit( Long id,Long sId,ModelMap model) {
    	if(id==null){//新增
    		DeviceSpareIn dsi;
    		if(sId!=null){
    			dsi=deviceSpareInService.findById(sId,Constant.SPARE_DEVICE);
    		}else{
    			dsi=deviceSpareInService.findTopOne();
    		}   	  
    		model.put("deviceSpareInId", dsi.getId());
    	}else{
    		DeviceSpareInDetail dsd=deviceSpareInDetailService.findById(id);
    		model.put("spareDetail", dsd);
    	}
    	return "base/deviceSpare/deviceSpareDetail_edit";
    }
    @RequestMapping("spareOutlst")
    public String spareOutlst( Long id,ModelMap model) {
    	DeviceSpareIn device =new DeviceSpareIn();
    	device=deviceSpareInService.findById(id,Constant.SPARE_DEVICE);
    	if(device.getDeptId()!=null){
    		device.setDeptName(organizationService.getFullDeptNameByDeptId(device.getDeptId()));
    	}
    	model.put("device", device);
    	
    	return "base/deviceSpare/deviceSpareOut_list";
    }
    
    @RequestMapping("lookup")
    public String lookup(Long deviceId,String txtId,String txtName,ModelMap model) {
    	DeviceSpareIn device =deviceSpareInService.findByDeviceId(deviceId);
    	model.put("deviceSpareInId", device.getId()); 
        model.put("txtId", txtId);  
        model.put("txtName", txtName); 
        return "base/deviceSpare/deviceSpare_lookup";
    }
    
    @ResponseBody
    @RequestMapping("detailPage")
    public Pageable<DeviceSpareInDetail> detailpage( Pagination<DeviceSpareInDetail> pager, @ModelAttribute DeviceSpareInDetail deviceSpareInDetail,
    		ModelMap model) {
    	return deviceSpareInDetailService.findByCondition(pager,deviceSpareInDetail);
    }
    @ResponseBody 
    @RequestMapping("detailsave")
    public ScoMessage detailsave(DeviceSpareInDetail deviceSpareInDetail,ModelMap model) {
    	if(deviceSpareInDetail.getId()==null){
    		deviceSpareInDetail.setRemainder(deviceSpareInDetail.getTotal());
    		if(StringUtil.isEmpty(deviceSpareInDetail.getSortIdx())){ 
    			deviceSpareInDetail.setSortIdx("1"); //设置排序 的默认值
    		}
    		deviceSpareInDetailService.insert(deviceSpareInDetail);
    		DeviceSpareInDetail dsd=deviceSpareInDetailService.findById(deviceSpareInDetail.getId());
    		if(dsd.getSortIdx()==null){
    			dsd.setSortIdx(String.valueOf(dsd.getId()));
    			deviceSpareInDetailService.update(dsd);
    		}
    	}else{
    		DeviceSpareInDetail deviceSpareInDetailTemp= deviceSpareInDetailService.findById(deviceSpareInDetail.getId());
    		if((Integer.parseInt(deviceSpareInDetail.getTotal())-(Integer.parseInt(deviceSpareInDetailTemp.getTotal())-Integer.parseInt(deviceSpareInDetailTemp.getRemainder())))<0){
    			return ScoMessage.error("该备件的总数量不能小于取用量");
    		}else{
    			if(StringUtil.isEmpty(deviceSpareInDetail.getSortIdx())){ 
    				deviceSpareInDetail.setSortIdx("1"); //设置排序 的默认值
        		}
    			deviceSpareInDetailService.update(deviceSpareInDetail);
    		}
    		
    	}
    	DeviceSpareIn dsi=deviceSpareInService.findById(deviceSpareInDetail.getDeviceSpareInId(),Constant.SPARE_DEVICE);
    	return ScoMessage.success("base/deviceSpareIn/register?deviceId="+dsi.getDeviceId()+"&id="+dsi.getId(),"操作成功");
    }
    @ResponseBody
    @RequestMapping("spareOutsave")
    public ScoMessage spareOutsave(DeviceSpareOut deviceSpareOut,ModelMap model) {
    	if(!UtilTool.isEmptyList(deviceSpareOut.getDeviceSpareOutlst())){
    		List<DeviceSpareOut> dlst=new ArrayList<DeviceSpareOut>();
    		List<DeviceSpareInDetail> ddlst=new ArrayList<DeviceSpareInDetail>();
    		for(int i=0;i<deviceSpareOut.getDeviceSpareOutlst().size();i++){
    			SubDeviceSpareOut subDeviceSpareOut=deviceSpareOut.getDeviceSpareOutlst().get(i);
    			DeviceSpareInDetail detail=deviceSpareInDetailService.findById(subDeviceSpareOut.getSpareId());
    			if(StringUtil.isEmpty(subDeviceSpareOut.getGetSum())&&StringUtil.isEmpty(subDeviceSpareOut.getGetor())&&subDeviceSpareOut.getGetDate()==null){
    				continue;
    			}
    			//数据校验
    			if(StringUtil.isEmpty(subDeviceSpareOut.getGetSum())){
    				return ScoMessage.error("该备件["+detail.getSpareName()+"]的取用数量不可以为空"); 
    			}else if(!RegExpValidatorUtils.IsIntNumber(subDeviceSpareOut.getGetSum())){
    				return ScoMessage.error("该备件["+detail.getSpareName()+"]的取用数量必须为非零正整数"); 
    			}else if(StringUtil.isEmpty(subDeviceSpareOut.getGetor())){
    				return ScoMessage.error("该备件["+detail.getSpareName()+"]的取用人不可以为空"); 
    			}else if(subDeviceSpareOut.getGetor().length()>11){
    				return ScoMessage.error("该备件["+detail.getSpareName()+"]的取用人长度不超过11个字符"); 
    			}else if(subDeviceSpareOut.getGetDate()==null){
    				return ScoMessage.error("该备件["+detail.getSpareName()+"]的取用时间不可以为空"); 
    			}else if(subDeviceSpareOut.getAffirmor().length()>11){
    				return ScoMessage.error("该备件["+detail.getSpareName()+"]的确认人长度不超过11个字符"); 
    			}else if(subDeviceSpareOut.getRemark().length()>100){
    				return ScoMessage.error("该备件["+detail.getSpareName()+"]的备注长度不超过100个字符"); 
    			}
    			DeviceSpareOut dd=new DeviceSpareOut();	
    			dd.setGetSum(subDeviceSpareOut.getGetSum());
    			dd.setGetDate(subDeviceSpareOut.getGetDate());
    			dd.setGetor(subDeviceSpareOut.getGetor());
    			dd.setAffirmor(subDeviceSpareOut.getAffirmor());
    			dd.setSpareId(subDeviceSpareOut.getSpareId());
    			dd.setRemark(subDeviceSpareOut.getRemark());
        		if(Integer.valueOf(detail.getRemainder())-Integer.valueOf(dd.getGetSum())<0){
        			return ScoMessage.error("该备件["+detail.getSpareName()+"]余量不足");
        		}
        		detail.setRemainder(String.valueOf(Integer.valueOf(detail.getRemainder())-Integer.valueOf(dd.getGetSum())));
    			ddlst.add(detail);
    			dlst.add(dd);
    		}
    		deviceSpareOutService.insertlst(dlst);
    		deviceSpareInDetailService.updatelst(ddlst);
    	}
    	return ScoMessage.success("操作成功");
    }
    @ResponseBody
    @RequestMapping("save")
    public ScoMessage save(DeviceSpareIn deviceSpareIn,ModelMap model) {
    	if(deviceSpareIn.getId()==null){
    		copyCreateInfo(deviceSpareIn);
    		deviceSpareInService.insert(deviceSpareIn);
    	}else{
    		deviceSpareInService.update(deviceSpareIn);
    	}
    	model.put("deviceSpareIn",deviceSpareIn);
    	return ScoMessage.success(""+deviceSpareIn.getId(),"操作成功");
    }
    @ResponseBody
    @RequestMapping("detaildelete")
    public ScoMessage detaildelete(Long[] ids,ModelMap model) {
    		deviceSpareInDetailService.deleteByIds(ids);
    	return ScoMessage.success("删除成功");
    }
    @RequestMapping("register")
    public String regitster(Long id,Long deviceId,ModelMap model) {
    	if(id==null){
    		Device d=deviceService.getById(deviceId);
    		DeviceSpareIn device=new DeviceSpareIn();
    		if(d.getUseDepartmentId()!=null){
	    		device.setDeptName(organizationService.getFullDeptNameByDeptId(d.getUseDepartmentId()));
	    		device.setDeptId(d.getUseDepartmentId());
    		}
    		device.setDeviceName(d.getDeviceName());
    		device.setCreateId(getCurrentPersonId());
    		device.setCreator(getCurrentPersonName());
    		device.setSupplierName(d.getSupplierName());
    		device.setDeviceId(String.valueOf(d.getId()));
    		model.put("device", device);
    	}else{
    		DeviceSpareIn device=deviceSpareInService.findById(id,Constant.SPARE_DEVICE);
    		if(device.getDeptId()!=null){
    			device.setDeptName(organizationService.getFullDeptNameByDeptId(device.getDeptId()));
    		}
    		model.put("device", device);
    	}
    	return "base/deviceSpare/deviceSpare_register";
    }
    @RequestMapping("getSpare")
    public String getSpare(Long id,ModelMap model) {
		DeviceSpareIn device=deviceSpareInService.findById(id,Constant.SPARE_DEVICE);
		if(device.getDeptId()!=null){
			device.setDeptName(organizationService.getFullDeptNameByDeptId(device.getDeptId()));
		}
		model.put("device", device);
    	List<Attachment>  lstIcons = attachmentService.getAttachmentByFid(id,Constant.ATTACHEMENT_DEVCE_SPARE_IMG);
    	if(!UtilTool.isEmptyList(lstIcons)){
    		model.put("imageSysName", lstIcons.get(0).getStorePath());
    		model.put("imageShowName", lstIcons.get(0).getFileName());
    		model.put("imageattachId", lstIcons.get(0).getId());
    	} 
    	model.put("imageTempPath", imageTempServerUrl);
    	model.put("imagePath", imageServerUrl);
    	return "base/deviceSpare/deviceSpare_get";
    }
   
}
