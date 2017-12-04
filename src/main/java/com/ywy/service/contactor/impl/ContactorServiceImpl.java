package com.ywy.service.contactor.impl;

import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.entity.Page;
import com.ywy.entity.company.Company;
import com.ywy.entity.company.CompanyContactor;
import com.ywy.entity.contactor.Contactor;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.enumtype.CompanyEnum;
import com.ywy.enumtype.DatasourceEnum;
import com.ywy.exception.YWYException;
import com.ywy.parameter.Condition;
import com.ywy.repository.company.CompanyContactorRepository;
import com.ywy.repository.company.CompanyRepository;
import com.ywy.repository.contactor.ContactorRepository;
import com.ywy.repository.sys.CrmAccountRepository;
import com.ywy.service.contactor.ContactorService;
import com.ywy.service.impl.CommonService;
import com.ywy.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * com.ywy.service.contactor.impl
 * Created by yaoyan on 17/11/5.
 * 17/11/5 下午4:44
 */
@Service
public class ContactorServiceImpl extends CommonService implements ContactorService {

    private static final Logger LOG = LoggerFactory.getLogger(ContactorServiceImpl.class);

    @Autowired
    private ContactorRepository contactorDao;

    @Autowired
    private CompanyContactorRepository companyContactorDao;

    @Autowired
    private CompanyRepository companyDao;

    @Autowired
    private CrmAccountRepository crmAccountDao;

    @Autowired
    private ExcelExportUtil excelExportUtil;

    @Value("${import.file.path}")
    private String uploadPath;

    @Value("${import.file.suffix}")
    private String suffix;

    @Value("${import.file.limit}")
    private Integer importLimit;

    @Value("${import.file.contactorColumnLimit}")
    private Integer custColumnLimit;

    @Value("${import.file.contactorParam}")
    private String custParam;

    @Value("${export.templatepath}")
    private String exportPath;

    @Value("${export.maxsize}")
    private String exportSize;

    @Value("${export.file.contExportParam}")
    private String contExportParam;

    /**
     * 获取客户列表
     * @param pageNo
     * @param contactor
     * @return
     */
    @Override
    public JSONObject getListByCondition(Integer pageNo, Contactor contactor, CrmAccount crmAccount) {

        JSONObject rst = generateRst();
        Page<Contactor> page = new Page<>();
        page.setPageNo(pageNo);

        try {
            // 只查询有效状态的客户
            contactor.setValidFlag(CompanyEnum.STATUS_ENABLED.getVal());
            Page<Contactor> pageList = contactorDao.query(contactor, page);
            JSONArray array = new JSONArray();
            JSONObject object;
            if (pageList != null && pageList.getData() != null) {
                for (Contactor contactor1 : pageList.getData()) {
                    object = new JSONObject();
                    object.put("name", contactor1.getName());
                    object.put("serialNumber", contactor1.getSerialNumber());
                    String contactWay = null;
                    if(!StringUtil.isEmpty(contactor1.getMobile())) {
                        contactWay = !StringUtil.isEmpty(contactor1.getMobilePrefix()) ? (contactor1.getMobilePrefix() + contactor1.getMobile()) : contactor1.getMobile();
                    } else if(!StringUtil.isEmpty(contactor1.getPhone())) {
                        contactWay = !StringUtil.isEmpty(contactor1.getPhonePrefix()) ? (contactor1.getPhonePrefix() + contactor1.getPhone()) : contactor1.getPhone();
                    }
                    object.put("mobile", contactWay);
                    object.put("tag", getTagIds(contactor1.getTagIds()));
                    object.put("email", contactor1.getEmail());
                    object.put("note", contactor1.getNote());
                    object.put("qqAcct", contactor1.getQqAcct());
                    object.put("whatsAppAcct", contactor1.getWhatsAppAcct());
                    object.put("facebookAcct", contactor1.getFacebookAcct());
                    object.put("fbAcct", contactor1.getFbAcct());
                    object.put("messengerAcct", contactor1.getMessengerAcct());
                    object.put("uuid", contactor1.getUuid());
                    object.put("contactorCode", contactor1.getContactorCode());
                    // 判断是否当前可操作
                    if(crmAccount.getRole() == 1 || contactor1.getCreateorId().equals(crmAccount.getId())) {
                        object.put("bool", true);
                    } else {
                        object.put("bool", false);
                    }
                    array.add(object);
                }
                rst.put("dataList", array);
                rst.put("page", generatePage(pageList));
            }
        } catch (Exception e) {
            LOG.error("get contactor list error: [] by name and keyWords: []", e);
            e.printStackTrace();
            rst = generateErrorRst();
        }
        return rst;
    }

