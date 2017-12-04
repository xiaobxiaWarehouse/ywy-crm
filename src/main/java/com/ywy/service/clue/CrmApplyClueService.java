package com.ywy.service.clue;


import com.ywy.entity.sys.CrmAccount;

import net.sf.json.JSONObject;

public interface CrmApplyClueService {
	JSONObject applyClue(int pageNo,CrmAccount account);
	
}
