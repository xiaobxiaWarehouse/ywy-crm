package com.ywy.repository.contactor;

import com.ywy.annotation.Table;
import com.ywy.entity.Page;
import com.ywy.entity.contactor.ContactorPool;
import com.ywy.exception.YWYException;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;
import com.ywy.rowmaper.CustomerMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactorPoolRepository extends BaseRepository<ContactorPool> {

    /**
     * 获取列表
     * @param contactorPool
     * @param page
     * @return
     */
    public Page<ContactorPool> query(ContactorPool contactorPool, Page<ContactorPool> page) {
        StringBuffer bufferSql = new StringBuffer();
        bufferSql.append("select * from " + super.getTableNameFromObj(contactorPool) + " where 1=1 ");
        if (contactorPool.getDomain() != null)
            bufferSql.append(" and domain = '").append(contactorPool.getDomain()).append("'");
        return super.query(bufferSql.toString(), page, new CustomerMapper<>(ContactorPool.class));
    }

    public ContactorPool queryById(Condition condition) {
        return getSingleData(super.queryObjByCondition(condition, ContactorPool.class));
    }

    public Long add(ContactorPool contactorPool, List<String> fields) {
        return super.insertObjectAndGetAutoIncreaseId(contactorPool, fields);
    }

    public void update(Condition condition, ContactorPool contactorPool, String[] fields) {
        super.updateByCondition(condition, getUpdateParam(fields, contactorPool), ContactorPool.class);
    }

    public void delete(Condition condition, ContactorPool contactorPool) {
        super.deleteByCondition(condition, ContactorPool.class);
    }

    public long insert(ContactorPool t) {
        return super.insertObjectAndGetAutoIncreaseId(t, null);
    }

    /**
     * 获取表名
     * @param cls
     * @param domain
     * @return
     */
//    protected String getClassTableByName(Class<ContactorPool> cls,String domain){
//        Table table = cls.getAnnotation(Table.class);
//        if(table!=null){
//            return table.value() + "_" + domain.substring(0, 1);
//        } else {
//            throw new YWYException(10001, "获取表名失败");
//        }
//    }
}