    /**
     * 获取简单客户列表
     * @param pageNo
     * @param contactor
     * @return
     */
    @Override
    public JSONObject getListByConditionSimple(Integer pageNo, Contactor contactor, CrmAccount crmAccount) {

        JSONObject rst = generateRst();
        Page<Contactor> page = new Page<>();
        page.setPageNo(pageNo);

        try {
            contactor.setValidFlag(CompanyEnum.STATUS_ENABLED.getVal());
            Page<Contactor> pageList = contactorDao.query(contactor, page);
            JSONArray array = new JSONArray();
            JSONObject object;
            if (pageList != null && pageList.getData() != null) {

                for (Contactor contactor1 : pageList.getData()) {
                    object = new JSONObject();
                    object.put("name", contactor1.getName());
                    String contactWay = null;
                    if(!StringUtil.isEmpty(contactor1.getMobile())) {
                        contactWay = !StringUtil.isEmpty(contactor1.getMobilePrefix()) ? (contactor1.getMobilePrefix() + contactor1.getMobile()) : contactor1.getMobile();
                    } else if(!StringUtil.isEmpty(contactor1.getPhone())) {
                        contactWay = !StringUtil.isEmpty(contactor1.getPhonePrefix()) ? (contactor1.getPhonePrefix() + contactor1.getPhone()) : contactor1.getPhone();
                    }
                    object.put("mobile", contactWay);
                    object.put("email", contactor1.getEmail());
                    object.put("department", contactor1.getDepartment());
                    object.put("job", contactor1.getJob());
                    object.put("uuid", contactor1.getUuid());
                    object.put("id", contactor1.getId());
                    // 判断是否当前可操作
                    if(crmAccount.getRole() == 1 || contactor1.getCreateorId().equals(crmAccount.getId())) {
                        object.put("bool", true);
                    } else {
                        object.put("bool", false);
                    }
                    array.add(object);
                }
                rst.put("dataList", array);
                rst.put("page", generatePage(pageList));
            }
        } catch (Exception e) {
            LOG.error("get contactor simple list error: [] by name and keyWords: []", e);
            e.printStackTrace();
            rst = generateErrorRst();
        }
        return rst;
    }

    /**
     * 保存
     * @param contactor
     * @return
     */
    @Override
    public JSONObject save(Contactor contactor) {
        String[] fields = {"companyId", "name", "email", "phone", "mobile", "department", "job",
                "createTime", "createorId", "validFlag", "source", "serialNumber", "customerId",
                "sex", "birth", "country", "phonePrefix", "mobilePrefix", "faxPrefix", "fax", "addressPrefix",
                "address", "note", "hobby", "identityNo", "homeAddress", "zipCode", "companyIds",
                "uuid", "tag", "tagIds", "wechatAcct", "qqAcct", "whatsAppAcct", "facebookAcct", "fbAcct",
                "messengerAcct", "socialAccts", "companyNames", "contactorCode"};
        JSONObject rst = save(contactor, fields);
        //TODO 客户与联系人对应关系存储
        return rst;
    }

