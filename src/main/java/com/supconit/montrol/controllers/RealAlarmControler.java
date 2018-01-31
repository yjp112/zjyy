package com.supconit.montrol.controllers;

import com.supconit.montrol.entity.*;
import com.supconit.montrol.service.DeviceTagService;
import com.supconit.montrol.service.IHistoryAlarmService;
import com.supconit.montrol.service.IRealAlarmService;
import com.supconit.montrol.util.RedisUtil;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.safety.manager.SafetyManager;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.authorization.services.UserService;
import com.supconit.montrol.service.IAlarmLevelService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("/montrol/alarm/realalarm")
public class RealAlarmControler {

    // 日志打印工具
    Logger logger = LoggerFactory.getLogger(RealAlarmControler.class);

    @Autowired
    private IRealAlarmService realAlarmService;

    @Autowired
    private IHistoryAlarmService historyAlarmService;

    @Autowired
    private IAlarmLevelService levelService;

    @Autowired
    private UserService userService;

    @Autowired
    private SafetyManager safetyManager;

    @Autowired
    private DeviceTagService deviceTagService;
    @Value("${alarm.message.redisIp}")
    private String redisIp;


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        List<MAlarmLevel> showLevels = new ArrayList<MAlarmLevel>();
        showLevels.addAll(levelService.findList());
        model.put("alarmlevels", showLevels);
        return "montrol/alarm/realalarm/list";
    }

    /**
     * AJAX获取列表数据。
     *
     * @param pager     分页信息
     * @param condition 查询条件
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Pageable<MRealAlarm> list(MRealAlarm realAlarm,
                                     Pagination<MRealAlarm> pager,
                                     ModelMap model) {
        return realAlarmService.find(pager, realAlarm);
    }

    /**
     * 查看展示。
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String view(@RequestParam(required = true) Long id, ModelMap model) {

        MRealAlarm realAlarm = this.realAlarmService.getById(id);
        model.put("mObject", realAlarm);
        MAlarmLevel level = levelService.getById(Long.parseLong(realAlarm.getAlarmLevel()));
        model.put("level", level);
        return "montrol/alarm/realalarm/view";
    }

    @RequestMapping("todoAlarm")
    public String testGotoInsertAlarms(ModelMap model, Long id) {
        model.put("alarmId", id);
        MRealAlarm realAlarm = this.realAlarmService.getById(id);
        if(realAlarm!=null) {
            if(realAlarm.getProcessType()==null){
                realAlarm.setProcessState(0);
            }else if ("确认".equals(realAlarm.getProcessType())) {
                realAlarm.setProcessType("1");
            } else {
                realAlarm.setProcessType("3");
            }
            model.put("realAlarm", realAlarm);
        }
        return "montrol/alarm/realalarm/todoAlarm";
    }


    @ResponseBody
    @RequestMapping("testUpdateAlarms")
    public ScoMessage testUpdateAlarms(ModelMap model, MHandleMessage handle) {
        MRealAlarm realAlarm = this.realAlarmService.getById(handle.getAlarmId());

        MHistoryAlarm historyAlarm = historyAlarmService.getById(realAlarm.getHisAlarmId());
        historyAlarm.setProcessTime(realAlarm.getChangeTime());
        historyAlarm.setProcessPerson(realAlarm.getProcessPerson());
        if ("1".equals(handle.getHandleType())) {
            historyAlarm.setProcessType("确认");
        }else if ("2".equals(handle.getHandleType())) {
            historyAlarm.setProcessType("忽略");
        }else {
            historyAlarm.setProcessType("维修处理");
        }
       // historyAlarm.setProcessType(realAlarm.getProcessType());
        historyAlarm.setProcessState(handle.getHandleState());
        historyAlarmService.update(historyAlarm);
        if ("2".equals(handle.getHandleType())) {
            try {
                JedisPool pool = RedisUtil.getPool(redisIp);
                Jedis jedis = pool.getResource();
                jedis.rpush("Ignore_Tag", realAlarm.getTagCode() + ",1");
                DeviceTag deviceTag = new DeviceTag();
                deviceTag.setTagName(realAlarm.getTagCode());
                deviceTag = deviceTagService.findByName(deviceTag);
                deviceTag.setExtension1("1");
                deviceTagService.update(deviceTag);
                realAlarmService.delete(realAlarm);
            }catch (Exception e){
                e.printStackTrace();
                return ScoMessage.error("忽略失败，连接报警服务器失败");
            }
        } else {
            if (realAlarm.getChangeTime() == null) {
                realAlarm.setChangeTime(new Date());
            }
            if (handle.getHandlePerson() != null && !handle.getHandlePerson().trim().equals("")) {
                realAlarm.setProcessPerson(handle.getHandlePerson());
            }
            if (handle.getHandleType() != null && !handle.getHandleType().trim().equals("")) {
                if ("1".equals(handle.getHandleType())) {
                    realAlarm.setProcessType("确认");
                } else {
                    realAlarm.setProcessType("维修处理");
                }
            }
            realAlarm.setProcessState(handle.getHandleState());
            realAlarmService.update(realAlarm);
            realAlarmService.displayAlarm();
            historyAlarmService.displayAlarm();
        }
        return ScoMessage.success("montrol/alarm/realalarm/list", ScoMessage.SAVE_SUCCESS_MSG);
    }
    
    @RequestMapping("showWarmPage")
    public String showWarmPage(ModelMap model){
        long maxId = 0;
        MRealAlarm realAlarm = new MRealAlarm();
        Pageable<MRealAlarm> pager = new Pagination<MRealAlarm>();
        pager.setPageSize(5);
        pager = realAlarmService.find(pager,realAlarm);
        MRealAlarm maxAlarm = realAlarmService.findNewAlarm();
        if(maxAlarm != null){
            maxId = maxAlarm.getId();
        }
        model.put("alarms", pager);
        model.put("alarmSize", pager.getTotal());
        model.put("maxId", maxId);
        return "montrol/alarm/realalarm/alarmWarm";
    }
    
    @RequestMapping("viewHandle")
    public String viewHandle(ModelMap model, Long id) {
        model.put("alarmId", id);
        MRealAlarm realAlarm = this.realAlarmService.getById(id);
        MAlarmLevel level = levelService.getById(Long.parseLong(realAlarm.getAlarmLevel()));
        model.put("level", level);
        if(realAlarm!=null) {
            if(realAlarm.getProcessType()==null){
                realAlarm.setProcessState(0);
            }else if ("确认".equals(realAlarm.getProcessType())) {
                realAlarm.setProcessType("1");
            } else {
                realAlarm.setProcessType("3");
            }
            model.put("realAlarm", realAlarm);
        }
        return "montrol/alarm/realalarm/viewHandle";
    }

}
