package com.ywy.interceptor;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ywy.annotation.NoAuth;
import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.service.sys.CrmAccountService;

import net.sf.json.JSONObject;

@Component
public class YwyCrmInteceptor  extends HandlerInterceptorAdapter {
	@Resource
	private CrmAccountService crmAccountService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			if (((HandlerMethod) handler).getMethodAnnotation(NoAuth.class) == null) {
				Object userInfo = request.getSession().getAttribute(YWYConsts.USER_INFO_KEY);
				if (userInfo == null) {
					outputError(response, ErrorCode.FORCE_LOGOUT);
					return false;
				}else{
					JSONObject tmpInfo=JSONObject.fromObject(userInfo);
		        	CrmAccount mgAccount= (CrmAccount)JSONObject.toBean(tmpInfo,CrmAccount.class);
		        	if(mgAccount.getId()!=0){
		        		CrmAccount nowAccount=crmAccountService.findById(mgAccount.getId());
		        		 if(nowAccount!=null && nowAccount.getStatus()==2){
		        			 request.getSession().setAttribute(YWYConsts.USER_INFO_KEY, null);
		            		 outputError(response, ErrorCode.INVAILD_ACCOUNT);
		            		 return false;
		        		 }
		        	 }
				}
				request.setAttribute(YWYConsts.USER_INFO_KEY, userInfo.toString());
			}
		}
		return true;
	}
		
	private void outputError(HttpServletResponse response, int rst) {
		JSONObject obj = new JSONObject();
		obj.put(YWYConsts.RC, rst);
		response.setContentType("application/json");
		try {
			response.getOutputStream().write(obj.toString().getBytes());
		} catch (IOException e) {
		}
	}
}
