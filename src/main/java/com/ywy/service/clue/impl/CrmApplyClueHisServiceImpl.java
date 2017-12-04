package com.ywy.service.clue.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ywy.entity.Page;
import com.ywy.entity.clue.CrmApplyClueHiss;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.repository.clue.CrmApplyClueHisRepository;
import com.ywy.service.clue.CrmApplyClueHisService;
import com.ywy.service.customer.CrmCustomerService;
import com.ywy.service.impl.CommonService;
import com.ywy.util.JsonValueProcessorImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service
public class CrmApplyClueHisServiceImpl extends CommonService implements CrmApplyClueHisService {

	@Resource
	private CrmApplyClueHisRepository crmApplyClueHisDao;
	
	@Resource
	private CrmCustomerService crmcustomerService;
	
	@Override
	public JSONObject queryClues(int pageNo,CrmAccount account) {
		JSONObject rst = generateRst();
		Page<CrmApplyClueHiss> mgApplyClueHiss = new Page<CrmApplyClueHiss>();
		mgApplyClueHiss.setPageNo(pageNo);
		crmApplyClueHisDao.queryClues(account.getCustomerId(), mgApplyClueHiss);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,new JsonValueProcessorImpl());
		JSONArray dataList = JSONArray.fromObject(mgApplyClueHiss.getData(), config);
		rst.put("dataList", dataList);
		rst.put("page", generatePage(mgApplyClueHiss));
		return rst;
	}

	

}
