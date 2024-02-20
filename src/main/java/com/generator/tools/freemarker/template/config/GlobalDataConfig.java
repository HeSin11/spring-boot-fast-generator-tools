package com.generator.tools.freemarker.template.config;

import cn.hutool.core.util.StrUtil;
import com.generator.tools.common.enums.NameStrategyEnum;
import com.generator.tools.freemarker.generator.config.GeneratorConfig;
import com.generator.tools.freemarker.generator.config.GeneratorDataSourceConfig;
import com.generator.tools.freemarker.generator.config.GeneratorPackageConfig;
import com.generator.tools.freemarker.generator.config.GeneratorStrategyConfig;
import com.generator.tools.freemarker.model.ColumnInfo;
import com.generator.tools.freemarker.model.FieldInfo;
import com.generator.tools.util.DbUtil;
import com.generator.tools.util.PackagePathUtil;
import com.mysql.cj.MysqlType;
import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class GlobalDataConfig {

    private GeneratorConfig generatorConfig;

    protected Map<String, String> tableBaseNameMap = new HashMap<>();

    protected Map<String, List<ColumnInfo>> columnInfoMap = new HashMap<>();

    protected Map<String, List<FieldInfo>> filedInfoMap = new HashMap<>();

    protected Map<String, Set<String>> filedTypeMap = new HashMap<>();

    protected String baseProjectPath;

    protected String basePackageName;

    protected String basePackagePath;

    protected String baseResourcesPath;

    protected String entityPackageName;

    protected String entityPackagePath;

    protected String servicePackageName;

    protected String servicePackagePath;

    protected String serviceImplPackageName;

    protected String serviceImplPackagePath;

    protected String mapperPackageName;

    protected String mapperPackagePath;

    protected String controllerPackageName;

    protected String controllerPackagePath;

    private String commonPackagePath;

    private String commonPackageName;

    private String configPackagePath;

    private String configPackageName;

    private String queryPackageName;

    private String queryPackagePath;


    public GlobalDataConfig(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
        init();
    }

    void init(){
        GeneratorPackageConfig packageConfig = this.generatorConfig.getPackageConfig();
        Assert.notNull(packageConfig, "包配置不能为空");
        String projectName = this.generatorConfig.getPackageConfig().getProjectName();
        String parentPackagePath = this.generatorConfig.getPackageConfig().getParentPackagePath();
        if (StringUtils.hasText(parentPackagePath)){
            parentPackagePath= parentPackagePath.endsWith("/") ? parentPackagePath.substring(0, parentPackagePath.length() -1) : parentPackagePath;
        }

        this.baseResourcesPath =  StringUtils.hasText(projectName) ? GeneratorPackageConfig.baseResultPath + projectName + "/src/main/resources/" : GeneratorPackageConfig.baseResultPath + "src/main/resources/";

        if (StringUtils.hasText(projectName)){
            this.baseProjectPath = GeneratorPackageConfig.baseResultPath + projectName + "/";
            this.basePackagePath = projectName + (parentPackagePath.startsWith("/") ? "/src/main/java" + parentPackagePath : "/src/main/java/" + parentPackagePath);
        }else {
            this.baseProjectPath = GeneratorPackageConfig.baseResultPath;
            this.basePackagePath = parentPackagePath + "/";
        }

        this.basePackageName = PackagePathUtil.getPackageName(parentPackagePath);
        initTablesData();
        initPackageName();
    }



    private void initTablesData(){
        GeneratorDataSourceConfig dataSourceConfig = this.generatorConfig.getDataSourceConfig();
        GeneratorStrategyConfig strategyConfig = this.generatorConfig.getStrategyConfig();
        List<String> tables = strategyConfig.getTables();
        tables.forEach(table->{
            try {
                String baseName = table;
                if (strategyConfig.getTablePrefixIgnore()){
                    String tablePrefix = strategyConfig.getTablePrefix();
                    baseName = StringUtils.hasText(tablePrefix) ? baseName.replace(tablePrefix, "") : baseName;
                }
                NameStrategyEnum nameStrategyEnum = strategyConfig.getEntityNameStrategy();
                if (nameStrategyEnum == NameStrategyEnum.UPPER_CAMEL_CASE){
                    baseName = StrUtil.toCamelCase(baseName);
                }
                tableBaseNameMap.put(table, baseName);

                List<ColumnInfo> columns = DbUtil.getTableColumns(dataSourceConfig, dataSourceConfig.getDatabase(), table);
                this.columnInfoMap.put(table, columns);

                List<FieldInfo> fields = getFields(columns, strategyConfig.getEntityNameStrategy());
                filedInfoMap.put(baseName, fields);

                if (!CollectionUtils.isEmpty(fields)){
                    filedTypeMap.put(table, fields.stream().map(FieldInfo::getFieldType).collect(Collectors.toSet()));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void initPackageName(){
        String entity = this.generatorConfig.getPackageConfig().getEntity();
        String mapper = this.generatorConfig.getPackageConfig().getMapper();
        String service = this.generatorConfig.getPackageConfig().getService();
        String controller = this.generatorConfig.getPackageConfig().getController();
        this.entityPackageName = StringUtils.hasText(entity) ? this.basePackageName + "." + entity : this.basePackageName;
        this.mapperPackageName = StringUtils.hasText(mapper) ? this.basePackageName + "." + mapper : this.basePackageName;
        this.servicePackageName = StringUtils.hasText(service) ? this.basePackageName + "." + service : this.basePackageName;
        this.serviceImplPackageName = StringUtils.hasText(service) ? this.basePackageName + "." + service  + ".impl" : this.basePackageName;
        this.controllerPackageName = StringUtils.hasText(controller) ? this.basePackageName + "." + controller : this.basePackageName;
        this.commonPackageName = this.basePackageName + "." + "common";
        this.configPackageName = this.basePackageName + "." + "config";
        this.queryPackageName = this.basePackageName + "." + "query";

        this.entityPackagePath = PackagePathUtil.getFilePath(basePackagePath, this.getGeneratorConfig().getPackageConfig().getEntity());
        this.servicePackagePath = PackagePathUtil.getFilePath(basePackagePath, this.getGeneratorConfig().getPackageConfig().getService());
        this.serviceImplPackagePath = PackagePathUtil.getFilePath(basePackagePath, this.getGeneratorConfig().getPackageConfig().getService() + "/impl");
        this.mapperPackagePath = PackagePathUtil.getFilePath(basePackagePath, this.getGeneratorConfig().getPackageConfig().getMapper());
        this.controllerPackagePath = PackagePathUtil.getFilePath(basePackagePath, this.getGeneratorConfig().getPackageConfig().getController());
        this.commonPackagePath = PackagePathUtil.getFilePath(basePackagePath, "/common");
        this.configPackagePath = PackagePathUtil.getFilePath(basePackagePath, "/config");
        this.queryPackagePath = PackagePathUtil.getFilePath(basePackagePath, "/query");
    }

    private static List<FieldInfo> getFields(List<ColumnInfo> columns, NameStrategyEnum nameStrategyEnum) throws Exception {
        return columns.stream().map(columnInfo -> {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setFieldName(nameStrategyEnum == NameStrategyEnum.UPPER_CAMEL_CASE ? StrUtil.toCamelCase(columnInfo.getColumnName()) : columnInfo.getColumnName());
            fieldInfo.setIsPrimaryKey(columnInfo.getColumnKey().equals("PRI"));
            fieldInfo.setFieldRemark(columnInfo.getColumnComment());
            String dataType = columnInfo.getDataType().toUpperCase();
            MysqlType mysqlType = MysqlType.getByName(dataType);
            try {
                fieldInfo.setFieldType(Class.forName(mysqlType.getClassName()).getSimpleName());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            fieldInfo.setColumnType(dataType);
            fieldInfo.setColumnName(columnInfo.getColumnName());
            return fieldInfo;
        }).collect(Collectors.toList());
    }



    public String getEntityName(String table){
        GeneratorStrategyConfig strategyConfig = this.generatorConfig.getStrategyConfig();
        String baseName = this.tableBaseNameMap.get(table);
        return generatorClassName(baseName, strategyConfig.getEntitySuffix());
    }

    public String getPageQueryName(String table){
        String baseName = this.tableBaseNameMap.get(table);
        String pageQueryName = baseName + "PageQuery";
        return generatorClassName(pageQueryName, null);
    }

    public String getQueryName(String table){
        String baseName = this.tableBaseNameMap.get(table);
        String queryName = baseName + "Query";
        return generatorClassName(queryName, null);
    }

    public String getServiceName(String table){
        GeneratorStrategyConfig strategyConfig = this.generatorConfig.getStrategyConfig();
        String baseName = this.tableBaseNameMap.get(table);
        return generatorClassName(baseName, strategyConfig.getServiceSuffix());
    }

    public String getServiceImplName(String table){
        GeneratorStrategyConfig strategyConfig = this.generatorConfig.getStrategyConfig();
        String baseName = this.tableBaseNameMap.get(table);
        return generatorClassName(baseName, strategyConfig.getServiceSuffix()) + "Impl";
    }

    public String getControllerName(String table){
        String baseName = this.tableBaseNameMap.get(table);
        return generatorClassName(baseName, null) + "Controller";
    }

    public String getMapperName(String table){
        GeneratorStrategyConfig strategyConfig = this.generatorConfig.getStrategyConfig();
        String baseName = this.tableBaseNameMap.get(table);
        return generatorClassName(baseName, strategyConfig.getMapperSuffix());
    }

    public String generatorClassName(String baseName, String suffix){
        String otherChar = baseName.substring(1);
        String firstChar = baseName.substring(0, 1);
        baseName = firstChar.toUpperCase() + otherChar;
        return StringUtils.hasText(suffix) ? baseName + suffix : baseName;
    }
}
