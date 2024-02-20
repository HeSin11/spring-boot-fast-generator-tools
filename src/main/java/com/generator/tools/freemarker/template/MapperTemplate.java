package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.generator.config.GeneratorStrategyConfig;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.MapperTemplateInstance;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class MapperTemplate extends AbstractGeneratorTemplate<MapperTemplateInstance> {
    public MapperTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<MapperTemplateInstance> getInstances() {
        GeneratorStrategyConfig strategyConfig = this.globalDataConfig.getGeneratorConfig().getStrategyConfig();

        List<String> tables = strategyConfig.getTables();
        if (CollectionUtils.isEmpty(tables)){
            return null;
        }
        List<MapperTemplateInstance> templateInfos = new ArrayList<>();
        tables.forEach(table->{
            MapperTemplateInstance mapperTemplateInstance = new MapperTemplateInstance();
            mapperTemplateInstance.setTableName(table);
            String className = this.globalDataConfig.getMapperName(table);
            mapperTemplateInstance.setClassName(className);
            mapperTemplateInstance.setPackageName(this.globalDataConfig.getMapperPackageName());
            mapperTemplateInstance.setFileName(className + ".java");
            mapperTemplateInstance.setImportEntityName(this.globalDataConfig.getEntityName(table));
            mapperTemplateInstance.setImportEntityPackage(this.globalDataConfig.getEntityPackageName());
            templateInfos.add(mapperTemplateInstance);
        });
        return templateInfos;
    }

    @Override
    public void generator() throws Exception {
        super.generator("mapper_template.ftl", this.globalDataConfig.getMapperPackagePath());
    }
}
