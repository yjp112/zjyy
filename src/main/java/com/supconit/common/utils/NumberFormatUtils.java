package com.supconit.common.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

public class NumberFormatUtils {
	
	/**千分位以","隔开，并四舍五入保留2为小数
	 * @param number
	 * @return
	 */
	public static String formatThousand2(String number){
		return format(number, "#,##0.##");
	}
	public static Double formatThousand2(Double number){
		return formatDouble(number, "#,##0.##");
	}
	public static String formatDoubleThousand2(Double number){
		return formatDouble2Str(number, "#,##0.##");
	}
	public static Double formatDouble(Double number,String pattern){
		if(number==null){
			return 0D;
		}
		DecimalFormat df = new DecimalFormat(pattern); 
		try {
			df.setRoundingMode(RoundingMode.HALF_UP);
			return  Double.valueOf(df.format(number));
		} catch (Exception e) {
			e.printStackTrace();
			return number;
		}
	}
	public static String formatDouble2Str(Double number,String pattern){
		if(number==null){
			return "";
		}
		DecimalFormat df = new DecimalFormat(pattern); 
		try {
			df.setRoundingMode(RoundingMode.HALF_UP);
			return  df.format(number);
		} catch (Exception e) {
			e.printStackTrace();
			return number.toString();
		}
	}
	public static String format(String number,String pattern){
		if(StringUtils.isBlank(number)){
			return number;
		}
		DecimalFormat df = new DecimalFormat(pattern); 
		try {
			double d=Double.parseDouble(number);
			df.setRoundingMode(RoundingMode.HALF_UP);
			return df.format(d);
		} catch (Exception e) {
			e.printStackTrace();
			return number;
		}
	}
	
	public static void main(String[] args) {
		String num="11234567.0345";
		System.out.println(NumberFormatUtils.formatThousand2(num));
		System.out.println(NumberFormatUtils.formatThousand2(".1"));
		double d=100.1;
		System.out.println(NumberFormatUtils.formatDoubleThousand2(d));
		
	}
}
