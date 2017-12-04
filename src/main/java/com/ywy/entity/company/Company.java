package com.ywy.entity.company;

import com.ywy.annotation.ID;
import com.ywy.annotation.Table;

import java.util.Date;

/**
 * com.ywy.entity.company
 * Created by yaoyan on 17/11/5.
 * 17/11/5 上午11:56
 */
@Table(value = "crm_companys", modValue="50", splitFieldName="customerId")
public class Company {
    @ID
    private Long id;
    /** 唯一标识，为空表示添加 */
    private String uuid;
    /** 行业名字 */
    private String industry;
    /** 公司名字 */
    private String name;
    /** 国家 */
    private String country;
    /** 邮箱 */
    private String email;
    /** 电话前缀 */
    private String phonePrefix;
    /** 电话 */
    private String phone;
    /** 传真前缀 */
    private String faxPrefix;
    /** 传真 */
    private String fax;
    /** 手机前缀 */
    private String mobilePrefix;
    /** 手机 */
    private String mobile;
    /** 公司地址前缀 */
    private String addressPrefix;
    /** 公司地址 */
    private String address;
    /** 其他地址前缀 */
    private String otherAddressPrefix;
    /** 其他地址 */
    private String otherAddress;
    /** 社交账号 */
    private String pinterestAcct;
    /** 社交账号 */
    private String linkedInAcct;
    /** 社交账号 */
    private String facebookAcct;
    /** 微信账号 */
    private String instagramAcct;
    /** 微信账号 */
    private String twitterAcct;
    /** 微信账号 */
    private String wechatAcct;
    /** qq账号 */
    private String qqAcct;
    /** 企业社交账户 */
    private String socialAccts;
    /** 备注 */
    private String note;
    /** 标签 */
    private String tagIds;
    /** 联系人ID */
    private String contactorIds;
    /** 域名信息 */
    private String domain;
    /** 客户编号 */
    private String customerCode;

    private Date createTime;
    /** 0：未匹配，1：匹配中，2：匹配成功。10：匹配失败 */
    private Long matchStatus;
    /** 1:有效。2：无效 */
    private Long status;
    /** 来源. CLUE(线索），IMPORT(导入） */
    private String source;
    /** 客户id */
    private Long customerId;
    /** 创建人ID */
    private Long creatorId;
    /** 自动匹配开光。1：开启。0：未开启 */
    private Long autoMatchFlag;
    /** 匹配消息同步时间 */
    private Date syncTime;
    /** 自增id */
    private Long serialNumber;
    /** 客户描述 */
    private String companyDesc;
    /** 联系人信息 */
    private String contactor;
    /** 线索id */
    private String clueId;
    /** 客户归属 */
    private String belongTo;

    /**----------社交账号线索---------*/
    /** 社交账号 */
    private String pinterestAcctClue;
    /** 社交账号 */
    private String linkedInAcctClue;
    /** 社交账号 */
    private String facebookAcctClue;
    /** 微信账号 */
    private String instagramAcctClue;
    /** 微信账号 */
    private String twitterAcctClue;
    /** 企业社交账户 */
    private String socialAcctsClue;
    /** 是否需要发送数据中心 */
    private boolean sendFlag = false;

    /**-------导出使用---------*/
    private String google;
    private String weibo;
    private String tumblr;
    private String youtube;
    private String whatsApp;
    private String facebookMessenger;
    private String skype;
    private String snapchat;
    private String imessage;
    private String kakao;
    private String line;
    private String viber;
    private String vkontakte;
    private String odnoklassniki;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFaxPrefix() {
        return faxPrefix;
    }

