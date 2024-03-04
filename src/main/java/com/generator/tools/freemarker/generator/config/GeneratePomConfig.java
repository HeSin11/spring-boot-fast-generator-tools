package com.generator.tools.freemarker.generator.config;

import com.generator.tools.freemarker.template.instance.TemplateInstance;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeneratePomConfig{
    private String groupId;

    private String artifactId;

    @Builder.Default
    private String version = "1.0.0";

    @Builder.Default
    private String jdkVersion = "1.8";

    @Builder.Default
    private String springBootVersion = "2.6.13";

    @Builder.Default
    private String mysqlConnectorVersion = "8.0.31";

    @Builder.Default
    private String mybatisPlusVersion = "3.5.3.1";

    @Builder.Default
    private String springFoxStarterVersion = "3.0.0";

    @Builder.Default
    private String mainClass = "MainApplication";
}
