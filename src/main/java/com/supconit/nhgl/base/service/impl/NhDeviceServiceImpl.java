package com.supconit.nhgl.base.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import hc.base.domains.Pageable;






import hc.base.domains.Pagination;
import hc.safety.manager.SafetyManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.base.entities.Device;
import com.supconit.base.services.impl.pojos.ImpAlarmLevel;
import com.supconit.base.services.impl.pojos.ImpArea;
import com.supconit.base.services.impl.pojos.ImpDevice;
import com.supconit.base.services.impl.pojos.ImpLog;
import com.supconit.base.services.impl.pojos.ImpType;
import com.supconit.base.services.impl.pojos.ImpUnifyTag;
import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.common.services.AbstractBaseTreeServiceImpl;
import com.supconit.common.services.BaseTreeService;
import com.supconit.common.utils.excel.ExcelImportHelper;
import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.nhgl.base.dao.NhDeviceDao;
import com.supconit.nhgl.base.entities.NhDevice;
import com.supconit.nhgl.base.service.NhDeviceService;
import com.supconit.nhgl.base.service.impl.pojo.ImpNhArea;
import com.supconit.nhgl.base.service.impl.pojo.ImpNhDept;
@Service
public class NhDeviceServiceImpl extends AbstractBaseBusinessService<NhDevice, Long>implements  NhDeviceService{
	@Autowired
	private NhDeviceDao		nhDeviceDao;
	@Resource
    private SafetyManager safetyManager;
	@Resource
	private PersonService personService;


	@Override
	public NhDevice getById(Long arg0) {
		return null;
	}


	@Override
	public void save(NhDevice arg0) {
		
	}


	@Override
	public void insert(NhDevice entity) {
		
	}


	@Override
	public void update(NhDevice entity) {
		
	}


	@Override
	public void delete(NhDevice entity) {
		
	}
	@Override
	public void deleteById(Long id) {
		
	}
	@Override
	public Pageable<NhDevice> findByCondition(Pagination<NhDevice> pager,
			NhDevice condition) {
		return nhDeviceDao.findByCondition(pager, condition); 
	}
	
	@Override
	public List<String> importNhDevice(InputStream inputStream,List<String> lstErrMsg) throws BiffException, IOException, SQLException {
        User createUser  =(User)safetyManager.getCurrentUser();
        String createName =personService.getById(createUser.getPersonId()).getName();
        DataSource dataSource = SpringContextHolder.getBean("dataSource");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        NhBaseCache.getInstance(jdbcTemplate);
		ByteArrayOutputStream cacheStream=new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(buffer)) > -1 ) {
                    cacheStream.write(buffer, 0, len);
                }
                cacheStream.flush();
		} catch (IOException e) {
			lstErrMsg.add("导入失败:"+e.toString());
			return lstErrMsg;
		}
        try {
            List<String> sheetNames = Arrays.asList(Workbook.getWorkbook(new ByteArrayInputStream(cacheStream.toByteArray())).getSheetNames());
            final List<ImpNhDept> nhDeptColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("部门"), ImpNhDept.class);
            NhBaseCache.initNhDept(nhDeptColl, createUser, createName);
            rebuildDeviceTree();
            final List<ImpNhArea> nhAreaColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("服务区域"), ImpNhArea.class);
            NhBaseCache.initNhArea(nhAreaColl, createUser, createName);
            final List<ImpAlarmLevel> alarmLevelColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("报警级别"), ImpAlarmLevel.class);
            NhBaseCache.initAlarmLevem(alarmLevelColl);
            final List<ImpArea> areaColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("地理区域"), ImpArea.class);
            NhBaseCache.initArea(areaColl, createUser, createName);
            final List<ImpType> typeColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("设备分类"), ImpType.class);
            NhBaseCache.initType(typeColl, createUser, createName);
            final List<ImpDevice> deviceColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("设备对象"), ImpDevice.class);
            List<ImpLog> logs = NhBaseCache.initDevice(deviceColl, createUser, createName);
            for (ImpLog impLog : logs) {
            	lstErrMsg.add("设备HPID:'"+impLog.getImportNum()+"'"+impLog.getErrorMsg());
			}
            final List<ImpUnifyTag> unifyTagColl = parseExcel(new ByteArrayInputStream(cacheStream.toByteArray()), sheetNames.indexOf("位号表"), ImpUnifyTag.class);
            NhBaseCache.initTag(unifyTagColl);
            NhBaseCache.dispose();
        }catch (Exception e){
            e.printStackTrace();
            lstErrMsg.add("导入失败:"+e.toString());
        }
		return lstErrMsg;
	}
	
	private <T> List<T> parseExcel(InputStream inputStreams,int idx,Class<T> clazz) throws BiffException, IOException{
		if(idx==-1){
			return null;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
        ExcelImportHelper<T> importHelper = new ExcelImportHelper(clazz);
		
		return importHelper.importExcel(inputStreams, new int[]{idx});
	}
	
	private BaseTreeService<Device> getTreeService() {
		BaseTreeService<Device> treeService=new AbstractBaseTreeServiceImpl<Device>() {
    		@Override
    		public String getTableName() {
    			return "NH_DEPT";
    		}

    		@Override
    		public String getNameColumn() {
    			return "NAME";
    		}

    		@Override
    		public String getIdColumn() {
    			return "ID";
    		}

    		@Override
    		public String getPIDColumn() {
    			return "PID";
    		}
    	};
		return treeService;
	}

	@Transactional
	public void rebuildDeviceTree() {
		getTreeService().reBuildTree(0L, 1L);
	}
}
