package com.ywy.consts;

import java.util.HashMap;
import java.util.Map;

public class ErrorCode {
	public final static int FORCE_LOGOUT = -2003;
	public final static int INVAILD_ACCOUNT = -2004;
	public final static int NO_MATCH_DATA = -1000;
	public final static int INVALID_PARAM = -1001;
	public final static int DUPLICATE_ACCOUNT = 4000001;
	public final static int DUPLICATE_EMAIL = 4000002;
	public final static int DUPLICATE_ALLOCATE = 4000003;
	public final static int DUPLICATE_NUM = 4000004;
	public final static int LIMIT_ACCT_NUM = 4000005;
	public final static int NO_UPDATE_AUTH = 400005;

	public final static int NO_UPDATE_MANAGE = 400006;

	public final static int NO_ACCOUNT = 100001;
	public final static int ACCOUNT_CLOSE = 100002;
	public final static int ACCOUNT_OUT_TIME = 100012;
	public final static int NO_CRM_ACCOUNT=100003;
	public final static int NO_EMAIL = 100004;
	public final static int UPDATE_FAIL = 100005;
	public final static int NO_ACCOUNTS = 100006;
	public final static int NO_ACCOUNT_SESSION = 100007;
	public final static int ERROR_MOBILE = 100008;
	public final static int OLD_PASSWORD_ERROR = 100009;
	public final static int DUPLICATE_COMPANYNAME = 200001;
	public final static int DUPLICATE_INDUSTRY = 300001;
	public final static int DUPLICATE_CRMACCOUNT = 200002;
	public final static int DUPLICATE_APPLY = 200003;
	public final static int NO_AUDIT = 200004;
	public final static int AUDIT = 200005;
	public final static int DUPLICATE_AUDIT = 200006;
	public final static int NO_SEARCHCODE = 200007;
	public final static int DUPLICATE_COMPANY = 112701;
	public final static int NO_COMPANY = 112702;
	public final static int MATCH_NO_FIND = 113201;
	public final static int CUSTOMER_MATACED = 113202;
	public final static int NO_CLUE_ID = 113201;
	public final static int PARAM_ERROR = 113202;
	public final static int DOMAIN_ERROR = 113203;
	public final static int OPERATOR_ERROR = 9999;
	public final static int IMPORT_FORMAT_ERROR = 9999;
	public static Map<Integer,String> errorMap = new HashMap<Integer, String>();
	static {
		errorMap.put(NO_MATCH_DATA, "无匹配数据");
		errorMap.put(NO_CRM_ACCOUNT, "无匹配员工数据");
		errorMap.put(DUPLICATE_ACCOUNT, "账号已存在");
		errorMap.put(NO_EMAIL, "无邮箱信息");
		errorMap.put(ERROR_MOBILE, "无效电话");
		errorMap.put(OLD_PASSWORD_ERROR, "旧密码错误");
		errorMap.put(UPDATE_FAIL, "修改失败");
		errorMap.put(DUPLICATE_ALLOCATE, "分配数量不足");
		errorMap.put(DUPLICATE_NUM, "分配数量需大于0");
		errorMap.put(DUPLICATE_EMAIL, "邮箱已存在");
		errorMap.put(NO_ACCOUNT_SESSION, "未找到申请找回密码帐号或已失效.请重新申请邮箱找回或联系客服人员");
		errorMap.put(NO_ACCOUNT, "错误密码或账号不存在");
		errorMap.put(NO_ACCOUNTS, "账号不存在或手机号不匹配");
		errorMap.put(ACCOUNT_CLOSE, "帐号已锁定");
		errorMap.put(ACCOUNT_OUT_TIME, "帐号使用时间不在有效期内");
		errorMap.put(LIMIT_ACCT_NUM, "可创建员工帐号数量不足");
		errorMap.put(FORCE_LOGOUT, "未登入");
		errorMap.put(DUPLICATE_COMPANYNAME, "公司名重复");
		errorMap.put(DUPLICATE_INDUSTRY, "行业名或叶节点重复");
		errorMap.put(DUPLICATE_CRMACCOUNT, "帐号已存在");
		errorMap.put(DUPLICATE_APPLY, "重复申请");
		errorMap.put(NO_AUDIT, "您的获客请求尚未审核通过，请耐心等待");
		errorMap.put(AUDIT, "您的获客请求已审核通过，线索数据正在准备，请耐心等待");
		errorMap.put(DUPLICATE_AUDIT, "您的获客请求已生效，请勿重复提交.");
		errorMap.put(NO_SEARCHCODE, "未获取到搜索类型信息");
		errorMap.put(NO_UPDATE_AUTH, "操作权限不足");
		errorMap.put(NO_UPDATE_MANAGE, "只能修改自己公司下的员工信息");
		errorMap.put(NO_CLUE_ID, "线索ID不存在");
		errorMap.put(PARAM_ERROR, "参数错误");
		errorMap.put(OPERATOR_ERROR, "操作失败");
		errorMap.put(YWYConsts.FAIL, "操作失败");
		errorMap.put(DOMAIN_ERROR, "域名填写有误，请填写正确的域名信息。");
	}
}
