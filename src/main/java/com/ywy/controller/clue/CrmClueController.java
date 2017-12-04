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
import com.ywy.service.clue.CrmClueService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/clue")
public class CrmClueController extends BaseController {
	@Resource
	private CrmClueService crmClueService;

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public @ResponseBody JSONObject getClueDetail(@RequestParam("clueId") String clueId, @UserInfo CrmAccount account) {
		return wrapResult(crmClueService.getClueDetail(clueId, account));
	}

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public @ResponseBody JSONObject queryClue(@RequestParam("pageNo") int pageNo, String importStartDate,
			String importEndDate, Long status, String name,Long status2, @UserInfo CrmAccount account) {
		return wrapResult(crmClueService.queryClue(importStartDate, importEndDate, status,status2, name, pageNo, account));
	}

}
