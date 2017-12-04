package com.ywy.entity.clue;

import com.ywy.annotation.Table;

@Table(value="crm_clue_allocate", modValue="50")
public class CrmAllocateClueHis {
	
	private long industryId;
	
	private int allocateNum;
	
	private int totalNum;
	
	private String industryName;

	
	public long getIndustryId() {
		return industryId;
	}

	public void setIndustryId(long industryId) {
		this.industryId = industryId;
	}

	public int getAllocateNum() {
		return allocateNum;
	}

	public void setAllocateNum(int allocateNum) {
		this.allocateNum = allocateNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/**
	* @return industryName
	*/ 
	public String getIndustryName() {
		return industryName;
	}

	/**
	* @param industryName 要设置的 industryName
	*/
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	
}