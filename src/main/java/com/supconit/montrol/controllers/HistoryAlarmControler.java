package com.supconit.montrol.controllers;

import com.supconit.montrol.entity.*;
import com.supconit.montrol.service.IAlarmLevelService;
import com.supconit.montrol.service.IHistoryAlarmService;
import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("montrol/alarm/historyalarm")
public class HistoryAlarmControler
{
    // 日志打印工具
    Logger logger = LoggerFactory.getLogger(HistoryAlarmControler.class);

    @Autowired
    private IHistoryAlarmService historyAlarmService;



    @Autowired
    private IAlarmLevelService levelService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(String deviceCode,ModelMap model) {
        List<MAlarmLevel> showLevels = new ArrayList<MAlarmLevel>();
        showLevels.addAll(levelService.findList());
        model.put("alarmlevels", showLevels);
        model.put("deviceCode", deviceCode);
        return "montrol/alarm/historyalarm/list";
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
    public Pageable<MHistoryAlarm> list(MHistoryAlarm mHistoryAlarm,
                                     Pagination<MHistoryAlarm> pager,
                                     ModelMap model) {
        return historyAlarmService.find(pager, mHistoryAlarm);
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

        MHistoryAlarm historyAlarm = this.historyAlarmService.getById(id);
        model.put("mObject", historyAlarm);
        MAlarmLevel level = levelService.getById(historyAlarm.getAlarmLevel());
        model.put("level", level);
        return "montrol/alarm/historyalarm/view";
    }
}
