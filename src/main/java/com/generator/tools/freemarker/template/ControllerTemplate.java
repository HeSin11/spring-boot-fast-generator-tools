package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.generator.config.GeneratorStrategyConfig;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.ControllerTemplateInstance;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ControllerTemplate extends AbstractGeneratorTemplate<ControllerTemplateInstance>{
    public ControllerTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<ControllerTemplateInstance> getInstances() {
        GeneratorStrategyConfig strategyConfig = this.globalDataConfig.getGeneratorConfig().getStrategyConfig();

        List<String> tables = strategyConfig.getTables();
        if (CollectionUtils.isEmpty(tables)){
            return null;
        }
        List<ControllerTemplateInstance> templateInfos = new ArrayList<>();
        tables.forEach(table->{
            ControllerTemplateInstance controllerTemplateInstance = new ControllerTemplateInstance();
            controllerTemplateInstance.setTableName(table);
            String className = this.globalDataConfig.getControllerName(table);
            controllerTemplateInstance.setClassName(className);
            controllerTemplateInstance.setPackageName(this.globalDataConfig.getControllerPackageName());
            controllerTemplateInstance.setFileName(className + ".java");
            controllerTemplateInstance.setImportEntityName(this.globalDataConfig.getEntityName(table));
            controllerTemplateInstance.setImportEntityPackage(this.globalDataConfig.getEntityPackageName());
            controllerTemplateInstance.setImportServiceName(this.globalDataConfig.getServiceName(table));
            controllerTemplateInstance.setImportServicePackage(this.globalDataConfig.getServicePackageName());
            controllerTemplateInstance.setImportQueryPackage(this.globalDataConfig.getQueryPackageName());
            controllerTemplateInstance.setResultPackage(this.globalDataConfig.getCommonPackageName());
            controllerTemplateInstance.setResultClassName("CommonResult");
            controllerTemplateInstance.setBaseUrl(this.globalDataConfig.getTableBaseNameMap().get(table));
            controllerTemplateInstance.setImportPageQueryName(this.globalDataConfig.getPageQueryName(table));
            controllerTemplateInstance.setImportQueryName(this.globalDataConfig.getQueryName(table));
            templateInfos.add(controllerTemplateInstance);
        });
        return templateInfos;
    }

    @Override
    public void generator() throws Exception {
        super.generator("controller_template.ftl", this.globalDataConfig.getControllerPackagePath());
    }
}
