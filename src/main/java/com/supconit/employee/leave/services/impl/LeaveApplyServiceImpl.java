package com.supconit.employee.leave.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.utils.Constant;
import com.supconit.employee.leave.daos.LeaveApplyDao;
import com.supconit.employee.leave.entities.LeaveApply;
import com.supconit.employee.leave.services.LeaveApplyService;

import hc.base.domains.Pageable;
import hc.bpm.context.annotations.BpmSupport;
import hc.bpm.services.ProcessService;


@Service
public class LeaveApplyServiceImpl extends AbstractBaseBusinessService<LeaveApply, Long> implements LeaveApplyService{
	
	@Autowired
	private LeaveApplyDao leaveApplyDao;
	
	@Autowired
	private AttachmentDao attachmentDao;
	
    @Autowired
    private ProcessService processService;
	
	@Override
	public void deleteById(Long id) {
		leaveApplyDao.deleteById(id);
	}

	@Override
	public LeaveApply getById(Long id) {
		return leaveApplyDao.getById(id);
	}

	@Override
	public void insert(LeaveApply leaveApply) {
		leaveApplyDao.insert(leaveApply);
	}

	@Override
	public void update(LeaveApply leaveApply) {
		leaveApplyDao.update(leaveApply);
	}

	@Override
	public Pageable<LeaveApply> findByPage(Pageable<LeaveApply> pager,LeaveApply condition) {
		return leaveApplyDao.findByPage(pager,condition);
	}
	
    @Override
	@Transactional
	public void update(LeaveApply leaveApply,String[] fileorignal,String[] filename,String[] delfiles,String fileLength){     
		try {
		
			leaveApplyDao.update(leaveApply);
	  	    //update 诊断书
	    	attachmentDao.saveAttachements(leaveApply.getId(),Constant.ATTACHEMENT_LEAVE,fileorignal, filename, delfiles,fileLength);
  	
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessDoneException("保存失败");  
		}
	}
    
    
    @Override
	@Transactional
	public void insert(LeaveApply leaveApply,String[] fileorignal,String[] filename,String[] delfiles, String fileLength){     
		try {
		
			leaveApplyDao.insert(leaveApply);
	  	    //update 诊断书
	    	attachmentDao.saveAttachements(leaveApply.getId(),Constant.ATTACHEMENT_LEAVE,fileorignal, filename, delfiles,fileLength);
  	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessDoneException("保存失败");  
		}
	}
    
    @Override
	@Transactional
	@BpmSupport(businessKey = "#leaveApply.id", businessDomain = "#leaveApply", variableKeys = "{'task'}", variables = "{#leaveApply}")
	public void startProcess(LeaveApply leaveApply,String[] fileorignal,String[] filename,String[] delfiles,String fileLenth){     
    	insert( leaveApply,fileorignal,filename,delfiles,fileLenth);
	}
    
    @Override
	@Transactional
	@BpmSupport(businessKey = "#leaveApply.id", businessDomain = "#leaveApply", variableKeys = "{'task'}", variables = "{#leaveApply}")
	public void submitProcess(LeaveApply leaveApply,String[] fileorignal,String[] filename,String[] delfiles,String fileLength){     
    	if (null == leaveApply.getId()) {
            this.insert(leaveApply,fileorignal,filename,delfiles,fileLength);
        } else {
            this.update(leaveApply,fileorignal,filename,delfiles,fileLength);
        }
	}   
    
    @Override
    @Transactional
    public void deleteProcessInstance(Long id) {
        LeaveApply leave=leaveApplyDao.getById(id);
        deleteById(id);
        processService.deleteProcessInstance(leave.getProcessInstanceId());
    }

	@Override
	public LeaveApply sumByConditions(LeaveApply condition) {
		return leaveApplyDao.sumByConditions(condition);
	}


}
