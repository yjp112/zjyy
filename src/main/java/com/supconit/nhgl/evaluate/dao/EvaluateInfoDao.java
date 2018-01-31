package com.supconit.nhgl.evaluate.dao;

import java.util.List;

import com.supconit.nhgl.evaluate.entities.EvaluateInfo;
import com.supconit.nhgl.evaluate.entities.MonitorObjScore;

import hc.orm.BasicDao;

/**
 * Created by zhangzhaoyun on 14-6-23.
 */
public interface EvaluateInfoDao extends BasicDao<EvaluateInfo, Long> {

    List<EvaluateInfo> queryEvaluateInfosWithDate(String dateStr);

    List<MonitorObjScore> queryMonitorObjScoreByTaskCode(String taskCode, String date);

}
