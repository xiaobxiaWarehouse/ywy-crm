package com.ywy.repository.company;

import com.ywy.annotation.Table;
import com.ywy.entity.Page;
import com.ywy.entity.company.CompanyContactorMatch;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;
import com.ywy.rowmaper.CustomerMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyContactorMatchRepository extends BaseRepository<CompanyContactorMatch> {


    public Long add(CompanyContactorMatch companyContactorMatch, List<String> fields) {
        return super.insertObjectAndGetAutoIncreaseId(companyContactorMatch, fields);
    }

    public void update(Condition condition, CompanyContactorMatch companyContactorMatch, String[] fields) {
        super.updateByCondition(condition, getUpdateParam(fields, companyContactorMatch), CompanyContactorMatch.class);
    }

    public void delete(Condition condition) {
        super.deleteByCondition(condition, CompanyContactorMatch.class);
    }

    public long insert(CompanyContactorMatch t) {
        return super.insertObjectAndGetAutoIncreaseId(t, null);
    }

    public CompanyContactorMatch queryById(Condition condition) {
        return getSingleData(super.queryObjByCondition(condition, CompanyContactorMatch.class));
    }

    public Page<CompanyContactorMatch> query(CompanyContactorMatch companyContactorMatch, Page<CompanyContactorMatch> page) {
        StringBuffer bufferSql = new StringBuffer();
        Table table = CompanyContactorMatch.class.getAnnotation(Table.class);
        bufferSql.append("select * from " + table.value() + " where 1=1 ");
        if (companyContactorMatch.getCustomerId() != null)
            bufferSql.append(" and customerId = ").append(companyContactorMatch.getCustomerId()).append("");
        if (companyContactorMatch.getCompanyId() != null)
            bufferSql.append(" and companyId = ").append(companyContactorMatch.getCompanyId()).append("");
        if (companyContactorMatch.getDomain() != null)
            bufferSql.append(" and domain = '").append(companyContactorMatch.getDomain()).append("'");
        if (companyContactorMatch.getMatchStatus() != null)
            bufferSql.append(" and matchStatus = ").append(companyContactorMatch.getMatchStatus()).append("");
        return super.query(bufferSql.toString(), page, new CustomerMapper<>(CompanyContactorMatch.class));
    }
}
