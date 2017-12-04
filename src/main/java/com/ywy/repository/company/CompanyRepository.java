package com.ywy.repository.company;

import com.ywy.entity.Page;
import com.ywy.entity.company.Company;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;
import com.ywy.rowmaper.CompanyMapper;
import com.ywy.rowmaper.CustomerMapper;
import com.ywy.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository extends BaseRepository<Company> {

    /**
     * 获取列表
     * @param company
     * @param page
     * @return
     */
    public Page<Company> query(Company company, Page<Company> page) {
        StringBuffer bufferSql = new StringBuffer();
        bufferSql.append("select * from " + super.getClassTable(Company.class, company.getCustomerId()) + " where 1=1 ");
        if (company.getStatus() != null)
            bufferSql.append(" and status = ").append(company.getStatus());
        if (company.getCustomerId() != null)
            bufferSql.append(" and customerId = ").append(company.getCustomerId());
        if (!StringUtil.isEmpty(company.getName()))
            bufferSql.append(" and name like '%").append(company.getName()).append("%'");
        if (!StringUtil.isEmpty(company.getIndustry()))
            bufferSql.append(" and industry like '%").append(company.getIndustry()).append("%'");
        if (!StringUtil.isEmpty(company.getMobile())) {
            bufferSql.append(" and (mobile like '%").append(company.getMobile()).append("%'");
            bufferSql.append(" or phone like '%").append(company.getMobile()).append("%')");
        }
        // 标签做特殊处理
        if (!StringUtil.isEmpty(company.getTagIds())) {
            String[] tagId = company.getTagIds().split("\\|");
            for (int i = 0; i < tagId.length; i++) {
                bufferSql.append(" and tagIds like '%|").append(tagId[i]).append("|%'");
            }
        }
        return super.query(bufferSql.toString(), page, new CustomerMapper<Company>(Company.class));
    }

    /**
     * 通过客户号和企业名称获取客户信息
     * @param company
     * @return
     */
    public Company queryByCustomerIdAndName(Company company,Page<Company> page) {
        StringBuilder sql = new StringBuilder("select * from " + super.getClassTable(Company.class, company.getCustomerId()) + " where 1=1 ");
        List<Object> params = new ArrayList<Object>();

        if (StringUtils.isNotEmpty(company.getName())) {
            sql.append(" and name = ?");
            params.add(company.getName());
        }
        if(company.getCustomerId() != null) {
            sql.append(" and customerId = ?");
            params.add(company.getCustomerId());
        }
        Page<Company> pageList = super.query(sql.toString(), params.toArray(),page, new CompanyMapper<>(Company.class));
        return getSingleData(pageList.getData());
    }

    public Company queryByUuid(Company company,Page<Company> page) {
        StringBuilder sql = new StringBuilder("select * from " + super.getClassTable(Company.class, company.getCustomerId()) + " where 1=1 ");
        List<Object> params = new ArrayList<>();

        if (StringUtils.isNotEmpty(company.getName())) {
            sql.append(" and uuid = ?");
            params.add(company.getUuid());
        }
        Page<Company> pageList = super.query(sql.toString(), params.toArray(),page, new CompanyMapper<>(Company.class));
        return getSingleData(pageList.getData());
    }

    /**
     * 获取最大的流水号
     * @param company
     * @param page
     * @return
     */
    public Company queryMaxSerialNumber(Company company,Page<Company> page) {
        StringBuilder sql = new StringBuilder("select max(serialNumber) as serialNumber from " + super.getClassTable(Company.class, company.getCustomerId()) + " where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if(company.getCustomerId() != null) {
            sql.append(" and customerId = ?");
            params.add(company.getCustomerId());
        }
        Page<Company> pageList = super.query(sql.toString(), params.toArray(),page, new CompanyMapper<Company>(Company.class));
        return getSingleData(pageList.getData());
    }

    public Company queryById(Condition condition) {
        return getSingleData(super.queryObjByCondition(condition, Company.class));
    }

    public Long add(Company company, List<String> fields) {
        return super.insertObjectAndGetAutoIncreaseId(company, fields);
    }

    public void update(Condition condition, Company company, String[] fields) {
        super.updateByCondition(condition, getUpdateParam(fields, company), Company.class);
    }

    public long insert(Company t) {
        return super.insertObjectAndGetAutoIncreaseId(t, null);
    }
}
