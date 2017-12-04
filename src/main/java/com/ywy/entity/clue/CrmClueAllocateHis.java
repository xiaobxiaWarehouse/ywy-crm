package com.ywy.entity.clue;

import com.ywy.annotation.Table;
import com.ywy.annotation.ID;
import java.util.Date;

@Table("crm_clue_allocate_his")
public class CrmClueAllocateHis {
	@ID
	private long id;

	private long customerId;

	private long assignTo;

	private long clueNum;

	private Date createTime;

	private long industryId;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	* @return customerId
	*/
	public long getCustomerId() {
		return customerId;
	}

	/**
	* @param customerId 要设置的 customerId
	*/
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
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
	* @return clueNum
	*/
	public long getClueNum() {
		return clueNum;
	}

	/**
	* @param clueNum 要设置的 clueNum
	*/
	public void setClueNum(long clueNum) {
		this.clueNum = clueNum;
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
	
	
}