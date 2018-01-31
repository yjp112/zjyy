package com.supconit.inspection.controllers;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.Attachment;
import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.AttachmentService;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.DutyGroupPersonService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.inspection.services.InspectionSpareService;
import com.supconit.inspection.services.InspectionTaskContentService;
import com.supconit.inspection.services.InspectionTaskService;
import com.supconit.inspection.entities.InspectionSpare;
import com.supconit.inspection.entities.InspectionTask;
import com.supconit.inspection.entities.InspectionTaskContent;

/**
 * 巡检任务控制类
 * @author yuhuan
 * @日期 2015/09
 */
@Controller
@RequestMapping("inspection/task")
public class InspectionTaskController extends BaseControllerSupport {
	@Autowired
	private InspectionTaskService inspectionTaskService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private InspectionTaskContentService inspectionTaskContentService;
	@Autowired
	private InspectionSpareService inspectionSpareService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private DutyGroupPersonService dutyGroupPersonService;
	
	//配置文件长度
	private String fileLength="";
	/**
	 * 跳转到列表
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(String deviceCode,ModelMap model) {
		List<DeviceCategory> deviceCategoryList = deviceCategoryService.findAll();
        model.addAttribute("deviceCategoryList", deviceCategoryList);//设备类别树
        List<GeoArea> geoAreaList = geoAreaService.findTree();
		model.put("geoAreaList", geoAreaList);//地理区域树
		model.put("deviceCode", deviceCode);
        model.put("inspectionTaskStatusList", DictUtils.getDictList(DictTypeEnum.INSPECTION_TASK_STATUS)); 
		return "inspection/task/list";
    }
	
	/**
	 * 列表查询
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
	public Pageable<InspectionTask> dolist(InspectionTask condition,
			Pagination<InspectionTask> pager,
            ModelMap model) {
		return inspectionTaskService.findByCondition(pager, condition);
	}
	
	/**
	 * 跳转到编辑页
	 */
	@RequestMapping(value="edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false)Boolean viewOnly,@RequestParam(required = false)Boolean hisView,
			@RequestParam(required = false)Boolean view,ModelMap model, @RequestParam(required = false) Long id,String openStyle) {
		InspectionTask task = inspectionTaskService.getById(id);
		String inspectionCode = task.getInspectionCode();
		List<InspectionTaskContent> inspectionTaskContentList = inspectionTaskContentService.selectInspectionTaskContentList(inspectionCode);
		List<InspectionSpare> inspectionSpareList = inspectionSpareService.selectInspectionSpareList(inspectionCode);
		task.setInspectionTaskContentList(inspectionTaskContentList);
		task.setInspectionSpareList(inspectionSpareList);
		model.put(PROCESS_INSTANCE_ID,task.getProcessInstanceId());
		model.put("task",task);
		model.put("viewOnly",viewOnly==null? false:viewOnly);
		if(null != hisView && hisView){
			view = false;
		}
		model.put("view",view);
		if(null != view && view && null == viewOnly){
			model.put("show",true);
		}else{
			model.put("show",false);	
		}
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "巡检");
		}
		model.put("fileLength", fileLength);
		model.put("openStyle", openStyle==null? "":openStyle);
		model.put("inspectionTaskResultList", DictUtils.getDictList(DictTypeEnum.INSPECTION_TASK_STATUS));
		model.put("listAttachments", attachmentService.getAttachmentByFid(id,Constant.ATTACHEMENT_INSPECTION));
		return "inspection/task/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public ScoMessage doEdit(InspectionTask task,String[] fileorignal,String[] filename,String[] delfiles) {
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "巡检");
		}
		inspectionTaskService.update(task, fileorignal, filename, delfiles,fileLength);
		return ScoMessage.success("workspace/todo/list",ScoMessage.SAVE_SUCCESS_MSG);
	}
	
	/**
	 * 提交
	 */
	@ResponseBody
	@RequestMapping(value="submit", method = RequestMethod.POST)
	public ScoMessage submit(InspectionTask task,String[] fileorignal,String[] filename,String[] delfiles) {
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "巡检");
		}
		if(null == task.getActualStartTime()){
			return ScoMessage.error("实际开始时间不能为空");
		}
		if(null == task.getActualEndTime()){
			return ScoMessage.error("实际结束时间不能为空");
		}
		if(null == fileorignal || fileorignal.length == 0){
			List<Attachment> attList = attachmentService.getAttachmentByFid(task.getId(),Constant.ATTACHEMENT_INSPECTION);
			if(null == attList || attList.size() == 0){
				return ScoMessage.error("请先上传巡检结果纸质附件");
			}
		}
		if("INSPECTION_TASK_COMMIT".equals(task.getBpm_ts_id())){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("groupId", task.getInspectionGroupId());
			params.put("postId", 3);//主管工程师
			List<String> codeList = dutyGroupPersonService.findPersonCodeByGroupIdAndPostId(params);
			if(null != codeList && codeList.size() > 0){
				task.setProcessInstanceName("巡检单:" + task.getInspectionCode());
				task.setHandlePersonCode(codeList.get(0));
			}else{
				return ScoMessage.error("该班组未指定主管工程师,无法进行提交");
			}
			inspectionTaskService.submit(task, fileorignal, filename, delfiles,fileLength);
		}else if("INSPECTION_TASK_FINISH".equals(task.getBpm_ts_id())){
			inspectionTaskService.submit(task);
		}else{
			return ScoMessage.error("您提交的请求非法");
		}
		return ScoMessage.success("workspace/todo/list","提交成功");
	}
}
