package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.generator.config.GeneratorStrategyConfig;
import com.generator.tools.freemarker.model.FieldInfo;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.ServiceImplTemplateInstance;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ServiceImplTemplate extends AbstractGeneratorTemplate<ServiceImplTemplateInstance>{
    public ServiceImplTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<ServiceImplTemplateInstance> getInstances() {
        GeneratorStrategyConfig strategyConfig = this.globalDataConfig.getGeneratorConfig().getStrategyConfig();

        List<String> tables = strategyConfig.getTables();
        if (CollectionUtils.isEmpty(tables)){
            return null;
        }
        List<ServiceImplTemplateInstance> templateInfos = new ArrayList<>();
        tables.forEach(table->{
            ServiceImplTemplateInstance serviceImplTemplateInstance = new ServiceImplTemplateInstance();
            serviceImplTemplateInstance.setTableName(table);
            String className = this.globalDataConfig.getServiceImplName(table);
            serviceImplTemplateInstance.setClassName(className);
            serviceImplTemplateInstance.setPackageName(this.globalDataConfig.getServiceImplPackageName());
            serviceImplTemplateInstance.setFileName(className + ".java");
            serviceImplTemplateInstance.setImportEntityName(this.globalDataConfig.getEntityName(table));
            serviceImplTemplateInstance.setImportEntityPackage(this.globalDataConfig.getEntityPackageName());
            serviceImplTemplateInstance.setImportServiceName(this.globalDataConfig.getServiceName(table));
            serviceImplTemplateInstance.setImportServicePackage(this.globalDataConfig.getServicePackageName());
            serviceImplTemplateInstance.setImportMapperName(this.globalDataConfig.getMapperName(table));
            serviceImplTemplateInstance.setImportMapperPackage(this.globalDataConfig.getMapperPackageName());
            serviceImplTemplateInstance.setImportQueryPackage(this.globalDataConfig.getQueryPackageName());
            serviceImplTemplateInstance.setImportPageQueryName(this.globalDataConfig.getPageQueryName(table));
            serviceImplTemplateInstance.setImportQueryName(this.globalDataConfig.getQueryName(table));
            List<FieldInfo> fieldInfos = this.globalDataConfig.getFiledInfoMap().get(this.globalDataConfig.getTableBaseNameMap().get(table));
            ArrayList<FieldInfo> fields = new ArrayList<>(fieldInfos);
            List<FieldInfo> pageFields = new ArrayList<>(fields);
            serviceImplTemplateInstance.setFieldInfos(fields);
            FieldInfo pageNumField = new FieldInfo();
            pageNumField.setFieldName("pageNum");
            pageNumField.setFieldType("Integer");
            pageNumField.setFieldRemark("页码");
            FieldInfo pageSizeField = new FieldInfo();
            pageSizeField.setFieldName("pageSize");
            pageSizeField.setFieldType("Integer");
            pageFields.add(pageNumField);
            pageFields.add(pageSizeField);

            serviceImplTemplateInstance.setPageFieldInfos(pageFields);
            templateInfos.add(serviceImplTemplateInstance);
        });
        return templateInfos;
    }

    @Override
    public void generator() throws Exception {
        super.generator("service_impl_template.ftl", this.globalDataConfig.getServiceImplPackagePath());
    }
}