    public void setFaxPrefix(String faxPrefix) {
        this.faxPrefix = faxPrefix;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobilePrefix() {
        return mobilePrefix;
    }

    public void setMobilePrefix(String mobilePrefix) {
        this.mobilePrefix = mobilePrefix;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddressPrefix() {
        return addressPrefix;
    }

    public void setAddressPrefix(String addressPrefix) {
        this.addressPrefix = addressPrefix;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOtherAddressPrefix() {
        return otherAddressPrefix;
    }

    public void setOtherAddressPrefix(String otherAddressPrefix) {
        this.otherAddressPrefix = otherAddressPrefix;
    }

    public String getOtherAddress() {
        return otherAddress;
    }

    public void setOtherAddress(String otherAddress) {
        this.otherAddress = otherAddress;
    }

    public String getPinterestAcct() {
        return pinterestAcct;
    }

    public void setPinterestAcct(String pinterestAcct) {
        this.pinterestAcct = pinterestAcct;
    }

    public String getLinkedInAcct() {
        return linkedInAcct;
    }

    public void setLinkedInAcct(String linkedInAcct) {
        this.linkedInAcct = linkedInAcct;
    }

    public String getFacebookAcct() {
        return facebookAcct;
    }

    public void setFacebookAcct(String facebookAcct) {
        this.facebookAcct = facebookAcct;
    }

    public String getInstagramAcct() {
        return instagramAcct;
    }

    public void setInstagramAcct(String instagramAcct) {
        this.instagramAcct = instagramAcct;
    }

    public String getTwitterAcct() {
        return twitterAcct;
    }

    public void setTwitterAcct(String twitterAcct) {
        this.twitterAcct = twitterAcct;
    }

    public String getWechatAcct() {
        return wechatAcct;
    }

    public void setWechatAcct(String wechatAcct) {
        this.wechatAcct = wechatAcct;
    }

    public String getQqAcct() {
        return qqAcct;
    }

    public void setQqAcct(String qqAcct) {
        this.qqAcct = qqAcct;
    }

    public String getSocialAccts() {
        return socialAccts;
    }

    public void setSocialAccts(String socialAccts) {
        this.socialAccts = socialAccts;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getContactorIds() {
        return contactorIds;
    }

    public void setContactorIds(String contactorIds) {
        this.contactorIds = contactorIds;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(Long matchStatus) {
        this.matchStatus = matchStatus;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getAutoMatchFlag() {
        return autoMatchFlag;
    }

    public void setAutoMatchFlag(Long autoMatchFlag) {
        this.autoMatchFlag = autoMatchFlag;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
    }

    public String getClueId() {
        return clueId;
    }

    public void setClueId(String clueId) {
        this.clueId = clueId;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getPinterestAcctClue() {
        return pinterestAcctClue;
    }

    public void setPinterestAcctClue(String pinterestAcctClue) {
        this.pinterestAcctClue = pinterestAcctClue;
    }

    public String getLinkedInAcctClue() {
        return linkedInAcctClue;
    }

    public void setLinkedInAcctClue(String linkedInAcctClue) {
        this.linkedInAcctClue = linkedInAcctClue;
    }

    public String getFacebookAcctClue() {
        return facebookAcctClue;
    }

    public void setFacebookAcctClue(String facebookAcctClue) {
        this.facebookAcctClue = facebookAcctClue;
    }

    public String getInstagramAcctClue() {
        return instagramAcctClue;
    }

    public void setInstagramAcctClue(String instagramAcctClue) {
        this.instagramAcctClue = instagramAcctClue;
    }

    public String getTwitterAcctClue() {
        return twitterAcctClue;
    }

    public void setTwitterAcctClue(String twitterAcctClue) {
        this.twitterAcctClue = twitterAcctClue;
    }

    public boolean isSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(boolean sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getSocialAcctsClue() {
        return socialAcctsClue;
    }

    public void setSocialAcctsClue(String socialAcctsClue) {
        this.socialAcctsClue = socialAcctsClue;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getTumblr() {
        return tumblr;
    }

    public void setTumblr(String tumblr) {
        this.tumblr = tumblr;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(String whatsApp) {
        this.whatsApp = whatsApp;
    }

    public String getFacebookMessenger() {
        return facebookMessenger;
    }

    public void setFacebookMessenger(String facebookMessenger) {
        this.facebookMessenger = facebookMessenger;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getSnapchat() {
        return snapchat;
    }

    public void setSnapchat(String snapchat) {
        this.snapchat = snapchat;
    }

    public String getImessage() {
        return imessage;
    }

    public void setImessage(String imessage) {
        this.imessage = imessage;
    }

    public String getKakao() {
        return kakao;
    }

    public void setKakao(String kakao) {
        this.kakao = kakao;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getViber() {
        return viber;
    }

    public void setViber(String viber) {
        this.viber = viber;
    }

    public String getVkontakte() {
        return vkontakte;
    }

    public void setVkontakte(String vkontakte) {
        this.vkontakte = vkontakte;
    }

    public String getOdnoklassniki() {
        return odnoklassniki;
    }

    public void setOdnoklassniki(String odnoklassniki) {
        this.odnoklassniki = odnoklassniki;
    }
}
