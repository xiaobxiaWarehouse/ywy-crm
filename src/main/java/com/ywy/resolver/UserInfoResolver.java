package com.ywy.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ywy.annotation.UserInfo;
import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.entity.sys.CrmAccount;

import net.sf.json.JSONObject;
public class UserInfoResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(UserInfo.class) == null ? false : true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		 HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
         HttpSession session = request.getSession();
         Object obj=session.getAttribute(YWYConsts.USER_INFO_KEY); 
         if(obj!=null){
        	 JSONObject tmpInfo=JSONObject.fromObject(obj);
        	 CrmAccount mgAccount= (CrmAccount)JSONObject.toBean(tmpInfo,CrmAccount.class);
        	 if(mgAccount.getStatus()==2){
        		 JSONObject rst = new JSONObject();
        		 rst.put(YWYConsts.RC, ErrorCode.ACCOUNT_CLOSE);
        		 return rst;
        	 }
        	 return mgAccount;
         } 
		return null;
	}

}
