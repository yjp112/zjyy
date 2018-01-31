package com.supconit.base.services.impl.pojos;

import com.supconit.common.domains.TreeNode;
import com.supconit.honeycomb.business.authorization.entities.User;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


public class BaseCache {
    private static JdbcTemplate jdbcTemplate;
    private static BaseCache elementCache;
    private static Map<String, Long> levelMapByName = new HashMap<String, Long>();
    private static Map<Long, String> levelMapByID = new HashMap<Long, String>();
    private static Map<String, ImpArea> areaMapByCode = new HashMap<String, ImpArea>();
    private static Map<Long, ImpArea> areaMapById = new HashMap<Long, ImpArea>();
    private static Map<String, ImpType> typeMapByCode = new HashMap<String, ImpType>();
    private static Map<Long, ImpType> typeMapById = new HashMap<Long, ImpType>();
    private static Map<String, ImpUnifyTag> unifyMapByName = new HashMap<String, ImpUnifyTag>();
    private static Map<Long, ImpUnifyTag> unifyMapById = new HashMap<Long, ImpUnifyTag>();
    private static Map<String, ImpDevice> deviceMapByHpid = new HashMap<String, ImpDevice>();
    private static Map<Long, ImpDevice> deviceMapById = new HashMap<Long, ImpDevice>();

    public static BaseCache getInstance(JdbcTemplate jdbcTemplatetmp) {
        if (elementCache == null) {
            synchronized (BaseCache.class) {
                if (elementCache == null) {
                    elementCache = new BaseCache();
                }
                if (jdbcTemplate == null) {
                    jdbcTemplate = jdbcTemplatetmp;
                }
            }
        }
        return elementCache;
    }


    public static Map<String, ImpUnifyTag> getUnifyMapByName() {
        return unifyMapByName;
    }

    public static void setUnifyMapByName(Map<String, ImpUnifyTag> unifyMapByName) {
        BaseCache.unifyMapByName = unifyMapByName;
    }

    public static Map<Long, ImpUnifyTag> getUnifyMapById() {
        return unifyMapById;
    }

    public static void setUnifyMapById(Map<Long, ImpUnifyTag> unifyMapById) {
        BaseCache.unifyMapById = unifyMapById;
    }

    public static Map<String, Long> getLevelMapByName() {
        return levelMapByName;
    }

    public static void setLevelMapByName(Map<String, Long> levelMapByName) {
        BaseCache.levelMapByName = levelMapByName;
    }

    public static Map<Long, String> getLevelMapByID() {
        return levelMapByID;
    }

    public static void setLevelMapByID(Map<Long, String> levelMapByID) {
        BaseCache.levelMapByID = levelMapByID;
    }

    public static Map<Long, ImpArea> getAreaMapById() {
        return areaMapById;
    }

    public static void setAreaMapById(Map<Long, ImpArea> areaMapById) {
        BaseCache.areaMapById = areaMapById;
    }

    public static Map<String, ImpArea> getAreaMapByCode() {
        return areaMapByCode;
    }

    public static void setAreaMapByCode(Map<String, ImpArea> areaMapByCode) {
        BaseCache.areaMapByCode = areaMapByCode;
    }

    public static Map<String, ImpType> getTypeMapByCode() {
        return typeMapByCode;
    }

    public static void setTypeMapByCode(Map<String, ImpType> typeMapByCode) {
        BaseCache.typeMapByCode = typeMapByCode;
    }

    public static Map<Long, ImpType> getTypeMapById() {
        return typeMapById;
    }

    public static void setTypeMapById(Map<Long, ImpType> typeMapById) {
        BaseCache.typeMapById = typeMapById;
    }

    public static Map<String, ImpDevice> getDeviceMapByHpid() {
        return deviceMapByHpid;
    }

    public static void setDeviceMapByHpid(Map<String, ImpDevice> deviceMapByHpid) {
        BaseCache.deviceMapByHpid = deviceMapByHpid;
    }

    public static Map<Long, ImpDevice> getDeviceMapById() {
        return deviceMapById;
    }

    public static void setDeviceMapById(Map<Long, ImpDevice> deviceMapById) {
        BaseCache.deviceMapById = deviceMapById;
    }

