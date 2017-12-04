package com.ywy.repository.contactor;

import com.ywy.entity.Page;
import com.ywy.entity.contactor.Contactor;
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
public class ContactorRepository extends BaseRepository<Contactor> {

    /**
     * 获取列表
     * @param contactor
     * @param page
     * @return
     */
    public Page<Contactor> query(Contactor contactor, Page<Contactor> page) {
        StringBuffer bufferSql = new StringBuffer();
        bufferSql.append("select * from " + super.getClassTable(Contactor.class, contactor.getCustomerId()) + " where 1=1 ");
        if (contactor.getValidFlag() != null)
            bufferSql.append(" and validFlag = ").append(contactor.getValidFlag());
        if (contactor.getCustomerId() != null)
            bufferSql.append(" and customerId = ").append(contactor.getCustomerId());
        if (!StringUtil.isEmpty(contactor.getName()))
            bufferSql.append(" and name like '%").append(contactor.getName()).append("%'");
        if (!StringUtil.isEmpty(contactor.getMobile())) {
            bufferSql.append(" and (mobile like '%").append(contactor.getMobile()).append("%'");
            bufferSql.append(" or phone like '%").append(contactor.getMobile()).append("%')");
        }
        // 标签做特殊处理
        if (!StringUtil.isEmpty(contactor.getTagIds())) {
            String[] tagId = contactor.getTagIds().split("\\|");
            for (int i = 0; i < tagId.length; i++) {
                bufferSql.append(" and tagIds like '%|").append(tagId[i]).append("|%'");
            }
        }
        return super.query(bufferSql.toString(), page, new CustomerMapper<>(Contactor.class));
    }

    /**
     * 通过客户号和企业名称获取客户信息
     * @param contactor
     * @return
     */
    public Contactor queryByCustomerIdAndName(Contactor contactor,Page<Contactor> page) {
        StringBuilder sql = new StringBuilder("select * from " + super.getClassTable(Contactor.class, contactor.getCustomerId()) + " where 1=1 ");
        List<Object> params = new ArrayList<Object>();

        if (StringUtils.isNotEmpty(contactor.getName())) {
            sql.append(" and name = ?");
            params.add(contactor.getName());
        }
        if(contactor.getCustomerId() != null) {
            sql.append(" and customerId = ?");
            params.add(contactor.getCustomerId());
        }
        Page<Contactor> pageList = super.query(sql.toString(), params.toArray(),page, new CompanyMapper<>(Contactor.class));
        return getSingleData(pageList.getData());
    }

    public Contactor queryByUuid(Contactor contactor,Page<Contactor> page) {
        StringBuilder sql = new StringBuilder("select * from " + super.getClassTable(Contactor.class, contactor.getCustomerId()) + " where 1=1 ");
        List<Object> params = new ArrayList<>();

        if (StringUtils.isNotEmpty(contactor.getName())) {
            sql.append(" and uuid = ?");
            params.add(contactor.getUuid());
        }
        Page<Contactor> pageList = super.query(sql.toString(), params.toArray(),page, new CompanyMapper<>(Contactor.class));
        return getSingleData(pageList.getData());
    }

    /**
     * 获取最大的流水号
     * @param contactor
     * @param page
     * @return
     */
    public Contactor queryMaxSerialNumber(Contactor contactor,Page<Contactor> page) {
        StringBuilder sql = new StringBuilder("select max(serialNumber) as serialNumber from " + super.getClassTable(Contactor.class, contactor.getCustomerId()) + " where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if(contactor.getCustomerId() != null) {
            sql.append(" and customerId = ?");
            params.add(contactor.getCustomerId());
        }
        Page<Contactor> pageList = super.query(sql.toString(), params.toArray(),page, new CompanyMapper<>(Contactor.class));
        return getSingleData(pageList.getData());
    }

    public Contactor queryById(Condition condition) {
        return getSingleData(super.queryObjByCondition(condition, Contactor.class));
    }

    public Long add(Contactor contactor, List<String> fields) {
        return super.insertObjectAndGetAutoIncreaseId(contactor, fields);
    }

    public void update(Condition condition, Contactor contactor, String[] fields) {
        super.updateByCondition(condition, getUpdateParam(fields, contactor), Contactor.class);
    }

    public long insert(Contactor t) {
        return super.insertObjectAndGetAutoIncreaseId(t, null);
    }
}
