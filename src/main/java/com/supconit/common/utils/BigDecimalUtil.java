package com.supconit.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {
	/**
	 * @param dividend 被除数
	 * @param divisor 除数
	 * @return 商,四舍五入保留2位小数
	 */
	public static BigDecimal divide2(BigDecimal dividend,BigDecimal divisor){
		return dividend.divide(divisor,2,RoundingMode.HALF_UP);
	}
	/**
	 * @param dividend 被除数
	 * @param divisor 除数
	 * @return 商,四舍五入保留4位小数
	 */
	public static BigDecimal divide4(BigDecimal dividend,BigDecimal divisor){
		return dividend.divide(divisor,4,RoundingMode.HALF_UP);
	}
	
	public static void main(String[] args) {
		BigDecimal dividend=new BigDecimal(1);
		BigDecimal divisor=new BigDecimal(3);
		BigDecimal result=BigDecimalUtil.divide2(dividend,divisor);
		System.out.println(result.doubleValue());
	}
}
