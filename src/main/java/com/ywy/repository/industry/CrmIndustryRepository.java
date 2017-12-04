package com.ywy.repository.industry;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ywy.entity.industry.CrmIndustry;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;

@Repository
public class CrmIndustryRepository extends BaseRepository<CrmIndustry>{

	public CrmIndustry queryIndustry(Condition condition) {
		return getSingleData(super.queryObjByCondition(condition, CrmIndustry.class));
	}
	
	
	public Long newIndustry(CrmIndustry industry, List<String> fields) {
		return super.insertObjectAndGetAutoIncreaseId(industry, fields);
	}
	
	public void updateIndustry(Condition condition, CrmIndustry industry, String[] fields) {
		super.updateByCondition(condition, getUpdateParam(fields, industry), CrmIndustry.class);
	}


	public long insert(CrmIndustry t) {
		return super.insertObjectAndGetAutoIncreaseId(t, null);
	}
	
}
