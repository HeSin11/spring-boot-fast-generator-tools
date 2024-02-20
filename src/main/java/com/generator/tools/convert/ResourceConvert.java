package com.generator.tools.convert;

import com.generator.tools.freemarker.generator.config.GeneratorDataSourceConfig;
import com.generator.tools.freemarker.template.instance.ResourcesTemplateInstance;
import org.mapstruct.Mapper;

@Mapper
public interface ResourceConvert {
    ResourcesTemplateInstance generatorDB2ResourceTemplate(GeneratorDataSourceConfig generatorDataSourceConfig);
}
