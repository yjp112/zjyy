package com.supconit.base.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.VideoDetail;
import com.supconit.base.services.VideoDetailService;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
@Controller
@RequestMapping("videoDetail")
public class VideoDetailController extends BaseControllerSupport{
	private transient static final Logger log = LoggerFactory
			.getLogger(VideoDetailController.class);
	@Autowired
	private VideoDetailService videoDetailService;
	   /*
    get "videoDetail" list
    */
    @RequestMapping("list")
	public String list() {
		
		return "base/video/videoDetail_list";
	}
 
    @ResponseBody
    @RequestMapping("page")
	public Pageable<VideoDetail> page( Pagination<VideoDetail> pager, @ModelAttribute VideoDetail videoDetail,
			ModelMap model) {
		return videoDetailService.findByCondition(pager, videoDetail);
	}
    /*
    save  videoDetail
    VideoDetail object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(VideoDetail videoDetail) {
		ScoMessage msg = ScoMessage.success("videoDetail/list",ScoMessage.SAVE_SUCCESS_MSG);		
         if(videoDetail.getId()==null){
        	 copyCreateInfo(videoDetail);
            videoDetailService.insert(videoDetail);	
        }
        else{
             
            videoDetailService.update(videoDetail);
        }
            
		
		
		return msg;
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		
		videoDetailService.deleteByIds(ids);
		
		
		return ScoMessage.success(".",ScoMessage.DELETE_SUCCESS_MSG);
	}   
    
    /**
	 * Edit VideoDetail
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  ModelMap model,String showFlag) {
		if (null != id) {
			VideoDetail videoDetail = videoDetailService.getById(id);
			if (null == videoDetail) {
				throw new IllegalArgumentException("Object does not exist");
			}
			
			model.put("videoDetail", videoDetail);			
		}		
		model.put("showFlag", showFlag);
		return "base/video/videoDetail_edit";
	}
}
