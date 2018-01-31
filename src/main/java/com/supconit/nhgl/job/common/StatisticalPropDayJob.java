package com.supconit.nhgl.job.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supconit.common.utils.ListUtils;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.nhgl.analyse.electric.dao.ElectricMeterRealTimeDao;
import com.supconit.nhgl.analyse.electric.entities.StatisticalPropDay;
import com.supconit.nhgl.analyse.electric.service.StatisticalPropDayService;
import com.supconit.nhgl.basic.meterConfig.electic.entities.ConfigManager;
import com.supconit.nhgl.basic.meterConfig.electic.service.ConfigManagerService;
import com.supconit.nhgl.utils.HolidayConstans;

import jodd.datetime.JDateTime;

/**
 * 添加时间维度表
 * 
 * @author DELL
 * 
 */
public class StatisticalPropDayJob {

	private StatisticalPropDayService spdService = SpringContextHolder
			.getBean(StatisticalPropDayService.class);

	private ConfigManagerService cmService = SpringContextHolder
			.getBean(ConfigManagerService.class);
	private ElectricMeterRealTimeDao realTimeDao = SpringContextHolder
			.getBean(ElectricMeterRealTimeDao.class);

	private Map<String, Object> cache = new HashMap<String, Object>();
	private SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
	private List<ConfigManager> cmlist = null;

	/**
	 * 添加时间维度表
	 * 
	 * @throws ParseException
	 */
	public void addStatisticalPropDay() throws ParseException {
		Date sdate = null;
		Date edate = null;
		Date date = new Date();
		String[] configValue = null;
		List<StatisticalPropDay> spdlist = new ArrayList<StatisticalPropDay>();
		JDateTime time = new JDateTime(date); // TODO 改为局部变量
		if (cache.get(time.getYear()) == null) {
			cmlist = cmService.getConfigInfo();
			cache.put("'" + time.getYear() + "'", cmlist);
		} else {
			cmlist = (List<ConfigManager>) cache
					.get("'" + time.getYear() + "'");
		}
		StatisticalPropDay spd = spdService.getMaxDate();// TODO　nullpointexception
		// 如果查询为null，则表示第一次进行归档，则查询电表实时表中存在的最小天数，作为开始时间进行统计；
		boolean firstTime = false;
		String startTime = "";
		if (null == spd) {
			try {
				spd = realTimeDao.findMinCollectTime();
				startTime = spd.getStartTime().substring(0, 10);
				firstTime = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (spd == null)
				return;// 如果实时表中也没有数据，则不再进行数据归档。
		} else {
			startTime = spd.getDayOfMonthKey();
		}
		Date maxDate = sim.parse(startTime);
		//while (!sim.format(date).equals(sim.format(maxDate))) {
	    while (maxDate.compareTo(date)<0) {
			StatisticalPropDay sta = new StatisticalPropDay();
			if (firstTime) {
				maxDate.setDate(maxDate.getDate());
				firstTime = false;
			} else {
				maxDate.setDate(maxDate.getDate() + 1);
			}
			time = new JDateTime(maxDate);
			sta.setDayOfWeekKey(time.getDayOfWeek());
			sta.setDayOfWeekValue1("周" + time.getDayOfWeek());
			sta.setDayOfMonthKey(time.toString("YYYY-MM-DD"));
			sta.setDayOfMonthValue1(time.toString("YYYY年MM月DD日"));
			sta.setMonthKey(time.toString("YYYY-MM"));
			sta.setMonthValue1(time.toString("YYYY年MM月"));
			sta.setYearKey(time.getYear());
			sta.setYearValue1(time.getYear() + "年");
			sta.setQuarterKey(getQuarter(time.getMonth()));
			sta.setQuarterValue1(time.getYear() + "Q"
					+ getQuarter(time.getMonth()));
			for (ConfigManager cm : cmlist) {
				if ((HolidayConstans.ELECTRIC_JJR_TYPECODE).equals(cm
						.getTypeCode())) {
					configValue = cm.getConfigValue().split(" ");
					try {
						if (configValue[0].length() > 1)
							sdate = sim.parse(configValue[0]);
						if (configValue.length > 1)
							edate = sim.parse(configValue[1]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					continue;
				}

				Integer numEnd = edate.compareTo(maxDate);
				Integer numStart = sdate.compareTo(maxDate);

				if (cm.getFlag() == 1 && (numStart <= 0 && numEnd >= 0)) { // 配置项有效时加入节假日有效信息
					sta.setFlag(2);
					sta.setFalgValue(cm.getName());
					break; // 配置成功后推出节假日匹配
				} else if (time.getDayOfWeek() >= 1 && time.getDayOfWeek() <= 5) {
					sta.setFlag(0);
					sta.setFalgValue("周" + time.getDayOfWeek());
				} else {
					sta.setFlag(1);
					sta.setFalgValue("周" + time.getDayOfWeek());
				}

			}
			sta.setWeekOfYearKey(time.getWeekOfYear());
			if (time.getMonth() == 12 && time.getWeekOfYear() == 1)
				sta.setWeekOfYearValue1(time.getYear() + 1 + "年第"
						+ time.getWeekOfYear() + "周");
			else if(time.getMonth() == 1 && time.getWeekOfYear() > 4)
				sta.setWeekOfYearValue1((time.getYear()-1) + "年第"
						+ time.getWeekOfYear() + "周");
			else
				sta.setWeekOfYearValue1(time.getYear() + "年第"
						+ time.getWeekOfYear() + "周");
			spdlist.add(sta);
		}
		if (spdlist != null && spdlist.size() > 0) {
			List<List<StatisticalPropDay>> list = ListUtils.partition(spdlist,
					100);
			for (int i = 0; i < list.size(); i++) {
				spdService.insertSpd(list.get(i));
			}
		}
	}

	// 获取当前月是哪一季
	private int getQuarter(int month) {
		int quarter = 0;
		if (1 <= month && month < 4) {
			quarter = 1;
		} else if (4 <= month && month < 7) {
			quarter = 2;
		} else if (7 <= month && month < 10) {
			quarter = 3;
		} else {
			quarter = 4;
		}

		return quarter;
	}
}
