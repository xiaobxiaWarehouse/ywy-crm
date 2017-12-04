package com.ywy.controller.industry;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ywy.controller.BaseController;
import com.ywy.service.industry.CrmIndustryService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("industry")
public class CrmIndustryController extends BaseController {
	
	@Resource
	private CrmIndustryService crmIndustryService;
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public @ResponseBody JSONObject getIndustry(){
		return wrapResult(crmIndustryService.getIdustryList());
	}
	
}
