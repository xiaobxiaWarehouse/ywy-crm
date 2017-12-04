package com.ywy.service.contactor;

import com.ywy.entity.contactor.Contactor;
import com.ywy.entity.sys.CrmAccount;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * com.ywy.service.contactor
 * Created by yaoyan on 17/11/5.
 * 17/11/5 上午11:54
 */
public interface ContactorService {

    /**
     * <p>公司客户列表</p>
     * @param pageNo
     * @param contactor
     * @return
     */
    JSONObject getListByCondition(Integer pageNo, Contactor contactor, CrmAccount crmAccount);

    /**
     * <p>公司客户列表（简单）</p>
     * @param pageNo
     * @param contactor
     * @return
     */
    JSONObject getListByConditionSimple(Integer pageNo, Contactor contactor, CrmAccount crmAccount);

    /**
     * 保存客户信息
     * @param contactor
     * @return
     */
    JSONObject save(Contactor contactor);

    /**
     * 获取客户详情
     * @param contactor
     * @return
     */
    JSONObject queryById(Contactor contactor);

    /**
     * 删除客户
     * @param contactor
     * @return
     */
    JSONObject delete(Contactor contactor);

    /**
     * excel导入
     * @param crmAccount
     * @return
     */
    JSONObject importByExcel(MultipartFile file, CrmAccount crmAccount);

    String exportList(Integer pageNo, Contactor contactor, CrmAccount crmAccount);
}
