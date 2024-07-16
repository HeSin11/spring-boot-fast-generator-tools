package com.generator.tools.enums;

import lombok.Data;

@Data
public class FilterQuery {
    /**
     * 列名
     */
    private String columnName;

    /**
     * 表名
     */
    private String table;
}