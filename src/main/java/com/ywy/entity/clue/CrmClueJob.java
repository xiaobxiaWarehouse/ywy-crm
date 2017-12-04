package com.ywy.entity.clue;

import com.ywy.annotation.ID;
import com.ywy.annotation.Table;

import java.io.Serializable;
import java.util.Date;

@Table("mg_clue_job")
public class CrmClueJob implements Serializable{

    private static final long serialVersionUID = -5365194648658729535L;

    @ID
    private Long id;

    private Long submitorId;

    private Integer source;

    private Date createTime;

    private Integer status;

    private Long industryId;

    private Long customerId;

    private Long clueStartTime;

    private Long applyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubmitorId() {
        return submitorId;
    }

    public void setSubmitorId(Long submitorId) {
        this.submitorId = submitorId;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getClueStartTime() {
        return clueStartTime;
    }

    public void setClueStartTime(Long clueStartTime) {
        this.clueStartTime = clueStartTime;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }
}
