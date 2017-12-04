package com.ywy.repository.clue;


import org.springframework.stereotype.Repository;

import com.ywy.entity.clue.CrmClueApply;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;
@Repository
public class CrmClueApplyRepository extends BaseRepository<CrmClueApply>{
	public CrmClueApply query(Condition condition) {
		return getSingleData(super.queryObjByCondition(condition, CrmClueApply.class));
	}
	
	public long insert(CrmClueApply t) {
	        return super.insertObjectAndGetAutoIncreaseId(t, null);
	}
	 
}
