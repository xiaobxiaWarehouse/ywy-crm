package com.ywy.entity.ext;

public class UserDetail {
	private long id;
	private String quanxian;
	
	public UserDetail(long id, String quanxian) {
		this.id = id;
		this.quanxian = quanxian;
	}

	public long getId() {
		return id;
	}

	public String getQuanxian() {
		return quanxian;
	}

}
