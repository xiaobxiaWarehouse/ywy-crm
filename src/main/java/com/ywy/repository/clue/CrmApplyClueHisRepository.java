package com.ywy.repository.clue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ywy.entity.Page;
import com.ywy.entity.clue.CrmApplyClueHis;
import com.ywy.entity.clue.CrmApplyClueHiss;
import com.ywy.repository.BaseRepository;
import com.ywy.repository.Tables;
import com.ywy.rowmaper.CustomerMapper;

@Repository
public class CrmApplyClueHisRepository extends BaseRepository<CrmApplyClueHis>{

	public void queryClues(long customerId,Page<CrmApplyClueHiss> mgApplyClueHiss) {
		StringBuilder sql = new StringBuilder("select a.createTime,a.syncNum,b.nodeName clueName from " + Tables.TBL_MG_APPLYHIS + " a LEFT JOIN "  +Tables.MG_INDUSTRY +" b ON a.industryId = b.id  where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (customerId!=0) {
			sql.append(" and a.customerId = ?");
			params.add(customerId);
		}
		sql.append(" ORDER BY a.createTime DESC");
		super.query(sql.toString(), params.toArray(), mgApplyClueHiss, new CustomerMapper<CrmApplyClueHiss>(CrmApplyClueHiss.class));
	}
	
	
	
}
