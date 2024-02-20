package com.generator.tools.util;

import com.generator.tools.freemarker.generator.config.GeneratorDataSourceConfig;
import com.generator.tools.freemarker.model.ColumnInfo;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DbUtil {

    private static final Map<String, HikariDataSource> hikariDataSource = new ConcurrentHashMap<>();

    private static Connection getConnection(String jdbcUrl, String driverClassName, String username, String password) throws SQLException {
        if (hikariDataSource.containsKey(jdbcUrl)) {
            HikariDataSource dataSource = hikariDataSource.get(jdbcUrl);
            return dataSource.getConnection();
        }else {
            log.info("创建数据库连接");
            HikariDataSource dataSource = doGetConnection(jdbcUrl, driverClassName, username, password);
            hikariDataSource.put(jdbcUrl, dataSource);
            return dataSource.getConnection();
        }
    }

    private static HikariDataSource doGetConnection(String jdbcUrl, String driverClassName, String username, String password) throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl(jdbcUrl);
        //活跃连接探测
        hikariConfig.setKeepaliveTime(90000L);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }

    private static PreparedStatement getPreparedStatement(Connection con,  String sql, List<Object> params) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        if (CollectionUtils.isEmpty(params)){
            return preparedStatement;
        }
        for (int i = 0; i < params.size(); i++) {
            preparedStatement.setObject(i + 1, params.get(i));
        }
        return preparedStatement;
    }

    /* 关闭数据库
     * @param con
     * @param stm
     * @param rs
     */
    public static void close(Connection con, Statement stm, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("rs关闭失败",e);
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                log.error("stm关闭失败",e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.error("con关闭失败",e);
            }
        }
    }

    public static List<String> getTableNames(GeneratorDataSourceConfig dataSourceConfig, String database) throws SQLException{
        Connection connection = DbUtil.getConnection(dataSourceConfig.getJdbcUrl(),
                dataSourceConfig.getDriverClassName(),
                dataSourceConfig.getUsername(),
                dataSourceConfig.getPassword());
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String SQL =  "select * from information_schema.TABLES WHERE TABLE_SCHEMA = ?";
        List<Object> params = new ArrayList<>();
        params.add(database);

        try {
            preparedStatement = getPreparedStatement(connection, SQL, params);
            rs = preparedStatement.executeQuery();
            List<String> tables = new ArrayList<>();
            if (rs != null){
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    tables.add(tableName);
                }
            }
            return tables;
        }finally {
            DbUtil.close(connection, preparedStatement, rs);
        }
    }

    public static List<ColumnInfo> getTableColumns(GeneratorDataSourceConfig dataSourceConfig, String database, String tableName) throws Exception{
        Connection connection = DbUtil.getConnection(dataSourceConfig.getJdbcUrl(),
                dataSourceConfig.getDriverClassName(),
                dataSourceConfig.getUsername(),
                dataSourceConfig.getPassword());
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String SQL = "select * from information_schema.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
        List<Object> params = new ArrayList<>();
        params.add(database);
        params.add(tableName);

        try {
            preparedStatement = getPreparedStatement(connection, SQL, params);
            rs = preparedStatement.executeQuery();
            List<ColumnInfo> result = ResultSetConvert.getResult(ColumnInfo.class, rs);
            return result;
        }finally {
            DbUtil.close(connection, preparedStatement, rs);
        }
    }

    public static void main(String[] args) throws Exception {
        GeneratorDataSourceConfig dataSourceConfig = GeneratorDataSourceConfig.builder()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .jdbcUrl("jdbc:mysql://localhost:3306/generator_test")
                .username("root")
                .password("12345678").build();
        List<ColumnInfo> tableColumns = getTableColumns(dataSourceConfig, "generator_test", "test");
        System.out.println(tableColumns);
    }
}
