package com.supconit.common.web.entities;

import java.io.Serializable;

public class ExProcessInstance implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String processDefinitionId;
	private String businessKey;
	private String currentNode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getCurrentNode() {
		return currentNode;
	}
	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}
	
	
}
