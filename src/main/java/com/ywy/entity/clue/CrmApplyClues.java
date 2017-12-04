package com.ywy.entity.clue;

import java.util.Date;

public class CrmApplyClues {
	private String parentIndustryName;
	
	private Date createTime;
	
	private String name;
	
	private String customerName;
	
	private long applyId;
	
	private String path;
	
	private long status;

	private String rejectNote;
	
	private String searchTypeCode;
	
	public String getRejectNote() {
		return rejectNote;
	}

	public void setRejectNote(String rejectNote) {
		this.rejectNote = rejectNote;
	}

	public String getParentIndustryName() {
		return parentIndustryName;
	}

	public void setParentIndustryName(String parentIndustryName) {
		this.parentIndustryName = parentIndustryName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getApplyId() {
		return applyId;
	}

	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}

	/**
	* @return path
	*/ 
	public String getPath() {
		return path;
	}

	/**
	* @param path 要设置的 path
	*/
	public void setPath(String path) {
		this.path = path;
	}

	/**
	* @return status
	*/ 
	public long getStatus() {
		return status;
	}

	/**
	* @param status 要设置的 status
	*/
	public void setStatus(long status) {
		this.status = status;
	}

	public String getSearchTypeCode() {
		return searchTypeCode;
	}

	public void setSearchTypeCode(String searchTypeCode) {
		this.searchTypeCode = searchTypeCode;
	}
	
}
