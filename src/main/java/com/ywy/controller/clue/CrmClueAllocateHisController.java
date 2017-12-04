package com.ywy.controller.clue;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ywy.annotation.UserInfo;
import com.ywy.controller.BaseController;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.service.clue.CrmClueAllocateHisService;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/clue")
public class CrmClueAllocateHisController extends BaseController {
	@Resource
	private CrmClueAllocateHisService crmClueAllocateHisService;
	
	@RequestMapping(value = "/allocated/his", method = RequestMethod.POST)
	public @ResponseBody JSONObject allocatedClue(int pageNo,@UserInfo CrmAccount account ) {
		return wrapResult(crmClueAllocateHisService.allocatedClueHis(pageNo,account ));
	}
}
