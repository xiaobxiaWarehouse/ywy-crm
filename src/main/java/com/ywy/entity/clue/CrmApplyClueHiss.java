package com.ywy.entity.clue;

import java.util.Date;

public class CrmApplyClueHiss {
	
	private Date createTime;
	
	private String clueName;
	
	private long syncNum;

	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getClueName() {
		return clueName;
	}

	public void setClueName(String clueName) {
		this.clueName = clueName;
	}

	public long getSyncNum() {
		return syncNum;
	}

	public void setSyncNum(long syncNum) {
		this.syncNum = syncNum;
	}
	
	
}
