package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.generator.config.GeneratePomConfig;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.PomTemplateInstance;

import java.util.Collections;
import java.util.List;

public class PomTemplate extends AbstractGeneratorTemplate<PomTemplateInstance>{

    public PomTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<PomTemplateInstance> getInstances() {
        GeneratePomConfig pomConfig = this.globalDataConfig.getGeneratorConfig().getPomConfig();
        PomTemplateInstance pomTemplateInstance = new PomTemplateInstance();
        pomTemplateInstance.setPomConfig(pomConfig);
        pomTemplateInstance.setPackageName(this.globalDataConfig.getBasePackageName());
        pomTemplateInstance.setFileName("pom.xml");
        Boolean importSwagger = this.globalDataConfig.getGeneratorConfig().getStrategyConfig().getSwagger();
        pomTemplateInstance.setImportSwagger(importSwagger);
        return Collections.singletonList(pomTemplateInstance);
    }

    public void generator() throws Exception {
        super.generator("pom.ftl", this.globalDataConfig.getBaseProjectPath());
    }
}
