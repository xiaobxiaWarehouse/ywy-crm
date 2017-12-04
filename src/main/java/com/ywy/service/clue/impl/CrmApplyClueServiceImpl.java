package com.ywy.service.clue.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ywy.entity.Page;
import com.ywy.entity.clue.CrmApplyClues;
import com.ywy.entity.industry.CrmIndustry;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.parameter.Condition;
import com.ywy.repository.clue.CrmApplyClueRepository;
import com.ywy.repository.industry.CrmIndustryRepository;
import com.ywy.service.clue.CrmApplyClueService;
import com.ywy.service.impl.CommonService;
import com.ywy.util.JsonValueProcessorImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service
public class CrmApplyClueServiceImpl extends CommonService implements CrmApplyClueService {

	@Resource
	private CrmApplyClueRepository crmApplyClueDao;
	
	@Resource
	private CrmIndustryRepository crmIndustryDao;
	
	
	@Override
	public JSONObject applyClue(int pageNo,CrmAccount account) {
		JSONObject rst = generateRst();
		Page<CrmApplyClues> mgApplyAudit = new Page<CrmApplyClues>();
		mgApplyAudit.setPageNo(pageNo);
		crmApplyClueDao.applyClue(mgApplyAudit,account.getCustomerId());
		List<CrmApplyClues> list= mgApplyAudit.getData();
		for(CrmApplyClues mac:list){
			if(!StringUtils.isBlank(mac.getPath())){
				if(mac.getPath().split("#").length>2){
					long parentIndustryId=Long.parseLong(mac.getPath().split("#")[2]);
					Condition condition=new Condition();
					condition.setCondition("id", parentIndustryId);
					CrmIndustry mid=crmIndustryDao.queryIndustry(condition);
					if(mid!=null){
						mac.setParentIndustryName(mid.getNodeName());
					}
				}
			}
		}
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,new JsonValueProcessorImpl());
		JSONArray dataList = JSONArray.fromObject(mgApplyAudit.getData(), config);
		rst.put("applyList", dataList);
		rst.put("page", generatePage(mgApplyAudit));
		return rst;
	}

	

}
