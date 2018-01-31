package com.supconit.common.daos;

import java.util.List;

import hc.jdbc.JdbcProcessor;

public abstract interface SimpleJdbc extends JdbcProcessor
{
  
  public abstract int[] batchUpdate(String sql, List<Object[]> params);
  public abstract int[] batchUpdate(String[] sql);
  public abstract int[] batchUpdate(List<String> sql);
}
