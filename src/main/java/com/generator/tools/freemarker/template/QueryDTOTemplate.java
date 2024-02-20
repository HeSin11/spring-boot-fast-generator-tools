package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.generator.config.GeneratorStrategyConfig;
import com.generator.tools.freemarker.model.FieldInfo;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.QueryDTOTemplateInstance;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class QueryDTOTemplate extends AbstractGeneratorTemplate<QueryDTOTemplateInstance>{
    public QueryDTOTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<QueryDTOTemplateInstance> getInstances() {
        GeneratorStrategyConfig strategyConfig = this.globalDataConfig.getGeneratorConfig().getStrategyConfig();

        List<String> tables = strategyConfig.getTables();
        if (CollectionUtils.isEmpty(tables)){
            return null;
        }
        List<QueryDTOTemplateInstance> templateInfos = new ArrayList<>();
        //pageQuery
        tables.forEach(table->{
            QueryDTOTemplateInstance queryDTOTemplateInstance = new QueryDTOTemplateInstance();
            String baseName = this.globalDataConfig.getTableBaseNameMap().get(table);
            String className = this.globalDataConfig.generatorClassName(baseName, null) + "PageQuery";

            queryDTOTemplateInstance.setClassName(className);
            queryDTOTemplateInstance.setPackageName(this.globalDataConfig.getQueryPackageName());
            queryDTOTemplateInstance.setFileName(className + ".java");
            List<FieldInfo> fieldInfos = this.globalDataConfig.getFiledInfoMap().get(this.globalDataConfig.getTableBaseNameMap().get(table));
            queryDTOTemplateInstance.setImportFieldTypes(new ArrayList<>(this.globalDataConfig.getFiledTypeMap().get(table)));
            FieldInfo pageNumField = new FieldInfo();
            pageNumField.setFieldName("pageNum");
            pageNumField.setFieldType("Integer");
            pageNumField.setFieldRemark("页码");
            FieldInfo pageSizeField = new FieldInfo();
            pageSizeField.setFieldName("pageSize");
            pageSizeField.setFieldType("Integer");
            pageSizeField.setFieldRemark("每页条数");
            fieldInfos.add(pageNumField);
            fieldInfos.add(pageSizeField);
            queryDTOTemplateInstance.setQueryFields(fieldInfos);
            templateInfos.add(queryDTOTemplateInstance);
        });
        //query
        tables.forEach(table->{
            QueryDTOTemplateInstance queryDTOTemplateInstance = new QueryDTOTemplateInstance();

            String baseName = this.globalDataConfig.getTableBaseNameMap().get(table);
            String className = this.globalDataConfig.generatorClassName(baseName, null) + "Query";
            queryDTOTemplateInstance.setClassName(className);
            queryDTOTemplateInstance.setPackageName(this.globalDataConfig.getQueryPackageName());
            queryDTOTemplateInstance.setFileName(className + ".java");
            List<FieldInfo> fieldInfos = this.globalDataConfig.getFiledInfoMap().get(this.globalDataConfig.getTableBaseNameMap().get(table));
            queryDTOTemplateInstance.setQueryFields(fieldInfos);
            queryDTOTemplateInstance.setImportFieldTypes(new ArrayList<>(this.globalDataConfig.getFiledTypeMap().get(table)));
            templateInfos.add(queryDTOTemplateInstance);
        });
        return templateInfos;
    }

    @Override
    public void generator() throws Exception {
        super.generator("page_query_template.ftl", this.globalDataConfig.getQueryPackagePath());
    }
}
