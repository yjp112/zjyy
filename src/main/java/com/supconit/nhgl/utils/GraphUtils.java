package com.supconit.nhgl.utils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.supconit.nhgl.analyse.electric.area.entities.NhDAreaHour;
import com.supconit.nhgl.analyse.electric.entities.Temperataure;
import com.supconit.nhgl.basic.discipine.discipine.entities.NhItem;
import com.supconit.nhgl.basic.medical.entities.MedicalInfo;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;
import com.supconit.nhgl.domain.CollectDayable;
import com.supconit.nhgl.domain.CollectHourable;
import com.supconit.nhgl.domain.CollectMonthable;
import com.supconit.nhgl.query.collect.entities.ElectricMeterMonth;
import com.supconit.nhgl.query.collect.water.entities.WaterMeterMonth;

public class GraphUtils {
	/**
	 * 表示部门
	 */
	public static final Integer DEPT = 1;
	/**
	 * pid等于0的代表最高级别部门
	 */
	public static final Integer TECHNIC_PID = 0;
	/**
	 * pid等于0的代表最高级别区域
	 */
	public static final Integer AREA_PID = 0;
	/**
	 * 电表设备类别code
	 */
	public static final String DEVICE_CODE_ELE = "11_p";
	/**
	 * 设备类型 电
	 */
	public static final Integer ELECTRIC_TYPE = 1;
	/**
	 * 设备类型 水
	 */
	public static final Integer WATER_TYPE = 2;
	/**
	 * 设备类型 汽
	 */
	public static final Integer GAS_TYPE = 3;
	/**
	 * 设备类型 能量
	 */
	public static final Integer ENERGY_TYPE = 4;
	/**
	 * 任务归属 电
	 */
	public static final String ELECTRIC = "电";

	/**
	 * 任务归属 水
	 */
	public static final String WATER = "水";

	/**
	 * 任务归属 气
	 */
	public static final String GAS = "气";

	/**
	 * 任务归属 气
	 */
	public static final String DEVICE = "设备";

	/**
	 * 执行频率 执行一次
	 */
	public static final String EXECUTEONE = "执行一次";

	/**
	 * 执行频率 每日执行
	 */
	public static final String EXECUTE_EVERY_DAY = "每日执行";

	/**
	 * 执行频率 按设置
	 */
	public static final String SETUP = "按设置";

	/**
	 * 能耗配置中的类型名
	 */
	public static final String TYPE_NAME = "节假日";

	/**
	 * 时间维度表节假日标识
	 */
	public static final String FLAG = "2";

	/**
	 * 一级区域父类Id
	 */
	public static final Long PARENT_ID = (long) 0;

	/**
	 * 二级部门父Id
	 */
	public static final Long PID = 10001L;

	/**
	 * 一级分项的parentid的值
	 */
	public static final String SUB_CODE = "0";

	/**
	 * 执行状态 成功
	 */
	public static final String SUCCESS = "Y";

	/**
	 * 执行状态 失败
	 */
	public static final String UNSUCCESS = "N";

