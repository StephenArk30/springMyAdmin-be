package com.arkonrive.springmyadmin.utils.curd;

import com.alibaba.fastjson.JSONObject;
import com.arkonrive.springmyadmin.utils.Attribute;
import com.arkonrive.springmyadmin.utils.SQLBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreateSQL extends SQLBase {
    CreateSQL() { super(); }
    public static void createDatabase(String dbName, String username, String password) throws SQLException, ClassNotFoundException {
        String createSQL = "create database " + dbName;
        querySimpleSQL(username, password, "", createSQL); // dbName isn't required
    }

    public static void createTable(String dbName, String tableName, List<Attribute> attributes,
                                   String username, String password) throws SQLException, ClassNotFoundException {
        StringBuilder columnSQL = new StringBuilder();
        boolean first = true;
        for (Attribute attribute : attributes) {
            if (!first) columnSQL.append(",\n");
            else first = false;
            columnSQL.append(attribute.getCOLUMN_NAME());
            columnSQL.append(" ").append(attribute.getCOLUMN_TYPE());
            if (!attribute.getIS_NULLABLE().isEmpty()) {
                columnSQL.append(" ").append(attribute.getIS_NULLABLE());
            }
            if (!attribute.getCOLUMN_DEFAULT().isEmpty()) {
                columnSQL.append(" default ").append(attribute.getCOLUMN_DEFAULT());
            }
            if (!attribute.getCOLUMN_COMMENT().isEmpty()) {
                columnSQL.append(" comment ").append(attribute.getCOLUMN_COMMENT());
            }
        }
        String createSQL = String.format("create table %s\n(\n%s\n);", tableName, columnSQL);

        querySimpleSQL(username, password, dbName, createSQL);
    }

//    insert into {table} ({column1}, {column2},...) VALUES ({value1}, {value2},....)
    public static void insertData(String dbName, String tableName, JSONObject column_values,
                                  String username, String password) throws SQLException, ClassNotFoundException {
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (String key : column_values.keySet()) {
            String value = column_values.getString(key);
            columns.add(key);
            values.add(value);
        }

        StringBuilder columnSQL = new StringBuilder();
        boolean first = true;
        for (String column : columns) {
            if (!first) columnSQL.append(", ");
            else first = false;
            columnSQL.append(column);
        }
        StringBuilder valueSQL = new StringBuilder();
        first = true;
        for (String value : values) {
            if (!first) valueSQL.append(", ");
            else first = false;
            valueSQL.append(String.format("'%s'", value));
        }
        String createSQL = String.format("insert into %s (%s) values (%s)", tableName, columnSQL, valueSQL);

        querySimpleSQL(username, password, dbName, createSQL);
    }

}
