package com.ywy.repository.clue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ywy.entity.Page;
import com.ywy.entity.clue.CrmApplyClue;
import com.ywy.entity.clue.CrmApplyClues;
import com.ywy.repository.BaseRepository;
import com.ywy.repository.Tables;
import com.ywy.rowmaper.CustomerMapper;

@Repository
public class CrmApplyClueRepository extends BaseRepository<CrmApplyClue>{
	
	/**
	 * @param mgApplyAudit
	 */
	public void applyClue(Page<CrmApplyClues> crmApplyClues,long customerId) {
		StringBuilder sql = new StringBuilder("select a.id applyId,a.rejectNote,a.createTime,b.path,b.nodeName name,a.status,c.companyName customerName,a.searchTypeCode from " + Tables.TBL_CRM_APPLY + " a LEFT JOIN " +Tables.MG_INDUSTRY +" b ON a.industryId = b.id  LEFT JOIN "+Tables.MG_CUSTOMERS + " c ON a.customerId = c.id ");
		List<Object> params = new ArrayList<Object>();
		if(customerId!= 0 ) {
			sql.append(" where a.customerId = ?");
			params.add(customerId);
		}
		sql.append(" ORDER BY a.createTime DESC");
		super.query(sql.toString(), params.toArray(),crmApplyClues, new CustomerMapper<CrmApplyClues>(CrmApplyClues.class));
	}	
	
	
	
}
