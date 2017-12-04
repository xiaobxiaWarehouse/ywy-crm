package com.ywy.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.*;

public class StrUtil {
	/**
	 * 根据指定间隔符组合 如：ab,ab
	 * 
	 * @param list
	 *            String[]
	 * @param regex
	 *            String
	 * @return String
	 */
	public static String combine(String[] list, String regex) {
		String result = "";
		if (list.length > 0)
			result = list[0];
		for (int i = 1; i < list.length; i++)
			result = result + regex + list[i];
		return result;
	}

	/**
	 * 记录异常信息
	 * 
	 * @param throwExp
	 *            Throwable
	 * @return String
	 */
	public static String trace(Throwable throwExp) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		throwExp.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

	/** *****************RMB FIX********************** */
	/**
	 * 返回格式数字
	 * 
	 * @param value
	 *            Object
	 * @return String
	 */
	public static String formatNumber(Object value) {
		return formatNumber(value, "0.00");
	}

	public static String formatNumber(Object value, String format) {
		String fm1 = "{0,NUMBER," + format + "}";
		return MessageFormat.format(fm1, new Object[] { value });
	}

	/**
	 * 字符串编码从UNICODE转换为GBK
	 * 
	 * @param strvalue
	 *            UNICODE编码的字符串
	 * @return GBK编码的字符串
	 */
	public static String toCharSet(String strvalue, String charset1,
			String charset2) {
		try {
			if (strvalue == null) {
				return null;
			} else {
				strvalue = new String(strvalue.getBytes(charset1), charset2);// ISO8859_1,GBK
				return strvalue;
			}
		} catch (Exception e) {
			System.out.println("系统异常[" + new Date().toString() + "]");
			e.printStackTrace();
			return null;
		}
	}

	/** *****************RMB FIX********************** */

