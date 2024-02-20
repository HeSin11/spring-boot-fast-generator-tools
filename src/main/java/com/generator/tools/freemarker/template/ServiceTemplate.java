package com.generator.tools.freemarker.template;
import com.generator.tools.freemarker.generator.config.GeneratorStrategyConfig;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.ServiceTemplateInstance;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ServiceTemplate extends AbstractGeneratorTemplate<ServiceTemplateInstance> {

    public ServiceTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    public void generator() throws Exception {
        super.generator("service_template.ftl", this.globalDataConfig.getServicePackagePath());
    }

    @Override
    protected List<ServiceTemplateInstance> getInstances() {
        GeneratorStrategyConfig strategyConfig = this.globalDataConfig.getGeneratorConfig().getStrategyConfig();

        List<String> tables = strategyConfig.getTables();
        if (CollectionUtils.isEmpty(tables)){
            return null;
        }
        List<ServiceTemplateInstance> templateInfos = new ArrayList<>();
        tables.forEach(table->{
            ServiceTemplateInstance serviceTemplateInstance = new ServiceTemplateInstance();
            serviceTemplateInstance.setTableName(table);
            String className = this.globalDataConfig.getServiceName(table);
            serviceTemplateInstance.setClassName(className);
            serviceTemplateInstance.setPackageName(this.globalDataConfig.getServicePackageName());
            serviceTemplateInstance.setFileName(className + ".java");
            serviceTemplateInstance.setImportEntityName(this.globalDataConfig.getEntityName(table));
            serviceTemplateInstance.setImportEntityPackage(this.globalDataConfig.getEntityPackageName());
            serviceTemplateInstance.setImportQueryPackage(this.globalDataConfig.getQueryPackageName());
            serviceTemplateInstance.setImportPageQueryName(this.globalDataConfig.getPageQueryName(table));
            serviceTemplateInstance.setImportQueryName(this.globalDataConfig.getQueryName(table));
            templateInfos.add(serviceTemplateInstance);
        });
        return templateInfos;
    }
}
