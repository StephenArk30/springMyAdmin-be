package com.arkonrive.springmyadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.arkonrive.springmyadmin.utils.CookieUtil;
import com.arkonrive.springmyadmin.utils.Util;
import com.arkonrive.springmyadmin.utils.curd.UpdateSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Controller
@RequestMapping("/update")
public class UpdateController {
    @RequestMapping(value="/updateRow", method= RequestMethod.POST)
    @ResponseBody
    public JSONObject updateRow(HttpServletRequest request, HttpServletResponse response, @RequestBody String data) {
        String[] userInfo = CookieUtil.getUserInfo(request);
        if (userInfo == null) return Util.add2JSON("err", "no cookies");
        String username = userInfo[0];
        String password = userInfo[1];
        String database = JSONObject.parseObject(data).getString("database");
        String table = JSONObject.parseObject(data).getString("table");
        JSONObject column_values = JSONObject.parseObject(data).getJSONObject("column_values");
        JSONObject where = JSONObject.parseObject(data).getJSONObject("where");
        if (database == null || table == null) return Util.add2JSON("err", "invalid database or table");

        try {
            UpdateSQL.updateRow(database, table, username, password, column_values, where);
            return Util.add2JSON("res", "create table " + table);
        } catch (SQLException e) {
            response.setStatus(400);
            return Util.add2JSON("err", e.getMessage());
        } catch (ClassNotFoundException e) {
            response.setStatus(404);
            return Util.add2JSON("err", e.getMessage());
        }
    }
}
