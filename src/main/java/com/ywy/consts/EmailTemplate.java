package com.ywy.consts;

public class EmailTemplate {
	public final static String TEMPLATEHEAD="<table border=\"0\"cellspacing=\"0\" cellpadding=\"0\" style=\"font-family:\"微软雅黑\",Helvetica,Arial,sans-serif;font-size:14px\" width=\"100%\"><tbody>";
	
	public final static String NEW_CRMACCCOUNT_PASSWORD = "<tr><td style=\"font-family:Helvetica,Arial,sans-serif;font-size:14px;\">亲爱的name，您好：<br></td></tr><tr><td style=\"color:#000;margin:0;font-size:14px;line-height:24px;font-family:'微软雅黑',Helvetica,Arial,sans-serif;\"><br>你的员工账号:account已生成,详情如下：<br>密码为:password<br>登录地址： crmUrl <br>为了您的账号安全，请及时修改密码.<br></td></tr>";

	public final static String NEW_CRMINDUSTRY_MANAGE = "<tr><td style=\"font-family:Helvetica,Arial,sans-serif;font-size:14px;\">亲爱的name,您好:<br></td></tr><tr><td style=\"color:#000;margin:0;font-size:14px;line-height:24px;font-family:'微软雅黑',Helvetica,Arial,sans-serif;\"><br>time,  由companyName发起了keyWord关键字的请求，请及时处理<br></td></tr>";
	
	public final static String NEW_CRMINDUSTRY_NOMANAGE = "<tr><td style=\"font-family:Helvetica,Arial,sans-serif;font-size:14px;\">亲爱的name,您好:<br></td></tr><tr><td style=\"color:#000;margin:0;font-size:14px;line-height:24px;font-family:'微软雅黑',Helvetica,Arial,sans-serif;\"><br>time,  由companyName发起了keyWord关键字的请求，该关键字所属行业暂未有员工进行维护管理，请及时指派<br></td></tr>";
	
	public final static String NEW_CRMCLUE_ALLOCATE = "<tr><td style=\"font-family:Helvetica,Arial,sans-serif;font-size:14px;\">亲爱的name,您好:<br></td></tr><tr><td style=\"color:#000;margin:0;font-size:14px;line-height:24px;font-family:'微软雅黑',Helvetica,Arial,sans-serif;\"><br>time,  由贵公司管理员向您分配了clueNum条keyWord关键字线索数据，请及时查看并处理<br></td></tr>";

	public final static String RESTART_URL="<tr><td style=\"font-family:Helvetica,Arial,sans-serif;font-size:14px;\">亲爱的name，你好：<br></td></tr><tr><td style=\"color:#000;margin:0;font-size:14px;line-height:24px;font-family:'微软雅黑',Helvetica,Arial,sans-serif;\"><br>请在两小时内根据下面的地址修改密码:url<br></td></tr>";
	
	public final static String TEMPLATEEND="<tr><td style=\"font-family:Helvetica,Arial,sans-serif;font-size:14px;\"><br>找客户，上“一万亿”，一键找遍全球精准采购商。<br>www.OneThousandBillion.com<br> 联系邮箱：info@OneThousandBillion.com <br>手机/微信/WhatsApp: +86-18968121852</td></tr></tbody></table>";
}
