
package com.supconit.base.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.list.SetUniqueList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.supconit.base.domains.DeviceTimeLine;
import com.supconit.base.entities.Attachment;
import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.DeviceProperty;
import com.supconit.base.entities.DeviceStartStopRecords;
import com.supconit.base.entities.DeviceTree;
import com.supconit.base.entities.Document;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.AttachmentService;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.DevicePropertyService;
import com.supconit.base.services.DeviceService;
import com.supconit.base.services.DeviceStartStopRecordsService;
import com.supconit.base.services.DocumentService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.hl.montrol.entity.MHistoryAlarm;
import com.supconit.hl.montrol.service.IHistoryAlarmService;
import com.supconit.inspection.entities.InspectionTask;
import com.supconit.inspection.services.InspectionTaskService;
import com.supconit.maintain.entities.MaintainTask;
import com.supconit.maintain.services.MaintainTaskService;
import com.supconit.repair.entities.Repair;
import com.supconit.repair.services.RepairService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("device/device")
public class DeviceController  extends BaseControllerSupport{

	private transient static final Logger log = LoggerFactory.getLogger(DeviceController.class);
   
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DevicePropertyService devicePropertyService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;
//	@Autowired
//	private IHistoryAlarmService historyAlarmService;
	@Autowired
	private RepairService repairService;
	@Autowired
	private MaintainTaskService maintainTaskService;
	@Autowired
	private InspectionTaskService inspectionTaskService;
	@Autowired
	private DocumentService documentService;
//	@Autowired
//	private IMonitorObjectTagService monitorObjectTagService;
//	@Autowired
//	private IMonitorObjectService objectService;
	
	
	@Autowired
	private DeviceStartStopRecordsService deviceStartStopRecordsService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Value("${image.server.url}")
	private String imageServerUrl;
	@Value("${image.temp.server.url}")
	private String imageTempServerUrl;
    /*
    get "device" list
    */
    @RequestMapping("list")
	public String list(ModelMap model) {
		List<GeoArea> treeListLou = geoAreaService.findTree();
		model.put("treeListLou", treeListLou);		
		List<DeviceCategory> treeListType = deviceCategoryService.findAll();
		model.put("treeListType", treeListType);
		model.put("lstStatus", DictUtils.getDictList(DictUtils.DictTypeEnum.DEVICE_STATUS));
		return "base/device/device_list";
	}
    
    
    @RequestMapping("choose")
	public String choose(ModelMap model,String txtId,String txtName,String type,String dialogId) {
		List<GeoArea> treeListLou = geoAreaService.findTree();
		model.put("treeListLou", treeListLou);		
		List<DeviceCategory> treeListType = deviceCategoryService.findAll();
		model.put("treeListType", treeListType);
		model.put("txtId", txtId);
		model.put("txtName", txtName);
		model.put("type", type);
		model.put("dialogId", dialogId);
		return "base/device/choose_device";
	}
    
    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping("page")
	public Pageable<Device> page( Pagination<Device> pager, @ModelAttribute Device device,
			ModelMap model) {
		return deviceService.findByCondition(pager, device);
	}


    @RequestMapping("tree")
    public void treeList( @ModelAttribute Device device,
                                  ModelMap model,HttpServletResponse response) {
    	device.setSearch(false);
        if(device.getId()==null||device.getId().equals(0)) {
            if (device.getDeviceCode() != null && !"".equals(device.getDeviceCode())) {
                device.setSearch(true);
            }
            if (device.getStatusString() != null && "".equals(device.getStatusString())) {
                device.setSearch(true);
            }
            if (device.getDeviceName() != null && !"".equals(device.getDeviceName())) {
                device.setSearch(true);
            }
            if (device.getLocationId() != null && 0 != device.getLocationId()) {
                device.setSearch(true);
            }
            if (device.getCategoryId() != null && 0 != device.getCategoryId()) {
                device.setSearch(true);
            }
        }else{
            device.setDeviceCode(null);
            device.setStatusString(null);
            device.setDeviceName(null);
            device.setLocationId(null);
            device.setCategoryId(null);
        }
        List<DeviceTree> devices = deviceService.findByParent(device);
        String a = JSON.toJSONString(devices, SerializerFeature.WriteMapNullValue);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //JSON在传递过程中是普通字符串形式传递的，这里简单拼接一个做测试
        out.println(a);
        out.flush();
        out.close();

    }

