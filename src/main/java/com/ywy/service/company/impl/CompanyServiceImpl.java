package com.ywy.service.company.impl;

import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.entity.Page;
import com.ywy.entity.clue.CrmClue;
import com.ywy.entity.company.Company;
import com.ywy.entity.company.CompanyContactor;
import com.ywy.entity.company.CompanyContactorMatch;
import com.ywy.entity.contactor.Contactor;
import com.ywy.entity.contactor.ContactorPool;
import com.ywy.entity.contactor.DomainStatics;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.enumtype.CompanyEnum;
import com.ywy.enumtype.DatasourceEnum;
import com.ywy.exception.YWYException;
import com.ywy.parameter.Condition;
import com.ywy.repository.clue.CrmClueRepository;
import com.ywy.repository.company.CompanyContactorMatchRepository;
import com.ywy.repository.company.CompanyContactorRepository;
import com.ywy.repository.company.CompanyRepository;
import com.ywy.repository.contactor.ContactorPoolRepository;
import com.ywy.repository.contactor.ContactorRepository;
import com.ywy.repository.contactor.DomainStaticsRepository;
import com.ywy.repository.sys.CrmAccountRepository;
import com.ywy.service.HttpService;
import com.ywy.service.company.CompanyService;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CompanyServiceImpl extends CommonService implements CompanyService {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyRepository companyDao;

    @Autowired
    private ContactorRepository contactorDao;

    @Autowired
    private CompanyContactorRepository companyContactorDao;

    @Autowired
    private ContactorPoolRepository contactorPoolDao;

    @Autowired
    private CrmClueRepository crmClueDao;

    @Autowired
    private CrmAccountRepository crmAccountDao;

    @Autowired
    private CompanyContactorMatchRepository companyContactorMatchDao;

    @Autowired
    private DomainStaticsRepository domainStaticsDao;

    @Autowired
    private HttpService httpService;

    @Value("${import.file.path}")
    private String uploadPath;

    @Value("${import.file.suffix}")
    private String suffix;

    @Value("${import.file.limit}")
    private Integer importLimit;

    @Value("${import.file.companyColumnLimit}")
    private Integer custColumnLimit;

    @Value("${import.file.companyParam}")
    private String custParam;

    @Value("${spider.domain.url}")
    private String spiderUrl;

    @Value("${export.templatepath}")
    private String exportPath;

    @Value("${export.file.custExportParam}")
    private String custExportParam;

    @Value("${export.maxsize}")
    private String exportSize;

    @Autowired
    private ExcelExportUtil excelExportUtil;
    /**
     * 获取客户列表
     * @param pageNo
     * @param company
     * @return
     */
    @Override
    public JSONObject getListByCondition(Integer pageNo, Company company, CrmAccount crmAccount) {

        JSONObject rst = generateRst();
        Page<Company> page = new Page<>();
        page.setPageNo(pageNo);

        try {
            // 只查询有效状态的客户
            company.setStatus(CompanyEnum.STATUS_ENABLED.getVal());
            Page<Company> pageList = companyDao.query(company, page);
            JSONArray array = new JSONArray();
            JSONObject object;
            if (pageList != null && pageList.getData() != null) {
                for (Company company1 : pageList.getData()) {
                    object = new JSONObject();
                    object.put("name", company1.getName());
                    object.put("serialNumber", company1.getSerialNumber());
                    object.put("matchStatus", company1.getMatchStatus());
                    object.put("tag", getTagIds(company1.getTagIds()));
                    object.put("contactor", company1.getContactorIds());
                    object.put("industry", company1.getIndustry());
                    String contactWay = null;
                    if(!StringUtil.isEmpty(company1.getMobile())) {
                        contactWay = !StringUtil.isEmpty(company1.getMobilePrefix()) ? (company1.getMobilePrefix() + "-" + company1.getMobile()) : company1.getMobile();
                    } else if(!StringUtil.isEmpty(company1.getPhone())) {
                        contactWay = !StringUtil.isEmpty(company1.getPhonePrefix()) ? (company1.getPhonePrefix() + "-" + company1.getPhone()) : company1.getPhone();
                    }
                    object.put("contactWay",  contactWay);
                    object.put("email", company1.getEmail());
                    object.put("pinterestAcct", company1.getPinterestAcct());
                    object.put("linkedInAcct", company1.getLinkedInAcct());
                    object.put("facebookAcct", company1.getFacebookAcct());
                    object.put("instagramAcct", company1.getInstagramAcct());
                    object.put("twitterAcct", company1.getTwitterAcct());
                    object.put("uuid", company1.getUuid());
                    object.put("customerCode", company1.getCustomerCode());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    if (company.getCreateTime() != null)
                        object.put("createTime", format.format(company1.getCreateTime()));
                    // 判断是否当前可操作
                    if(crmAccount.getRole() == 1 || company1.getCreatorId().equals(crmAccount.getId())) {
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
            LOG.error("get company list error: [] by name and keyWords: []", e);
            e.printStackTrace();
            rst = generateErrorRst();
        }
        return rst;
    }

    /**
     * 获取简单客户列表
     * @param pageNo
     * @param company
     * @return
     */
    @Override
    public JSONObject getListByConditionSimple(Integer pageNo, Company company, CrmAccount crmAccount) {

        JSONObject rst = generateRst();
        Page<Company> page = new Page<>();
        page.setPageNo(pageNo);

        try {
            company.setStatus(CompanyEnum.STATUS_ENABLED.getVal());
            Page<Company> pageList = companyDao.query(company, page);
            JSONArray array = new JSONArray();
            JSONObject object;
            if (pageList != null && pageList.getData() != null) {

                for (Company company1 : pageList.getData()) {
                    object = new JSONObject();
                    object.put("name", company1.getName());
                    object.put("uuid", company1.getUuid());
                    object.put("id", company1.getId());
                    // 判断是否当前可操作
                    if(crmAccount.getRole() == 1 || company1.getCreatorId().equals(crmAccount.getId())) {
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
            LOG.error("get company list error: [] by name and keyWords: []", e);
            e.printStackTrace();
            rst = generateErrorRst();
        }
        return rst;
    }

    /**
     * 保存
     * @param company
     * @return
     */
    @Override
    public JSONObject save(Company company) {
        String[] fields = {"customerId", "creatorId", "name", "domain", "industry",
                "email", "phone", "fax", "address", "socialAccts", "note",
                "createTime", "status", "serialNumber", "matchStatus",
                "mobile", "country", "phonePrefix", "mobilePrefix", "faxPrefix",
                "addressPrefix", "otherAddressPrefix", "otherAddress", "pinterestAcct",
                "linkedInAcct", "facebookAcct", "instagramAcct", "twitterAcct", "wechatAcct",
                "qqAcct", "tagIds", "uuid", "contactorIds", "autoMatchFlag", "syncTime", "companyDesc", "customerCode", "source"};
        if(company.getAutoMatchFlag() == null) {
            company.setAutoMatchFlag(CompanyEnum.AUTO_MATCH_FLAG_CLOSE.getVal());
        }
        if(company.getAutoMatchFlag() == CompanyEnum.AUTO_MATCH_FLAG_OPEN.getVal()) {
            getDomainInfo(company);
        } else {
            company.setMatchStatus(CompanyEnum.MATCHSTATUS_INIT.getVal());
        }
        JSONObject rst = save(company, fields);
        saveContactorPool(company);
        return rst;
    }

    @Override
    public JSONObject queryById(Company company) {
        JSONObject rst = generateRst();
        Condition condition = new Condition();
        condition.setCondition("uuid", company.getUuid());
        condition.setSplitValue(company.getCustomerId());
        try {
            Company result = companyDao.queryById(condition);
            if(result == null) {
                rst.put(YWYConsts.RC, ErrorCode.NO_MATCH_DATA);
                return rst;
            }
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class,new JsonValueProcessorImpl());
            // 获取联系人信息
            CompanyContactor companyContactor = new CompanyContactor();
            companyContactor.setCustomerId(company.getCustomerId());
            companyContactor.setCompanyId(result.getId());
            Page<CompanyContactor> page = new Page<>();
            page.setPageNo(1);
            page.setPageSize(100);
            Page<CompanyContactor> cpage = companyContactorDao.query(companyContactor, page);
            if(cpage != null && cpage.getData() != null) {
                List<CompanyContactor> list = cpage.getData();
                for(CompanyContactor cc : list) {
                    // 获取客户信息
                    condition = new Condition();
                    condition.setCondition("id", cc.getContactorId());
                    condition.setSplitValue(company.getCustomerId());
                    Contactor contactor = contactorDao.queryById(condition);
                    if(contactor != null) {
                        cc.setEmail(contactor.getEmail());
                        cc.setPhone(contactor.getPhone());
                        cc.setMobile(contactor.getMobile());
                        cc.setName(contactor.getName());
                    }
                }
                result.setContactor(JSONArray.fromObject(list, config).toString());
            }
            // 获取联系线索信息
            condition = new Condition();
            condition.setCondition("domain", result.getDomain());
            condition.setSplitValue(StringUtil.getPoolSplitNumber(result.getDomain()));
            ContactorPool contactorPool = contactorPoolDao.queryById(condition);
            if(contactorPool != null) {
                result.setFacebookAcctClue(contactorPool.getFaceBookAcct());
                result.setInstagramAcctClue(contactorPool.getInstagramAcct());
                result.setPinterestAcctClue(contactorPool.getPinterestAcct());
                result.setLinkedInAcctClue(contactorPool.getLinkedinAcct());
                result.setTwitterAcctClue(contactorPool.getTwitterAcct());
                result.setSocialAcctsClue(contactorPool.getSocialAccts());
            }
            // 获取来源名称
            condition = new Condition();
            condition.setCondition("id", result.getCreatorId());
            CrmAccount crmAccount = crmAccountDao.queryAccount(condition);
            if(crmAccount != null) {
                result.setBelongTo(crmAccount.getName());
            }
            // 设置标签
            result.setTagIds(getTagIds(result.getTagIds()));
            JSONObject jsonObject = JSONObject.fromObject(result, config);
            rst.put("data", jsonObject);
        } catch (Exception e) {
            LOG.error("get company detail error: [] ", e);
            rst = generateErrorRst();
        }
        return rst;
    }

    @Override
    public JSONObject delete(Company company) {
        JSONObject rst = generateRst();

        Condition condition = new Condition();
        condition.setCondition("uuid", company.getUuid());
        condition.setSplitValue(company.getCustomerId());
        String[] fields = {"status"};

        Company result = companyDao.queryById(condition);
        if(result == null) {
            rst.put(YWYConsts.RC, ErrorCode.MATCH_NO_FIND);
            return rst;
        }
        // 设置状态为无效
        company.setStatus(CompanyEnum.STATUS_DISABLED.getVal());
        companyDao.update(condition, company, fields);

        // 删除对应关系
        condition = new Condition();
        condition.setCondition("companyId", result.getId());
        condition.setCondition("customerId", company.getCustomerId());
        condition.setSplitValue(company.getCustomerId());
        companyContactorDao.delete(condition);
        return rst;
    }

    /**
     * 转化
     * @param company
     * @return
     */
    @Override
    public JSONObject transfer(Company company) {
        String[] fields = {"customerId", "creatorId", "name", "domain", "industry",
                "email", "phone", "fax", "address", "socialAccts", "note",
                "status", "serialNumber", "matchStatus",
                "mobile", "country", "phonePrefix", "mobilePrefix", "faxPrefix",
                "addressPrefix", "otherAddressPrefix", "otherAddress", "pinterestAcct",
                "linkedInAcct", "facebookAcct", "instagramAcct", "twitterAcct", "wechatAcct",
                "qqAcct", "tagIds", "contactorIds", "autoMatchFlag", "syncTime", "companyDesc", "customerCode", "source", "uuid"};
        if(company.getAutoMatchFlag() == null) {
            company.setAutoMatchFlag(CompanyEnum.AUTO_MATCH_FLAG_CLOSE.getVal());
        }
        JSONObject rst = generateRst();
        Page<Company> page = new Page<>();
        page.setPageNo(1);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        company.setUuid(uuid);
        Company serialNumber  = companyDao.queryMaxSerialNumber(company, page);
        if (serialNumber != null) {
            company.setSerialNumber(serialNumber.getSerialNumber() + 1);
        } else {
            company.setSerialNumber(1l);
        }
        // 获取联系人总库数据
        if(company.getAutoMatchFlag() == CompanyEnum.AUTO_MATCH_FLAG_OPEN.getVal()) {
            getDomainInfo(company);
        }
        company.setStatus(CompanyEnum.STATUS_ENABLED.getVal());
        company.setCreateTime(new Date());
        // 设置来源为OTB
        company.setSource(DatasourceEnum.DATASOURCE_OTB.getVal());
        // 设置标签,标签做特殊处理方便后续查询
        company.setTagIds(getSaveTagIds(company.getTagIds(), company.getSource()));
        Long id = companyDao.add(company, CollectionUtils.arrayToList(fields));

        Condition condition = new Condition();
        condition.setCondition("clueId", company.getClueId());
        condition.setSplitValue(company.getCustomerId());
        String[] clueFields = {"transferId","validFlag"};

        CrmClue crmClue = new CrmClue();
        crmClue.setTransferId(id);
        crmClue.setValidFlag(3);
        crmClueDao.update(condition, crmClue, clueFields);
        if(company.isSendFlag()) {
            company.setId(id);
            saveMatchInfo(company);
        }
        saveContactorPool(company);
        rst.put("uuid", uuid);
        return rst;
    }

    /**
     * 客户自动匹配
     * @param company
     * @return
     */
    @Override
    public JSONObject autoMatch(Company company) {
        JSONObject rst = generateRst();

        Condition condition = new Condition();
        condition.setCondition("uuid", company.getUuid());
        condition.setSplitValue(company.getCustomerId());
        String[] fields = {"autoMatchFlag", "matchStatus", "email", "phone", "fax", "mobile"};
        try {
            Company result = companyDao.queryById(condition);
            if(result == null) {
                rst.put(YWYConsts.RC, ErrorCode.MATCH_NO_FIND);
                return rst;
            } else if (result.getMatchStatus() != null && result.getMatchStatus() == CompanyEnum.MATCHSTATUS_SUCC.getVal()) {
                rst.put(YWYConsts.RC, ErrorCode.CUSTOMER_MATACED);
                return rst;
            }
            company.setAutoMatchFlag(CompanyEnum.AUTO_MATCH_FLAG_OPEN.getVal());
            company.setDomain(result.getDomain());
            // 获取联系人总库数据
            getDomainInfo(company);
            companyDao.update(condition, company, fields);
            rst.put("matchStatus", company.getMatchStatus());
        } catch (Exception e) {
            LOG.error("customer auto match error: [] ", e);
            e.printStackTrace();
            rst = generateErrorRst();
        }
        return rst;
    }

    /**
     * 保存（转化和保存使用）
     * @param company
     * @param fields
     * @return
     */
    private JSONObject save(Company company, String[] fields) {
        JSONObject rst = generateRst();
        // 设置标签,标签做特殊处理方便后续查询
        company.setTagIds(getSaveTagIds(company.getTagIds(), company.getSource()));
        if(StringUtil.isEmpty(company.getUuid())) { // 新增
            Page<Company> page = new Page<>();
            page.setPageNo(1);
            // uuid通过客户id+公司名称md5生成
//            String uuid = MD5Util.ecodeByMD5(company.getCustomerId() + company.getName());
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            company.setUuid(uuid);
//            Condition condition = new Condition();
//            condition.setCondition("uuid", company.getUuid());
//            condition.setSplitValue(company.getCustomerId());
//            Company company1  = companyDao.queryById(condition);
//            if (company1 != null) {
//                rst.put(YWYConsts.RC, ErrorCode.DUPLICATE_COMPANY);
//                return rst;
//            }
            Company serialNumber  = companyDao.queryMaxSerialNumber(company, page);
            if (serialNumber != null) {
                company.setSerialNumber(serialNumber.getSerialNumber() + 1);
            } else {
                company.setSerialNumber(1l);
            }
            company.setStatus(CompanyEnum.STATUS_ENABLED.getVal());
//            company.setMatchStatus(CompanyEnum.MATCHSTATUS_INIT.getVal());
            company.setCreateTime(new Date());
            Long id = companyDao.add(company, CollectionUtils.arrayToList(fields));
            company.setId(id);
            saveCompanyContactor(company);
            if(company.isSendFlag()) { // 如果发送数据中心，保存对应关系，方便后续更新
                saveMatchInfo(company);
            }
        } else {
            Page<Company> page = new Page<>();
            page.setPageNo(1);
            Company company1  = companyDao.queryByUuid(company, page);
            if (company1 == null) {
                rst.put(YWYConsts.RC, ErrorCode.NO_COMPANY);
                return rst;
            }
            company.setStatus(company1.getStatus());
            company.setSerialNumber(company1.getSerialNumber());
            company.setCreateTime(company1.getCreateTime());
            company.setAutoMatchFlag(company1.getStatus());
            company.setMatchStatus(company1.getMatchStatus());
            company.setSyncTime(company1.getSyncTime());
            Condition condition = new Condition();
            condition.setCondition("uuid", company.getUuid());
            condition.setSplitValue(company.getCustomerId());
            companyDao.update(condition, company, fields);
            company.setId(company1.getId());
            saveCompanyContactor(company);
        }
        // 客户与联系人对应关系存储

        rst.put("uuid", company.getUuid());
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
            LOG.info("导入案件人员：" + crmAccount.getId() + "，上传的文件名为：" + fileName);
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
                        Company company = (Company) JSONObject.toBean(jsonObject, Company.class);
                        if(!StringUtil.isUrl(company.getDomain())) {
                            throw new YWYException(100002, "导入域名格式异常:" + company.getDomain());
                        }
                        company.setCustomerId(crmAccount.getCustomerId());
                        company.setCreatorId(crmAccount.getId());
                        company.setSource(DatasourceEnum.DATASOURCE_EXCEL.getVal());
                        // 设置其它账户
                        company.setSocialAccts(getSocialAccts(jsonObject));
                        JSONObject saveResult = this.save(company);
                        if("0".equals(saveResult.get(YWYConsts.RC))) succCount ++;
                    } catch (YWYException e) {
                        LOG.error("保存导入的客户信息失败：" + jsonObject.toString() + "：" + e.getMessage(), e);
                        throw new YWYException(e.getCode(), e.getMessage());
                    } catch (Exception e) {
                        LOG.error("保存导入的信息失败：" + jsonObject.toString() + "：" + e.getMessage(), e);
                        throw new YWYException(ErrorCode.OPERATOR_ERROR, "导入失败，请联系管理员");
                    }
                }
            }
            return rst;
        } catch (YWYException e) {
            LOG.info("客户excel导入失败：" + e.getMessage(), e);
            rst.put(YWYConsts.RC, e.getCode());
            rst.put(YWYConsts.ERR_MSG, e.getMessage());
        } catch (Exception e) {
            LOG.info("客户excel导入失败：" + e.getMessage(), e);
            rst.put(YWYConsts.RC, YWYConsts.FAIL);
            rst.put(YWYConsts.ERR_MSG, "导入失败");
        }
        return rst;
    }

    /**
     * 客户联系人对应关系保存
     * @param company
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void saveCompanyContactor(Company company) {
        try {
            // 校验关系是否存在，存在更新，不存在新增
            Condition condition1 = new Condition();
            condition1.setCondition("companyId", company.getId());
            condition1.setCondition("customerId", company.getCustomerId());
            condition1.setSplitValue(company.getCustomerId());
            companyContactorDao.delete(condition1);
            if(StringUtil.isEmpty(company.getContactor())) {
                LOG.error("COMPANY CONTACTOR IS EMPTY");
                return;
            }
            JSONArray jsonArray = JSONArray.fromObject(company.getContactor());
            String[] updateFields = {"department","job","title","managerName"};
            String[] addFields = {"uuid","companyId","contactorId","customerId","department","job","title","managerName"};
            // 删除之前关联的所有联系人
            if(jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    CompanyContactor companyContactor = (CompanyContactor) JSONObject.toBean(json, CompanyContactor.class);
                    companyContactor.setUuid(MD5Util.ecodeByMD5(companyContactor.getCustomerId() + "|" + companyContactor.getCompanyId() + "|" + companyContactor.getContactorId()));
                    companyContactor.setCustomerId(company.getCustomerId());
                    companyContactor.setCompanyId(company.getId());
                    companyContactorDao.add(companyContactor, CollectionUtils.arrayToList(addFields));
//                    condition1 = new Condition();
//                    condition1.setCondition("id", companyContactor.getUuid());
//                    condition1.setSplitValue(company.getCustomerId());
//                    Contactor cur = contactorDao.queryById(condition1);
//                    if(cur != null) {
//                        companyContactor.setContactorId(cur.getId());
//                    }
//                    Condition condition = new Condition();
//                    condition.setCondition("companyId", companyContactor.getCompanyId());
//                    condition.setCondition("contactorId", companyContactor.getContactorId());
//                    condition.setCondition("customerId", companyContactor.getCustomerId());
//                    condition.setSplitValue(company.getCustomerId());
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
        String param = "wechatAcct,qqAcct,Google+,Weibo,tumblr,Youtube,WhatsApp,Facebook Messenger,Skype,snapchat,imessage,kakao,Line,Viber,Vkontakte,Odnoklassniki";
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
     * 获取联系人总库域名信息
     * @param company
     */
    private void getDomainInfo(Company company) {
        Condition pool = new Condition();
        pool.setCondition("domain", company.getDomain());
        pool.setSplitValue(StringUtil.getPoolSplitNumber(company.getDomain()));
        ContactorPool contactorPool = contactorPoolDao.queryById(pool);
        if(contactorPool != null && contactorPool.getMatched() == 1l) {
            LOG.info("GET CONTACT POOL INFO:" + JSONObject.fromObject(contactorPool));
            if(StringUtil.isNotBlank(contactorPool.getEmail())) {
                JSONObject jsonObject = JSONObject.fromObject(contactorPool.getEmail());
                company.setEmail(jsonObject != null ? jsonObject.getString("email1") : null);
            }
            if(StringUtil.isNotBlank(contactorPool.getPhone())) {
                JSONObject jsonObject = JSONObject.fromObject(contactorPool.getPhone());
                company.setPhone(jsonObject != null ? jsonObject.getString("telphone1") : null);
            }
            if(StringUtil.isNotBlank(contactorPool.getMobile())) {
                JSONObject jsonObject = JSONObject.fromObject(contactorPool.getMobile());
                company.setMobile(jsonObject != null ? jsonObject.getString("mobile1") : null);
            }
            if(StringUtil.isNotBlank(contactorPool.getFax())) {
                JSONObject jsonObject = JSONObject.fromObject(contactorPool.getFax());
                company.setFax(jsonObject != null ? jsonObject.getString("fax1") : null);
            }
            // 社交账号不设置，详情查询时提供线索
//            company.setFacebookAcct(contactorPool.getFaceBookAcct());
//            company.setLinkedInAcct(contactorPool.getLinkedinAcct());
//            company.setTwitterAcct(contactorPool.getTwitterAcct());
//            company.setPinterestAcct(contactorPool.getPinterestAcct());
//            company.setInstagramAcct(contactorPool.getInstagramAcct());
//            company.setSocialAccts(contactorPool.getSocialAccts());
            company.setMatchStatus(CompanyEnum.MATCHSTATUS_SUCC.getVal());
            // 获取whois中国家信息
            if(!StringUtil.isEmpty(contactorPool.getWhoisRegContact())) {
                JSONObject reg = JSONObject.fromObject(contactorPool.getWhoisRegContact());
                if(reg.containsKey("Country")) {
                    company.setCountry(reg.getString("Country"));
                }
            }
            // 更新联系人总库表match状态
            if(contactorPool.getMatched() != null && contactorPool.getMatched() == 0l) {
                String[] fields = {"matched"};
                contactorPool.setMatched(1l);
                contactorPoolDao.update(pool, contactorPool, fields);
            }
        } else {
            company.setMatchStatus(CompanyEnum.MATCHSTATUS_PROC.getVal());
            company.setSendFlag(true);
            Map map = new HashMap();
            map.put("domain", company.getDomain());
            String result = httpService.processRequestStrV2(spiderUrl, map);
            LOG.info("DOMAIN:" + company.getDomain() + ",GET DATA CENTER SPIDER REQUEST RESULT:" + result);
        }
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

    /**
     * 生成匹配关系
     * @param company
     */
    private void saveMatchInfo(Company company) {
        String[] fields = {"customerId", "companyId", "creatorId", "domain", "matchStatus"};
        CompanyContactorMatch companyContactorMatch = new CompanyContactorMatch();
        companyContactorMatch.setCustomerId(company.getCustomerId());
        companyContactorMatch.setCreatorId(company.getCreatorId());
        companyContactorMatch.setCompanyId(company.getId());
        companyContactorMatch.setDomain(company.getDomain());
        companyContactorMatch.setMatchStatus(0);
        companyContactorMatchDao.add(companyContactorMatch, CollectionUtils.arrayToList(fields));
    }

    @Override
    public String exportList(Integer pageNo, Company company, CrmAccount crmAccount) {
        JSONObject rst = generateRst();
        Page<Company> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(Integer.valueOf(exportSize));
        try {
            // 只查询有效状态的客户
            company.setStatus(CompanyEnum.STATUS_ENABLED.getVal());
            Page<Company> pageList = companyDao.query(company, page);
            if (pageList != null && pageList.getData() != null) {
                StringBuffer sb = new StringBuffer();
                List<Company> list = new ArrayList<>(pageList.getData().size());
//                list.add("客户名称,客户编号,域名,行业,国家,邮箱,电话号码前缀,电话号码,手机号码前缀,手机号码,传真前缀,传真,地址,其它地址,备注," +
//                        "pinterestAcct,linkedInAcct,facebookAcct,instagramAcct,twitterAcct,wechatAcct,qqAcct,Google+," +
//                        "Weibo,tumblr,Youtube,WhatsApp,Facebook Messenger,Skype,snapchat,imessage,kakao,Line,Viber,Vkontakte,Odnoklassniki");
                for (Company company1 : pageList.getData()) {
//                    sb = new StringBuffer();
//                    sb.append(company1.getName() != null ? company1.getName() : "").append(",")
//                            .append(company1.getCustomerCode() != null ? company1.getCustomerCode() : "").append(",")
//                            .append(company1.getDomain() != null ? company1.getDomain() : "").append(",")
//                            .append(company1.getIndustry() != null ? company1.getIndustry() : "").append(",")
//                            .append(company1.getCountry() != null ? company1.getCountry() : "").append(",")
//                            .append(company1.getEmail() != null ? company1.getEmail() : "").append(",")
//                            .append(company1.getPhonePrefix() != null ? company1.getPhonePrefix() : "").append(",")
//                            .append(company1.getPhone() != null ? company1.getPhone() : "").append(",")
//                            .append(company1.getMobilePrefix() != null ? company1.getMobilePrefix() : "").append(",")
//                            .append(company1.getMobile() != null ? company1.getMobile() : "").append(",")
//                            .append(company1.getFaxPrefix() != null ? company1.getFaxPrefix() : "").append(",")
//                            .append(company1.getFax() != null ? company1.getFax() : "").append(",")
//                            .append(company1.getAddress() != null ? company1.getAddress() : "").append(",")
//                            .append(company1.getOtherAddress() != null ? company1.getOtherAddress() : "").append(",")
//                            .append(company1.getNote() != null ? company1.getNote() : "").append(",")
//                            .append(company1.getPinterestAcct() != null ? company1.getPinterestAcct() : "").append(",")
//                            .append(company1.getLinkedInAcct() != null ? company1.getLinkedInAcct() : "").append(",")
//                            .append(company1.getFacebookAcct() != null ? company1.getFacebookAcct() : "").append(",")
//                            .append(company1.getInstagramAcct() != null ? company1.getInstagramAcct() : "").append(",")
//                            .append(company1.getTwitterAcct() != null ? company1.getTwitterAcct() : "").append(",")
//                            .append(company1.getWechatAcct() != null ? company1.getWechatAcct() : "").append(",")
//                            .append(company1.getQqAcct() != null ? company1.getQqAcct() : "").append(",");
                    if(StringUtil.isNotBlank(company1.getSocialAccts())) {
                        JSONObject socialJson = new JSONObject();
                        JSONArray social = JSONArray.fromObject(company.getSocialAccts());
                        if(social != null && social.size() > 0) {
                            for (int i = 0; i < social.size(); i++) {
                                JSONObject json = social.getJSONObject(i);
                                if(json.containsKey("actName")) {
                                    socialJson.put(json.getString("actName"), json.getString("val"));
                                }
                            }
                        }
                        company1.setGoogle(socialJson.containsKey("Google+") ? socialJson.getString("Google+") : "");
                        company1.setWeibo(socialJson.containsKey("Weibo") ? socialJson.getString("Weibo") : "");
                        company1.setTumblr(socialJson.containsKey("tumblr") ? socialJson.getString("tumblr") : "");
                        company1.setYoutube(socialJson.containsKey("Youtube") ? socialJson.getString("Youtube") : "");
                        company1.setWhatsApp(socialJson.containsKey("Facebook Messenger") ? socialJson.getString("Facebook Messenger") : "");
                        company1.setSkype(socialJson.containsKey("Skype") ? socialJson.getString("Skype") : "");
                        company1.setSnapchat(socialJson.containsKey("snapchat") ? socialJson.getString("snapchat") : "");
                        company1.setImessage(socialJson.containsKey("imessage") ? socialJson.getString("imessage") : "");
                        company1.setKakao(socialJson.containsKey("kakao") ? socialJson.getString("kakao") : "");
                        company1.setLine(socialJson.containsKey("Line") ? socialJson.getString("Line") : "");
                        company1.setViber(socialJson.containsKey("Viber") ? socialJson.getString("Viber") : "");
                        company1.setVkontakte(socialJson.containsKey("Vkontakte") ? socialJson.getString("Vkontakte") : "");
                        company1.setOdnoklassniki(socialJson.containsKey("Odnoklassniki") ? socialJson.getString("Odnoklassniki") : "");;
//                                sb.append(socialJson.containsKey("Google+") ? socialJson.getString("Google+") : "").append(",")
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
                    list.add(company1);
                }
                String path = excelExportUtil.doExportData("company.xls", Company.class, list, null, custExportParam);
                return path;
//                String path = exportPath + File.separator + "company" + File.separator + DateUtil.customFormat("yyyyMMdd", new Date()) + "_" + company.getCustomerId();
//                String name = UUID.randomUUID().toString().replaceAll("-", "") + ".csv";
//                boolean result = CSVUtils.exportCsv(new File(path), name, list);
//                if(result) {
//                    return path + File.separator + name;
//                } else {
//                    throw new YWYException(9999, "导出失败");
//                }
            }
        } catch (YWYException e) {
            LOG.error("COMPANY EXPORT CSV ERROR:" + e.getMessage(), e);
            throw new YWYException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            LOG.error("COMPANY EXPORT CSV ERROR:" + e.getMessage(), e);
            throw new YWYException(9999, "导出失败");
        }
        return null;
    }

    /**
     * 保存联系人总库统计信息
     * @param company
     */
    private void saveContactorPool(Company company) {
        ContactorPool contactorPool = new ContactorPool();
        contactorPool.setDomain(company.getDomain());
        JSONObject rst = generateRst();
        String[] fields = {"id","domain","email","phone","fax","mobile","faceBookAcct",
                "linkedinAcct","twitterAcct","pinterestAcct","instagramAcct","socialAccts",
                "whoisDomainInfo","whoisRegContact","whoisAdminContact","whoisTechContact","matched"};
        Condition condition = new Condition();
        condition.setCondition("domain", contactorPool.getDomain());
        Long splitNumber = StringUtil.getPoolSplitNumber(contactorPool.getDomain());
        condition.setSplitValue(splitNumber);
        contactorPool.setSplitNumber(splitNumber);
        ContactorPool check = contactorPoolDao.queryById(condition);
        boolean dupFlag = false;
        if(check == null) {
            contactorPool.setMatched(0l);
            contactorPoolDao.add(contactorPool, CollectionUtils.arrayToList(fields));
        } else {
            dupFlag = true;
        }
        saveContactorPoolStatics(dupFlag);
    }

    /**
     * 保存统计信息
     * @param dupFlag
     */
    private void saveContactorPoolStatics(boolean dupFlag) {
        String[] fields = {"requestTotal","requestTotalNoDup","domainTotal"};
        Condition condition = new Condition();
        DomainStatics domainStatics = domainStaticsDao.queryById(condition);
        if(domainStatics != null) {
            if(!dupFlag) {
                domainStatics.setRequestTotalNoDup((domainStatics.getRequestTotalNoDup() != null ? domainStatics.getRequestTotalNoDup() : 0) + 1);
                domainStatics.setDomainTotal((domainStatics.getDomainTotal() != null ? domainStatics.getDomainTotal() : 0) + 1);
            }
            domainStatics.setRequestTotal((domainStatics.getRequestTotal() != null ? domainStatics.getRequestTotal() : 0) + 1);
            domainStaticsDao.update(condition, domainStatics, fields);
        }
    }

}
