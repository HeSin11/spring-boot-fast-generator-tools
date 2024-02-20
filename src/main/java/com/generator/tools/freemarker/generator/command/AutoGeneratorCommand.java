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


    public static void execute(GeneratorConfig generatorConfig) throws Exception {
        GlobalDataConfig globalDataConfig = new GlobalDataConfig(generatorConfig);
        List<AbstractGeneratorTemplate<? extends TemplateInstance>> generatorTemplates = new ArrayList<>();
        generatorTemplates.add(new EntityTemplate(globalDataConfig));
        generatorTemplates.add(new ServiceTemplate(globalDataConfig));
        generatorTemplates.add(new MapperTemplate(globalDataConfig));
        generatorTemplates.add(new ServiceImplTemplate(globalDataConfig));
        generatorTemplates.add(new CommonConfigTemplate(globalDataConfig));
        generatorTemplates.add(new MybatisConfigTemplate(globalDataConfig));
        generatorTemplates.add(new QueryDTOTemplate(globalDataConfig));
        generatorTemplates.add(new ControllerTemplate(globalDataConfig));
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
                .tablePrefix("t_")
                .tables(Arrays.asList("t_test"))
                .entitySuffix("Entity")
                .serviceSuffix("Service")
                .build();

        GeneratorPackageConfig packageConfig = GeneratorPackageConfig.builder()
                .parentPackagePath("/com/generator/tools/")
                .projectName("generator-demo")
                .entity("entity")
                .mapper("mapper")
                .service("service")
                .controller("controller")
                .build();

        GeneratorDataSourceConfig dataSourceConfig = GeneratorDataSourceConfig.builder()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .jdbcUrl("jdbc:mysql://localhost:3306/generator_test")
                .username("root")
                .password("12345678")
                .database("generator_test")
                .build();


        GeneratePomConfig generatePomConfig = GeneratePomConfig.builder()
                .groupId("com.generator.demo")
                .artifactId("generator-demo")
                .mainClass("GeneratorApplication")
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
