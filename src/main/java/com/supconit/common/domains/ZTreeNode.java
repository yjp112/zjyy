package com.supconit.common.domains;


public class ZTreeNode {
	
	private String id;
	private String pId;
	private String name;
	private boolean open;
	private boolean nocheck;
	private Integer treeId;
	
	public Integer getTreeId() {
		return treeId;
	}
	public void setTreeId(Integer treeId) {
		this.treeId = treeId;
	}
	public boolean isNocheck() {
		return nocheck;
	}
	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPId() {
		return pId;
	}
	public void setPId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
}
