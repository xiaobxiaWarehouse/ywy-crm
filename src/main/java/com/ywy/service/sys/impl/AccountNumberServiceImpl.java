package com.ywy.service.sys.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ywy.consts.EmailTemplate;
import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.entity.Page;
import com.ywy.entity.customer.MgCustomers;
import com.ywy.entity.emailJob.EmailJob;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.entity.sys.ResetSession;
import com.ywy.enumtype.ConditionType;
import com.ywy.parameter.Condition;
import com.ywy.repository.customer.MgCustomersRepository;
import com.ywy.repository.emailJob.EmailJobRepository;
import com.ywy.repository.sys.AccountNumberRepository;
import com.ywy.repository.sys.CrmAccountRepository;
import com.ywy.repository.sys.ResetSessionRepository;
import com.ywy.rowmaper.CustomerMapper;
import com.ywy.service.EmailService;
import com.ywy.service.impl.CommonService;
import com.ywy.service.sys.AccountNumberService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class AccountNumberServiceImpl extends CommonService implements AccountNumberService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountNumberService.class);

    @Resource
    private AccountNumberRepository accountRepo;
    @Resource
    private MgCustomersRepository MgCustomersDao;
    @Resource
	private EmailService emailService;
    @Resource
   	private CrmAccountRepository crmAccountDao;
    @Value("${crmUrl}")
    private String crmUrl;
    @Resource
    private EmailJobRepository emailJobDao;
    @Resource
    private ResetSessionRepository resetSessionDao;
    
    private JSONObject newCrmAccount(CrmAccount newCrmAccount,CrmAccount account) {
		JSONObject rst = generateRst();
		//判断当前是否为管理员
		if(account.getRole() == 2) {
			rst.put(YWYConsts.RC, ErrorCode.NO_UPDATE_AUTH);
			return rst;
		}
		String rep="^[0-9\\-]{1,20}$";
		if(StringUtils.isBlank(newCrmAccount.getMobile())|| !newCrmAccount.getMobile().matches(rep) ){
			rst.put(YWYConsts.RC, ErrorCode.ERROR_MOBILE);
			return rst;
		}
		Condition condition = new Condition();
		condition.setCondition("account", newCrmAccount.getAccount());
		if(StringUtils.isBlank(newCrmAccount.getEmail())){
			rst.put(YWYConsts.RC, ErrorCode.NO_EMAIL);
			return rst;
		}
		condition.getConditions().clear();
		condition.setCondition("email", newCrmAccount.getEmail());
		CrmAccount tmpAccount  = accountRepo.queryAccount(condition);
		if (tmpAccount != null) {
			rst.put(YWYConsts.RC, ErrorCode.DUPLICATE_EMAIL);
			return rst;
		}
		MgCustomers mgCustomer=new MgCustomers();
		condition.getConditions().clear();
		condition.setCondition("id", account.getCustomerId());
		mgCustomer=MgCustomersDao.query(condition);
		if(mgCustomer!=null) {
			if(mgCustomer.getLimitAcctNum()-mgCustomer.getUsedAcctNum()<1){
				rst.put(YWYConsts.RC, ErrorCode.LIMIT_ACCT_NUM);
				return rst;
			}
		}
		newCrmAccount.setAccount(newCrmAccount.getEmail());
		newCrmAccount.setRole(2);
		newCrmAccount.setCreateTime(new Date());
		if(account!=null){
			newCrmAccount.setCustomerId(account.getCustomerId());
			newCrmAccount.setCreatorId(account.getId());
		}
		Random random=new Random();
		long passw=random.nextInt(100000000);
		newCrmAccount.setPassword(DigestUtils.md5Hex(passw+""));
		rst.put("accountId", accountRepo.insert(newCrmAccount));
		if(mgCustomer!=null) {
			mgCustomer.setUsedAcctNum(mgCustomer.getUsedAcctNum()+1);
		}
		MgCustomersDao.updateMgCustomer(condition, mgCustomer,new String[]{"usedAcctNum"});
		//CRM员工账号生成成功后的发送邮箱
		//生成的密码发送邮件passw
		String emailHead=EmailTemplate.TEMPLATEHEAD;
		String emailStr=emailHead+EmailTemplate.NEW_CRMACCCOUNT_PASSWORD;
		String emailEnd=EmailTemplate.TEMPLATEEND;
		emailStr=emailStr.replaceFirst("crmUrl", crmUrl);
		emailStr=emailStr.replaceFirst("name", newCrmAccount.getName());
		emailStr=emailStr.replaceFirst("account", newCrmAccount.getEmail());
		emailStr=emailStr.replaceFirst("password", (passw+""));
		emailStr=emailStr+emailEnd;
		EmailJob emailJob=new EmailJob();
		emailJob.setSendFrom(newCrmAccount.getId());
		emailJob.setCreateTime(new Date());
		emailJob.setSendToEmail(newCrmAccount.getEmail());
		emailJob.setTitle("密码发送");
		emailJob.setContent(emailStr);
		emailJob.setSource(1);
		emailJobDao.insert(emailJob, null);
		return rst;
	}
    
    private JSONObject updateCrmAccount(CrmAccount newCrmAccount,CrmAccount account) {
		JSONObject rst = generateRst();
		//判断当前是否为管理员
		if(account.getRole() != 1) {
			rst.put(YWYConsts.RC, ErrorCode.NO_UPDATE_AUTH);
			return rst;
		}
		String rep="^[0-9\\-]{1,20}$";
		if(StringUtils.isBlank(newCrmAccount.getMobile())|| !newCrmAccount.getMobile().matches(rep) ){
			rst.put(YWYConsts.RC, ErrorCode.ERROR_MOBILE);
			return rst;
		}
		Condition condition = new Condition();
		condition.setCondition("id", newCrmAccount.getId());
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
//		if(StringUtils.isNotBlank(newCrmAccount.getPassword())){
//			accountRepo.update(condition, newCrmAccount, new String[]{"name","mobile","department",
//					"fax","job","sex","birth", "idNo","password"});
//		}else{
			accountRepo.update(condition, newCrmAccount, new String[]{"name","mobile","department",
					"fax","job","sex","birth", "idNo"});
//		}
		return rst;
	}
    
    @Override
	public JSONObject saveAccount(CrmAccount newAccount,CrmAccount account) {
		if (newAccount.getId() > 0) {
			return updateCrmAccount(newAccount,account);
		} else {
			return newCrmAccount(newAccount,account);
		}
	}

    @Override
    public JSONObject updateAccountStatusById(String status, Integer accountId,CrmAccount account) {
        JSONObject rst = generateRst();
        //判断当前是否为管理员
        if(account.getRole() != 1) {
			rst.put(YWYConsts.RC, ErrorCode.NO_UPDATE_AUTH);
			return rst;
		}
        try {
        	Condition condition = new Condition();
        	condition.setCondition("id", accountId, ConditionType.EQ);
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
    		tmpAccount.setStatus(Long.parseLong(status));
            accountRepo.update(condition, tmpAccount ,new String[]{"status"});
            return rst;
        } catch (Exception e) {
            LOG.error("update account status by id : {} errorInfo : {}", accountId, e);
            e.printStackTrace();
            rst.put(YWYConsts.RC, ErrorCode.UPDATE_FAIL);
            return rst;
        }
    }

    @Override
    public JSONObject getAccountInfoById(Integer accountId,CrmAccount account) {
        JSONObject rst = generateRst();
        //判断当前是否为管理员
        if(account.getRole() != 1) {
			rst.put(YWYConsts.RC, ErrorCode.NO_UPDATE_AUTH);
			return rst;
		}
        try {
            Condition condition = new Condition();
            condition.setCondition("id", accountId, ConditionType.EQ);
            List<CrmAccount> list = accountRepo.query(condition);
            if (list != null && list.size() > 0) {
                CrmAccount crmAccount = list.get(0);
                //判断该账号是否跟管理员为同一公司
        		if(crmAccount.getCustomerId() != account.getCustomerId()) {
        			rst.put(YWYConsts.RC, ErrorCode.NO_UPDATE_MANAGE);
        			return rst;
        		}
                JSONObject json = new JSONObject();
                json.put("accountNo", "YG"+crmAccount.getMobile());
                json.put("name", crmAccount.getName());
                json.put("account", crmAccount.getAccount());
                json.put("mobile", crmAccount.getMobile());
                json.put("department", crmAccount.getDepartment()==null?"":crmAccount.getDepartment());
                json.put("job", crmAccount.getJob()==null?"":crmAccount.getJob());
                json.put("fax", crmAccount.getFax()==null?"":crmAccount.getFax());
                json.put("email", crmAccount.getEmail()==null?"":crmAccount.getEmail());
                json.put("idNo", crmAccount.getIdNo()==null?"":crmAccount.getIdNo());
                json.put("status", crmAccount.getStatus());
                json.put("birth", crmAccount.getBirth()==null?"":crmAccount.getBirth());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (crmAccount.getCreateTime() != null)
                    json.put("createTime", format.format(crmAccount.getCreateTime()));
                rst.put("data", json);
                return rst;
            }
        } catch (Exception e) {
            LOG.error("get account info by id : {} exception error {}", accountId, e);
            e.printStackTrace();
        }
        return rst;
    }

    @Override
    public JSONObject getAccountListByNameAndPhone(Integer pageNo, String name, String phone,CrmAccount nowAccount) {

        JSONObject rst = generateRst();

        Page<CrmAccount> page = new Page<>();
        page.setPageNo(pageNo);

        StringBuffer bufferSql = new StringBuffer();
        bufferSql.append("select * from crm_account where 1=1 and role=2");
        if (name != null)
            bufferSql.append(" and name like '%").append(name).append("%'");
        if (phone != null)
            bufferSql.append(" and mobile like '%").append(phone).append("%'");
        if (nowAccount!=null)
        	bufferSql.append(" and customerId = '").append(nowAccount.getCustomerId()).append("'");
        try {
            Page<CrmAccount> accrountPage = accountRepo.query(bufferSql.toString(), page, new CustomerMapper<CrmAccount>(CrmAccount.class));

            if (accrountPage != null && accrountPage.getData() != null) {

                JSONArray jsonArray = new JSONArray();
                JSONObject object;
                for (CrmAccount crmAccount : accrountPage.getData()) {
                    object = new JSONObject();
                    object.put("accountNo", "YG-"+ crmAccount.getMobile());
                    object.put("name", crmAccount.getName());
                    object.put("account", crmAccount.getAccount());
                    object.put("mobile", crmAccount.getMobile());
                    object.put("department", crmAccount.getDepartment()==null?"":crmAccount.getDepartment());
                    object.put("job", crmAccount.getJob()==null?"":crmAccount.getJob());
                    object.put("fax", crmAccount.getFax()==null?"":crmAccount.getFax());
                    object.put("email", crmAccount.getEmail()==null?"":crmAccount.getEmail());
                    object.put("idNo", crmAccount.getIdNo()==null?"":crmAccount.getIdNo());
                    object.put("status", crmAccount.getStatus());
                    object.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(crmAccount.getCreateTime() != null? crmAccount.getCreateTime(): new Date()));
                    object.put("birth", crmAccount.getBirth()==null?"":crmAccount.getBirth());
                    object.put("accountId", crmAccount.getId());
                    jsonArray.add(object);
                }
                rst.put("dataList", jsonArray);
                int pageAll = (accrountPage.getTotalCount() - 1)/page.getPageSize() + 1;
                JSONObject pageJson = new JSONObject();
                pageJson.put("currentPage", pageNo);
                pageJson.put("totalPage", pageAll);
                pageJson.put("totalCount", accrountPage.getTotalCount());
                pageJson.put("pageSize", page.getPageSize());
                rst.put("page", pageJson);
            }
        } catch (Exception e) {
            LOG.error("getAccountListByNameAndPhone get list by name and phone error : {} exception error {}", name, e);
            e.printStackTrace();
        }
        return rst;
    }
    
    
    @Override
	public JSONObject sendEmail(String email,HttpServletRequest request,CrmAccount account){
		JSONObject rst = generateRst();
		String url="";
		if(account.getRole() != 1) {
			rst.put(YWYConsts.RC, ErrorCode.NO_UPDATE_AUTH);
			return rst;
		}
		Condition condition = new Condition();
		condition.setCondition("email", email);
		CrmAccount crmAccount=crmAccountDao.queryAccount(condition);
		if(crmAccount == null) {
			rst.put(YWYConsts.RC, ErrorCode.INVAILD_ACCOUNT);
		}else {
			//发送邮箱
			UUID token = UUID.randomUUID();
			ResetSession resetSession=new ResetSession();
			resetSession.setAccountId(crmAccount.getId());
			resetSession.setToken(token.toString());
			resetSession.setCreateTime(new Date());
			resetSessionDao.newResetSession(resetSession, null);
			EmailJob emailJob=new EmailJob();
			emailJob.setSendFrom(crmAccount.getId());
			emailJob.setCreateTime(new Date());
			emailJob.setSendToEmail(email);
			emailJob.setTitle("密码重置");
			url=crmUrl+"#/restartPassword?randomNum="+token;
			String emailHead=EmailTemplate.TEMPLATEHEAD;
			String emailStr=emailHead+EmailTemplate.RESTART_URL;
			String emailEnd=EmailTemplate.TEMPLATEEND;
			emailStr=emailStr.replaceAll("name", crmAccount.getName()!=null?crmAccount.getName():crmAccount.getAccount());
			emailStr=emailStr.replaceAll("url", url);
			emailStr=emailStr+emailEnd;
			emailJob.setContent(emailStr);
			emailJob.setSource(1);
			emailJobDao.insert(emailJob, null);
		}
		rst.put("url", url);
		return rst;
	}
	
	@Override
	public JSONObject savePassword(String randomNum,String password,HttpServletRequest request) {
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		condition.setCondition("token", randomNum);
		condition.setCondition("createTime", DateUtils.addHours(new Date(), -2), ConditionType.GT);
		ResetSession resetSession=resetSessionDao.queryResetSession(condition);
		if(resetSession!=null){
			condition.getConditions().clear();
			condition.setCondition("id", resetSession.getAccountId());
			CrmAccount crmAccount=crmAccountDao.queryAccount(condition);
			if(crmAccount == null) {
				rst.put(YWYConsts.RC, ErrorCode.NO_ACCOUNT_SESSION);	
				return rst;
			}else {
				crmAccount.setPassword(password);
				//更新密码
				crmAccountDao.updateAccount(condition, crmAccount, new String[]{"password"});
				condition.getConditions().clear();
				condition.setCondition("id", resetSession.getId());
				resetSessionDao.delResetSession(condition);
			}
		}else{
			System.out.println("未获取到session");
			rst.put(YWYConsts.RC, ErrorCode.NO_ACCOUNT_SESSION);
			return rst;	
		}
		return rst;
	}
	
	@Override
	public JSONObject updatePassword(String password,String oldPassword,CrmAccount account) {
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		condition.setCondition("id", account.getId());
		condition.setCondition("password", oldPassword);
		CrmAccount oldAccount=crmAccountDao.queryAccount(condition);
		if(oldAccount == null) {
			rst.put(YWYConsts.RC, ErrorCode.OLD_PASSWORD_ERROR);	
			return rst;
		}else {
			condition.getConditions().clear();
			condition.setCondition("id", account.getId());
			account.setPassword(password);
			//更新密码
			crmAccountDao.updateAccount(condition, account, new String[]{"password"});
		}
		return rst;
	}
}
