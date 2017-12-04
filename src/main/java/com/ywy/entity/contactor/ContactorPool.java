package com.ywy.entity.contactor;

import com.ywy.annotation.ID;
import com.ywy.annotation.Table;

/**
 * com.ywy.entity.contactor
 * Created by yaoyan on 17/11/9.
 * 17/11/9 下午8:18
 */
@Table(value = "crm_domain_cache", modValue="26", splitFieldName="splitNumber")
public class ContactorPool {
    @ID
    private String id;
    /** 域名 */
    private String domain;
    /** 邮箱 */
    private String email;
    /** 电话 */
    private String phone;
    /** 传真 */
    private String fax;
    /** 手机 */
    private String mobile;
    /** 社交账户 */
    private String faceBookAcct;
    /** 社交账户 */
    private String linkedinAcct;
    /** 社交账户 */
    private String twitterAcct;
    /** 社交账户 */
    private String pinterestAcct;
    private String instagramAcct;
    private String socialAccts;
    /** 域名信息 */
    private String whoisDomainInfo;
    /** 注册联系方式 */
    private String whoisRegContact;
    /** 联系人 */
    private String whoisAdminContact;
    /**  */
    private String whoisTechContact;
    /** 1：已经匹配过。0：未匹配。用于同样domain请求匹配次数去重 */
    private Long matched;
    /** 分表标识 */
    private Long splitNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFaceBookAcct() {
        return faceBookAcct;
    }

    public void setFaceBookAcct(String faceBookAcct) {
        this.faceBookAcct = faceBookAcct;
    }

    public String getLinkedinAcct() {
        return linkedinAcct;
    }

    public void setLinkedinAcct(String linkedinAcct) {
        this.linkedinAcct = linkedinAcct;
    }

    public String getTwitterAcct() {
        return twitterAcct;
    }

    public void setTwitterAcct(String twitterAcct) {
        this.twitterAcct = twitterAcct;
    }

    public String getPinterestAcct() {
        return pinterestAcct;
    }

    public void setPinterestAcct(String pinterestAcct) {
        this.pinterestAcct = pinterestAcct;
    }

    public String getInstagramAcct() {
        return instagramAcct;
    }

    public void setInstagramAcct(String instagramAcct) {
        this.instagramAcct = instagramAcct;
    }

    public String getSocialAccts() {
        return socialAccts;
    }

    public void setSocialAccts(String socialAccts) {
        this.socialAccts = socialAccts;
    }

    public String getWhoisDomainInfo() {
        return whoisDomainInfo;
    }

    public void setWhoisDomainInfo(String whoisDomainInfo) {
        this.whoisDomainInfo = whoisDomainInfo;
    }

    public String getWhoisRegContact() {
        return whoisRegContact;
    }

    public void setWhoisRegContact(String whoisRegContact) {
        this.whoisRegContact = whoisRegContact;
    }

    public String getWhoisAdminContact() {
        return whoisAdminContact;
    }

    public void setWhoisAdminContact(String whoisAdminContact) {
        this.whoisAdminContact = whoisAdminContact;
    }

    public String getWhoisTechContact() {
        return whoisTechContact;
    }

    public void setWhoisTechContact(String whoisTechContact) {
        this.whoisTechContact = whoisTechContact;
    }

    public Long getMatched() {
        return matched;
    }

    public void setMatched(Long matched) {
        this.matched = matched;
    }

    public Long getSplitNumber() {
        return splitNumber;
    }

    public void setSplitNumber(Long splitNumber) {
        this.splitNumber = splitNumber;
    }

    @Override
    public String toString() {
        return "ContactorPool{" +
                "id='" + id + '\'' +
                ", domain='" + domain + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", mobile='" + mobile + '\'' +
                ", faceBookAcct='" + faceBookAcct + '\'' +
                ", linkedinAcct='" + linkedinAcct + '\'' +
                ", twitterAcct='" + twitterAcct + '\'' +
                ", pinterestAcct='" + pinterestAcct + '\'' +
                ", instagramAcct='" + instagramAcct + '\'' +
                ", socialAccts='" + socialAccts + '\'' +
                ", whoisDomainInfo='" + whoisDomainInfo + '\'' +
                ", whoisRegContact='" + whoisRegContact + '\'' +
                ", whoisAdminContact='" + whoisAdminContact + '\'' +
                ", whoisTechContact='" + whoisTechContact + '\'' +
                ", matched=" + matched +
                ", splitNumber=" + splitNumber +
                '}';
    }
}
