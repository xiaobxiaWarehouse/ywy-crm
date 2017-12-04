package com.ywy.controller;

import com.ywy.util.StringUtil;
import org.springframework.stereotype.Component;

import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;

import net.sf.json.JSONObject;

@Component
public class BaseController {

	protected JSONObject wrapResult(JSONObject obj) {
		try {
			if (obj.getInt(YWYConsts.RC) != YWYConsts.OK) {
				obj.put(YWYConsts.ERR_MSG,ErrorCode.errorMap.get(obj.getInt(YWYConsts.RC)) == null ? 
						"" : ErrorCode.errorMap.get(obj.getInt(YWYConsts.RC)));
				return obj;
			} 
		} catch (Exception e) {
		}
		return obj;
	}

	protected JSONObject wrapErrorResult(int errorCode) {
		JSONObject obj = new JSONObject();
		obj.put(YWYConsts.RC, errorCode);
		obj.put(YWYConsts.ERR_MSG,ErrorCode.errorMap.get(obj.getInt(YWYConsts.RC)) == null ?
				"" : ErrorCode.errorMap.get(obj.getInt(YWYConsts.RC)));
		return obj;
	}

}
