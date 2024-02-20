package com.generator.tools.freemarker.generator.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeneratorPackageConfig {

    /**
     * 生成结果存储路径
     */
    public static String baseResultPath;

    /**
     * 包路径
     */
    private String parentPackagePath;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * entity包名
     */
    private String entity;

    /**
     * mapper包名
     */
    private String mapper;

    /**
     * service包名
     */
    private String service;

    /**
     * controller 包名
     */
    private String controller;

    static {
        baseResultPath = System.getProperty("user.dir") + "/generate-code/";
    }
}
