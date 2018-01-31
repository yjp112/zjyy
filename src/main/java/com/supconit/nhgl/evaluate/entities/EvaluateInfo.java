package com.supconit.nhgl.evaluate.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.supconit.common.utils.RandomUtils;

import hc.base.domains.LongId;
import jodd.datetime.JDateTime;

/**
 * Created by zhangzhaoyun on 14-6-23.
 */
public class EvaluateInfo extends LongId implements Serializable {

    private String taskCode; //任务编码
    private String taskName; //任务名称
    private Float score;  //得分
    private int taskVesting; //任务类型（1:设备运行，2:电，3：水，4：气）
    private Long crLevel;

    
    public Long getCrLevel() {
		return crLevel;
	}

	public void setCrLevel(Long crLevel) {
		this.crLevel = crLevel;
	}

	private String content;

    private List<MonitorObjScore> objScores = new ArrayList<MonitorObjScore>();

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public int getTaskVesting() {
        return taskVesting;
    }

    public void setTaskVesting(int taskVesting) {
        this.taskVesting = taskVesting;
    }

    public List<MonitorObjScore> getObjScores() {
        return objScores;
    }

    public void setObjScores(List<MonitorObjScore> objScores) {
        this.objScores = objScores;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @param num    生成对象的个数
     * @param type   生成对象的类型 1：Device 2：electric
     * @param subNum 当类型为device时生成具体设备的数量
     * @return
     */
    public static List<EvaluateInfo> generateDemoInfo(int num, int type, int subNum) {
        if (num <= 0) return null;
        List<EvaluateInfo> evaluateList = new ArrayList<EvaluateInfo>(num);
        for (int i = 0; i < num; i++) {
            EvaluateInfo info = new EvaluateInfo();
            info.setTaskCode("TASKCODE"+ RandomUtils.getIntRandom(1,100));
            info.setTaskName("TASKNAME"+RandomUtils.getIntRandom(1,100));
            info.setScore(Float.valueOf(String.valueOf(RandomUtils.getDoubleRandom(-10.0, 0.0,0))));
            info.setTaskVesting(type);

            info.setId(Long.valueOf(String.valueOf(RandomUtils.getIntRandom(100,1000))));
            if(type == 1){
                if (subNum>=0) {
                    List<MonitorObjScore> objScoreList = new ArrayList<MonitorObjScore>(subNum);

                    for (int j = 0; j < subNum; j++) {
                        MonitorObjScore score = new MonitorObjScore();
                        score.setDeviceCode("DEVICECODE"+RandomUtils.getIntRandom(1,100));
                        score.setExecuteTime(new JDateTime().toString());
                        score.setScore(Double.valueOf(RandomUtils.getDoubleRandom(-10.0,0.0,0)).intValue());

                        objScoreList.add(score);
                    }

                    info.setObjScores(objScoreList);
                }

            }
            evaluateList.add(info);
        }
        return evaluateList;
    }
}
