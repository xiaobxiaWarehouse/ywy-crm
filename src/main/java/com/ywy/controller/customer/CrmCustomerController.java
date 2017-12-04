package com.ywy.controller.customer;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ywy.controller.BaseController;
import com.ywy.entity.customer.CrmCustomer;
import com.ywy.service.customer.CrmCustomerService;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/customer")
public class CrmCustomerController extends BaseController {
	@Resource
	private CrmCustomerService crmcustomerService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody JSONObject saveCustomers(CrmCustomer crmCustomer){
		return wrapResult(crmcustomerService.saveCrmCustomers(crmCustomer));
	}
	
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public @ResponseBody JSONObject getCustomers(@RequestParam("transferId") Long transferId){
		return wrapResult(crmcustomerService.getCustomersDetail(transferId));
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody JSONObject deleteCustomer(long crmCustomerId) {
		return wrapResult(crmcustomerService.deleteCrmCustomer(crmCustomerId));
	}
	
}
