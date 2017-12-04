package com.ywy.repository.emailJob;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ywy.entity.emailJob.EmailJob;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;

@Repository
public class EmailJobRepository extends BaseRepository<EmailJob> {
	public long insert(EmailJob t, List<String> fields) {
		return super.insertObjectAndGetAutoIncreaseId(t, fields);
	}

	public int update(Condition condition, Map<String, Object> params) {
		return super.updateByCondition(condition, params, EmailJob.class);
	}

	public EmailJob query(Condition condition) {
		return getSingleData(super.queryObjByCondition(condition, EmailJob.class));
	}

	public int delete(Condition condition) {
		return super.deleteByCondition(condition, EmailJob.class);
	}

	public void updateEmailJob(Condition condition, EmailJob emailJob, String[] fields) {
		super.updateByCondition(condition, getUpdateParam(fields, emailJob), EmailJob.class);
	}
}
