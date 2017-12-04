package com.ywy.repository;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ywy.entity.Test;
import com.ywy.parameter.Condition;

@Repository
public class HelloRepository extends BaseRepository<Test> {
	public long insert(Test t) {
		return super.insertObjectAndGetAutoIncreaseId(t, null);
	}
	
	public int update(Condition condition, Map<String, Object> params) {
		return super.updateByCondition(condition, params, Test.class);
	}
	
	public List<Test> query(Condition condition) {
		return super.queryObjByCondition(condition, Test.class);
	}
	
	public int delete(Condition condition) {
		return super.deleteByCondition(condition, Test.class);
	}
}
