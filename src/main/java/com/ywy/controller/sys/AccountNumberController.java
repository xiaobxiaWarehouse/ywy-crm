package com.ywy.controller.sys;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ywy.annotation.NoAuth;
import com.ywy.annotation.UserInfo;
import com.ywy.consts.ErrorCode;
import com.ywy.controller.BaseController;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.service.sys.AccountNumberService;
import com.ywy.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * <p>account 员工控制层</p>
 * @date 2017-09-15
 * @author qj
 */
@Controller
@RequestMapping(value = "/account")
@SessionAttributes
public class AccountNumberController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountNumberService.class);

    @Autowired
    private AccountNumberService accountNumberService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public  @ResponseBody JSONObject saveAccountInfo(HttpServletRequest request,CrmAccount crmAccount,@UserInfo CrmAccount account) {
        JSONObject jsonObject = null;
        try {
            jsonObject = accountNumberService.saveAccount(crmAccount,account);
        } catch (Exception e) {
            LOG.error("save account info error : [] and request line: []", ErrorCode.NO_MATCH_DATA, request.getContextPath());
            e.printStackTrace();
        }
        return wrapResult(jsonObject);
    }

    @RequestMapping(value = "/status/update", method = RequestMethod.POST)
    public  @ResponseBody JSONObject setAccountStatusById(HttpServletRequest request,@RequestParam(value = "accountId", required = true) Integer accountId, @RequestParam(value = "status", required = true) String status,@UserInfo CrmAccount account) {

        if (StringUtil.isEmpty(status)|| accountId == null) {
            LOG.error("request parameter is null {} AccountNumberController save setAccountStatusById error : {}", request.getContextPath(), ErrorCode.NO_MATCH_DATA);
            throw new RuntimeException("参数不能为空");
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = accountNumberService.updateAccountStatusById(status, accountId,account);
        } catch (Exception e) {
            LOG.error("update account status error : [] and request line: []", ErrorCode.NO_MATCH_DATA, request.getContextPath());
            e.printStackTrace();
        }
        return wrapResult(jsonObject);
    }

    @RequestMapping(value = "/detail/get", method = RequestMethod.POST)
    public  @ResponseBody JSONObject getAccountInfoById(HttpServletRequest request,@RequestParam("accountId") Integer accountId,@UserInfo CrmAccount account) {

        if (accountId == null) {
            LOG.error("request parameter is null {} AccountNumberController save setAccountStatusById error : {}", request.getContextPath(), ErrorCode.NO_MATCH_DATA);
            throw new RuntimeException("参数不能为空");
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = accountNumberService.getAccountInfoById(accountId,account);
        } catch (Exception e) {
            LOG.error("get account info error : [] and request line: []", ErrorCode.NO_MATCH_DATA, request.getContextPath());
            e.printStackTrace();
        }
        return wrapResult(jsonObject);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public  @ResponseBody JSONObject getAccountListByNameAndPhone(HttpServletRequest request,@RequestParam("pageNo") Integer pageNo, String name, String mobile,@UserInfo CrmAccount account) {

        if (pageNo == null) {
            LOG.error("request parameter is null {} AccountNumberController get getAccountListByNameAndPhone error : {}", request.getContextPath(), ErrorCode.NO_MATCH_DATA);
            throw new RuntimeException("参数不能为空");
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = accountNumberService.getAccountListByNameAndPhone(pageNo, name, mobile,account);
        } catch (Exception e) {
            LOG.error("get account list error : [] and request line: []", ErrorCode.NO_MATCH_DATA, request.getContextPath());
            e.printStackTrace();
        }
        return wrapResult(jsonObject);
    }
    
    @RequestMapping(value="/restartUrl")
	public @ResponseBody JSONObject restartUrl(String email,HttpServletRequest request,@UserInfo CrmAccount account){
		return wrapResult(accountNumberService.sendEmail(email, request, account));
	}
	
	@RequestMapping(value="/restartPassword")
	@NoAuth
	public @ResponseBody JSONObject restartPassword(String randomNum,@RequestParam("password")String password,HttpServletRequest request){
		return wrapResult(accountNumberService.savePassword(randomNum, password, request));
	}
	
	@RequestMapping(value="/updatePassword")
	public @ResponseBody JSONObject updatePassword(@RequestParam("password")String password,@RequestParam("oldPassword")String oldPasswrod,@UserInfo CrmAccount account){
		return wrapResult(accountNumberService.updatePassword(password,oldPasswrod,account));
	}

}
