package com.ywy.entity.clue;

import com.ywy.annotation.Table;
import com.ywy.annotation.ID;
import java.util.Date;

@Table("mg_clue_auth_audit")
public class MgClueAuthAudit {
	@ID
	private long id;

	private long parentindustryid;

	private String name;

	private Date createtime;

	private long creatorid;

	private long customerid;

	private long status;

	private String rejectnote;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public long getParentindustryid() {
		return this.parentindustryid;
	}

	public void setParentindustryid(long parentindustryid) {
		this.parentindustryid = parentindustryid;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public long getCreatorid() {
		return this.creatorid;
	}

	public void setCreatorid(long creatorid) {
		this.creatorid = creatorid;
	}
	public long getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(long customerid) {
		this.customerid = customerid;
	}
	public long getStatus() {
		return this.status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
	public String getRejectnote() {
		return this.rejectnote;
	}

	public void setRejectnote(String rejectnote) {
		this.rejectnote = rejectnote;
	}
}