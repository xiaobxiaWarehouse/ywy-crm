package com.ywy.service.contactor.impl;

import com.ywy.entity.Page;
import com.ywy.entity.company.Company;
import com.ywy.entity.company.CompanyContactorMatch;
import com.ywy.entity.contactor.ContactorPool;
import com.ywy.entity.contactor.DomainStatics;
import com.ywy.enumtype.CompanyEnum;
import com.ywy.parameter.Condition;
import com.ywy.repository.company.CompanyContactorMatchRepository;
import com.ywy.repository.company.CompanyRepository;
import com.ywy.repository.contactor.ContactorPoolRepository;
import com.ywy.repository.contactor.DomainStaticsRepository;
import com.ywy.service.contactor.ContactorPoolService;
import com.ywy.service.impl.CommonService;
import com.ywy.util.JsonValueProcessorImpl;
import com.ywy.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;

@Service
public class ContactorPoolServiceImpl extends CommonService implements ContactorPoolService {

    private static final Logger LOG = LoggerFactory.getLogger(ContactorPoolServiceImpl.class);

    @Autowired
    private ContactorPoolRepository contactorPoolDao;

    @Autowired
    private DomainStaticsRepository domainStaticsDao;

    @Autowired
    private CompanyContactorMatchRepository companyContactorMatchDao;

    @Autowired
    private CompanyRepository companyDao;

    /**
     * 获取联系人总库列表
     * @param pageNo
     * @param contactorPool
     * @return
     */
    @Override
    public JSONObject getListByCondition(Integer pageNo, ContactorPool contactorPool) {

        JSONObject rst = generateRst();
        Page<ContactorPool> page = new Page<>();
        page.setPageNo(pageNo);

        try {
            String[] domains = contactorPool.getDomain().split("\\|");
            ContactorPool query;
            JSONArray array = new JSONArray();
            for (String domain : domains) {
                query = new ContactorPool();
                query.setDomain(domain);
                query.setSplitNumber(StringUtil.getPoolSplitNumber(domain));
                Page<ContactorPool> pageList = contactorPoolDao.query(query, page);
                JSONObject object;
                if (pageList != null && pageList.getData() != null) {
                    for (ContactorPool contactorPool1 : pageList.getData()) {
                        object = new JSONObject();
                        object.put("id", contactorPool1.getId());
                        object.put("domain", contactorPool1.getDomain());
                        object.put("email", contactorPool1.getEmail());
                        object.put("phone", contactorPool1.getPhone());
                        object.put("pinterestAcct", contactorPool1.getPinterestAcct());
                        object.put("linkedInAcct", contactorPool1.getLinkedinAcct());
                        object.put("facebookAcct", contactorPool1.getFaceBookAcct());
                        object.put("instagramAcct", contactorPool1.getInstagramAcct());
                        object.put("twitterAcct", contactorPool1.getTwitterAcct());
                        object.put("whois", contactorPool1.getWhoisDomainInfo());
                        array.add(object);
                    }
                }
            }
            rst.put("dataList", array);
            Page<ContactorPool> pageList = new Page<>();
            pageList.setTotalCount(array.size());
            rst.put("page", generatePage(pageList));
            // 获取统计信息
            Condition condition = new Condition();
            DomainStatics domainStatics = domainStaticsDao.queryById(condition);
            if(domainStatics != null) {
                rst.put("requestTotal", domainStatics.getRequestTotal());
                rst.put("matchedTotal", domainStatics.getMatchedTotal());
                rst.put("requestTotalNoDup", domainStatics.getRequestTotalNoDup());
                rst.put("domainTotal", domainStatics.getDomainTotal());
            } else {
                rst.put("requestTotal", 0);
                rst.put("matchedTotal", 0);
                rst.put("requestTotalNoDup", 0);
                rst.put("domainTotal", 0);
            }
        } catch (Exception e) {
            LOG.error("get contactor pool list error: [] by name and keyWords: []", e);
            rst = generateErrorRst();
        }
        return rst;
    }

    /**
     * 获取联系人总库列表
     * @return
     */
    @Override
    public JSONObject statics() {

        JSONObject rst = generateRst();

        try {
            Condition condition = new Condition();
            DomainStatics domainStatics = domainStaticsDao.queryById(condition);
            if(domainStatics != null) {
                rst.put("requestTotal", domainStatics.getRequestTotal());
                rst.put("matchedTotal", domainStatics.getMatchedTotal());
                rst.put("requestTotalNoDup", domainStatics.getRequestTotalNoDup());
                rst.put("domainTotal", domainStatics.getDomainTotal());
            } else {
                rst.put("requestTotal", 0);
                rst.put("matchedTotal", 0);
                rst.put("requestTotalNoDup", 0);
                rst.put("domainTotal", 0);
            }
        } catch (Exception e) {
            LOG.error("get contactor pool list error: [] by name and keyWords: []", e);
            rst = generateErrorRst();
        }
        return rst;
    }

