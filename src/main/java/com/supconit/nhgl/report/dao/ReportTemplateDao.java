package com.supconit.nhgl.report.dao;

import java.util.List;

import com.supconit.nhgl.report.entities.ReportTemplate;

public interface ReportTemplateDao {


    List<ReportTemplate> queryByCodeAndType(ReportTemplate reportTemplate);

    void deleteByCodeAndType(ReportTemplate template);

    void batchAdd(List<ReportTemplate> templates);

    void batchUpdate(List<ReportTemplate> templates);
}
