package com.ywy.service.contactor;

import com.ywy.entity.contactor.ContactorPool;
import net.sf.json.JSONObject;

/**
 * <p>联系人总库列表</p>
 */
public interface ContactorPoolService {


    /**
     * <p>联系人总库列表</p>
     * @param pageNo
     * @param contactorPool
     * @return
     */
    JSONObject getListByCondition(Integer pageNo, ContactorPool contactorPool);

    public JSONObject statics();

    /**
     * 保存联系人总库信息
     * @param contactorPool
     * @return
     */
    JSONObject save(ContactorPool contactorPool);

    /**
     * 获取联系人总库详情
     * @param contactorPool
     * @return
     */
    JSONObject queryById(ContactorPool contactorPool);

    /**
     * 删除联系人总库
     * @param contactorPool
     * @return
     */
    JSONObject delete(ContactorPool contactorPool);

}
