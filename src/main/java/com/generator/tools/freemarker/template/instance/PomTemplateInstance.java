package com.generator.tools.freemarker.template.instance;

import com.generator.tools.freemarker.generator.config.GeneratePomConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PomTemplateInstance extends TemplateInstance{

    private Boolean importSwagger;

    private GeneratePomConfig pomConfig;
}
