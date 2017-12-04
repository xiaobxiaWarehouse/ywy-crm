package com.ywy.repository.clue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ywy.entity.Page;
import com.ywy.entity.clue.CrmClueAllocateHis;
import com.ywy.entity.clue.CrmClueAllocateHiss;
import com.ywy.repository.BaseRepository;
import com.ywy.repository.Tables;
import com.ywy.rowmaper.CustomerMapper;

@Repository
public class CrmClueAllocateHisRepository extends BaseRepository<CrmClueAllocateHis> {


	
	public void allocatedClueHis(Page<CrmClueAllocateHiss> crmClueAllocateHiss,long customerId) {
		StringBuilder sql = new StringBuilder("select a.createTime,b.name,a.clueNum,mi.nodeName industryName from " + Tables.TBL_CRM_ALLOCATE_HIS + " a LEFT JOIN " +Tables.TBL_CRM_ACCOUNT +" b ON a.assignTo = b.id  LEFT JOIN "+Tables.MG_INDUSTRY+" mi ON a.industryId = mi.id ");
		List<Object> params = new ArrayList<Object>();
		if (customerId!=0) {
			sql.append(" where a.customerId = ?");
			params.add(customerId);
		}
		sql.append(" ORDER BY a.createTime DESC");
		super.query(sql.toString(), params.toArray(),crmClueAllocateHiss, new CustomerMapper<CrmClueAllocateHiss>(CrmClueAllocateHiss.class));
	}	
	
	public long insert(CrmClueAllocateHis t) {
        return super.insertObjectAndGetAutoIncreaseId(t, null);
    }
	
}
