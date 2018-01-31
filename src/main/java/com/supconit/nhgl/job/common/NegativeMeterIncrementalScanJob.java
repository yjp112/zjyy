package com.supconit.nhgl.job.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.supconit.common.utils.DateUtils;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.nhgl.alarm.meter.entities.MeterAlarm;

import jodd.datetime.JDateTime;

/**
 * 扫描电表，水表小时表增量负值的数据。
 * 暂定晚上12电后执行，扫描前一天的抄表数据。
 */
public class NegativeMeterIncrementalScanJob {


    private static final String SCAN_METER_SQL_BY_DATE =
            "SELECT ID AS HOUR_METER_ID, BIT_NO,DEVICE_ID,TOTAL,INCREMENTAL,COLLECT_TIME,'电表' AS METER_TYPE FROM NH_ELECTRIC_METER WHERE COLLECT_DATE = ? AND INCREMENTAL < 0" +
            "UNION ALL" +
            "SELECT ID AS HOUR_METER_ID, BIT_NO,DEVICE_ID,TOTAL,INCREMENTAL,COLLECT_TIME,'水表' AS METER_TYPE FROM NH_WATER_METER WHERE COLLECT_DATE = ? AND INCREMENTAL < 0";

    private static final String INSERT_NEGATIVE_DATA_TO_ALARM_TABLE = "INSERT INTO NH_METER_DATA_ALARM (HOUR_METER_ID,ALARM_TIME,METER_TYPE,STATUS) values(?,?,?,?)";
    private static final int BATCH_SIZE = 1000;

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(SpringContextHolder.getBean(DataSource.class));


    public void scanMeterAlarmJob(){
        JDateTime jdt = new JDateTime();
        jdt.addDay(-1);//扫描前一天数据

        List<MeterAlarm> alarmList =jdbcTemplate.query(SCAN_METER_SQL_BY_DATE, new Object[]{jdt.toString("yyyy-mm-dd"), jdt.toString("yyyy-mm-dd")}, new RowMapper<MeterAlarm>() {
            @Override
            public MeterAlarm mapRow(ResultSet rs, int rowNum) throws SQLException {
                MeterAlarm alarm = new MeterAlarm();
                alarm.setHourMeterId(rs.getLong("HOUR_METER_ID"));
                alarm.setStatus(1);
                alarm.setAlarmTime(rs.getDate("COLLECT_TIME"));
                alarm.setMeterType(rs.getString("METER_TYPE"));
                return alarm;
            }
        });


        jdbcTemplate.batchUpdate(INSERT_NEGATIVE_DATA_TO_ALARM_TABLE, alarmList, BATCH_SIZE, new ParameterizedPreparedStatementSetter<MeterAlarm>() {
            @Override
            public void setValues(PreparedStatement ps, MeterAlarm argument) throws SQLException {
                ps.setLong(1,argument.getHourMeterId());
                ps.setString(2, DateUtils.format(argument.getAlarmTime(),"yyyy-MM-dd HH:mm:ss"));
                ps.setString(3, argument.getMeterType());
                ps.setLong(4,argument.getStatus());
            }
        });
    }



}
