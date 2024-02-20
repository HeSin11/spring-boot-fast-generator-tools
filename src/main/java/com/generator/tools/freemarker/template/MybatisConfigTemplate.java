package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.MybatisPlusConfigTemplateInstance;

import java.util.Collections;
import java.util.List;

public class MybatisConfigTemplate extends AbstractGeneratorTemplate<MybatisPlusConfigTemplateInstance>{
    public MybatisConfigTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<MybatisPlusConfigTemplateInstance> getInstances() {
        MybatisPlusConfigTemplateInstance mybatisPlusConfigTemplateInstance = new MybatisPlusConfigTemplateInstance();
        mybatisPlusConfigTemplateInstance.setPackageName(this.globalDataConfig.getConfigPackageName());
        mybatisPlusConfigTemplateInstance.setFileName("MybatisPlusConfig.java");
        return Collections.singletonList(mybatisPlusConfigTemplateInstance);
    }

    @Override
    public void generator() throws Exception {
        super.generator("mybatisplus_config.ftl", this.globalDataConfig.getConfigPackagePath());
    }
}
