package com.ywy.repository.sys;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ywy.entity.sys.ResetSession;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;

@Repository
public class ResetSessionRepository extends BaseRepository<ResetSession>{

	
	public ResetSession queryResetSession(Condition condition) {
		return getSingleData(super.queryObjByCondition(condition, ResetSession.class));
	}
	
	public Long newResetSession(ResetSession resetSession, List<String> fields) {
		return super.insertObjectAndGetAutoIncreaseId(resetSession, fields);
	}
	
	public void delResetSession(Condition condition) {
		super.deleteByCondition(condition, ResetSession.class);
	}
}
