package com.ywy.service.company;

import com.ywy.entity.company.Company;
import com.ywy.entity.sys.CrmAccount;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>公司客户列表</p>
 */
public interface CompanyService {


    /**
     * <p>公司客户列表</p>
     * @param pageNo
     * @param company
     * @return
     */
    JSONObject getListByCondition(Integer pageNo, Company company, CrmAccount crmAccount);

    /**
     * <p>公司客户列表（简单）</p>
     * @param pageNo
     * @param company
     * @return
     */
    JSONObject getListByConditionSimple(Integer pageNo, Company compan, CrmAccount crmAccount);

    /**
     * 保存客户信息
     * @param company
     * @return
     */
    JSONObject save(Company company);

    /**
     * 获取客户详情
     * @param company
     * @return
     */
    JSONObject queryById(Company company);

    /**
     * 删除客户
     * @param company
     * @return
     */
    JSONObject delete(Company company);

    /**
     * 转化客户
     * @param company
     * @return
     */
    JSONObject transfer(Company company);

    /**
     * 自动匹配
     * @param company
     * @return
     */
    JSONObject autoMatch(Company company);

    /**
     * excel导入
     * @param crmAccount
     * @return
     */
    JSONObject importByExcel(MultipartFile file, CrmAccount crmAccount);

    String exportList(Integer pageNo, Company company, CrmAccount crmAccount);
}
