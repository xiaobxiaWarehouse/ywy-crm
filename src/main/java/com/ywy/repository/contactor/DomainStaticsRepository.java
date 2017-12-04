package com.ywy.repository.contactor;

import com.ywy.annotation.Table;
import com.ywy.entity.contactor.ContactorPool;
import com.ywy.entity.contactor.DomainStatics;
import com.ywy.exception.YWYException;
import com.ywy.parameter.Condition;
import com.ywy.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DomainStaticsRepository extends BaseRepository<DomainStatics> {


    public DomainStatics queryById(Condition condition) {
        return getSingleData(super.queryObjByCondition(condition, DomainStatics.class));
    }

    public Long add(DomainStatics domainStatics, List<String> fields) {
        return super.insertObjectAndGetAutoIncreaseId(domainStatics, fields);
    }

    public void update(Condition condition, DomainStatics domainStatics, String[] fields) {
        super.updateByCondition(condition, getUpdateParam(fields, domainStatics), DomainStatics.class);
    }

    public void delete(Condition condition, DomainStatics domainStatics) {
        super.deleteByCondition(condition, DomainStatics.class);
    }

    public long insert(DomainStatics t) {
        return super.insertObjectAndGetAutoIncreaseId(t, null);
    }

    /**
     * 获取表名
     * @param cls
     * @return
     */
    protected String getClassTableByName(Class<ContactorPool> cls){
        Table table = cls.getAnnotation(Table.class);
        if(table!=null){
            return table.value();
        } else {
            throw new YWYException(10001, "获取表名失败");
        }
    }
}
