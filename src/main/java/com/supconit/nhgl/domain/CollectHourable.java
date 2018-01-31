package com.supconit.nhgl.domain;

import java.math.BigDecimal;

public interface CollectHourable {

	public void setCollectHour(Integer collectHour);
	public Integer getCollectHour();
	
	public void setTotal(BigDecimal total);
	public BigDecimal getTotal();
}
