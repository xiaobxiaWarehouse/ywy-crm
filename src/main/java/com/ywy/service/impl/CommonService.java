package com.ywy.service.impl;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ywy.consts.YWYConsts;
import com.ywy.entity.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class CommonService {
	public JSONObject generateRst() {
		JSONObject rst = new JSONObject();
		rst.put(YWYConsts.RC, YWYConsts.OK);
		return rst;
	}
	
//	public JSONObject generatePage(int currentPage, int totalCount, int totalPage) {
//		JSONObject rst = new JSONObject();
//		rst.put("currentPage", currentPage);
//		rst.put("totalCount", totalCount);
//		rst.put("totalPage", totalPage);
//		return rst;
//	}
	public JSONObject generatePage(Page<?> page) {
		JSONObject rst = new JSONObject();
		rst.put("currentPage", page.getPageNo());
		rst.put("totalCount", page.getTotalCount());
		rst.put("totalPage", page.getTotalPage());
		rst.put("pageSize", page.getPageSize());
		return rst;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject processNull(Object o) {
		JSONObject rst = JSONObject.fromObject(o);
		Iterator<String> keys = rst.keys();
		String key = null;
		while (keys.hasNext()) {
			key = keys.next();
			if (rst.getString(key).equals("null")) {
				rst.put(key, "");
			}
		}
		return rst;
	}

	public JSONArray processNull(List<?> list) {
		JSONArray array = new JSONArray();
		for (Object o : list) {
			array.add(processNull(o));
		}
		return array;
	}

	public JSONObject generateErrorRst() {
		JSONObject rst = new JSONObject();
		rst.put(YWYConsts.RC, YWYConsts.FAIL);
		return rst;
	}
}
