package com.ywy.repository.clue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ywy.entity.Page;
import com.ywy.entity.clue.CrmClueAllocate;
import com.ywy.entity.clue.CrmClueAllocates;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;
import com.ywy.repository.Tables;
import com.ywy.rowmaper.CustomerMapper;

@Repository
public class CrmClueAllocateRepository extends BaseRepository<CrmClueAllocate> {


	public CrmClueAllocate query(Condition condition) {
		return getSingleData(super.queryObjByCondition(condition, CrmClueAllocate.class));
	}
	
	public void newClueAllocate(long allocatenum,long industryid, Page<CrmClueAllocate> crmClueAllocate) {
		StringBuilder sql = new StringBuilder("select a.allocatenum,a.industryid,b.clueNum from " + Tables.TBL_CRM_SUBSCRIBE + " a LEFT JOIN " +Tables.TBL_CRM_ACCOUNT +" b ON a.allocateNum = b.clueNum");
		List<Object> params = new ArrayList<Object>();
		super.query(sql.toString(), params.toArray(),crmClueAllocate, new CustomerMapper<CrmClueAllocate>(CrmClueAllocate.class));
//		super.insertObjectAndGetAutoIncreaseId(clue, fields);
	}

	public void updateCrmCustomer(Condition condition, CrmClueAllocate mgCustomer, String[] fields) {
		super.updateByCondition(condition, getUpdateParam(fields, mgCustomer), CrmClueAllocate.class);
	}
	
	public void allocatedClue(Page<CrmClueAllocates> crmClueAllocates) {
		StringBuilder sql = new StringBuilder("select b.name,b.contactPhone,b.email,b.department,b.job,b.clueNum from " + Tables.TBL_CRM_ALLOCATE + " a LEFT JOIN " +Tables.TBL_CRM_ACCOUNT +" b ON a.assignTo = b.id");
		List<Object> params = new ArrayList<Object>();
		super.query(sql.toString(), params.toArray(),crmClueAllocates, new CustomerMapper<CrmClueAllocates>(CrmClueAllocates.class));
	}	
	
	public List<CrmClueAllocate> querys(Condition condition) {
		return super.queryObjByCondition(condition, CrmClueAllocate.class);
	}
	
}
