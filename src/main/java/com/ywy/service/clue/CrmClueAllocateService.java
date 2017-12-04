package com.ywy.service.clue;

import com.ywy.entity.sys.CrmAccount;

import net.sf.json.JSONObject;

public interface CrmClueAllocateService {
	
	JSONObject clueAllocate(long allocateNum,long assignTo,long industryId,CrmAccount account);
	
	JSONObject allocatedClue(int pageNo,CrmAccount account);
	
	JSONObject newDataCheck(CrmAccount crmAccount);
}