    /**
   	 * 生命周期 
   	 */
    @RequestMapping(value = "lifeCycle", method = RequestMethod.GET)
   	public String lifeCycle(@RequestParam(required = false) Long id, ModelMap model) {
    	Device device = deviceService.getById(id);
//    	MHistoryAlarm hisAlarm = historyAlarmService.getByDeviceId(id);//第一次报警
    	Repair repair = repairService.getByDeviceId(id);//第一次维修
    	MaintainTask maintainTask = maintainTaskService.getBydeviceId(id);//第一次保养
    	InspectionTask inspectionTask = inspectionTaskService.getBydeviceId(id);//第一次巡检
    	List<Document> documentList = documentService.getByDeviceId(id);//文档
    	List<String> yearSet = SetUniqueList.decorate(new ArrayList<String>());
    	//报警
//    	if(null != hisAlarm && null != hisAlarm.getAlarmTime()){
//    		String year = hisAlarm.getAlarmTime().split(" ")[0].split("-")[0];
//    		yearSet.add(year);
//    	}
    	//维修
    	if(null != repair && null != repair.getActualFinishTime()){
    		String year = sdf.format(repair.getActualFinishTime()).split(" ")[0].split("-")[0];
    		yearSet.add(year);
    	}
    	//保养
    	if(null != maintainTask && null != maintainTask.getActualEndTime()){
    		String year = sdf.format(maintainTask.getActualEndTime()).split(" ")[0].split("-")[0];
    		yearSet.add(year);
    	}
    	//巡检
    	if(null != inspectionTask && null != inspectionTask.getActualEndTime()){
    		String year = sdf.format(inspectionTask.getActualEndTime()).split(" ")[0].split("-")[0];
    		yearSet.add(year);
    	}
    	//文档
    	if(null != documentList){
    		for (Document document : documentList) {
				if(null != document.getCreateDate()){
					String year = sdf.format(document.getCreateDate()).split(" ")[0].split("-")[0];
					yearSet.add(year);
				}
			}
    	}
    	List<String> yearList= new ArrayList<>(yearSet);
    	Collections.sort(yearList);
    	Collections.reverse(yearList);
    	List<DeviceTimeLine> deviceTimeLineList = new ArrayList<DeviceTimeLine>();
    	for (String year : yearList) {
    		DeviceTimeLine timeLine = new DeviceTimeLine();
    		timeLine.setYear(year);
    		deviceTimeLineList.add(timeLine);
		}
    	for (DeviceTimeLine deviceTimeLine : deviceTimeLineList) {
    		String orderYear = deviceTimeLine.getYear();
    		//报警
//    		if(null != hisAlarm && null != hisAlarm.getAlarmTime()){
//    			String[] splitDate = hisAlarm.getAlarmTime().split(" ")[0].split("-");
//        		String year = splitDate[0];
//        		if(orderYear.equals(year)){
//        			String content = "第一次报警(描述:"+sdf.format(hisAlarm.getAlarmRemark())+")";
//        			String url = "alarm";
//        			setTimelineContent(deviceTimeLine, splitDate, content, url, hisAlarm.getDeviceId());
//        		}
//        	}
    		//维修
    		if(null != repair && null != repair.getActualFinishTime()){
    			String[] splitDate = sdf.format(repair.getActualFinishTime()).split(" ")[0].split("-");
        		String year = splitDate[0];
        		if(orderYear.equals(year)){
        			String content = "第一次维修(维修班组:"+repair.getRepairGroupName()+",维修完成时间:"+sdf.format(repair.getActualFinishTime())+")";
        			String url = "repair";
        			setTimelineContent(deviceTimeLine, splitDate, content, url, repair.getDeviceId());
        		}
        	}
    		//保养
    		if(null != maintainTask && null != maintainTask.getActualEndTime()){
    			String[] splitDate = sdf.format(maintainTask.getActualEndTime()).split(" ")[0].split("-");
    			String year = splitDate[0];
    			if(orderYear.equals(year)){
    				String content = "第一次保养(保养班组:"+maintainTask.getMaintainGroupName()+",保养完成时间:"+sdf.format(maintainTask.getActualEndTime())+")";
    				String url = "maintain";
    				setTimelineContent(deviceTimeLine, splitDate, content, url, maintainTask.getId());
    			}
    		}
    		//巡检
    		if(null != inspectionTask && null != inspectionTask.getActualEndTime()){
    			String[] splitDate = sdf.format(inspectionTask.getActualEndTime()).split(" ")[0].split("-");
    			String year = splitDate[0];
    			if(orderYear.equals(year)){
    				String content = "第一次巡检(巡检班组:"+inspectionTask.getInspectionGroupName()+",巡检完成时间:"+sdf.format(inspectionTask.getActualEndTime())+")";
    				String url = "inspection";
    				setTimelineContent(deviceTimeLine, splitDate, content, url, inspectionTask.getId());
    			}
    		}
    		//文档
    		if(null != documentList){
        		for (Document document : documentList) {
    				if(null != document.getCreateDate()){
    					String[] splitDate = sdf.format(document.getCreateDate()).split(" ")[0].split("-");
    	    			String year = splitDate[0];
    	    			if(orderYear.equals(year)){
    	    				String content = "";
    	    				String url = "documents";
    	    				Integer type = document.getDocType();
    	    				switch (type) {
							case 1:
								content = "设计文档";
								break;
							case 2:
								content = "招标文档";
								break;
							case 3:
								content = "实施文档";
								break;
							case 4:
								content = "验收文档";
								break;
							default:
								content = "其它";
								break;
							}
    	    				setTimelineContent(deviceTimeLine, splitDate, content, url, document.getDeviceId());
    	    			}
    				}
    			}
        	}
		}
    	model.put("device", device);
    	model.put("yearList", yearList);
    	model.put("deviceTimeLineList", deviceTimeLineList);
   		return "base/device/device_lifeCycle";
   	}
    
