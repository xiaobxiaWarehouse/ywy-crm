package com.ywy.service.sys;


import javax.servlet.http.HttpServletRequest;

import com.ywy.entity.sys.CrmAccount;

import net.sf.json.JSONObject;


public interface AccountNumberService {


    /**
     * <p>账号禁用，开启</p>
     * @param status
     * @param accountId
     * @return
     */
    JSONObject updateAccountStatusById(String status, Integer accountId,CrmAccount account);

    /**
     * <p>账号详情查看</p>
     * @param accountId
     * @return
     */
    JSONObject getAccountInfoById(Integer accountId,CrmAccount account);

    /**
     * <p>根据名称手机号获取员工列表</p>
     * @param pageNo
     * @param name
     * @param phone
     * @return
     */
    JSONObject getAccountListByNameAndPhone(Integer pageNo, String name, String phone,CrmAccount nowAccount);
    /**
     * 
    * @Title: saveAccount
    * @Description: TODO(维护用户信息)
    * @param @param account
    * @param @param nowAccount
    * @param @return    设定文件
    * @return JSONObject    返回类型
    * @throws
     */
	JSONObject saveAccount(CrmAccount account, CrmAccount nowAccount);

	JSONObject sendEmail(String email, HttpServletRequest request, CrmAccount account);

	JSONObject savePassword(String randomNum, String password, HttpServletRequest request);

	JSONObject updatePassword(String password,String oldPassword,CrmAccount account);


}
