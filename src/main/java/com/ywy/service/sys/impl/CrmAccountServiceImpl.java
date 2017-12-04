package com.ywy.service.sys.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import com.ywy.consts.EmailTemplate;
import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.entity.customer.MgCustomers;
import com.ywy.entity.emailJob.EmailJob;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.entity.sys.ResetSession;
import com.ywy.enumtype.ConditionType;
import com.ywy.parameter.Condition;
import com.ywy.repository.customer.MgCustomersRepository;
import com.ywy.repository.emailJob.EmailJobRepository;
import com.ywy.repository.sys.CrmAccountRepository;
import com.ywy.repository.sys.ResetSessionRepository;
import com.ywy.service.EmailService;
import com.ywy.service.impl.CommonService;
import com.ywy.service.sys.CrmAccountService;
import com.ywy.util.DateUtil;

import net.sf.json.JSONObject;

@Service
public class CrmAccountServiceImpl extends CommonService implements CrmAccountService {

	@Resource
	private CrmAccountRepository crmAccountDao;
	@Resource
	private EmailService emailService;
	@Resource
	private MgCustomersRepository mgCustomersDao;
	@Value("${crmUrl}")
	private String crmUrl;
	@Resource
	private EmailJobRepository emailJobDao;
	@Resource
	private ResetSessionRepository resetSessionDao;
	@Override
	public JSONObject findForLongin(String account, String password, HttpServletRequest request) {
		JSONObject rst = generateRst();
		Condition condition = new Condition();
				  condition.setCondition("account", account);
				  condition.setCondition("password", password);
		CrmAccount crmAccount=crmAccountDao.queryAccount(condition);		   
		if(crmAccount!=null){
			//判断账号是否在有效期内
			condition.getConditions().clear();
			condition.setCondition("id", crmAccount.getCustomerId());
			MgCustomers mgCustomer=mgCustomersDao.query(condition);
			if(mgCustomer.getValidStartTime()!=null) {
				SimpleDateFormat dtlong14 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				try {
					Date now= dtlong14.parse(dtlong14.format(new Date()));
					long nowLong=now.getTime();
					long startTime=mgCustomer.getValidStartTime().getTime();
					if(nowLong<startTime) {
						rst.put(YWYConsts.RC, ErrorCode.ACCOUNT_OUT_TIME);
						return rst;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(mgCustomer.getValidEndTime()!=null) {
				SimpleDateFormat dtlong14 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				try {
					Date now= dtlong14.parse(dtlong14.format(new Date())) ;
					long nowLong=now.getTime();
					long endTime=mgCustomer.getValidEndTime().getTime();
					if(nowLong>endTime) {
						rst.put(YWYConsts.RC, ErrorCode.ACCOUNT_OUT_TIME);		
						return rst;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(crmAccount.getStatus()==1){
				request.getSession().setAttribute(YWYConsts.USER_INFO_KEY, crmAccount);
				rst.put("roleId", crmAccount.getRole());
			}else{
				rst.put(YWYConsts.RC, ErrorCode.ACCOUNT_CLOSE);
			}
		}else{
			rst.put(YWYConsts.RC, ErrorCode.NO_ACCOUNT);
		}
		return rst;
	}

	@Override
	public JSONObject logOut(HttpServletRequest request) {
		JSONObject rst = generateRst();
		request.getSession().setAttribute(YWYConsts.USER_INFO_KEY, null);
		return rst;
	}

	@Override
	public JSONObject sendEmail(String account,String mobile,HttpServletRequest request){
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		condition.setCondition("account", account);
		condition.setCondition("mobile", mobile);
		CrmAccount crmAccount=crmAccountDao.queryAccount(condition);
		if(crmAccount == null) {
			rst.put(YWYConsts.RC, ErrorCode.NO_ACCOUNTS);
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
			emailJob.setSendToEmail(account);
			emailJob.setTitle("密码找回");
			String url="";
			url=crmUrl+"#/resetPassword?randomNum="+token;
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
		return rst;
	}
	
	

	@Override
	public JSONObject findPassword(String token,String password,HttpServletRequest request) {
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		condition.setCondition("token", token);
		condition.setCondition("createTime", DateUtils.addHours(new Date(), -2), ConditionType.GT);
		ResetSession resetSession=resetSessionDao.queryResetSession(condition);
		if(resetSession!=null){
			condition.getConditions().clear();
			condition.setCondition("id", resetSession.getAccountId());
			CrmAccount crmAccount=crmAccountDao.queryAccount(condition);
			if(crmAccount == null) {
				System.out.println("未获取到帐号信息");
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
			rst.put(YWYConsts.RC, ErrorCode.NO_ACCOUNT_SESSION);
			return rst;	
		}
		return rst;
	}

	@Override
	public JSONObject checkLogon(NativeWebRequest webRequest) {
		JSONObject rst = generateRst();
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();
        Object obj=session.getAttribute(YWYConsts.USER_INFO_KEY); 
        if(obj==null) {
        	rst.put("result" , -1);
        }else {
        	rst.put("result" , 0);
        }
		return rst;
	}

	@Override
	public CrmAccount findById(long id) {
		Condition condition = new Condition();
        condition.setCondition("id", id);
        CrmAccount crmAccount = crmAccountDao.queryAccount(condition);
    	return crmAccount; 
	}

	
	
	
}
