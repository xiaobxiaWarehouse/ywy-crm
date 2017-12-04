package com.ywy.entity.sys;

import com.ywy.annotation.Table;
import com.ywy.util.DateUtil;
import com.ywy.annotation.ID;
import com.ywy.annotation.Ignore;

import java.util.Date;

@Table("mg_account")
public class MgAccount {
	@ID
	private long id;

	private String name;

	private String account;

	private String password;

	private Date lstLoginTime;

	private long role;

	private Date createTime;

	private long creatorId;

	private String mobile;

	private long status;

	private String industryIds;

	private String department;

	private String email;

	private String fax;

	private String job;

	private long sex;

	private String birth;

	private String idNo;
	@Ignore
	private String industryNames;
	

	public String getIndustryNames() {
		return industryNames;
	}

	public void setIndustryNames(String industryNames) {
		this.industryNames = industryNames;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getLstLoginTime() {
		return this.lstLoginTime == null ? null : DateUtil.dt14LongFormat(this.lstLoginTime);
	}

	public void setLstLoginTime(Date lstLoginTime) {
		this.lstLoginTime = lstLoginTime;
	}
	public long getRole() {
		return this.role;
	}

	public void setRole(long role) {
		this.role = role;
	}
	public String getCreateTime() {
		return this.createTime == null ? DateUtil.dt14LongFormat(new Date()) : DateUtil.dt14LongFormat(this.createTime);
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
	
	public long getStatus() {
		return this.status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
	public String getIndustryIds() {
		return this.industryIds;
	}

	public void setIndustryIds(String industryIds) {
		this.industryIds = industryIds;
	}
	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	public long getSex() {
		return this.sex;
	}

	public void setSex(long sex) {
		this.sex = sex;
	}
	public String getBirth() {
		return this.birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getIdNo() {
		return this.idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	* @return mobile
	*/ 
	public String getMobile() {
		return mobile;
	}

	/**
	* @param mobile 要设置的 mobile
	*/
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}