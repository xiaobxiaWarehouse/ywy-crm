package com.ywy.service.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.NativeWebRequest;

import com.ywy.entity.sys.CrmAccount;

import net.sf.json.JSONObject;

public interface CrmAccountService {

	
	JSONObject findForLongin(String account, String password, HttpServletRequest request);

	JSONObject logOut(HttpServletRequest request);
	
	JSONObject checkLogon(NativeWebRequest webRequest);
	
	JSONObject sendEmail(String account,String mobile,HttpServletRequest request);
	
	JSONObject findPassword(String token,String password,HttpServletRequest request);

	CrmAccount findById(long id);
	
	
}
