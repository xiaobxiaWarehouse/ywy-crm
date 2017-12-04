package com.ywy.controller.sys;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.NativeWebRequest;

import com.ywy.annotation.NoAuth;
import com.ywy.annotation.UserInfo;
import com.ywy.controller.BaseController;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.service.sys.CrmAccountService;

import net.sf.json.JSONObject;

@RequestMapping("/auth")
@Controller
@SessionAttributes
public class LoginController extends BaseController{
	@Resource
	private CrmAccountService crmAccountService;
	
	@RequestMapping(value="/logon/byPwd", method = RequestMethod.POST)
	@NoAuth
	public @ResponseBody JSONObject logon(@RequestParam("account") String account,@RequestParam("password") String password,HttpServletRequest request) {
		return wrapResult(crmAccountService.findForLongin(account, password,request));
	}
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public @ResponseBody JSONObject loginOut(HttpServletRequest request,@UserInfo CrmAccount crmAccount) {
		return wrapResult(crmAccountService.logOut(request));
	} 
	@RequestMapping(value="/sendEmail", method = RequestMethod.POST)
	@NoAuth
	public @ResponseBody JSONObject sendEmail(@RequestParam("account")String account,@RequestParam("mobile")String mobile,HttpServletRequest request) {
		return wrapResult(crmAccountService.sendEmail(account,mobile,request));
	} 
	@RequestMapping(value="/resetPassword", method = RequestMethod.POST)
	@NoAuth
	public @ResponseBody JSONObject findPassword(@RequestParam("randomNum")String randomNum,@RequestParam("password")String password,HttpServletRequest request) {
		return wrapResult(crmAccountService.findPassword(randomNum, password,request));
	}
	
	@NoAuth
	@RequestMapping(value="check/logon", method = RequestMethod.POST)
	public @ResponseBody JSONObject checkLogon(NativeWebRequest webRequest) {
		return wrapResult(crmAccountService.checkLogon(webRequest));
	} 
	
}
