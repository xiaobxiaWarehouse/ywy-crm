package com.ywy.service.clue;



import com.ywy.entity.sys.CrmAccount;

import net.sf.json.JSONObject;

public interface CrmApplyClueHisService {
	
	JSONObject queryClues(int pageNo,CrmAccount account);
	
}
