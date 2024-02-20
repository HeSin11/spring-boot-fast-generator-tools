package com.generator.tools.freemarker.template.instance;

import lombok.Data;

@Data
public class ResourcesTemplateInstance extends TemplateInstance{
    private Integer port;

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
