package com.supconit.nhgl.report.service;

import java.util.List;

import com.supconit.nhgl.report.entities.ReportTemplate;

public interface ReportTemplateService {
    List<ReportTemplate> getTemplates(ReportTemplate template);

    void save(String[] contents, ReportTemplate template);

    void restoreTemplate(ReportTemplate template);
}