    public static void initAlarmLevem(List<ImpAlarmLevel> excelAlarmLevel) throws  Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        System.out.println("----------报警级别开始导入-------" +sdf.format( new Date()));
        final List<ImpAlarmLevel> saveForjdbc = excelAlarmLevel;
        String dbName = "";
        try {
            DatabaseMetaData md = jdbcTemplate.getDataSource().getConnection().getMetaData();
            dbName = md.getDatabaseProductName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "";
        if ("Oracle".equals(dbName)) {
            sql = "merge into   A_ALARMLEVEL a  using (select ? as ALARMLEVEL,? as LEVELREMARK ,? as ALARMRANK  from dual)b" +
                    " on (a.ALARMLEVEL=b.ALARMLEVEL)"
                    + " when not matched then insert(ID, ALARMLEVEL, LEVELREMARK, ALARMRANK) values(SEQ_A_ALARMLEVEL.NEXTVAL ,b.ALARMLEVEL,b.LEVELREMARK,b.ALARMRANK)" +
                    " WHEN MATCHED THEN UPDATE set LEVELREMARK =b.LEVELREMARK ,ALARMRANK =b.ALARMRANK";
        }else{
            sql = "merge into   A_ALARMLEVEL a  using (select ? as ALARMLEVEL,? as LEVELREMARK ,? as ALARMRANK  )b" +
                    " on (a.ALARMLEVEL=b.ALARMLEVEL)"
                    + " when not matched then insert( ALARMLEVEL, LEVELREMARK, ALARMRANK) values(b.ALARMLEVEL,b.LEVELREMARK,b.ALARMRANK)" +
                    " WHEN MATCHED THEN UPDATE set LEVELREMARK =b.LEVELREMARK ,ALARMRANK =b.ALARMRANK;";
        }
        if (excelAlarmLevel != null) {
            jdbcTemplate.batchUpdate(sql, new
                    BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, saveForjdbc.get(i).getAlarmLevel());
                            ps.setString(2, saveForjdbc.get(i).getLevelRemark());
                            ps.setInt(3, Integer.parseInt(saveForjdbc.get(i).getAlarmRank()));
                        }

                        public int getBatchSize() {
                            return saveForjdbc.size();
                        }
                    });
        }
        List<ImpAlarmLevel> nowAlarms = jdbcTemplate.query("select a.id as id,a.ALARMLEVEL as alarmLevel from A_ALARMLEVEL  a",
                new RowMapper<ImpAlarmLevel>() {
                    public ImpAlarmLevel mapRow(ResultSet arg0, int arg1) throws SQLException {
                        ImpAlarmLevel t = new ImpAlarmLevel();
                        t.setId(arg0.getLong("id"));
                        t.setAlarmLevel(arg0.getString("alarmLevel"));
                        return t;
                    }
                }
        );

        for (ImpAlarmLevel impAlarmLevel : nowAlarms) {
            BaseCache.levelMapByName.put(impAlarmLevel.getAlarmLevel(), impAlarmLevel.getId());
            BaseCache.levelMapByID.put(impAlarmLevel.getId(), impAlarmLevel.getAlarmLevel());
        }
        System.out.println("----------报警级别导入完成-------" +sdf.format( new Date()));
    }

    public static void initArea(List<ImpArea> excelArea, User user, String userName)throws  Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        System.out.println("----------区域开始导入-------" +sdf.format( new Date()));
        final List<ImpArea> saveForjdbc = excelArea;
        final User createUser = user;
        final String suserName = userName;
        String dbName = "";
        try {
            DatabaseMetaData md = jdbcTemplate.getDataSource().getConnection().getMetaData();
            dbName = md.getDatabaseProductName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "";
        if ("Oracle".equals(dbName)) {
            ////AREA_TYPE默认为1表示园区
            sql = "merge into   GEO_AREA  a  using (select ? as AREA_NAME,? as AREA_CODE ,? as REMARK ,? as SORT,? as FULL_LEVEL_NAME,? as CREATE_ID ,? as  CREATOR ,? as CREATE_DATE from dual)b" +
                    " on (a.AREA_NAME=b.AREA_NAME)"
                    + " when not matched then insert(ID, AREA_NAME, AREA_CODE, REMARK,SORT,PARENT_ID,FULL_LEVEL_NAME,CREATE_ID,CREATOR,CREATE_DATE,AREA_TYPE) values(SEQ_GEO_AREA.NEXTVAL ,b.AREA_NAME,b.AREA_CODE,b.REMARK,b.SORT,0,b.FULL_LEVEL_NAME,b.CREATE_ID,b.CREATOR,b.CREATE_DATE,1)" +
                    " WHEN MATCHED THEN UPDATE set AREA_CODE =b.AREA_CODE ,REMARK =b.REMARK,SORT=b.SORT,UPDATE_ID=b.CREATE_ID,UPDATOR =b.CREATOR,UPDATE_DATE=b.CREATE_DATE";
        }else{
            sql = "merge into   GEO_AREA  a  using (select ? as AREA_NAME,? as AREA_CODE ,? as REMARK ,? as SORT,? as FULL_LEVEL_NAME,? as CREATE_ID ,? as  CREATOR ,? as CREATE_DATE )b" +
                    " on (a.AREA_NAME=b.AREA_NAME)"
                    + " when not matched then insert( AREA_NAME, AREA_CODE, REMARK,SORT,PARENT_ID,FULL_LEVEL_NAME,CREATE_ID,CREATOR,CREATE_DATE,AREA_TYPE) values(b.AREA_NAME,b.AREA_CODE,b.REMARK,b.SORT,0,b.FULL_LEVEL_NAME,b.CREATE_ID,b.CREATOR,b.CREATE_DATE,1)" +
                    " WHEN MATCHED THEN UPDATE set AREA_CODE =b.AREA_CODE ,REMARK =b.REMARK,SORT=b.SORT,UPDATE_ID=b.CREATE_ID,UPDATOR =b.CREATOR,UPDATE_DATE=b.CREATE_DATE;";
        }
        if (excelArea != null) {
            jdbcTemplate.batchUpdate(sql, new
                    BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, saveForjdbc.get(i).getAreaName());
                            ps.setString(2, saveForjdbc.get(i).getAreaCode());
                            ps.setString(3, saveForjdbc.get(i).getAreaRemark());
                            Integer sort = 0;
                            if (saveForjdbc.get(i).getNum() != null) {
                                sort = Integer.parseInt(saveForjdbc.get(i).getNum());
                            }
                            ps.setInt(4, sort);
                            ps.setString(5, "11");
                            ps.setLong(6, createUser.getPersonId());
                            ps.setString(7, suserName);
                            ps.setObject(8, new java.sql.Timestamp(new Date().getTime()));
                        }

                        public int getBatchSize() {
                            return saveForjdbc.size();
                        }
                    });
        }
        List<ImpArea> nowAreas = jdbcTemplate.query("select ID as id, AREA_NAME as areaName,PARENT_ID as parentId ,AREA_CODE as areaCode  from GEO_AREA ",
                new RowMapper<ImpArea>() {
                    public ImpArea mapRow(ResultSet arg0, int arg1) throws SQLException {
                        ImpArea t = new ImpArea();
                        t.setId(arg0.getLong("id"));
                        t.setAreaName(arg0.getString("areaName"));
                        t.setParentAreaId(arg0.getLong("parentId"));
                        t.setAreaCode(arg0.getString("areaCode"));
                        return t;
                    }
                }
        );
        for (ImpArea impArea : nowAreas) {
            BaseCache.areaMapByCode.put(impArea.getAreaCode(), impArea);
            BaseCache.areaMapById.put(impArea.getId(), impArea);
        }
        if (excelArea != null) {
            for (ImpArea impArea : excelArea) {
                impArea.setId(BaseCache.areaMapByCode.get(impArea.getAreaCode()).getId());
                if (impArea.getParentAreaName() != null && !"".equals(impArea.getParentAreaName())) {
                    impArea.setParentAreaId(BaseCache.areaMapByCode.get(impArea.getParentAreaName()).getId());
                    BaseCache.areaMapByCode.get(impArea.getAreaCode()).setParentAreaId(BaseCache.areaMapByCode.get(impArea.getParentAreaName()).getId());
                    impArea.setParentAreaName(getFullName(impArea, impArea.getAreaName()));
                    BaseCache.areaMapByCode.get(impArea.getAreaCode()).setParentAreaName(impArea.getParentAreaName());
                } else {
                    impArea.setParentAreaName(impArea.getAreaName());
                    impArea.setParentAreaId(0);
                }
            }

            final List<ImpArea> updateForjdbc = excelArea;
            if ("Oracle".equals(dbName)) {
                sql = "merge into   GEO_AREA  a  using (select ? as ID,? as PARENT_ID,? as FULL_LEVEL_NAME  from dual)b" +
                        " on (a.ID=b.ID)" +
                        " WHEN MATCHED THEN UPDATE set PARENT_ID =b.PARENT_ID ,FULL_LEVEL_NAME=b.FULL_LEVEL_NAME";
            }else{
                sql = "merge into   GEO_AREA  a  using (select ? as ID,? as PARENT_ID,? as FULL_LEVEL_NAME )b" +
                        " on (a.ID=b.ID)" +
                        " WHEN MATCHED THEN UPDATE set PARENT_ID =b.PARENT_ID ,FULL_LEVEL_NAME=b.FULL_LEVEL_NAME;";
            }
            jdbcTemplate.batchUpdate(sql, new
                    BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, updateForjdbc.get(i).getId());
                            ps.setLong(2, updateForjdbc.get(i).getParentAreaId());
                            ps.setString(3, updateForjdbc.get(i).getParentAreaName());
                        }

                        public int getBatchSize() {
                            return updateForjdbc.size();
                        }
                    });
        }
        System.out.println("----------区域导入完成-------" +sdf.format( new Date()));

    }

    public static void initType(List<ImpType> excelType, User user, String userName) throws  Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        System.out.println("----------设备类别开始导入-------" +sdf.format( new Date()));

        final User createUser = user;
        final String suserName = userName;
        String dbName = "";
        try {
            DatabaseMetaData md = jdbcTemplate.getDataSource().getConnection().getMetaData();
            dbName = md.getDatabaseProductName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "";
        if ("Oracle".equals(dbName)) {
            sql = "merge into   DEVICE_CATEGORY  a  using (select ? as CATEGORY_NAME,? as CATEGORY_CODE ,? as REMARK ,? as SORT_INDEX,? as CREATE_ID ,? as  CREATOR ,? as CREATE_DATE from dual)b" +
                    " on (a.CATEGORY_NAME=b.CATEGORY_NAME)"
                    + " when not matched then insert(ID, CATEGORY_NAME, CATEGORY_CODE, REMARK,SORT_INDEX,PARENT_ID,CREATE_ID,CREATOR,CREATE_DATE,LAST_NODE) values(SEQ_DEVICE_CATEGORY.NEXTVAL ,b.CATEGORY_NAME,b.CATEGORY_CODE,b.REMARK,b.SORT_INDEX,0,b.CREATE_ID,b.CREATOR,b.CREATE_DATE,1)" +
                    " WHEN MATCHED THEN UPDATE set CATEGORY_CODE =b.CATEGORY_CODE ,REMARK =b.REMARK,SORT_INDEX=b.SORT_INDEX,UPDATE_ID=b.CREATE_ID,UPDATOR =b.CREATOR,UPDATE_DATE=b.CREATE_DATE";
        }else{
            sql = "merge into   DEVICE_CATEGORY  a  using (select ? as CATEGORY_NAME,? as CATEGORY_CODE ,? as REMARK ,? as SORT_INDEX,? as CREATE_ID ,? as  CREATOR ,? as CREATE_DATE)b" +
                    " on (a.CATEGORY_NAME=b.CATEGORY_NAME)"
                    + " when not matched then insert(CATEGORY_NAME, CATEGORY_CODE, REMARK,SORT_INDEX,PARENT_ID,CREATE_ID,CREATOR,CREATE_DATE,LAST_NODE) values(b.CATEGORY_NAME,b.CATEGORY_CODE,b.REMARK,b.SORT_INDEX,0,b.CREATE_ID,b.CREATOR,b.CREATE_DATE,1)" +
                    " WHEN MATCHED THEN UPDATE set CATEGORY_CODE =b.CATEGORY_CODE ,REMARK =b.REMARK,SORT_INDEX=b.SORT_INDEX,UPDATE_ID=b.CREATE_ID,UPDATOR =b.CREATOR,UPDATE_DATE=b.CREATE_DATE;";
        }
        if (excelType != null) {
            int num  = excelType.size()/1000+ (excelType.size()%1000>0?1:0);
            for(int i=0;i<num;i++ ) {
                final List<ImpType> saveForjdbc = excelType.subList(i*1000, (excelType.size()-i*1000)/1000>0?(i+1)*1000:(excelType.size()-i*1000));
                jdbcTemplate.batchUpdate(sql, new
                        BatchPreparedStatementSetter() {
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setString(1, saveForjdbc.get(i).getTypeName());
                                ps.setString(2, saveForjdbc.get(i).getTypeCode());
                                ps.setString(3, saveForjdbc.get(i).getTypeRemark());
                                Integer sort = 0;
                                if (saveForjdbc.get(i).getNum() != null) {
                                    sort = Integer.parseInt(saveForjdbc.get(i).getNum());
                                }
                                ps.setInt(4, sort);
                                ps.setLong(5, createUser.getPersonId());
                                ps.setString(6, suserName);
                                ps.setObject(7, new java.sql.Timestamp(new Date().getTime()));
                            }

                            public int getBatchSize() {
                                return saveForjdbc.size();
                            }
                        });
            }
        }
        List<ImpType> nowAreas = jdbcTemplate.query("select ID as id, CATEGORY_NAME as categoryName,PARENT_ID as parentId ,CATEGORY_CODE as categorCode from DEVICE_CATEGORY ",
                new RowMapper<ImpType>() {
                    public ImpType mapRow(ResultSet arg0, int arg1) throws SQLException {
                        ImpType t = new ImpType();
                        t.setId(arg0.getLong("id"));
                        t.setTypeName(arg0.getString("categoryName"));
                        t.setParentTypeId(arg0.getLong("parentId"));
                        t.setTypeCode(arg0.getString("categorCode"));
                        return t;
                    }
                }
        );
        for (ImpType impType : nowAreas) {
            BaseCache.typeMapByCode.put(impType.getTypeCode(), impType);
            BaseCache.typeMapById.put(impType.getId(), impType);
        }
        if (excelType != null) {
            for (ImpType impType : excelType) {
                impType.setId(BaseCache.typeMapByCode.get(impType.getTypeCode()).getId());
                if (impType.getParentTypeName() != null && !"".equals(impType.getParentTypeName())) {
                    impType.setParentTypeId(BaseCache.typeMapByCode.get(impType.getParentTypeName()).getId());
                } else {
                    impType.setParentTypeId(0);
                }
            }

            final List<ImpType> updateForjdbc = excelType;
            if ("Oracle".equals(dbName)) {
                sql = "merge into   DEVICE_CATEGORY  a  using (select ? as ID,? as PARENT_ID  from dual)b" +
                        " on (a.ID=b.ID)" +
                        " WHEN MATCHED THEN UPDATE set PARENT_ID =b.PARENT_ID";
            }else{
                sql = "merge into   DEVICE_CATEGORY  a  using (select ? as ID,? as PARENT_ID )b" +
                        " on (a.ID=b.ID)" +
                        " WHEN MATCHED THEN UPDATE set PARENT_ID =b.PARENT_ID;";
            }
            jdbcTemplate.batchUpdate(sql, new
                    BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, updateForjdbc.get(i).getId());
                            ps.setLong(2, updateForjdbc.get(i).getParentTypeId());
                        }

                        public int getBatchSize() {
                            return updateForjdbc.size();
                        }
                    });
        }
        System.out.println("----------设备类别导入完成-------" +sdf.format( new Date()));

    }


    public static List<ImpLog> initDevice(List<ImpDevice> excelDevice, User user, String userName)throws  Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        System.out.println("----------设备开始导入-------" +sdf.format( new Date()));
        List<ImpLog> impLogs = new ArrayList<>();

        final User createUser = user;
        final String suserName = userName;
        String dbName = "";
        try {
            DatabaseMetaData md = jdbcTemplate.getDataSource().getConnection().getMetaData();
            dbName = md.getDatabaseProductName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "";
        if ("Oracle".equals(dbName)) {
            sql = "merge into   DEVICE  a  using (select ? as HPID, ? as DEVICE_NAME,? as DEVICE_CODE ,? as DEVICE_SPEC ,? as ASSETS_CODE,? as BARCODE,? as MANAGE_PERSON_IDS ," +
                    " ? as  MANAGE_PERSON_NAME ,? as  USE_DEPARTMENT_ID ,? as  MANAGE_DEPARTMENT_ID ,? as G_SYSTEM_RULE_ID,? as MAP_DISPLAY,? as THREE_DIM_DISPLAY,? as SORT_IDX,? as CREATE_ID ," +
                    "? as  CREATOR ,? as CREATE_DATE ,? as CATEGORY_ID ,? as LOCATION_ID,? as LOCATION_NAME from dual)b" +
                    " on (a.HPID=b.HPID)" +
                    " when not matched then insert(ID,HPID, DEVICE_NAME, DEVICE_CODE, DEVICE_SPEC,ASSETS_CODE,BARCODE,MANAGE_PERSON_IDS,MANAGE_PERSON_NAME,USE_DEPARTMENT_ID,MANAGE_DEPARTMENT_ID,G_SYSTEM_RULE_ID,MAP_DISPLAY,THREE_DIM_DISPLAY,SORT_IDX,PARENT_ID,CREATE_ID,CREATOR,CREATE_DATE,CATEGORY_ID,LOCATION_ID,LOCATION_NAME,STATUS)" +
                    "           values(SEQ_DEVICE.NEXTVAL ,b.HPID,b.DEVICE_NAME,b.DEVICE_CODE,b.DEVICE_SPEC,b.ASSETS_CODE,b.BARCODE,b.MANAGE_PERSON_IDS,b.MANAGE_PERSON_NAME,b.USE_DEPARTMENT_ID,b.MANAGE_DEPARTMENT_ID,b.G_SYSTEM_RULE_ID,b.MAP_DISPLAY,b.THREE_DIM_DISPLAY,b.SORT_IDX,0,b.CREATE_ID,b.CREATOR,b.CREATE_DATE,b.CATEGORY_ID,b.LOCATION_ID,b.LOCATION_NAME,0)" +
                    " WHEN MATCHED THEN UPDATE set DEVICE_NAME =b.DEVICE_NAME ,DEVICE_CODE =b.DEVICE_CODE,DEVICE_SPEC=b.DEVICE_SPEC," +
                    " ASSETS_CODE =b.ASSETS_CODE ,BARCODE =b.BARCODE,MANAGE_PERSON_IDS=b.MANAGE_PERSON_IDS,MANAGE_PERSON_NAME=b.MANAGE_PERSON_NAME,USE_DEPARTMENT_ID=b.USE_DEPARTMENT_ID,MANAGE_DEPARTMENT_ID=b.MANAGE_DEPARTMENT_ID,G_SYSTEM_RULE_ID=b.G_SYSTEM_RULE_ID,MAP_DISPLAY=b.MAP_DISPLAY," +
                    " THREE_DIM_DISPLAY=b.THREE_DIM_DISPLAY,SORT_IDX=b.SORT_IDX,CATEGORY_ID=b.CATEGORY_ID,LOCATION_ID=b.LOCATION_ID,LOCATION_NAME=b.LOCATION_NAME,UPDATE_ID=b.CREATE_ID,UPDATOR =b.CREATOR,UPDATE_DATE=b.CREATE_DATE";
        }else{
            sql = "merge into   DEVICE  a  using (select ? as HPID, ? as DEVICE_NAME,? as DEVICE_CODE ,? as DEVICE_SPEC ,? as ASSETS_CODE,? as BARCODE,? as MANAGE_PERSON_IDS ," +
                    " ? as  MANAGE_PERSON_NAME ,? as  USE_DEPARTMENT_ID ,? as  MANAGE_DEPARTMENT_ID ,? as G_SYSTEM_RULE_ID,? as MAP_DISPLAY,? as THREE_DIM_DISPLAY,? as SORT_IDX,? as CREATE_ID ," +
                    "? as  CREATOR ,? as CREATE_DATE ,? as CATEGORY_ID ,? as LOCATION_ID,? as LOCATION_NAME )b" +
                    " on (a.HPID=b.HPID)" +
                    " when not matched then insert(HPID, DEVICE_NAME, DEVICE_CODE, DEVICE_SPEC,ASSETS_CODE,BARCODE,MANAGE_PERSON_IDS,MANAGE_PERSON_NAME,USE_DEPARTMENT_ID,MANAGE_DEPARTMENT_ID,G_SYSTEM_RULE_ID,MAP_DISPLAY,THREE_DIM_DISPLAY,SORT_IDX,PARENT_ID,CREATE_ID,CREATOR,CREATE_DATE,CATEGORY_ID,LOCATION_ID,LOCATION_NAME,STATUS)" +
                    "           values(b.HPID,b.DEVICE_NAME,b.DEVICE_CODE,b.DEVICE_SPEC,b.ASSETS_CODE,b.BARCODE,b.MANAGE_PERSON_IDS,b.MANAGE_PERSON_NAME,b.USE_DEPARTMENT_ID,b.MANAGE_DEPARTMENT_ID,b.G_SYSTEM_RULE_ID,b.MAP_DISPLAY,b.THREE_DIM_DISPLAY,b.SORT_IDX,0,b.CREATE_ID,b.CREATOR,b.CREATE_DATE,b.CATEGORY_ID,b.LOCATION_ID,b.LOCATION_NAME,0)" +
                    " WHEN MATCHED THEN UPDATE set DEVICE_NAME =b.DEVICE_NAME ,DEVICE_CODE =b.DEVICE_CODE,DEVICE_SPEC=b.DEVICE_SPEC," +
                    " ASSETS_CODE =b.ASSETS_CODE ,BARCODE =b.BARCODE,MANAGE_PERSON_IDS=b.MANAGE_PERSON_IDS,MANAGE_PERSON_NAME=b.MANAGE_PERSON_NAME,USE_DEPARTMENT_ID=b.USE_DEPARTMENT_ID,MANAGE_DEPARTMENT_ID=b.MANAGE_DEPARTMENT_ID,G_SYSTEM_RULE_ID=b.G_SYSTEM_RULE_ID,MAP_DISPLAY=b.MAP_DISPLAY," +
                    " THREE_DIM_DISPLAY=b.THREE_DIM_DISPLAY,SORT_IDX=b.SORT_IDX,CATEGORY_ID=b.CATEGORY_ID,LOCATION_ID=b.LOCATION_ID,LOCATION_NAME=b.LOCATION_NAME,UPDATE_ID=b.CREATE_ID,UPDATOR =b.CREATOR,UPDATE_DATE=b.CREATE_DATE;";
        }
        List<ImpDevice> saveNeed = new ArrayList<>();
        for (ImpDevice impDevice : excelDevice) {
            if(BaseCache.areaMapByCode.containsKey(impDevice.getLocationName())) {
                impDevice.setLocationId(BaseCache.areaMapByCode.get(impDevice.getLocationName()).getId());
                impDevice.setLocationName(BaseCache.areaMapByCode.get(impDevice.getLocationName()).getParentAreaName());
                if(BaseCache.typeMapByCode.containsKey(impDevice.getCategoryName())) {
                    impDevice.setCategoryId(BaseCache.typeMapByCode.get(impDevice.getCategoryName()).getId());
                    impDevice.setMapDisplay("是".equals(impDevice.getMapDisplayName()) ? 1 : 0);
                    impDevice.setThreeDimDisplay("是".equals(impDevice.getThreeDimDisplayName()) ? 1 : 0);
                    saveNeed.add(impDevice);
                }else{
                    impLogs.add(new ImpLog(impDevice.getHpid(), "类别不存在"));
                }
            }else{
                impLogs.add(new ImpLog(impDevice.getHpid(), "地理位置不存在"));
            }
        }

        final List<ImpDevice> saveForjdbc = saveNeed;
        if (excelDevice != null) {
            jdbcTemplate.batchUpdate(sql, new
                    BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, saveForjdbc.get(i).getHpid());
                            ps.setString(2, saveForjdbc.get(i).getDeviceName());
                            ps.setString(3, saveForjdbc.get(i).getDeviceCode());
                            ps.setString(4, saveForjdbc.get(i).getDeviceSpec());
                            ps.setString(5, saveForjdbc.get(i).getAssetsCode());
                            ps.setString(6, saveForjdbc.get(i).getBarcode());
                            ps.setString(7, saveForjdbc.get(i).getManagePersonIds());
                            ps.setString(8, saveForjdbc.get(i).getManagePersonName());
                            ps.setObject(9, saveForjdbc.get(i).getUseDepartmentId());
                            ps.setObject(10, saveForjdbc.get(i).getManageDepartmentId());
                            if (saveForjdbc.get(i).getRuleId() != null) {
                                ps.setLong(11, Long.parseLong(saveForjdbc.get(i).getRuleId()));
                            } else {
                                ps.setObject(11, null);
                            }
                            ps.setInt(12, saveForjdbc.get(i).getMapDisplay());
                            ps.setInt(13, saveForjdbc.get(i).getThreeDimDisplay());
                            int sort = 0;
                            if (saveForjdbc.get(i).getSort() != null && !"".equals(saveForjdbc.get(i).getSort())) {
                                sort = Integer.parseInt(saveForjdbc.get(i).getSort());
                            }
                            ps.setInt(14, sort);
                            ps.setLong(15, createUser.getPersonId());
                            ps.setString(16, suserName);
                            ps.setObject(17, new java.sql.Timestamp(new Date().getTime()));
                            ps.setLong(18, saveForjdbc.get(i).getCategoryId());
                            ps.setLong(19, saveForjdbc.get(i).getLocationId());
                            ps.setString(20, saveForjdbc.get(i).getLocationName());
                        }

                        public int getBatchSize() {
                            return saveForjdbc.size();
                        }
                    });
        }
        List<ImpDevice> nowDevices = jdbcTemplate.query("select ID as id, HPID as hpid,PARENT_ID as parentId  from DEVICE ",
                new RowMapper<ImpDevice>() {
                    public ImpDevice mapRow(ResultSet arg0, int arg1) throws SQLException {
                        ImpDevice t = new ImpDevice();
                        t.setId(arg0.getLong("id"));
                        t.setHpid(arg0.getString("hpid"));
                        t.setParentId(arg0.getLong("parentId"));
                        return t;
                    }
                }
        );
        for (ImpDevice impDevice : nowDevices) {
            BaseCache.deviceMapByHpid.put(impDevice.getHpid(), impDevice);
            BaseCache.deviceMapById.put(impDevice.getId(), impDevice);
        }
        if (excelDevice != null) {
            for (ImpDevice impDevice : saveNeed) {
                impDevice.setId(BaseCache.deviceMapByHpid.get(impDevice.getHpid()).getId());
                if (impDevice.getLastHpid() != null && !"".equals(impDevice.getLastHpid())) {
                    impDevice.setParentId(BaseCache.deviceMapByHpid.get(impDevice.getLastHpid()).getId());
                    BaseCache.deviceMapByHpid.get(impDevice.getHpid()).setParentId(BaseCache.deviceMapByHpid.get(impDevice.getLastHpid()).getId());
                } else {
                    impDevice.setLastHpid(impDevice.getHpid());
                    impDevice.setParentId(new Long(0));
                }
            }

            final List<ImpDevice> updateForjdbc = saveNeed;
            if ("Oracle".equals(dbName)) {
                sql = "merge into   DEVICE  a  using (select ? as ID,? as PARENT_ID from dual)b" +
                        " on (a.ID=b.ID)" +
                        " WHEN MATCHED THEN UPDATE set PARENT_ID =b.PARENT_ID ";
            }else{
                sql = "merge into   DEVICE  a  using (select ? as ID,? as PARENT_ID)b" +
                        " on (a.ID=b.ID)" +
                        " WHEN MATCHED THEN UPDATE set PARENT_ID =b.PARENT_ID ;";
            }
            jdbcTemplate.batchUpdate(sql, new
                    BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, updateForjdbc.get(i).getId());
                            ps.setLong(2, updateForjdbc.get(i).getParentId());
                        }

                        public int getBatchSize() {
                            return updateForjdbc.size();
                        }
                    });


            final List<ImpDevice> aa = BuildTree();
            if ("Oracle".equals(dbName)) {
                sql = "merge into   DEVICE  a  using (select ? as ID,? as LFT,? as RGT from dual)b" +
                        " on (a.ID=b.ID)" +
                        " WHEN MATCHED THEN UPDATE set LFT =b.LFT ,RGT =b.RGT";
            }else{
                sql = "merge into   DEVICE  a  using (select ? as ID,? as LFT,? as RGT )b" +
                        " on (a.ID=b.ID)" +
                        " WHEN MATCHED THEN UPDATE set LFT =b.LFT ,RGT =b.RGT;";
            }
            jdbcTemplate.batchUpdate(sql, new
                    BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, aa.get(i).getId());
                            ps.setLong(2, aa.get(i).getLft());
                            ps.setLong(3, aa.get(i).getRgt());
                        }

                        public int getBatchSize() {
                            return aa.size();
                        }
                    });
        }

        BaseCache.areaMapByCode.clear();
        BaseCache.areaMapById.clear();
        BaseCache.typeMapByCode.clear();
        BaseCache.typeMapById.clear();
        BaseCache.deviceMapByHpid.clear();
        BaseCache.deviceMapById.clear();
        System.out.println("----------设备导入完成-------" +sdf.format( new Date()));
        return  impLogs;
    }


    public static  void initTag(List<ImpUnifyTag> excelTag) throws  Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        System.out.println("----------点位开始导入-------" +sdf.format( new Date()));
        List<ImpLog> impLogs = new ArrayList<>();
        final List<ImpUnifyTag> saveForjdbc = excelTag;
        String dbName = "";
        try {
            DatabaseMetaData md = jdbcTemplate.getDataSource().getConnection().getMetaData();
            dbName = md.getDatabaseProductName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "";
        if ("Oracle".equals(dbName)) {
            sql = "merge into   A_DEVICETAG  a  using (select ? as MONITORID, ? as TAGNAME,? as TAGTYPE ,? as TAGDESC,? as ALARMPOINT,? as ISWRITE from dual)b" +
                    " on (a.TAGNAME=b.TAGNAME)" +
                    " when not matched then insert(ID, MONITORID, TAGNAME, TAGTYPE, TAGDESC, ALARMPOINT, ISWRITE)" +
                    "           values(SEQ_A_DEVICETAG.NEXTVAL ,b.MONITORID,b.TAGNAME,b.TAGTYPE,b.TAGDESC,b.ALARMPOINT,b.ISWRITE)" +
                    " WHEN MATCHED THEN UPDATE set MONITORID =b.MONITORID ,TAGTYPE =b.TAGTYPE,TAGDESC =b.TAGDESC,ALARMPOINT =b.ALARMPOINT,ISWRITE =b.ISWRITE";
        }else{
            sql = "merge into   A_DEVICETAG  a  using (select ? as MONITORID, ? as TAGNAME,? as TAGTYPE ,? as TAGDESC,? as ALARMPOINT,? as ISWRITE )b" +
                    " on (a.TAGNAME=b.TAGNAME)" +
                    " when not matched then insert(MONITORID, TAGNAME, TAGTYPE, TAGDESC, ALARMPOINT, ISWRITE)" +
                    "           values(b.MONITORID,b.TAGNAME,b.TAGTYPE,b.TAGDESC,b.ALARMPOINT,b.ISWRITE)" +
                    " WHEN MATCHED THEN UPDATE set MONITORID =b.MONITORID ,TAGTYPE =b.TAGTYPE,TAGDESC =b.TAGDESC,ALARMPOINT =b.ALARMPOINT,ISWRITE =b.ISWRITE;";
        }
        if (excelTag != null) {
            jdbcTemplate.batchUpdate(sql, new
                    BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, saveForjdbc.get(i).getHpid());
                            ps.setString(2, saveForjdbc.get(i).getTagName());
                            ps.setLong(3, saveForjdbc.get(i).getTagTypeId());
                            ps.setString(4, saveForjdbc.get(i).getDescription());
                            ps.setLong(5, saveForjdbc.get(i).getIsAlarmTag());
                            ps.setLong(6, saveForjdbc.get(i).getIsAlarmTag());
                        }

                        public int getBatchSize() {
                            return saveForjdbc.size();
                        }
                    });
        }
        List<ImpUnifyTag> nowTags = jdbcTemplate.query("select ID as id,TAGNAME as tagName  from A_DEVICETAG ",
                new RowMapper<ImpUnifyTag>() {
                    public ImpUnifyTag mapRow(ResultSet arg0, int arg1) throws SQLException {
                        ImpUnifyTag t = new ImpUnifyTag();
                        t.setId(arg0.getLong("id"));
                        t.setTagName(arg0.getString("tagName"));
                        return t;
                    }
                }
        );
        for (ImpUnifyTag impTag : nowTags) {
            BaseCache.unifyMapByName.put(impTag.getTagName(), impTag);
        }
        if (excelTag != null) {
            List<ImpLevelTag> add = new ArrayList<>();
            List<Long> ids = new ArrayList<>();
            for (ImpUnifyTag impUnifyTag : excelTag) {
                Long id = BaseCache.unifyMapByName.get(impUnifyTag.getTagName()).getId();
                if (impUnifyTag.getON() != null && !"".equals(impUnifyTag.getON()) && BaseCache.levelMapByName.containsKey(impUnifyTag.getON())) {
                    add.add(new ImpLevelTag(id, "ON", BaseCache.levelMapByName.get(impUnifyTag.getON())));
                }
                if (impUnifyTag.getOFF() != null && !"".equals(impUnifyTag.getOFF()) && BaseCache.levelMapByName.containsKey(impUnifyTag.getOFF())) {
                    add.add(new ImpLevelTag(id, "OFF", BaseCache.levelMapByName.get(impUnifyTag.getOFF())));
                }
                if (impUnifyTag.getH() != null && !"".equals(impUnifyTag.getH()) && BaseCache.levelMapByName.containsKey(impUnifyTag.getH())) {
                    add.add(new ImpLevelTag(id, "H", BaseCache.levelMapByName.get(impUnifyTag.getH())));
                }
                if (impUnifyTag.getHH() != null && !"".equals(impUnifyTag.getHH()) && BaseCache.levelMapByName.containsKey(impUnifyTag.getHH())) {
                    add.add(new ImpLevelTag(id, "HH", BaseCache.levelMapByName.get(impUnifyTag.getHH())));
                }
                if (impUnifyTag.getL() != null && !"".equals(impUnifyTag.getL()) && BaseCache.levelMapByName.containsKey(impUnifyTag.getL())) {
                    add.add(new ImpLevelTag(id, "L", BaseCache.levelMapByName.get(impUnifyTag.getL())));
                }
                if (impUnifyTag.getLL() != null && !"".equals(impUnifyTag.getLL()) && BaseCache.levelMapByName.containsKey(impUnifyTag.getLL())) {
                    add.add(new ImpLevelTag(id, "LL", BaseCache.levelMapByName.get(impUnifyTag.getLL())));
                }
                if (impUnifyTag.getPRIN() != null && !"".equals(impUnifyTag.getPRIN()) && BaseCache.levelMapByName.containsKey(impUnifyTag.getPRIN())) {
                    add.add(new ImpLevelTag(id, "PRIN", BaseCache.levelMapByName.get(impUnifyTag.getPRIN())));
                }
                if (impUnifyTag.getNRIN() != null && !"".equals(impUnifyTag.getNRIN()) && BaseCache.levelMapByName.containsKey(impUnifyTag.getNRIN())) {
                    add.add(new ImpLevelTag(id, "NRIN", BaseCache.levelMapByName.get(impUnifyTag.getNRIN())));
                }
                ids.add(BaseCache.unifyMapByName.get(impUnifyTag.getTagName()).getId());
            }
            sql = "delete  from    A_DEVICEALARMLEVEL  where TAGID = ? ";
            final   List<Long> delids = ids;
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                        public void setValues(PreparedStatement ps, int i)throws SQLException {
                            ps.setLong(1, delids.get(i));
                        }
                        public int getBatchSize() {
                            return delids.size();
                        }
                    }
            );


            if ("Oracle".equals(dbName)) {
                sql = "insert  into   A_DEVICEALARMLEVEL  (ID, TAGID, ALARMTYPE, ALARMLEVEL)" +
                        "    values(SEQ_A_DEVICEALARMLEVEL.NEXTVAL ,?,?,?)";
            }else{
                sql = "insert  into   A_DEVICEALARMLEVEL  (TAGID, ALARMTYPE, ALARMLEVEL)" +
                        "    values(?,?,?);";
            }

            final List<ImpLevelTag> insertTag = add;
            jdbcTemplate.batchUpdate(sql,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            int idx = 1;
                            ps.setLong(idx++, insertTag.get(i).getTypeId());
                            ps.setString(idx++, insertTag.get(i).getName());
                            ps.setLong(idx++, insertTag.get(i).getValueId());
                        }

                        @Override
                        public int getBatchSize() {
                            return insertTag.size();
                        }
                    }
            );
        }
        System.out.println("----------点位导入完成-------" +sdf.format( new Date()));

    }


    private static String getFullName(ImpArea impArea, String result) {
        if (impArea.getParentAreaId() != 0) {
            String name = BaseCache.areaMapById.get(impArea.getParentAreaId()).getAreaName();
            result = name + "→" + result;
            return getFullName(BaseCache.areaMapById.get(impArea.getParentAreaId()), result);
        }
        return result;
    }

    private static List<ImpDevice> BuildTree() {
        Map<Long, List<ImpDevice>> deviceMap = new HashMap<>();
        List<ImpDevice> nowDevices = jdbcTemplate.query("select ID as id, HPID as hpid,PARENT_ID as parentId  from DEVICE ORDER BY SORT_IDX",
                new RowMapper<ImpDevice>() {
                    public ImpDevice mapRow(ResultSet arg0, int arg1) throws SQLException {
                        ImpDevice t = new ImpDevice();
                        t.setId(arg0.getLong("id"));
                        t.setHpid(arg0.getString("hpid"));
                        t.setParentId(arg0.getLong("parentId"));
                        return t;
                    }
                }
        );
        for (ImpDevice impDevice : nowDevices) {
            if (deviceMap.containsKey(impDevice.getParentId())) {
                deviceMap.get(impDevice.getParentId()).add(impDevice);
            } else {
                List<ImpDevice> impDevices = new ArrayList<ImpDevice>();
                impDevices.add(impDevice);
                deviceMap.put(impDevice.getParentId(), impDevices);
            }
        }
        List<ImpDevice> result = new ArrayList<>();
        reBuildTree(null, 1L, deviceMap, result);
        return result;
    }

    private static long reBuildTree(ImpDevice dev, Long leftNmuber, Map<Long, List<ImpDevice>> deviceMap, List<ImpDevice> result) {
        Long rightNumber = leftNmuber + 1;
        List<ImpDevice> devices;
        if (dev != null) {
            devices = deviceMap.get(dev.getId());
        } else {
            devices = deviceMap.get(0L);
        }
        if (devices != null) {
            for (ImpDevice impDevice : devices) {
                rightNumber = reBuildTree(impDevice, rightNumber, deviceMap, result);
            }
        }
        if (dev != null) {
            dev.setLft(leftNmuber);
            dev.setRgt(rightNumber);
            result.add(dev);
        }

        return rightNumber + 1;
    }

    public static void dispose(){
        BaseCache.levelMapByName.clear();
        BaseCache.levelMapByID.clear();
        BaseCache.areaMapByCode.clear();
        BaseCache.areaMapById.clear();
        BaseCache.typeMapByCode.clear();
        BaseCache.typeMapById.clear();
        BaseCache.unifyMapByName.clear();
        BaseCache.unifyMapById.clear();
        BaseCache.deviceMapByHpid.clear();
        BaseCache.deviceMapById.clear();
    }
}

