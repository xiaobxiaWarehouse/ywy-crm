package com.ywy.repository.industry;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ywy.entity.industry.CrmIndustryTree;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;

@Repository
public class CrmIndustryTreeRepository extends BaseRepository<CrmIndustryTree>{
	
	public List<CrmIndustryTree> queryIndustrysTree(Condition condition){
		return super.queryObjByCondition(condition, CrmIndustryTree.class);
	}
	
}