package com.ywy.service;

import com.ywy.entity.Test;

import net.sf.json.JSONObject;

public interface HelloService {
	JSONObject insert(Test t);
	
	JSONObject query(String name);
	
	JSONObject update(long id, String name);
}
