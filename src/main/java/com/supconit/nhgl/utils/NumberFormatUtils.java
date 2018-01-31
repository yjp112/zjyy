package com.supconit.nhgl.utils;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

public class NumberFormatUtils {
	
	/**千分位以","隔开，并四舍五入保留2为小数
	 * @param number
	 * @return
	 */
	public static String formatThousand2(String number){
		return format(number, "#,##0.00");
	}
	
	public static String format(String number,String pattern){
		if(StringUtils.isBlank(number)){
			return number;
		}
		DecimalFormat df = new DecimalFormat(pattern); 
		try {
			double d=Double.parseDouble(number);
			return df.format(d);
		} catch (Exception e) {
			e.printStackTrace();
			return number;
		}
	}
	
	public static void main(String[] args) {
		String num="11234567.7150";
		System.out.println(NumberFormatUtils.formatThousand2(num));
		System.out.println(NumberFormatUtils.formatThousand2(".1"));
		System.out.println(NumberFormatUtils.format(num,"#,###"));
	}
}
