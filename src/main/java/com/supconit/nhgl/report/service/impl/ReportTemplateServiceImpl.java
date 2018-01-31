package com.supconit.nhgl.report.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.nhgl.report.dao.ReportTemplateDao;
import com.supconit.nhgl.report.entities.ReportTemplate;
import com.supconit.nhgl.report.service.ReportTemplateService;
@Service
@Transactional
public class ReportTemplateServiceImpl implements ReportTemplateService {

    @Autowired
    private ReportTemplateDao templateDao;

    @Override
    public List<ReportTemplate> getTemplates(ReportTemplate template){
        return templateDao.queryByCodeAndType(template);
    }
    @Override
    public void save(String[] contents, ReportTemplate template){
        List<ReportTemplate> templates = new ArrayList<ReportTemplate>();
        for(int i=1;i<=contents.length;i++){
            ReportTemplate rt = new ReportTemplate();
            rt.setType(template.getType());
            rt.setCode(template.getCode());
            rt.setContent(contents[i-1]);
            rt.setOrder(i);

            templates.add(rt);
        }

        List<ReportTemplate> rts = templateDao.queryByCodeAndType(template);

        if(null==rts || rts.size() == 0) {
            templateDao.batchAdd(templates);
        }else {
            templateDao.batchUpdate(templates);
        }
    }
    @Override
    public void restoreTemplate(ReportTemplate template){
        templateDao.deleteByCodeAndType(template);
    }
}
