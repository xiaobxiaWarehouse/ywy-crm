package com.ywy.entity.company;

import com.ywy.annotation.ID;
import com.ywy.annotation.Table;

import java.util.Date;

/**
 * com.ywy.entity.CompanyCompany
 * Created by yaoyan on 17/11/5.
 * 17/11/5 上午11:56
 */
@Table(value = "crm_company_contactor_map", modValue="50", splitFieldName="customerId")
public class CompanyContactor {
    @ID
    private Long id;
    /** 唯一标识，为空表示添加 */
    private String uuid;
    /** 关联crm_companys.id */
    private Long companyId;
    /** 关联crm_compnay_contact.id */
    private Long contactorId;
    /** 部门 */
    private String department;
    /** 职位 */
    private String job;
    /** 称谓 */
    private String title;
    /** 上级联系人 */
    private String managerName;
    /** 客户ID */
    private Long customerId;

    private String email;

    private String phone;

    private String mobile;
    /** 联系人姓名 */
    private String name;

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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getContactorId() {
        return contactorId;
    }

    public void setContactorId(Long contactorId) {
        this.contactorId = contactorId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
