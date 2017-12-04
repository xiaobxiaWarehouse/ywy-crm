package com.ywy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Package: cn.upg.credit.common.util
 * Description: 字符帮助类
 *
 * @author : yingxiaohong
 * @create : yingxiaohong 2016-11-11-下午3:12
 * @update : yingxiaohong (2016-11-11-下午3:12)
 **/


public class StringHelper {

	static final String CHARGE_PREFIX = "W";
	
    public static  String bytesToHex(byte[] ch) {
        StringBuffer ret = new StringBuffer("");
        for (int i = 0; i < ch.length; i++)
            ret.append(byteToHex(ch[i]));
        return ret.toString();
    }

    public static String byteToHex(byte ch) {
        String str[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
        return str[ch >> 4 & 0xF] + str[ch & 0xF];
    }

    /**
     * 格式化时间
     *
     * @param time
     *            时间
     * @param format
     *            时间格式
     * @return 时间字串
     */
    public static String formatDateTime(long time, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(new Date(time));
    }

    /**
     * 格式化时间
     *
     * @param time
     *            时间
     * @param format
     *            时间格式
     * @return 时间字串
     */
    public static String formatDateTime(Date time, String format) {
        if (time == null)
            return "";
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(time);
    }
    
    /**
	 * 
	 * @param str
	 *            String
	 * @param len
	 *            int
	 * @param type
	 *            int 0:后补space；1：后补0；2：前补space；3：前补0
	 * @return String
	 */
	public static String formatString(String str, int len, int type) {
		str = str == null ? "" : str;
		StringBuffer buffer = new StringBuffer(str);
		if (str.length() > len) {
			return str.substring(str.length() - len, str.length());
		}
		for (int i = 0; i < len - str.length(); i++) {
			switch (type) {
				case 0:
					buffer.append(' ');
					break;
				case 1:
					buffer.append('0');
					break;
				case 2:
					buffer.insert(0, ' ');
					break;
				case 3:
					buffer.insert(0, '0');
					break;
			}
		}
		return buffer.toString();
	}
	
	

    public static String generateCode() {
        Calendar cal = Calendar.getInstance();
        int s = cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DAY_OF_MONTH);
        Random random = new Random();
        String merchNo = String.valueOf(s) + formatString(String.valueOf(random.nextInt(9999)), 4, 3);
        return merchNo;
    }

	public static String generateCode(String param) {
		Calendar cal = Calendar.getInstance();
		int s = cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DAY_OF_MONTH);
		Random random = new Random();
		String merchNo = String.valueOf(s) + formatString(param, 4, 3) + formatString(String.valueOf(random.nextInt(9999)), 4, 3);
		return merchNo;
	}

	public static String generateCodeWithParam(String param) {
		Calendar cal = Calendar.getInstance();
		int s = cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DAY_OF_MONTH);
		Random random = new Random();
		String merchNo = String.valueOf(s) + formatString(param, 4, 3);
		return merchNo;
	}

    public static String createSerial() {
        StringBuilder sb = new StringBuilder();
        sb.append(CHARGE_PREFIX);
        sb.append(StringHelper.formatDateTime(Calendar.getInstance().getTime(), "yyyyMMddHHmmss"));
        sb.append(formatString(String.valueOf(new Random().nextInt(9999)), 4, 3));
        return sb.toString();
    }

    public static boolean isBlank(String str) {
		return str == null || str.trim().isEmpty();
	}
    
    public static boolean isNotBlank(String str) {
		return str != null && !str.trim().isEmpty();
	}

    public static boolean isMobileNO(String mobiles){
	    Pattern p = Pattern.compile("^1[34578]\\d{9}$");
	    Matcher m = p.matcher(mobiles);
	    return m.matches();
	}

	public static boolean isEmail(String email){
		boolean flag = false;
		try{
			String check = "^[\\w.!#$%&'*+\\/=?^`{|}~-]+@\\w+(-\\w+)*\\.\\w+(-\\w+)*$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}

	public static boolean isLonger(String str ,int length){
		return str.length() > length ;
	}

	public static String getMatcherStr(String msg,String regex){
		Pattern p = Pattern.compile(regex);
		Matcher m =p.matcher(msg);
		if (m.find())
			return m.group();
		else
			return null;
	}
}
