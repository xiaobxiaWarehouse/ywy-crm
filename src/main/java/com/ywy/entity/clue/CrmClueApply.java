package com.ywy.entity.clue;

import com.ywy.annotation.Table;
import com.ywy.annotation.ID;
import java.util.Date;

@Table("crm_clue_apply")
public class CrmClueApply {
	@ID
	private long id;

	private long submitorId;

	private long customerId;

	private Date createtime;

	private long industryId;

	private long status;

	private long datareadyFlag;

	private String rejectNote;

	private String searchTypeCode;
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public long getStatus() {
		return this.status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	/**
	* @return submitorId
	*/
	public long getSubmitorId() {
		return submitorId;
	}

	/**
	* @param submitorId 要设置的 submitorId
	*/
	public void setSubmitorId(long submitorId) {
		this.submitorId = submitorId;
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
	* @return datareadyFlag
	*/
	public long getDatareadyFlag() {
		return datareadyFlag;
	}

	/**
	* @param datareadyFlag 要设置的 datareadyFlag
	*/
	public void setDatareadyFlag(long datareadyFlag) {
		this.datareadyFlag = datareadyFlag;
	}

	/**
	* @return rejectNote
	*/
	public String getRejectNote() {
		return rejectNote;
	}

	/**
	* @param rejectNote 要设置的 rejectNote
	*/
	public void setRejectNote(String rejectNote) {
		this.rejectNote = rejectNote;
	}

	public String getSearchTypeCode() {
		return searchTypeCode;
	}

	public void setSearchTypeCode(String searchTypeCode) {
		this.searchTypeCode = searchTypeCode;
	}
	
}