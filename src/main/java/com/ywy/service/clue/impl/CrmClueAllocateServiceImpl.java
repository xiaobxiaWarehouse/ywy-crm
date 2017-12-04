package com.ywy.service.clue.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ywy.consts.EmailTemplate;
import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.entity.Page;
import com.ywy.entity.clue.CrmClue;
import com.ywy.entity.clue.CrmClueAllocateHis;
import com.ywy.entity.emailJob.EmailJob;
import com.ywy.entity.industry.CrmIndustry;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.entity.sys.CrmAccountForCule;
import com.ywy.parameter.Condition;
import com.ywy.repository.clue.CrmClueAllocateHisRepository;
import com.ywy.repository.clue.CrmClueAllocateRepository;
import com.ywy.repository.clue.CrmClueRepository;
import com.ywy.repository.customer.MgCustomersRepository;
import com.ywy.repository.emailJob.EmailJobRepository;
import com.ywy.repository.industry.CrmIndustryRepository;
import com.ywy.repository.sys.AccountNumberRepository;
import com.ywy.repository.sys.CrmAccountRepository;
import com.ywy.repository.sys.MgAccountRepository;
import com.ywy.service.EmailService;
import com.ywy.service.clue.CrmClueAllocateService;
import com.ywy.service.impl.CommonService;
import com.ywy.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service
public class CrmClueAllocateServiceImpl extends CommonService implements CrmClueAllocateService {

	@Resource
	private CrmClueAllocateRepository crmClueAllocateDao;
	@Resource
	private CrmAccountRepository crmAccountDao;
	@Resource
	private CrmClueRepository crmClueDao;
	@Resource
	private CrmClueAllocateHisRepository crmClueAllocateHisDao;
	@Resource
	private MgCustomersRepository mgCustomersDao;
	@Resource
	private MgAccountRepository mgAccountDao;
    @Resource
	private CrmIndustryRepository crmIndustryRepository;
  	@Resource
	private EmailService emailService;
  	@Resource
    private AccountNumberRepository accountRepo;
  	@Resource
    private EmailJobRepository emailJobDao;

	
	@Override
	public JSONObject allocatedClue(int pageNo,CrmAccount account) {
		JSONObject rst = generateRst();
		Page<CrmAccountForCule> accounts = new Page<CrmAccountForCule>();
		accounts.setPageNo(pageNo);
		crmAccountDao.queryAccounts(accounts, account.getCustomerId(),2);
		JsonConfig config = new JsonConfig();
		JSONArray dataList = JSONArray.fromObject(processNull(accounts.getData()), config);
		rst.put("dataList", dataList);
		rst.put("page", generatePage(accounts));
		return rst;
	}


	@Override
	public JSONObject newDataCheck(CrmAccount crmAccount) {
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		condition.setCondition("assignto", "0");
		condition.setSplitValue(crmAccount.getCustomerId());
		int crmClues  = crmClueDao.checkAmount(condition);
		rst.put("newDataNum", crmClues);
		return rst;
	}


	@Override
	public JSONObject clueAllocate(long allocateNum, long assignTo, long industryId ,CrmAccount account ) {
		JSONObject rst = generateRst();
		//判断当前是否为管理员账号
		if(account.getRole() != 1) {
			rst.put(YWYConsts.RC, ErrorCode.NO_UPDATE_AUTH);
			return rst;
		}
		if(allocateNum<1) {
			rst.put(YWYConsts.RC, ErrorCode.DUPLICATE_NUM);
			return rst;
		}
		Condition condition = new Condition();
		condition.setCondition("id", assignTo);
		CrmAccount tmpAccount  = accountRepo.queryAccount(condition);
		if (tmpAccount == null) {
			rst.put(YWYConsts.RC, ErrorCode.NO_MATCH_DATA);
			return rst;
		}
		//判断该账号是否跟管理员为同一公司
		if(tmpAccount.getCustomerId() != account.getCustomerId()) {
			rst.put(YWYConsts.RC, ErrorCode.NO_UPDATE_MANAGE);
			return rst;
		}
		condition.getConditions().clear();
		condition.setCondition("industryId", industryId);
		condition.setCondition("assignto", 0);
		condition.setSplitValue(account.getCustomerId());
		List<CrmClue> crmClues  = crmClueDao.queryClues(condition);
		if (crmClues == null ||  crmClues.size() < allocateNum) {
			rst.put(YWYConsts.RC, ErrorCode.DUPLICATE_ALLOCATE);
			return rst;
		}
		
		condition.getConditions().clear();
		condition.setCondition("industryId", industryId);
		condition.setCondition("assignto", 0);
		condition.setSplitValue(account.getCustomerId());
		crmClueDao.updateLimit(condition, assignTo, new String[] { "assignTo" }, allocateNum); 
		
		//找到用户信息 并 更新本次的分配数量
		CrmAccount crmAccount = new CrmAccount();
		crmAccount.setId(assignTo);
		condition.getConditions().clear();
		condition.setCondition("id", assignTo);
		crmAccount = crmAccountDao.queryAccount(condition);
		if(crmAccount!=null) {
			crmAccount.setClueNum(crmAccount.getClueNum()+allocateNum);
			crmAccountDao.updateAccount(condition, crmAccount, new String[]{"clueNum"});
		}else {
			rst.put(YWYConsts.RC, ErrorCode.NO_CRM_ACCOUNT);
			return rst;
		}
		//添加客户分配记录
		CrmClueAllocateHis crmClueAllocateHis=new CrmClueAllocateHis();
		crmClueAllocateHis.setAssignTo(assignTo);
		crmClueAllocateHis.setClueNum(allocateNum);
		crmClueAllocateHis.setCreateTime(new Date());
		crmClueAllocateHis.setCustomerId(account.getCustomerId());
		crmClueAllocateHis.setIndustryId(industryId);
		crmClueAllocateHisDao.insert(crmClueAllocateHis);
		//数据分配给员工成功后的发送邮箱
		//查询关键字
		condition.getConditions().clear();
		condition.setCondition("id", industryId);
		CrmIndustry crmIndustry = crmIndustryRepository.queryIndustry(condition);
		String keyWord="";
		if(crmIndustry!=null){
			keyWord= crmIndustry.getNodeName();
		}
		//发送邮箱
		String emailHead=EmailTemplate.TEMPLATEHEAD;
		String emailStr=emailHead+EmailTemplate.NEW_CRMCLUE_ALLOCATE;
		String emailEnd=EmailTemplate.TEMPLATEEND;
		emailStr=emailStr.replaceFirst("name", crmAccount.getName());
		emailStr=emailStr.replaceFirst("time", DateUtil.dot14Format(new Date()));
		emailStr=emailStr.replaceFirst("clueNum", allocateNum+"");
		emailStr=emailStr.replaceFirst("keyWord", keyWord);
		emailStr=emailStr+emailEnd;
		EmailJob emailJob=new EmailJob();
		emailJob.setSendFrom(account.getId());
		emailJob.setCreateTime(new Date());
		emailJob.setSendToEmail(crmAccount.getEmail());
		emailJob.setTitle("线索分配通知");
		emailJob.setContent(emailStr);
		emailJob.setSource(1);
		emailJobDao.insert(emailJob, null);
		return rst;
	}


}
