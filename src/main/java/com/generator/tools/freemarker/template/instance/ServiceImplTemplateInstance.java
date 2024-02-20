package com.generator.tools.freemarker.template.instance;

import com.generator.tools.freemarker.model.FieldInfo;
import lombok.Data;

import java.util.List;

@Data
public class ServiceImplTemplateInstance extends TemplateInstance{
    private String importEntityPackage;
    private String importEntityName;
    private String importMapperPackage;
    private String importMapperName;
    private String importServicePackage;
    private String importServiceName;
    private String importPageQueryName;
    private String importQueryPackage;
    private String importQueryName;
    private List<FieldInfo> fieldInfos;
    private List<FieldInfo> pageFieldInfos;
}
