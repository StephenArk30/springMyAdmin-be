package com.arkonrive.springmyadmin.utils.curd;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arkonrive.springmyadmin.utils.Attribute;
import com.arkonrive.springmyadmin.utils.Logger;
import com.arkonrive.springmyadmin.utils.SQLBase;

import java.sql.*;
import java.util.List;

public class ReadSQL extends SQLBase {

    ReadSQL() { super(); }

    public static JSONArray getDatabases(String username, String password) throws SQLException, ClassNotFoundException {

        Connection conn = null;
        Statement stmt = null;
        JSONArray databases = new JSONArray();
        // 打开链接
        conn = createConnection("",username,password);

        // 执行查询
        stmt = conn.createStatement();
        ResultSet rs = conn.getMetaData().getCatalogs();

        // 展开结果集数据库
        while(rs.next()){
//            System.out.println(rs.getString("TABLE_CAT"));
            databases.add(rs.getString("TABLE_CAT"));
        }
        // 完成后关闭
        rs.close();
        stmt.close();
        conn.close();
        return databases;
    }

    public static JSONArray getTables(String dbName, String username, String password) throws SQLException, ClassNotFoundException {

        Connection conn = null;
        Statement stmt = null;
        JSONArray tables = new JSONArray();
        // 打开链接
        conn = createConnection(dbName,username,password);

        // 执行查询
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("show tables");
        String columnName = "Tables_in_" + dbName;

        // 展开结果集数据库
        while(rs.next()){
//            System.out.println(rs.getString(columnName));
            tables.add(rs.getString(columnName));
        }
        // 完成后关闭
        rs.close();
        stmt.close();
        conn.close();
        return tables;
    }

    public static JSONArray getData(String dbName, String tableName,
                                       String username, String password,
                                       JSONObject where, Integer limit, Integer offset,
                                       String orderBy, String order) throws SQLException, ClassNotFoundException {

        Connection conn = null;
        Statement stmt = null;
        JSONArray dataset = new JSONArray();
        // 打开链接
        conn = createConnection(dbName,username,password);

        // 执行查询
        stmt = conn.createStatement();
        String selectSQL = "select * from " + tableName;
        if (where != null) selectSQL += Where2String(where);
        if (orderBy != null) selectSQL += String.format(" order by %s", orderBy);
        if (order != null && order.compareTo("desc") == 0) selectSQL += " desc";
        if (limit != null && limit > 0) selectSQL += String.format(" limit %d", limit);
        if (offset != null && offset > 0) selectSQL += String.format(" offset %d", offset);
        List<Attribute> attributes = getAttributes(stmt, tableName, dbName); // 获取列名和类别
        Logger.log(selectSQL);
        ResultSet rs = stmt.executeQuery(selectSQL);

        // 展开结果集数据库
        while(rs.next()){
            JSONObject row = new JSONObject();
            // 对每一个属性，判断其类别，放入json中
            for (Attribute attribute : attributes) {
                String columnName = attribute.getCOLUMN_NAME();
                String columnType = attribute.getTypeName();
                if (columnType.compareTo("int") == 0) {
                    row.put(columnName, rs.getInt(columnName));
                } else if (columnType.compareTo("varchar") == 0) {
                    row.put(columnName, rs.getString(columnName));
                } else if (columnType.compareTo("float") == 0) {
                    row.put(columnName, rs.getFloat(columnName));
                }
            }
            dataset.add(row); // 加入json列表
        }
        // 完成后关闭
        rs.close();
        stmt.close();
        conn.close();
        return dataset;
    }

    public static int getRowCount(String dbName, String tableName,
                                       String username, String password) throws SQLException, ClassNotFoundException {
        int count = 0;
        Connection conn = null;
        Statement stmt = null;
        // 打开链接
        conn = createConnection(dbName,username,password);

        // 执行查询
        stmt = conn.createStatement();
        String selectSQL = "select count(*) as row_count from " + tableName;
        Logger.log(selectSQL);
        ResultSet rs = stmt.executeQuery(selectSQL);

        // 展开结果集数据库
        while(rs.next()){
            count = rs.getInt("row_count");
        }
        // 完成后关闭
        rs.close();
        stmt.close();
        conn.close();
        return count;
    }

    public static JSONArray getColums(String dbName, String tableName,
                                       String username, String password) throws SQLException, ClassNotFoundException {
        JSONArray columns = new JSONArray();
        Connection conn = null;
        Statement stmt = null;
        // 打开链接
        conn = createConnection(dbName,username,password);

        // 执行查询
        stmt = conn.createStatement();
        List<Attribute> attributes = getAttributes(stmt, tableName, dbName);
        for (Attribute attribute : attributes) {
            columns.add(attribute.toJSONObject());
        }

        stmt.close();
        conn.close();
        return columns;
    }
}
