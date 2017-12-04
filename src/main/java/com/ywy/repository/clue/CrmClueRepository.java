package com.ywy.repository.clue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ywy.entity.Page;
import com.ywy.entity.clue.CrmClue;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;
import com.ywy.rowmaper.CustomerMapper;

@Repository
public class CrmClueRepository extends BaseRepository<CrmClue> {
	
	public void queryClue(Date startDate, Date endDate, Long status,Long status2,String name, Page<CrmClue> crmClue ,CrmAccount account) {
		
		StringBuilder sql = new StringBuilder("select * from " + super.getClassTable(CrmClue.class, account.getCustomerId()) + " where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		
		if (StringUtils.isNotEmpty(name)) {
			sql.append(" and name = ?");
			params.add(name);
		}
		if(account.getRole()!=1){
			sql.append(" and assignto = ?");
			params.add(account.getId());
		}
		if(status!=null){
			if(status.intValue()==1){
				sql.append(" and assignto = 0");
			}else if(status.intValue()==2){
				sql.append(" and assignto > 0");
			}
		}	
		if(startDate!=null) {
			sql.append(" and createTime >=?");
			params.add(startDate);
		}
		if(endDate!=null) {
			sql.append(" and createTime <=?");
			params.add(endDate);
		}
		if(status2!=null){
			sql.append(" and validFlag = "+status2);
		}else{
			sql.append(" and validFlag = 1");
		}
		super.query(sql.toString(), params.toArray(),crmClue, new CustomerMapper<CrmClue>(CrmClue.class));
	}
	public CrmClue query(Condition condition) {
		return getSingleData(super.queryObjByCondition(condition, CrmClue.class));
	}
	
	public List<CrmClue> queryClues(Condition condition) {
		return super.queryObjByCondition(condition, CrmClue.class);
	}
	
	public int delete(Condition condition) {
		return super.deleteByCondition(condition, CrmClue.class);
	}

	public void update(Condition condition, CrmClue crmClue, String[] fields) {
		super.updateByCondition(condition, getUpdateParam(fields, crmClue), CrmClue.class);
	}
	
	public int checkAmount(Condition condition){
		return super.countByCondition(condition, CrmClue.class);
	}
	
	public void updateLimit(Condition condition, long assignTo, String[] fields ,long limitNum) {
		CrmClue crmClue=new CrmClue();
		crmClue.setAssignTo(assignTo);
		super.updateByLimitCondition(condition, getUpdateParam(fields, crmClue), CrmClue.class, limitNum);
	}
}
