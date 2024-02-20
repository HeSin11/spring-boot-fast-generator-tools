package com.generator.tools.freemarker.generator.config;

import com.generator.tools.common.enums.NameStrategyEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 自动生成文件策略配置
 */
@Builder
@Getter
public class GeneratorStrategyConfig{
    /**
     * entity文件名生成配置
     */
    private NameStrategyEnum entityNameStrategy = NameStrategyEnum.UPPER_CAMEL_CASE;

    /**
     * entity类后缀
     */
    @Builder.Default
    private String entitySuffix = "Entity";

    /**
     * service类后缀
     */
    @Builder.Default
    private String serviceSuffix = "Service";

    /**
     * mapper类后缀
     */
    @Builder.Default
    private String mapperSuffix = "Mapper";

    /**
     * 是否忽略表前缀
     */
    private Boolean tablePrefixIgnore = Boolean.FALSE;

    /**
     * 表名前缀
     */
    private String tablePrefix;

    /**
     * 表名列表
     */
    private List<String> tables;
}
