package com.ywy.entity.clue;

import com.ywy.annotation.Table;
import com.ywy.annotation.ID;
import java.util.Date;

@Table("crm_clue_apply")
public class CrmApplyClue {
	@ID
	private long id;

	private long submitorId;

	private long customerId;

	private Date createTime;

	private long industryId;

	private long status;

	private long dataReadyFlag;

	private String rejectNote;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public long getSubmitorId() {
		return this.submitorId;
	}

	public void setSubmitorId(long submitorId) {
		this.submitorId = submitorId;
	}
	public long getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getIndustryId() {
		return this.industryId;
	}

	public void setIndustryId(long industryId) {
		this.industryId = industryId;
	}
	public long getStatus() {
		return this.status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
	public long getDataReadyFlag() {
		return this.dataReadyFlag;
	}

	public void setDataReadyFlag(long dataReadyFlag) {
		this.dataReadyFlag = dataReadyFlag;
	}
	public String getRejectNote() {
		return this.rejectNote;
	}

	public void setRejectNote(String rejectNote) {
		this.rejectNote = rejectNote;
	}
}