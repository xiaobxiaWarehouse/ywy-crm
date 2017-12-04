package com.ywy.repository.sys;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ywy.entity.sys.CrmAccount;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;
@Repository
public class AccountNumberRepository extends BaseRepository<CrmAccount>{

    public long insert(CrmAccount t) {
        return super.insertObjectAndGetAutoIncreaseId(t, null);
    }

    public int update(Condition condition, CrmAccount account,String[] fields) {
        return super.updateByCondition(condition, getUpdateParam(fields, account), CrmAccount.class);
    }
    
    public CrmAccount queryAccount(Condition condition) {
		return getSingleData(super.queryObjByCondition(condition, CrmAccount.class));
	}

    public List<CrmAccount> query(Condition condition) {
        return super.queryObjByCondition(condition, CrmAccount.class);
    }

    public int delete(Condition condition) {
        return super.deleteByCondition(condition, CrmAccount.class);
    }

}
