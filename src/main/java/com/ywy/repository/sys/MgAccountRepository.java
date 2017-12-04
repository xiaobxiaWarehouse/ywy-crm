package com.ywy.repository.sys;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ywy.entity.sys.MgAccount;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;

@Repository
public class MgAccountRepository extends BaseRepository<MgAccount>{

	
	public MgAccount queryAccount(Condition condition) {
		return getSingleData(super.queryObjByCondition(condition, MgAccount.class));
	}
	
	public Long newAccount(MgAccount account, List<String> fields) {
		return super.insertObjectAndGetAutoIncreaseId(account, fields);
	}
	
	public void updateAccount(Condition condition, MgAccount account, String[] fields) {
		super.updateByCondition(condition, getUpdateParam(fields, account), MgAccount.class);
	}
}
