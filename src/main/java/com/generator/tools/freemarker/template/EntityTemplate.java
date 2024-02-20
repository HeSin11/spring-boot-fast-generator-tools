package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.generator.config.GeneratorStrategyConfig;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.EntityTemplateInstance;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

public class EntityTemplate extends AbstractGeneratorTemplate<EntityTemplateInstance> {

    public EntityTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<EntityTemplateInstance> getInstances(){
        GeneratorStrategyConfig strategyConfig = this.globalDataConfig.getGeneratorConfig().getStrategyConfig();

        List<String> tables = strategyConfig.getTables();
        if (CollectionUtils.isEmpty(tables)){
            return null;
        }
        List<EntityTemplateInstance> templateInfos = new ArrayList<>();
        tables.forEach(table->{
            EntityTemplateInstance entityTemplateInstance = new EntityTemplateInstance();
            entityTemplateInstance.setTableName(table);
            String className = this.globalDataConfig.getEntityName(table);
            entityTemplateInstance.setClassName(className);
            entityTemplateInstance.setPackageName(this.globalDataConfig.getEntityPackageName());
            entityTemplateInstance.setFileName(className + ".java");
            entityTemplateInstance.setFieldInfos(this.globalDataConfig.getFiledInfoMap().get(this.globalDataConfig.getTableBaseNameMap().get(table)));
            entityTemplateInstance.setImportFieldTypes(new ArrayList<>(this.globalDataConfig.getFiledTypeMap().get(table)));
            templateInfos.add(entityTemplateInstance);
        });
        return templateInfos;
    }



    @Override
    public void generator() throws Exception {
        super.generator("entity_template.ftl", this.globalDataConfig.getEntityPackagePath());
    }
}
