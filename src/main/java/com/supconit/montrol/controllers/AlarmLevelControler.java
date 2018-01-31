package com.supconit.montrol.controllers;

import com.supconit.common.web.entities.ScoMessage;
import com.supconit.montrol.entity.MAlarmLevel;
import com.supconit.montrol.service.IAlarmLevelService;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.safety.manager.SafetyManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/montrol/alarm/level")
public class AlarmLevelControler {
    // 日志打印工具
    Logger logger = LoggerFactory.getLogger(AlarmLevelControler.class);

    @Autowired
    private IAlarmLevelService levelService;

    @Autowired
    private SafetyManager safetyManager;


    /**
     * 列表展现。
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        return "montrol/alarm/level/list";
    }
    /**
     * AJAX获取列表数据。
     *
     * @param pager
     *            分页信息
     * @param condition
     *            查询条件
     * @return
     */
    @RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
    public Pageable<MAlarmLevel> list(MAlarmLevel alarmlevel,
                                      Pagination<MAlarmLevel> pager,
                                      ModelMap model) {
        return levelService.find(pager, alarmlevel);
    }

    @RequestMapping(value="edit", method = RequestMethod.GET)
    public String edit(@RequestParam(required = false)Boolean viewOnly,ModelMap model, @RequestParam(required = false) Long id) {
        //修改
        if (null != id) {
            MAlarmLevel alarmLevel = levelService.getById(id);
            model.put("alarmLevel", alarmLevel);
        }

        model.put("viewOnly", viewOnly);
        return "montrol/alarm/level/edit";
    }

    @ResponseBody
    @RequestMapping(value="save", method = RequestMethod.POST)
    public ScoMessage doEdit(MAlarmLevel alarmLevel) {
        Long num= levelService.getCount(alarmLevel);
        if(num>=1){
            return ScoMessage.error("报警等级或者名称已经存在");
        }
        if(alarmLevel.getId()!=null&&"".equals(alarmLevel.getId())){
            levelService.update(alarmLevel);
        }else{
            levelService.save(alarmLevel);
        }
        return ScoMessage.success("montrol/alarm/level/list", ScoMessage.SAVE_SUCCESS_MSG);
    }

    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ScoMessage delete( @RequestParam("ids") Long[] ids) {
        for(int i =0;i<ids.length;i++) {
            MAlarmLevel alarmLevel = new MAlarmLevel();
            alarmLevel.setId(ids[0]);
            levelService.delete(alarmLevel);
        }
        return ScoMessage.success("montrol/alarm/level/list", ScoMessage.DELETE_SUCCESS_MSG);
    }


}