    /**
     * 设置设备时间轴数据
     */
    private void setTimelineContent(DeviceTimeLine deviceTimeLine,String[] splitDate,String contents,String urls,Long deviceId){
    	String monthAndDay = splitDate[1] + "月" + splitDate[2] + "日";
		List<DeviceTimeLine> monthList = deviceTimeLine.getDeviceTimeLineList();
    	if(null == monthList){
			monthList = new LinkedList<DeviceTimeLine>();
		}
		if(monthList.size() > 0){
			boolean b = false;
			for (DeviceTimeLine timeLine : monthList) {//同月
				if(monthAndDay.equals(timeLine.getMonthAndDay())){
					DeviceTimeLine content = new DeviceTimeLine();
					content.setId(deviceId);
					content.setContent(contents);
					content.setUrl(urls);
					timeLine.getDeviceTimeLineList().add(content);
					b = true;
					break;
				}
			}
			if(!b){//不同月
				DeviceTimeLine timeLine = new DeviceTimeLine();//时间
				List<DeviceTimeLine> contentList = new ArrayList<DeviceTimeLine>();//内容
				DeviceTimeLine content = new DeviceTimeLine();
				content.setId(deviceId);
				content.setContent(contents);
				content.setUrl(urls);
				contentList.add(content);
				timeLine.setDeviceTimeLineList(contentList);
				timeLine.setMonthAndDay(monthAndDay);
				for (int i = 0; i < monthList.size(); i++) {
					String m = monthList.get(i).getMonthAndDay();
					if(m.compareTo(monthAndDay) == -1){
						monthList.add(i, timeLine);
						break;
					}
				}
			}
		}else{
			DeviceTimeLine timeLine = new DeviceTimeLine();//时间
			List<DeviceTimeLine> contentList = new ArrayList<DeviceTimeLine>();//内容
			DeviceTimeLine content = new DeviceTimeLine();
			content.setId(deviceId);
			content.setContent(contents);
			content.setUrl(urls);
			contentList.add(content);
			timeLine.setDeviceTimeLineList(contentList);
			timeLine.setMonthAndDay(monthAndDay);
			monthList.add(timeLine);
		}
		deviceTimeLine.setDeviceTimeLineList(monthList);
    }
    
