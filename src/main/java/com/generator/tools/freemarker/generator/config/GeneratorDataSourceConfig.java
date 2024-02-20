package com.generator.tools.freemarker.generator.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeneratorDataSourceConfig {
    /**
     * 驱动名称
     */
    private String driverClassName;
    /**
     * 数据库连接
     */
    private String jdbcUrl;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 数据库名称
     */
    private String database;
}
