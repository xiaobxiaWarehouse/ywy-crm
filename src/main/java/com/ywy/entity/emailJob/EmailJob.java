package com.ywy.entity.emailJob;

import java.util.Date;

import com.ywy.annotation.ID;
import com.ywy.annotation.Table;
@Table("mg_email_job")
public class EmailJob {

	@ID
	private long id;

	private long source;
	
	private Date createTime;
	
	private long status;
	
	private long sendFrom;
	
	private String sendToEmail;
	
	private String content;
	
	private int num;
	
	private String remarks;
	
	private String title;
	/**
	* @return id
	*/
	public long getId() {
		return id;
	}

	/**
	* @param id 要设置的 id
	*/
	public void setId(long id) {
		this.id = id;
	}

	/**
	* @return source
	*/
	public long getSource() {
		return source;
	}

	/**
	* @param source 要设置的 source
	*/
	public void setSource(long source) {
		this.source = source;
	}

	/**
	* @return createTime
	*/
	public Date getCreateTime() {
		return createTime;
	}

	/**
	* @param createTime 要设置的 createTime
	*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	* @return status
	*/
	public long getStatus() {
		return status;
	}

	/**
	* @param status 要设置的 status
	*/
	public void setStatus(long status) {
		this.status = status;
	}


	/**
	* @return sendToEmail
	*/
	public String getSendToEmail() {
		return sendToEmail;
	}

	/**
	* @param sendToEmail 要设置的 sendToEmail
	*/
	public void setSendToEmail(String sendToEmail) {
		this.sendToEmail = sendToEmail;
	}

	/**
	* @return content
	*/
	public String getContent() {
		return content;
	}

	/**
	* @param content 要设置的 content
	*/
	public void setContent(String content) {
		this.content = content;
	}

	/**
	* @return num
	*/
	public int getNum() {
		return num;
	}

	/**
	* @param num 要设置的 num
	*/
	public void setNum(int num) {
		this.num = num;
	}

	/**
	* @return remarks
	*/
	public String getRemarks() {
		return remarks;
	}

	/**
	* @param remarks 要设置的 remarks
	*/
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	* @return title
	*/ 
	public String getTitle() {
		return title;
	}

	/**
	* @param title 要设置的 title
	*/
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	* @return sendFrom
	*/ 
	public long getSendFrom() {
		return sendFrom;
	}

	/**
	* @param sendFrom 要设置的 sendFrom
	*/
	public void setSendFrom(long sendFrom) {
		this.sendFrom = sendFrom;
	}
	
	
	
	
}
