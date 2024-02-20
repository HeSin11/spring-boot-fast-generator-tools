package com.generator.tools.freemarker.template.instance;

import com.generator.tools.freemarker.model.FieldInfo;
import lombok.Data;

import java.util.List;

@Data
public class QueryDTOTemplateInstance extends TemplateInstance{
    private List<FieldInfo> queryFields;
    private List<String> importFieldTypes;
}
