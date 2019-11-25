package com.arkonrive.springmyadmin.utils.curd;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arkonrive.springmyadmin.utils.SQLBase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateSQL extends SQLBase {
    CreateSQL() { super(); }
    public static void createDatabase(String dbName, String username, String password) throws SQLException, ClassNotFoundException {
        String createSQL = "create database " + dbName;
        querySimpleSQL(username, password, "", createSQL);
    }

    public static void createTable(String dbName, String tableName, JSONArray attributes,
                                   String username, String password) throws SQLException, ClassNotFoundException {
        StringBuilder columnSQL = new StringBuilder();
        StringBuilder columnIndexSQL = new StringBuilder();
        boolean firstAttributeLine = true;
        for (Object iterator : attributes) {
            JSONObject attribute = (JSONObject) iterator;
            if (!firstAttributeLine) columnSQL.append(",\n");
            else firstAttributeLine = false;

            // NAME TYPE(LENGTH) NULLABLE DEFAULT default AUTO_INCREMENT COMMENT comment
            columnSQL.append(attribute.getString("name"));
            columnSQL.append(" ").append(attribute.getString("type"));

            if (!attribute.getString("length").isEmpty())
                columnSQL.append(String.format("(%s)", attribute.getString("length")));

            if (attribute.getString("nullable").compareTo("false") == 0)
                columnSQL.append(" not");
            columnSQL.append(" null");

            if (attribute.getString("default").length() > 0) {
                String _default = attribute.getString("default");
                if (_default.toUpperCase().compareTo("NULL") == 0 || _default.toUpperCase().compareTo("CURRENT_TIMESTAMP") == 0)
                    columnSQL.append(String.format(" default %s", attribute.getString("default")));
                else columnSQL.append(String.format(" default '%s'", attribute.getString("default")));
            }

            if (attribute.getString("auto_increment").compareTo("true") == 0)
                columnSQL.append(" auto_increment");

            if (attribute.getString("comment").length() > 0)
                columnSQL.append(String.format(" comment '%s'", attribute.getString("comment")));

            if (attribute.getString("index").length() > 0)
                columnIndexSQL.append(String.format(",\n%s (%s)", attribute.getString("index"), attribute.getString("name")));
        }
        String createSQL = String.format("create table %s\n(\n%s%s\n);", tableName, columnSQL, columnIndexSQL);

        querySimpleSQL(username, password, dbName, createSQL);
    }

    public static void insertData(String dbName, String tableName, JSONObject column_values,
                                  String username, String password) throws SQLException, ClassNotFoundException {
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        // INSERT INTO {table} ({column1}, {column2},...) VALUES ({value1}, {value2},....)
        for (String key : column_values.keySet()) {
            String value = column_values.getString(key); // 将json的key和value分离
            columns.add(key);
            values.add(value);
        }
        StringBuilder columnSQL = new StringBuilder();
        boolean first = true;
        for (String column : columns) {
            if (!first) columnSQL.append(", "); // columns
            else first = false;
            columnSQL.append(column);
        }
        StringBuilder valueSQL = new StringBuilder();
        first = true;
        for (String value : values) {
            if (!first) valueSQL.append(", "); // values
            else first = false;
            valueSQL.append(String.format("'%s'", value));
        }
        String createSQL = String.format("insert into %s (%s) values (%s)", tableName, columnSQL, valueSQL);

        querySimpleSQL(username, password, dbName, createSQL);
    }

}
