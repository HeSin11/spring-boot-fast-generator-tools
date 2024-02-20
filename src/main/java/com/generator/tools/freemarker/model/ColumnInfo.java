package com.generator.tools.freemarker.model;

import com.generator.tools.common.annotation.ResultSetMapping;
import lombok.Data;

@Data
public class ColumnInfo {

    /**
     * 数据库名称
     */
    @ResultSetMapping(column = "TABLE_SCHEMA")
    private String database;

    /**
     * 表名
     */
    @ResultSetMapping(column = "TABLE_NAME")
    private String tableName;

    /**
     * 列名
     */
    @ResultSetMapping(column = "DATA_TYPE")
    private String dataType;

    /**
     * 列类型 PRI：主键
     */
    @ResultSetMapping(column = "COLUMN_KEY")
    private String columnKey;

    /**
     * 列名
     */
    @ResultSetMapping(column = "COLUMN_NAME")
    private String columnName;

    /**
     * 备注
     */
    @ResultSetMapping(column = "COLUMN_COMMENT")
    private String columnComment;
}
