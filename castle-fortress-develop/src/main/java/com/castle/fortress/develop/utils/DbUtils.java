package com.castle.fortress.develop.utils;

import com.castle.fortress.develop.entity.DevColConfig;
import com.castle.fortress.develop.entity.DevDbConfig;
import com.castle.fortress.develop.entity.DevTbConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DB工具类
 *
 * @author castle
 */
@Slf4j
public class DbUtils {
    private static final int CONNECTION_TIMEOUTS_SECONDS = 6;
    private static final String QUERY_ALL_TABLES="select table_name, table_comment from information_schema.tables where table_schema = (select database()) order by table_name asc";
    private static final String QUERY_TABLE_INFO="select table_name, table_comment from information_schema.tables where table_schema = (select database()) and table_name = ?";
    private static final String QUERY_TABLE_COLUMNS="select column_name, data_type, column_comment, column_key from information_schema.columns where table_name = ? and table_schema = (select database()) order by ordinal_position";
    private static final Map<String,String> colTypeMap=new HashMap<>();
    private static final Map<String,String> properPackageMap=new HashMap<>();
    static {
        colTypeMap.put("int","Integer");
        colTypeMap.put("tinyint","Boolean");
        colTypeMap.put("bigint","Long");
        colTypeMap.put("float","Float");
        colTypeMap.put("double","Double");
        colTypeMap.put("decimal","BigDecimal");
        colTypeMap.put("date","Date");
        colTypeMap.put("datetime","Date");
        colTypeMap.put("timestamp","Timestamp");
        colTypeMap.put("time","Time");
        colTypeMap.put("binary","byte[]");
        colTypeMap.put("varbinary","byte[]");
        colTypeMap.put("tinyblob","byte[]");
        colTypeMap.put("blob","byte[]");
        colTypeMap.put("mediumblob","byte[]");
        colTypeMap.put("longblob","byte[]");
        colTypeMap.put("longtext","String");
        colTypeMap.put("mediumtext","String");
        colTypeMap.put("text","String");
        colTypeMap.put("tinytext","String");
        colTypeMap.put("varchar","String");
        colTypeMap.put("char","String");

        properPackageMap.put("Date","java.util.Date");
        properPackageMap.put("BigDecimal","java.math.BigDecimal");
        properPackageMap.put("Timestamp","java.sql.Timestamp");
    }

    /**
     * 获得数据库连接
     * @param dbConfig
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(DevDbConfig dbConfig) throws ClassNotFoundException, SQLException {
        DriverManager.setLoginTimeout(CONNECTION_TIMEOUTS_SECONDS);
        Class.forName(dbConfig.getDbDriverName());
        Connection connection = DriverManager.getConnection(dbConfig.getDbUrl(), dbConfig.getDbUsername(), dbConfig.getDbPassword());
        return connection;
    }


    /**
     * 获取数据库表信息
     */
    public static DevTbConfig getTableInfo(DevDbConfig dbConfig, String tableName) {
        try {
            Connection conn = getConnection(dbConfig);
            //查询数据
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_TABLE_INFO);
            preparedStatement.setString(1,tableName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                DevTbConfig tbConfig = new DevTbConfig();
                tbConfig.setTbName(rs.getString("table_name"));
                tbConfig.setTbDesc(rs.getString("table_comment"));
                tbConfig.setDbId(dbConfig.getId());
                return tbConfig;
            }
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取所有的数据库表
     */
    public static List<DevTbConfig> getAllTables(DevDbConfig dbConfig) {
        List<DevTbConfig> tbConfigs = new ArrayList<>();
        try {
            Connection conn = getConnection(dbConfig);
            //查询数据
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_ALL_TABLES);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                DevTbConfig tbConfig = new DevTbConfig();
                tbConfig.setTbName(rs.getString("table_name"));
                tbConfig.setTbDesc(rs.getString("table_comment"));
                tbConfig.setDbId(dbConfig.getId());
                tbConfigs.add(tbConfig);
            }
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return tbConfigs;
    }


    /**
     * 获取表的列属性
     * @param dbConfig
     * @param tbId
     * @param tableName
     * @return
     */
    public static List<DevColConfig> getTableColumns(DevDbConfig dbConfig, Long tbId, String tableName) {
        List<DevColConfig> colConfigList = new ArrayList<>();
        try {
            Connection conn = getConnection(dbConfig);
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_TABLE_COLUMNS);
            preparedStatement.setString(1,tableName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                DevColConfig colConfig = new DevColConfig();
                colConfig.setTbId(tbId);
                colConfig.setColName(rs.getString("column_name"));
                colConfig.setColType(rs.getString("data_type").trim());
                colConfig.setPropDesc(rs.getString("column_comment"));
                colConfigList.add(colConfig);
            }
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return colConfigList;
    }

    /**
     * 获取数据库对应的java类型
     * @param colType
     * @return
     */
    public static String getProType(String colType){
        String proType=colTypeMap.get(colType.toLowerCase());
        if(Strings.isEmpty(proType)){
            proType="Object";
        }
        return proType;
    }

    public static String getProperPackages(String propType){
        return properPackageMap.get(propType);
    }

}