	/**
	 * 返回当前日期到当月开始日期的字符串
	 * 
	 * @方法名: getDayList
	 * @创建日期: 2014-5-8
	 * @开发人员:高文龙
	 * @描述:
	 */
	public static String getDayList() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		StringBuffer sb = new StringBuffer("[");
		for (int i = 1; i <= day; i++) {
			sb.append("\"" + i + "\"").append(",");
		}
		String pieString = sb.toString().substring(0,
				sb.toString().length() - 1);
		// System.out.println(pieString);
		return pieString + "]";
	}
	public static String addYear(String date,int amount) throws ParseException{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar cal = Calendar.getInstance();
	        cal.clear();
	        cal.setTime(df.parse(date));
	        cal.add(Calendar.YEAR, amount);
	        return df.format(cal.getTime());
	}
	public static void main(String[] args) {
		try {
			System.out.println(addYear("2016-02-29",-1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 返回所有节假日
	 * 
	 * @方法名: getDayList
	 * @创建日期: 2014-5-8
	 * @开发人员:王海波
	 * @描述:
	 */
	public static String getDayList(List<ConfigManager> cmlist) {
		StringBuffer sb = new StringBuffer("");
		if (null != cmlist && cmlist.size() > 0) {
			for (ConfigManager d : cmlist) {
				sb.append(d.getName()).append(",");
			}
		}
		String pieString = sb.length() > 1 ? sb.toString().substring(0,
				sb.toString().length() - 1) : "";
		// System.out.println(pieString);
		return pieString;
	}

	/**
	 * 返回分项的字符串
	 * 
	 * @方法名: getDayList
	 * @创建日期: 2014-5-8
	 * @开发人员:高文龙
	 * @描述:
	 */
	public static String getFxList(List<NhItem> list) {
		if (null == list || list.size() == 0)
			return "";
		StringBuffer sb = new StringBuffer("");
		int size = list.size();
		for (int i = 0; i < size; i++) {
			sb.append(list.get(i).getName()).append(",");
		}
		String pieString = sb.length() > 1 ? sb.toString().substring(0,
				sb.toString().length() - 1) : "";
		// System.out.println(pieString);
		return pieString;
	}

	

	
	/**
	 * 返回对应的数据
	 * 
	 * @方法名: getData
	 * @创建日期: 2014-5-8
	 * @开发人员:高文龙
	 * @描述:
	 */
	public static String getTemp(List<Temperataure> list) {
		String max = "";
		String min = "";
		StringBuffer sb = new StringBuffer("[");
		if (null == list || list.size() == 0)
			return "[]";
		for (Temperataure d : list) {
			// 获取温度平均值 start
			max = d.getMaxTemp().substring(0, d.getMaxTemp().length() - 1);
			min = d.getMintTemp().substring(0, d.getMintTemp().length() - 1);
			d.setAvgTemp((Integer.parseInt(max) - Integer.parseInt(min)) / 2.0
					+ "℃");
			// end
			sb.append(d.getAvgTemp()).append(",");
		}
		String pieString = sb.length() > 0 ? sb.toString().substring(0,
				sb.toString().length() - 1) : "[";
		return pieString + "]";
	}



	/**
	 * 返回对应的数据
	 * 
	 * @创建日期: 2014-5-22
	 * @开发人员:王海波
	 * @param list
	 * @return
	 */
	public static String getNowDayEle(List<NhDAreaHour> list, int hours) {
		StringBuffer sb = new StringBuffer();
		int len = 0;
		for (Integer i = 0; i <= hours; i++) {
			len = sb.length();
			for(NhDAreaHour ndh : list){
				if(ndh.getTotal() != null && ndh.getCollectHour() == i){
					sb.append(ndh.getTotal())
					.append(",");
				}
			}
			if(len == sb.length()){
				sb.append("0.00").append(",");
			}
		}
		String pieString = sb.length() > 0 ? sb.toString().substring(0,
				sb.toString().length() - 1) : "";
		return pieString;
	}


	
	

	/**
	 * 
	 * @方法名: getPerent
	 * @创建日期:2014-5-9
	 * @开发人员:王海波
	 * @参数:@param list
	 * @参数:@param olist
	 * @参数:@return
	 * @返回值:list
	 * @描述:计算百分比
	 */
	public static List<ConfigManager> getPerent(List<ConfigManager> list) {
		BigDecimal p = new BigDecimal(0.0);
		Integer tb = 0;
		if (null == list || list.size() == 0)
			return null;
		// 这里需要对两个list进行判空
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getTotal() != null
					&& list.get(i).getLastYearTotal() != null) {
				String total = list.get(i).getTotal();
				String lastYeartotal = list.get(i).getLastYearTotal();
				if (Integer.parseInt(lastYeartotal) > 0) {
					tb = Math.round(Float.valueOf(total))-Math.round(Float.valueOf(lastYeartotal));
					p = new BigDecimal((float)tb/Math.round(Float.valueOf(lastYeartotal)));
					list.get(i).setPrecent(new DecimalFormat("0.00").format(p));
				} else {
					list.get(i).setPrecent(
							new DecimalFormat("0.00").format(0.00));
				}
			} else {
				list.get(i).setPrecent("0");
			}
		}
		return list;
	}



	/**
	 * 获取string类型的日期
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getDateString(Integer year, Integer month, Integer day) {
		Calendar cal = Calendar.getInstance();
		String dayString = "";
		String monthString = "";
		if (day != null) {
			if (day == 0) {
				month = month - 1;
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month - 1);
				day = cal.getActualMaximum(Calendar.DATE);
				dayString = "-" + day.toString();
			} else if (day < 10) {
				dayString = "-" + "0" + day;
			} else {
				dayString = "-" + day.toString();
			}
		}
		if (month != null) {
			if (month == 0) {
				year = year - 1;
				month = 12;
				monthString = "-" + month.toString();
			} else if (month < 10) {
				monthString = "-" + "0" + month;
			} else if (month == 13) {
				year = year + 1;
				month = 1;
				monthString = "-" + "0" + month.toString();
			} else if (month > 13) {
				month = month - 12;
				year = year + 1;
				if (month < 10)
					monthString = "-" + "0" + month;
			} else {
				monthString = "-" + month.toString();
			}
		}
		return year + monthString + dayString;
	}

	
	


	//获取当前年的门诊量，平均温度，设备运行台数
	public static Map<String, Object> getMedicalInfo(List<MedicalInfo> mList){
		StringBuilder dataDevice = new StringBuilder();
		StringBuilder dataOperation = new StringBuilder();
		StringBuilder dataTemperature = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer j = 0;
		for(Integer i = 1; i <= 12; i++){
			if(mList.size() > j){
				//判断该月份是否等于i
				if(Integer.parseInt(mList.get(j).getMonthKey().substring(5,7)) == i){
					dataDevice.append(mList.get(j).getRunDevice()).append(",");
					dataTemperature.append(mList.get(j).getAvgTemperataure()).append(",");
					dataOperation.append(mList.get(j).getOutpatient()).append(",");
					j++;
				}else{
					dataDevice.append("0.00").append(",");
					dataTemperature.append("0.00").append(",");
					dataOperation.append("0.00").append(",");
				}
			}else{
				//如果今年每个月的用电总量都没有的话，设置为0.00
				dataDevice.append("0.00").append(",");
				dataTemperature.append("0.00").append(",");
				dataOperation.append("0.00").append(",");
			}
		}
		map.put("dataDevice", dataDevice);
		map.put("dataOperation", dataOperation);
		map.put("dataTemperature", dataTemperature);
		return map;
	}
	
	//获取当前年的用电总量
	public static Map<String, Object> getMonthInfoElectric(List<ElectricMeterMonth> emList){
		StringBuilder data = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer j = 0;
		for(Integer i = 1; i <= 12; i++){
			if(emList.size() > j){
				//判断该月份是否等于i
				if(Integer.parseInt(emList.get(j).getMonthKey().substring(5,7)) == i){
					data.append(emList.get(j).getTotalMonthValue()).append(",");
					j++;
				}else{
					data.append("0").append(",");
				}
			}else{
				//如果今年每个月的用电总量都没有的话，设置为0.00
				data.append("0").append(",");
			}
		}
		
		map.put("dataElectric", data);
		return map;
	}
	
	public static Map<String, Object> getMonthInfoWater(List<WaterMeterMonth> emList){
		StringBuilder data = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer j = 0;
		for(Integer i = 1; i <= 12; i++){
			if(emList.size() > j){
				//判断该月份是否等于i
				if(Integer.parseInt(emList.get(j).getMonthKey().substring(5,7)) == i){
					data.append(emList.get(j).getTotalMonthValue()).append(",");
					j++;
				}else{
					data.append("0").append(",");
				}
			}else{
				//如果今年每个月的用电总量都没有的话，设置为0.00
				data.append("0").append(",");
			}
		}
		
		map.put("dataWater", data);
		return map;
	}	
	/**
	 * @param list 实时监控数据
	 * @param hours
	 * @return
	 */
	public static <T extends CollectHourable> Map<String, Object> getHourData(List<T> list,Integer hours) {
		List<String> hourBuilder=new ArrayList<String>();
		List<String> dataBuilder=new ArrayList<String>();
		for (int i = 0; i <=hours; i++) {
			T current=searchHour(list, i);
			String month=i+"点";
			BigDecimal data=(current==null?BigDecimal.ZERO:current.getTotal());
			if(data!=null){
				data=data.setScale(2, RoundingMode.HALF_UP);
			}
			hourBuilder.add(month);
			dataBuilder.add(data.toString());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hour", StringUtils.join(hourBuilder,","));
		map.put("data", StringUtils.join(dataBuilder,","));
		return map;
	}
	public static <T extends CollectDayable> Map<String, Object> getDayData4DayNight(List<T> list,
			Integer dayOfMonth) {
		List<String> dayBuilder=new ArrayList<String>();
		List<String> dayDataBuilder=new ArrayList<String>();
		List<String> nightDataBuilder=new ArrayList<String>();
		for (int i = 0; i < dayOfMonth; i++) {
			T current=searchDay(list, i+1);
			String month=String.valueOf(i+1);
			BigDecimal total=(current==null?BigDecimal.ZERO:current.getDayDaytimeValue());
			BigDecimal totalYoy=(current==null?BigDecimal.ZERO:current.getDayNightValue());
			if(total!=null){
				total=total.setScale(0,RoundingMode.HALF_UP);
			}
			if(totalYoy!=null){
				totalYoy=totalYoy.setScale(0,RoundingMode.HALF_UP);
			}
			dayBuilder.add(month);
			dayDataBuilder.add(total.toString());
			nightDataBuilder.add(totalYoy.toString());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("day", StringUtils.join(dayBuilder,","));
		map.put("dayData", StringUtils.join(dayDataBuilder,","));
		map.put("nightData", StringUtils.join(nightDataBuilder,","));
		return map;
	}
	public static <T extends CollectMonthable> Map<String, Object> getMonthData(List<T> list,
			Integer year,Integer month) {
		List<String> monthBuilder=new ArrayList<String>();
		List<String> dataBuilder=new ArrayList<String>();
		List<String> lastDataBuilder=new ArrayList<String>();
		List<String> percentBuilder=new ArrayList<String>();
		for (int i = 0; i < month; i++) {
			T current=searchMonth(list, year,i+1);
			String monthItem=String.valueOf(String.valueOf(year)+"-"+StringUtils.leftPad(i+1+"", 2, "0"));
			String percent="";
			BigDecimal total=(current==null?BigDecimal.ZERO:current.getTotal());
			BigDecimal totalYoy=(current==null?BigDecimal.ZERO:current.getTotalYoy());
			if(total!=null){
				total=total.setScale(0,RoundingMode.HALF_UP);
			}
			if(totalYoy!=null){
				totalYoy=totalYoy.setScale(0,RoundingMode.HALF_UP);
			}
			if(totalYoy==null||totalYoy.compareTo(BigDecimal.ZERO)==0){
				if(total==null||total.compareTo(BigDecimal.ZERO)==0){
					percent="0.00";
				}else{
					percent="100.00";
				}
			}else{
				percent=(new DecimalFormat("0.00").format(total.subtract(totalYoy).setScale(4,RoundingMode.HALF_UP).divide(totalYoy,RoundingMode.HALF_UP).multiply(new BigDecimal(100)))).toString();
			}
			monthBuilder.add(monthItem);
			dataBuilder.add(total.toString());
			lastDataBuilder.add(totalYoy.toString());
			percentBuilder.add(percent);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("month", StringUtils.join(monthBuilder,","));
		map.put("data", StringUtils.join(dataBuilder,","));
		map.put("percent", StringUtils.join(percentBuilder,","));
		map.put("lastData", StringUtils.join(lastDataBuilder,","));
		return map;
	}
	public static <T extends CollectDayable> Map<String, Object> getDayData(List<T> list,
			Integer dayOfMonth) {
		List<String> monthBuilder=new ArrayList<String>();
		List<String> dataBuilder=new ArrayList<String>();
		List<String> lastDataBuilder=new ArrayList<String>();
		List<String> percentBuilder=new ArrayList<String>();
		for (int i = 0; i < dayOfMonth; i++) {
			T current=searchDay(list, i+1);
			String month=String.valueOf(i+1);
			String percent="";
			BigDecimal total=(current==null?BigDecimal.ZERO:current.getTotal());
			BigDecimal totalYoy=(current==null?BigDecimal.ZERO:current.getTotalYoy());
			if(total!=null){
				total=total.setScale(0,RoundingMode.HALF_UP);
			}
			if(totalYoy!=null){
				totalYoy=totalYoy.setScale(0,RoundingMode.HALF_UP);
			}
			if(totalYoy==null||totalYoy.compareTo(BigDecimal.ZERO)==0){
				if(total==null||total.compareTo(BigDecimal.ZERO)==0){
					percent="0.00";
				}else{
					percent="100.00";
				}
			}else{
				percent=(new DecimalFormat("0.00").format(total.subtract(totalYoy).setScale(4,RoundingMode.HALF_UP).divide(totalYoy,RoundingMode.HALF_UP).multiply(new BigDecimal(100)))).toString();
			}
			monthBuilder.add(month);
			dataBuilder.add(total.toString());
			lastDataBuilder.add(totalYoy.toString());
			percentBuilder.add(percent);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("month", StringUtils.join(monthBuilder,","));
		map.put("data", StringUtils.join(dataBuilder,","));
		map.put("percent", StringUtils.join(percentBuilder,","));
		map.put("lastData", StringUtils.join(lastDataBuilder,","));
		return map;
	}

	
	public static <T extends CollectHourable> Map<String, Object> getHourData(List<T> list,
			List<T> lastList, Integer hours) {
		List<String> monthBuilder=new ArrayList<String>();
		List<String> dataBuilder=new ArrayList<String>();
		List<String> lastDataBuilder=new ArrayList<String>();
		List<String> percentBuilder=new ArrayList<String>();
		for (int i = 0; i < hours; i++) {
			T current=searchHour(list, i);
			T last=searchHour(lastList, i);
			String month=i+"点";
			String percent="";
			BigDecimal data=(current==null?BigDecimal.ZERO:current.getTotal());
			BigDecimal lastData=(last==null?BigDecimal.ZERO:last.getTotal());
			if(data!=null){
				data=data.setScale(0, RoundingMode.HALF_UP);
			}
			if(lastData!=null){
				lastData=lastData.setScale(0, RoundingMode.HALF_UP);
			}
			if(lastData==null||lastData.compareTo(BigDecimal.ZERO)==0){
				if(data==null||data.compareTo(BigDecimal.ZERO)==0){
					percent="0.00";
				}else{
					percent="100.00";
				}
			}else{
				percent=(new DecimalFormat("0.00").format(data.subtract(lastData).setScale(4, RoundingMode.HALF_UP).divide(lastData,RoundingMode.HALF_UP).multiply(new BigDecimal(100)))).toString();
			}
			monthBuilder.add(month);
			dataBuilder.add(data.toString());
			lastDataBuilder.add(lastData.toString());
			percentBuilder.add(percent);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("month", StringUtils.join(monthBuilder,","));
		map.put("data", StringUtils.join(dataBuilder,","));
		map.put("percent", StringUtils.join(percentBuilder,","));
		map.put("lastData", StringUtils.join(lastDataBuilder,","));
		return map;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T extends CollectHourable> T searchHour(List<T> list, int hour){
		if(list==null||list.size()<=0){
			return null;
		}
		Class clazz=list.get(0).getClass();
		try {
			T h = (T) clazz.newInstance();
			h.setCollectHour(hour);
			T[] objects=list.toArray((T[])Array.newInstance(clazz, 0));
			Comparator<T> c=new Comparator<T>(){
				@Override
				public int compare(T o1, T o2) {
					if(o1==null){
						return -1;
					}
					if(o2==null){
						return 1;
					}
					try {
						return o1.getCollectHour().compareTo(o2.getCollectHour());
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
					return -1;
				}
				
			};
			Arrays.sort(objects,c);
			int idx=Arrays.binarySearch(objects, h, c);
			if(idx>=0){
				return objects[idx];
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}catch (SecurityException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T extends CollectDayable> T searchDay(List<T> list, int day){
		if(list==null||list.size()<=0){
			return null;
		}
		Class clazz=list.get(0).getClass();
		try {
			T h = (T) clazz.newInstance();
			String searchDay=list.get(0).getDayOfMonthKey().substring(0, 8)+StringUtils.leftPad(day+"", 2, "0");
			h.setDayOfMonthKey(searchDay);
			T[] objects=list.toArray((T[])Array.newInstance(clazz, 0));
			Comparator<T> c=new Comparator<T>(){
				@Override
				public int compare(T o1, T o2) {
					if(o1==null){
						return -1;
					}
					if(o2==null){
						return 1;
					}
					try {
						return o1.getDayOfMonthKey().substring(0, 10).compareTo(o2.getDayOfMonthKey().substring(0, 10));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
					return -1;
				}
				
			};
			Arrays.sort(objects,c);
			int idx=Arrays.binarySearch(objects, h, c);
			if(idx>=0){
				return objects[idx];
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}catch (SecurityException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T extends CollectMonthable> T searchMonth(List<T> list, int year,int month){
		if(list==null||list.size()<=0){
			return null;
		}
		Class clazz=list.get(0).getClass();
		try {
			T h = (T) clazz.newInstance();
			h.setMonthKey(String.valueOf(year)+"-"+StringUtils.leftPad(month+"", 2, "0"));
			T[] objects=list.toArray((T[])Array.newInstance(clazz, 0));
			Comparator<T> c=new Comparator<T>(){
				@Override
				public int compare(T o1, T o2) {
					if(o1==null){
						return -1;
					}
					if(o2==null){
						return 1;
					}
					try {
						return o1.getMonthKey().compareTo(o2.getMonthKey());
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
					return -1;
				}
			};
		    Arrays.sort(objects,c);
			int idx=Arrays.binarySearch(objects, h, c);
			if(idx>=0){
				return objects[idx];
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}catch (SecurityException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	
}
