package com.ywy.service.clue.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ywy.entity.clue.*;
import com.ywy.enumtype.CompanyEnum;
import com.ywy.repository.clue.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ywy.consts.EmailTemplate;
import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.entity.customer.MgCustomers;
import com.ywy.entity.emailJob.EmailJob;
import com.ywy.entity.industry.CrmIndustry;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.entity.sys.MgAccount;
import com.ywy.enumtype.ConditionType;
import com.ywy.parameter.Condition;
import com.ywy.repository.customer.MgCustomersRepository;
import com.ywy.repository.emailJob.EmailJobRepository;
import com.ywy.repository.industry.CrmIndustryRepository;
import com.ywy.repository.sys.MgAccountRepository;
import com.ywy.service.EmailService;
import com.ywy.service.clue.ClueService;
import com.ywy.service.impl.CommonService;
import com.ywy.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service
public class ClueServiceImpl extends CommonService implements ClueService {


    @Resource
    private ClueRepository clueRepository;
    @Resource
    private CrmClueApplyRepository crmClueApplyDao;
    @Resource
    private CrmIndustryRepository crmIndustryRepository;
    @Resource
    private MgClueAuthAuditRepository mgClueAuthAuditDao;
    @Resource
    private CrmClueApplysRepository crmClueApplysDao;
    @Resource
	private CrmAllocatHisRepository crmAllocatHisDao;
    @Resource
	private MgAccountRepository mgAccountDao;
    @Resource
	private MgCustomersRepository mgCustomersDao;
    @Resource
   	private EmailService emailService;
    @Resource
    private EmailJobRepository emailJobDao;
	@Resource
    private CrmClueRepository crmClueDao;

    @Override
    public JSONObject save(Long industryId,String searchTypeCode,CrmAccount account) {
    	JSONObject rst = generateRst();
    	if(account.getRole() != 1) {
    		rst.put(YWYConsts.RC, ErrorCode.NO_UPDATE_AUTH);
        	return rst;
    	}
    	if(StringUtils.isEmpty(searchTypeCode)){
			rst.put(YWYConsts.RC, ErrorCode.DUPLICATE_AUDIT);
			return rst;
		}
    	Condition condition=new Condition();
    	condition.setCondition("industryId", industryId);
    	condition.setCondition("customerId", account.getCustomerId());
    	condition.setCondition("searchTypeCode", searchTypeCode);
    	CrmClueApply crmClue=crmClueApplyDao.query(condition);
    	if(crmClue!=null){
    		if(crmClue.getStatus() == 1) {
        		rst.put(YWYConsts.RC, ErrorCode.NO_AUDIT);
        	}
        	else if(crmClue.getStatus() == 2) {
        		rst.put(YWYConsts.RC, ErrorCode.AUDIT);
        	}
        	else if(crmClue.getStatus() == 5) {
        		rst.put(YWYConsts.RC, ErrorCode.DUPLICATE_AUDIT);
        	}
        	return rst;
    	}else{
    		CrmClueApply crmClueApply=new CrmClueApply();
    		crmClueApply.setCreatetime(new Date());
    		crmClueApply.setCustomerId(account.getCustomerId());
    		crmClueApply.setSubmitorId(account.getId());
    		crmClueApply.setIndustryId(industryId);
    		crmClueApply.setStatus(1);
    		crmClueApply.setSearchTypeCode(searchTypeCode);
    		crmClueApplyDao.insert(crmClueApply);
    	}
		//根据行业id找到对应的员工
		condition.getConditions().clear();
		condition.setCondition("id", industryId);
		CrmIndustry crmIndustry = crmIndustryRepository.queryIndustry(condition);
		String path=crmIndustry.getPath();
		MgAccount mgAccount = new MgAccount();
		if(!StringUtils.isBlank(path) && path.split("#").length>2){
			long tradeId = Long.parseLong(path.split("#")[2]);
			condition.getConditions().clear();
    		condition.setCondition("industryIds", tradeId,ConditionType.LIKE);
    		mgAccount = mgAccountDao.queryAccount(condition);
		}
		//根据公司id找到对应的公司信息
		condition.getConditions().clear();
		condition.setCondition("id", account.getCustomerId());
		MgCustomers mgCustomers = mgCustomersDao.query(condition);
		String companyName = mgCustomers.getCompanyName();
		String keyWord = crmIndustry.getNodeName();
		if(mgAccount!=null) {
//			说明已经分配员工了
			String emailHead=EmailTemplate.TEMPLATEHEAD;
			String emailStr=emailHead+EmailTemplate.NEW_CRMINDUSTRY_MANAGE;
			String emailEnd=EmailTemplate.TEMPLATEEND;
			emailStr=emailStr.replaceFirst("name", mgAccount.getName());
			emailStr=emailStr.replaceFirst("time", DateUtil.dot14Format(new Date()));
			emailStr=emailStr.replaceFirst("companyName", companyName);
			emailStr=emailStr.replaceFirst("keyWord", keyWord);
			emailStr=emailStr+emailEnd;
			EmailJob emailJob=new EmailJob();
			emailJob.setSendFrom(account.getId());
			emailJob.setCreateTime(new Date());
			emailJob.setSendToEmail(mgAccount.getEmail());
			emailJob.setTitle("线索申请通知");
			emailJob.setContent(emailStr);
			emailJob.setSource(1);
			emailJobDao.insert(emailJob, null);
		}else{
			//未分配员工
			condition.getConditions().clear();
			condition.setCondition("role", 1);
			mgAccount = mgAccountDao.queryAccount(condition);
			String emailHead=EmailTemplate.TEMPLATEHEAD;
			String emailStr=emailHead+EmailTemplate.NEW_CRMINDUSTRY_NOMANAGE;
			String emailEnd=EmailTemplate.TEMPLATEEND;
			emailStr=emailStr.replaceFirst("name", mgAccount.getName());
			emailStr=emailStr.replaceFirst("time", DateUtil.dot14Format(new Date()));
			emailStr=emailStr.replaceFirst("companyName", companyName);
			emailStr=emailStr.replaceFirst("keyWord", keyWord);
			emailStr=emailStr+emailEnd;
			EmailJob emailJob=new EmailJob();
			emailJob.setSendFrom(account.getId());
			emailJob.setCreateTime(new Date());
			emailJob.setSendToEmail(mgAccount.getEmail());
			emailJob.setTitle("线索申请通知");
			emailJob.setContent(emailStr);
			emailJob.setSource(1);
			emailJobDao.insert(emailJob, null);
		}
        return rst;
    }

