package com.ywy.repository.clue;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ywy.entity.clue.CrmAllocateClueHis;
import com.ywy.repository.BaseRepository;
@Repository
public class CrmAllocatHisRepository extends BaseRepository<CrmAllocateClueHis>{
	 
	public List<CrmAllocateClueHis> queryClues(long customerId){
		StringBuilder sql = new StringBuilder("SELECT a.name industryName,a.industryId,sum(a.assignTo > 0) allocateNum,COUNT(a.industryId) totalNum from "+ super.getClassTable(CrmAllocateClueHis.class, customerId) +" a GROUP BY industryId");
		List<Object> params = new ArrayList<Object>();
		return super.queryObjBySqlStr(sql.toString(), params,CrmAllocateClueHis	.class);
	} 
}
