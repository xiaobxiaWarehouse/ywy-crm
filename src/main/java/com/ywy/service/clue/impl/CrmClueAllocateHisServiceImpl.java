package com.ywy.service.clue.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ywy.annotation.UserInfo;
import com.ywy.entity.Page;
import com.ywy.entity.clue.CrmClueAllocateHiss;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.repository.clue.CrmClueAllocateHisRepository;
import com.ywy.service.clue.CrmClueAllocateHisService;
import com.ywy.service.impl.CommonService;
import com.ywy.util.JsonValueProcessorImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service
public class CrmClueAllocateHisServiceImpl extends CommonService implements CrmClueAllocateHisService {

	@Resource
	private CrmClueAllocateHisRepository crmClueAllocateHisDao;

	@Override
	public JSONObject allocatedClueHis(int pageNo ,@UserInfo CrmAccount account) {
		JSONObject rst = generateRst();
		Page<CrmClueAllocateHiss> crmClueAllocateHis = new Page<CrmClueAllocateHiss>();
		crmClueAllocateHis.setPageNo(pageNo);
		crmClueAllocateHisDao.allocatedClueHis(crmClueAllocateHis,account.getCustomerId());
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,new JsonValueProcessorImpl());
		JSONArray dataList = JSONArray.fromObject(crmClueAllocateHis.getData(), config);
		rst.put("dataList", dataList);
		rst.put("page", generatePage(crmClueAllocateHis));
		return rst;
	}


}
