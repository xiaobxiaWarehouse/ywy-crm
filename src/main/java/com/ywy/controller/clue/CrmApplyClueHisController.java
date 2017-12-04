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
import com.ywy.service.clue.CrmApplyClueHisService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("clue")
public class CrmApplyClueHisController extends BaseController {
	
	@Resource
	private CrmApplyClueHisService crmApplyClueHisService;
	
	@RequestMapping(value = "/import/history/get", method = RequestMethod.POST)
	public @ResponseBody JSONObject applyClue(@RequestParam("pageNo") int pageNo,@UserInfo CrmAccount account) {
		return wrapResult(crmApplyClueHisService.queryClues(pageNo,account));
	}
	
}