	public static String stringOfchar(char ch, int len) {
		StringBuffer buffer = new StringBuffer(0);
		for (int i = 0; i < len; i++) {
			buffer.append(ch);
		}
		return buffer.toString();
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
	
	/**
	 * 
	 * @param bytes
	 * @param len
	 * @param type
	 * @return String
	 */
	public static String formatBytes(byte bytes[], int len, int type) {
		byte cbytes[] = new byte[len];
		if (bytes.length > len) {
			for (int i=0; i < len; i++)
				cbytes[i] = bytes[i];
		} else {
			for (int i=0; i < bytes.length; i++)
				cbytes[i] = bytes[i];
			for (int i=bytes.length; i < len; i++)
				cbytes[i] = ' ';
		}
		return new String(cbytes);
	}

	/**
	 * 返回金额,2位小数
	 * @param val
	 * @return double
	 */
	public static double getAmountFromStr(String val) {
		if (val.length() > 0)
			return Double.parseDouble(val) / 100; // 2位小数
		else
			return 0;
	}

	/**
	 * 
	 * @param amount
	 * @return String
	 */
	public static String getStrFromAmount(double amount) {
		String amt = String.valueOf(Math.round(amount * 100.0));
		int index = amt.indexOf(".");
		if (index == -1)
			return amt;
		else
			return amt.substring(0, index);
	}

	/**
	 * 
	 * @param rate
	 * @return String
	 */
	public static String getStrFromRate(double rate) {
		String tmp = String.valueOf(rate);
		int index = tmp.indexOf(".");
		return formatString(tmp.substring(0, index), 6, 3) + "."
				+ formatString(tmp.substring(index + 1), 6, 1);
	}
	
	/**
	 * 判断输入参数时间是否在指定的区间内
	 * time格式: 0900,如果超出4位自动被截断
	 * range格式:0800-1600
	 * @param time
	 * @param range
	 * @return boolean
	 */
	public static boolean isInRange(int time, String range){
		int len = String.valueOf(time).length();
		time = Integer.valueOf(String.valueOf(time).substring(0, len > 4 ? 4 : len));
		String[] start_end = range.split("-");
		if (start_end.length != 2)
			return false;
		return time >= Integer.parseInt(start_end[0]) && time <= Integer.parseInt(start_end[1]);
	}
	
	public static String getString(byte[] array, int fidx) {
		return getString(array, fidx, array.length);
	}

	public static String getString(byte[] array, int fidx, int tidx) {
		byte[] data = new byte[tidx - fidx];
		System.arraycopy(array, fidx, data, 0, tidx - fidx);
		return new String(data);
	}
	
	public static String fomatSqlInParam(String vals) {
		return fomatSqlInParam(vals.split(","));
	}
	
	public static String fomatSqlInParam(String[] vals) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < vals.length; i++) {
			buf.append("'");
			buf.append(vals[i]);
			buf.append("',");
		}
		return buf.length() > 0 ? buf.substring(0, buf.length() - 1) : "''";
	}
	
	public static String fomatSqlInParam(Set<String> vals) {
		StringBuffer buf = new StringBuffer();
		for (String val : vals) {
			buf.append("'");
			buf.append(val);
			buf.append("',");
		}
		return buf.length() > 0 ? buf.substring(0, buf.length() - 1) : "''";
	}
	
	public static Map convertFromArray(String[] s){
		Map m = new TreeMap();
		for(int i =0; i < s.length; i++){
			if(!s[i].equals(""))
				m.put(i, s[i]);
		}
		return m;
	}
	
	public static boolean isNum(String str){
		if(str == null || str.trim().length() == 0)
			return false;
		char[] numbers = str.toCharArray();
		for(int i = 0;i<numbers.length;i++){
			if(!Character.isDigit(numbers[i])){
				return false;
			}
		}
		return true;
	}
	/**
	 * 分割字符串，返回结果中包括分隔符
	 * @param string
	 * @param regSplit
	 * @return 
	 * @author panhf
	 * @since  2009-11-17 下午02:04:08
	 */
	public static String[] splitString(String string, String regSplit) {
		List<String> result = new ArrayList<String>();
		for (String item: string.split(regSplit)) {
			result.add(item);
			if (string.indexOf(item)+item.length() < string.length()) {
				result.add(string.substring(string.indexOf(item)+item.length(),
						string.indexOf(item)+item.length()+1));
			}
		}
		return result.toArray(new String[]{});
	}
	
	/**
	 * 全角转半角的方法
	 * @param QJstr 输入的字符串
	 * @return 转换成半角的字符串
	 */
	public static final String SBCchange(String QJstr) {
		if (QJstr == null || QJstr.trim().length() == 0)
			return QJstr;
		String outStr = "";
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < QJstr.length(); i++) {
			try {
				Tstr = QJstr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (b[3] == -1) {
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}
		return outStr;
	}
	
	/**  
     * 说明：如果需要截取的长度大于字符串实际可以被截取的长度，则按实际可截取长度进行截取，   
     *      此时不用考虑汉字问题。如果要截取的长度在实际可截取长度的许可范围内，则需要考虑   
     *      汉字的问题。具体做法是，先把字符串转换成字符数组，然后对每个字符进行判断，如果   
     *      字符所占用的长度是“2”的整数倍，则认为这个字属于非英文字符。   
     * @param str  待处理字符串   
     * @param index  处理的首位置   
     * @param length  长度   
     * @return   
     * @throws Exception
	 */
	public static String subString(String str,int index,int length){    
        try{    
            String returnStr=null;    
            byte[] bytes=str.getBytes();    
            int len=bytes.length;    
            boolean lenFlow=((len-index)>length)?(true):(false);    
            if(lenFlow){    
                String tmpStr;    
                StringBuffer tmpBfStr=new StringBuffer();    
                int curlen=0;    
                int j=0;    
                while(j<length){    
                    char mychar=str.charAt(j);    
                    tmpStr=String.valueOf(mychar);    
                    if(tmpStr.getBytes().length%2==0){    
                        j+=2;    
                        if(j>length){    
                            break;    
                        }    
                    }else{    
                        j++;    
                    }    
                    tmpBfStr.append(tmpStr);    
                }    
                returnStr=tmpBfStr.toString();    
            }else{    
                returnStr=str.substring(index,str.length());    
            }    
            return returnStr;    
        } catch(Exception e){    
            e.printStackTrace();
            return "";
        }    
    }
	
	/**
	 * 
	 * @param str
	 * @param pecStr
	 * @return
	 *	2011-10-19
	 *	Administrator
	 *	TODO 获取特定字符串个数
	 */
	public static int getStrNum(String str,char pecStr){
		int num=0;
		char[] strArr=str.toCharArray();
		for(char c:strArr){
			if(c == pecStr){
				num++;
			}
		}
		return num;
	}
}
