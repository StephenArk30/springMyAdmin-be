package com.arkonrive.springmyadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.arkonrive.springmyadmin.utils.CookieUtil;
import com.arkonrive.springmyadmin.utils.Util;
import com.arkonrive.springmyadmin.utils.curd.DeleteSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Controller
@RequestMapping("/delete")
public class DeleteController {
    @RequestMapping(value="/dropDB", method= RequestMethod.GET)
    @ResponseBody
    public JSONObject dropDatabase(HttpServletRequest request, HttpServletResponse response, String database) {
        String[] userInfo = CookieUtil.getUserInfo(request);
        if (userInfo == null) return Util.add2JSON("err", "no cookies");
        String username = userInfo[0];
        String password = userInfo[1];

        try {
            DeleteSQL.dropDatabase(database, username, password);
            return Util.add2JSON("res", "delete database " + database);
        } catch (SQLException e) {
            response.setStatus(400);
            return Util.add2JSON("err", e.getMessage());
        } catch (ClassNotFoundException e) {
            response.setStatus(404);
            return Util.add2JSON("err", e.getMessage());
        }
    }

    @RequestMapping(value="/dropTable", method= RequestMethod.GET)
    @ResponseBody
    public JSONObject dropTable(HttpServletRequest request, HttpServletResponse response, String database, String table) {
        String[] userInfo = CookieUtil.getUserInfo(request);
        if (userInfo == null) return Util.add2JSON("err", "no cookies");
        String username = userInfo[0];
        String password = userInfo[1];
        if (database == null || table == null) return Util.add2JSON("err", "invalid database or table");

        try {
            DeleteSQL.dropTable(database, table, username, password);
            return Util.add2JSON("res", "delete table " + table);
        } catch (SQLException e) {
            response.setStatus(400);
            return Util.add2JSON("err", e.getMessage());
        } catch (ClassNotFoundException e) {
            response.setStatus(404);
            return Util.add2JSON("err", e.getMessage());
        }
    }

    @RequestMapping(value="/deleteRow", method= RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteRow(HttpServletRequest request, HttpServletResponse response, @RequestBody String data){
        String[] userInfo = CookieUtil.getUserInfo(request);
        if (userInfo == null) return Util.add2JSON("err", "no cookies");
        String username = userInfo[0];
        String password = userInfo[1];
        String database = JSONObject.parseObject(data).getString("database");
        String table = JSONObject.parseObject(data).getString("table");
        JSONObject where = JSONObject.parseObject(data).getJSONObject("where");
        if (database == null || table == null) return Util.add2JSON("err", "invalid database or table");

        try {
            DeleteSQL.deleteRow(database, table, username, password, where);
            return Util.add2JSON("res", "delete 1 row");
        } catch (SQLException e) {
            response.setStatus(400);
            return Util.add2JSON("err", e.getMessage());
        } catch (ClassNotFoundException e) {
            response.setStatus(404);
            return Util.add2JSON("err", e.getMessage());
        }
    }
}
