package com.supconit.nhgl.job.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.supconit.common.utils.RandomUtils;
import com.supconit.honeycomb.base.context.SpringContextHolder;

/**模拟抄表系统,每小时往NH_ELECTRIC_METER_REALTIME 表里插入一条抄表数据
 * @author DELL
 *
 */
public class GenerateRealtimeMeterDataJob {

    private static final String QUERY_ELECTRIC_MAX_DATA = "select bit_NO as bitNo,max(TOTAL) as total FROM NH_ELECTRIC_METER_REALTIME GROUP BY BIT_NO";
    private static final String QUERY_WATER_MAX_DATA = "select bit_NO as bitNo,max(TOTAL) as total FROM NH_WATER_METER_REALTIME GROUP BY BIT_NO";

    private static final String INSERT_NEW_ELECTRIC_DATA_IN_REALTIME = "INSERT INTO NH_ELECTRIC_METER_REALTIME(BIT_NO,TOTAL,COLLECT_TIME) VALUES(?,?,?)";
    private static final String INSERT_NEW_WATER_DATA_IN_REALTIME = "INSERT INTO NH_WATER_METER_REALTIME(BIT_NO,TOTAL,COLLECT_TIME) VALUES(?,?,?)";

    private static JdbcTemplate jdbcTemplate = new JdbcTemplate((DataSource) SpringContextHolder.getBean("dataSource"));

    public void doElectricJob(){
        generateData(QUERY_ELECTRIC_MAX_DATA,INSERT_NEW_ELECTRIC_DATA_IN_REALTIME);
    }

    public void doWaterJob(){
        generateData(QUERY_WATER_MAX_DATA,INSERT_NEW_WATER_DATA_IN_REALTIME);
    }

    private void generateData(String querySql, String insertSql) {
        List<Map<String,Object>> resultMap = jdbcTemplate.queryForList(querySql);

        List<Object[]> objList = new ArrayList<Object[]>();

        for(Map<String,Object> map :  resultMap) {
                BigDecimal bigDecimal = BigDecimal.valueOf(Double.valueOf(map.get("total").toString())).add(BigDecimal.valueOf(RandomUtils.getDoubleRandom(0.0, 3.0, 1)));

                Object[] objs = new Object[]{map.get("bitNo"), bigDecimal.doubleValue(), new Date()};

                objList.add(objs);
        }
        jdbcTemplate.batchUpdate(insertSql,objList);
    }
}
