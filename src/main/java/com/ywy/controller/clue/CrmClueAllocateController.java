package com.ywy.controller.clue;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ywy.annotation.UserInfo;
import com.ywy.controller.BaseController;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.service.clue.CrmClueAllocateService;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/clue")
public class CrmClueAllocateController extends BaseController {
	@Resource
	private CrmClueAllocateService crmClueAllocateService;
	
	@RequestMapping(value = "/allocated/statics", method = RequestMethod.POST)
	public @ResponseBody JSONObject allocatedClue(@RequestParam("pageNo") int pageNo,@UserInfo CrmAccount account ) {
		return wrapResult(crmClueAllocateService.allocatedClue(pageNo,account));
	}
	@RequestMapping(value = "/allocate", method = RequestMethod.POST)
	public @ResponseBody JSONObject clueAllocate(long allocateNum,long assignTo,long industryId ,@UserInfo CrmAccount account ){
		return wrapResult(crmClueAllocateService.clueAllocate(allocateNum, assignTo, industryId,account));
	}
	@RequestMapping(value = "/newdata/check", method = RequestMethod.POST)
	public @ResponseBody JSONObject newDataCheck(@UserInfo CrmAccount crmAccount) {
		return wrapResult(crmClueAllocateService.newDataCheck(crmAccount));
	}
}
