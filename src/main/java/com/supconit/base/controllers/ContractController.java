
package com.supconit.base.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.Contract;
import com.supconit.base.services.AttachmentService;
import com.supconit.base.services.ContractService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

@Controller
@RequestMapping("device/contract")
public class ContractController extends BaseControllerSupport {

    
	@Autowired
	private ContractService contractService;
	@Autowired
	private AttachmentService attachmentService;
	//配置文件长度
	private String fileLength="";
    /*
    get "contract" list
    */
	
    @RequestMapping("list")
	public String list(ModelMap model) {
    	model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.CONTRACT_STATUS));
    	return "base/contract/contract_list";
	}
    /*
    show datagrid
    */
    @ResponseBody
    @RequestMapping("page")
	public Pageable<Contract> page( Pagination<Contract> pager, @ModelAttribute Contract contract,
			ModelMap model) {
		return contractService.findByCondition(pager, contract);
	}

    /*
    save  contract
    Contract object instance 
    */
	@ResponseBody
	@RequestMapping(value ="save", method = RequestMethod.POST)    
	public ScoMessage save(Contract contract,String[] fileorignal,String[] filename,String[] delfiles) {
		 //filename:新增的文件原名 系统名,fileorignal:新增的文件新名 显示名, delfiles:删除的文件	
		ScoMessage msg = ScoMessage.success("x",ScoMessage.SAVE_SUCCESS_MSG);
		if("".equals(fileLength)){//配置上传文件大小
			fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "委外合同");
		}
		if(contract.getId()==null){
			copyCreateInfo(contract);
			if("".equals(contract.getBeginDate())){contract.setBeginDate(null);}
			if("".equals(contract.getEndDate())){contract.setEndDate(null);}
			contractService.insert(contract,fileorignal,filename,delfiles,fileLength);	
		}
		else{
			copyUpdateInfo(contract);    
			if("".equals(contract.getBeginDate())){contract.setBeginDate(null);}
			if("".equals(contract.getEndDate())){contract.setEndDate(null);}
			contractService.update(contract,fileorignal,filename,delfiles,fileLength);	
		}
		return msg;
	}

    /*delete   
    */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)     
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		ScoMessage msg = ScoMessage.success("device/contract/list",ScoMessage.DELETE_SUCCESS_MSG);
		try{
			contractService.deleteByIds(ids);	
		}catch(Exception e){
	       	e.printStackTrace();
	       	msg = ScoMessage.fail(ScoMessage.DEFAULT_FAIL_MSG);
		}
		return msg;
	}   
    
    /**
	 * Edit Contract
	 * @param id  ID 
	 * @return
	 */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Long id,  ModelMap model,String showFlag) {
		if (null != id) {
			Contract contract = contractService.getById(id);
			if (null == contract) {
				throw new IllegalArgumentException("Object does not exist");
			}
			
			model.put("contract", contract);
			//随机档案
			model.put("listAttachments", attachmentService.getAttachmentByFid(id,Constant.ATTACHEMENT_CONTRACT));
		}		
		//配置上传文件大小
		fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "委外合同");
		model.put("fileLength", fileLength); 
		model.put("lstStatus", DictUtils.getDictList(DictTypeEnum.CONTRACT_STATUS));
		model.put("showFlag", showFlag);
		return "base/contract/contract_edit";
	}
    
}
