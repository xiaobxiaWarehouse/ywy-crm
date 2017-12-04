package com.ywy.entity.sys;

import com.ywy.annotation.ID;
import com.ywy.annotation.Table;

@Table("crm_account")
public class CrmAccountForCule {
	@ID
	private long id;

	private String name;


	private String department;

	private String email;

	private String job;

	private long clueNum;
	
	private String mobile;
	

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
	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	public long getClueNum() {
		return this.clueNum;
	}

	public void setClueNum(long clueNum) {
		this.clueNum = clueNum;
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