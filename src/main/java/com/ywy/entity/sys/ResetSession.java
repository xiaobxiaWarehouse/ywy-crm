package com.ywy.entity.sys;

import java.util.Date;

import com.ywy.annotation.ID;
import com.ywy.annotation.Table;

@Table("reset_session")
public class ResetSession {
	@ID
	private long id;
	
	private long accountId;
	
	private String token;

	private Date createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
