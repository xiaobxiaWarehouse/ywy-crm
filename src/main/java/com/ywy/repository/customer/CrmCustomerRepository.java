package com.ywy.repository.customer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ywy.entity.customer.CrmCustomer;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;

@Repository
public class CrmCustomerRepository extends BaseRepository<CrmCustomer> {
	public long insert(CrmCustomer t, List<String> fields) {
		return super.insertObjectAndGetAutoIncreaseId(t, fields);
	}

	public int update(Condition condition, Map<String, Object> params) {
		return super.updateByCondition(condition, params, CrmCustomer.class);
	}

	public CrmCustomer query(Condition condition) {
		return getSingleData(super.queryObjByCondition(condition, CrmCustomer.class));
	}

	public int delete(Condition condition) {
		return super.deleteByCondition(condition, CrmCustomer.class);
	}

	public void updateCrmCustomer(Condition condition, CrmCustomer crmCustomer, String[] fields) {
		super.updateByCondition(condition, getUpdateParam(fields, crmCustomer), CrmCustomer.class);
	}
}
