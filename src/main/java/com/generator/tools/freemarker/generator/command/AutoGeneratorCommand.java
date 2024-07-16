package com.generator.tools.freemarker.generator.command;

import com.generator.tools.common.enums.NameStrategyEnum;
import com.generator.tools.freemarker.generator.config.*;
import com.generator.tools.freemarker.template.*;
import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.TemplateInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoGeneratorCommand {

    /**
     * 获取全局配置对象
     * @param generatorConfig
     * @return
     */
    public static GlobalDataConfig getGlobalDataConfig(GeneratorConfig generatorConfig) {
        return new GlobalDataConfig(generatorConfig);
    }

    /**
     * 指定某些模版生成
     * @param generatorTemplates
     * @throws Exception
     */
    public static void execute(List<AbstractGeneratorTemplate<? extends TemplateInstance>> generatorTemplates) throws Exception {
        generatorTemplates.forEach(generatorTemplate->{
            try {
                generatorTemplate.generator();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 直接生成整个项目
     * @param generatorConfig
     * @throws Exception
     */
    public static void execute(GeneratorConfig generatorConfig) throws Exception {
        GlobalDataConfig globalDataConfig = getGlobalDataConfig(generatorConfig);
        List<AbstractGeneratorTemplate<? extends TemplateInstance>> generatorTemplates = new ArrayList<>();
        generatorTemplates.add(new EntityTemplate(globalDataConfig));
        generatorTemplates.add(new ServiceTemplate(globalDataConfig));
        generatorTemplates.add(new MapperTemplate(globalDataConfig));
        generatorTemplates.add(new ServiceImplTemplate(globalDataConfig));
        generatorTemplates.add(new CommonConfigTemplate(globalDataConfig));
        generatorTemplates.add(new MybatisConfigTemplate(globalDataConfig));
        generatorTemplates.add(new QueryDTOTemplate(globalDataConfig));
        generatorTemplates.add(new ControllerTemplate(globalDataConfig));
        if (generatorConfig.getStrategyConfig().getSwagger()){
            generatorTemplates.add(new SwaggerConfigTemplate(globalDataConfig));
        }
        generatorTemplates.add(new PomTemplate(globalDataConfig));
        generatorTemplates.add(new MainClassTemplate(globalDataConfig));
        generatorTemplates.add(new ResourcesTemplate(globalDataConfig));
        generatorTemplates.forEach(generatorTemplate->{
            try {
                generatorTemplate.generator();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        GeneratorStrategyConfig strategyConfig = GeneratorStrategyConfig.builder()
                .entityNameStrategy(NameStrategyEnum.UPPER_CAMEL_CASE)
                .tablePrefixIgnore(Boolean.TRUE)

                .tables(Arrays.asList("metric_items","user_metric_items"))
                .entitySuffix("Entity")
                .serviceSuffix("Service")
                .swagger(Boolean.TRUE)
                .build();

        GeneratorPackageConfig packageConfig = GeneratorPackageConfig.builder()
                .parentPackagePath("/com/mall/")
                .projectName("mall")
                .entity("entity")
                .mapper("mapper")
                .service("service")
                .controller("controller")
                .build();

        GeneratorDataSourceConfig dataSourceConfig = GeneratorDataSourceConfig.builder()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .jdbcUrl("jdbc:mysql://47.99.98.244:3306/performance?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai")
                .username("root")
                .password("Xz993298")
                .database("performance")
                .build();


        GeneratePomConfig generatePomConfig = GeneratePomConfig.builder()
                .groupId("com.mall.dal")
                .artifactId("performance-manager")
                .mainClass("PerformanceManagerApplication")
                .build();

        GeneratorConfig generatorConfig = GeneratorConfig.builder()
                .strategyConfig(strategyConfig)
                .packageConfig(packageConfig)
                .dataSourceConfig(dataSourceConfig)
                .pomConfig(generatePomConfig)
                .build();
        execute(generatorConfig);
    }
}
