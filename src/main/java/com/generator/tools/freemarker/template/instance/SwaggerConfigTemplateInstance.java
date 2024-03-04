package com.generator.tools.freemarker.template.instance;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SwaggerConfigTemplateInstance extends TemplateInstance{
    private String scanSwaggerPackage;
}
