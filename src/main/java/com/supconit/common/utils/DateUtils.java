package com.supconit.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @文 件 名：DateUtils.java
 * @创建日期：2012-8-23
 * @版    权：Copyrigth(c)2012
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版    本: 
 * @描    述：
 */
/**
 * @文 件 名：DateUtils.java @创建日期：2012-8-23
 * @版 权：Copyrigth(c)2012
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版 本:
 * @描 述：
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils{

	public static String[] getBegainAndEndTime(Date date, String mode) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date[] dates = getBegainAndEndDate(date, mode);

		return new String[] { format.format(dates[0]), format.format(dates[1]) };
	}

	public static Date[] getBegainAndEndDate(Date date, String mode) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date begin = new Date();
		Date end = new Date();

		if (mode == null || mode.equalsIgnoreCase("日") || mode.equalsIgnoreCase("day")) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			begin = calendar.getTime();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			end = calendar.getTime();
		} else if (mode.equalsIgnoreCase("周") || mode.equalsIgnoreCase("week")) {
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			begin = calendar.getTime();
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			end = calendar.getTime();
		} else if (mode.equalsIgnoreCase("月") || mode.equalsIgnoreCase("month") || mode.equalsIgnoreCase("0")) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			begin = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			end = calendar.getTime();
		} else if (mode.equalsIgnoreCase("季") || mode.equalsIgnoreCase("season")) {
			int month = calendar.get(Calendar.MONTH) + 1;
			if (month % 3 == 0) {// 季度结束月 (3,6,9)
				calendar.set(Calendar.MONTH, month - 3);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				begin = calendar.getTime();
				calendar.set(Calendar.MONTH, month - 1);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				end = calendar.getTime();
			} else if (month % 3 == 1) {// 季度起始月 (1,4,7)
				calendar.set(Calendar.MONTH, month - 1);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				begin = calendar.getTime();
				calendar.set(Calendar.MONTH, month + 1);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				end = calendar.getTime();
			} else if (month % 3 == 2) {// 季度中间月 (2,5,8)
				calendar.set(Calendar.MONTH, month - 2);
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				begin = calendar.getTime();
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				end = calendar.getTime();
			}
		} else if (mode.equalsIgnoreCase("半年") || mode.equalsIgnoreCase("halfyear")) {
			int month = calendar.get(Calendar.MONTH) + 1;
			if (month < 6) {
				// 上半年
				calendar.set(Calendar.MONTH, 0);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				begin = calendar.getTime();
				calendar.set(Calendar.MONTH, 4);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				end = calendar.getTime();
			} else {
				// 下半年
				calendar.set(Calendar.MONTH, 5);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				begin = calendar.getTime();
				calendar.set(Calendar.MONTH, 11);
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				end = calendar.getTime();
			}
			end = calendar.getTime();
		} else if (mode.equalsIgnoreCase("年") || mode.equalsIgnoreCase("year")) {
			calendar.set(Calendar.DAY_OF_YEAR, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			begin = calendar.getTime();
			calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			end = calendar.getTime();
		}

		return new Date[] { begin, end };
	}

	/**
	 * @方法名称:getMonthBegain
	 * @输 入:
	 * @输 出：
	 * @作 者:丁阳光
	 * @创建日期:2012-8-16
	 * @方法描述: 得到当前月份的1日
	 * @return Date
	 */
	public static Date getMonthBegain() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 
	 * 
	 * @方法名: getMonthEnd
	 * @创建日期: 2014-5-21
	 * @开发人员:高文龙
	 * @描述:得到当前日期月份的最后一天
	 */
	public static String getMonthEnd(int year, int month) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
		return format.format(calendar.getTime());
	}

	/*
	@方法名称:addHour
    @输    入:*
	@输    出：*
	@作
	者:丁阳光*
	@创建日期:2012-8-22
     *@方法描述: 日期增加或减少几小时 
     * @param oriDate
     * @param amount
     * @return Date
     */
    public static Date addHour(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(oriDate);
        cal.add(Calendar.HOUR_OF_DAY, amount);
        return cal.getTime();
    }

	/** 
     *@方法名称:addMinute
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-9-10
     *@方法描述:  日期增加或减少几分钟 
     * @param oriDate
     * @param amount
     * @return Date
     */
    public static Date addMinute(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(oriDate);
        cal.add(Calendar.MINUTE, amount);
        return cal.getTime();
    }

	/**日期增加或减少几秒钟
	 * @param oriDate
	 * @param amount
	 * @return
	 */
	public static Date addSecond(Date oriDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(oriDate);
		cal.add(Calendar.SECOND, amount);
		return cal.getTime();
	}

	/**	
	 * @方法名称:addDay
	 * @输 入:
	 * @输 出：
	 * @作者:丁阳光
	 * @创建日期:2012-6-11
     * @方法描述: 日期增加或减少几天
     * @param oriDate
     * @param amount
     * @return Date
     */
    public static Date addDay(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(oriDate);
        cal.add(Calendar.DATE, amount);
        return cal.getTime();
    }

	/**
     * @方法名称:addMonth
     * @输 入:
     * @输 出：
     * @作 者:丁阳光
     * @创建日期:2012-6-11
     * @方法描述: 日期增加或减少几月
     * @param oriDate
     * @param amount
     * @return Date
     */
    public static Date addMonth(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(oriDate);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }

	/** 
     *@方法名称:addYear
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-23
     *@方法描述:  日期增加或减少几年
     * @param oriDate
     * @param amount
     * @return Date
     */
    public static Date addYear(Date oriDate, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(oriDate);
        cal.add(Calendar.YEAR, amount);
        return cal.getTime();
    }

	/**
     * @方法名称:formatYyyyMMdd
     * @输 入:
     * @输 出：
     * @作 者:丁阳光
     * @创建日期:2012-6-11
     * @方法描述: 格式化成年月日的形式
     * @param date
     * @return String
     */
    public static String formatYyyyMMdd(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);

    }

	/**
     * @方法名称:format
     * @输 入:
     * @输 出：
     * @作 者:丁阳光
     * @创建日期:2012-6-11
     * @方法描述: 格式化成24小时制的形式
     * @param date
     * @return String
     */
    public static String format(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);

    }

	/**
     * @方法名称:format
     * @输 入:
     * @输 出：
     * @作 者:丁阳光
     * @创建日期:2012-6-11
     * @方法描述: 格式化成指定的形式
     * @param date
     * @return String
     */
    public static String format(Date date,String pattern) {
        if (date == null)
            return "";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
        
    }

	/** 
     *@方法名称:parseDate
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-16
     *@方法描述:  转换日期
     * @param date
     * @param pattern
     * @return
     * @throws ParseException Date
     */
    public static Date parseDate(String date,String pattern) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(date);
        
    }

	/** 
     *@方法名称:compare
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-16
     *@方法描述: 日期大小比较 
     * @param date1
     * @param date2
     * @return int
     */
    public static int compare(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            throw new RuntimeException("两个日期型都为null，不能比较");
        }else if(date1==null){
            return -1;
        }else if(date2==null){
            return 1;
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);
        return calendar1.compareTo(calendar2);
    }

	/**
	 * 
	 *@方法名称:compareWith
	 *@输    入:
	 *@输    出：
	 *@作    者:王瑞赞
	 *@创建日期:2013-8-5
	 *@方法描述:  两个String格式时间的比较
	 * @param stringTimeA
	 * @param stringTimeB
	 * @param str  日期格式  HH:mm:ss；yyyy-MM-dd HH:mm:ss等
	 * @return stringTimeA>stringTimeB 返回1，相等返回0，小于返回-1
	 */
	public static Integer compareWith(String stringTimeA,String stringTimeB,String str){
  		SimpleDateFormat format = new SimpleDateFormat(str);
		Date dateA =  null;
		Date dateB =  null;
		try {
			dateA = format.parse(stringTimeA);
			dateB = format.parse(stringTimeB);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateA.compareTo(dateB);
	}

	/** 
     *@方法名称:sub
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-17
     *@方法描述:  两个日期相减，返回两个时间的差值(小时为单位)，保留两位小数
     * @param date1
     * @param date2
     * @return double
     */
    public static double subHh(Date date1, Date date2){
        long mill=Math.abs(date1.getTime()-date2.getTime());
//        double ss=(mill)/(1000); //共计秒数 
//        int MM = (int)ss/60; //共计分钟数 
//        int hh=(int)ss/3600; //共计小时数 
//        int dd=(int)hh/24; //共计天数
        return (double) (Math.round((double)mill/(1000*60*60)*100)/100.0);

    }

	/** 
     *@方法名称:sub
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-17
     *@方法描述:  两个日期相减，返回两个时间的差值(小时为单位)，保留1位小数
     * @param date1
     * @param date2
     * @return double
     */
    public static double subHh1(Date date1, Date date2){
        long mill=Math.abs(date1.getTime()-date2.getTime());
//        double ss=(mill)/(1000); //共计秒数 
//        int MM = (int)ss/60; //共计分钟数 
//        int hh=(int)ss/3600; //共计小时数 
//        int dd=(int)hh/24; //共计天数
        return (double) (Math.round((double)mill/(1000*60*60)*10)/10.0);

    }

	/** 
     *@方法名称:sameDay
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-17
     *@方法描述:  是否是同一天
     * @param date1
     * @param date2
     * @return boolean
     */
    public static boolean sameDay(Date date1,Date date2){
      Date[] begainEnd1= getBegainAndEndDate(date1, "day");
      Date[] begainEnd2= getBegainAndEndDate(date2, "day");
      return compare(begainEnd1[0], begainEnd2[0])==0;
    }

	/** 
     *@方法名称:sameMonth
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-17
     *@方法描述:  是否是同一个月
     * @param date1
     * @param date2
     * @return boolean
     */
    public static boolean sameMonth(Date date1,Date date2){
        Date[] begainEnd1= getBegainAndEndDate(date1, "month");
        Date[] begainEnd2= getBegainAndEndDate(date2, "month");
        return compare(begainEnd1[0], begainEnd2[0])==0;
    }

	/** 
     *@方法名称:sameYear
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-17
     *@方法描述:  是否是同一年
     * @param date1
     * @param date2
     * @return boolean
     */
    public static boolean sameYear(Date date1,Date date2){
        Date[] begainEnd1= getBegainAndEndDate(date1, "year");
        Date[] begainEnd2= getBegainAndEndDate(date2, "year");
        return compare(begainEnd1[0], begainEnd2[0])==0;
    }

	/** 
     *@方法名称:getDay
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-23
     *@方法描述: 得到date所在的天 
     * @param date
     * @return int
     */
    public static int getDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        return cal.get(Calendar.DATE);  
    }

	/** 
     *@方法名称:getMonth
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-23
     *@方法描述: 得到date所在的月份 
     * @param date
     * @return int
     */
    public static int getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

	/** 
     *@方法名称:getYear
     *@输    入:
     *@输    出：
     *@作    者:丁阳光
     *@创建日期:2012-8-23
     *@方法描述:  得到date所在的年份
     * @param date
     * @return int
     */
    public static int getYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

	/**
     * 根据月份获取月份所在季度，如果为0，表示月份异常（不在1-12范围内）
     * @param month
     * @return
     */
    public static int getQuarter(int month){
        if(month >=1 && month <=3) return 1;
        else if(month >3 && month <7) return 2;
        else if(month >6 && month <10) return 3;
        else if (month >9 && month <13) return 4;
        else return 0;
    }

	public static String dateBetweenHumanRead(Date start,Date end){
		if(end.compareTo(start)<0){
			throw new IllegalArgumentException("终止时间必须不小于其实时间");
		}
		long diff=end.getTime()-start.getTime();
		long diffMilliseconds =diff%1000;
		long diffSeconds=diff/1000%60;
		long diffMinutes=diff/(60*1000)%60;
		long diffHours=diff/(60*60*1000)%24;
		long diffDays=diff/(24*60*60*1000);
		if(diffDays>0){
			return diffDays+"天"+diffHours+"小时"+diffMinutes+"分钟"+diffSeconds+"秒";
		}
		if(diffHours>0){
			return diffHours+"小时"+diffMinutes+"分钟"+diffSeconds+"秒";
		}
		if(diffMinutes>0){
			return diffMinutes+"分钟"+diffSeconds+"秒";
		}
		if(diffSeconds>0){
			return diffSeconds+"秒"+diffMilliseconds+"毫秒";
		}
		if(diffMilliseconds>0){
			return diff+"毫秒";
		}
		return "";
	}

	/**
	  * @throws ParseException 
	 * @方法名: isWeek
	  * @创建日期:2014-5-28
	  * @开发人员:高文龙
	  * @参数:@return
	  * @返回值:boolean 日期格式：'yyyy-MM-dd'
	  * @描述:判断是否是双休日，按照中国当地的休息日来计算
	 */
	public static boolean isWeek(String date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(format.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if(dayOfWeek==1 || dayOfWeek==7)return true;
		return false;
	}
	
	/**
	 * 毫秒格式化成:天,小时,分,秒,毫秒
	 * @author yuhuan
	 * @日期 2015/08
	 * @param ms 毫秒
	 */
	public static String milliSecondformatTime(Long ms) {
		if(null == ms){
			return "";
		}
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;
		Long day = ms / dd;
		Long hour = (ms - day * dd) / hh;
		Long minute = (ms - day * dd - hour * hh) / mi;
		Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
		StringBuffer sb = new StringBuffer();
		if (day > 0) sb.append(day + "天");
		if (hour > 0) sb.append(hour + "小时");
		if (minute > 0) sb.append(minute + "分");
		if (second > 0) sb.append(second + "秒");
		if (milliSecond > 0) sb.append(milliSecond + "毫秒");
		return sb.toString();
	}
	
	/**
	 * 计算年休假
	 * @author yuhuan
	 * @param Long ms 某年末尾和参加工作时间的差值
	 */
	public static double calculateNXJ(Long ms) {
		if(null == ms){
			return 0;
		}
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;
		Long day = ms / dd;
		double year = (double)day/365;
		double nxj = 0.0;
		if(year < 1){
			double days = year * 5;
			nxj = Math.rint(days);
			double tmp = Double.parseDouble(String.valueOf(days).split("\\.")[0]+".5");
			if(days < tmp){
				nxj = nxj + 0.5;
			}
		}else if(year >= 1 && year < 5){
			nxj = 5;
		}else if(year >= 5 && year < 10){
			nxj = 10;
		}else if(year >= 10){
			nxj = 15;
		}
		return nxj;
	}
	
	/**
	 * 
	 * 
	 * @方法名: getMonthEnd
	 * @创建日期: 2016-4-12
	 * @开发人员:王波
	 * @描述:得到当前日期月份的最后一天
	 */
	public static String getMonthBegin(int year, int month) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);// 设置为1号,当前日期既为本月第一天
		return format.format(calendar.getTime());
	}
}
