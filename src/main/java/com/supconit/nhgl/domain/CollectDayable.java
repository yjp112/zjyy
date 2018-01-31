package com.supconit.nhgl.domain;

import java.math.BigDecimal;

public interface CollectDayable {

	public void setDayOfMonthKey(String dayOfMonthKey);
	public String getDayOfMonthKey();
	
	public void setTotal(BigDecimal total);
	public BigDecimal getTotal();

	public void setTotalYoy(BigDecimal totalYoy);
	public BigDecimal getTotalYoy();
	
	public BigDecimal getDayDaytimeValue();
	public BigDecimal getDayNightValue();
	
}
