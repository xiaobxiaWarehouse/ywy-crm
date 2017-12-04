package com.ywy.entity.industry;

import com.ywy.annotation.Table;
import com.ywy.annotation.ID;
import java.util.Date;

@Table("mg_industry")
public class CrmIndustry {
	@ID
	private long id;

	private String nodeName;

	private long level;

	private long parentid;

	private Date createtime;

	private long creatorid;

	private String path;

	private long childflag;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public long getLevel() {
		return this.level;
	}

	public void setLevel(long level) {
		this.level = level;
	}
	public long getParentid() {
		return this.parentid;
	}

	public void setParentid(long parentid) {
		this.parentid = parentid;
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
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public long getChildflag() {
		return this.childflag;
	}

	public void setChildflag(long childflag) {
		this.childflag = childflag;
	}
}