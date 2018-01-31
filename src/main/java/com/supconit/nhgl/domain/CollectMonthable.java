package com.supconit.nhgl.domain;

import java.math.BigDecimal;

public interface CollectMonthable {

	public void setMonthKey(String monthKey);
	public String getMonthKey();
	
	//public void setTotal(BigDecimal total);
	public BigDecimal getTotal();

	//public void setTotalYoy(BigDecimal totalYoy);
	public BigDecimal getTotalYoy();
	
}
