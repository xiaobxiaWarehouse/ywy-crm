package com.ywy.entity.company;

import com.ywy.annotation.ID;
import com.ywy.annotation.Table;

/**
 * com.ywy.entity.CompanyCompany
 * Created by yaoyan on 17/11/5.
 * 17/11/5 上午11:56
 */
@Table(value = "crm_company_contactor_match")
public class CompanyContactorMatch {
    @ID
    private Long id;
    /** 客户id */
    private Long customerId;
    /** 公司id */
    private Long companyId;
    /** 创建人id */
    private Long creatorId;
    /** 域名 */
    private String domain;
    /** 匹配状态，0-匹配中；1-已匹配 */
    private Integer matchStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(Integer matchStatus) {
        this.matchStatus = matchStatus;
    }
}
