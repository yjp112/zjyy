package com.supconit.maintain.services.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
@Service("maintainTaskPublishService")
public class TaskPublishServiceImpl implements JavaDelegate{
	private Expression taskId; 
	@Override
	public void execute(DelegateExecution paramDelegateExecution) throws Exception {
		Long id=(Long) taskId.getValue(paramDelegateExecution);
		// 修改任务单状态
		System.err.println("#######################"+id);
		
	}

}