    /**
	 * Edit Device
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  String flage,ModelMap model,String showFlag,String from) {
    	model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.DEVICE_STATUS));		
    	model.put("lstZhejiu", DictUtils.getDictList(DictTypeEnum.DEVICE_DEPRECIATION));
    	model.put("lstRepairType", DictUtils.getDictList(DictUtils.DictTypeEnum.REPAIR_TYPE));
		model.put("imagePath", imageServerUrl);
		model.put("imageTempPath", imageTempServerUrl);
		if (null != id) {
			Device device = deviceService.findById(id);
			if (null == device) {
				throw new IllegalArgumentException("Object does not exist");
			}
			if(device.getParentId()!=null){
				Device d = deviceService.findById(device.getParentId());
				if(null !=d){
					String name = d.getDeviceName();
					if(name.equals("ROOT")){
						name = "";
						device.setParentId(null);
					}
					device.setParentName(name);
				}
			}
			model.put("device", device);
			//随机档案
			model.put("listAttachments", attachmentService.getAttachmentByFid(id,Constant.ATTACHEMENT_DEVICE_DOS));	
			
			//文档资料
			model.put("listDocuments", documentService.getByDeviceId(id));	
	    	
			//图片
			List<Attachment>  listAttachments = attachmentService.getAttachmentByFid(id,Constant.ATTACHEMENT_DEVICE_IMG);
			if(!UtilTool.isEmptyList(listAttachments)){
				model.put("imageSysName", listAttachments.get(0).getStorePath());
				model.put("imageShowName", listAttachments.get(0).getFileName());
				model.put("attachId", listAttachments.get(0).getId());
			}
		} 		
		model.put("from", from);
		model.put("flage", flage);
		model.put("showFlag", showFlag);
		if("1".equals(flage)){
			 return "base/device/device_dev_edit";
		}else{
		     return "base/device/device_edit";
		}
		
	}
    /*save  device
    Device object instance 
    */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ScoMessage save(String flag,Device device,String[] fileorignal,String[] filename,String[] delfiles,String[] fileorignal1,String[] filename1,String[] delfiles1) {
		    //随机档案 -----filename:32kje9834ijfoi.jpg,fileorignal:menjin.jpg, delfiles:删除的文件		
		    //设备图片 -----filename1,fileorignal1, delfiles1	
			if(device.getId()==null){
				copyCreateInfo(device);
				device.setTimeAfterMaintain(0L);
				device.setTotalRunningTime(0D);
				if(device.getParentId()==null)device.setParentId(0L);
				deviceService.insert(device,fileorignal,filename,delfiles,fileorignal1,filename1,delfiles1);
			}
			else{
				copyUpdateInfo(device);    
				if(device.getParentId()==null)device.setParentId(0L);
				deviceService.update(device,fileorignal,filename,delfiles,fileorignal1,filename1,delfiles1);	
			}
		if(flag.equals("1")){
			return ScoMessage.success("device/imp_exp/exp",ScoMessage.SAVE_SUCCESS_MSG);
		}else{
			return ScoMessage.success("device/device/list",ScoMessage.SAVE_SUCCESS_MSG);
		}
	}
    /*MasterTable.Name,0)%>  
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {	
		deviceService.deleteByIds(ids);	
		return ScoMessage.success("device/device/list",ScoMessage.DELETE_SUCCESS_MSG);
	}   

    /*
    show 扩展属性
    */
    @ResponseBody
    @RequestMapping("getExtendProperty")
	public List<DeviceProperty> getExtendProperty(Long categoryId, Long deviceId,ModelMap model) {
		Map mapSql = new HashMap();
    	mapSql.put("deviceId",deviceId);
    	mapSql.put("categoryId",categoryId);
    	List<DeviceProperty> lstProperty = devicePropertyService.findList("findByDeviceId", mapSql);
		return lstProperty;
	}
    /*
    	按设备id查询启停记录
	*/
	@ResponseBody
	@RequestMapping("findstartStopRecords")
	public Pageable<DeviceStartStopRecords> findstartStopRecords( Pagination<DeviceStartStopRecords> pager, DeviceStartStopRecords deviceStartStopRecords,
			ModelMap model) {
		return deviceStartStopRecordsService.findByCondition(pager,deviceStartStopRecords);
	}
	
	/**根据id，parent_id重建lfg，rgt
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "rebuildDeviceTree", method = RequestMethod.POST)     
	public ScoMessage rebuildDeviceTree() {
		Date start=new Date();
		log.info("重建设备档案树开始.....");
		deviceService.rebuildDeviceTree();
		log.info("重建设备档案树结束,耗时["+DateUtils.dateBetweenHumanRead(start, new Date())+"]");
		return ScoMessage.success("device/device/list",ScoMessage.DEFAULT_SUCCESS_MSG);
	}   
}
