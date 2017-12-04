package com.ywy.repository.sys;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ywy.entity.Page;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.entity.sys.CrmAccountForCule;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;
import com.ywy.repository.Tables;
import com.ywy.rowmaper.CustomerMapper;

@Repository
public class CrmAccountRepository extends BaseRepository<CrmAccount>{

	
	public CrmAccount queryAccount(Condition condition) {
		return getSingleData(super.queryObjByCondition(condition, CrmAccount.class));
	}
	
	public void updateAccount(Condition condition, CrmAccount crmAccount, String[] fields) {
		super.updateByCondition(condition, getUpdateParam(fields, crmAccount), CrmAccount.class);
	}
	
	public void queryAccounts(Page<CrmAccountForCule> accounts,long customerId ,long role) {
		StringBuilder sql = new StringBuilder("select * from " + Tables.TBL_CRM_ACCOUNT + " where 1=1 and status = 1");
		List<Object> params = new ArrayList<Object>();
		if(customerId!=0){
			sql.append(" and customerId = ?");
			params.add(customerId);
		}
		if(role!=0){
			sql.append(" and role = ?");
			params.add(role);
		}
		super.query(sql.toString(), params.toArray(), accounts, new CustomerMapper<CrmAccountForCule>(CrmAccountForCule.class));
	}
}
