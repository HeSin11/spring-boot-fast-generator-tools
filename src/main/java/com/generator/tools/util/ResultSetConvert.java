package com.generator.tools.util;

import com.generator.tools.common.annotation.ResultSetMapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResultSetConvert<T> {
    public static <T> List<T> getResult(Class<T> clazz, ResultSet rs) throws Exception {
        if (rs == null) {
            return null;
        }
        List<T> resultList = new ArrayList<>();
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> fields = new ArrayList<>(Arrays.asList(declaredFields));
        Map<String, Field> columnGroup = fields.stream().filter(field -> {
            field.setAccessible(Boolean.TRUE);
            return field.getAnnotation(ResultSetMapping.class) != null;
        }).collect(Collectors.toMap(field -> field.getAnnotation(ResultSetMapping.class).column(), Function.identity(), (v1, v2) -> v1));
        Set<String> columnNames = columnGroup.keySet();
        while (rs.next()) {
            T t = constructor.newInstance();
            columnNames.forEach(columnName->{
                try {
                    String columnValue = rs.getString(columnName);
                    Field field = columnGroup.get(columnName);
                    field.set(t, columnValue);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            resultList.add(t);
        }
        return resultList;
    }
}
