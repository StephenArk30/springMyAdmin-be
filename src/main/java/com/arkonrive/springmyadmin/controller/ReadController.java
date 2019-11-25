package com.arkonrive.springmyadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.arkonrive.springmyadmin.utils.CookieUtil;
import com.arkonrive.springmyadmin.utils.Util;
import com.arkonrive.springmyadmin.utils.curd.ReadSQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Controller
@RequestMapping("/read")
public class ReadController {

    @RequestMapping(value="/getData", method= RequestMethod.GET)
    @ResponseBody
    public JSONObject readTable(HttpServletRequest request, HttpServletResponse response,
                                String database, String table,
                                Integer limit, Integer offset,
                                String orderBy, String order) {
        String[] userInfo = CookieUtil.getUserInfo(request);
        if (userInfo == null) return Util.add2JSON("err", "no cookies");
        String username = userInfo[0];
        String password = userInfo[1];

        try {
            JSONObject res = Util.add2JSON("res", ReadSQL.getData(database, table,
                    username, password, null, limit, offset, orderBy, order));
            res.put("count", ReadSQL.getRowCount(database, table, username, password));
            res.put("columns", ReadSQL.getColums(database, table, username, password));
            return res;
        } catch (SQLException e) {
            response.setStatus(400);
            return Util.add2JSON("err", e.getMessage());
        } catch (ClassNotFoundException e) {
            response.setStatus(404);
            return Util.add2JSON("err", e.getMessage());
        }
    }

    @RequestMapping(value="/getDB", method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getDatabases(HttpServletRequest request, HttpServletResponse response) {
        String[] userInfo = CookieUtil.getUserInfo(request);
        if (userInfo == null) return Util.add2JSON("err", "no cookies");
        String username = userInfo[0];
        String password = userInfo[1];

        try {
            return Util.add2JSON("res", ReadSQL.getDatabases(username, password));
        } catch (SQLException e) {
            response.setStatus(400);
            return Util.add2JSON("err", e.getMessage());
        } catch (ClassNotFoundException e) {
            response.setStatus(404);
            return Util.add2JSON("err", e.getMessage());
        }
    }

    @RequestMapping(value="/getTables", method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getTables(HttpServletRequest request, HttpServletResponse response, String database) {
        String[] userInfo = CookieUtil.getUserInfo(request);
        if (userInfo == null) return Util.add2JSON("err", "no cookies");
        String username = userInfo[0];
        String password = userInfo[1];

        try {
            return Util.add2JSON("res", ReadSQL.getTables(database, username, password));
        } catch (SQLException e) {
            response.setStatus(400);
            return Util.add2JSON("err", e.getMessage());
        } catch (ClassNotFoundException e) {
            response.setStatus(404);
            return Util.add2JSON("err", e.getMessage());
        }
    }
}
