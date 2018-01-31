package com.supconit.nhgl.job.electric;

import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.supconit.common.utils.DBConnectionUtils;
import com.supconit.common.utils.DateUtils;
import com.supconit.honeycomb.base.context.SpringContextHolder;

@Service("PROC_ARCHIVE_ELECTRIC_JOB")
public class ArchiveDataByProcedureJob {
	private transient static final Logger log = LoggerFactory
			.getLogger(ArchiveDataByProcedureJob.class);
   
    public void archiveJob() throws SQLException{
    	archiveJobByDate(DateUtils.formatYyyyMMdd(new Date()));
    }

    /**
     * @param fixDate yyyy-MM-dd
     * @throws SQLException 
     */
    public void archiveJobByDate(String fixDate) throws SQLException{
    	//1.实时表---->整点表
    	String procedureName="PROC_ARCHIVE_ELECTRIC_HOUR_DATA(?)";
    	String parameter=fixDate;
		DataSource dataSouce = (DataSource) SpringContextHolder.getBean("dataSource");
		DBConnectionUtils.executeProcedure(dataSouce, procedureName, parameter);
      //2.整点表---->天表
        procedureName="PROC_ARCHIVE_ELECTRIC_DAY_DATA(?)";
		DBConnectionUtils.executeProcedure(dataSouce, procedureName, parameter);
      //3.天表------->月表
        procedureName="PROC_ARCHIVE_ELECTRIC_MONTH_DATA(?)";
		DBConnectionUtils.executeProcedure(dataSouce, procedureName, parameter);
    }
    
}
