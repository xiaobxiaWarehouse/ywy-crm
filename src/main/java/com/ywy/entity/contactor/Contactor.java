package com.ywy.entity.contactor;

import com.ywy.annotation.ID;
import com.ywy.annotation.Table;

import java.util.Date;

/**
 * com.ywy.entity.contactor
 * Created by yaoyan on 17/11/5.
 * 17/11/5 上午11:57
 */
@Table(value = "crm_company_contact", modValue="50", splitFieldName="customerId")
public class Contactor {

    @ID
    private Long id;
    /** 记录ID */
    private String uuid;
    /** 姓名 */
    private String name;
    /** 性别 */
    private String sex;
    /** 生日 */
    private String birth;
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
    /** 地址前缀 */
    private String addressPrefix;
    /** 地址 */
    private String address;
    /** 家庭地址 */
    private String homeAddress;
    /** 邮编 */
    private String zipCode;
    /** 备注 */
    private String note;
    /** 爱好 */
    private String hobby;
    /** 证件号码 */
    private String identityNo;
    /** 标签 */
    private String tagIds;
    /** 公司信息 */
    private String companyList;
    /** 联系人编号 */
    private String contactorCode;
    /** 客户ID */
    private Long customerId;
    /** 状态，1-有效；2-无效 */
    private Long validFlag;
    /** 公司ID */
    private Long companyId;

    private String wechatAcct;

    private String qqAcct;

    private String whatsAppAcct;

    private String facebookAcct;

    private String fbAcct;

    private String messengerAcct;

    private Long serialNumber;

    private Date createTime;
    /** 其它社交账户 */
    private String socialAccts;

    private Long createorId;

    private String department;

    private String job;

    private String source;

    private String companyIds;

    private String companyNames;

    private String tag;

    private String belongTo;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
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

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getCompanyList() {
        return companyList;
    }

    public void setCompanyList(String companyList) {
        this.companyList = companyList;
    }

    public String getContactorCode() {
        return contactorCode;
    }

    public void setContactorCode(String contactorCode) {
        this.contactorCode = contactorCode;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Long validFlag) {
        this.validFlag = validFlag;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getQqAcct() {
        return qqAcct;
    }

    public void setQqAcct(String qqAcct) {
        this.qqAcct = qqAcct;
    }

    public String getWhatsAppAcct() {
        return whatsAppAcct;
    }

    public void setWhatsAppAcct(String whatsAppAcct) {
        this.whatsAppAcct = whatsAppAcct;
    }

    public String getFacebookAcct() {
        return facebookAcct;
    }

    public void setFacebookAcct(String facebookAcct) {
        this.facebookAcct = facebookAcct;
    }

    public String getFbAcct() {
        return fbAcct;
    }

    public void setFbAcct(String fbAcct) {
        this.fbAcct = fbAcct;
    }

    public String getMessengerAcct() {
        return messengerAcct;
    }

    public void setMessengerAcct(String messengerAcct) {
        this.messengerAcct = messengerAcct;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSocialAccts() {
        return socialAccts;
    }

    public void setSocialAccts(String socialAccts) {
        this.socialAccts = socialAccts;
    }

    public Long getCreateorId() {
        return createorId;
    }

    public void setCreateorId(Long createorId) {
        this.createorId = createorId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(String companyIds) {
        this.companyIds = companyIds;
    }

    public String getCompanyNames() {
        return companyNames;
    }

    public void setCompanyNames(String companyNames) {
        this.companyNames = companyNames;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getWechatAcct() {
        return wechatAcct;
    }

    public void setWechatAcct(String wechatAcct) {
        this.wechatAcct = wechatAcct;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
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