    @Override
    public JSONObject queryById(Contactor contactor) {
        JSONObject rst = generateRst();
        Condition condition = new Condition();
        condition.setCondition("uuid", contactor.getUuid());
        condition.setSplitValue(contactor.getCustomerId());
        try {
            Contactor result = contactorDao.queryById(condition);
            if(result == null) {
                rst.put(YWYConsts.RC, ErrorCode.NO_MATCH_DATA);
                return rst;
            }
            result.setTagIds(getTagIds(result.getTagIds()));
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class,new JsonValueProcessorImpl());
            // 获取联系人信息
            CompanyContactor companyContactor = new CompanyContactor();
            companyContactor.setCustomerId(contactor.getCustomerId());
            companyContactor.setContactorId(result.getId());
            Page<CompanyContactor> page = new Page<>();
            page.setPageNo(1);
            page.setPageSize(100);
            Page<CompanyContactor> cpage = companyContactorDao.query(companyContactor, page);
            if(cpage != null && cpage.getData() != null) {
                List<CompanyContactor> list = cpage.getData();
                for(CompanyContactor cc : list) {
                    // 获取客户信息
                    condition = new Condition();
                    condition.setCondition("id", cc.getCompanyId());
                    condition.setSplitValue(contactor.getCustomerId());
                    Company company = companyDao.queryById(condition);
                    if(company != null) {
                        cc.setName(company.getName());
                    }
                }
                result.setCompanyList(JSONArray.fromObject(list, config).toString());
            }
            // 获取来源名称
            condition = new Condition();
            condition.setCondition("id", result.getCreateorId());
            CrmAccount crmAccount = crmAccountDao.queryAccount(condition);
            if(crmAccount != null) {
                result.setBelongTo(crmAccount.getName());
            }
            JSONObject jsonObject = JSONObject.fromObject(result, config);
            rst.put("data", jsonObject);
        } catch (Exception e) {
            LOG.error("get contactor detail error: [] ", e);
            rst = generateErrorRst();
        }
        return rst;
    }

    @Override
    public JSONObject delete(Contactor contactor) {
        JSONObject rst = generateRst();

        Condition condition = new Condition();
        condition.setCondition("uuid", contactor.getUuid());
        condition.setSplitValue(contactor.getCustomerId());
        String[] fields = {"validFlag"};

        Contactor contactor1  = contactorDao.queryById(condition);
        if (contactor1 == null) {
            rst.put(YWYConsts.RC, ErrorCode.NO_COMPANY);
            return rst;
        }

        // 设置状态为无效
        contactor.setValidFlag(CompanyEnum.STATUS_DISABLED.getVal());
        contactorDao.update(condition, contactor, fields);

        // 删除对应关系
        condition = new Condition();
        condition.setCondition("contactorId", contactor1.getId());
        condition.setCondition("customerId", contactor.getCustomerId());
        condition.setSplitValue(contactor.getCustomerId());
        companyContactorDao.delete(condition);
        return rst;
    }


    /**
     * 保存（转化和保存使用）
     * @param contactor
     * @param fields
     * @return
     */
    private JSONObject save(Contactor contactor, String[] fields) {
        JSONObject rst = generateRst();
        // 设置标签,标签做特殊处理方便后续查询
        contactor.setTagIds(getSaveTagIds(contactor.getTagIds(), contactor.getSource()));
        if(StringUtil.isEmpty(contactor.getUuid())) { // 新增
            Page<Contactor> page = new Page<>();
            page.setPageNo(1);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            contactor.setUuid(uuid);
            Contactor serialNumber  = contactorDao.queryMaxSerialNumber(contactor, page);
            if (serialNumber != null) {
                contactor.setSerialNumber(serialNumber.getSerialNumber() + 1);
            } else {
                contactor.setSerialNumber(1l);
            }
            contactor.setValidFlag(CompanyEnum.STATUS_ENABLED.getVal());
            contactor.setCreateTime(new Date());
            Long id = contactorDao.add(contactor, CollectionUtils.arrayToList(fields));
            contactor.setId(id);
            saveCompanyContactor(contactor);
        } else {
            Page<Contactor> page = new Page<>();
            page.setPageNo(1);
            Contactor contactor1  = contactorDao.queryByUuid(contactor, page);
            if (contactor1 == null) {
                rst.put(YWYConsts.RC, ErrorCode.NO_COMPANY);
                return rst;
            }
            contactor.setValidFlag(contactor1.getValidFlag());
            contactor.setSerialNumber(contactor1.getSerialNumber());
            contactor.setCreateTime(contactor1.getCreateTime());
            Condition condition = new Condition();
            condition.setCondition("uuid", contactor1.getUuid());
            condition.setSplitValue(contactor1.getCustomerId());
            contactorDao.update(condition, contactor, fields);
            contactor.setId(contactor1.getId());
            saveCompanyContactor(contactor);
        }
        // 客户与联系人对应关系存储
        rst.put("uuid", contactor.getUuid());
        return rst;
    }

    /**
     * excel导入
     * @param crmAccount
     * @return
     */
    @Override
    public JSONObject importByExcel(MultipartFile file, CrmAccount crmAccount) {
        JSONObject rst = generateRst();

        // 生成文件，并导入
        try {
            String fileName = file.getOriginalFilename();
            LOG.info("导入联系人信息，导入案件人员：" + crmAccount.getId() + "，上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            if(!suffix.equals(suffixName)) {
                throw new YWYException(1001, "只支持xlsx类型文件");
            }
            String fileFullPath = uploadPath + File.separator + "customer" + File.separator + StringHelper.generateCodeWithParam(String.valueOf(crmAccount.getId())) + File.separator + fileName;
            java.io.File dest = new java.io.File(fileFullPath);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            Long succCount = 0l;
            file.transferTo(dest);
            InputStream is = new FileInputStream(fileFullPath);
            List<JSONObject> excelList = ExcelReaderUtil.readExcelContent(is, custParam, custColumnLimit);
            if(!CollectionUtils.isEmpty(excelList)) {
                if(excelList.size() > importLimit) {
                    throw new YWYException(100001, "导入数据超过限制：" + importLimit + "条.");
                }
                for(JSONObject jsonObject : excelList) { // 循环数据存储
                    try {
                        Contactor contactor = (Contactor) JSONObject.toBean(jsonObject, Contactor.class);
                        contactor.setCustomerId(crmAccount.getCustomerId());
                        contactor.setCreateorId(crmAccount.getId());
                        contactor.setSource(DatasourceEnum.DATASOURCE_EXCEL.getVal());
                        // 设置其它账户
                        contactor.setSocialAccts(getSocialAccts(jsonObject));
                        JSONObject saveResult = this.save(contactor);
                        if("0".equals(saveResult.get(YWYConsts.RC))) succCount ++;
                    } catch (YWYException e) {
                        LOG.error("保存导入的联系人信息失败：" + jsonObject.toString() + "：" + e.getMessage(), e);
                        throw new YWYException(e.getCode(), e.getMessage());
                    } catch (Exception e) {
                        LOG.error("保存导入的联系人信息失败：" + jsonObject.toString() + "：" + e.getMessage(), e);
                        throw new YWYException(ErrorCode.OPERATOR_ERROR, "操作失败");
                    }
                }
            }
            return rst;
        } catch (YWYException e) {
            LOG.info("联系人信息excel导入失败：" + e.getMessage(), e);
            rst.put(YWYConsts.RC, e.getCode());
            rst.put(YWYConsts.ERR_MSG, e.getMessage());
        } catch (Exception e) {
            LOG.info("联系人信息excel导入失败：" + e.getMessage(), e);
            rst.put(YWYConsts.RC, YWYConsts.FAIL);
            rst.put(YWYConsts.ERR_MSG, "导入失败");
        }
        return rst;
    }

    /**
     * 客户联系人对应关系保存
     * @param contactor
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void saveCompanyContactor(Contactor contactor) {
        try {
            // 校验关系是否存在，存在更新，不存在新增
            Condition condition1 = new Condition();
            condition1.setCondition("contactorId", contactor.getId());
            condition1.setCondition("customerId", contactor.getCustomerId());
            condition1.setSplitValue(contactor.getCustomerId());
            companyContactorDao.delete(condition1);
            if(StringUtil.isEmpty(contactor.getCompanyList())) {
                LOG.error("COMPANY CONTACTOR IS EMPTY.");
                // 删除之前关联的所有联系人
                return;
            }
            JSONArray jsonArray = JSONArray.fromObject(contactor.getCompanyList());
            String[] updateFields = {"department","job","title","managerName"};
            String[] addFields = {"uuid","companyId","contactorId","customerId","department","job","title","managerName"};
            if(jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    CompanyContactor companyContactor = (CompanyContactor) JSONObject.toBean(json, CompanyContactor.class);
                    companyContactor.setUuid(MD5Util.ecodeByMD5(companyContactor.getCustomerId() + "|" + companyContactor.getCompanyId() + "|" + companyContactor.getContactorId()));
                    companyContactor.setCustomerId(contactor.getCustomerId());
                    companyContactor.setContactorId(contactor.getId());
                    companyContactorDao.add(companyContactor, CollectionUtils.arrayToList(addFields));
//                    CompanyContactor companyContactor = (CompanyContactor) JSONObject.toBean(json, CompanyContactor.class);
//                    companyContactor.setCustomerId(contactor.getCustomerId());
//                    companyContactor.setContactorId(contactor.getId());
//                    // 通过uuid获取企业id
//                    Page<Company> page = new Page<>();
//                    page.setPageNo(1);
//                    Company company =new Company();
//                    company.setCustomerId(contactor.getCustomerId());
//                    company.setUuid(companyContactor.getUuid());
//                    company = companyDao.queryByUuid(company, page);
//                    if(company == null) {
//                        LOG.error("CURRENT UUID FIND COMPANY ERROR:" + company.getUuid());
//                        continue;
//                    }
//                    companyContactor.setCompanyId(company.getId());
//                    companyContactor.setUuid(MD5Util.ecodeByMD5(companyContactor.getCustomerId() + "|" + companyContactor.getCompanyId() + "|" + companyContactor.getContactorId()));
//                    Condition condition = new Condition();
//                    condition.setCondition("companyId", companyContactor.getCompanyId());
//                    condition.setCondition("contactorId", companyContactor.getContactorId());
//                    condition.setCondition("customerId", companyContactor.getCustomerId());
//                    condition.setSplitValue(contactor.getCustomerId());
//                    CompanyContactor check = companyContactorDao.queryById(condition);
//                    if(check != null) {
//                        companyContactorDao.update(condition, companyContactor, updateFields);
//                    } else {
//                        companyContactorDao.add(companyContactor, CollectionUtils.arrayToList(addFields));
//                    }
                }
            }
        } catch (Exception e) {
            LOG.error("SAVE COMPANY CONTACTOR ERROR:" + e.getMessage(), e);
            throw new YWYException(ErrorCode.OPERATOR_ERROR, "操作失败，请联系管理员");
        }

    }

    /**
     * 获取其它联系方式
     * @param jsonObject
     * @return
     */
    private static String getSocialAccts(JSONObject jsonObject) {
        JSONArray socialAccts = new JSONArray();
        String param = "Google+,Weibo,tumblr,Youtube,WhatsApp,Facebook Messenger,Skype,snapchat,imessage,kakao,Line,Viber,Vkontakte,Odnoklassniki";
        String[] params = param.split(",");
        JSONObject account = null;
        for (int i = 0; i < params.length; i++) {
            if(jsonObject.get(params[i]) != null) {
                account = new JSONObject();
                account.put("actName", params[i]);
                account.put("val", jsonObject.get(params[i]));
                socialAccts.add(account);
            }
        }
        return socialAccts.toString();
    }

    /**
     * 获取标签
     * @param tagIds
     * @return
     */
    private static String getTagIds(String tagIds) {
        if(!StringUtil.isEmpty(tagIds)) {
            if(tagIds.startsWith("|")) {
                tagIds = tagIds.substring(1);
            }
            if(tagIds.endsWith("|")) {
                tagIds = tagIds.substring(0, tagIds.length() - 1);
            }
        }
        return tagIds;
    }

    /**
     * 存储时设置标签
     * @param tagIds
     * @return
     */
    private static String getSaveTagIds(String tagIds, String source) {
        if(StringUtil.isNotBlank(tagIds)) {
            if(!tagIds.startsWith("|")) {
                tagIds = "|" + tagIds;
            }
            if(!tagIds.endsWith("|")) {
                tagIds = tagIds + "|";
            }
        } else {
            tagIds = "|";
        }
        switch (source) {
            case "OTB":
                tagIds = tagIds + "18|";
                break;
            case "HANDWORK":
                tagIds = tagIds + "19|";
                break;
            case "EXCEL":
                tagIds = tagIds + "20|";
                break;
            default:
                break;
        }
        return tagIds;
    }

    @Override
    public String exportList(Integer pageNo, Contactor contactor, CrmAccount crmAccount) {
        JSONObject rst = generateRst();
        Page<Contactor> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(Integer.valueOf(exportSize));
        try {
            // 只查询有效状态的客户
            contactor.setValidFlag(CompanyEnum.STATUS_ENABLED.getVal());
            Page<Contactor> pageList = contactorDao.query(contactor, page);
            if (pageList != null && pageList.getData() != null) {
                StringBuffer sb = new StringBuffer();
                List<Contactor> list = new ArrayList<>(pageList.getData().size());
//                list.add("姓名,联系人编号,性别,生日,国家,邮箱,电话号码前缀,电话号码,手机号码前缀,手机号码,传真前缀,传真,地址,备注,爱好," +
//                        "身份证号码,家庭地址,邮编,wechatAcct,qqAcct,whatsAppAcct,facebookAcct,fbAcct,messengerAcct,Google+," +
//                        "Weibo,tumblr,Youtube,WhatApp,Facebook Messenger,Skype,snapchat,imessage,kakao,Line,Viber,Vkontakte,Odnoklassniki");
                for (Contactor contactor1 : pageList.getData()) {
//                    sb = new StringBuffer();
//                    sb.append(contactor1.getName() != null ? contactor1.getName() : "").append(",")
//                            .append(contactor1.getContactorCode() != null ? contactor1.getContactorCode() : "").append(",")
//                            .append(contactor1.getSex() != null ? contactor1.getSex() : "").append(",")
//                            .append(contactor1.getBirth() != null ? contactor1.getBirth() : "").append(",")
//                            .append(contactor1.getCountry() != null ? contactor1.getCountry() : "").append(",")
//                            .append(contactor1.getEmail() != null ? contactor1.getEmail() : "").append(",")
//                            .append(contactor1.getPhonePrefix() != null ? contactor1.getPhonePrefix() : "").append(",")
//                            .append(contactor1.getPhone() != null ? contactor1.getPhone() : "").append(",")
//                            .append(contactor1.getMobilePrefix() != null ? contactor1.getMobilePrefix() : "").append(",")
//                            .append(contactor1.getMobile() != null ? contactor1.getMobile() : "").append(",")
//                            .append(contactor1.getFaxPrefix() != null ? contactor1.getFaxPrefix() : "").append(",")
//                            .append(contactor1.getFax() != null ? contactor1.getFax() : "").append(",")
//                            .append(contactor1.getAddress() != null ? contactor1.getAddress() : "").append(",")
//                            .append(contactor1.getNote() != null ? contactor1.getNote() : "").append(",")
//                            .append(contactor1.getHobby() != null ? contactor1.getHobby() : "").append(",")
//                            .append(contactor1.getIdentityNo() != null ? contactor1.getIdentityNo() : "").append(",")
//                            .append(contactor1.getHomeAddress() != null ? contactor1.getHomeAddress() : "").append(",")
//                            .append(contactor1.getZipCode() != null ? contactor1.getZipCode() : "").append(",")
//                            .append(contactor1.getWechatAcct() != null ? contactor1.getWechatAcct() : "").append(",")
//                            .append(contactor1.getQqAcct() != null ? contactor1.getQqAcct() : "").append(",")
//                            .append(contactor1.getWhatsAppAcct() != null ? contactor1.getWhatsAppAcct() : "").append(",")
//                            .append(contactor1.getFacebookAcct() != null ? contactor1.getFacebookAcct() : "").append(",")
//                            .append(contactor1.getFbAcct() != null ? contactor1.getFbAcct() : "").append(",")
//                            .append(contactor1.getMessengerAcct() != null ? contactor1.getMessengerAcct() : "").append(",");
                    if(StringUtil.isNotBlank(contactor1.getSocialAccts())) {
                        JSONObject socialJson = new JSONObject();
                        JSONArray social = JSONArray.fromObject(contactor1.getSocialAccts());
                        if(social != null && social.size() > 0) {
                            for (int i = 0; i < social.size(); i++) {
                                JSONObject json = social.getJSONObject(i);
                                if(json.containsKey("actName")) {
                                    socialJson.put(json.getString("actName"), json.getString("val"));
                                }
                            }
                        }
                        contactor1.setGoogle(socialJson.containsKey("Google+") ? socialJson.getString("Google+") : "");
                        contactor1.setWeibo(socialJson.containsKey("Weibo") ? socialJson.getString("Weibo") : "");
                        contactor1.setTumblr(socialJson.containsKey("tumblr") ? socialJson.getString("tumblr") : "");
                        contactor1.setYoutube(socialJson.containsKey("Youtube") ? socialJson.getString("Youtube") : "");
                        contactor1.setWhatsApp(socialJson.containsKey("Facebook Messenger") ? socialJson.getString("Facebook Messenger") : "");
                        contactor1.setSkype(socialJson.containsKey("Skype") ? socialJson.getString("Skype") : "");
                        contactor1.setSnapchat(socialJson.containsKey("snapchat") ? socialJson.getString("snapchat") : "");
                        contactor1.setImessage(socialJson.containsKey("imessage") ? socialJson.getString("imessage") : "");
                        contactor1.setKakao(socialJson.containsKey("kakao") ? socialJson.getString("kakao") : "");
                        contactor1.setLine(socialJson.containsKey("Line") ? socialJson.getString("Line") : "");
                        contactor1.setViber(socialJson.containsKey("Viber") ? socialJson.getString("Viber") : "");
                        contactor1.setVkontakte(socialJson.containsKey("Vkontakte") ? socialJson.getString("Vkontakte") : "");
                        contactor1.setOdnoklassniki(socialJson.containsKey("Odnoklassniki") ? socialJson.getString("Odnoklassniki") : "");;
//                        sb.append(socialJson.containsKey("Google+") ? socialJson.getString("Google+") : "").append(",")
//                                .append(socialJson.containsKey("Weibo") ? socialJson.getString("Weibo") : "").append(",")
//                                .append(socialJson.containsKey("tumblr") ? socialJson.getString("tumblr") : "").append(",")
//                                .append(socialJson.containsKey("Youtube") ? socialJson.getString("Youtube") : "").append(",")
//                                .append(socialJson.containsKey("WhatsApp") ? socialJson.getString("WhatsApp") : "").append(",")
//                                .append(socialJson.containsKey("Facebook") ? socialJson.getString("Facebook") : "").append(",")
//                                .append(socialJson.containsKey("Messenger") ? socialJson.getString("Messenger") : "").append(",")
//                                .append(socialJson.containsKey("Skype") ? socialJson.getString("Skype") : "").append(",")
//                                .append(socialJson.containsKey("snapchat") ? socialJson.getString("snapchat") : "").append(",")
//                                .append(socialJson.containsKey("imessage") ? socialJson.getString("imessage") : "").append(",")
//                                .append(socialJson.containsKey("kakao") ? socialJson.getString("kakao") : "").append(",")
//                                .append(socialJson.containsKey("Line") ? socialJson.getString("Line") : "").append(",")
//                                .append(socialJson.containsKey("Viber") ? socialJson.getString("Viber") : "").append(",")
//                                .append(socialJson.containsKey("Vkontakte") ? socialJson.getString("Vkontakte") : "").append(",")
//                                .append(socialJson.containsKey("Odnoklassniki") ? socialJson.getString("Odnoklassniki") : "");
                    }
                    list.add(contactor1);
                }
                String path = excelExportUtil.doExportData("contactor.xls", Contactor.class, list, null, contExportParam);
                return path;
//                String path = exportPath + File.separator + "contactor" + File.separator + DateUtil.customFormat("yyyyMMdd", new Date()) + "_" + contactor.getCustomerId();
//                String name = UUID.randomUUID().toString().replaceAll("-", "") + ".csv";
//                boolean result = CSVUtils.exportCsv(new File(path), name, list);
//                if(result) {
//                    return path + File.separator + name;
//                } else {
//                    throw new YWYException(9999, "导出失败");
//                }
            }
        } catch (YWYException e) {
            LOG.error("CONTACTOR EXPORT CSV ERROR:" + e.getMessage(), e);
            throw new YWYException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOG.error("CONTACTOR EXPORT CSV ERROR:" + e.getMessage(), e);
            throw new YWYException(9999, "导出失败");
        }
        return null;
    }

}
