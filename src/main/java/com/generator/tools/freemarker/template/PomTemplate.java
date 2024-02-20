package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.generator.config.GeneratePomConfig;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import java.util.Collections;
import java.util.List;

public class PomTemplate extends AbstractGeneratorTemplate<GeneratePomConfig>{

    public PomTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<GeneratePomConfig> getInstances() {
        GeneratePomConfig pomConfig = this.globalDataConfig.getGeneratorConfig().getPomConfig();
        pomConfig.setPackageName(this.globalDataConfig.getBasePackageName());
        pomConfig.setFileName("pom.xml");
        return Collections.singletonList(pomConfig);
    }

    public void generator() throws Exception {
        super.generator("pom.ftl", this.globalDataConfig.getBaseProjectPath());
    }
}
