package com.ywy.service.customer.impl;


import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.entity.customer.CrmCustomer;
import com.ywy.parameter.Condition;
import com.ywy.repository.customer.CrmCustomerRepository;
import com.ywy.service.customer.CrmCustomerService;
import com.ywy.service.impl.CommonService;

import net.sf.json.JSONObject;

@Service
public class CrmCustomerServiceImpl extends CommonService implements CrmCustomerService {

	@Resource
	private CrmCustomerRepository crmcustomerDao;
	
	private JSONObject updateCrmCustomers(CrmCustomer crmCustomer) {
		JSONObject rst = generateRst();
		String rep="^[0-9\\-]{1,20}$";
		if(StringUtils.isBlank(crmCustomer.getPhone())|| !crmCustomer.getPhone().matches(rep) ){
			rst.put(YWYConsts.RC, ErrorCode.ERROR_MOBILE);
			return rst;
		}
		Condition condition = new Condition();
		condition.setCondition("name", crmCustomer.getName());
		CrmCustomer CrmCustomers = crmcustomerDao.query(condition);
		if (CrmCustomers != null && CrmCustomers.getId() != crmCustomer.getId()) {
			rst.put(YWYConsts.RC, ErrorCode.DUPLICATE_COMPANYNAME);
			return rst;
		}
		
		condition.getConditions().clear();
		condition.setCondition("id", crmCustomer.getId());
		crmcustomerDao.updateCrmCustomer(condition, crmCustomer,
				new String[] { "name", "domain", "industry", "email", "phone",
						"fax", "address", "socialAccts", "note", "contactor", "tag" });
		return rst;
	}

	
	@Override
	public JSONObject saveCrmCustomers(CrmCustomer crmCustomer) {
//		if (crmCustomer.getId() > 0) {
//			return updateCrmCustomers(mgCustomer);
//		} else {
//			return newCrmCustomers(mgCustomer);
//		}
		return updateCrmCustomers(crmCustomer);
	}
	
	
	@Override
	public JSONObject getCustomersDetail(Long crmCustomerId) {
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		condition.setCondition("id", crmCustomerId);
		CrmCustomer crmCustomer=crmcustomerDao.query(condition);
		if(crmCustomer==null){
			rst.put(YWYConsts.RC, ErrorCode.NO_MATCH_DATA);
			return rst;
		}
		rst.put("data", crmCustomer);
		return rst;
	}


	@Override
	public JSONObject deleteCrmCustomer(long crmCustomerId) {
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		condition.setCondition("id", crmCustomerId);
		crmcustomerDao.delete(condition);
		return rst;
	}
	
	@Override
	public CrmCustomer getCustomersById(Long crmCustomerId) {
		Condition condition = new Condition();
		condition.setCondition("id", crmCustomerId);
		CrmCustomer crmCustomer=crmcustomerDao.query(condition);
		return crmCustomer;
	}
	
}
