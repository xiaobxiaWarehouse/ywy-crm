package com.ywy.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: http://www.erayt.com
 * </p>
 * 
 * @author yonee
 * @version 1.0
 */

public class Maths {
	public static final double E = 1.0E-10;

	public static final int ROUND_UP = BigDecimal.ROUND_UP; // 向上舍入
	public static final int ROUND_DOWN = BigDecimal.ROUND_DOWN; // 向下舍入
	public static final int ROUND_CEILING = BigDecimal.ROUND_CEILING; // 向正无穷舍入
	public static final int ROUND_FLOOR = BigDecimal.ROUND_FLOOR; // 向负无穷舍入
	public static final int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP; // 四舍五入模式
	public static final int DEFALUT_PREC = 10; // 默认精确位数10位.

	private static DecimalFormat nf = new DecimalFormat(",##0.00");

	/**这个类不能实例化*/
	private Maths() {
	}

	/**
	 * 精确加法计算
	 * 
	 * @param d1
	 * @param d2
	 * @return double
	 */
	public static double add(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.add(bd2).doubleValue();
	}

	/**
	 * 高精度减法运算
	 * 
	 * @param d1
	 * @param d2
	 * @return double
	 */
	public static double sub(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.subtract(bd2).doubleValue();
	}

	/**
	 * 高精度乘法运算
	 * 
	 * @param d1
	 * @return double
	 */
	public static double mul(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.multiply(bd2).doubleValue();
	}

	/**
	 * 高精度除法运算(四舍五入模式，精度10位)
	 * 
	 * @param d1
	 * @param d2
	 * @return double
	 */
	public static double div(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.divide(bd2, DEFALUT_PREC, ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 高精度除法运算
	 * @param d1
	 * @param d2
	 * @param prec 精确位数
	 * @param roundMode	舍入模式
	 * @return double
	 */
	public static double div(double d1, double d2, int prec, int roundMode) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.divide(bd2, prec, roundMode).doubleValue();
	}

	/**
	 * 取精度
	 * 
	 * @param value
	 *            double
	 * @param prec
	 *            int
	 * @return double
	 */
	public static double round(double value, int prec, int roundMode) {
		if (prec < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(value));
		return b.setScale(prec, roundMode).doubleValue();
	}

	/**
	 * 判断2个double是否相等
	 * 
	 * @param d1
	 * @param d2
	 * @return boolean
	 */
	public static boolean isEqual(double d1, double d2) {
		return Math.abs(d1 - d2) < E;
	}

	/**
	 * 判断1个duoble类型的绝对值是否大于0
	 * 
	 * @param d1
	 * @return boolean
	 */
	public static boolean greatZero(double d1) {
		return Math.abs(d1) > E;
	}
	
	/**
	 * 比较2个double大小
	 * @param d1
	 * @param d2
	 * @return int
	 */
	public static int compareTo(double d1,double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.compareTo(bd2);
	}

	public static DecimalFormat getFormator(String pattern, short fraction) {
		if (nf == null) {
			nf = new DecimalFormat();
		}
		nf.applyPattern(pattern);
		nf.setMaximumFractionDigits(fraction);
		nf.setMinimumFractionDigits(fraction);
		return nf;
	}

	/**
	 * 格式化double类型的数字
	 * 
	 * @param amt
	 * @param fraction
	 * @return String
	 */
	public static String formatAmt(double amt, short fraction) {
		getFormator(",#00.00", fraction);
		return nf.format(amt);
	}

	/**
	 * 得到货币金额符号
	 * 
	 * @param cycode
	 * @return String
	 */
	public static String getCurrFlag(short cycode) {
		if (cycode == 14) {
			return "$";
		} else if (cycode == 40) {
			return "￥";
		} else {
			return "";
		}
	}
	
	/**
	 * 判断一个double数是否为整数
	 * @param d
	 * @return
	 */
	public static boolean isInteger(double d) {
		return d-(int)d == 0;
	}
}
