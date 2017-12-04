package com.ywy.service.clue.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.entity.Page;
import com.ywy.entity.clue.CrmClue;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.parameter.Condition;
import com.ywy.repository.clue.CrmClueRepository;
import com.ywy.service.clue.CrmClueService;
import com.ywy.service.impl.CommonService;
import com.ywy.util.DateUtil;
import com.ywy.util.JsonValueProcessorImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service
public class CrmClueServiceImpl extends CommonService implements CrmClueService {

	@Resource
	private CrmClueRepository crmClueDao;
	
	
	@Override
	public JSONObject getClueDetail(String clueId ,CrmAccount account) {
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		condition.setCondition("clueId", clueId);
		condition.setSplitValue(account.getCustomerId());
		CrmClue crmClue=crmClueDao.query(condition);
		if(crmClue==null){
			rst.put(YWYConsts.RC, ErrorCode.NO_MATCH_DATA);
			return rst;
		}
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,new JsonValueProcessorImpl());
		JSONObject data=JSONObject.fromObject(crmClue,config);
		rst.put("data", data);
		return rst;
	}


	@Override
	public JSONObject queryClue(String importStartDate,String importEndDate, Long status,Long status2, String name, int pageNo,CrmAccount account) {
		Date startDate = null;
		if(!StringUtils.isBlank(importStartDate)){
			startDate=DateUtil.dt10FromStr(importStartDate);
		}
		Date endDate = null;
		if(!StringUtils.isBlank(importEndDate)){
			Date endDate1=DateUtil.dt14FromStr(importEndDate+" 00:00:00");
			Calendar c=Calendar.getInstance();
			c.setTime(endDate1);
			c.add(Calendar.HOUR_OF_DAY, 23);
			c.add(Calendar.MINUTE, 59);
			endDate=c.getTime();
		}
		JSONObject rst = generateRst();
		Page<CrmClue> crmClue = new Page<CrmClue>();
		crmClue.setPageNo(pageNo);
		crmClueDao.queryClue(startDate,endDate,status,status2, name, crmClue, account);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,new JsonValueProcessorImpl());
		JSONArray dataList = JSONArray.fromObject(crmClue.getData(), config);
		rst.put("dataList", dataList);
		rst.put("page", generatePage(crmClue));
		return rst;
	}

}
