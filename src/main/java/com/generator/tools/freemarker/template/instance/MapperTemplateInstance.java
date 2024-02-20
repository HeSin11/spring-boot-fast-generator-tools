package com.generator.tools.freemarker.template.instance;

import lombok.Data;

@Data
public class MapperTemplateInstance extends TemplateInstance{
    private String importEntityName;
    private String importEntityPackage;
}
