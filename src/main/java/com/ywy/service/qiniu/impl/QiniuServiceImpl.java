package com.ywy.service.qiniu.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ywy.repository.qiniu.QiniuRepository;
import com.ywy.service.impl.CommonService;
import com.ywy.service.qiniu.QiniuService;

import net.sf.json.JSONObject;
import com.qiniu.util.Auth;

@Service
public class QiniuServiceImpl extends CommonService implements QiniuService{
	
	@Resource
	private QiniuRepository qiniuDao;
	
	@Override
	public JSONObject getQiniuToken(String bucket, HttpServletRequest request) {
		JSONObject rst = generateRst();
		String accessKey = "CKceCE8PjAhs3W9EBUgBJlW714Pn0DAbO-9O91sX";
        String secretKey = "QzSvVM_-8WVfk0-9-AD0fBSR3Jig3tRj_HjDw0FZ";
        
        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
        String upToken = auth.uploadToken(bucket, null, 7200, null, true);
        rst.put("token", upToken);
		return rst;
		
	}

	@Override
	public JSONObject qiniuUploadCallBack(HttpServletRequest request) {
		return null;
	}
}
