package com.arkonrive.springmyadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.arkonrive.springmyadmin.utils.Attribute;
import com.arkonrive.springmyadmin.utils.CookieUtil;
import com.arkonrive.springmyadmin.utils.Util;
import com.arkonrive.springmyadmin.utils.curd.CreateSQL;
import com.arkonrive.springmyadmin.utils.curd.ReadSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/create")
public class CreateController {

    @RequestMapping(value="/createDB", method= RequestMethod.GET)
    @ResponseBody
    public JSONObject createDatabase(HttpServletRequest request, HttpServletResponse response, String database) {
        String[] userInfo = CookieUtil.getUserInfo(request);
        if (userInfo == null) return Util.add2JSON("err", "no cookies");
        String username = userInfo[0];
        String password = userInfo[1];

        try {
            CreateSQL.createDatabase(database, username, password);
            return Util.add2JSON("res", "create database " + database);
        } catch (SQLException e) {
            response.setStatus(400);
            return Util.add2JSON("err", e.getMessage());
        } catch (ClassNotFoundException e) {
            response.setStatus(404);
            return Util.add2JSON("err", e.getMessage());
        }
    }

    @RequestMapping(value="/createTable", method= RequestMethod.POST)
    @ResponseBody
    public JSONObject createTable(HttpServletRequest request, HttpServletResponse response, @RequestBody String data) {
        String[] userInfo = CookieUtil.getUserInfo(request);
        if (userInfo == null) return Util.add2JSON("err", "no cookies");
        String username = userInfo[0];
        String password = userInfo[1];
        String database = JSONObject.parseObject(data).getString("database");
        String table = JSONObject.parseObject(data).getString("table");
        JSONObject table_data = JSONObject.parseObject(data).getJSONObject("data");
        if (database == null || table == null) return Util.add2JSON("err", "invalid database or table");

        try {
            CreateSQL.createTable(database, table, Util.JSONObject2Array(table_data), username, password);
            return Util.add2JSON("res", "create table " + table);
        } catch (SQLException e) {
            response.setStatus(400);
            return Util.add2JSON("err", e.getMessage());
        } catch (ClassNotFoundException e) {
            response.setStatus(404);
            return Util.add2JSON("err", e.getMessage());
        }
    }

    @RequestMapping(value="/insertRow", method= RequestMethod.POST)
    @ResponseBody
    public JSONObject insertRow(HttpServletRequest request, HttpServletResponse response, @RequestBody String data){
        String[] userInfo = CookieUtil.getUserInfo(request);
        if (userInfo == null) return Util.add2JSON("err", "no cookies");
        String username = userInfo[0];
        String password = userInfo[1];
        String database = JSONObject.parseObject(data).getString("database");
        String table = JSONObject.parseObject(data).getString("table");
        JSONObject row_data = JSONObject.parseObject(data).getJSONObject("data");
        if (database == null || table == null) return Util.add2JSON("err", "invalid database or table");

        try {
            CreateSQL.insertData(database, table, row_data, username, password);
            return Util.add2JSON("res", "insert 1 row");
        } catch (SQLException e) {
            response.setStatus(400);
            return Util.add2JSON("err", e.getMessage());
        } catch (ClassNotFoundException e) {
            response.setStatus(404);
            return Util.add2JSON("err", e.getMessage());
        }
    }
}
