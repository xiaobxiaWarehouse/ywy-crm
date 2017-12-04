package com.ywy.service.clue;



import com.ywy.entity.sys.CrmAccount;

import net.sf.json.JSONObject;

public interface CrmClueAllocateHisService {
	
	JSONObject allocatedClueHis(int pageNo,CrmAccount account);
}
