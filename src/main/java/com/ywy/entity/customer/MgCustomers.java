
package com.ywy.entity.customer;
import com.ywy.annotation.Table;
import com.ywy.annotation.ID;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Table("mg_customers")
public class MgCustomers {
	@ID
	private long id;

	private String companyName;

	private String industryName;

	private String contactor;

	private String contactPhone;

	private Date validStartTime;

	private Date validEndTime;

	private long status;

	private long limitAcctNum;

	private Date createTime;

	private long creatorId;

	private long usedAcctNum;

	private String contatorEmail;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIndustryName() {
		return this.industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getContactor() {
		return this.contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getValidStartTime() {
		return this.validStartTime;
	}

	public void setValidStartTime(Date validStartTime) {
		this.validStartTime = validStartTime;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getValidEndTime() {
		return this.validEndTime;
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}
	public long getStatus() {
		return this.status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
	public long getLimitAcctNum() {
		return this.limitAcctNum;
	}

	public void setLimitAcctNum(long limitAcctNum) {
		this.limitAcctNum = limitAcctNum;
	}
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}
	public long getUsedAcctNum() {
		return this.usedAcctNum;
	}

	public void setUsedAcctNum(long usedAcctNum) {
		this.usedAcctNum = usedAcctNum;
	}
	public String getContatorEmail() {
		return this.contatorEmail;
	}

	public void setContatorEmail(String contatorEmail) {
		this.contatorEmail = contatorEmail;
	}
}