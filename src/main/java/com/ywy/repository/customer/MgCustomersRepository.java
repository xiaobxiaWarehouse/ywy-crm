package com.ywy.repository.customer;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ywy.entity.customer.MgCustomers;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;

@Repository
public class MgCustomersRepository extends BaseRepository<MgCustomers> {

	public int update(Condition condition, Map<String, Object> params) {
		return super.updateByCondition(condition, params, MgCustomers.class);
	}

	public MgCustomers query(Condition condition) {
		return getSingleData(super.queryObjByCondition(condition, MgCustomers.class));
	}

	public int delete(Condition condition) {
		return super.deleteByCondition(condition, MgCustomers.class);
	}

	public void updateMgCustomer(Condition condition, MgCustomers mgCustomer, String[] fields) {
		super.updateByCondition(condition, getUpdateParam(fields, mgCustomer), MgCustomers.class);
	}
}
