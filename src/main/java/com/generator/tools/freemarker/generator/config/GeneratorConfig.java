package com.generator.tools.freemarker.generator.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeneratorConfig {

    /**
     * 数据源配置
     */
    private GeneratorDataSourceConfig dataSourceConfig;

    /**
     * 包配置
     */
    private GeneratorPackageConfig packageConfig;

    /**
     * 策略配置
     */
    private GeneratorStrategyConfig strategyConfig;

    /**
     * pom文件配置
     */
    private GeneratePomConfig pomConfig;
}
