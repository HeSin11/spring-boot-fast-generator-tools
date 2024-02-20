package com.generator.tools.freemarker.template.instance;

import com.generator.tools.freemarker.model.FieldInfo;
import lombok.Data;

import java.util.List;

@Data
public class EntityTemplateInstance extends TemplateInstance {

    private List<FieldInfo> fieldInfos;

    private List<String> importFieldTypes;
}
