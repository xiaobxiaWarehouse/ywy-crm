package com.ywy.repository.company;

import com.ywy.entity.Page;
import com.ywy.entity.company.Company;
import com.ywy.entity.company.CompanyContactor;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;
import com.ywy.rowmaper.CustomerMapper;
import com.ywy.util.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyContactorRepository extends BaseRepository<CompanyContactor> {


    public Long add(CompanyContactor companyContactor, List<String> fields) {
        return super.insertObjectAndGetAutoIncreaseId(companyContactor, fields);
    }

    public void update(Condition condition, CompanyContactor companyContactor, String[] fields) {
        super.updateByCondition(condition, getUpdateParam(fields, companyContactor), CompanyContactor.class);
    }

    public void delete(Condition condition) {
        super.deleteByCondition(condition, CompanyContactor.class);
    }

    public long insert(CompanyContactor t) {
        return super.insertObjectAndGetAutoIncreaseId(t, null);
    }

    public CompanyContactor queryById(Condition condition) {
        return getSingleData(super.queryObjByCondition(condition, CompanyContactor.class));
    }

    public Page<CompanyContactor> query(CompanyContactor companyContactor, Page<CompanyContactor> page) {
        StringBuffer bufferSql = new StringBuffer();
        bufferSql.append("select * from " + super.getClassTable(CompanyContactor.class, companyContactor.getCustomerId()) + " where 1=1 ");
        if (companyContactor.getCustomerId() != null)
            bufferSql.append(" and customerId = ").append(companyContactor.getCustomerId()).append("");
        if (companyContactor.getCompanyId() != null)
            bufferSql.append(" and companyId = ").append(companyContactor.getCompanyId()).append("");
        if (companyContactor.getContactorId() != null)
            bufferSql.append(" and contactorId = ").append(companyContactor.getContactorId()).append("");
        return super.query(bufferSql.toString(), page, new CustomerMapper<CompanyContactor>(CompanyContactor.class));
    }
}
