package com.ywy.entity.clue;

import com.ywy.annotation.Table;
import com.ywy.annotation.ID;
import java.util.Date;

@Table("mg_clue_sync_his")
public class CrmApplyClueHis {
	@ID
	private long id;

	private long industryId;

	private long customerId;

	private long syncNum;

	private Date createTime;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public long getIndustryId() {
		return this.industryId;
	}

	public void setIndustryId(long industryId) {
		this.industryId = industryId;
	}
	public long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getSyncNum() {
		return this.syncNum;
	}

	public void setSyncNum(long syncNum) {
		this.syncNum = syncNum;
	}
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}