package com.arkonrive.springmyadmin.utils;
import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLBase {

    protected static Connection createConnection(String dbName, String username, String password) throws SQLException, ClassNotFoundException {
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//        String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // below mysql8.0
        String DB_URL = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&serverTimezone=UTC";
//        String DB_URL = "jdbc:mysql://localhost:3306/" + dbName; // below mysql8.0

        Connection conn = null;
        // 注册 JDBC 驱动
        Class.forName(JDBC_DRIVER);
        // 打开链接
        conn = DriverManager.getConnection(DB_URL,username,password);
        return conn;
    }

    protected static List<Attribute> getAttributes(Statement stmt, String tableName, String schemaName) throws SQLException {
        String getColumnSql = "select COLUMN_NAME,COLUMN_TYPE,COLUMN_DEFAULT,IS_NULLABLE,COLUMN_KEY,COLUMN_COMMENT "
                + "from information_schema.COLUMNS where table_name = '" + tableName + "' and table_schema='" + schemaName + "'";
        ResultSet rs = stmt.executeQuery(getColumnSql);
        List<Attribute> attributes = new ArrayList<>();
        // 展开结果集数据库
        while(rs.next()){
            // 通过字段检索
            String COLUMN_NAME  = rs.getString("COLUMN_NAME");
            String COLUMN_TYPE = rs.getString("COLUMN_TYPE");
            String COLUMN_DEFAULT = rs.getString("COLUMN_DEFAULT");
            String COLUMN_KEY = rs.getString("COLUMN_KEY");
            String IS_NULLABLE = rs.getString("IS_NULLABLE");
            String COLUMN_COMMENT = rs.getString("COLUMN_COMMENT");
            Attribute attribute = new Attribute(COLUMN_NAME, COLUMN_TYPE, COLUMN_DEFAULT, COLUMN_KEY, IS_NULLABLE, COLUMN_COMMENT);
//            System.out.println(attribute);
            attributes.add(attribute);
        }
        rs.close();
        return attributes;
    }

    protected static StringBuilder Where2String(JSONObject where) {
        StringBuilder stringWhere = new StringBuilder();
        boolean first = true;
        for (String key : where.keySet()) {
            String value = where.getString(key);
//            System.out.println(value);
            if (first) {
                stringWhere.append(String.format(" where %s='%s'", key, value));
                first = false;
            }
            else stringWhere.append(String.format(" and %s='%s'", key, value));
        }
        return stringWhere;
    }

    protected static void querySimpleSQL(String username, String password, String dbName, String sql) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        // 打开链接
        conn = createConnection(dbName, username, password); // dbName isn't required

        // 执行查询
        stmt = conn.createStatement();
        Logger.log(sql);
        stmt.execute(sql);
        // 完成后关闭
        stmt.close();
        conn.close();
    }

    public static boolean authUser(String username, String password) {
        Connection conn = null;
        try {
            conn = createConnection("", username, password); // dbName isn't required
            conn.close();
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }

}
