package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.CommonConfigTemplateInstance;
import java.util.Collections;
import java.util.List;

public class CommonConfigTemplate extends AbstractGeneratorTemplate<CommonConfigTemplateInstance>{

    public CommonConfigTemplate(GlobalDataConfig globalDataConfig) {
        super(globalDataConfig);
    }

    @Override
    protected List<CommonConfigTemplateInstance> getInstances() {
        CommonConfigTemplateInstance commonConfigTemplateInstance = new CommonConfigTemplateInstance();
        commonConfigTemplateInstance.setPackageName(this.globalDataConfig.getCommonPackageName());
        commonConfigTemplateInstance.setFileName("CommonResult.java");
        return Collections.singletonList(commonConfigTemplateInstance);
    }

    @Override
    public void generator() throws Exception {
        super.generator("common_result_template.ftl", this.globalDataConfig.getCommonPackagePath());
    }
}
