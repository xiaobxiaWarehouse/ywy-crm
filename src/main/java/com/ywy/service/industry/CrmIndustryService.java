package com.ywy.service.industry;

import com.ywy.entity.industry.CrmIndustry;

import net.sf.json.JSONObject;

public interface CrmIndustryService {
	JSONObject saveCrmIndustry(CrmIndustry industry);
	
	JSONObject getIdustryList();

	
}
