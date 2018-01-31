package com.supconit.montrol.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HisAlarmDateReport {
	private String date;
	private int showNum;
	private int moniNum;
	private int openNum;
	private List<HisAlarmDailyReport> moniDates;
	private List<HisAlarmDailyReport> openDates;
	public int getShowNum() {
		return showNum;
	}
	public void setShowNum(int showNum) {
		this.showNum = showNum;
	}
	public List<HisAlarmDailyReport> getMoniDates() {
		return moniDates;
	}

	public List<HisAlarmDailyReport> getOpenDates() {
		return openDates;
	}

	public String getDate(){
		return date;
	}
	
	public int getMoniNum() {
		return moniNum;
	}
	public int getOpenNum() {
		return openNum;
	}
	public void parseDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00.000");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.date = sdf1.format(sdf.parse(this.date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addHisAlarmReport(HisAlarmDailyReport report){
		if(report.getTagType() == 13){
			openDates.add(report);
			openNum += report.getAlarmNum();
		}else{
			moniDates.add(report);
			moniNum += report.getAlarmNum();
		}
	}
	
	public HisAlarmDateReport(String date){
		this.moniNum = 0;
		this.openNum = 0;
		this.date = date;
		this.showNum = 0;
		this.moniDates = new ArrayList<HisAlarmDailyReport>();
		this.openDates = new ArrayList<HisAlarmDailyReport>();
	}
	
	public void compareToShowNum(){
		this.showNum = moniDates.size() > openDates.size()? moniDates.size() : openDates.size();
	}
	
}
