package com.supconit.nhgl.evaluate.service;

import java.util.List;
import java.util.Map;

import com.supconit.nhgl.evaluate.entities.MonitorObjScore;

/**
 * Created by zhangzhaoyun on 14-6-23.
 */
public interface EvaluateInfoService {
    Map showEvaluateResult();

    List<MonitorObjScore> showEvaluateDetail(String taskCode, String date);

    Map showDemoEvaluateResult();
}
