package com.supconit.nhgl.evaluate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.nhgl.evaluate.dao.EvaluateInfoDao;
import com.supconit.nhgl.evaluate.entities.EvaluateInfo;
import com.supconit.nhgl.evaluate.entities.MonitorObjScore;

import hc.orm.AbstractBasicDaoImpl;

/**
 * Created by zhangzhaoyun on 14-6-23.
 */
@Repository
public class EvaluateInfoDaoImpl extends AbstractBasicDaoImpl<EvaluateInfo,Long> implements EvaluateInfoDao {
    @Override
    protected String getNamespace() {
        return EvaluateInfo.class.getName();
    }

    @Override
    public List<EvaluateInfo> queryEvaluateInfosWithDate(String dateStr) {
        return selectList("queryEvaluateInfosWithDate",dateStr);
    }

    @Override
    public List<MonitorObjScore> queryMonitorObjScoreByTaskCode(String taskCode, String date) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("taskCode",taskCode);
        map.put("today",date);
        return selectList("queryMonitorObjScoreByTaskCode",map);
    }
}
