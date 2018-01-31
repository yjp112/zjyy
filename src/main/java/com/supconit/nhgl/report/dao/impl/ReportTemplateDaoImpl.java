package com.supconit.nhgl.report.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supconit.nhgl.report.dao.ReportTemplateDao;
import com.supconit.nhgl.report.entities.ReportTemplate;

@Repository
public class ReportTemplateDaoImpl implements ReportTemplateDao {
    private final static String NAMESPACE = ReportTemplate.class.getName();
    @Autowired
    private SqlSessionTemplate template;

    @Override
    public List<ReportTemplate> queryByCodeAndType(ReportTemplate reportTemplate) {
        return template.selectList(getStatement("queryByCodeAndType"),reportTemplate);
    }

    @Override
    public void deleteByCodeAndType(ReportTemplate template) {
        this.template.delete(getStatement("deleteByCodeAndType"), template);
    }

    @Override
    public void batchAdd(List<ReportTemplate> templates) {
        Map map = new HashMap();
        map.put("templates",templates);
        template.insert(getStatement("batchInsert"),map);
    }

    @Override
    public void batchUpdate(List<ReportTemplate> templates) {
        for(ReportTemplate rt : templates){
            template.update(getStatement("updateByCodeAndType"),rt);
        }
    }

    private String getStatement(String sql) {
        return NAMESPACE+"."+sql;
    }

}
