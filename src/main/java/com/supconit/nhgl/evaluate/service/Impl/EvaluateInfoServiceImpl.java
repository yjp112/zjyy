package com.supconit.nhgl.evaluate.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.nhgl.evaluate.dao.EvaluateInfoDao;
import com.supconit.nhgl.evaluate.entities.EvaluateInfo;
import com.supconit.nhgl.evaluate.entities.MonitorObjScore;
import com.supconit.nhgl.evaluate.service.EvaluateInfoService;
import com.supconit.nhgl.schedule.dao.CriteriaDetailDao;
import com.supconit.nhgl.schedule.entites.CriteriaDetail;
import com.supconit.nhgl.utils.TaskTypeEnum;

import jodd.datetime.JDateTime;

/**
 * Created by zhangzhaoyun on 14-6-23.
 */
@Service
public class EvaluateInfoServiceImpl implements EvaluateInfoService {
    @Autowired
    private EvaluateInfoDao evaluateInfoDao;
    @Autowired
    private CriteriaDetailDao criteriaDetailDao;

    @Override
    @Transactional(readOnly = true)
    public Map showEvaluateResult() {
        JDateTime jdt = new JDateTime();
        jdt.addDay(-1);
        String yeastday = jdt.toString("YYYY-MM-DD");
        List<EvaluateInfo> evaluateInfoList = evaluateInfoDao.queryEvaluateInfosWithDate(yeastday);

        Map map = new HashMap();

        int totalResult = 100;

        int deviceResult = 0;
        int energyResult = 0;
        CriteriaDetail condition=new CriteriaDetail();
        CriteriaDetail criteriaDetail=null;
        List<EvaluateInfo> deviceEvaluateInfoList = new ArrayList<EvaluateInfo>();
        List<EvaluateInfo> energyEvaluateInfoList = new ArrayList<EvaluateInfo>();

        for (EvaluateInfo info : evaluateInfoList) {
            totalResult += info.getScore();
            condition.setCrScore(new BigDecimal(info.getScore()));
            condition.setTaskCode(info.getTaskCode());
            if (info.getTaskVesting() == TaskTypeEnum.DEVICE_STATE.getNo()) {
                deviceResult += info.getScore();
                //获取设备监控任务的细节 移动至其他方法中，异步加载
                //List<MonitorObjScore> objScores = evaluateInfoDao.queryMonitorObjScoreByTaskCode(info.getTaskCode(),yesterday);
                //info.setObjScores(objScores);
                info.setCrLevel(1l);
                deviceEvaluateInfoList.add(info);
            } else {
                criteriaDetail=criteriaDetailDao.selectByTaskCodeAndScore(condition);
                info.setCrLevel(criteriaDetail.getCrLevel());
                energyEvaluateInfoList.add(info);
                energyResult += info.getScore();
            }

        }

        map.put("TOTAL_RESULT_SCORE", totalResult);
        map.put("ERROR_NUM",deviceEvaluateInfoList.size()+energyEvaluateInfoList.size());

        map.put("DEVICE_RESULT", deviceResult);
        map.put("DEVICE_DETAIL", deviceEvaluateInfoList);

        map.put("ENERGY_RESULT", energyResult);
        map.put("ENERGY_DETAIL", energyEvaluateInfoList);

        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonitorObjScore> showEvaluateDetail(String taskCode, String date) {
        return evaluateInfoDao.queryMonitorObjScoreByTaskCode(taskCode, date);
    }


    @Override
    public Map showDemoEvaluateResult() {
        Map map = new HashMap();
        List<EvaluateInfo> energyEvaluateInfoList = EvaluateInfo.generateDemoInfo(5, 2, 0);
        List<EvaluateInfo> deviceEvaluateInfoList = EvaluateInfo.generateDemoInfo(2, 1, 4);
        int totalResult = 100;

        int deviceResult = 0;
        int energyResult = 0;

        for (EvaluateInfo info : deviceEvaluateInfoList) {
            totalResult += info.getScore();

            deviceResult += info.getScore();
            //获取设备监控任务的细节 移动至其他方法中，异步加载
            //List<MonitorObjScore> objScores = evaluateInfoDao.queryMonitorObjScoreByTaskCode(info.getTaskCode(),yesterday);
            //info.setObjScores(objScores);
            System.out.print(info.getScore() + "|");
        }
        System.out.println("\t"+totalResult+"|"+deviceResult);
        for (EvaluateInfo info : energyEvaluateInfoList) {
            totalResult += info.getScore();


            energyResult += info.getScore();
            System.out.print(info.getScore()+"|");
        }

        System.out.println("\t"+totalResult+"|"+energyResult);
        map.put("TOTAL_RESULT_SCORE", totalResult);
        map.put("ERROR_NUM",deviceEvaluateInfoList.size()+energyEvaluateInfoList.size());

        map.put("DEVICE_RESULT", deviceResult);
        map.put("DEVICE_DETAIL", deviceEvaluateInfoList);

        map.put("ENERGY_RESULT", energyResult);
        map.put("ENERGY_DETAIL", energyEvaluateInfoList);

        return map;
    }
}
