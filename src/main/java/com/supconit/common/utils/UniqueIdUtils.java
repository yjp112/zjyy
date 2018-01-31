package com.supconit.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UniqueIdUtils{
  private static Date date = new Date();
  private static final int MAX_GENERATE_COUNT = 99999;
  private static Map<String, Integer> map=new HashMap<String, Integer>();
  public static synchronized String next(String prefix){
	Integer seq= map.get(prefix);
    if (seq==null||seq > MAX_GENERATE_COUNT){
    	seq = 0;
    }
    seq++;
    map.put(prefix, seq);
    date.setTime(System.currentTimeMillis());
    return String.format(prefix+"%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq);
  }
  
  public static void main(String[] args) {
	System.out.println(UniqueIdUtils.next("a"));
	System.out.println(UniqueIdUtils.next("a"));
	System.out.println(UniqueIdUtils.next("b"));
	System.out.println(UniqueIdUtils.next("b"));
	System.out.println(UniqueIdUtils.next("c"));
	System.out.println(UniqueIdUtils.next("c"));
	System.out.println(UniqueIdUtils.next("c"));
	System.out.println(UniqueIdUtils.next("c"));
}
}