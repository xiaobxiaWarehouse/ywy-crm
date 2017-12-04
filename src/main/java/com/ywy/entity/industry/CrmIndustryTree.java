package com.ywy.entity.industry;

import java.util.List;

import com.ywy.annotation.ID;
import com.ywy.annotation.Table;

@Table("mg_industry")
public class CrmIndustryTree {
	@ID
	private long id;

	private String nodeName;

	private long level;

	private long parentId;

	private int childFlag;
	
	
	private List<CrmIndustryTree> children;
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public long getLevel() {
		return this.level;
	}

	public void setLevel(long level) {
		this.level = level;
	}
	public long getParentId() {
		return this.parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	/**
	* @return childFlag
	*/
	public int getChildFlag() {
		return childFlag;
	}

	/**
	* @param childFlag 要设置的 childFlag
	*/
	public void setChildFlag(int childFlag) {
		this.childFlag = childFlag;
	}

	/**
	* @return children
	*/ 
	public List<CrmIndustryTree> getChildren() {
		return children;
	}

	/**
	* @param children 要设置的 children
	*/
	public void setChildren(List<CrmIndustryTree> children) {
		this.children = children;
	}


}