package com.arkonrive.springmyadmin.utils.curd;

import com.alibaba.fastjson.JSONObject;
import com.arkonrive.springmyadmin.utils.SQLBase;

import java.sql.SQLException;

public class UpdateSQL extends SQLBase {
    UpdateSQL() { super(); }

    // update {table} set {column}={value} where {}
    public static void updateRow(String dbName, String tableName,
                                  String username, String password,
                                  JSONObject column_values, JSONObject where) throws SQLException, ClassNotFoundException {
        StringBuilder columnValueSQL = new StringBuilder();
        boolean first = true;
        for (String key : column_values.keySet()) {
            String value = where.getString(key);
//            System.out.println(value);
            if (!first) columnValueSQL.append(", ");
            else first = false;
            columnValueSQL.append(key).append("=").append(value);
        }
        String updateSQL = String.format("update %s set %s%s", tableName, columnValueSQL, Where2String(where));
        querySimpleSQL(username, password, dbName, updateSQL);
    }
}
