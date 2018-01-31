package com.supconit.base.services.impl;

import com.supconit.base.entities.Device;
import com.supconit.base.services.DeviceImpExpService;
import com.supconit.base.services.impl.pojos.*;
import com.supconit.common.utils.DBConnectionUtils;
import com.supconit.common.utils.UniqueIdUtils;
import com.supconit.common.utils.excel.ExcelImportHelper;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.honeycomb.business.authorization.services.UserService;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.entities.Person;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.honeycomb.business.organization.services.PersonService;

import hc.safety.manager.SafetyManager;
import jodd.io.FileUtil;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Service
public class DeviceImpExpServiceImpl implements DeviceImpExpService {
	private static final Logger log = LoggerFactory
			.getLogger(DeviceImpExpServiceImpl.class);
	@Resource
	private DepartmentService departmentService;
	@Resource
	private PersonService personService;
	@Resource
	public UserService userService;

    @Resource
    private SafetyManager safetyManager;

	private <T> List<T> parseExcel(InputStream inputStreams,int idx,Class<T> clazz) throws BiffException, IOException{
		if(idx==-1){
			return null;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
        ExcelImportHelper<T> importHelper = new ExcelImportHelper(clazz);
		
		return importHelper.importExcel(inputStreams, new int[]{idx});
	}

	private String loadSQL(String sqlfileName) {
		String packageName=ImpDevice.class.getPackage().getName();
		packageName=packageName.replaceAll("\\.", "/");
		URL url = DeviceImpExpServiceImpl.class.getClassLoader().getResource(packageName);

		try {
			String fileName = URLDecoder.decode(url.getFile(), "UTF-8")
					.substring(1);
			return FileUtil.readString(fileName + "/" + sqlfileName);
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}	
	private Timestamp buildTimestamp(Date d){
		if(d==null) return null;
		return new Timestamp(d.getTime());
	}
	public long sum(int[] count){
		long sum = 0;
		for (int i : count) {
			sum += i;
		}
		return sum;
	}
	@Override
	public List<String> importDevice(InputStream inputStream) throws BiffException, IOException, SQLException {
        User createUser  =(User)safetyManager.getCurrentUser();
        String createName =personService.getById(createUser.getId()).getName();
        DataSource dataSource = SpringContextHolder.getBean("dataSource");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        BaseCache.getInstance(jdbcTemplate);
		List<String> errorMsgs=new ArrayList<String>();
		ByteArrayOutputStream cacheStream=new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(buffer)) > -1 ) {
                    cacheStream.write(buffer, 0, len);
                }
                cacheStream.flush();
		} catch (IOException e) {
			errorMsgs.add(e.getMessage());
			return errorMsgs;
		}
        try {
            List<String> sheetNames = Arrays.asList(Workbook.getWorkbook(new ByteArrayInputStream(cacheStream.toByteArray())).getSheetNames());
            final List<ImpAlarmLevel> alarmLevelColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("报警级别"), ImpAlarmLevel.class);
            BaseCache.initAlarmLevem(alarmLevelColl);
            final List<ImpArea> areaColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("区域"), ImpArea.class);
            BaseCache.initArea(areaColl, createUser, createName);
            final List<ImpType> typeColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("设备分类"), ImpType.class);
            BaseCache.initType(typeColl, createUser, createName);
            final List<ImpDevice> deviceColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("设备对象"), ImpDevice.class);
            List<ImpLog> logs = BaseCache.initDevice(deviceColl, createUser, createName);
            final List<ImpUnifyTag> unifyTagColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("位号表"), ImpUnifyTag.class);
            BaseCache.initTag(unifyTagColl);
            BaseCache.dispose();
        }catch (Exception e){
            e.printStackTrace();
            errorMsgs.add(e.getMessage());
        }
		return errorMsgs;
	}

	@Override
	public List<Device> exportDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		String packageName=ImpDevice.class.getPackage().getName();
		packageName=packageName.replaceAll("\\.", "/");
		URL url = DeviceImpExpServiceImpl.class.getClassLoader().getResource(packageName);
		System.out.println(url.getFile());
	}
	
	private List<ImpLog> queryImportLog(JdbcTemplate jdbcTemplate,final String importNum){
		String querySql = "select ERROR_MSG, IMPORT_NUM from IMP_LOG WHERE IMPORT_NUM=?";
		List<ImpLog> errorLogs = jdbcTemplate.query(querySql,
				new String[] { importNum }, new RowMapper<ImpLog>() {
					@Override
					public ImpLog mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						ImpLog task = new ImpLog();
						task.setErrorMsg(rs.getString("ERROR_MSG"));
						task.setImportNum(importNum);
						return task;
					}

				});
		return errorLogs;
	}
}