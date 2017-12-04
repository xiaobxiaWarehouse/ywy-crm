package com.ywy.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private final static String BASE = "abcdefghijklmnopqrstuvwxyz0123456789abcdefghijklmnopqrstuvwxyz0123456789abcdefghijklmnop"
            + "qrstuvwxyz0123456789abcdefghijklmnopqrstuvwxyz0123456789";

    private final static String BASE_NUM = "012345678901234567890123456789012345678901234567890123456789";

    public static String getRandomString(int length) { // length表示生成字符串的长度
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE.length());
            sb.append(BASE.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomNumString(int length) { // length表示生成字符串的长度
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE_NUM.length());
            sb.append(BASE_NUM.charAt(number));
        }
        return sb.toString();
    }

    public static String getShareCode(String pre) {
        return pre + UUID.randomUUID().toString().replaceAll("-", "") + StringUtil.getRandomNumString(6);
    }

    public static String getTakeOrderNo() {
        return DateUtil.dt14FromDate(new Date()) + getRandomNumString(8);
    }

    public static String processNullStr(String str) {
        return str == null ? "" : str;
    }

    public static Boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }

    public static Boolean isNotBlank(String value) {
        return value != null && !"".equals(value.trim());
    }

    public static Boolean isBlank(String value) {
        return value == null || "".equals(value.trim());
    }

    public static boolean isUrl(String url) {
        if(StringUtil.isEmpty(url)) return false;
        String regex = "^[A-Za-z0-9_-]{1,63}[.]{1}[A-Za-z0-9\u4e00-\u9fa5_-]{1,10}$|[A-Za-z0-9\u4e00-\u9fa5_-]{1,63}.{1}[A-Za-z\u4e00-\u9fa5]{1,10}+.{1}[A-Za-z\u4e00-\u9fa5]{1,10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        System.out.println(matcher.matches());
        return matcher.matches() && UrlPatternMap.map.containsKey(url.substring(url.indexOf(".")));
    }

    public static Long getPoolSplitNumber(String input) {
        if(StringUtil.isEmpty(input)) return 0l;
        String reg = "[a-zA-Z]";
        StringBuffer strBuf = new StringBuffer();
        input = input.substring(0, 1).toLowerCase();
        for (char c : input.toCharArray()) {
            if (String.valueOf(c).matches(reg)) {
                strBuf.append(c - 96);
            } else {
                strBuf.append(c);
            }
        }
        return Long.valueOf(strBuf.toString());
    }

}