    /**
     * 保存
     * @param contactorPool
     * @return
     */
    @Override
    public JSONObject save(ContactorPool contactorPool) {
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
        if(check != null) {
            dupFlag = true;
            contactorPool.setId(check.getId());
            // 数据中心过来设置为已匹配
            contactorPool.setMatched(1l);
            contactorPoolDao.update(condition, contactorPool, fields);
        } else {
            contactorPool.setMatched(0l);
            contactorPoolDao.add(contactorPool, CollectionUtils.arrayToList(fields));
        }
//        long matchTotal = 0;
        // 更新客户表匹配状态
        Page<CompanyContactorMatch> page = new Page<>();
        page.setPageNo(1);
        CompanyContactorMatch companyContactorMatch = new CompanyContactorMatch();
        companyContactorMatch.setDomain(contactorPool.getDomain());
        companyContactorMatch.setMatchStatus(0);
        Page<CompanyContactorMatch> pageList = companyContactorMatchDao.query(companyContactorMatch, page);
        if(pageList != null && pageList.getData() != null) {
            String[] fields1 = {"matchStatus", "email", "phone", "fax", "mobile"};
            for (CompanyContactorMatch match : pageList.getData()) {
                condition = new Condition();
                condition.setCondition("id", match.getCompanyId());
                condition.setSplitValue(match.getCustomerId());
                Company result = companyDao.queryById(condition);
                if(result != null) {
                    result.setMatchStatus(CompanyEnum.MATCHSTATUS_SUCC.getVal());
                    if(StringUtil.isNotBlank(contactorPool.getEmail())) {
                        JSONObject jsonObject = JSONObject.fromObject(contactorPool.getEmail());
                        result.setEmail(jsonObject != null ? jsonObject.getString("email1") : null);
                    }
                    if(StringUtil.isNotBlank(contactorPool.getPhone())) {
                        JSONObject jsonObject = JSONObject.fromObject(contactorPool.getPhone());
                        result.setPhone(jsonObject != null ? jsonObject.getString("telphone1") : null);
                    }
                    if(StringUtil.isNotBlank(contactorPool.getMobile())) {
                        JSONObject jsonObject = JSONObject.fromObject(contactorPool.getMobile());
                        result.setMobile(jsonObject != null ? jsonObject.getString("mobile1") : null);
                    }
                    if(StringUtil.isNotBlank(contactorPool.getFax())) {
                        JSONObject jsonObject = JSONObject.fromObject(contactorPool.getFax());
                        result.setFax(jsonObject != null ? jsonObject.getString("fax1") : null);
                    }
                    companyDao.update(condition, result, fields1);
                    // 更新匹配状态

//                    matchTotal ++ ;
                }
                condition.setCondition("id", match.getId());
                condition.setSplitValue(0);
                companyContactorMatchDao.delete(condition);
            }
        }
//        if(matchTotal > 0) {
//            contactorPool.setMatched(1l);
//            contactorPoolDao.update(condition, contactorPool, fields);
//        }
        saveStatics(check.getMatched());
        return rst;
    }

    @Override
    public JSONObject queryById(ContactorPool contactorPool) {
        JSONObject rst = generateRst();
        Condition condition = new Condition();
        condition.setCondition("id", contactorPool.getId());
        condition.setCondition("domain", contactorPool.getDomain());
        condition.setSplitValue(StringUtil.getPoolSplitNumber(contactorPool.getDomain()));
        try {
            ContactorPool result = contactorPoolDao.queryById(condition);
            JsonConfig config = new JsonConfig();
            config.registerJsonValueProcessor(Date.class,new JsonValueProcessorImpl());
            JSONObject jsonObject = JSONObject.fromObject(result, config);
            rst.put("data", jsonObject);
        } catch (Exception e) {
            LOG.error("get contactor pool detail error: [] ", e);
            rst = generateErrorRst();
        }
        return rst;
    }

    @Override
    public JSONObject delete(ContactorPool contactorPool) {
        JSONObject rst = generateRst();

        Condition condition = new Condition();
        condition.setCondition("id", contactorPool.getId());
        condition.setCondition("domain", contactorPool.getDomain());
        condition.setSplitValue(StringUtil.getPoolSplitNumber(contactorPool.getDomain()));
        contactorPoolDao.delete(condition, contactorPool);
        return rst;
    }

    /**
     * 保存联系人总库统计信息
     * @param matched
     */
    private void saveStatics(Long matched) {
        String[] fields = {"requestTotal","matchedTotal","requestTotalNoDup","domainTotal"};
        Condition condition = new Condition();
        DomainStatics domainStatics = domainStaticsDao.queryById(condition);
        if(domainStatics != null) {
            if(matched == 0l) {
//                domainStatics.setRequestTotalNoDup((domainStatics.getRequestTotalNoDup() != null ? domainStatics.getRequestTotalNoDup() : 0) + 1);
//                domainStatics.setDomainTotal((domainStatics.getDomainTotal() != null ? domainStatics.getDomainTotal() : 0) + 1);
                domainStatics.setMatchedTotal((domainStatics.getMatchedTotal() != null ? domainStatics.getMatchedTotal() : 0) + 1);
            }
//            domainStatics.setRequestTotal((domainStatics.getRequestTotal() != null ? domainStatics.getRequestTotal() : 0) + 1);
            domainStaticsDao.update(condition, domainStatics, fields);
        }
    }

}
