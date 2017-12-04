package com.ywy.service.qiniu;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public interface QiniuService {
	JSONObject getQiniuToken(String bucket, HttpServletRequest request);

	JSONObject qiniuUploadCallBack(HttpServletRequest request);
}
