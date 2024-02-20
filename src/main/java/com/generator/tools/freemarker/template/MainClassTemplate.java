package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.generator.config.GeneratePomConfig;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.TemplateInstance;
import com.generator.tools.util.PackagePathUtil;

import java.util.Collections;
import java.util.List;

public class MainClassTemplate extends AbstractGeneratorTemplate<TemplateInstance>{

    public MainClassTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<TemplateInstance> getInstances() {
        GeneratePomConfig pomConfig = this.globalDataConfig.getGeneratorConfig().getPomConfig();
        String basePackageName = this.globalDataConfig.getBasePackageName();
        TemplateInstance templateInstance = new TemplateInstance();
        templateInstance.setPackageName(basePackageName);
        templateInstance.setClassName(pomConfig.getMainClass());
        templateInstance.setFileName(pomConfig.getMainClass() + ".java");
        return Collections.singletonList(templateInstance);
    }

    public void generator() throws Exception {
        String mainClassPath = PackagePathUtil.getFilePath(this.globalDataConfig.getBasePackagePath(), null);
        super.generator("main_application.ftl", mainClassPath);
    }
}
