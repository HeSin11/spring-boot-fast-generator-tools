package com.generator.tools.freemarker.template.instance;

import lombok.Data;

@Data
public class TemplateInstance {

    protected String tableName;

    protected String fileName;

    protected String targetPath;

    protected String packageName;

    protected String className;
}
