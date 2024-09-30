package com.generator.tools.freemarker.template;

import com.generator.tools.enums.FilterQuery;
import com.generator.tools.enums.FuzzyTypeEnum;
import com.generator.tools.freemarker.generator.config.GeneratorStrategyConfig;
import com.generator.tools.freemarker.model.FieldInfo;
import com.generator.tools.freemarker.model.FuzzyField;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.QueryDTOTemplateInstance;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class QueryDTOTemplate extends AbstractGeneratorTemplate<QueryDTOTemplateInstance>{
    /**
     * 模糊查询配置
     */
    private Map<String, List<FuzzyField>> fuzzyFieldsGroup;
    /**
     * 忽略查询字段
     */
    private Map<String, List<String>> ignoreQueryFieldsGroup;
    /**
     * 包含查询字段
     */
    private Map<String, List<String>> includeQueryFieldsGroup;


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
            //处理字段信息
            List<FieldInfo> fieldInfos = Optional.ofNullable(processingAndFilterFields(table)).orElse(new ArrayList<>());
            queryDTOTemplateInstance.setImportFieldTypes(fieldInfos.stream().map(FieldInfo::getFieldType).collect(Collectors.toList()));
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

    private List<FieldInfo> processingAndFilterFields(String table){
        List<FieldInfo> fieldInfos = this.globalDataConfig.getFiledInfoMap().get(this.globalDataConfig.getTableBaseNameMap().get(table));
        List<FieldInfo> filterResult = filterFields(fieldInfos, table);
        setFuzzyState(filterResult, table);
        return filterResult;
    }


    private List<FieldInfo> filterFields(List<FieldInfo> fieldInfos, String table){
        if (CollectionUtils.isEmpty(ignoreQueryFieldsGroup) && CollectionUtils.isEmpty(includeQueryFieldsGroup)){
            return fieldInfos;
        }
        List<String> includeFields = includeQueryFieldsGroup.get(table);
        List<String> ignoreFields = ignoreQueryFieldsGroup.get(table);
        if (CollectionUtils.isEmpty(ignoreFields) && CollectionUtils.isEmpty(includeFields)){
            return fieldInfos;
        }
        if (!CollectionUtils.isEmpty(includeFields)){
            return fieldInfos.stream().filter(fieldInfo -> includeFields.contains(fieldInfo.getColumnName())).collect(Collectors.toList());
        }
        return fieldInfos.stream().filter(fieldInfo -> !ignoreFields.contains(fieldInfo.getColumnName())).collect(Collectors.toList());
    }

    private void setFuzzyState(List<FieldInfo> fieldInfos, String table){
        if (CollectionUtils.isEmpty(fuzzyFieldsGroup) || CollectionUtils.isEmpty(fieldInfos)){
            return;
        }
        List<FuzzyField> fuzzyFields = fuzzyFieldsGroup.get(table);

        Map<String, FuzzyTypeEnum> columnFuzzyMap = fuzzyFields.stream().collect(Collectors.toMap(FuzzyField::getColumnName, FuzzyField::getFuzzyType, (v1, v2) -> v2));

        fieldInfos.forEach(fieldInfo -> {
            String columnName = fieldInfo.getColumnName();
            FuzzyTypeEnum fuzzyTypeEnum = columnFuzzyMap.get(columnName);
            if (fuzzyTypeEnum == null){
                return;
            }
            fieldInfo.setIsFuzzy(Boolean.TRUE);
            fieldInfo.setFuzzyType(fuzzyTypeEnum.name());
        });
    }

    public void setFuzzyFieldsGroup(List<FuzzyField> fuzzyFields){
        if (CollectionUtils.isEmpty(fuzzyFields)){
            return;
        }
        this.fuzzyFieldsGroup = fuzzyFields.stream().collect(Collectors.groupingBy(FuzzyField::getTable));
    }

    public void setIgnoreQueryFieldsGroup(List<FilterQuery> ignoreQueryFields){
        if (CollectionUtils.isEmpty(ignoreQueryFields)){
            return;
        }
        this.ignoreQueryFieldsGroup = ignoreQueryFields.stream().collect(Collectors.groupingBy(FilterQuery::getTable, Collectors.collectingAndThen(Collectors.toList(), list -> list.stream().map(FilterQuery::getColumnName).collect(Collectors.toList()))));
    }

    public void setIncludeQueryFieldsGroup(List<FilterQuery> includeQueryFields){
        if (CollectionUtils.isEmpty(includeQueryFields)){
            return;
        }
        this.includeQueryFieldsGroup = includeQueryFields.stream().collect(Collectors.groupingBy(FilterQuery::getTable, Collectors.collectingAndThen(Collectors.toList(), list -> list.stream().map(FilterQuery::getColumnName).collect(Collectors.toList()))));
    }

    @Override
    public void generator() throws Exception {
        super.generator("page_query_template.ftl", this.globalDataConfig.getQueryPackagePath());
    }

}
