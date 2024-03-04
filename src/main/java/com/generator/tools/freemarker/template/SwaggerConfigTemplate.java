package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.CommonConfigTemplateInstance;
import com.generator.tools.freemarker.template.instance.SwaggerConfigTemplateInstance;

import java.util.Collections;
import java.util.List;

public class SwaggerConfigTemplate  extends AbstractGeneratorTemplate<SwaggerConfigTemplateInstance>{
    public SwaggerConfigTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<SwaggerConfigTemplateInstance> getInstances() {
        SwaggerConfigTemplateInstance swaggerConfigTemplateInstance = new SwaggerConfigTemplateInstance();
        swaggerConfigTemplateInstance.setPackageName(this.globalDataConfig.getCommonPackageName());
        swaggerConfigTemplateInstance.setFileName("SwaggerConfig.java");
        swaggerConfigTemplateInstance.setScanSwaggerPackage(this.globalDataConfig.getBasePackageName());
        swaggerConfigTemplateInstance.setPackageName(this.globalDataConfig.getConfigPackageName());
        return Collections.singletonList(swaggerConfigTemplateInstance);
    }

    @Override
    public void generator() throws Exception {
        super.generator("swagger_config.ftl", this.globalDataConfig.getConfigPackagePath());
    }
}
