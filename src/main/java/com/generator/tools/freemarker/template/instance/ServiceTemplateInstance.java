package com.generator.tools.freemarker.template.instance;

import lombok.Data;


@Data
public class ServiceTemplateInstance extends TemplateInstance{
    private String importEntityName;
    private String importEntityPackage;
    private String importPageQueryName;
    private String importQueryPackage;
    private String importQueryName;
}
