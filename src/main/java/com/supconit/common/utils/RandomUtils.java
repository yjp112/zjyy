package com.supconit.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class RandomUtils {
    /** 
     *@方法名称:getIntRandom
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-22
     *@方法描述:  生成[min,max)之间的整数
     * @param min
     * @param max
     * @return int
     */
    public static int getIntRandom(int min,int max){   
        return new Random().nextInt(max)%(max-min+1) + min;   
    }
    /** 
     *@方法名称:getDoubleRandom
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-22
     *@方法描述:  生成[min,max)之间的小数
     * @param min
     * @param max
     * @return double
     */
    public static double getDoubleRandom(double min,double max){   
        return  new Random().nextDouble()*(max-min)+min;
    }
    public static double getDoubleRandom(double min,double max,int scale){   
        return  Double.parseDouble(format(new Random().nextDouble()*(max-min)+min,scale));
    }
    
    public static String  format(double numer,int scale){
        NumberFormat format=NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(scale);
        return format.format(numer);
    }
    
    public static String format(String number) {
    	if(StringUtils.isEmpty(number)){
    		return number;
    	}
    	try {
    		BigDecimal b = new BigDecimal(number); 
//    	b.toPlainString();
    		DecimalFormat df = new DecimalFormat("#0");
    		return df.format(b.doubleValue());
		} catch (Exception e) {
			return number;
		}
	}
    public static String format1(String number) {
    	if(StringUtils.isEmpty(number)){
    		return number;
    	}
    	try {
    		BigDecimal b = new BigDecimal(number); 
//    	b.toPlainString();
    		DecimalFormat df = new DecimalFormat("#0.0");
    		return df.format(b.doubleValue());
    	} catch (Exception e) {
    		return number;
    	}
    }
    public static String format2(String number) {
    	if(StringUtils.isEmpty(number)){
    		return number;
    	}
    	try {
    		BigDecimal b = new BigDecimal(number); 
//    	b.toPlainString();
    		DecimalFormat df = new DecimalFormat("#0.00");
    		return df.format(b.doubleValue());
    	} catch (Exception e) {
    		return number;
    	}
    }
    public static String format3(String number) {
    	if(StringUtils.isEmpty(number)){
    		return number;
    	}
    	try {
    		BigDecimal b = new BigDecimal(number); 
//    	b.toPlainString();
    		DecimalFormat df = new DecimalFormat("#0.000");
    		return df.format(b.doubleValue());
    	} catch (Exception e) {
    		return number;
    	}
    }
}
