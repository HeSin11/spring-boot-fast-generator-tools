package com.generator.tools.freemarker.model;

import lombok.Data;

@Data
public class FieldInfo {

    private Boolean isPrimaryKey = Boolean.FALSE;

    private String columnType;

    private String columnName;

    private String fieldType;

    private String fieldName;

    /**
     * 字段备注
     */
    private String fieldRemark;

    private Boolean isFuzzy = Boolean.FALSE;

    private String fuzzyType;
}
