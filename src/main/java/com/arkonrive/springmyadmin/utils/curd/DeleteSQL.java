package com.arkonrive.springmyadmin.utils.curd;

import com.alibaba.fastjson.JSONObject;
import com.arkonrive.springmyadmin.utils.SQLBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteSQL extends SQLBase {
    DeleteSQL() { super(); }

    public static void dropDatabase(String dbName, String username, String password) throws SQLException, ClassNotFoundException {
        String dropSQL = "drop database " + dbName;
        querySimpleSQL(username, password, "", dropSQL);
    }

    public static void dropTable(String dbName, String table,
                                 String username, String password) throws SQLException, ClassNotFoundException {
        String dropSQL = "drop table " + table;
        querySimpleSQL(username, password, dbName, dropSQL);
    }

    public static void deleteRow(String dbName, String tableName,
                                 String username, String password, JSONObject where) throws SQLException, ClassNotFoundException {
        String deleteSQL = "delete from " + tableName + Where2String(where);
        querySimpleSQL(username, password, dbName, deleteSQL);
    }

}
