package com.ywy.entity.clue;

import com.ywy.annotation.Table;
import com.ywy.annotation.ID;
import java.util.Date;

@Table(value="crm_clue_allocate", modValue="50", splitFieldName="customerId")
public class CrmClue {
	@ID
	private long id;

	private long industryId;

	private String description;

	private String domain;

	private String name;

	private String clueId;

	private long validFlag;

	private long assignTo;

	private Date createTime;

	private long transferId;
	
	private long customerId;
	
	private String title;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	* @return industryId
	*/
	public long getIndustryId() {
		return industryId;
	}

	/**
	* @param industryId 要设置的 industryId
	*/
	public void setIndustryId(long industryId) {
		this.industryId = industryId;
	}

	/**
	* @return clueId
	*/
	public String getClueId() {
		return clueId;
	}

	/**
	* @param clueId 要设置的 clueId
	*/
	public void setClueId(String clueId) {
		this.clueId = clueId;
	}

	/**
	* @return validFlag
	*/
	public long getValidFlag() {
		return validFlag;
	}

	/**
	* @param validFlag 要设置的 validFlag
	*/
	public void setValidFlag(long validFlag) {
		this.validFlag = validFlag;
	}

	/**
	* @return assignTo
	*/
	public long getAssignTo() {
		return assignTo;
	}

	/**
	* @param assignTo 要设置的 assignTo
	*/
	public void setAssignTo(long assignTo) {
		this.assignTo = assignTo;
	}

	/**
	* @return createTime
	*/
	public Date getCreateTime() {
		return createTime;
	}

	/**
	* @param createTime 要设置的 createTime
	*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	* @return transferId
	*/
	public long getTransferId() {
		return transferId;
	}

	/**
	* @param transferId 要设置的 transferId
	*/
	public void setTransferId(long transferId) {
		this.transferId = transferId;
	}

	/**
	* @return title
	*/ 
	public String getTitle() {
		return title;
	}

	/**
	* @param title 要设置的 title
	*/
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}