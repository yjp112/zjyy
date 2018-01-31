package com.supconit.jobschedule.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.TriggerUtils;

/**
秒（0~59） 
分钟（0~59） 
小时（0~23） 
天（月）（0~31，但是你需要考虑你月的天数）
月（0~11） 
天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT） 
年份（1970－2099）
cronStr = Second + " " + Minute + " " + Hour + " " + Day+ " " 
         + Month + " " + Week;
其中每个元素可以是一个值(如6),一个连续区间(9-12),一个间隔时间(8-18/4)(/表示每隔4小时),一个列表(1,3,5),通配符。由于"月份中的日期"和"星期中的日期"这两个元素互斥的,必须要对其中一个设置?. 
0 0 10,14,16 * * ? 每天上午10点，下午2点，4点 
0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时 
0 0 12 ? * WED 表示每个星期三中午12点 
有些子表达式能包含一些范围或列表 
例如：子表达式（天（星期））可以为 “MON-FRI”，“MON，WED，FRI”，“MON-WED,SAT” 
“*”字符代表所有可能的值 
因此，“*”在子表达式（月）里表示每个月的含义，“*”在子表达式（天（星期））表示星期的每一天 

“/”字符用来指定数值的增量 
例如：在子表达式（分钟）里的“0/15”表示从第0分钟开始，每15分钟 
         在子表达式（分钟）里的“3/20”表示从第3分钟开始，每20分钟（它和“3，23，43”）的含义一样 

“？”字符仅被用于天（月）和天（星期）两个子表达式，表示不指定值 
当2个子表达式其中之一被指定了值以后，为了避免冲突，需要将另一个子表达式的值设为“？” 

“L” 字符仅被用于天（月）和天（星期）两个子表达式，它是单词“last”的缩写 
但是它在两个子表达式里的含义是不同的。 
在天（月）子表达式中，“L”表示一个月的最后一天 
在天（星期）自表达式中，“L”表示一个星期的最后一天，也就是SAT 
如果在“L”前有具体的内容，它就具有其他的含义了 
例如：“6L”表示这个月的倒数第６天，“ＦＲＩＬ”表示这个月的最一个星期五 
注意：在使用“L”参数时，不要指定列表或范围，因为这会导致问题 
 * @author DELL
 *
 */
public class CronExpressionEx extends CronExpression {
	private static final long serialVersionUID = 1L;
	//public static final Integer ALL_SPEC = new Integer(ALL_SPEC_INT);
	//public static final int NO_SPEC_INT = 98; // '?'

	private String secondsExp;
	private String minutesExp;
	private String hoursExp;
	private String daysOfMonthExp;
	private String monthsExp;
	private String daysOfWeekExp;

