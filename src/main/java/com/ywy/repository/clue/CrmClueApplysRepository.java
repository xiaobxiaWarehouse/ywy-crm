package com.ywy.repository.clue;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ywy.entity.clue.CrmClueApplys;
import com.ywy.repository.BaseRepository;
import com.ywy.repository.Tables;
@Repository
public class CrmClueApplysRepository extends BaseRepository<CrmClueApplys>{
	 
	public List<CrmClueApplys> queryClues(long customerId){
		StringBuilder sql = new StringBuilder("select b.id , b.nodeName  from " + Tables.TBL_CRM_APPLY + " a LEFT JOIN " +Tables.MG_INDUSTRY + " b ON a.industryId = b.id");
		sql.append(" where a.customerId = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(customerId);
		return super.queryObjBySqlStr(sql.toString(), params,CrmClueApplys.class);
	}
}
