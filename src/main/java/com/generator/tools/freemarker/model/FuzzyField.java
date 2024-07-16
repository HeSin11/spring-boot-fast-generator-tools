package com.generator.tools.freemarker.model;

import com.generator.tools.enums.FuzzyTypeEnum;
import lombok.Data;

@Data
public class FuzzyField {
    /**
     * 表名
     */
    private String table;
    /**
     * 列名
     */
    private String columnName;

    /**
     * 模糊查询类型
     */
    private FuzzyTypeEnum fuzzyType;
}