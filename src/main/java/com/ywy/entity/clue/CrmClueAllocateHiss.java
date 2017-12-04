package com.ywy.entity.clue;

import java.util.Date;

public class CrmClueAllocateHiss {
	private String name;
	
	private Date createTime;
	
	private long clueNum;
	
	private String industryName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getClueNum() {
		return clueNum;
	}

	public void setClueNum(long clueNum) {
		this.clueNum = clueNum;
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