package com.ywy.service.customer;

import com.ywy.entity.customer.CrmCustomer;

import net.sf.json.JSONObject;

public interface CrmCustomerService {
	

	JSONObject saveCrmCustomers(CrmCustomer crmCustomer);

	JSONObject getCustomersDetail(Long id);
	
	JSONObject deleteCrmCustomer(long crmCustomerId);

	CrmCustomer getCustomersById(Long crmCustomerId);
}
