package com.generator.tools.freemarker.template;

import com.generator.tools.convert.ResourceConvert;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.ResourcesTemplateInstance;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

public class ResourcesTemplate extends AbstractGeneratorTemplate<ResourcesTemplateInstance>{
    public ResourcesTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<ResourcesTemplateInstance> getInstances() {
        ResourceConvert resourceConvert = Mappers.getMapper(ResourceConvert.class);
        ResourcesTemplateInstance resourcesTemplateInstance = resourceConvert.generatorDB2ResourceTemplate(globalDataConfig.getGeneratorConfig().getDataSourceConfig());
        resourcesTemplateInstance.setPort(8080);
        resourcesTemplateInstance.setFileName("application.properties");
        return Collections.singletonList(resourcesTemplateInstance);
    }

    @Override
    public void generator() throws Exception {
        super.generator("application_properties.ftl", this.globalDataConfig.getBaseResourcesPath());
    }
}