	public CronExpressionEx(String cronExpression) throws ParseException {
		super(cronExpression);

		StringTokenizer exprsTok = new StringTokenizer(cronExpression, " \t",
				false);
		secondsExp = exprsTok.nextToken().trim();
		minutesExp = exprsTok.nextToken().trim();
		hoursExp = exprsTok.nextToken().trim();
		daysOfMonthExp = exprsTok.nextToken().trim();
		monthsExp = exprsTok.nextToken().trim();
		daysOfWeekExp = exprsTok.nextToken().trim();
	}
	@SuppressWarnings("unchecked")
	public List<Date> computeFireTimes(org.quartz.Calendar cal,int numTimes){
        CronTrigger cronTrigger=new CronTrigger();
        cronTrigger.setCronExpression(this);
        
        return TriggerUtils.computeFireTimes(cronTrigger, cal, numTimes);
	}
	/**[startDate,endDate]这段时间内执行的时间点
	 * @param startDate 开始时间，如果开始时间小于当前系统时间，则开始时间等于系统当前时间
	 * @param endDate 结束时间
	 * @param maxTimes 最大执行次数
	 * @return
	 */
	public List<Date> computeFireTimes(Date startDate,Date endDate,int maxTimes){
	    List<Date> result=new ArrayList<Date>(); 
	    Date now=new Date();
	    if(startDate==null||startDate.before(now)){
	    	startDate=now;
	    }
	    
	    int count=0;
	    do{
	    	if(count==0){
	    		Calendar cal = Calendar.getInstance();
	    		cal.clear();
	    		cal.setTime(startDate);
	    		cal.add(Calendar.SECOND, -1);
	    		Date fireTime= getNextValidTimeAfter(cal.getTime());
	    		if(fireTime.compareTo(startDate)>=0){
	    			startDate=fireTime;
	    		}
	    	}else{
	    		startDate = getNextValidTimeAfter(startDate);
	    	}
	    	if(endDate!=null&&startDate.after(endDate)){
	    		break;
	    	}
			result.add(startDate);
			count++;
	    }while(maxTimes<0||result.size()<maxTimes);
		return result;
	}
	/**[startDate,endDate]这段时间内执行的时间点
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param maxTimes 最大执行次数
	 * @return
	 * @throws IllegalArgumentException 
	 */
	public List<Date> computeFireTimesIncludePassed(Date startDate,Date endDate,int maxTimes) throws IllegalArgumentException{
		
		List<Date> result=new ArrayList<Date>(); 
		if(startDate==null){
			throw new IllegalArgumentException("开始时间不能为空。");
		}
		
		int count=0;
		do{
			if(count==0){
				Calendar cal = Calendar.getInstance();
				cal.clear();
				cal.setTime(startDate);
				cal.add(Calendar.SECOND, -1);
				Date fireTime= getNextValidTimeAfter(cal.getTime());
				if(fireTime.compareTo(startDate)>=0){
					startDate=fireTime;
				}
			}else{
				startDate = getNextValidTimeAfter(startDate);
			}
			if(endDate!=null&&startDate.after(endDate)){
				break;
			}
			result.add(startDate);
			count++;
		}while(maxTimes<0||result.size()<maxTimes);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Set<TreeSet<Integer>> getSecondsSet() {
		return seconds;
	}

	public String getSecondsField() {
		return getExpressionSetSummary(seconds);
	}
	@SuppressWarnings("unchecked")
	public Set<TreeSet<Integer>> getMinutesSet() {
		return minutes;
	}

	public String getMinutesField() {
		return getExpressionSetSummary(minutes);
	}

	@SuppressWarnings("unchecked")
	public Set<TreeSet<Integer>> getHoursSet() {
		return hours;
	}

	public String getHoursField() {
		return getExpressionSetSummary(hours);
	}

	@SuppressWarnings("unchecked")
	public Set<TreeSet<Integer>> getDaysOfMonthSet() {
		return daysOfMonth;
	}

	public String getDaysOfMonthField() {
		return getExpressionSetSummary(daysOfMonth);
	}

	@SuppressWarnings("unchecked")
	public Set<TreeSet<Integer>> getMonthsSet() {
		return months;
	}

	public String getMonthsField() {
		return getExpressionSetSummary(months);
	}

	@SuppressWarnings("unchecked")
	public Set<TreeSet<Integer>> getDaysOfWeekSet() {
		return daysOfWeek;
	}

	public String getDaysOfWeekField() {
		return getExpressionSetSummary(daysOfWeek);
	}

	public String getSecondsExp() {
		return secondsExp;
	}

	public String getMinutesExp() {
		return minutesExp;
	}

	public String getHoursExp() {
		return hoursExp;
	}

	public String getDaysOfMonthExp() {
		return daysOfMonthExp;
	}

	public String getMonthsExp() {
		return monthsExp;
	}

	public String getDaysOfWeekExp() {
		return daysOfWeekExp;
	}
	
	public static void main(String[] args) throws ParseException {
	      CronExpressionEx exp = new CronExpressionEx("0 3/5 3,5,14 1-30 * ?");

	      System.out.println("toString: " + exp.toString());
	      System.out.println("isValidExpression: "
	          + CronExpressionEx.isValidExpression(exp
	              .getCronExpression()));

	      System.out.println("===============Fields================");
	      System.out.println("getSecondsField: " + exp.getSecondsField()
	          + " | getSecondsExp: " + exp.getSecondsExp());
	      System.out.println("getMinutesField: " + exp.getMinutesField()
	          + " | getMinutesExp: " + exp.getMinutesExp());
	      System.out.println("getHoursField: " + exp.getHoursField()
	          + " | getHoursExp: " + exp.getHoursExp());
	      System.out.println("getDaysOfMonthField: "
	          + exp.getDaysOfMonthField() + " | getDaysOfMonthExp: "
	          + exp.getDaysOfMonthExp());
	      System.out.println("getMonthsField: " + exp.getMonthsField()
	          + " | getMonthsExp: " + exp.getMonthsExp());
	      System.out.println("getDaysOfWeekField: "
	          + exp.getDaysOfWeekField() + " | getDaysOfWeekExp: "
	          + exp.getDaysOfWeekExp());
	      System.out.println("===============Fields================");


	      java.util.Date dd = new java.util.Date();
	      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      System.out.println("=============执行时间举例getNextValidTimeAfter()============");
	      for (int i = 0; i < 10; i++) {
	        dd = exp.getNextValidTimeAfter(dd);
			System.out.println((i+1) + "."+"\t"+simpleDateFormat.format( dd));
	        //dd = new java.util.Date(dd.getTime() + 1000);
	      }
	      System.out.println("=============执行时间举例computeFireTimes()============"); 
	      CronExpressionEx exp2 = new CronExpressionEx("54 50 15 */1 * ?");
	      Date startDate=simpleDateFormat.parse("2014-09-12 15:50:54");
	      Date endDate=simpleDateFormat.parse("2014-09-25 15:25:54");
	      for (Date d : exp2.computeFireTimes(startDate, endDate, 10)) {
			System.out.println(simpleDateFormat.format(d));
		}
	}
}
