package com.ywy.service.clue;


import com.ywy.entity.sys.CrmAccount;

import net.sf.json.JSONObject;

public interface CrmClueService {
	
	JSONObject getClueDetail(String clueId,CrmAccount account);
	
	JSONObject queryClue(String importStartDate,String importEndDate, Long assignto,Long status2,String name, int pageNo,CrmAccount account);
}