    @Override
    public JSONObject saveIndustryByclueName(String clueName, Integer parentIndustryId,CrmAccount account) {
        JSONObject rst = generateRst();
        if(account.getRole() != 1) {
        	rst.put(YWYConsts.RC, ErrorCode.NO_UPDATE_AUTH);
        	return rst;
        }
        Condition condition=new Condition(); 
        condition.setCondition("nodeName", clueName);
        condition.setCondition("parentId", parentIndustryId);
        CrmIndustry crmIndustry = crmIndustryRepository.queryIndustry(condition);
        if(crmIndustry!=null){
        	rst.put(YWYConsts.RC, ErrorCode.DUPLICATE_INDUSTRY);
        	return rst;
        }
        MgClueAuthAudit mcaa=new MgClueAuthAudit();
				        mcaa.setName(clueName);
				        mcaa.setParentindustryid(parentIndustryId);
				        mcaa.setCustomerid(account.getCustomerId());
				        mcaa.setCreatorid(account.getId());
				        mcaa.setStatus(1);
				        mcaa.setCreatetime(new Date());
		mgClueAuthAuditDao.insert(mcaa);	
        return rst;
    }

	@Override
	public JSONObject queryClues(long customerId) {
		JSONObject rst = generateRst();
		List<CrmClueApplys>  crmClueApplys=crmClueApplysDao.queryClues(customerId);
		JsonConfig config = new JsonConfig();
		JSONArray dataList = JSONArray.fromObject(crmClueApplys, config);
		rst.put("dataList", dataList);
		return rst;
	}
	
	@Override
	public JSONObject queryAllocateClues(long customerId) {
		JSONObject rst = generateRst();
		List<CrmAllocateClueHis>  crmClueApplys=crmAllocatHisDao.queryClues(customerId);
		JsonConfig config = new JsonConfig();
		JSONArray dataList = JSONArray.fromObject(crmClueApplys, config);
		rst.put("dataList", dataList);
		return rst;
	}


	/**
	 * 删除线索
	 * @param customerId
	 * @param clueId
	 * @return
	 */
	@Override
	public JSONObject delete(long customerId, String clueId) {
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		condition.setCondition("clueId", clueId);
		condition.setSplitValue(customerId);
		String[] fields = {"validFlag"};
		// 设置状态为无效
		CrmClue crmClue = new CrmClue();
		crmClue.setValidFlag(CompanyEnum.STATUS_DISABLED.getVal());
		crmClueDao.update(condition, crmClue, fields);
		return rst;
	}
}
