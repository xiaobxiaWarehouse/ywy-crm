package com.ywy.entity.clue;

import com.ywy.annotation.ID;

public class CrmClueAllocates {
	@ID
	private String name;

	private long clueNum;
	
	private String contactPhone;

	private String department;

	private String email;

	private String fax;

	private String job;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getClueNum() {
		return clueNum;
	}

	public void setClueNum(long clueNum) {
		this.clueNum = clueNum;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}


	
}