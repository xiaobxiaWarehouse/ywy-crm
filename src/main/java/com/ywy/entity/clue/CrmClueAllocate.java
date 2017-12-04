package com.ywy.entity.clue;

import com.ywy.annotation.Table;
import com.ywy.annotation.ID;
@Table("crm_clue_subscribe")
public class CrmClueAllocate {
	@ID
	private long id;

	private long customerid;

	private long industryid;

	private long lstclueproctime;

	private long totalclue;

	private long allocatenum;

	private long cluestartid;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public long getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(long customerid) {
		this.customerid = customerid;
	}
	public long getIndustryid() {
		return this.industryid;
	}

	public void setIndustryid(long industryid) {
		this.industryid = industryid;
	}
	public long getLstclueproctime() {
		return this.lstclueproctime;
	}

	public void setLstclueproctime(long lstclueproctime) {
		this.lstclueproctime = lstclueproctime;
	}
	public long getTotalclue() {
		return this.totalclue;
	}

	public void setTotalclue(long totalclue) {
		this.totalclue = totalclue;
	}
	public long getAllocatenum() {
		return this.allocatenum;
	}

	public void setAllocatenum(long allocatenum) {
		this.allocatenum = allocatenum;
	}
	public long getCluestartid() {
		return this.cluestartid;
	}

	public void setCluestartid(long cluestartid) {
		this.cluestartid = cluestartid;
	}
}