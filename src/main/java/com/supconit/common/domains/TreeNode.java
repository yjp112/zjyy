package com.supconit.common.domains;

import java.util.List;

import com.supconit.common.web.entities.AuditExtend;

@SuppressWarnings("rawtypes")
public class TreeNode<T extends TreeNode> extends AuditExtend{
	private static final long serialVersionUID = 6721204452622001954L;
	private Long id;
	private Long pid;
	private String name;
	private Long lft;
	private Long rgt;
	private Integer lvl;
	private Integer sortIdx;
	//Transient
	private Long width;
	private List<T> children;
	private T parent;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLft() {
		return lft;
	}

	public void setLft(Long lft) {
		this.lft = lft;
	}

	public Long getRgt() {
		return rgt;
	}

	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}

	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	public Integer getSortIdx() {
		return sortIdx;
	}

	public void setSortIdx(Integer sortIdx) {
		this.sortIdx = sortIdx;
	}

	public Integer getLvl() {
			return lvl;
	}
	public Long getWidth() {
		if(width!=null){
			return width;
		}
		if(rgt!=null||lft!=null){
			return rgt - lft + 1;
		}
		return null;
	}
	
	public Long getChidrenSize(){
		if(getWidth()==null){
			return null;
		}
		return getWidth()/2-1;
	}

	public boolean hasChildren(){
		return getChidrenSize()!=0;
	}

	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", pid=" + pid + ", name=" + name + ", lft=" + lft + ", rgt=" + rgt + ", lvl="
				+ lvl + ", sortIdx=" + sortIdx + ", width=" + width + ", getWidth()=" + getWidth()
				+ ", getChidrenSize()=" + getChidrenSize() + ", hasChildren()=" + hasChildren() + "]";
	}

	
} 