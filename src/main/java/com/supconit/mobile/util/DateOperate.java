package com.supconit.mobile.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式转换功能块
 * Created by wangwei on 15/5/20.
 */

public class DateOperate {

    private static String DATE_CALENDAR;

    private static boolean DATEBOOLEAN;//是否差值24小时

    /**
     * 根据传入rpDate转换成指定格式的日期字符串
     * @param date 传入long型日期
     * @param isDate 需要转换日、月、年格式
     * @param change 需要前几天、几月、几年
     * @return
     */
    public static String DateChange(Date date,String isDate,int change,String dateFormat)
    {
        if(dateFormat==null||"".equals(dateFormat))
        {
            dateFormat="yyyy-MM-dd";
        }
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        if ("day".equals(isDate))
        {
            Calendar calendar = Calendar. getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, change);
            DATE_CALENDAR=sf.format(calendar.getTime());
        }
        else if ("month".equals(isDate))
        {
            Calendar calendar = Calendar. getInstance();

            calendar.setTime(date);
            calendar.add(Calendar.MONTH, change);
            DATE_CALENDAR=sf.format(calendar.getTime());
        }
        else if ("year".equals(isDate))
        {
            Calendar calendar = Calendar. getInstance();

            calendar.setTime(date);
            calendar.add(Calendar.YEAR, change);
            DATE_CALENDAR=sf.format(calendar.getTime());
        }
        else
        {
            DATE_CALENDAR=sf.format(new Date().getTime());
        }
        return DATE_CALENDAR;
    }

    public static boolean DateCha(Date startTime,Date endTime,int s)
    {
        long cha = endTime.getTime() - startTime.getTime();
        double result = cha * 1.0 / (1000 * 60 * 60);
        if(result>=s){
            DATEBOOLEAN=true;
        }
        else
        {
            DATEBOOLEAN=false;
        }
        return DATEBOOLEAN;
    }

    public static void main(String[] args) {

        System.out.println(DateOperate.DateChange(new Date(),"month",-1,"yyyy-M-d"));
    }
}
