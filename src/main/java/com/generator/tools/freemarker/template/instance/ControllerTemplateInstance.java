package com.generator.tools.freemarker.template.instance;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ControllerTemplateInstance extends TemplateInstance{
    private String importEntityPackage;
    private String importEntityName;
    private String importServicePackage;
    private String importServiceName;
    private String importPageQueryName;
    private String importQueryPackage;
    private String importQueryName;
    private String resultPackage;
    private String resultClassName;
    private String baseUrl;
}
