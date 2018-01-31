package com.supconit.nhgl.job.electric;

import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.socketio.server.impl.BitReader;
import com.supconit.common.utils.socketio.server.impl.BlockRequestResponse;
import com.supconit.common.utils.socketio.server.impl.HpidReadBlock;
import com.supconit.honeycomb.base.context.SpringContextHolder;

/**
 * 每小时读取电表读数并保存至表NH_D_METER_REALTIME
 */
public class RealTimeDataFetchJob {
	private transient static final Logger log = LoggerFactory
			.getLogger(RealTimeDataFetchJob.class);
	private static JdbcTemplate jdbcTemplate = new JdbcTemplate(
			(DataSource) SpringContextHolder.getBean("dataSource"));

	private static final String QUERY_METER_DEVICE = "SELECT D.ID,D.HPID,D.EXTENDED1,D.SPRING_EL FROM DEVICE D  inner join device d2 on d.parent_id=d2.id WHERE D2.Device_Code= ?";
	private static final String SQLSERVER_INSERT_METER_REALTIME = "INSERT INTO NH_D_METER_REALTIME(BIT_NO,TOTAL,COLLECT_TIME) values (?,?,?)";
	private static final String ORACLE_INSERT_METER_REALTIME = "INSERT INTO NH_D_METER_REALTIME(ID,BIT_NO,TOTAL,COLLECT_TIME) values (SEQ_NH_D_METER_REALTIME.NEXTVAL,?,?,?)";
	private static String INSERT_METER_REALTIME=SQLSERVER_INSERT_METER_REALTIME;
	static{
		try {
			DatabaseMetaData metaData=jdbcTemplate.getDataSource().getConnection().getMetaData();
			if("Oracle".equalsIgnoreCase(metaData.getDatabaseProductName())){
				INSERT_METER_REALTIME=ORACLE_INSERT_METER_REALTIME;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param deviceCode 电表系统设备编号
	 * @param tagSuffix 电表位号后缀
	 * @throws ParseException
	 */
	public void fetchData(String deviceCode,String tagSuffix) throws ParseException {
		long start = System.currentTimeMillis();
		if (log.isInfoEnabled()) {
            log.info("获取电表读数Job开始执行【" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:ssss") + "】.........");
        }
		List<Map<String, Object>> meters = jdbcTemplate.queryForList(
				QUERY_METER_DEVICE, deviceCode);
		List<HpidReadBlock> blocks = new ArrayList<HpidReadBlock>();
		for (Map<String, Object> map : meters) {
			String hpid=(String) map.get("HPID");
			String springEL=(String) map.get("SPRING_EL");
			blocks.add(new HpidReadBlock(hpid,hpid+tagSuffix,springEL));
		}
		BlockRequestResponse request = new BlockRequestResponse();
		request.setBlocks(blocks);
		BlockRequestResponse response = (BlockRequestResponse) BitReader
				.getInstance().dataRead(request);
		final HpidReadBlock[] datas=response.getBlocks();
		/*try {
			File f=new File(System.getProperty("java.io.tmpdir"),"NHGL_"+deviceCode+"_"+DateUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
			f.createNewFile();
			System.err.println(f.getAbsolutePath());
			FileUtils.writeLines(f,"UTF-8",Arrays.asList(datas));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		int[] count = jdbcTemplate.batchUpdate(INSERT_METER_REALTIME,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						int idx=1;
						HpidReadBlock d=datas[i];
						ps.setString(idx++, d.getHpid());
						ps.setObject(idx++, StringUtils.isBlank(d.getValue())?null:Double.parseDouble(d.getValue()));
						if(d.getUpdateTime()==null){
							d.setUpdateTime(new Date());
						}
						ps.setTimestamp(idx++, new Timestamp(d.getUpdateTime().getTime()));
					}

					@Override
					public int getBatchSize() {
						return datas.length;
					}
				});
        if (log.isInfoEnabled()) {
        	long sum=0;
        	for (int i : count) {
        		sum+=i;
        	}
        	long end = System.currentTimeMillis();
            log.info("获取电表读数Job执行完毕【" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss:ssss") + "】，本次执行耗时【"
                            + (end - start) + "】ms");
            log.info("成功读取并保存【"+sum+"】条数据到NH_D_METER_REALTIME");		
        }
	}
	

}
