package com.supconit.montrol.controllers;

import com.supconit.common.web.entities.ScoMessage;
import com.supconit.montrol.entity.DeviceAlarmLevel;
import com.supconit.montrol.entity.DeviceTag;
import com.supconit.montrol.entity.MAlarmLevel;
import com.supconit.montrol.service.DeviceAlarmLevelService;
import com.supconit.montrol.service.DeviceTagService;
import com.supconit.montrol.service.IAlarmLevelService;
import com.supconit.montrol.util.RedisUtil;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import hc.safety.manager.SafetyManager;

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

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/montrol/alarm/deviceTag")
public class DeviceTagControler {
    // 日志打印工具
    Logger logger = LoggerFactory.getLogger(DeviceTagControler.class);

    @Autowired
    private DeviceTagService deviceTagService;

    @Autowired
    private SafetyManager safetyManager;
    @Autowired
    private IAlarmLevelService alarmLevelService;

    @Autowired
    private DeviceAlarmLevelService deviceAlarmLevelService;

    @Value("${alarm.message.redisIp}")
    private String redisIp;
    /**
     * 列表展现。
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(ModelMap model, String hpid, boolean viewOnly) {
        model.put("hpid", hpid);
        model.put("viewOnly", viewOnly);
        return "montrol/devicetag/list";
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
    public Pageable<DeviceTag> list(DeviceTag deviceTag,
                                    Pagination<DeviceTag> pager,
                                    ModelMap model) {
        return deviceTagService.find(pager, deviceTag);
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(ModelMap model, @RequestParam(required = false) Long id, String monitorId) {
        List<MAlarmLevel> levels = alarmLevelService.findList();
        if (id != null) {
            DeviceTag tag = deviceTagService.getById(Long.valueOf(id));
            model.put("tag", tag);
            DeviceAlarmLevel deviceAlarmLevel = new DeviceAlarmLevel();
            deviceAlarmLevel.setTagId(id);
            List<DeviceAlarmLevel> points = deviceAlarmLevelService.findList(deviceAlarmLevel);
            Map<String, DeviceAlarmLevel> pointMap = new HashMap<String, DeviceAlarmLevel>();
            for (DeviceAlarmLevel point : points) {
                pointMap.put(point.getAlarmType(), point);
            }
            model.put("pointMap", pointMap);
        }
        model.put("monitorId", monitorId);
        model.put("levels", levels);
        return "montrol/devicetag/edit";
    }

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ScoMessage doEdit(DeviceTag deviceTag, HttpServletRequest request) {
        if (deviceTag.getTagName().length() > 50) {
            return ScoMessage.error("位号名超出范围！最多只能输入50个字符.");
        }
        if (deviceTag.getTagDesc() != null && deviceTag.getTagDesc().length() > 200) {
            return ScoMessage.error("备注信息超出范围！最多只能输入200个字符.");
        }
        DeviceTag selTag = deviceTagService.findByName(deviceTag);
        if (deviceTag.getId() == null) {
            if (selTag != null) {
                return ScoMessage.error("已存在同名位号！保存失败.");
            }
            deviceTagService.insert(deviceTag);
        } else {
            if (selTag != null && !selTag.getId().equals(deviceTag.getId())) {
                return ScoMessage.error("已存在同名位号！保存失败.");
            }
            deviceTagService.update(deviceTag);
        }
        deviceAlarmLevelService.delByTagId(deviceTag.getId());
        if (deviceTag.getAlarmPoint() == 1 || deviceTag.getAlarmPoint().equals(1)) {
            if (deviceTag.getTagType().equals("13")) {
                String onlevel = request.getParameter("ON");
                if (onlevel != null && !onlevel.equals("")) {
                    DeviceAlarmLevel onPoint = new DeviceAlarmLevel();
                    onPoint.setTagId(deviceTag.getId());
                    onPoint.setAlarmType("ON");
                    onPoint.setAlarmLevelId(Long.valueOf(onlevel));
                    deviceAlarmLevelService.insert(onPoint);
                }
                String offLevel = request.getParameter("OFF");
                if (offLevel != null && !offLevel.equals("")) {
                    DeviceAlarmLevel offPoint = new DeviceAlarmLevel();
                    offPoint.setTagId(deviceTag.getId());
                    offPoint.setAlarmType("OFF");
                    offPoint.setAlarmLevelId(Long.valueOf(offLevel));
                    deviceAlarmLevelService.insert(offPoint);
                }
            } else {
                String hhLevel = request.getParameter("HH");
                if (hhLevel != null && !hhLevel.equals("")) {
                    DeviceAlarmLevel llPoint = new DeviceAlarmLevel();
                    llPoint.setTagId(deviceTag.getId());
                    llPoint.setAlarmType("HH");
                    llPoint.setAlarmLevelId(Long.valueOf(hhLevel));
                    deviceAlarmLevelService.insert(llPoint);
                }

                String hevel = request.getParameter("H");
                if (hevel != null && !hevel.equals("")) {
                    DeviceAlarmLevel hPoint = new DeviceAlarmLevel();
                    hPoint.setTagId(deviceTag.getId());
                    hPoint.setAlarmType("H");
                    hPoint.setAlarmLevelId(Long.valueOf(hevel));
                    deviceAlarmLevelService.insert(hPoint);
                }

                String lllevel = request.getParameter("LL");
                if (lllevel != null && !lllevel.equals("")) {
                    DeviceAlarmLevel llPoint = new DeviceAlarmLevel();
                    llPoint.setTagId(deviceTag.getId());
                    llPoint.setAlarmType("LL");
                    llPoint.setAlarmLevelId(Long.valueOf(lllevel));
                    deviceAlarmLevelService.insert(llPoint);
                }

                String llevel = request.getParameter("L");
                if (llevel != null && !llevel.equals("")) {
                    DeviceAlarmLevel lPoint = new DeviceAlarmLevel();
                    lPoint.setTagId(deviceTag.getId());
                    lPoint.setAlarmType("L");
                    lPoint.setAlarmLevelId(Long.valueOf(llevel));
                    deviceAlarmLevelService.insert(lPoint);
                }

                String prinlevel = request.getParameter("PRIN");
                if (prinlevel != null && !prinlevel.equals("")) {
                    DeviceAlarmLevel prinPoint = new DeviceAlarmLevel();
                    prinPoint.setTagId(deviceTag.getId());
                    prinPoint.setAlarmType("PRIN");
                    prinPoint.setAlarmLevelId(Long.valueOf(prinlevel));
                    deviceAlarmLevelService.insert(prinPoint);
                }

                String nrinlevel = request.getParameter("NRIN");
                if (nrinlevel != null && !nrinlevel.equals("")) {
                    DeviceAlarmLevel nrinPoint = new DeviceAlarmLevel();
                    nrinPoint.setTagId(deviceTag.getId());
                    nrinPoint.setAlarmType("NRIN");
                    nrinPoint.setAlarmLevelId(Long.valueOf(nrinlevel));
                    deviceAlarmLevelService.insert(nrinPoint);
                }
            }
        }
        return ScoMessage.success(ScoMessage.SAVE_SUCCESS_MSG);
    }


    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ScoMessage delete(@RequestParam("id") Long id) {
        DeviceTag deviceTag = new DeviceTag();
        deviceTag.setId(id);
        deviceAlarmLevelService.delByTagId(deviceTag.getId());
        deviceTagService.delete(deviceTag);
        return ScoMessage.success(ScoMessage.DELETE_SUCCESS_MSG);
    }

    @ResponseBody
    @RequestMapping(value = "quxiaohulue", method = RequestMethod.POST)
    public ScoMessage quxiaohulue(@RequestParam("id") Long id) {
        DeviceTag  deviceTag =deviceTagService.getById(id);
        try {
            JedisPool pool = RedisUtil.getPool(redisIp);;
            Jedis jedis = pool.getResource();
            jedis.rpush("Ignore_Tag", deviceTag.getTagName() + ",0");
            deviceTag.setExtension1("0");
            deviceTagService.update(deviceTag);
        }catch (Exception e){
            return ScoMessage.error("连接报警服务器失败");
        }

        return ScoMessage.success(ScoMessage.DEFAULT_SUCCESS_MSG);
    }
}
